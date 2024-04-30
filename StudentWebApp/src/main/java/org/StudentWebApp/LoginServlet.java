// Samuel Benicewicz // Login Servlet to facilitate the student login

package org.StudentWebApp;

import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login_submit"})

// Gathers the information from the user-submitted form and send it to the database query to be checked for valid login credentials
public class LoginServlet extends HttpServlet {
    // POST request
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        StringBuffer buffer = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) { // Handle the exception
            System.out.println(e);
        }

        // Creating the login information object
        Gson gson = new Gson();
        LoginObj loginInfo = gson.fromJson(buffer.toString(), LoginObj.class); // Creates the object
        String studentUTDID = loginInfo.utdID; // Gets the UTDID entered
        String password = loginInfo.password; // Gets the password entered

        // Sends the information to the query in the DBManager and returns whether the login credentials were valid
        LoginInfo success = DBManager.getInstance().login(studentUTDID, password);
        if (success == null) {
            DBManager.getInstance().close();
            return;
        }

        // Performs a time check to make sure the quiz is active for the student to take

        // Get duration of quiz
        int hours = success.duration.toLocalTime().getHour();
        int minutes = success.duration.toLocalTime().getMinute();
        int seconds = success.duration.toLocalTime().getSecond();
        int totalSeconds = seconds + (minutes * 60) + (hours * 60 * 60);

        // Calculates teh end time
        LocalTime endTime = success.startTime.toLocalTime().plusSeconds(totalSeconds);

        // If the current time is after the start time and before the calculated end time, proceed with the login
        if (success != null && success.startTime.toLocalTime().isBefore(LocalTime.now()) && endTime.isAfter(LocalTime.now())) {
            PrintWriter writer = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            String infoString = new Gson().toJson(success);
            writer.print(infoString);
            writer.flush();
        }
        DBManager.getInstance().close(); // Close the database connection
    }

    // Main function used for testing
    public static void main(String[] args) {
        String body = "{\"utdID\":\"123456789\",\"password\":\"quizpassword\"}";
        Gson gson = new Gson();
        LoginObj loginInfo = gson.fromJson(body, LoginObj.class);
        String studentUTDID = loginInfo.utdID; // Gets the UTDID entered
        String password = loginInfo.password; // Gets the password entered
    }
}