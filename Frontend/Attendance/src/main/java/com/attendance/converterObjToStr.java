package com.attendance;

import java.util.ArrayList;
import java.util.List;

public class converterObjToStr {
    // helper method to convert a two-dimensional arraylist of Objects to one containing Strings
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

