package com.is.classroomevnmngapp.data.source.remote;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;

import com.is.classroomevnmngapp.data.source.local.entities.HelperEntity;
import com.is.classroomevnmngapp.data.source.local.entities.LectureHallEntity;
import com.is.classroomevnmngapp.data.source.local.entities.ReservationEntity;
import com.is.classroomevnmngapp.data.source.remote.api.ApiService;
import com.is.classroomevnmngapp.data.source.remote.api.RestClient;
import com.is.classroomevnmngapp.utils.Log1;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadCentral {
    private static final String TAG = DownloadCentral.class.getSimpleName();
    private final Context mContext;
    private final ApiService apiService;


    public DownloadCentral(Context context) {
        this.mContext = context;
        this.apiService = RestClient.getInstance().getApiService();
    }


    @SuppressLint("NewApi")
    private void request(@NotNull Call<?> headerCall) {
        Log1.w(TAG, "request:" + headerCall.request().toString());
        Log1.w(TAG, "host:" + headerCall.request().url().host());
        Log1.w(TAG, "encodedPath:" + headerCall.request().url().encodedPath());
        //Log1.w(TAG,"redact:"+ headerCall.request().url().redact());
        Log1.w(TAG, "headers:" + headerCall.request().headers().toString());
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

    //==================base handle for each task download
    @NotNull
    private <T> Callback<T> mResponseCallback(Long lId, String title, DownloadCallback<T> callback) {
        return new Callback<T>() {
            @Override
            public void onResponse(@NotNull Call<T> call, @NonNull Response<T> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        PrintEvent(String.format("1-onSuccess-> result %s :%s", title, response.body().toString()));
                        callback.onSuccess(response.body());
                        return;
                    }
                    PrintError(String.format("2-onResponse-> " + title + ":Empty response body - %s", response.toString()));
                    callback.onError(getRespError(response.toString()));
                    return;
                }
                PrintError(String.format("3-onResponse -> %s: unsuccessful Response  - %s", title, getRespError(response.toString())));
                callback.onError(String.format("3-onResponse -> %s unsuccessful Response - %s", title, getRespError(response.toString())));
            }

            @Override
            public void onFailure(@NotNull Call<T> call, @NotNull Throwable t) {
                PrintError(String.format("4-onFailure-> %s : request failed - %s", title, t.toString()));
                callback.onError(String.format("4-onFailure-> %s%s", title, t.toString()));
            }
        };
    }

    private <T> void downloadData(String title, @NonNull Call<T> call, DownloadCallback<T> callback) {
        long defaultID=9;
        call.enqueue(mResponseCallback(defaultID, title, callback));
    }
   //==================

    /***
     * 1-down data main menu
     * @param callBackDown return list main menu to callback for save in db
     */
    public void downAllHeaderData(DownloadCallback<List<HelperEntity.MainMenu>> callBackDown) {
        //------------------
        final Call<List<HelperEntity.MainMenu>> headerCall = apiService.getHeaderData();
        //request(headerCall);
        downloadData("MainMenu", headerCall, callBackDown);
    }

    /***
     * 2-down data sub menu
     * @param callbackDown return list sub menu to callback for save in db
     */
    public void downAllSubHeaderData(DownloadCallback<List<HelperEntity.SubMenu>> callbackDown) {
        //------------------
        Call<List<HelperEntity.SubMenu>> subHeaderCall = apiService.getSubHeaderData();
        downloadData("SubMenu", subHeaderCall, callbackDown);
    }

    public void downLoadLectureHalls(DownloadCallback<List<LectureHallEntity>> callback) {
        //------------------
        Call<List<LectureHallEntity>> subHeaderCall = apiService.getLectureHallsData();
        downloadData("LectureHall", subHeaderCall, callback);
    }

    public void downLoadReservations(DownloadCallback<List<ReservationEntity>> callback) {
        //------------------
        Call<List<ReservationEntity>> subHeaderCall = apiService.getReservationsData();
        downloadData("Reservations", subHeaderCall, callback);
    }


}
