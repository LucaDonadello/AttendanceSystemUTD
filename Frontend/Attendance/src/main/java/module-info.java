module com.attendance {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.attendance to javafx.fxml;
    exports com.attendance;
}