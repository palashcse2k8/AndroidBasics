package com.example.androidbasics.apihandle;

public class PayLoad {
    private String mobileNo;
    private String assessmentYear;

    public PayLoad(String mobileNo, String assessmentYear) {
        this.mobileNo = mobileNo;
        this.assessmentYear = assessmentYear;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAssessmentYear() {
        return assessmentYear;
    }

    public void setAssessmentYear(String assessmentYear) {
        this.assessmentYear = assessmentYear;
    }
}