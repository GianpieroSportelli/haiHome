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
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author gianp_000
 */
@Stateless
public class GestoreTest implements GestoreTestLocal {

    @EJB
    private QuartiereFacadeLocal quartiereFacade;

    @EJB
    private CittàFacadeLocal cittàFacade;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void addCittà(String nome) {
        Città nuova = new Città();
        nuova.setNome(nome);
        cittàFacade.create(nuova);
    }

    @Override
    public ArrayList<String> getAllCittàNome() {
        ArrayList<String> lista = new ArrayList();
        for (Città city : cittàFacade.findAll()) {
            lista.add(city.getNome());
        }
        return lista;
    }

    @Override
    public void addQuartiere(String città, String Quartiere) {
        Città find = null;
        for (Città x : cittàFacade.findAll()) {
            if (x.getNome().equalsIgnoreCase(città)) {
                find = x;
            }
        }
        if (find != null) {
            cittàFacade.remove(find);
            Collection<Quartiere> quartieri = find.getListaQuartieri();
            if (quartieri == null) {
                quartieri = new ArrayList<>();
            }
            Quartiere nuovoQuartiere = new Quartiere();
            nuovoQuartiere.setNome(Quartiere);
            quartiereFacade.create(nuovoQuartiere);
            quartieri.add(nuovoQuartiere);
            for (Quartiere quartiere : quartieri) {
                System.out.println(quartiere.getNome());
            }
            find.setListaQuartieri(quartieri);
            cittàFacade.create(find);
        }
    }

    @Override
    public ArrayList<String> getListaQuartieriNome(String nomeCittà) {
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
    public void cancellaCittà(String nomeCittà) {
        Città find = null;
        for (Città x : cittàFacade.findAll()) {
            if (x.getNome().equalsIgnoreCase(nomeCittà)) {
                find = x;
            }
        }
        if (find != null) {
            cittàFacade.remove(find);
            Collection<Quartiere> del=find.getListaQuartieri();
            for(Quartiere q: del)
                quartiereFacade.remove(q);
        }
    }
}


