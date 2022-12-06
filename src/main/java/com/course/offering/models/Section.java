package com.course.offering.models;

public class Section {
    private String courseName;
    private String sectionNumber;
    private String activity;
    private String CRN;
    private String courseFullName;
    private String instructor;
    private String day;
    private String time;
    private String location;

    public Section(String courseSec, String activtiy, String CRN,
            String courseFullName, String instructor, String day, String time, String location) {

        String[] courseSecArray = courseSec.split("-");
        this.courseName = courseSecArray[0];
        this.sectionNumber = courseSecArray[1];
        this.activity = activtiy;
        this.CRN = CRN;
        this.courseFullName = courseFullName;
        this.instructor = instructor;
        this.day = day;
        this.time = time;
        this.location = location;
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

    public String getDay() {
        return day;
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

    @Override
    public String toString() {
        return "Course " + courseName + " Section " + sectionNumber + " Activity " + activity +
                " CRN " + CRN + " Course Name " + courseFullName + " Instructor " + instructor +
                " Day " + day + " Time " + time + " location " + location;
    }
}
