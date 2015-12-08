/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Annuncio;
import entity.Città;
import entity.Locatore;
import facade.AnnuncioFacadeLocal;
import facade.CittàFacadeLocal;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;


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
    public boolean inserisciInfoAppartamento(String descrizione, double metratura, Date dataInizioAffitto, int numeroStanze) {
        this.annuncio.setDescrizione(descrizione);
        this.annuncio.setMetratura(metratura);
        this.annuncio.setDataInizioAffitto(dataInizioAffitto);
        this.annuncio.setNumeroStanze(numeroStanze);
        return true;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}
