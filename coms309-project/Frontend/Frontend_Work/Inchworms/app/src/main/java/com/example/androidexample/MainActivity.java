package com.example.androidexample;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button signInBtn, signUpBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signInBtn = findViewById(R.id.btnSignIn);
        signUpBtn = findViewById(R.id.btnSignUpRequest);

        /* button click listeners */
        signInBtn.setOnClickListener(this);
        signUpBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnSignIn) {
            startActivity(new Intent(MainActivity.this, SignInRequest.class));
        } else if (id == R.id.btnSignUpRequest) {
            startActivity(new Intent(MainActivity.this, SignUpActivity.class));
        }
    }
}