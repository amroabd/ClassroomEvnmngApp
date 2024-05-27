package com.is.classroomevnmngapp.data.source.local.dao;

import androidx.room.Dao;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

@Dao
public interface BaseDao {
    @RawQuery
    int getCount(SupportSQLiteQuery query);
    @RawQuery
    int deleteAllRecords(SupportSQLiteQuery query);

    @RawQuery
    String retrieve(SupportSQLiteQuery query);

    @RawQuery
    int checkPoint(SupportSQLiteQuery supportSQLiteQuery);
}
