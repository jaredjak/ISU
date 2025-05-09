package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.androidexample.UserUtils;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SignInRequest extends AppCompatActivity {

    private Button btnSignIn;
    private Button btnUpdateProfile;
    private TextView msgResponse;
    private Button exitButton;
    private EditText usernameEditText;
    private EditText passwordEditText;

    public static final String URL_GET_USER_BY_USERNAME = "http://coms-3090-013.class.las.iastate.edu:8080/users/username/";
    public static final String URL_POST_SIGN_IN = "http://coms-3090-013.class.las.iastate.edu:8080/users";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnUpdateProfile = findViewById(R.id.btnUpdateProfile);
        msgResponse = findViewById(R.id.msgResponse);
        usernameEditText = findViewById(R.id.signin_username_edt);
        passwordEditText = findViewById(R.id.signin_password_edt);
        exitButton = findViewById(R.id.exitBtn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    msgResponse.setText("Please enter both username and password.");
                } else {
                    //makeSignInReq(username, password);
                    getUserDetails(username, password);
                }
            }
        });

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(SignInRequest.this, UpdateProfileActivity.class);
//                startActivity(intent);
                startActivity(new Intent(SignInRequest.this, UpdateProfileRequest.class));
            }
        });

        /* click listener on exit button pressed */
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInRequest.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

//     private void getUserDetails(final String username, final String inputPassword) {
//         //Debugging:
//         String url = URL_GET_USER_BY_USERNAME + username;
//         Log.d("Request URL", url);  // Log the actual request URL

//         StringRequest userDetailsReq = new StringRequest(
//                 Request.Method.GET,
//                 URL_GET_USER_BY_USERNAME + username,
//                 new Response.Listener<String>() {
//                     @Override
//                     public void onResponse(String response) {
//                         Log.d("Volley Response", response);
//                         try {
//                             JSONObject jsonResponse = new JSONObject(response);
//                             String fetchedPassword = jsonResponse.getString("password");

//                             if (inputPassword.equals(fetchedPassword)) {
//                                 msgResponse.setText("Welcome Back to Insatiable Inchworms!\n\nUsername: " + username + "\nPassword: " + inputPassword);
//                                 btnUpdateProfile.setVisibility(View.VISIBLE);

//                                 /* Sends user to the games home page*/
//                                 Intent intent = new Intent(SignInRequest.this, HomeActivity.class);
//                                 startActivity(intent);
//                             } else {
//                                 // Not reaching here - investigate
//                                 msgResponse.setText("Invalid credentials. Please try again.");
//                             }

//                         } catch (JSONException e) {
//                             e.printStackTrace();
//                             msgResponse.setText("Error parsing response.");
//                         }
//                     }
//                 },
//                 new Response.ErrorListener() {
//                     @Override
//                     public void onErrorResponse(VolleyError error) {
//                         Log.e("Volley Error", "Error: " + error.toString());

// //                        if (error.networkResponse != null && error.networkResponse.statusCode == 404) {
// //                            msgResponse.setText("Username not found. Please try again.");
// //                        } else {
// //                            msgResponse.setText("Failed to fetch user details. Please check your connection.");
// //                        }

//                         if (error.networkResponse != null) {
//                             int statusCode = error.networkResponse.statusCode;
//                             Log.e("Volley Error", "HTTP Status Code: " + statusCode);
//                             msgResponse.setText("Error: " + statusCode);
//                         } else {
//                             msgResponse.setText("Failed to fetch user details. Please check your connection.");
//                         }
//                     }
//                 }
//         );

//         Volley.newRequestQueue(this).add(userDetailsReq);
//     }

    private void getUserDetails(final String username, final String inputPassword) {
        UserUtils.getUserDetails(this, username, inputPassword, msgResponse, new UserUtils.UserDetailsCallback() {
            @Override
            public void onSuccess(int userId) {
                msgResponse.setText("Welcome Back to Insatiable Inchworms!\n\nUsername: " + username + "\nPassword: " + inputPassword);
                btnUpdateProfile.setVisibility(View.VISIBLE);

                /* Sends user to the games home page*/
                fetchUserClubId(username);
            }

            @Override
            public void onFailure() {
                // Error message is already set in UserUtils
            }
        });
    }

    private void fetchUserClubId(String username) {
        String clubIdURL = "http://coms-3090-013.class.las.iastate.edu:8080/users/username/" + username;

        JsonObjectRequest clubIdRequest = new JsonObjectRequest(Request.Method.GET, clubIdURL, null,
                response -> {
                    try {
                        int clubId = response.getInt("clubId");

                        // Check if user is banned
                        checkIfBanned(username, isBanned -> {
                            if (isBanned) {
                                Toast.makeText(this, "Your account has been banned.", Toast.LENGTH_LONG).show();
                                return;
                            }


                            SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("username", username);
                            editor.putInt("clubId", clubId);
                            editor.apply();

                            Intent intent = new Intent(SignInRequest.this, HomeActivity.class);
                            startActivity(intent);
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(this, "Failed to fetch club id", Toast.LENGTH_SHORT).show();
                });

        Volley.newRequestQueue(this).add(clubIdRequest);
    }

    private void checkIfBanned(String username, BanCheckCallback callback) {
        String bannedURL = "http://coms-3090-013.class.las.iastate.edu:8080/admin/status/" + username;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, bannedURL, null, response -> {
            try {
                boolean isBanned = response.getBoolean("banned");
                Log.d("Ban Check", "isBanned: " + isBanned);
                callback.onResult(isBanned);
            } catch (JSONException e) {
                e.printStackTrace();
                callback.onResult(false); // Assume not banned
            }
        },
                error -> {
                    error.printStackTrace();
                    callback.onResult(false); // Assume not banned
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    /* Used because volley requests are asynchronous or something */
    public interface BanCheckCallback {
        void onResult(boolean isBanned);
    }


    // Not needed- Logically incorrect to check credentials with GET then POST an 'new" user that already exists
//    private void makeSignInReq(final String username, final String password) {
//        StringRequest signInReq = new StringRequest(
//                Request.Method.POST,
//                URL_POST_SIGN_IN,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("Volley Response", response);
//                        try {
//                            JSONObject jsonResponse = new JSONObject(response);
//                            boolean success = jsonResponse.getBoolean("success");
//
//                            if (success) {
//                                msgResponse.setText("Welcome Back to Insatiable Inchworms!\n\nUsername: " + username + "\nPassword: " + password);
//                                btnUpdateProfile.setVisibility(View.VISIBLE);
//
//                                /* Sends user to the games home page*/
//                                Intent intent = new Intent(SignInRequest.this, HomeActivity.class);
//                                startActivity(intent);
//                            } else {
//                                msgResponse.setText("Invalid credentials. Please try again.");
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            msgResponse.setText("Error parsing response.");
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("Volley Error", error.toString());
//                        msgResponse.setText("Login request failed. Please check your connection.");
//                    }
//                }
//        ) {
//            @Override
//            public Map<String, String> getHeaders() {
//                Map<String, String> headers = new HashMap<>();
//                return headers;
//            }
//
//            @Override
//            public byte[] getBody() {
//                Map<String, String> params = new HashMap<>();
//                params.put("username", username);
//                params.put("password", password);
//
//                return new JSONObject(params).toString().getBytes(StandardCharsets.UTF_8);
//            }
//        };
//
//        Volley.newRequestQueue(this).add(signInReq);
//    }

}