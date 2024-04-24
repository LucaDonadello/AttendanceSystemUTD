// Samuel Benicewicz // Login Servlet to facilitate the student login

package org.StudentWebApp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login_submit"})

// Gathers the information from the user-submitted form and send it to the database query to be checked for valid login credentials
public class LoginServlet extends HttpServlet {
    // POST request
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String studentUTDID = request.getParameter("utd-id"); // Gets the user ID entered
        String password = request.getParameter("pwd"); // Gets the password entered

        // Sends the information to the query in the DBManager and returns whether the login credentials were valid
        boolean success = DBManager.getInstance().login(studentUTDID, password);
        if (success) { // If valid login, redirect to the quiz page
            response.getWriter().write("Login successful");
            response.sendRedirect("/quiz"); // Redirect to quiz page
        } else { // If invalid login, fail the login
            response.getWriter().write("Login failed");
            // TODO: have it redirect back to the login page
        }
        DBManager.getInstance().close(); // Close the database connection
    }
}