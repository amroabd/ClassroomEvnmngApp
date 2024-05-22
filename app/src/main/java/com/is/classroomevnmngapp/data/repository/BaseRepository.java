package com.is.classroomevnmngapp.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.is.classroomevnmngapp.data.source.local.AppDatabase;
import com.is.classroomevnmngapp.data.source.local.dao.BaseDao;
import com.is.classroomevnmngapp.data.source.local.entities.BaseEntity;
import com.is.classroomevnmngapp.data.source.remote.DownloadSourceClient;
import com.is.classroomevnmngapp.data.source.remote.UploadingSourceClient;
import com.is.classroomevnmngapp.utils.DateUtils;
import com.is.classroomevnmngapp.utils.Log1;
import com.is.classroomevnmngapp.utils.SharePerf;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class BaseRepository {
    private static final String TAG = BaseRepository.class.getSimpleName();
    protected ExecutorService mExecutorService;
    private final BaseDao mBaseDao;
    protected final AppDatabase mDb;
    protected final SharePerf mSharePerf;

    protected DownloadSourceClient mDownloadSourceClient;
    protected UploadingSourceClient mUploadingSourceClient;

    public BaseRepository(Context context) {
        Log1.d(TAG, "=========>BaseRep(context)<===========");
        mDb = AppDatabase.getInstance(context.getApplicationContext());
        mBaseDao = mDb.mainMenuDao();
        mSharePerf = SharePerf.getInstance(context.getApplicationContext());
        //-------------
        mDownloadSourceClient = new DownloadSourceClient(context.getApplicationContext());
        mUploadingSourceClient = new UploadingSourceClient();
        //-------------
    }


    @NotNull
    @Contract(value = "_, _ -> new", pure = true)
    private SupportSQLiteQuery sqlQuery(String cols, String tableName) {
        String query = "SELECT COUNT(" + cols + ") FROM " + tableName;
        return new SimpleSQLiteQuery(query);
    }

    public final int getCount(String cols, String tableName) {
        int count = 0;
        mExecutorService = Executors.newSingleThreadExecutor();
        Callable<Integer> integerCallable = () -> mBaseDao.getCount(sqlQuery(cols, tableName));
        Future<Integer> integerFuture = mExecutorService.submit(integerCallable);
        try {
            count = integerFuture.get(100, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            shutdown(mExecutorService);
            Log.d(TAG, String.format("getCount-> %s,COUNT:%d", tableName, count));
        }
        return count;
    }

    @NotNull
    @Contract(value = "_, _ -> new", pure = true)
    private SupportSQLiteQuery sqlDelQuery(String tableName, String where) {
        String query;
        if (where !=null && where.length()>0) query = String.format("DELETE  FROM %s %s", tableName, where);
        else query = String.format("DELETE  FROM %s", tableName);
        return new SimpleSQLiteQuery(query);
    }

    /***
     * delete as
     * @param tableName name table for delte
     * @param where if null delete all or delete as where
     * @return count delete records
     */
    public final int delete(String tableName, String where) {
        int count = 0;
        mExecutorService = Executors.newSingleThreadExecutor();
        Callable<Integer> integerCallable = () -> mBaseDao.deleteAllRecords(sqlDelQuery( tableName,where));
        Future<Integer> integerFuture = mExecutorService.submit(integerCallable);
        try {
            count = integerFuture.get(100, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            shutdown(mExecutorService);
            Log.d(TAG, String.format("deleteAllRecords-> %s,COUNT:%d", tableName, count));
        }
        return count;
    }


    protected final void insertFooter(@NonNull BaseEntity baseEntity, boolean isServer) {
        baseEntity.setUserIdFk(isServer ? baseEntity.getUserIdFk() : Integer.parseInt(mSharePerf.getUserID()));
        baseEntity.setCreatedDateTime(isServer ? baseEntity.getCreatedDateTime() : DateUtils.getCurrentDTime());
        baseEntity.setLastModifiedDateTime(isServer ? baseEntity.getLastModifiedDateTime() : DateUtils.getCurrentDTime());
        baseEntity.setStatusSync(0);
        baseEntity.setStatusUpload(isServer ? 1 : 0);
    }

    protected void shutdown(ExecutorService executorService) {
        if (executorService != null && (!executorService.isShutdown())) {
            executorService.shutdown();
            Log.i(TAG, "ExecutorService.shutdown()");
        }
    }

    protected PagedList.Config configPagedList() {
        return (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                //.setPrefetchDistance(10)
                .setPageSize(20).build();
    }

}
