package com.is.classroomevnmngapp.ui.auth.sigup;

import android.os.CountDownTimer;

import androidx.lifecycle.ViewModel;

import com.is.classroomevnmngapp.ui.auth.sigup.data.SigUpResponse;
import com.is.classroomevnmngapp.ui.auth.sigup.data.SignUpRepository;
import com.is.classroomevnmngapp.ui.auth.sigup.data.SignUpRequest;

public class SigUpViewModel extends ViewModel {
  private SignUpRepository signUpRepository;


    public void sendSigUpRequest(SignUpRequest signUpRequest, SignUpCallback callback) {
        new CountDownTimer(5000L,1000L) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
              callback.onSuccess(new SigUpResponse("Done","success"));
            }
        }.start();
       /* // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(username, password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
   */ }



}