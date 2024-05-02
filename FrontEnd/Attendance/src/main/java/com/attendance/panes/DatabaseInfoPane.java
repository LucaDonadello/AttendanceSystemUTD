/******************************************************************************
 * This class is responsible for creating the database info pane. This pane
 * displays the location, name, username, and password of the database.
 * The class contains a method that builds the database info pane.
 * Written by Luca Donadello And Dylan Farmer for CS4485.0W1 , Project Attendance System,
 * starting >>>><<<<, 2024 NetID: lxd210013
 * ******************************************************************************/

package com.attendance.panes;

import com.attendance.database.ConnectionDB;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.sql.SQLException;

public class DatabaseInfoPane {
    public static Pane buildDatabaseInfoPane() throws SQLException {
        // databaseInfoPane:- Page containing all database info: location, name, and
        // login info.
        Pane databaseInfoPane = new Pane();
        // *** BUILD DATABASE INFO PANE HERE ***
        Label databaseInfo = new Label(
                "Location/URL:   " + ConnectionDB.getDBURL() +
                        "\nName:               " + ConnectionDB.getDBConnection().getCatalog() +
                        "\nUsername:        " + ConnectionDB.getDBUsername() +
                        "\nPassword:         " + ConnectionDB.getDBPassword());
        databaseInfo.setFont(Font.font("Arial", 16));
        databaseInfo.setId("databaseInfo");
        databaseInfo.setPadding(new Insets(25));
        databaseInfoPane.getChildren().add(databaseInfo);
        return databaseInfoPane;
    }
}
