package com.is.classroomevnmngapp.data.source.local.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.is.classroomevnmngapp.data.source.local.entities.LectureHallEntity;

import java.util.List;

@Dao
public interface LectureHallDao {


    //---------------------------------------------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertLectureHall(LectureHallEntity lectureHallEntity);

    @Transaction
    default long insertWithTriggerLogic(LectureHallEntity entity) {
        long newId = insertLectureHall(entity);
        // Implement the trigger logic here
        if (entity.getLectureHallId() <= 0) {
            updateIdAfterInsert(newId);
        }
        return newId;
    }

    @Query("UPDATE LectureHalls SET id = :localId WHERE localId = :localId")
    void updateIdAfterInsert( long localId);
    //---------------------------------------

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<LectureHallEntity> entities);

    @Query("SELECT * FROM LectureHalls WHERE id = :lectureHallId")
    LectureHallEntity getLectureHallById(int lectureHallId);

    @Query("SELECT * FROM LectureHalls")
    DataSource.Factory<Integer,LectureHallEntity> getAll();

    @Query("SELECT COUNT(localId) FROM LectureHalls")
    int getCount();

    @Query("DELETE FROM LectureHalls")
    int deleteAllRecords();
}
