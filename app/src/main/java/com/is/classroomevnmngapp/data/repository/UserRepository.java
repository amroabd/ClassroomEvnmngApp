package com.is.classroomevnmngapp.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.is.classroomevnmngapp.data.model.ResponseObj;
import com.is.classroomevnmngapp.data.repository.remote.IRemoteDataSource;
import com.is.classroomevnmngapp.data.source.local.dao.UserDao;
import com.is.classroomevnmngapp.data.source.local.entities.UserEntity;
import com.is.classroomevnmngapp.data.source.remote.DownloadCallback;
import com.is.classroomevnmngapp.data.source.remote.UploadCallback;
import com.is.classroomevnmngapp.utils.Log1;
import com.is.classroomevnmngapp.utils.executor.AppExecutor;
import com.is.classroomevnmngapp.utils.spinner.ListSpinner;

import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static com.is.classroomevnmngapp.utils.constant.NameTableConst.NAME_USERS;
import static com.is.classroomevnmngapp.utils.constant.ParamsStatus.UPLOAD_STATUS_OFF;
import static com.is.classroomevnmngapp.utils.constant.ParamsStatus.UPLOAD_STATUS_ONN;

public class UserRepository extends BaseRepository implements IRemoteDataSource {
    private static final String TAG = "UserRepository";

    private final UserDao userDao;


    public UserRepository(Context context) {
        super(context);
        userDao = mDb.userDao();
    }


    private void notifyToUI(GetResultCallback<ResponseObj> resultCallback, ResponseObj data) {
        if (resultCallback != null) {
            resultCallback.onResult(data);
        }
    }

    @Override
    public void downloadData(GetResultCallback<ResponseObj> resultCallback) {
        final String[] message = {"download Data not found.!!"};
        final int countRecordsNotUpload = getCountAsUploadStatus(UPLOAD_STATUS_OFF);
        if (countRecordsNotUpload > 0) {
            message[0] = String.format("Found Records not upload from local to remote,size: %s", countRecordsNotUpload);
            Log1.d(TAG, message[0]);
            notifyToUI(resultCallback, ResponseObj.newInstance(message[0]));
            //will start upload the records before download
            uploadingData(resultCallback);
        } else {
            mDownloadSourceClient.downLoadUsers(new DownloadCallback<List<UserEntity>>() {
                @Override
                public void onSuccess(List<UserEntity> tList) {
                    message[0] = String.format("result from remote to db local ,size: %s", tList.size());
                    //save result from remote to db local
                    AppExecutor.getInstance().diskIO().execute(() -> syncUsers(tList));
                    //AppExecutor.getInstance().diskIO().execute(() -> insertAll(tList));
                    Log1.d(TAG, message[0]);
                    notifyToUI(resultCallback, ResponseObj.newInstance(message[0]));
                }

                @Override
                public void onError(String error) {
                    message[0] = (String.format("onError  download users : %s", error));
                    notifyToUI(resultCallback, ResponseObj.newInstance(message[0]));
                    Log1.e(TAG, message[0]);
                }
            });
        }
    }

    @Override
    public void uploadingData(GetResultCallback<ResponseObj> resultCallback) {
        AppExecutor.getInstance().diskIO().execute(() ->
                mUploadingSourceClient.uploadUser(userDao.getDataAsLimit(5), new UploadCallback<ResponseObj>() {
                    @Override
                    public void onSuccess(ResponseObj obj) {
                        updateStatusUpload(obj.getlId(), Long.parseLong(obj.getServeId()), UPLOAD_STATUS_ONN);
                    }

                    @Override
                    public void onError(String error) {

                    }
                }));

    }

    private void syncUsers(@NonNull List<UserEntity> remoteUsers) {
        List<UserEntity> localUsers = userDao.getAll();
        // List<UserEntity> remoteUsers = userService.getUsers().execute().body();
        for (UserEntity remoteUser : remoteUsers) {
            UserEntity localUser = findUserById(localUsers, remoteUser.getUserId());
            if (localUser == null) {
                Log1.d(TAG, MessageFormat.format("syncUsers-> insertUser(remoteUser), remoteUser :{0}", remoteUser));
                userDao.insertUser(remoteUser);
            } else if (!localUser.equals(remoteUser)) {
                Log1.d(TAG, MessageFormat.format("syncUsers-> update(remoteUser), remoteUser :{0}", remoteUser));
                //data return from remote server have not local id,there do add here
                remoteUser.setLocalId(localUser.getLocalId());
                userDao.update(remoteUser);
            }
        }
        //
        for (UserEntity localUser : localUsers) {
            if (findUserById(remoteUsers, localUser.getUserId()) == null) {
                userDao.delete(localUser);
            }
        }

    }

    @Nullable
    private UserEntity findUserById(@NonNull List<UserEntity> users, int id) {
        for (UserEntity user : users) {
            if (user.getUserId() == id) {
                return user;
            }
        }
        return null;
    }

    //---------------------------------------------------//


    public void insertUser(UserEntity userEntity) {
        AppExecutor.getInstance().diskIO().execute(() -> userDao.insertUser(userEntity));
    }

    public UserEntity getUserById(int userId) {
        return userDao.getUserById(userId);
    }

    public void insertAll(List<UserEntity> entities) {
        AppExecutor.getInstance().diskIO().execute(() -> userDao.insertAll(entities));
    }

    void updateStatusUpload(long localID, long centerID, int upload) {
        userDao.updateStatusUpload(localID, centerID, upload);
    }

    public List<UserEntity> getAll() {
        return userDao.getAll();
    }

    public int getCount() {
        return getCount("userId", NAME_USERS);
    }

    public int deleteAllRecords() {
        return userDao.deleteAllRecords();
    }

    public int getCountAsUploadStatus(int status) {
        int count = 0;
        mExecutorService = Executors.newSingleThreadExecutor();
        Callable<Integer> integerCallable = () -> userDao.getCountAsUploadStatus(status);
        Future<Integer> integerFuture = mExecutorService.submit(integerCallable);
        try {
            count = integerFuture.get(100, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            shutdown(mExecutorService);
            Log.d(TAG, String.format("getCountAsUploadStatus-> COUNT:%d", count));
        }
        return count;
    }


    /***
     *  list data using pagedList
     */
    @NonNull
    public LiveData<PagedList<UserEntity>> getUsersPagedList() {
        return new LivePagedListBuilder<>(userDao.getAllUsers(), configPagedList())
                /*.setFetchExecutor(Executors.newSingleThreadExecutor())*/.build();
    }


    public void loadAsSpinnerDataUser(GetResultCallback<List<ListSpinner>> callback) {
        AppExecutor.getInstance().diskIO().execute(() -> {
            List<ListSpinner> list = userDao.loadAsSpinnerData();
            Log.d(TAG, "ListSpinner:" + list.size());
            AppExecutor.getInstance().mainThread().execute(() -> callback.onResult(list));
        });
    }

}
