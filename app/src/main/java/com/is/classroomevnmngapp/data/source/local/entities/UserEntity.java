package com.is.classroomevnmngapp.data.source.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Users")
public class UserEntity  {

    @PrimaryKey
    private int userId;

    @ColumnInfo(name = "Username")
    private String username;

    @ColumnInfo(name = "Password")
    private String password;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "phone")
    private String phone;

    @ColumnInfo(name = "UserTypeIDFK")
    private int userTypeIdFk;

    @ColumnInfo(name = "Status")
    private int status;

    @ColumnInfo(name = "IsActive")
    private int isActive;

    @ColumnInfo(name = "CreatedAt")
    private String createdAt;

    //-----------------------------

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getUserTypeIdFk() {
        return userTypeIdFk;
    }

    public void setUserTypeIdFk(int userTypeIdFk) {
        this.userTypeIdFk = userTypeIdFk;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public int getIsActive() {
        return isActive;
    }


    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }


    public String getCreatedAt() {
        return createdAt;
    }


    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
