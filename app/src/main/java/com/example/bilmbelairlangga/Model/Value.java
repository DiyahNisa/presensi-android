package com.example.bilmbelairlangga.Model;

public class Value {

    String success;
    String message;
    String karyawan_id;

    public Value(String success, String message, String karyawan_id) {
        this.success = success;
        this.message = message;
        this.karyawan_id = karyawan_id;
    }

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getKaryawan_id() {
        return karyawan_id;
    }
}
