package com.is.classroomevnmngapp.view_model;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.is.classroomevnmngapp.data.repository.LectureHallRepository;
import com.is.classroomevnmngapp.data.repository.ReservationRepository;
import com.is.classroomevnmngapp.data.repository.remote.INetworkSource;
import com.is.classroomevnmngapp.data.repository.remote.NetworkProviderRepository;

public class NetworkProviderViewModel extends AndroidViewModel {

    private final NetworkProviderRepository sNetworkProviderRepository;

    public NetworkProviderViewModel(@NonNull Application application) {
        super(application);
        sNetworkProviderRepository =new NetworkProviderRepository();
    }


    public void downloadDataLecture(Context context){
        sNetworkProviderRepository.downloadData(LectureHallRepository.getInstance(context));
    }

    public void downloadDataReservation(Context context){
        sNetworkProviderRepository.downloadData(ReservationRepository.getInstance(context));
    }

    public void uploadingDataReservation(Context context){
        sNetworkProviderRepository.uploadingData(ReservationRepository.getInstance(context));
    }

    //inject any object implement to INetworkSource
    public void downloadData(@NonNull INetworkSource iNetworkSource){
        sNetworkProviderRepository.downloadData(iNetworkSource);
    }

}
