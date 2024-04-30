// Samuel Benicewicz // Class for the login information gathered from login query

package org.StudentWebApp;

import java.sql.Time;

public class LoginInfo {

    // Variables for storing and using the information gathered fro the login query
    public String firstName;
    public String lastName;
    public int quizID;
    public String className;
    public int courseID;
    public Time duration;
    public Time startTime;

    // Constructor
    public LoginInfo(String firstName, String lastName, int quizID, String className, int courseID, Time duration, Time startTime) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.quizID = quizID;
        this.className = className;
        this.courseID = courseID;
        this.duration = duration;
        this.startTime = startTime;
    }
}
