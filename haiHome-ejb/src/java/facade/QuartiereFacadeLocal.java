/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Quartiere;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gianp_000
 */
@Local
public interface QuartiereFacadeLocal {

    void create(Quartiere quartiere);

    void edit(Quartiere quartiere);

    void remove(Quartiere quartiere);

    Quartiere find(Object id);

    List<Quartiere> findAll();

    List<Quartiere> findRange(int[] range);

    int count();
    
}
