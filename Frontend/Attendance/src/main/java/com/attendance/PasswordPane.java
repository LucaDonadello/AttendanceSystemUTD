package com.attendance;

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
    public static Pane buildPasswordPane(Pane dashboardPane, Pane titlePane) throws SQLException {
        // passwordsPane:- Page containing a table of all password banks and buttons to upload/create password banks.
        Pane passwordsPane = new Pane();
        // *** BUILD PASSWORDS PANE HERE ***
        GridPane passwordsTable = new GridPane();
        passwordsTable.setId("passwordsTable");
        passwordsTable.setGridLinesVisible(true);
        List<List<String>> passwordsRows = ConverterObjToStr.convertObjListToStrList(QuerySystem.selectQuery(new ArrayList<>(Arrays.asList("Password_, QuizID", "Quiz", "", "", "", ""))));
        List<String> passwordsColumnNames = new ArrayList<>(Arrays.asList("Password","QuizID"));
        int passwordsColumnCount = passwordsColumnNames.size();
        StackPane cell;
        Label cellContents;
        //create the grid for password
        for (int i = 0; i < passwordsColumnCount; i++) {
            cellContents = new Label(passwordsColumnNames.get(i));
            cell = new StackPane();
            cell.setPadding(new Insets(5));
            cell.getChildren().add(cellContents);
            passwordsTable.add(cell, i, 0);
        }
        for (int i = 0; i < passwordsRows.size(); i++) {
            for (int j = 0; j < passwordsRows.get(i).size(); j++) {
                cellContents = new Label(passwordsRows.get(i).get(j));
                cell = new StackPane();
                cell.setPadding(new Insets(5));
                cell.getChildren().add(cellContents);
                passwordsTable.add(cell, j, i + 1);
            }
            int finalI = i;
            // edit password section
            Button editButton = new Button("edit");
            editButton.setOnAction(e -> EditButtons.editPassword(passwordsRows,finalI));
            Button deleteButton = new Button("delete");
            //need to refresh page
            deleteButton.setOnAction(e -> {
                try {
                    //not actually delete the password but set it to null so there is no psw for that quizID
                    QuerySystem.updateData("Quiz", List.of("Password_"), List.of("") ,"QuizID=".concat(passwordsRows.get(finalI).get(0)));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            Button copyButton = new Button("copy");
            passwordsTable.add(editButton, passwordsColumnCount, i + 1);
            passwordsTable.add(deleteButton, passwordsColumnCount + 1, i + 1);
            passwordsTable.add(copyButton, passwordsColumnCount + 2, i + 1);
        }
        passwordsPane.getChildren().add(passwordsTable);
        return passwordsPane;
    }
}
