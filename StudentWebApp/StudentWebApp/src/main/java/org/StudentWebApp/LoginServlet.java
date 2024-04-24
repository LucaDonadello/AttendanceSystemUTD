// Samuel Benicewicz // Login Servlet to facilitate the student login

package org.StudentWebApp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login_submit"})

public class LoginServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String studentUTDID = request.getParameter("utd-id");
        String password = request.getParameter("pwd");
        System.out.println("ID: " + studentUTDID + ", Password: " + password);
        boolean success = DBManager.getInstance().login(studentUTDID, password);
        if (success) {
            //response.getWriter().write("Login successful");
            response.sendRedirect("/quiz");
        } else {
            response.getWriter().write("Login failed");
        }
        DBManager.getInstance().close();
    }
}