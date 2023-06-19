package com.example.androidbasics.OCR;

import com.example.androidbasics.OCR.others.FrameMetadata;
import com.example.androidbasics.OCR.others.GraphicOverlay;

//import com.google.firebase.ml.common.FirebaseMLException;

import java.nio.ByteBuffer;

public interface FrameProcessor  {

    public void process(ByteBuffer data, FrameMetadata frameMetadata, GraphicOverlay graphicOverlay) throws Exception;

    public void stop();
}
