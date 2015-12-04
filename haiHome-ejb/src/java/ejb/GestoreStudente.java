/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Studente;
import facade.StudenteFacadeLocal;
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
        return true;//tutto ok
    }

    public List<Studente> getStudenti() {
         List<Studente> listaStudenti = studenteFacade.findAll();
        
        return listaStudenti;
    }

    public void removeStudente(Studente s) {
        //lo cancello
        studenteFacade.remove(studenteFacade.find(s.getId()));
    }

    public Studente checkStudente(String email, String nome, String cognome) {
        for(Studente s: getStudenti()){
               if((s.getCognome().compareToIgnoreCase(cognome) == 0) && (s.getNome().compareToIgnoreCase(nome) == 0) && (s.getEmail().compareToIgnoreCase(email) == 0)){
                   //ho trovato uno studente duplicato
                   return s;
               }
            }
        return null;
    }
}
