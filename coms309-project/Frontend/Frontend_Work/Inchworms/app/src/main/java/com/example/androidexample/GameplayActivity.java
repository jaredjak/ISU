package com.example.androidexample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameplayActivity extends AppCompatActivity {

    private ImageView player1; // Reference to the player1 image in XML
    protected FrameLayout gameBoard; // Changed from private to protected
    private FrameLayout.LayoutParams player1LayoutParams; // To dynamically adjust player1's height
    protected TextView timeRemainingTextView; // Changed from private to protected
    protected TextView scoreTextView; // Changed from private to protected
    private boolean isExtending = false;
    private int extensionLength = 0;
    private final int maxExtension = 400;
    protected final CopyOnWriteArrayList<Ball> balls = new CopyOnWriteArrayList<>(); // Changed to CopyOnWriteArrayList
    private final int backendBoardWidth = 1000; // Backend game board width
    private final int backendBoardHeight = 1000; // Backend game board height
    private GameplayWebSocketManager webSocketManager;
    private String username = "did not work"; // Default username
    private String playerIdentifier = null; // Default player identifier
    protected final HashMap<String, String> playerNameMap = new HashMap<>();
    protected final HashMap<String, String> rotatedPlayerMap = new HashMap<>();
    private JSONArray cachedScores = null; // Cache scores if received before players
    private String lobbyId; // Lobby ID for the game
    private String userUsername;
    private String userPlayerIdentifier;
    

    /*
     * This method is called when the activity is first created. It sets up the UI elements, initializes the WebSocket connection,
     * and handles touch events for extending the player's avatar.
     * 
     * @param savedInstanceState The saved instance state bundle.
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);

        // Retrieve the username and lobbyId from the intent
        userUsername = getIntent().getStringExtra("username");
        lobbyId = getIntent().getStringExtra("lobbyId");

        // Reference the player1 image, game board, and other UI elements from the XML
        player1 = findViewById(R.id.player1);
        gameBoard = findViewById(R.id.game_board);
        timeRemainingTextView = findViewById(R.id.time_remaining);
        scoreTextView = findViewById(R.id.scores);
        player1LayoutParams = (FrameLayout.LayoutParams) player1.getLayoutParams();

        // Add the custom GameplayView programmatically to the FrameLayout
        GameplayView gameplayView = new GameplayView(this);
        gameBoard.addView(gameplayView);

        // Reuse the WebSocket Manager from LobbyActivity
        webSocketManager = GameplayWebSocketManager.getInstance();
        webSocketManager.setWebSocketListener(new GameplayWebSocketListener() {
            @Override
            public void onWebSocketOpen(org.java_websocket.handshake.ServerHandshake handshakedata) {
                Log.d("GameplayActivity", "WebSocket connection reused");
            }

            @Override
            public void onWebSocketMessage(String message) {
                handleWebSocketMessage(message, gameplayView, username);
            }

            @Override
            public void onWebSocketClose(int code, String reason, boolean remote) {
                Log.d("GameplayActivity", "WebSocket closed: " + reason);
            }

            @Override
            public void onWebSocketError(Exception ex) {
                Log.e("GameplayActivity", "WebSocket error", ex);
            }
        });

        // Ensure the WebSocket is connected to the correct lobby
        String webSocketUrl = "ws://coms-3090-013.class.las.iastate.edu:8080/gameSocket/" + lobbyId + "/" + username;
        webSocketManager.connectWebSocket(webSocketUrl);

        // Set touch listener for sending user hit mssage to the backend
        gameBoard.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                // Send "hit" command to the backend
                webSocketManager.sendMessage("hit");
            }
            return true;
        });

        // Handle weather data from Intent
        String weatherData = getIntent().getStringExtra("weatherData");
        if (weatherData != null) {
            handleWeatherData(weatherData);
        }

        //assignPlayerSkins();
        fetchAndRotatePlayers();
        
        // Set the scoreboard with default scores
        // Delay the scoreboard initialization by 1 second
        new android.os.Handler().postDelayed(this::updateScoreboardWithDefaultScores, 1000);
    }

    /*
     * Handles incoming WebSocket messages. It processes player assignments, ball updates, scores,
     * time remaining, player hit events, and final results.
     * 
     * @param message The incoming WebSocket message.
     * @param gameplayView The custom view for rendering the game board.
     * @param username The username of the current player.
     * @throws JSONException If there is an error parsing the JSON message.
     */
    protected void handleWebSocketMessage(String message, GameplayView gameplayView, String username) {
        try {
            JSONObject jsonObject = new JSONObject(message);

            // Handle player assignments
            if (jsonObject.has("players")) {
                JSONObject playersObject = jsonObject.getJSONObject("players");
                playerIdentifier = null;

                // Iterate through the players and map player identifiers to usernames
                for (Iterator<String> it = playersObject.keys(); it.hasNext(); ) {
                    String key = it.next(); // e.g., "player1", "player2"
                    String assignedUsername = playersObject.getString(key); // e.g., "AchTest1"

                    // Map the player identifier to the username
                    playerNameMap.put(key, assignedUsername);

                    // Determine the current player's identifier
                    if (assignedUsername.equals(username)) {
                        playerIdentifier = key;
                    }
                }

                if (playerIdentifier != null) {
                    Log.d("GameplayActivity", "Player Identifier: " + playerIdentifier);
                    orientGameBoard(playerIdentifier);
                }
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
                updateScoreboardWithScores(scoresArray);
            }

            // Handle time remaining
            if (jsonObject.has("timeRemainingMillis")) {
                long timeRemaining = jsonObject.getLong("timeRemainingMillis");
                runOnUiThread(() -> timeRemainingTextView.setText("Time Remaining: " + timeRemaining / 1000 + "s"));
            }

            // Handle player hit
            if (jsonObject.has("type") && jsonObject.getString("type").equals("hit")) {
                String hitPlayer = jsonObject.getString("player");

                // Map the hit player to the rotated player map
                String rotatedPlayer = rotatedPlayerMap.get(hitPlayer);
                if (rotatedPlayer != null) {
                    runOnUiThread(() -> extendPlayerAvatar(hitPlayer));
                } else {
                    Log.e("GameplayActivity", "Player not found in rotatedPlayerMap: " + hitPlayer);
                }
            }

            // Handle final results
            if (jsonObject.has("finalResults")) {
                Log.d("GameplayActivity", "Final Results received: " + message);

                // Transition to GameEndActivity
                Intent intent = new Intent(GameplayActivity.this, GameEndActivity.class);
                intent.putExtra("finalResults", message); // Pass the final results JSON
                intent.putExtra("username", username); // Pass the username
                startActivity(intent);
                finish(); // Close GameplayActivity
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*
     * Extends the player's avatar based on the player identifier. The avatar is extended and then contracted back to its original size.
     * 
     * @param playerIdentifier The identifier of the player whose avatar is being extended.
     * @throws JSONException If there is an error parsing the JSON message.
     */
    protected void extendPlayerAvatar(String playerIdentifier) {
        // Map the player identifier to the rotated position
        String rotatedPlayer = rotatedPlayerMap.get(playerIdentifier);
        if (rotatedPlayer == null) {
            Log.e("GameplayActivity", "Player not found in rotatedPlayerMap: " + playerIdentifier);
            return;
        }
    
        // Find the correct player's avatar based on the rotated position
        ImageView playerAvatar;
        switch (rotatedPlayer) {
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
                Log.e("GameplayActivity", "Unknown rotated player identifier: " + rotatedPlayer);
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
                    if (rotatedPlayer.equals("player2") || rotatedPlayer.equals("player4")) {
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
                    if (rotatedPlayer.equals("player2") || rotatedPlayer.equals("player4")) {
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

    /*
     * Updates the height of player1 dynamically to simulate extension and contraction.
     * This method is called from the main thread to ensure UI updates are performed correctly.
     */
//    private void updatePlayer1Height() {
//        // Dynamically adjust the height of player1 to simulate extension/contraction
//        player1LayoutParams.height = 80 + extensionLength; // Base height + extension
//        player1.setLayoutParams(player1LayoutParams);
//    }

    /*
     * Custom view class for rendering the game board and balls.
     * This class extends View and overrides the onDraw method to handle custom drawing.
     */
    private class GameplayView extends View {
        private final Paint paint = new Paint();

        /*
         * Constructor for the GameplayView class. It initializes the view with the given context.
         * 
         * @param context The context in which the view is created.
         * @throws IllegalArgumentException If the context is null.
         */
        public GameplayView(Context context) {
            super(context);
        }

        /*
         * This method is called to draw the view. It draws the game board and the balls on it.
         * 
         * @param canvas The canvas on which to draw the view.
         * @throws IllegalArgumentException If the canvas is null.
         */
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

    /*
     * Class representing a ball on the game board. Each ball has an x and y coordinate.
     * This class is used to store the position of balls on the game board.
     */
    private static class Ball {
        double x, y;

        /*
         * Constructor for the Ball class. It initializes the ball's position with the given x and y coordinates.
         * 
         * @param x The x coordinate of the ball.
         * @param y The y coordinate of the ball.
         */
        Ball(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    /*
     * Orients the game board based on the player's identifier. This method is a placeholder for orienting the game board.
     * 
     * @param playerIdentifier The identifier of the player whose orientation is being set.
     */
    private void orientGameBoard(String playerIdentifier) {
        // Logic to orient the game board based on the player's identifier
        Log.d("GameplayActivity", "Orienting game board for player: " + playerIdentifier);
    }

    /*
     * Updates the scoreboard with default scores for each player. This method is called when the game starts.
     * It initializes the scores to 0 for each player in the playerNameMap.
     */
    protected void updateScoreboardWithDefaultScores() {
        try {
            // Make Default 0 score JSON Array scoresArray
            JSONArray scoresArray = new JSONArray();

            for (int i = 1; i <= 4; i++) {
                JSONObject scoreEntry = new JSONObject();
                scoreEntry.put("playerIdentifier", "player" + i);
                scoreEntry.put("score", 0);
                scoresArray.put(scoreEntry);
            }

            JSONObject scoresObject = new JSONObject();
            scoresObject.put("scores", scoresArray);

            // Continue with logic established in updateScoreboardWithScores
            StringBuilder scoresText = new StringBuilder("Scores:\n");
            for (int i = 0; i < scoresArray.length(); i++) {
                JSONObject scoreObject = scoresArray.getJSONObject(i);
                String playerIdentifier = scoreObject.getString("playerIdentifier");
                int score = scoreObject.getInt("score");

                // Get the username associated with the playerIdentifier
                String playerUsername = playerNameMap.getOrDefault(playerIdentifier, "Unknown");
                scoresText.append(playerUsername).append(": ").append(score).append("\n");
            }
            runOnUiThread(() -> scoreTextView.setText(scoresText.toString()));
        } catch (JSONException e) {
            Log.e("GameplayActivity", "Error updating scoreboard", e);
        }
    }

    /*
     * Updates the scoreboard with the scores received from the backend. This method is called when scores are received.
     * 
     * @param scoresArray The JSON array containing the scores for each player.
     * @throws JSONException If there is an error parsing the JSON array.
     */
    protected void updateScoreboardWithScores(JSONArray scoresArray) {
        try {
            StringBuilder scoresText = new StringBuilder("Scores:\n");
            for (int i = 0; i < scoresArray.length(); i++) {
                JSONObject scoreObject = scoresArray.getJSONObject(i);
                String playerIdentifier = scoreObject.getString("playerIdentifier");
                int score = scoreObject.getInt("score");

                // Get the username associated with the playerIdentifier
                String playerUsername = playerNameMap.getOrDefault(playerIdentifier, "Unknown");
                scoresText.append(playerUsername).append(": ").append(score).append("\n");
            }
            runOnUiThread(() -> scoreTextView.setText(scoresText.toString()));
        } catch (JSONException e) {
            Log.e("GameplayActivity", "Error updating scoreboard", e);
        }
    }

    /*
     * Handles weather data received from the backend. This method updates the game board's background color,
     * handles precipitation effects, and manages cloud cover.
     * 
     * @param weatherData The weather data string received from the backend.
     * @throws NumberFormatException If there is an error parsing the weather data.
     */
    private void handleWeatherData(String weatherData) {
        try {
            // Parse the weather data
            String[] weatherParts = weatherData.split(",");
            int isDaytime = Integer.parseInt(weatherParts[1]);
            double cloudCover = Double.parseDouble(weatherParts[2]);
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

    /*
     * Fetches the player data from the backend and rotates the players so that the current user is at the bottom.
     * This method is called when the game starts to ensure the player order is correct.
     * 
     * @param lobbyId The ID of the lobby to fetch player data from.
     * @throws JSONException If there is an error parsing the JSON response.
     */
    private void fetchAndRotatePlayers() {
        String url = "http://coms-3090-013.class.las.iastate.edu:8080/game/players/" + lobbyId;
    
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray playersArray = new JSONArray(response);
                        HashMap<String, String> tempPlayerNameMap = new HashMap<>();
    
                        // Map playerIdentifier to username
                        for (int i = 0; i < playersArray.length(); i++) {
                            JSONObject playerObject = playersArray.getJSONObject(i);
                            String playerIdentifier = playerObject.getString("playerIdentifier");
                            String username = playerObject.getString("username");
                            playerNameMap.put(playerIdentifier, username);

                            // Identify the current user's playerIdentifier
                            if (username.equals(userUsername)) {
                                userPlayerIdentifier = playerIdentifier;
                            }
                        }

                        Log.d("GameplayActivity", "Player Map: " + playerNameMap);
                        Log.d("GameplayActivity", "User Player Identifier: " + userPlayerIdentifier);

                        // Rotate players so the current user is at the bottom
                        rotatePlayers();
                        Log.d("GameplayActivity", "Rotated Player Map: " + rotatedPlayerMap);
                        
                        // Assign skins to the players
                        assignPlayerSkins();
    
                    } catch (JSONException e) {
                        Log.e("GameplayActivity", "Error parsing player data", e);
                    }
                },
                error -> Log.e("GameplayActivity", "Error fetching players: " + error.toString()));
    
        Volley.newRequestQueue(this).add(getRequest);
    }

    /**
     * Assigns player skins by fetching player data from the lobby and retrieving their selected skins.
     */
    protected void assignPlayerSkins() {
        // Iterate through the playerNameMap to fetch and assign skins for each player
        for (String playerIdentifier : playerNameMap.keySet()) {
            String username = playerNameMap.get(playerIdentifier);
            Log.d("GameplayActivity", "Assigning skin for player: " + playerIdentifier + ", username: " + username);
            // Fetch and assign the player's skin
            fetchAndAssignSkin(playerIdentifier, username);
        }
    }

    /*
     * Rotates the players so that the current user is at the bottom of the screen.
     */
    private void rotatePlayers() {
        String[] rotatedPositions;
    
        switch (userPlayerIdentifier) {
            case "player1":
                rotatedPositions = new String[]{"player1", "player2", "player3", "player4"};
                break;
            case "player2":
                rotatedPositions = new String[]{"player2", "player3", "player4", "player1"};
                break;
            case "player3":
                rotatedPositions = new String[]{"player3", "player4", "player1", "player2"};
                break;
            case "player4":
                rotatedPositions = new String[]{"player4", "player1", "player2", "player3"};
                break;
            default:
                Log.e("GameplayActivity", "Invalid userPlayerIdentifier: " + userPlayerIdentifier);
                return;
        }
    
        Log.d("GameplayActivity", "Rotated Positions: " + java.util.Arrays.toString(rotatedPositions));
    
        // Update the rotatedPlayerMap to reflect the new positions
        rotatedPlayerMap.clear();
        for (int i = 0; i < rotatedPositions.length; i++) {
            //rotatedPlayerMap.put("player" + (i+1), playerNameMap.get(rotatedPositions[i]));
            rotatedPlayerMap.put(rotatedPositions[i], "player" + (i + 1));
        }
    
        Log.d("GameplayActivity", "Rotated Player Map: " + rotatedPlayerMap);
    }

    /**
     * Fetches the selected skin for a user and assigns it to the appropriate player avatar.
     *
     * @param playerIdentifier The identifier of the player (e.g., "player1", "player2").
     * @param username         The username of the player.
     */
    private void fetchAndAssignSkin(String playerIdentifier, String username) {
        if (playerIdentifier == null || username == null) {
            Log.e("GameplayActivity", "Invalid playerIdentifier or username: " + playerIdentifier + ", " + username);
            return;
        }

        String url = "http://coms-3090-013.class.las.iastate.edu:8080/cosmetics/getSelected/" + username;

        // Fetch the selected skin for the user
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    String skin = response.trim().toLowerCase(); // Convert to lowercase
                    int skinResId = getResources().getIdentifier(skin, "drawable", getPackageName());
                    Log.d("GameplayActivity", "Skin resource for " + username + ": " + skin);
                    
                    if (skinResId != 0) {
                        // Assign the skin to the appropriate player avatar
                        ImageView playerAvatar = getPlayerAvatar(playerIdentifier);
    
                        if (playerAvatar != null) {
                            playerAvatar.setImageResource(skinResId);
                            //Log.d("GameplayActivity", "Skin resource for " + username + ": " + skin);
                        } else {
                            Log.e("GameplayActivity", "Player avatar not found for: " + playerIdentifier);
                        }
                    } else {
                        Log.e("GameplayActivity", "Skin resource not found for: " + skin);
                    }
                },
                error -> Log.e("GameplayActivity", "Error fetching skin for user " + username + ": " + error.toString()));
    
        Volley.newRequestQueue(this).add(getRequest);
    }

    /**
     * Returns the ImageView for a player based on the player number.
     *
     & @param playerIdentifier The identifier of the player (e.g., "player1", "player2").
     * @return The ImageView for the player's avatar.
     */
    private ImageView getPlayerAvatar(String playerIdentifier) {
        switch (playerIdentifier) {
            case "player1":
                return findViewById(R.id.player1);
            case "player2":
                return findViewById(R.id.player2);
            case "player3":
                return findViewById(R.id.player3);
            case "player4":
                return findViewById(R.id.player4);
            default:
                Log.e("GameplayActivity", "Unknown player identifier: " + playerIdentifier);
                return null;
        }
    }

    /**
     * Callback interface for fetching user skins.
     */
//    private interface SkinCallback {
//        void onSkinFetched(String skin);
//    }
}
