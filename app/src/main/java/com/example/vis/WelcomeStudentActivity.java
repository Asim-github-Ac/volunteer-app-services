package com.example.vis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.vis.SharedPrefrence.PrefManager;
import com.example.vis.Student.StudentTimer;
import com.example.vis.Student.SubmitReport;
import com.example.vis.databinding.ActivityWelcomeStudentBinding;
import com.google.android.material.navigation.NavigationView;


public class WelcomeStudentActivity extends AppCompatActivity {
    private ActivityWelcomeStudentBinding mActivityBinding;
    PrefManager prefManager;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityBinding = ActivityWelcomeStudentBinding.inflate(getLayoutInflater());
        setContentView(mActivityBinding.getRoot());
        prefManager=new PrefManager(this);
        setSupportActionBar(mActivityBinding.topLogo);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mActivityBinding.drawerstd, R.string.nav_open, R.string.nav_close);
        mActivityBinding.drawerstd.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mActivityBinding.husnatkhalid.setText(prefManager.getUserName());
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
    mActivityBinding.pdfupload.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), SubmitReport.class));
        }
    });
    mActivityBinding.navviewstd.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.addloc:
                    Intent intent=new Intent(getApplicationContext(), StudentTimer.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            switch (item.getItemId()){
                case R.id.addloc:
                    Intent intent=new Intent(getApplicationContext(),WelcomeStudentActivity.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}