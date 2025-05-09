package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import android.text.Editable;
import android.graphics.Color;
import android.widget.Toast;


public class SignUpActivity extends AppCompatActivity {

//    private Button btnJsonObjReq;
    private TextView msgResponse;

    private EditText usernameEditText;  // define username edittext variable
    private EditText passwordEditText;  // define password edittext variable
    private EditText confirmEditText;   // define confirm edittext variable
    private EditText emailEditText;
    private EditText ssnEditText;
    private Button loginButton;         // define login button variable
    private Button signupButton;        // define signup button variable
    private Button exitButton;
    private TextView passStrText;
    private TextView userStrText;
    private TextView emailStrText;
    private TextView ssnStrText;

    private static final String SignUp_URL = "http://coms-3090-013.class.las.iastate.edu:8080/users";

//    private static final String URL_JSON_OBJECT = //"http://10.0.2.2:8080/Persons/1";
//            "https://jsonplaceholder.typicode.com/users/1";



// ADD A POST THAT ADDS THE SKINS REPOSITORY ON SIGNUP (CREATED ON SIGNUP - in that function)
// make sure to give the user as a request body!!!!


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        /* initialize UI elements */
        usernameEditText = findViewById(R.id.signup_username_edt);  // link to username edtext in the Signup activity XML
        passwordEditText = findViewById(R.id.signup_password_edt);  // link to password edtext in the Signup activity XML
        confirmEditText = findViewById(R.id.signup_confirm_edt);    // link to confirm edtext in the Signup activity XML
        emailEditText = findViewById(R.id.signup_email_edt);        // link to email edtext in the Signup activity XML
        ssnEditText = findViewById(R.id.signup_ssn_edt);            // link to ssn edtext in the Signup activity XML

        loginButton = findViewById(R.id.signup_login_btn);    // link to login button in the Signup activity XML
        signupButton = findViewById(R.id.signup_signup_btn);  // link to signup button in the Signup activity XML
        exitButton = findViewById(R.id.signup_main_btn);       // ling to main button in the Signup activity XML

        /* Following code checks for username, password, email, and ssn strength  */
        passStrText = findViewById(R.id.passwordStrength); // link to passwordStrength text
        userStrText = findViewById(R.id.usernameStrength);
        emailStrText = findViewById(R.id.emailStrength);
        ssnStrText = findViewById(R.id.ssnStrength);


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

        emailEditText.addTextChangedListener(new TextWatcher() {
            /* added because interface. no real use */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // checkPassStr function on line: 95
                checkEmailStr(s.toString());
            }
            /* added because interface. no real use */
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        ssnEditText.addTextChangedListener(new TextWatcher() {
            /* added because interface. no real use */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // checkPassStr function on line: 95
                checkSsnStr(s.toString());
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
                Intent intent = new Intent(SignUpActivity.this, SignInRequest.class);
                startActivity(intent);  // go to LoginActivity
            }
        });

