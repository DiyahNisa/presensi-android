package com.diyahnisa.bilmbelairlangga.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponIzin {

    @SerializedName("success")
    private String success;
    @SerializedName("message")
    private String message;
    @SerializedName ("DataIzin")
    private List<DataIzin> result;

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

    public List<DataIzin> getResult() {
        return result;
    }

    public void setResult(List<DataIzin> result) {
        this.result = result;
    }
}
