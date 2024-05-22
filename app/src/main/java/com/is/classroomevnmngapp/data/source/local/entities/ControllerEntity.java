package com.is.classroomevnmngapp.data.source.local.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "Controllers")
public class ControllerEntity  extends BaseEntity{
    @PrimaryKey(autoGenerate = true)
    private int localId;

    @ColumnInfo(name = "id")
    private int controllerId;

    @SerializedName("name")
    private String name;


    @ColumnInfo(name = "lecture_hall_id_fk")
    private int lectureHallIdFK;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "iP_address")
    private String ipAddress;

    //----------------


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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