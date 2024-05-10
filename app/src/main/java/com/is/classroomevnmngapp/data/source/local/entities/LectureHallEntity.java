package com.is.classroomevnmngapp.data.source.local.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "LectureHalls")
public class LectureHallEntity extends BaseEntity {
    @PrimaryKey(autoGenerate = true)
    private int localId;

    @ColumnInfo(name = "LectureHallID")
    private int lectureHallId;

    @ColumnInfo(name = "UniversityIDFK")
    private int universityIdFK;

    @ColumnInfo(name = "Capacity")
    private int capacity;

    @ColumnInfo(name = "HasProjector")
    private int hasProjector;

    @ColumnInfo(name = "LightingStatus")
    private int lightingStatus;

    @ColumnInfo(name = "SmokeDetector")
    private String smokeDetector;

    @ColumnInfo(name = "ACStatus")
    private int acStatus;

    // SETTER AND GETTER


    public int getLocalId() {
        return localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    public int getLectureHallId() {
        return lectureHallId;
    }

    public void setLectureHallId(int lectureHallId) {
        this.lectureHallId = lectureHallId;
    }

    public int getUniversityIdFK() {
        return universityIdFK;
    }

    public void setUniversityIdFK(int universityIdFK) {
        this.universityIdFK = universityIdFK;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getHasProjector() {
        return hasProjector;
    }

    public void setHasProjector(int hasProjector) {
        this.hasProjector = hasProjector;
    }

    public int getLightingStatus() {
        return lightingStatus;
    }

    public void setLightingStatus(int lightingStatus) {
        this.lightingStatus = lightingStatus;
    }

    public String getSmokeDetector() {
        return smokeDetector;
    }

    public void setSmokeDetector(String smokeDetector) {
        this.smokeDetector = smokeDetector;
    }

    public int getAcStatus() {
        return acStatus;
    }

    public void setAcStatus(int acStatus) {
        this.acStatus = acStatus;
    }
}