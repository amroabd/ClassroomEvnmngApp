package com.is.classroomevnmngapp.data.source.local.entities;

import androidx.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

public class BaseEntity {
    @SerializedName("is_active")
    @ColumnInfo(name = "is_active")
    protected int isActive;


    @SerializedName("status_upload")
    @ColumnInfo(name = "status_upload")
    protected int statusUpload;

    @SerializedName("status_sync")
    @ColumnInfo(name = "status_sync")
    protected int statusSync;


    @SerializedName("user_id_fk")
    @ColumnInfo(name = "user_id_fk", defaultValue = "0")
    protected int userIdFk;

    @SerializedName("create_date")
    @ColumnInfo(name = "create_date", defaultValue = "CURRENT_TIMESTAMP")
    String createdDateTime;

    @SerializedName("update_date")
    @ColumnInfo(name = "update_date", defaultValue = "CURRENT_TIMESTAMP")
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
