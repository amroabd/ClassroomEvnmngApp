package com.is.classroomevnmngapp.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.sqlite.db.SimpleSQLiteQuery;

import com.is.classroomevnmngapp.data.source.local.dao.HelperDao;
import com.is.classroomevnmngapp.data.source.local.entities.HelperEntity;
import com.is.classroomevnmngapp.data.source.remote.DownloadCallback;
import com.is.classroomevnmngapp.data.source.remote.DownloadClient;
import com.is.classroomevnmngapp.data.source.remote.InsertResultDownloadState;
import com.is.classroomevnmngapp.utils.executor.AppExecutor;
import com.is.classroomevnmngapp.utils.spinner.ListSpinner;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static com.is.classroomevnmngapp.utils.constant.NameTableConst.NAME_INIT_SUB_MENU;


public final class HelperRepository extends BaseRepository {
    private static final String TAG = HelperRepository.class.getSimpleName();
    //-----------
    private static HelperRepository mInstance;
    protected final HelperDao.MainMenu mainMenuDao;
    private final HelperDao.SubMenu subMenuDao;

    private final DownloadClient mDownloadClient;

    //-----------

    private HelperRepository(Context context) {
        super(context);
        mainMenuDao = mDb.mainMenuDao();
        subMenuDao = mDb.subMenuDao();

        mDownloadClient = new DownloadClient(context);
        //-----
        //insertDefault();
        if (getCount("id",NAME_INIT_SUB_MENU)<=0) {
            initDefaultMenu();
        }
    }

    public static HelperRepository getInstance(Context context) {
        if (mInstance == null) {
            Log.d(TAG, "getInstance:Creating new instance MenuRepository");
            mInstance = new HelperRepository(context);
        }
        return mInstance;
    }

    private void initDefaultMenu(){
        List<HelperEntity.SubMenu>subMenus= Arrays.asList(
                new HelperEntity.SubMenu("1","classroom-1","1"),
                new HelperEntity.SubMenu("2","classroom-2","1"),
                new HelperEntity.SubMenu("3","classroom-3","1"),
                new HelperEntity.SubMenu("4","classroom-4","1"),
                new HelperEntity.SubMenu("1","sensor-1","2"),
                  new HelperEntity.SubMenu("2","sensor-2","2"),
                new HelperEntity.SubMenu("3","sensor-3","2")
                );
        AppExecutor.getInstance().diskIO().execute(() -> subMenuDao.insertAll(subMenus));

    }



    /***
     *  list data
     * @param callback return result data
     */
    public void loadAllSubMenuById(String mainID, GetResultCallback<List<HelperEntity.SubMenu>> callback) {
        AppExecutor.getInstance().diskIO().execute(() -> {
            List<HelperEntity.SubMenu> list = subMenuDao.loadAllById(mainID);
            Log.d(TAG, "subMenSIZE:" + list.size());
            AppExecutor.getInstance().mainThread().execute(() -> callback.onResult(list));
        });
    }

    /***
     *  list data
     * @param callback return result data
     */
    public void getSpinnerList(String mainID, GetResultCallback<List<ListSpinner>> callback) {
        AppExecutor.getInstance().diskIO().execute(() -> {
            List<ListSpinner> list = subMenuDao.findById(mainID);
            Log.d(TAG, "ListSpinner:" + list.size());
            AppExecutor.getInstance().mainThread().execute(() -> callback.onResult(list));
        });
    }



    //=========================================================================
    public void downMainMenu(InsertResultDownloadState<String> resultState) {
        mDownloadClient.downAllHeaderData(new DownloadCallback<List<HelperEntity.MainMenu>>() {
            @Override
            public void onSuccess(List<HelperEntity.MainMenu> tList) {
                AtomicInteger count = new AtomicInteger();
                Log.d(TAG, "Insert all row in table MainMenu");
                AppExecutor.getInstance().diskIO().execute(() -> {
                    for (HelperEntity.MainMenu mainMenu : tList) {
                        mainMenuDao.insertMainMenu(mainMenu);
                        count.getAndIncrement();
                    }
                    AppExecutor.getInstance().mainThread().execute(() -> resultState.onCompleted("Completed:" + count.get()));
                });
                // Log.d(TAG, "Insert Success MainMenu:" + getCount());
            }

            @Override
            public void onError(String Error) {
                resultState.onFailure(Error);
            }
        });
    }

    //2--------------------
    public void downSubMenu(InsertResultDownloadState<String> resultState) {
        mDownloadClient.downAllSubHeaderData(new DownloadCallback<List<HelperEntity.SubMenu>>() {
            @Override
            public void onSuccess(List<HelperEntity.SubMenu> tList) {
                Log.d(TAG, "Insert all row in table subMenus");
                int total = tList.size();
                AtomicInteger count = new AtomicInteger();
                AppExecutor.getInstance().diskIO().execute(() -> {
                    for (HelperEntity.SubMenu subMenu : tList) {
                        subMenuDao.insertSubMenu(subMenu);
                        count.getAndIncrement();
                        callback(resultState, total, count.get());
                    }
                    AppExecutor.getInstance().mainThread().execute(() -> resultState.onCompleted("Completed:" + count.get()));
                    Log.d(TAG, "Insert Success subMenus:" + count.get());
                });
                Log.d(TAG, "in Insert  subMenus:" + getCount("id", NAME_INIT_SUB_MENU));
            }

            @Override
            public void onError(String Error) {
                resultState.onFailure(Error);
            }
        });

    }




    //return state for ui
    private void callback(InsertResultDownloadState<String> insertResultDownloadState, int total, int currentCount) {
        AppExecutor.getInstance().mainThread().execute(() -> insertResultDownloadState.onUpdateProgress(calRate(total, currentCount)));
    }

    //calculate rate progress
    private float calRate(int total, int currentCount) {
        float mod = (float) total / 100;
        return (float) currentCount / mod;
    }

//==========================================================================


    public int deleteAllMainMen() {
        int count = 0;
        ExecutorService mExecutorService = Executors.newSingleThreadExecutor();
        Callable<Integer> integerCallable = mainMenuDao::deleteAllRecords;
        Future<Integer> integerFuture = mExecutorService.submit(integerCallable);
        try {
            count = integerFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        shutdown(mExecutorService);
        Log.d(TAG, "delete->COUNT MainMenu:" + count);
        return count;
    }

    public int deleteAllSubMen() {
        int count = 0;
        ExecutorService mExecutorService = Executors.newSingleThreadExecutor();
        Callable<Integer> integerCallable = subMenuDao::deleteAllRecords;
        Future<Integer> integerFuture = mExecutorService.submit(integerCallable);
        try {
            count = integerFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        shutdown(mExecutorService);
        Log.d(TAG, "delete->COUNT SubMenu:" + count);
        return count;
    }




   //--------------------

    /**
     * to merge data from other shm and wal files  to db ,run this method
     * useful before taking backup
     */
    public void checkPoint(){
       AppExecutor.getInstance().diskIO().execute(() ->
               mainMenuDao.checkPoint(new SimpleSQLiteQuery("pragma wal_checkpoint(full)")));
   }
}