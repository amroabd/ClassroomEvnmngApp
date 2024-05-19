package com.is.classroomevnmngapp.data.source.remote.api;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    private static RestClient mInstance;
    private final ApiService apiService;

    private RestClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService.BaseURL)
                .addConverterFactory(GsonConverterFactory.create(/*gson*/)).build();
        apiService = retrofit.create(ApiService.class);
    }

    public static synchronized RestClient getInstance() {
        if (mInstance==null)mInstance=new RestClient();
        return mInstance;
    }

    public ApiService getApiService(){
        return apiService;
    }



}
