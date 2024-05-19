package com.is.classroomevnmngapp.ui.auth.login.data;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("name")
    private final String userName;
    @SerializedName("password")
    private final String password;
    @SerializedName("type_user")
    private final int type_user;

    public LoginRequest(String userName, String password,int type_user) {
        this.userName = userName;
        this.password = password;
        this.type_user=type_user;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getType_user() {
        return type_user;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", type_user=" + type_user +
                '}';
    }
}
