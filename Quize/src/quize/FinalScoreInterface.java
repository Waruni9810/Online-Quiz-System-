package quize;

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
import java.awt.event.WindowEvent;

public class FinalScoreInterface {
    private static boolean scoreShown = false;

    public static void showScore(String scoreMsg, String review) {
        if (!scoreShown) {
            JFrame scoreFrame = new JFrame("Final Score");
            scoreFrame.setLayout(new BorderLayout());
            scoreFrame.getContentPane().setBackground(new Color(255, 253, 186));

            // Create a multiline label with HTML formatting
         String scoreLabelText = "<html><div style='text-align: center; font-size: 16px;color: navy;'><b>Final Score</b></div><br>" +
                    "<div style='font-size: 14x; color: Black;'> " + scoreMsg.split("\n")[0] + "</div><br>" +
                    "<div style='font-size: 14px;color: Black;'> " + scoreMsg.split("\n")[1] + "</div><br>" +
                    "<div style='font-size: 14px;color: navy;'> " + review + "</div></html>";

            JLabel scoreLabel = new JLabel(scoreLabelText, SwingConstants.CENTER);
            scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
            scoreLabel.setForeground(new Color(41, 128, 185));

            scoreFrame.add(scoreLabel, BorderLayout.CENTER);

            scoreFrame.setSize(400, 250);
            scoreFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            scoreFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    scoreShown = true; // Set the flag to true when the window is closed
                }
            });

            scoreFrame.setVisible(true);
        }
    }
}












