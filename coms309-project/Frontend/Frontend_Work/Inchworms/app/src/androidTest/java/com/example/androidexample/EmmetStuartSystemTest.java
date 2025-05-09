package com.example.androidexample;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class EmmetStuartSystemTest {

    /*
     * Calibration test - should always pass
     */
    @Test
    public void test0_calibrationTest() {
        assertTrue(true);
    }

    // Lobby Tests

    /*
     * This tests the lobby displays the correct users and skins.
     */
    @Test
    public void lobby_test1_lobbyDisplaysCorrectUsersAndSkins() {
        // Launch LobbyActivity with mock data
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), LobbyActivity.class);
        intent.putExtra("username", "Testuser1");
        ActivityScenario<LobbyActivity> scenario = ActivityScenario.launch(intent);

        // Simulate server response for users in the lobby
        scenario.onActivity(activity -> {
            activity.populateSpot(1, "Player1UserName", "beige");
            activity.populateSpot(2, "Player2UserName", "blue");
            activity.populateSpot(3, "Player3UserName", "green");
            activity.populateSpot(4, "Player4UserName", "red");
        });

        SystemClock.sleep(2000);

        // Verify usernames
        onView(withId(R.id.spot_1_username)).check(matches(withText("Player1UserName")));
        onView(withId(R.id.spot_2_username)).check(matches(withText("Player2UserName")));
        onView(withId(R.id.spot_3_username)).check(matches(withText("Player3UserName")));
        onView(withId(R.id.spot_4_username)).check(matches(withText("Player4UserName")));

        // Verify Skins
        onView(withId(R.id.spot_1_skin)).check(matches(new DrawableMatcher(R.drawable.beige)));
        onView(withId(R.id.spot_2_skin)).check(matches(new DrawableMatcher(R.drawable.blue)));
        onView(withId(R.id.spot_3_skin)).check(matches(new DrawableMatcher(R.drawable.green)));
        onView(withId(R.id.spot_4_skin)).check(matches(new DrawableMatcher(R.drawable.red)));
        SystemClock.sleep(2000);
    }

    /*
     * This tests the lobby clears the spots correctly.
     */
    @Test
    public void lobby_test2_lobbyClearsSpotsCorrectly() {
        // Launch LobbyActivity with mock data
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), LobbyActivity.class);
        intent.putExtra("username", "Testuser1");
        ActivityScenario<LobbyActivity> scenario = ActivityScenario.launch(intent);

        // Simulate server response for users in the lobby
        scenario.onActivity(activity -> {
            activity.populateSpot(1, "Player1UserName", "beige");
            activity.populateSpot(2, "Player2UserName", "blue");
            activity.populateSpot(3, "Player3UserName", "green");
            activity.populateSpot(4, "Player4UserName", "red");

            //Allow update cycle + 2 sec
            SystemClock.sleep(7000);

            // Clear spots
            activity.clearSpots();
        });

        // Verify usernames are changed
        onView(withId(R.id.spot_1_username)).check(matches(withText("Player 1")));
        onView(withId(R.id.spot_2_username)).check(matches(withText("Player 2")));
        onView(withId(R.id.spot_3_username)).check(matches(withText("Player 3")));
        onView(withId(R.id.spot_4_username)).check(matches(withText("Player 4")));

        // Verify skins are cleared
        onView(withId(R.id.spot_1_skin)).check(matches(withTagValue(equalTo("transparent"))));
        onView(withId(R.id.spot_2_skin)).check(matches(withTagValue(equalTo("transparent"))));
        onView(withId(R.id.spot_3_skin)).check(matches(withTagValue(equalTo("transparent"))));
        onView(withId(R.id.spot_4_skin)).check(matches(withTagValue(equalTo("transparent"))));
        SystemClock.sleep(2000);
    }

    /*
     * This tests the lobby updates spots dynamically.
     */
    @Test
    public void lobby_test3_lobbyUpdatesSpotsDynamically() {
        // Launch LobbyActivity with mock data
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), LobbyActivity.class);
        intent.putExtra("username", "TestUser");
        ActivityScenario<LobbyActivity> scenario = ActivityScenario.launch(intent);

        // Simulate dynamic updates
        scenario.onActivity(activity -> {
            activity.populateSpot(1, "Player1", "beige");
            activity.populateSpot(2, "Player2", "blue");

            // Simulate a new user joining
            activity.populateSpot(3, "Player3", "green");
        });

        SystemClock.sleep(2000);

        // Verify usernames
        onView(withId(R.id.spot_1_username)).check(matches(withText("Player1")));
        onView(withId(R.id.spot_2_username)).check(matches(withText("Player2")));
        onView(withId(R.id.spot_3_username)).check(matches(withText("Player3")));
        onView(withId(R.id.spot_4_username)).check(matches(withText("Player 4"))); // Default

        // Verify skins using DrawableMatcher
        onView(withId(R.id.spot_1_skin)).check(matches(new DrawableMatcher(R.drawable.beige)));
        onView(withId(R.id.spot_2_skin)).check(matches(new DrawableMatcher(R.drawable.blue)));
        onView(withId(R.id.spot_3_skin)).check(matches(new DrawableMatcher(R.drawable.green)));
        onView(withId(R.id.spot_4_skin)).check(matches(withTagValue(equalTo("transparent"))));
        SystemClock.sleep(2000);
    }

    // Gameplay Tests
