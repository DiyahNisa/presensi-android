package com.diyahnisa.bilmbelairlangga.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("tglPresensi")
    @Expose
    private String tglPresensi;
    @SerializedName("waktuPresensi")
    @Expose
    private String waktuPresensi;
    @SerializedName("idPresensi")
    @Expose
    private Integer idPresensi;

    public String getTglPresensi() {
        return tglPresensi;
    }

    public void setTglPresensi(String tglPresensi) {
        this.tglPresensi = tglPresensi;
    }

    public String getWaktuPresensi() {
        return waktuPresensi;
    }

    public void setWaktuPresensi(String waktuPresensi) {
        this.waktuPresensi = waktuPresensi;
    }

    public Integer getIdPresensi() {
        return idPresensi;
    }

    public void setIdPresensi(Integer idPresensi) {
        this.idPresensi = idPresensi;
    }

}