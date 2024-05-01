/******************************************************************************
 * PasswordPane class that builds the password pane for the application.
 * The password pane displays a table of all password banks and buttons to upload or create 
 * password banks.The table contains the password and quiz ID columns. The class also contains 
 * a method that allows the user to edit the password bank.
 * Written by Luca Donadello and Dylan Farmer for CS4485.0W1 , Project Attendance System,
 * starting >>>><<<<, 2024 NetID: lxd210013
 * ******************************************************************************/

package com.attendance.panes;

import com.attendance.utilities.ConverterObjToStr;
import com.attendance.EditButtons;
import com.attendance.database.QuerySystem;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PasswordPane {
    // method to build the password pane
    public static Pane buildPasswordPane() throws SQLException {
        // passwordsPane:- Page containing a table of all password banks and buttons to
        // upload/create password banks.
        Pane passwordsPane = new Pane();

        // *** BUILD PASSWORDS PANE HERE ***
        // create the grid for password and the titles
        GridPane passwordsTable = new GridPane();
        // set the id of the table
        passwordsTable.setId("passwordsTable");
        passwordsTable.setGridLinesVisible(true);
        // get the password data from the database
        List<List<String>> passwordsRows = ConverterObjToStr.convertObjListToStrList(
                QuerySystem.selectQuery(new ArrayList<>(Arrays.asList("Password_, QuizID", "Quiz", "", "", "", ""))));
        // create a list of column names for the password table
        List<String> passwordsColumnNames = new ArrayList<>(Arrays.asList("Password", "QuizID"));
        int passwordsColumnCount = passwordsColumnNames.size();
        StackPane cell = null;
        Label cellContents;
        // create the grid for password and the titles
        for (int i = 0; i < passwordsColumnCount; i++) {
            cellContents = new Label(passwordsColumnNames.get(i));
            cell = new StackPane();
            cell.setPadding(new Insets(5));
            cell.getChildren().add(cellContents);
            passwordsTable.add(cell, i, 0);
        }

        // create array of cells
        List<StackPane> cellsList = new ArrayList<>();

        // insert the values inside the grid
        for (int i = 0; i < passwordsRows.size(); i++) {
            for (int j = 0; j < passwordsRows.get(i).size(); j++) {
                cellContents = new Label(passwordsRows.get(i).get(j));
                cell = new StackPane();
                cell.setPadding(new Insets(5));
                cell.getChildren().add(cellContents);
                passwordsTable.add(cell, j, i + 1);
                // add cell in an array of cells
                cellsList.add(cell);
            }

            int finalI = i;
            // edit button
            Button editButton = new Button("edit");
            editButton.setOnAction(e -> {
                // edit database and change UI
                EditButtons.editPassword(passwordsRows, finalI, cellsList);
            });

            // delete button
            Button deleteButton = new Button("delete");
            deleteButton.setOnAction(e -> {
                int pos;
                try {
                    // not actually delete the password but set it to null so there is no psw for
                    // that quizID
                    QuerySystem.updateData("Quiz", List.of("Password_"), List.of(""),
                            "QuizID=".concat(passwordsRows.get(finalI).get(1)));
                    // row * 2 + (row-1) * 2 + 1 --> formula get the position of the cell in the
                    // grid based on the row.
                    pos = (GridPane.getRowIndex(deleteButton) * 2) + (GridPane.getRowIndex(deleteButton) - 1) * 2 + 1;
                    // empty cell
                    Label cellContentsEmpty = new Label("");
                    // create the empty cell
                    StackPane cellEmpty = new StackPane();
                    // add the empty cell to the grid
                    cellEmpty.getChildren().add(cellContentsEmpty);
                    passwordsTable.getChildren().set(pos, cellEmpty);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            // add the buttons to the grid
            passwordsTable.add(editButton, passwordsColumnCount, i + 1);
            passwordsTable.add(deleteButton, passwordsColumnCount + 1, i + 1);
        }
        // add the table to the pane
        passwordsPane.getChildren().add(passwordsTable);
        return passwordsPane;
    }
}
