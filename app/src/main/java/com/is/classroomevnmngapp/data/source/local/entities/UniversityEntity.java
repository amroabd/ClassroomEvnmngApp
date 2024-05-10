package com.is.classroomevnmngapp.data.source.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "University")
public class UniversityEntity {
    @PrimaryKey(autoGenerate = true)
    private final int universityId;
    @ColumnInfo(name = "Name")
    private final String name;
    @ColumnInfo(name = "Location")
    private final String location;

    public UniversityEntity(int universityId, String name, String location) {
        this.universityId = universityId;
        this.name = name;
        this.location = location;
    }

    public int getUniversityId() {
        return universityId;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }
}
