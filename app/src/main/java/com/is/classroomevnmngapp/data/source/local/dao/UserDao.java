package com.is.classroomevnmngapp.data.source.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.is.classroomevnmngapp.data.source.local.entities.UserEntity;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertUser(UserEntity userEntity);

    @Query("SELECT * FROM Users WHERE UserID = :userId")
    UserEntity getUserById(int userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<UserEntity> entities);

    @Query("SELECT * FROM Users")
    LiveData<List<UserEntity>> getAll();

    @Query("SELECT COUNT(userId) FROM Users")
    int GetCount();

    @Query("DELETE FROM Users")
    int deleteAllRecords();
}
