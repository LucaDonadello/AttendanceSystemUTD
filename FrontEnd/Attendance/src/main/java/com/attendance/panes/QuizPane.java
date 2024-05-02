/****************************************************************************** 
 * This file contains the code for the QuizPane class. This class
 * is responsible for displaying the quiz table and buttons to upload or create quizzes.
 * The class contains a method that builds the quiz pane. The pane displays a table of all
 * quizzes and buttons to upload or create quizzes. The table contains the quiz ID, password,
 * start time, and duration columns. The class also contains a method that builds the questions
 * pane. The questions pane displays a table of all questions for a particular quiz and buttons
 * to upload or create questions. The table contains the question ID, question, quiz bank ID,
 * and correct answer columns. The class also contains a method that allows the user to edit
 * the quiz questions.
 * Written by Luca Donadello and Dylan Farmer for CS4485.0W1 , Project Attendance System,
 * starting >>>><<<<, 2024 NetID: lxd210013
 * ******************************************************************************/

package com.attendance.panes;

import com.attendance.utilities.ConverterObjToStr;
import com.attendance.EditButtons;
import com.attendance.database.QuerySystem;
import com.attendance.utilities.SwitchDashboard;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizPane {
    // method to build the quiz pane
    public static Pane buildQuizPane(Pane dashboardPane, Pane titlePane) throws SQLException {
        // quizzesPane:- Page containing a table of all quizzes and buttons to
        // upload/create quizzes.
        Pane quizzesPane = new Pane();

        // *** BUILD QUIZZES PANE HERE ***
        // create a grid pane to display the quiz table
        GridPane quizzesTable = new GridPane();
        // set the ID of the quiz table
        quizzesTable.setId("quizzesTable");
        quizzesTable.setGridLinesVisible(true);

        // get the quiz data from the database
        List<List<String>> quizRows = ConverterObjToStr.convertObjListToStrList(QuerySystem.selectQuery(
                new ArrayList<>(Arrays.asList("QuizID, Password_, StartTime, Duration", "Quiz", "", "", "", ""))));
        // create a list of column names for the quiz table
        List<String> quizColumnNames = new ArrayList<>(Arrays.asList("QuizID", "Password", "Start Time", "Duration"));
        // create a stack pane to hold the cell contents
        StackPane cell;
        Label cellContents;
        int quizzesColumnCount = quizColumnNames.size();
        // add the column names to the quiz table
        for (int i = 0; i < quizzesColumnCount; i++) {
            cellContents = new Label(quizColumnNames.get(i));
            cellContents.setFont(Font.font("Arial", 16));
            cell = new StackPane();
            cell.setPadding(new Insets(5));
            cell.getChildren().add(cellContents);
            quizzesTable.add(cell, i, 0);
        }
        // add the quiz data to the quiz table
        List<StackPane> cellList = new ArrayList<>();
        // add the quiz data to the quiz table
        for (int i = 0; i < quizRows.size(); i++) {
            for (int j = 0; j < quizRows.get(i).size(); j++) {
                cellContents = new Label(quizRows.get(i).get(j));
                cellContents.setFont(Font.font("Arial", 14));
                cell = new StackPane();
                cell.setPadding(new Insets(5));
                cell.getChildren().add(cellContents);
                quizzesTable.add(cell, j, i + 1);
                cellList.add(cell);
            }

            // create a button to view the quiz
            Button viewButton = new Button("view");
            viewButton.setMinHeight(35);
            viewButton.setMinWidth(50);
            viewButton.setFont(Font.font(14));
            // get the final value of i
            int finalI = i;
            // set the action for the view quiz button
            viewButton.setOnAction(e -> {
                try {
                    SwitchDashboard.switchDashboard(dashboardPane, buildQuestionsPane(quizRows.get(finalI).get(0)),
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
            editButton.setOnAction(e -> EditButtons.editQuiz(quizRows, finalI, cellList));
            // create a button to delete the quiz
            Button deleteButton = new Button("delete");
            deleteButton.setMaxHeight(35);
            deleteButton.setMinWidth(60);
            deleteButton.setFont(Font.font(14));
            // copy label
            deleteButton.setOnAction(e -> {
                int pos;
                try {
                    // actual delete the quiz
                    QuerySystem.deleteData("Quiz", "QuizID=".concat(quizRows.get(finalI).get(0)));
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
            // add the view button, edit button, and delete button to the quiz table
            quizzesTable.add(viewButton, quizzesColumnCount, i + 1);
            quizzesTable.add(editButton, quizzesColumnCount + 1, i + 1);
            quizzesTable.add(deleteButton, quizzesColumnCount + 2, i + 1);

        }
        ScrollPane sp = new ScrollPane(quizzesTable);
        sp.setPadding(new Insets(35,0,0,0));
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setFitToHeight(true);
        sp.setFitToWidth(true);
        sp.fitToHeightProperty();
        sp.fitToWidthProperty();
        sp.setContent(quizzesTable);

        // add quiz
        Button addQuizButton = new Button("Add Quiz");
        addQuizButton.setMinHeight(35);
        addQuizButton.setMinWidth(80);
        addQuizButton.setFont(Font.font(14));
        // open a new window to insert values
        addQuizButton.setOnAction(e -> EditButtons.addQuiz(dashboardPane, titlePane, cellList, quizzesTable, quizRows));
        addQuizButton.setId("addClassButton");
        // create a vertical box to hold the add quiz button
        VBox quizBox = new VBox();
        quizBox.setId("quizBox");
        quizBox.getChildren().add(addQuizButton);

        quizzesPane.getChildren().add(sp);
        // add the quiz table to the quiz box
        quizBox.getChildren().add(quizzesTable);
        quizzesPane.getChildren().add(quizBox);
        return quizzesPane;
    }

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
            editButton.setOnAction(e -> EditButtons.editQuestions(questionsRows, finalI, cellList));
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

        questionsPane.getChildren().add(sp);
        // add the questions table to the questions pane
        questionsPane.getChildren().add(questionsTable);
        return questionsPane;
    }
}
