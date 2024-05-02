package com.attendance.panes.classpane;

import com.attendance.database.QuerySystem;
import com.attendance.utilities.ConverterObjToStr;
import com.attendance.utilities.Parser;
import com.attendance.utilities.SwitchDashboard;
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

import static com.attendance.editButtons.EditStudent.editStudent;
import static com.attendance.panes.classpane.AttendancePane.buildAttendancePane;
import static com.attendance.utilities.SwitchDashboard.switchDashboard;

public class StudentPane {
    public static Pane buildStudentsPane(Pane dashboardPane, Pane titlePane, String courseID, Pane classesPane) throws SQLException {
        // questionsPane:- Page containing a table of all questions for a particular
        // quiz and buttons to upload/create questions.
        // -accessible from the quizzes page table
        Pane studentsPane = new Pane();

        // *** BUILD STUDENTS PANE HERE ***
        List<String> classesColumnNames = Arrays.asList("FirstName", "MiddleName", "LastName", "StudentUTDID",
                "StudentNetID");

        GridPane studentsTable = new GridPane();
        studentsTable.setId("studentsTable");
        studentsTable.setGridLinesVisible(true);

        List<List<String>> studentsRows = ConverterObjToStr
                .convertObjListToStrList(QuerySystem.selectQuery(new ArrayList<>(
                        Arrays.asList("FirstName, MiddleName, LastName, Student.StudentNetID, Student.StudentUTDID",
                                "Attendance JOIN Student ON Student.StudentUTDID=Attendance.StudentUTDID",
                                "CourseID=".concat(courseID), "", "", ""))));
        List<String> studentsColumnNames = new ArrayList<>(
                Arrays.asList("First Name", "Middle Name", "Last Name", "NET-ID", "UTD-ID", "Attendance"));
        StackPane cell;
        Label cellContents;
        int questionsColumnCount = studentsColumnNames.size();
        // add column names to the table
        for (int i = 0; i < questionsColumnCount; i++) {
            cellContents = new Label(studentsColumnNames.get(i));
            cellContents.setFont(Font.font("Arial", 16));
            cell = new StackPane();
            cell.setPadding(new Insets(5));
            cell.getChildren().add(cellContents);
            studentsTable.add(cell, i, 0);
        }
        // add students to the table
        List<StackPane> cellList = new ArrayList<>();
        // add the students to the table
        for (int i = 0; i < studentsRows.size(); i++) {
            for (int j = 0; j < studentsRows.get(i).size(); j++) {
                cellContents = new Label(studentsRows.get(i).get(j));
                cellContents.setFont(Font.font("Arial", 14));
                cell = new StackPane();
                cell.setPadding(new Insets(5));
                cell.getChildren().add(cellContents);
                studentsTable.add(cell, j, i + 1);
                cellList.add(cell);
            }
            // save the index of the student
            int finalI = i;
            // view the attendance of the student
            Button viewButton = new Button("view Attendance");
            viewButton.setMaxHeight(35);
            viewButton.setMinWidth(50);
            viewButton.setOnAction(e -> {
                // switch to the attendance pane
                try {
                    switchDashboard(dashboardPane, buildAttendancePane(studentsRows.get(finalI).get(4)), titlePane,
                            "Attendance");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            Button editButton = new Button("edit");
            editButton.setMinWidth(50);
            editButton.setMaxHeight(35);

            // open a new window to insert values
            editButton.setOnAction(e -> editStudent(studentsRows, finalI, cellList));
            Button deleteButton = new Button("delete");
            deleteButton.setMinWidth(60);
            deleteButton.setMaxHeight(35);
            deleteButton.setOnAction(e -> {
                int pos;
                //actual delete the quiz
                try {
                    QuerySystem.deleteData("Attendance",
                            "StudentUTDID=".concat(studentsRows.get(finalI).get(4)));
                    QuerySystem.deleteData("Student",
                            "StudentUTDID=".concat(studentsRows.get(finalI).get(4)));
                    pos = (7 * GridPane.getRowIndex(deleteButton));
                    Label cellContentsEmpty = new Label("");
                    StackPane cellEmpty = new StackPane();
                    cellEmpty.getChildren().add(cellContentsEmpty);
                    studentsTable.getChildren().remove(pos, pos + 8);

                    // update index of cell after deletion
                    for (int j = pos; j < studentsTable.getChildren().size(); j++) {
                        Node node = studentsTable.getChildren().get(j);
                        GridPane.setRowIndex(node, GridPane.getRowIndex(node) - 1);
                    }
                }catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            // add the buttons to the table
            studentsTable.add(viewButton, questionsColumnCount, i + 1);
            studentsTable.add(editButton, questionsColumnCount + 1, i + 1);
            studentsTable.add(deleteButton, questionsColumnCount + 2, i + 1);

        }

        ScrollPane sp = new ScrollPane(studentsTable);
        sp.setPadding(new Insets(35,0,0,0));
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setFitToHeight(true);
        sp.setFitToWidth(true);
        sp.setContent(studentsTable);
        // solved requestLayout() resize "Bug" avoid resize when pressed
        sp.setOnMousePressed(Event::consume);
        sp.getContent().setOnMousePressed(Event::consume);

        // upload students
        Button uploadStudentsButton = new Button("Upload Students");
        uploadStudentsButton.setId("uploadStudentsButton");
        uploadStudentsButton.setOnAction(e -> {
            Parser.studentsUploader(classesColumnNames);
            // change pane to course to refresh the page. Easier to change UI this way due to connectivity time with database
            SwitchDashboard.switchDashboard(dashboardPane, classesPane , titlePane, "Classes");
        });
        uploadStudentsButton.setMinHeight(35);
        uploadStudentsButton.setMinWidth(120);
        uploadStudentsButton.setFont(Font.font(14));
        VBox studentsBox = new VBox();
        studentsBox.setId("studentsBox");
        studentsBox.getChildren().add(uploadStudentsButton);

        // add the table to the pane
        studentsPane.getChildren().add(sp);
        studentsBox.getChildren().add(studentsTable);
        studentsPane.getChildren().add(studentsBox);
        return studentsPane;
    }
}
