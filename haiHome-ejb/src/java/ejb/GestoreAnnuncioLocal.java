/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Città;
import entity.Locatore;
import entity.Quartiere;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;

/**
 * 
 *     protected Collection<String> foto;
        
    protected double metratura;
    * 
    * 
 * @author gianp_000
 */
@Local
public interface GestoreAnnuncioLocal {
    
    public boolean CreaAnnuncio(Locatore locatore);
    
    public boolean inserisciInfoIndirizzo(String citta, String quartiere,String indirizzo,double[] latlng );
    
    public boolean inserisciInfoAppartamento(String descrizione, double metratura, Date dataInizioAffitto, int numeroStanze);
    
    /*
    public boolean inserisciNuovaStanza(Collection<String> foto, double metratura, String tipo, double prezzo);
    
    public boolean eliminaStanza();
    
    public boolean modificaStanza();
    */
}
