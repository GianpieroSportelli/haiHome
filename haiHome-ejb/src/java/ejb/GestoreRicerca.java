/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Annuncio;
import entity.Città;
import entity.FiltroAppartamento;
import entity.FiltroDiRicerca;
import entity.FiltroStanza;
import entity.TipoStanzaInAffitto;
import facade.AnnuncioFacadeLocal;
import facade.CittàFacadeLocal;
import facade.QuartiereFacadeLocal;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author gianp_000
 */
@Stateful
public class GestoreRicerca implements GestoreRicercaLocal {

    @EJB
    private QuartiereFacadeLocal quartiereFacade;
    @EJB
    private CittàFacadeLocal cittàFacade;
    @EJB
    private AnnuncioFacadeLocal annuncioFacade;

    FiltroDiRicerca filtroAttuale = null;

    Città cittàAttuale = null;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public boolean selezionaCittà(String città) {
        for (Città cit : cittàFacade.findAll()) {
            if (cit.getNome().equalsIgnoreCase(città)) {
                cittàAttuale = cit;
                filtroAttuale = new FiltroDiRicerca();
                filtroAttuale.setCittà(cittàAttuale);
                return true;
            }
        }
        return false;
    }

    //SE la lista è vuota per convenzione vuol dire tutti i quartieri di una determinata città
    @Override
    public boolean creaFiltroDiRicerca(double prezzo, ArrayList<String> listaQuartieri) {
        if (filtroAttuale == null) {
            return false;
        } else {
            filtroAttuale.setListaQuartieri(listaQuartieri);
            filtroAttuale.setPrezzo(prezzo);
            return true;
        }
    }

    @Override
    public boolean aggiornaAFiltroAppartamento(int numeroLocali, int numeroBagni, int numeroCamereDaLetto, double metratura) {
        if (filtroAttuale == null) {
            return false;
        } else if (filtroAttuale instanceof FiltroAppartamento || filtroAttuale instanceof FiltroStanza) {
            return false;
        } else {
            FiltroAppartamento nuovoAttuale = (FiltroAppartamento) filtroAttuale;
            nuovoAttuale.setMetratura(metratura);
            nuovoAttuale.setNumeroBagni(numeroBagni);
            nuovoAttuale.setNumeroCamereDaLetto(numeroCamereDaLetto);
            nuovoAttuale.setNumeroLocali(numeroLocali);
            filtroAttuale = nuovoAttuale;
            return true;
        }
    }

    @Override
    public boolean aggiornaAFiltroStanza(String tipo) {
        TipoStanzaInAffitto enumTipo;
        try {
            enumTipo = TipoStanzaInAffitto.valueOf(tipo);
            if (filtroAttuale == null) {
                return false;
            } else if (filtroAttuale instanceof FiltroAppartamento || filtroAttuale instanceof FiltroStanza) {
                return false;
            } else {
                FiltroStanza nuovoAttuale = (FiltroStanza) filtroAttuale;
                nuovoAttuale.setTipo(enumTipo);
                filtroAttuale = nuovoAttuale;
                return true;
            }
        } catch (IllegalArgumentException e) {
        }
        return false;
    }

    @Override
    public JSONArray usaFiltroAttuale() {
        JSONArray result = null;
        if (filtroAttuale != null) {
            ArrayList<Annuncio> listResult=new ArrayList();
            ArrayList<Annuncio> annunci=(ArrayList<Annuncio>) annuncioFacade.findAll();
            for(Annuncio x: annunci){
                
            }
            if (filtroAttuale instanceof FiltroAppartamento) {

            } else if (filtroAttuale instanceof FiltroAppartamento) {

            }
        }
        return result;
    }

}
