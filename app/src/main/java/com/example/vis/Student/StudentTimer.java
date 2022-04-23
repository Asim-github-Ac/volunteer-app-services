package com.example.vis.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.vis.R;
import com.example.vis.databinding.ActivityStudentTimerBinding;
import com.example.vis.databinding.ActivityWelcomeStudentBinding;

public class StudentTimer extends AppCompatActivity {

    public ActivityStudentTimerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentTimerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}