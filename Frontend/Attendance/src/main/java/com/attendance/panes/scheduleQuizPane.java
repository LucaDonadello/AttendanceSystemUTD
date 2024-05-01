/******************************************************************************
 * scheduleQuizPane.java
 * This file provides the implementation of the scheduleQuizPane class, which
 * is responsible for displaying the quiz schedule table and buttons to upload
 * or create quizzes. The class contains a method that builds the schedule quiz
 * pane. The pane displays a table of all quizzes and buttons to upload or create
 * quizzes. The table contains the quiz ID and class name columns. The class also
 * contains a method that schedules a quiz and displays a pop-up message to confirm
 * that the quiz has been scheduled. The class also contains a method that allows
 * the user to edit the quiz schedule.
 * Written by Luca Donadello and Dylan Farmer for CS4485.0W1 , Project Attendance System,
 * starting >>>><<<<, 2024 NetID: lxd210013
 * ******************************************************************************/

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
    // method to build the schedule quiz pane
    public static Pane buildScheduleQuiz() throws SQLException {
        // scheduleQuizPane:- Page containing a table of all quizzes and buttons to
        // upload/create quizzes.
        Pane scheduleQuizPane = new Pane();

        // *** BUILD QUIZZES PANE HERE ***
        // create a grid pane to display the quiz schedule table
        GridPane scheduleTable = new GridPane();
        // set the ID of the schedule table
        scheduleTable.setId("scheduleTable");
        scheduleTable.setGridLinesVisible(true);
        // get the quiz schedule data from the database
        List<List<String>> quizClassRows = ConverterObjToStr.convertObjListToStrList(QuerySystem
                .selectQuery(new ArrayList<>(Arrays.asList("QuizID, ClassName, CourseID", "Course", "", "", "", ""))));
        // create a list of column names for the quiz schedule table
        List<String> quizColumnNames = new ArrayList<>(Arrays.asList("QuizID", "Class"));
        StackPane cell;
        Label cellContents;
        int quizzesColumnCount = quizColumnNames.size();
        // add the column names to the quiz schedule table
        for (int i = 0; i < quizzesColumnCount; i++) {
            cellContents = new Label(quizColumnNames.get(i));
            cell = new StackPane();
            cell.setPadding(new Insets(5));
            cell.getChildren().add(cellContents);
            scheduleTable.add(cell, i, 0);
        }
        // add the quiz schedule data to the quiz schedule table
        for (int i = 0; i < quizClassRows.size(); i++) {
            for (int j = 0; j < quizClassRows.get(i).size(); j++) {
                cellContents = new Label(quizClassRows.get(i).get(j));
                cell = new StackPane();
                cell.setPadding(new Insets(5));
                cell.getChildren().add(cellContents);
                scheduleTable.add(cell, j, i + 1);
            }
            // create a button to upload the quiz
            Button uploadQuizButton = new Button("Schedule Quiz");
            uploadQuizButton.setId("uploadQuizButton");
            // get the final value of i
            int finalI = i;
            // set the action for the upload quiz button
            uploadQuizButton.setOnAction(e -> {
                try {
                    // update display quiz to true
                    QuerySystem.updateData("Quiz", List.of("DisplayQuiz"), List.of("1"),
                            "QuizID = ".concat(quizClassRows.get(finalI).get(0)));
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
            // create a button to edit the quiz schedule
            Button editButton = new Button("Edit");
            editButton.setId("editButton");
            editButton.setOnAction(
                    e -> EditButtons.editQuizSchedule(quizClassRows, finalI, quizClassRows.get(finalI).get(2)));
            // add the upload quiz button and edit button to the quiz schedule table
            scheduleTable.add(uploadQuizButton, quizzesColumnCount, i + 1);
            scheduleTable.add(editButton, quizzesColumnCount + 1, i + 1);
        }
        // add the quiz schedule table to the schedule quiz pane
        scheduleQuizPane.getChildren().add(scheduleTable);
        return scheduleQuizPane;
    }
}
