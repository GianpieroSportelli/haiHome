/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.Local;

/**
 *
 * @author gianp_000
 */
@Local
public interface GestoreAdminLocal {

    /**
     * verifica la presenza di un admin denominato dai parametri
     * @param email
     * @param password
     * @return valore di verità corrispondente al check
     */
    boolean checkAdmin(String email, String password);

    /**
     * Aggiunge un admin dati i parametri
     * @param email
     * @param password
     * @return esito
     */
    boolean addAdmin(String email, String password);

}
