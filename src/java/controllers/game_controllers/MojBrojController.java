/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.game_controllers;

import controllers.GameController;
import java.io.Serializable;
import java.util.Random;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 *
 * @author acamr
 */
@ManagedBean
@SessionScoped
@Named(value = "MojBrojController")
public class MojBrojController implements Serializable {

    private enum Pick {
        Number, Operator
    };
    private final Random random = new Random();

    private static String[] numbersForMojBroj = {"0", "", "", "", "", "", ""};
    private final String[] mojBrojOperations = {"+", "-", "*", "/", "(", ")"};
    private static String mojBrojWord = "";
    private static boolean[] mojBrojButtons = {true, true, true, true, true, true, true};
    private static boolean[] chosenNumbers = {false, false, false, false, false, false, false};
    private static Pick lastPick = Pick.Operator;
    public static int indexChosen = 0;

    public static void clearData() {
        numbersForMojBroj = new String[]{"0", "", "", "", "", "", ""};
        mojBrojWord = "";
        mojBrojButtons = new boolean[]{true, true, true, true, true, true, true};
        chosenNumbers = new boolean[]{false, false, false, false, false, false, false};
        lastPick = Pick.Operator;
        indexChosen = 0;
    }

    public boolean isButtonAvailable(int index) {
        return mojBrojButtons[index];
    }

    public boolean isChosenNumber(int index) {
        return chosenNumbers[index];
    }

    public void chooseNumber() {
        if (!numbersForMojBroj[indexChosen].equals("")) {
            chosenNumbers[indexChosen++] = true;
        }
    }

    public boolean isAllChosen() {
        return indexChosen == 7;
    }

    public void reset() {
        for (int i = 0; i < 7; i++) {
            mojBrojButtons[i] = true;
        }
        mojBrojWord = "";
        lastPick = Pick.Operator;
    }

    public String getMojBrojWord() {
        return mojBrojWord;
    }

    public String getNumber(int index) {
        GameController.timer = 60;
        String number = "";
        switch (index) {
            case 0:
            case 1:
            case 2:
            case 3:
                number = Integer.toString(random.nextInt(9) + 1);
                break;
            case 4:
                number = Integer.toString((random.nextInt(3) + 2) * 5);
                break;
            case 5:
                number = Integer.toString((random.nextInt(4) + 1) * 25);
                break;
            case 6:
                number = Integer.toString(random.nextInt(999) + 1);
                break;
            default:
                break;
        }

        numbersForMojBroj[index] = number;

        return number;
    }

    public String getOperation(int index) {
        return mojBrojOperations[index];
    }

    public void pickNumber(int index) {
        if (index == 6) {
            return;
        }
        if (lastPick == Pick.Operator) {
            mojBrojWord += numbersForMojBroj[index];
            mojBrojButtons[index] = false;
            lastPick = Pick.Number;
        }
    }

    public void pickOperator(int index) {
        if (lastPick == Pick.Number) {
            mojBrojWord += mojBrojOperations[index];
            lastPick = Pick.Operator;
        }
    }

    public void pickBracket(int index) {
        mojBrojWord += mojBrojOperations[index];
    }

    public String numbers(int index) {
        return numbersForMojBroj[index];
    }

    public boolean isNumberChosing() {
        return GameController.isMyPage(GameController.GameView.MojBroj) && (indexChosen < 7);
    }

    public void update() {
        if (indexChosen >= 7) {
            return;
        }
        if (!chosenNumbers[indexChosen]) {
            getNumber(indexChosen);
        }
    }

    public static int getPoints() {
        if (mojBrojWord == null || "".equals(mojBrojWord)) {
            return 0;
        }
        try {
            ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("javascript");
            int result = (int) scriptEngine.eval(mojBrojWord);
            int diff = Math.abs(result - Integer.parseInt(numbersForMojBroj[6]));
            return diff == 0 ? 10 : 0;
        } catch (ScriptException ex) {
            return 0;
        }
    }

}
