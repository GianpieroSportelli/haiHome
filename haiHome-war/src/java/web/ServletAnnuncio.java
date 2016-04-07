/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
             
             /*DA ELIMINARE
             Map<String,String[]> parametri1 = request.getParameterMap();
             Part filePart1 = request.getPart("file");
             InputStream filecontent1 = filePart1.getInputStream();
             
             //FINE DA ELIMINARE */
              System.out.println("Azione richiesta: " + action);
             if (action.equalsIgnoreCase("Annunci-newAnnuncio")){
                 
                 System.out.println("SIAMO DENTRO");
                 
                 System.out.println("Elenco parametri disponibili");
                 
                 Map<String,String[]> parametri = request.getParameterMap();
                 
                 // prova per le foto
                 Part filePart = request.getPart("file");
                  InputStream filecontent = filePart.getInputStream();
                 
                 
                 //fine prova
                 
                 //prendo i dati
                 System.out.println("Inizio A prendere i Dati dell'annuncio");
                 //Info Appartamento
                 String citta = request.getParameter("Città").trim();
                 String quartiere = request.getParameter("Quartiere").trim();
                 String indirizzo = request.getParameter("Indirizzo").trim();
                 String civico = request.getParameter("Civico").trim();
                 
                 //info Annuncio (manca la data)
                 String descrizione = request.getParameter("Descrizione").trim();
                 String metratura = request.getParameter("Metratura").trim();
                 
                 


                 System.out.println("Appartamento");
                 
                 System.out.println("  " + citta);
                 System.out.println("  " + quartiere);
                 System.out.println("  " + indirizzo);
                 System.out.println("  " + civico);
                 System.out.println("  " + descrizione);
                 System.out.println("  " + metratura);
                 
                 
                 
                response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
                response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
                response.getWriter().write("SIAMO DENTRO");
                
             }else if(action.equalsIgnoreCase("Annunci-newAnnuncio-infoAppartamento")){
                    System.out.println("FUNONZIA!!!!");
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
