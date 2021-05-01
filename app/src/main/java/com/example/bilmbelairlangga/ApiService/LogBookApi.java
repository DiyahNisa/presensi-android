package com.example.bilmbelairlangga.ApiService;

import com.example.bilmbelairlangga.Model.Value;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LogBookApi {

    @FormUrlEncoded
    @POST("logBook")
    Call<Value> addPresensi(@Field("tglPresensi") String tglPresensi,
                            @Field("waktuPresensi") String waktuPresensi);
}
