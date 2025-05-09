package com.example.androidexample;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClubFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClubFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;


    /* Class variables */
    private RecyclerView recyclerChat;
    private RecyclerView recyclerJoin;
    private TextView clubName;
    private TextView chatText;
    private EditText chatMessage;
    private Button sendBtn;
    private Button joinBtn;
    private Button leaveBtn;
    private Button memberBtn;
    private Button playClubGameButton;
    private RequestQueue requestQueue;
    private WebSocketClient webSocketClient;
    private List<ChatMessage> chatMessages;
    private ChatAdapter chatAdapter;
    private AlertDialog joinDialog;
    private String username;
    private int clubId;

    private List<String> clubMembers;
    private int clubCount;


    /**
     * A basic empty Club Fragment constructor
     */
    public ClubFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * Sets the ClubFragment and gets the arguments that will be used throughout.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClubFragment.
     */
    public static ClubFragment newInstance(String param1, String param2) {
        ClubFragment fragment = new ClubFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Gets the savedInstanceState as well as sets up the RequestQueue that will be used for requests within the class.
     *
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(requireContext());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /**
     * Sets up all of the UI elements for the screen view.
     * Contains various click listeners for user interaction with the screen
     * <p>
     * - joining a club
     * </p>
     * <p>
     * - leaving a club
     * </p>
     * <p>
     * - sending a message in a club
     * </p>
     *
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_club, container, false);

        // Fetch the username and clubId
        SharedPreferences prefs = getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        username = prefs.getString("username", null);
        clubId = prefs.getInt("clubId", 0);

        // Initialization for class variables
        recyclerChat = view.findViewById(R.id.chat_msg);
        clubName = view.findViewById(R.id.club_name);
        chatText = view.findViewById(R.id.chat_tv);
        chatMessage = view.findViewById(R.id.chat_input);
        sendBtn = view.findViewById(R.id.send_btn);
        joinBtn = view.findViewById(R.id.club_btn);
        leaveBtn = view.findViewById(R.id.leave_btn);
        memberBtn = view.findViewById(R.id.members_btn);
        playClubGameButton = view.findViewById(R.id.clubgame_btn);

        clubMembers = new ArrayList<>();
        clubCount = 0;

        // Set up recyclerChat and chatAdapter
        recyclerChat.setLayoutManager(new LinearLayoutManager(getContext()));
        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(requireContext(), chatMessages, username);
        recyclerChat.setAdapter(chatAdapter);

        Log.d("clubId", String.valueOf(clubId)); // Debug to check clubId

        if (clubId == 0) {
            // User not in a club yet
            setChatVisibility(false);
        } else {
            // User is in a club
            fetchClubUsername(clubId);
        }

        /* Click listener for join button */
        joinBtn.setOnClickListener(v -> showJoinClubDialog());

        /* Click listener for leave button */
        leaveBtn.setOnClickListener(v -> leaveClub(clubName.getText().toString().trim(), username));

        /* Click listener for send button */
        sendBtn.setOnClickListener(v -> sendMessage());

        /* Click listener for members button */
        memberBtn.setOnClickListener(v -> showMemberClubDialog());

        playClubGameButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ClubLobbyActivity.class);
            intent.putExtra("clubId", clubId);
            intent.putExtra("username", username);
            startActivity(intent);
        });
        
        return view;
    }

    /**
     * Sets the visibility of the given UI elements to true or false depending on the method call to it.
     * @param isVisible
     */
    private void setChatVisibility(boolean isVisible) {
        int visibility = isVisible ? View.VISIBLE : View.GONE;
        recyclerChat.setVisibility(visibility);
        clubName.setVisibility(visibility);
        chatText.setVisibility(visibility);
        chatMessage.setVisibility(visibility);
        sendBtn.setVisibility(visibility);
        leaveBtn.setVisibility(visibility);
        memberBtn.setVisibility(visibility);
        playClubGameButton.setVisibility(visibility);
    }

    /**
     * Pulls up a dialog view of the members currently within the club as well as provides a member count.
     */
    private void showMemberClubDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setTitle("Club Members");

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_member_list, null);
        builder.setView(dialogView);
        builder.setCancelable(false);
        AlertDialog membersDialog = builder.create();

        TextView memberCountText = dialogView.findViewById(R.id.member_count);
        RecyclerView membersRecyclerView = dialogView.findViewById(R.id.memberClub);

        // Set member count
        memberCountText.setText("Member Count: " + clubCount);

        // Set up RecyclerView
        membersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        membersRecyclerView.setAdapter(new ClubMembersAdapter(clubMembers));

