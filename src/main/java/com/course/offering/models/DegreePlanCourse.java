package com.course.offering.models;

public class DegreePlanCourse extends Course {
    private String prerequesties;
    private String corequesties;

    // Degree Plan constructor
    public DegreePlanCourse(String course, String creditHours, String prerequesties, String corequesties) {
        super(course, creditHours);
        this.prerequesties = prerequesties;
        this.corequesties = corequesties;
    }

    public String getPrerequesties() {
        return prerequesties;
    }

    public String getCorequesties() {
        return corequesties;
    }

    // Eligiablity checker
    public Boolean isEligable(Student student) {

        for (Course courseObj : student.getFinishedCourses()) {
            if (this.getCourseName().equals(courseObj.getCourseName())) {
                courseObj.setCreditHours(this.getCreditHours());
                return false;
            }
        }

        if (this.getPrerequesties().equals("Senior Standing") &&
                student.getCurrentStanding() != null &&
                student.getCurrentStanding().equals("Senior Standing")) {
            return true;
        }

        if (this.getPrerequesties().equals("Junior Standing") &&
                student.getCurrentStanding() != null &&
                student.getCurrentStanding().equals("Junior Standing")) {
            return true;
        }

        if (this.getPrerequesties().equals("None")) {
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
                return true;
            }
        }
        return false;
    }
}
