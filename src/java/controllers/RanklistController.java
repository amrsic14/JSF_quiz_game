/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import classes.UserScore;
import entities.PlayedGame;
import entities.SinglePlayerGame;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Collections;
import java.sql.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.hibernate.Query;
import org.hibernate.Session;
import util.SessionUtil;
import util.Transaction;

/**
 *
 * @author acamr
 */
@ManagedBean
@ViewScoped
@Named(value = "RanklistController")
public class RanklistController implements Serializable {
    private short rankingPage = 0;
    private List<SinglePlayerGame> games;
    private SinglePlayerGame myGame;
    private int myPlacement = 0;

    private List<UserScore> users;
    private List<PlayedGame> myWeekly;
    private List<UserScore> usersMonthly;
    private List<UserScore> usersPast20days;

    @PostConstruct
    public void initScores() {
        if (SessionUtil.getUser() != null) {
            initSinglePlayerGames();
            initWeekly();
        }
        initMonthly();
        initPast20days();
        users = usersPast20days;
    }

    public void initSinglePlayerGames() {
        String myName = SessionUtil.getUser().getUsername();
        java.util.Date today = new java.util.Date();

        Session session = Transaction.openSession();

        Query query = session.createQuery("FROM SinglePlayerGame WHERE gameDate=:date ORDER BY points DESC");
        List results = query.setDate("date", today).list();

        query = session.createQuery("FROM SinglePlayerGame WHERE gameDate=:date AND username=:username");
        myGame = (SinglePlayerGame) query.setDate("date", today).setString("username", myName).uniqueResult();

        Transaction.closeSession();

        boolean playedToday = myGame != null;
        boolean myGameAdded = false;

        games = new LinkedList<>();
        int gameIndex = 1;
        for (Object result : results) {
            if (result instanceof SinglePlayerGame) {
                if (gameIndex > 10) {
                    break;
                }
                SinglePlayerGame game = (SinglePlayerGame) result;
                if (game == myGame) {
                    myGameAdded = true;
                    myPlacement = gameIndex;
                }
                games.add(game);
                gameIndex++;
            }
        }

        if (playedToday && !myGameAdded) {
            games.add(myGame);
            myPlacement = 11;
        }
    }

    public void initWeekly() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        java.sql.Date currentDate = new java.sql.Date(year - 1900, month, day);
        java.sql.Date startDate = new java.sql.Date(currentDate.getTime() - 1000 * 60 * 60 * 24 * 6);

        myWeekly = new LinkedList<>();
        List results = getMyGames(startDate, SessionUtil.getUser().getUsername());

