package com.example.androidexample;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private RecyclerView chatsRecyclerView, reportsRecyclerView;
    private EditText chatSearchEditText, clubSearchEditText, adminSearchEditText, suspendTimeEditText;
    private TextView reportDetailsUser, reportDetailsExplanation, reportDetailsMessage, reportDetailsTimestamp, reportDetailsReporter;
    private Button searchChatBtn, searchClubBtn, banBtn, suspendBtn, exitBtn;

    private List<ChatMessage> chatList = new ArrayList<>();
    private List<AdminReport> reportList = new ArrayList<>();

    private AdminUserChatAdapter adminUserChatAdapter;
    private AdminReportAdapter adminReportAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity);

        // Variable initialization
        chatsRecyclerView = findViewById(R.id.chatsRecyclerView);
        reportsRecyclerView = findViewById(R.id.reportsRecyclerView);

        chatSearchEditText = findViewById(R.id.chatSearchEditText);
//        clubSearchEditText = findViewById(R.id.clubSearchEditText);
        adminSearchEditText = findViewById(R.id.adminSearchEditText);
        suspendTimeEditText = findViewById(R.id.suspendTimeEditText);

        reportDetailsUser = findViewById(R.id.reportDetailsUser);
        reportDetailsExplanation = findViewById(R.id.reportDetailsExplanation);
        reportDetailsMessage = findViewById(R.id.reportDetailsMessage);
        reportDetailsTimestamp = findViewById(R.id.reportDetailsTimestamp);
        reportDetailsReporter = findViewById(R.id.reportDetailsReporter);

        searchChatBtn = findViewById(R.id.searchChats);
//        searchClubBtn = findViewById(R.id.searchClub);
        banBtn = findViewById(R.id.banButton);
        suspendBtn = findViewById(R.id.suspendButton);
        exitBtn = findViewById(R.id.exitButton);

        // RecyclerView setup
        chatsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adminUserChatAdapter = new AdminUserChatAdapter(chatList);
        chatsRecyclerView.setAdapter(adminUserChatAdapter);

        //Hard-coded test report
