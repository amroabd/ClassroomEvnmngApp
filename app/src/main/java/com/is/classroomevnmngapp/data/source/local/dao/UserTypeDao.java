package com.is.classroomevnmngapp.data.source.local.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.is.classroomevnmngapp.data.source.local.entities.UserTypeEntity;

import java.util.List;

@Dao
public interface UserTypeDao {
    @Insert
    void insertUserType(UserTypeEntity userTypeEntity);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<UserTypeEntity> entities);

    @Query("SELECT * FROM UserTypes WHERE UserTypeID = :userTypeId")
    UserTypeEntity getUserTypeById(int userTypeId);


    @Query("SELECT * FROM UserTypes")
    LiveData<List<UserTypeEntity>> getAll();

    @Query("SELECT COUNT(userTypeId) FROM UserTypes")
    int GetCount();

    @Query("DELETE FROM UserTypes")
    int deleteAllRecords();

}
