/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import com.google.gson.Gson;
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
            System.out.println(action);
            if (action.equalsIgnoreCase("setCity")) {
                String city = request.getParameter("city");
                gestoreRicerca.selezionaCittà(city);
                //invia quartieri
                System.out.println("Filtro Creato:");
                System.out.println(gestoreRicerca.attualeToJSON());
                /*PROVA AJAX
                 ArrayList<String> quartieri = gestoreRicerca.getQuartieriCittà();
                 ArrayList<String> tipoStanza=gestoreRicerca.getTipoStanza();
                 request.setAttribute("quartieri", quartieri);
                 request.setAttribute("tipoStanza", tipoStanza);
                 request.setAttribute("JSONAnnunci", gestoreRicerca.usaFiltroAttuale());
                 request.setAttribute("latlng", gestoreRicerca.geocodeCurrentCity());
                 */
                getServletContext().getRequestDispatcher("/search-page.jsp").forward(request, response);

            } else if (action.equalsIgnoreCase("search")) {
                String[] quartieri = request.getParameterValues("quartieri");
                String tipo = request.getParameter("tipo");
                String pricefrom = request.getParameter("pricefrom");
                String compCondomino = request.getParameter("compCondomino");
                String compRiscaldamento = request.getParameter("compRiscaldamento");
                //System.out.println("Quartieri selezionti");
                ArrayList<String> quartieriCittà = gestoreRicerca.getQuartieriCittà();
                ArrayList<String> quartieriSel = new ArrayList();
                if (quartieri == null) {
                    quartieriSel = quartieriCittà;
                } else {
                    for (String selection : quartieri) {
                        //System.out.println(selection);
                        boolean find = false;
                        for (String qC : quartieriCittà) {
                            if (qC.equalsIgnoreCase(selection)) {
                                find = true;
                                break;
                            }
                        }
                        if (find) {
                            quartieriSel.add(selection);
                        }
                    }
                }

                
                boolean result;
                result=gestoreRicerca.creaFiltroDiRicerca(Integer.valueOf(pricefrom), quartieriSel, compCondomino != null, compRiscaldamento != null);
                System.out.println(gestoreRicerca.attualeToJSON());
                if (tipo.equalsIgnoreCase("Appartamento")) {
                    String nL = request.getParameter("numeroLocali");
                    String nB = request.getParameter("numeroBagni");
                    String nC = request.getParameter("numeroCamere");
                    String met = request.getParameter("metratura");
                    result=gestoreRicerca.aggiornaAFiltroAppartamento(Integer.valueOf(nL), Integer.valueOf(nB), Integer.valueOf(nC), Double.valueOf(met));
                    System.out.println(gestoreRicerca.attualeToJSON());
                } else if (tipo.equalsIgnoreCase("Stanza")) {
                    String tS = request.getParameter("tipoStanza");
                    result=gestoreRicerca.aggiornaAFiltroStanza(tS);
                    System.out.println(gestoreRicerca.attualeToJSON());
                }
                //ArrayList<String> quartieri_all = gestoreRicerca.getQuartieriCittà();
                /*for (String q : quartieri) {
                 System.out.println(q);
                 }*/
                //ArrayList<String> tipoStanza = gestoreRicerca.getTipoStanza();
                
                /*request.setAttribute("quartieri", quartieri_all);
                request.setAttribute("tipoStanza", tipoStanza);
                request.setAttribute("JSONAnnunci", gestoreRicerca.usaFiltroAttuale());
                request.setAttribute("latlng", gestoreRicerca.geocodeCurrentCity());
                getServletContext().getRequestDispatcher("/search.jsp").forward(request, response);*/
                response.setContentType("application/json");
                System.out.println(" Filtro Aggiornato: "+result);
                String json = new Gson().toJson(""+result);
                System.out.println(json);
                out.write(json);
            } else if (action.equalsIgnoreCase("AjaxGetInfo")) {
                System.out.println("I'm in!!");
                response.setContentType("application/json");
                String json = new Gson().toJson(gestoreRicerca.geocodeCurrentCity());
                System.out.println(json);
                out.write(json);
            }else if (action.equalsIgnoreCase("Ricerca-geoCity")) {
                //System.out.println("I'm in!!");
                response.setContentType("application/json");
                String json = new Gson().toJson(gestoreRicerca.geocodeCurrentCity());
                //System.out.println(json);
                out.write(json);
            } else if (action.equalsIgnoreCase("Ricerca-getAnnunciFiltro")) {
                //System.out.println("I'm in!!");
                response.setContentType("application/json");
                String json =gestoreRicerca.usaFiltroAttuale().toString(); //new Gson().toJson(gestoreRicerca.usaFiltroAttuale());
                System.out.println(json);
                out.write(json);
            } else if (action.equalsIgnoreCase("Ricerca-getQuartieri")) {
                //System.out.println("I'm in!!");
                response.setContentType("application/json");
                String json =new Gson().toJson(gestoreRicerca.getQuartieriCittà());
                System.out.println(json);
                out.write(json);
            }else if (action.equalsIgnoreCase("Ricerca-getTipoStanza")) {
                //System.out.println("I'm in!!");
                response.setContentType("application/json");
                String json =new Gson().toJson(gestoreRicerca.getTipoStanza());
                System.out.println(json);
                out.write(json);
            }else {
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
