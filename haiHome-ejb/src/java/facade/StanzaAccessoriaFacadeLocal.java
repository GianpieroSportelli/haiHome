/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.StanzaAccessoria;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gianp_000
 */
@Local
public interface StanzaAccessoriaFacadeLocal {

    void create(StanzaAccessoria stanzaAccessoria);

    void edit(StanzaAccessoria stanzaAccessoria);

    void remove(StanzaAccessoria stanzaAccessoria);

    StanzaAccessoria find(Object id);

    List<StanzaAccessoria> findAll();

    List<StanzaAccessoria> findRange(int[] range);

    int count();
    
}
