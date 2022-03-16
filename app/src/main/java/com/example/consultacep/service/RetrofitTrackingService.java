package com.example.consultacep.service;

import com.example.consultacep.model.trackingCode;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitTrackingService {
<<<<<<< HEAD
    @GET("json?")
    Call<TrackingCode> consultTrackingCode(@Query("user") String user,
                                           @Query("token") String token,
                                           @Query("codigo") String codigo);
=======
    @GET("{CODE}")
    Call<trackingCode> consultTrackingCode(@Path("CODE") String CODE);
>>>>>>> parent of 0a08ed1 (Tracking Service Generator class)
}
