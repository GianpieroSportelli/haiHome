/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.FiltroDiRicerca;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gianp_000
 */
@Local
public interface FiltroDiRicercaFacadeLocal {

    void create(FiltroDiRicerca filtroDiRicerca);

    void edit(FiltroDiRicerca filtroDiRicerca);

    void remove(FiltroDiRicerca filtroDiRicerca);

    FiltroDiRicerca find(Object id);

    List<FiltroDiRicerca> findAll();

    List<FiltroDiRicerca> findRange(int[] range);

    int count();
    
}
