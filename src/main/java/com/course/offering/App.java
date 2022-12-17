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

        TableView<Section> table = SectionsTableController.getInstance().getTable();

        root.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, Insets.EMPTY)));

        HBox hBoxTop = new HBox();
        hBoxTop.setSpacing(5);
        hBoxTop.setPadding(new Insets(12, 0, 0, 12));
        hBoxTop.setAlignment(Pos.BASELINE_CENTER);
        hBoxTop.setBackground(new Background(new BackgroundFill(Color.DARKBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        root.setTop(hBoxTop);

        Button loadButton = new Button("Load");
        loadButton.setPrefSize(80, 40);
        loadButton.setAlignment(Pos.CENTER);
        hBoxTop.getChildren().add(loadButton);
        hBoxTop.setAlignment(Pos.BASELINE_CENTER);

        Label top = createLabel("Sections in basket : 0", "#ffd0fe");
        top.setTextFill(Color.WHITE);
        top.setAlignment(Pos.CENTER);
        top.setMinSize(50, 50);
        // top.setBackground(new Background(new BackgroundFill(Color.ORANGE,
        // CornerRadii.EMPTY, Insets.EMPTY)));
        hBoxTop.getChildren().add(top);

        Button nextButton = new Button("Next");
        Button addButton = new Button("Add");
        Button removeButton = new Button("Remove");
        nextButton.setPrefSize(80, 40);
        addButton.setPrefSize(80, 40);
        removeButton.setPrefSize(80, 40);
        HBox hBox = new HBox();
        hBox.setSpacing(5);
        hBox.setPadding(new Insets(5));
        nextButton.setAlignment(Pos.CENTER);
        hBox.setAlignment(Pos.BASELINE_RIGHT);
        hBox.setBackground(new Background(new BackgroundFill(Color.DARKBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        hBox.getChildren().addAll(addButton, removeButton, nextButton);
        root.setBottom(hBox);
        addButton.setAlignment(Pos.CENTER);
        removeButton.setAlignment(Pos.CENTER);

        removeButton.setDisable(true);
        addButton.setDisable(true);

        root.setCenter(table);

        BorderPane.setAlignment(top, Pos.CENTER);
        BorderPane.setAlignment(table, Pos.CENTER);

        BorderPane.setMargin(table, new Insets(5));
        BorderPane.setMargin(top, new Insets(5));

        SectionsTableController.getInstance().setItems(student.getValidSections());

        loadButton.setOnAction(e -> {
            ArrayList<Section> loadedSections = FileController.readScheduleSections(primaryStage, loadButton);
            BasketController.getInstance().clearBasket();
            for (Section section : BasketController.getInstance().getBasketSections()) {
                section.getStatus().setStyle("-fx-background-color: #F5F5F5;");
            }

            for (Section loadedSection : loadedSections) {
                BasketController.getInstance().addSection(loadedSection);

                for (Section studentSec : student.getValidSections()) {
                    if (studentSec.getCRN().compareTo(loadedSection.getCRN()) == 0) {
                        System.out.println(studentSec);

                        studentSec.getStatus().setStyle("-fx-background-color: #baffba;");
                    }
                }
                loadButton.setDisable(true);
            }
            top.setText("Sections in basket : " + BasketController.getInstance().getBasketSections().size());
            System.out.println(BasketController.getInstance().getBasketSections());
        });

        nextButton.setOnAction(e -> {
            initSecondPageUI();
        });

        table.setRowFactory(tv -> new TableRow<Section>() {
            @Override
            protected void updateItem(Section item, boolean empty) {
                super.updateItem(item, empty);
                top.setText("Sections in basket : " + BasketController.getInstance().getBasketSections().size());
                int rowIndex = table.getSelectionModel().getSelectedIndex();
                // student.getValidSections().get(rowIndex);
                if (rowIndex != -1) {
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
                    removeButton.setOnAction(e -> {
                        student.getValidSections().get(rowIndex).getStatus().setStyle("-fx-background-color: #F5F5F5;");
                        table.getSelectionModel().clearSelection();
                        BasketController.getInstance().removeSection(item);
                    });
                    // System.out.println(rowIndex);
                    addButton.setOnAction(e -> {
                        // setStyle("-fx-control-inner-background: #baffba;");
                        student.getValidSections().get(rowIndex).getStatus().setStyle("-fx-background-color: #baffba;");
                        // student.getValidSections()[rowIndex].getStatus().setStyle("-fx-background-color:
                        // #baffba;");
                        table.getSelectionModel().clearSelection();
                        BasketController.getInstance().addSection(item);
                        System.out.println(BasketController.getInstance().getBasketSections()
                                .get(BasketController.getInstance().getBasketSections().indexOf(item)));
                    });
                } else {
                    addButton.setDisable(true);
                    removeButton.setDisable(true);
                }

                // System.out.println(rowIndex);
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
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMaxHeight(Double.MAX_VALUE);
        button.setPrefWidth(prefWidth);
        button.setPrefHeight(prefHeight);
        BorderPane.setMargin(button, Button_INSETS);
        BorderPane.setAlignment(button, Pos.CENTER);
        return button;
    }

    private static Label createLabel(String text, String styleClass) {
        Label label = new Label(text);
        label.getStyleClass().add(styleClass);
        label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        return label;
    }

    public static void main(String[] args) {
        launch();
    }

}