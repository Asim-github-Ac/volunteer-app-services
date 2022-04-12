package com.example.vis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.vis.adapters.UserAdapter;
import com.example.vis.databinding.ActivityAllProjectsBinding;
import com.example.vis.model.ProjectModel;
import com.example.vis.model.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AllProjectsActivity extends AppCompatActivity {
    private ActivityAllProjectsBinding mActivityBinding;
    ArrayList<UserModel> mProjectData;
    ArrayList<ProjectModel> projectModels;
    Boolean mCheck;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityBinding = ActivityAllProjectsBinding.inflate(getLayoutInflater());
        setContentView(mActivityBinding.getRoot());
        GetData();
        mActivityBinding.projectRv.setLayoutManager(new LinearLayoutManager(AllProjectsActivity.this));

        mCheck = SharedPref.getInstance().getBooleanPref("save", true);
        if (mCheck) {
            saveDataIntoDataBase();
        }
     //   getAllProjects();
    }

    public void saveDataIntoDataBase() {
        UserModel project;
        project = new UserModel("Al-Khidmat Foundation", "15", "");
        project.saveProjectRecord();
        project = new UserModel("JDC Welfare Organization", "12", "");
        project.saveProjectRecord();
        project = new UserModel("Edhi Foundation", "15", "");
        project.saveProjectRecord();
        project = new UserModel("Akhuwat Foundation", "25", "");
        project.saveProjectRecord();
        project = new UserModel("Action Aid", "30", "");
        project.saveProjectRecord();
        project = new UserModel("Agha Khan Foundation", "18", "");
        long id = project.saveProjectRecord();
        if (id >= 1) {
            SharedPref.getInstance().savePref("save", false);
            //Toast.makeText(AllProjectsActivity.this, "Successfull", Toast.LENGTH_SHORT).show();
        } else {
            // Toast.makeText(AllProjectsActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
        }
    }

    public void getAllProjects() {
//        mProjectData = UserModel.getRecordsList();
//        if (mProjectData.size() > 0) {
//            mActivityBinding.projectRv.setAdapter(new UserAdapter(AllProjectsActivity.this,
//                    mProjectData));
//        }
    }
    public void GetData(){
        firebaseFirestore=FirebaseFirestore.getInstance();
        projectModels=new ArrayList<>();
        firebaseFirestore.collection("Allprojects").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()){
                    Toast.makeText(AllProjectsActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                }else {
                    List<ProjectModel> types = queryDocumentSnapshots.toObjects(ProjectModel.class);
                    // Add all to your list
                    projectModels.addAll(types);
                    mActivityBinding.projectRv.setAdapter(new UserAdapter(AllProjectsActivity.this,
                            projectModels));
                    System.out.println("data is _________________"+projectModels);
                } }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}