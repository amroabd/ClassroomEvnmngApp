package com.is.classroomevnmngapp.data.repository.remote;

import androidx.annotation.NonNull;

public class RemoteProviderRepository {
  /*  IRemoteRepository remoteRepository;

    public RemoteProviderRepository(IRemoteRepository remoteRepository) {
        this.remoteRepository = remoteRepository;
    }*/

    public void downloadData(@NonNull IRemoteRepository remoteRepository){
        remoteRepository.downloadData();
    }

    public void uploadingData(@NonNull IRemoteRepository remoteRepository){
        remoteRepository.uploadingData();
    }

}
