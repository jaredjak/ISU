package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    private EditText usernameEditText;  // define username edittext variable
    private EditText passwordEditText;  // define password edittext variable
    private EditText confirmEditText;   // define confirm edittext variable
    private EditText phoneEditText;     // define phone edittext variable
    private SeekBar phoneSlider1;       // define phone slider1 variable
    private SeekBar phoneSlider2;       // define phone slider2 variable
    private SeekBar phoneSlider3;       // define phone slider3 variable
    private Button loginButton;         // define login button variable
    private Button signupButton;        // define signup button variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        /* initialize UI elements */
        usernameEditText = findViewById(R.id.signup_username_edt);  // link to username edtext in the Signup activity XML
        passwordEditText = findViewById(R.id.signup_password_edt);  // link to password edtext in the Signup activity XML
        confirmEditText = findViewById(R.id.signup_confirm_edt);    // link to confirm edtext in the Signup activity XML
        phoneEditText = findViewById(R.id.signup_phone_edt);        // link to phone edittext in the Signup activity XML
        phoneSlider1 = findViewById(R.id.signup_phone_slider1);     // link to phone slider1 in the Signup activity XML
        phoneSlider2 = findViewById(R.id.signup_phone_slider2);     // link to phone slider2 in the Signup activity XML
        phoneSlider3 = findViewById(R.id.signup_phone_slider3);     // link to phone slider3 in the Signup activity XML
        loginButton = findViewById(R.id.signup_login_btn);          // link to login button in the Signup activity XML
        signupButton = findViewById(R.id.signup_signup_btn);        // link to signup button in the Signup activity XML

        SeekBar.OnSeekBarChangeListener phoneSliderListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int part1 = phoneSlider1.getProgress();
                int part2 = phoneSlider2.getProgress();
                int part3 = phoneSlider3.getProgress();
                String phoneNumber = String.format("(%03d)-%03d-%04d", part1, part2, part3);
                phoneEditText.setText(phoneNumber);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }
        };

        phoneSlider1.setOnSeekBarChangeListener(phoneSliderListener);
        phoneSlider2.setOnSeekBarChangeListener(phoneSliderListener);
        phoneSlider3.setOnSeekBarChangeListener(phoneSliderListener);

        /* click listener on login button pressed */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* when login button is pressed, use intent to switch to Login Activity */
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);  // go to LoginActivity
            }
        });

        /* click listener on signup button pressed */
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* grab strings from user inputs */
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirm = confirmEditText.getText().toString();
                String phone = phoneEditText.getText().toString();

                if (password.equals(confirm)){
                    Toast.makeText(getApplicationContext(), "Signing up", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Password don't match", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}