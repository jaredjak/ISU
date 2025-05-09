package com.example.androidexample;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class GlobalChatFragment extends DialogFragment {

        private RecyclerView recyclerChat;
        private EditText inputMessage;
        private Button sendButton;
        private Button exitButton;

        private ChatAdapter chatAdapter;
        private List<ChatMessage> chatMessages = new ArrayList<>();

        private WebSocketClient webSocketClient;

        private String username;

        public GlobalChatFragment(String username) {
            this.username = username;
        }

        public GlobalChatFragment() {
            // Default constructor
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater,
                                 @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_global_chat, container, false);

            // Fetch the username and clubId
            SharedPreferences prefs = getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
            username = prefs.getString("username", null);

            recyclerChat = view.findViewById(R.id.globalChatRecyclerView);
            inputMessage = view.findViewById(R.id.globalInputMessage);
            sendButton = view.findViewById(R.id.globalSendButton);
            exitButton = view.findViewById(R.id.globalExitButton);

            chatAdapter = new ChatAdapter(getContext(), chatMessages, username);
            recyclerChat.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerChat.setAdapter(chatAdapter);

            connectWebSocket(username);

            sendButton.setOnClickListener(v -> {
                String message = inputMessage.getText().toString().trim();
                if (!message.isEmpty() && webSocketClient != null) {
                    webSocketClient.send(message);
                    inputMessage.setText("");
                }
            });

            exitButton.setOnClickListener(v -> {
                disconnectWebSocket();
                dismiss();
            });

            return view;
        }

        @Override
        public void onStart() {
            super.onStart();

            Dialog dialog = getDialog();
            if (dialog != null && dialog.getWindow() != null) {
                // Get the screen height
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int screenHeight = displayMetrics.heightPixels;

                dialog.getWindow().setLayout(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        screenHeight/2
//                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
            }
        }


        private void connectWebSocket(String user_name) {
            URI uri;
            try {
                uri = new URI("ws://coms-3090-013.class.las.iastate.edu:8080/chat/" + user_name);
            } catch (URISyntaxException e) {
                e.printStackTrace();
                return;
            }

            webSocketClient = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    Log.d("WebSocket", "Connected to global chat");
                }

                @Override
                public void onMessage(String message) {
                    requireActivity().runOnUiThread(() -> {

                        ChatMessage chatMessage;
                        try {
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
//                            Log.d("WebSocket", "Received global message: " + message);
//
//                            ChatMessage chatMessage;
//
//                            if (message.trim().startsWith("{")) {
//                                JSONObject json = new JSONObject(message);
//                                int id = json.has("id") ? json.getInt("id") : -1;
//                                String text = json.getString("message");
//                                String sender = json.getString("senderUsername");
//                                String timestamp = json.optString("timestamp", "");
//
//                                chatMessage = new ChatMessage(text, sender, timestamp, id);
//                            } else {
//                                chatMessage = new ChatMessage(message.trim(), "Unknown", "", -1);
//                            }
//
//                            chatMessages.add(chatMessage);
//                            chatAdapter.notifyItemInserted(chatMessages.size() - 1);
//                            recyclerChat.post(() -> recyclerChat.scrollToPosition(chatAdapter.getItemCount() - 1));
//
//                        } catch (JSONException e) {
//                            Log.e("WebSocket", "Failed to parse global message: " + e.getMessage());
//                            e.printStackTrace();
//                        }


                    });
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("WebSocket", "Global chat disconnected: " + reason);
                }

                @Override
                public void onError(Exception ex) {
                    Log.d("WebSocket", "Global chat error: " + ex.getMessage());
                }
            };

            webSocketClient.connect();
        }

        private void disconnectWebSocket() {
            if (webSocketClient != null) {
                webSocketClient.close();
                webSocketClient = null;
                Log.d("WebSocket", "Global chat WebSocket disconnected");
            }
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            disconnectWebSocket();
        }
}
