package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// new items imported
import android.widget.TextView;
import android.text.TextWatcher;
import android.text.Editable;
import android.graphics.Color;

public class SignupActivity extends AppCompatActivity {

    private EditText usernameEditText;  // define username edittext variable
    private EditText passwordEditText;  // define password edittext variable
    private EditText confirmEditText;   // define confirm edittext variable
    private Button loginButton;         // define login button variable
    private Button signupButton;        // define signup button variable

    private TextView passStrText;
    private TextView userStrText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        /* initialize UI elements */
        usernameEditText = findViewById(R.id.signup_username_edt);  // link to username edtext in the Signup activity XML
        passwordEditText = findViewById(R.id.signup_password_edt);  // link to password edtext in the Signup activity XML
        confirmEditText = findViewById(R.id.signup_confirm_edt);    // link to confirm edtext in the Signup activity XML
        loginButton = findViewById(R.id.signup_login_btn);    // link to login button in the Signup activity XML
        signupButton = findViewById(R.id.signup_signup_btn);  // link to signup button in the Signup activity XML

        /* Following code checks for username and password strength (lines 41-73 */
        passStrText = findViewById(R.id.passwordStrength); // link to passwordStrength text
        userStrText = findViewById(R.id.usernameStrength);

        passwordEditText.addTextChangedListener(new TextWatcher() {
            /* added because interface. no real use */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // checkPassStr function on line: 95
                checkPassStr(s.toString());
            }
            /* added because interface. no real use */
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        usernameEditText.addTextChangedListener(new TextWatcher() {
            /* added because interface. no real use */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // checkUserStr function on line: 137
                checkUserStr(s.toString());
            }
            /* added because interface. no real use */
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

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

                /* Added methods to check that username and password are strong*/
                if (!isValidUser(username)) {
                    Toast.makeText(getApplicationContext(), "Username is not strong enough", Toast.LENGTH_LONG).show();
                }

                if (!isValidPass(password)) {
                    Toast.makeText(getApplicationContext(), "Password is not strong enough", Toast.LENGTH_LONG).show();
                }

                if (password.equals(confirm)){
                    Toast.makeText(getApplicationContext(), "Signing up", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Password don't match", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /* Checks the strength of the password given with the following criteria:
     *  - at least 5 characters long
     *  - contains at least one capital letter
     *  - contains at least one special character */
    private void checkPassStr(String password) {
        //if (password == null) return;

        if (password.isEmpty()) {
            passStrText.setText("");
            passStrText.setTextColor(Color.parseColor("#ffffff"));
        }

        if (password.length() < 5) {
            passStrText.setText("Weak: >5 characters");
            passStrText.setTextColor(Color.parseColor("#ff0000"));
        } else if (!password.matches(".*[A-Z].*")) {
            passStrText.setText("Weak: Add a capital letter");
            passStrText.setTextColor(Color.parseColor("#ff0000"));
        } else if (!password.matches(".*[!@#$%^&*()\\-+=].*")) {
            passStrText.setText("Weak: Add a special character");
            passStrText.setTextColor(Color.parseColor("#ff0000"));
        } else {
            passStrText.setText("Strong password");
            passStrText.setTextColor(Color.parseColor("#00ff00"));
        }
    }

    /* Checks the strength of the username given with the following criteria:
     *  - at least 8 characters long
     *  - contains at least one capital letter
     *  - contains at least one number */
    private void checkUserStr(String user) {
        //if (password == null) return;

        if (user.length() == 0) {
            userStrText.setText("");
            userStrText.setTextColor(Color.parseColor("#ffffff"));
        }

        if (user.length() < 8) {
            userStrText.setText("Must be at least 8 characters");
            userStrText.setTextColor(Color.parseColor("#ff0000"));
        } else if (!user.matches(".*[A-Z].*")) {
            userStrText.setText("Must contain at least 1 capital letter");
            userStrText.setTextColor(Color.parseColor("#ff0000"));
        } else if (!user.matches(".*[0-9].*")) {
            userStrText.setText("Must contain at least 1 number");
            userStrText.setTextColor(Color.parseColor("#ff0000"));
        } else {
            userStrText.setText("Strong username");
            userStrText.setTextColor(Color.parseColor("#00ff00"));
        }
    }

    /*The two following methods check if the username and valid are valid
    given the criteria.
    */
    private boolean isValidUser(String user) {
        if (user.length()>=8 && user.matches(".*[A-Z].*") && user.matches(".*[0-9].*")) {
            return true;
        }
        return false;
    }

    private boolean isValidPass(String pass) {
        if (pass.length()>=5 && pass.matches(".*[A-Z].*") && pass.matches(".*[!@#$%^&*()\\-+=].*")) {
            return true;
        }
        return false;
    }
}