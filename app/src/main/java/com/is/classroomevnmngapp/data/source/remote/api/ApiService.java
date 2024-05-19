package com.is.classroomevnmngapp.data.source.remote.api;

import com.is.classroomevnmngapp.data.model.ResponseObj;
import com.is.classroomevnmngapp.data.source.local.entities.HelperEntity;
import com.is.classroomevnmngapp.data.source.local.entities.LectureHallEntity;
import com.is.classroomevnmngapp.data.source.local.entities.ReservationEntity;
import com.is.classroomevnmngapp.ui.auth.login.data.LoginRequest;
import com.is.classroomevnmngapp.ui.auth.login.data.LoginResponse;
import com.is.classroomevnmngapp.ui.auth.sigup.data.SigUpResponse;
import com.is.classroomevnmngapp.ui.auth.sigup.data.SignUpRequest;
import com.is.classroomevnmngapp.ui.setting.change_pass.data.ChangePassRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
/*
ApiService: This is the Retrofit interface that defines the API endpoint and the method to handle
the signUp request. In this case,
the signUp method takes a SignUpRequest object as a parameter and returns a SigUpResponse object.
 */
public interface ApiService {
    String BaseURL = "https://libscientific.000webhostapp.com/UniApi/";

    //====================================auth
    @POST("auth/login.php")
    Call<LoginResponse> authLogin(@Body LoginRequest user);
    //signUp method to send the signup request to the server.
    @POST("auth/signup.php")
    Call<SigUpResponse> signUp(@Body SignUpRequest signupRequest);

    @POST("auth/change_pass.php")
    Call<ResponseObj> change_pass(@Body ChangePassRequest user);
    //====================================Helper
    @Headers("Cache-Control: max-age=600000")
    @GET("Helper/HeaderData")
    Call<List<HelperEntity.MainMenu>> getHeaderData();

    @GET("Helper/SubData")
    Call<List<HelperEntity.SubMenu>> getSubHeaderData();

    //====================================base

    //-1----------------------
    @POST("LectureHall/Create.php")
    Call<ResponseObj> createLectureHall(@Body LectureHallEntity entity);

    @POST("LectureHall/Update.php")
    Call<ResponseObj> updateLectureHall(@Body LectureHallEntity entity);

    @GET("LectureHall/List.php")
    Call<List<LectureHallEntity>> getLectureHallsData();

    //-2----------------------
    @POST("Reservation/Create.php")
    Call<ResponseObj> createReservation(@Body ReservationEntity entity);

    @POST("Reservation/Update.php")
    Call<ResponseObj> updateReservation(@Body ReservationEntity entity);

    @GET("Reservation/List.php")
    Call<List<ReservationEntity>> getReservationsData();

}

