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
