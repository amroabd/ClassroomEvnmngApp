package com.is.classroomevnmngapp.data.source.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "UserTypes")
public class UserTypeEntity {

    @PrimaryKey(autoGenerate = true)
    private final int userTypeId;
    @ColumnInfo(name = "UserType")
    private final String userType;

    public UserTypeEntity(int userTypeId, String userType) {
        this.userTypeId = userTypeId;
        this.userType = userType;
    }

    public int getUserTypeId() {
        return userTypeId;
    }

    public String getUserType() {
        return userType;
    }

}