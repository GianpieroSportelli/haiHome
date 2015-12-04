/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Studente;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gianp_000
 */
@Local
public interface StudenteFacadeLocal {

    void create(Studente studente);

    void edit(Studente studente);

    void remove(Studente studente);

    Studente find(Object id);

    List<Studente> findAll();

    List<Studente> findRange(int[] range);

    int count();
    
}
