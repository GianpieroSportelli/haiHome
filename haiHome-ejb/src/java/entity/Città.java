/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author gianp_000
 */
@Entity
public class Città implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany
    private Collection<Quartiere> listaQuartieri;

    private String foto;
    
    private String nome;
    @OneToMany(mappedBy = "citt\u00e0")
    private List<Annuncio> annunci;
    
    public void addAnnuncio(Annuncio annuncio){
        this.annunci.add(annuncio);
    }
    public List<Annuncio> getAnnunci() {
        return annunci;
    }

    public Città() {
        this.listaQuartieri = new ArrayList<Quartiere>();
    }

    /**
     * Get the value of foto
     *
     * @return the value of foto
     */
    public String getFoto() {
        return foto;
    }

    /**
     * Set the value of foto
     *
     * @param foto new value of foto
     */
    public void setFoto(String foto) {
        this.foto = foto;
    }
    

    /**
     * Get the value of nome
     *
     * @return the value of nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Set the value of nome
     *
     * @param nome new value of nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Get the value of listaQuartieri
     *
     * @return the value of listaQuartieri
     */
    public Collection<Quartiere> getListaQuartieri() {
        return listaQuartieri;
    }

    /**
     * Set the value of listaQuartieri
     *
     * @param listaQuartieri new value of listaQuartieri
     */
    public void setListaQuartieri(Collection<Quartiere> listaQuartieri) {
        this.listaQuartieri = listaQuartieri;
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
        if (!(object instanceof Città)) {
            return false;
        }
        Città other = (Città) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Citt\u00e0[ id=" + id + " ]";
    }

}
