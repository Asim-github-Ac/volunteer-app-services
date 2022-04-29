package com.example.vis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.vis.Attendance.Attendance_View;
import com.example.vis.SharedPrefrence.PrefManager;
import com.example.vis.Student.ViewOrgProject;
import com.example.vis.databinding.ActivityWelcomeAdminBinding;
import com.example.vis.databinding.ActivityWelcomeOrganizationBinding;

public class WelcomeAdminActivity extends AppCompatActivity {

    ActivityWelcomeAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        PrefManager prefManager=new PrefManager(getApplicationContext());

        binding.husnatkhalid.setText(prefManager.getUserName());
        binding.manageorg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeAdminActivity.this, ViewOrgProject.class));
            }
        });
        binding.btnstd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeAdminActivity.this, ViewOrgProject.class));
            }
        });
        binding.viewreports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(),ViewReports.class));
            }
        });
        binding.attendanceview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Attendance_View.class));
            }
        });
    }
}