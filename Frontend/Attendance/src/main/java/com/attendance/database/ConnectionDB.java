package com.attendance.database;
import java.sql.*;

public class ConnectionDB {
    static  String URL = "jdbc:mysql://roundhouse.proxy.rlwy.net:21552/AttendanceApp";
    static  String USERNAME = "root";
    static  String PASSWORD = "vWoCqlMXjrXADJqmiWGXlrZNoNrwInxI";
    public static String getDBURL() { return URL; }
    public static String getDBUsername() { return USERNAME; }
    public static String getDBPassword() { return PASSWORD; }
    public static void setDBURL(String url) { URL = url; }
    public static void setDBUsername(String username) { USERNAME = username; }
    public static void setDBPassword(String password) { PASSWORD = password;}
    public static Connection getDBConnection() throws SQLException { return DriverManager.getConnection(URL, USERNAME, PASSWORD); }
}
