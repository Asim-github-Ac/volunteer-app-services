package com.example.vis.model;

public class UploadPdf {
    String pdfurl,email;

    public UploadPdf(String pdfurl, String email) {
        this.pdfurl = pdfurl;
        this.email = email;
    }

    public UploadPdf() {
    }

    public String getPdfurl() {
        return pdfurl;
    }

    public void setPdfurl(String pdfurl) {
        this.pdfurl = pdfurl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
