package com.is.classroomevnmngapp.data.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.is.classroomevnmngapp.data.model.ResponseObj;
import com.is.classroomevnmngapp.data.repository.remote.IRemoteDataSource;
import com.is.classroomevnmngapp.data.source.local.dao.LectureHallDao;
import com.is.classroomevnmngapp.data.source.local.entities.LectureHallEntity;
import com.is.classroomevnmngapp.data.source.remote.DownloadCallback;
import com.is.classroomevnmngapp.data.source.remote.UploadCallback;
import com.is.classroomevnmngapp.utils.Log1;
import com.is.classroomevnmngapp.utils.executor.AppExecutor;
import com.is.classroomevnmngapp.utils.spinner.ListSpinner;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.is.classroomevnmngapp.utils.constant.NameTableConst.NAME_LECTURE_HALLS;


public final class LectureHallRepository extends BaseRepository implements IRemoteDataSource {
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


    private void notifyToUI(GetResultCallback<ResponseObj> resultCallback, ResponseObj data) {
        if (resultCallback != null) {
            resultCallback.onResult(data);
        }
    }

    @Override
    public void downloadData(GetResultCallback<ResponseObj> resultCallback) {
        final String[] message = {"download Data not found.!!"};
        mDownloadSourceClient.downLoadLectureHalls(new DownloadCallback<List<LectureHallEntity>>() {
            @Override
            public void onSuccess(List<LectureHallEntity> tList) {
                //here : insert list data return from remote
                AppExecutor.getInstance().diskIO().execute(() -> syncLectureHalls(tList));
                message[0] = String.format("result LectureHal from remote to db local ,size: %s", tList.size());
                Log1.d(TAG, message[0]);
                notifyToUI(resultCallback, ResponseObj.newInstance(message[0]));
            }

            @Override
            public void onError(String error) {
                message[0] = (String.format("onError  download LectureHal : %s", error));
                notifyToUI(resultCallback, ResponseObj.newInstance(message[0]));
                Log1.e(TAG, message[0]);
            }
        });
    }

    @Override
    public void uploadingData(GetResultCallback<ResponseObj> resultCallback) {
        AppExecutor.getInstance().diskIO().execute(() ->
                mUploadingSourceClient.uploadLectureHall(lectureHallDao.getDataAsLimit(5), new UploadCallback<ResponseObj>() {
                    @Override
                    public void onSuccess(ResponseObj obj) {
                        AppExecutor.getInstance().diskIO().execute(() ->
                                lectureHallDao.updateStatusUpload(obj.getlId(), Long.parseLong(obj.getServeId()), 1));
                        notifyToUI(resultCallback, obj);
                    }

                    @Override
                    public void onError(String error) {
                        String message = (String.format("onError  uploading LectureHal : %s", error));
                        notifyToUI(resultCallback, ResponseObj.newInstance(message));
                        Log1.e(TAG, message);
                    }
                }));
    }

    private void syncLectureHalls(@NonNull List<LectureHallEntity> remoteLectureHalls) {
        List<LectureHallEntity> localLectureHalls = lectureHallDao.getAll();

        for (LectureHallEntity remoteLectureHall : remoteLectureHalls) {
            LectureHallEntity localLectureHall = findLectureHallById(localLectureHalls, remoteLectureHall.getLectureHallId());
            if (localLectureHall == null) {
                lectureHallDao.insertLectureHall(remoteLectureHall);
            } else if (!localLectureHall.equals(remoteLectureHall)) {
                lectureHallDao.update(remoteLectureHall);
            }
        }

    }

    @Nullable
    private LectureHallEntity findLectureHallById(@NonNull List<LectureHallEntity> entities, int id) {
        for (LectureHallEntity entity : entities) {
            if (entity.getLectureHallId() == id) {
                return entity;
            }
        }
        return null;
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
     *  list data
     * @param callback return result data
     */
    public void loadAsSpinnerDataLecture(GetResultCallback<List<ListSpinner>> callback) {
        AppExecutor.getInstance().diskIO().execute(() -> {
            List<ListSpinner> list = lectureHallDao.loadAsSpinnerData();
            Log.d(TAG, "ListSpinner:" + list.size());
            AppExecutor.getInstance().mainThread().execute(() -> callback.onResult(list));
        });
    }


    /***
     *  list data using pagedList
     */
    @NonNull
    public LiveData<PagedList<LectureHallEntity>> getAllHallEntityFactory() {
        return new LivePagedListBuilder<>(lectureHallDao.getAllHallEntityFactory(), configPagedList())
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
