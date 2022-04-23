package com.example.vis.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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


        binding.confirmnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.startdate.getText().toString().isEmpty() && binding.starttime.getText().toString().isEmpty() && binding.endtime.getText().toString().isEmpty()) {
                    Toast.makeText(StudentTimer.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }else {
                    ConfirmOrder();
                }
            }
        });
    }

    private void ConfirmOrder() {

    }
}