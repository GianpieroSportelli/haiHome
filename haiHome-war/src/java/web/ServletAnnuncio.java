/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import com.google.gson.Gson;
import ejb.GestoreAnnuncioLocal;
import ejb.GestoreCittaLocal;
import ejb.GestoreImmaginiLocal;
import ejb.GoogleMapsBeanLocal;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sun.misc.BASE64Encoder;

@MultipartConfig
/**
 *
 * @author gianp_000
 */
public class ServletAnnuncio extends HttpServlet {

    @EJB
    private GestoreAnnuncioLocal gestoreAnnuncio;

    @EJB
    private GoogleMapsBeanLocal gestoreMaps;

    @EJB
    private GestoreCittaLocal gestoreCitta;

    @EJB
    private GestoreImmaginiLocal gestoreImmagini;

    /*
    //Parametri richiesti
    private String citta;
    private String quartiere;
    private String indirizzo;
    private double[] latlng = {0.0, 0.0};

    String descrizione;
    double metraturaApp;*/
    //Utily Photo haiHome-war/src/java/web/FotoUploadServlet.java
    ////Users/giacomocavallo/NetBeansProjects/ProgettoSSCSWeb/haiHome/haiHome-war/web/Immagini/tempAppImg
    //ArrayList<FileOutputStream> photoTempPath;
    //private HashMap<String, ArrayList<String>> photoTempPath;

    //private ArrayList<String> editPhotoTempPath;
    //private HashMap<String, ArrayList<String>> stanzeInfo;

    //private HashMap<String, ArrayList<String>> capMap;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JSONException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            String action = request.getParameter("action");
            String header = request.getHeader("action");

            if (header != null) {

                action = header;

            }

            if (action.equalsIgnoreCase("Annunci-newAnnuncio-initialRequest")) {

                System.out.println("----- RICHIESTA INIZIALE:");

                //prendo la sessione
                HttpSession session = request.getSession();

                //preparo la risposta
                response.setContentType("application/json");  // Set content type of the response so that jQuery knows what it can expect.
                response.setCharacterEncoding("UTF-8");

                //controllo il tipo utente
                String userType = (String) session.getAttribute("user-type");

                if (userType == null) {
                    userType = "random";
                }
                if ((userType.equalsIgnoreCase("locatore")) /*(userType.equalsIgnoreCase("locatore"))*/) {
                    JSONObject locatoreJSON = (JSONObject) session.getAttribute("user-data");
                    long idLocatore = (long) locatoreJSON.get("id");
                    System.out.println("Tipo utente: " + userType);
                    System.out.println("Id locatore : " + idLocatore);
                    boolean result = gestoreAnnuncio.CreaAnnuncio(idLocatore);
                    System.out.println("Annuncio inizializzato : " + result);
                    //scrivo la risposa
                    if (result) {
                        ArrayList<String> tipoStanzeAffitto = gestoreAnnuncio.getTipologieStanzaAffitto();
                        ArrayList<String> tipoStanzeAccessorio = gestoreAnnuncio.getTipologieStanzaAcc();
                        JSONObject resp = new JSONObject();
                        resp.accumulate("tipoAffitto", tipoStanzeAffitto);
                        resp.accumulate("tipoAccessorio", tipoStanzeAccessorio);
                        resp.accumulate("response", "OK");

                        out.write(resp.toString());

                    }

                } else {
                    long idLocatore = 36;
                    boolean result = gestoreAnnuncio.CreaAnnuncio(idLocatore);
                    System.out.println("ID LOCATORE aggiunto manualmente: " + idLocatore);
                    System.out.println("Annuncio inizializzato : " + result);
                    //scrivo la risposta
                    //preparo la risposta
                    JSONObject resp = new JSONObject();
                    resp.accumulate("response", "NO");

                    out.write(resp.toString());

                }
                //inizializzo arrayList stanze
                HashMap<String,ArrayList<String>> photoTempPath = new HashMap();
                gestoreAnnuncio.setphotoTempPath(photoTempPath);
                HashMap<String,ArrayList<String>> stanzeInfo = new HashMap();
                gestoreAnnuncio.setstanzeInfo(stanzeInfo);

                //Carico i quartieri
                HashMap<String, ArrayList<String>> capMap = gestoreCitta.getQuartieriCapMap();
                
                //carico lista quartieri
                gestoreCitta.buildQuartieriCapMap();
                
                /*
                for (String key : capMap.keySet()) {
                    System.out.println("Cap: " + key);
                    System.out.println("Quartieri : ");
                    for (String quart : capMap.get(key)) {
                        System.out.println("-       -  " + quart);
                    }

                }*/

            } else if (action.equalsIgnoreCase("Annunci-newAnnuncio-infoAppartamento")) {

                System.out.println("----- INFO APPARTAMENTO:");

                String citta = request.getParameter("Città").trim();
                String quartiere = request.getParameter("Quartiere").trim();
                String indirizzo = request.getParameter("Indirizzo").trim() + ", " + request.getParameter("Civico").trim();
                //Via Carlo Alberto, 41, Torino, TO, Italia
                String indirizzoString = indirizzo + "," + citta + ", Italia";
                indirizzoString = indirizzoString.replace(" ", "+");

                //da gestire la latitudine longitutine latlng = {0.0,0.0};
                //double[] latlng = {0.0, 0.0};
                double[] latlng = gestoreMaps.geocodingAddress(indirizzoString);

                //Inserisco informazioni
                gestoreAnnuncio.inserisciInfoIndirizzo(citta, quartiere, indirizzo, latlng);

                System.out.println("CITTA: " + citta);
                System.out.println("QUARTIERE: " + quartiere);
                System.out.println("INDIRIZZO: " + indirizzo);
                System.out.println("COORDINATE INDIRIZZO: " + latlng[0] + ", " + latlng[1]);
                System.out.println("\n");

                response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
                response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
                out.write("Dati info Appartamento inseriti");

            } else if (action.equalsIgnoreCase("Annunci-newAnnuncio-infoAnnuncio")) {

                System.out.println("-----INFO ANNUNCIO:");

                String descrizione = request.getParameter("Descrizione").trim();
                String m = request.getParameter("Metratura").trim();
                boolean arredato = request.getParameter("Arredato") != null;
                System.out.println("M = " + m);

                double metraturaApp = 0;
                if (!m.equalsIgnoreCase("")) {
                    metraturaApp = Double.parseDouble(m);
                }

                String[] data = request.getParameter("DataInizioAffitto").split("-");
                System.out.println(data[0] + " - " + data[1] + " - " + data[2]);
                GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(data[2]), Integer.parseInt(data[1]) - 1, Integer.parseInt(data[0]));
                //Da gestire la data inizio affitto Date dataInizioAffitto = new Date();
                System.out.println(gc.getTime());
                gestoreAnnuncio.inserisciInfoAnnuncio(descrizione, metraturaApp, (Date) gc.getTime(), arredato);

