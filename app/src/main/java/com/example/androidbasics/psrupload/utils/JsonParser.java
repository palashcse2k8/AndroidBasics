package com.example.androidbasics.psrupload.utils;

import android.content.Context;

import com.example.androidbasics.apihandle.QueryResponseModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {

    public static List<QueryResponseModel> getAssessmentResultsList(Context context) {
        ArrayList<QueryResponseModel> queryResponseModels = new ArrayList<>();

        try {
            // Read JSON file from assets folder
            InputStream inputStream = context.getAssets().open("query_response_all.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");

            // Parse JSON data
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Create QueryResponseModel object and add to the list
                QueryResponseModel responseModel = new QueryResponseModel();
                responseModel.setMobileNo(jsonObject.getString("mobileNo"));
                responseModel.setCustomerName(jsonObject.getString("customerName"));
                responseModel.setAssessmentYear(jsonObject.getString("assessmentYear"));
                responseModel.setTin(jsonObject.getString("tin"));
                responseModel.setTrStatus(jsonObject.getString("trStatus"));
                responseModel.setTrUploadDate(jsonObject.getString("trUploadDate"));
                responseModel.setPrimaryAccount(jsonObject.getString("primaryAccount"));
                // Set other fields similarly

                queryResponseModels.add(responseModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return queryResponseModels;
    }

    public static List<QueryResponseModel> getAssessmentResultsList(Context context, String fileName) {
        ArrayList<QueryResponseModel> queryResponseModels = new ArrayList<>();

        try {
            // Read JSON file from assets folder
            InputStream inputStream = context.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");

            // Parse JSON data
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Create QueryResponseModel object and add to the list
                QueryResponseModel responseModel = new QueryResponseModel();
                responseModel.setMobileNo(jsonObject.getString("mobileNo"));
                responseModel.setCustomerName(jsonObject.getString("customerName"));
                responseModel.setAssessmentYear(jsonObject.getString("assessmentYear"));
                responseModel.setTin(jsonObject.getString("tin"));
                responseModel.setTrStatus(jsonObject.getString("trStatus"));
                responseModel.setTrUploadDate(jsonObject.getString("trUploadDate"));
                responseModel.setPrimaryAccount(jsonObject.getString("primaryAccount"));
                // Set other fields similarly

                queryResponseModels.add(responseModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return queryResponseModels;
    }
}
