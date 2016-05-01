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
                JSONObject result = gestoreCitta.getListaCitta();
                if (result != null) {
                    System.out.println(result.toString());
                    out.write(result.toString());
                } else {
                    System.out.println("Non è stato possibile ottenere la lista delle città.");
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
