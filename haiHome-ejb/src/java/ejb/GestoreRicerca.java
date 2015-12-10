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
import facade.AnnuncioFacadeLocal;
import facade.CittàFacadeLocal;
import facade.QuartiereFacadeLocal;
import java.util.ArrayList;
import java.util.Collection;
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

    @Override
    public JSONArray usaFiltroAttuale() {
        JSONArray result = null;
        if (filtroAttuale != null) {
            try {
                ArrayList<Annuncio> listResult = new ArrayList<>();
                Collection<Annuncio> annunci = filtroAttuale.getCittà().getAnnunci();
                Collection<String> quartieriFiltro =filtroAttuale.getListaQuartieri();

                for (Annuncio x : annunci) {
                    if (x.isArchiviato() || x.isOscurato()) {
                        break;
                    } else if (!quartieriFiltro.isEmpty()) {
                        for (String quart : quartieriFiltro) {
                            if (x.getQuartiere().equalsIgnoreCase(quart)) {
                                listResult.add(x);
                                break;
                            }
                        }
                    }
                }
                if (filtroAttuale instanceof FiltroAppartamento) {
                    FiltroAppartamento attuale = (FiltroAppartamento) filtroAttuale;

                    for (Annuncio x : listResult) {

                        if (x.getPrezzo() > attuale.getPrezzo()) {
                            listResult.remove(x);
                            break;
                        }

                        if (attuale.getNumeroLocali() > x.getNumeroStanze()) {
                            listResult.remove(x);
                            break;
                        } else {
                            //stanze[0] numeroBagni annuncio
                            //stanze[1] numeroCamereDaLetto
                            int[] stanze = countRoom(x);

                            if (stanze[0] < attuale.getNumeroBagni()) {
                                listResult.remove(x);
                                break;
                            }

                            if (stanze[1] < attuale.getNumeroCamereDaLetto()) {
                                listResult.remove(x);
                                break;
                            }
                        }
                        if (attuale.getMetratura() > x.getMetratura()) {
                            listResult.remove(x);
                            break;
                        }
                    }

                } else if (filtroAttuale instanceof FiltroStanza) {
                    FiltroStanza attuale = (FiltroStanza) filtroAttuale;
                    for (Annuncio x : listResult) {
                        boolean ok = false;
                        for (Stanza s : x.getListaStanza()) {
                            if (s instanceof StanzaInAffitto) {
                                StanzaInAffitto sF = (StanzaInAffitto) s;
                                if (sF.isArchiviato()) {
                                    break;
                                }
                                if (sF.getPrezzo() > attuale.getPrezzo()) {
                                    break;
                                }
                                if (sF.getTipo() != attuale.getTipo()) {
                                    break;
                                }
                                ok = true;
                            }
                        }
                        if (!ok) {
                            listResult.remove(x);
                        }
                    }
                } else {
                    for (Annuncio x : listResult) {

                        if (x.getPrezzo() > filtroAttuale.getPrezzo()) {
                            listResult.remove(x);
                            break;
                        }
                    }
                }
                result = ArrayListToJSONArray(listResult);
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
    private JSONArray ArrayListToJSONArray(ArrayList<Annuncio> annunci) throws JSONException {
        JSONArray result = new JSONArray();
        for (Annuncio x : annunci) {
            JSONObject attuale = new JSONObject();
            attuale.accumulate("Città", x.getCittà().getNome());
            attuale.accumulate("DataInizioAffitto", x.getDataInizioAffitto().toString());
            attuale.accumulate("DataPubblicazione", x.getDataPubblicazione().toString());
            attuale.accumulate("Prezzo", x.getPrezzo());
            attuale.accumulate("Quartiere", x.getQuartiere());
            attuale.accumulate("NumeroLocali", x.getNumeroStanze());
            attuale.accumulate("Locatore", x.getLocatore().toString());
            attuale.accumulate("Descrizione", x.getDescrizione());
            attuale.accumulate("Indirizzo", x.getIndirizzo());
            attuale.accumulate("LatLng", x.getLatLng());
            attuale.accumulate("Metratura", x.getMetratura());
            JSONArray JSONstanze = new JSONArray();
            ArrayList<Stanza> stanze = (ArrayList<Stanza>) x.getListaStanza();
            for (Stanza s : stanze) {
                JSONObject sAttuale = new JSONObject();
                sAttuale.accumulate("Foto", s.getFoto());
                sAttuale.accumulate("Metratura", s.getMetratura());
                if (s instanceof StanzaAccessoria) {
                    StanzaAccessoria sA = (StanzaAccessoria) s;
                    sAttuale.accumulate("Class", "Accessoria");
                    sAttuale.accumulate("Tipo", sA.getTipo().toString());
                } else {
                    StanzaInAffitto sI = (StanzaInAffitto) s;
                    if (!sI.isArchiviato()) {
                        sAttuale.accumulate("Class", "Affitto");
                        sAttuale.accumulate("Prezzo", sI.getPrezzo());
                        sAttuale.accumulate("Tipo", sI.getTipo());
                        sAttuale.accumulate("CompresoCondominio", sI.isCompresoCondominio());
                        sAttuale.accumulate("CompresoRiscaldamento", sI.isCompresoRiscaldamento());
                    }
                }
                JSONstanze.put(sAttuale);
            }
            attuale.accumulate("Stanze", JSONstanze);
            result.put(attuale);
        }
        return result;
    }

    @Override
    public boolean isFiltroAppartamento() {
        return filtroAttuale instanceof FiltroAppartamento;
    }
}
