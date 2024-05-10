package com.is.classroomevnmngapp.data.source.local.entities;

import androidx.room.ColumnInfo;

public class BaseEntity {
    @ColumnInfo(name = "IsActive")
    protected int isActive;

    @ColumnInfo(name = "StatusUpload")
    protected int statusUpload;

    @ColumnInfo(name = "StatusSync")
    protected int statusSync;

    @ColumnInfo(name = "CreatedAt")
    protected String createdAt;

    @ColumnInfo(name = "UserIDFk")
    protected int userIdFk;

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public int getStatusUpload() {
        return statusUpload;
    }

    public void setStatusUpload(int statusUpload) {
        this.statusUpload = statusUpload;
    }

    public int getStatusSync() {
        return statusSync;
    }

    public void setStatusSync(int statusSync) {
        this.statusSync = statusSync;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getUserIdFk() {
        return userIdFk;
    }

    public void setUserIdFk(int userIdFk) {
        this.userIdFk = userIdFk;
    }
}
