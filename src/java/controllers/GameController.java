/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import classes.SinglePlayerGamePoints;
import controllers.game_controllers.AnagramController;
import controllers.game_controllers.HangmanController;
import controllers.game_controllers.MojBrojController;
import controllers.game_controllers.PeharController;
import controllers.game_controllers.ZanimljivaGeografijaController;
import entities.GameOfTheDay;
import entities.SinglePlayerGame;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.hibernate.Query;
import org.hibernate.Session;
import org.primefaces.PrimeFaces;
import util.SessionUtil;
import util.Transaction;

/**
 *
 * @author acamr
 */
@ManagedBean
@SessionScoped
@Named(value = "GameController")
public class GameController implements Serializable {

    public enum GameView {
        Waiting, Anagram, MojBroj, Hangman, ZanimljivaGeografija, Pehar, GameOver
    }

    private static GameView gameView;
    private GameView nextGame;
    private GameView currentGame;
    public static int timer;
    private String playerUsername;
    private List<SinglePlayerGamePoints> gamePoints = new LinkedList<>();

    @PostConstruct
    public void initGame() {
        playerUsername = SessionUtil.getUser().getUsername();
        currentGame = null;
        gameView = GameView.Waiting;
        nextGame = GameView.Anagram;
        prepareNextGame();
    }

    public boolean isGameInProgress() {
        return !(gameView == GameView.Waiting || gameView == GameView.GameOver);
    }

    public int getTimer() {
        return timer;
    }

    public String getUpdate() {
        if (GameController.isMyPage(GameController.GameView.MojBroj) && (MojBrojController.indexChosen < 7)) {
            return "";
        } else if (!ZanimljivaGeografijaController.canUpdateTimer) {
            return "";
        } else {
            return "timer";
        }
    }

    public void setTimer(int timer) {
        GameController.timer = timer;
    }

    public String getTimerColor() {
        return timer < 20 ? "#e2574c" : "#51bba8";
    }

    public static boolean isMyPage(GameView myView) {
        return myView == gameView;
    }

    public void thick() {
        isNextGameReady();
        isCurrentGameOver();
    }

    public void isNextGameReady() {
        if (gameView != GameView.Waiting) {
            return;
        }
        
        gameView = currentGame = nextGame;
        PrimeFaces.current().executeScript("window.location.reload(true)");

        setNextGame();
    }

    private void setNextGame() {
        switch (currentGame) {
            case Anagram:
                nextGame = GameView.MojBroj;
                timer = 60;
                break;
            case MojBroj:
                nextGame = GameView.Hangman;
                timer = 60;
                break;
            case Hangman:
                nextGame = GameView.ZanimljivaGeografija;
                timer = 60;
                break;
            case ZanimljivaGeografija:
                nextGame = GameView.Pehar;
                timer = 120;
                break;
            case Pehar:
                nextGame = GameView.GameOver;
                timer = 30;
                break;
            case GameOver:
                saveGameToDB();
                break;
        }
    }

    private void saveGameToDB() {
        int allPoints = 0;
        allPoints = gamePoints.stream().map((game) -> game.getPoints()).reduce(allPoints, Integer::sum);

        SinglePlayerGame finishedGame = new SinglePlayerGame(playerUsername, allPoints, new Date());
        Session session = Transaction.openSession();

        session.save(finishedGame);

        Transaction.closeSession();
    }

    public void isCurrentGameOver() {
        if (!isGameInProgress()) {
            return;
        }

        timer--;

        if (timer == 0) {
            if (currentGame == GameView.Pehar && !PeharController.peharFinished) {
                PeharController.skip();
                PrimeFaces.current().executeScript("window.location.reload(true)");
            } else if (!ZanimljivaGeografijaController.canUpdateTimer) {
                timer = 60;
            } else {
                finished();
            }
        }
    }

    public void finished() {
        int myPoints = 0;

        switch (currentGame) {
            case Anagram:
                myPoints = AnagramController.getPoints();
                AnagramController.clearData();
                break;
            case MojBroj:
                myPoints = MojBrojController.getPoints();
                MojBrojController.clearData();
                break;
            case Hangman:
                myPoints = HangmanController.points;
                HangmanController.clearData();
                break;
            case ZanimljivaGeografija:
                myPoints = ZanimljivaGeografijaController.points;
                ZanimljivaGeografijaController.clearData();
                break;
            case Pehar:
                myPoints = PeharController.points;
                PeharController.clearData();
                break;
            default:
                break;
        }

        gamePoints.add(new SinglePlayerGamePoints(currentGame, myPoints));

        gameView = GameView.Waiting;
    }

    public void prepareNextGame() {
        Session session = Transaction.openSession();

        if (nextGame == GameView.Anagram) {
            Query query = session.createQuery("FROM GameOfTheDay WHERE gameDate=:currentDate");
            GameOfTheDay game = (GameOfTheDay) query.setDate("currentDate", new Date()).uniqueResult();

            AnagramController.prepareAnagram(game);
            PeharController.preparePehar(game.getPehar());
            HangmanController.prepareHangman(game.getHangman());

            game.setPlayed(true);
        }

        Transaction.closeSession();
    }

    public String applyColor(SinglePlayerGamePoints game) {
        return "coloredDefeat";
    }

    public int getGamePoints(int game) {
        if (gamePoints.size() < 5) {
            return 0;
        } else {
            return gamePoints.get(game).getPoints();
        }
    }

    public int getSum() {
        int allPoints = 0;
        allPoints = gamePoints.stream().map((p) -> p.getPoints()).reduce(allPoints, Integer::sum);
        return allPoints;
    }

}
