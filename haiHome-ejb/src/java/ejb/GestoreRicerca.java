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
import entity.Quartiere;
import entity.Stanza;
import entity.StanzaAccessoria;
import entity.StanzaInAffitto;
import entity.Studente;
import entity.TipoStanzaAccessoria;
import entity.TipoStanzaInAffitto;
import facade.CittàFacadeLocal;
import facade.FiltroDiRicercaFacadeLocal;
import facade.QuartiereFacadeLocal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateful;
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
    private FiltroDiRicercaFacadeLocal filtroDiRicercaFacade;
    @EJB
    private QuartiereFacadeLocal quartiereFacade;
    @EJB
    private CittàFacadeLocal cittàFacade;
    @EJB
    private GoogleMapsBeanLocal gmb;

    FiltroDiRicerca filtroAttuale = null;

    Città cittàAttuale = null;
    private Collection<Annuncio> ricerca;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public boolean selezionaCittà(String città) {
        for (Città cit : cittàFacade.findAll()) {
            if (cit.getNome().equalsIgnoreCase(città)) {
                cittàAttuale = cit;
                filtroAttuale = new FiltroDiRicerca();
                filtroAttuale.setCittà(cittàAttuale);
                filtroAttuale.setListaQuartieri(this.getQuartieriCittà());
                filtroAttuale.setPrezzo(0);
                filtroAttuale.setCompresoCondominio(false);
                filtroAttuale.setCompresoRiscaldamento(false);
                return true;
            }
        }
        return false;
    }

    //SE la lista è vuota per convenzione vuol dire tutti i quartieri di una determinata città
    @Override
    public boolean creaFiltroDiRicerca(double prezzo, ArrayList<String> listaQuartieri, boolean compresoCondominio, boolean compresoRiscaldamento) {
        if (filtroAttuale == null) {
            return false;
        } else {
            filtroAttuale = new FiltroDiRicerca();
            filtroAttuale.setCittà(cittàAttuale);
            filtroAttuale.setListaQuartieri(listaQuartieri);
            filtroAttuale.setPrezzo(prezzo);
            filtroAttuale.setCompresoCondominio(compresoCondominio);
            filtroAttuale.setCompresoRiscaldamento(compresoRiscaldamento);
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

            FiltroAppartamento nuovoAttuale = new FiltroAppartamento();
            nuovoAttuale.setCittà(filtroAttuale.getCittà());
            nuovoAttuale.setPrezzo(filtroAttuale.getPrezzo());
            nuovoAttuale.setListaQuartieri(filtroAttuale.getListaQuartieri());
            nuovoAttuale.setCompresoCondominio(filtroAttuale.isCompresoCondominio());
            nuovoAttuale.setCompresoRiscaldamento(filtroAttuale.isCompresoRiscaldamento());
            //END CAST
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
                FiltroStanza nuovoAttuale = new FiltroStanza();
                nuovoAttuale.setCittà(filtroAttuale.getCittà());
                nuovoAttuale.setPrezzo(filtroAttuale.getPrezzo());
                nuovoAttuale.setListaQuartieri(filtroAttuale.getListaQuartieri());
                nuovoAttuale.setCompresoCondominio(filtroAttuale.isCompresoCondominio());
                nuovoAttuale.setCompresoRiscaldamento(filtroAttuale.isCompresoRiscaldamento());
                //END CAST
                nuovoAttuale.setTipo(enumTipo);
                filtroAttuale = nuovoAttuale;
                return true;
            }
        } catch (IllegalArgumentException e) {
        }
        return false;
    }
