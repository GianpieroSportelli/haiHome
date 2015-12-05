/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.FiltroDiRicerca;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author gianp_000
 */
@Stateless
public class FiltroDiRicercaFacade extends AbstractFacade<FiltroDiRicerca> implements FiltroDiRicercaFacadeLocal {
    @PersistenceContext(unitName = "haiHome-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FiltroDiRicercaFacade() {
        super(FiltroDiRicerca.class);
    }
    
}
