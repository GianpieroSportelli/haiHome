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
     * Metodo che verifica se il filtro contenuto in gestoreRicerca è un filtro appartamento oppure no
     * @return true se il filtro in gestoreRicerca è di tipo appartamento false altrimenti
     */
    boolean isFiltroAppartamento();
    
    /**
     * Metodo che carica in gestoreRicerca un particolare filtro di ricerca persistito scelto tramite l'id
     * @param id_FiltroDiRicerca id del filtro di ricerca che si vuole caricare
     * @return true se il caricamento va a buon fine
     */
    boolean cambiaFiltroAttuale(long id_FiltroDiRicerca);
    
    /**
     * Metodo che ritorna il json rappresentante il filro di ricerca presente al interno di gestoreRicerca
     * @return JSON filtro in gestoreRicerca null se il non è presente un filtro
     */
    JSONObject attualeToJSON();
    
    /**
     * Metodo incaricato di persistere il filtro presente al interno del gestoreRicerca comunicando con gestoreStudente, salva il filtro nella lista
     * filtri preferiti dello studente specificato mediante id_studente
     * @param id_studente id dello studente a cui verrà collegato il filtro di ricerca
     * @return valore di veerità rappresentate l'esito del oprazione
     */
    boolean persistiFiltroAttuale(String id_studente);
    
    /**
     * Metodo che ritorna la lista dei queartieri precedentemente selezionata
     * @return lista dei nomi dei quesrtieri della città selezionata
     */
    ArrayList<String> getQuartieriCittà();
    
    /**
     * Metodo che ritorna la lista dei tipiStanza presenti in haiHome
     * @return lista tipi stanza
     */
    ArrayList<String> getTipoStanza();
    
    /**
     * Metodo che si occupa di ottenere le coordinate di geocodifica della città selezionata in precedenza comunicando con
     * googleMapsBean
     * @return [latitudine,longitudine] città selezionata secondo la codifica ottenuta da goole
     */
    double[] geocodeCurrentCity();
    /**
     * Metodo che di occupa di recuperare i supermercati in uno specifico raggio ottenuti mediante googleMapsBean
     * @param lat latitudine centro del area di interesse
     * @param lng longitudine area di interesse
     * @param rad raggio dell'area di interesse
     * @return JSONArray dei servizi
     */
    public JSONArray getSupermarketNearBy(double lat, double lng, double rad);
    
    /**
     * Metodo che di occupa di recuperare le banche in uno specifico raggio ottenuti mediante googleMapsBean
     * @param lat latitudine centro del area di interesse
     * @param lng longitudine area di interesse
     * @param rad raggio dell'area di interesse
     * @return JSONArray dei servizi
     */
    public JSONArray getBankNearBy(double lat, double lng, double rad);
    
    /**
     * Metodo che di occupa di recuperare le fermate dei mezzi pubblici in uno specifico raggio ottenuti mediante googleMapsBean
     * @param lat latitudine centro del area di interesse
     * @param lng longitudine area di interesse
     * @param rad raggio dell'area di interesse
     * @return JSONArray dei servizi
     */
    public JSONArray getBusNearBy(double lat, double lng, double rad);
    
    /**
     * Metodo che si occupa di cancellare un particolare filtro di ricerca collegato a uno specifico studente,
     * operazione effetuata in collaborazione con gestoreStudente
     * @param id_filtro id filtro da cancellare
     * @param id_studente id studente detentore del filtro
     * @return esito cancellazione valore di verità
     */
    public boolean removeFiltro(String id_filtro, String id_studente);
    
     /**
     * Metodo che carica in gestoreRicerca un particolare filtro di ricerca persistito scelto tramite l'id
     * @param id id del filtro di ricerca che si vuole caricare
     * @return true se il caricamento va a buon fine
     */
    boolean loadFiltro(String id);
    
    /**
     * Metodo utilizzato per interfacciamento andorid riceve le info necessarie per la creazione di un filtro di ricerca
     * @param obj JSONObject che codifica le info del filtro di ricerca
     * @return JSONObject contenente status valore di verità che indica se la creazione e l'utilizzo del filtro è andata a buon fine 
     *         e un campo esito che contiene un JSONArray degli annunci risultato
     */
    public JSONObject create_useFilter(JSONObject obj);
    
    /**
     * Metodo utilizzato per l'interfacciamento con android per ottenere i quartieri di una specifica città
     * @param Città stringa nome città
     * @return JSONObject con due campi {status: valore di verità successo o fallimento, esito: lista di quartieri}
     */
    public JSONObject getQuartieri(String Città);
    
    /**
     * Metodo utilizzato per l'interfacciamento con android per ottenere i tipi stanza presenti in haihome
     * @return JSONObject con due campi {status: valore di verità successo o fallimento, esito: lista di tipi stanza}
     */
    public JSONObject getTipoStanzaJSON();

}
