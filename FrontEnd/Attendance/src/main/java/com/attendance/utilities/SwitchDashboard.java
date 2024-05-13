/******************************************************************************
 * This class is used to switch dashboard pane and title pane based on what menu
 * button is pressed
 * Written Dylan Farmer for CS4485.0W1 , Project Attendance System,
 * starting 22/03/2024
 * ******************************************************************************/
package com.attendance.utilities;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class SwitchDashboard {

    public static void switchDashboard(Pane dashboardPane, Pane targetPane, Pane titlePane, String newTitle) {
        dashboardPane.getChildren().clear();
        dashboardPane.getChildren().add(targetPane);
        ((Label) titlePane.getChildren().get(0)).setText(newTitle);
    }
}
