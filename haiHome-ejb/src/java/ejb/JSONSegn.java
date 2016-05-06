/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Annuncio;
import entity.Studente;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author SPORT
 */
class JSONSegn implements Comparable<JSONSegn>{
    private JSONObject result=null;
    private Annuncio x;
    private Collection<SegnStudente> list;
    private int n_segn;
    private boolean archiviato=true;
    
    public JSONSegn(Annuncio x,Collection<SegnStudente> list){
     this.x=x;
     this.list=list;
     for(SegnStudente s:list){
         if(!s.isArch()){
             archiviato=false;
             break;
         }
     }
     n_segn=list.size();
     result=new JSONObject();
        try {
            result.put("Annuncio",x.toJSON());
            JSONArray stud=new JSONArray();
            for(SegnStudente s:list){
                stud.put(s.toJSON());
            }
            result.put("Studenti", stud);
            result.put("NSegn",n_segn);
            result.put("Archiviato",archiviato);
        } catch (JSONException ex) {
            Logger.getLogger(JSONSegn.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }
    
    int getNsegn(){
        return n_segn;
    }

    @Override
    public int compareTo(JSONSegn o) {
       return -Integer.valueOf(n_segn).compareTo(o.getNsegn());
    }
    
    public JSONObject toJSON(){
        return result;
    }
    
    
}
