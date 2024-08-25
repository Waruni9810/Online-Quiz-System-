package quize;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import SampleQuestions.Question;
import SampleQuestions.SampleQuestions;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class QuizServer {
    private static final int PORT = 12345;
    private static List<Socket> clients = new ArrayList<>();
    private static List<Question> questions = SampleQuestions.getQuestions();
    private static AtomicInteger questionIndex = new AtomicInteger(0);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running and waiting for connections...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                clients.add(clientSocket);
                System.out.println("Client connected: " + clientSocket);
                new QuizSession(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Question getNextQuestion() {
        int index = questionIndex.getAndIncrement();
        if (index < questions.size()) {
            return questions.get(index);
        } else {
            return null;
        }
    }

    public static int getQuestionCount() {
        return questions.size();
    }
}



