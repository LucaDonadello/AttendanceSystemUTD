// Samuel Benicewicz // Database Managerto connect to the database and run the queries

package org.StudentWebApp;

import java.sql.*;

// This class is responsible for connecting to the database and executing queries
public class DBManager {

    // Private variables global within the class
    private static DBManager instance;
    private Connection conn;

    // Constructor
    public DBManager() {
        instance = this;
    }

    // Getting an instance of the database connection
    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    // Connect to the database and return the connection
    public Connection connect() {
        try {

            // If there is already an active connection, return the connection
            if (conn != null) {
                return conn;
            }


            // Connect to the database
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection( // Make the connection to the database
                    "jdbc:mysql://roundhouse.proxy.rlwy.net:21552/AttendanceApp",
                    "root",
                    "vWoCqlMXjrXADJqmiWGXlrZNoNrwInxI");
            return conn; // Return the connection
        } catch (Exception e) { // Handle exception
            System.out.println("Exception: " + e);
        }
        return null;
    }

    // Close the connection to the database when done
    public void close() {

        // If there is an active connection, try closing it
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        conn = null;
    }

    // Checks if the user's login credentials are correct
    public boolean login(String username, String password) {
        Connection connect = connect(); // Creates connection to the database
        if (connect == null) { // If there is no connection, send a message and return false
            System.out.println("Failed to connect to the database");
            return false;
        }
        try {
            Statement stmnt = connect.createStatement(); // Create a statement
            // TODO: Change query to pull StudentUTDID and Password from frontend and insert into query
            ResultSet rs = stmnt.executeQuery( // Execute the login query and verify the student's credentials
                    "SELECT FirstName, LastName, Quiz.QuizID FROM Student\n" +
                            "INNER JOIN Course on Student.StudentUTDID = Course.StudentUTDID\n" +
                            "INNER JOIN Quiz on Course.QuizID = Quiz.QuizID\n" +
                            "WHERE Student.StudentUTDID = 123456789 AND Quiz.Password_ = 'quizpassword'");
            if (rs.next()) { // Test if the login was successful and print message
                System.out.println("Login successful");
                return true;
            } else {
                System.out.println("Login failed");
                return false;
            }
        } catch (Exception e) { // Handle exception
            System.out.println("Exception: " + e);
        }
        return false;
    }

    // Submits the quiz to the database, records the student's attendance, and
    // records which questions were wrong
    public void takeAttendance(int attended, String macID, String ipAddress, int studentUTDID, int courseID) {
        Connection connect = connect(); // Creates connection to hte database
        if (connect == null) { // If there is no connection, send a message and return false
            System.out.println("Failed to connect to the database");
            return;
        }
        try {
            Statement stmnt = connect.createStatement();
            // TODO: Fix the query to work, given the new database changes
            stmnt.executeUpdate( // Execute the query to submit the attendance and quiz answers to the database
                    "INSERT INTO Attendance (Attended, MacID, IPAddress, StudentUTDID, CourseID, DateAndTime) VALUES ("
                            + attended + ", '" + macID + "', '" + ipAddress + "', " + studentUTDID + ", " + courseID
                            + ", NOW()");
            System.out.println("Attendance submitted successfully");
        } catch (Exception e) { // Handle exception
            System.out.println("Exception: " + e);
        }
    }

    // Run the program
    public static void main(String[] args) {
        DBManager dbManager = new DBManager();
        dbManager.login("admin", "admin");
//        Connection conn = dbManager.connect();
//        if (conn != null) {
//            System.out.println("Connected to the database");
//        } else {
//            System.out.println("Failed to connect to the database");
//        }
        dbManager.close();
    }
}
