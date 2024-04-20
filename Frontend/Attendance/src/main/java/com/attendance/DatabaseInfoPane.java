package com.attendance;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.sql.SQLException;

public class DatabaseInfoPane {
    public static Pane buildDatabaseInfoPane(Pane dashboardPane, Pane titlePane) throws SQLException {
        // databaseInfoPane:- Page containing all database info: location, name, and login info.
        Pane databaseInfoPane = new Pane();
        // *** BUILD DATABASE INFO PANE HERE ***
        Label databaseInfo = new Label(
                "Location/URL:   " + ConnectionDB.getDBURL() +
                        "\nName:               " + ConnectionDB.getDBConnection().getCatalog() +
                        "\nUsername:        " + ConnectionDB.getDBUsername() +
                        "\nPassword:         " + ConnectionDB.getDBPassword());
        databaseInfo.setId("databaseInfo");
        databaseInfo.setPadding(new Insets(25));
        databaseInfoPane.getChildren().add(databaseInfo);
        return databaseInfoPane;
    }
}
