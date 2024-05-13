/******************************************************************************
 * Description: This class is used to edit the class data in the database.
 * The EditClass class contains a method to create a new window to edit the
 * class data. The method takes in the class data, the index of the
 * row to be edited, and a list of StackPane objects representing the cells in
 * the table. The method creates a new window with text fields for each column
 * of the class data, allowing the user to edit the data. The user can then
 * save the changes, which updates the database and the UI.
 * Written by Luca Donadello for CS4485.0W1 , Project Attendance System,
 * starting 15/04/2024 NetID: lxd210013
 * ******************************************************************************/

package com.attendance.editButtons;

import com.attendance.AttendanceApplication;
import com.attendance.database.ConnectionDB;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Objects;

public class EditDatabase {
    // edit database info
    // create a new window to insert values
    public static void editDatabaseInfo() {
        // create a new window to insert values
        Stage editStage = new Stage();
        // create a new grid pane
        GridPane editPane = new GridPane();
        editPane.setId("editPane");
        editPane.setPadding(new Insets(10));
        editPane.setHgap(10);
        editPane.setVgap(10);
        // create labels and text fields for each column
        Label urlLabel = new Label("URL:");
        TextField urlField = new TextField();
        urlField.setText(ConnectionDB.getDBURL());
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        try {
            // get the database name
            nameField.setText(ConnectionDB.getDBConnection().getCatalog());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // get the database username password and set the text fields to the values
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setText(ConnectionDB.getDBUsername());

        Label passwordLabel = new Label("Password:");
        TextField passwordField = new TextField();
        passwordField.setText(ConnectionDB.getDBPassword());

        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            // save the new values in the database
            ConnectionDB.setDBURL(urlField.getText());
            ConnectionDB.setDBUsername(usernameField.getText());
            ConnectionDB.setDBPassword(passwordField.getText());

            editStage.close();
        });
        // add the labels and text fields to the grid pane
        editPane.add(urlLabel, 0, 0);
        editPane.add(urlField, 1, 0);
        editPane.add(nameLabel, 0, 1);
        editPane.add(nameField, 1, 1);
        editPane.add(usernameLabel, 0, 2);
        editPane.add(usernameField, 1, 2);
        editPane.add(passwordLabel, 0, 3);
        editPane.add(passwordField, 1, 3);
        editPane.add(saveButton, 1, 4);
        Scene editScene = new Scene(editPane);
        editScene.getStylesheets()
                .add(Objects.requireNonNull(AttendanceApplication.class.getResource("Style.css")).toExternalForm());
        editStage.setScene(editScene);
        editStage.show();
    }
}
