/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.GameOfTheDay;
import entities.SinglePlayerGame;
import java.io.File;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import util.ImageHelper;
import util.SessionUtil;
import util.Transaction;

/**
 *
 * @author acamr
 */
@ManagedBean
@SessionScoped
@Named(value = "GameMenuController")
public class GameMenuController implements Serializable {

    private final String REDIRECT = "?faces-redirect=true";
    private String errorMessage = "";
    private String username = "";

    public String startGameOfTheDay() {
        String username = SessionUtil.getUser().getUsername();
        Date currentDate = new Date();

        Session session = Transaction.openSession();

        SinglePlayerGame playedToday = (SinglePlayerGame) session.createCriteria(SinglePlayerGame.class)
                .add(Restrictions.eq("username", username)).add(Restrictions.eq("gameDate", currentDate)).uniqueResult();

        GameOfTheDay gameOfTheDay = (GameOfTheDay) session.createCriteria(GameOfTheDay.class)
                .add(Restrictions.eq("gameDate", currentDate)).uniqueResult();

        Transaction.closeSession();

        if (playedToday != null) {
            setErrorMessage("Game already played!");
            return "menu";
        } else if (gameOfTheDay == null) {
            setErrorMessage("Game doesn't ready yet!");
            return "menu";
        } else {
            SessionUtil.setGameMode("singleplayer");
            clearMessage();
            return "game" + REDIRECT;
        }
    }

    public String getImageName() {
        try {
            username = SessionUtil.getUser().getFirstName();
            String imgName = SessionUtil.getUser().getUsername();
            File file = new File(ImageHelper.imageFolderPath + imgName + ".jpg");
            if (file.exists()) {
                return "resources/images/users/" + imgName + ".jpg";
            }
            file = new File(ImageHelper.imageFolderPath + imgName + ".png");
            if (file.exists()) {
                return "resources/images/users/" + imgName + ".png";
            }
        } catch (NullPointerException e) {
            username = "Guest";
        }
        return "resources/images/users/noImage.jpg";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String returnToMenu() {
        clearMessage();
        return "menu" + REDIRECT;
    }

    public String hostGame() {
        clearMessage();
        return "user_hosting" + REDIRECT;
    }

    public String loadGames() {
        clearMessage();
        return "user_pendingGames" + REDIRECT;
    }

    public String loadMyGames() {
        clearMessage();
        return "user_userGames" + REDIRECT;
    }

    public String loadSingleplayerRank() {
        clearMessage();
        return "user_singleplayerRank" + REDIRECT;
    }

    public String loadMultiplayerRank() {
        clearMessage();
        return "user_multiplayerRank" + REDIRECT;
    }

    public boolean isGuest() {
        return SessionUtil.getUser() == null;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String message) {
        this.errorMessage = message;
    }

    private void clearMessage() {
        errorMessage = "";
    }

}
