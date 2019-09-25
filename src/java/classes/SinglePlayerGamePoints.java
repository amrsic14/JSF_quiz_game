/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import controllers.GameController;
import java.io.Serializable;

/**
 *
 * @author acamr
 */
public class SinglePlayerGamePoints implements Serializable {

    private String game;
    private int points;

    public SinglePlayerGamePoints(String game, int points) {
        this.game = game;
        this.points = points;
    }

    public SinglePlayerGamePoints(GameController.GameView game, int points) {
        this(game.name(), points);
    }

    public String getGame() {
        return game;
    }

    public int getPoints() {
        return points;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public void setPoints(int points) {
        this.points = points;
    }
    
}
