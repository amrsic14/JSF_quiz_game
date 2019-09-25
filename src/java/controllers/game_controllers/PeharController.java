/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.game_controllers;

import controllers.GameController;
import entities.game_data.PeharQuestion;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author acamr
 */
@ManagedBean
@SessionScoped
@Named(value = "PeharController")
public class PeharController implements Serializable {

    public static int points;
    private static int activeIndex = 0;
    public static boolean peharFinished = false;
    private static String answer;
    private static List<PeharQuestion> peharData;
    private static boolean[] isAnswered;
    private static boolean[] isTrueAnswer;
    private static final int[] pointsValue = {9, 8, 7, 6, 5, 4, 3, 4, 5, 6, 7, 8, 9};

    public static void preparePehar(int idPehar) {
        Session session = db.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Criteria querry = session.createCriteria(PeharQuestion.class);
        List peharResultSet = querry.add(Restrictions.eq("idPehar", idPehar)).addOrder(Order.asc("idPitanja")).list();

        session.getTransaction().commit();
        session.close();

        peharData = new LinkedList<>();
        peharResultSet.stream().filter((obj) -> (obj instanceof PeharQuestion)).forEachOrdered((obj) -> {
            peharData.add((PeharQuestion) obj);
        });

        isAnswered = new boolean[13];
        isTrueAnswer = new boolean[13];
        for (int i = 0; i < 13; i++) {
            isAnswered[i] = false;
            isTrueAnswer[i] = false;
        }
    }

    public static void clearData() {
        points = 0;
        activeIndex = 0;
        peharFinished = false;
        answer = "";
        peharData = null;
        isAnswered = null;
        isTrueAnswer = null;
    }

    public String getQuestion() {
        if (activeIndex < 13) {
            return peharData.get(activeIndex).getPitanje() + ": ";
        } else {
            return "";
        }
    }

    public String getValue(int indexRow, int indexButton) {
        if (indexRow >= activeIndex) {
            return "_";
        } else {
            return peharData.get(indexRow).getOdgovor().charAt(indexButton) + "";
        }
    }

    public String getColor(int index) {
        if (!isAnswered[index]) {
            return "peharButton";
        } else {
            if (isTrueAnswer[index]) {
                return "peharButtonBlue";
            } else {
                return "peharButtonRed";
            }
        }
    }

    public boolean notFinished() {
        return activeIndex < 13;
    }

    public void submit() {
        isAnswered[activeIndex] = true;

        if (answer.equals(peharData.get(activeIndex).getOdgovor())) {
            points += pointsValue[activeIndex];
            isTrueAnswer[activeIndex] = true;
        }

        answer = "";
        
        if (activeIndex++ < 12) {
            GameController.timer = 30;
        } else {
            peharFinished = true;
            GameController.timer = 5;
        }
    }

    public static void skip() {
        isAnswered[activeIndex] = true;
        answer = "";
        
        if (activeIndex++ < 12) {
            GameController.timer = 30;
        } else {
            peharFinished = true;
            GameController.timer = 5;
        }
    }

    public Object[] makeArray(int size) {
        return new Object[size];
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        PeharController.answer = answer;
    }

}
