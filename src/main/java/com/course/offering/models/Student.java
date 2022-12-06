package com.course.offering.models;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Student {
    private ArrayList<Course> finishedCourses;
    private ArrayList<Course> degreePlanCourses;
    private ArrayList<Course> eligibleCourses;
    private ArrayList<Section> validSections;
    private int compeletedCreditHours;

    public ArrayList<Course> getDegreePlanCourses() {
        return degreePlanCourses;
    }

    public ArrayList<Course> getEligibleCourses() {
        return eligibleCourses;
    }

    public ArrayList<Course> getFinishedCourses() {
        return finishedCourses;
    }

    public ArrayList<Section> getValidSections() {
        return validSections;
    }

    public void setDegreePlanCourses(ArrayList<Course> degreePlanCourses) {
        this.degreePlanCourses = degreePlanCourses;
    }

    public void setEligibleCourses(ArrayList<Course> eligibleCourses) {
        this.eligibleCourses = eligibleCourses;
    }

    public void setFinishedCourses(ArrayList<Course> finishedCourses) {
        this.finishedCourses = finishedCourses;

    }

    public void setValidSections(ArrayList<Section> validSections) {
        this.validSections = validSections;
    }

}
