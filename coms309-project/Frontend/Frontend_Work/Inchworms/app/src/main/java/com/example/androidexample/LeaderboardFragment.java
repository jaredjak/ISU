package com.example.androidexample;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LeaderboardFragment extends Fragment {

    private RecyclerView topRecyclerView, middleRecyclerView, bottomRecyclerView;
    private ProgressBar progressBar;
//    private Button addButton, deleteButton;
    private EditText usernameEditText, idEditText, scoreEditText, winStreakEditText;
    private Button submitButton;

    private static final String BASE_URL = "http://coms-3090-013.class.las.iastate.edu:8080/leaderboard";
    private static final String URL_LEADERBOARD = BASE_URL + "/leaders";
    private static final String URL_HALL_OF_FAME = BASE_URL + "/hall-of-fame";
    private static final String URL_WALL_OF_SHAME = BASE_URL + "/wall-of-shame";

    private JSONArray leaderboardData;
    private JSONArray wallOfShameData;
    private JSONArray hallOfFameData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        topRecyclerView = view.findViewById(R.id.topRecyclerView);
        middleRecyclerView = view.findViewById(R.id.middleRecyclerView);
        bottomRecyclerView = view.findViewById(R.id.bottomRecyclerView);
        progressBar = view.findViewById(R.id.progressBar);
//        addButton = view.findViewById(R.id.addButton);
//        deleteButton = view.findViewById(R.id.deleteButton);

        // Set GridLayoutManager with 2 columns for all RecyclerViews
        topRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        middleRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        bottomRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        getLeaderboard();
        getHallOfFame();
        getWallOfShame();

//        addButton.setOnClickListener(v -> showAddUserDialog());
//        deleteButton.setOnClickListener(v -> showDeleteUserDialog());

        return view;
    }

//    private void showAddUserDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        LayoutInflater inflater = getLayoutInflater();
//        View dialogView = inflater.inflate(R.layout.dialog_add_user_leaderboard, null);
//        builder.setView(dialogView);
//
//        // Find the input fields and submit button
//        usernameEditText = dialogView.findViewById(R.id.usernameEditText);
//        idEditText = dialogView.findViewById(R.id.idEditText);
//        scoreEditText = dialogView.findViewById(R.id.scoreEditText);
//        winStreakEditText = dialogView.findViewById(R.id.winStreakEditText);
//        submitButton = dialogView.findViewById(R.id.submitButton);
//
//        builder.setTitle("Add Leaderboard Entry");
//
//        AlertDialog dialog = builder.create();
//
//        // Set onClickListener for the Submit button inside the dialog
//        submitButton.setOnClickListener(v -> {
//            try {
//                // Get input values
//                String username = usernameEditText.getText().toString();
//                int userId = Integer.parseInt(idEditText.getText().toString());
//                int score = Integer.parseInt(scoreEditText.getText().toString());
//                int winStreak = Integer.parseInt(winStreakEditText.getText().toString());
//
//                // Check if the user is in the leaderboard
//                checkUserInLeaderboard(username, isInLeaderboard -> {
//                    if (isInLeaderboard) {
//                        // If the user is found, update their leaderboard entry
//                        updateUserLeaderboardEntry(username, score, winStreak, dialog);
//                    } else {
//                        // If the user is not found, create a new leaderboard entry
//                        createUserLeaderboardEntry(userId, score, winStreak, dialog);
//                    }
//                });
//
//            } catch (NumberFormatException e) {
//                Toast.makeText(getContext(), "Please enter valid numbers", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        // Cancel button for the dialog
//        builder.setNegativeButton("Cancel", (dialog1, which) -> dialog1.dismiss());
//
//        dialog.show();
//    }

//    private void checkUserInLeaderboard(String username, ResponseListener listener) {
//        String url = BASE_URL + "/rank/" + username;
//
//        StringRequest request = new StringRequest(Request.Method.GET, url,
//                response -> listener.onResponse(true),  // If the user is found
//                error -> {
//                    if (error.networkResponse != null && error.networkResponse.statusCode == 404) {
//                        listener.onResponse(false);  // User not found, return false
//                    } else {
//                        listener.onResponse(false);  // Handle other errors by returning false
//                    }
//                });
//
//        VolleySingleton.getInstance(getContext()).addToRequestQueue(request);
//    }

    // The interface for the callback - had to google this so many times
