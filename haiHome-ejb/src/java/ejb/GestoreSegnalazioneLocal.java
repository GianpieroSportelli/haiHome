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

    boolean addSegnalazione(String id_studente, String id_annuncio, String descrizione);

    public JSONArray getAllSegnalazioni();

    JSONArray MapAnnunciSegnalati();

    public boolean archiviaSegnalazioni(JSONObject json,boolean status);
    
}
