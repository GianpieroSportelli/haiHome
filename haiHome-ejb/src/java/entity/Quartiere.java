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
import javax.persistence.OneToMany;

/**
 *
 * @author gianp_000
 */
@Entity
public class Quartiere implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    private String descrizione;
    
    private Collection<String> cap;
    
    public Collection<String> getCap() {
        return cap;
    }

    public void setCap(Collection<String> cap) {
        this.cap = cap;
    }

    @OneToMany
    private Collection<FeedBack> listaFeedBack;
    
        private String foto;

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
     * Get the value of listaFeedBack
     *
     * @return the value of listaFeedBack
     */
    public Collection<FeedBack> getListaFeedBack() {
        return listaFeedBack;
    }

    /**
     * Set the value of listaFeedBack
     *
     * @param listaFeedBack new value of listaFeedBack
     */
    public void setListaFeedBack(Collection<FeedBack> listaFeedBack) {
        this.listaFeedBack = listaFeedBack;
    }

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
        if (!(object instanceof Quartiere)) {
            return false;
        }
        Quartiere other = (Quartiere) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Quartieri[ id=" + id + " ]";
    }

}