//        hardCodedReport();

        reportsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adminReportAdapter = new AdminReportAdapter(reportList, new AdminReportAdapter.OnReportClickListener() {
            @Override
            public void onDetailsClick(AdminReport report) {
                reportDetailsUser.setText("Reported User: " + report.getReportedUser());
                reportDetailsReporter.setText("Reporting User: " + report.getReportingUser());
                reportDetailsExplanation.setText("Explanation: " + report.getExplanation());
//                reportDetailsMessage.setText("Message: " + report.getMessage());
                reportDetailsMessage.setText("They deserve to be banned!");
                reportDetailsTimestamp.setText("Timestamp: " + report.getTimestamp());
            }

            @Override
            public void onDismissClick(AdminReport report) {
                /**
                 * FUNCTIONLESS BUTTON. YOU HATE TO SEE IT 😔
                 */
            }
        });

        reportsRecyclerView.setAdapter(adminReportAdapter);
        // Get the reports to fill in the recycler view
        getReports();

        // Click listener for search chat button
        searchChatBtn.setOnClickListener(v -> {
            String username = chatSearchEditText.getText().toString().trim();

            if (username.isEmpty()) {
                Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
            }

            String chatHistoryURL = "http://coms-3090-013.class.las.iastate.edu:8080/admin/chat/history/" + username;

            JsonArrayRequest chatRequest = new JsonArrayRequest(Request.Method.GET, chatHistoryURL, null, response -> {
                try {
                    chatList.clear();

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject obj = response.getJSONObject(i);
                        String message = obj.getString("message");
                        String sender = obj.getString("senderUsername");
                        String timestamp = obj.getString("timestamp");

                        chatList.add(new ChatMessage(message, sender, timestamp, null));
                    }

                    adminUserChatAdapter.updateMessages(chatList);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error parsing chat history", Toast.LENGTH_SHORT).show();
                }
            },
            error -> {
                error.printStackTrace();
                Toast.makeText(this, "Failed to load chat history", Toast.LENGTH_SHORT).show();
            });

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(chatRequest);
        });

        // Click listener for ban button
        banBtn.setOnClickListener(view -> {
            String username = adminSearchEditText.getText().toString().trim();

            if (username.isEmpty()) {
                Toast.makeText(this, "Please enter username to ban", Toast.LENGTH_SHORT).show();
            }

            String banURL = "http://coms-3090-013.class.las.iastate.edu:8080/admin/ban/" + username;

            StringRequest banRequest = new StringRequest(Request.Method.PUT, banURL, response -> {
                Log.d("Ban  Response", response.toString());
                Toast.makeText(this, "User '" + username + "' has been banned.", Toast.LENGTH_SHORT).show();
            },
            error -> {
                Toast.makeText(this, "Failed to ban user:" + username, Toast.LENGTH_SHORT).show();
            }
            );

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(banRequest);
        });


        // Click listener for suspend button
        suspendBtn.setOnClickListener(view -> {
            String username = adminSearchEditText.getText().toString().trim();
            String minutesStr = suspendTimeEditText.getText().toString().trim();

            if (username.isEmpty()) {
                Toast.makeText(this, "Please enter username to ban", Toast.LENGTH_SHORT).show();
            }

            if (minutesStr.isEmpty()) {
                Toast.makeText(this, "Please enter suspend time", Toast.LENGTH_SHORT).show();
            }

            int minutes = 0;
            try {
                minutes = Integer.parseInt(minutesStr);
                if (minutes <= 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter valid number of minutes", Toast.LENGTH_SHORT).show();
            }

            String suspendURL = "http://coms-3090-013.class.las.iastate.edu:8080/admin/suspend/" + username + "/" + minutes;
            int finalMinutes = minutes; //Got an error without this (idk why)

            StringRequest suspendRequest = new StringRequest(Request.Method.PUT, suspendURL, response -> {
                Log.d("Suspend  Response", response.toString());
                Toast.makeText(this, "User '" + username + "' has been suspended for " + finalMinutes + " minutes.", Toast.LENGTH_SHORT).show();
            },
                    error -> {
                        Toast.makeText(this, "Failed to ban user:" + username, Toast.LENGTH_SHORT).show();
                    }
            );

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(suspendRequest);
        });


        // Click listener for exit button
        exitBtn.setOnClickListener(view -> {
            Intent intent = new Intent(AdminActivity.this, HomeActivity.class);
            startActivity(intent);
        });
    }

    /* Gets the reports stored in the backend */
    private void getReports() {
        String reportsURL = "http://coms-3090-013.class.las.iastate.edu:8080/reports/all";

        Log.d("GET_REPORTS", "Attempting to fetch reports from: " + reportsURL);

        JsonArrayRequest reportRequest = new JsonArrayRequest(Request.Method.GET, reportsURL, null, response -> {
            try {
                Log.d("GET_REPORTS", "Response received: " + response.toString());

                reportList.clear();

                for (int i = 0; i < response.length(); i++) {
                    Log.d("Report Response", "Response:" + response);
                    JSONObject obj = response.getJSONObject(i);

                    Log.d("Object", obj.toString());

                    JSONObject reportedUserObj = obj.getJSONObject("reportedUser");

                    Log.d("Check", "check");

                    JSONObject reportingUserObj = obj.getJSONObject("reportingUser");

//                    String reportedUser = reportedUserObj.getString("reportedUser");
//                    String reportingUser = reportingUserObj.getString("reportingUser");
                    String reportedUser = reportedUserObj.getString("username");
                    String reportingUser = reportingUserObj.getString("username");
                    String explanation = obj.getString("explanation");
//                    String message = obj.getString("message");
                    String message = obj.optString("reportedMessage", "No message provided");
                    String timestamp = obj.getString("timestamp");

                    reportList.add(new AdminReport(reportedUser, reportingUser, message, explanation, timestamp));
                }

                adminReportAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                Log.e("GET_REPORTS", "JSON Parsing error: " + e.getMessage());
                e.printStackTrace();
                Toast.makeText(this, "Error parsing reports", Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            Log.e("GET_REPORTS", "Volley error: " + error.toString());
            error.printStackTrace();
            Toast.makeText(this, "Failed to load reports", Toast.LENGTH_SHORT).show();
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(reportRequest);
    }
}
