package com.example.androidexample;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SkinsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SkinsFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    //New variables
    private TextView wormBucks;
    private RecyclerView recyclerView;
    private SkinAdapter skinAdapter;
    private List<Skin> skinList;
    private String username;
    private Button equipButton;
    private String selectedSkin;
    private Button tempBtn;
//    private RequestQueue requestQueue;

    /**
     * Default constructor for the Skins Fragment. Empty constructor.
     */
    public SkinsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * Sets the SkinsFragment and gets the arguments that will be used throughout.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SkinsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SkinsFragment newInstance(String param1, String param2) {
        SkinsFragment fragment = new SkinsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Gets the saved Instance if any for the Skins Fragment.
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /**
     * Sets up all of the UI elements for the screen view.
     * The screen is populated by making a call to the getUSerSkins() method.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_skins, container, false);
        View view = inflater.inflate(R.layout.fragment_skins, container, false);

        //Initialize
        recyclerView = view.findViewById(R.id.skins_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        wormBucks = view.findViewById(R.id.wormbucks);
        skinList = new ArrayList<>();
        selectedSkin = "";
//        equipButton = view.findViewById(R.id.equip_skin_button);

        //Set up bundle that saves username
//        Bundle args = getArguments();
//        username = (args != null) ? args.getString("username") : null;

        SharedPreferences prefs = getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        username = prefs.getString("username", null);

        skinAdapter = new SkinAdapter(getContext(), skinList, this, username);
        recyclerView.setAdapter(skinAdapter);

//        Skin testSkin1 = new Skin("Red", 2);
//        addSkin(username, testSkin1);

//        getUserWormBucks(username);
//        getUserSkins(username);
        getSelectedSkin(username);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserWormBucks(username);
    }


    /**
     * Makes a backend call to get the selected user skin.
     * @param username
     */
    private void getSelectedSkin(String username) {
        String selectedURL = "http://coms-3090-013.class.las.iastate.edu:8080/cosmetics/getSelected/" + username;

        StringRequest selectedRequest = new StringRequest(Request.Method.GET, selectedURL, response -> {
//            selectedSkin = response;
            setSelectedSkin(response);
            getUserSkins(username);
        },
        error -> {
            error.printStackTrace();
            Toast.makeText(getContext(), "Failed to get selected skin", Toast.LENGTH_SHORT).show();
        });

        Volley.newRequestQueue(requireContext()).add(selectedRequest);
    }

    /* GET for user skins*/
    /**
     * This method gets all of the user's skins. It adds the given skin information into to the Skin class / skinList.
     * @param username
     */
    private void getUserSkins(String username) {
//        String userURL = "http://coms-3090-013.class.las.iastate.edu:8080/cosmetics/getAllSkins/user/" + username;
        String userURL = "http://coms-3090-013.class.las.iastate.edu:8080/cosmetics/getAll/user/" + username;

        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, userURL, null, response -> {
            Log.d("API_RESPONSE", "Full Response: " + response.toString());
            try {
                if (response.has("counts") && response.has("allWorms")) {
                    JSONArray colorArray = response.getJSONArray("allWorms");
                    JSONArray countArray = response.getJSONArray("counts");

                    skinList.clear();

                    for (int i = 0; i < colorArray.length(); i++) {
                        String color = colorArray.getString(i);
                        int count = countArray.getInt(i);
                        skinList.add(new Skin(color, count));
                        Log.d("Response", "Worm Color: " + color + ", Count:" + count);
                    }
                } else {
                    Log.e("API_ERROR", "wormbox is missing in the response");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            skinAdapter.notifyDataSetChanged();
        },
        error -> {
            Log.e("Error", "Error: " + error.toString());
        });

        Volley.newRequestQueue(requireContext()).add(jsonObjRequest);
    }

    /* GET for user wormBucks */
    /**
     * This method gets the user's current amount of wormbucks.
     * This is used to display the user's wormbucks on the top of the screen.
     * It is updated any time a user buys or sells a skin in the marketplace.
     * @param username
     */
    private void getUserWormBucks(String username) {
        String wormURL = "http://coms-3090-013.class.las.iastate.edu:8080/cosmetics/getWormbucks/" + username;

        Log.d("Debug", "Fetching wormbucks");
        StringRequest request = new StringRequest(Request.Method.GET, wormURL, response -> {
            Log.d("API_RESPONSE", "Full Response: " + response.toString());
            try {
                double wormMoney = Double.parseDouble(response.trim());
                wormBucks.setText("WormBucks: " + String.format("%.2f", wormMoney));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        },
                error -> {
                    Log.e("API_ERROR", "Error getting worm bucks: " + error.toString());
                    Toast.makeText(getContext(), "Error getting worm bucks", Toast.LENGTH_SHORT).show();
                });
        Volley.newRequestQueue(requireContext()).add(request);
    }

    /**
     * Properly sets UI up for equipping the click skin.
     * Makes a call to the equipSkin() method
     */
    public void onEquipClicked(String username, Skin skin) {
        setSelectedSkin(skin.getColor());
        skinAdapter.notifyDataSetChanged();
        equipSkin(username, skin);
    }

    /**
     * Sends a put request to the backend to equip a new skin.
     * @param username
     * @param skin
     */
    public void equipSkin(String username, Skin skin) {
//        String removeURL = "http://coms-3090-013.class.las.iastate.edu:8080/cosmetics/remove/user/" + username;
        String equipURL = "http://coms-3090-013.class.las.iastate.edu:8080/cosmetics/setSelected/" + skin.getColor() + "/" + username;
        String skinColor = skin.getColor();

        Log.d("DEBUG", "Sending request to: " + equipURL);
        Log.d("DEBUG", "Request body: " + skinColor);

        StringRequest request = new StringRequest(Request.Method.PUT, equipURL, response -> {
            Log.d("Request response: ", response);
            if (Boolean.parseBoolean(response)) {
                Toast.makeText(getContext(), "Equipped: " + skinColor, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Failed to equip", Toast.LENGTH_SHORT).show();
            }
        },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(getContext(), "Failed to equip skin", Toast.LENGTH_SHORT).show();
                });

        Volley.newRequestQueue(requireContext()).add(request);
    }

    /**
     * HELPER METHOD
     * Used as a getter for the skin (Did not want to edit the Skin class)
     */
    public String getSelectedSkinColor() {
        return selectedSkin;
    }

    /**
     * HELPER METHOD
     * Used as a setter for the skin (Did not want to edit the Skin class)
     */
    public void setSelectedSkin(String skin) {
        selectedSkin = skin;
    }
}