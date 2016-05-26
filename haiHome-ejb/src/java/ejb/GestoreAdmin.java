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

    /**
     * Controlla se un admin è presente con le credenziali fornite
     *
     * @param email La mail dell'admin
     * @param password La password dell'admin
     * @return true se l'admin con le credenziali fornite è presente, false
     * altrimenti
     */
    @Override
    public boolean checkAdmin(String email, String password) {
        List<Administrator> listaAdmin = administratorFacade.findAll();

        for (Administrator adm : listaAdmin) {
            if (adm.getEmail().equalsIgnoreCase(email)
                    && adm.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Aggiunge un admin con le credenziali fornite
     *
     * @param email La mail dell'admin
     * @param password La password dell'admin
     * @return true se l'admin è stato inserito con successo, false altrimenti
     */
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
