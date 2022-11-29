package com.course.offering;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.Date;

public class App extends Application {

    private BorderPane root;
    private VBox basketItemsContainer;
    private TableView table;

    // Basics
    private Stage primaryStage;
    private Scene scene;

    // Margin control for buttons
    private Insets General_INSETS = new Insets(5, 5, 5, 5);
    private Insets Button_INSETS = new Insets(5, 5, 5, 5);

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        initUi();
    }

    private void setupMainPane() {
        // Components smallest to largest
        ScheduleController tableController = new ScheduleController();
        table = tableController.initialize();
        AnchorPane mainPane = new AnchorPane(table);

        tableController.addSection(new Section("ICS104", 1, new Date(), "#59-1013"));

        // createTableColumns(table);

        // TableRow<String> sevenAM = new TableRow<String>();

        // table.getItems().add(new String("Buggs"));
        // table.getItems().add(new String("Daffy", "Duck", 83));
        // table.getItems().add(new String("Foghorn", "Leghorn", 74));
        // table.getItems().add(new String("Elmer", "Fudd", 83));
        // table.getItems().add(new String("Tweety", "Bird", 73));

        AnchorPane.setTopAnchor(table, 0.0);
        AnchorPane.setBottomAnchor(table, 0.0);
        AnchorPane.setLeftAnchor(table, 0.0);
        AnchorPane.setRightAnchor(table, 0.0);
        mainPane.setMaxWidth(Double.MAX_VALUE);
        mainPane.setMaxHeight(Double.MAX_VALUE);

        mainPane.getChildren().setAll(table);

        BorderPane.setMargin(mainPane, General_INSETS);
        root.setCenter(mainPane);
    }

    private void createTableColumns(TableView table) {
        // Sample for later
        // TableColumn<Person, Integer> ageColumn = new TableColumn<Person,
        // Integer>("Age");
        // ageColumn.setCellValueFactory(new PropertyValueFactory<Person,
        // Integer>("age"));

        TableColumn<String, String> time = new TableColumn<String, String>("Time");
        time.setMaxWidth(80);
        time.setMinWidth(80);
        time.setResizable(false);

        TableColumn<Section, Node> sunday = new TableColumn<Section, Node>("Sunday");
        sunday.setCellValueFactory(new PropertyValueFactory<Section, Node>("firstName"));

        TableColumn<Section, Node> monday = new TableColumn<Section, Node>("Monday");
        monday.setCellValueFactory(new PropertyValueFactory<Section, Node>("lastName"));

        TableColumn<Section, Node> tuesday = new TableColumn<Section, Node>("Tuesday");
        tuesday.setCellValueFactory(new PropertyValueFactory<Section, Node>("lastName"));

        TableColumn<Section, Node> wednesday = new TableColumn<Section, Node>("Wednesday");
        wednesday.setCellValueFactory(new PropertyValueFactory<Section, Node>("lastName"));

        TableColumn<Section, Node> thursday = new TableColumn<Section, Node>("Thursday");
        thursday.setCellValueFactory(new PropertyValueFactory<Section, Node>("lastName"));

        // TableColumn<String, String> friday = new TableColumn<String,
        // String>("Friday");
        // friday.setCellValueFactory(new PropertyValueFactory<String,
        // String>("lastName"));

        // TableColumn<String, String> saturday = new TableColumn<String,
        // String>("Saturday");
        // saturday.setCellValueFactory(new PropertyValueFactory<String,
        // String>("lastName"));

        table.getColumns().addAll(time, sunday, monday, tuesday, wednesday, thursday);
        // table.getColumns().add(friday);
        // table.getColumns().add(saturday);
    }

    private void setupBottomPane() {
        HBox bottomPane = new HBox(10);
        Button bottomButton = createBasicButton("Save Schedule", 150, 35);

        bottomPane.getChildren().setAll(bottomButton);

        // Bottom Pane Setup
        HBox.setMargin(bottomButton, General_INSETS);
        bottomPane.setAlignment(Pos.CENTER_LEFT);
        bottomPane.setStyle("-fx-background-color: #eb8d13");

        BorderPane.setMargin(bottomButton, General_INSETS);
        root.setBottom(bottomPane);

    }

    private void initUi() {
        root = new BorderPane();

        // The pane on the right with all courses
        setupBasketPane();
        // The schedule pane
        setupMainPane();
        // The saving button pane
        setupBottomPane();

        scene = new Scene(root, 1200, 750);
        primaryStage.setTitle("Second Page (Prototype)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setupBasketPane() {
        // Adding components, smallest to largest
        basketItemsContainer = new VBox();
        ScrollPane scrollPane = new ScrollPane(basketItemsContainer);
        AnchorPane anchorPane = new AnchorPane(scrollPane);

        // Styling
        // Fill the AnchorPane with the ScrollPane and set the Anchors to 0.0
        // That way the ScrollPane will take the full size of the Parent of
        // the AnchorPane
        AnchorPane.setTopAnchor(scrollPane, 0.0);
        AnchorPane.setBottomAnchor(scrollPane, 0.0);
        AnchorPane.setLeftAnchor(scrollPane, 0.0);
        AnchorPane.setRightAnchor(scrollPane, 0.0);
        anchorPane.setMaxWidth(Double.MAX_VALUE);
        anchorPane.setMaxHeight(Double.MAX_VALUE);
        anchorPane.setPrefWidth(300);

        scrollPane.setMaxWidth(Double.MAX_VALUE);
        scrollPane.setMaxHeight(Double.MAX_VALUE);
        scrollPane.setFitToWidth(true);

        basketItemsContainer.setAlignment(Pos.CENTER);
        basketItemsContainer.setMaxWidth(Double.MAX_VALUE);
        basketItemsContainer.setMaxHeight(Double.MAX_VALUE);

        BorderPane.setMargin(anchorPane, General_INSETS);
        root.setRight(anchorPane);
    }

    private Button createBasicButton(String text, double prefWidth, double prefHeight) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMaxHeight(Double.MAX_VALUE);
        button.setPrefWidth(prefWidth);
        button.setPrefHeight(prefHeight);
        BorderPane.setMargin(button, Button_INSETS);
        BorderPane.setAlignment(button, Pos.CENTER);
        return button;
    }

    public static void main(String[] args) {
        launch();
    }

}