package com.course.offering;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

@SuppressWarnings("unchecked")
public class ScheduleController {

    // Table
    private TableView<Section> table;

    // Columns
    private TableColumn<Section, String> time;
    private TableColumn<Section, Node> sunday;
    private TableColumn<Section, Node> monday;
    private TableColumn<Section, Node> tuesday;
    private TableColumn<Section, Node> wednesday;
    private TableColumn<Section, Node> thursday;

    public TableView initialize() {
        table = new TableView<Section>();
        time = new TableColumn<Section, String>("Time");
        sunday = new TableColumn<Section, Node>("Sunday");
        monday = new TableColumn<Section, Node>("Monday");
        tuesday = new TableColumn<Section, Node>("Tuesday");
        wednesday = new TableColumn<Section, Node>("Wednesday");
        thursday = new TableColumn<Section, Node>("Thursday");

        time.setMaxWidth(80);
        time.setMinWidth(80);
        time.setResizable(false);

        sunday.setCellValueFactory(new PropertyValueFactory<Section, Node>("firstName"));

        monday.setCellValueFactory(new PropertyValueFactory<Section, Node>("lastName"));

        tuesday.setCellValueFactory(new PropertyValueFactory<Section, Node>("lastName"));

        wednesday.setCellValueFactory(new PropertyValueFactory<Section, Node>("lastName"));

        thursday.setCellValueFactory(new PropertyValueFactory<Section, Node>("lastName"));

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.getColumns().addAll(time, sunday, monday, tuesday, wednesday, thursday);

        return table;
    }

    // Save button
    // TODO: Implement the exportation of schedule as a binary file
    public void saveSchedule(ActionEvent event) {

    }

    // TODO: Implement the addition of sections to schedule and subscribe this to
    // section on press event
    public void addSection(Section section) {
        ObservableList<Section> sections = table.getItems();
        sections.add(section);
        table.setItems(sections);
    }

    // TODO: Get the seciton in question and remove it
    public void removeSection(ActionEvent event) {
        int selectedID = table.getSelectionModel().getSelectedIndex();
        table.getItems().remove(selectedID);
    }
}
