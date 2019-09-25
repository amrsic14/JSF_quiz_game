/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.game_data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author acamr
 */
@Entity
@Table(name = "game_hangman")
public class HangmanData implements Serializable {
    
    @Id
    @Column(name = "idHangman")
    private int idHangman;

    @Column(name = "rec")
    private String rec;

    public int getIdHangman() {
        return idHangman;
    }

    public void setIdHangman(int idHangman) {
        this.idHangman = idHangman;
    }

    public String getRec() {
        return rec;
    }

    public void setRec(String rec) {
        this.rec = rec;
    }
    
}
