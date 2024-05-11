package com.is.classroomevnmngapp.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.is.classroomevnmngapp.data.source.local.dao.ControllerDao;
import com.is.classroomevnmngapp.data.source.local.entities.ControllerEntity;
import com.is.classroomevnmngapp.utils.executor.AppExecutor;

import java.util.List;

import static com.is.classroomevnmngapp.utils.constant.NameTableConst.NAME_CONTROLLERS;

public class ControllerRepository extends BaseRepository {
    private static final String TAG = "ControllerRepository";
    private static  ControllerRepository sInstance;

    private final ControllerDao controllerDao;

    private ControllerRepository(Context context) {
        super(context);
        controllerDao=mDb.controllerDao();
    }

    public static ControllerRepository getInstance(Context context) {
        if (sInstance == null) {
            Log.d(TAG, "getInstance:Creating new instance ControllerRepository");
            sInstance=new ControllerRepository(context);
        }
        return sInstance;
    }

    public void insertController(ControllerEntity controllerEntity) {
        AppExecutor.getInstance().diskIO().execute(() ->
                controllerDao.insertController(controllerEntity));
    }

    public void insertAll(List<ControllerEntity> entities) {
        AppExecutor.getInstance().diskIO().execute(() ->
                controllerDao.insertAll(entities));
    }

    public ControllerEntity getControllerById(int controllerId) {
        return controllerDao.getControllerById(controllerId);
    }

    public LiveData<List<ControllerEntity>> getAllControllers() {
        return controllerDao.getAll();
    }

    public int getCount() {
       return getCount("controllerId",NAME_CONTROLLERS);
       // return controllerDao.getCount();
    }

    public int deleteAllRecords() {
        return controllerDao.deleteAllRecords();
    }
}
