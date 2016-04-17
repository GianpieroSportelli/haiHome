/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import com.google.gson.Gson;
import ejb.GestoreRicercaLocal;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
                result = gestoreRicerca.creaFiltroDiRicerca(Integer.valueOf(pricefrom), quartieriSel, compCondomino != null, compRiscaldamento != null);
                System.out.println(gestoreRicerca.attualeToJSON());
                if (tipo.equalsIgnoreCase("Appartamento")) {
                    String nL = request.getParameter("numeroLocali");
                    String nB = request.getParameter("numeroBagni");
                    String nC = request.getParameter("numeroCamere");
                    String met = request.getParameter("metratura");
                    result = gestoreRicerca.aggiornaAFiltroAppartamento(Integer.valueOf(nL), Integer.valueOf(nB), Integer.valueOf(nC), Double.valueOf(met));
                    System.out.println(gestoreRicerca.attualeToJSON());
                } else if (tipo.equalsIgnoreCase("Stanza")) {
                    String tS = request.getParameter("tipoStanza");
                    result = gestoreRicerca.aggiornaAFiltroStanza(tS);
                    System.out.println(gestoreRicerca.attualeToJSON());
                } else {
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
                System.out.println(" Filtro Aggiornato: " + result);
                String json = new Gson().toJson("" + result);
                System.out.println(json);
                out.write(json);
            } else if (action.equalsIgnoreCase("AjaxGetInfo")) {
                System.out.println("I'm in!!");
                response.setContentType("application/json");
                String json = new Gson().toJson(gestoreRicerca.geocodeCurrentCity());
                System.out.println(json);
                out.write(json);
            } else if (action.equalsIgnoreCase("Ricerca-geoCity")) {
                //System.out.println("I'm in!!");
                response.setContentType("application/json");
                String json = new Gson().toJson(gestoreRicerca.geocodeCurrentCity());
                //System.out.println(json);
                out.write(json);
            } else if (action.equalsIgnoreCase("Ricerca-getAnnunciFiltro")) {
                //System.out.println("I'm in!!");
                response.setContentType("application/json");
                JSONArray ris = gestoreRicerca.usaFiltroAttuale();
                System.out.println(ris.length());
                String json = ris.toString(); //new Gson().toJson(gestoreRicerca.usaFiltroAttuale());
                System.out.println(json);
                out.write(json);
            } else if (action.equalsIgnoreCase("Ricerca-getQuartieri")) {
                //System.out.println("I'm in!!");
                response.setContentType("application/json");
                String json = new Gson().toJson(gestoreRicerca.getQuartieriCittà());
                System.out.println(json);
                out.write(json);
            } else if (action.equalsIgnoreCase("Ricerca-getTipoStanza")) {
                //System.out.println("I'm in!!");
                response.setContentType("application/json");
                String json = new Gson().toJson(gestoreRicerca.getTipoStanza());
                System.out.println(json);
                out.write(json);
            } else if (action.equalsIgnoreCase("Ricerca-getImageAnnuncio")) {
                response.setContentType("image/jpeg");
                String[] paths = request.getParameterValues("srcImg");
                if (paths == null) {
                    System.out.println("NULLO!!");
                } else {
                    for (String path : paths) {
                        System.out.println(path);
                        String pathToWeb = getServletContext().getRealPath(File.separator);
                        File f = new File(pathToWeb + path.substring(2));
                        BufferedImage bi = ImageIO.read(f);
                        //OutputStream out = response.getOutputStream();
                        ImageIO.write(bi, "jpg", (ImageOutputStream) out);
                    }
                }

                out.close();
            } else if (action.equalsIgnoreCase("dettagliAnnuncio")) {
                System.out.println("Ecco");
                System.out.println(request.getParameter("data"));
                String urlToRedirect = "/haiHome-war/dettagliAnnuncio.jsp";
                response.setContentType("application/json");
                String json = new Gson().toJson(urlToRedirect);
                System.out.println(json);
                out.write(json);
                //response.sendRedirect("/haiHome-war/dettagliAnnuncio.jsp");
                //getServletContext().getRequestDispatcher("/dettagliAnnuncio.jsp").forward(request, response);
            } else if (action.equalsIgnoreCase("Ricerca-salvaFiltro")) {
                response.setContentType("text/html;charset=UTF-8");
                HttpSession session = request.getSession();
                if (session == null) {
                    out.println("non sei loggato!!");
                } else {
                    String user_type = (String) session.getAttribute("user-type");
                    if (user_type == null) {
                        out.println("non sei loggato!!");
                    } else if (user_type.equalsIgnoreCase("STUDENTE")) {
                        JSONObject Jstudente = (JSONObject) session.getAttribute("user-data");
                        try {
                            String id_studente = "" + Jstudente.get("ID");
                            gestoreRicerca.persistiFiltroAttuale(id_studente);
                            out.write("ok");
                        } catch (JSONException ex) {
                            out.write("errore lettura ID studente da sessione");
                            Logger.getLogger(ServletRicerca.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else {
                        //String json = new Gson().toJson("ok");
                        //System.out.println(json);
                        out.write("no studente");
                    }
                }
            } else if (action.equalsIgnoreCase("Ricerca-addServices")) {
                //System.out.println("I'm in!!");
                String jsonA = (String) request.getParameter("annuncio");
                System.out.println(jsonA);
                try {
                    JSONObject annuncio = new JSONObject(jsonA);
                    double lat = annuncio.getDouble("Lat");
                    double lng = annuncio.getDouble("Lng");

                    response.setContentType("application/json");
                    JSONArray superJ = gestoreRicerca.getSupermarketNearBy(lat, lng, 500.0);
                    String json = superJ.toString(); //new Gson().toJson();
                    System.out.println(json);
                    out.write(json);

                } catch (JSONException ex) {
                    String json = new Gson().toJson("ERRORE SUPERMARKET");
                    System.out.println(json);
                    out.write(json);
                    Logger.getLogger(ServletRicerca.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if (action.equalsIgnoreCase("Ricerca-deleteFiltro")) {
                response.setContentType("text/html;charset=UTF-8");
                HttpSession session = request.getSession();
                if (session == null) {
                    out.println("non sei loggato!!");
                } else {
                    String id_filtro = request.getParameter("filtroID");
                    JSONObject Jstudente = (JSONObject) session.getAttribute("user-data");
                    if (id_filtro == null) {
                        out.write("ERRORE");
                    } else {
                        try {
                            String id_studente = "" + Jstudente.get("ID");
                            if (gestoreRicerca.removeFiltro(id_filtro, id_studente)) {
                                out.write("OK");
                            } else {
                                out.write("ERRORE");
                            }
                        } catch (JSONException ex) {
                            Logger.getLogger(ServletRicerca.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
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
