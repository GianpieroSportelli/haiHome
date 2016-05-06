/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Studente;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author SPORT
 */
public class SegnStudente {
    
    private Studente x;
    private Date data;
    private String descrizione;
    private boolean arch;
    private String oid;

           
    public SegnStudente(Studente st,Date dt,String des,String oid,boolean archiviata){
        x=st;
        data=dt;
        descrizione=des;
        this.oid=oid;
        this.arch=archiviata;
    }
    
    public boolean isArch() {
        return arch;
    }

    public void setArch(boolean arch) {
        this.arch = arch;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    
    public Studente getX() {
        return x;
    }

    public void setX(Studente x) {
        this.x = x;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
    
    public JSONObject toJSON() throws JSONException{
        JSONObject result=new JSONObject();
        result.accumulate("Studente", x.toJSON());
        if(this.data!=null){
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTime(this.data);
                result.accumulate("Data", ""+gc.get(Calendar.DAY_OF_MONTH)+"/"+(int) (gc.get(Calendar.MONTH)+1)+"/"+gc.get(Calendar.YEAR));
            }
        result.accumulate("Descrizione", descrizione);
        result.accumulate("ID", oid);
        result.accumulate("Archiviato", arch);
        return result;
    }
    
    
    
}
