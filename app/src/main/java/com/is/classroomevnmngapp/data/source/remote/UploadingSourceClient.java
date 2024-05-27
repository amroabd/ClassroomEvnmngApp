package com.is.classroomevnmngapp.data.source.remote;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.is.classroomevnmngapp.data.model.ResponseObj;
import com.is.classroomevnmngapp.data.source.local.entities.LectureHallEntity;
import com.is.classroomevnmngapp.data.source.local.entities.ReservationEntity;
import com.is.classroomevnmngapp.data.source.local.entities.UserEntity;
import com.is.classroomevnmngapp.data.source.remote.api.ApiService;
import com.is.classroomevnmngapp.data.source.remote.api.RestClient;
import com.is.classroomevnmngapp.utils.Log1;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadingSourceClient {
    private static final String TAG = "UploadCentral";
    private final ApiService apiService;

    public UploadingSourceClient() {
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

    @SuppressLint("NewApi")
    private void request(@NotNull Call<?> headerCall) {
        Log1.w(TAG, "request:" + headerCall.request().toString());
        // Log1.w(TAG,"host:"+ headerCall.request().url().host());
        // Log1.w(TAG,"encodedPath:"+ headerCall.request().url().encodedPath());
        //Log1.w(TAG,"redact:"+ headerCall.request().url().redact());
        //Log1.w(TAG,"headers:"+headerCall.request().headers().toString());
    }

    private void PrintEvent(String msg) {
        Log1.PrintFileEvent(TAG, msg);
        Log1.d(TAG, msg);
    }

    private void PrintError(String msg) {
        Log1.PrintFileError(TAG, msg);
    }

    @NotNull
    private String getRespError(@NotNull String d) {
        return d.substring(0, d.lastIndexOf("url"));
    }


    //--------base handle  for  each task upload
    @NotNull
    private Callback<ResponseObj> mResponseCallback(int lId, String title, UploadCallback<ResponseObj> callback) {
        return new Callback<ResponseObj>() {
            @Override
            public void onResponse(@NotNull Call<ResponseObj> call, @NonNull Response<ResponseObj> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getCode().equals("Success")) {
                        ResponseObj obj = response.body();
                        obj.setlId(lId);
                        PrintEvent(String.format("1-onSuccess-> result %s :%s", title, obj));
                        callback.onSuccess(obj);
                        return;
                    }
                    PrintError(String.format("2-onResponse-> " + title + ":Empty response body - %s", response.body().toString()));
                    callback.onError(getRespError(response.toString()));
                    return;
                }
                PrintError(String.format("3-onResponse -> %s: unsuccessful Response  - %s", title, getRespError(response.toString())));
                callback.onError(String.format("3-onResponse -> %s unsuccessful Response - %s", title, getRespError(response.toString())));
            }

            @Override
            public void onFailure(@NotNull Call<ResponseObj> call, @NotNull Throwable t) {
                PrintError(String.format("4-onFailure-> %s : request failed - %s", title, t.toString()));
                callback.onError(String.format("4-onFailure-> %s%s", title, t.toString()));
            }
        };
    }

    private void uploadData(int loId, String title, @NonNull Call<ResponseObj> call, UploadCallback<ResponseObj> callback) {
        call.enqueue(mResponseCallback(loId, title, callback));
        requestBody(call);
    }

    //===========tasks =========================================

    public void uploadLectureHall(@NonNull List<LectureHallEntity> entities, UploadCallback<ResponseObj> callback) {
        //------------------
        try {
            for (LectureHallEntity entity : entities) {
                if (entity.getStatusUpload() == 0) {
                    Call<ResponseObj> subHeaderCall = apiService.createLectureHall(entity);
                    uploadData(entity.getLocalId(), "LectureHall", subHeaderCall, callback);
                } else if (entity.getStatusUpload() == 3) {
                    Call<ResponseObj> subHeaderCall = apiService.updateLectureHall(entity);
                    uploadData(entity.getLocalId(), "LectureHall", subHeaderCall, callback);
                }
            }
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void uploadReservation(@NonNull List<ReservationEntity> entities, UploadCallback<ResponseObj> callback) {
        try {
            for (ReservationEntity entity : entities) {
                if (entity.getStatusUpload() == 0) {
                    Call<ResponseObj> subHeaderCall = apiService.createReservation(entity);
                    uploadData(entity.getLocalId(), "Reservations", subHeaderCall, callback);
                } else if (entity.getStatusUpload() == 3) {
                    Call<ResponseObj> subHeaderCall = apiService.updateReservation(entity);
                    uploadData(entity.getLocalId(), "Reservations", subHeaderCall, callback);
                }
            }
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void uploadUser(List<UserEntity> entities, UploadCallback<ResponseObj> callback) {
        for (UserEntity entity : entities) {
            Call<ResponseObj> subHeaderCall = apiService.updateUser(entity);
            uploadData(entity.getLocalId(), "User", subHeaderCall, callback);
        }
    }


}
