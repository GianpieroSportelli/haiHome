/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Annuncio;
import entity.Città;
import entity.Locatore;
import entity.Stanza;
import entity.StanzaAccessoria;
import entity.StanzaInAffitto;
import entity.TipoStanzaAccessoria;
import entity.TipoStanzaInAffitto;
import facade.AnnuncioFacadeLocal;
import facade.CittàFacadeLocal;
import facade.LocatoreFacadeLocal;
import facade.StanzaAccessoriaFacadeLocal;
import facade.StanzaInAffittoFacadeLocal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import org.json.JSONObject;


/*
    INIZIALI
    private Locatore locatore;

    INDIRIZZO
    private String indirizzo;
    @ManyToOne
    private Città città;
    private String quartiere;
    private double[] latLng;


    APPARTAMENTO
    private String descrizione;
    private double metratura;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataInizioAffitto;
    private int numeroStanze;


    STANZE
    @OneToMany
    private Collection<Stanza> listaStanza;


    COSTI
    private boolean compresoCondominio;
    private boolean compresoRiscaldamento;
    private double prezzo = 0;


    FINALE
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataPubblicazione;
*/







/**
 *
 * @author gianp_000
 */
@Stateful
public class GestoreAnnuncio implements GestoreAnnuncioLocal {
    @EJB
    private LocatoreFacadeLocal locatoreFacade;
    @EJB
    private StanzaAccessoriaFacadeLocal stanzaAccessoriaFacade;
    @EJB
    private StanzaInAffittoFacadeLocal stanzaInAffittoFacade;
    
    
    
    @EJB
    private CittàFacadeLocal cittàFacade;
    @EJB
    private AnnuncioFacadeLocal annuncioFacade;
    
    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    private Annuncio annuncio;
    
    //serve come passo inziale dell'inserimento dell'annuncio, inserisce solo
    //il Locatore
    @Override
    public boolean CreaAnnuncio(Locatore locatore) {
        
        this.annuncio = new Annuncio();
        
        annuncio.setLocatore(locatore);
        
        /*
        TODO
         bisonga mettere i controlli, quando non si può creare un nuovo Annuncio?
            - Locatore bloccato     
        */
        if(true)
            return true;
        else
            return false;
    }
    
        @Override
    public boolean CreaAnnuncio(Object idLocatore) {
                
        this.annuncio = new Annuncio();
        /*
        List<Locatore> locatori =locatoreFacade.findAll();
        
        for(Locatore l: locatori){
            if(l.getId() == idLocatore){
                        annuncio.setLocatore(l);
                        return true;
            }
        }
        */

        Locatore l = locatoreFacade.find(idLocatore);
        this.annuncio.setLocatore(l);
        return l != null;
    }

    @Override
    public boolean inserisciInfoIndirizzo(String cittaStr, String quartiere, String indirizzo, double[] latlng) {
       if(this.annuncio==null)
           return false;
       
       //recupero la città dal nome
       Città città = new Città();
       List<Città> mieCittà = cittàFacade.findAll();
       boolean trovato=false;
       int i=0;
       while(!trovato){
           Città c = mieCittà.get(i);
           if(c.getNome().equalsIgnoreCase(cittaStr)){
               città = c;
               trovato = true;
           }
       }

               
       this.annuncio.setCittà(città);
       this.annuncio.setQuartiere(quartiere);
       this.annuncio.setIndirizzo(indirizzo);
       this.annuncio.setLatLng(latlng);
       return true;
    }

    @Override
    public boolean inserisciInfoAppartamento(String descrizione, double metratura, Date dataInizioAffitto, int numeroStanze, boolean atomico) {
        this.annuncio.setDescrizione(descrizione);
        this.annuncio.setMetratura(metratura);
        this.annuncio.setDataInizioAffitto(dataInizioAffitto);
        this.annuncio.setNumeroStanze(numeroStanze);
        this.annuncio.setListaStanza(new ArrayList<>());
        this.annuncio.setAtomico(atomico);
        return true;
    }

