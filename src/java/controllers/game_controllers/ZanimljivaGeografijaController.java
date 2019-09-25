/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.game_controllers;

import controllers.GameController;
import controllers.SupervisorController;
import entities.game_data.zamiljiva_geografija.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.hibernate.Query;
import org.hibernate.Session;
import org.primefaces.PrimeFaces;
import util.Transaction;

/**
 *
 * @author acamr
 */
@ManagedBean
@SessionScoped
@Named(value = "ZanimljivaGeografijaController")
public class ZanimljivaGeografijaController implements Serializable {

    private static String drzava = "";
    private static String grad = "";
    private static String jezero = "";
    private static String planina = "";
    private static String reka = "";
    private static String zivotinja = "";
    private static String biljka = "";
    private static String muzickaGrupa = "";

    private static char startLetter;
    Random r = new Random();

    public static boolean[] isReviewed = {true, true, true, true, true, true, true, true};
    public static boolean[] isTrue = {false, false, false, false, false, false, false, false};
    public static String[] optionName = {"Drzava", "Grad", "Jezero", "Planina", "Reka", "Zivotinja", "Biljka", "MuzickaGrupa"};
    public static String[] optionEntity = {"State", "City", "Lake", "Mountain", "River", "Animal", "Plant", "MusicGroup"};
    
    private static String message = "";

    public static int points = 0;

    private static boolean sentToSupervisor = false;
    private static boolean checkedBySupervisor = false;
    private static boolean finished = false;
    
    public static boolean canUpdateTimer = true;

    private static List<Integer> reviewed = new LinkedList<>();

    @PostConstruct
    public void init() {        
        do {
            startLetter = (char) (r.nextInt(26) + 'a');
        } while (startLetter == 'q' || startLetter == 'w' || startLetter == 'y' || startLetter == 'x');
        for (int i = 0; i < 8; i++) {
            isReviewed[i] = true;
            isTrue[i] = false;
        }
    }

    public static void clearData() {
        clearInputs();
        isReviewed = new boolean[]{true, true, true, true, true, true, true, true};
        isTrue = new boolean[]{false, false, false, false, false, false, false, false};
        message = "";
        points = 0;
        sentToSupervisor = false;
        checkedBySupervisor = false;
        finished = false;
        reviewed = new LinkedList<>();
    }
    
    private static void clearInputs() {
        drzava = "";
        grad = "";
        jezero = "";
        planina = "";
        reka = "";
        zivotinja = "";
        biljka = "";
        muzickaGrupa = "";
    }
    
    private int check(Session session, String option, int index) {
        int pointss = 0;
        Query query = session.createQuery("FROM " + optionEntity[index] + " WHERE name=:name");
        query.setString("name", option.toLowerCase());
        Object obj = query.uniqueResult();

        if (option.equals("")) {

        } else if (obj != null && option.toLowerCase().charAt(0) == startLetter) {
            pointss += 2;
        } else {
            reviewed.add(index);
            delegateToSupervisor(index, option);
        }

        return pointss;
    }

    public void sendToSupervisor() {
        canUpdateTimer = false;
        PrimeFaces.current().executeScript("window.location.reload(true)");
        
        String[] option = {drzava, grad, jezero, planina, reka, zivotinja, biljka, muzickaGrupa};

        Session session = db.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        for (int i = 0; i < 8; i++) {
            points += check(session, option[i], i);
        }

        session.getTransaction().commit();
        session.close();

        sentToSupervisor = true;
    }

    private void delegateToSupervisor(int index, String option) {
        Session session = Transaction.openSession();

        GeografijaCheck check = new GeografijaCheck();
        check.setIndeks(index);
        check.setOdgovor(optionName[index] + " na slovo - " + startLetter + " : " + option);
        check.setProvereno(false);
        isReviewed[index] = false;
        session.save(check);
        message = "Wait for supervisor to check answers";

        Transaction.closeSession();
    }

    public void checkAnswers() {
        if (reviewed.isEmpty() && !finished) {
            finished = true;
            message = "Wait for next game";
            canUpdateTimer = true;
            PrimeFaces.current().executeScript("window.location.reload(true)");
            GameController.timer = 5;
        } else if (sentToSupervisor && !finished && SupervisorController.doneChecking) {

            while (!reviewed.isEmpty()) {
                int i = reviewed.remove(0);
                if (isTrue[i]) {
                    points += 4;
                }
            }

            checkedBySupervisor = true;
            message = "Supervisor finished checking";
            canUpdateTimer = true;
            finished = true;
            PrimeFaces.current().executeScript("window.location.reload(true)");
            GameController.timer = 5;
        }
    }

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        ZanimljivaGeografijaController.drzava = drzava;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        ZanimljivaGeografijaController.grad = grad;
    }

    public String getJezero() {
        return jezero;
    }

    public void setJezero(String jezero) {
        ZanimljivaGeografijaController.jezero = jezero;
    }

    public String getPlanina() {
        return planina;
    }

    public void setPlanina(String planina) {
        ZanimljivaGeografijaController.planina = planina;
    }

    public String getReka() {
        return reka;
    }

    public void setReka(String reka) {
        ZanimljivaGeografijaController.reka = reka;
    }

    public String getZivotinja() {
        return zivotinja;
    }

    public void setZivotinja(String zivotinja) {
        ZanimljivaGeografijaController.zivotinja = zivotinja;
    }

    public String getBiljka() {
        return biljka;
    }

    public void setBiljka(String biljka) {
        ZanimljivaGeografijaController.biljka = biljka;
    }

    public String getMuzickaGrupa() {
        return muzickaGrupa;
    }

    public void setMuzickaGrupa(String muzickaGrupa) {
        ZanimljivaGeografijaController.muzickaGrupa = muzickaGrupa;
    }

    public char getStartLetter() {
        return startLetter;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        ZanimljivaGeografijaController.message = message;
    }

    public boolean isSentToSupervisor() {
        return sentToSupervisor;
    }

    public void setSentToSupervisor(boolean sentToSupervisor) {
        ZanimljivaGeografijaController.sentToSupervisor = sentToSupervisor;
    }

    public boolean isCheckedBySupervisor() {
        return checkedBySupervisor;
    }

    public void setCheckedBySupervisor(boolean checkedBySupervisor) {
        ZanimljivaGeografijaController.checkedBySupervisor = checkedBySupervisor;
    }

}
