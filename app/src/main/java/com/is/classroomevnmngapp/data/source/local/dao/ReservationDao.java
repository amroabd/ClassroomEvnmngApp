package com.is.classroomevnmngapp.data.source.local.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.is.classroomevnmngapp.data.model.JoinReserveALecture;
import com.is.classroomevnmngapp.data.source.local.entities.ReservationEntity;

import java.util.List;

@Dao
public interface ReservationDao {

    @Query("UPDATE Reservations " +
            "SET reserve_status=:status ,status_upload=:statusUpload " +
            "WHERE id=:id")
    void updateReserveStatus(long id, int status,int statusUpload);

    @Query("UPDATE Reservations " +
            "SET id=:centerID,status_upload=:upload " +
            "WHERE localId=:localID")
    void updateStatusUpload(long localID, long centerID, int upload);

    @Update
    void update(ReservationEntity reservationEntity);
//---------------------------------------------
    @Insert
    Long insertReservation(ReservationEntity reservationEntity);

    @Transaction
    default long insertWithTriggerLogic(ReservationEntity entity) {
        long newId = insertReservation(entity);
        // Implement the trigger logic here
        if (entity.getReserveId() <= 0) {
            updateIdAfterInsert(newId);
        }
        return newId;
    }

    @Query("UPDATE Reservations SET id = :localId WHERE localId = :localId")
    void updateIdAfterInsert( long localId);
    //---------------------------------------

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ReservationEntity> entities);

    @Query("SELECT * FROM Reservations WHERE id = :reserveId")
    ReservationEntity getReservationById(int reserveId);

    @Query("SELECT * FROM Reservations WHERE status_upload IN(0,3)  LIMIT :limit ")
    List<ReservationEntity> getDataAsLimit(int limit);

    @Query("SELECT * FROM Reservations  ")
    List<ReservationEntity> getDataAll();

    @Query("SELECT COUNT(localId) FROM Reservations WHERE reserve_status=1 ")
    int getReservedCount();

    @Query("DELETE FROM Reservations")
    int deleteAllRecords();


    //=========================


    @Query("SELECT lh.name AS title," +
            "  lh.id AS lectureHallID,  " +
            " rs.id AS reserveId, " +
            " rs.reserve_start_time AS reserveStartTime, " +
            " rs.reserve_end_time AS reserveEndTime ," +
            " rs.reserve_status AS reserveStatus ," +
            " rs.reserve_username_fk AS reserveUsername " +
            " FROM LectureHalls  AS lh INNER JOIN Reservations rs on rs.lecture_hall_id_fk=lh.id" +
            " WHERE rs.reserve_username_fk=:id" )
    DataSource.Factory<Integer, JoinReserveALecture>getReserveALectureHistList(String id);

}
