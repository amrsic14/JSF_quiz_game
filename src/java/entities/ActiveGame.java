/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author acamr
 */
public class ActiveGame {

    private String blue;

    private String red;
    
    public ActiveGame(){}
    
    public ActiveGame(String blue, String red){
        this.blue = blue;
        this.red = red;
    }

    public String getBlue() {
        return blue;
    }

    public void setBlue(String blue) {
        this.blue = blue;
    }

    public String getRed() {
        return red;
    }

    public void setRed(String red) {
        this.red = red;
    }
    
}

