module com.attendance {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.attendance to javafx.fxml;
    exports com.attendance;
}