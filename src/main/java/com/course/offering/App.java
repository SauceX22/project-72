package com.course.offering;

import java.io.IOException;

import com.course.offering.controllers.BasketController;
import com.course.offering.controllers.FileController;
import com.course.offering.controllers.ScheduleController;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {

    // TODO sort this mess of a file

    private BorderPane root;
    // private TableView table;
    private GridPane grid;

    // Basics
    private Stage primaryStage;
    private Scene scene;

    // Margin control for buttons
    private Insets General_INSETS = new Insets(5, 5, 5, 5);
    private Insets Button_INSETS = new Insets(5, 5, 5, 5);

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;

        FileController.Initialize();
        initUi();
    }

    private void setupMainPane() {
        // Components smallest to largest
        grid = ScheduleController.getInstance().initialize();
        ScrollPane scrollPane = new ScrollPane(grid);
        AnchorPane mainPane = new AnchorPane(scrollPane);

        ScheduleController.getInstance().setStickyTopHeaders(scrollPane);
        ScheduleController.getInstance().setStickySideHeaders(scrollPane);

        AnchorPane.setTopAnchor(scrollPane, 0.0);
        AnchorPane.setBottomAnchor(scrollPane, 0.0);
        AnchorPane.setLeftAnchor(scrollPane, 0.0);
        AnchorPane.setRightAnchor(scrollPane, 0.0);
        mainPane.setMaxWidth(Double.MAX_VALUE);
        mainPane.setMaxHeight(Double.MAX_VALUE);
        mainPane.setBorder(new Border(new BorderStroke(Color.TRANSPARENT,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        scrollPane.setMaxWidth(Double.MAX_VALUE);
        scrollPane.setMaxHeight(Double.MAX_VALUE);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(false);
        scrollPane.setMaxSize(grid.getWidth(), grid.getHeight());
        root.setCenter(mainPane);
    }

    private void setupBottomPane() {
        HBox bottomPane = new HBox(10);
        Button saveScheduleButton = createBasicButton("Save Schedule", 150, 35);
        Button readScheduleButton = createBasicButton("Read", 150, 35);

        bottomPane.getChildren().addAll(saveScheduleButton, readScheduleButton);

        // Bottom Pane Setup
        HBox.setMargin(saveScheduleButton, General_INSETS);
        HBox.setMargin(readScheduleButton, General_INSETS);
        bottomPane.setAlignment(Pos.CENTER_LEFT);
        bottomPane.setStyle("-fx-background-color: #eb8d13");

        saveScheduleButton.setOnAction(
                e -> FileController.saveScheduleSections(ScheduleController.getScheduleSections()));
        readScheduleButton.setOnAction(
                e -> FileController.readScheduleSections());

        BorderPane.setMargin(saveScheduleButton, General_INSETS);
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
        // TODO: Pass the basket items to this constructor
        VBox basketItemsContainer = BasketController.getInstance().getBasketButtonsContainer();
        ScrollPane scrollPane = new ScrollPane(basketItemsContainer);
        AnchorPane anchorPane = new AnchorPane(scrollPane);

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