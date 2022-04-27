package com.example.vis.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.vis.Organization.UploadProject;
import com.example.vis.R;
import com.example.vis.SharedPrefrence.PrefManager;
import com.example.vis.WelcomeStudentActivity;
import com.example.vis.adapters.ViewHistory_Adapter;
import com.example.vis.databinding.ActivityStudentAttendanceBinding;
import com.example.vis.databinding.ActivityWelcomeStudentBinding;
import com.example.vis.model.UoloadTimer_Data;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Student_Attendance extends AppCompatActivity {
    ActivityStudentAttendanceBinding mActivityBinding;
    List<UoloadTimer_Data> upload= new ArrayList<>();
    ProgressDialog progressDialog;
    ViewHistory_Adapter viewHistory_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityBinding = ActivityStudentAttendanceBinding.inflate(getLayoutInflater());
        setContentView(mActivityBinding.getRoot());

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Student Time");
        progressDialog.setMessage("Loadding.....");
        progressDialog.setCancelable(true);
        progressDialog.show();
        GetDetails();

        mActivityBinding.viewhistor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivityBinding.rvhistory.setVisibility(View.VISIBLE);
                Histtory();
            }
        });
        mActivityBinding.stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                UploadTimer("12","2","10/10/2022","0");
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
                    progressDialog.dismiss();
                    Toast.makeText(Student_Attendance.this, "Please Start Your Time", Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.dismiss();
                    List<UoloadTimer_Data> uoloadTimer_data=queryDocumentSnapshots.toObjects(UoloadTimer_Data.class);
                    upload.addAll(uoloadTimer_data);
                  if (upload.get(0).getStatus().equals("1")){
                      mActivityBinding.date.setText("Date : "+upload.get(0).getDate());
                      mActivityBinding.startdate.setText("Start Time : "+upload.get(0).getStarttime());
                      mActivityBinding.endtimeis.setText("End Time : "+upload.get(0).getEndtime());
                      mActivityBinding.status.setText("Status Active");
                  }else {
                      Toast.makeText(Student_Attendance.this, "Please Start Your Time", Toast.LENGTH_SHORT).show();
                  }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressDialog.dismiss();
                Toast.makeText(Student_Attendance.this, "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void UploadTimer(String endtime,String starttime,String data ,String status){
        PrefManager prefManager=new PrefManager(this);

        FirebaseFirestore firestore;
        firestore=FirebaseFirestore.getInstance();
        UoloadTimer_Data uoloadTimer_data=new UoloadTimer_Data(prefManager.getUserEmail(),starttime,endtime,data,status);
        firestore.collection("Student_Timing").document("documents").collection(prefManager.getUserEmail()).add(uoloadTimer_data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Intent intent=new Intent(getApplicationContext(), WelcomeStudentActivity.class);
                        startActivity(intent);
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Student_Attendance.this, "error"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void Histtory(){
        PrefManager prefManager=new PrefManager(this);

        FirebaseFirestore firestore;
        firestore=FirebaseFirestore.getInstance();
        firestore.collection("Student_Timing").document("documents").collection(prefManager.getUserEmail()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()){
                            Toast.makeText(Student_Attendance.this, "Nothing Found", Toast.LENGTH_SHORT).show();
                        }else {
                            List<UoloadTimer_Data> data=queryDocumentSnapshots.toObjects(UoloadTimer_Data.class);
                            upload.addAll(data);
                            mActivityBinding.rvhistory.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));
                            viewHistory_adapter=new ViewHistory_Adapter(getApplicationContext(),upload);
                            mActivityBinding.rvhistory.setAdapter(viewHistory_adapter);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Student_Attendance.this, "error"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}