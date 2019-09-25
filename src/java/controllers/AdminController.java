/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.GameOfTheDay;
import entities.RegistrationRequest;
import entities.User;
import entities.game_data.AnagramData;
import entities.game_data.HangmanData;
import entities.game_data.PeharQuestion;
import util.ImageHelper;
import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import util.Transaction;

/**
 *
 * @author acamr
 */
@ManagedBean
@ViewScoped
@Named(value = "AdminController")
public class AdminController implements Serializable {

    private short adminPageTab = 0; //0-Reg Requests, 1 - Game of the Day
    private List<RegistrationRequest> requests;

    private String outputMessage = "Please Choose a Date";
    private final Date currentDate = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
    private GameOfTheDay gameOnDate;
    private Date pickedDate;

    private String pickedAnagram;
    private List<String> anagrams;
    private Map<String, AnagramData> anagramsMap;

    private String pickedHangman;
    private List<String> hangmans;
    private Map<String, HangmanData> hangmansMap;

    private int pickedPehar;
    private List<Integer> pehari;

    @PostConstruct
    public void initialize() {
        initRequests();
        initAnagram();
        initPehar();
        initHangman();
    }

    public void initAnagram() {
        Session session = Transaction.openSession();

        List anagramsResult = session.createQuery("FROM AnagramData").list();

        Transaction.closeSession();

        anagramsMap = new HashMap<>();
        anagrams = new LinkedList<>();
        for (Object anagram : anagramsResult) {
            if (anagram instanceof AnagramData) {
                AnagramData anagramData = (AnagramData) anagram;
                String id = anagramData.getRec() + " - " + anagramData.getResenje();
                anagrams.add(id);
                anagramsMap.put(id, anagramData);
            }
        }
    }

    public void initHangman() {
        Session session = Transaction.openSession();

        List anagramsResult = session.createQuery("FROM HangmanData").list();

        Transaction.closeSession();

        hangmansMap = new HashMap<>();
        hangmans = new LinkedList<>();
        for (Object hangman : anagramsResult) {
            if (hangman instanceof HangmanData) {
                HangmanData hangmanData = (HangmanData) hangman;
                String id = hangmanData.getRec();
                hangmans.add(id);
                hangmansMap.put(id, hangmanData);
            }
        }
    }

    public void initPehar() {
        Session session = Transaction.openSession();

        Criteria query = session.createCriteria(PeharQuestion.class);
        query.addOrder(Order.desc("idPehar"));
        query.setMaxResults(1);
        PeharQuestion pq = (PeharQuestion) query.uniqueResult();

        Transaction.closeSession();

        pehari = new LinkedList<>();

        for (int size = pq.getIdPehar(); size > 0; size--) {
            pehari.add(0, size);
        }

    }

    public void initRequests() {
        Session session = Transaction.openSession();

        List requestsResult = session.createQuery("FROM RegistrationRequest").list();

        Transaction.closeSession();

        requests = new LinkedList<>();
        requestsResult.stream().filter((request) -> (request instanceof RegistrationRequest)).forEachOrdered((request) -> {
            requests.add((RegistrationRequest) request);
        });
    }

    public void submit() {
        Session session = Transaction.openSession();

        if (gameOnDate == null) {
            GameOfTheDay game = new GameOfTheDay(pickedDate, anagramsMap.get(pickedAnagram), pickedPehar, hangmansMap.get(pickedHangman).getIdHangman());
            session.save(game);
            outputMessage = "You can still change this game";
        } else {
            gameOnDate = (GameOfTheDay) session.get(GameOfTheDay.class, pickedDate);
            gameOnDate.setAnagram(anagramsMap.get(pickedAnagram));
            gameOnDate.setPehar(pickedPehar);
            gameOnDate.setHangman(hangmansMap.get(pickedHangman).getIdHangman());
        }

        Transaction.closeSession();
    }

    public boolean getCanSubmit() {
        return pickedDate == null || (gameOnDate != null && gameOnDate.isPlayed());
    }

    public void accept(RegistrationRequest request) {
        User user = new User(request, User.UserType.User);

        Session session = Transaction.openSession();

        Query removeFromRequestsQuery = session.createQuery("DELETE FROM RegistrationRequest WHERE username=:username");
        removeFromRequestsQuery.setString("username", request.getUsername());
        removeFromRequestsQuery.executeUpdate();

        session.save(user);

        Transaction.closeSession();

        requests.remove(request);
    }

    public void refuse(RegistrationRequest request) {
        Session session = Transaction.openSession();

        Query removeFromRequestsQuery = session.createQuery("DELETE FROM RegistrationRequest WHERE username=:username");
        removeFromRequestsQuery.setString("username", request.getUsername());
        removeFromRequestsQuery.executeUpdate();

        Transaction.closeSession();

        if (request.isHasImage()) {
            new File(ImageHelper.imageFolderPath + request.getUsername() + ".jpg").delete();
            new File(ImageHelper.imageFolderPath + request.getUsername() + ".png").delete();
        }
    }

    public String hasImage(RegistrationRequest req) {
        if (req.isHasImage()) {
            return "Yes";
        } else {
            return "No";
        }
    }

    public List<RegistrationRequest> getRequests() {
        return requests;
    }

    public short getAdminPageTab() {
        return adminPageTab;
    }

    public void setAdminPageTab(short adminPageTab) {
        this.adminPageTab = adminPageTab;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public boolean canShowPage(short pageId) {
        return pageId == adminPageTab;
    }

    public String getOutputMessage() {
        return outputMessage;
    }

    public Date getPickedDate() {
        return pickedDate;
    }

    public String getPickedAnagram() {
        return pickedAnagram;
    }

    public void setPickedAnagram(String pickedAnagram) {
        this.pickedAnagram = pickedAnagram;
    }

    public List<String> getAnagrams() {
        return anagrams;
    }

    public void setAnagrams(List<String> anagrams) {
        this.anagrams = anagrams;
    }

    public int getPickedPehar() {
        return pickedPehar;
    }

    public void setPickedPehar(int pickedPehar) {
        this.pickedPehar = pickedPehar;
    }

    public List<Integer> getPehari() {
        return pehari;
    }

    public void setPehari(List<Integer> pehari) {
        this.pehari = pehari;
    }

    public void setPickedDate(Date chosenDate) {
        Session session = Transaction.openSession();

        gameOnDate = (GameOfTheDay) session.get(GameOfTheDay.class, chosenDate);
        if (gameOnDate != null) {
            if (gameOnDate.isPlayed()) {
                outputMessage = "You can't change this game";
            } else {
                outputMessage = "You can still change this game";
            }
        } else {
            outputMessage = "You can make game for this date";
        }

        Transaction.closeSession();

        this.pickedDate = chosenDate;
    }

    public String getPickedHangman() {
        return pickedHangman;
    }

    public void setPickedHangman(String pickedHangman) {
        this.pickedHangman = pickedHangman;
    }

    public List<String> getHangmans() {
        return hangmans;
    }

    public void setHangmans(List<String> hangmans) {
        this.hangmans = hangmans;
    }

}
