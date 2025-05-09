package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;
import android.graphics.Color;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView messageText;   // define message textview variable
    private Button button;      // define button

    private final String[] msg = {
            "Just kidding",
            "No game here",
            "Error 404",
            "Where game",
            "Try again",
            "You misclicked"
    };

    private int[] colors = {Color.RED, Color.BLUE, Color.GREEN};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);             // link to Main activity XML

        /* initialize UI elements */
        messageText = findViewById(R.id.main_msg_txt);      // link to message textview in the Main activity XML
        messageText.setText("Insatiable Insatiable Inchworms");

        button = findViewById(R.id.button);
        button.setOnClickListener(v -> updateMessage());
    }

    private void updateMessage() {
        Random random = new Random();

        int randomIndex = random.nextInt(msg.length);
        int randomColor = random.nextInt(colors.length);

        //applies new msg and color
        messageText.setText(msg[randomIndex]);
        messageText.setTextColor(colors[randomColor]);
    }
}