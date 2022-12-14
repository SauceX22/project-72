package com.course.offering.controllers;

import java.util.ArrayList;

import com.course.offering.models.Lecture;
import com.course.offering.models.Section;
import com.course.offering.utils.ScheduleTimeConverter;

import javafx.beans.InvalidationListener;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ScheduleController {

    private static ScheduleController instance = null;

    public static ScheduleController getInstance() {
        if (instance == null)
            instance = new ScheduleController();
        return instance;
    }

    private GridPane grid;

    public GridPane getGrid() {
        if (grid == null)
            System.err.println("A grid has not been initialized");
        return grid;
    }

    public void setGrid(GridPane grid) {
        this.grid = grid;
    }

    private int numberOfHours = 14;
    private int firstRowHeight = 40;
    private int hourRowsHeight = 100;
    private int dayColWidth = 150;

    private String outlineColor = "#A2A2A2";
    private String evenCellBG = "#fff";
    private String oddCellBG = "#E7E7E7";
    private double borderWidth = 0.2;

    private Node[] topHeaders = new Node[6];
    private Node[] sideHeaders = new Node[numberOfHours * 4];

    private static ArrayList<Section> scheduleSections = new ArrayList<Section>();

    public static ArrayList<Section> getScheduleSections() {
        return scheduleSections;
    }

    public GridPane initialize() {
        grid = new GridPane();

        grid.setMaxWidth(Double.MAX_VALUE);
        grid.setMaxHeight(Double.MAX_VALUE);

        fillAsEmptyGrid();
        setSideHeaders();
        setTopHeaders();

        setupColConstraints();

        return grid;
    }

    public void setStickyTopHeaders(ScrollPane scrollPane) {
        scrollPane.setPrefHeight(400);

        // keep header in position on top of the viewport
        InvalidationListener headerUpdater = o -> {
            final double ty = (grid.getHeight() - scrollPane.getViewportBounds().getHeight()) * scrollPane.getVvalue();
            for (Node header : topHeaders) {
                header.setTranslateY(ty);
            }
        };
        grid.heightProperty().addListener(headerUpdater);
        scrollPane.viewportBoundsProperty().addListener(headerUpdater);
        scrollPane.vvalueProperty().addListener(headerUpdater);
    }

    public void setStickySideHeaders(ScrollPane scrollPane) {
        scrollPane.setPrefWidth(400);

        // keep header in position on side of the viewport
        InvalidationListener headerUpdater = o -> {
            final double tx = (grid.getWidth() - scrollPane.getViewportBounds().getWidth()) * scrollPane.getHvalue();
            for (Node header : sideHeaders) {
                header.setTranslateX(tx);
            }
        };
        grid.widthProperty().addListener(headerUpdater);
        scrollPane.viewportBoundsProperty().addListener(headerUpdater);
        scrollPane.hvalueProperty().addListener(headerUpdater);
    }

    private void setTopHeaders() {
        topHeaders = new Node[] {
                getSimpleTextCell("Time", Pos.CENTER),
                getSimpleTextCell("Sunday", Pos.CENTER),
                getSimpleTextCell("Monday", Pos.CENTER),
                getSimpleTextCell("Tuesday", Pos.CENTER),
                getSimpleTextCell("Wednesday", Pos.CENTER),
                getSimpleTextCell("Thursday", Pos.CENTER)
        };

        grid.addRow(0, topHeaders);
    }

    private void setSideHeaders() {
        RowConstraints headerRowConstraint = new RowConstraints();
        headerRowConstraint.setPercentHeight(firstRowHeight / numberOfHours);
        grid.getRowConstraints().add(headerRowConstraint);

        RowConstraints timeRowConstraint = new RowConstraints();
        timeRowConstraint.setMinHeight(hourRowsHeight / 4);
        timeRowConstraint.setPercentHeight(200 / (numberOfHours * 4));
        int subRowIndex = 0;
        for (int i = 0; i < numberOfHours; i++) {
            for (int j = 0; j < 4; j++) {
                Node timeHeader = getSimpleTextCell(j == 0 ? ScheduleTimeConverter.indexToTime12(i) : "",
                        Pos.TOP_CENTER,
                        ((i + 1) % 2 == 0 ? evenCellBG : oddCellBG));
                grid.add(timeHeader, 0, subRowIndex + 1);
                sideHeaders[subRowIndex] = timeHeader;
                grid.getRowConstraints().add(timeRowConstraint);
                subRowIndex++;
            }
        }
    }

    private VBox getSimpleTextCell(String text, Pos position) {
        return getSimpleTextCell(text, position, "#fff");
    }

    private VBox getSimpleTextCell(String text, Pos position, String color) {
        Label dayLabel = new Label(text);
        VBox dayBox = new VBox(dayLabel);

        dayBox.setStyle("-fx-background-color: " + color + ";");
        dayBox.setAlignment(position);

        dayBox.setBorder(new Border(new BorderStroke(Color.valueOf(outlineColor),
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                new BorderWidths(ScheduleController.getInstance().getBorderWidth()))));
        return dayBox;
    }

    private void fillAsEmptyGrid() {

        int subRowIndex = 0;
        for (int row = 0; row < numberOfHours; row++) {
            for (int subRow = 0; subRow < 4; subRow++) {
                for (int col = 0; col < 6; col++) {
                    createEmptyCell(col, row + 1, subRowIndex + 1);
                }
                subRowIndex++;
            }
        }
    }

    private void setupColConstraints() {
        ColumnConstraints timeColumnConstraint = new ColumnConstraints();
        ColumnConstraints sundayColumnConstraint = new ColumnConstraints();
        ColumnConstraints mondayColumnConstraint = new ColumnConstraints();
        ColumnConstraints tuesdayColumnConstraint = new ColumnConstraints();
        ColumnConstraints wednesdayColumnConstraint = new ColumnConstraints();
        ColumnConstraints thursdayColumnConstraint = new ColumnConstraints();

        timeColumnConstraint.setPercentWidth(dayColWidth / 2.5);
        timeColumnConstraint.setMinWidth(60);
        sundayColumnConstraint.setPercentWidth(dayColWidth);
        mondayColumnConstraint.setPercentWidth(dayColWidth);
        tuesdayColumnConstraint.setPercentWidth(dayColWidth);
        wednesdayColumnConstraint.setPercentWidth(dayColWidth);
        thursdayColumnConstraint.setPercentWidth(dayColWidth);

        grid.getColumnConstraints().addAll(
                timeColumnConstraint,
                sundayColumnConstraint,
                mondayColumnConstraint,
                tuesdayColumnConstraint,
                wednesdayColumnConstraint,
                thursdayColumnConstraint);

    }

    private void updateHeaderZIndex() {
        for (Node header : sideHeaders)
            header.toFront();
        for (Node header : topHeaders)
            header.toFront();
    }

    private VBox createEmptyCell(int colIndex, int hourRowIndex, int subRowIndex) {
        VBox emptyCell = new VBox();

        emptyCell.setStyle(
                "-fx-background-color: " + (hourRowIndex % 2 == 0 ? ScheduleController.getInstance().getEvenCellBG()
                        : ScheduleController.getInstance().getOddCellBG()) + ";");

        emptyCell.setBorder(
                new Border(new BorderStroke(Color.valueOf(ScheduleController.getInstance().getOutlineColor()),
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                        new BorderWidths(
                                ScheduleController.getInstance().getBorderWidth()))));

        grid.add(emptyCell, colIndex, subRowIndex);

        return emptyCell;
    }

    public void addSection(Section section) {
        for (Lecture lecture : section.getLectures()) {
            addLecture(lecture);
        }
        scheduleSections.add(section);
    }

    // Adds a lecture to the schedule (grid) and the final sections list
    private void addLecture(Lecture lecture) {
        int colIndex = ScheduleTimeConverter.dayOfWeekTOIndex(lecture.getDay());
        int rowIndex = lecture.getSubRowIndex();
        grid.getChildren()
                .removeIf(node -> GridPane.getColumnIndex(node) == colIndex &&
                        GridPane.getRowIndex(node) == rowIndex);

        grid.add(lecture, colIndex, rowIndex);
        GridPane.setRowSpan(lecture, lecture.getRowSpan());
        updateHeaderZIndex();
    }

    public void removeSection(Section section) {
        for (Lecture lecture : section.getLectures()) {
            removeLecture(lecture);
        }
        scheduleSections.remove(section);
        BasketController.getInstance().updateAvailability();
    }

    private void removeLecture(Lecture lecture) {
        grid.getChildren().removeIf(lecNode -> lecNode.equals(lecture));
        createEmptyCell(lecture.getColIndex(), lecture.getRowIndex(), lecture.getSubRowIndex());
    }

    public String getOutlineColor() {
        return outlineColor;
    }

    public void setOutlineColor(String outlineColor) {
        this.outlineColor = outlineColor;
    }

    public String getEvenCellBG() {
        return evenCellBG;
    }

    public void setEvenCellBG(String evenCellBG) {
        this.evenCellBG = evenCellBG;
    }

    public String getOddCellBG() {
        return oddCellBG;
    }

    public void setOddCellBG(String oddCellBG) {
        this.oddCellBG = oddCellBG;
    }

    public double getBorderWidth() {
        return borderWidth;
    }
}
