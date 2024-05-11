package com.is.classroomevnmngapp.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.is.classroomevnmngapp.data.source.local.dao.ScheduleDao;
import com.is.classroomevnmngapp.data.source.local.entities.ScheduleEntity;
import com.is.classroomevnmngapp.utils.executor.AppExecutor;

import java.util.List;

import static com.is.classroomevnmngapp.utils.constant.NameTableConst.NAME_SCHEDULES;


public final class ScheduleRepository extends BaseRepository implements ScheduleDao {
    private static final String TAG = ScheduleRepository.class.getSimpleName();
    private static ScheduleRepository instance;
    private final ScheduleDao dao;

    private ScheduleRepository(Context context) {
        super(context);
        Log.d(TAG, String.format("getInstance:Creating new instance %s", TAG));
        dao = mDb.scheduleDao();
    }

    public static ScheduleRepository getInstance(Context context) {
        if (instance == null) {
            instance = new ScheduleRepository(context);
        }
        return instance;
    }


    public void insertSchedule(ScheduleEntity entity) {
        AppExecutor.getInstance().diskIO().execute(() -> dao.insertSchedule(entity));
    }


    public void insertAll(List<ScheduleEntity> entities) {
        AppExecutor.getInstance().diskIO().execute(() -> dao.insertAll(entities));
    }


    public ScheduleEntity getScheduleById(int id) {
        return dao.getScheduleById(id);
    }


    public LiveData<List<ScheduleEntity>> getAll() {
        return dao.getAll();
    }


    public int getCount() {
        return getCount("scheduleId", NAME_SCHEDULES);
        // return dao.getCount();
    }


    public int deleteAllRecords() {
        return dao.deleteAllRecords();
    }


}