//        builder.setNegativeButton("Exit", (dialog, which) -> dialog.dismiss());

        Button memberExit = dialogView.findViewById(R.id.member_exit_btn);
        memberExit.setOnClickListener(v -> membersDialog.dismiss());


        membersDialog.show();
    }

    /**
     * Populates the screen with a dialog view box for joining a new club.
     * Contains two options where the user can either join an existing club or create their own.
     */
    private void showJoinClubDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setTitle("Join a Club");

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_join_club, null);
        builder.setView(dialogView);

        recyclerJoin = dialogView.findViewById(R.id.joinClubs);
        recyclerJoin.setLayoutManager(new LinearLayoutManager(getContext()));

        getAllClubs();

        // Button for creating club
        Button createClub = dialogView.findViewById(R.id.btnCreateClub);
        /* Click listener for create button */
        createClub.setOnClickListener(v -> showCreateClubDialog(username));

//        builder.setNegativeButton("Exit", (dialog, which) -> dialog.dismiss());

        // Show dialog
        joinDialog = builder.create();


        Button exitButton = dialogView.findViewById(R.id.join_exit_btn);
        exitButton.setOnClickListener(v -> joinDialog.dismiss());

        joinDialog.show();
    }

    /**
     * Populates the screen with a dialog view box for creating a new club.
     * @param user_name
     */
    private void showCreateClubDialog(String user_name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setTitle("Create a Club");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_create_club, null);
        builder.setView(dialogView);

        EditText newClubName = dialogView.findViewById(R.id.new_club_name);
        Button createButton = dialogView.findViewById(R.id.create_btn);

