package com.is.classroomevnmngapp.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.is.classroomevnmngapp.data.source.local.dao.DeviceDao;
import com.is.classroomevnmngapp.data.source.local.entities.DeviceEntity;
import com.is.classroomevnmngapp.utils.executor.AppExecutor;

import java.util.List;

import static com.is.classroomevnmngapp.utils.constant.NameTableConst.NAME_DEVICES;


public final class DeviceRepository extends BaseRepository implements DeviceDao {
    private static final String TAG = DeviceRepository.class.getSimpleName();
    private static DeviceRepository instance;
    private final DeviceDao deviceDao;

    private DeviceRepository(Context context) {
        super(context);
        Log.d(TAG, String.format("getInstance:Creating new instance %s", TAG));
        deviceDao = mDb.deviceDao();
    }

    public static DeviceRepository getInstance(Context context) {
        if (instance == null) {
            instance = new DeviceRepository(context);
        }
        return instance;
    }


    public void insertDevice(DeviceEntity deviceEntity) {
        AppExecutor.getInstance().diskIO().execute(() -> deviceDao.insertDevice(deviceEntity));
    }


    public void insertAll(List<DeviceEntity> entities) {
        AppExecutor.getInstance().diskIO().execute(() -> deviceDao.insertAll(entities));
    }


    public DeviceEntity getDeviceById(int deviceIdId) {
        return deviceDao.getDeviceById(deviceIdId);
    }


    public LiveData<List<DeviceEntity>> getAll() {
        return deviceDao.getAll();
    }


    public int getCount() {
        return getCount("deviceId", NAME_DEVICES);
        // return mDb.departmentDao().getCount();
    }


    public int deleteAllRecords() {
        return deviceDao.deleteAllRecords();
    }


}
