package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateProfileRequest extends AppCompatActivity {

    private EditText newUsernameEditText;
    private EditText curentUsernameEditText;
    private EditText newPasswordEditText;
    private EditText curentPasswordEditText;
    private EditText emailEditText;
    private Button btnExit;
    private Button btnUpdateProfile;
    private TextView msgResponse;

    public static final String URL_UPDATE_USERNAME = "http://coms-3090-013.class.las.iastate.edu:8080/users/usernameReset/";
    public static final String URL_UPDATE_PASSWORD = "http://coms-3090-013.class.las.iastate.edu:8080/users/passwordReset/";
    public static final String URL_UPDATE_EMAIL = "http://coms-3090-013.class.las.iastate.edu:8080/users/emailReset/";
    public static final String URL_GET_PROFILE = "http://coms-3090-013.class.las.iastate.edu:8080/users/username/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_profile);

        curentUsernameEditText = findViewById(R.id.current_username_edt);
        newUsernameEditText = findViewById(R.id.update_username_edt);
        curentPasswordEditText = findViewById(R.id.current_password_edt);
        newPasswordEditText = findViewById(R.id.update_password_edt);
        emailEditText= findViewById(R.id.update_email_edt);
        btnUpdateProfile = findViewById(R.id.btnUpdateProfile);
        btnExit = findViewById(R.id.btnExit);
        msgResponse = findViewById(R.id.msgResponse);

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentUsername = curentUsernameEditText.getText().toString().trim();
                String newUsername = newUsernameEditText.getText().toString().trim();
                String currentPassword = curentPasswordEditText.getText().toString().trim();
                String newPassword = newPasswordEditText.getText().toString().trim();
                String newEmail = emailEditText.getText().toString().trim();

                if (newUsername.isEmpty() || newPassword.isEmpty() || newEmail.isEmpty()) {
                    msgResponse.setText("Please fill out all fields.");
                } else {
                    // Get the current username and then update the username
                    UserUtils.getUserDetails(UpdateProfileRequest.this, currentUsername, currentPassword, msgResponse, new UserUtils.UserDetailsCallback() {
                        @Override
                        public void onSuccess(int userId) {
                            updateUsername(currentUsername, newUsername);
                            //
                            // Introduce a delay of 2 seconds (2000 milliseconds)
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // Now, call updatePassword after the delay
                                    updatePassword(newUsername, newPassword); // Pass the desired new password
                                }
                            }, 2000); // 2000ms delay
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // Now, call updatePassword after the delay
                                    updateEmail(newUsername, newEmail); // Pass the desired new email
                                }
                            }, 2000); // 2000ms delay

                        }

                        @Override
                        public void onFailure() {
                            msgResponse.setText("Failed to fetch user details.");
                        }
                    });
                }
            }
        });
        
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateProfileRequest.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private void updateUsername(final String currentUsername, final String newUsername) {
        String url = URL_UPDATE_USERNAME + currentUsername + "/" + newUsername;

        // Use JsonObjectRequest as the response is a JSON object
        JsonObjectRequest updateUsernameReq = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                null, // No body content for PUT request in this case
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Volley Response", response.toString()); // Log the raw response

                        try {
                            // Inspect the actual fields of the response here
                            Log.d("Parsed Response", response.toString());

                            // Check if the username was updated successfully
                            String updatedUsername = response.getString("username");

                            if (updatedUsername.equals(newUsername)) {
                                msgResponse.setText("Username updated successfully.");
                            } else {
                                msgResponse.setText("Failed to update username. Please try again.");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            msgResponse.setText("Error parsing response. Raw response: " + response); // Show the raw response for debugging
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                        msgResponse.setText("Update request failed. Please check your connection.");
                    }
                }
        );

        Volley.newRequestQueue(this).add(updateUsernameReq);
    }

    private void updatePassword(final String currentUsername, final String newPassword) {
        String url = URL_UPDATE_PASSWORD + currentUsername + "/" + newPassword;

        // Use JsonObjectRequest as the response is a JSON object
        JsonObjectRequest updatePasswordReq = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                null, // No body content for PUT request in this case
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Volley Response", response.toString()); // Log the raw response

                        try {
                            // Inspect the actual fields of the response here
                            Log.d("Parsed Response", response.toString());

                            // Check if the password was updated successfully by checking the updated username (or other fields)
                            String updatedPassword = response.getString("password");

                            if (updatedPassword.equals(newPassword)) {
                                msgResponse.setText("Password updated successfully.");
                            } else {
                                msgResponse.setText("Failed to update password. Please try again.");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            msgResponse.setText("Error parsing response. Raw response: " + response); // Show the raw response for debugging
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                        msgResponse.setText("Update request failed. Please check your connection.");
                    }
                }
        );

        Volley.newRequestQueue(this).add(updatePasswordReq);
    }

    private void updateEmail(final String currentUsername, final String newEmail) {
        // Construct the URL with the current username and the new email
        String url = URL_UPDATE_EMAIL + currentUsername + "/" + newEmail;

        // Use JsonObjectRequest as the response is a JSON object
        JsonObjectRequest updateEmailReq = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                null, // No body content for PUT request in this case
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Volley Response", response.toString()); // Log the raw response

                        try {
                            // Inspect the actual fields of the response here
                            Log.d("Parsed Response", response.toString());

                            // Check if the email was updated successfully
                            String updatedEmail = response.getString("email");

                            if (updatedEmail.equals(newEmail)) {
                                msgResponse.setText("Email updated successfully.");
                            } else {
                                msgResponse.setText("Failed to update email. Please try again.");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            msgResponse.setText("Error parsing response. Raw response: " + response); // Show the raw response for debugging
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                        msgResponse.setText("Update request failed. Please check your connection.");
                    }
                }
        );

        // Add the request to the Volley queue
        Volley.newRequestQueue(this).add(updateEmailReq);
    }
}