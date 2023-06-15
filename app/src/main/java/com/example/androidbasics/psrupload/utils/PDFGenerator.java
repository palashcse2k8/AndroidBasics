package com.example.androidbasics.psrupload.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfRenderer;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PDFGenerator {
    PDFGenerator() {

    }

    public static void generatePSRPDF(Bitmap bitmap, String tin, String assessmentYear, String date, String fileName) {

        // postscript size of A4 size paper
        int pageHeight = 842;
        int pageWidth = 595;
        int lineHeight = 100;
        int textSizeLarge = 25;
        int textSizeMedium = 20;

        // for our PDF document.
        PdfDocument pdfDocument = new PdfDocument();

        Paint paint = new Paint();
        Paint title = new Paint();

        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();
        PdfDocument.Page myPage = pdfDocument.startPage(myPageInfo);
        Canvas canvas = myPage.getCanvas();

        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        title.setTextSize(textSizeLarge);
        title.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("PSR Uploaded for bank approval", pageWidth / 2, lineHeight, title);
        lineHeight = lineHeight + textSizeLarge;
        title.setTextSize(textSizeMedium);
        canvas.drawText("TIN: " + tin, pageWidth / 2, lineHeight, title);
        lineHeight = lineHeight + textSizeLarge;
        canvas.drawText("TAX Assessment Year: " + assessmentYear, pageWidth / 2, lineHeight, title);
        lineHeight = lineHeight + textSizeLarge;
        canvas.drawText("PSR Upload Date: " + date, pageWidth / 2, lineHeight, title);

        canvas.drawBitmap(Bitmap.createScaledBitmap(bitmap, 500, 600, false), 40, 200, paint);

        pdfDocument.finishPage(myPage);
        File file = new File(fileName);

        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            Log.d("pdf", "PDF file generated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
//        bitmap.recycle();
        pdfDocument.close();
    }

    public static void generateFormCPDF(Bitmap bitmap, String fileName) {

        // postscript size of A4 size paper
        int pageHeight = 842;
        int pageWidth = 595;

        // for our PDF document.
        PdfDocument pdfDocument = new PdfDocument();

        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);

        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();
        PdfDocument.Page myPage = pdfDocument.startPage(myPageInfo);
        Canvas canvas = myPage.getCanvas();

        Log.d("", bitmap.getHeight() + " " + bitmap.getWidth() + " " + bitmap.getAllocationByteCount());

        canvas.drawBitmap(bitmap, 0, 0, paint);

        pdfDocument.finishPage(myPage);

        File file = new File(fileName);

        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            Log.d("pdf", "PDF file generated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        bitmap.recycle();
        pdfDocument.close();
    }

    public static Bitmap BITMAP_RESIZER(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float ratioX = newWidth / (float) bitmap.getWidth();
        float ratioY = newHeight / (float) bitmap.getHeight();
        float middleX = newWidth / 2.0f;
        float middleY = newHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, middleX - bitmap.getWidth() / 2, middleY - bitmap.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;
    }

    public static Bitmap getPdfFileAsBitmap(String filePath) {
        File file = new File(filePath);
        PdfRenderer renderer = null;
        ParcelFileDescriptor fileDescriptor = null;
        Bitmap bitmap = null;

        try {
            fileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);

            if (fileDescriptor != null) {
                renderer = new PdfRenderer(fileDescriptor);
                PdfRenderer.Page page = renderer.openPage(0);

                // Create a bitmap to hold the PDF page
                bitmap = Bitmap.createBitmap(page.getWidth(), page.getHeight(), Bitmap.Config.ARGB_8888);

                // Render the PDF page onto the bitmap
                page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

                // Close the PDF page and renderer
                page.close();
                renderer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileDescriptor != null) {
                try {
                    fileDescriptor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return bitmap;
    }
}
