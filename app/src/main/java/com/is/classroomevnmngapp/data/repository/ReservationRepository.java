package com.is.classroomevnmngapp.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.is.classroomevnmngapp.data.model.JoinReserveALecture;
import com.is.classroomevnmngapp.data.source.local.dao.ReservationDao;
import com.is.classroomevnmngapp.data.source.local.entities.ReservationEntity;
import com.is.classroomevnmngapp.utils.Log1;
import com.is.classroomevnmngapp.utils.executor.AppExecutor;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.is.classroomevnmngapp.utils.constant.NameTableConst.NAME_RESERVATIONS;


public final class ReservationRepository extends BaseRepository  {
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


    public long insertReservation(ReservationEntity entity) {
        long rowID = 0;
        insertFooter(entity,false);
        mExecutorService = Executors.newSingleThreadExecutor();
        Callable<Long> callable = () -> dao.insertReservation(entity);
        Future<Long> future = mExecutorService.submit(callable);
        try {
            rowID = (future.get(100, TimeUnit.MILLISECONDS));
        } catch (ExecutionException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            shutdown(mExecutorService);
            Log1.d(TAG, "insertReservation(OBJ)-> " + entity + ",rowID :" + rowID);
        }
        return rowID;
    }


    public void insertAll(List<ReservationEntity> entities) {
        AppExecutor.getInstance().diskIO().execute(() -> dao.insertAll(entities));
    }


    public void updateReserveStatus(long localID, int status) {
     AppExecutor.getInstance().diskIO().execute(() -> dao.updateReserveStatus(localID, status));
    }


    public void updateStatusUpload(long localID, long centerID, int upload) {

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

    /***
     *  list data using pagedList
     */
    @NonNull
    public LiveData<PagedList<JoinReserveALecture>>getReserveALectureList(){
        return new LivePagedListBuilder<>(dao.getReserveALectureList(),configPagedList())
                .setFetchExecutor(Executors.newSingleThreadExecutor()).build();
    }



}
