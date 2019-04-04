package com.example.recyclerview;

import java.util.Date;

public class Event {
    private String title, note;
    private Date date;
    private int priority;
    private Boolean status;

    public Event(String title, String note, Date date, int priority, Boolean status) {
        this.title = title;
        this.note = note;
        this.date = date;
        this.priority = priority;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public String getNote() {
        return note;
    }

    public Date getDate() {
        return date;
    }

    public int getPriority() {
        return priority;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
