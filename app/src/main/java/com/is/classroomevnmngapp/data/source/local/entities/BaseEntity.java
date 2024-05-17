package com.is.classroomevnmngapp.data.source.local.entities;

import androidx.room.ColumnInfo;

public class BaseEntity {
    @ColumnInfo(name = "IsActive")
    protected int isActive;

    @ColumnInfo(name = "StatusUpload")
    protected int statusUpload;

    @ColumnInfo(name = "StatusSync")
    protected int statusSync;


    @ColumnInfo(name = "UserIDFk",defaultValue = "0")
    protected int userIdFk;


    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    String createdDateTime;
    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    String lastModifiedDateTime;
    @ColumnInfo(defaultValue = "('Created at' || CURRENT_TIMESTAMP)")
    public String notice;

    //setter and getter=======================================


    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getLastModifiedDateTime() {
        return lastModifiedDateTime;
    }

    public void setLastModifiedDateTime(String lastModifiedDateTime) {
        this.lastModifiedDateTime = lastModifiedDateTime;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

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



    public int getUserIdFk() {
        return userIdFk;
    }

    public void setUserIdFk(int userIdFk) {
        this.userIdFk = userIdFk;
    }
}
