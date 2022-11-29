package com.course.offering;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class App extends Application {

    private BorderPane root;

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
        TableView table = new TableView<String>();
        AnchorPane mainPane = new AnchorPane(table);

        createTableColumns(table);

        TableRow<String> sevenAM = new TableRow<>();

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

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

        TableColumn<String, String> sunday = new TableColumn<String, String>("Sunday");
        sunday.setCellValueFactory(new PropertyValueFactory<String, String>("firstName"));

        TableColumn<String, String> monday = new TableColumn<String, String>("Monday");
        monday.setCellValueFactory(new PropertyValueFactory<String, String>("lastName"));

        TableColumn<String, String> tuesday = new TableColumn<String, String>("Tuesday");
        tuesday.setCellValueFactory(new PropertyValueFactory<String, String>("lastName"));

        TableColumn<String, String> wednesday = new TableColumn<String, String>("Wednesday");
        wednesday.setCellValueFactory(new PropertyValueFactory<String, String>("lastName"));

        TableColumn<String, String> thursday = new TableColumn<String, String>("Thursday");
        thursday.setCellValueFactory(new PropertyValueFactory<String, String>("lastName"));

        TableColumn<String, String> friday = new TableColumn<String, String>("Friday");
        friday.setCellValueFactory(new PropertyValueFactory<String, String>("lastName"));

        TableColumn<String, String> saturday = new TableColumn<String, String>("Saturday");
        saturday.setCellValueFactory(new PropertyValueFactory<String, String>("lastName"));

        table.getColumns().add(sunday);
        table.getColumns().add(monday);
        table.getColumns().add(tuesday);
        table.getColumns().add(wednesday);
        table.getColumns().add(thursday);
        table.getColumns().add(friday);
        table.getColumns().add(saturday);
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
        VBox basketItemsContainer = new VBox();
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

        for (int i = 0; i < 40; i++) {
            Button rightButton = createBasicButton("Right", 100, 70);
            basketItemsContainer.getChildren().add(rightButton);
        }

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