package com.is.classroomevnmngapp.ui.auth.login.data;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("token")
    private String token;

    @SerializedName("user_id")
    private String userId;
    @SerializedName("user_type")
    private int userType;

    public String getToken() {
        return token;
    }

    public String getUserId() {
        return userId;
    }

    public int getUserType() {
        return userType;
    }
}