    /*
            Caso in cui annuncio.atomico = false e quindi il prezzo è riferito alla stanza
    I parametri metratura e prezzo non sono obbligatori, se settati a 0 non li setto
    */
    @Override
    public boolean inserisciNuovaStanzaInAffitto(String tipo, Collection<String> foto, boolean compresoCondominio, boolean compresoRiscaldamento, double metratura, double prezzo) {
        StanzaInAffitto nuovaStanza = new StanzaInAffitto();
        
        //il tipo diventa un int??? TODO
        nuovaStanza.setTipo(TipoStanzaInAffitto.Tripla);
        
        nuovaStanza.setFoto(foto);
        nuovaStanza.setCompresoCondominio(compresoCondominio);
        nuovaStanza.setCompresoRiscaldamento(compresoRiscaldamento);
        if(metratura!=0){
            nuovaStanza.setMetratura(metratura);
        }
        if(prezzo!=0){
            nuovaStanza.setPrezzo(prezzo);
        }

        //aggiungo nuova stanza
        Collection<Stanza> listaStanze = annuncio.getListaStanza();
        if(listaStanze.size() + 1 > annuncio.getNumeroStanze())
            return false;
        
        listaStanze.add(nuovaStanza);
        annuncio.setListaStanza(listaStanze);
        
        return true;
    }
    
        /*
    Caso in cui annuncio.atomico = true e quindi il prezzo è riferito all'appartamento
    I parametri metratura non sono obbligatori, se settati a 0 non li setto
    */
    @Override
    public boolean inserisciNuovaStanzaInAffitto(String tipo, Collection<String> foto,double metratura) {
        StanzaInAffitto nuovaStanza = new StanzaInAffitto();
        
        //il tipo diventa un int??? TODO
        nuovaStanza.setTipo(TipoStanzaInAffitto.Tripla);
        
        nuovaStanza.setFoto(foto);

        if(metratura!=0){
            nuovaStanza.setMetratura(metratura);
        }

        //aggiungo nuova stanza
        Collection<Stanza> listaStanze = annuncio.getListaStanza();
        if(listaStanze.size() + 1 > annuncio.getNumeroStanze())
            return false;
        
        listaStanze.add(nuovaStanza);
        annuncio.setListaStanza(listaStanze);
        
        return true;
    }

    
    /*

    Metratura non obbligatorio, se settato a 0 non lo setto
    */
    @Override
    public boolean inserisciNuovaStanzaAccessoria(String tipo, Collection<String> foto, double metratura) {
        StanzaAccessoria nuovaStanza = new StanzaAccessoria();
        
        if(tipo.compareToIgnoreCase(TipoStanzaAccessoria.Bagno.name())==0){
                nuovaStanza.setTipo(TipoStanzaAccessoria.Bagno);
        }else if(tipo.compareToIgnoreCase(TipoStanzaAccessoria.Cucina.name())==0){
                nuovaStanza.setTipo(TipoStanzaAccessoria.Cucina);
        }else if(tipo.compareToIgnoreCase(TipoStanzaAccessoria.Soggiorno.name())==0){
                nuovaStanza.setTipo(TipoStanzaAccessoria.Soggiorno);
        }else{
                nuovaStanza.setTipo(TipoStanzaAccessoria.Altro);
        }
        nuovaStanza.setFoto(foto);

        if(metratura!=0){
            nuovaStanza.setMetratura(metratura);
        }

        //aggiungo nuova stanza
        Collection<Stanza> listaStanze = annuncio.getListaStanza();
        if(listaStanze.size() + 1 > annuncio.getNumeroStanze())
            return false;
        
        listaStanze.add(nuovaStanza);
        annuncio.setListaStanza(listaStanze);
        
        return true;
    }

    @Override
    public boolean inserisciInfoCostiAppartamento(double prezzo, boolean compresoCondominio, boolean compresoRiscaldamento) {
            
            annuncio.setPrezzo(prezzo);
            annuncio.setCompresoCondominio(compresoCondominio);
            annuncio.setCompresoRiscaldamento(compresoRiscaldamento);
            return true;
    }



    @Override
    public boolean rendiAnnuncioPersistente() {
        GregorianCalendar gc = new GregorianCalendar();
        Date d = new Date();
        //TODO inserimento data, classe Date deprecata
        this.annuncio.setDataPubblicazione(d);
        Collection<Stanza> lista = this.annuncio.getListaStanza();

        for(Stanza s: lista){
            if(s instanceof StanzaInAffitto)
                stanzaInAffittoFacade.create((StanzaInAffitto) s);
            else if(s instanceof StanzaAccessoria)
                stanzaAccessoriaFacade.create((StanzaAccessoria) s);
            else
                return false;
        }
        annuncioFacade.create(this.annuncio);
        
        return true;
    }

    @Override
    public JSONObject toJSON() {
        return this.annuncio.toJSON();
    }


    
    
    



    
    
    
    
    
    
    
    
    
    
    
    
    
}
