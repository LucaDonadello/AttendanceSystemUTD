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
 * starting 25/03/2024, 2024 NetID: lxd210013
 * ******************************************************************************/

package com.attendance.panes;

import com.attendance.database.QuerySystem;
import com.attendance.utilities.ConverterObjToStr;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.attendance.editButtons.EditQuizSchedule.editQuizSchedule;

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
            cellContents.setFont(Font.font("Arial", 16));
            cell = new StackPane();
            cell.setPadding(new Insets(5));
            cell.getChildren().add(cellContents);
            scheduleTable.add(cell, i, 0);
        }
        // add the quiz schedule data to the quiz schedule table
        for (int i = 0; i < quizClassRows.size(); i++) {
            for (int j = 0; j < quizClassRows.get(i).size(); j++) {
                cellContents = new Label(quizClassRows.get(i).get(j));
                cellContents.setFont(Font.font("Arial", 14));
                cell = new StackPane();
                cell.setPadding(new Insets(5));
                cell.getChildren().add(cellContents);
                scheduleTable.add(cell, j, i + 1);
            }
            // create a button to upload the quiz
            Button uploadQuizButton = new Button("Schedule Quiz");
            uploadQuizButton.setMaxHeight(35);
            uploadQuizButton.setMinWidth(120);
            uploadQuizButton.setFont(Font.font(14));
            uploadQuizButton.setId("uploadQuizButton");
            // get the final value of i
            int finalI = i;
            // set the action for the upload quiz button
            uploadQuizButton.setOnAction(e -> {
                try {
                    // update display quiz to true
                    QuerySystem.updateData("Quiz", List.of("DisplayQuiz"), List.of("1"),
                            "QuizID = ".concat(quizClassRows.get(finalI).get(0)));

                    // select all the utdIDs from calss
                    List<List<String>> utdIDs = ConverterObjToStr.convertObjListToStrList(
                            QuerySystem.selectQuery(new ArrayList<>(Arrays.asList("Attendance.StudentUTDID",
                                    "Student INNER JOIN Attendance on Student.StudentUTDID = Attendance.StudentUTDID INNER JOIN Course ON Attendance.CourseID = Course.CourseID",
                                    "Course.CourseID = ".concat(quizClassRows.get(finalI).get(2)), "", "", ""))));

                    for (int j = 0; j < utdIDs.size(); j++) {
                        // insert the quiz into the attendance table
                        QuerySystem.insertData("AttendanceInfo",
                                List.of("AttendanceInfoID", "Attended", "DateAndTime", "IPAddress", "MacID",
                                        "StudentUTDID", "CourseID"),
                                List.of("0", "0", "2024-01-01", "0.0.0.0", "00:00:00:00:00:00", utdIDs.get(j).get(0),
                                        quizClassRows.get(finalI).get(2)));
                    }

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
            editButton.setMaxHeight(35);
            editButton.setMinWidth(50);
            editButton.setFont(Font.font(14));
            editButton.setId("editButton");
            editButton.setOnAction(
                    e -> editQuizSchedule(quizClassRows, finalI, quizClassRows.get(finalI).get(2)));
            // add the upload quiz button and edit button to the quiz schedule table
            scheduleTable.add(uploadQuizButton, quizzesColumnCount, i + 1);
            scheduleTable.add(editButton, quizzesColumnCount + 1, i + 1);
        }

        ScrollPane sp = new ScrollPane(scheduleTable);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setFitToHeight(true);
        sp.setFitToWidth(true);
        sp.setContent(scheduleTable);
        // solved requestLayout() resize "Bug" avoid resize when pressed
        sp.setOnMousePressed(Event::consume);
        sp.getContent().setOnMousePressed(Event::consume);

        scheduleQuizPane.getChildren().add(sp);
        // add the quiz schedule table to the schedule quiz pane
        scheduleQuizPane.getChildren().add(scheduleTable);
        return scheduleQuizPane;
    }
}
