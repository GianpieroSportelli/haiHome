/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.FiltroStanza;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gianp_000
 */
@Local
public interface FiltroStanzaFacadeLocal {

    void create(FiltroStanza filtroStanza);

    void edit(FiltroStanza filtroStanza);

    void remove(FiltroStanza filtroStanza);

    FiltroStanza find(Object id);

    List<FiltroStanza> findAll();

    List<FiltroStanza> findRange(int[] range);

    int count();
    
}
