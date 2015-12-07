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
public class Locatore implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    private String cognome;

    private String email;
    
    private String password;

    private String telefono;

    @OneToMany(mappedBy = "locatore")
    private Collection<Annuncio> listaAnnunci;

    private String fotoProfilo;

    private boolean bloccato;

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
     * Get the value of bloccato
     *
     * @return the value of bloccato
     */
    public boolean isBloccato() {
        return bloccato;
    }

    /**
     * Set the value of bloccato
     *
     * @param bloccato new value of bloccato
     */
    public void setBloccato(boolean bloccato) {
        this.bloccato = bloccato;
    }

    /**
     * Get the value of fotoProfilo
     *
     * @return the value of fotoProfilo
     */
    public String getFotoProfilo() {
        return fotoProfilo;
    }

    /**
     * Set the value of fotoProfilo
     *
     * @param fotoProfilo new value of fotoProfilo
     */
    public void setFotoProfilo(String fotoProfilo) {
        this.fotoProfilo = fotoProfilo;
    }

    /**
     * Get the value of listaAnnunci
     *
     * @return the value of listaAnnunci
     */
    public Collection<Annuncio> getListaAnnunci() {
        return listaAnnunci;
    }

    /**
     * Set the value of listaAnnunci
     *
     * @param listaAnnunci new value of listaAnnunci
     */
    public void setListaAnnunci(Collection<Annuncio> listaAnnunci) {
        this.listaAnnunci = listaAnnunci;
    }

    /**
     * Get the value of telefono
     *
     * @return the value of telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Set the value of telefono
     *
     * @param telefono new value of telefono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Get the value of email
     *
     * @return the value of email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the value of email
     *
     * @param email new value of email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the value of cognome
     *
     * @return the value of cognome
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Set the value of cognome
     *
     * @param cognome new value of cognome
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
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
     * Get the value of password
     *
     * @return the value of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the value of password
     *
     * @param password new value of password
     */
    public void setPassword(String password) {
        this.password = password;
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
        if (!(object instanceof Locatore)) {
            return false;
        }
        Locatore other = (Locatore) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Locatore[ id=" + id + " ]";
    }

}