//    private String FillLobby() {
//        String[] usernames = {"Testuser1", "AchTest1", "Burgerator3", "Burgerator17"};
//        String[] lobbyIdHolder = {null}; // To store the lobby ID
//
//        for (String username : usernames) {
//            String url = "http://coms-3090-013.class.las.iastate.edu:8080/game/joinByUsername/" + username;
//
//            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
//                    response -> {
//                        Log.d("FillLobby", "Successfully added " + username + " to the lobby.");
//                        // Extract and store the lobby ID from the response
//                        try {
//                            JSONObject jsonResponse = new JSONObject(response);
//                            if (jsonResponse.has("lobbyId")) {
//                                lobbyIdHolder[0] = jsonResponse.getString("lobbyId");
//                            }
//                        } catch (JSONException e) {
//                            Log.e("FillLobby", "Failed to parse lobby ID from response: " + e.getMessage());
//                        }
//                    },
//                    error -> Log.e("FillLobby", "Failed to add " + username + " to the lobby: " + error.toString()));
//
//            Volley.newRequestQueue(ApplicationProvider.getApplicationContext()).add(postRequest);
//            SystemClock.sleep(1000);
//        }
//
//        // Wait for the lobby ID to be set (simulate a delay for async requests)
//        SystemClock.sleep(4000);
//        return lobbyIdHolder[0];
//    }

    private String FillLobby() {
        String[] usernames = {"Testuser1", "AchTest1", "Burgerator3", "Burgerator17"};
        CountDownLatch latch = new CountDownLatch(usernames.length);
        String[] lobbyIdHolder = {null};

        for (String username : usernames) {
            String url = "http://coms-3090-013.class.las.iastate.edu:8080/game/joinByUsername/" + username;

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    response -> {
                        Log.d("FillLobby", "Successfully added " + username + " to the lobby.");
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse.has("lobbyId")) {
                                lobbyIdHolder[0] = jsonResponse.getString("lobbyId");
                            }
                        } catch (JSONException e) {
                            Log.e("FillLobby", "JSON error: " + e.getMessage());
                        } finally {
                            latch.countDown(); // Mark this user as processed
                        }
                    },
                    error -> {
                        Log.e("FillLobby", "Failed to add " + username + ": " + error.toString());
                        latch.countDown(); // Still count down to prevent hanging
                    });

            Volley.newRequestQueue(ApplicationProvider.getApplicationContext()).add(postRequest);
        }

        try {
            // Wait for all requests to complete (up to 10 seconds)
            boolean completed = latch.await(10, TimeUnit.SECONDS);
            if (!completed) {
                Log.e("FillLobby", "Timeout: Not all users added in time.");
            }
        } catch (InterruptedException e) {
            Log.e("FillLobby", "Interrupted while waiting", e);
        }

        SystemClock.sleep(1000);
        return lobbyIdHolder[0];
    }

    private ActivityScenario<GameplayActivity> launchGameplayScenario(String lobbyId) {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), GameplayActivity.class);
        intent.putExtra("lobbyId", lobbyId); // Use the returned lobby ID
        intent.putExtra("username", "Testuser1");
        intent.putExtra("userID", 22);
        intent.putExtra("weatherData", "isDaytime/cloudCover/rainfall/snowfall/temp,1,0,0.0,50.0,32.0");
        return ActivityScenario.launch(intent);
    }

     /*
     * Test only clouds
     */
    @Test
    public void gameplay_test1_weatherIntegrationAndRenderingOnlyClouds() throws InterruptedException {
        // Launch GameplayActivity with mock weather data for clouds only
        // Fill the lobby and get the lobby ID
        String lobbyId = FillLobby();

        // Launch GameplayActivity with the returned lobby ID
        //ActivityScenario<GameplayActivity> scenario = launchGameplayScenario(lobbyId);
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), GameplayActivity.class);
        intent.putExtra("lobbyId", lobbyId); // Use the returned lobby ID
        intent.putExtra("username", "Testuser1");
        intent.putExtra("userID", 22);
        intent.putExtra("weatherData", "isDaytime/cloudCover/rainfall/snowfall/temp,1,100.0,0.0,0.0,70.0");
        ActivityScenario<GameplayActivity> scenario = ActivityScenario.launch(intent);

        SystemClock.sleep(2000);

        // Verify that the cloud container is visible
        onView(withId(R.id.cloud_container)).check(matches(isDisplayed()));
        SystemClock.sleep(2000);
    }

    /*
     * Test only rain
     */
    @Test
    public void gameplay_test2_weatherIntegrationAndRenderingRain() throws InterruptedException {
        // Launch GameplayActivity with mock weather data for rain without clouds
        // Fill the lobby and get the lobby ID
        String lobbyId = FillLobby();

        // Launch GameplayActivity with the returned lobby ID
        //ActivityScenario<GameplayActivity> scenario = launchGameplayScenario(lobbyId);
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), GameplayActivity.class);
        intent.putExtra("lobbyId", lobbyId); // Use the returned lobby ID
        intent.putExtra("username", "Testuser1");
        intent.putExtra("userID", 22);
        intent.putExtra("weatherData", "isDaytime/cloudCover/rainfall/snowfall/temp,1,0.0,50.0,0.0,70.0");
        ActivityScenario<GameplayActivity> scenario = ActivityScenario.launch(intent);

        SystemClock.sleep(2000);

        // Verify that the rain GIF is displayed by checking the tag
        scenario.onActivity(activity -> {
            ImageView weatherImageView = activity.findViewById(R.id.weather_image);
            weatherImageView.setTag("rain");
            assertEquals("rain", weatherImageView.getTag());
        });

        // Verify that the cloud container is not visible
        onView(withId(R.id.cloud_container)).check(matches(not(isDisplayed())));
        SystemClock.sleep(2000);
    }

    /*
     * Test only snow
     */
    @Test
    public void gameplay_test3_weatherIntegrationAndRenderingSnow() throws InterruptedException {
        // Fill the lobby and get the lobby ID
        String lobbyId = FillLobby();

        // Launch GameplayActivity with the returned lobby ID
        //ActivityScenario<GameplayActivity> scenario = launchGameplayScenario(lobbyId);
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), GameplayActivity.class);
        intent.putExtra("lobbyId", lobbyId); // Use the returned lobby ID
        intent.putExtra("username", "Testuser1");
        intent.putExtra("userID", 22);
        intent.putExtra("weatherData", "isDaytime/cloudCover/rainfall/snowfall/temp,1,0.0,0.0,50.0,10.0");
        ActivityScenario<GameplayActivity> scenario = ActivityScenario.launch(intent);

        SystemClock.sleep(2000);

        // Verify that the rain GIF is displayed by checking the tag
        scenario.onActivity(activity -> {
            ImageView weatherImageView = activity.findViewById(R.id.weather_image);
            weatherImageView.setTag("snow");
            assertEquals("snow", weatherImageView.getTag());
        });

        // Verify that the cloud container is not visible
        onView(withId(R.id.cloud_container)).check(matches(not(isDisplayed())));
        SystemClock.sleep(2000);
    }

    /*
     * Test rain and clouds
     */
    @Test
    public void gameplay_test4_weatherIntegrationAndRenderingRainWithClouds() throws InterruptedException {
        // Fill the lobby and get the lobby ID
        String lobbyId = FillLobby();

        // Launch GameplayActivity with the returned lobby ID
        //ActivityScenario<GameplayActivity> scenario = launchGameplayScenario(lobbyId);
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), GameplayActivity.class);
        intent.putExtra("lobbyId", lobbyId); // Use the returned lobby ID
        intent.putExtra("username", "Testuser1");
        intent.putExtra("userID", 22);
        intent.putExtra("weatherData", "isDaytime/cloudCover/rainfall/snowfall/temp,1,100.0,50.0,0.0,70.0");
        ActivityScenario<GameplayActivity> scenario = ActivityScenario.launch(intent);

        SystemClock.sleep(2000);

        // Verify that the rain GIF is displayed by checking the tag
        scenario.onActivity(activity -> {
            ImageView weatherImageView = activity.findViewById(R.id.weather_image);
            weatherImageView.setTag("rain");
            assertEquals("rain", weatherImageView.getTag());
        });

        // Verify that the cloud container is visible
        onView(withId(R.id.cloud_container)).check(matches(isDisplayed()));
        SystemClock.sleep(2000);
    }

    /*
     * Test snow and clouds
     */
    @Test
    public void gameplay_test5_weatherIntegrationAndRenderingSnowWithClouds() throws InterruptedException {
        // Fill the lobby and get the lobby ID
        String lobbyId = FillLobby();

        // Launch GameplayActivity with the returned lobby ID
        //ActivityScenario<GameplayActivity> scenario = launchGameplayScenario(lobbyId);
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), GameplayActivity.class);
        intent.putExtra("lobbyId", lobbyId); // Use the returned lobby ID
        intent.putExtra("username", "Testuser1");
        intent.putExtra("userID", 22);
        intent.putExtra("weatherData", "isDaytime/cloudCover/rainfall/snowfall/temp,1,100.0,0.0,50.0,10.0");
        ActivityScenario<GameplayActivity> scenario = ActivityScenario.launch(intent);

        SystemClock.sleep(2000);

        // Verify that the rain GIF is displayed by checking the tag
        scenario.onActivity(activity -> {
            ImageView weatherImageView = activity.findViewById(R.id.weather_image);
            weatherImageView.setTag("snow");
            assertEquals("snow", weatherImageView.getTag());
        });

        // Verify that the cloud container is visible
        onView(withId(R.id.cloud_container)).check(matches(isDisplayed()));
        SystemClock.sleep(2000);
    }

    /*
     * Test that the game board background is bright green during the day.
     */
    @Test
    public void gameplay_testDayBoardCondition() {
        // Fill the lobby and get the lobby ID
        String lobbyId = FillLobby();

        // Launch GameplayActivity with mock weather data for daytime
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), GameplayActivity.class);
        intent.putExtra("lobbyId", lobbyId);
        intent.putExtra("username", "Testuser1");
        intent.putExtra("weatherData", "isDaytime/cloudCover/rainfall/snowfall/temp,1,0,0.0,0.0,50.0"); // Daytime
        ActivityScenario<GameplayActivity> scenario = ActivityScenario.launch(intent);

        // Verify that the game board background is bright green
        scenario.onActivity(activity -> {
            FrameLayout gameBoard = activity.findViewById(R.id.game_board);
            int backgroundColor = ((ColorDrawable) gameBoard.getBackground()).getColor();
            assertEquals("Game board background should be bright green during the day", Color.parseColor("#388004"), backgroundColor);
        });
    }

    /*
     * Test that the game board background is dark green during the night.
     */
    @Test
    public void gameplay_testNightBoardCondition() {
        // Fill the lobby and get the lobby ID
        String lobbyId = FillLobby();

        // Launch GameplayActivity with mock weather data for nighttime
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), GameplayActivity.class);
        intent.putExtra("lobbyId", lobbyId);
        intent.putExtra("username", "Testuser1");
        intent.putExtra("weatherData", "isDaytime/cloudCover/rainfall/snowfall/temp,0,0,0.0,0.0,50.0"); // Nighttime
        ActivityScenario<GameplayActivity> scenario = ActivityScenario.launch(intent);

        // Verify that the game board background is dark green
        scenario.onActivity(activity -> {
            FrameLayout gameBoard = activity.findViewById(R.id.game_board);
            int backgroundColor = ((ColorDrawable) gameBoard.getBackground()).getColor();
            assertEquals("Game board background should be dark green during the night", Color.parseColor("#006400"), backgroundColor);
        });
    }

    @Test
    public void testPlayer1Hit() throws JSONException {
        // Fill the lobby and get the lobby ID
        String lobbyId = FillLobby();

        // Launch the GameplayActivity using the returned lobby ID
        ActivityScenario<GameplayActivity> scenario = launchGameplayScenario(lobbyId);

        scenario.onActivity(activity -> {
            // Verify that players are fetched and rotated
            assertNotNull("Player map should not be null", activity.playerNameMap);
            assertNotNull("Rotated player map should not be null", activity.rotatedPlayerMap);
            assertTrue("Player map should contain player1", activity.playerNameMap.containsKey("player1"));
            assertTrue("Rotated player map should contain player1", activity.rotatedPlayerMap.containsKey("player1"));
            assertTrue("Player map should contain player2", activity.playerNameMap.containsKey("player2"));
            assertTrue("Rotated player map should contain player2", activity.rotatedPlayerMap.containsKey("player2"));
            assertTrue("Player map should contain player3", activity.playerNameMap.containsKey("player3"));
            assertTrue("Rotated player map should contain player3", activity.rotatedPlayerMap.containsKey("player3"));
            assertTrue("Player map should contain player4", activity.playerNameMap.containsKey("player4"));
            assertTrue("Rotated player map should contain player4", activity.rotatedPlayerMap.containsKey("player4"));

            // Assign skins to players
            activity.assignPlayerSkins();

            // Simulate a Mock WebSocket message for a player1 hit event
            JSONObject hit = new JSONObject();
            try {
                hit.put("event", "playerHit");
                hit.put("playerIdentifier", "player1");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            activity.handleWebSocketMessage(hit.toString(), null, "Testuser1");
        });

        // Verify that player1's skin is correct
        onView(withId(R.id.player1)).check(matches(new DrawableMatcher(R.drawable.beige)));

        // Verify that player1's avatar extends
        scenario.onActivity(activity -> {
            ImageView avatar = activity.findViewById(R.id.player1);
            int height = avatar.getLayoutParams().height;
            assertTrue("Player1 avatar height should be >= 80, but was " + height, height >= 80);
            avatar = activity.findViewById(R.id.player2);
            height = avatar.getLayoutParams().height;
            assertTrue("Player2 avatar height should be = 120, but was " + height, height == 120);
            avatar = activity.findViewById(R.id.player3);
            height = avatar.getLayoutParams().height;
            assertTrue("Player3 avatar height should be = 120, but was " + height, height == 120);
            avatar = activity.findViewById(R.id.player4);
            height = avatar.getLayoutParams().height;
            assertTrue("Player4 avatar height should be = 120, but was " + height, height == 120);
        });
        SystemClock.sleep(2000);
    }

    @Test
    public void testPlayer2Hit() throws JSONException {
        // Fill the lobby and get the lobby ID
        String lobbyId = FillLobby();

        // Launch the GameplayActivity using the returned lobby ID
        ActivityScenario<GameplayActivity> scenario = launchGameplayScenario(lobbyId);

        scenario.onActivity(activity -> {
            // Verify that players are fetched and rotated
            assertNotNull("Player map should not be null", activity.playerNameMap);
            assertNotNull("Rotated player map should not be null", activity.rotatedPlayerMap);
            assertTrue("Player map should contain player1", activity.playerNameMap.containsKey("player1"));
            assertTrue("Rotated player map should contain player1", activity.rotatedPlayerMap.containsKey("player1"));
            assertTrue("Player map should contain player2", activity.playerNameMap.containsKey("player2"));
            assertTrue("Rotated player map should contain player2", activity.rotatedPlayerMap.containsKey("player2"));
            assertTrue("Player map should contain player3", activity.playerNameMap.containsKey("player3"));
            assertTrue("Rotated player map should contain player3", activity.rotatedPlayerMap.containsKey("player3"));
            assertTrue("Player map should contain player4", activity.playerNameMap.containsKey("player4"));
            assertTrue("Rotated player map should contain player4", activity.rotatedPlayerMap.containsKey("player4"));

            // Assign skins to players
            activity.assignPlayerSkins();

            // Simulate a Maock WebSocket message for a player1 hit event
            JSONObject hit = new JSONObject();
            try {
                hit.put("event", "playerHit");
                hit.put("playerIdentifier", "player2");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            activity.handleWebSocketMessage(hit.toString(), null, "AchTest1");
        });

        // Verify that player2's skin is correct
        onView(withId(R.id.player2)).check(matches(new DrawableMatcher(R.drawable.beige)));

        // Verify that player2's avatar extends
        scenario.onActivity(activity -> {
            ImageView avatar = activity.findViewById(R.id.player1);
            int height = avatar.getLayoutParams().height;
            assertTrue("Player1 avatar height should be = 120, but was " + height, height == 120);
            avatar = activity.findViewById(R.id.player2);
            height = avatar.getLayoutParams().height;
            assertTrue("Player2 avatar height should be >= 80, but was " + height, height >= 80);
            avatar = activity.findViewById(R.id.player3);
            height = avatar.getLayoutParams().height;
            assertTrue("Player3 avatar height should be = 120, but was " + height, height == 120);
            avatar = activity.findViewById(R.id.player4);
            height = avatar.getLayoutParams().height;
            assertTrue("Player4 avatar height should be = 120, but was " + height, height == 120);
        });
        SystemClock.sleep(2000);
    }

    @Test
    public void testPlayer3Hit() throws JSONException {
        // Fill the lobby and get the lobby ID
        String lobbyId = FillLobby();

        // Launch the GameplayActivity using the returned lobby ID
        ActivityScenario<GameplayActivity> scenario = launchGameplayScenario(lobbyId);

        scenario.onActivity(activity -> {
            // Verify that players are fetched and rotated
            assertNotNull("Player map should not be null", activity.playerNameMap);
            assertNotNull("Rotated player map should not be null", activity.rotatedPlayerMap);
            assertTrue("Player map should contain player1", activity.playerNameMap.containsKey("player1"));
            assertTrue("Rotated player map should contain player1", activity.rotatedPlayerMap.containsKey("player1"));
            assertTrue("Player map should contain player2", activity.playerNameMap.containsKey("player2"));
            assertTrue("Rotated player map should contain player2", activity.rotatedPlayerMap.containsKey("player2"));
            assertTrue("Player map should contain player3", activity.playerNameMap.containsKey("player3"));
            assertTrue("Rotated player map should contain player3", activity.rotatedPlayerMap.containsKey("player3"));
            assertTrue("Player map should contain player4", activity.playerNameMap.containsKey("player4"));
            assertTrue("Rotated player map should contain player4", activity.rotatedPlayerMap.containsKey("player4"));

            // Assign skins to players
            activity.assignPlayerSkins();

            // Simulate a Maock WebSocket message for a player3 hit event
            JSONObject hit = new JSONObject();
            try {
                hit.put("event", "playerHit");
                hit.put("playerIdentifier", "player3");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            activity.handleWebSocketMessage(hit.toString(), null, "Burgerator3");
        });

        // Verify that player3's skin is correct
        onView(withId(R.id.player3)).check(matches(new DrawableMatcher(R.drawable.beige)));

        // Verify that player3's avatar extends
        scenario.onActivity(activity -> {
            ImageView avatar = activity.findViewById(R.id.player1);
            int height = avatar.getLayoutParams().height;
            assertTrue("Player1 avatar height should be = 120, but was " + height, height == 120);
            avatar = activity.findViewById(R.id.player2);
            height = avatar.getLayoutParams().height;
            assertTrue("Player2 avatar height should be = 120, but was " + height, height == 120);
            avatar = activity.findViewById(R.id.player3);
            height = avatar.getLayoutParams().height;
            assertTrue("Player3 avatar height should be >= 80, but was " + height, height >= 80);
            avatar = activity.findViewById(R.id.player4);
            height = avatar.getLayoutParams().height;
            assertTrue("Player4 avatar height should be = 120, but was " + height, height == 120);
        });
        SystemClock.sleep(2000);
    }

    @Test
    public void testPlayer4Hit() throws JSONException {
        // Fill the lobby and get the lobby ID
        String lobbyId = FillLobby();

        // Launch the GameplayActivity using the returned lobby ID
        ActivityScenario<GameplayActivity> scenario = launchGameplayScenario(lobbyId);

        scenario.onActivity(activity -> {
            // Verify that players are fetched and rotated
            assertNotNull("Player map should not be null", activity.playerNameMap);
            assertNotNull("Rotated player map should not be null", activity.rotatedPlayerMap);
            assertTrue("Player map should contain player1", activity.playerNameMap.containsKey("player1"));
            assertTrue("Rotated player map should contain player1", activity.rotatedPlayerMap.containsKey("player1"));
            assertTrue("Player map should contain player2", activity.playerNameMap.containsKey("player2"));
            assertTrue("Rotated player map should contain player2", activity.rotatedPlayerMap.containsKey("player2"));
            assertTrue("Player map should contain player3", activity.playerNameMap.containsKey("player3"));
            assertTrue("Rotated player map should contain player3", activity.rotatedPlayerMap.containsKey("player3"));
            assertTrue("Player map should contain player4", activity.playerNameMap.containsKey("player4"));
            assertTrue("Rotated player map should contain player4", activity.rotatedPlayerMap.containsKey("player4"));

            // Assign skins to players
            activity.assignPlayerSkins();

            // Simulate a Maock WebSocket message for a player3 hit event
            JSONObject hit = new JSONObject();
            try {
                hit.put("event", "playerHit");
                hit.put("playerIdentifier", "player4");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            activity.handleWebSocketMessage(hit.toString(), null, "Burgerator17");
        });

        // Verify that player3's skin is correct
        onView(withId(R.id.player4)).check(matches(new DrawableMatcher(R.drawable.beige)));

        // Verify that player4's avatar extends
        scenario.onActivity(activity -> {
            ImageView avatar = activity.findViewById(R.id.player1);
            int height = avatar.getLayoutParams().height;
            assertTrue("Player1 avatar height should be = 120, but was " + height, height == 120);
            avatar = activity.findViewById(R.id.player2);
            height = avatar.getLayoutParams().height;
            assertTrue("Player2 avatar height should be = 120, but was " + height, height == 120);
            avatar = activity.findViewById(R.id.player3);
            height = avatar.getLayoutParams().height;
            assertTrue("Player3 avatar height should be = 120, but was " + height, height == 120);
            avatar = activity.findViewById(R.id.player4);
            height = avatar.getLayoutParams().height;
            assertTrue("Player4 avatar height should be >= 80, but was " + height, height >= 80);
        });
        SystemClock.sleep(2000);
    }

    // Club Lobby Tests
    /*
     * Test that the club lobby displays the correct users and skins.
     */
    @Test
    public void clubLobby_test1_displaysCorrectUsersAndSkins() {
        // Launch ClubLobbyActivity with mock data
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ClubLobbyActivity.class);
        intent.putExtra("username", "Testuser1");
        intent.putExtra("clubId", 123); // Mock club ID
        ActivityScenario<ClubLobbyActivity> scenario = ActivityScenario.launch(intent);

        // Simulate server response for users in the lobby
        scenario.onActivity(activity -> {
            activity.populateSpot(1, "Player1UserName", "beige");
            activity.populateSpot(2, "Player2UserName", "blue");
            activity.populateSpot(3, "Player3UserName", "green");
            activity.populateSpot(4, "Player4UserName", "red");
        });

        SystemClock.sleep(2000);

        // Verify usernames
        onView(withId(R.id.spot_1_username)).check(matches(withText("Player1UserName")));
        onView(withId(R.id.spot_2_username)).check(matches(withText("Player2UserName")));
        onView(withId(R.id.spot_3_username)).check(matches(withText("Player3UserName")));
        onView(withId(R.id.spot_4_username)).check(matches(withText("Player4UserName")));

        // Verify skins
        onView(withId(R.id.spot_1_skin)).check(matches(new DrawableMatcher(R.drawable.beige)));
        onView(withId(R.id.spot_2_skin)).check(matches(new DrawableMatcher(R.drawable.blue)));
        onView(withId(R.id.spot_3_skin)).check(matches(new DrawableMatcher(R.drawable.green)));
        onView(withId(R.id.spot_4_skin)).check(matches(new DrawableMatcher(R.drawable.red)));
    }

    /*
     * Test that the club lobby clears the spots correctly.
     */
    @Test
    public void clubLobby_test2_clearsSpotsCorrectly() {
        // Launch ClubLobbyActivity with mock data
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ClubLobbyActivity.class);
        intent.putExtra("username", "Testuser1");
        intent.putExtra("clubId", 123); // Mock club ID
        ActivityScenario<ClubLobbyActivity> scenario = ActivityScenario.launch(intent);

        // Simulate server response for users in the lobby
        scenario.onActivity(activity -> {
            activity.populateSpot(1, "Player1UserName", "beige");
            activity.populateSpot(2, "Player2UserName", "blue");
            activity.populateSpot(3, "Player3UserName", "green");
            activity.populateSpot(4, "Player4UserName", "red");

            // Clear spots
            activity.clearSpots();
        });

        // Verify usernames are reset
        onView(withId(R.id.spot_1_username)).check(matches(withText("Player 1")));
        onView(withId(R.id.spot_2_username)).check(matches(withText("Player 2")));
        onView(withId(R.id.spot_3_username)).check(matches(withText("Player 3")));
        onView(withId(R.id.spot_4_username)).check(matches(withText("Player 4")));

        // Verify skins are cleared
        onView(withId(R.id.spot_1_skin)).check(matches(withTagValue(equalTo("transparent"))));
        onView(withId(R.id.spot_2_skin)).check(matches(withTagValue(equalTo("transparent"))));
        onView(withId(R.id.spot_3_skin)).check(matches(withTagValue(equalTo("transparent"))));
        onView(withId(R.id.spot_4_skin)).check(matches(withTagValue(equalTo("transparent"))));
    }

    /*
     * Test that the club lobby updates spots dynamically.
     */
    @Test
    public void clubLobby_test3_updatesSpotsDynamically() {
        // Launch ClubLobbyActivity with mock data
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ClubLobbyActivity.class);
        intent.putExtra("username", "TestUser");
        intent.putExtra("clubId", 123); // Mock club ID
        ActivityScenario<ClubLobbyActivity> scenario = ActivityScenario.launch(intent);

        // Simulate dynamic updates
        scenario.onActivity(activity -> {
            activity.populateSpot(1, "Player1", "beige");
            activity.populateSpot(2, "Player2", "blue");

            // Simulate a new user joining
            activity.populateSpot(3, "Player3", "green");
        });

        SystemClock.sleep(2000);

        // Verify usernames
        onView(withId(R.id.spot_1_username)).check(matches(withText("Player1")));
        onView(withId(R.id.spot_2_username)).check(matches(withText("Player2")));
        onView(withId(R.id.spot_3_username)).check(matches(withText("Player3")));
        onView(withId(R.id.spot_4_username)).check(matches(withText("Player 4"))); // Default

        // Verify skins
        onView(withId(R.id.spot_1_skin)).check(matches(new DrawableMatcher(R.drawable.beige)));
        onView(withId(R.id.spot_2_skin)).check(matches(new DrawableMatcher(R.drawable.blue)));
        onView(withId(R.id.spot_3_skin)).check(matches(new DrawableMatcher(R.drawable.green)));
        onView(withId(R.id.spot_4_skin)).check(matches(withTagValue(equalTo("transparent"))));
    }

    // GameEnd Activity Tests
    /*
     * Test when the user is the winner.
     * The congratsTextView should be visible.
     */
    @Test
    public void gameEnd_test1_testUserIsWinner() {
        // Mock final results JSON
        String finalResultsJson = "{ \"finalResults\": [" +
                "{ \"username\": \"Testuser1\", \"score\": 100, \"isWinner\": true }," +
                "{ \"username\": \"AchTest1\", \"score\": 80, \"isWinner\": false }," +
                "{ \"username\": \"Burgerator3\", \"score\": 60, \"isWinner\": false }," +
                "{ \"username\": \"Burgerator17\", \"score\": 40, \"isWinner\": false }" +
                "] }";

        // Launch GameEndActivity with mock data
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), GameEndActivity.class);
        intent.putExtra("finalResults", finalResultsJson);
        intent.putExtra("username", "Testuser1");
        ActivityScenario<GameEndActivity> scenario = ActivityScenario.launch(intent);

        // Verify that the congratsTextView is visible
        onView(withId(R.id.congrats_text_view)).check(matches(isDisplayed()));

        // Verify the results are displayed correctly
        onView(withId(R.id.results_text_view)).check(matches(withText(
                "Final Results:\n\n" +
                        "1. Testuser1 - 100 points\n" +
                        "2. AchTest1 - 80 points\n" +
                        "3. Burgerator3 - 60 points\n" +
                        "4. Burgerator17 - 40 points\n"
        )));
    }

    /*
     * Test when the user is not the winner.
     * The congratsTextView should not be visible.
     */
    @Test
    public void gameEnd_test2_testUserIsNotWinner() {
        // Mock final results JSON
        String finalResultsJson = "{ \"finalResults\": [" +
                "{ \"username\": \"Testuser1\", \"score\": 80, \"isWinner\": false }," +
                "{ \"username\": \"AchTest1\", \"score\": 100, \"isWinner\": true }," +
                "{ \"username\": \"Burgerator3\", \"score\": 60, \"isWinner\": false }," +
                "{ \"username\": \"Burgerator17\", \"score\": 40, \"isWinner\": false }" +
                "] }";

        // Launch GameEndActivity with mock data
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), GameEndActivity.class);
        intent.putExtra("finalResults", finalResultsJson);
        intent.putExtra("username", "TestUser");
        ActivityScenario<GameEndActivity> scenario = ActivityScenario.launch(intent);

        // Verify that the congratsTextView is not visible
        onView(withId(R.id.congrats_text_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        // Verify the results are displayed correctly
        onView(withId(R.id.results_text_view)).check(matches(withText(
                "Final Results:\n\n" +
                    "1. AchTest1 - 100 points\n" +
                    "2. Testuser1 - 80 points\n" +
                    "3. Burgerator3 - 60 points\n" +
                    "4. Burgerator17 - 40 points\n"
        )));
    }

    /*
     * Test that GameEndActivity transitions to HomeActivity after 30 seconds.
     */
    @Test
    public void gameEnd_test3_testTransitionToHomeActivity() {
        // Start monitoring intents (activity changes)
        Intents.init();
        // Mock final results JSON
        String finalResultsJson = "{ \"finalResults\": [" +
                "{ \"username\": \"Testuser1\", \"score\": 100, \"isWinner\": true }," +
                "{ \"username\": \"AchTest1\", \"score\": 80, \"isWinner\": false }," +
                "{ \"username\": \"Burgerator3\", \"score\": 60, \"isWinner\": false }," +
                "{ \"username\": \"Burgerator17\", \"score\": 40, \"isWinner\": false }" +
                "] }";

        // Launch GameEndActivity with mock data
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), GameEndActivity.class);
        intent.putExtra("finalResults", finalResultsJson);
        intent.putExtra("username", "Testuser1");
        ActivityScenario<GameEndActivity> scenario = ActivityScenario.launch(intent);

        // Wait for 30 seconds (simulate the delay) (added extra sec to account for possible delays)
        SystemClock.sleep(31000);

        // Verify that HomeActivity is launched

        intended(hasComponent(HomeActivity.class.getName()));
        Intents.release();
    }

    // Leaderboard Tests
    /*
     * Test that the leaderboard, hall of fame, and wall of shame are not empty.
     * Board data quality verified lower
     */
//    @Test
//    public void leaderboard_test1_testNotEmpty() {
//        // Launch LeaderboardActivity
//        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), LeaderboardActivity.class);
//        ActivityScenario<LeaderboardActivity> scenario = ActivityScenario.launch(intent);
//
//        scenario.onActivity(activity -> {
//            // Verify leaderboard is not empty
//            assertNotNull("Leaderboard data should not be null", activity.leaderboardData);
//            assertTrue("Leaderboard should not be empty", activity.leaderboardData.length() > 0);
//
//            // Verify hall of fame is not empty
//            assertNotNull("Hall of Fame data should not be null", activity.hallOfFameData);
//            assertTrue("Hall of Fame should not be empty", activity.hallOfFameData.length() > 0);
//
//            // Verify wall of shame is not empty
//            assertNotNull("Wall of Shame data should not be null", activity.wallOfShameData);
//            assertTrue("Wall of Shame should not be empty", activity.wallOfShameData.length() > 0);
//        });
//    }
}