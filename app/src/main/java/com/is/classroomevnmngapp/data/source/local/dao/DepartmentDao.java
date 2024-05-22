package com.is.classroomevnmngapp.data.source.local.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomWarnings;

import com.is.classroomevnmngapp.data.source.local.entities.DepartmentEntity;
import com.is.classroomevnmngapp.utils.spinner.ListSpinner;

import java.util.List;

@Dao
public interface DepartmentDao {
    @Insert
    void insertDepartment(DepartmentEntity departmentEntity);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DepartmentEntity> entities);

    @Query("SELECT * FROM Departments WHERE id = :departmentId")
    DepartmentEntity getDepartmentById(int departmentId);

    @Query("SELECT * FROM Departments")
    LiveData<List<DepartmentEntity>> getAll();

    @Query("SELECT COUNT(localId) FROM Departments")
    int getCount();

    @Query("DELETE FROM Departments")
    int deleteAllRecords();

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT id AS idSpinnerS,name AS nameSpinner" +
            " FROM Departments ")
    List<ListSpinner> loadAsSpinnerData();
}