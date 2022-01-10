package com.sownt.awesomeclass.model;

import android.graphics.Color;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Objects;

public class Lecture implements Comparable<Lecture> {
    private String name;
    private Calendar start;
    private Calendar end;
    private String link;
    private String location;
    private int notification = 0;
    private int color = Color.parseColor("#F6BF26");
    private String description;

    public Lecture() {
    }

    public Lecture(String name, Calendar start, Calendar end, String link, String location, int notification, int color, String description) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.link = link;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getNotification() {
        return notification;
    }

    public void setNotification(int notification) {
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

    @Override
    public int compareTo(Lecture o) {
        return o.getStart().compareTo(start);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lecture lecture = (Lecture) o;
        return notification == lecture.notification && color == lecture.color && name.equals(lecture.name) && start.equals(lecture.start) && end.equals(lecture.end) && link.equals(lecture.link) && location.equals(lecture.location) && description.equals(lecture.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, start, end, link, location, notification, color, description);
    }

    public static String toJson(Lecture lecture) {
        Gson gson = new Gson();
        return gson.toJson(lecture);
    }

    public static Lecture fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Lecture.class);
    }
}
