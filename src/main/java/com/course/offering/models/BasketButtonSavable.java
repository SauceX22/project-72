package com.course.offering.models;

import java.io.Serializable;

import javafx.scene.Node;
import javafx.scene.control.Button;

public class BasketButtonSavable extends Button implements Serializable {

    public BasketButtonSavable() {
        super();
    }

    public BasketButtonSavable(String arg0) {
        super(arg0);
    }

    public BasketButtonSavable(String arg0, Node arg1) {
        super(arg0, arg1);
    }

}