package com.is.classroomevnmngapp.data.source.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Devices")
public class DeviceEntity {

    @PrimaryKey(autoGenerate = true)
    private int localId;
    @ColumnInfo(name = "id")
    private int deviceId;
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "controll_id_fk")
    private int controllerIdFK;

    @ColumnInfo(name = "type_id")
    private String type;

    @ColumnInfo(name = "status")
    private int status;

    //--------------------


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