//    public interface ResponseListener {
//        void onResponse(boolean isInLeaderboard);
//    }

//    private void updateUserLeaderboardEntry(String username, int score, int winStreak, AlertDialog dialog) {
//        String url = BASE_URL + "/" + username;
//
//        JSONObject requestBody = new JSONObject();
//        try {
//            requestBody.put("score", score);
//            requestBody.put("winStreak", winStreak);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, requestBody,
//                response -> {
//                    Toast.makeText(getContext(), "Entry updated successfully", Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();
//                    getLeaderboard();
//                    getHallOfFame();
//                    getWallOfShame();
//                },
//                error -> Toast.makeText(getContext(), "Error updating entry", Toast.LENGTH_SHORT).show());
//
//        VolleySingleton.getInstance(getContext()).addToRequestQueue(request);
//    }

//    private void createUserLeaderboardEntry(int userId, int score, int winStreak, AlertDialog dialog) {
//        //String url = BASE_URL + "/leaderboard";
//        String url = BASE_URL;
//
//        JSONObject requestBody = new JSONObject();
//        try {
//            JSONObject userObject = new JSONObject();
//            userObject.put("id", userId);
//            requestBody.put("user", userObject);
//            requestBody.put("score", score);
//            requestBody.put("winStreak", winStreak);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody,
//                response -> {
//                    Toast.makeText(getContext(), "Entry created successfully", Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();
//                    getLeaderboard();
//                    getHallOfFame();
//                    getWallOfShame();
//                },
//                error -> Toast.makeText(getContext(), "Error creating entry", Toast.LENGTH_SHORT).show());
//
//        VolleySingleton.getInstance(getContext()).addToRequestQueue(request);
//    }

//    private void showDeleteUserDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        LayoutInflater inflater = getLayoutInflater();
//        View dialogView = inflater.inflate(R.layout.dialog_delete_user_leaderboard, null);
//        builder.setView(dialogView);
//
//        // Find the input field and delete button
//        EditText usernameInput = dialogView.findViewById(R.id.usernameInput);
//        Button deleteButton = dialogView.findViewById(R.id.deleteButton);
//
//        builder.setTitle("Delete Leaderboard Entry");
//
//        AlertDialog dialog = builder.create();
//
//        // Set onClickListener for the Delete button inside the dialog
//        deleteButton.setOnClickListener(v -> {
//            String username = usernameInput.getText().toString();
//            deleteUserEntry(username);
//            dialog.dismiss();
//
//            getLeaderboard();
//            getHallOfFame();
//            getWallOfShame();
//        });
//
//        // Cancel button for the dialog
//        builder.setNegativeButton("Cancel", (dialog1, which) -> dialog1.dismiss());
//
//        dialog.show();
//    }

