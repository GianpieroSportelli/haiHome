package ejb;

import entity.Annuncio;
import entity.Locatore;
import facade.LocatoreFacadeLocal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import org.json.JSONObject;

/**
 * @author Nicola Licheri
 */
@Stateful
public class GestoreLocatore implements GestoreLocatoreLocal {

    @EJB
    private LocatoreFacadeLocal locatoreFacade;

    private Locatore locatore = null;

    /**
     * Aggiunge un nuovo locatore con le informazioni specificate
     *
     * @param email Email (univoca) del locatore
     * @param password Password (se null il locatore si è registrato tramite
     * social)
     * @param nome Nome del locatore
     * @param cognome Cognome del locatore
     * @param telefono Numero di telefono del locatore
     * @param img_profilo Url dell'immagine del profilo
     * @return true se l'operazione di inserimento è andata a buon fine, false
     * altrimenti
     */
    @Override
    public boolean aggiungiLocatore(String email, String password, String nome,
            String cognome, String telefono, String img_profilo) {
        Locatore loc = new Locatore();
        //inizializza l'oggetto
        loc.setEmail(email);        
        loc.setPassword(password);
        loc.setNome(nome);
        loc.setCognome(cognome);
        loc.setTelefono(telefono);
        loc.setFotoProfilo(img_profilo);
        loc.setDescrizione("");

        //rende persistente l'oggetto
        locatoreFacade.create(loc);
        //verifica esito inserimento
        Locatore check = locatoreFacade.find(loc.getId());
        boolean res = (check != null);
        //mantiene riferimento all'oggetto 
        if (res) {
            this.locatore = loc;
        }
        return res;
    }

    /**
     * Restituisce una lista di tutti i locatori memorizzati
     *
     * @return Lista di locatori
     */
    @Override
    public List<String> getLocatori() {
        List<Locatore> listaLocatori = locatoreFacade.findAll();
        List<String> result = new ArrayList<>();

        for (Locatore l : listaLocatori) {
            result.add(l.toString());
        }

        return result;
    }

    /**
     * Verifica se esiste il locatore associato all'email specificata. Se il
     * locatore esiste, il gestore viene inizializzato
     *
     * @param email Email dell locatore da cercare
     * @return true se il locatore esiste, false altrimenti
     */
    @Override
    public boolean checkLocatore(String email) {
        List<Locatore> listaLocatori = locatoreFacade.findAll();
        locatore = null;

        for (Locatore l : listaLocatori) {
            if (l.getEmail().equalsIgnoreCase(email)) {
                locatore = l;
                break;
            }
        }

        return locatore != null;
    }

    /**
     * Verifica se esiste il locatore date le sue credenziali di accesso
     *
     * @param email Email del locatore da cercare
     * @param password Password del locatore
     * @return true se il locatore esiste, false altrimenti
     */
    @Override
    public boolean checkLocatore(String email, String password) {
        Locatore temp = null;

        if (this.checkLocatore(email)) {
            if (locatore.getPassword() != null && locatore.getPassword().equalsIgnoreCase(password)) {
                temp = locatore;
            }
        }

        locatore = temp;
        return locatore != null;
    }

    /**
     * Rimuove dal db il locatore attualmente in uso
     *
     * @return true se il locatore viene rimosso correttamente, false altrimenti
     */
    @Override
    public boolean removeLocatore() {
        boolean res = false;

        if (locatore != null) {
            locatoreFacade.remove(locatoreFacade.find(locatore.getId()));
            res = true;
        }

        return res;
    }

    /**
     * Modifica il campo password del locatore in uso
     *
     * @param oldpassword Password corrente al momento del cambio
     * @param newpassword Nuova password da impostare
     * @return true se il cambiamento viene effettuato, false altrimenti
     */
    @Override
    public boolean modificaPassword(String oldpassword, String newpassword) {
        boolean res = false;

        if (this.locatore.getPassword().equals(oldpassword)) {
            this.locatore.setPassword(newpassword);
            rendiModifichePersistenti();
            res = true;
        }

        return res;
    }

    /**
     * Modifica il campo password del locatore in uso
     *
     * @param newpassword Nuova password da impstare
     */
    @Override
    public void modificaPassword(String newpassword) {
        this.locatore.setPassword(newpassword);
        rendiModifichePersistenti();
    }

    /**
     * Modifica il campo telefono del locatore in uso
     * @param telefono Numero di telefono 
     */
    @Override
    public void modificaTelefono(String telefono) {
        this.locatore.setTelefono(telefono);
        rendiModifichePersistenti();
    }

    /**
     * Modifica il campo descrizione del locatore in uso
     * @param descrizione Descrizione 
     */
    @Override
    public void modificaDescrizione(String descrizione) {
        this.locatore.setDescrizione(descrizione);
        rendiModifichePersistenti();
    }

