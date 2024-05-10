package com.is.classroomevnmngapp.data.source.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.is.classroomevnmngapp.data.source.local.entities.ReservationEntity;

import java.util.List;

@Dao
public interface ReservationDao {

    @Insert
    void insertReservation(ReservationEntity reservationEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ReservationEntity> entities);

    @Query("SELECT * FROM Reservations WHERE ReserveId = :reserveId")
    ReservationEntity getReservationById(int reserveId);


    @Query("SELECT * FROM Reservations")
    LiveData<List<ReservationEntity>> getAll();

    @Query("SELECT COUNT(localId) FROM Reservations")
    int GetCount();

    @Query("DELETE FROM Reservations")
    int deleteAllRecords();

}
