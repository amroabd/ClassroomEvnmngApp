package com.is.classroomevnmngapp.data.source.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.is.classroomevnmngapp.data.source.local.entities.LectureHallEntity;

import java.util.List;

@Dao
public interface LectureHallDao {
    @Insert
    void insertLectureHall(LectureHallEntity lectureHallEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<LectureHallEntity> entities);

    @Query("SELECT * FROM LectureHalls WHERE LectureHallID = :lectureHallId")
    LectureHallEntity getLectureHallById(int lectureHallId);

    @Query("SELECT * FROM LectureHalls")
    LiveData<List<LectureHallEntity>> getAll();

    @Query("SELECT COUNT(localId) FROM LectureHalls")
    int GetCount();

    @Query("DELETE FROM LectureHalls")
    int deleteAllRecords();
}
