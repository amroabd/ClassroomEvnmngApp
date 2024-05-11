package com.is.classroomevnmngapp.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.is.classroomevnmngapp.data.source.local.dao.ReservationDao;
import com.is.classroomevnmngapp.data.source.local.entities.ReservationEntity;
import com.is.classroomevnmngapp.utils.executor.AppExecutor;

import java.util.List;

import static com.is.classroomevnmngapp.utils.constant.NameTableConst.NAME_RESERVATIONS;


public final class ReservationRepository extends BaseRepository implements ReservationDao {
    private static final String TAG = ReservationRepository.class.getSimpleName();
    private static ReservationRepository instance;
    private final ReservationDao dao;

    private ReservationRepository(Context context) {
        super(context);
        Log.d(TAG, String.format("getInstance:Creating new instance %s", TAG));
        dao = mDb.reservationDao();
    }

    public static ReservationRepository getInstance(Context context) {
        if (instance == null) {
            instance = new ReservationRepository(context);
        }
        return instance;
    }


    public void insertReservation(ReservationEntity entity) {
        AppExecutor.getInstance().diskIO().execute(() -> dao.insertReservation(entity));
    }


    public void insertAll(List<ReservationEntity> entities) {
        AppExecutor.getInstance().diskIO().execute(() -> dao.insertAll(entities));
    }


    public ReservationEntity getReservationById(int id) {
        return dao.getReservationById(id);
    }


    public LiveData<List<ReservationEntity>> getAll() {
        return dao.getAll();
    }


    public int getCount() {
        return getCount("reserveId", NAME_RESERVATIONS);
        // return mDb.departmentDao().getCount();
    }


    public int deleteAllRecords() {
        return dao.deleteAllRecords();
    }


}
