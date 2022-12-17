package com.course.offering.models;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.ArrayList;

import com.course.offering.utils.ScheduleTimeConverter;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

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
    private transient ArrayList<Lecture> lectures = new ArrayList<>();

    private SerializableButton basketButton;
    private double basketItemHeight = 120;

    public ArrayList<Lecture> getLectures() {
        if (lectures == null)
            setLectures();
        return lectures;
    }

    private void setLectures() {
        if (lectures == null)
            lectures = new ArrayList<Lecture>();
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

    public boolean isEligable(Student student) {
        if (this.sectionNumber.contains("F"))
            return false;
        for (Course courseObj : student.getEligibleCourses()) {
            if (this.getCourseName().equals(courseObj.getCourseName())) {
                return true;
            }
        }
        return false;
    }

    public HBox getStatus() {
        if (status == null) {
            this.status = new HBox();
            this.status.setStyle("-fx-background-color: #F5F5F5;");
        }
        return status;
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
        return "Course " + courseName + " Section " + sectionNumber + " Activity " +
                activity +
                " CRN " + CRN + " Course Name " + courseFullName + " Instructor " +
                instructor +
                " Day " + days + " Time " + time + " location " + location;
    }

    public SerializableButton createBaskeButton() {
        SerializableButton button = new SerializableButton();
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

        sectionDays.setTextFill(Color.WHITE);
        sectionTime.setTextFill(Color.WHITE);
        sectionLoc.setTextFill(Color.WHITE);
        footer.setMaxWidth(Double.MAX_VALUE);
        footer.setAlignment(Pos.CENTER);
        BorderPane.setMargin(footer, new Insets(0, 0, 0, 0));
        sectionData.setBottom(footer);
        sectionData.getBottom().setStyle("-fx-background-color: #00008b");

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

    public SerializableButton getBasketButton() {
        return basketButton;
    }

    // Check for conflicts
    public boolean isConflict(Section section) {
        if (getCRN().equals(section.getCRN()))
            return true;
        // Same course and same type of activity
        if (getCourseName().equals(section.getCourseName()))
            if (getActivity().equals(section.getActivity()))
                return true;

        // Same days and time
        if (daysConflict(section))
            if (timeConflict(section))
                return true;

        return false;
    }

    private boolean daysConflict(Section section) {
        if (section.getDays().equals(this.getDays()))
            return true;
        for (char day : this.getDays().toCharArray())
            if (section.getDays().contains(day + ""))
                return true;

        return false;
    }

    private boolean timeConflict(Section section) {
        if (getTime().equals(section.getTime()))
            return true;

        int basketStartTime = Integer.parseInt(section.getLectures().get(0).getStartTime24());
        int basketEndTime = Integer.parseInt(section.getLectures().get(0).getEndTime24());
        int tableEndTime = Integer.parseInt(getLectures().get(0).getStartTime24());
        int tableStartTime = Integer.parseInt(getLectures().get(0).getEndTime24());

        if (basketStartTime == tableEndTime)
            return true;

        boolean startsEarlier = basketStartTime < tableEndTime;
        boolean endsSameOrAfter = (basketEndTime >= tableEndTime);
        if (startsEarlier && endsSameOrAfter)
            return true;

        boolean startsAfter = basketStartTime > tableEndTime;
        boolean endsSameOrBefore = (basketEndTime <= tableStartTime);
        if (startsAfter && endsSameOrBefore)
            return true;

        return false;
    }

    public void updateButton() {
        this.basketButton = createBaskeButton();
    }

}
