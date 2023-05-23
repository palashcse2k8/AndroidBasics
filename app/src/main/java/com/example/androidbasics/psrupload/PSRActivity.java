package com.example.androidbasics.psrupload;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidbasics.R;
import com.example.androidbasics.psrupload.models.CurrentUser;

public class PSRActivity extends AppCompatActivity {

    CurrentUser currentUser;

    TextView taxAssessMentYear, psrStatus;
    TextView nameText, acNumberText, tinText;
    Button uploadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psr);
        currentUser = new CurrentUser();


        taxAssessMentYear = findViewById(R.id.label_tax_assessment_year);
        taxAssessMentYear.setText(currentUser.taxAssessmentYear);

        psrStatus = findViewById(R.id.label_tax_assessment_status);
        psrStatus.setText(currentUser.psrStatus);

        nameText = findViewById(R.id.label_name_text);
        nameText.setText(currentUser.fullName);

        acNumberText = findViewById(R.id.label_ac_number_text);
        String text = " " + currentUser.accountNumber;
        acNumberText.setText("text");

        tinText = findViewById(R.id.label_tin_text);
        tinText.setText(currentUser.tinNumber);

        uploadButton = (Button) findViewById(R.id.btn_upload);
        uploadButton.setOnClickListener(v -> {
            Intent intent = new Intent(PSRActivity.this, PSRUploadActivity.class);
            startActivity(intent);
        });

        if(currentUser.psrStatus.equalsIgnoreCase("UPDATED")) {
            uploadButton.setVisibility(View.VISIBLE);
        } else {
            uploadButton.setVisibility(View.GONE);
        }
    }
}