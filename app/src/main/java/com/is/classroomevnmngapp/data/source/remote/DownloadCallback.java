package com.is.classroomevnmngapp.data.source.remote;

public interface DownloadCallback<T> {
    public void onSuccess(T tList);
    default void onError(String Error){}
}
