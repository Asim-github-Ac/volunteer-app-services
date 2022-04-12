package com.example.vis.Organization;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.vis.R;
import com.example.vis.adapters.History_adapter;
import com.example.vis.databinding.ActivityRegisterProjectBinding;
import com.example.vis.databinding.ActivityTotalRegisterStdBinding;
import com.example.vis.databinding.ActivityWelcomeOrganizationBinding;
import com.example.vis.model.StdRegisterProject;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TotalRegisterStd extends AppCompatActivity {

    ActivityTotalRegisterStdBinding binding;
    FirebaseFirestore firebaseFirestore;
    List<StdRegisterProject> stdRegisterProjects;
    ProgressDialog progressDialog;
    String project;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTotalRegisterStdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        stdRegisterProjects=new ArrayList<>();
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("VIS");
        progressDialog.setMessage("Loading...............");
        progressDialog.show();
        Intent intent=getIntent();
        project=   intent.getStringExtra("name");
        System.out.println("name is s+++++++++++++++++++++++"+project);
        GetTotalStd();

    }
    public void GetTotalStd(){
        firebaseFirestore=FirebaseFirestore.getInstance();
        binding.rvview.setLayoutManager(new LinearLayoutManager(this));
        CollectionReference collectionReference=firebaseFirestore.collection("StdRegisterProject").document("data").collection(project);
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()){
                    progressDialog.cancel();
                    Toast.makeText(TotalRegisterStd.this, "Record Not Found", Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.cancel();
                    List<StdRegisterProject> projects=queryDocumentSnapshots.toObjects(StdRegisterProject.class);
                    stdRegisterProjects.addAll(projects);
                    System.out.println("data is ______________"+stdRegisterProjects);
                    binding.rvview.setAdapter(new History_adapter(TotalRegisterStd.this,stdRegisterProjects));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.cancel();
                Toast.makeText(TotalRegisterStd.this, "error"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}