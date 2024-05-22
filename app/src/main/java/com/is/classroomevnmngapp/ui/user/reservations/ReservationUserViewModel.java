package com.is.classroomevnmngapp.ui.user.reservations;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.is.classroomevnmngapp.data.model.JoinReserveALecture;
import com.is.classroomevnmngapp.data.repository.GetResultCallback;
import com.is.classroomevnmngapp.data.repository.LectureHallRepository;
import com.is.classroomevnmngapp.data.repository.ReservationRepository;
import com.is.classroomevnmngapp.data.repository.UserRepository;
import com.is.classroomevnmngapp.data.source.local.entities.ReservationEntity;
import com.is.classroomevnmngapp.utils.spinner.ListSpinner;

import java.util.List;

public class ReservationUserViewModel extends AndroidViewModel {

    private final ReservationRepository sReservationRepository;

    private final LectureHallRepository sLectureHallRepository;
    private final UserRepository sUserRepository;

    public ReservationUserViewModel(Application application) {
        super(application);
        sReservationRepository = ReservationRepository.getInstance(application);
        sLectureHallRepository=LectureHallRepository.getInstance(application);
        sUserRepository=new UserRepository(application);
    }

    public void loadAsSpinnerDataLecture(GetResultCallback<List<ListSpinner>> getResultCallback){
        sLectureHallRepository.loadAsSpinnerDataLecture(getResultCallback);
    }

    public void loadAsSpinnerDataUser(GetResultCallback<List<ListSpinner>> getResultCallback){
        sUserRepository.loadAsSpinnerDataUser(getResultCallback);
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

    public void uploadData(GetResultCallback resultCallback){
        sReservationRepository.uploadingData(resultCallback);
    }

}