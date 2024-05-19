package com.is.classroomevnmngapp.ui.auth.sigup.data;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.is.classroomevnmngapp.data.source.remote.api.ApiService;
import com.is.classroomevnmngapp.data.source.remote.api.RestClient;
import com.is.classroomevnmngapp.ui.auth.sigup.SignUpCallback;
import com.is.classroomevnmngapp.utils.Log1;
import com.is.classroomevnmngapp.utils.executor.AppExecutor;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.is.classroomevnmngapp.data.source.remote.api.ApiService.BaseURL;

public class SignUpRepository {
    private static final String TAG = "SignUpRepository";
    ApiService apiService;

    public SignUpRepository() {
        this.apiService = RestClient.getInstance().getApiService();
    }

    private void requestBody(@NotNull Call<SigUpResponse> call) {
        // Get the request body
        RequestBody requestBody = call.request().body();
        // Convert the request body to a string
        String requestBodyString;
        if (requestBody != null) {
            Buffer buffer = new Buffer();
            try {
                requestBody.writeTo(buffer);
                requestBodyString = buffer.readUtf8();
                Log1.d(TAG, requestBodyString.length() + "request:" + requestBodyString);
                //SharePerf.getInstance(mContext).addLog(requestBodyString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void signUp(SignUpRequest signUpRequest, SignUpCallback callback){
        signUp(signUpRequest,callback);
    }

    public void signUp0(SignUpRequest signUpRequest, SignUpCallback callback) {
        // method sends the SignUpRequest object to the server and returns a Call<SigUpResponse> object.
        Call<SigUpResponse> sigUpResponseCall = apiService.signUp(signUpRequest);
        requestBody(sigUpResponseCall);
        //enqueue : method is used to handle the response asynchronously, with callbacks for successful and failed responses.
        sigUpResponseCall.enqueue(new Callback<SigUpResponse>() {
            @Override
            public void onResponse(@NonNull Call<SigUpResponse> call, @NonNull Response<SigUpResponse> response) {
                Log1.d(TAG, "response :" + response.body());
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                    return;
                }
                assert response.errorBody() != null;
                callback.onFailure(new Exception(response.errorBody().toString()));
            }

            @Override
            public void onFailure(@NonNull Call<SigUpResponse> call, @NonNull Throwable t) {
                callback.onFailure(new Exception(t.toString()));
                Log1.e(TAG, "Err :" + t.toString());
                t.printStackTrace();
            }
        });
    }

    public final void setSignUp02(SignUpRequest signUpRequest, SignUpCallback callback) {
        OkHttpClient client = new OkHttpClient();

        String url = BaseURL + "auth/signup.php";

        // Create the request body using the SignUpRequest object
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json"),
                new Gson().toJson(signUpRequest)
        );

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        AppExecutor.getInstance().diskIO().execute(() -> {
            try {
                okhttp3.Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    if (responseBody != null) {
                        String data = responseBody.string();
                        System.out.println("Signup response: " + data);
                        // Handle the successful signup response
                        AppExecutor.getInstance().mainThread().execute(() -> callback.onSuccess(new SigUpResponse("sss",data,"1")));
                    }
                } else {
                    System.out.println("Error: Failed to sign up, status code: " + response.code() + ", response: " + response.toString());
                    // Handle the error response
                    AppExecutor.getInstance().mainThread().execute(() -> callback.onFailure(new Exception("Error: Failed to sign up, status code: " + response.code() + ", response: " + response.toString())));
                }
            } catch (Exception e) {
                System.out.println("Error: Failed to sign up, exception: " + e.getMessage());
                e.printStackTrace();
                // Handle the exception
                AppExecutor.getInstance().mainThread().execute(() ->callback.onFailure(new Exception(e.getMessage())));
            }
        });
    }



}
