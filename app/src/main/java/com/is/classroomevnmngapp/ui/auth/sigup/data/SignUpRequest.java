package com.is.classroomevnmngapp.ui.auth.sigup.data;

import com.google.gson.annotations.SerializedName;

    /*
    The SignUpRequest object is used to encapsulate the data that needs to be sent to the server.
    SignUpRequest: This is the request object that will be sent to the server.
    It contains the necessary fields for the signup process, such as name, email, password, phone, and type.
     */
public class SignUpRequest {

    @SerializedName("name")
    private final String name;
    @SerializedName("email")
    private final String email;
    @SerializedName("password")
    private final String password;
    @SerializedName("phone")
    private final String phone;
    @SerializedName("type_user")
    private final int type_user;

    public SignUpRequest(String name, String email, String password, String phone, int type_user) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.type_user = type_user;
    }

    public int getType_user() {
        return type_user;
    }

    public String getName() {
        return name;
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
