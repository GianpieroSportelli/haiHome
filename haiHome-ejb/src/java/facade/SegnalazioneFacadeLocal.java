/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Segnalazione;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gianp_000
 */
@Local
public interface SegnalazioneFacadeLocal {

    void create(Segnalazione segnalazione);

    void edit(Segnalazione segnalazione);

    void remove(Segnalazione segnalazione);

    Segnalazione find(Object id);

    List<Segnalazione> findAll();

    List<Segnalazione> findRange(int[] range);

    int count();
    
}
