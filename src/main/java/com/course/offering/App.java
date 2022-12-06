package com.course.offering;

import java.io.IOException;
import java.time.DayOfWeek;

import com.course.offering.controllers.BasketController;
import com.course.offering.controllers.ScheduleController;
import com.course.offering.models.Lecture;
import com.course.offering.models.Section;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {

    private BorderPane root;
    private BasketController basketController;
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
        initUi();
    }

    private void setupMainPane() {
        // Components smallest to largest
        // ScheduleController tableController = new ScheduleController();
        // table = tableController.initialize();
        grid = ScheduleController.getInstance().initialize();
        // VBox scrollContainer = new VBox(grid);
        ScrollPane scrollPane = new ScrollPane(grid);
        AnchorPane mainPane = new AnchorPane(scrollPane);

        ScheduleController.getInstance().setStickyTopHeaders(scrollPane);
        ScheduleController.getInstance().setStickySideHeaders(scrollPane);

        Lecture mine = new Lecture("Haitham", 22556, DayOfWeek.TUESDAY,
                "1100",
                50, "Mr. Nobody", "#59-1013");
        ScheduleController.getInstance()
                .addLectureToGrid(new Lecture("ICS104", 22556, DayOfWeek.MONDAY, "0700", 50,
                        "Mr. Nobody", "#59-1013"));
        ScheduleController.getInstance()
                .addLectureToGrid(new Lecture("ICS104", 22556, DayOfWeek.THURSDAY,
                        "0800",
                        50, "Mr. Nobody", "#59-1013"));
        ScheduleController.getInstance()
                .addLectureToGrid(new Lecture("ICS104", 22556, DayOfWeek.SUNDAY,
                        "0900",
                        50, "Mr. Nobody", "#59-1013"));
        ScheduleController.getInstance()
                .addLectureToGrid(new Lecture("ICS104", 22556, DayOfWeek.MONDAY,
                        "1000",
                        50, "Mr. Nobody", "#59-1013"));
        ScheduleController.getInstance()
                .addLectureToGrid(mine);

        // createTableColumns(table);

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
        // BorderPane.setMargin(mainPane, General_INSETS);
        root.setCenter(mainPane);
    }

    private void setupBottomPane() {
        HBox bottomPane = new HBox(10);
        Button bottomButton = createBasicButton("Save Schedule", 150, 35);

        bottomPane.getChildren().addAll(bottomButton);

        // Bottom Pane Setup
        HBox.setMargin(bottomButton, General_INSETS);
        bottomPane.setAlignment(Pos.CENTER_LEFT);
        bottomPane.setStyle("-fx-background-color: #eb8d13");

        bottomButton.setOnAction(e -> grid.getChildren().clear());

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
        // TODO: Pass the basket items to this constructor
        basketController = new BasketController();
        VBox basketItemsContainer = basketController.getBasketButtonsContainer();
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

        // TODO Delete this sample thing
        // Sample basket items
        for (int i = 0; i < 40; i++) {
            // basketController.addSection(new Section("ICS10" + i, 20568));
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