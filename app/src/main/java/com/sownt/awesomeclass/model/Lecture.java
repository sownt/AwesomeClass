package com.sownt.awesomeclass.model;

import java.util.Date;

public class Lecture {
    private String name;
    private Date start;
    private Date end;
    private String location;
    private boolean notification;
    private int color;
    private String description;

    public Lecture(String name, Date start, Date end, String location, boolean notification, int color, String description) {
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

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
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
}
