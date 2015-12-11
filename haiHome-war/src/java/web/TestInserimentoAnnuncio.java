/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import ejb.GestoreAnnuncioLocal;
import ejb.GestoreLocatoreLocal;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author giacomocavallo
 */
@WebServlet(name = "TestInserimentoAnnuncio", urlPatterns = {"/TestInserimentoAnnuncio"})
public class TestInserimentoAnnuncio extends HttpServlet {
    
    @EJB
    GestoreLocatoreLocal gestoreLocatore;

    @EJB
    private GestoreAnnuncioLocal gestoreAnnuncio;


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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TestInserimentoAnnuncio</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<p>inserimento Annunci</p>");
            gestoreLocatore.aggiungiLocatore("gianpiero.sportelli@libero.it", "gianpiero", "sportelli", "foto", "password","prova locatore");
            //inizio inserimento annuncio
            double[] latlng = {0, 0};
            ArrayList<String> foto = new ArrayList<>();

            for (int i = 0; i < 10; i++) {

                gestoreAnnuncio.CreaAnnuncio(gestoreLocatore.getLocatore());

                gestoreAnnuncio.inserisciInfoIndirizzo("Torino", "Aurora", "Via don Bosco 1" + i, latlng);

               
                if (i % 2 == 0) {
                     //Meglio InfoAnnuncio
                    gestoreAnnuncio.inserisciInfoAnnuncio("descrizione"+i, 200, new Date(), 3, true);

                    gestoreAnnuncio.inserisciNuovaStanzaInAffitto("Singola", foto, 50);

                    gestoreAnnuncio.inserisciNuovaStanzaAccessoria("Bagno", foto, 30);

                    gestoreAnnuncio.inserisciNuovaStanzaAccessoria("Cucina", foto, 30);

                    //Meglio InfoCostiAnnuncio
                    gestoreAnnuncio.inserisciInfoCostiAppartamento(500, false, false);
                }else{
                   //Meglio InfoAnnuncio
                    gestoreAnnuncio.inserisciInfoAnnuncio("descrizione" + 1, 300, new Date(), 4, false);

                    gestoreAnnuncio.inserisciNuovaStanzaInAffitto("Singola", foto, true, true, 50, 250);
                    
                    gestoreAnnuncio.inserisciNuovaStanzaInAffitto("Singola", foto, true, true, 50, 250);

                    gestoreAnnuncio.inserisciNuovaStanzaAccessoria("Bagno", foto, 30);

                    gestoreAnnuncio.inserisciNuovaStanzaAccessoria("Cucina", foto, 30);

                    //Meglio InfoCostiAnnuncio
                    gestoreAnnuncio.inserisciInfoCostiAppartamento(0, false, false); 
                }

                gestoreAnnuncio.rendiAnnuncioPersistente();

                JSONObject json = this.gestoreAnnuncio.toJSON();

                out.println("<p>" + json.toString() + "</p>");
            }
            out.println("</body>");
            out.println("</html>");
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
