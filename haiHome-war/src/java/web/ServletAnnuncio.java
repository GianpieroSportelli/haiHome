/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import ejb.GestoreAnnuncioLocal;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@MultipartConfig
/**
 *
 * @author gianp_000
 */
public class ServletAnnuncio extends HttpServlet {

    @EJB
    private GestoreAnnuncioLocal gestoreAnnuncio;

    //Parametri richiesti
    private String citta;
    private String quartiere;
    private String indirizzo;
    private double[] latlng = {0.0, 0.0};

    String descrizione;
    double metraturaApp;
    Date dataInizioAffitto = new Date();
    int numeroStanze;
    boolean atomico;

    //Utily Photo haiHome-war/src/java/web/FotoUploadServlet.java
    
    ////Users/giacomocavallo/NetBeansProjects/ProgettoSSCSWeb/haiHome/haiHome-war/web/Immagini/tempAppImg
    String tempFolderPath ="//Users//giacomocavallo//NetBeansProjects//ProgettoSSCSWeb//haiHome//haiHome-war//web//Immagini//tempAppImg//"; 
    //ArrayList<FileOutputStream> photoTempPath;
    HashMap<String,String> photoTempPath;

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

                //Elaboro la Risposta
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                out.write("Locatore confermato - Annuncio Inizializzato");

            } else if (action.equalsIgnoreCase("Annunci-newAnnuncio-infoAppartamento")) {

                System.out.println("----- INFO APPARTAMENTO:");

                citta = request.getParameter("Città").trim();
                quartiere = request.getParameter("Quartiere").trim();
                indirizzo = request.getParameter("Indirizzo").trim() + ", " + request.getParameter("Civico").trim();

                //da gestire la latitudine longitutine latlng = {0.0,0.0};
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

                descrizione = request.getParameter("Descrizione").trim();
                metraturaApp = Double.parseDouble(request.getParameter("Metratura").trim());
                //Da inserire la data

                //Da gestire la data inizio affitto Date dataInizioAffitto = new Date();
                gestoreAnnuncio.inserisciInfoAnnuncio(descrizione, metraturaApp, dataInizioAffitto);

                System.out.println("DESCRIZIONE: " + descrizione);
                System.out.println("METRATURA: " + metraturaApp);
                System.out.println("\n");
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                out.write("Dati info Annuncio inseriti " + citta);

            } else if (action.equalsIgnoreCase("Annunci-newAnnuncio-infoStanze")) {

                System.out.println("-----INFO STANZE");

                String[] numeroStanza = request.getParameterValues("numStanza");
                for(String s : numeroStanza){
                    System.out.println("Numero Stanza: " + s);
                }
                
                String[] tipologiaStanze = request.getParameterValues("TipologiaStanza");
                String[] tipoL = request.getParameterValues("TipoL");
                String[] tipoA = request.getParameterValues("TipoA");

                String[] metraturaS = request.getParameterValues("MetraturaS");

                for (int i = 0; i < tipologiaStanze.length; i++) {
                    System.out.println("Stanza n° " + (i + 1) + "-----------------------");

                    System.out.println("Tipologia stanza " + tipologiaStanze[i]);
                    System.out.println("Tipo Letto " + tipoL[i]);
                    System.out.println("Tipo Accessoria " + tipoA[i]);
                    System.out.println("Metratura Stanza" + metraturaS[i]);

                }

                response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
                response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
                out.write("Dati info Annuncio inseriti " + citta);

            } else if (action.equalsIgnoreCase("Annunci-newAnnuncio-FotoUpload")) {

                System.out.println("-----FOTO DELLE STANZE");
                Collection<Part> files = request.getParts();

                for (Part filePart : files) {
                    String numStanza = filePart.getName();
                    //String numStanza = request.getHeader("numStanza");
                    String fileName = filePart.getSubmittedFileName();
                    
                    String stanzaFolderName = tempFolderPath + numStanza;
                    
                    (new File(stanzaFolderName)).mkdir();
                    
                    String path = stanzaFolderName + "//" + fileName;

                    InputStream filecontent = filePart.getInputStream();
                    FileOutputStream tempFile = new FileOutputStream(path);
                    
                    int read = 0;
                    final byte[] bytes = new byte[1024];
                    while ((read = filecontent.read(bytes)) != -1) {
                        tempFile.write(bytes, 0, read);
                    }
                    
                    filecontent.close();
                    tempFile.close();
                    
                   //photoTempPath.put(numStanza, path);
                   
                   

                    System.out.println("NOME FOTO: " + fileName);
                }

                response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
                response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
                out.write("Dati info Annuncio inseriti ");

            } else if (action.equalsIgnoreCase("Annunci-newAnnuncio-infoCosti")) {

                //System.out.println("action: Annunci-newAnnuncio-infoAppartamento");
                System.out.println("----- INFO COSTI:");

                String tipoCosti = request.getParameter("Tipo Costo").trim();
                String prezzoA = request.getParameter("prezzoA").trim();
                String compCondA = request.getParameter("compresoCondominioA").trim();
                String compRiscA = request.getParameter("compresoRiscaldamentoA").trim();
                //System.out.println("Dati letti:  " + citta + " - " + quartiere + " - " + indirizzo + ", " + civico);

                String[] prezzoS = request.getParameterValues("PrezzoS");
                String[] compCondS = request.getParameterValues("compresoCondominioS");
                String[] compRiscS = request.getParameterValues("compresoRiscaldamentoS");

                System.out.println("TIPO COSTI: " + tipoCosti);
                System.out.println("PREZZO APPARTAMENTO: " + prezzoA);
                System.out.println("COMPRESO CONDOMINIO APP: " + compCondA);
                System.out.println("COMPRESO RISCALDAMENTO APP: " + compRiscA);
                System.out.println("\n");
                for (int i = 0; i < prezzoS.length; i++) {
                    System.out.println("Stanza: " + i);
                    System.out.println("PREZZO STANZA: " + prezzoS[i]);
                    System.out.println("COMPRESO CONDOMINIO STA: " + compCondS[i]);
                    System.out.println("COMPRESO RISCALDAMENTO STA: " + compRiscS[i]);
                }

                // out.write("Dati InfoAppartamento Letti!");
                response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
                response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
                out.write("Dati info Appartamento inseriti");

            } else {

            }
     
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
