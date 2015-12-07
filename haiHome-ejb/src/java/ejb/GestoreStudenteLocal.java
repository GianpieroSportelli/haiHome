/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Studente;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gianp_000
 */
@Local
public interface GestoreStudenteLocal {
    public boolean aggiungiStudente(String email, String nome, String cognome, String foto, String password);
    public List<String> getStudenti();
    public void removeStudente(Studente s);
    public boolean checkStudente(String email, String nome, String cognome);
}
