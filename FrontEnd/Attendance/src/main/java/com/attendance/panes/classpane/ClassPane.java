/******************************************************************************
 * ClassPane.java is a file that contains the code to build the
 * classes pane, students pane, and attendance pane. The classes pane displays a
 * table of all classes and buttons to upload/create classes. The students pane
 * displays a table of all students for a particular class and buttons to
 * upload/create students. The attendance pane displays the attendance for each
 * student. The code in this file uses the QuerySystem class to query the
 * database and the EditButtons class to handle user interactions.
 * Written by Luca Donadello and Dylan farmer for CS4485.0W1 , Project Attendance System,
 * starting >>>><<<<, 2024 NetID: lxd210013
 * ******************************************************************************/

package com.attendance.panes.classpane;

import com.attendance.utilities.ConverterObjToStr;
import com.attendance.database.QuerySystem;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.attendance.addButtons.AddClass.addClass;
import static com.attendance.editButtons.EditClass.editClasses;
import static com.attendance.panes.classpane.StudentPane.buildStudentsPane;
import static com.attendance.utilities.SwitchDashboard.switchDashboard;

public class ClassPane {
    // classesPane shows a table of all classes and buttons to upload/create classes
    public static Pane buildClassesPane(Pane dashboardPane, Pane titlePane) throws SQLException {
        // classesPane:- Page containing a table of all classes and buttons to
        // upload/create classes.
        Pane classesPane = new Pane();

        // *** BUILD CLASSES PANE HERE ***
        GridPane classesTable = new GridPane();
        classesTable.setId("classesTable");
        classesTable.setGridLinesVisible(true);

        // get all classes from the database
        List<List<String>> classesRows = ConverterObjToStr.convertObjListToStrList(QuerySystem.selectQuery(
                new ArrayList<>(Arrays.asList("CourseID, ClassName, StartTime, EndTime, StartDate, EndDate", "Course",
                        "", "", "", ""))));
        // column names
        List<String> classesColumnNames = new ArrayList<>(
                Arrays.asList("Section", "Course", "Start Time", "End Time", "Start Date", "End Date"));
        int classesColumnCount = classesColumnNames.size();
        StackPane cell;
        Label cellContents;
        // add column names to the table
        for (int i = 0; i < classesColumnCount; i++) {
            cellContents = new Label(classesColumnNames.get(i));
            cellContents.setFont(Font.font("Arial", 16));
            cell = new StackPane();
            cell.setPadding(new Insets(5));
            cell.getChildren().add(cellContents);
            classesTable.add(cell, i, 0);
        }
        // add classes to the table
        List<StackPane> cellList = new ArrayList<>();
        // add the classes to the table
        for (int i = 0; i < classesRows.size(); i++) {
            // create a cell for each class
            for (int j = 0; j < classesRows.get(i).size(); j++) {
                cellContents = new Label(classesRows.get(i).get(j));
                cellContents.setFont(Font.font("Arial", 14));
                cell = new StackPane();
                cell.setPadding(new Insets(5));
                cell.getChildren().add(cellContents);
                classesTable.add(cell, j, i + 1);
                cellList.add(cell);
            }
            // view classes section
            Button viewButton = new Button("view");
            viewButton.setMaxHeight(35);
            viewButton.setMinWidth(50);
            viewButton.setFont(Font.font(14));
            int finalI = i;
            viewButton.setOnAction(e -> {
                try {
                    // switch to the students pane
                    switchDashboard(dashboardPane,
                            buildStudentsPane(dashboardPane, titlePane, classesRows.get(finalI).get(0), classesPane), titlePane,
                            "Attendance");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });

            // edit classes section
            Button editButton = new Button("edit");
            editButton.setMaxHeight(35);
            editButton.setMinWidth(50);
            editButton.setFont(Font.font(14));
            editButton.setOnAction(e -> editClasses(classesRows, finalI, cellList));
            // delete classes section
            Button deleteButton = new Button("delete");
            deleteButton.setMaxHeight(35);
            deleteButton.setMinWidth(60);
            deleteButton.setFont(Font.font(14));
            deleteButton.setOnAction(e -> {
                int pos;
                try {
                    // delete the class
                    QuerySystem.deleteData("Course", "CourseID=".concat(classesRows.get(finalI).get(0)));
                    // 7 + 9 * (columnNum - 1) --> formula to get the position of the cell in the
                    // grid from the row
                    // cannot find better solution change if you can.
                    pos = (7 + (9 * (GridPane.getRowIndex(deleteButton) - 1)));
                    // remove the class from the table
                    Label cellContentsEmpty = new Label("");
                    StackPane cellEmpty = new StackPane();
                    cellEmpty.getChildren().add(cellContentsEmpty);
                    classesTable.getChildren().remove(pos, pos + 9);
                    // set the index of each cell to the new value starting form 0 to the end
                    for (int j = pos; j < classesTable.getChildren().size(); j++) {
                        Node node = classesTable.getChildren().get(j);
                        GridPane.setRowIndex(node, GridPane.getRowIndex(node) - 1);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            // add the buttons to the table
            classesTable.add(viewButton, classesColumnCount, i + 1);
            classesTable.add(editButton, classesColumnCount + 1, i + 1);
            classesTable.add(deleteButton, classesColumnCount + 2, i + 1);
        }


        ScrollPane sp = new ScrollPane(classesTable);
        sp.setPadding(new Insets(35,0,0,0));
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setFitToHeight(true);
        sp.setFitToWidth(true);
        sp.setContent(classesTable);
        // solved requestLayout() resize "Bug" avoid resize when pressed
        sp.setOnMousePressed(Event::consume);
        sp.getContent().setOnMousePressed(Event::consume);

        // add class
        Button addClassButton = new Button("Add Class");
        addClassButton.setMinHeight(35);
        addClassButton.setMinWidth(80);
        addClassButton.setFont(Font.font(14));
        addClassButton.setOnAction(e -> addClass(dashboardPane, titlePane, cellList, classesTable, classesRows));
        addClassButton.setId("addClassButton");
        VBox classesBox = new VBox();
        classesBox.setId("classesBox");
        classesBox.getChildren().add(addClassButton);

        classesPane.getChildren().add(sp);
        classesBox.getChildren().add(classesTable);
        classesPane.getChildren().add(classesBox);

        return classesPane;
    }
}
