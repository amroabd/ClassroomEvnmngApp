package com.is.classroomevnmngapp.data.source.local.entities;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(tableName = "LectureHalls",indices = {@Index(value = {"id"},unique = true)})

public class LectureHallEntity extends BaseEntity {
    @PrimaryKey(autoGenerate = true)
    private int localId;

    @SerializedName("Id")
    @ColumnInfo(name = "id")
    private int lectureHallId;

    @SerializedName("univ_id_fk")
    @ColumnInfo(name = "univ_id_fk")
    private int universityIdFK;

    @SerializedName("name")
    @ColumnInfo(name = "name")
    private String title;

    @SerializedName("captacity")
    @ColumnInfo(name = "capacity")
    private int capacity;

    @SerializedName("has_projector")
    @ColumnInfo(name = "has_projector")
    private int hasProjector;

    @SerializedName("lighting_status")
    @ColumnInfo(name = "lighting_status")
    private int lightingStatus;

    @SerializedName("ac_status")
    @ColumnInfo(name = "ac_status")
    private int acStatus;

    @ColumnInfo(name = "SmokeDetector")
    private String smokeDetector;

    // SETTER AND GETTER


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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


    //==============================================================================
    public static final DiffUtil.ItemCallback<LectureHallEntity>DIFF_CALLBACK =new DiffUtil.ItemCallback<LectureHallEntity>() {

        @Override
        public boolean areItemsTheSame(@NonNull LectureHallEntity oldItem, @NonNull LectureHallEntity newItem) {
            return oldItem.localId == newItem.localId;
        }

        @Override
        public boolean areContentsTheSame(@NonNull LectureHallEntity oldItem, @NonNull LectureHallEntity newItem) {
            return oldItem.equals(newItem);
        }
    };
    //=================================================================================


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LectureHallEntity)) return false;
        LectureHallEntity entity = (LectureHallEntity) o;
        return  //localId == entity.localId &&
                lectureHallId == entity.lectureHallId &&
                capacity == entity.capacity &&
                title.equals(entity.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(/*localId,*/ lectureHallId, title, capacity);
    }

    @NonNull
    @Override
    public String toString() {
        return "LectureHallEntity{" +
                "localId=" + localId +
                ", lectureHallId=" + lectureHallId +
                ", universityIdFK=" + universityIdFK +
                ", title='" + title + '\'' +
                ", capacity=" + capacity +
                ", hasProjector=" + hasProjector +
                ", lightingStatus=" + lightingStatus +
                ", acStatus=" + acStatus +
                ", smokeDetector='" + smokeDetector + '\'' +
                ", statusUpload=" + statusUpload +
                ", userIdFk=" + userIdFk +
                ", createdDateTime='" + createdDateTime + '\'' +
                ", lastModifiedDateTime='" + lastModifiedDateTime + '\'' +
                "} " + super.toString();
    }
}