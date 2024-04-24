// Samuel Benicewicz // Quiz Servlet to facilitate submitting the quiz correctness to to the database

package org.StudentWebApp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Gathers the information from the user-submitted quiz form
public class QuizServlet extends HttpServlet {
    // GET request
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int attended = Integer.parseInt(request.getParameter("attended"));
        String ipAddress = request.getParameter("ipaddress");
        String macID = request.getParameter("macid");
        int studentUTDID = Integer.parseInt(request.getParameter("studentutdid"));
        int courseID = Integer.parseInt(request.getParameter("courseID"));

        // Sends the information to the query in the DBManager
        DBManager.getInstance().takeAttendance(attended, ipAddress, macID, studentUTDID, courseID);
    }
}