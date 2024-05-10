package com.is.classroomevnmngapp.data.source.local.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Controllers")
public class ControllerEntity  extends BaseEntity{
    @PrimaryKey(autoGenerate = true)
    private int localId;

    @ColumnInfo(name = "ControllerID")
    private int controllerId;

    @ColumnInfo(name = "LectureHallIDFK")
    private int lectureHallIdFK;

    @ColumnInfo(name = "Type")
    private String type;

    @ColumnInfo(name = "IPAddress")
    private String ipAddress;

    //----------------


    public int getLocalId() {
        return localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    public int getControllerId() {
        return controllerId;
    }

    public void setControllerId(int controllerId) {
        this.controllerId = controllerId;
    }

    public int getLectureHallIdFK() {
        return lectureHallIdFK;
    }

    public void setLectureHallIdFK(int lectureHallIdFK) {
        this.lectureHallIdFK = lectureHallIdFK;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}