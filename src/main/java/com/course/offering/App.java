package com.course.offering;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    // Panes
    private BorderPane root;

    // Basics
    private Stage primaryStage;
    private Scene scene;

    // Margin control for buttons
    private Insets INSETS = new Insets(5, 5, 5, 5);

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        // Image image = new Image("sample1.jpg", 60, 60, true, true);
        // ImageView sampleImage = new ImageView(image);
        // Button button = new Button("Close", sampleImage);
        // button.setMinHeight(80);
        // button.setMinWidth(140);
        // StackPane pane = new StackPane();
        // pane.getChildren().add(button);
        // button.setOnAction(e -> primaryStage.close());
        // borderPane.setStyle("-fx-background-color: #000");

        initUi();
    }

    private void initUi() {
        // Setting panes
        root = new BorderPane();
        VBox basketPane = new VBox();
        HBox bottomPane = new HBox();
        VBox mainPane = new VBox();

        // Testing buttons to fill up space
        Button centerButton = createButton("Center");
        Button rightButton = createButton("Right");
        Button bottomButton = createButton("Bottom");

        mainPane.getChildren().setAll(centerButton);
        basketPane.getChildren().setAll(rightButton);
        bottomPane.getChildren().setAll(bottomButton);

        // Main Pane Setup

        // Right Pane Setup
        VBox.setMargin(rightButton, INSETS);
        basketPane.setStyle("-fx-background-color: #f34839");
        basketPane.setAlignment(Pos.CENTER);

        // Bottom Pane Setup
        HBox.setMargin(bottomButton, INSETS);
        bottomPane.setAlignment(Pos.CENTER_LEFT);
        bottomPane.setStyle("-fx-background-color: #eb8d13");

        root.setCenter(mainPane);
        root.setRight(basketPane);
        root.setBottom(bottomPane);

        scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Second Page (Prototype)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMaxHeight(Double.MAX_VALUE);
        button.setMinWidth(150);
        BorderPane.setMargin(button, INSETS);
        BorderPane.setAlignment(button, Pos.CENTER);
        return button;
    }

    public static void main(String[] args) {
        launch();
    }

}