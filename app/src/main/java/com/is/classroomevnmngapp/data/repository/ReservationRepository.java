package com.is.classroomevnmngapp.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.is.classroomevnmngapp.data.model.JoinReserveALecture;
import com.is.classroomevnmngapp.data.model.ResponseObj;
import com.is.classroomevnmngapp.data.repository.remote.IRemoteDataSource;
import com.is.classroomevnmngapp.data.source.local.dao.ReservationDao;
import com.is.classroomevnmngapp.data.source.local.entities.ReservationEntity;
import com.is.classroomevnmngapp.data.source.remote.DownloadCallback;
import com.is.classroomevnmngapp.data.source.remote.UploadCallback;
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


public final class ReservationRepository extends BaseRepository implements IRemoteDataSource {
    private static final String TAG = ReservationRepository.class.getSimpleName();
    private static ReservationRepository instance;
    private final ReservationDao dao;

    public ReservationRepository(Context context) {
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

    @Override
    public void downloadData(GetResultCallback<ResponseObj> resultCallback) {

        mDownloadSourceClient.downLoadReservations(new DownloadCallback<List<ReservationEntity>>() {
            @Override
            public void onSuccess(List<ReservationEntity> tList) {
                //save result from remote to db local
                Log.d(TAG, String.format("insert list data return from server size: %s", tList.size()));
                AppExecutor.getInstance().diskIO().execute(() -> {
                    try {
                        Thread.sleep(1000);
                        syncReservation(tList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            public void onError(String error) {
                Log.e(TAG, String.format("onError : %s", error));
            }
        });
    }

    @Override
    public void uploadingData(GetResultCallback<ResponseObj> resultCallback) {
        AppExecutor.getInstance().diskIO().execute(() ->
                mUploadingSourceClient.uploadReservation(getAll(), new UploadCallback<ResponseObj>() {
                    @Override
                    public void onSuccess(ResponseObj obj) {
                        updateStatusUpload(obj.getlId(), Long.parseLong(obj.getServeId()), 1);
                        if (resultCallback != null) {
                            resultCallback.onResult((obj));
                        }
                    }

                    @Override
                    public void onError(String error) {
                        if (resultCallback != null) {
                            resultCallback.onResult(new ResponseObj(error, "error"));
                        }
                    }
                }));

    }

    private void syncReservation(List<ReservationEntity> remoteReserves) {
        List<ReservationEntity> localReserves = getAll();

        for (ReservationEntity remoteRes : remoteReserves) {
            ReservationEntity localRes = findReserveById(localReserves, remoteRes.getReserveId());

            if (localRes == null) {
                if (remoteRes.getLectureHallIdFk()==0)remoteRes.setLectureHallIdFk(1);
                dao.insertReservation(remoteRes);
            } else if (!localRes.equals(remoteRes)) {
                dao.update(remoteRes);
            }
        }
    }

    private ReservationEntity findReserveById(List<ReservationEntity> entities, int id) {
        for (ReservationEntity entity : entities) {
            if (entity.getReserveId() == id) {
                return entity;
            }
        }
        return null;
    }


    public long insertReservation(ReservationEntity entity) {
        long rowID = 0;
        insertFooter(entity, false);
        mExecutorService = Executors.newSingleThreadExecutor();
        Callable<Long> callable = () -> dao.insertWithTriggerLogic(entity);
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
        AppExecutor.getInstance().diskIO().execute(() -> dao.updateStatusUpload(localID, centerID, upload));

    }

    public ReservationEntity getReservationById(int id) {
        return dao.getReservationById(id);
    }


    public List<ReservationEntity> getAll() {
        return dao.getAll();
    }


    public int getCount() {
        return getCount("id", NAME_RESERVATIONS);

    }

    public int getReservedCount() {
        Integer countReserved = 0;
        mExecutorService = Executors.newSingleThreadExecutor();
        Callable<Integer> callable = dao::getReservedCount;
        Future<Integer> future = mExecutorService.submit(callable);
        try {
            countReserved = (future.get(100, TimeUnit.MILLISECONDS));
        } catch (ExecutionException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            shutdown(mExecutorService);
            Log1.d(TAG, "getReservedCount :" + countReserved);
        }
        return countReserved;
    }


    public int deleteAllRecords() {
        return delete(NAME_RESERVATIONS, null);
        //return dao.deleteAllRecords();
    }

    /***
     *  list data using pagedList
     */
    @NonNull
    public LiveData<PagedList<JoinReserveALecture>> getReserveALectureList() {
        return new LivePagedListBuilder<>(dao.getReserveALectureList(), configPagedList())
                /*  .setFetchExecutor(Executors.newSingleThreadExecutor())*/.build();
    }


    /***
     *  list data using pagedList
     */
    @NonNull
    public LiveData<PagedList<JoinReserveALecture>> getReserveALectureHistList() {
        return new LivePagedListBuilder<>(dao.getReserveALectureHistList(), configPagedList())
                .setFetchExecutor(Executors.newSingleThreadExecutor()).build();
    }


}
