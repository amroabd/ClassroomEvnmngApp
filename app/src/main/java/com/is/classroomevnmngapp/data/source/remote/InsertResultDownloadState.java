package com.is.classroomevnmngapp.data.source.remote;

public interface InsertResultDownloadState<T> {
    void onCompleted(T t);
   default void onUpdateProgress(float progress){}
    void onFailure(String s);
}
