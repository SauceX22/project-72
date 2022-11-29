package com.course.offering;

import java.util.Date;

public class Section {
    private String course;
    private int number;
    private Date time;
    private String location;

    public Section(String course, int number, Date time, String location) {
        this.course = course;
        this.number = number;
        this.time = time;
        this.location = location;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
