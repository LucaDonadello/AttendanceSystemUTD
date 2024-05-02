/******************************************************************************
 * Description: This file contains the QuerySystem class which is used to query the database.
 * The QuerySystem class contains methods to execute select, insert, delete, and update queries
 * on the database. The selectQuery method is used to query the database using a select statement
 * and returns the result as a two-dimensional object arraylist. The insertData method is used to
 * insert data into the database. The deleteData method is used to delete data from the database.
 * The updateData method is used to update data in the database.
 * Written by Luca Donadello for CS4485.0W1 , Project Attendance System,
 * starting >>>><<<<, 2024 NetID: lxd210013
 * ******************************************************************************/

package com.attendance.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuerySystem {
    // Dylan farmer
    // helper method to query select statement on database as two-dimensional object arraylist
    // -takes in a list of 6 Strings that may either be blank("") or have a specified conditional value:
    // SELECT    String_1
    // FROM      String_2
    // WHERE     String_3
    // GROUP BY  String_4
    // HAVING    String_5
    // ORDER BY  String_6
    public static List<List<Object>> selectQuery(List<String> selectConditions) throws SQLException {
        Connection con = ConnectionDB.getDBConnection();
        // build SQL query String based on given conditions
        List<String> queryTemplate = new ArrayList<>(Arrays.asList("SELECT String ", "FROM String ", "WHERE String ", "GROUP BY String ", "HAVING String ", "ORDER BY String"));
        // for each String in input list, substitute selectionCondition values into query String;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < selectConditions.size(); i++) {
            String inputString = selectConditions.get(i);
            if (!inputString.isEmpty())
                stringBuilder.append(queryTemplate.get(i).replace("String", inputString));
        }
        String queryString = stringBuilder.toString();
        Statement stmt = con.createStatement();
        // execute query on database
        ResultSet rs = stmt.executeQuery(queryString);
        List<List<Object>> tableList = new ArrayList<>();
        // retrieve body of the table
        while (rs.next()) { // while there are more rows in table
            List<Object> rowList = new ArrayList<>();
            for (int i = 1; i < rs.getMetaData().getColumnCount() + 1; i++) // for each column in the table, retrieve cell contents
                rowList.add(rs.getObject(i));
            tableList.add(rowList);
        }
        con.close(); // close database connection
        return tableList;
    }

    // helper method to insert data into the database
    public static void insertData(String tableName, List<String> columnNames, List<String> values) throws SQLException {
        Connection con = ConnectionDB.getDBConnection();
        StringBuilder queryBuilder = new StringBuilder();
        //create the base of the query
        queryBuilder.append("INSERT INTO ").append(tableName).append(" (");
        //add the column names to the query
        for (int i = 0; i < columnNames.size(); i++) {
            queryBuilder.append(columnNames.get(i));
            if (i != columnNames.size() - 1) {
                queryBuilder.append(", ");
            }
        }
        //add the values to the query
        queryBuilder.append(") VALUES (");
        for (int i = 0; i < values.size(); i++) {
            queryBuilder.append("'").append(values.get(i)).append("'");
            if (i != values.size() - 1) {
                queryBuilder.append(", ");
            }
        }
        queryBuilder.append(")");
        //query complete ready to execute
        String query = queryBuilder.toString();
        Statement stmt = con.createStatement();
        stmt.executeUpdate(query);
        con.close();
        System.out.println(query);
    }

    // helper method to delete data from the database
    public static void deleteData(String tableName, String condition) throws SQLException {
        Connection con = ConnectionDB.getDBConnection();
        StringBuilder queryBuilder = new StringBuilder();
        //create the base of the query
        queryBuilder.append("DELETE FROM ").append(tableName);
        if (!condition.isEmpty()) {
            //add the condition to the query
            queryBuilder.append(" WHERE ").append(condition);
        }
        //query complete ready to execute
        String query = queryBuilder.toString();
        Statement stmt = con.createStatement();
        stmt.executeUpdate(query);
        con.close();
        System.out.println(query);
    }

    // helper method to update data in the database
    public static void updateData(String tableName, List<String> columnNames, List<String> values, String condition) throws SQLException {
        Connection con = ConnectionDB.getDBConnection();
        StringBuilder queryBuilder = new StringBuilder();
        //create the base od the query
        queryBuilder.append("UPDATE ").append(tableName).append(" SET ");
        //add the column names and values to the query
        for (int i = 0; i < columnNames.size(); i++) {
            queryBuilder.append(columnNames.get(i)).append(" = '").append(values.get(i)).append("'");
            if (i != columnNames.size() - 1) {
                queryBuilder.append(", ");
            }
        }
        //add the condition to the query
        if (!condition.isEmpty()) {
            queryBuilder.append(" WHERE ").append(condition);
        }
        //query complete ready to execute
        String query = queryBuilder.toString();
        Statement stmt = con.createStatement();
        stmt.executeUpdate(query);
        con.close();
        System.out.println(query);
    }
}