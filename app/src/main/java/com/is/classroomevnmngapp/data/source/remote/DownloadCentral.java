package com.is.classroomevnmngapp.data.source.remote;

import android.annotation.SuppressLint;
import android.content.Context;

import com.is.classroomevnmngapp.data.source.local.entities.HelperEntity;
import com.is.classroomevnmngapp.data.source.remote.api.ApiService;
import com.is.classroomevnmngapp.data.source.remote.api.RestClient;
import com.is.classroomevnmngapp.utils.Log1;
import com.is.classroomevnmngapp.utils.executor.AppExecutor;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadCentral {
    private static final String TAG = DownloadCentral.class.getSimpleName();
    private final Context mContext;
    private final ApiService apiService;


    public DownloadCentral(Context context) {
        this.mContext = context;
        this.apiService= RestClient.getInstance().getApiService();
    }

    @SafeVarargs
    public final void downAllHeaderData1(DownloadCallback<JSONArray>... backDownVolley) {
        //----------------------------------------
        OkHttpClient client = new OkHttpClient();

        String url = "https://host/api/v1/Helper/HeaderData";

        okhttp3.Request request = new okhttp3.Request.Builder().url(url).build();

            AppExecutor.getInstance().diskIO().execute(() -> {
                try {
                okhttp3.Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    if (responseBody != null) {
                        String data = responseBody.string();

                        System.out.println(data);
                    }
                } else {
                    //
                    System.out.println("Error:حدث خطأ في الاستجابة, " + response.code() + " " + response.toString());
                }
            } catch (Exception e) {
                // حدث خطأ في الطلب
                e.printStackTrace();
            }
            });


    }

    /***
     * 1-down data main menu
     * @param callBackDown return list main menu to callback for save in db
     */
    public void downAllHeaderData(DownloadCallback<List<HelperEntity.MainMenu>> callBackDown) {
        //downAllHeaderData1();
        //------------------
        final Call<List<HelperEntity.MainMenu>> headerCall = apiService.getHeaderData();
        //request(headerCall);
        headerCall.enqueue(new Callback<List<HelperEntity.MainMenu>>() {
            @Override
            public void onResponse(@NotNull Call<List<HelperEntity.MainMenu>> call, @NotNull Response<List<HelperEntity.MainMenu>> response) {
                if (response.isSuccessful()) {
                    Log1.PrintFileEvent(TAG, "1-onSuccess-> result1MainMenu :" + (response.body() != null ? response.body().toString() : null));
                    callBackDown.onSuccess(response.body());
                    return;
                }
                //Log1.PrintFileError(TAG, "2-onResponse-> MainMenu:Empty response body - " + response.toString());
                callBackDown.onError(getResp(response.toString()));
            }

            @Override
            public void onFailure(@NotNull Call<List<HelperEntity.MainMenu>> call, @NotNull Throwable t) {
                Log1.PrintFileError(TAG, "3-onFailure-> MainMenu: request failed - " + t.toString());
                callBackDown.onError("onFailure-> MainMenu: request failed - " + t.toString());
            }
        });
    }
    @NotNull
    private String getResp(@NotNull String d){
        return d.substring(0,d.lastIndexOf("url"));
    }

    @SuppressLint("NewApi")
    private void request(@NotNull  Call<?> headerCall ) {
        Log1.w(TAG,"request:"+ headerCall.request().toString());
        Log1.w(TAG,"host:"+ headerCall.request().url().host());
        Log1.w(TAG,"encodedPath:"+ headerCall.request().url().encodedPath());
        //Log1.w(TAG,"redact:"+ headerCall.request().url().redact());
        Log1.w(TAG,"headers:"+headerCall.request().headers().toString());
        //Log1.w(TAG,"cacheControl:"+ headerCall.request().cacheControl().maxAgeSeconds()+","+headerCall.request().cacheControl().minFreshSeconds());
    }


    /***
     * 2-down data sub menu
     * @param callbackDown return list sub menu to callback for save in db
     */
    public void downAllSubHeaderData(DownloadCallback<List<HelperEntity.SubMenu>> callbackDown) {
        //------------------
        Call<List<HelperEntity.SubMenu>> subHeaderCall = apiService.getSubHeaderData();
        subHeaderCall.enqueue(new Callback<List<HelperEntity.SubMenu>>() {
            @Override
            public void onResponse(@NotNull Call<List<HelperEntity.SubMenu>> call, @NotNull Response<List<HelperEntity.SubMenu>> response) {
                if (response.isSuccessful()) {
                    Log1.d(TAG, "onSuccess:");
                    //Log1.PrintFileEvent(TAG, "onSuccess-> resultSubMenu :" + (response.body() != null ? response.body().toString() : null));
                    callbackDown.onSuccess(response.body());
                    return;
                }
                Log1.PrintFileError(TAG, "onResponse-> SubMenu :Empty response body - " + response.toString());
                callbackDown.onError(getResp(response.toString()));
            }

            @Override
            public void onFailure(@NotNull Call<List<HelperEntity.SubMenu>> call, @NotNull Throwable t) {
                Log1.PrintFileError(TAG, "onFailure-> SubMenu : request failed - " + t.toString());
                callbackDown.onError(t.toString());
            }
        });
    }




}
