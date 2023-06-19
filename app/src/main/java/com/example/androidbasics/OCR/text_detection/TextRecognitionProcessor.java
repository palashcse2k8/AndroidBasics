package com.example.androidbasics.OCR.text_detection;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

import com.example.androidbasics.OCR.Action;

import com.example.androidbasics.OCR.FrameProcessor;
import com.example.androidbasics.OCR.others.FrameMetadata;
import com.example.androidbasics.OCR.others.GraphicOverlay;
import com.example.androidbasics.OCR.OCR_result;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

//import com.google.firebase.ml.common.FirebaseMLException;
//import com.google.firebase.ml.vision.FirebaseVision;
//import com.google.firebase.ml.vision.common.FirebaseVisionImage;
//import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
//import com.google.firebase.ml.vision.text.FirebaseVisionText;
//import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;


public class TextRecognitionProcessor implements FrameProcessor {


//    private final FirebaseVisionTextRecognizer detector;


    private final AtomicBoolean shouldThrottle = new AtomicBoolean(false);
    String months = "Jan Feb Mar Apr May Jun Jul Aug Sep Oct Nov Dec";
    private Bitmap bitmap;
    private int cx;
    private int cy;
    private int dx;
    private int dxy;
    private int min;
    private Action action;
    private Bitmap cropBitmap;
    private ByteBuffer data;
    private FrameMetadata frameMetadata;
    private GraphicOverlay graphicOverlay;
    private Rect frameRect;

    public TextRecognitionProcessor(Action action) {
        this.action = action;
//        detector = FirebaseVision.getInstance().getOnDeviceTextRecognizer();
    }

    @Override
    public void stop() {
        //            detector.close();
    }

    //endregion

    //region ----- Helper Methods -----

    public void startOcrProcessing() {

        //cropBitmap = Bitmap.createBitmap(bitmap, frameRect.left, frameRect.top, frameRect.width(), frameRect.height(), null, false);
        cropBitmap = getImg();
//        detectInVisionImage(FirebaseVisionImage.fromBitmap(cropBitmap), frameMetadata, graphicOverlay);

    }

    public Bitmap getImg() {

        cropBitmap = Bitmap.createBitmap(bitmap, frameRect.left, frameRect.top, frameRect.width(), frameRect.height(), null, false);

        return cropBitmap;


    }

    @Override
    public void process(ByteBuffer data, FrameMetadata frameMetadata, GraphicOverlay graphicOverlay) throws Exception {
        //graphicOverlay.clear();

        /*this.data = data;
        this.frameMetadata = frameMetadata;
        this.graphicOverlay = graphicOverlay;*/

//        FirebaseVisionImageMetadata metadata =
//                new FirebaseVisionImageMetadata.Builder()
//                        .setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
//                        .setWidth(frameMetadata.getWidth())
//                        .setHeight(frameMetadata.getHeight())
//                        .setRotation(frameMetadata.getRotation())
//                        .build();

//        FirebaseVisionImage visionImage = FirebaseVisionImage.fromByteBuffer(data, metadata);

//        bitmap = visionImage.getBitmap();
        //Log.d("=====>>",bitmap.getWidth()+","+bitmap.getHeight());

        cx = bitmap.getWidth() / 2;
        cy = bitmap.getHeight() / 2;

        dx = (int) (3 * cx / 4.5f);
        dxy = (int) (dx * 2.125f / 3.37f);
        //yumdxy=5*dxy/6;
        frameRect = new Rect();
        //float cx = frameCenterX;
        //float cy = frameCenterY;
        frameRect.left = cx - dx;
        frameRect.right = cx + dx;
        frameRect.top = cy - dxy;
        frameRect.bottom = cy + dxy;

        GraphicOverlay.Graphic textGraphic = new TextGraphic(graphicOverlay, frameRect);
        graphicOverlay.add(textGraphic);

        /*if (shouldThrottle.get()) {
            return;
        }
        FirebaseVisionImageMetadata metadata =
                new FirebaseVisionImageMetadata.Builder()
                        .setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
                        .setWidth(frameMetadata.getWidth())
                        .setHeight(frameMetadata.getHeight())
                        .setRotation(frameMetadata.getRotation())
                        .build();*/

        //detectInVisionImage(FirebaseVisionImage.fromByteBuffer(data, metadata), frameMetadata, graphicOverlay);
    }

//    private Task<FirebaseVisionText> detectInImage(FirebaseVisionImage image) {
//        return detector.processImage(image);
//    }

    private boolean isNid(String s) {

        int len = s.length();
        if (len == 10 || len == 13 || len == 17) {
            int num = 0;
            for (int i = 0; i < len; i++) {
                if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                    num++;

                }

            }
            return (float) (len - num) / len < .3;
        } else {
            return false;
        }

    }

