/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import ejb.GestoreRicercaLocal;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author gianp_000
 */
public class ServletRicerca extends HttpServlet {

    @EJB
    private GestoreRicercaLocal gestoreRicerca;

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
            RequestDispatcher rd = null;
            String action = request.getParameter("action");
            //out.println(action);
            if (action.equalsIgnoreCase("setCity")) {
                String city = request.getParameter("city");
                gestoreRicerca.selezionaCittà(city);
                //invia quartieri
                System.out.println(gestoreRicerca.attualeToJSON());
                ArrayList<String> quartieri = gestoreRicerca.getQuartieriCittà();
                for (String q : quartieri) {
                    System.out.println(q);
                }
                request.setAttribute("quartieri", quartieri);
                getServletContext().getRequestDispatcher("/search.jsp").forward(request, response);

            } else if (action.equalsIgnoreCase("search")) {
                String[] quartieri = request.getParameterValues("quartieri");
                String tipo = request.getParameter("tipo");
                String pricefrom = request.getParameter("pricefrom");
                String compCondomino = request.getParameter("compCondomino");
                String compRiscaldamento = request.getParameter("compRiscaldamento");
                System.out.println("Quartieri selezionti");
                ArrayList<String> quartieriCittà = gestoreRicerca.getQuartieriCittà();
                ArrayList<String> quartieriSel = new ArrayList();
                for (String selection : quartieri) {
                    System.out.println(selection);
                    if (selection.equalsIgnoreCase("all")) {
                        quartieriSel = quartieriCittà;
                        break;
                    } else {
                        boolean find = false;
                        for (String qC : quartieriCittà) {
                            if (qC.equalsIgnoreCase(selection)) {
                                find = true;
                            }
                        }
                        if (find) {
                            quartieriSel.add(selection);
                        }
                    }
                }
                System.out.println(" Tipo:" + tipo + " PriceFrom:" + pricefrom);
                gestoreRicerca.creaFiltroDiRicerca(Integer.valueOf(pricefrom), quartieriSel, compCondomino!=null, compRiscaldamento!=null);
                System.out.println(gestoreRicerca.attualeToJSON());
                if(tipo.equalsIgnoreCase("Appartamento")){
                    String nL = request.getParameter("numeroLocali");
                    String nB = request.getParameter("numeroBagni");
                    String nC = request.getParameter("numeroCamere");
                    String met = request.getParameter("metratura");
                    gestoreRicerca.aggiornaAFiltroAppartamento(Integer.valueOf(nL), Integer.valueOf(nB), Integer.valueOf(nC), Double.valueOf(met));
                    System.out.println(gestoreRicerca.attualeToJSON());
                }else if(tipo.equalsIgnoreCase("Stanza")){
                   String tS = request.getParameter("tipoStanza"); 
                   gestoreRicerca.aggiornaAFiltroStanza(tS);
                   System.out.println(gestoreRicerca.attualeToJSON());
                }
            } else {
                out.println("<p> Che vuoi?? </p>");
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
