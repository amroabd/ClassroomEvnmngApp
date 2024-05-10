package com.is.classroomevnmngapp.data.source.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Professors")
public class ProfessorEntity extends BaseEntity{
    @PrimaryKey(autoGenerate = true)
    private int localId;

    @ColumnInfo(name = "ProfessorID")
    private int professorId;

    @ColumnInfo(name = "Name")
    private String name;

    @ColumnInfo(name = "ContactInfo")
    private String contactInfo;

    @ColumnInfo(name = "DepartmentIDFK")
    private int departmentIdFK;

    //---------------

    public ProfessorEntity(int localId, int professorId, String name, String contactInfo, int departmentIdFK) {
        this.localId = localId;
        this.professorId = professorId;
        this.name = name;
        this.contactInfo = contactInfo;
        this.departmentIdFK = departmentIdFK;
    }

    //---------------------

    public int getLocalId() {
        return localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    public int getProfessorId() {
        return professorId;
    }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public int getDepartmentIdFK() {
        return departmentIdFK;
    }

    public void setDepartmentIdFK(int departmentIdFK) {
        this.departmentIdFK = departmentIdFK;
    }
}
