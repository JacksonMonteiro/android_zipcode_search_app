package com.example.consultacep.service;

import com.example.consultacep.model.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Field;

public interface RetrofitService {
    Call<ServerResponse> useService(@Field("from-type") String from_type,
                                    @Field("from-value") String from_value,
                                    @Field("to-type") String to_type);
}
