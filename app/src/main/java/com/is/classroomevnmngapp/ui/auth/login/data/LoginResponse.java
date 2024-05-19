package com.is.classroomevnmngapp.ui.auth.login.data;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("Data")
    private String userId;
    @SerializedName("Code")
    private String code;


    public String getUserId() {
        return userId;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "userId='" + userId + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
