/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Annuncio;
import entity.Locatore;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
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
    
    public boolean inserisciInfoAnnuncio(String descrizione, double metratura, Date dataInizioAffitto,boolean arredato);

    public boolean inserisciNuovaStanzaInAffitto(String tipo,Collection<String> foto,boolean compresoCondominio,boolean compresoRiscaldamento,double metratura, double prezzo);
    
    public boolean inserisciNuovaStanzaInAffitto(String tipo,Collection<String> foto,double metratura);
    
    public boolean inserisciNuovaStanzaAccessoria(String tipo,Collection<String> foto, double metratura);
    
    public boolean inserisciInfoStanze(int numeroStanze, boolean atomico);
        
    public boolean inserisciInfoCostiAppartamento(double prezzo, boolean compresoCondominio, boolean compresoRiscaldamento);
    
    public boolean rendiAnnuncioPersistente();

    
    //MODIFICA ANNUNCIO
    public boolean modificaAnnuncio(Annuncio annuncio);
    
    public boolean modificaAnnuncio(long oidAnnuncio);

    public boolean modificaInfoIndirizzo(String citta, String quartiere, String indirizzo, double[] latlng);

    public boolean modificaInfoAnnuncio(String descrizione, double metratura, Date dataInizioAffitto,boolean arredato);
    
    public boolean modificaInfoStanze(int numeroStanze, boolean atomico);
    
    public boolean modificaStanza(long oid, Collection<String> newFoto,Collection<String> deletedFoto, double metratura);
    
    public boolean modificaStanza(long oid, Collection<String> newFoto,Collection<String> deletedFoto, double metratura, double prezzo);
    
    public boolean modificaStanza(long oid, boolean CC, boolean CR, double prezzo);
    
    public boolean aggiungiStanza(String tipologia,String tipo, Collection<String> foto, double metratura, double prezzo);
    
    public String persistiEditedFoto(InputStream fotoStream, String nomePhoto, String denominazioneLocatore, String denominazioneStanza);

    //public boolean modificaStanzaInAffitto(long oid, String tipo, Collection<String> foto, boolean compresoCondominio, boolean compresoRiscaldamento, double metratura, double prezzo);

    //public boolean modificaStanzaInAffitto(long oid, String tipo, Collection<String> foto, double metratura);

    //public boolean modificaStanzaAccessoria(long oid, String tipo, Collection<String> foto, double metratura);

    public boolean modificaInfoCostiAppartamento(double prezzo, boolean compresoCondominio, boolean compresoRiscaldamento);

    public boolean rendiModifichePersistenti() ;
    
    //ELIMINA
    
    public boolean eliminaAnnuncio(long oidAnnuncio);
    
    //public boolean eliminaStanza(Stanza s);
    
    public boolean eliminaStanza(long oidStanza);
    
    //OSCURA
    
    public boolean oscusaAnnuncio(long oidAnnuncio, boolean val);
    
    // ARCHIVIA
    public boolean archiviaAnnuncio(long oidAnnuncio, boolean archiviato);
    
    public boolean archiviaStanza(long oidAnnuncio,long oidStanza, boolean archiviato);
    
    
    //UTILY FOTO
    
    public String persistiFoto(InputStream fotoStream,String nomePhoto, String denominazioneLocatore, String denominazioneStanza);
    
    
    
    //METODI DI RITORNO    
    public JSONObject toJSON();
    
    public JSONObject toJSON(long oid);
    
    public Annuncio predniAnnuncio(long oid);
    
    public ArrayList<String> getTipologieStanzaAffitto();
    
    public ArrayList<String> getTipologieStanzaAcc();
    
    //METODI DI SUPPORTO
    public boolean svuotaStante();
    
    public Annuncio getAnnuncioByID(long oid);

    
    //METODI DI STATO
    
    public HashMap<String, ArrayList<String>> getphotoTempPath();
    
    public void setphotoTempPath(HashMap<String, ArrayList<String>> photoTempPath);
    
    
    public ArrayList<String> geteditPhotoTempPath();
    
    public void seteditPhotoTempPath(ArrayList<String> editPhotoTempPath);
    
    public HashMap<String, ArrayList<String>> getstanzeInfo();
    
    public void setstanzeInfo(HashMap<String, ArrayList<String>> stanzeInfo);
    
    
    
    
}
