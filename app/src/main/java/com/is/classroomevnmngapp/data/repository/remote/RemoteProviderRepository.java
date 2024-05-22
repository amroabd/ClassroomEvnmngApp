package com.is.classroomevnmngapp.data.repository.remote;

import androidx.annotation.NonNull;

import com.is.classroomevnmngapp.data.repository.GetResultCallback;

public class RemoteProviderRepository {
  /*  IRemoteRepository remoteRepository;

    public RemoteProviderRepository(IRemoteRepository remoteRepository) {
        this.remoteRepository = remoteRepository;
    }*/

    public void downloadData(@NonNull IRemoteDataSource remoteRepository, GetResultCallback resultCallback){
        remoteRepository.downloadData(resultCallback);
    }

    public void uploadingData(@NonNull IRemoteDataSource remoteRepository, GetResultCallback resultCallback){
        remoteRepository.uploadingData(resultCallback);
    }

}
