package com.is.classroomevnmngapp.data.repository.providers;

import android.content.Context;
import android.util.Log;

import com.is.classroomevnmngapp.data.repository.DepartmentRepository;

public class DepartmentRepositoryProvider {
    private static final String TAG = DepartmentRepository.class.getSimpleName();
    private static DepartmentRepository instance;


    public static DepartmentRepository getInstance(Context context) {
        if (instance == null) {
            Log.d(TAG, String.format("getInstance:Creating new instance %s", TAG));
            instance = new DepartmentRepository(context);
        }
        return instance;
    }

}
