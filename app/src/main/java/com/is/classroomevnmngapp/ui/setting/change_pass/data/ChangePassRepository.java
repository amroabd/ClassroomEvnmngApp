package com.is.classroomevnmngapp.ui.setting.change_pass.data;

import androidx.annotation.NonNull;

import com.is.classroomevnmngapp.data.model.ResponseObj;
import com.is.classroomevnmngapp.data.source.remote.UploadCallback;
import com.is.classroomevnmngapp.data.source.remote.api.ApiService;
import com.is.classroomevnmngapp.data.source.remote.api.RestClient;
import com.is.classroomevnmngapp.utils.Log1;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassRepository {
    private static final String TAG = "ChangePassRepository";
    ApiService apiService;

    public ChangePassRepository() {
        this.apiService = RestClient.getInstance().getApiService();
    }

    private void requestBody(@NotNull Call<ResponseObj> call) {
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



    public void changePassRequest(ChangePassRequest changePassRequest, UploadCallback<ResponseObj> callback) {
        // method sends the ChangePassRequest object to the server and returns a Call<ResponseObj> object.
        Call<ResponseObj> sigUpResponseCall = apiService.change_pass(changePassRequest);
        requestBody(sigUpResponseCall);
        //enqueue : method is used to handle the response asynchronously, with callbacks for successful and failed responses.
        sigUpResponseCall.enqueue(new Callback<ResponseObj>() {
            @Override
            public void onResponse(@NonNull Call<ResponseObj> call, @NonNull Response<ResponseObj> response) {
                Log1.d(TAG, "response :" + response.body());
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                    return;
                }
                assert response.errorBody() != null;
                callback.onError((response.errorBody().toString()));
            }

            @Override
            public void onFailure(@NonNull Call<ResponseObj> call, @NonNull Throwable t) {
                callback.onError((t.toString()));
                Log1.e(TAG, "Err :" + t.toString());
                t.printStackTrace();
            }
        });
    }




}
