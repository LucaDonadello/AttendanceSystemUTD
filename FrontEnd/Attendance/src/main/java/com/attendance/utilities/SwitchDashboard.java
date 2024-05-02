/******************************************************************************
 * This class is used to switch dashboard pane and title pane based on what menu
 * button is pressed
 * starting >>>><<<<, 2024 NetID: 
 * ******************************************************************************/
package com.attendance.utilities;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
public class SwitchDashboard {

    public static void switchDashboard(Pane dashboardPane, Pane targetPane, Pane titlePane, String newTitle) {
        dashboardPane.getChildren().clear();
        dashboardPane.getChildren().add(targetPane);
        ((Label)titlePane.getChildren().get(0)).setText(newTitle);
    }
}