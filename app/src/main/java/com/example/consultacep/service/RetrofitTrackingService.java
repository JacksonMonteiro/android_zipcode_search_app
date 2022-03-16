package com.example.consultacep.service;

import com.example.consultacep.model.TrackingCode;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitTrackingService {
    @GET("json?")
    Call<TrackingCode> consultTrackingCode(@Query("user") String user,
                                           @Query("token") String token,
                                           @Query("codigo") String codigo);
}
