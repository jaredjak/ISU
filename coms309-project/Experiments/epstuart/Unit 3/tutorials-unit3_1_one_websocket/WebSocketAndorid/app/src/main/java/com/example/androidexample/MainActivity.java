package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

//import com.example.androidexample.WebSocketManager;
//import com.example.androidexample.WebSocketListener;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity implements WebSocketListener{

    private Button connectBtn;
    private EditText serverEtx, usernameEtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* initialize UI elements */
        connectBtn = (Button) findViewById(R.id.connectBtn);
        serverEtx = (EditText) findViewById(R.id.serverEdt);
        usernameEtx = (EditText) findViewById(R.id.unameEdt);

        /* connect button listener */
        connectBtn.setOnClickListener(view -> {
            String serverUrl = serverEtx.getText().toString() + usernameEtx.getText().toString();
            //String serverUrl = "ws://10.0.2.2:8080/chat/";

            // Establish WebSocket connection and set listener
            WebSocketManager.getInstance().connectWebSocket(serverUrl);
            WebSocketManager.getInstance().setWebSocketListener(MainActivity.this);

            // got to chat activity
            Intent intent = new Intent(this, ChatActivity.class);
            startActivity(intent);
        });
    }


    @Override
    public void onWebSocketMessage(String message) {}

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {}

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {}

    @Override
    public void onWebSocketError(Exception ex) {}

//    private void connectWebSocket() {
//        URI uri;
//        try {
//            uri = new URI("ws://echo.websocket.org");
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//            return;
//        }
//
//        mWebSocketClient = new WebSocketClient(uri) {
//            @Override
//            public void onOpen(ServerHandshake serverHandshake) {
//                // WebSocket connection opened
//                Log.i("Websocket", "Opened");
//            }
//
//            @Override
//            public void onMessage(String s) {
//                // Message recieved from server
//                Log.i("Websocket", "Message Recieved");
//                mOutput.append("\n" + msg);
//            }
//
//            @Override
//            public void onClose(int i, String s, boolean b) {
//                // WebSocket connection closed
//                Log.i("Websocket", "Closed" + reason);
//            }
//
//            @Override
//            public void onError(Exception e) {
//                // Error occured
//                Log.i("Websocket", "Error" + e.getMessage());
//            }
//        };
//        mWebSocketClient.connect();
//
//    }
}