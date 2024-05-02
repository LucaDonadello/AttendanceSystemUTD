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

public class EditQuestions {
    // edit for questions
    // create a new window to insert values
    public static void editQuestions(List<List<String>> questionsRows, int finalI, List<StackPane> cellList) {
        // create a new window to insert values
        Stage editStage = new Stage();
        // create a new grid pane
        GridPane editPane = new GridPane();
        editPane.setId("editPane");
        editPane.setPadding(new Insets(10));
        editPane.setHgap(10);
        editPane.setVgap(10);
        // create labels and text fields for each column
        Label questionIDLabel = new Label("QuestionID:");
        TextField questionIDField = new TextField();
        questionIDField.setText(questionsRows.get(finalI).get(0));
        Label questionLabel = new Label("Question:");
        TextField questionField = new TextField();
        questionField.setText(questionsRows.get(finalI).get(1));
        Label quizBankLabel = new Label("QuizBankID:");
        TextField QuizBankIDField = new TextField();
        QuizBankIDField.setText(questionsRows.get(finalI).get(2));
        Label correctAnswerLabel = new Label("CorrectAnswer:");
        TextField correctAnswerField = new TextField();
        correctAnswerField.setText(questionsRows.get(finalI).get(3));
        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            try {
                // send query
                QuerySystem.updateData("QuizQuestion", List.of("QuestionID", "Question", "QuizBankID", "CorrectAnswer"),
                        List.of(questionIDField.getText(), questionField.getText(), QuizBankIDField.getText(),
                                correctAnswerField.getText()),
                        "QuestionID=".concat(questionIDField.getText()));
                // change UI
                int finalPos = finalI * 2;
                // save new values in questionRowsNew
                List<String> questionRowsNew;
                // save new values in questionRowsNew
                questionRowsNew = List.of(questionIDField.getText(), questionField.getText(), QuizBankIDField.getText(),
                        correctAnswerField.getText());
                // update the UI with the new values in questionRowsNew
                for (int i = 0; i < questionRowsNew.size(); i++, finalPos++) {
                    Label cellContents = new Label(questionRowsNew.get(i));
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
        editPane.add(questionIDLabel, 0, 0);
        editPane.add(questionIDField, 1, 0);
        editPane.add(questionLabel, 0, 1);
        editPane.add(questionField, 1, 1);
        editPane.add(quizBankLabel, 0, 2);
        editPane.add(QuizBankIDField, 1, 2);
        editPane.add(correctAnswerLabel, 0, 3);
        editPane.add(correctAnswerField, 1, 3);
        editPane.add(saveButton, 1, 4);
        Scene editScene = new Scene(editPane);
        editScene.getStylesheets()
                .add(Objects.requireNonNull(AttendanceApplication.class.getResource("Style.css")).toExternalForm());
        editStage.setScene(editScene);
        editStage.show();
    }
}
