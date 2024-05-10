package com.is.classroomevnmngapp.data.source.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.is.classroomevnmngapp.data.source.local.entities.DeviceEntity;

import java.util.List;

@Dao
public interface DeviceDao {
    @Insert
    void insertDevice(DeviceEntity deviceEntity);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DeviceEntity> entities);

    @Query("SELECT * FROM Devices WHERE DeviceID = :deviceId")
    DeviceEntity getDeviceById(int deviceId);

    @Query("SELECT * FROM Devices")
    LiveData<List<DeviceEntity>> getAll();

    @Query("SELECT COUNT(localId) FROM Devices")
    int GetCount();

    @Query("DELETE FROM Devices")
    int deleteAllRecords();

}
