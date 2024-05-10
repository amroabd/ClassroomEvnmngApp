package com.is.classroomevnmngapp.ui.auth.login;

import com.is.classroomevnmngapp.ui.auth.login.data.LoginResponse;

public interface LoginCallback {
    void onSuccess(LoginResponse response);
    void onFailure(Throwable t);
}
