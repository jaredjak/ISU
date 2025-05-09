package com.example.androidexample;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class SpectateGameplayActivity extends AppCompatActivity {

    private String spectatorUsername;
    private GameplayWebSocketManager webSocketManager;
    private TextView timeRemainingTextView;
    private TextView scoreTextView;
    private FrameLayout gameBoard;
    private final CopyOnWriteArrayList<Ball> balls = new CopyOnWriteArrayList<>();
    private final int backendBoardWidth = 1000; // Backend game board width
    private final int backendBoardHeight = 1000; // Backend game board height
    private long timeRemainingMillis = 0;
    private TextView betDetailsTextView; // TextView to display bet details

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spectate_gameplay);

        // Get the spectator's username from the Intent
        spectatorUsername = getIntent().getStringExtra("username");

        // Reference UI elements
        timeRemainingTextView = findViewById(R.id.time_remaining);
        scoreTextView = findViewById(R.id.scores);
        gameBoard = findViewById(R.id.game_board);

        // Initialize the bet details TextView
        betDetailsTextView = findViewById(R.id.bet_details_text_view);

        // Add the custom GameplayView programmatically to the FrameLayout
        GameplayView gameplayView = new GameplayView(this);
        gameBoard.addView(gameplayView);

        // Add "Place Bet" button functionality
        Button placeBetButton = findViewById(R.id.place_bet_button);
        placeBetButton.setOnClickListener(v -> openBetDialog());

        // Reuse the WebSocket Manager from the lobby
        webSocketManager = GameplayWebSocketManager.getInstance();
        webSocketManager.setWebSocketListener(new GameplayWebSocketListener() {
            @Override
            public void onWebSocketOpen(org.java_websocket.handshake.ServerHandshake handshakedata) {
                Log.d("SpectateGameplayActivity", "Reusing WebSocket connection");
            }

            @Override
            public void onWebSocketMessage(String message) {
                handleWebSocketMessage(message, gameplayView);
            }

            @Override
            public void onWebSocketClose(int code, String reason, boolean remote) {
                Log.d("SpectateGameplayActivity", "WebSocket closed: " + reason);
            }

            @Override
            public void onWebSocketError(Exception ex) {
                Log.e("SpectateGameplayActivity", "WebSocket error: " + ex.getMessage());
            }
        });

        // Handle weather data from Intent
        String weatherData = getIntent().getStringExtra("weatherData");
        if (weatherData != null) {
            handleWeatherData(weatherData);
        }
    }

    private void openBetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Place Bet");

        // Create a custom layout for the dialog
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_place_bet, null);
        builder.setView(customLayout);

        builder.setPositiveButton("Place Bet", (dialog, which) -> {
            // Get the input values
            EditText betAmountInput = customLayout.findViewById(R.id.bet_amount_input);
            EditText playerPositionInput = customLayout.findViewById(R.id.player_position_input);

            try {
                int betAmount = Integer.parseInt(betAmountInput.getText().toString());
                String playerPosition = playerPositionInput.getText().toString();

                // Send the validated bet
                if (betAmount > 0) {
                    sendBet(betAmount, playerPosition);
                } else {
                    // Don't know what this does, but vsCode suggested it
                    Toast.makeText(this, "Bet amount must be greater than 0", Toast.LENGTH_SHORT).show();
                }   
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid bet amount", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        // Show the dialog
        builder.create().show();
    }

    private void sendBet(int betAmount, String playerPosition) {
        String url = "http://coms-3090-013.class.las.iastate.edu:8080/placeBet";

        JSONObject betDetails = new JSONObject();
        try {
            betDetails.put("username", spectatorUsername); // Spectator's username
            betDetails.put("amount", betAmount); // Bet amount
            betDetails.put("mult", timeRemainingMillis / 1000.00); // Convert time remaining long to seconds long
            betDetails.put("positionBet", playerPosition); // Player position (e.g., "player1")
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error creating bet request", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, betDetails,
                response -> {
                    try {
                        // Parse the response
                        int id = response.getInt("id");
                        double amount = response.getDouble("amount");
                        String positionBet = response.getString("positionBet");
                        double mult = response.getDouble("mult");
                        String username = response.getString("username");
                        int betStreak = response.getInt("betStreak");

                        // Display success message
                        String successMessage = String.format(
                                "Bet placed successfully!\nID: %d\nAmount: %.2f\nPosition: %s\nMultiplier: %.2f\nBet Streak: %d",
                                id, amount, positionBet, mult, betStreak
                        );

                        // Update the bet details TextView
                        String betDetailsText = String.format(
                                "Bet Placed: $%.2f on %s ",
                                amount, positionBet
                        );
                        runOnUiThread(() -> betDetailsTextView.setText(betDetailsText));
                        Toast.makeText(this, successMessage, Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error parsing response", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e("BetResponse", "POST Error: " + error.toString());
                    if (error.networkResponse != null && error.networkResponse.statusCode == 500) {
                        Toast.makeText(this, "Invalid player position or server error. Please try again.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to place bet. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(this).add(postRequest);
    }

    private void handleWebSocketMessage(String message, GameplayView gameplayView) {
        try {
            JSONObject jsonObject = new JSONObject(message);

            // Handle player assignments
            if (jsonObject.has("players")) {
                JSONObject playersObject = jsonObject.getJSONObject("players");
                StringBuilder scoresText = new StringBuilder("Scores:\n");
                for (Iterator<String> it = playersObject.keys(); it.hasNext(); ) {
                    String key = it.next();
                    String username = playersObject.getString(key);
                    scoresText.append(username).append(": 0\n"); // Default score is 0 initially
                }
                runOnUiThread(() -> scoreTextView.setText(scoresText.toString()));
            }


            // Handle ball updates
            if (jsonObject.has("balls")) {
                JSONArray ballsArray = jsonObject.getJSONArray("balls");
                balls.clear();
                for (int i = 0; i < ballsArray.length(); i++) {
                    JSONObject ballObject = ballsArray.getJSONObject(i);
                    double x = ballObject.getDouble("x");
                    double y = ballObject.getDouble("y");
                    balls.add(new Ball(x, y));
                }
                runOnUiThread(gameplayView::invalidate); // Redraw the game board
            }

            // Handle scores
            if (jsonObject.has("scores")) {
                JSONArray scoresArray = jsonObject.getJSONArray("scores");
                StringBuilder scoresText = new StringBuilder("Scores:\n");
                for (int i = 0; i < scoresArray.length(); i++) {
                    JSONObject scoreObject = scoresArray.getJSONObject(i);
                    String username = scoreObject.getString("playerIdentifier");
                    int score = scoreObject.getInt("score");
                    scoresText.append(username).append(": ").append(score).append("\n");
                }
                runOnUiThread(() -> scoreTextView.setText(scoresText.toString()));
            }

            // Handle time remaining
            if (jsonObject.has("timeRemainingMillis")) {
                long timeRemainingMillis = jsonObject.getLong("timeRemainingMillis");
                runOnUiThread(() -> timeRemainingTextView.setText("Time Left: " + timeRemainingMillis / 1000 + "s"));
            }

            // Handle player hit
            if (jsonObject.has("event") && jsonObject.getString("event").equals("playerHit")) {
                String playerIdentifier = jsonObject.getString("playerIdentifier");
                runOnUiThread(() -> extendPlayerAvatar(playerIdentifier));
            }

            // Handle final results
            if (jsonObject.has("finalResults")) {
                Log.d("SpectateGameplayActivity", "Final Results received: " + message);

                // Parse the final results to determine the winner
                JSONArray finalResultsArray = jsonObject.getJSONArray("finalResults");
                String winningPlayer = null;
                for (int i = 0; i < finalResultsArray.length(); i++) {
                    JSONObject playerResult = finalResultsArray.getJSONObject(i);
                    if (playerResult.getBoolean("isWinner")) {
                        winningPlayer = playerResult.getString("username");
                        break;
                    }
                }

                // bet cashout is handled by backend - this was for testing
                // if (winningPlayer != null) {
                //     // Trigger bet cashout for the winning player
                //     cashoutBets(winningPlayer);
                // }

                // Transition to GameEndActivity
                Intent intent = new Intent(SpectateGameplayActivity.this, GameEndActivity.class);
                intent.putExtra("finalResults", message); // Pass the final results JSON
                intent.putExtra("username", spectatorUsername); // Pass the spectator's username
                startActivity(intent);
                finish(); // Close SpectateGameplayActivity
            }

        } catch (JSONException e) {
            Log.e("SpectateGameplayActivity", "Error parsing WebSocket message", e);
        }
    }

    private void extendPlayerAvatar(String playerIdentifier) {
        // Find the corresponding player's avatar
        ImageView playerAvatar;
        switch (playerIdentifier) {
            case "player1": // Bottom player (extends vertically)
                playerAvatar = findViewById(R.id.player1);
                break;
            case "player2": // Left player (extends horizontally)
                playerAvatar = findViewById(R.id.player2);
                break;
            case "player3": // Top player (extends vertically)
                playerAvatar = findViewById(R.id.player3);
                break;
            case "player4": // Right player (extends horizontally)
                playerAvatar = findViewById(R.id.player4);
                break;
            default:
                Log.e("SpectateGameplayActivity", "Unknown player identifier: " + playerIdentifier);
                return;
        }

        // Get the player's layout parameters
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) playerAvatar.getLayoutParams();

        // Extend the player's avatar
        new Thread(() -> {
            int extensionLength = 0;
            int maxExtension = 400;

            // Extend the avatar
            while (extensionLength < maxExtension) {
                extensionLength += 20;
                int finalExtensionLength = extensionLength;
                runOnUiThread(() -> {
                    if (playerIdentifier.equals("player2") || playerIdentifier.equals("player4")) {
                        // Extend horizontally for players at 3 and 9 o'clock
                        layoutParams.width = 80 + finalExtensionLength; // Base width + extension
                    } else {
                        // Extend vertically for players at 12 and 6 o'clock
                        layoutParams.height = 80 + finalExtensionLength; // Base height + extension
                    }
                    playerAvatar.setLayoutParams(layoutParams);
                });
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Pause at the maximum extension
            try {
                Thread.sleep(400); // Pause for 400ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Contract the avatar
            while (extensionLength > 0) {
                extensionLength -= 20;
                int finalExtensionLength = extensionLength;
                runOnUiThread(() -> {
                    if (playerIdentifier.equals("player2") || playerIdentifier.equals("player4")) {
                        // Contract horizontally for players at 3 and 9 o'clock
                        layoutParams.width = 80 + finalExtensionLength; // Base width + extension
                    } else {
                        // Contract vertically for players at 12 and 6 o'clock
                        layoutParams.height = 80 + finalExtensionLength; // Base height + extension
                    }
                    playerAvatar.setLayoutParams(layoutParams);
                });
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void cashoutBets(String winningPlayer) {
        String url = "http://coms-3090-013.class.las.iastate.edu:8080/cashoutBets/" + winningPlayer;
    
        JsonObjectRequest deleteRequest = new JsonObjectRequest(Request.Method.DELETE, url, null,
                response -> Log.d("CashoutBets", "Bets cashed out successfully for: " + winningPlayer),
                error -> Log.e("CashoutBets", "Error cashing out bets: " + error.toString()));
    
        Volley.newRequestQueue(this).add(deleteRequest);
    }

    private void handleWeatherData(String weatherData) {
        try {
            // Parse the weather data
            String[] weatherParts = weatherData.split(",");
            int isDaytime = Integer.parseInt(weatherParts[1]);
            int cloudCover = Integer.parseInt(weatherParts[2]);
            double rainfall = Double.parseDouble(weatherParts[3]);
            double snowfall = Double.parseDouble(weatherParts[4]);
            double temp = Double.parseDouble(weatherParts[5]);

            // Update game board background color
            if (isDaytime == 0) {
                gameBoard.setBackgroundColor(Color.parseColor("#006400")); // Dark green for night
            } else {
                gameBoard.setBackgroundColor(Color.parseColor("#388004")); // Bright green for day
            }

            // Handle percipitation
            ImageView weatherImageView = findViewById(R.id.weather_image);
            if (snowfall > 0) {
                Glide.with(this)
                    .asGif()
                    .load(R.drawable.snow) // Snow gif
                    .into(weatherImageView);
            } else if (rainfall > 0) {
                Glide.with(this)
                    .asGif()
                    .load(R.drawable.rain) // Rain gif
                    .into(weatherImageView);
            } else {
                weatherImageView.setImageDrawable(null); // no percipitation
            }

            // Handle cloudy weather
            FrameLayout cloudContainer = findViewById(R.id.cloud_container);
            cloudContainer.removeAllViews(); // Clear any existing clouds
            if (cloudCover > 35) {
                cloudContainer.setVisibility(View.VISIBLE);

                cloudContainer.post(() -> {
                    //int numberOfClouds = Math.min(cloudCover / 10, 10); // Scale number of clouds
                    int numberOfClouds = 500; // Fixed number of clouds
                    int cloudWidth = 300;
                    int cloudHeight = 200;
                    int containerWidth = cloudContainer.getWidth();
                    int containerHeight = cloudContainer.getHeight();

                    for (int i = 0; i < numberOfClouds; i++) {
                        ImageView cloud = new ImageView(this);
                        cloud.setImageResource(R.drawable.cloudy);

                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(cloudWidth, cloudHeight);

                        // Allow clouds to overflow the sides
                        int horizontalRange = containerWidth + cloudWidth; // add extra space to allow overflow
                        params.leftMargin = (int) (Math.random() * horizontalRange) - cloudWidth / 2;

                        // Keep clouds fully within vertical bounds
                        int maxTopMargin = containerHeight - cloudHeight - 10;
                        params.topMargin = (int) (Math.random() * Math.max(1, maxTopMargin));

                        cloud.setLayoutParams(params);
                        cloudContainer.addView(cloud);
                    }
                });
            } else {
                cloudContainer.setVisibility(View.GONE); // Hide cloud container
            }
        } catch (Exception e) {
            Log.e("GameplayActivity", "Error handling weather data", e);
        }
    }

    private class GameplayView extends View {
        private final Paint paint = new Paint();

        public GameplayView(SpectateGameplayActivity context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            // Draw background
            canvas.drawColor(Color.TRANSPARENT); // Keep the FrameLayout's background

            // Scale factor to map backend board size to frontend board size
            float scaleX = (float) gameBoard.getWidth() / backendBoardWidth;
            float scaleY = (float) gameBoard.getHeight() / backendBoardHeight;

            // Draw balls
            paint.setColor(Color.RED);
            for (Ball ball : balls) {
                float scaledX = (float) ball.x * scaleX;
                float scaledY = (float) ball.y * scaleY;
                canvas.drawCircle(scaledX, scaledY, 10, paint); // Draw scaled ball
            }
        }
    }

    private static class Ball {
        double x, y;

        Ball(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}
