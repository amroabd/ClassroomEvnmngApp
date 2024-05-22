package com.is.classroomevnmngapp.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.is.classroomevnmngapp.data.source.local.entities.DepartmentEntity;
import com.is.classroomevnmngapp.utils.executor.AppExecutor;
import com.is.classroomevnmngapp.utils.spinner.ListSpinner;

import java.util.List;

import static com.is.classroomevnmngapp.utils.constant.NameTableConst.NAME_DEPARTMENTS;


public final class DepartmentRepository extends BaseRepository  {
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
        return getCount("id",NAME_DEPARTMENTS);
       // return mDb.departmentDao().getCount();
    }


    public int deleteAllRecords() {
        return delete(NAME_DEPARTMENTS,null);
        //return mDb.departmentDao().deleteAllRecords();
    }

    /***
     *  list data
     * @param callback return result data
     */
    public void getSpinnerListDepartment( GetResultCallback<List<ListSpinner>> callback) {
        AppExecutor.getInstance().diskIO().execute(() -> {
            List<ListSpinner> list = mDb.departmentDao().loadAsSpinnerData();
            Log.d(TAG, "ListSpinner:" + list.size());
            AppExecutor.getInstance().mainThread().execute(() -> callback.onResult(list));
        });
    }


}
