package com.attendance.panes.classpane;

import com.attendance.database.QuerySystem;
import com.attendance.utilities.ConverterObjToStr;
import javafx.event.Event;
import javafx.geometry.Insets;
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

import static com.attendance.editButtons.EditAttendance.editAttendance;

public class AttendancePane {
    // attendance pane shows the attendance for each student
    public static Pane buildAttendancePane(String studentID) throws SQLException {
        Pane attendPane = new Pane();
        VBox attendBox = new VBox();
        attendBox.setId("attendBox");
        GridPane attendTable = new GridPane();
        attendTable.setId("attendTable");
        attendTable.setGridLinesVisible(true);
        // create a table to display the attendance
        List<List<String>> attendRows = ConverterObjToStr.convertObjListToStrList(QuerySystem.selectQuery(
                new ArrayList<>(Arrays.asList("Attended, DateAndTime, IPAddress, MACID,  StudentUTDID, CourseID",
                        "AttendanceInfo", "StudentUTDID=".concat(studentID), "", "", ""))));
        List<String> attendColumnNames = new ArrayList<>(
                Arrays.asList("Attended", "DateAndTime", "IPAddress", "MACID", "StudentUTDID", "CourseID"));
        int attendedColumnCount = attendColumnNames.size();
        // add column names to the table
        StackPane cell;
        Label cellContents;
        int i;
        // add column names to the table
        for (i = 0; i < attendedColumnCount; ++i) {
            cellContents = new Label(attendColumnNames.get(i));
            cellContents.setFont(Font.font("Arial", 16));
            cell = new StackPane();
            cell.setPadding(new Insets(5.0));
            cell.getChildren().add(cellContents);
            attendTable.add(cell, i, 0);
        }
        // list of cells
        List<StackPane> cellList = new ArrayList<>();
        // add the attendance to the table
        for (i = 0; i < attendRows.size(); ++i) {
            // create a cell for each attendance
            for (int j = 0; j < (attendRows.get(i)).size(); ++j) {
                cellContents = new Label(attendRows.get(i).get(j));
                cellContents.setFont(Font.font("Arial", 14));
                cell = new StackPane();
                cell.setPadding(new Insets(5.0));
                cell.getChildren().add(cellContents);
                attendTable.add(cell, j, i + 1);
                cellList.add(cell);
            }
            // edit the attendance
            int finalI = i;
            Button editButton = new Button("edit");
            editButton.setMinWidth(50);
            editButton.setMaxHeight(35);
            editButton.setOnAction(e -> editAttendance(attendRows, finalI, cellList));
            attendTable.add(editButton, attendedColumnCount, i + 1);
        }

        //create scrollable pane in case too many entries for size of the Wrapper pane
        ScrollPane sp = new ScrollPane(attendTable);
        sp.setPadding(new Insets(35,0,0,0));
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setFitToHeight(true);
        sp.setFitToWidth(true);
        sp.setContent(attendTable);
        // solved requestLayout() resize "Bug" avoid resize when pressed
        sp.setOnMousePressed(Event::consume);
        sp.getContent().setOnMousePressed(Event::consume);

        attendPane.getChildren().add(sp);
        // add the table to the pane
        attendBox.getChildren().add(attendTable);
        attendPane.getChildren().add(attendBox);
        return attendPane;
    }
}
