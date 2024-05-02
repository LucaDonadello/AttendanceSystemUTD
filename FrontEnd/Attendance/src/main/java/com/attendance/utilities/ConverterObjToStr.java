/******************************************************************************
 * Description: This class is used to convert a two-dimensional arraylist of 
 * Objects to one containing Strings. The ConverterObjToStr class contains a 
 * method to convert a two-dimensional arraylist of Objects to one containing 
 * Strings. The method takes in a two-dimensional arraylist of Objects and returns
 * a two-dimensional arraylist of Strings.
 * Written Dylan Farmer for CS4485.0W1 , Project Attendance System,
 * starting 22/03/2024
 * ******************************************************************************/

package com.attendance.utilities;

import java.util.ArrayList;
import java.util.List;

public class ConverterObjToStr {
    // helper method to convert a two-dimensional arraylist of Objects to one
    // containing Strings
    public static List<List<String>> convertObjListToStrList(List<List<Object>> inputList) {
        List<List<String>> stringList = new ArrayList<>();
        for (List<Object> objects : inputList) {
            List<String> rowList = new ArrayList<>();
            for (Object object : objects)
                if (object != null)
                    rowList.add(object.toString());
                else
                    rowList.add("NULL"); // convert null objects to be displayed as "NULL"
            stringList.add(rowList);
        }
        return stringList;
    }
}
