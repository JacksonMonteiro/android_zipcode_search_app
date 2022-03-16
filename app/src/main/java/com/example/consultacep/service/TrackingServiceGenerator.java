package com.example.consultacep.service;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrackingServiceGenerator {
<<<<<<< HEAD
    public static final String TRACKING_URL = "https://api.linketrack.com/track/";

    public static final String USER = "teste";
    public static final String TOKEN = "1abcd00b2731640e886fb41a8a9671ad1434c599dbaa0a0de9a5aa619f29a83f";

    private Context context;
    private RetrofitTrackingService service;
=======
>>>>>>> parent of 0a08ed1 (Tracking Service Generator class)

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

<<<<<<< HEAD
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
=======
        return retrofit.create(serviceClass);
>>>>>>> parent of 0a08ed1 (Tracking Service Generator class)
    }
}
