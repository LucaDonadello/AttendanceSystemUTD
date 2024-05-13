/******************************************************************************
 * Purpose: This class is responsible for parsing the CSV file and inserting the
 * data into the database. The class contains a method that reads the CSV file
 * and populates the table with the data.
 * Written by Luca Donadello and Mohammed Basar for CS4485.0W1 , Project Attendance System,
 * starting 31/03/2024, 2024 NetID: lxd210013, mfb220000
 * ******************************************************************************/

package com.attendance.utilities;

import com.attendance.database.QuerySystem;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {

    // method implementing functionality to parse student file and insert new
    // students into student table
    // Inside your studentsUploader() method
    public static void studentsUploader(List<String> classesColumnNames, String classID) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select CSV File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        // Show open file dialog
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            // Parse the CSV file and populate the table
            parseCSV(selectedFile, classesColumnNames, classID);
        }
    }

    // Method that reads the CSV file and populates the table with the data
    public static void parseCSV(File file, List<String> columnNames, String classID) {
        // list of column names for the attendance table
        List<String> AttendanceColumnNames = new ArrayList<>(Arrays.asList("StudentUTDID", "CourseID"));
        // Read the CSV file
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            List<String[]> data = new ArrayList<>();
            String line;
            boolean firstLine = true; // To skip the header line
            boolean secondLine = true; // Skip second line
            while ((line = br.readLine()) != null) {
                // no need to check file is e-learning only need to skip 1st line
                if (line.contains("\"")) {
                    firstLine = false;
                    secondLine = false;
                }
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip the header line
                }
                if (secondLine) {
                    secondLine = false;
                    continue; // Skip the header line
                }
                // Split the line by tab
                // replace " with null char
                line = line.replace("\"", "");
                // replace the hex 0 with the null char
                line = line.replace("\0", "");
                // replace conversion char with null char -- check if needed or get rid of
                line = line.replaceAll("\ufffd", "");
                data.add(line.split("\t", 0));
            }
            // Get the first entry to get the index of each info
            String[] entry = data.get(0);
            // System.out.println(Arrays.toString(entry)); --> Testing purposes
            // get the index of each info to display
            int firstNamePos = 0;
            int middleNamePos = 0;
            int lastNamePos = 0;
            int studentIdPos = 0;
            int netIDPos = 0;
            int classIDPos = 0;

            // Iterate through the array of entry
            for (int i = 0; i < entry.length; i++) {
                // Check if the current entry matches certain strings and assign positions
                // accordingly
                if (entry[i].equals("EMPLID") || entry[i].equals("Student ID"))
                    studentIdPos = i;
                if (entry[i].equals("First Name"))
                    firstNamePos = i;
                if (entry[i].equals("Middle Name"))
                    middleNamePos = i;
                if (entry[i].equals("Last Name"))
                    lastNamePos = i;
                if (entry[i].equals("NetId") || entry[i].equals("Username"))
                    netIDPos = i;
                if (entry[i].equals("Class"))
                    classIDPos = i;
            }

            boolean columnName = true;
            // Check if it's the first row (column names)
            for (String[] datum : data) {
                if (columnName) {
                    columnName = false;
                } else {
                    // send the data to the database
                    // First insert data of student in Student Table
                    System.out.println(Arrays.asList(datum[firstNamePos], datum[middleNamePos], datum[lastNamePos],
                            datum[studentIdPos], datum[netIDPos]));
                    QuerySystem.insertData("Student", columnNames,
                            Arrays.asList(datum[firstNamePos], datum[middleNamePos], datum[lastNamePos],
                                    datum[studentIdPos], datum[netIDPos]));
                    // Second insert data inside attendance
                    // if classIDPos is empty means e-learning file need to retrieve it from class
                    // pane
                    if (classIDPos == 0)
                        QuerySystem.insertData("Attendance", AttendanceColumnNames,
                                Arrays.asList(datum[studentIdPos], classID)); // placeholders
                    else
                        QuerySystem.insertData("Attendance", AttendanceColumnNames,
                                Arrays.asList(datum[studentIdPos], datum[classIDPos])); // placeholders
                }
            }
        } catch (IOException | SQLException e) {
            // Log any exceptions using the Java logger
            // Display error message if SQL query fails
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Server Error");
            alert.setHeaderText("An error occurred while updating data in the SQL server.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            throw new RuntimeException(e);
        }
    }
}
