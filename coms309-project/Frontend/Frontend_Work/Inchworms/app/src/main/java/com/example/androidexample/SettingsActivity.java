package com.example.androidexample;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.androidexample.UserUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {

    private Button deleteBtn, exitBtn, logoutBtn, reportBtn;
    private String currentUsername;
    private static final String backend_URL = "https://5f0b41a5-6959-4925-861a-c001c1e110fe.mock.pstmn.io/signTest"; //"https://5f0b41a5-6959-4925-861a-c001c1e110fe.mock.pstmn.io/signup";
    public static final String URL_GET_USER_BY_USERNAME = "http://coms-3090-013.class.las.iastate.edu:8080/users/username/";
    public static final String URL_DELETE_USER = "http://coms-3090-013.class.las.iastate.edu:8080/users/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        // Fetch the username and clubId for now
        SharedPreferences prefs = this.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        currentUsername = prefs.getString("username", null);


        /* Initalizing */
        deleteBtn = findViewById(R.id.deleteBtn);
        exitBtn = findViewById(R.id.exitBtn);
        logoutBtn = findViewById(R.id.logoutBtn);
        reportBtn = findViewById(R.id.reportBtn);

        // I have no idea what this does yet. Came with the pre-made Settings Activity
//        if (savedInstanceState == null) {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.settings, new SettingsFragment())
//                    .commit();
//        }
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }

        /* Listener for the delete button */
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog();
            }
        });

        /* Listener for the report button */
        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReportDialog();
            }
        });

        /* Listener for the exit button */
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        /* Listener for the logout button */
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.apply();

                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    /* Dialog for delete button prompting for the userame, password, and SSN number to continue with deletion*/
    public void deleteDialog() {

//        builder.setTitle("Confirm Deletion");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_delete_user, null);
        final EditText userInput = dialogView.findViewById(R.id.username_input);
        final EditText passwordInput = dialogView.findViewById(R.id.password_input);
        final EditText ssnInput = dialogView.findViewById(R.id.ssn_input);
        Button delete = dialogView.findViewById(R.id.settings_delete_btn);
        Button exit = dialogView.findViewById(R.id.delete_exit_btn);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create();

//        builder.setView(dialogView);

//        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                String username = userInput.getText().toString().trim();
//                String password = passwordInput.getText().toString().trim();
//                String ssn = ssnInput.getText().toString().trim();
//
//                if (!username.isEmpty() && !password.isEmpty() && !ssn.isEmpty()) {
//                    //verifyUser(username, password, ssn);
//                    verifyUser(username, password, ssn);
//                } else {
//                    Toast.makeText(SettingsActivity.this, "tsk tsk tsk", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        delete.setOnClickListener(v -> {
            String username = userInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            String ssn = ssnInput.getText().toString().trim();

            if (!username.isEmpty() && !password.isEmpty() && !ssn.isEmpty()) {
                verifyUser(username, password, ssn);
                dialog.dismiss();
            } else {
                Toast.makeText(SettingsActivity.this, "tsk tsk tsk", Toast.LENGTH_SHORT).show();
            }
        });

//        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
//        builder.show();

        exit.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    /* Verifies the user information before proceeding to deletion */
    private void verifyUser(String username, String password, String ssn) {
        String username_URL = "http://coms-3090-013.class.las.iastate.edu:8080/users/username/" + username;

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                username_URL,
                response -> {
            try {
                JSONObject user = new JSONObject(response);
                String storedPassword = user.getString("password");
                int storedSSN = user.getInt("ssn");
                int userId = user.getInt("id");



                if (password.equals(storedPassword) && Integer.parseInt(ssn) == storedSSN) {
                    deleteUserSkins(userId, username);
                } else {
                    Toast.makeText(SettingsActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(SettingsActivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
            }
        },
        error -> {
            Toast.makeText(SettingsActivity.this, "User not found pr server error", Toast.LENGTH_SHORT).show();
        });

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    /* Deletes the users cosmetics before proceeding with deletion */
    private void deleteUserSkins(int id, String username) {
        String skinsURL = "http://coms-3090-013.class.las.iastate.edu:8080/cosmetics/user/" + username;

        StringRequest request = new StringRequest(
                Request.Method.DELETE,
                skinsURL,
                response -> {
                    Log.d("DEL_response", "Response: " + response.toString());

                    deleteUser(id);
                },
                error -> {
                    Log.e("Volley Error", "Failed to delete cosmetics: " + error.toString());
                    Toast.makeText(SettingsActivity.this, "Failed to delete cosmetics", Toast.LENGTH_SHORT).show();
                }
        );

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    /* Deletes the account after deleting the users cosmetics */
    private void deleteUser(int id) {
        String userURL = "http://coms-3090-013.class.las.iastate.edu:8080/users/" + id;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.DELETE,
                userURL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //debugging
                            Log.d("DEL_response", "Response: " + response.toString());

//                            boolean success = response.getBoolean("success");
                            String message = response.getString("message");

//                            if (success) {

                            if (message.equals("success")) {

                                SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.clear();
                                editor.apply();

                                /* Sends user to the main page*/
                                Toast.makeText(SettingsActivity.this, "User deleted", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(SettingsActivity.this, "Failed to delete", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // getting to here even tho deletion was successful??
                            Toast.makeText(SettingsActivity.this, "Error deleting", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SettingsActivity.this, "Error connecting to server", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Authorization", "Bearer YOUR_ACCESS_TOKEN");
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }

    private void showReportDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_report_settings, null);

        final EditText badUser = dialogView.findViewById(R.id.bad_username_input);
        final EditText explanationReport = dialogView.findViewById(R.id.bad_explanation_input);
        Button submit = dialogView.findViewById(R.id.settings_report_btn);
        Button exit = dialogView.findViewById(R.id.report_exit_btn);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create();
//                .setPositiveButton("Report", (dialog, which) -> {
//                    String reportedUser = badUser.getText().toString().trim();
//                    String explanation = explanationReport.getText().toString().trim();
//
//                    if (!reportedUser.isEmpty() && !explanation.isEmpty()) {
//                        submitReport(reportedUser, explanation);
//                    } else {
//                        Toast.makeText(this, "Please fill out both fields.", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        submit.setOnClickListener(v -> {
            String reportedUser = badUser.getText().toString().trim();
            String explanation = explanationReport.getText().toString().trim();

            if (!reportedUser.isEmpty()) {
                submitReport(reportedUser, explanation);
                dialog.dismiss();
            } else {
                Toast.makeText(this, "Please put a valid user.", Toast.LENGTH_SHORT).show();
            }
        });

        exit.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void submitReport(String reportedUser, String explanation) {
        String url = "http://coms-3090-013.class.las.iastate.edu:8080/reports?reportingUsername="
                + currentUsername +
                "&reportedUsername=" + reportedUser +
                "&explanation=" + Uri.encode(explanation);

        //"&messageId=" + message.getId() + // goes before explanation

        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            Log.d("Given response", "Response: " + response);
            Toast.makeText(this, "Report submitted.", Toast.LENGTH_SHORT).show();
        },
                error -> {
                    Log.d("Submit Report Error", "Error: " + error.toString());
                    Toast.makeText(this, "Error submitting report.", Toast.LENGTH_SHORT).show();
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }


}