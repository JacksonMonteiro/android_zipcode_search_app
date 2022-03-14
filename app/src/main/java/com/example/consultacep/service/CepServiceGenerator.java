package com.example.consultacep.service;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CepServiceGenerator {

    public static final String CEP_URL = "https://viacep.com.br/ws/";

    public static <S> S createCepService(Class<S> serviceClass) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder().readTimeout(15, TimeUnit.SECONDS);
        client.addInterceptor(interceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CEP_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(client.build())
                .build();

        return retrofit.create(serviceClass);
    }
}
