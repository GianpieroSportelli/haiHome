/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Annuncio;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author gianp_000
 */
@Stateless
public class AnnuncioFacade extends AbstractFacade<Annuncio> implements AnnuncioFacadeLocal {
    @PersistenceContext(unitName = "haiHome-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AnnuncioFacade() {
        super(Annuncio.class);
    }
    
}
