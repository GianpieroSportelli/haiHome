/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import ejb.GestoreSegnalazioneLocal;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author SPORT
 */
public class ServletSegnalazione extends HttpServlet {

    @EJB
    private GestoreSegnalazioneLocal gestoreSegnalazione;

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
            System.out.println("ACTION: " + action);

            if (action.equalsIgnoreCase("Segnalazione-addSegnalazione")) {
                String id_studente = request.getParameter("id_studente");
                String id_annuncio = request.getParameter("id_annuncio");
                String descrizione = request.getParameter("descrizione");
                //System.out.println("studente: "+id_studente+" annuncio: "+id_annuncio+" descrizione: "+descrizione);
                boolean result = gestoreSegnalazione.addSegnalazione(id_studente, id_annuncio, descrizione);
                //System.out.println("result: "+result);
                out.write("" + result);

            } else if (action.equalsIgnoreCase("Segnalazione-getAllSegnalazioni")) {
                JSONArray all = gestoreSegnalazione.MapAnnunciSegnalati();
                String json = all.toString();
                response.setContentType("application/json");
                //System.out.println("Result: "+json);
                out.write(json);
            } else if (action.equalsIgnoreCase("Segnalazione-archivia")) {
                String json = request.getParameter("segnalazione");
                String status = request.getParameter("status");
                try {
                    JSONObject Segn = new JSONObject(json);
                    System.out.println("Da archiviare: " + json);
                    boolean result = gestoreSegnalazione.archiviaSegnalazioni(Segn, Boolean.valueOf(status));
                    out.write("" + result);
                } catch (JSONException ex) {
                    out.write("false");
                    Logger.getLogger(ServletSegnalazione.class.getName()).log(Level.SEVERE, null, ex);
                }
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
