package com.is.classroomevnmngapp.ui.setting.change_pass;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChangPasswordViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ChangPasswordViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}