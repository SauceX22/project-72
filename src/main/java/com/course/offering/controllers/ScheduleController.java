package com.course.offering.controllers;

import com.course.offering.models.Lecture;
import com.course.offering.utils.ScheduleTimeConverter;

import javafx.beans.InvalidationListener;
import javafx.event.ActionEvent;
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

    // Table
    private GridPane grid;

    public GridPane getGrid() {
        if (grid == null)
            System.err.println("A grid has not been initialized");
        return grid;
    }

    public void setGrid(GridPane grid) {
        this.grid = grid;
    }

    private int numberOfHourRows = 14;
    private int firstRowHeight = 40;
    private int hourRowsHeight = 100;
    private int dayColWidth = 150;

    private String outlineColor = "#A2A2A2";
    private String evenCellBG = "white";
    private String oddCellBG = "#E7E7E7";
    private double borderWidth = 0.2;

    private Node[] topHeaders = new Node[6];
    private Node[] sideHeaders = new Node[numberOfHourRows];

    public GridPane initialize() {
        grid = new GridPane();

        grid.setMaxWidth(Double.MAX_VALUE);
        grid.setMaxHeight(Double.MAX_VALUE);

        fillAsEmptyGrid();
        setSideHeaders(grid, numberOfHourRows);
        setTopHeaders(grid);

        setupColConstraints(dayColWidth);

        // grid.getChildren().remove(2 * 2);

        // grid.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // time.setSortable(false);

        // grid.getColumns().addAll(time, sunday, monday, tuesday, wednesday, thursday);

        // for (int i = 0; i < initialRows; i++) {
        // grid.getItems().add(new LectureTableRow());
        // }

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

    private void setTopHeaders(GridPane gridPane) {
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

    private void setSideHeaders(GridPane gridPane, int numberOfHours) {
        RowConstraints headerRowConstraint = new RowConstraints();
        headerRowConstraint.setPercentHeight(firstRowHeight / numberOfHours);
        grid.getRowConstraints().add(headerRowConstraint);
        for (int i = 0; i < numberOfHours; i++) {
            Node timeHeader = getSimpleTextCell(ScheduleTimeConverter.indexToTime12(i), Pos.TOP_CENTER,
                    (i % 2 == 0 ? evenCellBG : oddCellBG));
            gridPane.add(timeHeader, 0, i + 1);
            sideHeaders[i] = timeHeader;
            RowConstraints timeRowConstraint = new RowConstraints();
            timeRowConstraint.setMinHeight(hourRowsHeight);
            timeRowConstraint.setPercentHeight(100 / numberOfHours);
            grid.getRowConstraints().add(timeRowConstraint);
        }
    }

    private VBox getSimpleTextCell(String text, Pos position) {
        return getSimpleTextCell(text, position, "white");
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
        for (int row = 0; row < numberOfHourRows; row++) {
            for (int col = 0; col < 6; col++) {
                grid.add(createEmptyLectureCell(row), col + 1, row + 1);
            }
        }
    }

    private void setupColConstraints(int dayColWidth) {
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

    // Save button
    // TODO: Implement the exportation of schedule as a binary file
    public void saveSchedule(ActionEvent event) {

    }

    private VBox createEmptyLectureCell(int rowIndex) {
        VBox fillerCell = new VBox();

        fillerCell.setStyle(
                "-fx-background-color: " + (rowIndex % 2 == 0 ? ScheduleController.getInstance().getEvenCellBG()
                        : ScheduleController.getInstance().getOddCellBG()) + "; ");

        fillerCell.setBorder(
                new Border(new BorderStroke(Color.valueOf(ScheduleController.getInstance().getOutlineColor()),
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                        new BorderWidths(ScheduleController.getInstance().getBorderWidth()))));
        return fillerCell;
    }

    // TODO: Implement the addition of Lectures to schedule
    // lecture on press event
    public void addLectureToGrid(Lecture lecture) {
        // lectures.add(lecture.getTableIndex(), lecture);
        int colIndex = ScheduleTimeConverter.dayOfWeekTOIndex(lecture.getDay());
        int rowIndex = lecture.getTableRowIndex();

        grid.getChildren()
                .removeIf(node -> GridPane.getColumnIndex(node) == colIndex &&
                        GridPane.getRowIndex(node) == rowIndex);

        Node newLecture = lecture.getLectureTableItem();
        grid.add(newLecture, colIndex, rowIndex);
        newLecture.toBack();
        // System.out.println(lecture);
        // grid.setItems(lectureRows);
    }

    // TODO: Get the seciton in question and remove it
    public void removeLecture(ActionEvent event) {
        // int selectedID = table.getSelectionModel().getSelectedIndex();
        // table.getItems().remove(selectedID);
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
