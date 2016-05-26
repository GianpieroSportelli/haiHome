/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Annuncio;
import entity.FiltroDiRicerca;
import entity.Studente;
import facade.StudenteFacadeLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import org.json.JSONObject;

/**
 *
 * @author Eugenio Liso
 */
@Stateful
public class GestoreStudente implements GestoreStudenteLocal {

    @EJB
    private StudenteFacadeLocal studenteFacade;

    @EJB
    private GestoreAnnuncioLocal gestoreAnnuncio;

    private Studente studente = null;

    /**
     * Aggiunge uno studente indicando le sue informazioni
     *
     * @param email L'email dello studente
     * @param nome Il nome dello studente
     * @param cognome Il cognome dello studente
     * @param foto La foto dello studente
     * @param password La password dello studente. Se è null, lo studente si è
     * loggato attraverso un canale social
     * @return true se l'operazione è andata a buon fine, false altrimenti
     */
    @Override
    public boolean aggiungiStudente(String email, String nome, String cognome, String foto, String password) {

        Studente stud = new Studente();
        stud.setEmail(email);
        stud.setNome(nome);
        stud.setCognome(cognome);
        stud.setFotoProfilo(foto);
        stud.setPassword(password);

        //Persisto l'oggetto
        studenteFacade.create(stud);

        //vedo se è stato inserito
        //Se lo è, allora l'oggetto checkStudente non è null
        Studente checkStudente = studenteFacade.find(stud.getId());
        if (checkStudente == null) {
            return false;
        } else {
            //Mi mantengo il riferimento allo studente 
            this.studente = stud;
            return true;//tutto ok
        }
    }

    /**
     * Permette di ottenere la lista di tutti gli studenti memorizzati
     *
     * @return Una lista contenente gli studenti
     */
    @Override
    public List<String> getStudenti() {
        List<Studente> listaStudenti = studenteFacade.findAll();
        List<String> result = new ArrayList<>();

        for (Studente s : listaStudenti) {
            result.add(s.toString());
        }
        return result;
    }

    /**
     * Rimuove uno studente memorizzato all'interno del Database
     *
     * @return true se l'eliminazione è andata a buon fine, false altrimenti
     */
    @Override
    public boolean removeStudente() {

        if (studente == null) {
            return false;
        }
        //lo cancello
        studenteFacade.remove(studenteFacade.find(this.studente.getId()));
        return true;
    }

    /**
     * Cerca, se esiste, uno studente specifico. Se esiste, lo studente viene
     * memorizzato
     *
     * @param email L'email dello studente da cercare
     * @return true sse lo studente è già presente, false altrimenti
     */
    @Override
    public boolean checkStudente(String email) {
        List<Studente> listaStudenti = studenteFacade.findAll();

        for (Studente s : listaStudenti) {
            if (s.getEmail().compareToIgnoreCase(email) == 0) {
                //ho trovato uno studente duplicato
                //Mi salvo il riferimento allo studente trovato
                studente = s;
                return true;
            }
        }
        return false;
    }

    /**
     * Restituisce il JSON associato allo studente attualmente utilizzato
     *
     * @return JSONObject contenente il JSON dello studente
     */
    @Override
    public JSONObject toJSON() {
        return this.studente.toJSON();
    }

    /**
     * Restituisce lo studente attualmente utilizzato
     *
     * @return Lo studente attualmente in uso
     */
    @Override
    public Studente getStudente() {
        return this.studente;
    }

    /**
     * Restituisce uno Studente dando in input il suo ID
     *
     * @param id
     * @return Studente se è presente, null altrimenti
     */
    @Override
    public Studente getStudenteByID(String id) {
        Studente s = studenteFacade.find(Long.valueOf(id));
        return s;
    }

    /**
     * Ricarica il riferimento allo studente presente all'interno del gestore
     *
     * @return false se lo studente non è loggato oppure non viene trovato nel
     * Database, true altrimenti
     */
    @Override
    public boolean reloadStudente() {
        if (this.getStudente() == null) {
            return false;
        }
        Studente newStudente = studenteFacade.find(this.getStudente().getId());

        if (newStudente == null) {
            return false; //Non ho trovato l'ID
        } else {
            this.studente = newStudente;
            return true;
        }

    }

    /**
     * Aggiunge un filtro preferito nella lista dei filtri preferiti di uno
     * studente
     *
     * @param id ID dello studente
     * @param filtro Filtro da memorizzare
     * @return true se l'inserimento va a buon fine, false altrimenti
     */
    @Override
    public boolean addFiltroStudente(String id, FiltroDiRicerca filtro) {
        Studente stud = studenteFacade.find(Long.valueOf(id));

        if (stud == null) {
            return false;
        }
        stud.addFiltro(filtro);
        studenteFacade.edit(stud);
        return true;
    }

    /**
     * Rimuove un filtro preferito di uno studente
     *
     * @param id ID dello studente
     * @param filtro Filtro da eliminare
     * @return true se l'eliminazione è andata a buon fine, false altrimenti
     */
    @Override
    public boolean removeFiltroStudente(String id, FiltroDiRicerca filtro) {
        Studente stud = studenteFacade.find(Long.valueOf(id));

        if (stud == null) {
            return false;
        }
        stud.deleteFiltro(filtro);
        studenteFacade.edit(stud);
        return true;
    }

    /**
     * Aggiunge un annuncio preferito alla lista degli annunci preferiti dello
     * studente attualmente in uso
     *
     * @param id ID dell'annuncio da aggiungere
     * @return false se l'annuncio è già presente, true altrimenti
     */
    @Override
    public boolean addAnnuncio(String id) {
        Annuncio a = this.gestoreAnnuncio.getAnnuncioByID(Long.parseLong(id));

        for (Annuncio aTemp : this.studente.getListaAnnunciPreferiti()) {
            if (aTemp.getId().toString().equalsIgnoreCase(id)) {
                return false;
            }
        }
        this.studente.addAnnuncio(a);
        studenteFacade.edit(this.studente);
        return true;
    }

    /**
     * Rimuove un annuncio preferito dalla lista degli annunci preferiti dello
     * studente attualmente in uso
     *
     * @param id ID dell'annuncio da eliminare
     * @return false se l'annuncio è già presente, true altrimenti
     */
    @Override
    public boolean removeAnnuncio(String id) {
        Annuncio a = this.gestoreAnnuncio.getAnnuncioByID(Long.valueOf(id));

        boolean annuncioPresente = false;
        for (Annuncio aTemp : this.studente.getListaAnnunciPreferiti()) {
            if (aTemp.getId().toString().equalsIgnoreCase(id)) {
                annuncioPresente = true;
                break;
            }
        }
        if (!annuncioPresente) {
            return false;
        }
        
        this.studente.deleteAnnuncio(a);
        studenteFacade.edit(this.studente);
        return true;
    }

    /**
     * Cambia la password dello studente attualmente in uso
     *
     * @param password La nuova password
     */
    @Override
    public void changePassword(String password) {
        this.studente.setPassword(password);
        this.studenteFacade.edit(this.studente);
    }
}
