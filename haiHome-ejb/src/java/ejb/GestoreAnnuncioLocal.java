/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Locatore;
import javax.ejb.Local;

/**
 *
 * @author gianp_000
 */
@Local
public interface GestoreAnnuncioLocal {
    
    public boolean CreaAnnuncio(Locatore locatore);
    
}
