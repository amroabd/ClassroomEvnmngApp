package com.is.classroomevnmngapp.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.is.classroomevnmngapp.data.source.local.dao.ProfessorDao;
import com.is.classroomevnmngapp.data.source.local.entities.ProfessorEntity;
import com.is.classroomevnmngapp.utils.executor.AppExecutor;
import com.is.classroomevnmngapp.utils.spinner.ListSpinner;

import java.util.List;

import static com.is.classroomevnmngapp.utils.constant.NameTableConst.NAME_PROFESSORS;


public final class ProfessorRepository extends BaseRepository  {
    private static final String TAG = ProfessorRepository.class.getSimpleName();
    private static ProfessorRepository instance;
    private final ProfessorDao professorDao;

    private ProfessorRepository(Context context) {
        super(context);
        Log.d(TAG, String.format("getInstance:Creating new instance %s", TAG));
        professorDao = mDb.professorDao();
    }

    public static ProfessorRepository getInstance(Context context) {
        if (instance == null) {
            instance = new ProfessorRepository(context);
        }
        return instance;
    }


    public void insertProfessor(ProfessorEntity professorEntity) {
        AppExecutor.getInstance().diskIO().execute(() -> professorDao.insertProfessor(professorEntity));
    }


    public void insertAll(List<ProfessorEntity> entities) {
        AppExecutor.getInstance().diskIO().execute(() -> professorDao.insertAll(entities));
    }


    public ProfessorEntity getProfessorById(int professorId) {
        return professorDao.getProfessorById(professorId);
    }


    public LiveData<List<ProfessorEntity>> getAll() {
        return professorDao.getAll();
    }


    public int getCount() {
        return getCount("professorId", NAME_PROFESSORS);
        // return mDb.departmentDao().getCount();
    }


    public int deleteAllRecords() {
        return delete(NAME_PROFESSORS,null);
        //return professorDao.deleteAllRecords();
    }

    /***
     *  list data
     * @param callback return result data
     */
    public void getSpinnerListProfessor( GetResultCallback<List<ListSpinner>> callback) {
        AppExecutor.getInstance().diskIO().execute(() -> {
            List<ListSpinner> list = professorDao.loadAsSpinnerData();
            Log.d(TAG, "ListSpinner:" + list.size());
            AppExecutor.getInstance().mainThread().execute(() -> callback.onResult(list));
        });
    }




}
