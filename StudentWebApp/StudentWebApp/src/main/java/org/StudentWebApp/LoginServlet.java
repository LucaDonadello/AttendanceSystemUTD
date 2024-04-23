// Samuel Benicewicz // Login Servlet to facilitate the student login

package org.StudentWebApp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        boolean success = DBManager.getInstance().login(username, password);
        System.out.println("After Bool");
        if (success) {
            response.getWriter().write("Login successful");
        } else {
            response.getWriter().write("Login failed");
        }
        DBManager.getInstance().close();
    }
}