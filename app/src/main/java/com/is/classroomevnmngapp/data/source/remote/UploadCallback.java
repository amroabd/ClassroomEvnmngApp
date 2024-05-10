package com.is.classroomevnmngapp.data.source.remote;

public interface UploadCallback<T> {
    void onSuccess(T obj);
    default void onError(String error){}
    default void goToNextTaskProcess(T obj) { }
    default void goToNextTask(T obj) { }

   interface VerifyData {
        void onResultVerify(Object obj, boolean statusVerify);
        default void onFailure(Throwable throwable){}
   }

}
