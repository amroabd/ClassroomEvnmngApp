package com.is.classroomevnmngapp.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.is.classroomevnmngapp.data.source.local.dao.LectureHallDao;
import com.is.classroomevnmngapp.data.source.local.entities.LectureHallEntity;
import com.is.classroomevnmngapp.utils.executor.AppExecutor;

import java.util.List;

import static com.is.classroomevnmngapp.utils.constant.NameTableConst.NAME_LECTURE_HALLS;


public final class LectureHallRepository extends BaseRepository implements LectureHallDao {
    private static final String TAG = LectureHallRepository.class.getSimpleName();
    private static LectureHallRepository instance;
    private final LectureHallDao lectureHallDao;

    private LectureHallRepository(Context context) {
        super(context);
        Log.d(TAG, String.format("getInstance:Creating new instance %s", TAG));
        lectureHallDao = mDb.lectureHallDao();
    }

    public static LectureHallRepository getInstance(Context context) {
        if (instance == null) {
            instance = new LectureHallRepository(context);
        }
        return instance;
    }


    public void insertLectureHall(LectureHallEntity lectureHallEntity) {
        AppExecutor.getInstance().diskIO().execute(() -> lectureHallDao.insertLectureHall(lectureHallEntity));
    }


    public void insertAll(List<LectureHallEntity> entities) {
        AppExecutor.getInstance().diskIO().execute(() -> lectureHallDao.insertAll(entities));
    }


    public LectureHallEntity getLectureHallById(int lectureHallId) {
        return lectureHallDao.getLectureHallById(lectureHallId);
    }


    public LiveData<List<LectureHallEntity>> getAll() {
        return lectureHallDao.getAll();
    }


    public int getCount() {
        return getCount("lectureHallId", NAME_LECTURE_HALLS);
        // return mDb.departmentDao().getCount();
    }


    public int deleteAllRecords() {
        return lectureHallDao.deleteAllRecords();
    }


}
