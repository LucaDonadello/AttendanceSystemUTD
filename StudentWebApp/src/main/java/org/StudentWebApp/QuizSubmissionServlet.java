// Samuel Benicewicz // Quiz submission Servlet to facilitate the submission and record of attendance to the database

package org.StudentWebApp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "QuizSubmissionServlet", urlPatterns = {"/quiz_submit"})

public class QuizSubmissionServlet extends HttpServlet {
    // POST request
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // Gets the variables needed for attendance recording
        String utdID = request.getParameter("utdID");
        String courseID = request.getParameter("courseID");
        String macID = request.getParameter("macID");
        String ipAddress = request.getParameter("ipAddress");

        // Sending the variables to the DBManager to record the student's attendance in the database
        DBManager.getInstance().takeAttendance(1, ipAddress, macID, Integer.parseInt(utdID), Integer.parseInt(courseID));

        // Writes that the submission was a success, if it was
        PrintWriter writer = response.getWriter();
        response.setCharacterEncoding("utf-8");
        writer.print("Success");
        writer.flush();

        DBManager.getInstance().close(); // Close the database connection
    }
}
