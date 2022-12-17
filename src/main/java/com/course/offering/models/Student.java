package com.course.offering.models;

import java.util.ArrayList;

public class Student {
    private ArrayList<FinishedCourse> finishedCourses;
    private ArrayList<DegreePlanCourse> degreePlanCourses;
    private ArrayList<DegreePlanCourse> eligibleCourses;
    private ArrayList<Section> validSections;
    private int finishedHours;
    private String currentStanding;

    public ArrayList<DegreePlanCourse> getDegreePlanCourses() {
        return degreePlanCourses;
    }

    public ArrayList<DegreePlanCourse> getEligibleCourses() {
        return eligibleCourses;
    }

    public ArrayList<FinishedCourse> getFinishedCourses() {
        return finishedCourses;
    }

    public ArrayList<Section> getValidSections() {
        return validSections;
    }

    public void setDegreePlanCourses(ArrayList<DegreePlanCourse> degreePlanCourses) {
        this.degreePlanCourses = degreePlanCourses;
    }

    public void setEligibleCourses(ArrayList<DegreePlanCourse> eligibleCourses) {
        this.eligibleCourses = eligibleCourses;
    }

    public void setFinishedCourses(ArrayList<FinishedCourse> finishedCourses) {
        this.finishedCourses = finishedCourses;

    }

    public void setValidSections(ArrayList<Section> validSections) {
        this.validSections = validSections;
    }

    public int getFinishedHours() {
        return finishedHours;
    }

    public void addFinishedHours(int finishedHours) {
        this.finishedHours += finishedHours;
    }

    public void setFinishedHours() {
        for (Course course : degreePlanCourses) {
            for (Course courseObj : this.getFinishedCourses()) {
                if (course.getCourseName().equals(courseObj.getCourseName())) {
                    courseObj.setCreditHours(course.getCreditHours());
                    addFinishedHours(Integer.parseInt(course.getCreditHours()));
                }
            }
        }
        if (this.finishedHours >= 97) {
            setCurrentStanding("Senior Standing");
        } else if (this.finishedHours > 62) {
            setCurrentStanding("Junior Standing");
        }
    }

    public String getCurrentStanding() {
        return currentStanding;
    }

    public void setCurrentStanding(String currentStanding) {
        this.currentStanding = currentStanding;
    }
}
