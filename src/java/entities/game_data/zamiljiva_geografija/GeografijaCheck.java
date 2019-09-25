/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.game_data.zamiljiva_geografija;

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
@Table(name = "supervisor_geografijacheck")
public class GeografijaCheck implements Serializable {
    @Id
    @Column(name = "odgovor")
    private String odgovor;
    
    @Column(name = "indeks")
    private int indeks;
    
    @Column(name = "provereno")
    private boolean provereno;

    public boolean isProvereno() {
        return provereno;
    }

    public void setProvereno(boolean provereno) {
        this.provereno = provereno;
    }

    public String getOdgovor() {
        return odgovor;
    }

    public void setOdgovor(String odgovor) {
        this.odgovor = odgovor;
    }

    public int getIndeks() {
        return indeks;
    }

    public void setIndeks(int indeks) {
        this.indeks = indeks;
    }
    
}
