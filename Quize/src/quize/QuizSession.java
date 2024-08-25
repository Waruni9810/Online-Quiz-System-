/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quize;
import SampleQuestions.Question;

import java.io.*;
import java.net.Socket;


public class QuizSession extends Thread {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private boolean scoreDisplayed = false; // Add a flag to track if score is displayed

    public QuizSession(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("Quiz session started for client: " + clientSocket);

            // Read the index number sent by the client
            String indexNumber = in.readLine();
            System.out.println("Client index number: " + indexNumber);

            // Run the quiz and get the score
            int score = runQuiz();

            // Close the client socket
            clientSocket.close();
            System.out.println("Quiz session ended for client: " + clientSocket);

            if (!scoreDisplayed) {
                // Pass the score, index number, and review to FinalScoreInterface only if not displayed yet
                String scoreMsg = "Index Number: " + indexNumber + "\nYour Score: " + score;
                String review = generateReview(score);
                FinalScoreInterface.showScore(scoreMsg, review);
                scoreDisplayed = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int runQuiz() throws IOException {
        int score = 0; // Initialize the score

        while (true) {
            Question question = QuizServer.getNextQuestion();
            if (question == null) {
                // No more questions, end the session
                break;
            }

            out.println(question.getText());

            String[] options = question.getOptions();
            for (int i = 0; i < 4; i++) {
                out.println(options[i]);
            }

            String clientAnswer = in.readLine();
            System.out.println("Client answer: " + clientAnswer);

            if (clientAnswer.equals("-1")) {
                // Handle timeout
                break;
            }

            // Check if the client's answer is correct (existing code)
            int correctOption = question.getCorrectOption();
            int selectedOption = Integer.parseInt(clientAnswer);
            if (selectedOption == correctOption) {
                score++; // Increment the score for correct answers
            }
        }

        return score;
    }

    private String generateReview(int score) {
        if (score >= 7) {
            return "Congratulations! You did great!";
        } else if (score >= 5) {
            return "Not bad! You can do better next time.";
        } else {
            return "Keep practicing. You can improve!";
        }
    }
}










