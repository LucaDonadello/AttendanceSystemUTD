/******************************************************************************
 * Description: This class is used to edit the class data in the database.
 * The EditClass class contains a method to create a new window to edit the
 * class data. The method takes in the class data, the index of the
 * row to be edited, and a list of StackPane objects representing the cells in
 * the table. The method creates a new window with text fields for each column
 * of the class data, allowing the user to edit the data. The user can then
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
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class EditQuizSchedule {
    // edit quiz schedule
    // create a new window to insert values
    public static void editQuizSchedule(List<List<String>> quizClassRows, int finalI, String CourseID) {
        // create a new window to insert values
        Stage editStage = new Stage();
        // create a new grid pane
        GridPane editPane = new GridPane();
        editPane.setId("editPane");
        editPane.setPadding(new Insets(10));
        editPane.setHgap(10);
        editPane.setVgap(10);
        // create labels and text fields for each column
        Label quizIDLabel = new Label("QuizID:");
        TextField quizIDField = new TextField();
        quizIDField.setText(quizClassRows.get(finalI).get(0));
        quizIDField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[0-9]")) {
                event.consume();
            }
        });

        TextField classNameField = new TextField();
        classNameField.setText(quizClassRows.get(finalI).get(1));

        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            try {
                // send query to update the database with the new values
                QuerySystem.updateData("Course", List.of("QuizID"), List.of(quizIDField.getText()),
                        "CourseID = ".concat(CourseID));
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
        editPane.add(quizIDLabel, 0, 0);
        editPane.add(quizIDField, 1, 0);
        editPane.add(saveButton, 1, 2);
        Scene editScene = new Scene(editPane);
        editScene.getStylesheets()
                .add(Objects.requireNonNull(AttendanceApplication.class.getResource("Style.css")).toExternalForm());
        editStage.setScene(editScene);
        editStage.show();
    }
}
