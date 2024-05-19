package com.is.classroomevnmngapp.ui.auth.sigup.data;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/*
SigUpResponse: This is the response object that will be
 returned from the server. It contains the Code and Data fields.
 */
public class SigUpResponse {
    @SerializedName("Code")
    private final String code;
    @SerializedName("Data")
    private final String data;
    @SerializedName("ID")
    private final String ID;

    public String getCode() {
        return code;
    }

    public String getData() {
        return data;
    }

    public String getID() {
        return ID;
    }

    public SigUpResponse(String data, String code,String ID) {
        this.data = data;
        this.code = code;
        this.ID=ID;
    }

    @NonNull
    @Override
    public String toString() {
        return "SigUpResponse{" +
                "code='" + code + '\'' +
                ", data='" + data + '\'' +
                ", ID='" + ID + '\'' +
                '}';
    }
}
