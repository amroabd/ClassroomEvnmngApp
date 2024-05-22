package com.is.classroomevnmngapp.data.source.local.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Transaction;
import androidx.room.Update;

import com.is.classroomevnmngapp.data.source.local.entities.LectureHallEntity;
import com.is.classroomevnmngapp.utils.spinner.ListSpinner;

import java.util.List;

@Dao
public interface LectureHallDao {

    @Query("UPDATE LectureHalls " +
            "SET id=:centerID,status_upload=:upload " +
            "WHERE localId=:localID")
    void updateStatusUpload(long localID, long centerID, int upload);
    @Update
    Long update(LectureHallEntity lectureHallEntity);

    @Delete
    void delete(LectureHallEntity lectureHallEntity);
    //---------------------------------------------

    @Transaction
    default long insertWithTriggerLogic(LectureHallEntity entity) {
        long newId = insertLectureHall(entity);
        // Implement the trigger logic here
        if (entity.getLectureHallId() <= 0) {
            updateIdAfterInsert(newId);
        }
        return newId;
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertLectureHall(LectureHallEntity lectureHallEntity);

    @Query("UPDATE LectureHalls SET id = :localId WHERE localId = :localId")
    void updateIdAfterInsert( long localId);

    //---------------------------------------

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<LectureHallEntity> entities);


    @Query("SELECT * FROM LectureHalls WHERE id = :lectureHallId")
    LectureHallEntity getLectureHallById(int lectureHallId);

    @Query("SELECT * FROM LectureHalls WHERE status_upload=0 LIMIT :limit")
    List<LectureHallEntity> getDataAsLimit(int limit);


    @Query("SELECT * FROM LectureHalls")
    List<LectureHallEntity> getAll();

    @Query("SELECT * FROM LectureHalls")
    DataSource.Factory<Integer,LectureHallEntity> getAllHallEntityFactory();

    @Query("SELECT COUNT(localId) FROM LectureHalls")
    int getCount();

    @Query("SELECT COUNT(localId) FROM LectureHalls WHERE status_upload=:status")
    int getCountAsUploadStatus(int status);

    @Query("DELETE FROM LectureHalls")
    int deleteAllRecords();



    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT id AS idSpinnerS,name AS nameSpinner" +
            " FROM LectureHalls ")
    List<ListSpinner> loadAsSpinnerData();
}
