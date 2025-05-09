package com.example.androidexample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import android.view.MenuItem;

public class HomeActivity extends AppCompatActivity{

    private ImageButton settingsButton;

    //private Button leaderboardButton; // Temp button to go to leaderboard
    private ImageButton profileButton;
    BottomNavigationView bottomNavView;
    Fragment selectedFragment;

    FloatingActionButton globalBtn;
    FloatingActionButton achieveBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        globalBtn = findViewById(R.id.global_chat_btn);

        settingsButton = findViewById(R.id.settings_btn);
        //leaderboardButton = findViewById(R.id.leaderboard_btn);
        profileButton = findViewById(R.id.profile_btn);

        achieveBtn = findViewById(R.id.achievement_btn);

        /* Setup for the bottom navigation thingy */
        bottomNavView = findViewById(R.id.bottom_navigation);
        bottomNavView.setOnNavigationItemSelectedListener(navListener);

        /* Setup for eventual admin dialog box. Only displays after sign up */
        boolean shouldShowAdminDialog = getIntent().getBooleanExtra("showAdminDialog", false);
        Bundle bundle = new Bundle();
        bundle.putBoolean("showAdminDialog", shouldShowAdminDialog);

        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);

        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();

        /* click listener on settings button pressed */
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });


        /* Feel free to remove before Demo 2, @epstuart needed to test */
//        leaderboardButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, LeaderboardActivity.class);
//                startActivity(intent);
//            }
//        });

        /* click listener for the profile button */
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, UpdateProfileRequest.class);
                startActivity(intent);
            }
        });

        /* click listener for the global chat button */
        globalBtn.setOnClickListener(v -> {
            GlobalChatFragment globalChatFragment = new GlobalChatFragment();
            globalChatFragment.show(getSupportFragmentManager(), "GlobalChat");
        });

        /* click listener for the achievements button */
        achieveBtn.setOnClickListener(v -> {
            AchievementsFragment achieveFragment = new AchievementsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, achieveFragment).commit();
        });
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        selectedFragment = null;
        int itemId = item.getItemId();

//        String username = getIntent().getStringExtra("username");
//        int clubId = getIntent().getIntExtra("clubId", 0);

        // Now used for user data storage instead of intent
//        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
//        String username = prefs.getString("username", null);
//        int clubId = prefs.getInt("clubId", 0);

        if (itemId == R.id.nav_home) {
            selectedFragment = new HomeFragment();
        } else if (itemId == R.id.nav_lb) {
            selectedFragment = new LeaderboardFragment();
        } else if (itemId == R.id.nav_mp) {
            selectedFragment = new MarketplaceFragment();
        } else if (itemId == R.id.nav_skins) {
            selectedFragment = new SkinsFragment();
        } else if (itemId == R.id.nav_club) {
            selectedFragment = new ClubFragment();
        }

//        Bundle bundle = new Bundle();
//        bundle.putString("username", username);
//        bundle.putInt("clubId", clubId);
//        selectedFragment.setArguments(bundle);

        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        }

        return true;
    };
}
