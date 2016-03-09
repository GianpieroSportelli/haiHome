/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.FiltroDiRicerca;
import entity.Studente;
import java.util.ArrayList;
import javax.ejb.Local;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author gianp_000
 */
@Local
public interface GestoreRicercaLocal {

    boolean creaFiltroDiRicerca(double prezzo, ArrayList<String> listaQuartieri,boolean compresoCondominio, boolean compresoRiscaldamento);

    boolean selezionaCittà(String città);

    boolean aggiornaAFiltroAppartamento(int numeroLocali, int numeroBagni, int numeroCamereDaLetto, double metratura);

    boolean aggiornaAFiltroStanza(String tipo);

    JSONArray usaFiltroAttuale();

    boolean isFiltroAppartamento();

   boolean cambiaFiltroAttuale(long id_FiltroDiRicerca);

    JSONObject attualeToJSON();

    boolean persistiFiltroAttuale(Studente studente);

    ArrayList<String> getQuartieriCittà();

    ArrayList<String> getTipoStanza();

    double[] geocodeCurrentCity();
    
}
