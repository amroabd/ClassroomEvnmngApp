package com.is.classroomevnmngapp.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.is.classroomevnmngapp.data.source.local.dao.DepartmentDao;
import com.is.classroomevnmngapp.data.source.local.entities.DepartmentEntity;
import com.is.classroomevnmngapp.utils.executor.AppExecutor;

import java.util.List;

import static com.is.classroomevnmngapp.utils.constant.NameTableConst.NAME_DEPARTMENTS;


public final class DepartmentRepository extends BaseRepository implements DepartmentDao {
    private static final String TAG = DepartmentRepository.class.getSimpleName();

    public DepartmentRepository(Context context) {
        super(context);
        Log.d(TAG, String.format("getInstance:Creating new instance %s", TAG));
    }


    public void insertDepartment(DepartmentEntity departmentEntity) {
        AppExecutor.getInstance().diskIO().execute(() -> mDb.departmentDao().insertDepartment(departmentEntity));
    }


    public void insertAll(List<DepartmentEntity> entities) {
        AppExecutor.getInstance().diskIO().execute(() -> mDb.departmentDao().insertAll(entities));
    }


    public DepartmentEntity getDepartmentById(int departmentId) {
        return mDb.departmentDao().getDepartmentById(departmentId);
    }


    public LiveData<List<DepartmentEntity>> getAll() {
        return mDb.departmentDao().getAll();
    }


    public int getCount() {
        return getCount("departmentId",NAME_DEPARTMENTS);
       // return mDb.departmentDao().getCount();
    }


    public int deleteAllRecords() {
        return mDb.departmentDao().deleteAllRecords();
    }


}
