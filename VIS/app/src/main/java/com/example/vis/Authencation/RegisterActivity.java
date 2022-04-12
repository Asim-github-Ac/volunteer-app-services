package com.example.vis.Authencation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.vis.R;
import com.example.vis.SharedPrefrence.PrefManager;
import com.example.vis.WelcomeStudentActivity;
import com.example.vis.databinding.ActivityLoginBinding;
import com.example.vis.databinding.ActivityRegisterBinding;
import com.example.vis.model.LoginModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding mActivityBinding;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(mActivityBinding.getRoot());

        mActivityBinding.registernow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mActivityBinding.pbregister.setVisibility(View.VISIBLE);
                RegisterNow(mActivityBinding.entername.getText().toString(),mActivityBinding.enteremail.getText().toString(),mActivityBinding.enterpass.getText().toString());

            }
        });
    }
    public void RegisterNow(String name,String email,String pass){
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("Users is ");
        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    LoginModel loginModel=new LoginModel(name,email);
                    databaseReference.setValue(loginModel);
                    PrefManager prefManager=new PrefManager(RegisterActivity.this);
                    prefManager.setUserEmail(email);
                    Intent intent=new Intent(RegisterActivity.this, WelcomeStudentActivity.class);
                    startActivity(intent);
                    mActivityBinding.pbregister.setVisibility(View.INVISIBLE);
                }else {
                    Toast.makeText(RegisterActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(RegisterActivity.this, "Error is"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}