//    private void deleteUserEntry(String username) {
//        //String url = BASE_URL + "/leaderboard/" + username;
//        String url = BASE_URL + "/" + username;
//
//        StringRequest request = new StringRequest(Request.Method.DELETE, url,
//                response -> {
//                    Toast.makeText(getContext(), "Entry deleted successfully", Toast.LENGTH_SHORT).show();
//                    getLeaderboard(); // Refresh the leaderboard view
//                    getHallOfFame();
//                    getWallOfShame();
//                },
//                error -> {
//                    // Check if the response is actually a success - debugging purposes lol
//                    if (error.networkResponse != null && error.networkResponse.statusCode == 204) {
//                        Toast.makeText(getContext(), "Entry deleted successfully", Toast.LENGTH_SHORT).show();
//                        getLeaderboard();
//                        getHallOfFame();
//                        getWallOfShame();
//                    } else {
//                        Toast.makeText(getContext(), "Bye Bye :(", Toast.LENGTH_SHORT).show();
//                    }
//                });
//        VolleySingleton.getInstance(getContext()).addToRequestQueue(request);
//    }

    private void getLeaderboard() {
        progressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL_LEADERBOARD, null,
                response -> {
                    progressBar.setVisibility(View.GONE);
                    leaderboardData = response; // Store the response in the global JSON array variable
                    topRecyclerView.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
                        @Override
                        public int getItemCount() {
                            return leaderboardData.length();
                        }

                        @NonNull
                        @Override
                        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = getLayoutInflater().inflate(R.layout.leaderboard_entry, parent, false);
                            return new RecyclerView.ViewHolder(view) {};
                        }

                        @Override
                        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                            try {
                                JSONObject entry = leaderboardData.getJSONObject(position);
                                JSONObject user = entry.getJSONObject("user");
                                String username = user.getString("username");
                                int score = entry.getInt("score");
                                int winStreak = entry.getInt("winStreak");

                                TextView usernameText = holder.itemView.findViewById(R.id.usernameText);
                                TextView scoreText = holder.itemView.findViewById(R.id.scoreText);
                                TextView winStreakText = holder.itemView.findViewById(R.id.winStreakText);

                                usernameText.setText(username);
                                scoreText.setText(String.valueOf(score));
                                winStreakText.setText(String.valueOf(winStreak));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                },
                error -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Error fetching data", Toast.LENGTH_SHORT).show();
                });
        VolleySingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    private void getHallOfFame() {
        progressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL_HALL_OF_FAME, null,
                response -> {
                    progressBar.setVisibility(View.GONE);
                    hallOfFameData = response; // Store the response in the global JSON array variable
                    middleRecyclerView.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
                        @Override
                        public int getItemCount() {
                            return hallOfFameData.length();
                        }

                        @NonNull
                        @Override
                        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = getLayoutInflater().inflate(R.layout.leaderboard_entry, parent, false);
                            return new RecyclerView.ViewHolder(view) {};
                        }

                        @Override
                        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                            try {
                                JSONObject entry = hallOfFameData.getJSONObject(position);
                                JSONObject user = entry.getJSONObject("user");
                                String username = user.getString("username");
                                int score = entry.getInt("score");
                                int winStreak = entry.getInt("winStreak");

                                TextView usernameText = holder.itemView.findViewById(R.id.usernameText);
                                TextView scoreText = holder.itemView.findViewById(R.id.scoreText);
                                TextView winStreakText = holder.itemView.findViewById(R.id.winStreakText);

                                usernameText.setText(username);
                                scoreText.setText(String.valueOf(score));
                                winStreakText.setText(String.valueOf(winStreak));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                },
                error -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Error fetching Hall of Fame", Toast.LENGTH_SHORT).show();
                });
        VolleySingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    private void getWallOfShame() {
        progressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL_WALL_OF_SHAME, null,
                response -> {
                    progressBar.setVisibility(View.GONE);
                    wallOfShameData = response; // Store the response in the global JSON array variable
                    bottomRecyclerView.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
                        @Override
                        public int getItemCount() {
                            return wallOfShameData.length();
                        }

                        @NonNull
                        @Override
                        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = getLayoutInflater().inflate(R.layout.leaderboard_entry, parent, false);
                            return new RecyclerView.ViewHolder(view) {};
                        }

                        @Override
                        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                            try {
                                JSONObject entry = wallOfShameData.getJSONObject(position);
                                JSONObject user = entry.getJSONObject("user");
                                String username = user.getString("username");
                                int score = entry.getInt("score");
                                int winStreak = entry.getInt("winStreak");

                                TextView usernameText = holder.itemView.findViewById(R.id.usernameText);
                                TextView scoreText = holder.itemView.findViewById(R.id.scoreText);
                                TextView winStreakText = holder.itemView.findViewById(R.id.winStreakText);

                                usernameText.setText(username);
                                scoreText.setText(String.valueOf(score));
                                winStreakText.setText(String.valueOf(winStreak));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                },
                error -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Error fetching Wall of Shame", Toast.LENGTH_SHORT).show();
                });
        VolleySingleton.getInstance(getContext()).addToRequestQueue(request);
    }
}