package com.attendance;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.io.*;

public class attendanceApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        // rootPane:- Root pane of the application window.
        GridPane rootPane = new GridPane();
        rootPane.setId("rootPane");
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
        List<List<String>> quizRows = convertObjListToStrList(querySystem.selectQuery(new ArrayList<>(Arrays.asList("QuizID", "Quiz", "", "", "", ""))));
        List<String> quizColumnNames = new ArrayList<>(Arrays.asList("QuizID", "Password", "Start Time", "End Time"));
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
            Button deleteButton = new Button("delete");
            Button downloadButton = new Button("download");
            quizzesTable.add(viewButton, quizzesColumnCount, i + 1);
            quizzesTable.add(editButton, quizzesColumnCount + 1, i + 1);
            quizzesTable.add(deleteButton, quizzesColumnCount + 2, i + 1);
            quizzesTable.add(downloadButton, quizzesColumnCount + 3, i + 1);
        }
        quizzesPane.getChildren().add(quizzesTable);

        // passwordsPane:- Page containing a table of all password banks and buttons to upload/create password banks.
        Pane passwordsPane = new Pane();
        // *** BUILD PASSWORDS PANE HERE ***
        GridPane passwordsTable = new GridPane();
        passwordsTable.setId("passwordsTable");
        passwordsTable.setGridLinesVisible(true);
        List<List<String>> passwordsRows = convertObjListToStrList(querySystem.selectQuery(new ArrayList<>(Arrays.asList("Password_", "Password", "", "", "", ""))));
        List<String> passwordsColumnNames = new ArrayList<>(List.of("Password"));
        int passwordsColumnCount = passwordsColumnNames.size();
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
            Button deleteButton = new Button("delete");
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
        List<List<String>> classesRows = convertObjListToStrList(querySystem.selectQuery(new ArrayList<>(Arrays.asList("CourseID, ClassName, StartTime, EndTime", "Course", "", "", "", ""))));
        List<String> classesColumnNames = new ArrayList<>(Arrays.asList("Section", "Course", "Start Time", "End Time", "Days","Start Date", "End Date"));
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
            Button deleteButton = new Button("delete");
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
        scheduleQuizButton.setId("scheduleQuizButton");
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
        settingsButton.setId("settingsButton");
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
        applicationScene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm()); // retrieve application stylesheet
        stage.setTitle("Attendance App");
        stage.setScene(applicationScene);
        stage.show();
    }

    // method implementing functionality to parse student file and insert new students into student table
    // method implementing functionality to parse student file and insert new students into student table
    // Inside your studentsUploader() method
    private void studentsUploader(List<String> classesColumnNames) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select CSV File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );

        // Show open file dialog
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            // Parse the CSV file and populate the table
            parseCSV(selectedFile, classesColumnNames);
        }
    }
    private void parseCSV(File file, List<String> columnNames) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            List<String[]> data = new ArrayList<>();
            String line = "";
            boolean firstLine = true; // To skip the header line
            boolean secondLine = true; // Skip second line
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip the header line
                }
                if (secondLine) {
                    secondLine = false;
                    continue; // Skip the header line
                }
                // Split the line by tab
                data.add(line.split("\t",0));
            }
            String[] entry = data.get(0);
            //get the index of each info to display
            int firstNamePos = 0;
            int middleNamePos = 0;
            int lastNamePos = 0;
            int studentIdPos = 0;

            for (int i = 0 ; i < entry.length; i++) {
                if (entry[i].equals("EMPLID"))
                    studentIdPos = i;
                if (entry[i].equals("First Name"))
                    firstNamePos = i;
                if (entry[i].equals("Middle Name"))
                    middleNamePos = i;
                if (entry[i].equals("Last Name"))
                    lastNamePos = i;
            }
            //check get correct data insert in database we can return the pos and array
            for (String[] datum : data) {
                //print first name
                System.out.print(datum[firstNamePos] + " ");
                //print middle name
                System.out.print(datum[middleNamePos] + " ");
                //print last name
                System.out.print(datum[lastNamePos] + " ");
                //print studentID name
                System.out.print(datum[studentIdPos] + " \n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        List<List<String>> questionsRows = convertObjListToStrList(querySystem.selectQuery(new ArrayList<>(Arrays.asList("QuestionID, Question, AnswerSet, CorrectAnswer", "QuizQuestion", "QuestionID=".concat(quizID), "", "", ""))));
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
            Button editButton = new Button("edit");
            Button deleteButton = new Button("delete");
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
        List<String> classesColumnNames = Arrays.asList("FirstName", "MiddleName", "LastName", "StudentUTDID");

        Button uploadStudentsButton = new Button("Upload Students");
        uploadStudentsButton.setId("uploadStudentsButton");
        uploadStudentsButton.setOnAction(e -> {
            studentsUploader(classesColumnNames);
        });

        VBox studentsBox = new VBox();
        studentsBox.setId("studentsBox");
        studentsBox.getChildren().add(uploadStudentsButton);
        GridPane studentsTable = new GridPane();
        studentsTable.setId("studentsTable");
        studentsTable.setGridLinesVisible(true);
        List<List<String>> studentsRows = convertObjListToStrList(querySystem.selectQuery(new ArrayList<>(Arrays.asList("FirstName, MiddleName, LastName, Student.StudentNetID, Student.StudentUTDID", "Student JOIN Course ON Student.StudentUTDID=Course.StudentUTDID", "CourseID=".concat(courseID), "", "", ""))));
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
            Button viewButton = new Button("view");
            Button editButton = new Button("edit");
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

    // helper method to convert a two-dimensional arraylist of Objects to one containing Strings
    public List<List<String>> convertObjListToStrList(List<List<Object>> inputList) {
        List<List<String>> stringList = new ArrayList<List<String>>();
        for (List<Object> objects : inputList) {
            List<String> rowList = new ArrayList<>();
            for (Object object : objects)
                if (object != null)
                    rowList.add(object.toString());
                else
                    rowList.add("NULL"); // convert null objects to be displayed as "NULL"
            stringList.add(rowList);
        }
        return stringList;
    }

    public static void main(String[] args) {
        launch();
    }
}
