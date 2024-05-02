package com.attendance.addButtons;

import com.attendance.AttendanceApplication;
import com.attendance.database.QuerySystem;
import com.attendance.utilities.SwitchDashboard;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static com.attendance.editButtons.EditClass.editClasses;
import static com.attendance.panes.quizpane.QuestionPane.buildQuestionsPane;

public class AddClass {
    // add class
    // create a new window to insert values
    public static void addClass(Pane dashboardPane, Pane titlePane, List<StackPane> cellList, GridPane classesTable,
                                List<List<String>> classesRows) {
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
        courseIDField.setText("");
        Label classNameLabel = new Label("ClassName:");
        TextField classNameField = new TextField();
        classNameField.setText("");
        Label startTimeLabel = new Label("StartTime:");
        TextField startTimeField = new TextField();
        startTimeField.setText("");
        Label endTimeLabel = new Label("EndTime:");
        TextField endTimeField = new TextField();
        endTimeField.setText("");
        Label startDateLabel = new Label("StartDate:");
        TextField startDateField = new TextField();
        startDateField.setText("");
        Label endDateLabel = new Label("EndDate:");
        TextField endDateField = new TextField();
        endDateField.setText("");
        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            try {
                // send query to insert the new values in the database
                QuerySystem.insertData("Course",
                        List.of("CourseID", "ClassName", "StartTime", "EndTime", "StartDate", "EndDate"),
                        List.of(courseIDField.getText(), classNameField.getText(), startTimeField.getText(),
                                endTimeField.getText(), startDateField.getText(), endDateField.getText()));
                // edit the UI
                // save new values
                int finalPos = classesTable.getRowCount();
                // save new values in classesRowsNew
                List<String> classesRowsNew;
                // save new values in classesRowsNew
                classesRowsNew = List.of(courseIDField.getText(), classNameField.getText(), startTimeField.getText(),
                        endTimeField.getText(), startDateField.getText(), endDateField.getText());
                int finalI = 0;
                // update the UI with the new values in classesRowsNew
                for (int i = 0; i < classesRowsNew.size(); i++) {
                    Label cellContents = new Label(classesRowsNew.get(i));
                    cellContents.setFont(Font.font("Arial", 14));
                    StackPane cellNew = new StackPane();
                    cellNew.setPadding(new Insets(5));
                    cellNew.getChildren().add(cellContents);
                    classesTable.add(cellNew, i, finalPos);
                    cellList.add(cellNew);
                    finalI = i;
                }
                // create a button to view the class
                Button viewButton = new Button("view");
                viewButton.setMinHeight(35);
                viewButton.setMinWidth(50);
                viewButton.setFont(Font.font(14));
                // set the action for the view class button
                int finalI1 = finalPos-1;
                viewButton.setOnAction(e -> {
                    try {
                        SwitchDashboard.switchDashboard(dashboardPane, buildQuestionsPane(classesRows.get(finalI1).get(0)),
                                titlePane, "Questions");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                // create a button to edit the class
                Button editButton = new Button("edit");
                editButton.setMaxHeight(35);
                editButton.setMinWidth(50);
                editButton.setFont(Font.font(14));
                // open a new window to edit class
                editButton.setOnAction(e -> editClasses(classesRows, finalI1, cellList));
                // create a button to delete the class
                Button deleteButton = new Button("delete");
                deleteButton.setMaxHeight(35);
                deleteButton.setMinWidth(60);
                deleteButton.setFont(Font.font(14));
                // copy label
                deleteButton.setOnAction(e -> {
                    int pos;
                    try {
                        // Solve thread problem change with better solution
                        System.out.print("");
                        // delete the class
                        QuerySystem.deleteData("Course", "CourseID=".concat(classesRows.get(finalI1).get(0)));
                        // 7 + 9 * (columnNum - 1) --> formula to get the position of the cell in the
                        // grid from the row
                        // cannot find better solution change if you can.
                        pos = (7 + (9 * (GridPane.getRowIndex(deleteButton) - 1)));
                        // remove the class from the table
                        Label cellContentsEmpty = new Label("");
                        StackPane cellEmpty = new StackPane();
                        cellEmpty.getChildren().add(cellContentsEmpty);
                        classesTable.getChildren().remove(pos, pos + 9);
                        // set the index of each cell to the new value starting form 0 to the end
                        for (int j = pos; j < classesTable.getChildren().size(); j++) {
                            Node node = classesTable.getChildren().get(j);
                            GridPane.setRowIndex(node, GridPane.getRowIndex(node) - 1);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                // add the buttons to the table
                classesTable.add(viewButton, finalI + 1, finalPos);
                classesTable.add(editButton, finalI + 2, finalPos);
                classesTable.add(deleteButton, finalI + 3, finalPos);

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
