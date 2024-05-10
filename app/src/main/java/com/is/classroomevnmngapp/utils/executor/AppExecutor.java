package com.is.classroomevnmngapp.utils.executor;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutor {
    private static final Object LOCK = new Object();
    private static AppExecutor mInstance;
    private final Executor mDiskIO;
    private final  Executor mainThread;

    public AppExecutor(Executor diskIO,Executor mainThread) {
        mDiskIO = diskIO;
        this.mainThread = mainThread;
    }


    public static AppExecutor getInstance() {
        if (mInstance == null) {
            synchronized (LOCK) {
                mInstance = new AppExecutor(Executors.newFixedThreadPool(2), new MainThreadExecutor());
            }
        }
        return mInstance;
    }

    public Executor diskIO() {
        return mDiskIO;
    }

    public Executor mainThread() {
        return mainThread;
    }

    private static class MainThreadExecutor implements Executor {
        private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }



/*    public void insert(final NameEntity nameEntity) {
        AppExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mNameDao.insertName(nameEntity);
            }
        });
    }*/



}
