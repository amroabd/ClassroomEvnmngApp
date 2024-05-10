package com.is.classroomevnmngapp.ui.auth.login;

import androidx.lifecycle.ViewModel;

import com.is.classroomevnmngapp.ui.auth.login.data.LoginRepository;

public class LoginViewModel extends ViewModel {
  LoginRepository LoginRepository;


  //  private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();


    //LoginViewModel(LoginRepository loginRepository) {
      //  this.loginRepository = loginRepository;
   // }


  /*  LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }
*/
    public void sendLoginRequest(String username, String password, LoginCallback callback) {
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