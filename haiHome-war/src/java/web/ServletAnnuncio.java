/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import ejb.GestoreAnnuncioLocal;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig
/**
 *
 * @author gianp_000
 */
public class ServletAnnuncio extends HttpServlet {

    @EJB
    private GestoreAnnuncioLocal gestoreAnnuncio;

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
    private final static String tempFolderPath = "//Users//giacomocavallo//NetBeansProjects//ProgettoSSCSWeb//haiHome//haiHome-war//web//Immagini//tempAppImg//";
    //ArrayList<FileOutputStream> photoTempPath;
    private HashMap<String, ArrayList<String>> photoTempPath;
    private HashMap<String, ArrayList<String>> stanzeInfo;

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            String action = request.getParameter("action");
            String header = request.getHeader("action");

            if (header != null) {

                action = "Annunci-newAnnuncio-FotoUpload";

            }

            if (action.equalsIgnoreCase("Annunci-newAnnuncio-initialRequest")) {

                String idLocatore = request.getParameter("idLocatore");
                System.out.println("ID LOCATORE: " + idLocatore);

                //creo annuncio
                boolean result = gestoreAnnuncio.CreaAnnuncio(Long.parseLong(idLocatore));
                System.out.println("CREA ANNUNCIO RESULT: " + result);

                //inizializzo arrayList stanze
                photoTempPath = new HashMap();
                stanzeInfo = new HashMap();

                //Elaboro la Risposta
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                out.write("Locatore confermato - Annuncio Inizializzato");

            } else if (action.equalsIgnoreCase("Annunci-newAnnuncio-infoAppartamento")) {

                System.out.println("----- INFO APPARTAMENTO:");

                String citta = request.getParameter("Città").trim();
                String quartiere = request.getParameter("Quartiere").trim();
                String indirizzo = request.getParameter("Indirizzo").trim() + ", " + request.getParameter("Civico").trim();

                //da gestire la latitudine longitutine latlng = {0.0,0.0};
                double[] latlng = {0.0, 0.0};
                //Inserisco informazioni
                gestoreAnnuncio.inserisciInfoIndirizzo(citta, quartiere, indirizzo, latlng);

                System.out.println("CITTA: " + citta);
                System.out.println("QUARTIERE: " + quartiere);
                System.out.println("INDIRIZZO: " + indirizzo);
                System.out.println("\n");

                response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
                response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
                out.write("Dati info Appartamento inseriti");

            } else if (action.equalsIgnoreCase("Annunci-newAnnuncio-infoAnnuncio")) {

                System.out.println("-----INFO ANNUNCIO:");

                String descrizione = request.getParameter("Descrizione").trim();
                double metraturaApp = Double.parseDouble(request.getParameter("Metratura").trim());
                //Da inserire la data
                Date dataInizioAffitto = new Date();
                //Da gestire la data inizio affitto Date dataInizioAffitto = new Date();
                gestoreAnnuncio.inserisciInfoAnnuncio(descrizione, metraturaApp, dataInizioAffitto);

                System.out.println("DESCRIZIONE: " + descrizione);
                System.out.println("METRATURA: " + metraturaApp);
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

                    stanzeInfo.put(numeroStanza[i], infoStanza);

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

                Collection<Part> files = request.getParts();

                for (Part filePart : files) {

                    String fileName = filePart.getSubmittedFileName();

                    String numStanza = filePart.getName();

                    InputStream filecontent;
                    filecontent = filePart.getInputStream();

                    String path = gestoreAnnuncio.persistiFoto(filecontent, fileName, "", numStanza);

                    /*
                    (new File(stanzaFolderName)).mkdir();
                    
                    String path = stanzaFolderName + "//" + fileName;

                    FileOutputStream tempFile = new FileOutputStream(path);
                    
                    int read = 0;
                    final byte[] bytes = new byte[1024];
                    while ((read = filecontent.read(bytes)) != -1) {
                        tempFile.write(bytes, 0, read);
                    }
                    
                    filecontent.close();
                    tempFile.close();
                     */
                    if (!photoTempPath.containsKey(numStanza)) {
                        ArrayList<String> temp = new ArrayList();
                        temp.add(path);
                        photoTempPath.put(numStanza, temp);
                    } else {
                        ArrayList<String> temp = photoTempPath.get(numStanza);
                        temp.add(path);
                        photoTempPath.put(numStanza, temp);
                    }

                    System.out.println("NOME FOTO: " + fileName);

                }

                response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
                response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/Annunci_JSP/InserimentoAnnunci.jsp");
                rd.forward(request, response);
                out.write("Dati FOTO annunci inseriti ");

            } else if (action.equalsIgnoreCase("Annunci-newAnnuncio-infoCosti")) {

                //System.out.println("action: Annunci-newAnnuncio-infoAppartamento");
                System.out.println("----- INFO COSTI:");

                String tipoCosti = request.getParameter("Tipo Costo").trim();
                int numeroStanze = stanzeInfo.size();
                
                //Info sulle stanze
                gestoreAnnuncio.inserisciInfoStanze(numeroStanze, tipoCosti.compareToIgnoreCase("1") == 0);


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

                    for(int j=0;j<idStanze.length;j++){
                        ArrayList<String> infoStanza = stanzeInfo.get(idStanze[j]);
                        gestoreAnnuncio.inserisciNuovaStanzaInAffitto(infoStanza.get(1), photoTempPath.get(idStanze[j]),CCS,CRS, Double.parseDouble(infoStanza.get(2)),0);
                        stanzeInfo.remove(idStanze[j]);
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
                
                //inserisco info finali sull'annuncio
                
                //non qui da mettere l'anteprima e la conferma
                boolean result = gestoreAnnuncio.rendiAnnuncioPersistente();

                response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
                response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
                out.write("Appartamento inserito, risultato: " + result);

            } else {

            }

            System.out.println("Errori nella response: " + out.checkError());
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
        processRequest(request, response);
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
        processRequest(request, response);
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
