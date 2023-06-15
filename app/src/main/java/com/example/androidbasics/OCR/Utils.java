package com.example.androidbasics.OCR;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.UUID;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Utils {

    public static String saveBitmap(Context activity, Bitmap bitmap) {
        String fileName =  UUID.randomUUID().toString();//no .png or .jpg needed
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            FileOutputStream fo = activity.openFileOutput(fileName, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
            fileName = null;
        }
        return fileName;
    }

    public static Bitmap getBitmap(Context activity,String fileName)
    {
        Bitmap bitmap = null;
        try {

            bitmap = BitmapFactory.decodeStream(activity.openFileInput(fileName));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return bitmap;
    }


    public static String convertImageIntoBase64(Context context, Bitmap bitmap, int imageType)
    {
        String ConvertBackImage = null;
        ByteArrayOutputStream byteArrayOutputStreamObject  = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);
        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();
        ConvertBackImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);
        // 1 for selfie
        //2 for nid font page
        // 3 for nid back page


        if(imageType==2)
        {
//            StaticConstants.nidFontImage = ConvertBackImage;
        }
        else if(imageType==3)
        {
//            StaticConstants.nidBackImage = ConvertBackImage;
        }
        return  "";
    }


    public static String DateConverter(String dateofbirth) {
            String outputFormat = "yyyy-MM-dd";

            try {
                SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd MMM yyyy");
                Date date = inputDateFormat.parse(dateofbirth);

                SimpleDateFormat outputDateFormat = new SimpleDateFormat(outputFormat);
                String formattedDate = outputDateFormat.format(date);

                return formattedDate;
            } catch (ParseException e) {
                e.printStackTrace();
                return "";
            }
    }






}
