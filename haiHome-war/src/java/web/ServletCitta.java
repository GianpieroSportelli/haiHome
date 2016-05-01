/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import ejb.GestoreCittaLocal;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Eugenio Liso
 */
public class ServletCitta extends HttpServlet {

    @EJB
    private GestoreCittaLocal gestoreCitta;

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

            if (action.equalsIgnoreCase("inserisci-citta")) {
                String nomeCitta = request.getParameter("NomeCitta");
                if (gestoreCitta.insertCitta(nomeCitta)) {
                    out.write("OK");
                } else {
                    out.write("ERRORE");
                }
            } else if (action.equalsIgnoreCase("cancella-citta")) {
                String nomeCitta = request.getParameter("NomeCitta");
                if (gestoreCitta.deleteCitta(nomeCitta)) {
                    out.write("OK");
                } else {
                    out.write("ERRORE");
                }
            } else if (action.equalsIgnoreCase("get-lista-citta")) {
                ArrayList<String> result = gestoreCitta.getAllCittàNome();
                JSONArray citta = new JSONArray();
                for (String c : result) {

                    citta.put(c);

                }
                if (result != null) {
                    out.write(citta.toString());
                } else {
                    out.write("false");
                }
            } else if (action.equalsIgnoreCase("get-lista-quartieri-citta")) {
                ArrayList<String> result = gestoreCitta.getListaQuartieri(request.getParameter("citta"));
                JSONArray quartieri = new JSONArray();
                for (String quartiere : result) {

                    quartieri.put(quartiere);

                }
                if (result != null) {
                    out.write(quartieri.toString());
                } else {
                    out.write("false");
                }
            } else if (action.equalsIgnoreCase("add-cap")) {
                String citta = request.getParameter("citta");
                String quartiere = request.getParameter("quartiere");
                String[] caps = request.getParameter("cap").split(",");

                ArrayList<String> capDaInserire = new ArrayList<>();
                for (String c : caps) {

                    capDaInserire.add(c);
                }

                if (gestoreCitta.insertQuartiere(citta, quartiere, capDaInserire)) {
                    System.out.println("Cap inseriti: " + capDaInserire.toString());

                    out.write("true");
                } else {
                    out.write("false");
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
