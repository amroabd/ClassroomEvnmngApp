package com.is.classroomevnmngapp.ui.auth.forget_pass;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ForgetPasswordViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ForgetPasswordViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}