        /* click listener on exit button pressed */
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        /* click listener on signup button pressed */
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* grab strings from user inputs */
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String email = emailEditText.getText().toString();
                int ssn = 0;
                try {
                    ssn = Integer.parseInt(ssnEditText.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Invalid SSN. Numbers only", Toast.LENGTH_SHORT).show();
                }
                //String ssn = ssnEditText.getText().toString();
                String confirm = confirmEditText.getText().toString();

                /* Added methods to check that username, password, email, and ssn are strong*/
                if (!isValidUser(username)) {
                    Toast.makeText(getApplicationContext(), "Username is not strong enough", Toast.LENGTH_LONG).show();
                }

                else if (!isValidPass(password)) {
                    Toast.makeText(getApplicationContext(), "Password is not strong enough", Toast.LENGTH_LONG).show();
                }

                else if (!isValidEmail(email)) {
                    Toast.makeText(getApplicationContext(), "Email is not strong enough", Toast.LENGTH_LONG).show();
                }

                /* Makes it so that the ssn box only appears after every other field is complete */
                else if (ssnEditText.getHint().toString().equals(" ")) {
                    ssnEditText.setHint("Enter SSN");
                    ssnEditText.setBackground(Drawable.createFromPath("@android:color/transparent"));
                }

                else if (!isValidSsn(ssn)) {
                    Toast.makeText(getApplicationContext(), "SSN is not strong enough", Toast.LENGTH_LONG).show();
                }

                else if (password.equals(confirm)){
                    Toast.makeText(getApplicationContext(), "Signing up", Toast.LENGTH_LONG).show();

                    sendSignUpRequest(username, password, email, ssn);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Making json object request for sign up
     */
    private void sendSignUpRequest(String username, String password, String email, int ssn) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", username);
            jsonBody.put("password", password);
            jsonBody.put("email", email);
            jsonBody.put("ssn", ssn);
        } catch (JSONException e) {
            e.printStackTrace();
            msgResponse.setText("Error creating request data.");
            return;
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,
                SignUp_URL,
                jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Volley Response", response.toString());
                        Toast.makeText(getApplicationContext(), "Signup Successful", Toast.LENGTH_SHORT).show();

                        fetchClubId(username);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                        Toast.makeText(getApplicationContext(), "Signup Failed", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Authorization", "Bearer YOUR_ACCESS_TOKEN");
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);
    }

    private void fetchClubId(String username) {
        String clubIdURL = "http://coms-3090-013.class.las.iastate.edu:8080/users/username/" + username;

        JsonObjectRequest clubIdRequest = new JsonObjectRequest(Request.Method.GET, clubIdURL, null,
                response -> {
                    try {
                        int clubId = response.getInt("clubId");

                        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("username", username);
                        editor.putInt("clubId", clubId);
                        editor.apply();

                        Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
//                        intent.putExtra("username", username);
//                        intent.putExtra("clubId", clubId);
                        intent.putExtra("showAdminDialog", true);
                        startActivity(intent);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(this, "Failed to fetch club id", Toast.LENGTH_SHORT).show();
                });

        Volley.newRequestQueue(this).add(clubIdRequest);
    }

    /* Checks the strength of the password given with the following criteria:
     *  - at least 5 characters long
     *  - contains at least one capital letter
     *  - contains at least one special character */
    private void checkPassStr(String password) {
        //if (password == null) return;

        if (password.isEmpty()) {
            passStrText.setText("");

        }
        else if (password.length() < 5) {
            passStrText.setText("Weak: >5 characters");
            passStrText.setTextColor(Color.parseColor("#ff0000"));
        } else if (!password.matches(".*[A-Z].*")) {
            passStrText.setText("Weak: Add a capital letter");
            passStrText.setTextColor(Color.parseColor("#ff0000"));
        } else if (!password.matches(".*[!@#$%^&*()\\-+=].*")) {
            passStrText.setText("Weak: Add a special character");
            passStrText.setTextColor(Color.parseColor("#ff0000"));
        }
        else {
            passStrText.setText("");
        }
    }

    /* Checks the strength of the username given with the following criteria:
     *  - at least 8 characters long
     *  - contains at least one capital letter
     *  - contains at least one number */
    private void checkUserStr(String user) {
        //if (password == null) return;

        if (user.isEmpty()) {
            userStrText.setText("");
        }
        else if (user.length() < 8) {
            userStrText.setText("Must be at least 8 characters");
            userStrText.setTextColor(Color.parseColor("#ff0000"));
        } else if (!user.matches(".*[A-Z].*")) {
            userStrText.setText("Must contain at least 1 capital letter");
            userStrText.setTextColor(Color.parseColor("#ff0000"));
        } else if (!user.matches(".*[0-9].*")) {
            userStrText.setText("Must contain at least 1 number");
            userStrText.setTextColor(Color.parseColor("#ff0000"));
        }
        else {
            userStrText.setText("");
        }
    }

    /* Checks the strength of the email given with the following criteria:
     *  Is a valid email format
     */
    private void checkEmailStr(String email) {

        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        if (email.isEmpty()) {
            emailStrText.setText("");
        }
        else if (!email.matches(emailPattern)) {
            emailStrText.setText("Not a valid email");
            emailStrText.setTextColor(Color.parseColor("#ff0000"));
        }
        else {
            emailStrText.setText("");
        }
    }

    /* Checks the strength of the ssn given with the following criteria:
     *  Is the correct length of a ssn
     */
    private void checkSsnStr(String ssn) {
        if (ssn.isEmpty()) {
            ssnStrText.setText("");
        }
        else if (!(ssn.length() == 9)) {
            ssnStrText.setText("Not a valid SSN");
            ssnStrText.setTextColor(Color.parseColor("#ff0000"));
        }
        else {
            ssnStrText.setText("");
        }
    }

    /*The four following methods check if the username and valid are valid
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
    private boolean isValidEmail(String email) {
        if (email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            return true;
        }
        return false;
    }
    private boolean isValidSsn(int ssn) {
        if ((ssn >= 100000000) && (ssn <= 999999999)) {
            return true;
        }
        return false;
    }
}