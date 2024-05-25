package com.is.classroomevnmngapp.data.source.local.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;


@Entity(tableName = "Reservations",
        indices = @Index(value = {"lecture_hall_id_fk", "reserve_start_time", "reserve_status"}),
        foreignKeys = @ForeignKey(entity = LectureHallEntity.class,
                parentColumns = "id",
                childColumns = "lecture_hall_id_fk",
                onUpdate = ForeignKey.CASCADE))
public class ReservationEntity extends BaseEntity {
    @PrimaryKey(autoGenerate = true)
    private int localId;

    @SerializedName("Id")
    @ColumnInfo(name = "id")
    private int reserveId;

    @SerializedName("lecture_hall_id_fk")
    @ColumnInfo(name = "lecture_hall_id_fk")
    private int lectureHallIdFk;

    @SerializedName("reserve_username_fk")
    @ColumnInfo(name = "reserve_username_fk")
    private String reserveUsername;

    @SerializedName("reserve_date")
    @ColumnInfo(name = "reserve_date")
    private String reserveDate;

    @SerializedName("reserve_start_time")
    @ColumnInfo(name = "reserve_start_time")
    private String reserveStartTime;

    @SerializedName("reserve_end_time")
    @ColumnInfo(name = "reserve_end_time")
    private String reserveEndTime;

    @SerializedName("reserve_status")
    @ColumnInfo(name = "reserve_status")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationEntity)) return false;
        ReservationEntity entity = (ReservationEntity) o;
        return localId == entity.localId &&
                reserveId == entity.reserveId &&
                lectureHallIdFk == entity.lectureHallIdFk &&
                reserveStatus == entity.reserveStatus &&
                reserveUsername.equals(entity.reserveUsername) &&
                reserveDate.equals(entity.reserveDate) &&
                reserveStartTime.equals(entity.reserveStartTime) &&
                reserveEndTime.equals(entity.reserveEndTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(localId, reserveId, lectureHallIdFk, reserveUsername, reserveDate, reserveStartTime, reserveEndTime, reserveStatus);
    }

    @NonNull
    @Override
    public String toString() {
        return "ReservationEntity{" +
                "localId=" + localId +
                ", reserveId=" + reserveId +
                ", lectureHallIdFk=" + lectureHallIdFk +
                ", reserveUsername='" + reserveUsername + '\'' +
                ", reserveDate='" + reserveDate + '\'' +
                ", reserveStartTime='" + reserveStartTime + '\'' +
                ", reserveEndTime='" + reserveEndTime + '\'' +
                ", reserveStatus=" + reserveStatus +
                ", isActive=" + isActive +
                ", statusUpload=" + statusUpload +
                ", userIdFk=" + userIdFk +
                ", createdDateTime='" + createdDateTime + '\'' +
                ", lastModifiedDateTime='" + lastModifiedDateTime + '\'' +
                "} " + super.toString();
    }
}
