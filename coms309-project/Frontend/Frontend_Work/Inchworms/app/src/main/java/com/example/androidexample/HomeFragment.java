package com.example.androidexample;


import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button adminBtn;
    private String username;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Fetch the username and clubId for now
        SharedPreferences prefs = getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        username = prefs.getString("username", null);
        int clubId = prefs.getInt("clubId", 0);

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Find the button and set an onClickListener
        Button gameplayButton = view.findViewById(R.id.gameplay_btn);
        gameplayButton.setOnClickListener(v -> {
            // Navigate to LobbyActivity
            Intent intent = new Intent(getActivity(), LobbyActivity.class);
            intent.putExtra("username", username); // Pass the username to the LobbyActivity
            startActivity(intent);
        });

        // Find the spectate button and set an onClickListener
        Button spectateButton = view.findViewById(R.id.spectate_btn);
        spectateButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SpectateLobbyActivity.class);
            intent.putExtra("username", username); // Pass the username
            startActivity(intent);
        });

        // Open the Daily Challenges for the user
        Button dailyButton = view.findViewById(R.id.daily_btn);
        dailyButton.setOnClickListener(v -> {
//            DailyFragment dailyFragment = new DailyFragment();
//            dailyFragment.show(getParentFragmentManager(), "Daily");
            resetDailyCheck(username);
        });

        /* Admin button setup + click listener */
        adminBtn = view.findViewById(R.id.admin_btn);
        adminBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AdminActivity.class);
            startActivity(intent);
        });

        /* Check if user is admin */
        checkAdminStatus(username);

        /* Dialog box check for admin */
        boolean showAdminDialog = getArguments() != null && getArguments().getBoolean("showAdminDialog", false);

        if (showAdminDialog) {
            showAdminPromptDialog();
            getArguments().putBoolean("showAdminDialog", false);
        }

        return view;
    }

    private void resetDailyCheck(String username) {
        String resetURL = "http://coms-3090-013.class.las.iastate.edu:8080/quests/resetTime/" + username;

        StringRequest request = new StringRequest(Request.Method.PUT, resetURL, response -> {
            DailyFragment dailyFragment = new DailyFragment();
            dailyFragment.show(getParentFragmentManager(), "Daily");
        },
        error -> {
            Toast.makeText(getContext(), "Failed to check daily reset", Toast.LENGTH_SHORT).show();
        });

        Volley.newRequestQueue(getContext()).add(request);
    }

    private void showAdminPromptDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Admin Access");
        builder.setMessage("Do you want to become an admin? Answer the question then" +
                "\n\nQuestion: Who authorized the metaphysical lint to unionize under the jurisdiction of interdimensional soup?");
        builder.setCancelable(false);

        final EditText input = new EditText(getActivity());
        input.setHint("Enter your answer here");

        builder.setView(input);

        builder.setPositiveButton("Submit", (dialog, which) -> {
            String answer = input.getText().toString();
            if (isCorrectAnswer(answer)) {
                giveAdmin(username);
            }
            dialog.dismiss();
        });

        builder.setNegativeButton("Exit", (dialog, which) -> {
            dialog.dismiss();
        });

        builder.create().show();
    }

    private boolean isCorrectAnswer(String answer) {
        return "gobbledy-gook".equalsIgnoreCase(answer);
    }

    /* Method for promoting a user to admin via their username */
    private void giveAdmin(String username) {
        String adminURL = "http://coms-3090-013.class.las.iastate.edu:8080/admin/promote/" + username;

        StringRequest request = new StringRequest(Request.Method.PUT, adminURL, response -> {
            Toast.makeText(getContext(), "Hello New Admin", Toast.LENGTH_SHORT).show();
            adminBtn.setVisibility(View.VISIBLE);
        },
        error -> {
            Toast.makeText(getContext(), "Failed to promote to admin", Toast.LENGTH_SHORT).show();
            error.printStackTrace();
        });

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(request);
    }

    /* Method for checking to see whether the user is an admin or not */
    private void checkAdminStatus(String username) {
        String newAdminURL = "http://coms-3090-013.class.las.iastate.edu:8080/admin/status/" + username;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, newAdminURL, null, response -> {
            try {
                boolean isAdmin = response.getBoolean("admin");
                Log.d("Admin Check", "isAdmin: " + isAdmin);
                if (isAdmin) {
                    adminBtn.setVisibility(View.VISIBLE);
                } else {
                    adminBtn.setVisibility(View.GONE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                adminBtn.setVisibility(View.GONE);
            }
        },
        error -> {
            error.printStackTrace();
            adminBtn.setVisibility(View.GONE);
        });

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(request);
    }
}