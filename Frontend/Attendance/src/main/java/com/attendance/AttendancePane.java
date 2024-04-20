package com.attendance;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.sql.SQLException;

public class AttendancePane {
    public static Pane buildAttendancePane(Pane dashboardPane, Pane titlePane) throws SQLException {
        // attendancePane:- Page containing all attendance information for a particular class and buttons to upload/create/download attendance.
        // -accessible from the classes page table
        Pane attendancePane = new Pane();
        // *** BUILD ATTENDANCE PANE HERE ***
        Label attendancePlaceholderContent = new Label("<Attendance Placeholder Content>");
        attendancePane.getChildren().add(attendancePlaceholderContent);
        return attendancePane;
    }
}
