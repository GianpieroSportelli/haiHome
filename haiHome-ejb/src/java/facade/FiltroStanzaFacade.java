/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.FiltroStanza;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author gianp_000
 */
@Stateless
public class FiltroStanzaFacade extends AbstractFacade<FiltroStanza> implements FiltroStanzaFacadeLocal {
    @PersistenceContext(unitName = "haiHome-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FiltroStanzaFacade() {
        super(FiltroStanza.class);
    }
    
}
