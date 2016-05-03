/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Annuncio;
import entity.FiltroDiRicerca;
import entity.Studente;
import java.util.List;
import javax.ejb.Local;
import org.json.JSONObject;

/**
 *
 * @author gianp_000
 */
@Local
public interface GestoreStudenteLocal {

    public boolean aggiungiStudente(String email, String nome, String cognome, String foto, String password);

    public List<String> getStudenti();

    public boolean checkStudente(String email);

    public boolean removeStudente();

    public JSONObject toJSON();

    public Studente getStudente();

    public Studente getStudenteByID(String id);

    public boolean reloadStudente();

    public boolean addFiltroStudente(String id, FiltroDiRicerca filtro);

    public boolean removeFiltroStudente(String id, FiltroDiRicerca filtro);

    public boolean addAnnuncio(String id);

    public boolean removeAnnuncio(String id);
    
    public void changePassword(String password);
}
