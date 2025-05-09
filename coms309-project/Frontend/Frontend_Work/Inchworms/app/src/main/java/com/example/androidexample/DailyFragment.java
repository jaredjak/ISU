package com.example.androidexample;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class DailyFragment extends DialogFragment {
    private RecyclerView dailyRecycler;
    private DailyAdapter dailyAdapter;
    private List<Daily> dailyList;
    private Button dailyExit;
    private String username;


    /**
     * Default constructor that is required by the class
     */
    public DailyFragment() {
        // Empty constructor
    }

    /**
     * This method makes sure to set the dialogFragment view to match_parent for width and wrap_content for height
     */
    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    /**
     * Sets up all of the UI elements that will be present on the screen
     * Makes a call to the method that will hold all of the information necessary
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily, container, false);

        // Fetch the username and clubId for now
        SharedPreferences prefs = getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        username = prefs.getString("username", null);

        dailyRecycler = view.findViewById(R.id.daily_recycler);
        dailyList = new ArrayList<>();
        dailyAdapter = new DailyAdapter(dailyList);

        dailyRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        dailyRecycler.setAdapter(dailyAdapter);

        /* Exit button click listener */
        dailyExit = view.findViewById(R.id.DailyExitButton);
        dailyExit.setOnClickListener(v -> {
            dismiss();
        });

        fetchDailyInfo();

        return view;
    }

    /**
     * Gets all of the information required to populate the Daily/Quest fragment
     */
    private void fetchDailyInfo() {
        String progressURL = "http://coms-3090-013.class.las.iastate.edu:8080/quests/toNextTier/" + username;
        String profileURL = "http://coms-3090-013.class.las.iastate.edu:8080/quests/" + username;

        JsonArrayRequest progressRequest = new JsonArrayRequest(Request.Method.GET, progressURL, null, progressResponse -> {
            Log.d("Progress Request", "Full Response: " + progressResponse);
            JsonObjectRequest profileRequest = new JsonObjectRequest(Request.Method.GET, profileURL, null, profileResponse -> {
                Log.d("Quest info request", "Full Response: " + profileResponse);
                try {
                    dailyList.clear();

                    JSONArray names = profileResponse.getJSONArray("quests");
                    JSONArray counts = profileResponse.getJSONArray("questCounts");
                    JSONArray states = profileResponse.getJSONArray("questStates");
                    JSONArray targets = profileResponse.getJSONArray("questChecks");

                    for (int i = 0; i < names.length(); i++) {
                        String name = names.getString(i);
                        boolean isCompleted = states.getBoolean(i);
                        int current = counts.getInt(i);
                        int target = targets.getInt(i);
                        double progress = progressResponse.getDouble(i);

                        dailyList.add(new Daily(name, isCompleted, progress, current, target));
                    }

                    dailyAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            },
            error -> {
                Log.e("Volley", "Quest fetch failed", error);
            });

            Volley.newRequestQueue(getContext()).add(profileRequest);
        },
        error -> {
            Log.e("Volley", "Progress fetch failed", error);
        });

        Volley.newRequestQueue(getContext()).add(progressRequest);
    }
}
