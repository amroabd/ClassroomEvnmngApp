package com.is.classroomevnmngapp.data.source.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Schedules")
public class ScheduleEntity extends BaseEntity {

    @PrimaryKey(autoGenerate = true)
    private int localId;

    @ColumnInfo(name = "ScheduleID")
    private int scheduleId;

    @ColumnInfo(name = "LectureHallIDFK")
    private int lectureHallIdFK;

    @ColumnInfo(name = "ProfessorIDFK")
    private int professorIdFK;

    @ColumnInfo(name = "StartTime")
    private String startTime;

    @ColumnInfo(name = "EndTime")
    private String endTime;


    // SETTER AND GETTER


    public int getLocalId() {
        return localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getLectureHallIdFK() {
        return lectureHallIdFK;
    }

    public void setLectureHallIdFK(int lectureHallIdFK) {
        this.lectureHallIdFK = lectureHallIdFK;
    }

    public int getProfessorIdFK() {
        return professorIdFK;
    }

    public void setProfessorIdFK(int professorIdFK) {
        this.professorIdFK = professorIdFK;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
