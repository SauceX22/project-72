module com.course.offering {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.course.offering to javafx.fxml;

    exports com.course.offering;
    exports com.course.offering.models;
    exports com.course.offering.controllers;
    exports com.course.offering.utils;
}
