package com.example.persistent_storage;

import androidx.room.TypeConverter;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarConverter {

    @TypeConverter
    public static Long fromCalendar(Calendar calendar)
    {
        return calendar.getTimeInMillis();
    }

    @TypeConverter
    public static Calendar toCalendar(Long millis) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(millis);
        return calendar;
    }
}
