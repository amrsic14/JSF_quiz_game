/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.game_controllers.ZanimljivaGeografijaController;
import entities.game_data.AnagramData;
import entities.game_data.HangmanData;
import entities.game_data.PeharQuestion;
import entities.game_data.zamiljiva_geografija.Animal;
import entities.game_data.zamiljiva_geografija.City;
import entities.game_data.zamiljiva_geografija.GeografijaCheck;
import entities.game_data.zamiljiva_geografija.Lake;
import entities.game_data.zamiljiva_geografija.Mountain;
import entities.game_data.zamiljiva_geografija.MusicGroup;
import entities.game_data.zamiljiva_geografija.Plant;
import entities.game_data.zamiljiva_geografija.River;
import entities.game_data.zamiljiva_geografija.State;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
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
@SessionScoped
@Named(value = "SupervisorController")
public class SupervisorController implements Serializable {

    private short activetTabIndex = 0; //0-anagram, 1-hangman, 2-geografija, 3-pehar

    private String anagramRec = "";
    private String anagramResenje = "";

    private String hangmanWord = "";

    private String peharQuestion1;
    private String peharQuestion2;
    private String peharQuestion3;
    private String peharQuestion4;
    private String peharQuestion5;
    private String peharQuestion6;
    private String peharQuestion7;
    private String peharQuestion8;
    private String peharQuestion9;
    private String peharQuestion10;
    private String peharQuestion11;
    private String peharQuestion12;
    private String peharQuestion13;

    private String peharAnswer1;
    private String peharAnswer2;
    private String peharAnswer3;
    private String peharAnswer4;
    private String peharAnswer5;
    private String peharAnswer6;
    private String peharAnswer7;
    private String peharAnswer8;
    private String peharAnswer9;
    private String peharAnswer10;
    private String peharAnswer11;
    private String peharAnswer12;
    private String peharAnswer13;

    private List<GeografijaCheck> requests = new LinkedList();

    public static boolean doneChecking = false;
    private String message = "";

    @PostConstruct
    public void init() {
        Session session = Transaction.openSession();

        Query query = session.createQuery("FROM GeografijaCheck");
        List results = query.list();

        Transaction.closeSession();

        results.stream().filter((res) -> (res instanceof GeografijaCheck)).forEachOrdered((res) -> {
            boolean hasItem = false;
            for (GeografijaCheck gc : requests) {
                if (gc.getOdgovor().equals(((GeografijaCheck) res).getOdgovor())) {
                    hasItem = true;
                }
            }
            if (!hasItem) {
                requests.add((GeografijaCheck) res);
            }
        });
    }

    public String getMessage() {
        return message;
    }

    public String getStyleClass() {
        switch (activetTabIndex) {
            case 0:
                return "supervisor-anagram";
            case 1:
                return "supervisor-hangman";
            case 2:
                return "supervisor-geografija";
            case 3:
                return "supervisor-pehar";
            default:
                return "";
        }
    }

    public void refresh() {
        init();
        SupervisorController.doneChecking = requests.isEmpty();
    }

    public void acceptGeografija(GeografijaCheck request) {
        ZanimljivaGeografijaController.isTrue[request.getIndeks()] = true;
        ZanimljivaGeografijaController.isReviewed[request.getIndeks()] = true;

        String className = request.getOdgovor().split(" ")[0].trim();

        String entityValue = request.getOdgovor().split(": ")[1].trim().toLowerCase();

        Object object = null;
        switch (className) {
            case "Drzava":
                object = (State) new State(entityValue);
                break;
            case "Grad":
                object = (City) new City(entityValue);
                break;
            case "Jezero":
                object = (Lake) new Lake(entityValue);
                break;
            case "Planina":
                object = (Mountain) new Mountain(entityValue);
                break;
            case "Reka":
                object = (River) new River(entityValue);
                break;
            case "Zivotinja":
                object = (Animal) new Animal(entityValue);
                break;
            case "Biljka":
                object = (Plant) new Plant(entityValue);
                break;
            case "MuzickaGrupa":
                object = (MusicGroup) new MusicGroup(entityValue);
                break;
        }

        Session session = Transaction.openSession();

        session.save(object);
        session.delete(request);

        Transaction.closeSession();
        requests.remove(request);
    }

