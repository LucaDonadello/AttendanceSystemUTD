module com.attendance {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;
    requires javafx.graphics;

    opens com.attendance to javafx.fxml;
    exports com.attendance;
    exports com.attendance.panes;
    opens com.attendance.panes to javafx.fxml;
    exports com.attendance.database;
    opens com.attendance.database to javafx.fxml;
    exports com.attendance.utilities;
    opens com.attendance.utilities to javafx.fxml;
    exports com.attendance.panes.classpane;
    opens com.attendance.panes.classpane to javafx.fxml;
    exports com.attendance.panes.quizpane;
    opens com.attendance.panes.quizpane to javafx.fxml;
    exports com.attendance.addButtons;
    opens com.attendance.addButtons to javafx.fxml;
}