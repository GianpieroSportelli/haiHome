/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import ejb.GestoreRicercaLocal;
import ejb.GestoreTestLocal;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author gianp_000
 */
public class TestServlet extends HttpServlet {

    @EJB
    private GestoreRicercaLocal gestoreRicerca;

    @EJB
    private GestoreTestLocal gestoreTest;

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
            out.println("<title>Servlet TestServlet</title>");
            out.println("</head>");
            out.println("<body>");
            String nome = "Torino";

            gestoreTest.addCittà(nome);
            ArrayList<String> cities = gestoreTest.getAllCittàNome();
            if (cities.isEmpty()) {
                out.println("<p>Non sono Presenti città nel DB</p>");
            } else {
                for (String cit : cities) {
                    out.println("<p>" + cit + "</p>");
                }
            }
            String[] Quartieri = {"Centro", "Crocetta", "Santa Rita", "Mirafiori Nord",
                "Borgo San Paolo", "Cenisia", "Pozzo Strada", "Cit Turin", "Borgata Lesna",
                "San Donato", "Campidoglio", "Parella",
                "Borgo Vittoria", "Madonna di Campagna", "Lucento", "Vallette",
                "Barriera di Milano", "Regio Parco", "Barca", "Bertolla", "Falchera", "Rebaudengo", "Villaretto",
                "Aurora", "Vanchiglia", "Sassi", "Madonna del Pilone",
                "San Salvario", "Cavoretto", "Borgo Po",
                "Nizza Millefonti", "Lingotto", "Filadelfia",
                "Mirafiori Sud"};
            String circoscrizioni="Di Bruce The Deus - Opera propria, <a href=\"http://creativecommons.org/licenses/by-sa/3.0/\" title=\"Creative Commons Attribuzione - Condividi allo stesso modo versioni 3.0\">CC BY-SA 3.0</a>, https://it.wikipedia.org/w/index.php?curid=4681908";
            for (String quartiere : Quartieri) {
                gestoreTest.addQuartiere(nome, quartiere);
            }
            ArrayList<String> quartieri = gestoreTest.getListaQuartieriNome(nome);
            for (String quartiere : quartieri) {
                out.println("<p>" + quartiere + "</p>");
            }

            /*
             gestoreTest.cancellaCittà(nome);
             gestoreRicerca.selezionaCittà(nome);
             gestoreRicerca.creaFiltroDiRicerca(600, new ArrayList<>(), true, true);
             gestoreRicerca.aggiornaAFiltroAppartamento(1,1,1,0);
             out.println(gestoreRicerca.isFiltroAppartamento());
             request.setAttribute("gestoreRicerca", gestoreRicerca);
             request.getRequestDispatcher("/TestServlet2").forward(request, response);
             */
            
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
