/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import ejb.GestoreAnnuncio;
import ejb.GestoreAnnuncioLocal;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;
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

    private GestoreAnnuncioLocal gestoreAnnuncio;
    private String citta;
    
    
    
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
            
            if(header!=null){
            //System.out.println("    action: immaginistanza");
            //Part filePart = request.getPart("file");
                action="sa";   
                Collection<Part> files = request.getParts();
                System.out.println("----- STANZA");
                for(Part filePart : files){
                    String fileName = filePart.getSubmittedFileName();
                    
                    System.out.println("NOME FOTO: " + fileName);
                    System.out.println("\n");
                }
                
                

                response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
                response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
                out.write("Dati info Annuncio inseriti ");
            }


            if(action.equalsIgnoreCase("Annunci-newAnnuncio-initialRequest")){
                gestoreAnnuncio = new GestoreAnnuncio();
                String idLocatore = request.getParameter("idLocatore");
                System.out.println("ID LOCATORE: " + idLocatore );
                
                //creo annuncio
                //boolean result = gestoreAnnuncio.CreaAnnuncio(idLocatore);
                //System.out.println("CREA ANNUNCIO RESULT: " + result );
                
                
            }else if (action.equalsIgnoreCase("Annunci-newAnnuncio-infoAppartamento")) {

                
                System.out.println("----- INFO APPARTAMENTO:");
                citta = request.getParameter("Città").trim();
                String quartiere = request.getParameter("Quartiere").trim();
                String indirizzo = request.getParameter("Indirizzo").trim();
                String civico = request.getParameter("Civico").trim();
                //System.out.println("Dati letti:  " + citta + " - " + quartiere + " - " + indirizzo + ", " + civico);
                System.out.println("CITTA: " + citta);
                System.out.println("QUARTIERE: " + quartiere);
                System.out.println("INDIRIZZO: " + indirizzo + ", " + civico);
                System.out.println("\n");
                
               // out.write("Dati InfoAppartamento Letti!");
               
               response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
               response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
               out.write("Dati info Appartamento inseriti");


            }else if (action.equalsIgnoreCase("Annunci-newAnnuncio-infoAnnuncio")) {
                
                
                //System.out.println("    action: Annunci-newAnnuncio-infoAnnuncio");
                System.out.println("-----INFO ANNUNCIO:");
                String descrizione = request.getParameter("Descrizione").trim();
                String metraturaApp = request.getParameter("Metratura").trim();
                
                //Da inserire la data

                //System.out.println("Dati letti:  " + descrizione + " - " + metraturaApp);
                //out.write("Dati InfoAnnuncio Letti!");
                System.out.println("DESCRIZIONE: " + descrizione);
                System.out.println("METRATURA: " + metraturaApp);
                System.out.println("\n");
                response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
                response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
                 out.write("Dati info Annuncio inseriti " + citta);

                
            } else if (action.equalsIgnoreCase("Annunci-newAnnuncio-infoStanze")) {
                
                
                System.out.println("    action: Annunci-newAnnuncio-infoStanze");
                String[] tipologiaStanze = request.getParameterValues("TipologiaStanza");
                Map<String, String[]> parametri = request.getParameterMap();
                String[] tipoL = request.getParameterValues("TipoL");
                String[] tipoA = request.getParameterValues("TipoA");
                
                String[] metraturaS = request.getParameterValues("MetraturaS");
                
                for(int i=0;i<tipologiaStanze.length;i++){
                    System.out.println("Stanza n° " + (i+1) + "-----------------------" );
                    
                    System.out.println("Tipologia stanza " + tipologiaStanze[i] );
                    System.out.println("Tipo Letto " + tipoL[i] );
                    System.out.println("Tipo Accessoria " + tipoA[i] );
                    System.out.println("Metratura Stanza" + metraturaS[i] );
                    
                }
                
                response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
                response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
                 out.write("Dati info Annuncio inseriti " + citta);

                
            }else if (action.equalsIgnoreCase("prova")) {
                //System.out.println("    action: informazioni stanza");
                System.out.println("INFO STANZA:");
                
                String tipologia = request.getParameter("TipologiaStanza").trim();     
                String tipoL = request.getParameter("TipoL").trim();
                String tipoA = request.getParameter("TipoA").trim();
                
                String metraturaS = request.getParameter("MetraturaS");
                
                //System.out.println("Tipo Letto " + tipoL + " Tipo Accessoria " + tipoA + " Metratura " + metraturaS);
                System.out.println("TIPOLOGIA STANZA: " + tipologia);
                System.out.println("TIPO STANZA LETTO: " + tipoL);  
                System.out.println("TIPO STANZA ACCESSORIA: " + tipoA);
                System.out.println("Metratura: " + metraturaS);
                System.out.println("\n");
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
                for(int i =0;i<prezzoS.length;i++){
                    System.out.println("Stanza: " + i);
                    System.out.println("PREZZO STANZA: " + prezzoS[i]);
                    System.out.println("COMPRESO CONDOMINIO STA: " + compCondS[i]);
                    System.out.println("COMPRESO RISCALDAMENTO STA: " + compRiscS[i]);
                }
                
               // out.write("Dati InfoAppartamento Letti!");
               
               response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
               response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
               out.write("Dati info Appartamento inseriti");


            }else{
                           
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
