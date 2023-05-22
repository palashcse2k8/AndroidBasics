package com.example.androidbasics.models;

enum PSRStatus {
    Submitted,
    Updated,
    Rejected,
    Processing
}


public class CurrentUser {
    public static String fullName = "Md. Mosiur Rahman";
    public static String accountNumber = "1312801024500";
    public static String tinNumber = "123456789012";

    public static String psrStatus = PSRStatus.Updated.name();
    public static String taxAssessmentYear = "2022-2023";
}
