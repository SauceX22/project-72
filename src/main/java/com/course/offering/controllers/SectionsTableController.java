package com.course.offering.controllers;

import java.util.ArrayList;

import com.course.offering.models.Section;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class SectionsTableController {
    private static SectionsTableController instance = null;

    public static SectionsTableController getInstance() {
        if (instance == null)
            instance = new SectionsTableController();
        return instance;
    }

    private TableView<Section> table;

    private TableColumn<Section, String> courseNameColumn;
    private TableColumn<Section, String> sectionColumn;
    private TableColumn<Section, String> activityColumn;
    private TableColumn<Section, String> crnColumn;
    private TableColumn<Section, String> courseFullNameColumn;
    private TableColumn<Section, String> instructorColumn;
    private TableColumn<Section, String> daysColumn;
    private TableColumn<Section, String> timeColumn;
    private TableColumn<Section, String> locationColumn;
    private TableColumn<Section, HBox> statusColumn;

    public TableView<Section> getTable() {
        return table;
    }

    public SectionsTableController() {
        initialize();
    }

    private void initialize() {
        table = new TableView<Section>();

        setupTableColumns();
        setupTableStyling();

    }

    public void setItems(ArrayList<Section> data) {
        setItems(FXCollections.observableArrayList(data));
    }

    public void setItems(ObservableList<Section> data) {
        table.setItems(data);
    }

    private void setupTableStyling() {
        courseNameColumn.setStyle("-fx-alignment: CENTER;");
        sectionColumn.setStyle("-fx-alignment: CENTER;");
        activityColumn.setStyle("-fx-alignment: CENTER;");
        crnColumn.setStyle("-fx-alignment: CENTER;");
        courseFullNameColumn.setStyle("-fx-alignment: CENTER;");
        instructorColumn.setStyle("-fx-alignment: CENTER;");
        daysColumn.setStyle("-fx-alignment: CENTER;");
        timeColumn.setStyle("-fx-alignment: CENTER;");
        locationColumn.setStyle("-fx-alignment: CENTER;");
        statusColumn.setStyle("-fx-alignment: CENTER;");

        table.setFixedCellSize(45);
        table.setPadding(new Insets(0));
        table.setStyle("-fx-background-color: grey;");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void setupTableColumns() {
        courseNameColumn = new TableColumn<Section, String>("Course Name");
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<Section, String>("courseName"));

        sectionColumn = new TableColumn<Section, String>("Section");
        sectionColumn.setCellValueFactory(new PropertyValueFactory<Section, String>("sectionNumber"));
        // sectionColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.05));

        activityColumn = new TableColumn<Section, String>("Activity");
        activityColumn.setCellValueFactory(new PropertyValueFactory<Section, String>("activity"));

        crnColumn = new TableColumn<Section, String>("CRN");
        crnColumn.setCellValueFactory(new PropertyValueFactory<Section, String>("CRN"));

        courseFullNameColumn = new TableColumn<Section, String>("Course Full Name");
        courseFullNameColumn.setCellValueFactory(new PropertyValueFactory<Section, String>("courseFullName"));

        instructorColumn = new TableColumn<Section, String>("Instructor Name");
        instructorColumn.setCellValueFactory(new PropertyValueFactory<Section, String>("instructor"));

        daysColumn = new TableColumn<Section, String>("Days");
        daysColumn.setCellValueFactory(new PropertyValueFactory<Section, String>("days"));

        timeColumn = new TableColumn<Section, String>("Time");
        timeColumn.setCellValueFactory(new PropertyValueFactory<Section, String>("time"));

        locationColumn = new TableColumn<Section, String>("Location");
        locationColumn.setCellValueFactory(new PropertyValueFactory<Section, String>("location"));

        statusColumn = new TableColumn<Section, HBox>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<Section, HBox>("status"));
        // statusColumn.setResizable(false);

        table.getColumns().add(courseNameColumn);
        table.getColumns().add(sectionColumn);
        table.getColumns().add(activityColumn);
        table.getColumns().add(crnColumn);
        table.getColumns().add(courseFullNameColumn);
        table.getColumns().add(instructorColumn);
        table.getColumns().add(daysColumn);
        table.getColumns().add(timeColumn);
        table.getColumns().add(locationColumn);
        table.getColumns().add(statusColumn);
    }

}
