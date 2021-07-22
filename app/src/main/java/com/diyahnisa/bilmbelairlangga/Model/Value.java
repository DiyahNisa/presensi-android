package com.diyahnisa.bilmbelairlangga.Model;

public class Value {

    String success;
    String message;
    String karyawan_kode;

    public Value(String success, String message, String karyawan_kode) {
        this.success = success;
        this.message = message;
        this.karyawan_kode = karyawan_kode;
    }

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getKaryawan_kode() {
        return karyawan_kode;
    }
}
