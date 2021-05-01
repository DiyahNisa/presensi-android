package com.example.bilmbelairlangga.Model;

public class Value {

    String success;
    String message;

    public Value(String success, String message) {
        this.success = success;
        this.message = message;
    }

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
