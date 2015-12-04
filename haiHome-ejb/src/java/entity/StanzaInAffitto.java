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

/**
 *
 * @author gianp_000
 */
@Entity
public class StanzaInAffitto extends Stanza implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private TipoStanzaInAffitto tipo;

    private double prezzo;

    private boolean archiviato;

    /**
     * Get the value of archiviato
     *
     * @return the value of archiviato
     */
    public boolean isArchiviato() {
        return archiviato;
    }

    /**
     * Set the value of archiviato
     *
     * @param archiviato new value of archiviato
     */
    public void setArchiviato(boolean archiviato) {
        this.archiviato = archiviato;
    }

    /**
     * Get the value of prezzo
     *
     * @return the value of prezzo
     */
    public double getPrezzo() {
        return prezzo;
    }

    /**
     * Set the value of prezzo
     *
     * @param prezzo new value of prezzo
     */
    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    /**
     * Get the value of tipo
     *
     * @return the value of tipo
     */
    public TipoStanzaInAffitto getTipo() {
        return tipo;
    }

    /**
     * Set the value of tipo
     *
     * @param tipo new value of tipo
     */
    public void setTipo(TipoStanzaInAffitto tipo) {
        this.tipo = tipo;
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
        if (!(object instanceof StanzaInAffitto)) {
            return false;
        }
        StanzaInAffitto other = (StanzaInAffitto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.StanzaInAffitto[ id=" + id + " ]";
    }

}
