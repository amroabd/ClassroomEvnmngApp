package com.is.classroomevnmngapp.data.source.local.dao;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomWarnings;

import com.is.classroomevnmngapp.data.source.local.entities.ControllerEntity;
import com.is.classroomevnmngapp.utils.spinner.ListSpinner;

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

    @Query("SELECT * FROM Controllers")
    DataSource.Factory<Integer, ControllerEntity> getAllController();

    @Query("SELECT COUNT(localId) FROM Controllers")
    int GetCount();

    @Query("DELETE FROM Controllers")
    int deleteAllRecords();

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT id AS idSpinnerS,name AS nameSpinner" +
            " FROM Controllers ")
    List<ListSpinner> loadAsSpinnerData();

}
