/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Annuncio;
import entity.Città;
import entity.Locatore;
import entity.Stanza;
import entity.StanzaAccessoria;
import entity.StanzaInAffitto;
import entity.TipoStanzaAccessoria;
import entity.TipoStanzaInAffitto;
import facade.AnnuncioFacadeLocal;
import facade.CittàFacadeLocal;
import facade.LocatoreFacadeLocal;
import facade.StanzaAccessoriaFacadeLocal;
import facade.StanzaFacadeLocal;
import facade.StanzaInAffittoFacadeLocal;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import org.json.JSONObject;

/**
 *
 * @author gianp_000
 */
@Stateful
public class GestoreAnnuncio implements GestoreAnnuncioLocal {

    @EJB
    private LocatoreFacadeLocal locatoreFacade;
    @EJB
    private StanzaAccessoriaFacadeLocal stanzaAccessoriaFacade;
    @EJB
    private StanzaInAffittoFacadeLocal stanzaInAffittoFacade;

    @EJB
    private StanzaFacadeLocal stanzaFacade;

    @EJB
    private CittàFacadeLocal cittàFacade;
    @EJB
    private AnnuncioFacadeLocal annuncioFacade;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private Annuncio annuncio;

    //serve come passo inziale dell'inserimento dell'annuncio, inserisce solo
    //il Locatore
    @Override
    public boolean CreaAnnuncio(Locatore locatore) {

        this.annuncio = new Annuncio();

        annuncio.setLocatore(locatore);

        /*
        TODO
         bisonga mettere i controlli, quando non si può creare un nuovo Annuncio?
            - Locatore bloccato     
         */
        if (true) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean CreaAnnuncio(Object idLocatore) {

        this.annuncio = new Annuncio();

        /*
        List<Locatore> locatori =locatoreFacade.findAll();
        
        for(Locatore l: locatori){
            if(l.getId() == idLocatore){
                        annuncio.setLocatore(l);
                        return true;
            }
        }
         */
        System.out.println(idLocatore.toString());
        Locatore l = locatoreFacade.find(idLocatore);
        this.annuncio.setLocatore(l);
        checkPhotoFolder();
        return l != null;
    }

    @Override
    public boolean inserisciInfoIndirizzo(String cittaStr, String quartiere, String indirizzo, double[] latlng) {
        if (this.annuncio == null) {
            return false;
        }

        //recupero la città dal nome
        Città città = new Città();
        List<Città> mieCittà = cittàFacade.findAll();
        boolean trovato = false;
        int i = 0;
        while (!trovato && i < mieCittà.size()) {
            Città c = mieCittà.get(i);
            i++;
            if (c.getNome().equalsIgnoreCase(cittaStr)) {
                città = c;
                trovato = true;
            }
        }

        this.annuncio.setCittà(città);
        this.annuncio.setQuartiere(quartiere);
        this.annuncio.setIndirizzo(indirizzo);
        this.annuncio.setLatLng(latlng);
        return true;
    }

    @Override
    public boolean inserisciInfoAnnuncio(String descrizione, double metratura, Date dataInizioAffitto) {
        this.annuncio.setDescrizione(descrizione);
        if (metratura != 0) {
            this.annuncio.setMetratura(metratura);
        }
        
        this.annuncio.setDataInizioAffitto(dataInizioAffitto);
        this.annuncio.setListaStanza(new ArrayList<>());
        return true;
    }

    /*
            Caso in cui annuncio.atomico = false e quindi il prezzo è riferito alla stanza
    I parametri metratura e prezzo non sono obbligatori, se settati a 0 non li setto
     */
    @Override
    public boolean inserisciNuovaStanzaInAffitto(String tipo, Collection<String> foto, boolean compresoCondominio, boolean compresoRiscaldamento, double metratura, double prezzo) {
        StanzaInAffitto nuovaStanza = new StanzaInAffitto();

        nuovaStanza.setTipo(TipoStanzaInAffitto.valueOf(tipo));

        nuovaStanza.setFoto(foto);
        nuovaStanza.setCompresoCondominio(compresoCondominio);
        nuovaStanza.setCompresoRiscaldamento(compresoRiscaldamento);
        if (metratura != 0) {
            nuovaStanza.setMetratura(metratura);
        }
        if (prezzo != 0) {
            nuovaStanza.setPrezzo(prezzo);
        }

        //aggiungo nuova stanza
        Collection<Stanza> listaStanze = annuncio.getListaStanza();
        if (listaStanze.size() + 1 > annuncio.getNumeroStanze()) {
            return false;
        }

        listaStanze.add(nuovaStanza);
        annuncio.setListaStanza(listaStanze);

        return true;
    }

    /*
    Caso in cui annuncio.atomico = true e quindi il prezzo è riferito all'appartamento
    I parametri metratura non sono obbligatori, se settati a 0 non li setto
     */
    @Override
    public boolean inserisciNuovaStanzaInAffitto(String tipo, Collection<String> foto, double metratura) {
        StanzaInAffitto nuovaStanza = new StanzaInAffitto();

        nuovaStanza.setTipo(TipoStanzaInAffitto.valueOf(tipo));

        nuovaStanza.setFoto(foto);

        if (metratura != 0) {
            nuovaStanza.setMetratura(metratura);
        }

        //aggiungo nuova stanza
        Collection<Stanza> listaStanze = annuncio.getListaStanza();
        if (listaStanze.size() + 1 > annuncio.getNumeroStanze()) {
            return false;
        }

        listaStanze.add(nuovaStanza);
        annuncio.setListaStanza(listaStanze);

        return true;
    }

    /*

    Metratura non obbligatorio, se settato a 0 non lo setto
     */
    @Override
    public boolean inserisciNuovaStanzaAccessoria(String tipo, Collection<String> foto, double metratura) {
        StanzaAccessoria nuovaStanza = new StanzaAccessoria();

        nuovaStanza.setTipo(TipoStanzaAccessoria.valueOf(tipo));
        nuovaStanza.setFoto(foto);

        if (metratura != 0) {
            nuovaStanza.setMetratura(metratura);
        }

        //aggiungo nuova stanza
        Collection<Stanza> listaStanze = annuncio.getListaStanza();
        if (listaStanze.size() + 1 > annuncio.getNumeroStanze()) {
            return false;
        }

        listaStanze.add(nuovaStanza);
        annuncio.setListaStanza(listaStanze);

        return true;
    }

    @Override
    public boolean inserisciInfoCostiAppartamento(double prezzo, boolean compresoCondominio, boolean compresoRiscaldamento) {

        annuncio.setPrezzo(prezzo);
        annuncio.setCompresoCondominio(compresoCondominio);
        annuncio.setCompresoRiscaldamento(compresoRiscaldamento);
        return true;
    }

    @Override
    public boolean rendiAnnuncioPersistente() {
        //GregorianCalendar gc = new GregorianCalendar();
        Date d = new Date();
        //TODO inserimento data, classe Date deprecata
        this.annuncio.setDataPubblicazione(d);

        for (int i = 0; i < this.annuncio.getListaStanza().size(); i++) {

            Stanza s = ((List<Stanza>) this.annuncio.getListaStanza()).get(i);
            Collection<String> foto = s.getFoto();
            ArrayList<String> tempFoto = new ArrayList();
            String newFotoPath = PathUtily.getPhotoPath() + annuncio.getLocatore().getId();
           
            for (String f : foto) {
                
                tempFoto.add(f.replace("_temp_",""));
            }

            s.setFoto(tempFoto);

            if (s instanceof StanzaInAffitto) {
                stanzaInAffittoFacade.create((StanzaInAffitto) ((List<Stanza>) this.annuncio.getListaStanza()).get(i));
            } else if (s instanceof StanzaAccessoria) {
                stanzaAccessoriaFacade.create((StanzaAccessoria) ((List<Stanza>) this.annuncio.getListaStanza()).get(i));
            } else {
                return false;
            }

        }

        List<Stanza> list = stanzaFacade.findAll();

        for (Stanza s : list) {
            System.out.println("Id:" + s.getId());

        }
        changeNameFolder();

        annuncioFacade.create(this.annuncio);
        
        
        

        System.out.println("ciao");

        return true;
    }

    @Override
    public JSONObject toJSON() {
        return this.annuncio.toJSON();
    }

    @Override
    public boolean modificaAnnuncio(Annuncio annuncio) {
        this.annuncio = annuncio;

        return true;

    }

    @Override
    public boolean modificaInfoIndirizzo(String citta, String quartiere, String indirizzo, double[] latlng, int numeroStanze, boolean atomico) {
        if (this.annuncio == null) {
            return false;
        }

        if (!citta.equalsIgnoreCase(this.annuncio.getCittà().getNome())) {
            //recupero la città dal nome
            Città città = new Città();
            List<Città> mieCittà = cittàFacade.findAll();
            boolean trovato = false;
            int i = 0;
            while (!trovato && i < mieCittà.size()) {
                Città c = mieCittà.get(i);
                i++;
                if (c.getNome().equalsIgnoreCase(citta)) {
                    città = c;
                    trovato = true;
                }

            }
            if (!trovato) {
                return false;
            }
            this.annuncio.setCittà(città);
        }

        if (!quartiere.equalsIgnoreCase(this.annuncio.getQuartiere())) {
            this.annuncio.setQuartiere(quartiere);
        }

        if (!indirizzo.equalsIgnoreCase(this.annuncio.getIndirizzo())) {
            this.annuncio.setIndirizzo(indirizzo);
        }

        //se si è arrivati ad una modica qui, sicuramente le cordinate saranno 
        //modificate
        this.annuncio.setLatLng(latlng);
        return true;
    }

    @Override
    public boolean modificaInfoAnnuncio(String descrizione, double metratura, Date dataInizioAffitto, int numeroStanze, boolean atomico) {

        this.annuncio.setDescrizione(descrizione);
        this.annuncio.setMetratura(metratura);
        this.annuncio.setDataInizioAffitto(dataInizioAffitto);
        this.annuncio.setNumeroStanze(numeroStanze);
        this.annuncio.setAtomico(atomico);
        return true;

    }

    private Stanza trovaStanza(long oid) {
        ArrayList<Stanza> stanze = (ArrayList<Stanza>) this.annuncio.getListaStanza();
        boolean trovato = false;
        Stanza myStanza = null;

        int i = 0;
        while (!trovato && i < stanze.size()) {
            Stanza temp = stanze.get(i);
            if (temp.getId() == oid) {
                trovato = true;
                myStanza = temp;
            }
            i++;
        }
        return myStanza;
    }

    @Override
    public boolean modificaStanzaInAffitto(long oid, String tipo, Collection<String> foto, boolean compresoCondominio, boolean compresoRiscaldamento, double metratura, double prezzo) {

        Collection<Stanza> listaStanze = annuncio.getListaStanza();

        StanzaInAffitto stanza = (StanzaInAffitto) trovaStanza(oid);
        if (stanza == null) {
            return false;
        }

        listaStanze.remove(stanza);

        stanza.setTipo(TipoStanzaInAffitto.valueOf(tipo));

        stanza.setFoto(foto);
        stanza.setCompresoCondominio(compresoCondominio);
        stanza.setCompresoRiscaldamento(compresoRiscaldamento);
        if (metratura != 0) {
            stanza.setMetratura(metratura);
        }
        stanza.setPrezzo(prezzo);

        listaStanze.add(stanza);
        annuncio.setListaStanza(listaStanze);

        return true;

    }

    @Override
    public boolean modificaStanzaInAffitto(long oid, String tipo, Collection<String> foto, double metratura) {

        Collection<Stanza> listaStanze = annuncio.getListaStanza();

        StanzaInAffitto stanza = (StanzaInAffitto) trovaStanza(oid);
        if (stanza == null) {
            return false;
        }

        listaStanze.remove(stanza);

        stanza.setTipo(TipoStanzaInAffitto.valueOf(tipo));

        stanza.setFoto(foto);

        if (metratura != 0) {
            stanza.setMetratura(metratura);
        }

        listaStanze.add(stanza);
        annuncio.setListaStanza(listaStanze);

        return true;
    }

    @Override
    public boolean modificaStanzaAccessoria(long oid, String tipo, Collection<String> foto, double metratura) {
        Collection<Stanza> listaStanze = annuncio.getListaStanza();

        StanzaAccessoria stanza = (StanzaAccessoria) trovaStanza(oid);
        if (stanza == null) {
            return false;
        }

        stanza.setTipo(TipoStanzaAccessoria.valueOf(tipo));
        stanza.setFoto(foto);
        if (metratura != 0) {
            stanza.setMetratura(metratura);
        }

        return true;
    }

    @Override
    public boolean modificaInfoCostiAppartamento(double prezzo, boolean compresoCondominio, boolean compresoRiscaldamento) {
        annuncio.setPrezzo(prezzo);
        annuncio.setCompresoCondominio(compresoCondominio);
        annuncio.setCompresoRiscaldamento(compresoRiscaldamento);
        return true;
    }

    @Override
    public boolean rendiModifichePersistenti() {

        //per ora non modifico la data di inserimento
        /*
        GregorianCalendar gc = new GregorianCalendar();
        Date d = new Date();
        this.annuncio.setDataPubblicazione(d);   
         */
        Collection<Stanza> lista = this.annuncio.getListaStanza();

        for (Stanza s : lista) {
            if (s instanceof StanzaInAffitto) {
                stanzaInAffittoFacade.edit((StanzaInAffitto) s);
            } else if (s instanceof StanzaAccessoria) {
                stanzaAccessoriaFacade.edit((StanzaAccessoria) s);
            } else {
                return false;
            }
        }
        annuncioFacade.edit(this.annuncio);

        return true;
    }

    @Override
    public boolean eliminaAnnuncio(long oidAnnuncio) {
        this.annuncio = annuncioFacade.find(oidAnnuncio);

        Collection<Stanza> listaStanze = this.annuncio.getListaStanza();
        this.annuncio.setListaStanza(new ArrayList<>());

        for (Stanza s : listaStanze) {
            stanzaFacade.remove(s);
        }
        
        annuncioFacade.remove(annuncio);
        return true;
    }

    @Override
    public boolean eliminaStanza(Stanza s) {
        stanzaFacade.remove(s);

        return true;
    }

    @Override
    public Annuncio predniAnnuncio(long oid) {
        Annuncio temp = annuncioFacade.find(oid);
        /*
        ArrayList<Stanza> stanza= (ArrayList<Stanza>) temp.getListaStanza();
        long id = stanza.get(0).getId();
         */
        return temp;
    }

    @Override
    public boolean inserisciInfoStanze(int numeroStanze, boolean atomico) {
        this.annuncio.setNumeroStanze(numeroStanze);
        this.annuncio.setAtomico(atomico);
        return true;
    }

    @Override
    public String persistiFoto(InputStream fotoStream, String nomePhoto, String denominazioneLocatore, String denominazioneStanza) {

        String pathFoto = PathUtily.getPhotoPath() + annuncio.getLocatore().getId();

        String pathTempFoto = PathUtily.getPhotoPath() + denominazioneLocatore + "//_temp_";

        File cartellaStanza = (new File(pathFoto));
        if (!cartellaStanza.exists()) {
            cartellaStanza.mkdir();
        }

        File cartellaTemp = (new File(pathTempFoto));
        if (!cartellaTemp.exists()) {
            cartellaTemp.mkdir();
        } else {

        }

        Date d = new Date();
        
        Random r = new Random();
        

        String est = nomePhoto.substring(nomePhoto.length() - 3);

        String photoName = denominazioneLocatore + r.nextInt(100) + d.getTime() + "." + est;

        pathFoto = pathTempFoto + "//" + photoName;

        try {
            try (FileOutputStream tempFile = new FileOutputStream(pathFoto)) {
                int read;

                final byte[] bytes = new byte[1024];
                while ((read = fotoStream.read(bytes)) != -1) {
                    tempFile.write(bytes, 0, read);
                }

                tempFile.close();
                fotoStream.close();
            }

        } catch (FileNotFoundException ex) {

            Logger.getLogger(GestoreAnnuncio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GestoreAnnuncio.class.getName()).log(Level.SEVERE, null, ex);
        }

        return pathFoto;
    }

    private boolean changeNameFolder() {
        boolean result = true;

        //path temporaneo
        String pathTempFoto = PathUtily.getPhotoPath() + annuncio.getLocatore().getId() + "//_temp_";
        File cartellaTemp = (new File(pathTempFoto));

        //path reale
        String pathFoto = PathUtily.getPhotoPath() + annuncio.getLocatore().getId();
        //File cartellaFoto = (new File(pathFoto));

        if (cartellaTemp.exists()) {
            String[] contenuto = cartellaTemp.list();
            for (int i = 0; i < contenuto.length; i++) {
                System.out.println("Nome file contenuto nella cartella " + contenuto[i]);
                File oldFoto = (new File(pathTempFoto + "//" + contenuto[i]));
                File newFoto = (new File(pathFoto + "//" + contenuto[i]));
                try {
                    PathUtily.spostaFoto(oldFoto, newFoto);

                } catch (IOException ex) {
                    System.out.println("Erroreeee");

                    Logger.getLogger(GestoreAnnuncio.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }

            }

        }

        return result;
    }

    private void checkPhotoFolder() {

        String pathTempFoto = PathUtily.getPhotoPath() + annuncio.getLocatore().getId() + "//_temp_";

        File cartellaTemp = (new File(pathTempFoto));

        if (cartellaTemp.exists()) {
            String[] contenuto = cartellaTemp.list();
            for (int i = 0; i < contenuto.length; i++) {
                File f = new File(pathTempFoto + "//" + contenuto[i]);
                f.delete();
            }
        }

    }

    @Override
    public boolean svuotaStante() {
        boolean result = this.annuncio.getListaStanza().isEmpty();
        this.annuncio.setListaStanza(new ArrayList<>());
        return result;
    }

    @Override
    public Annuncio getAnnuncioByID(long oid) {
        Annuncio ann = this.annuncioFacade.find(oid);
        return ann;
    }

    @Override
    public boolean archiviaAnnuncio(long oidAnnuncio, boolean archiviato) {
        this.annuncio = this.annuncioFacade.find(oidAnnuncio);
        
        //XOR potentissimo!!!!!!!! se dà vero vuol dire che i due valori sono diversi
        if(this.annuncio.isArchiviato() ^ archiviato){
            this.annuncio.setArchiviato(archiviato);
            this.annuncioFacade.edit(annuncio);
            return true;
            
        }else{
            return false;
        }
        
        
    }
    
    
   

}
