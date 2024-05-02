/******************************************************************************
 * Purpose: This class is responsible for parsing the CSV file and inserting the
 * data into the database. The class contains a method that reads the CSV file
 * and populates the table with the data.
 * Written by Luca Donadello for CS4485.0W1 , Project Attendance System,
 * starting >>>><<<<, 2024 NetID: lxd210013
 * ******************************************************************************/
package com.attendance.utilities;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
    // Exception handler with logger
    private static final Logger logger = Logger.getLogger(Parser.class.getName());

    // Method to upload the students CSV file
    public static void studentsUploader(List<String> classesColumnNames) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select CSV File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        // Show open file dialog
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            // Parse the CSV file and populate the table
            parseCSV(selectedFile, classesColumnNames);
        }
    }

    // Method that reads the CSV file and populates the table with the data
    public static void parseCSV(File file, List<String> columnNames) {
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
                // replace the hex 0 with the null char
                line = line.replace("\0", "");
                // replace conversion char with null char
                line = line.replaceAll("\ufffd", "");
                // replace " with null char
                if (line.contains("\"")) {
                    line = line.replace("\"", "");
                    // replace the Name with the null char
                    line = line.replace("Name", "");
                    // add the line to the data
                    data.add(line.split(" ", 0));
                } else
                    // add the line to the data
                    data.add(line.split("\t", 0));
            }
            // Get the first entry to get the index of each info
            String[] entry = data.get(0);
            System.out.println(Arrays.toString(entry));
            // get the index of each info to display
            int firstNamePos = 0;
            int middleNamePos = 0;
            int lastNamePos = 0;
            int studentIdPos = 0;
            int netIDPos = 0;
            int classIDPos = 0;

            for (int i = 0; i < entry.length; i++) {
                if (entry[i].equals("EMPLID") || entry[i].contains("Student"))
                    studentIdPos = i;
                if (entry[i].equals("First Name"))
                    firstNamePos = i;
                if (entry[i].equals("Middle Name"))
                    middleNamePos = i;
                if (entry[i].equals("Last Name"))
                    lastNamePos = i;
                if (entry[i].equals("NetId") || entry[i].contains("Username"))
                    netIDPos = i;
                if (entry[i].equals("Class"))
                    classIDPos = i;
            }
            boolean columnName = true;
            // check get correct data insert in database we can return the pos and array
            for (String[] datum : data) {
                if (columnName) {
                    columnName = false;
                } else {
                    // send the data to the database -- check correctness
                    // First insert data of student in Student Table
                    System.out.println(Arrays.asList(datum[firstNamePos], datum[middleNamePos], datum[lastNamePos],
                            datum[studentIdPos], datum[netIDPos]));
                    // QuerySystem.insertData("Student", columnNames,
                    // Arrays.asList(datum[firstNamePos], datum[middleNamePos], datum[lastNamePos],
                    // datum[studentIdPos], datum[netIDPos]));
                    // //Second insert data inside attendance
                    // QuerySystem.insertData("Attendance", AttendanceColumnNames,
                    // Arrays.asList(datum[studentIdPos],datum[classIDPos])); //placeholders
                }
            }
            // Insert data in the database
        } catch (IOException e) {
            // Log the exception using the Java logger
            logger.severe("An error occurred:");
            logger.severe(e.toString());

        }
    }
}
