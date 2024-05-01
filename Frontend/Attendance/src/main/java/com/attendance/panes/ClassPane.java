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

package com.attendance.panes;

import com.attendance.EditButtons;
import com.attendance.utilities.ConverterObjToStr;
import com.attendance.utilities.Parser;
import com.attendance.database.QuerySystem;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        // add class
        Button addClassButton = new Button("Add Class");
        addClassButton.setMaxHeight(35);
        addClassButton.setMinWidth(50);
        addClassButton.setFont(Font.font(14));
        addClassButton.setOnAction(e -> EditButtons.addClass());
        addClassButton.setId("addClassButton");
        VBox classesBox = new VBox();
        classesBox.setId("classesBox");
        classesBox.getChildren().add(addClassButton);

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
                            buildStudentsPane(dashboardPane, titlePane, classesRows.get(finalI).get(0)), titlePane,
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
            editButton.setOnAction(e -> EditButtons.editClasses(classesRows, finalI, cellList));
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

        classesBox.getChildren().add(classesTable);
        classesPane.getChildren().add(classesBox);
        return classesPane;
    }

    public static Pane buildStudentsPane(Pane dashboardPane, Pane titlePane, String courseID) throws SQLException {
        // questionsPane:- Page containing a table of all questions for a particular
        // quiz and buttons to upload/create questions.
        // -accessible from the quizzes page table
        Pane studentsPane = new Pane();

        // *** BUILD STUDENTS PANE HERE ***
        List<String> classesColumnNames = Arrays.asList("FirstName", "MiddleName", "LastName", "StudentUTDID",
                "StudentNetID");
        // maybe be wrong check bcs it fucked up the index of the buttons
        Button uploadStudentsButton = new Button("Upload Students");
        uploadStudentsButton.setId("uploadStudentsButton");
        uploadStudentsButton.setOnAction(e -> Parser.studentsUploader(classesColumnNames));

        VBox studentsBox = new VBox();
        studentsBox.setId("studentsBox");
        studentsBox.getChildren().add(uploadStudentsButton);
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
            editButton.setOnAction(e -> EditButtons.editStudent(studentsRows, finalI, cellList));
            Button deleteButton = new Button("delete");
            deleteButton.setMinWidth(60);
            deleteButton.setMaxHeight(35);
            deleteButton.setOnAction(e -> {
                int pos;
                // not working figure why
                // try {
                // //actual delete the quiz
                // QuerySystem.deleteData("Attendance",
                // "StudentUTDID=".concat(studentsRows.get(finalI).get(4)));
                // QuerySystem.deleteData("Student",
                // "StudentUTDID=".concat(studentsRows.get(finalI).get(4)));
                // pos = (7 * GridPane.getRowIndex(deleteButton));
                // System.out.println(pos);
                // Label cellContentsEmpty = new Label("");
                // StackPane cellEmpty = new StackPane();
                // cellEmpty.getChildren().add(cellContentsEmpty);
                // studentsTable.getChildren().remove(pos,pos+8);
                // studentsBox.getChildren().remove(pos,pos+8);
                // for (int j = pos; j < studentsTable.getChildren().size(); j++) {
                // Node node = studentsTable.getChildren().get(j);
                // GridPane.setRowIndex(node, GridPane.getRowIndex(node) - 1);
                // }
                // throw new RuntimeException(ex);
                // }
            });
            // add the buttons to the table
            studentsTable.add(viewButton, questionsColumnCount, i + 1);
            studentsTable.add(editButton, questionsColumnCount + 1, i + 1);
            studentsTable.add(deleteButton, questionsColumnCount + 2, i + 1);

        }
        // add the table to the pane
        studentsBox.getChildren().add(studentsTable);
        studentsPane.getChildren().add(studentsBox);
        return studentsPane;
    }

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
            editButton.setOnAction(e -> EditButtons.editAttendance(attendRows, finalI, cellList));
            attendTable.add(editButton, attendedColumnCount, i + 1);
        }
        // add the table to the pane
        attendBox.getChildren().add(attendTable);
        attendPane.getChildren().add(attendBox);
        return attendPane;
    }
}
