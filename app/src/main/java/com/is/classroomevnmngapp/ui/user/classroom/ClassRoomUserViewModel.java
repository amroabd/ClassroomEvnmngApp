package com.is.classroomevnmngapp.ui.user.classroom;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ClassRoomUserViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ClassRoomUserViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}