package com.course.offering.models;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Date;

import com.course.offering.controllers.ScheduleController;
import com.course.offering.utils.ScheduleTimeConverter;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Lecture {

    private final String course;

    private final int sectionId;

    private final DayOfWeek day;

    private final String timeOfDay24;

    private final String timeOfDay12;

    private final int durationInMinutes;

    private final String instructor;

    private final String location;

    private Node lectureTableItem;

    private final int tableRowIndex;
    private final int tableColIndex;

    public Node getLectureTableItem() {
        return lectureTableItem;
    }

    public void setLectureTableItem(Node newLecTableItem) {
        this.lectureTableItem = newLecTableItem;
    }

    public int getTableColIndex() {
        return tableColIndex;
    }

    public int getTableRowIndex() {
        return tableRowIndex;
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

    public Lecture(int colIndex, int rowIndex) {
        this.course = "";
        this.sectionId = 0;
        this.day = ScheduleTimeConverter.indexToDayOfWeek(colIndex);
        this.timeOfDay24 = ScheduleTimeConverter.indexToTime24(rowIndex);
        this.timeOfDay12 = ScheduleTimeConverter.indexToTime12(rowIndex);
        this.durationInMinutes = 0;
        this.instructor = "";
        this.location = "";
        this.lectureTableItem = new VBox();
        this.tableRowIndex = rowIndex;
        this.tableColIndex = colIndex;
        // System.out.println(getTimeOfDay12());
        // System.out.println(getTableIndex());
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
        this.lectureTableItem = createLectureCell();
        this.tableRowIndex = ScheduleTimeConverter.time24ToIndex(timeOfDay24);
        this.tableColIndex = ScheduleTimeConverter.dayOfWeekTOIndex(day);
    }

    private Node createLectureCell() {
        Button button = new Button(getCourse());
        BorderPane lecturePane = new BorderPane();
        lecturePane.setMaxWidth(Double.MAX_VALUE);
        lecturePane.setMaxHeight(Double.MAX_VALUE);
        lecturePane.setMinHeight(80);
        lecturePane.setRight(button);
        lecturePane.setStyle("-fx-background-color: #f34839;" +
                "-fx-background-radius: 20;" +
                "" +
                "");
        lecturePane.setBorder(
                new Border(new BorderStroke(Color.valueOf(ScheduleController.getInstance().getOutlineColor()),
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                        new BorderWidths(ScheduleController.getInstance().getBorderWidth()))));
        button.setOnAction(e -> {
            System.out.println(e.getSource().getClass() + " was clicked");
        });

        return lecturePane;
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