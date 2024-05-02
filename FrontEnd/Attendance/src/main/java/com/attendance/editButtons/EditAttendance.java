/******************************************************************************
 * Description: This class is used to edit the attendance data in the database.
 * The EditAttendance class contains a method to create a new window to edit the
 * attendance data. The method takes in the attendance data, the index of the
 * row to be edited, and a list of StackPane objects representing the cells in
 * the table. The method creates a new window with text fields for each column
 * of the attendance data, allowing the user to edit the data. The user can then
 * save the changes, which updates the database and the UI.
 * Written by Luca Donadello for CS4485.0W1 , Project Attendance System,
 * starting 15/04/2024 NetID: lxd210013
 * ******************************************************************************/

package com.attendance.editButtons;

import com.attendance.AttendanceApplication;
import com.attendance.database.QuerySystem;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class EditAttendance {
    // edit for attendance
    // create a new window to insert values
    public static void editAttendance(List<List<String>> attendanceRows, int finalI, List<StackPane> cellList) {
        // create a new window to insert values
        Stage editStage = new Stage();
        // create a new grid pane
        GridPane editPane = new GridPane();
        editPane.setId("editPane");
        editPane.setPadding(new Insets(10));
        editPane.setHgap(10);
        editPane.setVgap(10);

        // create labels and text fields for each column
        Label attendedLabel = new Label("Attended:");
        TextField attendedField = new TextField();
        attendedField.setText(attendanceRows.get(finalI).get(0));

        Label dateAndTimeLabel = new Label("DateAndTime:");
        TextField dateAndTimeField = new TextField();
        dateAndTimeField.setText(attendanceRows.get(finalI).get(1));
        dateAndTimeField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[0-9:-]")) {
                event.consume();
            }
        });

        Label ipLabel = new Label("IPAddress:");
        TextField ipField = new TextField();
        ipField.setText(attendanceRows.get(finalI).get(2));
        dateAndTimeField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[0-9:-]")) {
                event.consume();
            }
        });

        Label macLabel = new Label("MACID:");
        TextField macField = new TextField();
        macField.setText(attendanceRows.get(finalI).get(3));
        dateAndTimeField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[0-9:-]")) {
                event.consume();
            }
        });

        Label StudentIDLabel = new Label("StudentUTDID:");
        TextField StudentIDField = new TextField();
        StudentIDField.setText(attendanceRows.get(finalI).get(4));
        dateAndTimeField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[0-9:-]")) {
                event.consume();
            }
        });

        Label CourseIDLabel = new Label("CourseID:");
        TextField CourseIDField = new TextField();
        dateAndTimeField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[0-9:-]")) {
                event.consume();
            }
        });

        CourseIDField.setText(attendanceRows.get(finalI).get(5));
        Button saveButton = new Button("Save");
        // save the new values in the database
        // values are stored as 1 for true and 0 for false
        String attendedTrue = "1";
        String attendedFalse = "0";
        saveButton.setOnAction(event -> {
            try {
                // send query to update the database based on the new values
                if (attendedField.getText().equals("True") || attendedField.getText().equals("true")
                        || attendedField.getText().equals("1"))
                    QuerySystem.updateData("AttendanceInfo",
                            List.of("Attended", "DateAndTime", "IPAddress", "MACID", "StudentUTDID", "CourseID"),
                            List.of(attendedTrue, dateAndTimeField.getText(), ipField.getText(), macField.getText(),
                                    StudentIDField.getText(), CourseIDField.getText()),
                            "StudentUTDID=".concat(StudentIDField.getText()));
                else
                    QuerySystem.updateData("AttendanceInfo",
                            List.of("Attended", "DateAndTime", "IPAddress", "MACID", "StudentUTDID", "CourseID"),
                            List.of(attendedFalse, dateAndTimeField.getText(), ipField.getText(), macField.getText(),
                                    StudentIDField.getText(), CourseIDField.getText()),
                            "StudentUTDID=".concat(StudentIDField.getText()));

                // edit the UI
                // save new values
                int finalPos = finalI * (attendanceRows.size() + 4);
                // save new values in attendanceRowsNew
                List<String> attendanceRowsNew;
                // save new values in attendanceRowsNew
                attendanceRowsNew = List.of(attendedField.getText(), dateAndTimeField.getText(), ipField.getText(),
                        macField.getText(), StudentIDField.getText());
                // update the UI with the new values in attendanceRowsNew
                for (int i = 0; i < attendanceRowsNew.size(); i++, finalPos++) {
                    Label cellContents = new Label(attendanceRowsNew.get(i));
                    cellContents.setFont(Font.font("Arial", 14));
                    StackPane cellNew = new StackPane();
                    cellNew.getChildren().add(cellContents);
                    cellList.get(finalPos).getChildren().set(0, cellNew);
                }
            } catch (SQLException ex) {
                // Display error message if SQL query fails
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("SQL Server Error");
                alert.setHeaderText("An error occurred while updating data in the SQL server.");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
                throw new RuntimeException(ex);
            }
            editStage.close();
        });
        // add the labels and text fields to the grid pane
        editPane.add(attendedLabel, 0, 0);
        editPane.add(attendedField, 1, 0);
        editPane.add(dateAndTimeLabel, 0, 1);
        editPane.add(dateAndTimeField, 1, 1);
        editPane.add(ipLabel, 0, 2);
        editPane.add(ipField, 1, 2);
        editPane.add(macLabel, 0, 3);
        editPane.add(macField, 1, 3);
        editPane.add(StudentIDLabel, 0, 4);
        editPane.add(StudentIDField, 1, 4);
        editPane.add(CourseIDLabel, 0, 5);
        editPane.add(CourseIDField, 1, 5);
        editPane.add(saveButton, 1, 6);
        Scene editScene = new Scene(editPane);
        editScene.getStylesheets()
                .add(Objects.requireNonNull(AttendanceApplication.class.getResource("Style.css")).toExternalForm());
        editStage.setScene(editScene);
        editStage.show();
    }
}
