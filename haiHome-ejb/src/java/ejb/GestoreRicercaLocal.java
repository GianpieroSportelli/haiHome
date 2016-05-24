/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

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
    
    /**
     * Genera un Filtro di Ricerca personalizzato in base alle richieste del utente
     *
     * 
     * @param prezzo valore reale indicatente il prezzo massimo di accettazione di un annuncio
     * @param listaQuartieri lista dei quartieri di interesse della città precedentemente selezionata per convenzione lista vuota corrisponde a selezionare tutti i quartieri
     * @param compresoCondominio se false non verrà considerato come un campo discriminante, se true l'annnuncio deve avere costo compresivo di spese condominio
     * @param compresoRiscaldamento se false non verrà considerato come un campo discriminante, se true l'annnuncio deve avere costo compresivo di spese riscaldamento
     * @param arredato se false non verrà considerato come un campo discriminante, se true l'annnuncio deve essere fornito arredato
     * @return valore di verità sul esito del generazione
     */
    boolean creaFiltroDiRicerca(double prezzo, ArrayList<String> listaQuartieri, boolean compresoCondominio, boolean compresoRiscaldamento, boolean arredato);

    /**
     * Metodo inizializzatore del gestoreRicerca, la selezione della città è
     * l'operazione iniziale essenziale per la creazione di un filtro di ricerca.
     * Crea un filtro di ricerca con valori standard {prezzo 0, Condominio
     * false, Riscaldamento false, Tutti i quartieri}
     *
     * @param città Stringa contenete il nome della città di interesse
     * @return valore di verità, true se la città esiste false altrimenti
     */
    boolean selezionaCittà(String città);

    /**
     * Aggiorna un filtro precedentemente creato con creaFiltroDiRicerca a filtroAppartamento che ritorna solo annunci di tipo appartamento
     * 
     * @param numeroLocali numero minimo di Locali
     * @param numeroBagni numero minimo Bagni
     * @param numeroCamereDaLetto numero minimo Camere da letto
     * @param metratura metratura minima
     * @return valore di verità corrispondente al esito
     */
    boolean aggiornaAFiltroAppartamento(int numeroLocali, int numeroBagni, int numeroCamereDaLetto, double metratura);
    
    /**
     *  Aggiorna un filtro precedentemente creato con creaFiltroDiRicerca a filtroAppartamento che ritorna solo annunci di tipo stanza
     * 
     * @param tipo stringa tipo stanza desiderato
     * @return valore di verità esito aggiornamento
     */
    boolean aggiornaAFiltroStanza(String tipo);
    
    /**
     * Metodo che utilizza il filtro di ricerca carica in gestore per ottenere i risultati
     * @return JSONArray dei risultati ogni elemento è un annuncio
     */
    JSONArray usaFiltroAttuale();
    
    /**
     * 
     * @return 
     */
    boolean isFiltroAppartamento();
    
    /**
     * 
     * @param id_FiltroDiRicerca
     * @return 
     */
    boolean cambiaFiltroAttuale(long id_FiltroDiRicerca);
    
    /**
     * 
     * @return 
     */
    JSONObject attualeToJSON();
    
    /**
     * 
     * @param id_studente
     * @return 
     */
    boolean persistiFiltroAttuale(String id_studente);
    
    /**
     * 
     * @return 
     */
    ArrayList<String> getQuartieriCittà();
    
    /**
     * 
     * @return 
     */
    ArrayList<String> getTipoStanza();
    
    /**
     * 
     * @return 
     */
    double[] geocodeCurrentCity();
    /**
     * 
     * @param lat
     * @param lng
     * @param rad
     * @return 
     */
    public JSONArray getSupermarketNearBy(double lat, double lng, double rad);
    
    /**
     * 
     * @param lat
     * @param lng
     * @param rad
     * @return 
     */
    public JSONArray getBankNearBy(double lat, double lng, double rad);
    
    /**
     * 
     * @param lat
     * @param lng
     * @param rad
     * @return 
     */
    public JSONArray getBusNearBy(double lat, double lng, double rad);
    
    /**
     * 
     * @param id_filtro
     * @param id_studente
     * @return 
     */
    public boolean removeFiltro(String id_filtro, String id_studente);
    
    /**
     * 
     * @param id
     * @return 
     */
    boolean loadFiltro(String id);
    
    /**
     * 
     * @param obj
     * @return 
     */
    public JSONObject create_useFilter(JSONObject obj);
    
    /**
     * 
     * @param Città
     * @return 
     */
    public JSONObject getQuartieri(String Città);
    
    /**
     * 
     * @return 
     */
    public JSONObject getTipoStanzaJSON();

}
