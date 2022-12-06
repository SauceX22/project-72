package com.course.offering.controllers;

import com.course.offering.models.Section;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class BasketController {

    private ObservableList<Section> basketSections;

    private VBox basketButtonsContainer;

    private Insets Button_INSETS = new Insets(5, 5, 5, 5);

    private double basketItemHeight = 120;

    public BasketController() {
        this(FXCollections.observableArrayList());

    }

    public BasketController(ObservableList<Section> sections) {
        initializeBasketMenu();
        setBasketSections(sections);
        // getBasketSections().addListener(new ListChangeListener<Section>() {
        // @Override
        // public void onChanged(Change<? extends Section> listener) {
        // System.out
        // .println(
        // "Basket Section List Changed! \n New List Size is: " +
        // listener.getList().size());
        // }
        // });
    }

    public ObservableList<Section> getBasketSections() {
        return this.basketSections;
    }

    public void setBasketSections(ObservableList<Section> sections) {
        this.basketSections = FXCollections.observableArrayList(sections);
        addSections(sections);
    }

    public void addSection(Section section) {
        this.basketSections.add(section);
        basketButtonsContainer.getChildren().add(createBasketButton(section, basketItemHeight));
    }

    public void addSections(ObservableList<Section> sections) {
        for (Section section : sections) {
            addSection(section);
        }
    }

    // public void removeSection(Section section) {
    // this.basketSections.remove(section);
    // }

    public void removeSection(int index) {
        this.basketSections.remove(index);
        basketButtonsContainer.getChildren().remove(index);
    }

    public VBox getBasketButtonsContainer() {
        return basketButtonsContainer;
    }

    public void initializeBasketMenu() {
        basketButtonsContainer = new VBox();
        basketButtonsContainer.setAlignment(Pos.CENTER);
        basketButtonsContainer.setMaxWidth(Double.MAX_VALUE);
        basketButtonsContainer.setMaxHeight(Double.MAX_VALUE);
    }

    private Button createBasketButton(Section section, double prefHeight) {
        Button button = new Button(section.getCourseFullName() + " ");
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMaxHeight(Double.MAX_VALUE);
        button.setPrefHeight(prefHeight);
        BorderPane.setMargin(button, Button_INSETS);
        BorderPane.setAlignment(button, Pos.CENTER);
        return button;
    }

}
