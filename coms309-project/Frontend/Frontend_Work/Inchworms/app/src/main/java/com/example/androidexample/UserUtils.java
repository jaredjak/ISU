package com.example.androidexample;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class UserUtils {

    public static final String URL_GET_USER_BY_USERNAME = "http://coms-3090-013.class.las.iastate.edu:8080/users/username/";

    public static void getUserDetails(Context context, final String username, final String inputPassword, final TextView msgResponse, final UserDetailsCallback callback) {
        JsonObjectRequest userDetailsReq = new JsonObjectRequest(
                Request.Method.GET,
                URL_GET_USER_BY_USERNAME + username,
                null, // No request body for GET request
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Volley Response", response.toString());
                        try {
                            String fetchedPassword = response.getString("password");

                            if (inputPassword.equals(fetchedPassword)) {
                                int userId = response.getInt("id");
                                callback.onSuccess(userId);
                            } else {
                                msgResponse.setText("Invalid credentials. Please try again.");
                                callback.onFailure();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            msgResponse.setText("Error parsing response.");
                            callback.onFailure();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                        if (error.networkResponse != null && error.networkResponse.statusCode == 404) {
                            msgResponse.setText("Username not found. Please try again.");
                        } else {
                            msgResponse.setText("Failed to fetch user details. Please check your connection.");
                        }
                        callback.onFailure();
                    }
                }
        );

        Volley.newRequestQueue(context).add(userDetailsReq);
    }


    public interface UserDetailsCallback {
        void onSuccess(int userId);
        void onFailure();
    }
}
