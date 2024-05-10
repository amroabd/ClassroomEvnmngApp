package com.is.classroomevnmngapp.data.source.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Devices")
public class DeviceEntity {

    @PrimaryKey(autoGenerate = true)
    private int localId;
    @ColumnInfo(name = "DeviceID")
    private int deviceId;

    @ColumnInfo(name = "ControllerIDFK")
    private int controllerIdFK;

    @ColumnInfo(name = "Type")
    private String type;

    @ColumnInfo(name = "Status")
    private int status;

    //--------------------

    public int getLocalId() {
        return localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getControllerIdFK() {
        return controllerIdFK;
    }

    public void setControllerIdFK(int controllerIdFK) {
        this.controllerIdFK = controllerIdFK;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
