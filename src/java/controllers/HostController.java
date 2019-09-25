/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.ActiveGame;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import util.SessionUtil;

/**
 *
 * @author acamr
 */
@ManagedBean
@Named(value = "HostController")
@ViewScoped
public class HostController implements Serializable {

    private String message = "";
    private boolean hosting = false;
    private boolean isGameReady = false;
    private String username = null;
    public static List<ActiveGame> games = new LinkedList<>();
    public static List<String> gameQueue = new LinkedList<>();

    @PostConstruct
    public void init() {
        username = SessionUtil.getUser().getUsername();
    }

    public void checkIfAccepted() {
        if (!hosting) {
            return;
        }

        ActiveGame active = null;
        for (ActiveGame ag : games) {
            if (ag.getBlue().equals(username)) {
                active = ag;
                break;
            }
        }
        
        if (active != null) {
            SessionUtil.setGameMode("multiplayer");
            SessionUtil.setPlayerSide("blue");
            message = "Your game is ready ";
            games.remove(active);
            isGameReady = true;
        }
    }

    public boolean isGameReady() {
        return isGameReady;
    }

    public void startHosting() {
        if (hosting) {
            return;
        }
        gameQueue.add(username);
        hosting = true;
        message = "Waiting for someone to join";
    }

    public void stopHosting() {
        hosting = false;
        gameQueue.remove(username);
        message = "";
    }

    @PreDestroy
    public void removeRequest() {
        gameQueue.remove(username);
        hosting = false;
        message = "";
    }

    public String getMessage() {
        return message;
    }

}
