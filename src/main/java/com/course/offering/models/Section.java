package com.course.offering.models;

public class Section {
    private String course;
    private int number;

    public Section(String course, int number) {
        this.course = course;
        this.number = number;
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

}
