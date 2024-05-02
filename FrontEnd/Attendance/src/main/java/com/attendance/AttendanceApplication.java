/******************************************************************************
 * The attendanceApplication class is the main class of the attendance application.
 * This class contains the start method which initializes the application window
 * and sets up the menu and dashboard panes. The menu pane contains buttons to
 * navigate to different pages of the application, and the dashboard pane contains
 * the content of the current page. The application window is built using JavaFX.
 * The main method of this class launches the application.
 * Written by Luca Donadello and Dylan Farmer for CS4485.0W1 , Project Attendance System,
 * starting April 6, 2024 NetID: lxd210013
 * ******************************************************************************/

package com.attendance;

import com.attendance.panes.*;
import com.attendance.panes.classpane.ClassPane;
import com.attendance.panes.quizpane.QuizPane;
import com.attendance.utilities.SwitchDashboard;
import com.attendance.panes.scheduleQuizPane;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.sql.*;
import java.util.Objects;
public class AttendanceApplication extends Application {
    // start:- Initializes the application window and sets up the menu and dashboard panes.
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
        Pane scheduleQuiz = scheduleQuizPane.buildScheduleQuiz();
        Pane quizzesPane = QuizPane.buildQuizPane(dashboardPane, titlePane);
        Pane passwordsPane = PasswordPane.buildPasswordPane();
        Pane classesPane = ClassPane.buildClassesPane(dashboardPane, titlePane);
        Pane databaseInfoPane = DatabaseInfoPane.buildDatabaseInfoPane();
        MenuPane.buildMenu(dashboardPane, quizzesPane, titlePane, menuPane, passwordsPane, classesPane, databaseInfoPane, scheduleQuiz);

        // set default dashboard to quizzes page
        SwitchDashboard.switchDashboard(dashboardPane, quizzesPane, titlePane, "Quizzes");

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
    public static void main(String[] args) {
        launch();
    }
}