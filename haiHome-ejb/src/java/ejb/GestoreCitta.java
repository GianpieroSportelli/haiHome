/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Città;
import entity.Quartiere;
import facade.CittàFacadeLocal;
import facade.QuartiereFacadeLocal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Eugenio Liso
 */
@Stateless
public class GestoreCitta implements GestoreCittaLocal {

    @EJB
    private CittàFacadeLocal cittàFacade;

    @EJB
    private QuartiereFacadeLocal quartiereFacade;

    /**
     * Inserisce una città nel Database
     *
     * @param nome Il nome della città da inserire
     * @return true se l'inserimento viene eseguito con successo, false
     * altrimenti
     */
    @Override
    public boolean insertCitta(String nome) {

        for (Città c : cittàFacade.findAll()) {
            if (c.getNome().equals(nome)) {
                //città già presente
                return false;
            }
        }

        Città city = new Città();
        city.setNome(nome);
        cittàFacade.create(city);
        return cittàFacade.find(city.getId()) != null;
    }

    /**
     * Cancella una città dal Database
     *
     * @param nome Il nome della città da eliminare
     * @return true se la città è stata eliminata, false altrimenti
     */
    @Override
    public boolean deleteCitta(String nome) {
        for (Città c : cittàFacade.findAll()) {
            if (c.getNome().equals(nome)) {
                cittàFacade.remove(c);
                return true;
            }
        }
        return false;
    }

    /**
     * Inserisce, in una determinata citta, un quartiere e i cap ad esso
     * associati
     *
     * @param nomeCittà Il nome della città
     * @param nomeQuartiere Il nome del quartiere
     * @param cap I CAP associati al quartiere
     * @return false se la città non è stata trovata, true se l'inserimento è
     * andato a buon fine
     */
    @Override
    public boolean insertQuartiere(String nomeCittà, String nomeQuartiere, Collection<String> cap) {
        Città find = null;
        for (Città x : cittàFacade.findAll()) {
            if (x.getNome().equalsIgnoreCase(nomeCittà)) {
                find = x;
            }
        }
        if (find != null) {

            Collection<Quartiere> quartieri = find.getListaQuartieri();
            if (quartieri == null) {
                quartieri = new ArrayList<>();
            }
            Quartiere nuovoQuartiere = new Quartiere();

            nuovoQuartiere.setNome(nomeQuartiere);
            nuovoQuartiere.setCap(cap);

            quartiereFacade.create(nuovoQuartiere);
            quartieri.add(nuovoQuartiere);

            find.setListaQuartieri(quartieri);
            cittàFacade.edit(find);
            return true;
        }
        return false;

    }

    /**
     * Restituisce il nome di tutte le città presenti nel Database
     *
     * @return Un arrayList contenente il nome delle città
     */
    @Override
    public ArrayList<String> getAllCittàNome() {
        ArrayList<String> lista = new ArrayList();
        for (Città city : cittàFacade.findAll()) {
            lista.add(city.getNome());
        }
        return lista;
    }

    /**
     * Restituisce i quartieri di una determinata città
     *
     * @param nomeCittà Il nome della città
     * @return Un arrayList contenente i quartieri della città specificata
     */
    @Override
    public ArrayList<String> getListaQuartieri(String nomeCittà) {
        ArrayList<String> listaQuartieri = null;
        Città find = null;
        for (Città x : cittàFacade.findAll()) {
            if (x.getNome().equalsIgnoreCase(nomeCittà)) {
                find = x;
            }
        }
        if (find != null) {
            Collection<Quartiere> quartieri = find.getListaQuartieri();
            listaQuartieri = new ArrayList<>();
            for (Quartiere q : quartieri) {
                listaQuartieri.add(q.getNome());
            }
        }
        return listaQuartieri;
    }

    @Override
    public HashMap<String, ArrayList<String>> getQuartieriCapMap() {
        HashMap<String, ArrayList<String>> capMap = new HashMap();
        for (Quartiere quartiere : quartiereFacade.findAll()) {
            ArrayList<String> caps = (ArrayList<String>) quartiere.getCap();
            for (String cap : caps) {
                if (capMap.containsKey(cap)) {
                    ArrayList<String> quartTemp = capMap.get(cap);
                    capMap.remove(cap);
                    quartTemp.add(quartiere.getNome());
                    capMap.put(cap, quartTemp);
                } else {
                    ArrayList<String> quartTemp = new ArrayList();
                    quartTemp.add(quartiere.getNome());
                    capMap.put(cap, quartTemp);
                }
            }
        }

        return capMap;
    }

    private HashMap<String, ArrayList<String>> capMap;

    @Override
    public void setcapMap(HashMap<String, ArrayList<String>> capMap) {
        this.capMap = capMap;
    }

    @Override
    public ArrayList<String> getQuartieriByCap(String cap) {
        return this.capMap.get(cap);
    }

    @Override
    public void buildQuartieriCapMap() {
        this.capMap = new HashMap();
        for (Quartiere quartiere : quartiereFacade.findAll()) {
            ArrayList<String> caps = (ArrayList<String>) quartiere.getCap();
            for (String cap : caps) {
                if (capMap.containsKey(cap)) {
                    ArrayList<String> quartTemp = capMap.get(cap);
                    capMap.remove(cap);
                    quartTemp.add(quartiere.getNome());
                    capMap.put(cap, quartTemp);
                } else {
                    ArrayList<String> quartTemp = new ArrayList();
                    quartTemp.add(quartiere.getNome());
                    capMap.put(cap, quartTemp);
                }
            }
        }

    }

}
