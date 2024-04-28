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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.attendance.utilities.SwitchDashboard.switchDashboard;

public class ClassPane {
    public static Pane buildClassesPane(Pane dashboardPane, Pane titlePane) throws SQLException {
        // classesPane:- Page containing a table of all classes and buttons to upload/create classes.
        Pane classesPane = new Pane();

        // *** BUILD CLASSES PANE HERE ***
        GridPane classesTable = new GridPane();
        classesTable.setId("classesTable");
        classesTable.setGridLinesVisible(true);

        //add class
        Button addClassButton = new Button("Add Class");
        addClassButton.setOnAction(e -> EditButtons.addClass());
        addClassButton.setId("addClassButton");
        VBox classesBox = new VBox();
        classesBox.setId("classesBox");
        classesBox.getChildren().add(addClassButton);

        List<List<String>> classesRows = ConverterObjToStr.convertObjListToStrList(QuerySystem.selectQuery(new ArrayList<>(Arrays.asList("CourseID, ClassName, StartTime, EndTime, StartDate, EndDate", "Course", "", "", "", ""))));
        List<String> classesColumnNames = new ArrayList<>(Arrays.asList("Section", "Course", "Start Time", "End Time","Start Date", "End Date"));
        int classesColumnCount = classesColumnNames.size();
        StackPane cell;
        Label cellContents;

        for (int i = 0; i < classesColumnCount; i++) {
            cellContents = new Label(classesColumnNames.get(i));
            cell = new StackPane();
            cell.setPadding(new Insets(5));
            cell.getChildren().add(cellContents);
            classesTable.add(cell, i, 0);
        }

        for (int i = 0; i < classesRows.size(); i++) {
            for (int j = 0; j < classesRows.get(i).size(); j++) {
                cellContents = new Label(classesRows.get(i).get(j));
                cell = new StackPane();
                cell.setPadding(new Insets(5));
                cell.getChildren().add(cellContents);
                classesTable.add(cell, j, i + 1);
            }

            Button viewButton = new Button("view");
            int finalI = i;
            viewButton.setOnAction(e -> {
                try {
                    switchDashboard(dashboardPane, buildStudentsPane(dashboardPane, titlePane,classesRows.get(finalI).get(0)), titlePane, "Attendance");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });

            // edit classes section
            Button editButton = new Button("edit");
            editButton.setOnAction(e -> EditButtons.editClasses(classesRows,finalI));
            Button deleteButton = new Button("delete");
            deleteButton.setOnAction(e -> {
                int pos;
                try {
                    QuerySystem.deleteData("Course", "CourseID=".concat(classesRows.get(finalI).get(0)));
                    //7 + 9 * (columnNum - 1) --> formula
                    pos = (7 + (9 * (GridPane.getRowIndex(deleteButton)-1)));
                    System.out.println(pos);
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

            classesTable.add(viewButton, classesColumnCount, i + 1);
            classesTable.add(editButton, classesColumnCount + 1, i + 1);
            classesTable.add(deleteButton, classesColumnCount + 2, i + 1);
        }
        
        classesBox.getChildren().add(classesTable);
        classesPane.getChildren().add(classesBox);
        return classesPane;
    }

    public static Pane buildStudentsPane(Pane dashboardPane, Pane titlePane, String courseID) throws SQLException {
        // questionsPane:- Page containing a table of all questions for a particular quiz and buttons to upload/create questions.
        // -accessible from the quizzes page table
        Pane studentsPane = new Pane();

        // *** BUILD STUDENTS PANE HERE ***
        List<String> classesColumnNames = Arrays.asList("FirstName", "MiddleName", "LastName", "StudentUTDID", "StudentNetID");
        //maybe be wrong check bcs it fucked up the index of the buttons
        Button uploadStudentsButton = new Button("Upload Students");
        uploadStudentsButton.setId("uploadStudentsButton");
        uploadStudentsButton.setOnAction(e -> Parser.studentsUploader(classesColumnNames));

        VBox studentsBox = new VBox();
        studentsBox.setId("studentsBox");
        //check here too
        studentsBox.getChildren().add(uploadStudentsButton);
        GridPane studentsTable = new GridPane();
        studentsTable.setId("studentsTable");
        studentsTable.setGridLinesVisible(true);

        List<List<String>> studentsRows = ConverterObjToStr.convertObjListToStrList(QuerySystem.selectQuery(new ArrayList<>(Arrays.asList("FirstName, MiddleName, LastName, Student.StudentNetID, Student.StudentUTDID", "Attendance JOIN Student ON Student.StudentUTDID=Attendance.StudentUTDID", "CourseID=".concat(courseID), "", "", ""))));
        List<String> studentsColumnNames = new ArrayList<>(Arrays.asList("First Name", "Middle Name", "Last Name", "NET-ID","UTD-ID", "Attendance"));
        StackPane cell;
        Label cellContents;
        int questionsColumnCount = studentsColumnNames.size();

        for (int i = 0; i < questionsColumnCount; i++) {
            cellContents = new Label(studentsColumnNames.get(i));
            cell = new StackPane();
            cell.setPadding(new Insets(5));
            cell.getChildren().add(cellContents);
            studentsTable.add(cell, i, 0);
        }
        for (int i = 0; i < studentsRows.size(); i++) {
            for (int j = 0; j < studentsRows.get(i).size(); j++) {
                cellContents = new Label(studentsRows.get(i).get(j));
                cell = new StackPane();
                cell.setPadding(new Insets(5));
                cell.getChildren().add(cellContents);
                studentsTable.add(cell, j, i + 1);
            }
            int finalI = i;

            Button viewButton = new Button("view Attendance");
            viewButton.setOnAction(e -> {
                try {
                    switchDashboard(dashboardPane, buildAttendancePane(studentsRows.get(finalI).get(4)), titlePane, "Attendance");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            Button editButton = new Button("edit");

            // open a new window to insert values
            editButton.setOnAction(e -> EditButtons.editStudent(studentsRows,finalI));
            Button deleteButton = new Button("delete");
            deleteButton.setOnAction(e -> {
                int pos;
                // not working figure why
//                try {
//                    //actual delete the quiz
//                    QuerySystem.deleteData("Attendance", "StudentUTDID=".concat(studentsRows.get(finalI).get(4)));
//                    QuerySystem.deleteData("Student", "StudentUTDID=".concat(studentsRows.get(finalI).get(4)));
//                    pos = (7 * GridPane.getRowIndex(deleteButton));
//                    System.out.println(pos);
//                    Label cellContentsEmpty = new Label("");
//                    StackPane cellEmpty = new StackPane();
//                    cellEmpty.getChildren().add(cellContentsEmpty);
//                    studentsTable.getChildren().remove(pos,pos+8);
//                    studentsBox.getChildren().remove(pos,pos+8);
//                    for (int j = pos; j < studentsTable.getChildren().size(); j++) {
//                        Node node = studentsTable.getChildren().get(j);
//                        GridPane.setRowIndex(node, GridPane.getRowIndex(node) - 1);
//                    }
//                    throw new RuntimeException(ex);
//                }
            });
            studentsTable.add(viewButton, questionsColumnCount, i + 1);
            studentsTable.add(editButton, questionsColumnCount + 1, i + 1);
            studentsTable.add(deleteButton, questionsColumnCount + 2, i + 1);

        }
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
        List<List<String>> attendRows = ConverterObjToStr.convertObjListToStrList(QuerySystem.selectQuery(new ArrayList<>(Arrays.asList("Attended, DateAndTime, IPAddress, MACID,  StudentUTDID, CourseID", "AttendanceInfo", "StudentUTDID=".concat(studentID), "", "", ""))));
        List<String> attendColumnNames = new ArrayList<>(Arrays.asList("Attended", "DateAndTime", "IPAddress", "MACID", "StudentUTDID", "CourseID"));
        int attendedColumnCount = attendColumnNames.size();
        StackPane cell;
        Label cellContents;
        int i;

        for(i = 0; i < attendedColumnCount; ++i) {
            cellContents = new Label(attendColumnNames.get(i));
            cell = new StackPane();
            cell.setPadding(new Insets(5.0));
            cell.getChildren().add(cellContents);
            attendTable.add(cell, i, 0);
        }

        for(i = 0; i < attendRows.size(); ++i) {
            for (int j = 0; j < (attendRows.get(i)).size(); ++j) {
                cellContents = new Label(attendRows.get(i).get(j));
                cell = new StackPane();
                cell.setPadding(new Insets(5.0));
                cell.getChildren().add(cellContents);
                attendTable.add(cell, j, i + 1);
            }

            int finalI = i;
            Button editButton = new Button("edit");
            editButton.setOnAction(e -> EditButtons.editAttendance(attendRows, finalI));
            attendTable.add(editButton, attendedColumnCount, i+1);
        }

        attendBox.getChildren().add(attendTable);
        attendPane.getChildren().add(attendBox);
        return attendPane;
    }
}
