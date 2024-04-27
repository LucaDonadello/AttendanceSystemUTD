// Samuel Benicewicz // Quiz Question Servlet to facilitate gathering the quiz questions from the database

package org.StudentWebApp;

import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "QuizQuestionServlet", urlPatterns = {"/quiz_questions"})

public class QuizQuestionServlet extends HttpServlet {
    // GET request
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // Gets the variables needed for finding the quiz questions
        String quizID = request.getParameter("quiz-id");

        // Sends the information for the query in DBManager for gathering the questions
        Quiz quiz = DBManager.getInstance().getQuizQuestions(Integer.parseInt(quizID));

        if (quiz != null) {
            PrintWriter writer = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            String infoString = new Gson().toJson(quiz);
            writer.print(infoString);
            writer.flush();
        } else {}

        DBManager.getInstance().close(); // Close the database connection
    }
}
