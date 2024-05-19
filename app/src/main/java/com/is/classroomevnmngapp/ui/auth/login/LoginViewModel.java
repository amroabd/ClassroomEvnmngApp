package com.is.classroomevnmngapp.ui.auth.login;

import androidx.lifecycle.ViewModel;

import com.is.classroomevnmngapp.ui.auth.login.data.LoginRepository;
import com.is.classroomevnmngapp.ui.auth.login.data.LoginRequest;

public class LoginViewModel extends ViewModel {
  LoginRepository loginRepository;

  public LoginViewModel() {
    loginRepository = new LoginRepository();
  }

  //  private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();


    //LoginViewModel(LoginRepository loginRepository) {
      //  this.loginRepository = loginRepository;
   // }


  /*  LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }
*/
    public void sendLoginRequest(LoginRequest loginRequest, LoginCallback callback) {
      loginRepository.login(loginRequest,callback);
     }




}