/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.StanzaInAffitto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gianp_000
 */
@Local
public interface StanzaInAffittoFacadeLocal {

    void create(StanzaInAffitto stanzaInAffitto);

    void edit(StanzaInAffitto stanzaInAffitto);

    void remove(StanzaInAffitto stanzaInAffitto);

    StanzaInAffitto find(Object id);

    List<StanzaInAffitto> findAll();

    List<StanzaInAffitto> findRange(int[] range);

    int count();
    
}
