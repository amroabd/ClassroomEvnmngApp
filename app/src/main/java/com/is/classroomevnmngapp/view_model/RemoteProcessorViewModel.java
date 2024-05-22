package com.is.classroomevnmngapp.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.is.classroomevnmngapp.data.repository.GetResultCallback;
import com.is.classroomevnmngapp.data.repository.remote.IRemoteDataSource;
import com.is.classroomevnmngapp.data.repository.remote.RemoteProviderRepository;

public class RemoteProcessorViewModel extends AndroidViewModel {

    private final RemoteProviderRepository sRemoteProviderRepository;

    public RemoteProcessorViewModel(@NonNull Application application) {
        super(application);
        sRemoteProviderRepository =new RemoteProviderRepository();
    }




    //inject any object implement to INetworkSource
    public void downloadData(@NonNull IRemoteDataSource iRemoteDataSource, GetResultCallback resultCallback){
        sRemoteProviderRepository.downloadData(iRemoteDataSource,resultCallback);
    }

    //inject any object implement to INetworkSource
    public void uploadingData(@NonNull IRemoteDataSource iRemoteDataSource, GetResultCallback resultCallback){
        sRemoteProviderRepository.uploadingData(iRemoteDataSource,resultCallback);
    }

}
