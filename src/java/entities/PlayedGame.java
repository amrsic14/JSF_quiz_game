/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author acamr
 */
@Entity
@Table(name="played_game")
public class PlayedGame implements Serializable {
    
    @Id
    @Column(name="gameId")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int gameId;
    
    @Column(name="blue")
    private String bluePlayer;
    
    @Column(name="red")
    private String redPlayer;
    
    @Column(name="pointsBlue")
    private int bluePoints;
    
    @Column(name="pointsRed")
    private int redPoints;
    
    @Column(name="gameResult")
    private short gameResult;
    
    @Column(name="gameDate")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date gameDate;

    public PlayedGame(){}

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getBluePlayer() {
        return bluePlayer;
    }

    public void setBluePlayer(String bluePlayer) {
        this.bluePlayer = bluePlayer;
    }

    public String getRedPlayer() {
        return redPlayer;
    }

    public void setRedPlayer(String redPlayer) {
        this.redPlayer = redPlayer;
    }

    public int getBluePoints() {
        return bluePoints;
    }

    public void setBluePoints(int bluePoints) {
        this.bluePoints = bluePoints;
    }

    public int getRedPoints() {
        return redPoints;
    }

    public void setRedPoints(int redPoints) {
        this.redPoints = redPoints;
    }

    public short getGameResult() {
        return gameResult;
    }

    public void setGameResult(short gameResult) {
        this.gameResult = gameResult;
    }

    public Date getGameDate() {
        return gameDate;
    }

    public void setGameDate(Date gameDate) {
        this.gameDate = gameDate;
    }
    
}
