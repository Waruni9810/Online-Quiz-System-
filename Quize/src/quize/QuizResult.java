/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ASUS
 */
package quize;

public class QuizResult {
    private String scoreMsg;
    private String review;

    public QuizResult(String scoreMsg, String review) {
        this.scoreMsg = scoreMsg;
        this.review = review;
    }

    public String getScoreMsg() {
        return scoreMsg;
    }

    public String getReview() {
        return review;
    }
}

