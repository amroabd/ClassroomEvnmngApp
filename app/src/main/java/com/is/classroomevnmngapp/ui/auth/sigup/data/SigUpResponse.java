package com.is.classroomevnmngapp.ui.auth.sigup.data;

import com.google.gson.annotations.SerializedName;

public class SigUpResponse {
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

    public SigUpResponse(String data, String code) {
        this.data = data;
        this.code = code;
    }
}
