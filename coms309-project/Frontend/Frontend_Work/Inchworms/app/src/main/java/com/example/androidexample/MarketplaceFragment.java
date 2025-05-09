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
import com.android.volley.RequestQueue;
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
 * Use the {@link MarketplaceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MarketplaceFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    //New variables
    private TextView wormBucks;
    private RecyclerView recyclerView;
    private MarketAdapter marketAdapter;
    private List<Skin> skinList;
    private double[] buyPrices;
    private double[] sellPrices;
    private RequestQueue requestQueue;

    /**
     * Default constructor for the Marketplace Fragment. Empty constructor.
     */
    public MarketplaceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * Sets the MarketplaceFragment and gets the arguments that will be used throughout.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MarketplaceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MarketplaceFragment newInstance(String param1, String param2) {
        MarketplaceFragment fragment = new MarketplaceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Gets the saved Instance if any for the Marketplace Fragment.
     *
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
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_marketplace, container, false);
        View view = inflater.inflate(R.layout.fragment_marketplace, container, false);

        recyclerView = view.findViewById(R.id.market_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        wormBucks = view.findViewById(R.id.wormbucks);
        skinList = new ArrayList<>();
        buyPrices = new double[0];
        sellPrices = new double[0];

//        getMarketInfo();

        return view;
    }

    /**
     * After the view has been created, this method will make a call to get all of the market info.
     * This method was made as a way to ensure that all UI elements have been set up before getting important information.
     *
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getMarketInfo();
    }

    /**
     * This method is used to get the information for all currently available skins on the market.
     * A GET request is used to get the information which is then stored.
     * There is also a call to get the user's wormbucks so as to populate that portion as well.
     */
    private void getMarketInfo() {
        String marketURL = "http://coms-3090-013.class.las.iastate.edu:8080/cosmetics/getMarket";

        requestQueue = Volley.newRequestQueue(requireContext());

        StringRequest stringrequest = new StringRequest(Request.Method.GET, marketURL,
                response -> Log.d("API_RESPONSE", "Raw response: " + response),
                    error -> Log.e("VolleyError", "Error fetching market data: " + error.toString())
        );
        requestQueue.add(stringrequest);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, marketURL, null, response -> {
            Log.d("API_RESPONSE", "Full Response: " + response.toString());
            try {
                JSONArray colorArray = response.getJSONArray("skins");
                JSONArray countArray = response.getJSONArray("available");
                JSONArray buyArray = response.getJSONArray("prices");
                JSONArray sellArray = response.getJSONArray("sellPrices");

                skinList.clear();
                buyPrices = new double[colorArray.length()];
                sellPrices = new double[colorArray.length()];

                for (int i = 0; i < colorArray.length(); i++) {
                    String color = colorArray.getString(i);
                    int count = countArray.getInt(i);
                    double buyPrice = buyArray.getDouble(i);
                    double sellPrice = sellArray.getDouble(i);

                    skinList.add(new Skin(color, count));
                    buyPrices[i] = buyPrice;
                    sellPrices[i] = sellPrice;
                }

                //Set up bundle that saves username
//                Bundle args = getArguments();
//                String username = (args != null) ? args.getString("username") : null;

                SharedPreferences prefs = getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
                String username = prefs.getString("username", null);


                getUserWormBucks(username);

//                if (marketAdapter == null) {
//                    marketAdapter = new MarketAdapter(getContext(), skinList, this, username, buyPrices, sellPrices);
//                    recyclerView.setAdapter(marketAdapter);
//                } else {
//                    marketAdapter.notifyDataSetChanged();
//                }
                marketAdapter = new MarketAdapter(getContext(), skinList, this, username, buyPrices, sellPrices);
                recyclerView.setAdapter(marketAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        },
                error -> Log.e("VolleyError", "Error fetching market data: " + error.toString()));

        requestQueue.add(request);
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
     * This method gets a skin and adds it to a user's skin/cosmetic collection on success.
     * It works by using a PUT request, which transfers to the server/backend where it is added to the user's skin repository.
     * The user's wormbucks decrease with the corresponding price of the skin bought.
     * @param username
     * @param skin
     */
    protected void buySkin(String username, Skin skin) {
        String buyURL = "http://coms-3090-013.class.las.iastate.edu:8080/cosmetics/buyMarket/" + skin.getColor() + "/" + username;
        String skinColor = skin.getColor();

        Log.d("DEBUG", "Sending request to: " + buyURL);
        Log.d("DEBUG", "Request body: " + skinColor);
        Log.d("DEBUG", "User: " + username);

        StringRequest request = new StringRequest(Request.Method.PUT, buyURL, response -> {
            Toast.makeText(getContext(), "Added", Toast.LENGTH_SHORT).show();
            getMarketInfo();
        }, error -> {
            Log.e("VolleyError", "Error adding: " + error.toString());
            if (error.networkResponse != null) {
                Log.e("VolleyError", "Response code: " + error.networkResponse.statusCode);
                Log.e("VolleyError", "Response data: " + new String(error.networkResponse.data));
            }
            Toast.makeText(getContext(), "Failed to add skin", Toast.LENGTH_SHORT).show();
        });
        Volley.newRequestQueue(requireContext()).add(request);
    }

    /**
     * This method gets a skin and deletes it from a user's skin/cosmetics collection on success.
     * It works by using a PUT request, which transfers to te server/backend where it is removed from the user's skin repository.
     * The user's wormbucks increase with the corresponding price of the skin sold.
     * @param username
     * @param skin
     */
    protected void sellSkin(String username, Skin skin) {
        String sellURL = "http://coms-3090-013.class.las.iastate.edu:8080/cosmetics/sellMarket/" + skin.getColor() + "/" + username;

        Log.d("DEBUG", "Sending request to: " + sellURL);
        Log.d("DEBUG", "Request body: " + skin.getColor());

        StringRequest request = new StringRequest(Request.Method.PUT, sellURL, response -> {
            Toast.makeText(getContext(), "Sold", Toast.LENGTH_SHORT).show();
            getMarketInfo();
        }, error -> {
            Log.e("VolleyError", "Error selling: " + error.toString());
            Toast.makeText(getContext(), "Failed to sell", Toast.LENGTH_SHORT).show();
        });
        Volley.newRequestQueue(requireContext()).add(request);
    }
}