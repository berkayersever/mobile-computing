package com.example.viewmodel;

import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;

public class Reminder {
    private String title, note;
    private Calendar calendar;
    private int priority;
    private boolean status;

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
}