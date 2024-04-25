package com.attendance.panes;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import static com.attendance.utilities.SwitchDashboard.switchDashboard;


import com.attendance.EditButtons;

public class MenuPane {
    public static void buildMenu(Pane dashboardPane, Pane quizzesPane, Pane titlePane, GridPane menuPane, Pane passwordsPane, Pane classesPane, Pane databaseInfoPane, Pane scheduleQuiz){
        // create menu buttons:
        Button scheduleQuizButton = new Button("Schedule Quiz");
        scheduleQuizButton.setId("startQuizButton");
        scheduleQuizButton.setOnAction(e -> switchDashboard(dashboardPane, scheduleQuiz , titlePane, "Schedule Quizzes"));
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
        settingsButton.setOnAction(e -> EditButtons.editDatabaseInfo());
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

    }
}
