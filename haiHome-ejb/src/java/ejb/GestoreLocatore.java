/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Locatore;
import facade.LocatoreFacadeLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import org.json.JSONObject;

/**
 *
 * @author gianp_000
 */
@Stateful
public class GestoreLocatore implements GestoreLocatoreLocal {

    @EJB
    private LocatoreFacadeLocal locatoreFacade;

    private Locatore locatore = null;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
//    public boolean aggiungiLocatore(String email, String nome, String cognome,
//        String foto, String password, String descrizione) {
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

    @Override
    public List<String> getLocatori() {
        List<Locatore> listaLocatori = locatoreFacade.findAll();
        List<String> result = new ArrayList<>();

        for (Locatore l : listaLocatori) {
            result.add(l.toString());
        }

        return result;
    }

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

    @Override
    public boolean removeLocatore() {
        boolean res = false;

        if (locatore != null) {
            locatoreFacade.remove(locatoreFacade.find(locatore.getId()));
            res = true;
        }

        return res;
    }

    /* Gestione profilo del locatore*/
    @Override
    public boolean modificaPassword(String oldpassword, String newpassword) {
        boolean res = false;

        if (this.locatore.getPassword().equals(oldpassword)) {
            this.locatore.setPassword(newpassword);
            res = true;
        }

        return res;
    }

    @Override
    public void modificaPassword(String newpassword) {
        this.locatore.setPassword(newpassword);
        rendiModifichePersistenti();
    }

    @Override
    public void modificaInfoProfilo(String telefono, String descrizione) {
        /* necessario controllo input ?? */
        this.locatore.setTelefono(telefono);
        this.locatore.setDescrizione(descrizione);
        rendiModifichePersistenti();
    }

    @Override
    public void modificaAvatarByURL(String img) {
        if (!this.locatore.getFotoProfilo().equalsIgnoreCase(img)) {
            this.locatore.setFotoProfilo(img);
            rendiModifichePersistenti();
        }
    }

    @Override
    public JSONObject toJSON() {
        return this.locatore.toJSON();
    }

    @Override
    public Locatore getLocatore() {
        return this.locatore;
    }

    private void rendiModifichePersistenti() {
        this.locatoreFacade.edit(this.locatore);
    }
}