    /**
     * Modifica il campo fotoProfilo del locatore in uso
     * @param img URL immagine da impostare
     */
    @Override
    public void modificaAvatarByURL(String img) {
        if (!this.locatore.getFotoProfilo().equalsIgnoreCase(img)) {
            this.locatore.setFotoProfilo(img);
            rendiModifichePersistenti();
        }
    }

    /**
     * Restituisce tutti gli annunci del locatore in uso 
     * @return Collection di annunci
     */
    @Override
    public Collection<Annuncio> getAnnunci() {
        return this.locatore.getListaAnnunci();
    }

    /**
     * Restituisce gli annunci visibili del locatore in uso
     * @return Collection di annunci
     */
    @Override
    public List<Annuncio> getAnnunciVisibili() {
        List<Annuncio> res = new ArrayList<Annuncio>();

        for (Annuncio a : this.locatore.getListaAnnunci()) {
            if (!a.isArchiviato() && !a.isOscurato()) {
                res.add(a);
            }
        }

        return res;
    }

    /**
     * Restituisce gli annunci archiviati del locatore in uso
     * @return Collection di annunci 
     */
    @Override
    public List<Annuncio> getAnnunciArchiviati() {
        List<Annuncio> res = new ArrayList<Annuncio>();

        for (Annuncio a : this.locatore.getListaAnnunci()) {
            if (a.isArchiviato() && !a.isOscurato()) {
                res.add(a);
            }
        }

        return res;
    }

    /**
     * Restituisce gli annunci oscurati del locatore in uso
     * @return Collection di annunci
     */
    @Override
    public List<Annuncio> getAnnunciOscurati() {
        List<Annuncio> res = new ArrayList<Annuncio>();

        for (Annuncio a : this.locatore.getListaAnnunci()) {
            if (a.isOscurato()) {
                res.add(a);
            }
        }

        return res;
    }

    /**
     * Aggiunge un nuovo annuncio alla lista degli annunci del locatore in uso
     *  
     * @param a Annuncio da aggiungere
     * @return booleano, esito dell'operazione
     */
    @Override
    public boolean checkAnnuncio(Annuncio a) {
        return this.locatore.checkAnnuncio(a);
    }

    /**
     * Rimuove annuncio dalla lista degli annunci del locatore in uso 
     * 
     * @param a Annuncio da rimuovere 
     * @return booleano, esito dell'operazione
     */
    @Override
    public boolean removeAnnuncio(Annuncio a) {
        boolean res = this.locatore.removeAnnuncio(a);
        this.rendiModifichePersistenti();
        return res;
    }

    /**
     *
     * @param a Annuncio da rimuovere 
     * @return booleano, esito dell'operazione
     */
    @Override
    public boolean addAnnuncio(Annuncio a) {
        boolean res = this.locatore.addAnnuncio(a);
        this.rendiModifichePersistenti();
        return res;

    }

    /**
     * Aggiorna un singolo annuncio presente nella lista interna al locatore
     * 
     * @param oid ID identificativo dell'annuncio da aggiornare
     * @param a Oggetto di tipo Annuncio 
     * @return true se l'aggiornamento va a buon fine, false altrimenti
     */
    @Override
    public boolean updateAnnuncio(long oid, Annuncio a) {
        boolean res = false;

        if (a.getId() == oid && this.locatore.getListaAnnunci().contains(a)) {
            this.locatore.removeAnnuncio(a);
            this.locatore.addAnnuncio(a);
            this.rendiModifichePersistenti();
        }

        return res;
    }

    /**
     * Restituisce il JSON del locatore attualmente in uso
     * 
     * @return JSONObject del locatore in uso
     */
    @Override
    public JSONObject toJSON() {
        return this.locatore.toJSON();
    }

    /**
     * Restituisce l'oggetto Locatore del locatore attualmente in uso 
     * 
     * @return Oggetto di tipo Locatore del locatore in uso 
     */
    @Override
    public Locatore getLocatore() {
        return this.locatore;
    }

   
    private void rendiModifichePersistenti() {
        this.locatoreFacade.edit(this.locatore);
    }

    /**
     * Ricarica dal DB lo stato del locatore attualmente in uso 
     */
    @Override
    public void reloadLocatore() {
        if (this.locatore != null) {
            this.locatore = this.locatoreFacade.find(this.locatore.getId());
        }
    }

    /**
     * Blocca/sblocca un locatore dato il suo OID
     * 
     * @param oid OID del locatore da bloccare/sbloccare
     * @param bloccato valore booleano (bloccato/sbloccato) da settare
     * @return true se l'operazione viene eseguita con successo, false altrimenti
     */
    @Override
    public boolean bloccaLocatore(long oid, boolean bloccato) {
        Locatore loc = this.locatoreFacade.find(oid);
        boolean result = false;

        if (loc != null && loc.isBloccato() != bloccato) {
            loc.setBloccato(bloccato);
            this.locatoreFacade.edit(loc);
            result = true;
        }

        return result;
    }
}
