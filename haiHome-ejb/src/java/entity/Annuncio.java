/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author gianp_000
 */
@Entity
public class Annuncio implements Serializable,Comparable<Annuncio> {

    @ManyToOne
    private Locatore locatore;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String descrizione;

    private double prezzo = 0;

    private String indirizzo;

    @ManyToOne
    private Città città;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataPubblicazione;

    private double metratura;

    private String quartiere;

    private boolean archiviato;

    private boolean oscurato;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataInizioAffitto;

    @OneToMany
    private Collection<Stanza> listaStanza;

    private int numeroStanze;
    // latLng[0]=latitudine_googlemaps, latLng[1]=longitudine_googlemaps
    private double[] latLng;

    private boolean compresoCondominio;

    private boolean compresoRiscaldamento;

    private boolean atomico;
    
    private boolean arredato;

    public boolean isArredato() {
        return arredato;
    }

    public void setArredato(boolean arredato) {
        this.arredato = arredato;
    }

    /**
     * Get the value of atomico
     *
     * @return the value of atomico
     */
    public boolean isAtomico() {
        return atomico;
    }

    /**
     * Set the value of atomico
     *
     * @param atomico new value of atomico
     */
    public void setAtomico(boolean atomico) {
        this.atomico = atomico;
    }

    /**
     * Get the value of compresoRiscaldamento
     *
     * @return the value of compresoRiscaldamento
     */
    public boolean isCompresoRiscaldamento() {
        return compresoRiscaldamento;
    }

    /**
     * Set the value of compresoRiscaldamento
     *
     * @param compresoRiscaldamento new value of compresoRiscaldamento
     */
    public void setCompresoRiscaldamento(boolean compresoRiscaldamento) {
        this.compresoRiscaldamento = compresoRiscaldamento;
    }

    /**
     * Get the value of compresoCondominio
     *
     * @return the value of compresoCondominio
     */
    public boolean isCompresoCondominio() {
        return compresoCondominio;
    }

    /**
     * Set the value of compresoCondominio
     *
     * @param compresoCondominio new value of compresoCondominio
     */
    public void setCompresoCondominio(boolean compresoCondominio) {
        this.compresoCondominio = compresoCondominio;
    }

    /**
     * Get the value of prezzo
     *
     * @return the value of prezzo
     */
    public double getPrezzo() {
        return prezzo;
    }

    /**
     * Set the value of prezzo
     *
     * @param prezzo new value of prezzo
     */
    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    /**
     * Get the value of latLng
     *
     * @return the value of latLng
     */
    public double[] getLatLng() {
        return latLng;
    }

    /**
     * Set the value of latLng
     *
     * @param latLng new value of latLng
     */
    public void setLatLng(double[] latLng) {
        this.latLng = latLng;
    }

    /**
     * Get the value of numeroStanze
     *
     * @return the value of numeroStanze
     */
    public int getNumeroStanze() {
        return numeroStanze;
    }

    /**
     * Set the value of numeroStanze
     *
     * @param numeroStanze new value of numeroStanze
     */
    public void setNumeroStanze(int numeroStanze) {
        this.numeroStanze = numeroStanze;
    }

    /**
     * Get the value of listaStanza
     *
     * @return the value of listaStanza
     */
    public Collection<Stanza> getListaStanza() {
        return listaStanza;
    }

    /**
     * Set the value of listaStanza
     *
     * @param listaStanza new value of listaStanza
     */
    public void setListaStanza(Collection<Stanza> listaStanza) {
        this.listaStanza = listaStanza;
    }

    /**
     * Get the value of oscurato
     *
     * @return the value of oscurato
     */
    public boolean isOscurato() {
        return oscurato;
    }

    /**
     * Set the value of oscurato
     *
     * @param oscurato new value of oscurato
     */
    public void setOscurato(boolean oscurato) {
        this.oscurato = oscurato;
    }

    /**
     * Get the value of archiviato
     *
     * @return the value of archiviato
     */
    public boolean isArchiviato() {
        return archiviato;
    }

    /**
     * Set the value of archiviato
     *
     * @param archiviato new value of archiviato
     */
    public void setArchiviato(boolean archiviato) {
        this.archiviato = archiviato;
    }

    /**
     * Get the value of quartiere
     *
     * @return the value of quartiere
     */
    public String getQuartiere() {
        return quartiere;
    }

    /**
     * Set the value of quartiere
     *
     * @param quartiere new value of quartiere
     */
    public void setQuartiere(String quartiere) {
        this.quartiere = quartiere;
    }

    /**
     * Get the value of id_locatore
     *
     * @return the value of id_locatore
     */
    public Locatore getLocatore() {
        return locatore;
    }

    /**
     * Set the value of id_locatore
     *
     * @param locatore new value of locatore
     */
    public void setLocatore(Locatore locatore) {
        this.locatore = locatore;
    }

    /**
     * Get the value of metratura
     *
     * @return the value of metratura
     */
    public double getMetratura() {
        return metratura;
    }

    /**
     * Set the value of metratura
     *
     * @param metratura new value of metratura
     */
    public void setMetratura(double metratura) {
        this.metratura = metratura;
    }

    /**
     * Get the value of indirizzo
     *
     * @return the value of indirizzo
     */
    public String getIndirizzo() {
        return indirizzo;
    }

