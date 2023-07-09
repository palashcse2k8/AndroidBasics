package com.example.androidbasics.apihandle;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @Headers({
            "Content-Type: application/json"
    })
    @POST("fetchSubmittedReturn")
    Call<QueryResponseModel> fetchSubmittedReturn(
            @Header("deviceId") String deviceId,
            @Body String base64Request
    );
}