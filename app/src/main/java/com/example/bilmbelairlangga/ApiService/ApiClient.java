package com.example.bilmbelairlangga.ApiService;

import com.example.bilmbelairlangga.Model.ResponIzin;
import com.example.bilmbelairlangga.Model.ResponLog;
import com.example.bilmbelairlangga.Model.ResponPresensi;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiClient {

    @FormUrlEncoded
    @POST("presensi")
    Call<ResponPresensi> addPresensi(@Field("tglPresensi") String tglPresensi,
                                     @Field("waktuPresensi") String waktuPresensi);

    //Menu Log Book
    @FormUrlEncoded
    @POST("logBook")
    Call<ResponLog> addLog();

    @FormUrlEncoded
    @GET("logBook")
    Call<ResponLog> getLog();

    @FormUrlEncoded
    @DELETE("logBook")
    Call<ResponLog> delLog();

    //Menu Izin
    @FormUrlEncoded
    @POST("izin")
    Call<ResponIzin> addIzin();

    @FormUrlEncoded
    @GET("izin")
    Call<ResponIzin> getIzin();

}
