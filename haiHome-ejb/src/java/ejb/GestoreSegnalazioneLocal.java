/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.Local;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author SPORT
 */
@Local
public interface GestoreSegnalazioneLocal {

    /**
     * Genera nuova segnalazione
     * @param id_studente studente segnalante
     * @param id_annuncio annuncio segnalato
     * @param descrizione descrizione segnalazione
     * @return esito
     */
    boolean addSegnalazione(String id_studente, String id_annuncio, String descrizione);
    
    /**
     * restituisce tutte le segnalazioni 
     * @return JSONArray segnalazioni
     */
    public JSONArray getAllSegnalazioni();
    /**
     * Genera un hash map delle segnalazioni con chiave settata ad annuncio
     * @return hashmap key=Annuncio value=Lista segnalazioni per annuncio
     */
    JSONArray MapAnnunciSegnalati();
    /**
     * Archivia specifica segnalazione codificata in un json object
     * @param json
     * @param status true archivia false disarchivia
     * @return esito
     */
    public boolean archiviaSegnalazioni(JSONObject json,boolean status);
    
}
