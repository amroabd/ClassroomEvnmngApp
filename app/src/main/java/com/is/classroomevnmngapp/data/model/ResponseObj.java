package com.is.classroomevnmngapp.data.model;

import com.google.gson.annotations.SerializedName;

public class ResponseObj {
    private int lId;
    @SerializedName("Data")
    private final String servId;
    @SerializedName("Code")
    private final String code;


    public String getServId() {
        return servId;
    }

    public String getCode() {
        return code;
    }

    public int getlId() {
        return lId;
    }

    public ResponseObj(String data, String code) {
        this.servId = data;
        this.code = code;
    }

    public void setlId(int lId) {
        this.lId = lId;
    }

    @Override
    public String toString() {
        return "ResponseObj{" +
                "lId=" + lId +
                ", servId='" + servId + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
