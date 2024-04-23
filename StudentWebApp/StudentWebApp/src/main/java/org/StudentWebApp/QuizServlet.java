// Samuel Benicewicz // Quiz Servlet to facilitate submitting the quiz correctness to to the database

package org.StudentWebApp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class QuizServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String answer = request.getParameter("answer");
        if (answer.equals("42")) {
            response.getWriter().write("Correct");
        } else {
            response.getWriter().write("Incorrect");
        }
    }
}