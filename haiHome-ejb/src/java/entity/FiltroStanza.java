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
public class FiltroStanza extends FiltroDiRicerca implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private TipoStanzaInAffitto tipo;

    /**
     * Get the value of tipo
     *
     * @return the value of tipo
     */
    public TipoStanzaInAffitto getTipo() {
        return tipo;
    }
    
    @Override
    public JSONObject toJSON() throws JSONException{
        JSONObject result=super.toJSON();
        result.accumulate("Tipo", "Stanza");
        result.accumulate("TipoStanza", this.tipo);
        return result;
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
        if (!(object instanceof FiltroStanza)) {
            return false;
        }
        FiltroStanza other = (FiltroStanza) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FiltroStanza[ id=" + id + " ]";
    }

}
