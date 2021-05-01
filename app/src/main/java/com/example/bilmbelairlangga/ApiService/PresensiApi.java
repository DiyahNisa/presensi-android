package com.example.bilmbelairlangga.ApiService;

import com.example.bilmbelairlangga.Model.ResponPresensi;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PresensiApi {

    @FormUrlEncoded
    @POST("presensi")
    Call<ResponPresensi> addPresensi(@Field("tglPresensi") String tglPresensi,
                                     @Field("waktuPresensi") String waktuPresensi);
}
