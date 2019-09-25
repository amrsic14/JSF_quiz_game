/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.game_controllers;

import controllers.GameController;
import entities.game_data.HangmanData;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author acamr
 */
@ManagedBean
@SessionScoped
@Named(value = "HangmanController")
public class HangmanController implements Serializable {

    public static int points = 0;
    
    private static int imageIndex = 1;
    private static HangmanData hangman;
    private String[] chars = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "R", "S", "T", "U", "V", "Z"};
    private static String[] hangmanChars;
    private static boolean[] isOpened;
    private static boolean[] isDisabled = new boolean[22];
    
    public static void clearData() {
        points = 0;
        imageIndex = 1;
        hangman = null;
        hangmanChars = null;
        isOpened = null;
        isDisabled = new boolean[22];
    }

    public static void prepareHangman(int hangmanId) {
        Session session = db.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Criteria querry = session.createCriteria(HangmanData.class);
        hangman = (HangmanData) querry.add(Restrictions.eq("idHangman", hangmanId)).uniqueResult();

        session.getTransaction().commit();
        session.close();

        hangmanChars = hangman.getRec().split("");
        for (int i = 0; i < hangmanChars.length; i++) {
            hangmanChars[i] = hangmanChars[i].toUpperCase();
        }

        isOpened = new boolean[hangmanChars.length];
        for (int i = 0; i < isOpened.length; i++) {
            isOpened[i] = hangmanChars[i].equals(" ");
        }

    }

    public String getLetter(int index) {
        if (isOpened[index]) {
            return hangmanChars[index];
        } else {
            return "X";
        }
    }

    public void chooseLetter(String letter, int index) {
        isDisabled[index] = true;
        boolean foundLetter = false;
        for (int i = 0; i < hangmanChars.length; i++) {
            if (hangmanChars[i].equals(letter)) {
                foundLetter = true;
                isOpened[i] = true;
            }
        }

        boolean isFinished = true;
        for (int i = 0; i < isOpened.length; i++) {
            if (!isOpened[i]) {
                isFinished = false;
                break;
            }
        }

        if (isFinished) {
            disableAllButtons();
            calculatePoints();
            GameController.timer = 5;
        }

        if (!foundLetter) {
            imageIndex++;
        }

        if (imageIndex == 5) {
            disableAllButtons();
            calculatePoints();
            GameController.timer = 5;
        }

    }

    private void calculatePoints() {
        switch (imageIndex) {
            case 1:
                HangmanController.points = 20;
                break;
            case 2:
                HangmanController.points = 15;
                break;
            case 3:
                HangmanController.points = 10;
                break;
            case 4:
                HangmanController.points = 5;
                break;
            case 5:
                HangmanController.points = 0;
                break;
            default:
                HangmanController.points = 0;
                break;
        }
    }

    private void disableAllButtons() {
        for (int i = 0; i < isDisabled.length; i++) {
            isDisabled[i] = true;
        }
    }

    public String getButtonClass(int index) {
        if (hangmanChars[index].equals(" ")) {
            return "hangmanButtonNone";
        } else {
            return isOpened[index] ? "hangmanButton1" : "hangmanButtonNotOpen";
        }
    }

    public String getImageIndex() {
        return "" + imageIndex;
    }

    public boolean isDisabled(int index) {
        return isDisabled[index];
    }

    public String[] getChars() {
        return chars;
    }

    public void setChars(String[] chars) {
        this.chars = chars;
    }

    public String[] getHangmanChars() {
        return hangmanChars;
    }

    public void setHangmanChars(String[] hangmanChars) {
        HangmanController.hangmanChars = hangmanChars;
    }

}
