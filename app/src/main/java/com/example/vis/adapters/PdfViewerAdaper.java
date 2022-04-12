package com.example.vis.adapters;

import static android.content.Context.DOWNLOAD_SERVICE;


import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vis.DownloadPdf.DownloadTask;;
import com.example.vis.databinding.PdfvieweritemBinding;
import com.example.vis.model.UploadPdf;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class PdfViewerAdaper extends RecyclerView.Adapter<PdfViewerAdaper.myHolder> {
    Context context;
    List<UploadPdf> uploadPdfList;
    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 41;
    URL url;
    public PdfViewerAdaper(Context context, List<UploadPdf> uploadPdfList) {
        this.context = context;
        this.uploadPdfList = uploadPdfList;
    }

    @NonNull
    @Override
    public PdfViewerAdaper.myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PdfvieweritemBinding rowBinding = PdfvieweritemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PdfViewerAdaper.myHolder(rowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PdfViewerAdaper.myHolder holder, int position) {
        final UploadPdf uploadPdf=uploadPdfList.get(position);
        holder.binding.emailshow.setText("User Email : "+uploadPdf.getEmail());
        holder.binding.donwloadreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadPdf(uploadPdf.getPdfurl());

            //    new DownloadTask(context,uploadPdf.getPdfurl());
            }
        });
    }

    @Override
    public int getItemCount() {
        return uploadPdfList.size();
    }

    public class myHolder extends RecyclerView.ViewHolder {

        PdfvieweritemBinding binding;

        public myHolder(PdfvieweritemBinding rowBinding) {
            super(rowBinding.getRoot());
            this.binding = rowBinding;
        }
    }
    private void downloadPdf(String pdfURL) {

        try {
            url = new URL(pdfURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
          //  requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},READ_STORAGE_PERMISSION_REQUEST_CODE);
        }

        File direct = new File(Environment.getExternalStorageDirectory() + "/Download/AldoFiles");

        if (!direct.exists()) {
            File pdfDirectory = new File("/sdcard/Download/AldoFiles/");
            pdfDirectory.mkdirs();
        }

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url + ""));
        request.setTitle("pdf");
        request.setMimeType("application/pdf");
        request.allowScanningByMediaScanner();
        request.setAllowedOverMetered(true);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"pdf");
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);

    }


}
