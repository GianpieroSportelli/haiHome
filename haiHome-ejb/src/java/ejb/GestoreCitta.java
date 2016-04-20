/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Città;
import facade.CittàFacadeLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Eugenio Liso
 */
@Stateless
public class GestoreCitta implements GestoreCittaLocal {

    @EJB
    private CittàFacadeLocal cittàFacade;

    @Override
    public boolean insertCitta(String nome) {

        for (Città c : cittàFacade.findAll()) {
            if (c.getNome().equals(nome)) {
                //città già presente
                return false;
            }
        }

        Città city = new Città();
        city.setNome(nome);
        cittàFacade.create(city);
        System.out.println(city.getId());
        return cittàFacade.find(city.getId()) != null;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public boolean deleteCitta(String nome) {
        for (Città c : cittàFacade.findAll()) {
            if (c.getNome().equals(nome)) {
                cittàFacade.remove(c);
                return true;
            }
        }
        return false;
    }
}
