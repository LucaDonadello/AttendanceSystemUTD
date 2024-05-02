/******************************************************************************
 * EditButtons.java
 * This class is used to create the edit buttons for the different tables in the database.
 * The edit buttons allow the user to edit the data in the tables.
 * The class contains methods to create the edit buttons for quizzes, passwords, classes,
 * questions, students, and attendance.
 * The class also contains methods to add classes and quizzes to the database.
 * Written by Luca Donadello for CS4485.0W1 , Project Attendance System,
 * starting >>>><<<<, 2024 NetID: lxd210013
 * ******************************************************************************/

package com.attendance;

import com.attendance.database.ConnectionDB;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.attendance.panes.QuizPane.buildQuestionsPane;

public class EditButtons {

    // edit for quiz
    // create a new window to insert values
    public static void editQuiz(List<List<String>> quizRows, int finalI, List<StackPane> cellList) {
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
                // send query
                QuerySystem.updateData("Quiz", List.of("Password_", "StartTime", "Duration"),
                        List.of(passwordField.getText(), startTimeField.getText(), durationField.getText()),
                        "QuizID=".concat(quizIDField.getText()));
                // edit the UI
                // save new values
                int finalPos = finalI * (quizRows.size() + 1);
                // save new values in quizRowsNew
                List<String> quizRowsNew;
                // save new values in quizRowsNew
                quizRowsNew = List.of(quizIDField.getText(), passwordField.getText(), startTimeField.getText(),
                        durationField.getText());
                // update the UI with the new values in quizRowsNew
                for (int i = 0; i < quizRowsNew.size(); i++, finalPos++) {
                    Label cellContents = new Label(quizRowsNew.get(i));
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
        editScene.getStylesheets()
                .add(Objects.requireNonNull(EditButtons.class.getResource("Style.css")).toExternalForm());
        editStage.setScene(editScene);
        editStage.show();
    }

    // edit for password
    // create a new window to insert values
    public static void editPassword(List<List<String>> passwordsRows, int finalI, List<StackPane> cellList) {
        // create a new window to insert values
        Stage editStage = new Stage();
        // create a new grid pane
        GridPane editPane = new GridPane();
        editPane.setId("editPane");
        editPane.setPadding(new Insets(10));
        editPane.setHgap(10);
        editPane.setVgap(10);
        // create labels and text fields for each column
        Label passwordLabel = new Label("Password:");
        TextField passwordField = new TextField();
        passwordField.setText(passwordsRows.get(finalI).get(0));
        Label quizIDLabel = new Label("QuizID:");
        TextField quizIDField = new TextField();
        quizIDField.setText(passwordsRows.get(finalI).get(1));
        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            try {
                QuerySystem.updateData("Quiz", List.of("Password_"), List.of(passwordField.getText()),
                        "QuizID=".concat(quizIDField.getText()));
                // edit the UI
                // save new values in passwordRowsNew
                int finalPos = finalI * 2;
                // save new values in passwordRowsNew
                List<List<String>> passwordsRowsNew = new ArrayList<>();
                // save new values in passwordRowsNew
                passwordsRowsNew.add(List.of(passwordField.getText(), quizIDField.getText()));
                // update the UI with the new values in passwordRowsNew
                for (int i = 0; i <= passwordsRowsNew.size(); i++, finalPos++) {
                    Label cellContents = new Label(passwordsRowsNew.get(0).get(i));
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
        editPane.add(passwordLabel, 0, 0);
        editPane.add(passwordField, 1, 0);
        editPane.add(quizIDLabel, 0, 1);
        editPane.add(quizIDField, 1, 1);
        editPane.add(saveButton, 1, 2);
        Scene editScene = new Scene(editPane);
        editScene.getStylesheets()
                .add(Objects.requireNonNull(EditButtons.class.getResource("Style.css")).toExternalForm());
        editStage.setScene(editScene);
        editStage.show();
    }

    // edit for classes
    // create a new window to insert values
    public static void editClasses(List<List<String>> classesRows, int finalI, List<StackPane> cellList) {
        // create a new window to insert values
        Stage editStage = new Stage();
        // create a new grid pane
        GridPane editPane = new GridPane();
        editPane.setId("editPane");
        editPane.setPadding(new Insets(10));
        editPane.setHgap(10);
        editPane.setVgap(10);
        // create labels and text fields for each column
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
                QuerySystem.updateData("Course", List.of("ClassName", "StartTime", "EndTime", "StartDate", "EndDate"),
                        List.of(classNameField.getText(), startTimeField.getText(), endTimeField.getText(),
                                startDateField.getText(), endDateField.getText()),
                        "CourseID=".concat(courseIDField.getText()));
                // edit the UI
                // save new values
                int finalPos = finalI * (classesRows.size() + 3);
                // save new values in classesRowsNew
                List<String> classesRowNew;
                // save new values in classesRowsNew
                classesRowNew = List.of(courseIDField.getText(), classNameField.getText(), startTimeField.getText(),
                        endTimeField.getText(), startDateField.getText(), endDateField.getText());
                // update the UI with the new values in classesRowsNew
                for (int i = 0; i < classesRowNew.size(); i++, finalPos++) {
                    Label cellContents = new Label(classesRowNew.get(i));
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
        editScene.getStylesheets()
                .add(Objects.requireNonNull(EditButtons.class.getResource("Style.css")).toExternalForm());
        editStage.setScene(editScene);
        editStage.show();
    }

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
                    System.out.println(questionRowsNew.size());
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
                .add(Objects.requireNonNull(EditButtons.class.getResource("Style.css")).toExternalForm());
        editStage.setScene(editScene);
        editStage.show();
    }

    // edit for students
    // create a new window to insert values
    public static void editStudent(List<List<String>> studentsRows, int finalI, List<StackPane> cellList) {
        // create a new window to insert values
        Stage editStage = new Stage();
        // create a new grid pane
        GridPane editPane = new GridPane();
        editPane.setId("editPane");
        editPane.setPadding(new Insets(10));
        editPane.setHgap(10);
        editPane.setVgap(10);
        // create labels and text fields for each column
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
                QuerySystem.updateData("Student",
                        List.of("FirstName", "MiddleName", "LastName", "StudentNetID", "StudentUTDID"),
                        List.of(firstNameField.getText(), middleNameField.getText(), lastNameField.getText(),
                                studentNetIDField.getText(), studentUTDIDField.getText()),
                        "StudentUTDID=".concat(studentUTDIDField.getText()));
                // edit the UI
                // save new values
                int finalPos = finalI * (studentsRows.size() + 2);
                // save new values in studentRowsNew
                List<String> studentRowsNew;
                // save new values in studentRowsNew
                studentRowsNew = List.of(firstNameField.getText(), middleNameField.getText(), lastNameField.getText(),
                        studentNetIDField.getText(), studentUTDIDField.getText());
                // update the UI with the new values in studentRowsNew
                for (int i = 0; i < studentRowsNew.size(); i++, finalPos++) {
                    Label cellContents = new Label(studentRowsNew.get(i));
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
        editScene.getStylesheets()
                .add(Objects.requireNonNull(EditButtons.class.getResource("Style.css")).toExternalForm());
        editStage.setScene(editScene);
        editStage.show();
    }

    // edit for attendance
    // create a new window to insert values
    public static void editAttendance(List<List<String>> attendanceRows, int finalI, List<StackPane> cellList) {
        // create a new window to insert values
        Stage editStage = new Stage();
        // create a new grid pane
        GridPane editPane = new GridPane();
        editPane.setId("editPane");
        editPane.setPadding(new Insets(10));
        editPane.setHgap(10);
        editPane.setVgap(10);
        // create labels and text fields for each column
        Label attendedLabel = new Label("Attended:");
        TextField attendedField = new TextField();
        attendedField.setText(attendanceRows.get(finalI).get(0));
        Label dateAndTimeLabel = new Label("DateAndTime:");
        TextField dateAndTimeField = new TextField();
        dateAndTimeField.setText(attendanceRows.get(finalI).get(1));
        Label ipLabel = new Label("IPAddress:");
        TextField ipField = new TextField();
        ipField.setText(attendanceRows.get(finalI).get(2));
        Label macLabel = new Label("MACID:");
        TextField macField = new TextField();
        macField.setText(attendanceRows.get(finalI).get(3));
        Label StudentIDLabel = new Label("StudentUTDID:");
        TextField StudentIDField = new TextField();
        StudentIDField.setText(attendanceRows.get(finalI).get(4));
        Label CourseIDLabel = new Label("CourseID:");
        TextField CourseIDField = new TextField();
        CourseIDField.setText(attendanceRows.get(finalI).get(5));
        Button saveButton = new Button("Save");
        // save the new values in the database
        // values are stored as 1 for true and 0 for false
        String attendedTrue = "1";
        String attendedFalse = "0";
        saveButton.setOnAction(event -> {
            try {
                // send query to update the database based on the new values
                if (attendedField.getText().equals("True") || attendedField.getText().equals("true")
                        || attendedField.getText().equals("1"))
                    QuerySystem.updateData("AttendanceInfo",
                            List.of("Attended", "DateAndTime", "IPAddress", "MACID", "StudentUTDID", "CourseID"),
                            List.of(attendedTrue, dateAndTimeField.getText(), ipField.getText(), macField.getText(),
                                    StudentIDField.getText(), CourseIDField.getText()),
                            "StudentUTDID=".concat(StudentIDField.getText()));
                else
                    QuerySystem.updateData("AttendanceInfo",
                            List.of("Attended", "DateAndTime", "IPAddress", "MACID", "StudentUTDID", "CourseID"),
                            List.of(attendedFalse, dateAndTimeField.getText(), ipField.getText(), macField.getText(),
                                    StudentIDField.getText(), CourseIDField.getText()),
                            "StudentUTDID=".concat(StudentIDField.getText()));

                // edit the UI
                // save new values
                int finalPos = finalI * (attendanceRows.size() + 4);
                // save new values in attendanceRowsNew
                List<String> attendanceRowsNew;
                // save new values in attendanceRowsNew
                attendanceRowsNew = List.of(attendedField.getText(), dateAndTimeField.getText(), ipField.getText(),
                        macField.getText(), StudentIDField.getText());
                // update the UI with the new values in attendanceRowsNew
                for (int i = 0; i < attendanceRowsNew.size(); i++, finalPos++) {
                    Label cellContents = new Label(attendanceRowsNew.get(i));
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
        editPane.add(attendedLabel, 0, 0);
        editPane.add(attendedField, 1, 0);
        editPane.add(dateAndTimeLabel, 0, 1);
        editPane.add(dateAndTimeField, 1, 1);
        editPane.add(ipLabel, 0, 2);
        editPane.add(ipField, 1, 2);
        editPane.add(macLabel, 0, 3);
        editPane.add(macField, 1, 3);
        editPane.add(StudentIDLabel, 0, 4);
        editPane.add(StudentIDField, 1, 4);
        editPane.add(CourseIDLabel, 0, 5);
        editPane.add(CourseIDField, 1, 5);
        editPane.add(saveButton, 1, 6);
        Scene editScene = new Scene(editPane);
        editScene.getStylesheets()
                .add(Objects.requireNonNull(EditButtons.class.getResource("Style.css")).toExternalForm());
        editStage.setScene(editScene);
        editStage.show();
    }

    // edit database info
    // create a new window to insert values
    public static void editDatabaseInfo() {
        // create a new window to insert values
        Stage editStage = new Stage();
        // create a new grid pane
        GridPane editPane = new GridPane();
        editPane.setId("editPane");
        editPane.setPadding(new Insets(10));
        editPane.setHgap(10);
        editPane.setVgap(10);
        // create labels and text fields for each column
        Label urlLabel = new Label("URL:");
        TextField urlField = new TextField();
        urlField.setText(ConnectionDB.getDBURL());
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        try {
            // get the database name
            nameField.setText(ConnectionDB.getDBConnection().getCatalog());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // get the database username password and set the text fields to the values
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setText(ConnectionDB.getDBUsername());
        Label passwordLabel = new Label("Password:");
        TextField passwordField = new TextField();
        passwordField.setText(ConnectionDB.getDBPassword());
        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            // save the new values in the database
            ConnectionDB.setDBURL(urlField.getText());
            ConnectionDB.setDBUsername(usernameField.getText());
            ConnectionDB.setDBPassword(passwordField.getText());

            editStage.close();
        });
        // add the labels and text fields to the grid pane
        editPane.add(urlLabel, 0, 0);
        editPane.add(urlField, 1, 0);
        editPane.add(nameLabel, 0, 1);
        editPane.add(nameField, 1, 1);
        editPane.add(usernameLabel, 0, 2);
        editPane.add(usernameField, 1, 2);
        editPane.add(passwordLabel, 0, 3);
        editPane.add(passwordField, 1, 3);
        editPane.add(saveButton, 1, 4);
        Scene editScene = new Scene(editPane);
        editScene.getStylesheets()
                .add(Objects.requireNonNull(EditButtons.class.getResource("Style.css")).toExternalForm());
        editStage.setScene(editScene);
        editStage.show();
    }

    // add class
    // create a new window to insert values
    public static void addClass(Pane dashboardPane, Pane titlePane, List<StackPane> cellList, GridPane classesTable,
            List<List<String>> classesRows) {
        // create a new window to insert values
        Stage editStage = new Stage();
        // create a new grid pane
        GridPane editPane = new GridPane();
        editPane.setId("editPane");
        editPane.setPadding(new Insets(10));
        editPane.setHgap(10);
        editPane.setVgap(10);
        // create labels and text fields for each column
        Label courseIDLabel = new Label("CourseID:");
        TextField courseIDField = new TextField();
        courseIDField.setText("");
        Label classNameLabel = new Label("ClassName:");
        TextField classNameField = new TextField();
        classNameField.setText("");
        Label startTimeLabel = new Label("StartTime:");
        TextField startTimeField = new TextField();
        startTimeField.setText("");
        Label endTimeLabel = new Label("EndTime:");
        TextField endTimeField = new TextField();
        endTimeField.setText("");
        Label startDateLabel = new Label("StartDate:");
        TextField startDateField = new TextField();
        startDateField.setText("");
        Label endDateLabel = new Label("EndDate:");
        TextField endDateField = new TextField();
        endDateField.setText("");
        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            try {
                // send query to insert the new values in the database
                QuerySystem.insertData("Course",
                        List.of("CourseID", "ClassName", "StartTime", "EndTime", "StartDate", "EndDate"),
                        List.of(courseIDField.getText(), classNameField.getText(), startTimeField.getText(),
                                endTimeField.getText(), startDateField.getText(), endDateField.getText()));
                // edit the UI
                // save new values
                int finalPos = classesTable.getRowCount();
                // save new values in classesRowsNew
                List<String> classesRowsNew;
                // save new values in classesRowsNew
                classesRowsNew = List.of(courseIDField.getText(), classNameField.getText(), startTimeField.getText(),
                        endTimeField.getText(), startDateField.getText(), endDateField.getText());
                int finalI = 0;
                // update the UI with the new values in classesRowsNew
                for (int i = 0; i < classesRowsNew.size(); i++) {
                    Label cellContents = new Label(classesRowsNew.get(i));
                    cellContents.setFont(Font.font("Arial", 14));
                    StackPane cellNew = new StackPane();
                    cellNew.setPadding(new Insets(5));
                    cellNew.getChildren().add(cellContents);
                    classesTable.add(cellNew, i, finalPos);
                    cellList.add(cellNew);
                    finalI = i;
                }
                // create a button to view the class
                Button viewButton = new Button("view");
                viewButton.setMinHeight(35);
                viewButton.setMinWidth(50);
                viewButton.setFont(Font.font(14));
                // set the action for the view class button
                int finalI1 = finalPos-1;
                System.out.println(finalI1);
                viewButton.setOnAction(e -> {
                    try {
                        SwitchDashboard.switchDashboard(dashboardPane, buildQuestionsPane(classesRows.get(finalI1).get(0)),
                                titlePane, "Questions");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                // create a button to edit the class
                Button editButton = new Button("edit");
                editButton.setMaxHeight(35);
                editButton.setMinWidth(50);
                editButton.setFont(Font.font(14));
                // open a new window to edit class
                editButton.setOnAction(e -> EditButtons.editClasses(classesRows, finalI1, cellList));
                // create a button to delete the class
                Button deleteButton = new Button("delete");
                deleteButton.setMaxHeight(35);
                deleteButton.setMinWidth(60);
                deleteButton.setFont(Font.font(14));
                // copy label
                deleteButton.setOnAction(e -> {
                    int pos;
                    try {
                        // delete the class
                        QuerySystem.deleteData("Course", "CourseID=".concat(classesRows.get(finalI1).get(0)));
                        // 7 + 9 * (columnNum - 1) --> formula to get the position of the cell in the
                        // grid from the row
                        // cannot find better solution change if you can.
                        pos = (7 + (9 * (GridPane.getRowIndex(deleteButton) - 1)));
                        // remove the class from the table
                        Label cellContentsEmpty = new Label("");
                        StackPane cellEmpty = new StackPane();
                        cellEmpty.getChildren().add(cellContentsEmpty);
                        classesTable.getChildren().remove(pos, pos + 9);
                        // set the index of each cell to the new value starting form 0 to the end
                        for (int j = pos; j < classesTable.getChildren().size(); j++) {
                            // avoid thread error
                            System.out.println();
                            Node node = classesTable.getChildren().get(j);
                            GridPane.setRowIndex(node, GridPane.getRowIndex(node) - 1);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                // add the buttons to the table
                classesTable.add(viewButton, finalI + 1, finalPos);
                classesTable.add(editButton, finalI + 2, finalPos);
                classesTable.add(deleteButton, finalI + 3, finalPos);

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            editStage.close();
        });
        // add the labels and text fields to the grid pane
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
        editScene.getStylesheets()
                .add(Objects.requireNonNull(EditButtons.class.getResource("Style.css")).toExternalForm());
        editStage.setScene(editScene);
        editStage.show();
    }

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
                editButton.setOnAction(e -> EditButtons.editQuiz(quizRows, finalI1, cellList));
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
                            // avoid error thread
                            System.out.print("");
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
                .add(Objects.requireNonNull(EditButtons.class.getResource("Style.css")).toExternalForm());
        editStage.setScene(editScene);
        editStage.show();
    }

    // edit quiz schedule
    // create a new window to insert values
    public static void editQuizSchedule(List<List<String>> quizClassRows, int finalI, String CourseID) {
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
        quizIDField.setText(quizClassRows.get(finalI).get(0));
        TextField classNameField = new TextField();
        classNameField.setText(quizClassRows.get(finalI).get(1));
        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            try {
                // send query to update the database with the new values
                QuerySystem.updateData("Course", List.of("QuizID"), List.of(quizIDField.getText()),
                        "CourseID = ".concat(CourseID));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            editStage.close();
        });
        // add the labels and text fields to the grid pane
        editPane.add(quizIDLabel, 0, 0);
        editPane.add(quizIDField, 1, 0);
        editPane.add(saveButton, 1, 2);
        Scene editScene = new Scene(editPane);
        editScene.getStylesheets()
                .add(Objects.requireNonNull(EditButtons.class.getResource("Style.css")).toExternalForm());
        editStage.setScene(editScene);
        editStage.show();
    }
}
