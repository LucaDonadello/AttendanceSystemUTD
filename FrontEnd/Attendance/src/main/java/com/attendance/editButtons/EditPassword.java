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
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EditPassword {
    // edit for password
    // create a new window to insert values
    public static void editPassword(List<List<String>> passwordsRows, int finalI, List<StackPane> cellList) {
        // create a new window to insert values
        Stage editStage = new Stage();
        // create a new grid pane
        GridPane editPane = new GridPane();
        editPane.setId("editPane");
        editPane.setPadding(new Insets(10));
        editPane.setHgap(10);
        editPane.setVgap(10);
        // create labels and text fields for each column
        Label passwordLabel = new Label("Password:");
        TextField passwordField = new TextField();
        passwordField.setText(passwordsRows.get(finalI).get(0));

        Label quizIDLabel = new Label("QuizID:");
        TextField quizIDField = new TextField();
        quizIDField.setText(passwordsRows.get(finalI).get(1));
        quizIDField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[0-9]")) {
                event.consume();
            }
        });

        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            try {
                QuerySystem.updateData("Quiz", List.of("Password_"), List.of(passwordField.getText()),
                        "QuizID=".concat(quizIDField.getText()));
                // edit the UI
                // save new values in passwordRowsNew
                int finalPos = finalI * 2;
                // save new values in passwordRowsNew
                List<List<String>> passwordsRowsNew = new ArrayList<>();
                // save new values in passwordRowsNew
                passwordsRowsNew.add(List.of(passwordField.getText(), quizIDField.getText()));
                // update the UI with the new values in passwordRowsNew
                for (int i = 0; i <= passwordsRowsNew.size(); i++, finalPos++) {
                    Label cellContents = new Label(passwordsRowsNew.get(0).get(i));
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
        editPane.add(passwordLabel, 0, 0);
        editPane.add(passwordField, 1, 0);
        editPane.add(quizIDLabel, 0, 1);
        editPane.add(quizIDField, 1, 1);
        editPane.add(saveButton, 1, 2);
        Scene editScene = new Scene(editPane);
        editScene.getStylesheets()
                .add(Objects.requireNonNull(AttendanceApplication.class.getResource("Style.css")).toExternalForm());
        editStage.setScene(editScene);
        editStage.show();
    }
}
