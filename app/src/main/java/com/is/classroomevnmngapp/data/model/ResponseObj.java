package com.is.classroomevnmngapp.data.model;

import com.google.gson.annotations.SerializedName;

public class ResponseObj {
    @SerializedName("data")
    private final String data;
    @SerializedName("code")
    private final String code;


    public String getData() {
        return data;
    }

    public String getCode() {
        return code;
    }

    public ResponseObj(String data, String code) {
        this.data = data;
        this.code = code;
    }
}
