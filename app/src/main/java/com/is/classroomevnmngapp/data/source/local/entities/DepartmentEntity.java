package com.is.classroomevnmngapp.data.source.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Departments")
public class DepartmentEntity {
    @PrimaryKey(autoGenerate = true)
    private int localId;

    @ColumnInfo(name = "id")
    private int departmentId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "univ_id_fk")
    private int universityIdFK;

    public int getLocalId() {
        return localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUniversityIdFK() {
        return universityIdFK;
    }

    public void setUniversityIdFK(int universityIdFK) {
        this.universityIdFK = universityIdFK;
    }
}
