/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ASUS
 */ 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class QuizClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    private static Socket socket;
    private static PrintWriter out;
    private static BufferedReader in;
    private static JFrame frame;
    private static JTextArea questionTextArea;
    private static JRadioButton[] optionRadioButtons;
    private static ButtonGroup optionButtonGroup;
    private static JButton submitButton;
    private static JLabel timeLabel;
    private static int score = 0;
    private static String indexNumber; // Store the client's index number
    private static Timer quizTimer;
    private static boolean quizCompleted = false;
    private static final int QUIZ_DURATION = 10 * 60 * 1000; // 10 minutes in milliseconds
    private static long quizStartTime;

    public static void main(String[] args) {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            frame = new JFrame("Quiz Client");
            frame.setLayout(new BorderLayout());
            frame.getContentPane().setBackground(Color.LIGHT_GRAY);

            questionTextArea = new JTextArea();
            questionTextArea.setEditable(false);
            questionTextArea.setFont(new Font("Arial", Font.BOLD, 16));

            JPanel optionsPanel = new JPanel(new GridLayout(4, 1));
            optionRadioButtons = new JRadioButton[4];
            optionButtonGroup = new ButtonGroup();

            for (int i = 0; i < 4; i++) {
                optionRadioButtons[i] = new JRadioButton();
                optionRadioButtons[i].setFont(new Font("Arial", Font.PLAIN, 14));
                optionsPanel.add(optionRadioButtons[i]);
                optionButtonGroup.add(optionRadioButtons[i]);
            }

            JPanel controlPanel = new JPanel(new BorderLayout());

            submitButton = new JButton("Submit");
            submitButton.setFont(new Font("Arial", Font.BOLD, 14));
            submitButton.setEnabled(false);
            submitButton.setBackground(Color.GREEN);

            timeLabel = new JLabel("Time Left: 10:00", SwingConstants.RIGHT);
            timeLabel.setFont(new Font("Arial", Font.PLAIN, 14));

            controlPanel.add(timeLabel, BorderLayout.WEST);
            controlPanel.add(submitButton, BorderLayout.EAST);

            frame.add(questionTextArea, BorderLayout.NORTH);
            frame.add(optionsPanel, BorderLayout.CENTER);
            frame.add(controlPanel, BorderLayout.SOUTH);

            submitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int selectedOption = -1;
                    for (int i = 0; i < 4; i++) {
                        if (optionRadioButtons[i].isSelected()) {
                            selectedOption = i;
                            break;
                        }
                    }

                    if (selectedOption >= 0) {
                        out.println(selectedOption); // Send the selected option to the server
                        submitButton.setEnabled(false);

                        // Clear the radio button selection
                        optionButtonGroup.clearSelection();

                        // Request the next question
                        requestNextQuestion();
                    }
                }
            });

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 400);
            frame.setVisible(true);

            // Prompt for the client's index number
            indexNumber = JOptionPane.showInputDialog("Enter your index number (ex-: itbin-2110-xxxx):");
            out.println(indexNumber); // Send the index number to the server

            // Initial request for the first question
            requestNextQuestion();

            // Start the quiz timer
            startQuizTimer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startQuizTimer() {
        quizStartTime = System.currentTimeMillis();
        
        quizTimer = new Timer();
        quizTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!quizCompleted) {
                    long elapsedTime = System.currentTimeMillis() - quizStartTime;
                    long remainingTime = QUIZ_DURATION - elapsedTime;
                    if (remainingTime > 0) {
                        long minutes = (remainingTime / 1000) / 60;
                        long seconds = (remainingTime / 1000) % 60;
                        String timeLeft = String.format("%02d:%02d", minutes, seconds);
                        timeLabel.setText("Time Left: " + timeLeft);
                    } else {
                        quizCompleted = true;
                        submitButton.setEnabled(false);
                        out.println("-1"); // Signal that the time is up (-1 represents a timeout)
                        showScoreAndReview();
                        quizTimer.cancel(); // Stop the timer
                    }
                }
            }
        }, 0, 1000); // Update every second
    }

    private static void requestNextQuestion() {
        try {
            String question = in.readLine();
            if (question == null || question.isEmpty()) {
                // Quiz ended
                questionTextArea.setText("Quiz Ended");
                submitButton.setEnabled(false);
                showScoreAndReview();
                return;
            }
            questionTextArea.setText(question);

            for (int i = 0; i < 4; i++) {
                String option = in.readLine();
                optionRadioButtons[i].setText(option);
            }
            submitButton.setEnabled(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void showScoreAndReview() {
        try {
            String scoreMsg = in.readLine();
            String review = in.readLine();

            JOptionPane.showMessageDialog(frame, "Submit All the Answers" , "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            socket.close();
            frame.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
