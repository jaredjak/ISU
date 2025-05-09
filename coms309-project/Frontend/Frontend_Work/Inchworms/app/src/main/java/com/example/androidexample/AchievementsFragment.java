package com.example.androidexample;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AchievementsFragment extends Fragment {
    private RecyclerView achievementView;
    private AchievementAdapter achievementAdapter;
    private List<Achievement> achievementsList;
    private String username;
    private String equipped;

    private int[] moneyTiers;
    private int[] nonMoneyTiers;

    public AchievementsFragment() {
        // Default Constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_achievements, container, false);

        // Fetch the username
        SharedPreferences prefs = getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        username = prefs.getString("username", null);

        achievementsList = new ArrayList<>();
        equipped = "";
        moneyTiers = new int[0];
        nonMoneyTiers = new int[0];

        achievementView = view.findViewById(R.id.achievements_recycler_view);
        achievementView.setLayoutManager(new LinearLayoutManager((getContext())));

        achievementAdapter = new AchievementAdapter(getContext(), achievementsList, equipped, moneyTiers, nonMoneyTiers, this::onEquipClicked);
        achievementView.setAdapter(achievementAdapter);

        fetchAchievements();

        return view;
    }

    /* Chaining the four getters for achievement information into one method (🥱) */
    private void fetchAchievements() {
        // Make get call and put all the necessary information into achievementList
        String achievesURL = "http://coms-3090-013.class.las.iastate.edu:8080/achievements/" + username;
        String tiersURL = "http://coms-3090-013.class.las.iastate.edu:8080/achievements/getNextTiers/" + username;
        String namesURL = "http://coms-3090-013.class.las.iastate.edu:8080/achievements/getNames";
        String descURL = "http://coms-3090-013.class.las.iastate.edu:8080/achievements/getDescription";

        RequestQueue queue = Volley.newRequestQueue(requireContext());

        //Debugging purposes
//        StringRequest stringReq = new StringRequest(Request.Method.GET, tiersURL, response -> {
//            Log.d("API_RESPONSE", "Full Response: " + response);
//        },
//                error -> {
//                    Log.e("Error", "Error: " + error.toString());
//                });
//
//        Volley.newRequestQueue(requireContext()).add(stringReq);

        /* Section 1 - getting the basic achievement information */
        JsonObjectRequest achieveRequest = new JsonObjectRequest(Request.Method.GET, achievesURL, null, response -> {
            Log.d("Achievement Request", "Full Response: " + response);
            try {
                achievementsList.clear();

                JSONArray states = response.getJSONArray("achievementStates");
                JSONArray counts = response.getJSONArray("achievementCounts");

                Log.d("Counts check", counts.toString());
                Log.d("States check", states.toString());

                JSONArray nonMoneyArray = response.getJSONArray("nonMoneyTiers");
                nonMoneyTiers = new int[nonMoneyArray.length()];
                for (int i = 0; i < nonMoneyArray.length(); i++) {
                    try {
                        nonMoneyTiers[i] = nonMoneyArray.getInt(i);
                        Log.d("nonMoneyTier Check", String.valueOf(nonMoneyTiers[i]));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                JSONArray moneyArray = response.getJSONArray("moneyTiers");
                moneyTiers = new int[moneyArray.length()];
                for (int i = 0; i < moneyArray.length(); i++) {
                    try {
                        moneyTiers[i] = moneyArray.getInt(i);
                        Log.d("moneyTier Check", String.valueOf(moneyTiers[i]));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                String selected = response.getString("selected");
                equipped = selected;
                int tierSelected = response.getInt("tier");

                /* Section 2 - getting the tier information */
                JsonArrayRequest tierRequest = new JsonArrayRequest(Request.Method.GET, tiersURL, null, tierResponse -> {
                    Log.d("API_RESPONSE", "Full Response: " + tierResponse);
                        /* Section 3 - getting the name information */
                        JsonArrayRequest nameRequest = new JsonArrayRequest(Request.Method.GET, namesURL, null, nameResponse -> {
                            Log.d("API_RESPONSE", "Full Response: " + nameResponse);
                                /* Section 4 - getting the description information */
                                JsonArrayRequest descRequest = new JsonArrayRequest(Request.Method.GET, descURL, null, descResponse -> {
                                    Log.d("API_RESPONSE", "Full Response: " + descResponse);
                                    for (int i = 0; i < descResponse.length(); i++) {
                                        try {
                                            String name = nameResponse.getString(i);
                                            String description = descResponse.getString(i);
                                            int state = states.getInt(i);
//                                            Log.d("State Check", String.valueOf(state));
                                            int count = counts.getInt(i);
//                                            Log.d("Count Check", String.valueOf(count));
                                            boolean isTiered = (i < 9);
                                            float progress = isTiered ? (float) tierResponse.getDouble(i) : (count == 1 ? 1.0f : 0.0f);
                                            boolean isMoneyTier = (i == 4 || i == 5);
                                            boolean isEquipped = name.equals(selected);

                                            achievementsList.add(new Achievement(name, description, tierSelected, state, count, progress, isTiered, isMoneyTier, isEquipped));

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    achievementAdapter.updateTiers(moneyTiers, nonMoneyTiers);
                                    achievementAdapter.notifyDataSetChanged();
                                },
                                        error -> Log.e("Volley", "Description fetch failed", error));

                                queue.add(descRequest);

                        },
                                error -> Log.e("Volley", "Names fetch failed", error));

                        queue.add(nameRequest);

                },
                        error -> Log.e("Volley", "Tier fetch failed", error));

                queue.add(tierRequest);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        },
                error -> Log.e("Volley", "Achievement information fetch failed", error));

        queue.add(achieveRequest);

    }

    private void onEquipClicked(String selected) {
        int selectedId = -1;
        for (int i = 0; i < achievementsList.size(); i++) {
            Achievement a = achievementsList.get(i);
            a.setEquipped(a.getName().equals(selected));
            if (a.getName().equals(selected)) {
                selectedId = i;
//                a.setEquipped(a.getName().equals(selected));
            }
        }
        achievementAdapter.notifyDataSetChanged();
        equipAchievement(selected, selectedId);
    }

    private void equipAchievement(String achievementName, int id) {
        String equipURL = "http://coms-3090-013.class.las.iastate.edu:8080/achievements/selected/" + username + "/" + id;

        Log.d("id value: ", String.valueOf(id));
        StringRequest request = new StringRequest(Request.Method.PUT, equipURL, response -> {
            Log.d("Request response: ", response);
            if (Boolean.parseBoolean(response)) {
                Toast.makeText(getContext(), "Equipped: " + achievementName, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Failed to equip", Toast.LENGTH_SHORT).show();
            }
        },
        error -> {
            error.printStackTrace();
            Toast.makeText(getContext(), "Failed to equip achievement", Toast.LENGTH_SHORT).show();
        });

        Volley.newRequestQueue(requireContext()).add(request);
    }
}
