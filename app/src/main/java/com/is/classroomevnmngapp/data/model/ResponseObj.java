package com.is.classroomevnmngapp.data.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.Contract;

public class ResponseObj {
    private int lId;
    @SerializedName("Data")
    private final String servId;
    @SerializedName("Code")
    private final String code;

    public ResponseObj(String data, String code) {
        this.servId = data;
        this.code = code;
    }

    @NonNull
    @Contract(value = "_ -> new", pure = true)
    public static ResponseObj newInstance(String data){
        return new ResponseObj(data,"");
    }




    public String getServeId() {
        return servId;
    }

    public String getCode() {
        return code;
    }

    public int getlId() {
        return lId;
    }

    public void setlId(int lId) {
        this.lId = lId;
    }

    @NonNull
    @Override
    public String toString() {
        return "ResponseObj{" +
                "lId=" + lId +
                ", servId='" + servId + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
