package com.example.travelwishlist.db;

import java.sql.Date;

public class Converters {
    public static Date dateFromTimestamp(long time){
        return new Date(time);
    }

    public static long dateToTimestamp(Date date){
        return date == null ? 0 : date.getTime();
    }
}