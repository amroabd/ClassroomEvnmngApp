package com.is.classroomevnmngapp.data.source.local.dao;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Update;

import com.is.classroomevnmngapp.data.source.local.entities.UserEntity;
import com.is.classroomevnmngapp.utils.spinner.ListSpinner;

import java.util.List;

@Dao
public interface UserDao {

    @Query("UPDATE Users " +
            "SET userId=:centerID,status=:upload " +
            "WHERE localId=:localID")
    void updateStatusUpload(long localID, long centerID, int upload);

    @Insert
    void insertUser(UserEntity userEntity);

    @Update
    void update(UserEntity userEntity);

    @Delete
    void delete(UserEntity userEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<UserEntity> entities);

    @Query("DELETE FROM Users")
    int deleteAllRecords();

    //===================================================//

    @Query("SELECT * FROM Users ")
    List<UserEntity> getAll();

    @Query("SELECT * FROM Users WHERE status=0 LIMIT :limit ")
    List<UserEntity> getDataAsLimit(int limit);

    @Query("SELECT * FROM Users")
    LiveData<List<UserEntity>> getAllLiveData();

    @Query("SELECT * FROM Users")
    DataSource.Factory<Integer, UserEntity> getAllUsers();

    @Query("SELECT * FROM Users WHERE UserID = :userId")
    UserEntity getUserById(int userId);


    @Query("SELECT COUNT(userId) FROM Users")
    int getCount();

    @Query("SELECT COUNT(userId) FROM Users  WHERE Status = :status")
    int getCountAsUploadStatus(int status);


    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT userId AS idSpinnerS,Username AS nameSpinner" +
            " FROM Users ")
    List<ListSpinner> loadAsSpinnerData();


}
