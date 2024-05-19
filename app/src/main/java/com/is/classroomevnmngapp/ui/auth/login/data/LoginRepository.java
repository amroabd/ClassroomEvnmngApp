package com.is.classroomevnmngapp.ui.auth.login.data;

import androidx.annotation.NonNull;

import com.is.classroomevnmngapp.data.source.remote.api.ApiService;
import com.is.classroomevnmngapp.data.source.remote.api.RestClient;
import com.is.classroomevnmngapp.ui.auth.login.LoginCallback;
import com.is.classroomevnmngapp.utils.Log1;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    ApiService apiService;
    private static final String TAG = "LoginRepository";
    public LoginRepository() {
        this.apiService = RestClient.getInstance().getApiService();
    }
    public void login(LoginRequest signUpRequest, LoginCallback callback) {
        // method sends the LoginRequest object to the server and returns a Call<LoginResponse> object.
        Call<LoginResponse> sigUpResponseCall = apiService.authLogin(signUpRequest);
       // requestBody(sigUpResponseCall);
        //enqueue : method is used to handle the response asynchronously, with callbacks for successful and failed responses.
        sigUpResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body()!=null) {
                        Log1.d(TAG, "response :" + response.body().toString());
                        callback.onSuccess(response.body());
                        return;
                    }
                    Log1.d(TAG, "response empty :" + response.errorBody().toString());
                    return;
                }
                assert response.errorBody() != null;
                callback.onFailure(new Exception(response.errorBody().toString()));
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                callback.onFailure(new Exception(t.toString()));
                Log1.e(TAG, "Err :" + t.toString());
                t.printStackTrace();
            }
        });
    }
}
