package com.course.offering.models;

import java.time.DayOfWeek;

import com.course.offering.controllers.ScheduleController;
import com.course.offering.utils.ScheduleTimeConverter;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Lecture extends BorderPane {

    private final String course;

    private final int sectionId;

    private final DayOfWeek day;

    private final String timeOfDay24;

    private final String timeOfDay12;

    private final int durationInMinutes;

    private final String instructor;

    private final String location;

    private final int subRoxIndex;
    private final int rowIndex;
    private final int colIndex;
    private final int rowSpan = 4;

    public int getRowIndex() {
        return rowIndex;
    }

    public int getRowSpan() {
        return rowSpan;
    }

    public int getColIndex() {
        return colIndex;
    }

    public int getSubRowIndex() {
        return subRoxIndex;
    }

    public String getCourse() {
        return course;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public String getTimeOfDay24() {
        return timeOfDay24;
    }

    private String setTimeOfDay12() {
        return ScheduleTimeConverter.time24To12(getTimeOfDay24());
    }

    public String getTimeOfDay12() {

        return timeOfDay12;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public int getSectionId() {
        return sectionId;
    }

    public String getInstructor() {
        return instructor;
    }

    public String getLocation() {
        return location;
    }

    public Lecture(
            String course,
            int sectionId,
            DayOfWeek day,
            String timeOfDay24,
            int durationInMinutes,
            String instructor,
            String location) {
        this.course = course;
        this.sectionId = sectionId;
        this.day = day;
        this.timeOfDay24 = timeOfDay24;
        this.timeOfDay12 = setTimeOfDay12();
        this.durationInMinutes = durationInMinutes;
        this.instructor = instructor;
        this.location = location;
        this.rowIndex = ScheduleTimeConverter.time24ToIndex(timeOfDay24);
        this.subRoxIndex = (ScheduleTimeConverter.time24ToIndex(timeOfDay24) * 4 - 3);
        this.colIndex = ScheduleTimeConverter.dayOfWeekTOIndex(day);

        setLectureNode();
    }

    private void setLectureNode() {
        // Button button = new Button(getCourse());
        Label label = new Label(getCourse());
        this.setMaxWidth(Double.MAX_VALUE);
        this.setMaxHeight(Double.MAX_VALUE);
        this.setMinHeight(80);
        this.setCenter(label);
        this.setStyle("-fx-background-color: #f34839;" +
                "-fx-background-radius: 15;" +
                "");
        this.setBorder(
                new Border(new BorderStroke(Color.valueOf(ScheduleController.getInstance().getOutlineColor()),
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                        new BorderWidths(ScheduleController.getInstance().getBorderWidth()))));
        // label.setOnAction(e -> {
        // System.out.println(e.getSource().getClass() + " was clicked");
        // });
    }

    // TODO: Fix this conversion
    @Override
    public String toString() {
        if (course == null) {
            return "(empty)";
        } else {
            return course;
        }
    }

}