package com.example.vis.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.vis.R;
import com.example.vis.SharedPrefrence.PrefManager;
import com.example.vis.WelcomeStudentActivity;
import com.example.vis.databinding.ActivityAllProjectsBinding;
import com.example.vis.databinding.ActivityRegisterBinding;
import com.example.vis.databinding.ActivityRegisterProjectBinding;
import com.example.vis.model.StdRegisterProject;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterProject extends AppCompatActivity {
    ActivityRegisterProjectBinding binding;
    FirebaseFirestore firestore;
    PrefManager prefManager;
    String orgemail,orgname,orgslots;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterProjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefManager=new PrefManager(this);
        Intent intent=getIntent();
        orgemail=intent.getStringExtra("email");
        orgname=intent.getStringExtra("name");
        orgslots=intent.getStringExtra("slots");

        binding.email.setText(orgemail);
        binding.name.setText(orgname);
        binding.enteremail.setText(orgslots);
        binding.enteremail.setText(prefManager.getUserEmail());

        binding.confirnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterProject(orgemail,orgname,orgslots);
            }
        });


    }
   public void RegisterProject(String orgemail,String orgname,String slots){
        firestore=FirebaseFirestore.getInstance();

       StdRegisterProject stdRegisterProject=new StdRegisterProject(orgemail,orgname,slots,prefManager.getUserEmail());
        firestore.collection("StdRegisterProject").document("data").collection(orgname).add(stdRegisterProject).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(RegisterProject.this, "Successfully added", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(RegisterProject.this, WelcomeStudentActivity.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterProject.this, "error"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
   }
}