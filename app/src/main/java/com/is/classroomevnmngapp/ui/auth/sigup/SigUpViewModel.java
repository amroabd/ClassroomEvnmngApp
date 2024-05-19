package com.is.classroomevnmngapp.ui.auth.sigup;

import androidx.lifecycle.ViewModel;

import com.is.classroomevnmngapp.ui.auth.sigup.data.SignUpRepository;
import com.is.classroomevnmngapp.ui.auth.sigup.data.SignUpRequest;

public class SigUpViewModel extends ViewModel {

  private final SignUpRepository signUpRepository;

    public SigUpViewModel() {
        this.signUpRepository = new SignUpRepository();
    }

    public void sendSigUpRequest(SignUpRequest signUpRequest, SignUpCallback callback) {
         signUpRepository.signUp(signUpRequest,callback);

       /* // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(username, password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
   */
    }



}