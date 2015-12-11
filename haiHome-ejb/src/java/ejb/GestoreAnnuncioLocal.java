/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Annuncio;
import entity.Città;
import entity.Locatore;
import entity.Quartiere;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;
import org.json.JSONObject;

/**
 * 
 * @author gianp_000
 */
@Local
public interface GestoreAnnuncioLocal {
    
    public boolean CreaAnnuncio(Locatore locatore);
    
    public boolean CreaAnnuncio(Object idLocatore);
    
    public boolean inserisciInfoIndirizzo(String citta, String quartiere,String indirizzo,double[] latlng );
    
    public boolean inserisciInfoAnnuncio(String descrizione, double metratura, Date dataInizioAffitto, int numeroStanze, boolean atomico);

    public boolean inserisciNuovaStanzaInAffitto(String tipo,Collection<String> foto,boolean compresoCondominio,boolean compresoRiscaldamento,double metratura, double prezzo);
    
    public boolean inserisciNuovaStanzaInAffitto(String tipo,Collection<String> foto,double metratura);
    
    public boolean inserisciNuovaStanzaAccessoria(String tipo,Collection<String> foto, double metratura);
        
    public boolean inserisciInfoCostiAppartamento(double prezzo, boolean compresoCondominio, boolean compresoRiscaldamento);
    
    public boolean rendiAnnuncioPersistente();
    
    public JSONObject toJSON();
    
    
     
    public boolean modificaAnnuncio(Annuncio annuncio);


    public boolean modificaInfoIndirizzo(String citta, String quartiere, String indirizzo, double[] latlng);


    public boolean modificaInfoAnnuncio(String descrizione, double metratura, Date dataInizioAffitto, int numeroStanze, boolean atomico);


    public boolean modificaNuovaStanzaInAffitto(String tipo, Collection<String> foto, boolean compresoCondominio, boolean compresoRiscaldamento, double metratura, double prezzo);


    public boolean modificaNuovaStanzaInAffitto(String tipo, Collection<String> foto, double metratura);


    public boolean modificaNuovaStanzaAccessoria(String tipo, Collection<String> foto, double metratura);


    public boolean modificaInfoCostiAppartamento(double prezzo, boolean compresoCondominio, boolean compresoRiscaldamento);


    public boolean rendiModifichePersistenti() ;
    /*
     COSTI
    private boolean compresoCondominio;
    private boolean compresoRiscaldamento;
    private double prezzo = 0;
    */
}
