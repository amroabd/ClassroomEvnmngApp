package com.is.classroomevnmngapp.data.source.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.is.classroomevnmngapp.data.source.local.entities.ControllerEntity;

import java.util.List;

@Dao
public interface ControllerDao {

    @Insert
    void insertController(ControllerEntity controllerEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ControllerEntity> entities);

    @Query("SELECT * FROM Controllers WHERE id = :controllerId")
    ControllerEntity getControllerById(int controllerId);

    @Query("SELECT * FROM Controllers")
    LiveData<List<ControllerEntity>> getAll();

    @Query("SELECT COUNT(localId) FROM Controllers")
    int GetCount();

    @Query("DELETE FROM Controllers")
    int deleteAllRecords();


}
