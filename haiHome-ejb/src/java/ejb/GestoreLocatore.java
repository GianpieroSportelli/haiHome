/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Locatore;
import facade.LocatoreFacadeLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author gianp_000
 */
@Stateful
public class GestoreLocatore implements GestoreLocatoreLocal {
    @EJB
    private LocatoreFacadeLocal locatoreFacade;

    private Locatore locatore = null;
    
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    
    @Override
    public boolean aggiungiLocatore(String email, String nome, String cognome, 
    String foto, String password, String descrizione) {
        Locatore loc = new Locatore(); 
        //inizializza l'oggetto
        loc.setEmail(email);
        loc.setPassword(password);      
        loc.setNome(nome);
        loc.setCognome(cognome);
        loc.setFotoProfilo(foto);
        loc.setDescrizione(descrizione);
        //rende persistente l'oggetto
        locatoreFacade.create(loc); 
        //verifica esito inserimento
        Locatore check = locatoreFacade.find(loc.getId()); 
        boolean resOp = (check != null); 
        //mantiene riferimento all'oggetto 
        if (resOp)
            this.locatore = loc; 
        return resOp; 
    }
    
    @Override
    public List<String> getLocatori() {
        List<Locatore> listaLocatori = locatoreFacade.findAll(); 
        List<String> result = new ArrayList<>(); 
        
        for (Locatore l: listaLocatori) 
            result.add(l.toString());
        
        return result; 
    }
    
    @Override
    public boolean checkLocatore(String email) { 
       List<Locatore> listaLocatori = locatoreFacade.findAll(); 
       
       for (Locatore l: listaLocatori) 
           if (l.getEmail().compareToIgnoreCase(email) == 0) { 
               locatore = l; 
           }
       
       return locatore != null; 
    }
    
    @Override
    public boolean removeLocatore() {
        boolean resOp = false; 
        
        if (locatore != null) {
            locatoreFacade.remove(locatoreFacade.find(locatore.getId()));
            resOp = true; 
        }
        
        return resOp; 
    }
    
    @Override
    public Locatore getLocatore() {
        return this.locatore; 
    }
}
