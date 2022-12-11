package com.course.offering.controllers;

import java.util.ArrayList;

import com.course.offering.models.Section;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class BasketController {

    private static BasketController instance = null;

    public static BasketController getInstance() {
        if (instance == null)
            instance = new BasketController();
        return instance;
    }

    private ArrayList<Section> basketSections;

    private VBox basketButtonsContainer;

    public BasketController() {
        this(new ArrayList<>());
    }

    public BasketController(ArrayList<Section> sections) {
        initializeBasketMenu();
        setBasketSections(sections);
        addSections((FileController.getValidSections()));

    }

    public ArrayList<Section> getBasketSections() {
        return this.basketSections;
    }

    public void setBasketSections(ArrayList<Section> sections) {
        this.basketSections = new ArrayList<>();
        addSections(sections);
    }

    public void addSection(Section section) {
        this.basketSections.add(section);
        Button sectionButton = section.getBasketButton();
        basketButtonsContainer.getChildren().add(sectionButton);
        sectionButton.setOnAction(e -> {
            ScheduleController.getInstance().addSection(section);
            for (Section basketSection : basketSections) {
                if (basketSection.isConflict(section)) {
                    basketSection.getBasketButton().setDisable(true);
                }
            }
        });
    }

    public void addSections(ArrayList<Section> sections) {
        for (Section section : sections) {
            addSection(section);
        }
    }

    public void removeSection(Section section) {
        this.basketSections.remove(section);
    }

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

    public void updateAvailability() {
        // Enable all buttons
        for (Section basketSection : basketSections)
            basketSection.getBasketButton().setDisable(false);

        // for every schedule section, disable conflicts
        for (Section tableSection : ScheduleController.getScheduleSections()) {
            for (Section basketSection : basketSections) {
                if (tableSection.isConflict(basketSection))
                    basketSection.getBasketButton().setDisable(true);
            }
        }
    }

}
