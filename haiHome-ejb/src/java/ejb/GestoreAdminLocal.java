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

    boolean checkAdmin(String email);

    boolean addAdmin(String email, String password);

}
