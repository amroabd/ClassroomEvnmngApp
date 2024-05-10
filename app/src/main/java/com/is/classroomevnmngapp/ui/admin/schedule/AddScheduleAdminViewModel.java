package com.is.classroomevnmngapp.ui.admin.schedule;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddScheduleAdminViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AddScheduleAdminViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}