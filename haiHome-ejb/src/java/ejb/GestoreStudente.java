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

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //gli altri parametri, i.e. listaPreferiti saranno per forza null
    @Override
    public boolean aggiungiStudente(String email, String nome, String cognome, String foto, String password) {

        //L'oggetto si crea senza costruttore (perchè sta fatto così)
        Studente stud = new Studente();
        stud.setEmail(email);
        stud.setNome(nome);
        stud.setCognome(cognome);
        stud.setFotoProfilo(foto);
        stud.setPassword(password);

        //LO FACCIO DIVENTARE PERSISTENTE!
        studenteFacade.create(stud);

        //vedo se è stato inserito
        //Se lo è, allora l'oggetto checkStudente non è null
        Studente checkStudente = studenteFacade.find(stud.getId());
        if (checkStudente == null) {
            return false;
        } else {
            //Mi mantengo il riferimento allo studente durante la sessione
            this.studente = stud;
            return true;//tutto ok
        }
    }

    @Override
    public List<String> getStudenti() {
        List<Studente> listaStudenti = studenteFacade.findAll();
        List<String> result = new ArrayList<>();

        for (Studente s : listaStudenti) {
            result.add(s.toString());
        }
        return result;
    }

    //Non serve alcun parametro poichè lo studente, per rimuoversi, sarà loggato
    //Se è loggato, allora l'oggetto studente sarà avvalorato e quindi non ha 
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
     * Metodo per cercare se esiste uno studente specifico. Se esiste, lo
     * studente viene memorizzato all'interno della sessione.
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

    @Override
    public JSONObject toJSON() {
        return this.studente.toJSON();
    }

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

    @Override
    public boolean removeAnnuncio(String id) {
        Annuncio a = this.gestoreAnnuncio.getAnnuncioByID(Long.valueOf(id));
        this.studente.deleteAnnuncio(a);
        studenteFacade.edit(this.studente);
        return true;
    }
}