        results.stream().filter((o) -> (o instanceof PlayedGame)).forEachOrdered((o) -> {
            myWeekly.add((PlayedGame) o);
        });
    }

    public void initMonthly() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        Date startDate = new Date(year - 1900, month, 1);

        usersMonthly = new LinkedList<>();
        List results = getGames(startDate);

        countUserScores(usersMonthly, results);
    }

    public void initPast20days() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int hours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int mins = Calendar.getInstance().get(Calendar.MINUTE);
        int seconds = Calendar.getInstance().get(Calendar.SECOND);
        java.sql.Date currentDate = new java.sql.Date(year - 1900, month, day);
        java.sql.Date startDate = new java.sql.Date(currentDate.getTime() - 1000 * (60 * 60 * 24 * 19 + 60 * 60 * hours + 60 * mins + seconds));

        usersPast20days = new LinkedList<>();
        List results = getGames(startDate);

        countUserScores(usersPast20days, results);
    }

    private void countUserScores(List users, List games) {
        Map<String, UserScore> scores = new HashMap<>();

        for (Iterator it = games.iterator(); it.hasNext();) {
            Object obj = it.next();
            if (obj instanceof PlayedGame) {
                PlayedGame game = (PlayedGame) obj;
                addBlue(scores, game);
                addRed(scores, game);
                setGameResults(scores, game);
            }
        }

        scores.entrySet().stream().map((entry) -> {
            entry.getValue().calculateTotalScore();
            return entry;
        }).forEachOrdered((entry) -> {
            users.add(entry.getValue());
        });

        sort(users);
    }

    private void setGameResults(Map<String, UserScore> scores, PlayedGame game) {
        switch (game.getGameResult()) {
            case -1:
                scores.get(game.getBluePlayer()).incDefeats();
                scores.get(game.getRedPlayer()).incVictories();
                break;
            case 0:
                scores.get(game.getBluePlayer()).incDraws();
                scores.get(game.getRedPlayer()).incDraws();
                break;
            case 1:
                scores.get(game.getBluePlayer()).incVictories();
                scores.get(game.getRedPlayer()).incDefeats();
                break;
        }
    }

    private void addBlue(Map<String, UserScore> scores, PlayedGame game) {
        if (!scores.containsKey(game.getBluePlayer())) {
            scores.put(game.getBluePlayer(), new UserScore(game.getBluePlayer()));
        }
    }

    private void addRed(Map<String, UserScore> scores, PlayedGame game) {
        if (!scores.containsKey(game.getRedPlayer())) {
            scores.put(game.getRedPlayer(), new UserScore(game.getRedPlayer()));
        }
    }

    public boolean isGuestMode() {
        return SessionUtil.getUser() == null;
    }

    private void sort(List scores) {
        Collections.sort(scores, (UserScore score1, UserScore score2) -> {
            if (score1.getTotalScore() < score2.getTotalScore()) {
                return 1;
            } else if (score1.getTotalScore() > score2.getTotalScore()) {
                return -1;
            } else {
                return 0;
            }
        });
    }

    private List getMyGames(Date startDate, String username) {
        Session session = Transaction.openSession();

        Query query = session.createQuery("FROM PlayedGame WHERE gameDate>=:startDate and (blue=:username or red=:username)");
        query.setString("username", username);
        List results = query.setDate("startDate", startDate).list();

        Transaction.closeSession();

        return results;
    }

    private List getGames(Date startDate) {
        Session session = Transaction.openSession();

        Query query = session.createQuery("FROM PlayedGame WHERE gameDate>=:startDate");
        List results = query.setDate("startDate", startDate).list();

        Transaction.closeSession();

        return results;
    }

    public String result(PlayedGame game) {
        if (game.getBluePoints() == game.getRedPoints()) {
            return "nereseno";
        }
        if (game.getBluePlayer().equals(SessionUtil.getUser().getUsername())) {
            switch (game.getGameResult()) {
                case -1:
                    return "poraz";
                case 0:
                    return "nereseno";
                case 1:
                    return "pobeda";
            }
        }
        if (game.getRedPlayer().equals(SessionUtil.getUser().getUsername())) {
            switch (game.getGameResult()) {
                case -1:
                    return "pobeda";
                case 0:
                    return "nereseno";
                case 1:
                    return "poraz";
            }
        }
        return "";
    }

    public SinglePlayerGame getMyGame() {
        return myGame;
    }

    public int getMyPlacement() {
        return myPlacement;
    }

    public List<SinglePlayerGame> getGames() {
        return games;
    }

    public boolean isMyRow(SinglePlayerGame game) {
        return myGame != null && game.getUsername().equals(myGame.getUsername());
    }

    public boolean isMyRow(String username, int dummy) {
        try {
            return username.equals(SessionUtil.getUser().getUsername());
        } catch (NullPointerException e) {
            return false;
        }
    }

    public short getRankingPage() {
        return rankingPage;
    }

    public void setRankingPage(short rankingPage) {
        if (rankingPage == 0) {
            users = usersPast20days;
        } else {
            users = usersMonthly;
        }
        this.rankingPage = rankingPage;
    }

    public List<UserScore> getUsers() {
        return users;
    }

    public void setUsers(List<UserScore> users) {
        this.users = users;
    }

    public List<UserScore> getUsersMonthly() {
        return usersMonthly;
    }

    public void setUsersMonthly(List<UserScore> usersMonthly) {
        this.usersMonthly = usersMonthly;
    }

    public List<PlayedGame> getMyWeekly() {
        return myWeekly;
    }

    public void setMyWeekly(List<PlayedGame> myWeekly) {
        this.myWeekly = myWeekly;
    }

    public List<UserScore> getUsersPast20days() {
        return usersPast20days;
    }

    public void setUsersPast20days(List<UserScore> usersPast20days) {
        this.usersPast20days = usersPast20days;
    }

}
