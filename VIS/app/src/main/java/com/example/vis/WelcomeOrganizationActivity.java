package com.example.vis;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.vis.Organization.UploadProject;
import com.example.vis.SharedPrefrence.PrefManager;
import com.example.vis.databinding.ActivityUploadProjectBinding;
import com.example.vis.databinding.ActivityWelcomeOrganizationBinding;
public class WelcomeOrganizationActivity extends AppCompatActivity {
    public ActivityWelcomeOrganizationBinding binding;
    PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeOrganizationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefManager=new PrefManager(this);
        binding.toolbarorg.inflateMenu(R.menu.orgmenu);
        binding.projectupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeOrganizationActivity.this, UploadProject.class));
            }
        });
        binding.Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeOrganizationActivity.this,MainActivity.class));
                finish();
                prefManager.setToken_Email("");
            }
        });
        binding.viewproject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeOrganizationActivity.this,ViewProjects.class));
            }
        });
    }
}