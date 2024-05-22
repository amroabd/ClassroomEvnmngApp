package com.is.classroomevnmngapp.data.repository.remote;

import com.is.classroomevnmngapp.data.model.ResponseObj;
import com.is.classroomevnmngapp.data.repository.GetResultCallback;

public interface IRemoteDataSource {
    void downloadData(GetResultCallback<ResponseObj> resultCallback);
    void uploadingData(GetResultCallback<ResponseObj>resultCallback);
}
