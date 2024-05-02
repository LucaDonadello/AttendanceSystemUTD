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

public class EditClass {
    // edit for classes
    // create a new window to insert values
    public static void editClasses(List<List<String>> classesRows, int finalI, List<StackPane> cellList) {
        // create a new window to insert values
        Stage editStage = new Stage();
        // create a new grid pane
        GridPane editPane = new GridPane();
        editPane.setId("editPane");
        editPane.setPadding(new Insets(10));
        editPane.setHgap(10);
        editPane.setVgap(10);
        // create labels and text fields for each column
        Label courseIDLabel = new Label("CourseID:");
        TextField courseIDField = new TextField();
        courseIDField.setText(classesRows.get(finalI).get(0));
        Label classNameLabel = new Label("ClassName:");
        TextField classNameField = new TextField();
        classNameField.setText(classesRows.get(finalI).get(1));
        Label startTimeLabel = new Label("StartTime:");
        TextField startTimeField = new TextField();
        startTimeField.setText(classesRows.get(finalI).get(2));
        Label endTimeLabel = new Label("EndTime:");
        TextField endTimeField = new TextField();
        endTimeField.setText(classesRows.get(finalI).get(3));
        Label startDateLabel = new Label("StartDate:");
        TextField startDateField = new TextField();
        startDateField.setText(classesRows.get(finalI).get(4));
        Label endDateLabel = new Label("EndDate:");
        TextField endDateField = new TextField();
        endDateField.setText(classesRows.get(finalI).get(5));
        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            try {
                QuerySystem.updateData("Course", List.of("ClassName", "StartTime", "EndTime", "StartDate", "EndDate"),
                        List.of(classNameField.getText(), startTimeField.getText(), endTimeField.getText(),
                                startDateField.getText(), endDateField.getText()),
                        "CourseID=".concat(courseIDField.getText()));
                // edit the UI
                // save new values
                int finalPos = finalI * (classesRows.size() + 3);
                // save new values in classesRowsNew
                List<String> classesRowNew;
                // save new values in classesRowsNew
                classesRowNew = List.of(courseIDField.getText(), classNameField.getText(), startTimeField.getText(),
                        endTimeField.getText(), startDateField.getText(), endDateField.getText());
                // update the UI with the new values in classesRowsNew
                for (int i = 0; i < classesRowNew.size(); i++, finalPos++) {
                    Label cellContents = new Label(classesRowNew.get(i));
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
        editPane.add(courseIDLabel, 0, 0);
        editPane.add(courseIDField, 1, 0);
        editPane.add(classNameLabel, 0, 1);
        editPane.add(classNameField, 1, 1);
        editPane.add(startTimeLabel, 0, 2);
        editPane.add(startTimeField, 1, 2);
        editPane.add(endTimeLabel, 0, 3);
        editPane.add(endTimeField, 1, 3);
        editPane.add(startDateLabel, 0, 4);
        editPane.add(startDateField, 1, 4);
        editPane.add(endDateLabel, 0, 5);
        editPane.add(endDateField, 1, 5);
        editPane.add(saveButton, 1, 6);

        Scene editScene = new Scene(editPane);
        editScene.getStylesheets()
                .add(Objects.requireNonNull(AttendanceApplication.class.getResource("Style.css")).toExternalForm());
        editStage.setScene(editScene);
        editStage.show();
    }
}
