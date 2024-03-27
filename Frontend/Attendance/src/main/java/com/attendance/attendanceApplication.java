package com.attendance;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class attendanceApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // loginPane:- Page containing the login form fields and login button.
        StackPane loginPane = new StackPane();
        // *** BUILD LOGIN PANE HERE ***
        loginPane.setMinWidth(500);
        loginPane.setMinHeight(500);
        // ^feel free to change these values^
        Label loginPlaceholderContent = new Label("<Login Content Placeholder>");
        loginPane.getChildren().add(loginPlaceholderContent);

        // loginScene:- Scene containing login screen of the application.
        Scene loginScene = new Scene(loginPane);

        // rootPane:- Root pane of the application window.
        GridPane rootPane = new GridPane();
        rootPane.setId("rootPane");

        // menuPane:- Left partition of the application window.
        GridPane menuPane = new GridPane();
        menuPane.setId("menuPane");
        menuPane.setMinWidth(100);
        menuPane.setMinHeight(500);
        menuPane.setVgap(10);
        rootPane.add(menuPane, 0, 0);

        // windowPane:- Right partition of the application window.
        GridPane windowPane = new GridPane();
        windowPane.setId("windowPane");
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints();
        row2.setVgrow(Priority.ALWAYS);
        windowPane.getRowConstraints().addAll(row1, row2);
        rootPane.add(windowPane,1 ,0);

        // titlePane:- Upper partition of windowPane.
        StackPane titlePane = new StackPane();
        titlePane.setId("titlePane");
        titlePane.setMinWidth(800);
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
        Label quizzesPlaceholderContent = new Label("<Quizzes Content Placeholder>");
        quizzesPane.getChildren().add(quizzesPlaceholderContent);

        // questionsPane:- Page containing a table of all questions for a particular quiz and buttons to upload/create questions.
        // -accessible from the quizzes page table
        Pane questionsPane = new Pane();
        // *** BUILD QUESTIONS PANE HERE ***
        Label questionsPlaceholderContent = new Label("<Questions Content Placeholder>");
        questionsPane.getChildren().add(questionsPlaceholderContent);

        // passwordsPane:- Page containing a table of all password banks and buttons to upload/create password banks.
        Pane passwordsPane = new Pane();
        // *** BUILD PASSWORDS PANE HERE ***
        Label passwordsPlaceholderContent = new Label("<Passwords Content Placeholder>");
        passwordsPane.getChildren().add(passwordsPlaceholderContent);

        // classesPane:- Page containing a table of all classes and buttons to upload/create classes.
        Pane classesPane = new Pane();
        // *** BUILD CLASSES PANE HERE ***
        Label classesPlaceholderContent = new Label("<Classes Content Placeholder>");
        classesPane.getChildren().add(classesPlaceholderContent);

        // attendancePane:- Page containing all attendance information for a particular class and buttons to upload/create/download attendance.
        // -accessible from the classes page table
        Pane attendancePane = new Pane();
        // *** BUILD ATTENDANCE PANE HERE ***
        Label attendancePlaceholderContent = new Label("<Attendance Content Placeholder>");
        attendancePane.getChildren().add(attendancePlaceholderContent);

        // databaseInfoPane:- Page containing all database info: location, name, and login info.
        Pane databaseInfoPane = new Pane();
        // *** BUILD DATABASE INFO PANE HERE ***
        Label databaseInfoPlaceholderContent = new Label("<Database Info Content Placeholder>");
        databaseInfoPane.getChildren().add(databaseInfoPlaceholderContent);

        // settingsPane:- Page containing settings of the attendance application.
        Pane settingsPane = new Pane();
        // *** BUILD SETTINGS PANE HERE ***
        Label settingsPlaceholderContent = new Label("<Settings Content Placeholder>");
        settingsPane.getChildren().add(settingsPlaceholderContent);

        // create menu buttons:
        Button startQuizButton = new Button("Start Quiz");
        startQuizButton.setId("startQuizButton");
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
        settingsSpacingPane.setId("settingsPane");
        Button settingsButton = new Button("Settings");
        settingsButton.setId("settingsButton");
        settingsButton.setOnAction(e -> switchDashboard(dashboardPane, settingsPane, titlePane, "Settings"));
        settingsSpacingPane.getChildren().add(settingsButton);
        // set alignment of menu buttons:
        GridPane.setHalignment(startQuizButton, HPos.CENTER);
        GridPane.setHalignment(quizzesButton, HPos.CENTER);
        GridPane.setHalignment(passwordsButton, HPos.CENTER);
        GridPane.setHalignment(classesButton, HPos.CENTER);
        GridPane.setHalignment(databaseInfoButton, HPos.CENTER);
        GridPane.setHalignment(settingsButton, HPos.CENTER);
        settingsSpacingPane.setAlignment(Pos.BOTTOM_CENTER);
        // add menu buttons to menuPane:
        menuPane.add(startQuizButton, 0, 0);
        menuPane.add(quizzesButton, 0, 1);
        menuPane.add(passwordsButton, 0, 2);
        menuPane.add(classesButton, 0, 3);
        menuPane.add(databaseInfoButton, 0, 4);
        menuPane.add(settingsSpacingPane, 0, 5);

        // set default dashboard to quizzes page
        switchDashboard(dashboardPane, quizzesPane, titlePane, "Quizzes");

        // create the application scene and set the scene/stage:
        // applicationScene:- Scene containing all main functionality of attendance application.
        Scene applicationScene = new Scene(rootPane);
        applicationScene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
        stage.setTitle("Attendance");

        // Use these lines to switch between login scene and application scene for development process:
        stage.setScene(applicationScene);
        //stage.setScene(loginScene);

        stage.show();
    }

    // method used to switch dashboard pane and title pane based on what menu button is pressed
    public void switchDashboard(Pane dashboardPane, Pane targetPane, Pane titlePane, String newTitle) {
        dashboardPane.getChildren().clear();
        dashboardPane.getChildren().add(targetPane);
        ((Label)titlePane.getChildren().get(0)).setText(newTitle);
    }

    public static void main(String[] args) {
        launch();
    }
}