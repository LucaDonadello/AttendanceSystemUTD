package com.attendance.panes;

import com.attendance.EditButtons;
import com.attendance.database.QuerySystem;
import com.attendance.utilities.ConverterObjToStr;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class scheduleQuizPane {
    public static Pane buildScheduleQuiz() throws SQLException {
        // scheduleQuizPane:- Page containing a table of all quizzes and buttons to upload/create quizzes.
        Pane scheduleQuizPane = new Pane();

        // *** BUILD QUIZZES PANE HERE ***
        GridPane scheduleTable = new GridPane();
        scheduleTable.setId("scheduleTable");
        scheduleTable.setGridLinesVisible(true);
        List<List<String>> quizClassRows = ConverterObjToStr.convertObjListToStrList(QuerySystem.selectQuery(new ArrayList<>(Arrays.asList("QuizID, ClassName, CourseID", "Course", "", "", "", ""))));
        List<String> quizColumnNames = new ArrayList<>(Arrays.asList("QuizID", "Class"));
        StackPane cell;
        Label cellContents;
        int quizzesColumnCount = quizColumnNames.size();
        for (int i = 0; i < quizzesColumnCount; i++) {
            cellContents = new Label(quizColumnNames.get(i));
            cell = new StackPane();
            cell.setPadding(new Insets(5));
            cell.getChildren().add(cellContents);
            scheduleTable.add(cell, i, 0);
        }

        for (int i = 0; i < quizClassRows.size(); i++) {
            for (int j = 0; j < quizClassRows.get(i).size(); j++) {
                cellContents = new Label(quizClassRows.get(i).get(j));
                cell = new StackPane();
                cell.setPadding(new Insets(5));
                cell.getChildren().add(cellContents);
                scheduleTable.add(cell, j, i + 1);
            }

            Button uploadQuizButton = new Button("Schedule Quiz");
            uploadQuizButton.setId("uploadQuizButton");
            int finalI = i;
            uploadQuizButton.setOnAction(e -> {
                try {
                    //update display quiz to true
                    QuerySystem.updateData("Quiz", List.of("DisplayQuiz"), List.of("1"), "QuizID = ".concat(quizClassRows.get(finalI).get(0)));
                    // show pop-up message
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Quiz Scheduled");
                    alert.setHeaderText(null);
                    alert.setContentText("The quiz has been scheduled.");
                    alert.showAndWait();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            Button editButton = new Button("Edit");
            editButton.setId("editButton");
            editButton.setOnAction(e -> EditButtons.editQuizSchedule(quizClassRows,finalI, quizClassRows.get(finalI).get(2)));
            
            scheduleTable.add(uploadQuizButton, quizzesColumnCount, i + 1);
            scheduleTable.add(editButton, quizzesColumnCount + 1, i + 1);
        }
        scheduleQuizPane.getChildren().add(scheduleTable);
        return scheduleQuizPane;
    }
}
