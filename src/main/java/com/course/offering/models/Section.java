package com.course.offering.models;

import java.util.ArrayList;

public class Section {
    private String courseName;
    private String sectionNumber;
    private String activity;
    private String CRN;
    private String courseFullName;
    private String instructor;
    private String days;
    private String time;
    private String location;

    // These are for displaying in the table
    private ArrayList<Lecture> lectures;

    public ArrayList<Lecture> getLectures() {
        return lectures;
    }

    private void setLectures() {
        for (int i = 0; i < days.length(); i++) {

        }
    }

    public Section(String courseSec, String activtiy, String CRN,
            String courseFullName, String instructor, String days, String time, String location) {

        String[] courseSecArray = courseSec.split("-");
        this.courseName = courseSecArray[0];
        this.sectionNumber = courseSecArray[1];
        this.activity = activtiy;
        this.CRN = CRN;
        this.courseFullName = courseFullName;
        this.instructor = instructor;
        this.days = days;
        this.time = time;
        this.location = location;
        setLectures();
    }

    public boolean isEligible(Student student) {
        for (Course courseObj : student.getEligibleCourses()) {
            if (this.getCourseName().equals(courseObj.getCourseName())) {
                return true;
            }
        }
        return false;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getActivity() {
        return activity;
    }

    public String getCRN() {
        return CRN;
    }

    public String getCourseFullName() {
        return courseFullName;
    }

    public String getDays() {
        return days;
    }

    public String getInstructor() {
        return instructor;
    }

    public String getLocation() {
        return location;
    }

    public String getSectionNumber() {
        return sectionNumber;
    }

    public String getTime() {
        return time;
    }

    // TODO A save schedule function to save the schedule in the second page
    public void saveSchedule(Section[] sections) {

    }

    @Override
    public String toString() {
        return "Section " + sectionNumber + " Course " + courseFullName + "\n";
        // return "Course " + courseName + " Section " + sectionNumber + " Activity " +
        // activity +
        // " CRN " + CRN + " Course Name " + courseFullName + " Instructor " +
        // instructor +
        // " Day " + days + " Time " + time + " location " + location;
    }
}