//    private void onSuccess(@NonNull FirebaseVisionText results, @NonNull FrameMetadata frameMetadata, @NonNull GraphicOverlay graphicOverlay) {
//
//
//        List<FirebaseVisionText.TextBlock> blocks = results.getTextBlocks();
//        if (blocks.size() == 0) {
//            action.gotoNextActivity(new OCR_result(cropBitmap, "", "", "", false, false));
//            return;
//        }
//        String id = "";
//        boolean isFoundNid = false;
//        boolean isFoundDOB = false;
//        StringBuilder name = new StringBuilder();
//
//        StringBuilder dob = new StringBuilder();
//        String nid = "";
//        boolean isSmartNID = false;
//        boolean nextBlock = false;
//        boolean nextLine = false;
//        boolean isName = false;
//
//        for (int i = 0; i < blocks.size(); i++) {
//            List<FirebaseVisionText.Line> lines = blocks.get(i).getLines();
//
//            if (nextBlock) {
//                nextBlock = false;
//            } else {
//                isName = false;
//            }
//            for (int j = 0; j < lines.size(); j++) {
//                List<FirebaseVisionText.Element> elements = lines.get(j).getElements();
//
//
//                if (nextLine) {
//                    nextLine = false;
//                } else {
//                    isName = false;
//                }
//
//                for (int k = 0; k < elements.size(); k++) {
//
//                    String text = elements.get(k).getText().trim();
//                    //Log.d("=====>>", "(" + i + "," + j + "," + k + ") [" + text + "]");
//
//                    int len = text.length();
//
//                    if (!isFoundDOB) {
//                        if (len == 2) {
//
//                            if (isDigits(text)) {
//                                if (k + 2 < elements.size()) {
//                                    String s2 = elements.get(k + 2).getText().trim();
//                                    if (s2.length() == 4 && isDigits(s2)) {
//                                        dob.append(text).append(" ");
//                                        String monFinal = "";
//                                        String mon = elements.get(k + 1).getText().trim();
//
//
//                                        monFinal = matchMonth(mon);
//
//                                        if (monFinal == null) {
//                                            monFinal = mon;
//                                            isFoundDOB = false;
//
//                                        } else {
//                                            isFoundDOB = true;
//                                        }
//
//                                        dob.append(monFinal).append(" ");
//                                        dob.append(elements.get(k + 2).getText().trim());
//
//
//                                    }
//                                }
//
//                            }
//
//
//                        }
//
//                    }
//
//
//                    if (!isFoundNid && isNid(text)) {
//                        id = text;
//                        isSmartNID = false;
//                        isFoundNid = true;
//                        //continue;
//
//                    }
//                    if (!isFoundNid && len == 3) {
//                        // Log.d("=====>>", "len == 3 (" + i + "," + j + "," + k + ") [" + text + "]");
//
//                        if (isDigits(text)) {
//                            if (k + 2 < elements.size()) {
//
//                                String s2 = elements.get(k + 1).getText().trim();
//                                if (s2.length() == 3 && isDigits(s2)) {
//
//                                    //Log.d("=====>>", "len == 3 (" + i + "," + j + "," + k + ") [" + s2 + "]");
//
//                                    String s3 = elements.get(k + 2).getText().trim();
//                                    if (s3.length() == 4 && isDigits(s3)) {
//
//                                        // Log.d("=====>>", "len == 4 (" + i + "," + j + "," + k + ") [" + s3 + "]");
//                                        /*nid.append(text).append(" ");
//                                        nid.append(s2).append(" ");
//                                        nid.append(s3);*/
//                                        id = text + " " + s2 + " " + s3;
//
//                                        isFoundNid = true;
//                                        isSmartNID = true;
//                                    }
//
//
//                                }
//                            }
//
//                        }
//
//
//                    }
//
//
//                    if (text.contains("Name") || text.contains("Namo") || text.contains("Naie") || text.contains("Namie") || text.contains("Narie") || text.contains("Narmo") || text.contains("Nario") || text.contains("Namio")) {
//                        if (elements.size() - 1 == k && lines.size() - 1 == j) {
//                            nextBlock = true;
//                            nextLine = true;
//                            Log.d("=====>>", "::  next block");
//                        } else if (elements.size() - 1 == k) {
//                            Log.d("=====>>", "::  next line");
//                            nextLine = true;
//                        }
//                        isName = true;
//                        continue;
//
//
//                    } else if (text.contains("Date") || text.contains("Oate")) {
//                        isName = false;
//                        //break;
//
//                    }
//                    if (isName) {
//                        name.append(text).append(" ");
//                    }
//
//
//                }
//            }
//        }
//
//
//        Log.d("=====>>", "name >>" + name + "<< ");
//        Log.d("=====>>", "dob >>" + dob + "<<");
//        Log.d("=====>>", "nid >>" + nid + "<<");
//        //Log.d("=====>>", "secondNidtry >>" + secondNidtry + "<<");
//        Log.d("=====>>", "id >>" + id + "<<");
//
//
//        action.gotoNextActivity(new OCR_result(cropBitmap, name.toString(), dob.toString(), id, isFoundNid, isFoundDOB));
//
//
//    }

    String MonLen1(String mon) {
        System.out.println("MonLen1.......");

        String monthFinal = null;
        ArrayList<String> pList = new ArrayList<>(Arrays.asList(mon + "\\S\\S", "\\S\\S" + mon, "\\S" + mon + "\\S"));

        int count = 0;
        for (int i = 0; i < pList.size(); i++) {
            String pattern = pList.get(i);

            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(months);

            while (m.find()) {
                count++;
                monthFinal = m.group(0);
                System.out.println(m.group(0));

            }

        }
        if (count == 1) {
            return monthFinal;

        } else {
            return null;
        }

    }

    String MonLen2(String mon) {
        System.out.println("MonLen2.......");

        String monthFinal = null;
        ArrayList<String> pList = new ArrayList<>(
                Arrays.asList(mon + "\\S", mon.charAt(0) + "\\S" + mon.charAt(1), "\\S" + mon));

        int count = 0;
        for (int i = 0; i < pList.size(); i++) {
            String pattern = pList.get(i);

            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(months);

            while (m.find()) {
                count++;
                monthFinal = m.group(0);
                System.out.println(m.group(0));
            }
        }
        if (count == 1) {
            return monthFinal;

        } else {
            return null;
        }

    }

    String matchMonth(String mon) {
        String monthFinal = null;

        if (mon.length() == 3 && months.contains(mon)) {
            return mon;
        }

        if (mon.length() == 1) {

            return MonLen1(mon);

        } else if (mon.length() == 2) {
            monthFinal = MonLen2(mon);
            if (monthFinal != null) {
                return monthFinal;
            } else {
                ArrayList<String> pList = new ArrayList<>(Arrays.asList(mon.charAt(0) + "", mon.charAt(1) + ""));

                int count = 0;
                for (int i = 0; i < pList.size(); i++) {
                    monthFinal = MonLen1(pList.get(i));
                    if (monthFinal != null)
                        count++;

                }
                if (count == 1) {
                    return monthFinal;
                } else {
                    return null;
                }

            }
        } else if (mon.length() == 3) {

            ArrayList<String> pList = new ArrayList<>(Arrays.asList(mon.charAt(0) + mon.charAt(1) + "",
                    mon.charAt(0) + "" + mon.charAt(2), "" + mon.charAt(1) + mon.charAt(2)));
            int count = 0;

            for (int i = 0; i < pList.size(); i++) {
                monthFinal = MonLen2(pList.get(i));
                if (monthFinal != null)
                    count++;

            }
            if (count == 1) {
                return monthFinal;
            }
            System.out.println("MonLen2 not");

            pList = new ArrayList<>(Arrays.asList(mon.charAt(0) + "", mon.charAt(1) + "", "" + mon.charAt(2)));

            count = 0;
            for (int i = 0; i < pList.size(); i++) {
                monthFinal = MonLen1(pList.get(i));
                if (monthFinal != null)
                    count++;

            }
            if (count == 1) {
                return monthFinal;
            } else {
                return null;
            }

        }

        return null;

    }

    private boolean isDigits(String s) {

        int len = s.length();
        //if (len == 3 || len == 4) {
        int num = 0;
        for (int i = 0; i < len; i++) {
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                num++;

            }

        }
        //return (float) (len - num) / len < .3;
        return len == num;


    }

    protected void onFailure(@NonNull Exception e) {
        Log.w("=====>>", "Text detection failed." + e);
    }

//    private void detectInVisionImage(FirebaseVisionImage image, final FrameMetadata metadata, final GraphicOverlay graphicOverlay) {
//
//        detectInImage(image)
//                .addOnSuccessListener(
//                        new OnSuccessListener<FirebaseVisionText>() {
//                            @Override
//                            public void onSuccess(FirebaseVisionText results) {
//                                shouldThrottle.set(false);
//                                TextRecognitionProcessor.this.onSuccess(results, metadata, graphicOverlay);
//                            }
//                        })
//                .addOnFailureListener(
//                        new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                shouldThrottle.set(false);
//                                TextRecognitionProcessor.this.onFailure(e);
//                            }
//                        });
//        // Begin throttling until this frame of input has been processed, either in onSuccess or
//        // onFailure.
//        shouldThrottle.set(true);
//    }

    //endregion


}
