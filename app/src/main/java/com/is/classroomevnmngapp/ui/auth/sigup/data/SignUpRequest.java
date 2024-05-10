package com.is.classroomevnmngapp.ui.auth.sigup.data;

import com.google.gson.annotations.SerializedName;

public class SignUpRequest {

    @SerializedName("username")
    private final String userName;
    @SerializedName("password")
    private final String password;
    @SerializedName("email")
    private final String email;
    @SerializedName("phone")
    private final String phone;

    public SignUpRequest(String userName, String password, String email, String phone) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
