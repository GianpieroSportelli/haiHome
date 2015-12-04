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

/**
 *
 * @author gianp_000
 */
@Entity
public class FiltroDiRicerca implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    protected double prezzo;

    @ManyToOne
    protected Città Città;

    protected Collection<String> listaQuartieri;

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
    public void setIdCittà(Città Città) {
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
