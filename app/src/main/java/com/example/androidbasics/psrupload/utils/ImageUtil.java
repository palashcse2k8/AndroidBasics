package com.example.androidbasics.psrupload.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Base64;
import java.io.ByteArrayOutputStream;

public class ImageUtil
{
    public static Bitmap convertToBitmap(String base64Str) throws IllegalArgumentException
    {
        byte[] decodedBytes = Base64.decode(
                base64Str.substring(base64Str.indexOf(",")  + 1),
                Base64.DEFAULT
        );

        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static String convertToBase64(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap combineBitmapsSideBySide(final Bitmap left, final Bitmap right){
        // Get the size of the images combined side by side.
        if (left == null && right != null)
            return right;

        if (right == null && left != null)
            return left;

        if (left == null && right == null)
            return null;

        int width = left.getWidth() + right.getWidth();
        int height = left.getHeight() > right.getHeight() ? left.getHeight() : right.getHeight();

        // Create a Bitmap large enough to hold both input images and a canvas to draw to this
        // combined bitmap.
        Bitmap combined = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(combined);

        // Render both input images into the combined bitmap and return it.
        canvas.drawBitmap(left, 0f, 0f, null);
        canvas.drawBitmap(right, left.getWidth(), 0f, null);

        return combined;
    }

    public static Bitmap combineBitmapsVertically(final Bitmap top, final Bitmap bottom){
        // Get the size of the images combined side by side.
        if (top == null && bottom != null)
            return bottom;

        if (bottom == null && top != null)
            return top;

        if (top == null && bottom == null)
            return null;

        int height = top.getHeight() + bottom.getHeight();
        int width = top.getWidth() > bottom.getWidth() ? top.getWidth() : bottom.getWidth();

        // Create a Bitmap large enough to hold both input images and a canvas to draw to this
        // combined bitmap.
        Bitmap combined = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(combined);

        // Render both input images into the combined bitmap and return it.
        canvas.drawBitmap(top, 0f, 0f, null);
        canvas.drawBitmap(bottom, 0, top.getHeight(), null);

        return combined;
    }
}

//Usage
//Bitmap bitmap = ImageUtil.convertToBitmap(base64String);
//String base64String = ImageUtil.convertToBase64(bitmap);