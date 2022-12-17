package com.course.offering.controllers;

import java.util.ArrayList;

import com.course.offering.models.Section;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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

    public ArrayList<Section> getBasketSections() {
        if (basketSections == null)
            this.basketSections = new ArrayList<Section>();
        return this.basketSections;
    }

    public void addSection(Section section) {
        this.basketSections.add(section);
        Button sectionButton = section.getBasketButton();
        sectionButton.setOnAction(e -> {
            ScheduleController.getInstance().addSection(section);
            for (Section basketSection : basketSections) {
                if (section.isConflict(basketSection)) {
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
        this.basketSections.removeIf(e -> section.getCRN().equals(e.getCRN()));
    }

    public void removeSection(int index) {
        this.basketSections.remove(index);
    }

    public VBox getBasketButtonsContainer() {
        basketButtonsContainer = new VBox();
        basketButtonsContainer.setAlignment(Pos.CENTER);
        basketButtonsContainer.setMaxWidth(Double.MAX_VALUE);
        basketButtonsContainer.setMaxHeight(Double.MAX_VALUE);
        for (Section section : basketSections) {
            basketButtonsContainer.getChildren().add(section.getBasketButton());
        }
        return basketButtonsContainer;
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

    public void clearBasket() {
        basketSections.clear();
    }
}
