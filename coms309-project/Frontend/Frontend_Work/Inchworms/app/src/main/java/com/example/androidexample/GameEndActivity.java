package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GameEndActivity extends AppCompatActivity {

    private static final int DELAY_MILLIS = 30000; // 30 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end);

        TextView resultsTextView = findViewById(R.id.results_text_view);
        TextView congratsTextView = findViewById(R.id.congrats_text_view);

        // Get the final results JSON string and username from the intent
        String finalResultsJson = getIntent().getStringExtra("finalResults");
        String username = getIntent().getStringExtra("username");

        try {
            JSONObject jsonObject = new JSONObject(finalResultsJson);
            JSONArray finalResultsArray = jsonObject.getJSONArray("finalResults");

            List<PlayerResult> playerResults = new ArrayList<>();
            for (int i = 0; i < finalResultsArray.length(); i++) {
                JSONObject playerObject = finalResultsArray.getJSONObject(i);
                String playerUsername = playerObject.getString("username");
                int score = playerObject.getInt("score");
                boolean isWinner = playerObject.getBoolean("isWinner");
                playerResults.add(new PlayerResult(playerUsername, score, isWinner));
            }

            Collections.sort(playerResults, Comparator.comparingInt(PlayerResult::getScore).reversed());

            StringBuilder resultsBuilder = new StringBuilder("Final Results:\n\n");
            for (int i = 0; i < playerResults.size(); i++) {
                PlayerResult player = playerResults.get(i);
                resultsBuilder.append((i + 1)).append(". ")
                        .append(player.getUsername())
                        .append(" - ")
                        .append(player.getScore())
                        .append(" points\n");
            }

            resultsTextView.setText(resultsBuilder.toString());

            for (PlayerResult player : playerResults) {
                if (player.getUsername().equals(username) && player.isWinner()) {
                    congratsTextView.setText("Congratulations, You Won!");
                    congratsTextView.setVisibility(TextView.VISIBLE);
                    break;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error parsing final results", Toast.LENGTH_SHORT).show();
        }

        // Schedule a transition back to the home screen after 30 seconds
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(GameEndActivity.this, HomeActivity.class);
            startActivity(intent);
            finish(); // Close GameEndActivity
        }, DELAY_MILLIS);
    }

    private static class PlayerResult {
        private final String username;
        private final int score;
        private final boolean isWinner;

        public PlayerResult(String username, int score, boolean isWinner) {
            this.username = username;
            this.score = score;
            this.isWinner = isWinner;
        }

        public String getUsername() {
            return username;
        }

        public int getScore() {
            return score;
        }

        public boolean isWinner() {
            return isWinner;
        }
    }
}
