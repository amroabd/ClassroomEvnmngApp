package com.is.classroomevnmngapp.ui.user.reservations;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.is.classroomevnmngapp.data.source.local.entities.ReservationEntity;

public class ReservationUserViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;

    public ReservationUserViewModel(Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }


    public int addReservation(ReservationEntity entity){
        return 0;
    }

}