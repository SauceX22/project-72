package com.course.offering.models;

import java.time.DayOfWeek;

import com.course.offering.controllers.ScheduleController;
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

public class Lecture extends BorderPane {

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

        createLectureNode();
    }

    private void createLectureNode() {
        // Top
        HBox header = new HBox();
        Label sectionCName = new Label(section.getCourseName() + "-" + section.getSectionNumber());
        Label sectionCRN = new Label(section.getCRN());
        Label sectionActivity = new Label(section.getActivity());
        Region headerSplit1 = new Region();
        Region headerSplit2 = new Region();
        HBox.setHgrow(headerSplit1, Priority.ALWAYS);
        HBox.setHgrow(headerSplit2, Priority.ALWAYS);
        header.getChildren().addAll(sectionCName, headerSplit1, sectionCRN, headerSplit2, sectionActivity);
        header.setMinWidth(this.getWidth());
        header.setMaxWidth(Double.MAX_VALUE);
        header.setAlignment(Pos.CENTER);
        header.setStyle("-fx-background-color: #e0e0e0; -fx-background-radius: 3;");
        this.setTop(header);
        BorderPane.setMargin(header, new Insets(2));

        // Bottom
        HBox footer = new HBox();
        Label sectionDays = new Label(section.getDays());
        Label sectionTime = new Label(section.getTime());
        Label sectionLoc = new Label(getLocation());
        Region footerSplit1 = new Region();
        Region footerSplit2 = new Region();
        HBox.setHgrow(footerSplit1, Priority.ALWAYS);
        HBox.setHgrow(footerSplit2, Priority.ALWAYS);
        footer.setStyle("-fx-background-color: #e0e0e0; -fx-background-radius: 3;");
        footer.getChildren().addAll(sectionDays, footerSplit1, sectionTime, footerSplit2, sectionLoc);
        footer.setMinWidth(this.getWidth());

        footer.setMaxWidth(Double.MAX_VALUE);
        footer.setAlignment(Pos.CENTER);
        BorderPane.setMargin(footer, new Insets(2));
        this.setBottom(footer);
        // this.getBottom().setStyle("-fx-background-color: #eb8d13");

        // Center
        VBox center = new VBox();
        Label sectionInstructor = new Label(getInstructor());
        Region centerSplit1 = new Region();
        Region centerSplit2 = new Region();
        VBox.setVgrow(centerSplit1, Priority.ALWAYS);
        VBox.setVgrow(centerSplit2, Priority.ALWAYS);
        sectionInstructor.setStyle("-fx-background-color: #e0e0e0; -fx-background-radius: 3;");

        center.getChildren().addAll(centerSplit1, sectionInstructor, centerSplit2);
        center.setMinWidth(this.getMinWidth());
        center.setMaxWidth(Double.MAX_VALUE);
        center.setAlignment(Pos.CENTER);
        this.setCenter(center);
        BorderPane.setMargin(center, new Insets(2));

        // Right
        VBox right = new VBox();
        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e -> {
            ScheduleController.getInstance().removeSection(getSection());
        });
        Region rightSplit1 = new Region();
        Region rightSplit2 = new Region();
        VBox.setVgrow(rightSplit1, Priority.ALWAYS);
        VBox.setVgrow(rightSplit2, Priority.ALWAYS);
        BorderPane.setMargin(right, new Insets(2));
        right.setMaxWidth(Double.MAX_VALUE);
        right.setMaxHeight(Double.MAX_VALUE);
        right.getChildren().addAll(rightSplit1, removeButton, rightSplit2);
        removeButton.setStyle("-fx-background-radius: 8;");

        // this.setPadding(new Insets(2));
        this.setRight(right);
        this.setStyle("-fx-background-color: #f34839; -fx-background-radius: 5;");

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