    /**
     * Set the value of indirizzo
     *
     * @param indirizzo new value of indirizzo
     */
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    /**
     * Get the value of dataInizioAffitto
     *
     * @return the value of dataInizioAffitto
     */
    public Date getDataInizioAffitto() {
        return dataInizioAffitto;
    }

    /**
     * Set the value of dataInizioAffitto
     *
     * @param dataInizioAffitto new value of dataInizioAffitto
     */
    public void setDataInizioAffitto(Date dataInizioAffitto) {
        this.dataInizioAffitto = dataInizioAffitto;
    }

    /**
     * Get the value of descrizione
     *
     * @return the value of descrizione
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Set the value of descrizione
     *
     * @param descrizione new value of descrizione
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the value of dataPubblicazione
     *
     * @return the value of dataPubblicazione
     */
    public Date getDataPubblicazione() {
        return dataPubblicazione;
    }

    /**
     * Set the value of dataPubblicazione
     *
     * @param dataPubblicazione new value of dataPubblicazione
     */
    public void setDataPubblicazione(Date dataPubblicazione) {
        this.dataPubblicazione = dataPubblicazione;
    }

    /**
     * Get the value of città
     *
     * @return the value of città
     */
    public Città getCittà() {
        return città;
    }

    /**
     * Set the value of città
     *
     * @param città new value of città
     */
    public void setCittà(Città città) {
        this.città = città;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Annuncio)) {
            return false;
        }
        Annuncio other = (Annuncio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Annuncio[ id=" + id + " ]";
    }
    
    public JSONObject toJSON(){
        
            
            JSONObject annuncioJSON = new JSONObject();
        try {
            annuncioJSON.accumulate("OID", this.getId());
            annuncioJSON.accumulate("Città", this.città.getNome());
            
            Calendar gc = new GregorianCalendar();
            gc.setTime(this.dataInizioAffitto);
            
            System.out.println("Data GC: " + gc.get(Calendar.DAY_OF_MONTH) + " " + (int) (gc.get(Calendar.MONTH)+1) + " " + gc.get(Calendar.YEAR));
            
            annuncioJSON.accumulate("DataInizioAffitto", ""+gc.get(Calendar.DAY_OF_MONTH)+"/"+(int) (gc.get(Calendar.MONTH)+1)+"/"+gc.get(Calendar.YEAR));
            if(this.dataPubblicazione!=null){
                gc = new GregorianCalendar();
                gc.setTime(this.dataPubblicazione);
                annuncioJSON.accumulate("DataPubblicazione", ""+gc.get(Calendar.DAY_OF_MONTH)+"/"+(int) (gc.get(Calendar.MONTH)+1)+"/"+gc.get(Calendar.YEAR));
            }
            annuncioJSON.accumulate("Prezzo", this.prezzo);
            annuncioJSON.accumulate("Quartiere", this.quartiere);
            annuncioJSON.accumulate("Archiviato", this.archiviato);
            annuncioJSON.accumulate("NumeroLocali", this.numeroStanze);
            annuncioJSON.accumulate("Locatore", locatore.toJSON()); 
            annuncioJSON.accumulate("Descrizione", this.descrizione);
            annuncioJSON.accumulate("Indirizzo", this.indirizzo);
            annuncioJSON.accumulate("Lat", this.latLng[0]);
            annuncioJSON.accumulate("Lng", this.latLng[1]);
            annuncioJSON.accumulate("Metratura", this.metratura);
            annuncioJSON.accumulate("Atomico", this.atomico);
            if(this.atomico){
                annuncioJSON.accumulate("CompresoRiscaldamento", this.compresoRiscaldamento);
                annuncioJSON.accumulate("CompresoCondominio", this.compresoCondominio); 
            }
            annuncioJSON.accumulate("Arredato", this.arredato);
            annuncioJSON.accumulate("Oscurato", this.oscurato);
            
            //aggiungo le stanze
            JSONArray JSONstanze = new JSONArray();
            for(Stanza s: this.listaStanza)       
                JSONstanze.put(s.toJSON());
                
            annuncioJSON.accumulate("Stanze", JSONstanze);
            
        } catch (JSONException ex) {
            Logger.getLogger(Annuncio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return annuncioJSON;
    }
    
    public Annuncio clone(){
        Annuncio result = new Annuncio();
        result.setId(this.getId());
        result.setCittà(this.getCittà());
        result.setArredato(this.isArredato());
        result.setArchiviato(this.isArchiviato());
        result.setAtomico(this.isAtomico());
        result.setCompresoCondominio(this.isCompresoCondominio());
        result.setCompresoRiscaldamento(this.isCompresoRiscaldamento());
        result.setDataInizioAffitto(this.getDataInizioAffitto());
        result.setDataPubblicazione(this.getDataPubblicazione());
        result.setDescrizione(this.getDescrizione());
        result.setIndirizzo(this.getIndirizzo());
        result.setLatLng(this.getLatLng());
        result.setListaStanza(this.getListaStanza());
        result.setLocatore(this.getLocatore());
        result.setMetratura(this.getMetratura());
        result.setNumeroStanze(this.getNumeroStanze());
        result.setOscurato(this.isOscurato());
        result.setPrezzo(this.getPrezzo());
        result.setQuartiere(this.getQuartiere());
        
        
        return result;
    }

   
    @Override
    public int compareTo(Annuncio o) {
        return -this.dataPubblicazione.compareTo(o.getDataPubblicazione()); //To change body of generated methods, choose Tools | Templates.
    }

}
