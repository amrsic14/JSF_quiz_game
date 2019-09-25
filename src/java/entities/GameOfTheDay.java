/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.game_data.AnagramData;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author acamr
 */
@Entity
@Table(name="game_of_the_day")
public class GameOfTheDay implements Serializable {
    
    @Id
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name="gameDate")
    private Date gameDate;
    
    @OneToOne
    @JoinColumn(name="idAnagram")
    private AnagramData anagram;

    @Column(name="idPehar")
    private int pehar;
    
    @Column(name="idHangman")
    private int hangman;

    private boolean played;
    
    public GameOfTheDay() {}
    
    public GameOfTheDay(Date gameDate, AnagramData anagram, int pehar, int hangman) {
        this.gameDate = gameDate;
        this.anagram = anagram;
        this.pehar = pehar;
        this.hangman = hangman;
    }
    
    public Date getGameDate() {
        return gameDate;
    }

    public void setGameDate(Date gameDate) {
        this.gameDate = gameDate;
    }

    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }

    public AnagramData getAnagram() {
        return anagram;
    }

    public void setAnagram(AnagramData anagram) {
        this.anagram = anagram;
    }

    public int getPehar() {
        return pehar;
    }

    public void setPehar(int pehar) {
        this.pehar = pehar;
    }

    public int getHangman() {
        return hangman;
    }

    public void setHangman(int hangman) {
        this.hangman = hangman;
    }

}
