// Samuel Benicewicz // Database Manager to connect to the database and run the queries

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
    public LoginInfo login(String username, String password) {
        Connection connect = connect(); // Creates connection to the database
        if (connect == null) { // If there is no connection, send a message and return false
            System.out.println("Failed to connect to the database");
            return null;
        }
        try {
            Statement stmnt = connect.createStatement(); // Create a statement
            ResultSet rs = stmnt.executeQuery( // Execute the login query and verify the student's credentials
                    "SELECT FirstName, LastName, Quiz.QuizID, Course.ClassName, Course.CourseID FROM Student\n" +
                            "INNER JOIN Attendance ON Student.StudentUTDID = Attendance.StudentUTDID\n" +
                            "INNER JOIN Course ON Attendance.CourseID = Course.CourseID\n" +
                            "INNER JOIN Quiz ON Course.QuizID = Quiz.QuizID\n" +
                            "WHERE Student.StudentUTDID = '"+ username +"' AND Quiz.Password_ = '"+ password +"'");
            if (rs.next()) { // Test if the login was successful and print message
                // Get the login information needed from the query
                LoginInfo info = new LoginInfo(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getInt(5));
                System.out.println("Login successful");
                return info;
            } else {
                System.out.println("Login failed");
                return null;
            }
        } catch (Exception e) { // Handle exception
            System.out.println("Exception: " + e);
        }
        return null;
    }

    public Quiz getQuizQuestions(int quizID) {
        Connection connect = connect(); // Creates connection to the database
        if (connect == null) { // If there is no connection, send a message and return false
            System.out.println("Failed to connect to the database");
            return null;
        }
        try {
            Statement stmnt = connect.createStatement(); // Create a statement
            ResultSet rs = stmnt.executeQuery( // Execute the login query and verify the student's credentials
                    "SELECT Question, Answer FROM Quiz\n" +
                            "    JOIN QuizBank ON QuizBank.QuestionBankID = Quiz.QuizBankID\n" +
                            "    JOIN QuizQuestion ON QuizQuestion.QuestionID = QuizBank.QuizQuestionID\n" +
                            "    JOIN AnswerSet ON AnswerSet.QuestionID = QuizQuestion.QuestionID\n" +
                            "    WHERE Quiz.QuizID = " + quizID);

            Quiz quiz = new Quiz(); // Creating the quiz
            while (rs.next()) {
                quiz.addAnswer(rs.getString(1), rs.getString(2)); // Adding the answers to the quiz questions
            }
            return quiz;
        } catch (Exception e) { // Handle Exception
            System.out.println("Exception: " + e);
        }
        return null;
    }

    // Submits the quiz to the database, records the student's attendance, and
    // records which questions were wrong
    public void takeAttendance(int attended, String ipAddress, String macID, int studentUTDID, int courseID) {
        Connection connect = connect(); // Creates connection to the database
        if (connect == null) { // If there is no connection, send a message and return false
            System.out.println("Failed to connect to the database");
            return;
        }
        try {
            Statement stmnt = connect.createStatement(); // Create a statement
            // Execute the attendance submission query and insert into the database
            String query = String.format("INSERT INTO AttendanceInfo (Attended, DateAndTime, IPAddress, MacID, StudentUTDID, CourseID) " +
                    "VALUES (%s, NOW(), '%s', '%s', %s, %s)", attended, ipAddress, macID, studentUTDID, courseID);
            stmnt.executeUpdate(query); // Execute the query to submit the attendance and quiz answers to the database
        } catch (Exception e) { // Handle exception
            System.out.println("Exception: " + e);
        }
    }

    // Main function used for testing
    public static void main(String[] args) {
        DBManager dbManager = new DBManager();
        dbManager.login("admin", "admin");
        dbManager.close();
    }
}
