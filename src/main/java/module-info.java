module com.course.offering {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.course.offering to javafx.fxml;

    exports com.course.offering;
}
