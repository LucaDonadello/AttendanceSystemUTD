package com.attendance.panes;

import com.attendance.utilities.ConverterObjToStr;
import com.attendance.EditButtons;
import com.attendance.database.QuerySystem;
import com.attendance.utilities.SwitchDashboard;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizPane {
    public static Pane buildQuizPane(Pane dashboardPane, Pane titlePane) throws SQLException {
        // quizzesPane:- Page containing a table of all quizzes and buttons to upload/create quizzes.
        Pane quizzesPane = new Pane();

        // *** BUILD QUIZZES PANE HERE ***
        GridPane quizzesTable = new GridPane();
        quizzesTable.setId("quizzesTable");
        quizzesTable.setGridLinesVisible(true);
        List<List<String>> quizRows = ConverterObjToStr.convertObjListToStrList(QuerySystem.selectQuery(new ArrayList<>(Arrays.asList("QuizID, Password_, StartTime, Duration", "Quiz", "", "", "", ""))));
        List<String> quizColumnNames = new ArrayList<>(Arrays.asList("QuizID", "Password", "Start Time", "Duration"));
        StackPane cell = null;
        Label cellContents = null;
        int quizzesColumnCount = quizColumnNames.size();
        for (int i = 0; i < quizzesColumnCount; i++) {
            cellContents = new Label(quizColumnNames.get(i));
            cell = new StackPane();
            cell.setPadding(new Insets(5));
            cell.getChildren().add(cellContents);
            quizzesTable.add(cell, i, 0);
        }

        for (int i = 0; i < quizRows.size(); i++) {
            for (int j = 0; j < quizRows.get(i).size(); j++) {
                cellContents = new Label(quizRows.get(i).get(j));
                cell = new StackPane();
                cell.setPadding(new Insets(5));
                cell.getChildren().add(cellContents);
                quizzesTable.add(cell, j, i + 1);
            }

            Button viewButton = new Button("view");
            int finalI = i;
            viewButton.setOnAction(e -> {
                try {
                    SwitchDashboard.switchDashboard(dashboardPane, buildQuestionsPane(), titlePane, "Questions");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });

            Button editButton = new Button("edit");
            // open a new window to edit quiz
            editButton.setOnAction(e -> EditButtons.editQuiz(quizRows, finalI));

            Button deleteButton = new Button("delete");
            //copy label
            deleteButton.setOnAction(e -> {
                try {
                    QuerySystem.deleteData("Quiz", "QuizID=".concat(quizRows.get(finalI).get(0)));
                    //clear the table
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            quizzesTable.add(viewButton, quizzesColumnCount, i + 1);
            quizzesTable.add(editButton, quizzesColumnCount + 1, i + 1);
            quizzesTable.add(deleteButton, quizzesColumnCount + 2, i + 1);

        }
        quizzesPane.getChildren().add(quizzesTable);
        return quizzesPane;
    }

    public static Pane buildQuestionsPane() throws SQLException {
        // questionsPane:- Page containing a table of all questions for a particular quiz and buttons to upload/create questions.
        // -accessible from the quizzes page table
        Pane questionsPane = new Pane();

        // *** BUILD QUESTIONS PANE HERE ***
        GridPane questionsTable = new GridPane();
        questionsTable.setId("quizzesTable");
        questionsTable.setGridLinesVisible(true);
        List<List<String>> questionsRows = ConverterObjToStr.convertObjListToStrList(QuerySystem.selectQuery(new ArrayList<>(Arrays.asList("QuizQuestion.QuestionID, Question, CorrectAnswer, NumberOfOptions", "QuizQuestion JOIN QuizBank ON QuizQuestion.QuestionID = QuizBank.QuizQuestionID", "", "", "", ""))));
        List<String> questionsColumnNames = new ArrayList<>(Arrays.asList("Question Number", "Question", "Correct Answer", "Number of Options"));
        StackPane cell;
        Label cellContents;
        int questionsColumnCount = questionsColumnNames.size();
        for (int i = 0; i < questionsColumnCount; i++) {
            cellContents = new Label(questionsColumnNames.get(i));
            cellContents.setMinWidth(50);
            cellContents.setMinHeight(50);
            cell = new StackPane();
            cell.setPadding(new Insets(5));
            cell.getChildren().add(cellContents);
            questionsTable.add(cell, i, 0);
        }

        for (int i = 0; i < questionsRows.size(); i++) {
            for (int j = 0; j < questionsRows.get(i).size(); j++) {
                cellContents = new Label(questionsRows.get(i).get(j));
                cell = new StackPane();
                cell.setPadding(new Insets(5));
                cell.getChildren().add(cellContents);
                questionsTable.add(cell, j, i + 1);
            }

            int finalI = i;
            Button editButton = new Button("edit");
            // open a new window to insert values
            editButton.setOnAction(e -> EditButtons.editQuestions(questionsRows,finalI));

            Button deleteButton = new Button("delete");
            deleteButton.setOnAction(e -> {
                try {
                    //actual delete the quiz
                    QuerySystem.deleteData("QuizQuestion", "QuestionID=".concat(questionsRows.get(finalI).get(0)));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });

            questionsTable.add(editButton, questionsColumnCount, i + 1);
            questionsTable.add(deleteButton, questionsColumnCount + 1, i + 1);
        }
        questionsPane.getChildren().add(questionsTable);
        return questionsPane;
    }
}
