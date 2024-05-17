package com.is.classroomevnmngapp.data.source.local.dao;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.is.classroomevnmngapp.data.model.JoinReserveALecture;
import com.is.classroomevnmngapp.data.source.local.entities.ReservationEntity;

import java.util.List;

@Dao
public interface ReservationDao {

    @Query("UPDATE Reservations " +
            "SET ReserveStatus=:status " +
            "WHERE localId=:localID")
    void updateReserveStatus(long localID, int status);

    @Query("UPDATE Reservations " +
            "SET ReserveId=:centerID,StatusUpload=:upload " +
            "WHERE localId=:localID")
    void updateStatusUpload(long localID, long centerID, int upload);

    @Insert
    Long insertReservation(ReservationEntity reservationEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ReservationEntity> entities);

    @Query("SELECT * FROM Reservations WHERE ReserveId = :reserveId")
    ReservationEntity getReservationById(int reserveId);


    @Query("SELECT * FROM Reservations")
    LiveData<List<ReservationEntity>> getAll();

    @Query("SELECT COUNT(localId) FROM Reservations")
    int getCount();

    @Query("DELETE FROM Reservations")
    int deleteAllRecords();


    //=========================
    @Query("SELECT lh.Title AS title," +
            " lh.localId AS lectureHallID,  " +
            " rs.localId AS reserveId, " +
            " rs.ReserveStartTime AS reserveStartTime, " +
            " rs.ReserveEndTime AS reserveEndTime ," +
            " rs.ReserveStatus AS reserveStatus ," +
            " rs.ReserveUsername AS reserveUsername " +
            " FROM LectureHalls  AS lh LEFT JOIN Reservations rs on rs.LectureHallIdFk=lh.localId ")
    DataSource.Factory<Integer, JoinReserveALecture>getReserveALectureList();

}
