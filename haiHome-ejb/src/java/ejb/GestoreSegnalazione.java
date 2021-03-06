/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Annuncio;
import entity.Segnalazione;
import entity.Studente;
import facade.SegnalazioneFacadeLocal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author SPORT
 */
@Stateless
public class GestoreSegnalazione implements GestoreSegnalazioneLocal {

    @EJB
    private GestoreAnnuncioLocal gestoreAnnuncio;

    @EJB
    private GestoreStudenteLocal gestoreStudente;

    @EJB
    private SegnalazioneFacadeLocal segnalazioneFacade;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public boolean addSegnalazione(String id_studente, String id_annuncio, String descrizione) {
        Annuncio da_segnalare = gestoreAnnuncio.getAnnuncioByID(Long.valueOf(id_annuncio));
        Studente segnalatore = gestoreStudente.getStudenteByID(id_studente);
        Segnalazione nuova = new Segnalazione();
        boolean result = da_segnalare != null && segnalatore != null;
        if (result) {
            nuova.setStudente(segnalatore);
            nuova.setAnnuncio(da_segnalare);
            nuova.setDescrizione(descrizione);
            nuova.setDataSegnalazione(new Date());
            segnalazioneFacade.create(nuova);
        }
        return result;
    }

    @Override
    public JSONArray getAllSegnalazioni() {
        JSONArray result = null;
        result = new JSONArray();
        Collection<Segnalazione> allSegn = segnalazioneFacade.findAll();
        for (Segnalazione x : allSegn) {
            result.put(x.toJSON());
        }
        return result;
    }

    @Override
    public JSONArray MapAnnunciSegnalati() {
        JSONArray result = null;
        Collection<Segnalazione> allSegn = segnalazioneFacade.findAll();
        result=MaptoJSON(createMap(allSegn));
        return result;
    }
    
    
    private Collection<JSONSegn> createMap(Collection<Segnalazione> all){
        HashMap<Annuncio,Collection<SegnStudente>> result=new HashMap<>();
        Collection<JSONSegn> out=new ArrayList<>();
        for(Segnalazione x:all){
            //System.out.println("Annuncio: "+x.getAnnuncio().getId()+" Studente: "+x.getStudente().getId()+" archiviata? "+x.isArchiviato());
            if(result.containsKey(x.getAnnuncio())){
                result.get(x.getAnnuncio()).add(new SegnStudente(x.getStudente(),x.getDataSegnalazione(),x.getDescrizione(),""+x.getId(),x.isArchiviato()));
            }else{
               Collection<SegnStudente> list=new ArrayList<>();
               list.add(new SegnStudente(x.getStudente(),x.getDataSegnalazione(),x.getDescrizione(),""+x.getId(),x.isArchiviato()));
                result.put(x.getAnnuncio(), list);
            }
        }
        for(Annuncio x:result.keySet()){
            out.add(new JSONSegn(x, result.get(x)));
        }
        return out;
    }
    
    private JSONArray MaptoJSON(Collection<JSONSegn> map){
        JSONArray result = null;
        result=new JSONArray();
        Collections.sort((List<JSONSegn>) map);
        for(JSONSegn x:map){
            result.put(x.toJSON());
        }
        return result;
    }

    @Override
    public boolean archiviaSegnalazioni(JSONObject json,boolean status) {
        //System.out.println("Archivia?"+status);
        try {
            JSONArray studenti=json.getJSONArray("Studenti");
            for(int i=0;i<studenti.length();i++){
                JSONObject x=studenti.getJSONObject(i);
                String oid=x.getString("ID");
                boolean arch=x.getBoolean("Archiviato");
                //System.out.println("segnalazione: "+oid+" archiviata? "+arch);
                if(arch!=status){
                    Segnalazione actual=segnalazioneFacade.find(Long.valueOf(oid));
                    actual.setArchiviato(status);
                    segnalazioneFacade.edit(actual);
                }
            }
            
            return true;
        } catch (JSONException ex) {
            Logger.getLogger(GestoreSegnalazione.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
