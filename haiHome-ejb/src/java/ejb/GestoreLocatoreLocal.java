/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gianp_000
 */
@Local
public interface GestoreLocatoreLocal {
    public boolean aggiungiLocatore(String email, String nome, String cognome, 
        String foto, String password, String descrizione);
    public List<String> getLocatori(); 
    public boolean checkLocatore(String email);
    public boolean removeLocatore();
}
