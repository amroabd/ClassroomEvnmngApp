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

    @ColumnInfo(name = "LectureHallIdFk")
    private int lectureHallIdFk;

    @ColumnInfo(name = "ReserveUsername")
    private String reserveUsername;

    @ColumnInfo(name = "ReserveDate")
    private String reserveDate;
    @ColumnInfo(name = "ReserveStartTime")
    private String reserveStartTime;
    @ColumnInfo(name = "ReserveEndTime")
    private String reserveEndTime;
    @ColumnInfo(name = "ReserveStatus")
    private int reserveStatus;

    //-----------------------


    public String getReserveEndTime() {
        return reserveEndTime;
    }

    public void setReserveEndTime(String reserveEndTime) {
        this.reserveEndTime = reserveEndTime;
    }

    public int getLectureHallIdFk() {
        return lectureHallIdFk;
    }

    public void setLectureHallIdFk(int lectureHallIdFk) {
        this.lectureHallIdFk = lectureHallIdFk;
    }

    public int getReserveStatus() {
        return reserveStatus;
    }

    public void setReserveStatus(int reserveStatus) {
        this.reserveStatus = reserveStatus;
    }

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

    public String getReserveStartTime() {
        return reserveStartTime;
    }

    public void setReserveStartTime(String reserveStartTime) {
        this.reserveStartTime = reserveStartTime;
    }
}
