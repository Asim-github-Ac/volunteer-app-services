package com.example.vis.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.vis.Organization.UploadProject;
import com.example.vis.R;
import com.example.vis.SharedPrefrence.PrefManager;
import com.example.vis.databinding.ActivityStudentAttendanceBinding;
import com.example.vis.databinding.ActivityWelcomeStudentBinding;
import com.example.vis.model.UoloadTimer_Data;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Student_Attendance extends AppCompatActivity {
    ActivityStudentAttendanceBinding mActivityBinding;
    List<UoloadTimer_Data> upload= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityBinding = ActivityStudentAttendanceBinding.inflate(getLayoutInflater());
        setContentView(mActivityBinding.getRoot());

        GetDetails();

        mActivityBinding.stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    public void GetDetails(){
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();

        PrefManager prefManager=new PrefManager(this);
        firestore.collection("Student_Timing").document("documents").collection(prefManager.getUserEmail()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()){
                    Toast.makeText(Student_Attendance.this, "Please Start Your Time", Toast.LENGTH_SHORT).show();
                }else {
                    List<UoloadTimer_Data> uoloadTimer_data=queryDocumentSnapshots.toObjects(UoloadTimer_Data.class);
                    upload.addAll(uoloadTimer_data);
                    mActivityBinding.date.setText(upload.get(0).getDate());
                    mActivityBinding.startdate.setText(upload.get(0).getStarttime());
                    mActivityBinding.endtimeis.setText(upload.get(0).getEndtime());


                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(Student_Attendance.this, "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}