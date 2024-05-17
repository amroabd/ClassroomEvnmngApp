package com.is.rhismonitor.data.source_data.local_data;


import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static Date toDate(Long timestamp){
        if (timestamp!=null){
            return new Date(timestamp);
        }
        return null;
    }
    @TypeConverter
    public static Long toTimestamp(Date date){
        if (date!=null){
            return date.getTime();
        }
        return null;
    }
}
