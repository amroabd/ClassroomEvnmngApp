package com.is.classroomevnmngapp.data.source.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class HelperEntity {

    @Entity(tableName ="init_main_menu")
    public static class MainMenu {
        @SerializedName("id")
        @PrimaryKey(autoGenerate = true)
        private long id;
        @SerializedName("titleAr")
        private String title_ar;
        @SerializedName("titleEn")
        private String title_en;
        @SerializedName("titleFr")
        private String title_fr;
        @SerializedName("father")
        private String total_record_load;
        private String note;
        private String is_active;
       @Ignore
        public MainMenu() { }

        public MainMenu(long id, String title_ar) {
            this.id = id;
            this.title_ar = title_ar;
        }

        public long getId() {
            return id;
        }

        public void setTitle_ar(String title_ar) {
            this.title_ar = title_ar;
        }

        public String getTitle_ar() {
            return title_ar;
        }

        public String getTitle_en() {
            return title_en;
        }

        public String getTitle_fr() {
            return title_fr;
        }

        public void setTitle_fr(String title_fr) {
            this.title_fr = title_fr;
        }

        public void setTitle_en(String title_en) {
            this.title_en = title_en;
        }

        public String getTotal_record_load() {
            return total_record_load;
        }

        public void setTotal_record_load(String total_record_load) {
            this.total_record_load = total_record_load;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getIs_active() {
            return is_active;
        }

        public void setIs_active(String is_active) {
            this.is_active = is_active;
        }

        @NotNull
        @Override
        public String toString() {
            return "MainMenu{" +
                    "id='" + id + '\'' +
                    ", title_ar='" + title_ar + '\'' +
                    ", title_en='" + title_en + '\'' +
                    ", total_record_load='" + total_record_load + '\'' +
                    ", note='" + note + '\'' +
                    ", is_active='" + is_active + '\'' +
                    '}';
        }

    }
    //2----------
    @Entity(tableName = "init_sub_menu")
    public static class SubMenu  {
        @PrimaryKey(autoGenerate = true)
        private long _id;
        @SerializedName("id")
        private String id;
        @SerializedName("titleAr")
        private String title_ar;
        @SerializedName("titleEn")
        private String title_en;
        @SerializedName("titleFr")
        private String title_fr;
        @SerializedName("header")
        private String main_id_fk;
        @SerializedName("father")
        private String parent_id_fk;
        @ColumnInfo(defaultValue = "0")
        private String note;
        private String is_active;
        @Ignore
        public SubMenu() { }

        public SubMenu(String id, String title_ar, String main_id_fk) {
            this.id = id;
            this.title_ar = title_ar;
            this.main_id_fk = main_id_fk;
        }

        @Ignore
        public SubMenu(String id, String title_ar, String main_id_fk, String parent_id_fk, String is_active) {
            this.id = id;
            this.title_ar = title_ar;
            this.main_id_fk = main_id_fk;
            this.parent_id_fk = parent_id_fk;
            this.is_active = is_active;
        }


        public String getTitle_fr() {
            return title_fr;
        }

        public void setTitle_fr(String title_fr) {
            this.title_fr = title_fr;
        }

        public long get_id() {
            return _id;
        }
        public void set_id(long _id) {
            this._id = _id;
        }

        public String getParent_id_fk() {
            return parent_id_fk;
        }

        public void setParent_id_fk(String parent_id_fk) {
            this.parent_id_fk = parent_id_fk;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getId() {
            return id;
        }

        public String getTitle_ar() {
            return title_ar;
        }

        public String getMain_id_fk() {
            return main_id_fk;
        }

        public String getTitle_en() {
            return title_en;
        }

        public void setTitle_en(String title_en) {
            this.title_en = title_en;
        }

        public String getIs_active() {
            return is_active;
        }

        public void setIs_active(String is_active) {
            this.is_active = is_active;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setTitle_ar(String title_ar) {
            this.title_ar = title_ar;
        }

        public void setMain_id_fk(String main_id_fk) {
            this.main_id_fk = main_id_fk;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SubMenu)) return false;
            SubMenu subMenu = (SubMenu) o;
            return Objects.equals(_id, subMenu._id) &&
                    id.equals(subMenu.id) &&
                    Objects.equals(title_ar, subMenu.title_ar) &&
                    Objects.equals(title_en, subMenu.title_en) &&
                    main_id_fk.equals(subMenu.main_id_fk) &&
                    Objects.equals(parent_id_fk, subMenu.parent_id_fk);
        }

        @Override
        public int hashCode() {
            return Objects.hash(_id, id, title_ar, title_en, main_id_fk, parent_id_fk);
        }

        @NotNull
        @Override
        public String toString() {
            return String.format("SubMenu{id=%s, title_ar='%s', title_en='%s', main_id_fk='%s', is_active='%s'}", id, title_ar, title_en, main_id_fk, is_active);
        }


    }



}
