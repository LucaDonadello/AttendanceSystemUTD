package org.StudentWebApp;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "HelloServlet", urlPatterns = {"/hello"})

public class HelloServlet extends HttpServlet {
    @Override public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        ServletOutputStream out = res.getOutputStream();
        out.write("Hello".getBytes());
        out.flush();
        out.close();
    }
}
