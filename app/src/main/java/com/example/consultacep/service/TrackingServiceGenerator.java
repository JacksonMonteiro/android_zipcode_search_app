package com.example.consultacep.service;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrackingServiceGenerator {

    public static final String TRACKING_URL = "https://correios.contrateumdev.com.br/api/rastreio";

    public static <S> S CreateTrackingService(Class<S> serviceClass) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder().readTimeout(15, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TRACKING_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(client.build())
                .build();

        return retrofit.create(serviceClass);
    }
}
