package com.example.androidbasics.psrupload;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidbasics.MainActivity;
import com.example.androidbasics.R;

public class PSRSubmissionActivity extends AppCompatActivity {

    Button btnDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psrsubmission);
        btnDone = findViewById(R.id.btn_done);
        btnDone.setOnClickListener(v -> startActivity(new Intent(PSRSubmissionActivity.this, MainActivity.class)));
    }
}