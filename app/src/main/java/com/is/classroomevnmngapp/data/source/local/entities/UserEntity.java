package com.is.classroomevnmngapp.data.source.local.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(tableName = "Users",indices = {@Index(value = {"userId","Username"},unique = true)})
public class UserEntity  {

    @PrimaryKey(autoGenerate = true)
    private int localId;

    @SerializedName("Id")
    private int userId;

    @SerializedName("user_name")
    @ColumnInfo(name = "Username")
    private String username;

    @SerializedName("Password")
    @ColumnInfo(name = "Password")
    private String password;

    @ColumnInfo(name = "email")
    private String email;

    @SerializedName("phone_number")
    @ColumnInfo(name = "phone")
    private String phone;

    @SerializedName("type_user")
    @ColumnInfo(name = "UserTypeIDFK")
    private int userTypeIdFk;

    @SerializedName("status_upload")
    @ColumnInfo(name = "Status")
    private int status;

    @SerializedName("is_active")
    @ColumnInfo(name = "IsActive")
    private int isActive;

    @SerializedName("create_date")
    @ColumnInfo(name = "CreatedAt")
    private String createdAt;

    //-----------------------------


    public int getLocalId() {
        return localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getUserTypeIdFk() {
        return userTypeIdFk;
    }

    public void setUserTypeIdFk(int userTypeIdFk) {
        this.userTypeIdFk = userTypeIdFk;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public int getIsActive() {
        return isActive;
    }


    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }


    public String getCreatedAt() {
        return createdAt;
    }


    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity)) return false;
        UserEntity that = (UserEntity) o;
        return userId == that.userId &&
                userTypeIdFk == that.userTypeIdFk &&
                status == that.status &&
                isActive == that.isActive &&
                username.equals(that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(email, that.email) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, password, email, phone, userTypeIdFk, status, isActive, createdAt);
    }

    @NonNull
    @Override
    public String toString() {
        return "UserEntity{" +
                "localId=" + localId +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", userTypeIdFk=" + userTypeIdFk +
                ", status=" + status +
                ", isActive=" + isActive +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
