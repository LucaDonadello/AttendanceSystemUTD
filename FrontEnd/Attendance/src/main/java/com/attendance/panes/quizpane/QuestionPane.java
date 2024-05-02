package com.attendance.panes.quizpane;

import com.attendance.database.QuerySystem;
import com.attendance.utilities.ConverterObjToStr;
import javafx.event.Event;
import javafx.geometry.Insets;
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

import static com.attendance.editButtons.EditQuestions.editQuestions;

public class QuestionPane {
    public static Pane buildQuestionsPane(String quizID) throws SQLException {
        // questionsPane:- Page containing a table of all questions for a particular
        // quiz and buttons to upload/create questions.
        // -accessible from the quizzes page table
        Pane questionsPane = new Pane();

        // *** BUILD QUESTIONS PANE HERE ***
        // create a grid pane to display the questions table
        GridPane questionsTable = new GridPane();
        // set the ID of the questions table
        questionsTable.setId("quizzesTable");
        questionsTable.setGridLinesVisible(true);
        // get the questions data from the database
        List<List<String>> questionsRows = ConverterObjToStr.convertObjListToStrList(QuerySystem.selectQuery(
                new ArrayList<>(Arrays.asList("QuestionID, Question, QuizQuestion.QuizBankID, CorrectAnswer",
                        "Quiz JOIN QuizBank ON QuizBank.QuizBankID = Quiz.QuizBankID JOIN QuizQuestion ON QuizQuestion.QuizBankID = QuizBank.QuizBankID",
                        "Quiz.QuizID=".concat(quizID), "", "", ""))));
        // create a list of column names for the questions table
        List<String> questionsColumnNames = new ArrayList<>(
                Arrays.asList("QuestionID", "Question", "QuizBankID", "CorrectAnswer"));
        StackPane cell;
        Label cellContents;
        int questionsColumnCount = questionsColumnNames.size();
        // add the column names to the questions table
        for (int i = 0; i < questionsColumnCount; i++) {
            cellContents = new Label(questionsColumnNames.get(i));
            cellContents.setFont(Font.font("Arial", 16));
            cellContents.setMinWidth(50);
            cellContents.setMinHeight(50);
            cell = new StackPane();
            cell.setPadding(new Insets(5));
            cell.getChildren().add(cellContents);
            questionsTable.add(cell, i, 0);
        }
        // cell list to store the cells
        List<StackPane> cellList = new ArrayList<>();
        // add the questions data to the questions table
        for (int i = 0; i < questionsRows.size(); i++) {
            for (int j = 0; j < questionsRows.get(i).size(); j++) {
                cellContents = new Label(questionsRows.get(i).get(j));
                cellContents.setFont(Font.font("Arial", 14));
                cell = new StackPane();
                cell.setPadding(new Insets(5));
                cell.getChildren().add(cellContents);
                questionsTable.add(cell, j, i + 1);
                cellList.add(cell);
            }
            // store the final value of i
            int finalI = i;
            // create a button to edit the questions
            Button editButton = new Button("edit");
            editButton.setMaxHeight(35);
            editButton.setMinWidth(50);
            editButton.setFont(Font.font(14));
            // open a new window to insert values
            editButton.setOnAction(e -> editQuestions(questionsRows, finalI, cellList));
            // create a button to delete the questions
            Button deleteButton = new Button("delete");
            deleteButton.setMaxHeight(35);
            deleteButton.setMinWidth(60);
            deleteButton.setFont(Font.font(14));
            deleteButton.setOnAction(e -> {
                try {
                    // Implement delete functionality
                    // actual delete the quiz question
                    QuerySystem.deleteData("QuizQuestion", "QuestionID=".concat(questionsRows.get(finalI).get(0)));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            // add the edit button and delete button to the questions table
            questionsTable.add(editButton, questionsColumnCount, i + 1);
            questionsTable.add(deleteButton, questionsColumnCount + 1, i + 1);
        }
        ScrollPane sp = new ScrollPane(questionsTable);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setFitToHeight(true);
        sp.setFitToWidth(true);
        sp.setContent(questionsTable);
        // solved requestLayout() resize "Bug" avoid resize when pressed
        sp.setOnMousePressed(Event::consume);
        sp.getContent().setOnMousePressed(Event::consume);

        questionsPane.getChildren().add(sp);
        // add the questions table to the questions pane
        questionsPane.getChildren().add(questionsTable);
        return questionsPane;
    }
}
