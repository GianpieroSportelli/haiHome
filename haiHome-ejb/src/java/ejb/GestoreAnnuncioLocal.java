/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Annuncio;
import entity.Locatore;
import entity.Stanza;
import java.io.InputStream;
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
    
    
    //INSERIMENTO ANNUNCIO
    public boolean CreaAnnuncio(Locatore locatore);
    
    public boolean CreaAnnuncio(Object idLocatore);
    
    public boolean inserisciInfoIndirizzo(String citta, String quartiere,String indirizzo,double[] latlng);
    
    public boolean inserisciInfoAnnuncio(String descrizione, double metratura, Date dataInizioAffitto);

    public boolean inserisciNuovaStanzaInAffitto(String tipo,Collection<String> foto,boolean compresoCondominio,boolean compresoRiscaldamento,double metratura, double prezzo);
    
    public boolean inserisciNuovaStanzaInAffitto(String tipo,Collection<String> foto,double metratura);
    
    public boolean inserisciNuovaStanzaAccessoria(String tipo,Collection<String> foto, double metratura);
    
    public boolean inserisciInfoStanze(int numeroStanze, boolean atomico);
        
    public boolean inserisciInfoCostiAppartamento(double prezzo, boolean compresoCondominio, boolean compresoRiscaldamento);
    
    public boolean rendiAnnuncioPersistente();

    
    //MODIFICA ANNUNCIO
    public boolean modificaAnnuncio(Annuncio annuncio);

    public boolean modificaInfoIndirizzo(String citta, String quartiere, String indirizzo, double[] latlng, int numeroStanze, boolean atomico);

    public boolean modificaInfoAnnuncio(String descrizione, double metratura, Date dataInizioAffitto, int numeroStanze, boolean atomico);

    public boolean modificaStanzaInAffitto(long oid, String tipo, Collection<String> foto, boolean compresoCondominio, boolean compresoRiscaldamento, double metratura, double prezzo);

    public boolean modificaStanzaInAffitto(long oid, String tipo, Collection<String> foto, double metratura);

    public boolean modificaStanzaAccessoria(long oid, String tipo, Collection<String> foto, double metratura);

    public boolean modificaInfoCostiAppartamento(double prezzo, boolean compresoCondominio, boolean compresoRiscaldamento);

    public boolean rendiModifichePersistenti() ;
    
    //ELIMINA
    
    public boolean eliminaAnnuncio(Annuncio annuncio);
    
    public boolean eliminaStanza(Stanza s);
    
    
    //UTILY FOTO
    
    public String persistiFoto(InputStream fotoStream,String nomePhoto, String denominazioneLocatore, String denominazioneStanza);
    
    
    
    //METODI DI RITORNO    
    public JSONObject toJSON();
    
    public Annuncio predniAnnuncio(long oid);
}