    public void refuseGeografija(GeografijaCheck request) {
        ZanimljivaGeografijaController.isReviewed[request.getIndeks()] = true;

        Session session = Transaction.openSession();

        session.delete(request);

        Transaction.closeSession();
        requests.remove(request);
    }

    public void doneChecking() {
        if (requests.isEmpty()) {
            doneChecking = true;
        }
    }

    public void submitAnagram() {
        Session session = Transaction.openSession();

        Criteria criteria = session.createCriteria(AnagramData.class);
        criteria.addOrder(Order.desc("id"));
        criteria.setMaxResults(1);
        AnagramData anagram = (AnagramData) criteria.uniqueResult();

        AnagramData data = new AnagramData();
        data.setIdAnagram(String.valueOf(Integer.parseInt(anagram.getIdAnagram()) + 1));
        data.setRec(anagramRec);
        data.setResenje(anagramResenje);
        session.save(data);

        Transaction.closeSession();

        anagramRec = "";
        anagramResenje = "";
    }

    public void submitHangman() {
        Session session = Transaction.openSession();

        Criteria criteria = session.createCriteria(HangmanData.class);
        criteria.addOrder(Order.desc("idHangman"));
        criteria.setMaxResults(1);
        HangmanData hangman = (HangmanData) criteria.uniqueResult();

        HangmanData data = new HangmanData();
        data.setIdHangman(hangman.getIdHangman() + 1);
        data.setRec(hangmanWord);
        session.save(data);

        Transaction.closeSession();

        hangmanWord = "";
    }

    private String[] getQuestions() {
        return new String[]{
            peharQuestion1,
            peharQuestion2,
            peharQuestion3,
            peharQuestion4,
            peharQuestion5,
            peharQuestion6,
            peharQuestion7,
            peharQuestion8,
            peharQuestion9,
            peharQuestion10,
            peharQuestion11,
            peharQuestion12,
            peharQuestion13};
    }

    private String[] getAnswers() {
        return new String[]{
            peharAnswer1,
            peharAnswer2,
            peharAnswer3,
            peharAnswer4,
            peharAnswer5,
            peharAnswer6,
            peharAnswer7,
            peharAnswer8,
            peharAnswer9,
            peharAnswer10,
            peharAnswer11,
            peharAnswer12,
            peharAnswer13};
    }

    private PeharQuestion[] getPeharData() {
        return new PeharQuestion[]{
            new PeharQuestion(),
            new PeharQuestion(),
            new PeharQuestion(),
            new PeharQuestion(),
            new PeharQuestion(),
            new PeharQuestion(),
            new PeharQuestion(),
            new PeharQuestion(),
            new PeharQuestion(),
            new PeharQuestion(),
            new PeharQuestion(),
            new PeharQuestion(),
            new PeharQuestion()};
    }

    private boolean checkPeharAnswers(String[] answers) {
        for (int i = 0; i < 12; i++) {
            char[] str1 = answers[i].toCharArray();
            char[] str2 = answers[i + 1].toCharArray();

            if (str1.length < str2.length) {
                char[] temp = str1;
                str1 = str2;
                str2 = temp;
            }

            boolean contain = false;
            for (char c2 : str2) {
                for (char c1 : str1) {
                    if (c1 == c2) {
                        contain = true;
                    }
                }
                if (!contain) {
                    message = "Wrong asnwer input for answers " + (i + 1) + " and " + (i + 2);
                    return false;
                } else {
                    contain = false;
                }
            }
        }
        return true;
    }

