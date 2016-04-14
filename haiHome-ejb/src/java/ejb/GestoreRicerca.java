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
import facade.StudenteFacadeLocal;
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
    @EJB
    private GestoreStudenteLocal gestoreStudente;

    FiltroDiRicerca filtroAttuale = null;

    Città cittàAttuale = null;
    private Collection<Annuncio> ricerca;
    @EJB
    private StudenteFacadeLocal studenteFacade;

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
                    Annuncio after = acceptAnnuncio(x, quartieriFiltro);
                    if (after != null) {
                        listResult.add(after);
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
    public boolean persistiFiltroAttuale(String id_studente) {
        Studente studente = gestoreStudente.getStudenteByID("" + id_studente);
        System.out.println(studente.toJSON());
        //filtroAttuale.setStudente(studente);
        filtroDiRicercaFacade.create(filtroAttuale);
        //System.out.println(filtroAttuale.getId());
        //System.out.println(filtroDiRicercaFacade.find(filtroAttuale.getId()));
        studente.addFiltro(filtroAttuale);
        studenteFacade.edit(studente);
        studente = gestoreStudente.getStudenteByID("" + id_studente);
        JSONArray filtri;
        try {
            filtri = studente.getListaFiltriPreferiti();
            System.out.println(filtri.length());
            for (int i = 0; i < filtri.length(); i++) {
                JSONObject filtro;
                filtro = filtri.getJSONObject(i);
                System.out.println(filtro.toString());
            }
        } catch (JSONException ex) {
            Logger.getLogger(GestoreRicerca.class.getName()).log(Level.SEVERE, null, ex);
        }

        return filtroDiRicercaFacade.find(filtroAttuale.getId()) != null;
    }

    private Annuncio acceptAnnuncio(Annuncio x, Collection<String> quartieriFiltro) {
        //Ricordati di aggiungere compreso riscaldamento e compreso condominio
        Annuncio result = new Annuncio();
        result.setId(x.getId());
        result.setCittà(x.getCittà());
        result.setArchiviato(x.isArchiviato());
        result.setAtomico(x.isAtomico());
        result.setCompresoCondominio(x.isCompresoCondominio());
        result.setCompresoRiscaldamento(x.isCompresoRiscaldamento());
        result.setDataInizioAffitto(x.getDataInizioAffitto());
        result.setDataPubblicazione(x.getDataPubblicazione());
        result.setDescrizione(x.getDescrizione());
        result.setIndirizzo(x.getIndirizzo());
        result.setLatLng(x.getLatLng());
        result.setListaStanza(x.getListaStanza());
        result.setLocatore(x.getLocatore());
        result.setMetratura(x.getMetratura());
        result.setNumeroStanze(x.getNumeroStanze());
        result.setOscurato(x.isOscurato());
        result.setPrezzo(x.getPrezzo());
        result.setQuartiere(x.getQuartiere());

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
            accept=false;
            if (filtroAttuale instanceof FiltroAppartamento) {
                FiltroAppartamento attuale = (FiltroAppartamento) filtroAttuale;
                if (!x.isAtomico()) {
                    accept = false;
                } else {
                    boolean out_prezzo=attuale.getPrezzo() != 0 && x.getPrezzo() > attuale.getPrezzo();
                    boolean out_locali=attuale.getNumeroLocali() != 0 && attuale.getNumeroLocali() > x.getNumeroStanze();
                    int[] stanze = countRoom(x);
                    boolean out_bagni=!out_locali && attuale.getNumeroBagni() != 0 && stanze[0] < attuale.getNumeroBagni();
                    boolean out_camere_letto=!out_locali &&attuale.getNumeroCamereDaLetto() != 0 && stanze[1] < attuale.getNumeroCamereDaLetto();
                    boolean out_met=!out_locali &&attuale.getMetratura() != 0 && attuale.getMetratura() > x.getMetratura();
                    boolean cmpCon=attuale.isCompresoCondominio() && !x.isCompresoCondominio();
                    boolean cmpRisc=attuale.isCompresoRiscaldamento() && !x.isCompresoRiscaldamento();
                    accept=!out_bagni &&!out_camere_letto && !out_locali && !out_met && !out_prezzo && !cmpCon && !cmpRisc;
                    /*if (attuale.getPrezzo() != 0 && x.getPrezzo() > attuale.getPrezzo()) {
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
                    if (attuale.isCompresoCondominio() && !x.isCompresoCondominio()) {
                        accept = false;
                    }
                    if (attuale.isCompresoRiscaldamento() && !x.isCompresoRiscaldamento()) {
                        accept = false;
                    }*/
                }

            } else if (filtroAttuale instanceof FiltroStanza) {
                FiltroStanza attuale = (FiltroStanza) filtroAttuale;
                boolean ok;
                if (!x.isAtomico()) {
                    Collection<Stanza> stanze = x.getListaStanza();
                    ArrayList<Stanza> newStanze = new ArrayList<Stanza>();
                    for (Stanza s : stanze) {
                        //Stanza s=stanze.get(i);
                        if (s instanceof StanzaInAffitto) {
                            StanzaInAffitto sF = (StanzaInAffitto) s;
                            boolean archiviato=sF.isArchiviato();
                            boolean out_prezzo=attuale.getPrezzo() != 0 && sF.getPrezzo() > attuale.getPrezzo();
                            boolean cmpCond=attuale.isCompresoCondominio() && !sF.isCompresoCondominio();
                            boolean cmpRisc=attuale.isCompresoRiscaldamento() && !sF.isCompresoRiscaldamento();
                            boolean tipo=sF.getTipo() != attuale.getTipo();
                            /*if (sF.isArchiviato()) {
                            } else if (attuale.getPrezzo() != 0 && sF.getPrezzo() > attuale.getPrezzo()) {
                            } else if (sF.getTipo() != attuale.getTipo()) {
                            } else if (attuale.isCompresoCondominio() && !sF.isCompresoCondominio()) {
                            } else if (attuale.isCompresoRiscaldamento() && !sF.isCompresoRiscaldamento()) {
                            } else {
                                ok = true;
                                newStanze.add(sF);
                            }*/
                            ok=!archiviato && !out_prezzo && !tipo && !cmpCond && !cmpRisc;
                            if(ok){
                              newStanze.add(sF);
                              accept=true;
                            }else{
                              StanzaInAffitto newS=new StanzaInAffitto();
                              newS.setId(sF.getId());
                              newS.setArchiviato(true);
                              newS.setCompresoCondominio(sF.isCompresoCondominio());
                              newS.setCompresoRiscaldamento(sF.isCompresoRiscaldamento());
                              newS.setFoto(sF.getFoto());
                              newS.setMetratura(sF.getMetratura());
                              newS.setPrezzo(sF.getPrezzo());
                              newS.setTipo(sF.getTipo());
                              newStanze.add(newS);
                            }
                        } else if (s instanceof StanzaAccessoria) {
                            StanzaAccessoria sA = (StanzaAccessoria) s;
                            newStanze.add(sA);
                        }
                    }
                    result.setListaStanza(newStanze);
                }else{
                    accept=false;
                }

                //accept = ok;
            } else {
                boolean out_prezzo_atomico=filtroAttuale.getPrezzo() != 0 && x.isAtomico() && x.getPrezzo() > filtroAttuale.getPrezzo();
                boolean cmpCon_atomico=filtroAttuale.isCompresoCondominio() && x.isAtomico() && !x.isCompresoCondominio();
                boolean cmpRisc_atomico=filtroAttuale.isCompresoRiscaldamento() && x.isAtomico() && !x.isCompresoRiscaldamento();
                /*if (filtroAttuale.getPrezzo() != 0 && x.isAtomico() && x.getPrezzo() > filtroAttuale.getPrezzo()) {
                    accept = false;
                } else {
                    accept = true;
                }*/
                accept=!out_prezzo_atomico && !cmpCon_atomico && !cmpRisc_atomico;
                if (!x.isAtomico()) {
                    boolean ok;
                    ArrayList<Stanza> newStanze = new ArrayList<Stanza>();
                    for (Stanza s : x.getListaStanza()) {
                        if (s instanceof StanzaInAffitto) {
                            StanzaInAffitto sF = (StanzaInAffitto) s;
                            boolean archiviato=sF.isArchiviato();
                            boolean out_prezzo=filtroAttuale.getPrezzo() != 0 && sF.getPrezzo() > filtroAttuale.getPrezzo();
                            boolean cmpCond=filtroAttuale.isCompresoCondominio() && !sF.isCompresoCondominio();
                            boolean cmpRisc=filtroAttuale.isCompresoRiscaldamento() && !sF.isCompresoRiscaldamento();
                            /*if (sF.isArchiviato()) {
                            } else if (filtroAttuale.getPrezzo() != 0 && sF.getPrezzo() > filtroAttuale.getPrezzo()) {
                            } else {
                                ok = true;
                                newStanze.add(sF);
                            }*/
                            ok=!archiviato && !out_prezzo && !cmpCond && !cmpRisc;
                            if(ok){
                              newStanze.add(sF); 
                              accept = ok;
                            }else{
                              StanzaInAffitto newS=new StanzaInAffitto();
                              newS.setId(sF.getId());
                              newS.setArchiviato(true);
                              newS.setCompresoCondominio(sF.isCompresoCondominio());
                              newS.setCompresoRiscaldamento(sF.isCompresoRiscaldamento());
                              newS.setFoto(sF.getFoto());
                              newS.setMetratura(sF.getMetratura());
                              newS.setPrezzo(sF.getPrezzo());
                              newS.setTipo(sF.getTipo());
                              newStanze.add(newS);
                            }
                        } else {
                            newStanze.add((StanzaAccessoria) s);
                        }
                    }
                    result.setListaStanza(newStanze);
                }
            }
        }
        if (!accept) {
            result = null;
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

    /**
     *
     * @param lat
     * @param lng
     * @param rad
     * @return
     */
    @Override
    public JSONArray getSupermarketNearBy(double lat, double lng, double rad) {
        JSONArray result = new JSONArray();
        ArrayList<JSONObject> superM = gmb.getSupermarketNearBy(lat, lng, rad);
        for (JSONObject x : superM) {
            result.put(x);
            //result.put(x.getId());
        }
        return result;
    }

    @Override
    public boolean eseguiRicerca() {
        if (filtroAttuale != null) {
            Collection<Annuncio> listResult = new ArrayList<>();
            Collection<Annuncio> annunci = filtroAttuale.getCittà().getAnnunci();
            Collection<String> quartieriFiltro = filtroAttuale.getListaQuartieri();
            for (Annuncio x : annunci) {
                Annuncio after = acceptAnnuncio(x, quartieriFiltro);
                if (after != null) {
                    listResult.add(after);
                }
            }
            this.ricerca = listResult;
            return true;
        }

        return false;
    }
}
