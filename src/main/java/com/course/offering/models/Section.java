package com.course.offering.models;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.ArrayList;

import com.course.offering.utils.ScheduleTimeConverter;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class Section implements Serializable {

    private String courseName;
    private String sectionNumber;
    private String activity;
    private String CRN;
    private String courseFullName;
    private String instructor;
    private String days;
    private String time;
    private String location;
    private transient HBox status;

    // These are for displaying in the table
    private ArrayList<Lecture> lectures = new ArrayList<>();

    private transient Button basketButton;
    private double basketItemHeight = 120;

    public ArrayList<Lecture> getLectures() {
        return lectures;
    }

    private void setLectures() {
        if (getTime().equals("None") || getDays().equals("None"))
            return;

        String[] timeStartEnd = getTime().split("-");

        for (int i = 0; i < days.length(); i++) {
            DayOfWeek day = ScheduleTimeConverter.letterToDayOfWeek(days.charAt(i));
            lectures.add(new Lecture(this, day, timeStartEnd[0], timeStartEnd[1]));
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
        this.status = new HBox();
        this.status.setStyle("-fx-background-color: #F5F5F5;");
        this.basketButton = createBaskeButton();
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

    public HBox getStatus() {
        return status;
    }

    public void setStatus(HBox status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "Section " + sectionNumber + " Course " + courseFullName + "\n";
        // return "Course " + courseName + " Section " + sectionNumber + " Activity " +
        // activity +
        // " CRN " + CRN + " Course Name " + courseFullName + " Instructor " +
        // instructor +
        // " Day " + days + " Time " + time + " location " + location;
    }

    public Button createBaskeButton() {
        Button button = new Button();
        BorderPane sectionData = new BorderPane();

        // Top
        HBox header = new HBox();
        Label sectionCName = new Label(getCourseName() + "-" + getSectionNumber());
        Label sectionCRN = new Label(getCRN());
        Label sectionActivity = new Label(getActivity());
        Region headerSplit1 = new Region();
        Region headerSplit2 = new Region();
        HBox.setHgrow(headerSplit1, Priority.ALWAYS);
        HBox.setHgrow(headerSplit2, Priority.ALWAYS);
        header.getChildren().addAll(sectionCName, headerSplit1, sectionCRN, headerSplit2, sectionActivity);
        header.setMinWidth(sectionData.getWidth());
        header.setMaxWidth(Double.MAX_VALUE);
        header.setAlignment(Pos.CENTER);
        sectionData.setTop(header);

        // Bottom
        HBox footer = new HBox();
        Label sectionDays = new Label(getDays());
        Label sectionTime = new Label(getTime());
        Label sectionLoc = new Label(getLocation());
        Region footerSplit1 = new Region();
        Region footerSplit2 = new Region();
        HBox.setHgrow(footerSplit1, Priority.ALWAYS);
        HBox.setHgrow(footerSplit2, Priority.ALWAYS);
        footer.getChildren().addAll(sectionDays, footerSplit1, sectionTime, footerSplit2, sectionLoc);
        footer.setMinWidth(sectionData.getWidth());

        footer.setMaxWidth(Double.MAX_VALUE);
        footer.setAlignment(Pos.CENTER);
        BorderPane.setMargin(footer, new Insets(0, 0, 0, 0));
        sectionData.setBottom(footer);
        sectionData.getBottom().setStyle("-fx-background-color: #eb8d13");

        // Center
        VBox center = new VBox();
        Label sectionCFName = new Label(getCourseFullName());
        Label sectionInstructor = new Label(getInstructor());
        Region centerSplit1 = new Region();
        Region centerSplit2 = new Region();
        Region centerSplit3 = new Region();
        VBox.setVgrow(centerSplit1, Priority.ALWAYS);
        VBox.setVgrow(centerSplit2, Priority.ALWAYS);
        VBox.setVgrow(centerSplit3, Priority.ALWAYS);

        center.getChildren().addAll(centerSplit1, sectionCFName, centerSplit2, sectionInstructor, centerSplit3);
        center.setMinWidth(sectionData.getMinWidth());
        center.setMaxWidth(Double.MAX_VALUE);
        center.setAlignment(Pos.CENTER);
        sectionData.setCenter(center);

        button.setMaxWidth(Double.MAX_VALUE);
        button.setMaxHeight(Double.MAX_VALUE);
        button.setPrefHeight(basketItemHeight);

        button.setGraphic(sectionData);
        BorderPane.setMargin(button, new Insets(5, 5, 5, 5));
        BorderPane.setAlignment(button, Pos.CENTER);
        return button;
    }

    public Button getBasketButton() {
        return basketButton;
    }

    public boolean isConflict(Section section) {
        // A section is a conflict if
        // same course with same type of section (Lec/Rec/Lab)
        // or other course with same time and ANY of the days matching the
        if (getCourseName().equals(section.getCourseName()))
            if (getActivity().equals(section.getActivity()))
                return true;

        if (getTime().equals(section.getTime()))
            for (char day : getDays().toCharArray())
                for (char otherDay : section.getDays().toCharArray())
                    if (day == otherDay)
                        return true;

        int basketRowIndex = section.getLectures().get(0).getRowIndex();
        int basketRowSpan = section.getLectures().get(0).getRowSpan();
        int tableItemRowIndex = getLectures().get(0).getRowIndex();
        int tableItemRowSpan = getLectures().get(0).getRowSpan();

        for (char day : getDays().toCharArray()) {
            if (section.getDays().contains(day + "")) {
                if (basketRowIndex == tableItemRowIndex)
                    return true;

                if (basketRowIndex < tableItemRowIndex && ((basketRowIndex + basketRowSpan) >= tableItemRowIndex))
                    return true;

                if (basketRowIndex > tableItemRowIndex
                        && ((basketRowIndex + basketRowSpan) <= (tableItemRowIndex + tableItemRowSpan)))
                    return true;
            }
        }
        return false;
    }

}
