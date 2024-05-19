package com.is.classroomevnmngapp.data.source.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.is.classroomevnmngapp.data.source.local.entities.ScheduleEntity;

import java.util.List;

@Dao
public interface ScheduleDao {
    @Insert
    void insertSchedule(ScheduleEntity scheduleEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ScheduleEntity> entities);

    @Query("SELECT * FROM Schedules WHERE id = :scheduleId")
    ScheduleEntity getScheduleById(int scheduleId);

    @Query("SELECT * FROM Schedules")
    LiveData<List<ScheduleEntity>> getAll();

    @Query("SELECT COUNT(localId) FROM Schedules")
    int getCount();

    @Query("DELETE FROM Schedules")
    int deleteAllRecords();
}