//        builder.setNegativeButton("Exit", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();

        Button exitButton = dialogView.findViewById(R.id.create_exit_btn);
        exitButton.setOnClickListener(v -> dialog.dismiss());

        /* Click listener for create button - creates a new club */
        createButton.setOnClickListener(v -> {
            String clubName = newClubName.getText().toString().trim();
            if (!clubName.isEmpty()) {
                createClub(clubName, user_name);
                dialog.dismiss();
                joinDialog.dismiss();
            } else {
                Toast.makeText(getContext(), "Enter a club name", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    /**
     * Joins a club with the given club id by making a PUT request to join.
     * @param club_name
     * @param club_id
     * @param user_name
     */
    private void joinClub(String club_name, int club_id, String user_name) {
        String joinURL = "http://coms-3090-013.class.las.iastate.edu:8080/club/addUser/" + club_id;

        // DEBUG statement to check that username is not null
        Log.d("Joining", "Sending username: '" + user_name + "'");

        StringRequest request = new StringRequest(Request.Method.PUT, joinURL,
                response -> {
                    /* Goes to a new method where the user's club id is applied */
                    fetchUpdatedClubId(user_name, club_name);

                    if (joinDialog != null && joinDialog.isShowing()) {
                        joinDialog.dismiss();  // Dismiss the dialog
                    }
                },
                error -> {
                    Log.e("API error", "Error joining: " + error.toString());
                    Toast.makeText(getContext(), "Error joining", Toast.LENGTH_SHORT).show();
                }) {

            @Override
            public byte[] getBody() {
                String requestBody = user_name;
                return requestBody.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        requestQueue.add(request);
    }

    /**
     * Gets the user's updated club id.
     * Simultaneously connects the user to their new club.
     * This is used when the user is in the process of joining a club.
     * @param user_name
     * @param club_name
     */
    private void fetchUpdatedClubId(String user_name, String club_name) {
        String clubidurl = "http://coms-3090-013.class.las.iastate.edu:8080/users/username/" + user_name;

        JsonObjectRequest clubIdRequest = new JsonObjectRequest(Request.Method.GET, clubidurl, null,
                response -> {
                    try {
                        int newClubId = response.getInt("clubId");

                        Toast.makeText(getContext(), "Joined " + club_name, Toast.LENGTH_SHORT).show();

                        // General set up when in club
                        clubName.setText(club_name);
                        setChatVisibility(true);
                        joinBtn.setVisibility(View.GONE);

                        SharedPreferences prefs = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt("clubId", newClubId);
                        clubId = newClubId;
                        editor.apply();

                        // Connect the Websocket with the new chat
                        connectWebSocket(newClubId, user_name);

                        // reload the fragment
                        reloadFragment();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.e("fetchUpdatedClubId", "Error fetching user details: " + error.toString());
                });

        Volley.newRequestQueue(requireContext()).add(clubIdRequest);
    }

    /**
     * Gets the club information and displays it given the club id.
     * This is used when a user is already in a club so that the information can be populated properly.
     * @param clubId
     */
    private void fetchClubUsername(int clubId) {
        String nameURL = "http://coms-3090-013.class.las.iastate.edu:8080/club/get/" + clubId;

        JsonObjectRequest clubIdRequest = new JsonObjectRequest(Request.Method.GET, nameURL, null,
                response -> {
                    Log.d("Club information", "Response: " + response);
                    try {
                        String club_name = response.getString("clubName");

                        clubCount = response.getInt("memberCount");
                        JSONArray users = response.getJSONArray("users");

                        for (int i = 0; i < users.length(); i++) {
                            clubMembers.add(users.getString(i));
                        }

                        // General setup when in club
                        clubName.setText(club_name);
                        setChatVisibility(true);
                        joinBtn.setVisibility(View.GONE);

                        // Connect the Websocket with the new chat
                        connectWebSocket(clubId, username);

                        // Reload the fragment
                        reloadFragment();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.e("fetchUpdatedClubId", "Error fetching club details: " + error.toString());
                });

        Volley.newRequestQueue(requireContext()).add(clubIdRequest);
    }

    /**
     * Gets a list of all of the existing clubs.
     * These clubs are then displayed in the Dialog view for joining a club.
     */
    private void getAllClubs() {
        String clubsURL = "http://coms-3090-013.class.las.iastate.edu:8080/club/get/getAll";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, clubsURL, null, response -> {
            // Logs the response given from the request
            Log.d("API_RESPONSE", "Full Response: " + response.toString());

            List<ClubInfo> clubList = new ArrayList<>();

            try {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject clubObject = response.getJSONObject(i);
                    String club_name = clubObject.getString("clubName");
                    int club_id = clubObject.getInt("id");
                    clubList.add(new ClubInfo(club_id, club_name));
                }

                ClubAdapter adapter = new ClubAdapter(clubList, username, this::joinClub);
                recyclerJoin.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        },
                error -> {
                    Log.e("VolleyError", "Error fetching club data: " + error.toString());
                });

        requestQueue.add(request);
    }

    /**
     * Creates a club with the given inputted club name.
     * It then makes a call to proceed with adding the user to their new club.
     * @param newClubName
     * @param user_name
     */
    private void createClub(String newClubName, String user_name) {
        String createURL = "http://coms-3090-013.class.las.iastate.edu:8080/club/create/" + newClubName;

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("username", user_name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, createURL, requestBody,
                response -> {
                    fetchUpdatedClubId(user_name, newClubName);
                },
                error -> {
                    Log.e("API error", "Error creating: " + error.toString());
                    Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                });

        requestQueue.add(request);
    }

    /**
     * Leaves the club the user is currently in.
     * Disconnects from the clubs websocket and hides certain UI features.
     * @param club_name
     * @param user_name
     */
    private void leaveClub(String club_name, String user_name) {
        String leaveURL = "http://coms-3090-013.class.las.iastate.edu:8080/club/deleteUser/" + clubId;

        StringRequest request = new StringRequest(Request.Method.PUT, leaveURL,
                response -> {
                    // Sets certain UI features to be invisible/visible
                    setChatVisibility(false);
                    joinBtn.setVisibility(View.VISIBLE);

                    SharedPreferences prefs = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("clubId", 0);
                    clubId = 0;
                    editor.apply();

                    disconnectWebSocket();
                },
                error -> {
                    Toast.makeText(getContext(), "Failed to leave", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }) {
            @Override
            public byte[] getBody() {
                // Send just the username string in quotes
                String requestBody = user_name;
                return requestBody.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        requestQueue.add(request);
    }

    /**
     * Sends the message that the user inputted in the text field for chatting.
     * The message is sent to the websocket as well as outputted into it.
     */
    private void sendMessage() {
        String message = chatMessage.getText().toString().trim();
        if (!message.isEmpty()) {
            webSocketClient.send(message);
            chatMessage.setText("");
        }
    }

    /* Reloads the fragment to ensure that all UI elements are updated properly */

    /**
     * Reloads the fragment so as to ensure that all of the UI elements within the current view are updated properly.
     */
    private void reloadFragment() {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    /**
     * Connects the current user with the websocket of the given club.
     * This will display all of the messages sent within the club since its creation.
     * @param clubId
     * @param user_name
     */
    private void connectWebSocket(int clubId, String user_name) {
        URI uri;
        try {
            uri = new URI("ws://coms-3090-013.class.las.iastate.edu:8080/chat/" + clubId + "/" + user_name);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                Log.d("WebSocket", "Connected");
            }

            @Override
            public void onMessage(String message) {
                getActivity().runOnUiThread(() -> {
//                    try {
//                        Log.d("WebSocket", "Received message: " + message);
//
//                        ChatMessage chatMessage;
//
//                        if (message.trim().startsWith("{")) {
//                            Log.d("WebSocket", "Message looks like JSON");
//
//                            JSONObject json = new JSONObject(message);
//                            int id = json.optInt("id", -1);
//                            String text = json.optString("message", "");
//                            String sender = json.optString("senderUsername", "Unknown");
//                            String timestamp = json.optString("timestamp", "");
//
//                            chatMessage = new ChatMessage(text, sender, timestamp, id);
//                            Log.d("WebSocket", "Parsed JSON message: " + chatMessage);
//                        } else {
//                            Log.w("WebSocket", "Received unstructured message. Attempting manual parse.");
//
//                            String[] parts = message.split(":", 2);
//                            if (parts.length == 2) {
//                                String sender = parts[0].trim();
//                                String text = parts[1].trim();
//
//                                chatMessage = new ChatMessage(text, sender, "", -1);
//                                Log.d("WebSocket", "Parsed fallback message: sender=" + sender + ", message=" + text);
//                            } else {
//                                Log.e("WebSocket", "Malformed fallback message, could not split by ':'");
//                                return; // EXIT EARLY – this skips adding to chatMessages
//                            }
//                        }
//
//                        chatMessages.add(chatMessage);
//                        Log.d("WebSocket", "Added message to list. Size: " + chatMessages.size());
//
//
//                        chatAdapter.notifyItemInserted(chatMessages.size() - 1);
//                        recyclerChat.scrollToPosition(chatAdapter.getItemCount() - 1);
//                        Log.d("WebSocket", "UI updated with new message.");
//
//                    } catch (JSONException e) {
//                        Log.e("WebSocket", "Failed to parse message: " + e.getMessage());
//                        e.printStackTrace();
//                    }


                    ChatMessage chatMessage;
                    try {
                        Log.d("WebSocket", "Received message: " + message);
                        // This doesn't actually work after reading through the backend code. Will need to receive the id somehow..
                        if (message.trim().startsWith("{")) {
                            JSONObject json = new JSONObject(message);
                            int id = json.has("id") ? json.getInt("id") : -1;
                            String text = json.getString("message");
                            String sender = json.getString("senderUsername");
                            String timestamp = json.optString("timestamp", "");

                            chatMessage = new ChatMessage(text, sender, timestamp, id);
                        } else {
                            Log.w("WebSocket", "Received unstructured message. Attempting manual parse.");
                            String[] parts = message.split(":", 2); // Only split on first colon
                            String sender = parts.length > 1 ? parts[0].trim() : "Unknown";
                            String text = parts.length > 1 ? parts[1].trim() : message.trim();

                            Log.d("WebSocket", "Parsed fallback message: sender=" + sender + ", message=" + text);

                            chatMessage = new ChatMessage(text, sender, "", -1);
                        }

                        chatMessages.add(chatMessage);
                        Log.d("WebSocket", "Added message to list. Size: " + chatMessages.size());


                        chatAdapter.notifyItemInserted(chatMessages.size() - 1);
                        recyclerChat.scrollToPosition(chatAdapter.getItemCount() - 1);
                        Log.d("WebSocket", "UI updated with new message.");

                    } catch (JSONException e) {
                        Log.e("WebSocket", "Failed to parse message: " + e.getMessage());
                        e.printStackTrace();
                    }



//                        try {
//                            Log.d("WebSocket", "Received message: " + message);
//
//                            ChatMessage chatMessage;
//
//                            // Check if the message starts with "{" (likely JSON)
//                            if (message.trim().startsWith("{")) {
//                                JSONObject json = new JSONObject(message);
//                                int id = json.has("id") ? json.getInt("id") : -1;
//                                String text = json.getString("message");
//                                String sender = json.getString("senderUsername");
//                                String timestamp = json.optString("timestamp", "");
//
//
//
//                                chatMessage = new ChatMessage(text, sender, timestamp, id);
//                            } else {
//                                // Plain text fallback
//                                chatMessage = new ChatMessage(message.trim(), "Unknown", "", -1);
//
//                            }
//
//                            chatMessages.add(chatMessage);
//                            chatAdapter.notifyItemInserted(chatMessages.size() - 1);
//                            recyclerChat.post(() -> recyclerChat.scrollToPosition(chatAdapter.getItemCount() - 1));
//
//                            Log.d("WebSocket", "Message added. Total now: " + chatMessages.size());
//
//                        } catch (JSONException e) {
//                            Log.e("WebSocket", "Failed to parse message: " + e.getMessage());
//                            e.printStackTrace();
//                        }


                });
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.d("WebSocket", "Disconnected: " + reason);
            }

            @Override
            public void onError(Exception ex) {
                Log.d("Websocket", "Error: " + ex.getMessage());
            }
        };

        webSocketClient.connect();
    }

    /**
     * Disconnects the websocket whenever the user leaves the club.
     */
    private void disconnectWebSocket() {
        if (webSocketClient != null) {
            webSocketClient.close();
            webSocketClient = null;
            Log.d("WebSocket", "WebSocket disconnected");
        }
    }
}