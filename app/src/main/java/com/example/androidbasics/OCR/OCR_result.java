package com.example.androidbasics.OCR;

import android.graphics.Bitmap;

public class OCR_result {

    Bitmap bitmap;
    String name;
    String dob;
    String nid;
    boolean isFoundNID;
    boolean isFoundDOB;

    public OCR_result(Bitmap bitmap, String name, String dob, String nid, boolean isFoundNID, boolean isFoundDOB) {
        this.bitmap = bitmap;
        this.name = name;
        this.dob = dob;
        this.nid = nid;
        this.isFoundNID = isFoundNID;
        this.isFoundDOB = isFoundDOB;
    }


    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public boolean getIsFoundNID() {
        return isFoundNID;
    }

    public void setIsFoundNID(boolean isFoundNID) {
        this.isFoundNID = isFoundNID;
    }

    public boolean getIsFoundDOB() {
        return isFoundDOB;
    }

    public void setIsFoundDOB(boolean isFoundDOB) {
        this.isFoundDOB = isFoundDOB;
    }
}
