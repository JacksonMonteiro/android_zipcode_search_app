package com.example.consultacep.service;

import android.content.Context;

import com.example.consultacep.model.SimpleCallback;
import com.example.consultacep.model.TrackingCode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrackingServiceGenerator {
    public static final String TRACKING_URL = "https://api.linketrack.com/track/";

    public static final String USER = "teste";
    public static final String TOKEN = "1abcd00b2731640e886fb41a8a9671ad1434c599dbaa0a0de9a5aa619f29a83f";

    private Context context;
    private RetrofitTrackingService service;

    public TrackingServiceGenerator(Context context) {
        this.context = context;
        initialize();
    }

    public void initialize() {
        Gson g = new GsonBuilder().create();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TRACKING_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(g))
                .build();

        service = retrofit.create(RetrofitTrackingService.class);
        final RetrofitTrackingService service = retrofit.create(RetrofitTrackingService.class);
    }

    public void getTracking(String code, final SimpleCallback<TrackingCode> callback) {
        service.consultTrackingCode(USER, TOKEN, code).enqueue(new Callback<TrackingCode>() {
            @Override
            public void onResponse(Call<TrackingCode> call, Response<TrackingCode> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResponse(response.body());
                } else {
                    if (response.body() != null ) {
                        callback.onError("Error");
                    } else {
                        callback.onError("Error");
                    }
                }
            }

            @Override
            public void onFailure(Call<TrackingCode> call, Throwable t) {
                t.printStackTrace();
                callback.onError(t.getMessage());
            }
        });
    }
}
