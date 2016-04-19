/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Administrator;
import facade.AdministratorFacadeLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author gianp_000
 */
@Stateful
public class GestoreAdmin implements GestoreAdminLocal {

    @EJB
    private AdministratorFacadeLocal administratorFacade;

    @Override
    public boolean checkAdmin(String email) {
        List<Administrator> listaAdmin = administratorFacade.findAll();

        for (Administrator adm : listaAdmin) {
            if (adm.getEmail().compareToIgnoreCase(email) == 0
                    && adm.getPassword().compareToIgnoreCase(adm.getPassword()) == 0) {
                //ho trovato uno studente duplicato
                //Mi salvo il riferimento allo studente trovato
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addAdmin(String email, String password) {
        Administrator adm = new Administrator();
        adm.setEmail(email);
        adm.setPassword(password);

        //LO FACCIO DIVENTARE PERSISTENTE!
        administratorFacade.create(adm);

        //vedo se è stato inserito
        return administratorFacade.find(adm.getId()) == null;

    }
}
