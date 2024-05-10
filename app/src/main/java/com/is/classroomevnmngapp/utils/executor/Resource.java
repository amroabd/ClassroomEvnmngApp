package com.is.classroomevnmngapp.utils.executor;

public interface Resource<ResultType> {
    public static <ResultType> Resource<ResultType> loading(Object o) {return null;}

    public static <ResultType> Resource<ResultType> success(ResultType newData) {
        return null;
    }

}
