package com.example.travelwishlist.db;


import androidx.room.TypeConverter;

import java.util.Date;

public class Converters { //this converts the date to a long type for room entry
    @TypeConverter
    public static Date dateFromTimestamp(long time) {
        return new Date(time);
    }

    @TypeConverter
    public static long dateToTimestamp(Date date) {
        return date == null ? 0 : date.getTime();
    }
}
