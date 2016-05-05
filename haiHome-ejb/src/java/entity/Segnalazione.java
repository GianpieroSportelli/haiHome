/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author gianp_000
 */
@Entity
public class Segnalazione implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Annuncio annuncio;

    @ManyToOne
    private Studente studente;

    private String descrizione;

    /**
     * Get the value of descrizione
     *
     * @return the value of descrizione
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Set the value of descrizione
     *
     * @param descrizione new value of descrizione
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * Get the value of id_studente
     *
     * @return the value of id_studente
     */
    public Studente getStudente() {
        return studente;
    }

    /**
     * Set the value of id_studente
     *
     * @param studente new value of studente
     */
    public void setStudente(Studente studente) {
        this.studente = studente;
    }

    /**
     * Get the value of id_annuncio
     *
     * @return the value of id_annuncio
     */
    public Annuncio getAnnuncio() {
        return annuncio;
    }

    /**
     * Set the value of id_annuncio
     *
     * @param annuncio new value of annuncio
     */
    public void setAnnuncio(Annuncio annuncio) {
        this.annuncio = annuncio;
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
        if (!(object instanceof Segnalazione)) {
            return false;
        }
        Segnalazione other = (Segnalazione) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Segnalazione[ id=" + id + " ]";
    }
    
    public JSONObject toJSON(){
        JSONObject result=null;
        try {
            result=new JSONObject();
            result.put("Annuncio",this.annuncio.toJSON());
            result.put("Studente", this.studente.toJSON());
            result.put("Descrizione",this.descrizione);
        } catch (JSONException ex) {
            Logger.getLogger(Segnalazione.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
