package com.example.consultacep.service;

import com.example.consultacep.model.trackingCode;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitTrackingService {
    @GET("{CODE}")
    Call<trackingCode> consultTrackingCode(@Path("CODE") String CODE);
}
