package com.is.classroomevnmngapp.ui.auth.login.data;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("username")
    private final String userName;
    @SerializedName("password")
    private final String password;

    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
