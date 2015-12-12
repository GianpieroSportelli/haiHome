/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author gianp_000
 */
@Entity
public class FiltroAppartamento extends FiltroDiRicerca implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private int numeroLocali;
    
    private double metratura;
    
    private int numeroCamereDaLetto;
    
    private int numeroBagni;

    /**
     * Get the value of numeroBagni
     *
     * @return the value of numeroBagni
     */
    public int getNumeroBagni() {
        return numeroBagni;
    }
    
    @Override
    public JSONObject toJSON() throws JSONException{
        JSONObject result=super.toJSON();
        result.accumulate("Tipo", "Appartamento");
        result.accumulate("Metratura", this.metratura);
        result.accumulate("NumeroBagni", this.numeroBagni);
        result.accumulate("NumeroCamereDaLetto", this.numeroCamereDaLetto);
        result.accumulate("NumeroLocali", this.numeroLocali);
        return result;
    }

    /**
     * Set the value of numeroBagni
     *
     * @param numeroBagni new value of numeroBagni
     */
    public void setNumeroBagni(int numeroBagni) {
        this.numeroBagni = numeroBagni;
    }


    /**
     * Get the value of numeroCamereDaLetto
     *
     * @return the value of numeroCamereDaLetto
     */
    public int getNumeroCamereDaLetto() {
        return numeroCamereDaLetto;
    }

    /**
     * Set the value of numeroCamereDaLetto
     *
     * @param numeroCamereDaLetto new value of numeroCamereDaLetto
     */
    public void setNumeroCamereDaLetto(int numeroCamereDaLetto) {
        this.numeroCamereDaLetto = numeroCamereDaLetto;
    }


    /**
     * Get the value of metratura
     *
     * @return the value of metratura
     */
    public double getMetratura() {
        return metratura;
    }

    /**
     * Set the value of metratura
     *
     * @param metratura new value of metratura
     */
    public void setMetratura(double metratura) {
        this.metratura = metratura;
    }


    /**
     * Get the value of numeroLocali
     *
     * @return the value of numeroLocali
     */
    public int getNumeroLocali() {
        return numeroLocali;
    }

    /**
     * Set the value of numeroLocali
     *
     * @param numeroLocali new value of numeroLocali
     */
    public void setNumeroLocali(int numeroLocali) {
        this.numeroLocali = numeroLocali;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FiltroAppartamento)) {
            return false;
        }
        FiltroAppartamento other = (FiltroAppartamento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FiltroAppartamento[ id=" + id + " ]";
    }
    
}
