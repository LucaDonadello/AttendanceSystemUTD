package com.attendance;
import java.sql.*;

public class connectionDB {
    public static void main(String[] args) {
        try {
            String URL = "jdbc:mysql://roundhouse.proxy.rlwy.net:21552/AttendanceApp";
            String USER = "root";
            String PASSWORD = "vWoCqlMXjrXADJqmiWGXlrZNoNrwInxI";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Student");
            while (rs.next()){
                System.out.println(rs.getString(1));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}