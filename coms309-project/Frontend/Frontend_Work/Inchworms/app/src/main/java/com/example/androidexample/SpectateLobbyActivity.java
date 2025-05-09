package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class SpectateLobbyActivity extends AppCompatActivity {

    private TextView lobbyStatusTextView;
    private Handler handler = new Handler();
    private String currentGameState = "LOBBY"; // Default state
    private String username; // Username of the spectator
    private GameplayWebSocketManager webSocketManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spectate_lobby);

        // Get the spectator's username from the Intent
        username = getIntent().getStringExtra("username");

        // Reference the TextView for displaying the lobby status
        lobbyStatusTextView = findViewById(R.id.lobby_status);

        // Add "Place Bet" button
        // Button placeBetButton = findViewById(R.id.place_bet_button);
        // placeBetButton.setOnClickListener(v -> openBetDialog());

        // Join the WebSocket
        joinWebSocket();

        // Start polling the GameState every 5 seconds
        pollGameState();
    }

    private void joinWebSocket() {
        // Will replace with spectate websocket later
        String webSocketUrl = "ws://coms-3090-013.class.las.iastate.edu:8080/spectatorSocket";

        // Initialize WebSocket Manager
        webSocketManager = GameplayWebSocketManager.getInstance();
        webSocketManager.setWebSocketListener(new GameplayWebSocketListener() {
            @Override
            public void onWebSocketOpen(org.java_websocket.handshake.ServerHandshake handshakedata) {
                Log.d("SpectateLobbyActivity", "Connected to WebSocket server");
            }

            @Override
            public void onWebSocketMessage(String message) {
                handleWebSocketMessage(message);
            }

            @Override
            public void onWebSocketClose(int code, String reason, boolean remote) {
                Log.d("SpectateLobbyActivity", "WebSocket closed: " + reason);
            }

            @Override
            public void onWebSocketError(Exception ex) {
                Log.e("SpectateLobbyActivity", "WebSocket error: " + ex.getMessage());
            }
        });

        // Connect to the WebSocket server
        webSocketManager.connectWebSocket(webSocketUrl);
    }

    private void handleWebSocketMessage(String message) {
        try {
            // Handle lobby-related messages only
            if (message.contains("joined the game") || message.contains("left the game")) {
                Log.d("SpectateLobbyActivity", "Lobby update: " + message);
            }
        } catch (Exception e) {
            Log.e("SpectateLobbyActivity", "Error parsing WebSocket message", e);
        }
    }

    private void pollGameState() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String url = "http://coms-3090-013.class.las.iastate.edu:8080/game/state";

                StringRequest getRequest = new StringRequest(Request.Method.GET, url,
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

                Volley.newRequestQueue(SpectateLobbyActivity.this).add(getRequest);

                // Poll again after 5 seconds
                handler.postDelayed(this, 5000);
            }
        }, 5000);
    }

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

    private void fetchWeatherDataAndStartGame() {
        String weatherUrl = "http://coms-3090-013.class.las.iastate.edu:8080/getWeather";
    
        StringRequest weatherRequest = new StringRequest(Request.Method.GET, weatherUrl,
                response -> {
                    Log.d("LobbyActivity", "Weather Data: " + response);
    
                    // Start GameplayActivity and pass weather data
                    Intent intent = new Intent(SpectateLobbyActivity.this, SpectateGameplayActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("userID", getIntent().getIntExtra("userID", -1)); // Pass userID
                    intent.putExtra("weatherData", response);
                    startActivity(intent);
                    finish();
                },
                error -> {
                    Log.e("LobbyActivity", "Error fetching weather data", error);
                    Toast.makeText(this, "Failed to fetch weather data", Toast.LENGTH_SHORT).show();
                });
    
        Volley.newRequestQueue(this).add(weatherRequest);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop polling when the activity is destroyed
        handler.removeCallbacksAndMessages(null);
    }
}
