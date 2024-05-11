package com.is.classroomevnmngapp.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.paging.PagedList;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.is.classroomevnmngapp.data.source.local.AppDatabase;
import com.is.classroomevnmngapp.data.source.local.dao.BaseDao;
import com.is.classroomevnmngapp.utils.Log1;
import com.is.classroomevnmngapp.utils.SharePerf;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BaseRepository {
    private static final String TAG = BaseRepository.class.getSimpleName();
    protected ExecutorService mExecutorService;
    private final BaseDao mBaseDao;
    protected final AppDatabase mDb;
    protected final SharePerf mSharePerf;

    public BaseRepository(Context context) {
        Log1.d(TAG,"=========>BaseRep(context)<===========");
        mDb = AppDatabase.getInstance(context.getApplicationContext());
        mBaseDao = mDb.mainMenuDao();
        mSharePerf=SharePerf.getInstance(context.getApplicationContext());
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
            count = integerFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }finally {
            shutdown(mExecutorService);
            Log.d(TAG, "getCount-> "+tableName+",COUNT:" + count);
        }
        return count;
    }

    protected void shutdown(ExecutorService executorService) {
        if (executorService != null && (!executorService.isShutdown())) {
            executorService.shutdown();
            Log.i(TAG, "ExecutorService.shutdown()");
        }
    }

    protected  PagedList.Config configPagedList(){
       return (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                //.setPrefetchDistance(10)
                .setPageSize(20).build();
    }

}
