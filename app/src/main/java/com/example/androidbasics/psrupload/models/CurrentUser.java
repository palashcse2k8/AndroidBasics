package com.example.androidbasics.psrupload.models;

import android.graphics.Bitmap;
import android.os.Environment;


public class CurrentUser {
    public String fullName = "Md. Mosiur Rahman";
    public String accountNumber = "1312801024500";
    public String tinNumber = "123456789012";
    public String psrStatusPreviousYear = PSRStatus.Updated.name();
    public String psrStatusCurrentYear = PSRStatus.NotSubmitted.name();

    public String fileLocation = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/PSR_Report.pdf";

    public String selectedAssessmentYear ;
    public Bitmap psrBitmap;

    public CurrentUser () {
    }

    public void setPsr(Bitmap bitmap){
        this.psrBitmap = bitmap;
    }

    public void setSelectedAssessmentYear (String str) {
        this.selectedAssessmentYear = str;
    }
}
