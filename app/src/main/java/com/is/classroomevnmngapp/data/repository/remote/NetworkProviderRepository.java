package com.is.classroomevnmngapp.data.repository.remote;

import androidx.annotation.NonNull;

public class NetworkProviderRepository {
  /*  IRemoteRepository remoteRepository;

    public RemoteProviderRepository(IRemoteRepository remoteRepository) {
        this.remoteRepository = remoteRepository;
    }*/

    public void downloadData(@NonNull INetworkSource remoteRepository){
        remoteRepository.downloadData();
    }

    public void uploadingData(@NonNull INetworkSource remoteRepository){
        remoteRepository.uploadingData();
    }

}
