package com.course.offering.models;

import java.util.ArrayList;

public class Course {
    private String courseName;
    private String creditHours;

    public Course() {

    }

    public Course(String courseName) {
        this.courseName = courseName;
    }

    public Course(String courseName, String creditHours) {
        this.courseName = courseName;
        this.creditHours = creditHours;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(String creditHours) {
        this.creditHours = creditHours;
    }
}
