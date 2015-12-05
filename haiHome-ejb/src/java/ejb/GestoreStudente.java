/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Studente;
import facade.StudenteFacadeLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Eugenio Liso
 */
@Stateless
public class GestoreStudente {

    @EJB
    private StudenteFacadeLocal studenteFacade;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //gli altri parametri, i.e. listaPreferiti saranno per forza null
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
            return true;//tutto ok
        }
    }

    public List<String> getStudenti() {
        List<Studente> listaStudenti = studenteFacade.findAll();

        List<String> result = new ArrayList<>();

        for (Studente s : listaStudenti) {
            result.add(s.toString());
        }
        return result;
    }

    public void removeStudente(Studente s) {
        //lo cancello
        studenteFacade.remove(studenteFacade.find(s.getId()));
    }

    /**
     * Metodo per cercare se esiste uno studente specifico
     *
     * @param email
     * @param nome
     * @param cognome
     * @return true sse lo studente è già presente, false altrimenti
     */
    public boolean checkStudente(String email, String nome, String cognome) {
        List<Studente> listaStudenti = studenteFacade.findAll();

        for (Studente s : listaStudenti) {
            if ((s.getCognome().compareToIgnoreCase(cognome) == 0) && (s.getNome().compareToIgnoreCase(nome) == 0) && (s.getEmail().compareToIgnoreCase(email) == 0)) {
                //ho trovato uno studente duplicato
                return true;
            }
        }
        return false;
    }
}
