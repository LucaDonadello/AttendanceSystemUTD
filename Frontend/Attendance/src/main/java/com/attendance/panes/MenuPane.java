/******************************************************************************
 * This file contains the code for the MenuPane class, which is
 * responsible for displaying the menu buttons on the dashboard. The class
 * contains a method that builds the menu pane. The pane displays buttons for
 * scheduling quizzes, viewing quizzes, viewing passwords, viewing classes, and
 * viewing database information. The class also contains a method that allows
 * the user to edit the database information.
 * Written by Luca Donadello and Dylan Farmer for CS4485.0W1 , Project Attendance System,
 * starting >>>><<<<, 2024 NetID: lxd210013
 * ******************************************************************************/

package com.attendance.panes;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import static com.attendance.utilities.SwitchDashboard.switchDashboard;

import com.attendance.EditButtons;

public class MenuPane {
    // method to build the menu pane
    public static void buildMenu(Pane dashboardPane, Pane quizzesPane, Pane titlePane, GridPane menuPane,
            Pane passwordsPane, Pane classesPane, Pane databaseInfoPane, Pane scheduleQuiz) {
        // create menu buttons:
        Button scheduleQuizButton = new Button("Schedule Quiz");
        scheduleQuizButton.setId("startQuizButton");
        scheduleQuizButton
                .setOnAction(e -> switchDashboard(dashboardPane, scheduleQuiz, titlePane, "Schedule Quizzes"));
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
        databaseInfoButton
                .setOnAction(e -> switchDashboard(dashboardPane, databaseInfoPane, titlePane, "Database Info"));

        // settingsSpacingPane:- Pane used to space settings button on bottom of
        // menuPane.
        StackPane settingsSpacingPane = new StackPane();
        settingsSpacingPane.setId("settingsSpacingPane");
        Button settingsButton = new Button("Settings");
        settingsButton.setId("settingsPane");
        settingsButton.setOnAction(e -> {
            EditButtons.editDatabaseInfo();

        });
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
        // set constraints for menuPane:
        RowConstraints settingsSpacingConstraint = new RowConstraints();
        RowConstraints nullConstraint = new RowConstraints();
        settingsSpacingConstraint.setVgrow(Priority.ALWAYS);
        menuPane.getRowConstraints().addAll(nullConstraint, nullConstraint, nullConstraint, nullConstraint,
                nullConstraint, settingsSpacingConstraint);

    }
}
