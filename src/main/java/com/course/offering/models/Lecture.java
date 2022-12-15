package com.course.offering.models;

import java.io.Serializable;
import java.time.DayOfWeek;

import com.course.offering.controllers.ScheduleController;
import com.course.offering.utils.ScheduleTimeConverter;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class Lecture extends BorderPane implements Serializable {

    private final Section section;
    private final DayOfWeek day;
    private final String startTime24;
    private final String endTime24;
    private final String startTime12;

    private final int subRoxIndex;
    private final int rowIndex;
    private final int colIndex;
    private final int rowSpan;

    public Section getSection() {
        return section;
    }

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

    public String getCourseName() {
        return section.getCourseFullName();
    }

    public DayOfWeek getDay() {
        return day;
    }

    public String getStartTime24() {
        return startTime24;
    }

    public String getStartTime12() {

        return startTime12;
    }

    public int getDurationInMinutes() {
        return ScheduleTimeConverter.timeIntervalToDuration(startTime12, endTime24);
    }

    public int getSectionId() {
        return Integer.parseInt(section.getCRN());
    }

    public String getInstructor() {
        return section.getInstructor();
    }

    public String getLocation() {
        return section.getLocation();
    }

    public Lecture(
            Section section,
            DayOfWeek day,
            String startTime24,
            String endTime24) {
        this.section = section;
        this.day = day;
        this.startTime24 = startTime24;
        this.endTime24 = endTime24;
        this.startTime12 = ScheduleTimeConverter.time24To12(startTime24);
        this.rowIndex = ScheduleTimeConverter.time24ToIndex(startTime24);
        this.subRoxIndex = (ScheduleTimeConverter.time24ToIndex(startTime24) * 4 - 3);
        this.colIndex = ScheduleTimeConverter.dayOfWeekTOIndex(day);
        this.rowSpan = ScheduleTimeConverter.timeIntervalToRowSpan(startTime24, endTime24);

        setLectureNode();
    }

    private void setLectureNode() {
        // Button button = new Button(getCourse());
        Label label = new Label(getCourseName());
        Button removeButton = new Button("Remove");

        removeButton.setOnAction(e -> {
            ScheduleController.getInstance().removeSection(getSection());
        });
        this.setMaxWidth(Double.MAX_VALUE);
        this.setMaxHeight(Double.MAX_VALUE);
        this.setMinHeight(80);
        this.setCenter(label);
        this.setRight(removeButton);
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

    @Override
    public String toString() {
        if (getCourseName() == null) {
            return "(empty)";
        } else {
            return getCourseName();
        }
    }

}