/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Locatore;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gianp_000
 */
@Local
public interface LocatoreFacadeLocal {

    void create(Locatore locatore);

    void edit(Locatore locatore);

    void remove(Locatore locatore);

    Locatore find(Object id);

    List<Locatore> findAll();

    List<Locatore> findRange(int[] range);

    int count();
    
}
