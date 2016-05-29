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
import facade.StanzaFacadeLocal;
import facade.StanzaInAffittoFacadeLocal;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import org.json.JSONObject;

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
    private StanzaFacadeLocal stanzaFacade;

    @EJB
    private CittàFacadeLocal cittàFacade;
    @EJB
    private AnnuncioFacadeLocal annuncioFacade;

    private Annuncio annuncio;

    private Città citta;

    private Locatore locatore;

    //serve come passo inziale dell'inserimento dell'annuncio, inserisce solo
    //il Locatore
    /**
     * Inizializza l'istanza Annuncio
     *
     * @param locatore - Oggetto locatore autore dell'annuncio
     * @return true - non fallisce
     */
    @Override
    public boolean CreaAnnuncio(Locatore locatore) {

        this.annuncio = new Annuncio();

        this.locatore = locatore;

        annuncio.setLocatore(this.locatore);

        return true;
    }

    /**
     *
     * Inizializza l'istanza Annuncio
     *
     * @param idLocatore - id del locatore autore dell'Annuncio
     * @return true - non fallisce
     */
    @Override
    public boolean CreaAnnuncio(Object idLocatore) {

        this.annuncio = new Annuncio();

        System.out.println(idLocatore.toString());
        Locatore l = locatoreFacade.find(idLocatore);

        this.locatore = l;

        annuncio.setLocatore(this.locatore);

        this.annuncio.setLocatore(l);
        checkPhotoFolder();
        return l != null;
    }

    /**
     *
     * Inserisce all'annuncio precedentemente istanzato le informazioni
     * riguardanti l'indirizzo dell'Annuncio
     *
     * @param cittaStr - città
     * @param quartiere - quartiere
     * @param indirizzo - indirizzo compreso di civico
     * @param latlng - coordinate
     *
     * @return false se l'annuncio non è stato istanziato / true altrimenti
     */
    @Override
    public boolean inserisciInfoIndirizzo(String cittaStr, String quartiere, String indirizzo, double[] latlng) {
        if (this.annuncio == null) {
            return false;
        }

        //recupero la città dal nome
        Città città = new Città();
        List<Città> mieCittà = cittàFacade.findAll();
        boolean trovato = false;
        int i = 0;
        while (!trovato && i < mieCittà.size()) {
            Città c = mieCittà.get(i);
            i++;
            if (c.getNome().equalsIgnoreCase(cittaStr)) {
                città = c;
                trovato = true;
            }
        }

        this.citta = città;
        this.annuncio.setCittà(this.citta);
        this.annuncio.setQuartiere(quartiere);
        this.annuncio.setIndirizzo(indirizzo);
        this.annuncio.setLatLng(latlng);
        return true;
    }

    /**
     *
     * Inserisce all'annuncio precedentemente istanzato le informazioni generali
     * dell'Annuncio
     *
     * @param descrizione
     * @param metratura
     * @param dataInizioAffitto
     * @param arredato - true se l'annuncio è ammobiliato, false altrimenti
     * @return false se l'annuncio non è stato istanziato / true altrimenti
     */
    @Override
    public boolean inserisciInfoAnnuncio(String descrizione, double metratura, Date dataInizioAffitto, boolean arredato) {
        if (this.annuncio == null) {
            return false;
        }

        this.annuncio.setDescrizione(descrizione);
        if (metratura != 0) {
            this.annuncio.setMetratura(metratura);
        }

        this.annuncio.setDataInizioAffitto(dataInizioAffitto);
        this.annuncio.setListaStanza(new ArrayList<>());
        this.annuncio.setArredato(arredato);
        return true;
    }

    /**
     *
     * Inserisce nuova stanza in affitto, comprensiva di prezzo, all'annuncio
     * già istanziato.
     *
     * Caso in cui annuncio.atomico = false e quindi il prezzo è riferito alla
     * stanza I parametri metratura e prezzo non sono obbligatori, se settati a
     * 0 non li setto
     *
     * @param tipo - tipo di stanza in affitto (singola, doppia ecc)
     * @param foto - Lista di foto(indirizzi) della stanza
     * @param compresoCondominio
     * @param compresoRiscaldamento
     * @param metratura - se uguale a 0 non viene settato (parametro opzionale)
     * @param prezzo
     * @return false se l'annuncio non è stato istanziato / true altrimenti
     */
    @Override
    public boolean inserisciNuovaStanzaInAffitto(String tipo, Collection<String> foto, boolean compresoCondominio, boolean compresoRiscaldamento, double metratura, double prezzo) {
        StanzaInAffitto nuovaStanza = new StanzaInAffitto();

        nuovaStanza.setTipo(TipoStanzaInAffitto.valueOf(tipo));

        nuovaStanza.setFoto(foto);
        nuovaStanza.setCompresoCondominio(compresoCondominio);
        nuovaStanza.setCompresoRiscaldamento(compresoRiscaldamento);
        if (metratura != 0) {
            nuovaStanza.setMetratura(metratura);
        }
        if (prezzo != 0) {
            nuovaStanza.setPrezzo(prezzo);
        }

        //aggiungo nuova stanza
        Collection<Stanza> listaStanze = annuncio.getListaStanza();
        if (listaStanze.size() + 1 > annuncio.getNumeroStanze()) {
            return false;
        }

        listaStanze.add(nuovaStanza);
        annuncio.setListaStanza(listaStanze);

        return true;
    }

    /*
    Caso in cui annuncio.atomico = true e quindi il prezzo è riferito all'appartamento
    I parametri metratura non sono obbligatori, se settati a 0 non li setto
     */
    /**
     *
     * Inserisce nuova stanza in affitto, NON comprensiva di prezzo,
     * all'annuncio già istanziato.
     *
     * Caso in cui annuncio.atomico = true e quindi il prezzo è riferito
     * all'appartamento I parametri metratura non sono obbligatori, se settati a
     * 0 non li setto
     *
     * @param tipo - tipo di stanza in affitto (singola, doppia ecc)
     * @param foto - Lista di foto(indirizzi) della stanza
     * @param metratura - se settata a 0 non viene impostata (parametro
     * opzionale)
     * @return false se l'annuncio non è stato istanziato / true altrimenti
     */
    @Override
    public boolean inserisciNuovaStanzaInAffitto(String tipo, Collection<String> foto, double metratura) {
        if (this.annuncio == null) {
            return false;
        }
        StanzaInAffitto nuovaStanza = new StanzaInAffitto();

        nuovaStanza.setTipo(TipoStanzaInAffitto.valueOf(tipo));

        nuovaStanza.setFoto(foto);

        if (metratura != 0) {
            nuovaStanza.setMetratura(metratura);
        }

        //aggiungo nuova stanza
        Collection<Stanza> listaStanze = annuncio.getListaStanza();
        if (listaStanze.size() + 1 > annuncio.getNumeroStanze()) {
            return false;
        }

        listaStanze.add(nuovaStanza);
        annuncio.setListaStanza(listaStanze);

        return true;
    }

    /*

    Metratura non obbligatorio, se settato a 0 non lo setto
     */
    /**
     *
     * Inserisce nuova stanza accessoria all'annuncio già istanziato.
     *
     * I parametri metratura non sono obbligatori, se settati a 0 non li setto
     *
     * @param tipo - tipo di stanza in affitto (singola, doppia ecc)
     * @param foto - Lista di foto(indirizzi) della stanza
     * @param metratura - se settata a 0 non viene impostata (parametro
     * opzionale)
     * @return false se l'annuncio non è stato istanziato / true altrimenti
     */
    @Override
    public boolean inserisciNuovaStanzaAccessoria(String tipo, Collection<String> foto, double metratura) {
        if (this.annuncio == null) {
            return false;
        }
        StanzaAccessoria nuovaStanza = new StanzaAccessoria();

        nuovaStanza.setTipo(TipoStanzaAccessoria.valueOf(tipo));
        nuovaStanza.setFoto(foto);

        if (metratura != 0) {
            nuovaStanza.setMetratura(metratura);
        }

        //aggiungo nuova stanza
        Collection<Stanza> listaStanze = annuncio.getListaStanza();
        if (listaStanze.size() + 1 > annuncio.getNumeroStanze()) {
            return false;
        }

        listaStanze.add(nuovaStanza);
        annuncio.setListaStanza(listaStanze);

        return true;
    }

    /**
     *
     * Inserisce info costi appartamento, se il campo annuncio.atomico=true
     *
     *
     * @param prezzo
     * @param compresoCondominio
     * @param compresoRiscaldamento
     * @return false se l'annuncio non è stato istanziato / true altrimenti
     */
    @Override
    public boolean inserisciInfoCostiAppartamento(double prezzo, boolean compresoCondominio, boolean compresoRiscaldamento) {

        if (this.annuncio == null) {
            return false;
        }

        annuncio.setPrezzo(prezzo);
        annuncio.setCompresoCondominio(compresoCondominio);
        annuncio.setCompresoRiscaldamento(compresoRiscaldamento);
        return true;
    }

    /**
     * Rende persistenti le modifiche dell'annuncio che si è trattato, inserisce
     * informazioni quali la data di pubblicazione e comunica con il Facade
     *
     * @return false se non persiste l'oggetto / true altrimenti
     */
    @Override
    public boolean rendiAnnuncioPersistente() {
        if (this.annuncio == null) {
            return false;
        }
        Date d = new Date();

        this.annuncio.setDataPubblicazione((Date) d);

        for (int i = 0; i < this.annuncio.getListaStanza().size(); i++) {

            Stanza s = ((List<Stanza>) this.annuncio.getListaStanza()).get(i);
            Collection<String> foto = s.getFoto();
            ArrayList<String> tempFoto = new ArrayList();

            for (String f : foto) {

                tempFoto.add(f.replace("_temp_", ""));
            }

            s.setFoto(tempFoto);

            if (s instanceof StanzaInAffitto) {
                stanzaInAffittoFacade.create((StanzaInAffitto) ((List<Stanza>) this.annuncio.getListaStanza()).get(i));
            } else if (s instanceof StanzaAccessoria) {
                stanzaAccessoriaFacade.create((StanzaAccessoria) ((List<Stanza>) this.annuncio.getListaStanza()).get(i));
            } else {
                return false;
            }

        }

        List<Stanza> list = stanzaFacade.findAll();

        for (Stanza s : list) {
            System.out.println("Id:" + s.getId());

        }
        changeNameFolder();

        annuncioFacade.create(this.annuncio);

        citta.addAnnuncio(this.annuncio);

        cittàFacade.edit(citta);

        locatore.addAnnuncio(annuncio);

        locatoreFacade.edit(locatore);

        locatore = locatoreFacade.find(locatore.getId());

        return true;
    }

    /**
     * Restituisce l'oggetto in questione in formato JSON
     *
     * @return JSON dell'annuncio in questione
     */
    @Override
    public JSONObject toJSON() {
        return this.annuncio.toJSON();
    }

    /**
     * Restituisce il JSON di un generico annuncio, in base al suo oid
     *
     * @param oid
     * @return
     */
    @Override
    public JSONObject toJSON(long oid) {
        Annuncio ann = annuncioFacade.find(oid);
        if (ann != null) {
            return ann.toJSON();
        } else {
            return null;
        }
    }

    /**
     * Inizializza l'istanza Annuncio che deve essere soggetta di modifiche
     *
     * @param annuncio - annuncio da modificare
     * @return non fallisce
     */
    @Override
    public boolean modificaAnnuncio(Annuncio annuncio) {
        this.annuncio = annuncio;

        return true;

    }

    /**
     * Inizializza l'istanza Annuncio che deve essere soggetta di modifiche
     *
     * @param oidAnnuncio - oid dell'annuncio annuncio da modificare
     * @return false se l'annuncio non esiste, true altrimenti
     */
    @Override
    public boolean modificaAnnuncio(long oidAnnuncio) {
        this.annuncio = annuncioFacade.find(oidAnnuncio);
        if (annuncio == null) {
            return false;
        }
        this.locatore = annuncio.getLocatore();
        this.citta = annuncio.getCittà();

        return true;
    }

    /**
     *
     * Modifiche le informazioni dell'indirizzo dell'annuncio in questione
     * 
     * @param citta
     * @param quartiere
     * @param indirizzo - compreso di cap
     * @param latlng
     * @return false se ci sono problemi con i dati/ true altrimenti
     */
    @Override
    public boolean modificaInfoIndirizzo(String citta, String quartiere, String indirizzo, double[] latlng) {
        if (this.annuncio == null) {
            return false;
        }

        if (!citta.equalsIgnoreCase(this.annuncio.getCittà().getNome())) {
            //recupero la città dal nome
            Città città = new Città();
            List<Città> mieCittà = cittàFacade.findAll();
            boolean trovato = false;
            int i = 0;
            while (!trovato && i < mieCittà.size()) {
                Città c = mieCittà.get(i);
                i++;
                if (c.getNome().equalsIgnoreCase(citta)) {
                    città = c;
                    trovato = true;
                }

            }
            if (!trovato) {
                return false;
            }
            this.annuncio.setCittà(città);
        }

        if (!quartiere.equalsIgnoreCase(this.annuncio.getQuartiere())) {
            this.annuncio.setQuartiere(quartiere);
        }

        if (!indirizzo.equalsIgnoreCase(this.annuncio.getIndirizzo())) {
            this.annuncio.setIndirizzo(indirizzo);
        }

        this.annuncio.setLatLng(latlng);
        return true;
    }

    /**
     * 
     *  Modifiche le informazioni generali dell'annuncio in questione
     * 
     * 
     * @param descrizione
     * @param metratura
     * @param dataInizioAffitto
     * @param arredato
     * @return false se l'annuncio non esiste / true altrimenti
     * 
     */
    @Override
    public boolean modificaInfoAnnuncio(String descrizione, double metratura, Date dataInizioAffitto, boolean arredato) {
        if(this.annuncio==null){
            return false;

        }
        this.annuncio.setDescrizione(descrizione);
        this.annuncio.setMetratura(metratura);
        this.annuncio.setDataInizioAffitto(dataInizioAffitto);
        this.annuncio.setArredato(arredato);
        return true;

    }

    /**
     * Metodo privato utile a restituire una stanza dell'annuncio in questione
     * in base al suo oid
     * @param oid - oid della stanza da trovare
     * @return stanza s oppure null se non esiste
     */
    private Stanza trovaStanza(long oid) {
        //ArrayList<Stanza> stanze = (ArrayList<Stanza>) this.annuncio.getListaStanza();
        Collection<Stanza> stanze = this.annuncio.getListaStanza();

        for (Stanza s : stanze) {
            if (s.getId() == oid) {
                return s;
            }
        }
        return null;
    }

    /**
     * 
     * METODO CHIAMATO NEL CASO DI ATOMICO=TRUE PER LE STANZE IN AFFITTO 
     * E PER TUTTE LE STANZE ACCESSORIE 
     * 
     * Modifica di una qualsiasi stanza riferita all'annuncio in questione, vede di che stanza si tratta
     * (usa trovaStanza) e chiama il metodo specifico
     * 
     * @param oid - oid stanza
     * @param newFoto - nuove foto aggiunte
     * @param deletedFoto - foto che sono state eliminate
     * @param metratura - metratura se 0 non viene settata
     * @return false se l'annuncio non è stato istanziato / altrimenti il risultato dei metodi specifici
     */
    @Override
    public boolean modificaStanza(long oid, Collection<String> newFoto, Collection<String> deletedFoto, double metratura) {
        if(this.annuncio==null){
            return false;
        }
        Stanza stanza = trovaStanza(oid);
        if (stanza instanceof StanzaAccessoria) {
            return modificaStanzaAccessoria(stanza, newFoto, deletedFoto, metratura);
        } else if (stanza instanceof StanzaInAffitto) {
            return modificaStanzaInAffitto(stanza, newFoto, deletedFoto, metratura);
        } else {
            return false;
        }

    }

        /**
     * 
     * //METODO CHIAMATO NEL CASO DI ATOMICO=FALSE PER LE STANZE IN AFFITO
     * Modifica di una stanza in affitto con atomico=false e, vede di che stanza si tratta
     * (usa trovaStanza) e chiama il metodo specifico
     * 
     * @param oid - oid stanza
     * @param newFoto - nuove foto aggiunte
     * @param deletedFoto - foto che sono state eliminate
     * @param metratura - metratura se 0 non viene settata
     * @param prezzo
     * @return false se l'annuncio non è stato istanziato / altrimenti il risultato dei metodi specifici
     */
    @Override
    public boolean modificaStanza(long oid, Collection<String> newFoto, Collection<String> deletedFoto, double metratura, double prezzo) {
        Stanza stanza = trovaStanza(oid);
        if (stanza instanceof StanzaAccessoria) {
            return false;
        } else if (stanza instanceof StanzaInAffitto) {
            return modificaStanzaInAffitto(stanza, newFoto, deletedFoto, metratura, prezzo);
        } else {
            return false;
        }

    }

    /**
     * Metodo solamente il prezzo della stanza aggiungendolo
     * 
     * @param oid - oid stanza
     * @param CC - compreso condominio
     * @param CR - compreso riscaldamento
     * @param prezzo 
     * @return false se non esiste l'annuncio / true altrimenti
     */
    @Override
    public boolean modificaStanza(long oid, boolean CC, boolean CR, double prezzo) {
        if(this.annuncio==null){
            return false;
        }
        Stanza stanza = trovaStanza(oid);

        StanzaInAffitto editedStanza = (StanzaInAffitto) stanza.clone();
        Collection<Stanza> listaStanze = this.annuncio.getListaStanza();
        listaStanze.remove(stanza);

        editedStanza.setPrezzo(prezzo);
        editedStanza.setCompresoCondominio(CC);
        editedStanza.setCompresoRiscaldamento(CR);

        stanzaInAffittoFacade.edit(editedStanza);
        listaStanze.add(editedStanza);
        this.annuncio.setListaStanza(listaStanze);
        annuncioFacade.edit(this.annuncio);
        return true;

    }

    
    /**
     * Modifica una stanza in affitto con atomico = true dell'annuncio in considerazione
     * 
     * @param stanza - oggetto stanza da modificare
     * @param newFoto - foto aggiunte
     * @param deletedFoto - foto eliminare
     * @param metratura
     * @return true non fallisce
     */
    private boolean modificaStanzaInAffitto(Stanza stanza, Collection<String> newFoto, Collection<String> deletedFoto, double metratura) {

        StanzaInAffitto editedStanza = (StanzaInAffitto) stanza.clone();
        Collection<Stanza> listaStanze = this.annuncio.getListaStanza();
        listaStanze.remove(stanza);

        if (metratura != editedStanza.getMetratura()) {
            editedStanza.setMetratura(metratura);
        }
        ArrayList<String> foto = (ArrayList<String>) editedStanza.getFoto();

        for (String deletedfoto : deletedFoto) {
            foto.remove(deletedfoto);
            PathUtily.eliminaFoto(deletedfoto);
        }

        for (String newfoto : newFoto) {
            foto.add(newfoto);
        }

        editedStanza.setFoto(foto);
        stanzaInAffittoFacade.edit(editedStanza);
        listaStanze.add(editedStanza);
        this.annuncio.setListaStanza(listaStanze);
        annuncioFacade.edit(this.annuncio);
        return true;
    }
    
    /**
     * 
     * Modifica una stanza in affitto con atomico = false dell'annuncio in considerazione
     * 
     * @param stanza - oggetto stanza da modificare
     * @param newFoto - foto aggiunte
     * @param deletedFoto - foto eliminate
     * @param metratura
     * @param prezzo
     * @return true non fallisce
     */
    private boolean modificaStanzaInAffitto(Stanza stanza, Collection<String> newFoto, Collection<String> deletedFoto, double metratura, double prezzo) {

        StanzaInAffitto editedStanza = (StanzaInAffitto) stanza.clone();
        Collection<Stanza> listaStanze = this.annuncio.getListaStanza();
        listaStanze.remove(stanza);

        if (metratura != editedStanza.getMetratura()) {
            editedStanza.setMetratura(metratura);
        }

        editedStanza.setPrezzo(prezzo);

        ArrayList<String> foto = (ArrayList<String>) editedStanza.getFoto();

        for (String deletedfoto : deletedFoto) {
            foto.remove(deletedfoto);
            PathUtily.eliminaFoto(deletedfoto);
        }

        for (String newfoto : newFoto) {
            foto.add(newfoto);
        }

        editedStanza.setFoto(foto);
        stanzaInAffittoFacade.edit(editedStanza);
        listaStanze.add(editedStanza);
        this.annuncio.setListaStanza(listaStanze);
        annuncioFacade.edit(this.annuncio);
        return true;
    }
    

    /**
     * 
     * Modifica di una stanza accessoria 
     * 
     * @param stanza - oggetto stanza da modificare
     * @param newFoto - nuove foto
     * @param deletedFoto - foto eliminare
     * @param metratura
     * @return true non fallisce
     */
    private boolean modificaStanzaAccessoria(Stanza stanza, Collection<String> newFoto, Collection<String> deletedFoto, double metratura) {

        StanzaAccessoria editedStanza = (StanzaAccessoria) stanza.clone();
        Collection<Stanza> listaStanze = this.annuncio.getListaStanza();
        listaStanze.remove(stanza);

        if (metratura != editedStanza.getMetratura()) {
            editedStanza.setMetratura(metratura);
        }
        ArrayList<String> foto = (ArrayList<String>) editedStanza.getFoto();

        for (String deletedfoto : deletedFoto) {
            foto.remove(deletedfoto);
            PathUtily.eliminaFoto(deletedfoto);
        }

        for (String newfoto : newFoto) {
            File fileTemp = (new File(newfoto));
            File fileDef = (new File(newfoto.replace("_temp_", "")));

            try {
                PathUtily.spostaFoto(fileTemp, fileDef);
            } catch (IOException ex) {
                System.out.println("Errore!!!!!");
                Logger.getLogger(GestoreAnnuncio.class.getName()).log(Level.SEVERE, null, ex);
            }

            foto.add(fileDef.getPath());
        }

        stanzaAccessoriaFacade.edit(editedStanza);
        listaStanze.add(editedStanza);
        this.annuncio.setListaStanza(listaStanze);
        annuncioFacade.edit(this.annuncio);
        return true;
    }

    /**
     * Aggiunge una nuoca stanza in fase di Modifica
     * 
     * @param tipologia - tipo ti stanza (accessoria o da letto)
     * @param tipo - tipo specifico
     * @param foto - foto della stanza 
     * @param metratura - se 0 non settata
     * @param prezzo
     * @return true non fallisce
     */
    @Override
    public boolean aggiungiStanza(String tipologia, String tipo, Collection<String> foto, double metratura, double prezzo) {

        if (tipologia.compareToIgnoreCase("1") == 0) {
            //stanza in affitto
            StanzaInAffitto s = new StanzaInAffitto();
            s.setMetratura(metratura);
            s.setFoto(foto);
            s.setTipo(TipoStanzaInAffitto.valueOf(tipo));
            if (prezzo > 0) {
                s.setPrezzo(prezzo);
                List<Stanza> listaStanze = (List<Stanza>) this.annuncio.getListaStanza();
                boolean trovato = false;
                int i = 0;
                while (!trovato) {
                    Stanza temp = listaStanze.get(i);
                    if (temp instanceof StanzaInAffitto) {
                        s.setCompresoCondominio(((StanzaInAffitto) temp).isCompresoCondominio());
                        s.setCompresoRiscaldamento(((StanzaInAffitto) temp).isCompresoRiscaldamento());
                        trovato = true;
                    }
                    i++;
                }

            }
            stanzaFacade.create(s);

            Collection<Stanza> oldListaStanze = this.annuncio.getListaStanza();
            oldListaStanze.add(s);
            this.annuncio.setListaStanza(oldListaStanze);

            this.annuncio.setNumeroStanze(this.annuncio.getNumeroStanze() + 1);
            annuncioFacade.edit(annuncio);
        } else {
            //stanza accessoria
            StanzaAccessoria s = new StanzaAccessoria();
            s.setMetratura(metratura);
            s.setFoto(foto);
            s.setTipo(TipoStanzaAccessoria.valueOf(tipo));

            stanzaFacade.create(s);

            Collection<Stanza> oldListaStanze = this.annuncio.getListaStanza();
            oldListaStanze.add(s);
            this.annuncio.setListaStanza(oldListaStanze);
            this.annuncio.setNumeroStanze(this.annuncio.getNumeroStanze() + 1);
            annuncioFacade.edit(annuncio);
        }

        return true;
    }

    /**
     * Modifica info coasti dell'appartamento (caso atomico = true)
     * @param prezzo
     * @param compresoCondominio
     * @param compresoRiscaldamento
     * @return false se l'annuncio è null true altrimenti
     */
    @Override
    public boolean modificaInfoCostiAppartamento(double prezzo, boolean compresoCondominio, boolean compresoRiscaldamento) {
        if(this.annuncio==null){
            return false;
        }
        annuncio.setPrezzo(prezzo);
        annuncio.setCompresoCondominio(compresoCondominio);
        annuncio.setCompresoRiscaldamento(compresoRiscaldamento);
        annuncioFacade.edit(this.annuncio);
        return true;
    }

    /**
     * Rende persistenti le modifiche dell'annuncio, va chiamato dopo qualsiasi modifica effettuata
     * @return true non fallsice
     */
    @Override
    public boolean rendiModifichePersistenti() {

        annuncioFacade.edit(this.annuncio);

        return true;
    }

    /**
     * Elimina un annuncio generito definito da un id
     * 
     * @param oidAnnuncio
     * @return false se l'annuncio non esiste, true altrimenti
     */
    @Override
    public boolean eliminaAnnuncio(long oidAnnuncio) {
        this.annuncio = annuncioFacade.find(oidAnnuncio);

        if(this.annuncio==null){
            return false;
        }
        
        Città miaCitta = this.annuncio.getCittà();
        
        
        
        for(Annuncio a: miaCitta.getAnnunci()){
            if(a.getId() == this.annuncio.getId()){
                miaCitta.getAnnunci().remove(a);
                break;
            }
        }
        
        
        
        Collection<Stanza> listaStanze = this.annuncio.getListaStanza();
        this.annuncio.setListaStanza(new ArrayList<>());

        for (Stanza s : listaStanze) {
            stanzaFacade.remove(s);
            ArrayList<String> foto = (ArrayList<String>) s.getFoto();
            for (String f : foto) {
                PathUtily.eliminaFoto(f);

            }
        }

        annuncioFacade.remove(annuncio);
        this.cittàFacade.edit(miaCitta);
        return true;
    }

    /**
     * Elimina una stanza appartenete all'annuncio in questione in base al suo oid
     * @param oidStanza
     * @return false se l'annuncio o la stanza sono null/ true altrimenti
     */
    @Override
    public boolean eliminaStanza(long oidStanza) {

        Stanza stanza = trovaStanza(oidStanza);
        if(stanza == null){
            return false;
        }
        Collection<Stanza> listaStanze = this.annuncio.getListaStanza();
        listaStanze.remove(stanza);
        annuncio.setListaStanza(listaStanze);
        this.annuncio.setNumeroStanze(this.annuncio.getNumeroStanze() - 1);
        annuncioFacade.edit(this.annuncio);

        if (stanza instanceof StanzaAccessoria) {
            this.stanzaAccessoriaFacade.remove((StanzaAccessoria) stanza);
        } else if (stanza instanceof StanzaInAffitto) {
            this.stanzaInAffittoFacade.remove((StanzaInAffitto) stanza);
        } else {
            return false;
        }
        return true;

    }

    
    /**
     * Restituisce un annuncio in base al suo id
     * @param oid
     * @return annuncio
     */
    @Override
    public Annuncio predniAnnuncio(long oid) {
        Annuncio temp = annuncioFacade.find(oid);

        return temp;
    }

    /**
     * Inserisce le informazioni delle stanze quali numero e informazioni sulla tipologia del prezzo
     * atomico/non atomico
     * 
     * @param numeroStanze
     * @param atomico
     * @return false se l'annuncio non esiste, true altrimenti
     */
    @Override
    public boolean inserisciInfoStanze(int numeroStanze, boolean atomico) {
        if(this.annuncio==null){
            return false;
        }
        this.annuncio.setNumeroStanze(numeroStanze);
        this.annuncio.setAtomico(atomico);

        return true;
    }

    /**
     * Metodo utile per persistere una foto, fa uso della classe PathUtily
     * 
     * @param fotoStream - foto da memorizzare
     * @param nomePhoto - denominazione della foto
     * @param denominazioneLocatore - denominazione del locatore
     * @param denominazioneStanza - denominazione della stanza
     * @return restituisce il path della foto appena memorizzata
     */
    @Override
    public String persistiFoto(InputStream fotoStream, String nomePhoto, String denominazioneLocatore, String denominazioneStanza) {

        String pathFoto = PathUtily.getPhotoPath() + annuncio.getLocatore().getId();

        String pathTempFoto = PathUtily.getPhotoPath() + denominazioneLocatore + "//_temp_";

        File cartellaStanza = (new File(pathFoto));
        if (!cartellaStanza.exists()) {
            cartellaStanza.mkdir();
        }

        File cartellaTemp = (new File(pathTempFoto));
        if (!cartellaTemp.exists()) {
            cartellaTemp.mkdir();
        } else {

        }

        Date d = new Date();

        Random r = new Random();

        String[] t = nomePhoto.split("\\.");

        String est = t[t.length - 1];

        String photoName = denominazioneLocatore + r.nextInt(100) + d.getTime() + "." + est;

        pathFoto = pathTempFoto + "//" + photoName;

        try {
            try (FileOutputStream tempFile = new FileOutputStream(pathFoto)) {
                int read;

                final byte[] bytes = new byte[1024];
                while ((read = fotoStream.read(bytes)) != -1) {
                    tempFile.write(bytes, 0, read);
                }

                tempFile.close();
                fotoStream.close();
            }

        } catch (FileNotFoundException ex) {

            Logger.getLogger(GestoreAnnuncio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GestoreAnnuncio.class.getName()).log(Level.SEVERE, null, ex);
        }

        return pathFoto;
    }

    
    /**
     * Metodo utile per cambiare i nomi dei path delle foto dopo
     * il trasferimento delle foto dalla cartella temporanea a quella definitiva
     * Funziona nell'Inserimento Annuncio
     * @return false se l'annuncio non esiste/ true altrimenti
     */
    private boolean changeNameFolder() {
        if(this.annuncio==null){
            return false;
        }
        boolean result = true;

        //path temporaneo
        String pathTempFoto = PathUtily.getPhotoPath() + annuncio.getLocatore().getId() + "//_temp_";
        File cartellaTemp = (new File(pathTempFoto));

        //path reale
        String pathFoto = PathUtily.getPhotoPath() + annuncio.getLocatore().getId();
        //File cartellaFoto = (new File(pathFoto));

        if (cartellaTemp.exists()) {
            String[] contenuto = cartellaTemp.list();
            for (int i = 0; i < contenuto.length; i++) {
                System.out.println("Nome file contenuto nella cartella " + contenuto[i]);
                File oldFoto = (new File(pathTempFoto + "//" + contenuto[i]));
                File newFoto = (new File(pathFoto + "//" + contenuto[i]));
                try {
                    PathUtily.spostaFoto(oldFoto, newFoto);

                } catch (IOException ex) {
                    System.out.println("Erroreeee");

                    Logger.getLogger(GestoreAnnuncio.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }

            }

        }

        return result;
    }

    /**
     * Metodo utile per spostare le foto da una cartella temporanea a quella definitiva
     */
    private void checkPhotoFolder() {

        String pathTempFoto = PathUtily.getPhotoPath() + annuncio.getLocatore().getId() + "//_temp_";

        File cartellaTemp = (new File(pathTempFoto));

        if (cartellaTemp.exists()) {
            String[] contenuto = cartellaTemp.list();
            for (int i = 0; i < contenuto.length; i++) {
                File f = new File(pathTempFoto + "//" + contenuto[i]);
                f.delete();
            }
        }

    }

    /**
     * Elimina tutte le stanze dell'annuncio in questione
     * 
     * @return false se l'annuncio non esiste/ true altrimenti
     * 
     */
    @Override
    public boolean svuotaStante() {
        if(this.annuncio==null){
            return false;
        }
        boolean result = this.annuncio.getListaStanza().isEmpty();
        this.annuncio.setListaStanza(new ArrayList<>());
        return result;
    }
    
    /**
     * Restituisce un annuncio in base al suo oid
     * @param oid - oid annuncio
     * @return istanza dell'annuncio
     */
    @Override
    public Annuncio getAnnuncioByID(long oid) {
        Annuncio ann = this.annuncioFacade.find(oid);
        return ann;
    }

    /**
     * Metodo utile modificare lo stato archiviato e non
     * di un annuncio generico identificato dal suo oid
     * 
     * @param oidAnnuncio
     * @param archiviato - true vuol dire che archiviamo, false che disarchiviamo
     * @return false se l'annuncio è gia archiviato (disarchiviato)
     * 
     */
    @Override
    public boolean archiviaAnnuncio(long oidAnnuncio, boolean archiviato) {
        this.annuncio = this.annuncioFacade.find(oidAnnuncio);

        //XOR potentissimo!!!!!!!! se dà vero vuol dire che i due valori sono diversi
        if (this.annuncio.isArchiviato() ^ archiviato) {
            this.annuncio.setArchiviato(archiviato);

            if (archiviato) {
                Date d = new Date();
                this.annuncio.setDataPubblicazione((Date) d);
            }

            this.annuncioFacade.edit(annuncio);
            return true;

        } else {
            return false;
        }

    }
    
    /**
     * Metodo utile all'archiviazione di una stanza di un annuncio generico, entrambi
     * identificati medianti oid
     * 
     * @param oidAnnuncio
     * @param oidStanza
     * @param archiviato - come sopra
     * @return come sopra
     */
    @Override
    public boolean archiviaStanza(long oidAnnuncio, long oidStanza, boolean archiviato) {
        this.annuncio = this.annuncioFacade.find(oidAnnuncio);

        for (int i = 0; i < this.annuncio.getListaStanza().size(); i++) {

            Stanza s = ((List<Stanza>) this.annuncio.getListaStanza()).get(i);

            if ((s.getId() == oidStanza)) {

                if (s instanceof StanzaInAffitto) {

                    if (((StanzaInAffitto) s).isArchiviato() ^ archiviato) {

                        StanzaInAffitto newStanza = (StanzaInAffitto) s.clone();
                        this.annuncio.getListaStanza().remove(s);
                        newStanza.setArchiviato(archiviato);
                        this.stanzaFacade.edit(newStanza);
                        this.annuncio.getListaStanza().add(newStanza);
                        this.annuncioFacade.edit(annuncio);
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }

        }
        return false;

    }

    /**
     * Restituisce le tipolofie di stanze in affitto 
     * @return tipologie stanze in affitto
     */
    @Override
    public ArrayList<String> getTipologieStanzaAffitto() {
        ArrayList<String> res = new ArrayList();
        TipoStanzaInAffitto[] array = TipoStanzaInAffitto.values();
        for (TipoStanzaInAffitto i : array) {
            res.add(i.toString());
        }
        return res;
    }

    /**
     * Restituisce le tipologie di stanza accessoria
     * 
     * @return tipologie stanza accessoria
     */
    @Override
    public ArrayList<String> getTipologieStanzaAcc() {
        ArrayList<String> res = new ArrayList();
        TipoStanzaAccessoria[] array = TipoStanzaAccessoria.values();
        for (TipoStanzaAccessoria i : array) {
            res.add(i.toString());
        }
        return res;
    }

    /**
     * Modifica info stanze dell'annuncio in questione quali numero e tipologia prezzo
     * 
     * @param numeroStanze
     * @param atomico
     * @return false se l'annuncio è null/true altrimenti
     */
    @Override
    public boolean modificaInfoStanze(int numeroStanze, boolean atomico) {
        
        if(this.annuncio==null){
            return false;
        }
        this.annuncio.setNumeroStanze(numeroStanze);
        this.annuncio.setAtomico(atomico);
        return true;
    }

    /**
     * Oscura un generico annuncio in base al suo oid                                                               In realtà chiede scusa all'Annuncio perchè è stato trattato male
     * @param oidAnnuncio
     * @param val - true se vogliamo orcurare / false se vogliamo dis-oscurare)           
     * @return risultato dell'XOR  
     * 
     */
    @Override
    public boolean oscusaAnnuncio(long oidAnnuncio, boolean val) {
        this.annuncio = annuncioFacade.find(oidAnnuncio);

        //XOR potentissimo!!!!!!!! se dà vero vuol dire che i due valori sono diversi
        if (this.annuncio.isOscurato() ^ val) {
            this.annuncio.setOscurato(val);
            this.annuncioFacade.edit(annuncio);
            return true;

        } else {
            return false;
        }
    }

    
    //GESTIONE DATI DI STATO
    private HashMap<String, ArrayList<String>> photoTempPath = new HashMap();

    /**
     * restituisce mappa temporanea stanza --> foto
     * @return mappa temporanea
     */
    @Override
    public HashMap<String, ArrayList<String>> getphotoTempPath() {
        return this.photoTempPath;
    }

    /**
     * Setta mappa temporanea stanze --> foto (inserimento)
     * @param photoTempPath 
     */
    @Override
    public void setphotoTempPath(HashMap<String, ArrayList<String>> photoTempPath) {
        this.photoTempPath = photoTempPath;

    }

    private ArrayList<String> editPhotoTempPath = new ArrayList();

    /**
     * restituisce mappa temporanea stanza --> foto (modifica)
     * @return mappa temporanea
     */
    @Override
    public ArrayList<String> geteditPhotoTempPath() {
        return this.editPhotoTempPath;
    }

    /**
     * Setta  mappa temporanea stanza --> foto (modifica)
     * @param editPhotoTempPath 
     */
    @Override
    public void seteditPhotoTempPath(ArrayList<String> editPhotoTempPath) {
        this.editPhotoTempPath = editPhotoTempPath;
    }

    private HashMap<String, ArrayList<String>> stanzeInfo = new HashMap();

    /**
     * restituisce mappa temporanea stanza --> prezzostanza
     * @return mappa temporanea
     */
    @Override
    public HashMap<String, ArrayList<String>> getstanzeInfo() {
        return this.stanzeInfo;
    }

    @Override
    public void setstanzeInfo(HashMap<String, ArrayList<String>> stanzeInfo) {
        this.stanzeInfo = stanzeInfo;

    }

}
