package com.example.bilmbelairlangga.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponLog {
    @SerializedName("success")
    private String success;
    @SerializedName ("message")
    private String message;
    @SerializedName ("DataLog")
    List<DataLog> data;

    public List<DataLog> getData() {
        return data;
    }

    public void setResult(List<DataLog> result) {
        this.data = result;
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

