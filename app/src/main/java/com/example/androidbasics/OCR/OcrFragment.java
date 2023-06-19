package com.example.androidbasics.OCR;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.androidbasics.R;
import com.example.androidbasics.OCR.others.GraphicOverlay;
import com.example.androidbasics.OCR.text_detection.TextRecognitionProcessor;


import com.example.androidbasics.OCR.others.Utils;
import com.example.androidbasics.OCR.camera.CameraSource;
import com.example.androidbasics.OCR.camera.CameraSourcePreview;

//import com.google.firebase.FirebaseApp;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class OcrFragment extends Fragment implements Action {

    public static int REQ_CODE_OCR = 101;
    public static int REQ_CODE_IMG_ONLY = 102;
    public static String RESPONSE_IMG = "RESPONSE_IMG";
    public static String RESPONSE_name = "RESPONSE_name";
    public static String RESPONSE_nid = "RESPONSE_nid";
    public static String RESPONSE_dob = "RESPONSE_dob";
    public static String RESPONSE_isNID = "RESPONSE_isNID";
    public static String RESPONSE_isDOB = "RESPONSE_isDOB";

    ImageView imageView;

    String TAG = "=====>>> " + getClass().getSimpleName();

    TextRecognitionProcessor textRecognitionProcessor;
    ProgressBar progressBar;
    private CameraSource cameraSource = null;
    private CameraSourcePreview preview;
    private GraphicOverlay graphicOverlay;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        return inflater.inflate(R.layout.fragment_ocr, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView=view.findViewById(R.id.img);

//        FirebaseApp.initializeApp(getActivity());

        preview = view.findViewById(R.id.camera_source_preview);
        if (preview == null) {
            Log.d(TAG, "Preview is null");
        }
        graphicOverlay = view.findViewById(R.id.graphics_overlay);
        if (graphicOverlay == null) {
            Log.d(TAG, "graphicOverlay is null");
        }
        progressBar = view.findViewById(R.id.progressBar);

        view.findViewById(R.id.clickBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeImg();
            }
        });

        getPermission();
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        startCameraSource();
    }

    /**
     * Stops the camera.
     */
    @Override
    public void onPause() {
        super.onPause();
        preview.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (cameraSource != null) {
            cameraSource.release();
        }
    }

    private void createCameraSource() {

        if (cameraSource == null) {
            cameraSource = new CameraSource(getActivity(), graphicOverlay);
            cameraSource.setFacing(CameraSource.CAMERA_FACING_BACK);
        }

        textRecognitionProcessor = new TextRecognitionProcessor(this);

        cameraSource.setMachineLearningFrameProcessor(textRecognitionProcessor);
    }

    private void startCameraSource() {
        if (cameraSource != null) {
            try {
                if (preview == null) {
                    Log.d(TAG, "resume: Preview is null");
                }
                if (graphicOverlay == null) {
                    Log.d(TAG, "resume: graphOverlay is null");
                }
                preview.start(cameraSource, graphicOverlay);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                cameraSource.release();
                cameraSource = null;
            }
        }
    }

    void getPermission() {
        int MyVersion = Build.VERSION.SDK_INT;
        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkIfAlreadyhavePermission()) {
                requestForSpecificPermission();
            } else {
                init();
            }
        }
    }


    void init() {
        createCameraSource();
        startCameraSource();
    }


    private boolean checkIfAlreadyhavePermission() {
        ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);
        return ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestForSpecificPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getPermission();
                } else {
                    requestForSpecificPermission();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void takeImg() {
        //ImageView imageView = findViewById(R.id.img);
        //view.setEnabled(false);
        preview.stop();
        progressBar.setVisibility(View.VISIBLE);

        if (getTargetRequestCode() == REQ_CODE_IMG_ONLY) {
            Bitmap bitmap = textRecognitionProcessor.getImg();
            String fileName = Utils.saveBitmap(getContext(), bitmap);


            Log.d("=====>>>","fileName: "+fileName);
            imageView.setImageBitmap(Utils.getBitmap(getContext(),fileName));


            Intent intent=new Intent();
            intent.putExtra(RESPONSE_IMG,fileName);
            getTargetFragment().onActivityResult(getTargetRequestCode(),REQ_CODE_IMG_ONLY,intent);

            getActivity().getSupportFragmentManager().popBackStack();

        } else {

            textRecognitionProcessor.startOcrProcessing();

        }


    }

    void gotoNext(String fileName, String name, String dob, String nid, boolean isNID, boolean isDOB) {



        Intent intent = new Intent();
        intent.putExtra(RESPONSE_IMG, fileName);
        intent.putExtra(RESPONSE_name, name);
        intent.putExtra(RESPONSE_dob, dob);
        intent.putExtra(RESPONSE_nid, nid);
        intent.putExtra(RESPONSE_isNID, isNID);
        intent.putExtra(RESPONSE_isDOB, isDOB);

        getTargetFragment().onActivityResult(getTargetRequestCode(), REQ_CODE_OCR, intent);
        getActivity().getSupportFragmentManager().popBackStack();


    }


    @Override
    public void gotoNextActivity(OCR_result ocr_result) {
        progressBar.setVisibility(View.INVISIBLE);

        Bitmap bitmap = ocr_result.getBitmap();

        String fileName = Utils.saveBitmap(getContext(), bitmap);

        gotoNext(fileName, ocr_result.getName(), ocr_result.getDob(), ocr_result.getNid(), ocr_result.getIsFoundNID(), ocr_result.getIsFoundDOB());


    }
}