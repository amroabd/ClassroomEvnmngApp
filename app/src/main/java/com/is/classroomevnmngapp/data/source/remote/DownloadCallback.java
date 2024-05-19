package com.is.classroomevnmngapp.data.source.remote;

public interface DownloadCallback<T> {
    void onSuccess(T tList);
    default void onError(String error){}
}
