package com.is.classroomevnmngapp.ui.user.reservations;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.is.classroomevnmngapp.data.model.JoinReserveALecture;
import com.is.classroomevnmngapp.data.repository.ReservationRepository;
import com.is.classroomevnmngapp.data.source.local.entities.ReservationEntity;

public class ReservationUserViewModel extends AndroidViewModel {


    private ReservationRepository sReservationRepository;

    public ReservationUserViewModel(Application application) {
        super(application);
        sReservationRepository = ReservationRepository.getInstance(application);
    }


    public LiveData<PagedList<JoinReserveALecture>> getReserveALectureList() {
        return sReservationRepository.getReserveALectureList();
    }

    public LiveData<PagedList<JoinReserveALecture>> getReserveALectureHistList() {
        return sReservationRepository.getReserveALectureHistList();
    }

    public int getReservedCount(){
       return sReservationRepository.getReservedCount();
    }


    public long addReservation(ReservationEntity entity) {
        return sReservationRepository.insertReservation(entity);
    }

    public void updateReservation(int id, int status) {
        sReservationRepository.updateReserveStatus(id, status);
    }

    public void uploadData(){
        sReservationRepository.uploadingData();
    }

}