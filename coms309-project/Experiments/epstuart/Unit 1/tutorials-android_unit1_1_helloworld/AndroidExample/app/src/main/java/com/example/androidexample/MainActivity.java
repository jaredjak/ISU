package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView messageText;   // define message textview variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);             // link to Main activity XML

        /* initialize UI elements */
        messageText = findViewById(R.id.main_msg_txt);      // link to message textview in the Main activity XML
        messageText.setText("Hello World, finally got something to show!!!");

        Button changeColorButton = findViewById(R.id.change_color_button);
        changeColorButton.setOnClickListener(new View.OnClickListener() {
            private boolean isRed = false;

            @Override
            public void onClick(View v) {
                View view = findViewById(R.id.change_color_button).getRootView();
                if (isRed) {
                    view.setBackgroundColor(getResources().getColor(android.R.color.white));
                } else {
                    view.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                }
                isRed = !isRed;
            }
        });
    }
}