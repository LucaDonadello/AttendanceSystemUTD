package com.attendance.utilities;

import com.attendance.database.QuerySystem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
    private static final Logger logger = Logger.getLogger(Parser.class.getName());

    // method implementing functionality to parse student file and insert new students into student table
    // Inside your studentsUploader() method
    public static void studentsUploader(List<String> classesColumnNames) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select CSV File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );

        // Show open file dialog
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            // Parse the CSV file and populate the table
            parseCSV(selectedFile, classesColumnNames);
        }
    }

    public static void parseCSV(File file, List<String> columnNames) {
        List<String> AttendanceColumnNames = new ArrayList<>(Arrays.asList("StudentUTDID","CourseID")); //most of the attributes at this time are placeholder they are going to dynamically change after client call
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            List<String[]> data = new ArrayList<>();
            String line;
            boolean firstLine = true; // To skip the header line
            boolean secondLine = true; // Skip second line
            while ((line = br.readLine()) != null) {
                //no need to check file is e-learning only need to skip 1st line
                if(line.contains("\"")) {
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
                //replace " with null char
                line = line.replace("\"", "");
                //replace the hex 0 with the null char
                line = line.replace("\0", "");
                //replace conversion char with null char -- check if needed or get rid of
                line = line.replaceAll("\ufffd", "");
                data.add(line.split("\t",0));
            }

            String[] entry = data.get(0);
            //get the index of each info to display -- consider change to array
            int firstNamePos = 0;
            int middleNamePos = 0;
            int lastNamePos = 0;
            int studentIdPos = 0;
            int netIDPos = 0;
            int classIDPos = 0;

            for (int i = 0 ; i < entry.length; i++) {
                if (entry[i].equals("EMPLID") || entry[i].equals("Student ID"))
                    studentIdPos = i;
                if (entry[i].equals("First Name"))
                    firstNamePos = i;
                if (entry[i].equals("Middle Name"))
                    middleNamePos = i;
                if (entry[i].equals("Last Name"))
                    lastNamePos = i;
                if (entry[i].equals("NetId"))
                    netIDPos = i;
                if (entry[i].equals("Class"))
                    classIDPos = i;
            }
            boolean columnName = true;
            //check get correct data insert in database we can return the pos and array
            for (String[] datum : data) {
                if(columnName) {
                    columnName = false;
                }
                else{
                    //send the data to the database -- check correctness
                    //First insert data of student in Student Table
                    QuerySystem.insertData("Student", columnNames, Arrays.asList(datum[firstNamePos], datum[middleNamePos], datum[lastNamePos], datum[studentIdPos], datum[netIDPos]));
                    //Second insert data inside attendance
                    QuerySystem.insertData("Attendance", AttendanceColumnNames, Arrays.asList(datum[studentIdPos],datum[classIDPos])); //placeholders
                }
            }
            //Insert data in the database
        } catch (IOException | SQLException e) {
            // Log the exception using the Java logger
            logger.severe("An error occurred:");
            logger.severe(e.toString());

        }
    }
}
