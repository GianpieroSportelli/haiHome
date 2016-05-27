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
import entity.TipoStanzaAccessoria;
import entity.TipoStanzaInAffitto;
import facade.CittàFacadeLocal;
import facade.FiltroDiRicercaFacadeLocal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
    private CittàFacadeLocal cittàFacade;
    
    @EJB
    private GoogleMapsBeanLocal gmb;
    
    @EJB
    private GestoreStudenteLocal gestoreStudente;

    FiltroDiRicerca filtroAttuale = null;

    Città cittàAttuale = null;

 
    @Override
    public boolean selezionaCittà(String città) {
        for (Città cit : cittàFacade.findAll()) {
            if (cit.getNome().equalsIgnoreCase(città)) {
                cittàAttuale = cit;
                filtroAttuale = new FiltroDiRicerca();
                filtroAttuale.setCittà(cittàAttuale);
                filtroAttuale.setListaQuartieri(new ArrayList<String>());
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
    public boolean creaFiltroDiRicerca(double prezzo, ArrayList<String> listaQuartieri, boolean compresoCondominio, boolean compresoRiscaldamento, boolean arredato) {
        if (filtroAttuale == null) {
            return false;
        } else {
            Long id = filtroAttuale.getId();
            filtroAttuale = new FiltroDiRicerca();
            filtroAttuale.setId(id);
            filtroAttuale.setCittà(cittàAttuale);
            filtroAttuale.setListaQuartieri(listaQuartieri);
            filtroAttuale.setPrezzo(prezzo);
            filtroAttuale.setCompresoCondominio(compresoCondominio);
            filtroAttuale.setCompresoRiscaldamento(compresoRiscaldamento);
            filtroAttuale.setArredato(arredato);
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
            nuovoAttuale.setId(filtroAttuale.getId());
            nuovoAttuale.setCittà(filtroAttuale.getCittà());
            nuovoAttuale.setPrezzo(filtroAttuale.getPrezzo());
            nuovoAttuale.setListaQuartieri(filtroAttuale.getListaQuartieri());
            nuovoAttuale.setCompresoCondominio(filtroAttuale.isCompresoCondominio());
            nuovoAttuale.setCompresoRiscaldamento(filtroAttuale.isCompresoRiscaldamento());
            nuovoAttuale.setArredato(filtroAttuale.isArredato());
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
                nuovoAttuale.setId(filtroAttuale.getId());
                nuovoAttuale.setCittà(filtroAttuale.getCittà());
                nuovoAttuale.setPrezzo(filtroAttuale.getPrezzo());
                nuovoAttuale.setListaQuartieri(filtroAttuale.getListaQuartieri());
                nuovoAttuale.setCompresoCondominio(filtroAttuale.isCompresoCondominio());
                nuovoAttuale.setCompresoRiscaldamento(filtroAttuale.isCompresoRiscaldamento());
                nuovoAttuale.setArredato(filtroAttuale.isArredato());
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
                Collection<Annuncio> listResult = new ArrayList<>();
                Collection<Annuncio> annunci = filtroAttuale.getCittà().getAnnunci();
                Collection<String> quartieriFiltro = filtroAttuale.getListaQuartieri();
                for (Annuncio x : annunci) {
                    Annuncio after = acceptAnnuncio(x, quartieriFiltro);
                    if (after != null) {
                        listResult.add(after);
                    }
                }
                Collections.sort((List<Annuncio>) listResult);
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
        try {
            System.out.println(filtroAttuale.toJSON());
        } catch (JSONException ex) {
            Logger.getLogger(GestoreRicerca.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (filtroAttuale.getId() != null) {
            String id_filtro = "" + filtroAttuale.getId();
            this.removeFiltro(id_filtro, id_studente);
            filtroAttuale.setId(null);
            System.out.println("Filtro Cancellato: " + id_filtro);
        }
        filtroDiRicercaFacade.create(filtroAttuale);
        boolean persisti = filtroDiRicercaFacade.find(filtroAttuale.getId()) != null;
        boolean salvato = gestoreStudente.addFiltroStudente(id_studente, filtroAttuale);
        try {
            System.out.println("Filtro Creato- ID: " + filtroAttuale.getId() + " filtro: " + filtroAttuale.toJSON());
        } catch (JSONException ex) {
            Logger.getLogger(GestoreRicerca.class.getName()).log(Level.SEVERE, null, ex);
        }
        return persisti && salvato;
    }

    private Annuncio acceptAnnuncio(Annuncio x, Collection<String> quartieriFiltro) {
        Annuncio result = x.clone();

        boolean accept = false;
        
        boolean sentinella_Arredato = filtroAttuale.isArredato() && !x.isArredato();
        //Primo step di accettazione
        if (!x.getLocatore().isBloccato()) { //locatore bloccato
            if (!x.isArchiviato() && !x.isOscurato()) {
                if (!sentinella_Arredato) {
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
            }
        }
        
        if (accept) {
            //Superato primo step ora si passa a controlli più approfonditi
            accept = false;
            //FiltroAttuale istanza di FiltroAppartamento accettati solo annunci Appartamento
            if (filtroAttuale instanceof FiltroAppartamento) {
                FiltroAppartamento attuale = (FiltroAppartamento) filtroAttuale;
                if (!x.isAtomico()) {
                    accept = false;
                } else {
                    boolean out_prezzo = attuale.getPrezzo() != 0 && x.getPrezzo() > attuale.getPrezzo();
                    boolean out_locali = attuale.getNumeroLocali() != 0 &&  x.getNumeroStanze() < attuale.getNumeroLocali();
                    int[] stanze = countRoom(x);
                    boolean out_bagni = !out_locali && attuale.getNumeroBagni() != 0 && stanze[0] < attuale.getNumeroBagni();
                    boolean out_camere_letto = !out_locali && attuale.getNumeroCamereDaLetto() != 0 && stanze[1] < attuale.getNumeroCamereDaLetto();
                    boolean out_met = !out_locali && attuale.getMetratura() != 0 && x.getMetratura() < attuale.getMetratura();
                    boolean cmpCon = attuale.isCompresoCondominio() && !x.isCompresoCondominio();
                    boolean cmpRisc = attuale.isCompresoRiscaldamento() && !x.isCompresoRiscaldamento();
                    
                    accept = !out_bagni && !out_camere_letto && !out_locali && !out_met && !out_prezzo && !cmpCon && !cmpRisc;
                }

            }//filtroAttuale FiltroStanze accettati solo annunci per stanze che rispettano i criteri
            else if (filtroAttuale instanceof FiltroStanza) {
                FiltroStanza attuale = (FiltroStanza) filtroAttuale;
                boolean ok;
                if (!x.isAtomico()) {
                    Collection<Stanza> stanze = x.getListaStanza();
                    ArrayList<Stanza> newStanze = new ArrayList<>();
                    for (Stanza s : stanze) {
                        if (s instanceof StanzaInAffitto) {
                            StanzaInAffitto sF = (StanzaInAffitto) s;
                            boolean archiviato = sF.isArchiviato();
                            boolean out_prezzo = attuale.getPrezzo() != 0 && sF.getPrezzo() > attuale.getPrezzo();
                            boolean cmpCond = attuale.isCompresoCondominio() && !sF.isCompresoCondominio();
                            boolean cmpRisc = attuale.isCompresoRiscaldamento() && !sF.isCompresoRiscaldamento();
                            boolean tipo = sF.getTipo() != attuale.getTipo();
                            ok = !archiviato && !out_prezzo && !tipo && !cmpCond && !cmpRisc;
                            
                            if (ok || archiviato) {
                                newStanze.add(sF);
                                accept = accept || ok;
                            } else {
                                StanzaInAffitto newS = (StanzaInAffitto) sF.clone();
                                newS.setVisibile(false);
                                newStanze.add(newS);
                            }
                        } else if (s instanceof StanzaAccessoria) {
                            StanzaAccessoria sA = (StanzaAccessoria) s;
                            newStanze.add(sA);
                        }
                    }
                    result.setListaStanza(newStanze);
                } else {
                    accept = false;
                }

            } //filtro di tipo generale controllo di accettazione su entrambi i tipi di annnuncio
            else if (x.isAtomico()) {
                boolean out_prezzo_atomico = filtroAttuale.getPrezzo() != 0 && x.isAtomico() && x.getPrezzo() > filtroAttuale.getPrezzo();
                boolean cmpCon_atomico = filtroAttuale.isCompresoCondominio() && x.isAtomico() && !x.isCompresoCondominio();
                boolean cmpRisc_atomico = filtroAttuale.isCompresoRiscaldamento() && x.isAtomico() && !x.isCompresoRiscaldamento();
                accept = !out_prezzo_atomico && !cmpCon_atomico && !cmpRisc_atomico;
            } else {
                boolean ok;
                ArrayList<Stanza> newStanze = new ArrayList<>();
                for (Stanza s : x.getListaStanza()) {
                    if (s instanceof StanzaInAffitto) {
                        StanzaInAffitto sF = (StanzaInAffitto) s;
                        boolean archiviato = sF.isArchiviato();
                        boolean out_prezzo = filtroAttuale.getPrezzo() != 0 && sF.getPrezzo() > filtroAttuale.getPrezzo();
                        boolean cmpCond = filtroAttuale.isCompresoCondominio() && !sF.isCompresoCondominio();
                        boolean cmpRisc = filtroAttuale.isCompresoRiscaldamento() && !sF.isCompresoRiscaldamento();
                        boolean searchCriteria=!out_prezzo && !cmpCond && !cmpRisc;
                        
                        ok = !archiviato && searchCriteria;
                       
                        if (ok || archiviato) {
                            newStanze.add(sF);
                            accept = accept || ok;
                        } else {
                            StanzaInAffitto newS = (StanzaInAffitto) sF.clone();
                            newS.setVisibile(false);
                            newStanze.add(newS);
                        }
                    } else {
                        newStanze.add((StanzaAccessoria) s);
                    }
                }
                result.setListaStanza(newStanze);
            }
        }
        //fine accettazione annuncio
        
        
        
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

    private JSONArray CollectionToJSONArray(Collection<Annuncio> annunci) throws JSONException {
        JSONArray result = new JSONArray();
        for (Annuncio x : annunci) {
            result.put(x.toJSON());
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
    public JSONArray getSupermarketNearBy(double lat, double lng, double rad) {
        JSONArray result = new JSONArray();
        ArrayList<JSONObject> superM = gmb.getSupermarketNearBy(lat, lng, rad);
        for (JSONObject x : superM) {
            result.put(x);
        }
        return result;
    }

    @Override
    public JSONArray getBankNearBy(double lat, double lng, double rad) {
        JSONArray result = new JSONArray();
        ArrayList<JSONObject> superM = gmb.getBankNearBy(lat, lng, rad);
        for (JSONObject x : superM) {
            result.put(x);
        }
        return result;
    }

    @Override
    public JSONArray getBusNearBy(double lat, double lng, double rad) {
        JSONArray result = new JSONArray();
        ArrayList<JSONObject> superM = gmb.getBusNearBy(lat, lng, rad);
        for (JSONObject x : superM) {
            result.put(x);
        }
        return result;
    }

    @Override
    public boolean removeFiltro(String id_filtro, String id_studente) {
        FiltroDiRicerca filtroToDelete = filtroDiRicercaFacade.find(Long.valueOf(id_filtro));
        filtroDiRicercaFacade.remove(filtroToDelete);
        return gestoreStudente.removeFiltroStudente(id_studente, filtroToDelete);

    }

    @Override
    public boolean loadFiltro(String id) {
        filtroAttuale = filtroDiRicercaFacade.find(Long.valueOf(id));
        cittàAttuale = filtroAttuale.getCittà();
        try {
            System.out.println(filtroAttuale.toJSON().toString());
        } catch (JSONException ex) {
            Logger.getLogger(GestoreRicerca.class.getName()).log(Level.SEVERE, null, ex);
        }

        return filtroAttuale != null;
    }

    
    //METODI WS_RESTFUL
    
    @Override
    public JSONObject create_useFilter(JSONObject obj) {
        JSONObject result = new JSONObject();
        System.out.println(obj.toString());
        try {
            System.out.println(obj.getString("Tipo"));
            String citta = obj.getString("City");
            System.out.println(citta);
            boolean sel = this.selezionaCittà(citta);
            ArrayList<String> listaQ = new ArrayList<>();
            if (obj.has("Quartieri")) {
                JSONArray Jquartieri = obj.getJSONArray("Quartieri");
                System.out.println(Jquartieri.toString());
                for (int i = 0; i < Jquartieri.length(); i++) {
                    listaQ.add(Jquartieri.getString(i));
                }
            }
            double prezzo = 0;
            if (obj.has("Prezzo")) {
                prezzo = Double.valueOf("" + obj.get("Prezzo"));
            }
            boolean cmpC = false;
            boolean cmpR = false;
            boolean sentArr = false;
            boolean gen = this.creaFiltroDiRicerca(prezzo, listaQ, cmpC, cmpR, sentArr);
            boolean spec = true;
            if (obj.has("Tipo")) {
                String tipo = obj.getString("Tipo");
                if (tipo.equalsIgnoreCase("Appartamento")) {
                    int nLoc = Integer.valueOf(obj.getString("NumeroLocali"));
                    int nCamereL = Integer.valueOf(obj.getString("NumeroCamereLetto"));
                    int nB = Integer.valueOf(obj.getString("NumeroBagni"));
                    int met = Integer.valueOf(obj.getString("Metratura"));
                    spec = this.aggiornaAFiltroAppartamento(nLoc, nB, nCamereL, met);
                } else if (!tipo.equalsIgnoreCase("TUTTI")) {
                    String tipoS = obj.getString("Tipo");
                    spec = this.aggiornaAFiltroStanza(tipoS);
                }
            }
            if (sel & gen & spec) {
                JSONArray resultSearch = this.usaFiltroAttuale();
                JSONObject ok = new JSONObject();
                ok.accumulate("status", true);
                result.accumulate("esito", ok);
                result.accumulate("risultato", resultSearch);
            } else {
                JSONObject err = new JSONObject();
                err.accumulate("status", false);
                result.accumulate("esito", err);
            }
        } catch (JSONException ex) {
            //Logger.getLogger(GestoreRicerca.class.getName()).log(Level.SEVERE, null, ex);
            JSONObject err = new JSONObject();
            try {
                err.accumulate("status", false);
                result.accumulate("esito", err);
            } catch (JSONException ex1) {
                Logger.getLogger(GestoreRicerca.class.getName()).log(Level.SEVERE, null, ex1);
            }
            //result.put(err);
        }
        System.out.println(result.toString());
        return result;
    }

    @Override
    public JSONObject getQuartieri(String Città) {
        JSONObject result = new JSONObject();
        if (this.selezionaCittà(Città)) {
            JSONObject ok = new JSONObject();
            try {
                ok.accumulate("status", true);
                result.accumulate("esito", ok);
                ArrayList<String> quartieri = this.getQuartieriCittà();
                JSONArray quart = new JSONArray();
                for (String q : quartieri) {
                    quart.put(q);
                }
                result.accumulate("risultato", quart);
            } catch (JSONException ex) {
                Logger.getLogger(GestoreRicerca.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JSONObject err = new JSONObject();
            try {
                err.accumulate("status", false);
                result.accumulate("esito", err);
            } catch (JSONException ex1) {
                Logger.getLogger(GestoreRicerca.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return result;
    }

    @Override
    public JSONObject getTipoStanzaJSON() {
        JSONObject result = new JSONObject();
        try {
            result = new JSONObject();
            JSONObject ok = new JSONObject();
            ok.accumulate("status", true);
            result.accumulate("esito", ok);
            ArrayList<String> tipoStanze = this.getTipoStanza();
            JSONArray quart = new JSONArray();
            for (String q : tipoStanze) {
                quart.put(q);
            }
            result.accumulate("risultato", quart);
        } catch (JSONException ex) {
            Logger.getLogger(GestoreRicerca.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

   

}
