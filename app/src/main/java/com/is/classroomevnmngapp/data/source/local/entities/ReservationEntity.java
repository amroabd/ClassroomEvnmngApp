package com.is.classroomevnmngapp.data.source.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "Reservations")
public class ReservationEntity extends BaseEntity {
    @PrimaryKey(autoGenerate = true)
    private int localId;

    @ColumnInfo(name = "ReserveId")
    private int reserveId;

    @ColumnInfo(name = "ReserveUsername")
    private String reserveUsername;

    @ColumnInfo(name = "ReserveDate")
    private String reserveDate;
    @ColumnInfo(name = "ReserveTime")
    private String reserveTime;

    //-----------------------


    public String getReserveUsername() {
        return reserveUsername;
    }

    public void setReserveUsername(String reserveUsername) {
        this.reserveUsername = reserveUsername;
    }

    public int getLocalId() {
        return localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    public int getReserveId() {
        return reserveId;
    }

    public void setReserveId(int reserveId) {
        this.reserveId = reserveId;
    }

    public String getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(String reserveDate) {
        this.reserveDate = reserveDate;
    }

    public String getReserveTime() {
        return reserveTime;
    }

    public void setReserveTime(String reserveTime) {
        this.reserveTime = reserveTime;
    }
}
