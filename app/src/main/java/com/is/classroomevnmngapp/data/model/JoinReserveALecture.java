package com.is.classroomevnmngapp.data.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

public class JoinReserveALecture {

    private int lectureHallID;
    private String title;
    private int reserveId;
    private String reserveUsername;
    private String reserveStartTime;
    private String reserveEndTime;
    private int reserveStatus;

    public int getLectureHallID() {
        return lectureHallID;
    }

    public void setLectureHallID(int lectureHallID) {
        this.lectureHallID = lectureHallID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReserveId() {
        return reserveId;
    }

    public void setReserveId(int reserveId) {
        this.reserveId = reserveId;
    }

    public String getReserveUsername() {
        return reserveUsername;
    }

    public void setReserveUsername(String reserveUsername) {
        this.reserveUsername = reserveUsername;
    }

    public String getReserveStartTime() {
        return reserveStartTime;
    }

    public void setReserveStartTime(String reserveStartTime) {
        this.reserveStartTime = reserveStartTime;
    }

    public String getReserveEndTime() {
        return reserveEndTime;
    }

    public void setReserveEndTime(String reserveEndTime) {
        this.reserveEndTime = reserveEndTime;
    }

    public int getReserveStatus() {
        return reserveStatus;
    }

    public void setReserveStatus(int reserveStatus) {
        this.reserveStatus = reserveStatus;
    }

    //-===========================
    public static final DiffUtil.ItemCallback<JoinReserveALecture>DIFF_CALLBACK =new DiffUtil.ItemCallback<JoinReserveALecture>() {

        @Override
        public boolean areItemsTheSame(@NonNull JoinReserveALecture oldItem, @NonNull JoinReserveALecture newItem) {
            return oldItem.reserveId == newItem.reserveId;
        }

        @Override
        public boolean areContentsTheSame(@NonNull JoinReserveALecture oldItem, @NonNull JoinReserveALecture newItem) {
            return oldItem.equals(newItem);
        }
    };
    //==========================


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JoinReserveALecture)) return false;
        JoinReserveALecture that = (JoinReserveALecture) o;
        return lectureHallID == that.lectureHallID &&
                reserveId == that.reserveId &&
                reserveStatus == that.reserveStatus &&
                Objects.equals(reserveStartTime, that.reserveStartTime) &&
                Objects.equals(reserveEndTime, that.reserveEndTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lectureHallID, reserveId, reserveStartTime, reserveEndTime, reserveStatus);
    }
}
