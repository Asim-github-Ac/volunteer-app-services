package com.example.vis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.example.vis.SharedPrefrence.PrefManager;

import java.util.ArrayList;
import java.util.List;

public class SplashScreen extends AppCompatActivity {
    PrefManager prefManager;
    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        prefManager = new PrefManager(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (prefManager.getToken_Email().equals("std")) {
                    Intent intent = new Intent(getApplicationContext(), WelcomeStudentActivity.class);
                    startActivity(intent);
                    finish();
                } else if (prefManager.getToken_Email().equals("org")) {
                    Intent intent = new Intent(getApplicationContext(), WelcomeOrganizationActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);
    }

    @Override
    protected void onStart() {
        super.onStart();
       checkPermissions(0);
    }

    private boolean checkPermissions(int type) {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(getApplicationContext(), p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new
                            String[listPermissionsNeeded.size()]), type);
            return false;
        }
    return  true;
    }
}