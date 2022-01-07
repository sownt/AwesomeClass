package com.sownt.awesomeclass.model;

import java.util.Calendar;

public class Lecture {
    private String name;
    private Calendar start;
    private Calendar end;
    private String location;
    private boolean notification;
    private int color;
    private String description;

    public Lecture(String name, Calendar start, Calendar end, String location, boolean notification, int color, String description) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.location = location;
        this.notification = notification;
        this.color = color;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getStart() {
        return start;
    }

    public void setStart(Calendar start) {
        this.start = start;
    }

    public Calendar getEnd() {
        return end;
    }

    public void setEnd(Calendar end) {
        this.end = end;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return start.getTime().toString();
    }
}