    public void submitPehar() {
        String[] peharAnswers = getAnswers();

        if (!checkPeharAnswers(peharAnswers)) {
            return;
        }

        String[] peharQuestions = getQuestions();
        PeharQuestion[] data = getPeharData();

        Session session = Transaction.openSession();

        Criteria criteria = session.createCriteria(PeharQuestion.class);
        criteria.addOrder(Order.desc("idPehar"));
        criteria.setMaxResults(1);
        PeharQuestion pehar = (PeharQuestion) criteria.uniqueResult();

        int idPehar = pehar.getIdPehar() + 1;

        for (int i = 0; i < 13; i++) {
            data[i].setIdPehar(idPehar);
            data[i].setIdPitanja(i + 1);
            data[i].setPitanje(peharQuestions[i]);
            data[i].setOdgovor(peharAnswers[i]);
            session.save(data[i]);
        }

        Transaction.closeSession();

        clearPeharInputs();
        message = "";
    }

    private void clearPeharInputs() {
        peharQuestion1 = "";
        peharQuestion2 = "";
        peharQuestion3 = "";
        peharQuestion4 = "";
        peharQuestion5 = "";
        peharQuestion6 = "";
        peharQuestion7 = "";
        peharQuestion8 = "";
        peharQuestion9 = "";
        peharQuestion10 = "";
        peharQuestion11 = "";
        peharQuestion12 = "";
        peharQuestion13 = "";
        peharAnswer1 = "";
        peharAnswer2 = "";
        peharAnswer3 = "";
        peharAnswer4 = "";
        peharAnswer5 = "";
        peharAnswer6 = "";
        peharAnswer7 = "";
        peharAnswer8 = "";
        peharAnswer9 = "";
        peharAnswer10 = "";
        peharAnswer11 = "";
        peharAnswer12 = "";
        peharAnswer13 = "";
    }

    public void setActiveTab(short index) {
        activetTabIndex = index;
    }

    public short getActivetTabIndex() {
        return activetTabIndex;
    }

    public void setActivetTabIndex(short activetTabIndex) {
        this.activetTabIndex = activetTabIndex;
    }

    public boolean canShowTab(short index) {
        return activetTabIndex == index;
    }

    public String getAnagramRec() {
        return anagramRec;
    }

    public void setAnagramRec(String anagramRec) {
        this.anagramRec = anagramRec;
    }

    public String getAnagramResenje() {
        return anagramResenje;
    }

    public void setAnagramResenje(String anagramResenje) {
        this.anagramResenje = anagramResenje;
    }

    public List<GeografijaCheck> getRequests() {
        return requests;
    }

    public void setRequests(List<GeografijaCheck> requests) {
        this.requests = requests;
    }

    public String getHangmanWord() {
        return hangmanWord;
    }

    public void setHangmanWord(String hangmanWord) {
        this.hangmanWord = hangmanWord;
    }

    public String getPeharQuestion1() {
        return peharQuestion1;
    }

    public void setPeharQuestion1(String peharQuestion1) {
        this.peharQuestion1 = peharQuestion1;
    }

    public String getPeharQuestion2() {
        return peharQuestion2;
    }

    public void setPeharQuestion2(String peharQuestion2) {
        this.peharQuestion2 = peharQuestion2;
    }

    public String getPeharQuestion3() {
        return peharQuestion3;
    }

    public void setPeharQuestion3(String peharQuestion3) {
        this.peharQuestion3 = peharQuestion3;
    }

    public String getPeharQuestion4() {
        return peharQuestion4;
    }

    public void setPeharQuestion4(String peharQuestion4) {
        this.peharQuestion4 = peharQuestion4;
    }

    public String getPeharQuestion5() {
        return peharQuestion5;
    }

    public void setPeharQuestion5(String peharQuestion5) {
        this.peharQuestion5 = peharQuestion5;
    }

    public String getPeharQuestion6() {
        return peharQuestion6;
    }

    public void setPeharQuestion6(String peharQuestion6) {
        this.peharQuestion6 = peharQuestion6;
    }

    public String getPeharQuestion7() {
        return peharQuestion7;
    }

    public void setPeharQuestion7(String peharQuestion7) {
        this.peharQuestion7 = peharQuestion7;
    }

    public String getPeharQuestion8() {
        return peharQuestion8;
    }

    public void setPeharQuestion8(String peharQuestion8) {
        this.peharQuestion8 = peharQuestion8;
    }

