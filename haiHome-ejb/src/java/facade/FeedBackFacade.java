/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.FeedBack;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author gianp_000
 */
@Stateless
public class FeedBackFacade extends AbstractFacade<FeedBack> implements FeedBackFacadeLocal {
    @PersistenceContext(unitName = "haiHome-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FeedBackFacade() {
        super(FeedBack.class);
    }
    
}
