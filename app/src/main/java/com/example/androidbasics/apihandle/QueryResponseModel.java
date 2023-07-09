package com.example.androidbasics.apihandle;

public class QueryResponseModel {
    private String mobileNo;
    private String customerName;
    private String primaryAccount;
    private String tin;
    private String assessmentYear;
    private String trStatus;
    private String trUploadDate;


    // Getter Methods

    public String getMobileNo() {
        return mobileNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPrimaryAccount() {
        return primaryAccount;
    }

    public String getTin() {
        return tin;
    }

    public String getAssessmentYear() {
        return assessmentYear;
    }

    public String getTrStatus() {
        return trStatus;
    }

    public String getTrUploadDate() {
        return trUploadDate;
    }

    // Setter Methods

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setPrimaryAccount(String primaryAccount) {
        this.primaryAccount = primaryAccount;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public void setAssessmentYear(String assessmentYear) {
        this.assessmentYear = assessmentYear;
    }

    public void setTrStatus(String trStatus) {
        this.trStatus = trStatus;
    }

    public void setTrUploadDate(String trUploadDate) {
        this.trUploadDate = trUploadDate;
    }
}