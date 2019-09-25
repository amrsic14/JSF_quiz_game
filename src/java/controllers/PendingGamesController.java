/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.ActiveGame;
import entities.User;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import util.SessionUtil;
import util.Transaction;

/**
 *
 * @author acamr
 */
@ManagedBean
@ViewScoped
@Named(value = "PendingGamesController")
public class PendingGamesController implements Serializable {

    List<String> games;

    @PostConstruct
    public void initGames() {
        games = HostController.gameQueue;
    }

    public String join(String bluePlayer) {
        User redPlayer = SessionUtil.getUser();
        ActiveGame game = new ActiveGame(bluePlayer, redPlayer.getUsername());

        HostController.gameQueue.remove(bluePlayer);
        HostController.games.add(game);
        SessionUtil.setGameMode("multiplayer");
        SessionUtil.setPlayerSide("red");

        return "game_notSupported?faces-redirect=true";
    }

    public List<String> getGames() {
        return games;
    }

}
