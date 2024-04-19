package com.attendance;

import javafx.application.Application;
//import javafx.stage.FileChooser;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
//import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import java.io.*;
import java.util.Objects;
//import javafx.scene.input.KeyCombination;


public class attendanceApplication extends Application {
    @Override
    public void start(Stage stage) throws /*IOException,*/ SQLException {
        // rootPane:- Root pane of the application window.
        GridPane rootPane = new GridPane();
        rootPane.setId("rootPane");
        rootPane.setMinWidth(500);
        rootPane.setMinHeight(500);
        RowConstraints rootRowConstraint = new RowConstraints();
        rootRowConstraint.setVgrow(Priority.ALWAYS);
        rootPane.getRowConstraints().add(rootRowConstraint);
        ColumnConstraints rootColumn1Constraint = new ColumnConstraints();
        rootColumn1Constraint.setHgrow(Priority.NEVER);
        ColumnConstraints rootColumn2Constraint = new ColumnConstraints();
        rootColumn2Constraint.setHgrow(Priority.ALWAYS);
        rootPane.getColumnConstraints().addAll(rootColumn1Constraint, rootColumn2Constraint);

        // menuPane:- Left partition of the application window.
        GridPane menuPane = new GridPane();
        menuPane.setId("menuPane");
        rootPane.add(menuPane, 0, 0);

        // windowPane:- Right partition of the application window.
        GridPane windowPane = new GridPane();
        windowPane.setId("windowPane");
        RowConstraints row2 = new RowConstraints();
        row2.setVgrow(Priority.ALWAYS);
        windowPane.getRowConstraints().addAll(new RowConstraints(), row2);
        ColumnConstraints windowColumnConstraint = new ColumnConstraints();
        windowColumnConstraint.setHgrow(Priority.ALWAYS);
        windowPane.getColumnConstraints().add(windowColumnConstraint);
        rootPane.add(windowPane,1 ,0);

        // titlePane:- Upper partition of windowPane.
        StackPane titlePane = new StackPane();
        titlePane.setId("titlePane");
        Label windowTitle = new Label("");
        windowTitle.setId("windowTitle");
        titlePane.getChildren().add(windowTitle);
        windowPane.add(titlePane, 0, 0);
        GridPane.setHalignment(titlePane, HPos.CENTER);

        // dashboardPane:- Lower partition of windowPane.
        Pane dashboardPane = new Pane();
        dashboardPane.setId("dashboardPane");
        windowPane.add(dashboardPane, 0, 1);

        // quizzesPane:- Page containing a table of all quizzes and buttons to upload/create quizzes.
        Pane quizzesPane = new Pane();

        // *** BUILD QUIZZES PANE HERE ***
        GridPane quizzesTable = new GridPane();
        quizzesTable.setId("quizzesTable");
        quizzesTable.setGridLinesVisible(true);
        List<List<String>> quizRows = converterObjToStr.convertObjListToStrList(querySystem.selectQuery(new ArrayList<>(Arrays.asList("QuizID, Password_, StartTime, Duration", "Quiz", "", "", "", ""))));
        List<String> quizColumnNames = new ArrayList<>(Arrays.asList("QuizID", "Password", "Start Time", "Duration"));
        StackPane cell;
        Label cellContents;
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
                    switchDashboard(dashboardPane, buildQuestionsPane(quizRows.get(finalI).get(0)), titlePane, "Questions");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });

            Button editButton = new Button("edit");
            // open a new window to insert values
            editButton.setOnAction(e -> {
                Stage editStage = new Stage();
                GridPane editPane = new GridPane();
                editPane.setId("editPane");
                editPane.setPadding(new Insets(10));
                editPane.setHgap(10);
                editPane.setVgap(10);
                Label quizIDLabel = new Label("QuizID:");
                TextField quizIDField = new TextField();
                quizIDField.setText(quizRows.get(finalI).get(0));
                Label passwordLabel = new Label("Password:");
                TextField passwordField = new TextField();
                passwordField.setText(quizRows.get(finalI).get(1));
                Label startTimeLabel = new Label("StartTime:");
                TextField startTimeField = new TextField();
                startTimeField.setText(quizRows.get(finalI).get(2));
                Label durationLabel = new Label("Duration:");
                TextField durationField = new TextField();
                durationField.setText(quizRows.get(finalI).get(3));
                Button saveButton = new Button("Save");
                saveButton.setOnAction(event -> {
                    try {
                        querySystem.updateData("Quiz", List.of("Password_", "StartTime", "Duration"), List.of(passwordField.getText(), startTimeField.getText(), durationField.getText()), "QuizID=".concat(quizIDField.getText()));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    editStage.close();
                });
                editPane.add(quizIDLabel, 0, 0);
                editPane.add(quizIDField, 1, 0);
                editPane.add(passwordLabel, 0, 1);
                editPane.add(passwordField, 1, 1);
                editPane.add(startTimeLabel, 0, 2);
                editPane.add(startTimeField, 1, 2);
                editPane.add(durationLabel, 0, 3);
                editPane.add(durationField, 1, 3);
                editPane.add(saveButton, 1, 4);
                Scene editScene = new Scene(editPane);
                editScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("Style.css")).toExternalForm());
                editStage.setScene(editScene);
                editStage.show();
            });
            Button deleteButton = new Button("delete");
            deleteButton.setOnAction(e -> {
                try {
                    querySystem.deleteData("Quiz", "QuizID=".concat(quizRows.get(finalI).get(0)));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            Button downloadButton = new Button("download"); //maybe not need
            quizzesTable.add(viewButton, quizzesColumnCount, i + 1);
            quizzesTable.add(editButton, quizzesColumnCount + 1, i + 1);
            quizzesTable.add(deleteButton, quizzesColumnCount + 2, i + 1); //maybe not need
            quizzesTable.add(downloadButton, quizzesColumnCount + 3, i + 1); //maybe not need

        }
        quizzesPane.getChildren().add(quizzesTable);

        // passwordsPane:- Page containing a table of all password banks and buttons to upload/create password banks.
        Pane passwordsPane = new Pane();
        // *** BUILD PASSWORDS PANE HERE ***
        GridPane passwordsTable = new GridPane();
        passwordsTable.setId("passwordsTable");
        passwordsTable.setGridLinesVisible(true);
        List<List<String>> passwordsRows = converterObjToStr.convertObjListToStrList(querySystem.selectQuery(new ArrayList<>(Arrays.asList("Password_, QuizID", "Quiz", "", "", "", ""))));
        List<String> passwordsColumnNames = new ArrayList<>(Arrays.asList("Password","QuizID"));
        int passwordsColumnCount = passwordsColumnNames.size();
        //create the grid for password
        for (int i = 0; i < passwordsColumnCount; i++) {
            cellContents = new Label(passwordsColumnNames.get(i));
            cell = new StackPane();
            cell.setPadding(new Insets(5));
            cell.getChildren().add(cellContents);
            passwordsTable.add(cell, i, 0);
        }
        for (int i = 0; i < passwordsRows.size(); i++) {
            for (int j = 0; j < passwordsRows.get(i).size(); j++) {
                cellContents = new Label(passwordsRows.get(i).get(j));
                cell = new StackPane();
                cell.setPadding(new Insets(5));
                cell.getChildren().add(cellContents);
                passwordsTable.add(cell, j, i + 1);
            }

            Button editButton = new Button("edit");
            int finalI = i;
            editButton.setOnAction(e -> {
                // create a new window to insert values
                Stage editStage = new Stage();
                GridPane editPane = new GridPane();
                editPane.setId("editPane");
                editPane.setPadding(new Insets(10));
                editPane.setHgap(10);
                editPane.setVgap(10);
                Label passwordLabel = new Label("Password:");
                TextField passwordField = new TextField();
                passwordField.setText(passwordsRows.get(finalI).get(0));
                Label quizIDLabel = new Label("QuizID:");
                TextField quizIDField = new TextField();
                quizIDField.setText(passwordsRows.get(finalI).get(1));
                Button saveButton = new Button("Save");
                saveButton.setOnAction(event -> {
                    try {
                        querySystem.updateData("Quiz", List.of("Password_"), List.of(passwordField.getText()), "QuizID=".concat(quizIDField.getText()));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    editStage.close();
                });
                editPane.add(passwordLabel, 0, 0);
                editPane.add(passwordField, 1, 0);
                editPane.add(quizIDLabel, 0, 1);
                editPane.add(quizIDField, 1, 1);
                editPane.add(saveButton, 1, 2);
                Scene editScene = new Scene(editPane);
                editScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("Style.css")).toExternalForm());
                editStage.setScene(editScene);
                editStage.show();
            });

            Button deleteButton = new Button("delete");

            //need to refresh page
            deleteButton.setOnAction(e -> {
                try {
                    //not actually delete the password but set it to null so there is no psw for that quizID
                    querySystem.updateData("Quiz", List.of("Password_"), List.of("") ,"QuizID=".concat(passwordsRows.get(finalI).get(0)));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            Button copyButton = new Button("copy");
            passwordsTable.add(editButton, passwordsColumnCount, i + 1);
            passwordsTable.add(deleteButton, passwordsColumnCount + 1, i + 1);
            passwordsTable.add(copyButton, passwordsColumnCount + 2, i + 1);
        }
        passwordsPane.getChildren().add(passwordsTable);

        // classesPane:- Page containing a table of all classes and buttons to upload/create classes.
        Pane classesPane = new Pane();
        // *** BUILD CLASSES PANE HERE ***
        GridPane classesTable = new GridPane();
        classesTable.setId("classesTable");
        classesTable.setGridLinesVisible(true);
        List<List<String>> classesRows = converterObjToStr.convertObjListToStrList(querySystem.selectQuery(new ArrayList<>(Arrays.asList("CourseID, ClassName, StartTime, EndTime, StartDate, EndDate", "Course", "", "", "", ""))));
        List<String> classesColumnNames = new ArrayList<>(Arrays.asList("Section", "Course", "Start Time", "End Time","Start Date", "End Date")); //removed days
        int classesColumnCount = classesColumnNames.size();
        for (int i = 0; i < classesColumnCount; i++) {
            cellContents = new Label(classesColumnNames.get(i));
            cell = new StackPane();
            cell.setPadding(new Insets(5));
            cell.getChildren().add(cellContents);
            classesTable.add(cell, i, 0);
        }
        for (int i = 0; i < classesRows.size(); i++) {
            for (int j = 0; j < classesRows.get(i).size(); j++) {
                cellContents = new Label(classesRows.get(i).get(j));
                cell = new StackPane();
                cell.setPadding(new Insets(5));
                cell.getChildren().add(cellContents);
                classesTable.add(cell, j, i + 1);
            }
            Button viewButton = new Button("view");
            int finalI = i;
            viewButton.setOnAction(e -> {
                try {
                    switchDashboard(dashboardPane, buildStudentsPane(classesRows.get(finalI).get(0)), titlePane, "Attendance");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });

            Button editButton = new Button("edit");
            editButton.setOnAction(e -> {
                // create a new window to insert values
                Stage editStage = new Stage();
                GridPane editPane = new GridPane();
                editPane.setId("editPane");
                editPane.setPadding(new Insets(10));
                editPane.setHgap(10);
                editPane.setVgap(10);
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
                        querySystem.updateData("Course", List.of("ClassName", "StartTime", "EndTime", "StartDate", "EndDate"), List.of(classNameField.getText(), startTimeField.getText(), endTimeField.getText(), startDateField.getText(), endDateField.getText()), "CourseID=".concat(courseIDField.getText()));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    editStage.close();
                });
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
                editScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("Style.css")).toExternalForm());
                editStage.setScene(editScene);
                editStage.show();

            });
            Button deleteButton = new Button("delete");
            deleteButton.setOnAction(e -> {
                try {
                    querySystem.deleteData("Course", "CourseID=".concat(classesRows.get(finalI).get(0)));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            Button downloadButton = new Button("download");
            classesTable.add(viewButton, classesColumnCount, i + 1);
            classesTable.add(editButton, classesColumnCount + 1, i + 1);
            classesTable.add(deleteButton, classesColumnCount + 2, i + 1);
            classesTable.add(downloadButton, classesColumnCount + 3, i + 1);
        }
        classesPane.getChildren().add(classesTable);

        // attendancePane:- Page containing all attendance information for a particular class and buttons to upload/create/download attendance.
        // -accessible from the classes page table
        Pane attendancePane = new Pane();
        // *** BUILD ATTENDANCE PANE HERE ***
        Label attendancePlaceholderContent = new Label("<Attendance Placeholder Content>");
        attendancePane.getChildren().add(attendancePlaceholderContent);

        // databaseInfoPane:- Page containing all database info: location, name, and login info.
        Pane databaseInfoPane = new Pane();
        // *** BUILD DATABASE INFO PANE HERE ***
        Label databaseInfo = new Label(
                "Location/URL:   " + connectionDB.getDBURL() +
                        "\nName:               " + connectionDB.getDBConnection().getCatalog() +
                        "\nUsername:        " + connectionDB.getDBUsername() +
                        "\nPassword:         " + connectionDB.getDBPassword());
        databaseInfo.setId("databaseInfo");
        databaseInfo.setPadding(new Insets(25));
        databaseInfoPane.getChildren().add(databaseInfo);

        // settingsPane:- Page containing settings of the attendance application.
        Pane settingsPane = new Pane();
        // *** BUILD SETTINGS PANE HERE ***
        Label settingsPlaceholderContent = new Label("<Settings Content Placeholder>");
        settingsPlaceholderContent.setPadding(new Insets(50));
        settingsPane.getChildren().add(settingsPlaceholderContent);

        // create menu buttons:
        Button scheduleQuizButton = new Button("Schedule Quiz");
        scheduleQuizButton.setId("startQuizButton");
        Button quizzesButton = new Button("Quizzes");
        quizzesButton.setId("quizzesButton");
        quizzesButton.setOnAction(e -> switchDashboard(dashboardPane, quizzesPane, titlePane, "Quizzes"));
        Button passwordsButton = new Button("Passwords");
        passwordsButton.setId("passwordsButton");
        passwordsButton.setOnAction(e -> switchDashboard(dashboardPane, passwordsPane, titlePane, "Passwords"));
        Button classesButton = new Button("Classes");
        classesButton.setId("classesButton");
        classesButton.setOnAction(e -> switchDashboard(dashboardPane, classesPane, titlePane, "Classes"));
        Button databaseInfoButton = new Button("Database Info");
        databaseInfoButton.setId("databaseInfoButton");
        databaseInfoButton.setOnAction(e -> switchDashboard(dashboardPane, databaseInfoPane, titlePane, "Database Info"));
        // settingsSpacingPane:- Pane used to space settings button on bottom of menuPane.
        StackPane settingsSpacingPane = new StackPane();
        settingsSpacingPane.setId("settingsSpacingPane");
        Button settingsButton = new Button("Settings");
        settingsButton.setId("settingsPane");
        settingsButton.setOnAction(e -> switchDashboard(dashboardPane, settingsPane, titlePane, "Settings"));
        settingsSpacingPane.getChildren().add(settingsButton);
        // set alignment of menu buttons:
        GridPane.setHalignment(scheduleQuizButton, HPos.CENTER);
        GridPane.setHalignment(quizzesButton, HPos.CENTER);
        GridPane.setHalignment(passwordsButton, HPos.CENTER);
        GridPane.setHalignment(classesButton, HPos.CENTER);
        GridPane.setHalignment(databaseInfoButton, HPos.CENTER);
        GridPane.setHalignment(settingsButton, HPos.CENTER);
        settingsSpacingPane.setAlignment(Pos.BOTTOM_CENTER);
        // add menu buttons to menuPane:
        menuPane.add(scheduleQuizButton, 0, 0);
        menuPane.setVgap(10);
        menuPane.add(quizzesButton, 0, 1);
        menuPane.add(passwordsButton, 0, 2);
        menuPane.add(classesButton, 0, 3);
        menuPane.add(databaseInfoButton, 0, 4);
        menuPane.add(settingsSpacingPane, 0, 5);
        RowConstraints settingsSpacingConstraint = new RowConstraints();
        RowConstraints nullConstraint = new RowConstraints();
        settingsSpacingConstraint.setVgrow(Priority.ALWAYS);
        menuPane.getRowConstraints().addAll(nullConstraint, nullConstraint, nullConstraint, nullConstraint, nullConstraint, settingsSpacingConstraint);

        // set default dashboard to quizzes page
        switchDashboard(dashboardPane, quizzesPane, titlePane, "Quizzes");

        // create the application scene and set the scene/stage:
        // applicationScene:- Scene containing all main functionality of attendance application.
        Scene applicationScene = new Scene(rootPane);
        applicationScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("Style.css")).toExternalForm()); // retrieve application stylesheet
        stage.setTitle("Attendance App");
        stage.setScene(applicationScene);
        stage.setWidth(1200);
        stage.setHeight(1000);
        stage.show();
    }

    // method used to switch dashboard pane and title pane based on what menu button is pressed
    public void switchDashboard(Pane dashboardPane, Pane targetPane, Pane titlePane, String newTitle) {
        dashboardPane.getChildren().clear();
        dashboardPane.getChildren().add(targetPane);
        ((Label)titlePane.getChildren().get(0)).setText(newTitle);
    }

    // helper method to build pane containing questions table when view button is clicked for particular quiz
    public Pane buildQuestionsPane(String quizID) throws SQLException {
        // questionsPane:- Page containing a table of all questions for a particular quiz and buttons to upload/create questions.
        // -accessible from the quizzes page table
        Pane questionsPane = new Pane();
        // *** BUILD QUESTIONS PANE HERE ***
        GridPane questionsTable = new GridPane();
        questionsTable.setId("quizzesTable");
        questionsTable.setGridLinesVisible(true);
        List<List<String>> questionsRows = converterObjToStr.convertObjListToStrList(querySystem.selectQuery(new ArrayList<>(Arrays.asList("QuestionID, Question, AnswerSet, CorrectAnswer", "QuizQuestion", "QuestionID=".concat(quizID), "", "", ""))));
        List<String> questionsColumnNames = new ArrayList<>(Arrays.asList("Question Number", "Question", "Answer Choices", "Correct Answer"));
        StackPane cell;
        Label cellContents;
        int questionsColumnCount = questionsColumnNames.size();
        for (int i = 0; i < questionsColumnCount; i++) {
            cellContents = new Label(questionsColumnNames.get(i));
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
            editButton.setOnAction(e -> {
                Stage editStage = new Stage();
                GridPane editPane = new GridPane();
                editPane.setId("editPane");
                editPane.setPadding(new Insets(10));
                editPane.setHgap(10);
                editPane.setVgap(10);
                Label questionIDLabel = new Label("QuestionID:");
                TextField questionIDField = new TextField();
                questionIDField.setText(questionsRows.get(finalI).get(0));
                Label questionLabel = new Label("Question:");
                TextField questionField = new TextField();
                questionField.setText(questionsRows.get(finalI).get(1));
                Label answerSetLabel = new Label("AnswerSet:");
                TextField answerSetField = new TextField();
                answerSetField.setText(questionsRows.get(finalI).get(2));
                Label correctAnswerLabel = new Label("CorrectAnswer:");
                TextField correctAnswerField = new TextField();
                correctAnswerField.setText(questionsRows.get(finalI).get(3));
                Button saveButton = new Button("Save");
                saveButton.setOnAction(event -> {
                    try {
                        querySystem.updateData("QuizQuestion", List.of("Question", "AnswerSet", "CorrectAnswer"), List.of(questionField.getText(), answerSetField.getText(), correctAnswerField.getText()), "QuestionID=".concat(questionIDField.getText()));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    editStage.close();
                });
                editPane.add(questionIDLabel, 0, 0);
                editPane.add(questionIDField, 1, 0);
                editPane.add(questionLabel, 0, 1);
                editPane.add(questionField, 1, 1);
                editPane.add(answerSetLabel, 0, 2);
                editPane.add(answerSetField, 1, 2);
                editPane.add(correctAnswerLabel, 0, 3);
                editPane.add(correctAnswerField, 1, 3);
                editPane.add(saveButton, 1, 4);
                Scene editScene = new Scene(editPane);
                editScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("Style.css")).toExternalForm());
                editStage.setScene(editScene);
                editStage.show();
            });

            Button deleteButton = new Button("delete");
            deleteButton.setOnAction(e -> {
                try {
                    //actual delete the quiz
                    querySystem.deleteData("QuizQuestion", "QuestionID=".concat(questionsRows.get(finalI).get(0)));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            Button downloadButton = new Button("download");
            questionsTable.add(editButton, questionsColumnCount, i + 1);
            questionsTable.add(deleteButton, questionsColumnCount + 1, i + 1);
            questionsTable.add(downloadButton, questionsColumnCount + 2, i + 1);
        }
        questionsPane.getChildren().add(questionsTable);
        return questionsPane;
    }

    // helper method to build pane containing students table when view button is clicked for particular class
    public Pane buildStudentsPane(String courseID) throws SQLException {
        // questionsPane:- Page containing a table of all questions for a particular quiz and buttons to upload/create questions.
        // -accessible from the quizzes page table
        Pane studentsPane = new Pane();
        // *** BUILD QUESTIONS PANE HERE ***
        List<String> classesColumnNames = Arrays.asList("FirstName", "MiddleName", "LastName", "StudentUTDID", "StudentNetID");

        Button uploadStudentsButton = new Button("Upload Students");
        uploadStudentsButton.setId("uploadStudentsButton");
        uploadStudentsButton.setOnAction(e -> parser.studentsUploader(classesColumnNames));

        VBox studentsBox = new VBox();
        studentsBox.setId("studentsBox");
        studentsBox.getChildren().add(uploadStudentsButton);
        GridPane studentsTable = new GridPane();
        studentsTable.setId("studentsTable");
        studentsTable.setGridLinesVisible(true);
        List<List<String>> studentsRows = converterObjToStr.convertObjListToStrList(querySystem.selectQuery(new ArrayList<>(Arrays.asList("FirstName, MiddleName, LastName, Student.StudentNetID, Student.StudentUTDID", "Attendance JOIN Student ON Student.StudentUTDID=Attendance.StudentUTDID", "CourseID=".concat(courseID), "", "", ""))));
        List<String> studentsColumnNames = new ArrayList<>(Arrays.asList("First Name", "Middle Name", "Last Name", "NET-ID","UTD-ID", "<Attendance Columns Placeholder>"));
        StackPane cell;
        Label cellContents;
        int questionsColumnCount = studentsColumnNames.size();
        for (int i = 0; i < questionsColumnCount; i++) {
            cellContents = new Label(studentsColumnNames.get(i));
            cell = new StackPane();
            cell.setPadding(new Insets(5));
            cell.getChildren().add(cellContents);
            studentsTable.add(cell, i, 0);
        }
        for (int i = 0; i < studentsRows.size(); i++) {
            for (int j = 0; j < studentsRows.get(i).size(); j++) {
                cellContents = new Label(studentsRows.get(i).get(j));
                cell = new StackPane();
                cell.setPadding(new Insets(5));
                cell.getChildren().add(cellContents);
                studentsTable.add(cell, j, i + 1);
            }
            int finalI = i;
            Button viewButton = new Button("view");
            Button editButton = new Button("edit");

            // open a new window to insert values
            editButton.setOnAction(e -> {
                Stage editStage = new Stage();
                GridPane editPane = new GridPane();
                editPane.setId("editPane");
                editPane.setPadding(new Insets(10));
                editPane.setHgap(10);
                editPane.setVgap(10);
                Label firstNameLabel = new Label("First Name:");
                TextField firstNameField = new TextField();
                firstNameField.setText(studentsRows.get(finalI).get(0));
                Label middleNameLabel = new Label("middle Name:");
                TextField middleNameField = new TextField();
                middleNameField.setText(studentsRows.get(finalI).get(1));
                Label lastNameLabel = new Label("Last Name:");
                TextField lastNameField = new TextField();
                lastNameField.setText(studentsRows.get(finalI).get(2));
                Label studentNetIDLabel = new Label("StudentNetID:");
                TextField studentNetIDField = new TextField();
                studentNetIDField.setText(studentsRows.get(finalI).get(3));
                Label studentUTDIDLabel = new Label("StudentUTDID:");
                TextField studentUTDIDField = new TextField();
                studentUTDIDField.setText(studentsRows.get(finalI).get(4));
                Button saveButton = new Button("Save");
                saveButton.setOnAction(event -> {
                    try {
                        querySystem.updateData("Student", List.of("FirstName", "MiddleName", "LastName", "StudentNetID", "StudentUTDID"), List.of(firstNameField.getText(), middleNameField.getText(), lastNameField.getText(), studentNetIDField.getText(), studentUTDIDField.getText()), "StudentUTDID=".concat(studentUTDIDField.getText()));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    editStage.close();
                });
                editPane.add(firstNameLabel, 0, 0);
                editPane.add(firstNameField, 1, 0);
                editPane.add(middleNameLabel, 0, 1);
                editPane.add(middleNameField, 1, 1);
                editPane.add(lastNameLabel, 0, 2);
                editPane.add(lastNameField, 1, 2);
                editPane.add(studentNetIDLabel, 0, 3);
                editPane.add(studentNetIDField, 1, 3);
                editPane.add(studentUTDIDLabel, 0, 4);
                editPane.add(studentUTDIDField, 1, 4);
                editPane.add(saveButton, 1, 5);
                Scene editScene = new Scene(editPane);
                editScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("Style.css")).toExternalForm());
                editStage.setScene(editScene);
                editStage.show();
            });
            Button deleteButton = new Button("delete");
            Button downloadButton = new Button("download");
            studentsTable.add(viewButton, questionsColumnCount, i + 1);
            studentsTable.add(editButton, questionsColumnCount + 1, i + 1);
            studentsTable.add(deleteButton, questionsColumnCount + 2, i + 1);
            studentsTable.add(downloadButton, questionsColumnCount + 3, i + 1);
        }
        studentsBox.getChildren().add(studentsTable);
        studentsPane.getChildren().add(studentsBox);
        return studentsPane;
    }
    public static void main(String[] args) {
        launch();
    }
}
