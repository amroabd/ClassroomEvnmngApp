package com.is.classroomevnmngapp.data.source.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.is.classroomevnmngapp.data.source.local.entities.ProfessorEntity;

import java.util.List;

@Dao
public interface ProfessorDao {

    @Insert
    void insertProfessor(ProfessorEntity professorEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ProfessorEntity> entities);

    @Query("SELECT * FROM Professors WHERE id = :professorId")
    ProfessorEntity getProfessorById(int professorId);


    @Query("SELECT * FROM Professors")
    LiveData<List<ProfessorEntity>> getAll();

    @Query("SELECT COUNT(localId) FROM Professors")
    int getCount();

    @Query("DELETE FROM Professors")
    int deleteAllRecords();


}
