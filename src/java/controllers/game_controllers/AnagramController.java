/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.game_controllers;

import entities.GameOfTheDay;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author acamr
 */
@ManagedBean
@SessionScoped
@Named(value = "AnagramController")
public class AnagramController implements Serializable {

    public static String[] lettersForAnagram;
    private static String resenje;
    private static boolean[] availableButtons = {true, true, true, true, true, true, true, true, true};
    private static String outputAnagram = "";
    
    public static void prepareAnagram(GameOfTheDay game) {
        int index = 0;
        int length = game.getAnagram().getRec().length();
        availableButtons = new boolean[length];
        lettersForAnagram = new String[length];
        resenje = game.getAnagram().getResenje();
        for (Character c : game.getAnagram().getRec().toCharArray()) {
            lettersForAnagram[index] = c.toString();
            availableButtons[index++] = true;
        }
        outputAnagram = "";
    }
    
    public static int getPoints(){
        return resenje.equals(outputAnagram) ? 10 : 0;
    }

    public static void clearData() {
        availableButtons = new boolean[]{true, true, true, true, true, true, true, true, true};
        outputAnagram = "";
    }

    public String[] getLettersForAnagram() {
        return lettersForAnagram;
    }

    public String getLetter(int index) {
        return lettersForAnagram[index];
    }

    public void pickLetter(int index) {
        outputAnagram = outputAnagram.concat(lettersForAnagram[index]);
        if(!lettersForAnagram[index].equals(" ")) availableButtons[index] = false;
    }

    public boolean isButtonAvailable(int index) {
        return availableButtons[index];
    }
    
    public void reset() {
        outputAnagram = "";
        for(int i = 0; i < availableButtons.length; i++) availableButtons[i] = true;
    }
    
    public String getOutputAnagram() {
        return outputAnagram;
    }

}
