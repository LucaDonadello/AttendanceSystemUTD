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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class EditStudent {
    // edit for students
    // create a new window to insert values
    public static void editStudent(List<List<String>> studentsRows, int finalI, List<StackPane> cellList) {
        // create a new window to insert values
        Stage editStage = new Stage();
        // create a new grid pane
        GridPane editPane = new GridPane();
        editPane.setId("editPane");
        editPane.setPadding(new Insets(10));
        editPane.setHgap(10);
        editPane.setVgap(10);
        // create labels and text fields for each column
        Label firstNameLabel = new Label("First Name:");
        TextField firstNameField = new TextField();
        firstNameField.setText(studentsRows.get(finalI).get(0));
        Label middleNameLabel = new Label("middle Name:");
        TextField middleNameField = new TextField();
        middleNameField.setText(studentsRows.get(finalI).get(1));
        Label lastNameLabel = new Label("Last Name:");
        TextField lastNameField = new TextField();
        lastNameField.setText(studentsRows.get(finalI).get(2));
        Label studentNetIDLabel = new Label("StudentNetID:");
        TextField studentNetIDField = new TextField();
        studentNetIDField.setText(studentsRows.get(finalI).get(3));
        Label studentUTDIDLabel = new Label("StudentUTDID:");
        TextField studentUTDIDField = new TextField();
        studentUTDIDField.setText(studentsRows.get(finalI).get(4));
        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            try {
                QuerySystem.updateData("Student",
                        List.of("FirstName", "MiddleName", "LastName", "StudentNetID", "StudentUTDID"),
                        List.of(firstNameField.getText(), middleNameField.getText(), lastNameField.getText(),
                                studentNetIDField.getText(), studentUTDIDField.getText()),
                        "StudentUTDID=".concat(studentUTDIDField.getText()));
                // edit the UI
                // save new values
                int finalPos = finalI * (studentsRows.size() + 2);
                // save new values in studentRowsNew
                List<String> studentRowsNew;
                // save new values in studentRowsNew
                studentRowsNew = List.of(firstNameField.getText(), middleNameField.getText(), lastNameField.getText(),
                        studentNetIDField.getText(), studentUTDIDField.getText());
                // update the UI with the new values in studentRowsNew
                for (int i = 0; i < studentRowsNew.size(); i++, finalPos++) {
                    Label cellContents = new Label(studentRowsNew.get(i));
                    cellContents.setFont(Font.font("Arial", 14));
                    StackPane cellNew = new StackPane();
                    cellNew.getChildren().add(cellContents);
                    cellList.get(finalPos).getChildren().set(0, cellNew);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            editStage.close();
        });
        // add the labels and text fields to the grid pane
        editPane.add(firstNameLabel, 0, 0);
        editPane.add(firstNameField, 1, 0);
        editPane.add(middleNameLabel, 0, 1);
        editPane.add(middleNameField, 1, 1);
        editPane.add(lastNameLabel, 0, 2);
        editPane.add(lastNameField, 1, 2);
        editPane.add(studentNetIDLabel, 0, 3);
        editPane.add(studentNetIDField, 1, 3);
        editPane.add(studentUTDIDLabel, 0, 4);
        editPane.add(studentUTDIDField, 1, 4);
        editPane.add(saveButton, 1, 5);
        Scene editScene = new Scene(editPane);
        editScene.getStylesheets()
                .add(Objects.requireNonNull(AttendanceApplication.class.getResource("Style.css")).toExternalForm());
        editStage.setScene(editScene);
        editStage.show();
    }
}