                System.out.println("DESCRIZIONE: " + descrizione);
                System.out.println("METRATURA: " + metraturaApp);
                System.out.println("ARREDATO: " + arredato);
                System.out.println("\n");
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                out.write("Dati info Annuncio inseriti");

            } else if (action.equalsIgnoreCase("Annunci-newAnnuncio-infoStanze")) {

                System.out.println("-----INFO STANZE");
                
                

                String[] numeroStanza = request.getParameterValues("numStanza");

                String[] tipologiaStanze = request.getParameterValues("TipologiaStanza");
                String[] tipoL = request.getParameterValues("TipoL");
                String[] tipoA = request.getParameterValues("TipoA");

                String[] metraturaS = request.getParameterValues("MetraturaS");

                for (int i = 0; i < tipologiaStanze.length; i++) {
                    System.out.println(numeroStanza[i] + " -----------------------");

                    ArrayList<String> infoStanza = new ArrayList();

                    System.out.println("Tipologia stanza " + tipologiaStanze[i]);

                    infoStanza.add(tipologiaStanze[i]);

                    if (metraturaS[i].equalsIgnoreCase("")) {
                        metraturaS[i] = "0";
                    }

                    //stanza da letto
                    if (tipologiaStanze[i].equalsIgnoreCase("1")) {
                        System.out.println("Tipo Letto " + tipoL[i]);
                        System.out.println("Metratura Stanza" + metraturaS[i]);
                        infoStanza.add(tipoL[i]);
                        infoStanza.add(metraturaS[i]);
                    } else {
                        System.out.println("Tipo Accessoria " + tipoA[i]);
                        System.out.println("Metratura Stanza" + metraturaS[i]);
                        infoStanza.add(tipoA[i]);
                        infoStanza.add(metraturaS[i]);
                    }

                    HashMap<String, ArrayList<String>> stanzeInfo = gestoreAnnuncio.getstanzeInfo();
                    stanzeInfo.put(numeroStanza[i], infoStanza);
                    gestoreAnnuncio.setstanzeInfo(stanzeInfo);
                    
                     HashMap<String,ArrayList<String>> photoTempPath = gestoreAnnuncio.getphotoTempPath();

                    ArrayList<String> fotoPath = photoTempPath.get(numeroStanza[i]);
                    for (String s : fotoPath) {
                        System.out.println("Foto stanza " + s);
                    }

                }

                response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
                response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
                out.write("Dati info Annuncio inseriti");

            } else if (action.equalsIgnoreCase("Annunci-newAnnuncio-FotoUpload")) {

                System.out.println("-----FOTO DELLE STANZE");
                
                 HashMap<String,ArrayList<String>> photoTempPath = gestoreAnnuncio.getphotoTempPath();
                HashMap<String,ArrayList<String>> stanzeInfo = gestoreAnnuncio.getstanzeInfo();
                
                System.out.println("bug detector");
                //mi assicuro che non ci siano stane inserite
                boolean listaStanzeVuota = photoTempPath.isEmpty();
                if (!listaStanzeVuota) {
                    photoTempPath = new HashMap<>();
                    stanzeInfo = new HashMap<>();

                }

                Collection<Part> files = request.getParts();

                for (Part filePart : files) {

                    String fileName = filePart.getSubmittedFileName();

                    String numStanza = filePart.getName();

                    InputStream filecontent;
                    filecontent = filePart.getInputStream();
                    
                    //prendo la sessione
                    HttpSession session = request.getSession();
                
                    JSONObject locatoreJSON = (JSONObject) session.getAttribute("user-data");
                    
                    long idLocatore = (long) locatoreJSON.get("id");

                    String path = gestoreAnnuncio.persistiFoto(filecontent, fileName, idLocatore + "", numStanza);

                    if (!photoTempPath.containsKey(numStanza)) {
                        ArrayList<String> temp = new ArrayList();
                        temp.add(path);
                        photoTempPath.put(numStanza, temp);
                    } else {
                        ArrayList<String> temp = photoTempPath.get(numStanza);
                        temp.add(path);
                        photoTempPath.put(numStanza, temp);
                    }

                    System.out.println("NOME FOTO: " + fileName + " NUOVO PATH FOTO " + path);

                }
                
                gestoreAnnuncio.setphotoTempPath(photoTempPath);
                gestoreAnnuncio.setstanzeInfo(stanzeInfo);
                response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
                response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/Annunci_JSP/InserimentoAnnunci.jsp");
                rd.forward(request, response);
                out.write("Dati FOTO annunci inseriti ");

            } else if (action.equalsIgnoreCase("Annunci-newAnnuncio-infoCosti")) {

                //System.out.println("action: Annunci-newAnnuncio-infoAppartamento");
                System.out.println("----- INFO COSTI:");

                String tipoCosti = request.getParameter("Tipo Costo").trim();
                
                HashMap<String,ArrayList<String>> stanzeInfo = gestoreAnnuncio.getstanzeInfo();
                        
                int numeroStanze = stanzeInfo.size();
                
                //recupero info foto
                HashMap<String,ArrayList<String>> photoTempPath = gestoreAnnuncio.getphotoTempPath();

                //Info sulle stanze
                gestoreAnnuncio.inserisciInfoStanze(numeroStanze, tipoCosti.compareToIgnoreCase("1") == 0);

                //controllo che non ci siano state gia fatti inserimenti di stanze
                boolean listaStanzeVuota = gestoreAnnuncio.svuotaStante();

                //prezzo riferito all'appartamento (parametro atomico)
                if (tipoCosti.compareToIgnoreCase("1") == 0) {

                    Set<String> listaChiavi = stanzeInfo.keySet();

                    String prezzoA = request.getParameter("prezzoA").trim();
                    String compCondA = request.getParameter("compresoCondominioA");
                    boolean CCA = request.getParameter("compresoCondominioA") != null;
                    String compRiscA = request.getParameter("compresoRiscaldamentoA");
                    boolean CRA = request.getParameter("compresoRiscaldamentoA") != null;
                    


                    //inserisco tutte le stanze
                    for (String s : listaChiavi) {
                        ArrayList<String> infoStanza = stanzeInfo.get(s);
                        String tipologiaStanza = infoStanza.get(0);

                        if (tipologiaStanza.equalsIgnoreCase("1")) {
                            //stanza da letto senza costi
                            gestoreAnnuncio.inserisciNuovaStanzaInAffitto(infoStanza.get(1), photoTempPath.get(s), Double.parseDouble(infoStanza.get(2)));
                        } else {
                            //stanza accessoria
                            gestoreAnnuncio.inserisciNuovaStanzaAccessoria(infoStanza.get(1), photoTempPath.get(s), Double.parseDouble(infoStanza.get(2)));
                        }

                    }

                    //inserisco info costi appartamento
                    gestoreAnnuncio.inserisciInfoCostiAppartamento(Double.parseDouble(prezzoA), CCA, CRA);

                    //Stampa di controllo
                    System.out.println("TIPO COSTI: " + tipoCosti);
                    System.out.println("PREZZO APPARTAMENTO: " + prezzoA);
                    System.out.println("COMPRESO CONDOMINIO APP: " + compCondA);
                    System.out.println("COMPRESO RISCALDAMENTO APP: " + compRiscA);
                    System.out.println("\n");
                } else { //il prezzo si riferisce alle singole stanze
                    String[] prezzoS = request.getParameterValues("PrezzoS");
                    String[] idStanze = request.getParameterValues("idStanza");
                    boolean CCS = request.getParameter("compresoCondominioS") != null;

                    boolean CRS = request.getParameter("compresoRiscaldamentoS") != null;
                                        
                    for (int i = 0; i < idStanze.length; i++) {
                        String idStanze1 = idStanze[i];
                        ArrayList<String> infoStanza = stanzeInfo.get(idStanze1);
                        gestoreAnnuncio.inserisciNuovaStanzaInAffitto(infoStanza.get(1), photoTempPath.get(idStanze1), CCS, CRS, Double.parseDouble(infoStanza.get(2)), Double.parseDouble(prezzoS[i]));
                        //stanzeInfo.remove(idStanze1);
                    }

                    Set<String> listaChiavi = stanzeInfo.keySet();

                    for (String s : listaChiavi) {
                        ArrayList<String> infoStanza = stanzeInfo.get(s);
                        String tipologiaStanza = infoStanza.get(0);
                        if (tipologiaStanza.equalsIgnoreCase("1")) {
                            System.out.println("Ci sono problemi, qui non dovrebbe entrare");
                        } else {
                            //stanza accessoria
                            gestoreAnnuncio.inserisciNuovaStanzaAccessoria(infoStanza.get(1), photoTempPath.get(s), Double.parseDouble(infoStanza.get(2)));
                        }

                    }

                    //non inserisco info sul prezzo
                    //Stampo dati per controllo
                    for (int i = 0; i < prezzoS.length; i++) {
                        System.out.println(idStanze[i]);
                        System.out.println("PREZZO STANZA: " + prezzoS[i]);
                    }

                    System.out.println("COMPRESO CONDOMINIO STA: " + CCS);
                    System.out.println("COMPRESO RISCALDAMENTO STA: " + CRS);
                }
                
                //gestoreAnnuncio.setstanzeInfo(stanzeInfo);

                //inserisco info finali sull'annuncio
                JSONObject annuncio = gestoreAnnuncio.toJSON();
                //non qui da mettere l'anteprima e la conferma
                System.out.println(annuncio);
                response.setContentType("application/json");  // Set content type of the response so that jQuery knows what it can expect.
                response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
                out.write(annuncio.toString());

            } else if (action.equalsIgnoreCase("Annunci-newAnnuncio-getImage")) {

                String url = request.getParameter("url");
                String type = request.getParameter("type");

                String imageString = null;
                BufferedImage originalImage = ImageIO.read(new File(url));

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                try {
                    ImageIO.write(originalImage, type, bos);
                    byte[] imageBytes = bos.toByteArray();
                    BASE64Encoder encoder = new BASE64Encoder();
                    imageString = encoder.encode(imageBytes);
                    System.out.println(url);
                    out.write(imageString);
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //return imageString;

            } else if (action.equalsIgnoreCase("Annunci-newAnnuncio-persisti")) {

                boolean result = gestoreAnnuncio.rendiAnnuncioPersistente();

                //inserisco info finali sull'annuncio
                JSONObject annuncio = gestoreAnnuncio.toJSON();
                //non qui da mettere l'anteprima e la conferma

                System.out.println("ANNUNCIO PERSISTITO, ECCO IL RISULTATO: " + annuncio);
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                if (result) {
                    out.write("OK");
                } else {
                    out.write("NO");
                }

            } else if (action.equalsIgnoreCase("Annunci-newAnnuncio-getQuartieri") || (action.equalsIgnoreCase("Annunci-editAnnuncio-getQuartieri"))) {
                //System.out.println("I'm in!!");
                response.setContentType("application/json");

                String cap = request.getParameter("cap");

                if (cap != null) {

                    System.out.println("CAP " + cap);
                    ArrayList<String> listaquartieri = gestoreCitta.getQuartieriByCap(cap);//capMap.get(cap);
                    String json = new Gson().toJson(listaquartieri);
                    System.out.println(json);
                    out.write(json);

                } else {

                    String json = new Gson().toJson(gestoreCitta.getListaQuartieri("Torino"));

                    //String json = new Gson().toJson(listaquartieri);
                    System.out.println(json);
                    out.write(json);
                }

            } else if (action.equalsIgnoreCase("Annunci-editAnnuncio-initialRequest")) {

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                System.out.println("-----MODIFICA  RICHIESTA INIZIALE:");

                String oidAnnuncio = request.getParameter("idAnnuncio").trim();
                String oidLocatore = request.getParameter("idAnnuncio").trim();

                System.out.println("OID Annuncio: " + oidAnnuncio);

                //INizio recupero dati locatore
                HttpSession session = request.getSession();

                String userType = (String) session.getAttribute("user-type");

                if (userType == null) {
                    userType = "random";
                }
                if ((userType.equalsIgnoreCase("locatore")) /*(userType.equalsIgnoreCase("locatore"))*/) {
                    JSONObject locatoreJSON = (JSONObject) session.getAttribute("user-data");
                    long idLocatore = (long) locatoreJSON.get("id");
                    //Fine recupero dati locatore

                    boolean result = gestoreAnnuncio.modificaAnnuncio(Long.parseLong(oidAnnuncio));

                    if (result) {
                        //carico lista quartieri
                        gestoreCitta.buildQuartieriCapMap();
                        

                        //carico i Tipi stanze
                        ArrayList<String> tipoStanzeAffitto = gestoreAnnuncio.getTipologieStanzaAffitto();
                        ArrayList<String> tipoStanzeAccessorio = gestoreAnnuncio.getTipologieStanzaAcc();

                        JSONObject annuncio = gestoreAnnuncio.toJSON();

                        JSONArray stanze = annuncio.getJSONArray("Stanze").getJSONArray(0);

                        for (int i = 0; i < stanze.length(); i++) {

                            JSONObject s = stanze.getJSONObject(i);
                            ArrayList<String> fotos = (ArrayList<String>) s.get("Foto");
                            ArrayList<String> fotos64 = new ArrayList();
                            ArrayList<String> estensioniFoto64 = new ArrayList();
                            for (String f : fotos) {
                                System.out.print(f);
                                String[] t = f.split("\\.");
                                String est = t[t.length-1];
                                System.out.println("PATH FOTO " +  f + " " + est);
                                System.out.println("Immagine: " + f + " estensione: " + est);
                                String foto64 = gestoreImmagini.getImage(f, est);
                                fotos64.add(foto64);
                                estensioniFoto64.add(est);
                            }

                            s.accumulate("Foto64", fotos64);
                            s.accumulate("Est64", estensioniFoto64);

                        }

                        JSONObject resp = new JSONObject();
                        resp.accumulate("response", "OK");
                        resp.accumulate("tipoAffitto", tipoStanzeAffitto);
                        resp.accumulate("tipoAccessorio", tipoStanzeAccessorio);
                        resp.accumulate("data", annuncio);
                        out.write(resp.toString());

                    } else {
                        JSONObject resp = new JSONObject();
                        resp.accumulate("response", "NO");
                        out.write(resp.toString());
                    }

                } else {
                    JSONObject resp = new JSONObject();
                    resp.accumulate("response", "NO");
                    out.write(resp.toString());
                }

            } else if (action.equalsIgnoreCase("Annunci-editAnnuncio-infoIndirizzo")) {

                System.out.println("----- EDIT INDIRIZZO:");

                String citta = "Torino";//request.getParameter("Città").trim();
                String quartiere = request.getParameter("Quartiere").trim();
                String indirizzo = request.getParameter("Indirizzo").trim() + ", " + request.getParameter("Civico").trim();

                String indirizzoString = indirizzo + "," + citta + ", Italia";
                indirizzoString = indirizzoString.replace(" ", "+");

                //da gestire la latitudine longitutine latlng = {0.0,0.0};
                //double[] latlng = {0.0, 0.0};
                double[] latlng = gestoreMaps.geocodingAddress(indirizzoString);

                //Inserisco informazioni
                //gestoreAnnuncio.inserisciInfoIndirizzo(citta, quartiere, indirizzo, latlng);
                gestoreAnnuncio.modificaInfoIndirizzo(citta, quartiere, indirizzo, latlng);
                gestoreAnnuncio.rendiModifichePersistenti();

                JSONObject editAnnuncio = gestoreAnnuncio.toJSON();

                System.out.println("CITTA: " + citta);
                System.out.println("QUARTIERE: " + quartiere);
                System.out.println("INDIRIZZO: " + indirizzo);
                System.out.println("COORDINATE INDIRIZZO: " + latlng[0] + ", " + latlng[1]);
                System.out.println("\n");

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                JSONObject resp = new JSONObject();
                resp.accumulate("response", "OK");
                resp.accumulate("data", editAnnuncio);
                out.write(resp.toString());

            } else if (action.equalsIgnoreCase("Annunci-editAnnuncio-infoAppartamento")) {

                System.out.println("-----EDIT INFO APPARTAMENTO:");

                String descrizione = request.getParameter("Descrizione").trim();
                String m = request.getParameter("Metratura").trim();
                boolean arredato = request.getParameter("Arredato") != null;

                double metraturaApp = 0;
                if (!m.equalsIgnoreCase("")) {
                    metraturaApp = Double.parseDouble(m);
                }

                String[] data = request.getParameter("DataInizioAffitto").split("-");
                GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(data[2]), Integer.parseInt(data[1]) - 1, Integer.parseInt(data[0]));
                //Da gestire la data inizio affitto Date dataInizioAffitto = new Date();

                gestoreAnnuncio.modificaInfoAnnuncio(descrizione, metraturaApp, (Date) gc.getTime(), arredato);
                gestoreAnnuncio.rendiModifichePersistenti();

                JSONObject editAnnuncio = gestoreAnnuncio.toJSON();

                System.out.println("DESCRIZIONE: " + descrizione);
                System.out.println("METRATURA: " + metraturaApp);
                System.out.println("ARREDATO: " + arredato);
                System.out.println("DATA: " + data[0] + " - " + data[1] + " - " + data[2]);

                System.out.println("\n");

                //ELABORO RISPOSTA
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                JSONObject resp = new JSONObject();
                resp.accumulate("response", "OK");
                resp.accumulate("data", editAnnuncio);
                out.write(resp.toString());

            } else if (action.equalsIgnoreCase("Annunci-editAnnuncio-FotoUpload")) {

                System.out.println("-----EDIT NEW FOTO UPLOAD:");

                ArrayList<String> editPhotoTempPath = new ArrayList();
                gestoreAnnuncio.seteditPhotoTempPath(editPhotoTempPath);
                

                Collection<Part> files = request.getParts();

                for (Part filePart : files) {

                    String fileName = filePart.getSubmittedFileName();

                    String numStanza = filePart.getName();

                    InputStream filecontent;
                    filecontent = filePart.getInputStream();
                    
                   //prendo la sessione
                    HttpSession session = request.getSession();
                
                    
                    //controllo il tipo utente

                    JSONObject locatoreJSON = (JSONObject) session.getAttribute("user-data");
                    
                    long idLocatore = (long) locatoreJSON.get("id");
                    

                    String path = gestoreAnnuncio.persistiFoto(filecontent, fileName, idLocatore + "", numStanza);

                    editPhotoTempPath.add(path);

                    System.out.println("NOME FOTO: " + fileName + " NUOVO PATH FOTO " + path);

                }

                for (String p : editPhotoTempPath) {
                    System.out.println(p);
                }
                
                gestoreAnnuncio.seteditPhotoTempPath(editPhotoTempPath);

                //ELABORO RISPOSTA
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                JSONObject resp = new JSONObject();
                resp.accumulate("response", "OK");
                out.write(resp.toString());

            } else if (action.equalsIgnoreCase("Annunci-editAnnuncio-infoEditStanze")) {

                System.out.println("-----EDIT INFO STANZA:");
                String oidStanza = request.getParameter("oidStanza");

                String metratura = request.getParameter("MetraturaS");
                
                metratura = metratura.compareToIgnoreCase("")==0 ? "0" : metratura;
                String prezzoTemp = request.getParameter("PrezzoS");
                System.out.println("request " +prezzoTemp);
                double prezzo;
                if(prezzoTemp==null || prezzoTemp.compareToIgnoreCase("")==0){
                    prezzo = -1;
                }else{
                    prezzo = Double.parseDouble(prezzoTemp);
                }
                
                String[] oldFoto = request.getParameterValues("fotoEliminate");

                System.out.println("OID: " + oidStanza);
                System.out.println("METRATURA: " + metratura);
                System.out.println("PREZZO: " + prezzo);
                System.out.println("FOTO ELIMINATE: ");
                ArrayList<String> stanzeEliminate = new ArrayList();
                if (oldFoto == null) {
                    System.out.println("Non è stata eliminata alcuna foto");

                } else {

                    for (int i = 0; i < oldFoto.length; i++) {
                        System.out.println("Foto eliminate");
                        System.out.println("NOME FOTO: " + oldFoto[i]);
                        stanzeEliminate.add(oldFoto[i]);
                    }

                }
                long oid = Long.parseLong(oidStanza);
                boolean result;
                ArrayList<String> editPhotoTempPath = gestoreAnnuncio.geteditPhotoTempPath();
                if(prezzo<=0){
                    result = gestoreAnnuncio.modificaStanza(oid, editPhotoTempPath, stanzeEliminate, Double.parseDouble(metratura));

                }else{
                    result = gestoreAnnuncio.modificaStanza(oid, editPhotoTempPath, stanzeEliminate, Double.parseDouble(metratura),prezzo);

                }
                JSONObject annuncioEdited = gestoreAnnuncio.toJSON();

                String risp = result ? "OK" : "NO";
                //ELABORO RISPOSTA
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                JSONObject resp = new JSONObject();
                resp.accumulate("response", risp);
                resp.accumulate("data", annuncioEdited);
                out.write(resp.toString());

            } else if (action.equalsIgnoreCase("Annunci-editAnnuncio-eliminaStanza")) {

                String oidStanza = request.getParameter("oid");

                System.out.println("-----ELIMINA STANZA OID: " + oidStanza);

                boolean esito = this.gestoreAnnuncio.eliminaStanza(Long.parseLong(oidStanza));

                //ELABORO RISPOSTA
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                if (esito) {
                    JSONObject annuncioEdited = this.gestoreAnnuncio.toJSON();

                    JSONArray stanze = annuncioEdited.getJSONArray("Stanze").getJSONArray(0);

                    for (int i = 0; i < stanze.length(); i++) {

                        JSONObject s = stanze.getJSONObject(i);
                        ArrayList<String> fotos = (ArrayList<String>) s.get("Foto");
                        ArrayList<String> fotos64 = new ArrayList();
                        ArrayList<String> estensioniFoto64 = new ArrayList();
                        for (String f : fotos) {
                            System.out.print(f);
                            String est = f.substring(f.length() - 3);
                            System.out.println("Immagine: " + f + " estensione: " + est);
                            String foto64 = gestoreImmagini.getImage(f, est);
                            fotos64.add(foto64);
                            estensioniFoto64.add(est);
                        }

                        s.accumulate("Foto64", fotos64);
                        s.accumulate("Est64", estensioniFoto64);

                    }

                    JSONObject resp = new JSONObject();
                    resp.accumulate("response", "OK");
                    resp.accumulate("data", annuncioEdited);
                    out.write(resp.toString());

                } else {
                    JSONObject resp = new JSONObject();
                    resp.accumulate("response", "OK");
                    out.write(resp.toString());
                }

            } else if (action.equalsIgnoreCase("Annunci-editAnnuncio-newStanza")) {

                System.out.println("-----AGGIUNGI NUOVA STANZA");
               

                String tipologiaStanze = request.getParameter("TipologiaStanza");
                String tipoL = request.getParameter("TipoL");
                String tipoA = request.getParameter("TipoA");
                String metraturaS = request.getParameter("MetraturaS");
                String prezzoS = request.getParameter("PrezzoS");
                
                System.out.println("TIPOLOGIA STANZA: " + tipologiaStanze);
                System.out.println("TIPO STANZA: " + tipoL + tipoA);
                System.out.println("METRATURA: " + metraturaS);
                System.out.println("PREZZO: " + prezzoS);
                
                ArrayList<String> editPhotoTempPath = gestoreAnnuncio.geteditPhotoTempPath();
                
                System.out.println("FOTO STANZA");
                for(String s : editPhotoTempPath){
                    System.out.println(s + "\n");
                }
                String tipo;
                
                if(tipologiaStanze.compareToIgnoreCase("1")==0){
                    tipo = tipoL;
                }else{
                    tipo=tipoA;
                }
                
                boolean res = gestoreAnnuncio.aggiungiStanza(tipologiaStanze,tipo, editPhotoTempPath, Double.parseDouble(metraturaS), Double.parseDouble(prezzoS));
                
              
                
                if (res) {
                    JSONObject annuncioEdited = this.gestoreAnnuncio.toJSON();

                    JSONArray stanze = annuncioEdited.getJSONArray("Stanze").getJSONArray(0);

                    for (int i = 0; i < stanze.length(); i++) {

                        JSONObject s = stanze.getJSONObject(i);
                        ArrayList<String> fotos = (ArrayList<String>) s.get("Foto");
                        ArrayList<String> fotos64 = new ArrayList();
                        ArrayList<String> estensioniFoto64 = new ArrayList();
                        for (String f : fotos) {
                            System.out.print(f);
                            String est = f.substring(f.length() - 3);
                            System.out.println("Immagine: " + f + " estensione: " + est);
                            String foto64 = gestoreImmagini.getImage(f, est);
                            fotos64.add(foto64);
                            estensioniFoto64.add(est);
                        }

                        s.accumulate("Foto64", fotos64);
                        s.accumulate("Est64", estensioniFoto64);

                    }

                    JSONObject resp = new JSONObject();
                    resp.accumulate("response", "OK");
                    resp.accumulate("data", annuncioEdited);
                    out.write(resp.toString());

                } else {
                    JSONObject resp = new JSONObject();
                    resp.accumulate("response", "OK");
                    out.write(resp.toString());
                }

               
              

            } else if (action.equalsIgnoreCase("Annunci-editAnnuncio-infoCosti")) {

                System.out.println("-----EDIT INFO COSTI");
               
                JSONObject resp = new JSONObject();
                
                
                String infoModifica = request.getParameter("info");
                boolean risp = true;

                
                System.out.println("INFOCOSTI: " + infoModifica);
                

                boolean CC = request.getParameter("compresoCondominio") != null;
                boolean CR = request.getParameter("compresoRiscaldamento") != null;
                
                if(infoModifica.compareToIgnoreCase("atomico-atomico")==0){
                    //da atomico ad atomico
                    String prezzo = request.getParameter("prezzoA");
                    risp = gestoreAnnuncio.modificaInfoCostiAppartamento(Double.parseDouble(prezzo), CC, CR);
                    
                    
                }else if(infoModifica.compareToIgnoreCase("atomico-stanze")==0){
                    
                   //elimino il prezzo all'appartamento
                    risp = risp && gestoreAnnuncio.modificaInfoCostiAppartamento(0, false, false);
                    
                    JSONObject annAttuale = this.gestoreAnnuncio.toJSON();
                    JSONArray JSONstanze = annAttuale.getJSONArray("Stanze").getJSONArray(0);
                    
                    String[] prezzoS = request.getParameterValues("PrezzoS");
                    String[] oidS = request.getParameterValues("oidStanza");
                    
                    HashMap<Long,Long> mappaPrezzi = new HashMap();
                    //preparo i dati
                    for(int j =0;j<oidS.length;j++){
                        mappaPrezzi.put(Long.parseLong(oidS[j]), Long.parseLong(prezzoS[j]));
                    }
                    

                    for(int i=0;i<JSONstanze.length();i++){
                        JSONObject jstanza = JSONstanze.getJSONObject(i);
                        String tipoStanza = jstanza.getString("SuperTipo");
                        if(tipoStanza.equalsIgnoreCase("StanzaInAffitto")){
                            risp =  risp & gestoreAnnuncio.modificaStanza(jstanza.getLong("OID"),CC,CR,mappaPrezzi.get(jstanza.getLong("OID")));
                            System.out.println("OID Stanza " + jstanza.getLong("OID") + "result = " + risp);                           
                        }
                    }
                    
                    risp = risp && gestoreAnnuncio.inserisciInfoStanze(JSONstanze.length(), false);
                    risp = risp && gestoreAnnuncio.rendiModifichePersistenti();
                    
                if(risp){
                        resp.accumulate("action", "mostra-prezzo-stanze");
                    }
 
                }else if(infoModifica.compareToIgnoreCase("stanze-stanze")==0){
                    JSONObject annAttuale = this.gestoreAnnuncio.toJSON();
                    JSONArray JSONstanze = annAttuale.getJSONArray("Stanze");
                    JSONstanze = JSONstanze.getJSONArray(0);
                    for(int i=0;i<JSONstanze.length();i++){
                        JSONObject jstanza = JSONstanze.getJSONObject(i);
                        String tipoStanza = jstanza.getString("SuperTipo");
                        if(tipoStanza.equalsIgnoreCase("StanzaInAffitto")){
                            risp =  gestoreAnnuncio.modificaStanza(jstanza.getLong("OID"),CC,CR,jstanza.getDouble("Prezzo"));
                            System.out.println("OID Stanza " + jstanza.getLong("OID") + "result = " + risp);                           
                        }
                    }    
                    
                    
                }else if(infoModifica.compareToIgnoreCase("stanze-atomico")==0){
                    String prezzo = request.getParameter("prezzoA");
                    
                    JSONObject annAttuale = this.gestoreAnnuncio.toJSON();
                    JSONArray JSONstanze = annAttuale.getJSONArray("Stanze");
                    JSONstanze = JSONstanze.getJSONArray(0);
                    for(int i=0;i<JSONstanze.length();i++){
                        JSONObject jstanza = JSONstanze.getJSONObject(i);
                        String tipoStanza = jstanza.getString("SuperTipo");
                        if(tipoStanza.equalsIgnoreCase("StanzaInAffitto")){
                            risp = gestoreAnnuncio.modificaStanza(jstanza.getLong("OID"),false,false,0);
                                                      
                        }
                    }
                    risp = risp && gestoreAnnuncio.modificaInfoCostiAppartamento(Double.parseDouble(prezzo), CC, CR);
                    risp = risp && gestoreAnnuncio.inserisciInfoStanze(JSONstanze.length(), true);
                    risp = risp && gestoreAnnuncio.rendiModifichePersistenti();
                    if(risp){
                        resp.accumulate("action", "nascondi-prezzo-stanze");
                    }
                    
                    
                }else{
                    
                }
                
                //ELABORO RISPOSTA
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                
                if(risp){
                    JSONObject editedAnnuncio = this.gestoreAnnuncio.toJSON();
                    System.out.println("ANDATO A BUON FINE");
                    resp.accumulate("response", "OK");
                    resp.accumulate("data", editedAnnuncio);
                    out.write(resp.toString());
                }else{
                System.out.println("NON ANDATO A BUON FINE");
                    resp.accumulate("response", "NO");
                    out.write(resp.toString());
                }

                

               
              

            } //metodi non di mia competenza  
            else if (action.equalsIgnoreCase("Annunci-oscuraAnnuncio")) {

                System.out.println("-----OSCURA ANNUNCIO:");

                String oidAnnuncio = request.getParameter("oidAnnuncio");
                String value = request.getParameter("oscuratoValue");

                boolean res = gestoreAnnuncio.oscusaAnnuncio(Long.parseLong(oidAnnuncio), value.equalsIgnoreCase("true"));

                //ELABORO RISPOSTA
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                JSONObject resp = new JSONObject();
                resp.accumulate("response", res);
                out.write(resp.toString());

            } else if (action.equalsIgnoreCase("Annuncio-getJSONByOid")) {

                System.out.println("-----RECUPERA ANNUNCIO BY OID:");

                String oidAnnuncio = request.getParameter("oid");

                JSONObject res = gestoreAnnuncio.toJSON(Long.parseLong(oidAnnuncio));

                //ELABORO RISPOSTA
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                if (res != null) {
                    JSONObject resp = new JSONObject();
                    resp.accumulate("response", "OK");
                    resp.accumulate("data", res);
                    out.write(resp.toString());
                } else {
                    JSONObject resp = new JSONObject();
                    resp.accumulate("response", "NO");
                    //resp.accumulate("data", res);
                    out.write(resp.toString());
                }

            } else {

            }
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(ServletAnnuncio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(ServletAnnuncio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
