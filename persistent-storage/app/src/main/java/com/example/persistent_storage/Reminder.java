package com.example.persistent_storage;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;

@Entity(tableName = "reminders")
public class Reminder {
    @PrimaryKey(autoGenerate = true)
    private int rid;
    private String title, note;
    private boolean status;

    @ColumnInfo(name = "CALENDAR")
    private Calendar calendar;

    @ColumnInfo(name = "PRIORITY")
    private int priority;

    /* Database */
    public Reminder(String title, String note, Calendar calendar, int priority, boolean status) {
        this.title = title;
        this.note = note;
        this.calendar = calendar;
        this.priority = priority;
        this.status = status;
    }
    public Reminder(String title, String note, String date, int priority, int status) {
        this.title = title;
        this.note = note;
        this.priority = priority;
        this.status = (status == 0) ? false : true;

        String [] dateSplit = date.split("/");
        int day = Integer.parseInt(dateSplit[0]);
        int month = Integer.parseInt(dateSplit[1]);
        int year = Integer.parseInt(dateSplit[2]);
        calendar = new GregorianCalendar(year, month-1,day);
    }

    /* Getters */
    public boolean getStatus() {
        return status;
    }
    public Calendar getCalendar() {
        return calendar;
    }
    public int getPriority() {
        return priority;
    }
    public int getRid() {
        return rid;
    }
    public Long getTime() {
        return calendar.getTimeInMillis();
    }
    public String getNote() {
        return note;
    }
    public String getTitle() {
        return title;
    }

    /* Setters */
    public void setStatus(boolean status) {
        this.status = status;
    }
    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public void setRid(int rid) {
        this.rid = rid;
    }
    public void setTime(Long time) {
        calendar.setTimeInMillis(time);
    }
    public void setNote(String note) {
        this.note = note;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /* Sort Methods */
    public static class SortByDate implements Comparator<Reminder> {
        @Override
        public int compare(Reminder o1, Reminder o2) {
            return o1.calendar.compareTo(o2.calendar);
        }
    }
    public static class SortByPriority implements Comparator<Reminder> {
        @Override
        public int compare(Reminder o1, Reminder o2) {
            Integer p1 = o1.getPriority();
            Integer p2 = o2.getPriority();
            return p2.compareTo(p1);
        }
    }

    /* Other Methods */
    public void copyFromReminder(Reminder reminder) {
        this.title = reminder.getTitle();
        this.note = reminder.getNote();
        this.calendar = reminder.getCalendar();
        this.priority = reminder.getPriority();
        this.status = reminder.getStatus();
    }
}
