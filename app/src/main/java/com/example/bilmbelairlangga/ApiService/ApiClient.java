package com.example.bilmbelairlangga.ApiService;

import com.example.bilmbelairlangga.Model.DataIzin;
import com.example.bilmbelairlangga.Model.DataLog;
import com.example.bilmbelairlangga.Model.ResponIzin;
import com.example.bilmbelairlangga.Model.ResponLog;
import com.example.bilmbelairlangga.Model.ResponPresensi;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiClient {

    //--------------------------------- MENU LOG PRESENSI ------------------------------------//
    @FormUrlEncoded
    @POST("presensi")
    Call<ResponPresensi> addPresensi(@Field("karyawan_id") String karyawan_id,
                                     @Field("tglPresensi") String tglPresensi,
                                     @Field("waktuPresensi") String waktuPresensi,
                                     @Field("ketPresensi") String ketPresensi,
                                     @Field("lokasiRuang") String lokasiRuang);

    //--------------------------------- MENU LOG BOOK --------------------------------------------//
    @Multipart
    @POST("logBook")
    Call<ResponLog> addLog(
            @Part("karyawan_id") RequestBody karyawan_id,
            @Part("tglKegiatan") RequestBody tglKegiatan,
            @Part("waktuMulai") RequestBody waktuMulai,
            @Part("waktuSelesai") RequestBody waktuSelesai,
            @Part("ketLog") RequestBody ketLog,
            @Part MultipartBody.Part buktiFoto);

    @GET("logBook/{karyawan_id}")
    Call<List<DataLog>> getLog1(@Path("karyawan_id") String karyawan_id);

    //--------------------------------- MENU IZIN --------------------------------------------//
    @Multipart
    @POST("izin")
    Call<ResponIzin> addIzin(
            @Part("karyawan_id")RequestBody karyawan_id,
            @Part("tglIzin")RequestBody tglIzin,
            @Part("tglSelesai")RequestBody tglSelesai,
            @Part("durasi")RequestBody durasi,
            @Part MultipartBody.Part buktiSurat);

    @GET("izin/{karyawan_id}")
    Call<List<DataIzin>> getIzin(@Path("karyawan_id") String karyawan_id);

}
