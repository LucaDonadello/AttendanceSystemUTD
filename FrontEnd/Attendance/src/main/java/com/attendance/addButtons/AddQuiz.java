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

import static com.attendance.editButtons.EditQuiz.editQuiz;
import static com.attendance.panes.quizpane.QuestionPane.buildQuestionsPane;

public class AddQuiz {
    // add quiz
    // create a new window to insert values
    public static void addQuiz(Pane dashboardPane, Pane titlePane, List<StackPane> cellList, GridPane quizzesTable,
                               List<List<String>> quizRows) {
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
        quizIDField.setText("");
        Label quizBankIDLabel = new Label("QuizBankID:");
        TextField quizBankIDField = new TextField();
        quizBankIDField.setText("");
        Label numberOfQuestionsLabel = new Label("NumberOfQuestions:");
        TextField numberOfQuestionsField = new TextField();
        numberOfQuestionsField.setText("");
        Label durationLabel = new Label("Duration:");
        TextField durationField = new TextField();
        durationField.setText("");
        Label startTimeLabel = new Label("StartTime:");
        TextField startTimeField = new TextField();
        startTimeField.setText("");
        Label displayQuizLabel = new Label("DisplayQuiz:");
        TextField displayQuizField = new TextField();
        displayQuizField.setText("");
        Label passwordLabel = new Label("Password:");
        TextField passwordField = new TextField();
        passwordField.setText("");
        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            try {
                // send query to insert the new values in the database
                QuerySystem.insertData("Quiz",
                        List.of("QuizID", "QuizBankID", "NumberOfQuestions", "Duration", "StartTime",
                                "DisplayQuiz", "Password_"),
                        List.of(quizIDField.getText(), quizBankIDField.getText(), numberOfQuestionsField.getText(),
                                durationField.getText(), startTimeField.getText(),
                                displayQuizField.getText(), passwordField.getText()));
                // edit the UI
                // save new values
                int finalPos = quizzesTable.getRowCount();
                // save new values in quizRowsNew
                List<String> quizRowsNew;
                // save new values in quizRowsNew
                quizRowsNew = List.of(quizIDField.getText(), passwordField.getText(),
                        startTimeField.getText(), durationField.getText());
                int finalI = 0;
                // update the UI with the new values in quizRowsNew
                for (int i = 0; i < quizRowsNew.size(); i++) {
                    Label cellContents = new Label(quizRowsNew.get(i));
                    cellContents.setFont(Font.font("Arial", 14));
                    StackPane cellNew = new StackPane();
                    cellNew.setPadding(new Insets(5));
                    cellNew.getChildren().add(cellContents);
                    quizzesTable.add(cellNew, i, finalPos);
                    cellList.add(cellNew);
                    finalI = i;
                }
                // create a button to view the quiz
                Button viewButton = new Button("view");
                viewButton.setMinHeight(35);
                viewButton.setMinWidth(50);
                viewButton.setFont(Font.font(14));
                // set the action for the view quiz button
                int finalI1 = finalI;
                viewButton.setOnAction(e -> {
                    try {
                        SwitchDashboard.switchDashboard(dashboardPane, buildQuestionsPane(quizRows.get(finalI1).get(0)),
                                titlePane, "Questions");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                // create a button to edit the quiz
                Button editButton = new Button("edit");
                editButton.setMaxHeight(35);
                editButton.setMinWidth(50);
                editButton.setFont(Font.font(14));
                // open a new window to edit quiz
                editButton.setOnAction(e -> editQuiz(quizRows, finalI1, cellList));
                // create a button to delete the quiz
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
                        // actual delete the quiz
                        QuerySystem.deleteData("Quiz", "QuizID=".concat(quizRows.get(finalI1).get(0)));
                        // remove the quiz from the table --> formula can be improved I could not find a
                        // better solution at the moment
                        // find the position of the first cell of the row based on which button was
                        // clicked
                        pos = (7 * (GridPane.getRowIndex(deleteButton) - 1) + 5);
                        // create an empty cell
                        Label cellContentsEmpty = new Label("");
                        // create a stack pane to hold the empty cell
                        StackPane cellEmpty = new StackPane();
                        // add the empty cell to the table
                        cellEmpty.getChildren().add(cellContentsEmpty);
                        quizzesTable.getChildren().remove(pos, pos + 7);
                        // reset the indexes after remove they still have the old indexes so need to be
                        // updated
                        for (int j = pos; j < quizzesTable.getChildren().size(); j++) {
                            Node node = quizzesTable.getChildren().get(j);
                            GridPane.setRowIndex(node, GridPane.getRowIndex(node) - 1);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                quizzesTable.add(viewButton, finalI1 + 1, finalPos);
                quizzesTable.add(editButton, finalI1 + 2, finalPos);
                quizzesTable.add(deleteButton, finalI1 + 3, finalPos);

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            editStage.close();
        });
        // add the labels and text fields to the grid pane
        editPane.add(quizIDLabel, 0, 0);
        editPane.add(quizIDField, 1, 0);
        editPane.add(quizBankIDLabel, 0, 1);
        editPane.add(quizBankIDField, 1, 1);
        editPane.add(numberOfQuestionsLabel, 0, 2);
        editPane.add(numberOfQuestionsField, 1, 2);
        editPane.add(durationLabel, 0, 3);
        editPane.add(durationField, 1, 3);
        editPane.add(startTimeLabel, 0, 4);
        editPane.add(startTimeField, 1, 4);
        editPane.add(displayQuizLabel, 0, 5);
        editPane.add(displayQuizField, 1, 5);
        editPane.add(passwordLabel, 0, 6);
        editPane.add(passwordField, 1, 6);
        editPane.add(saveButton, 1, 7);
        Scene editScene = new Scene(editPane);
        editScene.getStylesheets()
                .add(Objects.requireNonNull(AttendanceApplication.class.getResource("Style.css")).toExternalForm());
        editStage.setScene(editScene);
        editStage.show();
    }
}
