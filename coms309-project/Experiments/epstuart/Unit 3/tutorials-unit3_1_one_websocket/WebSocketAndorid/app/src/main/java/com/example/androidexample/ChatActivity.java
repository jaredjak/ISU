package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

//import com.example.androidexample.WebSocketManager;
//import com.example.androidexample.WebSocketListener;

import org.java_websocket.handshake.ServerHandshake;

public class ChatActivity extends AppCompatActivity implements WebSocketListener{

    private Button sendBtn;
    private EditText msgEtx;
    private TextView msgTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        /* initialize UI elements */
        sendBtn = (Button) findViewById(R.id.sendBtn);
        msgEtx = (EditText) findViewById(R.id.msgEdt);
        msgTv = (TextView) findViewById(R.id.tx1);

        /* connect this activity to the websocket instance */
        WebSocketManager.getInstance().setWebSocketListener(ChatActivity.this);

        /* send button listener */
        sendBtn.setOnClickListener(v -> {
            try {
                // send message
                WebSocketManager.getInstance().sendMessage(msgEtx.getText().toString());
            } catch (Exception e) {
                Log.d("ExceptionSendMessage:", e.getMessage().toString());
            }
        });
    }


    @Override
    public void onWebSocketMessage(String message) {
        /**
         * In Android, all UI-related operations must be performed on the main UI thread
         * to ensure smooth and responsive user interfaces. The 'runOnUiThread' method
         * is used to post a runnable to the UI thread's message queue, allowing UI updates
         * to occur safely from a background or non-UI thread.
         */
        // runOnUiThread(() -> {
        //     String s = msgTv.getText().toString();
        //     msgTv.setText(s + "\n"+message);
        // });
        runOnUiThread(() -> {
            String s = msgTv.getText().toString();
            msgTv.setText(s + "\n" + message);
    
            // Check if the message contains "Happy Birthday"
            if (message.toLowerCase().contains("happy birthday")) {
                showBalloonAnimation();
            }
        });
    }

    private void showBalloonAnimation() {
        ImageView balloonImage = findViewById(R.id.balloonImage);
        balloonImage.setVisibility(View.VISIBLE);
    
        // Scale up animation
        ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(balloonImage, "scaleX", 1f, 2f);
        ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(balloonImage, "scaleY", 1f, 2f);
    
        // Scale down animation
        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(balloonImage, "scaleX", 2f, 0f);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(balloonImage, "scaleY", 2f, 0f);
    
        // Combine animations
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleUpX).with(scaleUpY);
        animatorSet.play(scaleDownX).with(scaleDownY).after(scaleUpX);
        animatorSet.setDuration(1000);
    
        animatorSet.start();
    
        // Hide the balloon after the animation
        animatorSet.addListener(new android.animation.AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                balloonImage.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        String closedBy = remote ? "server" : "local";
        runOnUiThread(() -> {
            String s = msgTv.getText().toString();
            msgTv.setText(s + "---\nconnection closed by " + closedBy + "\nreason: " + reason);
        });
    }

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {}

    @Override
    public void onWebSocketError(Exception ex) {}
}