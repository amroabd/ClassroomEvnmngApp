package com.is.classroomevnmngapp.data.source.remote.api;

import com.is.classroomevnmngapp.data.source.local.entities.HelperEntity;
import com.is.classroomevnmngapp.ui.auth.login.data.LoginRequest;
import com.is.classroomevnmngapp.ui.auth.login.data.LoginResponse;
import com.is.classroomevnmngapp.ui.auth.sigup.data.SigUpResponse;
import com.is.classroomevnmngapp.ui.auth.sigup.data.SignUpRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    String BaseURL = "https://google.com";
    //====================================
    @POST("Authentication/Login")
    Call<LoginResponse> authenticationLogin(@Body LoginRequest user);
    @POST("Register/SignUp")
    Call<SigUpResponse> signUp(@Body SignUpRequest user);

    @Headers("Cache-Control: max-age=600000")
    @GET("Helper/HeaderData")
    Call<List<HelperEntity.MainMenu>> getHeaderData();
    @GET("Helper/SubData")
    Call<List<HelperEntity.SubMenu>> getSubHeaderData();
    //-4----------------------
}

