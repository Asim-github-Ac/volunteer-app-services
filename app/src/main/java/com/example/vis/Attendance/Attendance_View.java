package com.example.vis.Attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.vis.R;
import com.example.vis.SharedPrefrence.PrefManager;
import com.example.vis.Student.Student_Attendance;
import com.example.vis.adapters.ViewHistory_Adapter;
import com.example.vis.databinding.ActivityAttendanceViewBinding;
import com.example.vis.databinding.ActivityWelcomeStudentBinding;
import com.example.vis.model.UoloadTimer_Data;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Attendance_View extends AppCompatActivity {
    PrefManager prefManager;
    List<UoloadTimer_Data> upload= new ArrayList<>();
    ProgressDialog progressDialog;
    ViewHistory_Adapter viewHistory_adapter;
    private ActivityAttendanceViewBinding mActivityBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityBinding = ActivityAttendanceViewBinding.inflate(getLayoutInflater());
        setContentView(mActivityBinding.getRoot());
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Load Attendance");
        progressDialog.setMessage("Loading.......");
        progressDialog.show();
        Histtory();

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
                            progressDialog.dismiss();
                            Toast.makeText(Attendance_View.this, "Nothing Found", Toast.LENGTH_SHORT).show();
                        }else {
                            List<UoloadTimer_Data> data=queryDocumentSnapshots.toObjects(UoloadTimer_Data.class);
                            upload.addAll(data);
                            progressDialog.dismiss();
                            mActivityBinding.recyc.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));
                            viewHistory_adapter=new ViewHistory_Adapter(getApplicationContext(),upload);
                            mActivityBinding.recyc.setAdapter(viewHistory_adapter);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Attendance_View.this, "error"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}