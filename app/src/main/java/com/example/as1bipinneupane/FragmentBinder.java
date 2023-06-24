package com.example.as1bipinneupane;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class FragmentBinder extends AppCompatActivity {

    BottomNavigationView bottomNavBar;
    private long backPressedTime;
    private Toast toastmsg;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_binder);

        preferences = getSharedPreferences("userLogData", Context.MODE_PRIVATE);
        bottomNavBar = findViewById(R.id.bottomNavBar);

        bottomNavBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                String isUserAdmin = preferences.getString("isAdmin",null);

                if (id == R.id.homeMenu) {
                    loadFragment(new GeneralTimeline(), true);
                    getSupportActionBar().setTitle("General Timeline");
                } else if (id == R.id.adminMenu) {
//                    if the user is admin then it the fragment will replace
                    if(isUserAdmin.equals("1")){
                    loadFragment(new AdminControl(), false);
                    getSupportActionBar().setTitle("Admin");
                    }else {
                        Toast.makeText(FragmentBinder.this, "You are not admin", Toast.LENGTH_SHORT).show();
                    }
                } else if (id == R.id.createMenu) {
                    loadFragment(new CreatePost(), false);
                    getSupportActionBar().setTitle("Create");
                }
                else if (id == R.id.myTimelineMenu) {
                    loadFragment(new MyTimeline(), false);
                    getSupportActionBar().setTitle("My Timeline");
                }
                else if (id == R.id.profileMenu) {
                    loadFragment(new Profile(), false);
                    getSupportActionBar().setTitle("My Profile");
                }

                return true;
            }
        });

        bottomNavBar.setSelectedItemId(R.id.homeMenu);

    }

    public void loadFragment(Fragment fragment, boolean flag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (flag) {
            fragmentTransaction.replace(R.id.fragment_container, fragment);
        } else {
            fragmentTransaction.replace(R.id.fragment_container, fragment);
        }
        fragmentTransaction.commit();
    }

//    to toast message press again to exit
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            toastmsg.cancel();
            super.onBackPressed();
            return;
        } else {
            toastmsg = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            toastmsg.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}

