package com.example.androidbasics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidbasics.models.CurrentUser;

public class PSRUploadActivity extends AppCompatActivity {

    CurrentUser currentUser = new CurrentUser();

    TextView taxAssessMentYear, psrStatus;
    TextView nameText, acNumberText, tinText;
    Button uploadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psrupload);


        taxAssessMentYear = findViewById(R.id.label_tax_assessment_year);
        taxAssessMentYear.setText(currentUser.taxAssessmentYear);

        psrStatus = findViewById(R.id.label_tax_assessment_status);
        psrStatus.setText(currentUser.psrStatus);

        nameText = findViewById(R.id.label_name_text);
        nameText.setText(currentUser.fullName);

        acNumberText = findViewById(R.id.label_ac_number_text);
        acNumberText.setText(" " + currentUser.accountNumber);

        tinText = findViewById(R.id.label_tin_text);
        tinText.setText(currentUser.tinNumber);

        uploadButton = (Button) findViewById(R.id.btn_upload);

        if(currentUser.psrStatus.equalsIgnoreCase("UPDATE")) {
            uploadButton.setVisibility(View.VISIBLE);
        } else {
            uploadButton.setVisibility(View.GONE);
        }
    }
}