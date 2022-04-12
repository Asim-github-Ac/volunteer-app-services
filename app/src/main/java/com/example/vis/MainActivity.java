package com.example.vis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.vis.databinding.ActivityLoginBinding;
import com.example.vis.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mActivityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mActivityBinding.getRoot());

        mActivityBinding.studentPortalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoLogin("student");
            }
        });
        mActivityBinding.adminPortalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoLogin("admin");

            }
        });
        mActivityBinding.organizationPortalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoLogin("org");
            }
        });

    }
    private void gotoLogin(String role){
        Intent intentObj = new Intent(MainActivity.this, LoginActivity.class);
        intentObj.putExtra("Role", role);
        startActivity(intentObj);
    }
}