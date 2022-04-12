package com.example.vis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.vis.SharedPrefrence.PrefManager;

public class SplashScreen extends AppCompatActivity {
PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        prefManager=new PrefManager(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    if (prefManager.getToken_Email().equals("std")){
                        Intent intent = new Intent(getApplicationContext(),WelcomeStudentActivity.class);
                        startActivity(intent);
                        finish();
                    }else if (prefManager.getToken_Email().equals("org")){
                        Intent intent = new Intent(getApplicationContext(),WelcomeOrganizationActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },3000);
    }
}