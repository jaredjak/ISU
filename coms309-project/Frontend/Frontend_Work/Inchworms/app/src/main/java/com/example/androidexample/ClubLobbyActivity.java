package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClubLobbyActivity extends AppCompatActivity {

    private TextView lobbyStatusTextView;
    private Handler handler = new Handler();
    private String username;
    private String currentGameState = "LOBBY"; // Default state
    private GameplayWebSocketManager webSocketManager;
    private String lobbyId;
    private List<Map<String, String>> lobbyUsers = new ArrayList<>();
    private int clubId;
    //private LobbyUsersAdapter lobbyUsersAdapter;

    /*
     * This method is called when the activity is first created. It sets up the UI and starts the game lobby process.
     * It retrieves the username from the Intent, initializes the TextView for displaying lobby status,
     * 
     * @param savedInstanceState The saved instance state bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        // Get the username from the Intent
        username = getIntent().getStringExtra("username");
        clubId = getIntent().getIntExtra("clubId", -1); // -1 is the default if not found
        if (clubId == -1) {
            Log.e("LobbyActivity", "Club ID not found in Intent");
            finish();
            return;
        } else {
            Log.d("LobbyActivity", "Club ID: " + clubId);
        }

        // Reference the TextView for displaying the lobby status
        lobbyStatusTextView = findViewById(R.id.lobby_status);

        // Reset current game state
        currentGameState = "LOBBY";

        // // Initialize the RecyclerView and Adapter
        // RecyclerView lobbyUsersRecyclerView = findViewById(R.id.lobby_users_recycler_view);
        // lobbyUsersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // lobbyUsersAdapter = new LobbyUsersAdapter(this, lobbyUsers);
        // lobbyUsersRecyclerView.setAdapter(lobbyUsersAdapter);

        // Join the game lobby and WebSocket
        joinGame();
    }

    /*
     * This method is used to join the WebSocket server for real-time updates.
     * It sets up a listener to handle incoming messages and connection events.
     * 
     * @param lobbyId The ID of the game lobby to join.
     */
    private void joinWebSocket(String lobbyId) {
        String webSocketUrl = "ws://coms-3090-013.class.las.iastate.edu:8080/gameSocket/" + lobbyId + "/" + username;
    
        // Initialize WebSocket Manager
        webSocketManager = GameplayWebSocketManager.getInstance();
        webSocketManager.setWebSocketListener(new GameplayWebSocketListener() {
            @Override
            public void onWebSocketOpen(org.java_websocket.handshake.ServerHandshake handshakedata) {
                Log.d("LobbyActivity", "Connected to WebSocket server");
            }
    
            @Override
            public void onWebSocketMessage(String message) {
                handleWebSocketMessage(message);
            }
    
            @Override
            public void onWebSocketClose(int code, String reason, boolean remote) {
                Log.d("LobbyActivity", "WebSocket closed: " + reason);
            }
    
            @Override
            public void onWebSocketError(Exception ex) {
                Log.e("LobbyActivity", "WebSocket error", ex);
            }
        });
    
        // Connect to the WebSocket server
        webSocketManager.connectWebSocket(webSocketUrl);
    }

    /*
     * This method is used to join a game lobby by sending a POST request to the server.
     * It retrieves the lobby ID from the server response and starts the WebSocket connection.
     */
    private void joinGame() {
        String joinGameUrl = "http://coms-3090-013.class.las.iastate.edu:8080/game/joinGame/" + clubId + "/" + username;
    
        JsonObjectRequest joinRequest = new JsonObjectRequest(Request.Method.POST, joinGameUrl, null,
                response -> {
                    try {
                        // Extract and store the lobbyId
                        lobbyId = response.getString("lobbyId");
                        Log.d("LobbyActivity", "Joined game with lobbyId: " + lobbyId);
    
                        // Join the WebSocket
                        joinWebSocket(lobbyId);
    
                        // Start polling both the GameState and the list of users in the lobby
                        pollGameStateAndLobbyUsers(lobbyId);

                    } catch (JSONException e) {
                        Log.e("LobbyActivity", "Error parsing join game response", e);
                    }
                },
                error -> {
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        String errorMessage = new String(error.networkResponse.data);
                        Log.e("LobbyActivity", "Join game error: " + errorMessage);
    
                        // Handle moderation errors
                        if (errorMessage.contains("user is banned")) {
                            Toast.makeText(this, "You are banned from joining games.", Toast.LENGTH_LONG).show();
                        } else if (errorMessage.contains("user is suspended")) {
                            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(this, "Failed to join game.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    
        Volley.newRequestQueue(this).add(joinRequest);
    }
    
    /*
     * This method handles incoming WebSocket messages. It checks for game state changes and updates the UI accordingly.
     * 
     * @param message The incoming WebSocket message.
     */
    private void handleWebSocketMessage(String message) {
        try {
            if (message.trim().startsWith("{")) {
                JSONObject jsonObject = new JSONObject(message);

                // Handle GameState transitions
                if (jsonObject.has("gameState")) {
                    String newGameState = jsonObject.getString("gameState").trim();
                    if (!newGameState.equals(currentGameState)) {
                        Log.d("LobbyActivity", "GameState changed from " + currentGameState + " to " + newGameState);
                        handleGameStateTransition(newGameState);
                        currentGameState = newGameState;
                    }
                }

                // Handle other WebSocket messages 
            } else {
                if (message.contains("joined the game") || message.contains("left the game")) {
                    Log.d("SpectateLobbyActivity", "Lobby update: " + message);
                }
            }
        } catch (JSONException e) {
            Log.e("LobbyActivity", "Error parsing WebSocket message", e);
        }
    }

    /*
    * This method polls both the game state and the list of users in the lobby from the server every 5 seconds.
    * It updates the UI with the current game state and the usernames with their selected skins.
    *
    * @param lobbyId The ID of the game lobby to poll.
    */
    private void pollGameStateAndLobbyUsers(String lobbyId) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Poll the game state
                String gameStateUrl = "http://coms-3090-013.class.las.iastate.edu:8080/game/state/" + lobbyId;

                StringRequest gameStateRequest = new StringRequest(Request.Method.GET, gameStateUrl,
                        response -> {
                            Log.d("LobbyActivity", "GameState Response: " + response);
                            String newGameState = response.trim(); // Trim any extra whitespace

                            // Handle GameState transitions
                            if (!newGameState.equals(currentGameState)) {
                                Log.d("LobbyActivity", "GameState changed from " + currentGameState + " to " + newGameState);
                                handleGameStateTransition(newGameState);
                                currentGameState = newGameState;
                            }
                        },
                        error -> Log.e("LobbyActivity", "Error fetching GameState: " + error.toString()));

                Volley.newRequestQueue(ClubLobbyActivity.this).add(gameStateRequest);

                // Poll the list of users in the lobby
                String usersUrl = "http://coms-3090-013.class.las.iastate.edu:8080/game/players/" + lobbyId;

                StringRequest usersRequest = new StringRequest(Request.Method.GET, usersUrl,
                response -> {
                    try {
                        JSONArray usersArray = new JSONArray(response);

                        // Clear all spots
                        clearSpots();

                        // Populate spots with users
                        for (int i = 0; i < Math.min(usersArray.length(), 4); i++) {
                            JSONObject userObject = usersArray.getJSONObject(i);
                            String username = userObject.getString("username");
                        
                            // Create a final copy of the loop variable - wouldnt build without this???
                            final int spotIndex = i + 1;
                        
                            // Fetch skin for each user
                            fetchUserSkin(username, skin -> populateSpot(spotIndex, username, skin));
                        }
                    } catch (JSONException e) {
                        Log.e("LobbyActivity", "Error parsing lobby users response", e);
                    }
                },
                        error -> Log.e("LobbyActivity", "Error fetching lobby users: " + error.toString()));

                Volley.newRequestQueue(ClubLobbyActivity.this).add(usersRequest);

                // Poll again after 5 seconds
                handler.postDelayed(this, 5000);
            }
        }, 5000);
    }

    protected void clearSpots() {
        for (int i = 1; i <= 4; i++) {
            int skinId = getResources().getIdentifier("spot_" + i + "_skin", "id", getPackageName());
            int usernameId = getResources().getIdentifier("spot_" + i + "_username", "id", getPackageName());

            ImageView skinView = findViewById(skinId);
            TextView usernameView = findViewById(usernameId);

            skinView.setImageResource(R.drawable.transparent);
            usernameView.setText("Player " + i);
            skinView.setTag("transparent");
        }
    }

    protected void populateSpot(int spot, String username, String skin) {
        int skinId = getResources().getIdentifier("spot_" + spot + "_skin", "id", getPackageName());
        int usernameId = getResources().getIdentifier("spot_" + spot + "_username", "id", getPackageName());

        ImageView skinView = findViewById(skinId);
        TextView usernameView = findViewById(usernameId);

        // Set username
        usernameView.setText(username);

        // Set skin image
        int skinResId = getResources().getIdentifier(skin.toLowerCase(), "drawable", getPackageName());
        skinView.setImageResource(skinResId);
    }


    /*
     * This method handles the transition between different game states.
     * It updates the UI and starts the GameplayActivity if necessary.
     * 
     * @param newGameState The new game state received from the server.
     */
    private void handleGameStateTransition(String newGameState) {
        newGameState = newGameState.replace("\"", "").trim();
        Log.d("LobbyActivity", "Handling GameState: " + newGameState);

        switch (newGameState) {
            case "COUNTDOWN":
            case "RUNNING":
                // if (!currentGameState.equals("COUNTDOWN") && !currentGameState.equals("RUNNING")) {
                //     Log.d("LobbyActivity", "Transitioning to GameplayActivity");

                //     Intent intent = new Intent(LobbyActivity.this, GameplayActivity.class);
                //     Log.d("LobbyActivity", "Current GameState: " + currentGameState);
                //     Log.d("LobbyActivity", "Fetched userID: " + getIntent().getIntExtra("userID", -1));
                //     Log.d("LobbyActivity", "Username: " + username);
                //     intent.putExtra("username", username);
                //     intent.putExtra("userID", getIntent().getIntExtra("userID", -1));
                //     startActivity(intent);
                //     finish();
                // }

                if (!currentGameState.equals("COUNTDOWN") && !currentGameState.equals("RUNNING")) {
                    Log.d("LobbyActivity", "Transitioning to GameplayActivity");
                    fetchWeatherDataAndStartGame();
                }
                break;

            case "LOBBY":
            default:
                runOnUiThread(() -> lobbyStatusTextView.setText("Waiting for players..."));
                break;
        }
    }

    /*
     * This method fetches weather data from the server and starts the GameplayActivity.
     * It passes the weather data and other necessary information to the GameplayActivity.
     */
    private void fetchWeatherDataAndStartGame() {
        String weatherUrl = "http://coms-3090-013.class.las.iastate.edu:8080/getWeather";

        StringRequest weatherRequest = new StringRequest(Request.Method.GET, weatherUrl,
                response -> {
                    Log.d("LobbyActivity", "Weather Data: " + response);

                    // Pass WebSocket connection and weather data to GameplayActivity
                    Intent intent = new Intent(ClubLobbyActivity.this, GameplayActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("userID", getIntent().getIntExtra("userID", -1)); // Pass userID
                    intent.putExtra("weatherData", response);
                    intent.putExtra("lobbyId", lobbyId); // Use the global lobbyId variable
                    startActivity(intent);
                    finish();
                },
                error -> {
                    Log.e("LobbyActivity", "Error fetching weather data", error);
                    Toast.makeText(this, "Failed to fetch weather data", Toast.LENGTH_SHORT).show();
                });

        Volley.newRequestQueue(this).add(weatherRequest);
    }


    /*
     * This method fetches the selected skin for a user from the server.
     *
     * @param username The username of the user.
     * @param callback The callback to handle the fetched skin.
     */
    private void fetchUserSkin(String username, SkinCallback callback) {
        String url = "http://coms-3090-013.class.las.iastate.edu:8080/cosmetics/getSelected/" + username;

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                response -> callback.onSkinFetched(response.trim()),
                error -> Log.e("LobbyActivity", "Error fetching skin for user " + username + ": " + error.toString()));

        Volley.newRequestQueue(this).add(getRequest);
    }

    /*
     * Callback interface for fetching user skins.
     */
    private interface SkinCallback {
        void onSkinFetched(String skin);
    }

    /*
     * This method is called when the activity is resumed. It reconnects to the WebSocket server if necessary.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop polling when the activity is destroyed
        handler.removeCallbacksAndMessages(null);
    }
}
