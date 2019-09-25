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
@Table(name = "game_anagram")
public class AnagramData implements Serializable {

    @Id
    @Column(name = "idAnagram")
    private String idAnagram;

    @Column(name = "rec")
    private String rec;

    @Column(name = "resenje")
    private String resenje;

    public String getIdAnagram() {
        return idAnagram;
    }

    public void setIdAnagram(String idAnagram) {
        this.idAnagram = idAnagram;
    }

    public String getRec() {
        return rec;
    }

    public void setRec(String rec) {
        this.rec = rec;
    }

    public String getResenje() {
        return resenje;
    }

    public void setResenje(String resenje) {
        this.resenje = resenje;
    }

}
