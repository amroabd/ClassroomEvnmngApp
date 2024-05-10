package com.is.classroomevnmngapp.ui.auth.sigup;


import com.is.classroomevnmngapp.ui.auth.sigup.data.SigUpResponse;

public interface SignUpCallback {
    void onSuccess(SigUpResponse response);
    void onFailure(Throwable t);
}
