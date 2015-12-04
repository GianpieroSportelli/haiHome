/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Città;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gianp_000
 */
@Local
public interface CittàFacadeLocal {

    void create(Città città);

    void edit(Città città);

    void remove(Città città);

    Città find(Object id);

    List<Città> findAll();

    List<Città> findRange(int[] range);

    int count();
    
}
