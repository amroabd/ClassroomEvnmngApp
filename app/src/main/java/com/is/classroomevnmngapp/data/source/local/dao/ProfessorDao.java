package com.is.classroomevnmngapp.data.source.local.dao;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomWarnings;

import com.is.classroomevnmngapp.data.source.local.entities.ProfessorEntity;
import com.is.classroomevnmngapp.utils.spinner.ListSpinner;

import java.util.List;

@Dao
public interface ProfessorDao {

    @Query("UPDATE Professors " +
            "SET id=:centerID,status_upload=:upload " +
            "WHERE localId=:localID")
    void updateStatusUpload(long localID, long centerID, int upload);

    @Insert
    void insertProfessor(ProfessorEntity professorEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ProfessorEntity> entities);

    @Query("SELECT * FROM Professors WHERE id = :professorId")
    ProfessorEntity getProfessorById(int professorId);


    @Query("SELECT * FROM Professors")
    LiveData<List<ProfessorEntity>> getAll();

    @Query("SELECT * FROM Professors")
    DataSource.Factory<Integer, ProfessorEntity> getAllProfessors();

    @Query("SELECT COUNT(localId) FROM Professors")
    int getCount();

    @Query("DELETE FROM Professors")
    int deleteAllRecords();

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT id AS idSpinnerS,name AS nameSpinner" +
            " FROM Professors ")
    List<ListSpinner> loadAsSpinnerData();

}