    public String getPeharQuestion9() {
        return peharQuestion9;
    }

    public void setPeharQuestion9(String peharQuestion9) {
        this.peharQuestion9 = peharQuestion9;
    }

    public String getPeharQuestion10() {
        return peharQuestion10;
    }

    public void setPeharQuestion10(String peharQuestion10) {
        this.peharQuestion10 = peharQuestion10;
    }

    public String getPeharQuestion11() {
        return peharQuestion11;
    }

    public void setPeharQuestion11(String peharQuestion11) {
        this.peharQuestion11 = peharQuestion11;
    }

    public String getPeharQuestion12() {
        return peharQuestion12;
    }

    public void setPeharQuestion12(String peharQuestion12) {
        this.peharQuestion12 = peharQuestion12;
    }

    public String getPeharQuestion13() {
        return peharQuestion13;
    }

    public void setPeharQuestion13(String peharQuestion13) {
        this.peharQuestion13 = peharQuestion13;
    }

    public String getPeharAnswer1() {
        return peharAnswer1;
    }

    public void setPeharAnswer1(String peharAnswer1) {
        this.peharAnswer1 = peharAnswer1;
    }

    public String getPeharAnswer2() {
        return peharAnswer2;
    }

    public void setPeharAnswer2(String peharAnswer2) {
        this.peharAnswer2 = peharAnswer2;
    }

    public String getPeharAnswer3() {
        return peharAnswer3;
    }

    public void setPeharAnswer3(String peharAnswer3) {
        this.peharAnswer3 = peharAnswer3;
    }

    public String getPeharAnswer4() {
        return peharAnswer4;
    }

    public void setPeharAnswer4(String peharAnswer4) {
        this.peharAnswer4 = peharAnswer4;
    }

    public String getPeharAnswer5() {
        return peharAnswer5;
    }

    public void setPeharAnswer5(String peharAnswer5) {
        this.peharAnswer5 = peharAnswer5;
    }

    public String getPeharAnswer6() {
        return peharAnswer6;
    }

    public void setPeharAnswer6(String peharAnswer6) {
        this.peharAnswer6 = peharAnswer6;
    }

    public String getPeharAnswer7() {
        return peharAnswer7;
    }

    public void setPeharAnswer7(String peharAnswer7) {
        this.peharAnswer7 = peharAnswer7;
    }

    public String getPeharAnswer8() {
        return peharAnswer8;
    }

    public void setPeharAnswer8(String peharAnswer8) {
        this.peharAnswer8 = peharAnswer8;
    }

    public String getPeharAnswer9() {
        return peharAnswer9;
    }

    public void setPeharAnswer9(String peharAnswer9) {
        this.peharAnswer9 = peharAnswer9;
    }

    public String getPeharAnswer10() {
        return peharAnswer10;
    }

    public void setPeharAnswer10(String peharAnswer10) {
        this.peharAnswer10 = peharAnswer10;
    }

    public String getPeharAnswer11() {
        return peharAnswer11;
    }

    public void setPeharAnswer11(String peharAnswer11) {
        this.peharAnswer11 = peharAnswer11;
    }

    public String getPeharAnswer12() {
        return peharAnswer12;
    }

    public void setPeharAnswer12(String peharAnswer12) {
        this.peharAnswer12 = peharAnswer12;
    }

    public String getPeharAnswer13() {
        return peharAnswer13;
    }

    public void setPeharAnswer13(String peharAnswer13) {
        this.peharAnswer13 = peharAnswer13;
    }

//    public static void main(String[] args){
//        String[] a = {
//            "12345a789",
//            "12345a78",
//            "1234587",
//            "123457",
//            "12345",
//            "1234",
//            "123",
//            "1234",
//            "12345",
//            "654321",
//            "1234567",
//            "12345678",
//            "123456789",
//        };
//        
//        if(SupervisorController.checkPeharAnswers(a)){
//            System.out.println("OK");
//        } else {
//            System.out.println("NOT_OK");
//        }
//        
//    }
}
