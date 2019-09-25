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
@Table(name = "game_pehar")
public class PeharQuestion implements Serializable {

    @Id
    @Column(name = "idPehar")
    private int idPehar;

    @Id
    @Column(name = "idPitanja")
    private int idPitanja;

    @Column(name = "pitanje")
    private String pitanje;
    
    @Column(name = "odgovor")
    private String odgovor;

    public int getIdPehar() {
        return idPehar;
    }

    public void setIdPehar(int idPehar) {
        this.idPehar = idPehar;
    }

    public int getIdPitanja() {
        return idPitanja;
    }

    public void setIdPitanja(int idPitanja) {
        this.idPitanja = idPitanja;
    }

    public String getPitanje() {
        return pitanje;
    }

    public void setPitanje(String pitanje) {
        this.pitanje = pitanje;
    }

    public String getOdgovor() {
        return odgovor;
    }

    public void setOdgovor(String odgovor) {
        this.odgovor = odgovor;
    }
    
}
