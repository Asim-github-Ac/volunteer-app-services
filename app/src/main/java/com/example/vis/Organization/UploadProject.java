package com.example.vis.Organization;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.vis.R;
import com.example.vis.WelcomeOrganizationActivity;
import com.example.vis.databinding.ActivityUploadProjectBinding;
import com.example.vis.databinding.ActivityWelcomeStudentBinding;
import com.example.vis.model.ProjectModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UploadProject extends AppCompatActivity {
    public ActivityUploadProjectBinding binding;
    private FirebaseFirestore db;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadProjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = FirebaseFirestore.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Vis");
        progressDialog.setMessage("Wait please Uploadign Project");
        binding.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                addDataToFirestore(binding.email.getText().toString(),binding.projectname.getText().toString(),binding.slots.getText().toString());
            }
        });

        
    }
    private void addDataToFirestore(String email, String projectname, String slots) {

        // creating a collection reference
        // for our Firebase Firetore database.
        CollectionReference myproject = db.collection("Projects");
        CollectionReference allproject = db.collection("Allprojects");

        // adding our data to our courses object class.
        ProjectModel courses = new ProjectModel(email, projectname, slots);

        // below method is use to add data to Firebase Firestore.
        myproject.document("data").collection(email).add(courses).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                // after the data addition is successful
                // we are displaying a success toast message.
                allproject.add(courses).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(UploadProject.this, "Project Added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UploadProject.this, WelcomeOrganizationActivity.class));
                        progressDialog.cancel();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.cancel();
                // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.
                Toast.makeText(UploadProject.this, "Fail to add course \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}