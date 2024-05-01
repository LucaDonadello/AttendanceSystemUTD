// Samuel Benicewicz // Quiz Servlet to facilitate formatting the quiz questions received from the database

package org.StudentWebApp;

import java.util.ArrayList;
import java.util.HashMap;

public class Quiz {

    // Using a hash map to store the questions and their answers
    public HashMap<String, ArrayList<String>> questions;
    public int numberOfQuestions;

    // Constructor
    public Quiz() {
        this.questions = new HashMap<>();
    }

    // Adding the answers to each question
    public void addAnswer(String question, String answer, int numberOfQuestions) {
        // Checks if the question was already added. If not, add the question
        if (questions.containsKey(question) == false) {
            questions.put(question, new ArrayList<>());
        }
        questions.get(question).add(answer); // Adds answer to the question
        this.numberOfQuestions = numberOfQuestions;
    }
}
