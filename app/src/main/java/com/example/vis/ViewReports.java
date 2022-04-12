package com.example.vis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.vis.adapters.PdfViewerAdaper;
import com.example.vis.databinding.ActivityViewReportsBinding;
import com.example.vis.databinding.ActivityWelcomeAdminBinding;
import com.example.vis.model.UploadPdf;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewReports extends AppCompatActivity {
ActivityViewReportsBinding binding;
List<UploadPdf> uploadPdfList=new ArrayList<>();
PdfViewerAdaper pdfViewerAdaper;
FirebaseFirestore firebaseDatabase;
ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewReportsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.reportrecy.setLayoutManager(new LinearLayoutManager(this));
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Progress");
        progressDialog.setMessage("Loadings..............");
        progressDialog.show();
        progressDialog.setCancelable(false);
        firebaseDatabase=FirebaseFirestore.getInstance();
        firebaseDatabase.collection("PdfUrl").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()){
                    Toast.makeText(ViewReports.this, "Record Not Found", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else {
                    List<UploadPdf> uploadPdfs=queryDocumentSnapshots.toObjects(UploadPdf.class);
                    uploadPdfList.addAll(uploadPdfs);
                    progressDialog.dismiss();
                    pdfViewerAdaper=new PdfViewerAdaper(ViewReports.this,uploadPdfList);
                    binding.reportrecy.setAdapter(pdfViewerAdaper);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("error is viewreport___________"+e.getMessage());
            }
        });
    }
}