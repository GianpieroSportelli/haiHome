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
import entity.Stanza;
import entity.StanzaAccessoria;
import entity.StanzaInAffitto;
import entity.TipoStanzaAccessoria;
import entity.TipoStanzaInAffitto;
import facade.CittàFacadeLocal;
import facade.QuartiereFacadeLocal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import org.json.JSONArray;
import org.json.JSONException;

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
    public boolean creaFiltroDiRicerca(double prezzo, ArrayList<String> listaQuartieri, boolean compresoCondominio, boolean compresoRiscaldamento) {
        if (filtroAttuale == null) {
            return false;
        } else {
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
                Collection<Annuncio> listResultFinal = new ArrayList<>();
                Collection<Annuncio> annunci = filtroAttuale.getCittà().getAnnunci();
                Collection<String> quartieriFiltro = filtroAttuale.getListaQuartieri();

                for (Annuncio x : annunci) {
                    if (!x.isArchiviato() || !x.isOscurato()) {
                        if (!quartieriFiltro.isEmpty()) {
                            for (String quart : quartieriFiltro) {
                                if (x.getQuartiere().equalsIgnoreCase(quart)) {
                                    listResult.add(x);
                                }
                            }
                        } else {
                            listResult.add(x);
                        }
                    }
                }

                if (filtroAttuale instanceof FiltroAppartamento) {
                    FiltroAppartamento attuale = (FiltroAppartamento) filtroAttuale;
                    for (Annuncio x : listResult) {
                        if (!x.isAtomico()) {
                            continue;
                        }

                        if (attuale.getPrezzo() != 0 && x.getPrezzo() > attuale.getPrezzo()) {
                            continue;
                        }

                        if (attuale.getNumeroLocali() > x.getNumeroStanze()) {
                            continue;
                        } else {
                            //stanze[0] numeroBagni annuncio
                            //stanze[1] numeroCamereDaLetto
                            int[] stanze = countRoom(x);

                            if (stanze[0] < attuale.getNumeroBagni()) {
                                continue;
                            }

                            if (stanze[1] < attuale.getNumeroCamereDaLetto()) {
                                continue;
                            }
                        }
                        if (attuale.getMetratura() > x.getMetratura()) {
                            continue;
                        }
                        listResultFinal.add(x);
                    }

                } else if (filtroAttuale instanceof FiltroStanza) {
                    FiltroStanza attuale = (FiltroStanza) filtroAttuale;
                    for (Annuncio x : listResult) {
                        boolean ok = false;
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
                                ok = true;
                            }
                        }
                        if (ok) {
                            listResultFinal.add(x);
                        }
                    }
                } else if (filtroAttuale.getPrezzo() != 0) {
                    for (Annuncio x : listResult) {

                        if (x.getPrezzo() > filtroAttuale.getPrezzo()) {
                            continue;
                        }

                        boolean ok = false;
                        for (Stanza s : x.getListaStanza()) {
                            if (s instanceof StanzaInAffitto) {
                                StanzaInAffitto sF = (StanzaInAffitto) s;
                                if (sF.isArchiviato()) {
                                    continue;
                                }
                                if (sF.getPrezzo() > filtroAttuale.getPrezzo()) {
                                    continue;
                                }
                                ok = true;
                            }
                        }
                        if (ok) {
                            listResultFinal.add(x);
                        }
                    }
                }

                result = CollectionToJSONArray(listResultFinal);
                
            } catch (JSONException ex) {
                Logger.getLogger(GestoreRicerca.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
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
        }
        return result;
    }

    @Override
    public boolean isFiltroAppartamento() {
        return filtroAttuale instanceof FiltroAppartamento;
    }
}
