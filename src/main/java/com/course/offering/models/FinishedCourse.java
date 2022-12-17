package com.course.offering.models;

public class FinishedCourse extends Course {
    private String grade;
    private String term;

    // Finished courses constructer
    public FinishedCourse(String course, String term, String grade) {
        super(course);
        this.term = term;
        this.grade = grade;
    }

    public String getGrade() {
        return grade;
    }

    public String getTerm() {
        return term;
    }
}
