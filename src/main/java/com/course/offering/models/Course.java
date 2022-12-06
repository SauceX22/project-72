package com.course.offering.models;

public class Course {
    private String courseName;
    private String creditHours;
    private String prerequesties;
    private String corequesties;
    private String grade;
    private String term;

    // Finished courses constructer
    public Course(String course, String term, String grade) {
        this.courseName = course;
        this.term = term;
        this.grade = grade;
    }

    // Degree Plan constructor
    public Course(String course, String creditHours, String prerequesties, String corequesties) {
        this.courseName = course;
        this.creditHours = creditHours;
        this.prerequesties = prerequesties;
        this.corequesties = corequesties;
    }

    // Eligiablity checker
    public Boolean isEligible(Student student) {

        for (Course courseObj : student.getFinishedCourses()) {
            if (this.getCourseName().equals(courseObj.getCourseName())) {
                // System.out.println("False: Course in Finshedcourses");
                return false;
            }
        }

        if (this.getPrerequesties().equals("None")) {
            // System.out.println("True: this course doesn't have prerequesties");
            return true;
        }

        if (this.getPrerequesties().contains("-")) {
            int n = 0;
            String[] splitedPrerequesties = this.getPrerequesties().split("-");
            for (int i = 0; i < splitedPrerequesties.length; i++) {
                for (Course courseObj : student.getFinishedCourses()) {
                    if (splitedPrerequesties[i].equals(courseObj.getCourseName())) {
                        n++;
                    }
                }
            }
            if (n == splitedPrerequesties.length)
                return true;
        }

        for (Course courseObj : student.getFinishedCourses()) {
            if (this.getPrerequesties().equals(courseObj.getCourseName())) {
                // System.out.println("True: Prerequesties satisfied");
                return true;
            }
        }

        // System.out.println("False: Did not finish the prerequesties");
        return false;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getPrerequesties() {
        return prerequesties;
    }

    public String getCorequesties() {
        return corequesties;
    }

    public String getCreditHours() {
        return creditHours;
    }

    public String getGrade() {
        return grade;
    }

    public String getTerm() {
        return term;
    }

}
