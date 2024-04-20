package com.attendance;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.sql.*;
import java.util.Objects;
public class AttendanceApplication extends Application {
    @Override
    public void start(Stage stage) throws SQLException {
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

        //create the Panes for each section
        Pane quizzesPane = QuizPane.buildQuizPane(dashboardPane, titlePane);
        Pane passwordsPane = PasswordPane.buildPasswordPane(dashboardPane, titlePane);
        Pane classesPane = ClassPane.buildClassesPane(dashboardPane, titlePane);
        Pane databaseInfoPane = DatabaseInfoPane.buildDatabaseInfoPane(dashboardPane, titlePane);
        Pane settingsPane = SettingsPane.buildSettingsPane(databaseInfoPane, titlePane);
        MenuPane.buildMenu(dashboardPane, quizzesPane, titlePane, menuPane, passwordsPane, classesPane, databaseInfoPane, settingsPane);

        // set default dashboard to quizzes page
        SwitchDashboard.switchDashboard(dashboardPane, quizzesPane, titlePane, "Quizzes");

        // create the application scene and set the scene/stage:
        // applicationScene:- Scene containing all main functionality of attendance application.
        Scene applicationScene = new Scene(rootPane);
        applicationScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("Style.css")).toExternalForm()); // retrieve application stylesheet
        stage.setTitle("Attendance App");
        stage.setScene(applicationScene);
        stage.setWidth(1000);
        stage.setHeight(800);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
