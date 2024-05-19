package com.is.classroomevnmngapp.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.is.classroomevnmngapp.data.repository.LectureHallRepository;
import com.is.classroomevnmngapp.data.repository.remote.NetworkProviderRepository;

public class RemoteProviderViewModel extends AndroidViewModel {

    private final NetworkProviderRepository sNetworkProviderRepository;

    public RemoteProviderViewModel(@NonNull Application application) {
        super(application);
        sNetworkProviderRepository =new NetworkProviderRepository();
    }


    public void downloadDataLecture(){
        sNetworkProviderRepository.downloadData(LectureHallRepository.getInstance(null));
    }
    public void uploadingDataLecture(){
        sNetworkProviderRepository.uploadingData(LectureHallRepository.getInstance(null));
    }

}
