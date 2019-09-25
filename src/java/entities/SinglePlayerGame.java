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
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author acamr
 */
@Entity
@Table(name="singleplayergame")
public class SinglePlayerGame implements Serializable {
    @Id
    @Column(name="username")
    String username;
    
    @Id
    @Column(name="gameDate")
    @Temporal(javax.persistence.TemporalType.DATE)
    Date gameDate;
    
    @Column(name="points")
    int points;
    
    public SinglePlayerGame(String username, int points, Date date){
        this.gameDate = date;
        this.username = username;
        this.points = points;
    }
    
    public SinglePlayerGame(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getGameDate() {
        return gameDate;
    }

    public void setGameDate(Date gameDate) {
        this.gameDate = gameDate;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
    
}

