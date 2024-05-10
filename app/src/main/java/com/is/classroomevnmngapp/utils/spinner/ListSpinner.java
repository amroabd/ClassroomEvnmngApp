package com.is.classroomevnmngapp.utils.spinner;


import androidx.annotation.NonNull;
import androidx.room.Ignore;

public class ListSpinner {
  @Ignore
  private int idSpinner;

    private String idSpinnerS;
    private String nameSpinner;
    private String main_id_fk;


    public void setMain_id_fk(String main_id_fk) {
        this.main_id_fk = main_id_fk;
    }

    @Ignore
    public ListSpinner() {}
    //id  type string
    @Ignore
    public ListSpinner(String idspinner_s, String namespinner) {
        this.idSpinnerS = idspinner_s;
        this.nameSpinner = namespinner;
    }


    //3
    public ListSpinner(String idSpinnerS, String nameSpinner, String main_id_fk) {
        this.idSpinnerS = idSpinnerS;
        this.nameSpinner = nameSpinner;
        this.main_id_fk = main_id_fk;
    }

    public int getIdSpinner() {
        return this.idSpinner;
    }

    public String getNameSpinner() {
        return this.nameSpinner;
    }

    public String getIdSpinnerS() {
        return idSpinnerS;
    }

    public void setIdSpinnerS(String idSpinnerS) {
        this.idSpinnerS = idSpinnerS;
    }

    public void setNameSpinner(String nameSpinner) {
        this.nameSpinner = nameSpinner;
    }

    public String getMain_id_fk() { return main_id_fk; }


    @NonNull
    @Override
    public String toString() {
        return nameSpinner;
    }
}
