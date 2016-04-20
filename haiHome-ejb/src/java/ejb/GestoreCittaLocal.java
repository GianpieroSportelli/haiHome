/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.Local;

/**
 *
 * @author Eugenio Liso
 */
@Local
public interface GestoreCittaLocal {

    public boolean insertCitta(String nome);
    
    public boolean deleteCitta(String nome);
}
