package com.example.vis.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.vis.R;
import com.example.vis.databinding.ActivityViewProjectsBinding;
import com.example.vis.databinding.ActivityWelcomeAdminBinding;

public class ViewOrgProject extends AppCompatActivity {

    ActivityViewProjectsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewProjectsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }
}