//Non vengono considerati per ora il compresoRiscaldamento-Condominio

    @Override
    public JSONArray usaFiltroAttuale() {
        JSONArray result = null;
        if (filtroAttuale != null) {
            try {
                Collection<Annuncio> listResult = new ArrayList<>();
                Collection<Annuncio> annunci = filtroAttuale.getCittà().getAnnunci();
                /*for(Annuncio x:annunci){
                    System.out.println(x.toJSON());
                }*/
                Collection<String> quartieriFiltro = filtroAttuale.getListaQuartieri();

                for (Annuncio x : annunci) {
                    if (acceptAnnuncio(x, quartieriFiltro)) {
                        listResult.add(x);
                    }
                }

                result = CollectionToJSONArray(listResult);

            } catch (JSONException ex) {
                Logger.getLogger(GestoreRicerca.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    @Override
    public boolean isFiltroAppartamento() {
        return filtroAttuale instanceof FiltroAppartamento;
    }

    @Override
    public boolean cambiaFiltroAttuale(long id_FiltroDiRicerca) {
        filtroAttuale = filtroDiRicercaFacade.find(id_FiltroDiRicerca);
        return filtroAttuale != null;
    }

    @Override
    public JSONObject attualeToJSON() {
        try {
            return filtroAttuale.toJSON();
        } catch (JSONException ex) {
            Logger.getLogger(GestoreRicerca.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean persistiFiltroAttuale(Studente studente) {
        filtroAttuale.setStudente(studente);
        filtroDiRicercaFacade.create(filtroAttuale);
        return filtroDiRicercaFacade.find(filtroAttuale.getId()) != null;
    }

    private boolean acceptAnnuncio(Annuncio x, Collection<String> quartieriFiltro) {
        //Ricordati di aggiungere compreso riscaldamento e compreso condominio
        boolean accept = false;
        if (!x.isArchiviato() || !x.isOscurato()) {
            if (!quartieriFiltro.isEmpty()) {
                for (String quart : quartieriFiltro) {
                    if (x.getQuartiere().equalsIgnoreCase(quart)) {
                        accept = true;
                        break;
                    }
                }
            } else {
                accept = true;
            }
        }
        if (accept) {
            //accept = true;
            if (filtroAttuale instanceof FiltroAppartamento) {
                FiltroAppartamento attuale = (FiltroAppartamento) filtroAttuale;
                if (!x.isAtomico()) {
                    accept = false;
                } else {
                    if (attuale.getPrezzo() != 0 && x.getPrezzo() > attuale.getPrezzo()) {
                        accept = false;
                    }
                    if (attuale.getNumeroLocali() != 0 && attuale.getNumeroLocali() > x.getNumeroStanze()) {
                        accept = false;
                    } else {
                        //stanze[0] numeroBagni annuncio
                        //stanze[1] numeroCamereDaLetto
                        int[] stanze = countRoom(x);

                        if (attuale.getNumeroBagni() != 0 && stanze[0] < attuale.getNumeroBagni()) {
                            accept = false;
                        }

                        if (attuale.getNumeroCamereDaLetto() != 0 && stanze[1] < attuale.getNumeroCamereDaLetto()) {
                            accept = false;
                        }
                    }
                    if (attuale.getMetratura() != 0 && attuale.getMetratura() > x.getMetratura()) {
                        accept = false;
                    }
                    if(attuale.isCompresoCondominio() && !x.isCompresoCondominio()){
                        accept= false;
                    }
                    if(attuale.isCompresoRiscaldamento()&& !x.isCompresoRiscaldamento()){
                        accept= false;
                    }
                }

            } else if (filtroAttuale instanceof FiltroStanza) {
                FiltroStanza attuale = (FiltroStanza) filtroAttuale;
                boolean ok = false;
                if (!x.isAtomico()) {
                    for (Stanza s : x.getListaStanza()) {
                        if (s instanceof StanzaInAffitto) {
                            StanzaInAffitto sF = (StanzaInAffitto) s;
                            if (sF.isArchiviato()) {
                                continue;
                            }
                            if (attuale.getPrezzo() != 0 && sF.getPrezzo() > attuale.getPrezzo()) {
                                continue;
                            }
                            if (sF.getTipo() != attuale.getTipo()) {
                                continue;
                            }
                            if(attuale.isCompresoCondominio() && !sF.isCompresoCondominio()){
                                continue;
                            }
                            if(attuale.isCompresoRiscaldamento()&& !sF.isCompresoRiscaldamento()){
                                continue;
                            }
                            
                            ok = true;
                        }
                    }
                }
                accept = ok;
            } else {
                if (filtroAttuale.getPrezzo() != 0 && x.isAtomico() && x.getPrezzo() > filtroAttuale.getPrezzo()) {
                    accept = false;
                }
                if (!x.isAtomico()) {
                    boolean ok = false;
                    for (Stanza s : x.getListaStanza()) {
                        if (s instanceof StanzaInAffitto) {
                            StanzaInAffitto sF = (StanzaInAffitto) s;
                            if (sF.isArchiviato()) {
                                continue;
                            }
                            if (filtroAttuale.getPrezzo() != 0 && sF.getPrezzo() > filtroAttuale.getPrezzo()) {
                                continue;
                            }
                            ok = true;
                        }
                    }
                    accept = ok;
                }
            }
        }
        return accept;
    }

    private int[] countRoom(Annuncio x) {
        int nBagni = 0;
        int nStanzeDaLetto = 0;
        for (Stanza s : x.getListaStanza()) {
            if (s instanceof StanzaAccessoria) {
                StanzaAccessoria sA = (StanzaAccessoria) s;
                if (sA.getTipo() == TipoStanzaAccessoria.Bagno) {
                    nBagni++;
                }
            }
            if (s instanceof StanzaInAffitto) {
                StanzaInAffitto sF = (StanzaInAffitto) s;
                if (!sF.isArchiviato()) {
                    nStanzeDaLetto++;
                }
            }
        }
        int[] result = {nBagni, nStanzeDaLetto};
        return result;
    }

    //NOTA info Locatore prese con il .toString() cercare metodo migliore
    private JSONArray CollectionToJSONArray(Collection<Annuncio> annunci) throws JSONException {
        JSONArray result = new JSONArray();
        for (Annuncio x : annunci) {
            result.put(x.toJSON());
            //result.put(x.getId());
        }
        return result;
    }

    @Override
    public ArrayList<String> getQuartieriCittà() {
        ArrayList<String> result = new ArrayList();
        for (Quartiere q : this.cittàAttuale.getListaQuartieri()) {
            result.add(q.getNome());
        }
        return result;
    }

    @Override
    public ArrayList<String> getTipoStanza() {
        ArrayList<String> res = new ArrayList();
        TipoStanzaInAffitto[] array = TipoStanzaInAffitto.values();
        for (TipoStanzaInAffitto i : array) {
            res.add(i.toString());
        }
        return res;
    }

    @Override
    public double[] geocodeCurrentCity() {
        if (this.cittàAttuale != null) {
            return gmb.geocodingAddress(this.cittàAttuale.getNome());
        }
        return null;
    }

    @Override
    public boolean eseguiRicerca() {
        if (filtroAttuale != null) {
            Collection<Annuncio> listResult = new ArrayList<>();
            Collection<Annuncio> annunci = filtroAttuale.getCittà().getAnnunci();
            Collection<String> quartieriFiltro = filtroAttuale.getListaQuartieri();
            for (Annuncio x : annunci) {
                if (acceptAnnuncio(x, quartieriFiltro)) {
                    listResult.add(x);
                }
            }
            this.ricerca=listResult;
            return true;
        }

        return false;
    }
}
