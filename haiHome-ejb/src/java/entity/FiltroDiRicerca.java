/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
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
public class FiltroDiRicerca implements Serializable {

    @ManyToOne
    protected Studente studente;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    protected double prezzo;

    @ManyToOne
    protected Città Città;

    //SE la lista è vuota per convenzione vuol dire tutti i quartieri di una determinata città
    protected Collection<String> listaQuartieri;

    protected boolean compresoCondominio;

    protected boolean compresoRiscaldamento;

    /**
     * Get the value of compresoRiscaldamento
     *
     * @return the value of compresoRiscaldamento
     */
    public boolean isCompresoRiscaldamento() {
        return compresoRiscaldamento;
    }
    
    public JSONObject toJSON() throws JSONException{
        JSONObject result=new JSONObject();
        result.accumulate("Città", this.Città.getNome());
        result.accumulate("Quartieri", this.listaQuartieri);
        result.accumulate("Prezzo", this.prezzo);
        result.accumulate("Id", this.id);
        result.accumulate("CompresoCondominio", this.compresoCondominio);
        result.accumulate("CompresoRiscaldamento", this.compresoRiscaldamento);
        result.accumulate("Id_Studente", this.studente.getId());
        return result;
    }
    /**
     * Set the value of studente
     *
     * @param studente
     */
    public void setStudente(Studente studente) {
        this.studente=studente;
    }


    /**
     * Get the value of studente
     *
     * @return the value of numeroCamereDaLetto
     */
    public Studente getStudente() {
        return this.studente;
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
     * Get the value of listaQuartieri
     *
     * @return the value of listaQuartieri
     */
    public Collection<String> getListaQuartieri() {
        return listaQuartieri;
    }

    /**
     * Set the value of listaQuartieri
     *
     * @param listaQuartieri new value of listaQuartieri
     */
    public void setListaQuartieri(Collection<String> listaQuartieri) {
        this.listaQuartieri = listaQuartieri;
    }

    /**
     * Get the value of idCittà
     *
     * @return the value of idCittà
     */
    public Città getCittà() {
        return Città;
    }

    /**
     * Set the value of idCittà
     *
     * @param Città new value of Città
     */
    public void setCittà(Città Città) {
        this.Città = Città;
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
        if (!(object instanceof FiltroDiRicerca)) {
            return false;
        }
        FiltroDiRicerca other = (FiltroDiRicerca) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FiltroDiRicerca[ id=" + id + " ]";
    }

}
