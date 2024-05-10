package com.is.classroomevnmngapp.data.source.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.is.classroomevnmngapp.data.source.local.entities.UniversityEntity;

import java.util.List;

@Dao
public interface UniversityDao {
    @Insert
    void insertUniversity(UniversityEntity universityEntity);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<UniversityEntity> entities);

    @Query("SELECT * FROM University WHERE UniversityID = :universityId")
    UniversityEntity getUniversityById(int universityId);

    @Query("SELECT * FROM University")
    LiveData<List<UniversityEntity>> getAll();

    @Query("SELECT COUNT(universityId) FROM University")
    int GetCount();

    @Query("DELETE FROM University")
    int deleteAllRecords();

}
