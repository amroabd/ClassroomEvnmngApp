package com.is.classroomevnmngapp.ui.admin.check_device;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CheckDeviceAdminViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CheckDeviceAdminViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}