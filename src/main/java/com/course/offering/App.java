package com.course.offering;

import java.io.IOException;
import java.util.ArrayList;

import com.course.offering.controllers.BasketController;
import com.course.offering.controllers.FileController;
import com.course.offering.controllers.ScheduleController;
import com.course.offering.controllers.SectionsTableController;
import com.course.offering.models.Section;
import com.course.offering.models.Student;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
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
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application {

    private BorderPane root;
    // private TableView table;
    private GridPane grid;

    private Student student;

    // Basics
    private Stage primaryStage;
    private Scene scene;

    // Margin control for buttons
    private Insets General_INSETS = new Insets(5, 5, 5, 5);
    private Insets Button_INSETS = new Insets(5, 5, 5, 5);

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;

        student = new Student();
        FileController.Initialize(student);

        initFirstPageUI();

        scene = new Scene(root, 1200, 750);

        primaryStage.setScene(scene);
        primaryStage.setMinWidth(750);
        primaryStage.setMinHeight(500);
        primaryStage.setTitle("Project-72 Course Offering");
        primaryStage.show();
    }

    private void initSchedulePane() {
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

    private void initBottomPane() {
        HBox bottomPane = new HBox(10);
        Button saveScheduleButton = createBasicButton("Save Schedule", 150, 35);

        bottomPane.getChildren().addAll(saveScheduleButton);

        // Bottom Pane Setup
        HBox.setMargin(saveScheduleButton, General_INSETS);
        bottomPane.setAlignment(Pos.CENTER_LEFT);
        // bottomPane.setStyle("-fx-background-color: #eb8d13");
        bottomPane.setBackground(new Background(new BackgroundFill(Color.DARKBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        saveScheduleButton.setOnAction(e -> {
            FileController.saveScheduleSections(ScheduleController.getScheduleSections(), primaryStage);
        });

        BorderPane.setMargin(saveScheduleButton, General_INSETS);
        root.setBottom(bottomPane);

    }

    private void initFirstPageUI() {
        root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, Insets.EMPTY)));

        // initializing tableview
        TableView<Section> table = SectionsTableController.getInstance().getTable();
        BorderPane.setAlignment(table, Pos.CENTER);
        BorderPane.setMargin(table, new Insets(5));

        // initializing top Hbox
        HBox hBoxTop = new HBox();
        hBoxTop.setSpacing(5);
        hBoxTop.setPadding(new Insets(12, 0, 0, 12));
        hBoxTop.setAlignment(Pos.BASELINE_CENTER);
        hBoxTop.setBackground(new Background(new BackgroundFill(Color.DARKBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        // initializing load button and sections in basket label
        Button loadButton = createBasicButton("Load", 100, 20);
        Label inbasketLabel = createLabel("Sections in basket : 0");
        hBoxTop.getChildren().addAll(inbasketLabel, loadButton);

        // initializing top pane
        BorderPane topPane = new BorderPane();
        topPane.setBackground(new Background(new BackgroundFill(Color.DARKBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        topPane.setCenter(inbasketLabel);
        topPane.setRight(loadButton);

        // initializing bottom buttons
        Button nextButton = createBasicButton("Next", 80, 40);
        Button addButton = createBasicButton("Add", 80, 40);
        Button removeButton = createBasicButton("Remove", 80, 40);

        // initializing bottom hbox
        HBox hBoxBottom = new HBox();
        hBoxBottom.setSpacing(5);
        hBoxBottom.setPadding(new Insets(5));
        hBoxBottom.setAlignment(Pos.BASELINE_RIGHT);
        hBoxBottom.setBackground(new Background(new BackgroundFill(Color.DARKBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        hBoxBottom.getChildren().addAll(addButton, removeButton, nextButton);

        // adding table and top and bottom hboxes to root
        root.setTop(topPane);
        root.setCenter(table);
        root.setBottom(hBoxBottom);

        SectionsTableController.getInstance().setItems(student.getValidSections());

        // when pressing load button get loaded sections from binary file and put in
        // basket
        loadButton.setOnAction(e -> {
            ArrayList<Section> loadedSections = FileController.readScheduleSections(primaryStage, loadButton);
            for (Section section : BasketController.getInstance().getBasketSections()) {
                section.getStatus().setStyle("-fx-background-color: #F5F5F5;");
                BasketController.getInstance().removeSection(section);
            }

            for (Section loadedSection : loadedSections) {
                BasketController.getInstance().addSection(loadedSection);
                for (Section studentSec : student.getValidSections()) {
                    if (studentSec.getCRN().compareTo(loadedSection.getCRN()) == 0) {
                        studentSec.getStatus().setStyle("-fx-background-color: #baffba;");
                    }
                }
                loadButton.setDisable(true);
            }
            inbasketLabel.setText("Sections in basket : " + BasketController.getInstance().getBasketSections().size());
        });

        nextButton.setOnAction(e -> {
            initSecondPageUI();
        });

        removeButton.setDisable(true);
        addButton.setDisable(true);

        table.setRowFactory(tv -> new TableRow<Section>() {
            @Override
            protected void updateItem(Section item, boolean empty) {
                super.updateItem(item, empty);
                int rowIndex = table.getSelectionModel().getSelectedIndex();
                inbasketLabel
                        .setText("Sections in basket : " + BasketController.getInstance().getBasketSections().size());
                if (rowIndex != -1) { // section not found
                    table.setOnMouseClicked(e -> {
                        try {
                            if (table.getSelectionModel().getSelectedItem().getStatus()
                                    .getStyle() == "-fx-background-color: #F5F5F5;") {
                                addButton.setDisable(false);
                                removeButton.setDisable(true);
                            } else {
                                addButton.setDisable(true);
                                removeButton.setDisable(false);
                            }
                        } catch (Exception ex) {
                            // System.out.println(ex.getMessage());
                        }
                    });
                    // when remove button is pressed set inbasket color to white and remove section
                    // from basket
                    removeButton.setOnAction(e -> {
                        student.getValidSections().get(rowIndex).getStatus().setStyle("-fx-background-color: #F5F5F5;");
                        table.getSelectionModel().clearSelection();
                        BasketController.getInstance().removeSection(item);
                    });
                    // when add button is pressed set inbasket color to green and add section to
                    // basket
                    addButton.setOnAction(e -> {
                        student.getValidSections().get(rowIndex).getStatus().setStyle("-fx-background-color: #baffba;");
                        table.getSelectionModel().clearSelection();
                        BasketController.getInstance().addSection(item);
                        System.out.println(BasketController.getInstance().getBasketSections()
                                .get(BasketController.getInstance().getBasketSections().indexOf(item)));
                    });
                } else {
                    addButton.setDisable(true);
                    removeButton.setDisable(true);
                }

            }
        });

    }

    private void initSecondPageUI() {
        root = new BorderPane();

        // The pane on the right with all courses
        initBasketPane();
        // The schedule pane
        initSchedulePane();
        // The saving button pane
        initBottomPane();

        scene = new Scene(root, 1200, 750);
        primaryStage.setScene(scene);
    }

    private void initBasketPane() {
        // Adding components, smallest to largest
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
        button.setAlignment(Pos.CENTER);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMaxHeight(Double.MAX_VALUE);
        button.setPrefWidth(prefWidth);
        button.setPrefHeight(prefHeight);
        BorderPane.setMargin(button, Button_INSETS);
        BorderPane.setAlignment(button, Pos.CENTER);
        return button;
    }

    private static Label createLabel(String text) {
        Label label = new Label(text);
        label.setTextFill(Color.WHITE);
        label.setAlignment(Pos.CENTER);
        label.setMinSize(50, 50);
        label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        label.setFont(new Font(25));
        return label;
    }

    public static void main(String[] args) {
        launch();
    }

}