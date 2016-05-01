/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import javax.ejb.Local;

/**
 *
 * @author Eugenio Liso
 */
@Local
public interface GestoreCittaLocal {

    public boolean insertCitta(String nome);
    
    public boolean deleteCitta(String nome);
    
    public boolean insertQuartiere(String nomeCittà, String nomeQuartiere, Collection<String> cap);
    
    public ArrayList<String> getListaQuartieri(String nomeCittà);
    
    public ArrayList<String> getAllCittàNome();
    
    public HashMap<String,ArrayList<String>> getQuartieriCapMap();
    
}
