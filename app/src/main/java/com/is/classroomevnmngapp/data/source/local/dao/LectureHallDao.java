package com.is.classroomevnmngapp.data.source.local.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.is.classroomevnmngapp.data.source.local.entities.LectureHallEntity;

import java.util.List;

@Dao
public interface LectureHallDao {



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertLectureHall(LectureHallEntity lectureHallEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<LectureHallEntity> entities);

    @Query("SELECT * FROM LectureHalls WHERE LectureHallID = :lectureHallId")
    LectureHallEntity getLectureHallById(int lectureHallId);

    @Query("SELECT * FROM LectureHalls")
    DataSource.Factory<Integer,LectureHallEntity> getAll();

    @Query("SELECT COUNT(localId) FROM LectureHalls")
    int getCount();

    @Query("DELETE FROM LectureHalls")
    int deleteAllRecords();
}
