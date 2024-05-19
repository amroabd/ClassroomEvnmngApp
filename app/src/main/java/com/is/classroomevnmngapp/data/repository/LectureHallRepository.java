package com.is.classroomevnmngapp.data.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.is.classroomevnmngapp.data.repository.remote.INetworkSource;
import com.is.classroomevnmngapp.data.source.local.dao.LectureHallDao;
import com.is.classroomevnmngapp.data.source.local.entities.LectureHallEntity;
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

import static com.is.classroomevnmngapp.utils.constant.NameTableConst.NAME_LECTURE_HALLS;


public final class LectureHallRepository extends BaseRepository implements INetworkSource {
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


    @Override
    public void downloadData() {
        mDownloadClient.downLoadLectureHalls(new DownloadCallback<List<LectureHallEntity>>() {
            @Override
            public void onSuccess(List<LectureHallEntity> tList) {
               //here : insert list data return from server
                Log.d(TAG, String.format("insert list data return from server size: %s", tList.size()));
                AppExecutor.getInstance().diskIO().execute(() -> insertAll(tList));
            }

            @Override
            public void onError(String error) {
                Log.e(TAG, String.format("onError : %s", error));
            }
        });
    }

    @Override
    public void uploadingData() {
        mUploadCentral.uploadLectureHall(new UploadCallback<Object>() {
            @Override
            public void onSuccess(Object obj) {

            }

            @Override
            public void onError(String error) {

            }
        });
    }


    /***
     *  insert new row to table
     * @param lectureHallEntity obj
     * @return row id
     */
    @SuppressLint("NewApi")
    public long insertLectureHall(LectureHallEntity lectureHallEntity) {
        long rowID = 0;
        insertFooter(lectureHallEntity, false);
        mExecutorService = Executors.newSingleThreadExecutor();
        Callable<Long> callable = () -> lectureHallDao.insertWithTriggerLogic(lectureHallEntity);
        Future<Long> future = mExecutorService.submit(callable);
        try {
            rowID = Math.toIntExact(future.get(100, TimeUnit.MILLISECONDS));
        } catch (ExecutionException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            shutdown(mExecutorService);
            Log1.d(TAG, "insertLectureHall(OBJ)-> " + lectureHallEntity + ",rowID :" + rowID);
        }
        return rowID;
    }


    public void insertAll(List<LectureHallEntity> entities) {
        AppExecutor.getInstance().diskIO().execute(() -> lectureHallDao.insertAll(entities));
    }


    public LectureHallEntity getLectureHallById(int lectureHallId) {
        return lectureHallDao.getLectureHallById(lectureHallId);
    }


    /***
     *  list data using pagedList
     */
    @NonNull
    public LiveData<PagedList<LectureHallEntity>> getAllData() {
        return new LivePagedListBuilder<>(lectureHallDao.getAll(), configPagedList())
                .setFetchExecutor(Executors.newSingleThreadExecutor()).build();
    }


    public int getCount() {
        return getCount("id", NAME_LECTURE_HALLS);
        // return mDb.departmentDao().getCount();
    }


    public int deleteAllRecords() {
        return lectureHallDao.deleteAllRecords();
    }



}
