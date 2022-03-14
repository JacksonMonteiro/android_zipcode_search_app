package com.example.consultacep.service;

import android.content.Context;

import com.example.consultacep.helpers.CEPDeserializer;
import com.example.consultacep.model.Cep;
import com.example.consultacep.model.SimpleCallback;
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

public class CepServiceGenerator {

    public static final String CEP_URL = "https://viacep.com.br/ws/";

    private Context myContext;
    private RetrofitService service;

    public CepServiceGenerator(Context context) {
        this.myContext = context;
        initialize();
    }

    private void initialize() {
        Gson g = new GsonBuilder().registerTypeAdapter(Cep.class, new CEPDeserializer()).create();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.MINUTES)
                .readTimeout(30,TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CEP_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(g))
                .build();

        service = retrofit.create(RetrofitService.class);
        final RetrofitService service = retrofit.create(RetrofitService.class);
    }

    public void getCep(String cep, final SimpleCallback<Cep> callBack) {
        service.consultCep(cep).enqueue(new Callback<Cep>() {
            @Override
            public void onResponse(Call<Cep> call, Response<Cep> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callBack.onResponse(response.body());
                } else {
                    if (response.body() != null) {
                        callBack.onError("Error");
                    } else {
                        callBack.onError("Error");
                    }
                }
            }

            @Override
            public void onFailure(Call<Cep> call, Throwable t) {
                t.printStackTrace();
                callBack.onError(t.getMessage());
            }
        });
    }
}
