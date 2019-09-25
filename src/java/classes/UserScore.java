/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 *
 * @author acamr
 */
public class UserScore {

    private String username;
    private int totalScore;
    private int victories;
    private int defeats;
    private int draws;

    public UserScore(String username) {
        this.username = username;
        totalScore = victories = defeats = draws = 0;
    }

    public void calculateTotalScore() {
        totalScore = 3 * victories + 1 * draws - 1 * defeats;
    }

    public void incDefeats() {
        defeats++;
    }

    public void incDraws() {
        draws++;
    }

    public void incVictories() {
        victories++;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getVictories() {
        return victories;
    }

    public void setVictories(int vicoties) {
        this.victories = vicoties;
    }

    public int getDefeats() {
        return defeats;
    }

    public void setDefeats(int defeats) {
        this.defeats = defeats;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public float getTotalScore() {
        return totalScore;
    }

}
