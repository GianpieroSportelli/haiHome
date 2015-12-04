/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.ArrayList;
import javax.ejb.Local;

/**
 *
 * @author gianp_000
 */
@Local
public interface GestoreTestLocal {

    void addCittà(String nome);

    ArrayList<String> getAllCittàNome();
    
    void addQuartiere(String città,String Quartiere);

    ArrayList<String> getListaQuartieriNome(String nomeCittà);

    void cancellaCittà(String nomeCittà);
    
    
}
