package com.example.vis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.vis.SharedPrefrence.PrefManager;
import com.example.vis.databinding.ActivityWelcomeStudentBinding;

public class WelcomeStudentActivity extends AppCompatActivity {
    private ActivityWelcomeStudentBinding mActivityBinding;
    PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityBinding = ActivityWelcomeStudentBinding.inflate(getLayoutInflater());
        setContentView(mActivityBinding.getRoot());
        prefManager=new PrefManager(this);
        mActivityBinding.topLogo.inflateMenu(R.menu.orgmenu);
        mActivityBinding.studentBtnProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentObj = new Intent(WelcomeStudentActivity.this, AllProjectsActivity.class);
                startActivity(intentObj);
            }
        });
        mActivityBinding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeStudentActivity.this,MainActivity.class));
                finish();
                prefManager.setToken_Email("");
            }
        });
        mActivityBinding.viewproject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(WelcomeStudentActivity.this,ViewProjects.class));
            }
        });

    }
}