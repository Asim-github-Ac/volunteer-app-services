package com.example.vis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.vis.SharedPrefrence.PrefManager;
import com.example.vis.adapters.ViewporjectAdapter;
import com.example.vis.databinding.ActivityViewProjectsBinding;
import com.example.vis.databinding.ActivityWelcomeOrganizationBinding;
import com.example.vis.model.ProjectModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ViewProjects extends AppCompatActivity {

    List<ProjectModel> projectModels;
    ActivityViewProjectsBinding binding;
    FirebaseFirestore firebaseFirestore;
    PrefManager prefManager;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewProjectsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog=new ProgressDialog(this);
       GetProjects();
    }
    public void GetProjects(){

        prefManager=new PrefManager(this);
        projectModels=new ArrayList<>();
        progressDialog.setTitle("VIS");
        progressDialog.setMessage("Loading...............");
        progressDialog.show();
        System.out.println("data is +++++++++"+prefManager.getToken_Email());
        binding.rvproject.setLayoutManager(new LinearLayoutManager(this));
        firebaseFirestore=FirebaseFirestore.getInstance();
        CollectionReference collectionReference=firebaseFirestore.collection("Projects").document("data").collection(prefManager.getUserEmail());

        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()){
                    Toast.makeText(ViewProjects.this, "No Record FOund", Toast.LENGTH_SHORT).show();
                    progressDialog.cancel();
                }else {
                    List<ProjectModel> project=queryDocumentSnapshots.toObjects(ProjectModel.class);
                    projectModels.addAll(project);
                    progressDialog.cancel();
                    binding.rvproject.setAdapter(new ViewporjectAdapter(projectModels,ViewProjects.this));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.cancel();
                Toast.makeText(ViewProjects.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}