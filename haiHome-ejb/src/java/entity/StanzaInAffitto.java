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
import org.json.JSONException;
import org.json.JSONObject;

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

    private boolean compresoCondominio;

    private boolean compresoRiscaldamento;

    private boolean visibile = true;

    /**
     * Get the value of visibile
     *
     * @return the value of visibile
     */
    public boolean isVisibile() {
        return visibile;
    }

    /**
     * Set the value of visibile
     *
     * @param visibile new value of visibile
     */
    public void setVisibile(boolean visibile) {
        this.visibile = visibile;
    }

    /**
     * Get the value of compresoRiscaldamento
     *
     * @return the value of compresoRiscaldamento
     */
    public boolean isCompresoRiscaldamento() {
        return compresoRiscaldamento;
    }

    /**
     * Set the value of compresoRiscaldamento
     *
     * @param compresoRiscaldamento new value of compresoRiscaldamento
     */
    public void setCompresoRiscaldamento(boolean compresoRiscaldamento) {
        this.compresoRiscaldamento = compresoRiscaldamento;
    }

    /**
     * Get the value of compresoCondominio
     *
     * @return the value of compresoCondominio
     */
    public boolean isCompresoCondominio() {
        return compresoCondominio;
    }

    /**
     * Set the value of compresoCondominio
     *
     * @param compresoCondominio new value of compresoCondominio
     */
    public void setCompresoCondominio(boolean compresoCondominio) {
        this.compresoCondominio = compresoCondominio;
    }

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

    public JSONObject toJSON() {

        JSONObject stanzaJSON = new JSONObject();
        try {
            stanzaJSON.accumulate("OID", super.getId());
            stanzaJSON.accumulate("Foto", super.foto);
            stanzaJSON.accumulate("Metratura", super.metratura);
            stanzaJSON.accumulate("Tipo", this.tipo.name());
            stanzaJSON.accumulate("Prezzo", this.prezzo);
            stanzaJSON.accumulate("archiviato", this.archiviato);
            stanzaJSON.accumulate("compresoCondominio", this.compresoCondominio);
            stanzaJSON.accumulate("compresoRiscaldamento", this.compresoRiscaldamento);
            stanzaJSON.accumulate("visibile", this.visibile);
            stanzaJSON.accumulate("SuperTipo", "StanzaInAffitto");

        } catch (JSONException ex) {
            Logger.getLogger(Annuncio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stanzaJSON;
    }

    @Override
    public Stanza clone() {
        StanzaInAffitto newS = new StanzaInAffitto();
        newS.setId(this.getId());
        newS.setVisibile(this.isVisibile());
        newS.setCompresoCondominio(this.isCompresoCondominio());
        newS.setCompresoRiscaldamento(this.isCompresoRiscaldamento());
        newS.setFoto(this.getFoto());
        newS.setMetratura(this.getMetratura());
        newS.setPrezzo(this.getPrezzo());
        newS.setTipo(this.getTipo());
        return newS;
    }

}
