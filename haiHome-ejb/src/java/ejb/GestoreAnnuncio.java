/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Annuncio;
import entity.Locatore;
import javax.ejb.Stateful;

/**
 *
 * @author gianp_000
 */
@Stateful
public class GestoreAnnuncio implements GestoreAnnuncioLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    private Annuncio annuncio;
    
    //serve come passo inziale dell'inserimento dell'annuncio, inserisce solo
    //il Locatore
    @Override
    public boolean CreaAnnuncio(Locatore locatore) {
        
        this.annuncio = new Annuncio();
        
        annuncio.setLocatore(locatore);
        
        /*
        TODO
         bisonga mettere i controlli, quando non si può creare un nuovo Annuncio?
            - Locatore bloccato     
        */
        if(true)
            return true;
        else
            return false;

    }
    
    
    
}
