package com.attendance;

import com.attendance.database.QuerySystem;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class EditButtons {
    // edit for quizzes
    public static void editQuiz(List<List<String>> quizRows, int finalI) {
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
                QuerySystem.updateData("Quiz", List.of("Password_", "StartTime", "Duration"), List.of(passwordField.getText(), startTimeField.getText(), durationField.getText()), "QuizID=".concat(quizIDField.getText()));
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
        editScene.getStylesheets().add(Objects.requireNonNull(EditButtons.class.getResource("Style.css")).toExternalForm());
        editStage.setScene(editScene);
        editStage.show();
    }

    // edit for password
    // create a new window to insert values
    public static void editPassword(List<List<String>> passwordsRows, int finalI) {
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
                QuerySystem.updateData("Quiz", List.of("Password_"), List.of(passwordField.getText()), "QuizID=".concat(quizIDField.getText()));
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
        editScene.getStylesheets().add(Objects.requireNonNull(EditButtons.class.getResource("Style.css")).toExternalForm());
        editStage.setScene(editScene);
        editStage.show();
    }

    public static void editClasses(List<List<String>> classesRows, int finalI) {
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
                QuerySystem.updateData("Course", List.of("ClassName", "StartTime", "EndTime", "StartDate", "EndDate"), List.of(classNameField.getText(), startTimeField.getText(), endTimeField.getText(), startDateField.getText(), endDateField.getText()), "CourseID=".concat(courseIDField.getText()));
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
        editScene.getStylesheets().add(Objects.requireNonNull(EditButtons.class.getResource("Style.css")).toExternalForm());
        editStage.setScene(editScene);
        editStage.show();
    }

    public static void editQuestions(List<List<String>> questionsRows, int finalI){

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
                    QuerySystem.updateData("QuizQuestion", List.of("Question", "AnswerSet", "CorrectAnswer"), List.of(questionField.getText(), answerSetField.getText(), correctAnswerField.getText()), "QuestionID=".concat(questionIDField.getText()));
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
            editScene.getStylesheets().add(Objects.requireNonNull(EditButtons.class.getResource("Style.css")).toExternalForm());
            editStage.setScene(editScene);
            editStage.show();
    }

    public static void editStudent(List<List<String>> studentsRows, int finalI){
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
                QuerySystem.updateData("Student", List.of("FirstName", "MiddleName", "LastName", "StudentNetID", "StudentUTDID"), List.of(firstNameField.getText(), middleNameField.getText(), lastNameField.getText(), studentNetIDField.getText(), studentUTDIDField.getText()), "StudentUTDID=".concat(studentUTDIDField.getText()));
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
        editScene.getStylesheets().add(Objects.requireNonNull(EditButtons.class.getResource("Style.css")).toExternalForm());
        editStage.setScene(editScene);
        editStage.show();
    }

    public static void editAttendance(List<List<String>> attendanceRows, int finalI){
        Stage editStage = new Stage();
        GridPane editPane = new GridPane();
        editPane.setId("editPane");
        editPane.setPadding(new Insets(10));
        editPane.setHgap(10);
        editPane.setVgap(10);
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
        //find better
        String attendedTrue = "1";
        String attendedFalse = "0";
        saveButton.setOnAction(event -> {
            try {
                if(attendedField.getText().equals("True") || attendedField.getText().equals("true") || attendedField.getText().equals("1"))
                    QuerySystem.updateData("AttendanceInfo", List.of("Attended", "DateAndTime", "IPAddress", "MACID", "StudentUTDID", "CourseID"), List.of(attendedTrue , dateAndTimeField.getText(), ipField.getText(), macField.getText(), StudentIDField.getText(), CourseIDField.getText()), "StudentUTDID=".concat(StudentIDField.getText()));
                else
                    QuerySystem.updateData("AttendanceInfo", List.of("Attended", "DateAndTime", "IPAddress", "MACID", "StudentUTDID", "CourseID"), List.of(attendedFalse , dateAndTimeField.getText(), ipField.getText(), macField.getText(), StudentIDField.getText(), CourseIDField.getText()), "StudentUTDID=".concat(StudentIDField.getText()));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            editStage.close();
        });
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
        editScene.getStylesheets().add(Objects.requireNonNull(EditButtons.class.getResource("Style.css")).toExternalForm());
        editStage.setScene(editScene);
        editStage.show();
    }
}
