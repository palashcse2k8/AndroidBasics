package com.example.androidbasics.psrupload;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.androidbasics.R;
import com.example.androidbasics.psrupload.models.PSRActivityTest;

public class PSRUploadActivity extends AppCompatActivity {

    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psrupload);

        submitButton = findViewById(R.id.btn_submit);
        submitButton.setOnClickListener(v -> startActivity(new Intent(PSRUploadActivity.this, PSRActivityTest.class))
        );
    }
}