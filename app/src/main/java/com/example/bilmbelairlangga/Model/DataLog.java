package com.example.bilmbelairlangga.Model;

import java.util.ArrayList;

public class ArrayLog {
    String tglKegiatan, waktuMulai, waktuSelesai,ketLog;
    String success;
    String message;

    public ResponLog(String tglKegiatan, String waktuMulai, String waktuSelesai, String ketLog, String success, String message) {
        this.tglKegiatan = tglKegiatan;
        this.waktuMulai = waktuMulai;
        this.waktuSelesai = waktuSelesai;
        this.ketLog = ketLog;
        this.success = success;
        this.message = message;
    }

    public String getTglKegiatan() {
        return tglKegiatan;
    }

    public void setTglKegiatan(String tglKegiatan) {
        this.tglKegiatan = tglKegiatan;
    }

    public String getWaktuMulai() {
        return waktuMulai;
    }

    public void setWaktuMulai(String waktuMulai) {
        this.waktuMulai = waktuMulai;
    }

    public String getWaktuSelesai() {
        return waktuSelesai;
    }

    public void setWaktuSelesai(String waktuSelesai) {
        this.waktuSelesai = waktuSelesai;
    }

    public String getKetLog() {
        return ketLog;
    }

    public void setKetLog(String ketLog) {
        this.ketLog = ketLog;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
