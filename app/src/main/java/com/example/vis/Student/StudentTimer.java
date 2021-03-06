package com.example.vis.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.example.vis.R;
import com.example.vis.SharedPrefrence.PrefManager;
import com.example.vis.WelcomeStudentActivity;
import com.example.vis.databinding.ActivityStudentTimerBinding;
import com.example.vis.databinding.ActivityWelcomeStudentBinding;
import com.example.vis.model.UoloadTimer_Data;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class StudentTimer extends AppCompatActivity implements LocationListener {
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    String latitude, longitude;
    public ActivityStudentTimerBinding binding;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentTimerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Location Verification");
        progressDialog.setMessage("Waiting.....");
        progressDialog.setCancelable(false);

        binding.confirmnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.startdate.getText().toString().isEmpty() && binding.starttime.getText().toString().isEmpty() && binding.endtime.getText().toString().isEmpty()) {
                    Toast.makeText(StudentTimer.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }else {
                    ConfirmOrder();
                    progressDialog.show();
                }
            }
        });
    }

    private void ConfirmOrder() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getLocation();
        }
    }
    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                StudentTimer.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                StudentTimer.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
          getLocations();
        }
    }
    public  void getLocations() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
      String lat = String.valueOf(location.getLatitude());
      String newlat=lat.substring(0,6);
      if (newlat.equals("33.546") || newlat.equals("31.405")){
          progressDialog.dismiss();
          Toast.makeText(this, "Your time start now", Toast.LENGTH_SHORT).show();

          UploadTimer(binding.endtime.getText().toString(),binding.starttime.getText().toString(),binding.startdate.getText().toString(),"1");
      }else {
          Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
          progressDialog.dismiss();
      }
     //   Toast.makeText(this, ""+location.getLatitude(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(StudentTimer.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

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
                }
            }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(StudentTimer.this, "error"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    });
}
public void AddHistory(){

}
}