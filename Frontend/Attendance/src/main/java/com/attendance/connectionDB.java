package com.attendance;
import java.sql.*;

public class connectionDB {
    static final String URL = "jdbc:mysql://roundhouse.proxy.rlwy.net:21552/AttendanceApp";
    static final String USERNAME = "root";
    static final String PASSWORD = "vWoCqlMXjrXADJqmiWGXlrZNoNrwInxI";
    public static String getDBURL() { return URL; }
    public static String getDBUsername() { return USERNAME; }
    public static String getDBPassword() { return PASSWORD; }
    public static Connection getDBConnection() throws SQLException { return DriverManager.getConnection(URL, USERNAME, PASSWORD); }
}
