package com.attendance.panes;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import java.sql.SQLException;
public class SettingsPane {
    public static Pane buildSettingsPane(Pane dashboardPane, Pane titlePane) throws SQLException {
        // settingsPane:- Page containing settings of the attendance application.
        Pane settingsPane = new Pane();
        // *** BUILD SETTINGS PANE HERE ***
        Label settingsPlaceholderContent = new Label("<Settings Content Placeholder>");
        settingsPlaceholderContent.setPadding(new Insets(50));
        settingsPane.getChildren().add(settingsPlaceholderContent);
        return settingsPane;
    }
}
