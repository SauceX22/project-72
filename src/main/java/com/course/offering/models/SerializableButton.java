package com.course.offering.models;

import java.io.Serializable;

import javafx.scene.Node;
import javafx.scene.control.Button;

public class SerializableButton extends Button implements Serializable {

    public SerializableButton(String arg0, Node arg1) {
        super(arg0, arg1);
    }

    public SerializableButton() {
        super();
    }

    public SerializableButton(String arg0) {
        super(arg0);
    }

}
