/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author gianp_000
 */
@Entity
public class Studente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    private String cognome;

    private String email;

    private String password;

    @OneToMany
    private Collection<Annuncio> listaAnnunciPreferiti = null;

    private String fotoProfilo;

    @OneToMany//(mappedBy = "studente")
    private Collection<FiltroDiRicerca> listaFiltriPreferiti = new ArrayList<FiltroDiRicerca>();

    /**
     * Get the value of listaFiltriPreferiti
     *
     * @return the value of listaFiltriPreferiti
     */
    public JSONArray getListaFiltriPreferiti() {
        JSONArray result = new JSONArray();
        for (FiltroDiRicerca x : listaFiltriPreferiti) {
            try {
                result.put(x.toJSON());
            } catch (JSONException ex) {
                Logger.getLogger(Studente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    public void addFiltro(FiltroDiRicerca FiltroPreferito) {
        this.listaFiltriPreferiti.add(FiltroPreferito);
    }

    public void deleteFiltro(FiltroDiRicerca FiltroPreferito) {
        this.listaFiltriPreferiti.remove(FiltroPreferito);
    }

    /**
     * Set the value of listaFiltriPreferiti
     *
     * @param listaFiltriPreferiti new value of listaFiltriPreferiti
     */
    public void setListaFiltriPreferiti(Collection<FiltroDiRicerca> listaFiltriPreferiti) {
        this.listaFiltriPreferiti = listaFiltriPreferiti;
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
     * Get the value of listaAnnunciPreferiti
     *
     * @return the value of listaAnnunciPreferiti
     */
    public Collection<Annuncio> getListaAnnunciPreferiti() {
        return listaAnnunciPreferiti;
    }

    /**
     * Set the value of listaAnnunciPreferiti
     *
     * @param listaAnnunciPreferiti new value of listaAnnunciPreferiti
     */
    public void setListaAnnunciPreferiti(Collection<Annuncio> listaAnnunciPreferiti) {
        this.listaAnnunciPreferiti = listaAnnunciPreferiti;
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
        if (!(object instanceof Studente)) {
            return false;
        }
        Studente other = (Studente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String res = "Nome: " + getNome() + " - Cognome: " + getCognome() + " - Email: " + getEmail() + " - Foto: " + getFotoProfilo() + " - Password: " + getPassword() + "\n";
        return res;
    }

    public JSONObject toJSON() {

        JSONObject studenteJSON = new JSONObject();

        try {
            studenteJSON.accumulate("ID", this.getId());
            studenteJSON.accumulate("Nome", this.getNome());
            studenteJSON.accumulate("Cognome", this.getCognome());
            studenteJSON.accumulate("Email", this.getEmail());
            studenteJSON.accumulate("Password", this.getPassword());
            studenteJSON.accumulate("Foto", this.getFotoProfilo());

            //aggiungo Lista filtri preferiti
            //JSONArray JSONFiltriPreferiti = new JSONArray();
            //for (FiltroDiRicerca f : this.listaFiltriPreferiti) {
            //     JSONFiltriPreferiti.put(f.toJSON());
            // }
            //aggiungo Lista annunci preferiti
            //JSONArray JSONAnnunciPreferiti = new JSONArray();
            // for (Annuncio a : this.listaAnnunciPreferiti) {
            //    JSONFiltriPreferiti.put(a.toJSON());
            // }
            // studenteJSON.accumulate("FiltriPreferiti", JSONFiltriPreferiti);
            // studenteJSON.accumulate("AnnunciPreferiti", JSONAnnunciPreferiti);
        } catch (JSONException ex) {
            Logger.getLogger(Annuncio.class.getName()).log(Level.SEVERE, null, ex);
        }

        return studenteJSON;
    }

    public JSONArray getAnnunciJSON() {
        JSONArray array = new JSONArray();

        int contatore = 1;
        for (Annuncio a : this.getListaAnnunciPreferiti()) {
            JSONObject obj = new JSONObject();
            try {
                obj.accumulate("Annuncio" + String.valueOf(contatore), a);
            } catch (JSONException ex) {
                Logger.getLogger(Studente.class.getName()).log(Level.SEVERE, null, ex);
            }
            array.put(obj);

        }

        return array;
    }

}
