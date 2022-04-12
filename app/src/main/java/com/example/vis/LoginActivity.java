package com.example.vis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.vis.Authencation.RegisterActivity;
import com.example.vis.SharedPrefrence.PrefManager;
import com.example.vis.databinding.ActivityLoginBinding;
import com.example.vis.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding mActivityBinding;
    UserModel mUser;
    String mRole = "";
    PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(mActivityBinding.getRoot());
        prefManager=new PrefManager(this);
        mRole = getIntent().getStringExtra("Role");

        mActivityBinding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);

            }
        });
        mActivityBinding.LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mActivityBinding.EmailEt.getText()) && !TextUtils.isEmpty(mActivityBinding.PasswordEt.getText())) {

                    if (mRole.equalsIgnoreCase("admin")) { //admin
                        if (mActivityBinding.EmailEt.getText().toString().equalsIgnoreCase("admin@gmail.com") &&
                                mActivityBinding.PasswordEt.getText().toString().equalsIgnoreCase("admin")) {
                            Intent intentObj = new Intent(LoginActivity.this, WelcomeAdminActivity.class);
                            startActivity(intentObj);
                            Toast.makeText(LoginActivity.this, "Successfully Loggedin!", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(LoginActivity.this, "Wrong Credentials!", Toast.LENGTH_SHORT).show();
                        }
                    } else if (mRole.equalsIgnoreCase("org")) { //organization
                            LoginNow(mActivityBinding.EmailEt.getText().toString(),mActivityBinding.PasswordEt.getText().toString());


                    } else { //student
                        mUser = new UserModel(mActivityBinding.EmailEt.getText().toString(), mActivityBinding.PasswordEt.getText().toString());
                        long id = mUser.saveRecord();
                        if (id >= 1) {
                           LoginNow(mActivityBinding.EmailEt.getText().toString(),mActivityBinding.PasswordEt.getText().toString());
                        } else {
                            Toast.makeText(LoginActivity.this, "Wrong Credentials!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Must fill required fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
  public void LoginNow(String email,String pass)
  {
      mActivityBinding.pblogin.setVisibility(View.VISIBLE);
      FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
      firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
              if (task.isSuccessful()){
                  prefManager.setUserEmail(email);
                 if (mRole.equalsIgnoreCase("org")){
                     Intent intentObj = new Intent(LoginActivity.this, WelcomeOrganizationActivity.class);
                     startActivity(intentObj);
                     finish();
                     prefManager.setToken_Email("org");
                 }else {
                     Intent intentObj = new Intent(LoginActivity.this, WelcomeStudentActivity.class);
                     startActivity(intentObj);
                     finish();
                     prefManager.setToken_Email("std");
                 }
                  mActivityBinding.pblogin.setVisibility(View.INVISIBLE);
                  Toast.makeText(LoginActivity.this, "Successfully Loggedin!", Toast.LENGTH_SHORT).show();
              }
          }
      }).addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
              Toast.makeText(LoginActivity.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
          }
      });
  }
}