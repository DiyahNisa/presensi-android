package com.example.bilmbelairlangga.ApiService;

import com.example.bilmbelairlangga.Model.Value;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginApi {
    // Fungsi ini untuk memanggil API "http://192.168.0.104/airlanggaBimbel/public/api/login"
    @FormUrlEncoded
    @POST("login")
    Call<Value> login(@Field("username") String username,
                      @Field("password") String password);
}
