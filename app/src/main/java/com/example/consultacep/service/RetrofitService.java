package com.example.consultacep.service;

import com.example.consultacep.model.Cep;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitService {
    @GET("{CEP}/json")
    Call<Cep> consultCep(@Path("CEP") String CEP);
}
