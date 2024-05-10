package com.is.classroomevnmngapp.data.source.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.RoomWarnings;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.is.classroomevnmngapp.data.source.local.entities.HelperEntity;
import com.is.classroomevnmngapp.utils.spinner.ListSpinner;

import java.util.List;

public interface HelperDao {

    @Dao
    interface MainMenu extends BaseDao{
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insertMainMenu(HelperEntity.MainMenu mainMenu);

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insertAll(List<HelperEntity.MainMenu> mainMenus);

        @Query("SELECT * FROM init_main_menu")
        List<HelperEntity.MainMenu> getAll();

        @Query("SELECT * FROM init_main_menu")
        int GetCount();

        @Override
        @RawQuery
        int getCount(SupportSQLiteQuery query);

        @Query("DELETE FROM init_main_menu")
        int deleteAllRecords();
    }
//-------------------------
    @Dao
    interface SubMenu {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSubMenu(HelperEntity.SubMenu subMenu);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<HelperEntity.SubMenu> subMenus);

    @Query("SELECT * FROM init_sub_menu")
    List<HelperEntity.SubMenu> getAll();

    @Query("SELECT * FROM init_sub_menu WHERE main_id_fk = (:mainId)")
    List<HelperEntity.SubMenu> loadAllById(String mainId);

    @Query("SELECT * FROM init_sub_menu WHERE id LIKE :Id AND main_id_fk LIKE :mainId LIMIT 100 ")
    HelperEntity.SubMenu findById(String Id, String mainId);

    @Query("SELECT COUNT(id) FROM init_sub_menu")
    int GetCount();

    @Query("DELETE FROM init_sub_menu")
    int deleteAllRecords();

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT id AS idSpinnerS,title_ar AS nameSpinner" +
            " FROM init_sub_menu WHERE main_id_fk = (:mainId)")
    List<ListSpinner> findById(String mainId);
}


}
