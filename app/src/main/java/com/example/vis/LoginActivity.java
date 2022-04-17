package com.example.vis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    private FirebaseAuth mAuth;
    String mRole = "";
    PrefManager prefManager;
    ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(mActivityBinding.getRoot());
        prefManager=new PrefManager(this);
        mAuth = FirebaseAuth.getInstance();
        mRole = getIntent().getStringExtra("Role");

        mActivityBinding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);

            }
        });
        mActivityBinding.forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecoverPasswordDialog();
            }
        });
        mActivityBinding.LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefManager prefManager=new PrefManager(getApplicationContext());
                prefManager.setUserName(mActivityBinding.EmailEt.getText().toString());
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
    private void showRecoverPasswordDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Recover Password");
        LinearLayout linearLayout=new LinearLayout(this);
        final EditText emailet= new EditText(this);

        // write the email using which you registered
        emailet.setText("Email");
        emailet.setMinEms(16);
        emailet.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        linearLayout.addView(emailet);
        linearLayout.setPadding(10,10,10,10);
        builder.setView(linearLayout);

        // Click on Recover and a email will be sent to your registered email id
        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email=emailet.getText().toString().trim();
                beginRecovery(email);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    private void beginRecovery(String email) {
        loadingBar=new ProgressDialog(this);
        loadingBar.setMessage("Sending Email....");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        // calling sendPasswordResetEmail
        // open your email and write the new
        // password and then you can login
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                loadingBar.dismiss();
                if(task.isSuccessful())
                {
                    // if isSuccessful then done message will be shown
                    // and you can change the password
                    Toast.makeText(LoginActivity.this,"Done sent",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(LoginActivity.this,"Error Occured",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingBar.dismiss();
                Toast.makeText(LoginActivity.this,"Error Failed",Toast.LENGTH_LONG).show();
            }
        });
    }
}