/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Locatore;
import java.util.List;
import javax.ejb.Local;
import org.json.JSONObject;


/**
 *
 * @author gianp_000
 */
@Local
public interface GestoreLocatoreLocal {
    /* */
//    public boolean aggiungiLocatore(String email, String nome, String cognome,
//        String foto, String password, String descrizione);
    public boolean aggiungiLocatore(String email, String password, String nome, 
            String cognome, String telefono, String img_profilo); 
    public List<String> getLocatori(); 
    public boolean checkLocatore(String email);
    public boolean removeLocatore();
    /* Gestione profilo del locatore*/
    public void modificaInfoProfilo(String telefono, String descrizione); 
    public boolean modificaPassword(String oldpassword, String newpassword);   
    
    public JSONObject toJSON();
    public Locatore getLocatore(); 
}
