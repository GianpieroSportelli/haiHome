/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Stanza;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gianp_000
 */
@Local
public interface StanzaFacadeLocal {

    void create(Stanza stanza);

    void edit(Stanza stanza);

    void remove(Stanza stanza);

    Stanza find(Object id);

    List<Stanza> findAll();

    List<Stanza> findRange(int[] range);

    int count();
    
}
