/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import com.google.gson.Gson;
import ejb.GestoreImmaginiLocal;
import ejb.GestoreRicercaLocal;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
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

    @EJB
    private GestoreImmaginiLocal gestoreImmagini;

    private final double dist = 500d;

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

            System.out.println("ACTION: " + action);

            /*
            Richiesta del filtro attualmente salvato in gestoreRicerca
             */
            if (action.equalsIgnoreCase("Ricerca-getFiltro")) {

                response.setContentType("application/json");
                JSONObject filtro = gestoreRicerca.attualeToJSON();
                String json = "null";

                if (filtro != null) {
                    json = filtro.toString();
                }
                out.write(json);

            }/*
                Richiesta usata per impostare la città attuale, viene inviata una Stringa corrispondente alla
                città selezionata
             */ else if (action.equalsIgnoreCase("Ricerca-setCity")) {

                String city = request.getParameter("city");
                //selezionare la città corrisponde a generare un filtro con solo la città impostata
                boolean result = gestoreRicerca.selezionaCittà(city);
                //System.out.println("Selezione andata a buon fine? " + result);
                System.out.println(gestoreRicerca.attualeToJSON());
                out.write("" + result);

            }/*
             Richiesta che imposta in gestoreRicerca un particolare Filtro di Ricerca persistito
             */ else if (action.equalsIgnoreCase("Ricerca-setFiltro")) {
                String id = request.getParameter("ID");
                boolean esito = gestoreRicerca.loadFiltro(id);
                out.write("" + esito);

            }/*
             else if (action.equalsIgnoreCase("setCity")) {
                String city = request.getParameter("city");
                gestoreRicerca.selezionaCittà(city);
                //invia quartieri
                System.out.println("Filtro Creato:");
                System.out.println(gestoreRicerca.attualeToJSON());
                getServletContext().getRequestDispatcher("/search-page.jsp").forward(request, response);

            }*/
             /*
             Richiesta aggiornamento filtro di ricerca contenuto in gestoreRicerca
             */ else if (action.equalsIgnoreCase("search")) {

                String[] quartieri = request.getParameterValues("quartieri");

                String tipo = request.getParameter("tipo");
                String pricefrom = request.getParameter("pricefrom");

                double prezzo = 0;
                if (!pricefrom.equals("")) {
                    try {
                        prezzo = Double.valueOf(pricefrom);
                    } catch (NumberFormatException ex) {
                        System.err.println("Prezzo errato messo di default a 0");
                    }
                }

                boolean compCondomino = request.getParameter("compCondominio") != null;
                boolean compRiscaldamento = request.getParameter("compRiscaldamento") != null;
                boolean arredato = request.getParameter("arredato") != null;

                ArrayList<String> quartieriCittà = gestoreRicerca.getQuartieriCittà();
                ArrayList<String> quartieriSel = new ArrayList();
                if (quartieri == null) {
                    
                } else {
                    for (String selection : quartieri) {
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
                result = gestoreRicerca.creaFiltroDiRicerca(prezzo, quartieriSel, compCondomino, compRiscaldamento, arredato);

                if (tipo.equalsIgnoreCase("Appartamento")) {
                    String nL = request.getParameter("numeroLocali");
                    String nB = request.getParameter("numeroBagni");
                    String nC = request.getParameter("numeroCamere");
                    String met = request.getParameter("metratura");

                    int inL = 0;
                    if (!nL.equals("")) {
                        try {
                            inL = Integer.valueOf(nL);
                        } catch (NumberFormatException ex) {
                            System.err.println("numero Locali errato messo di default a 0");
                        }
                    }

                    int inB = 0;
                    if (!nB.equals("")) {
                        try {
                            inB = Integer.valueOf(nB);
                        } catch (NumberFormatException ex) {
                            System.err.println("numero Bagni errato messo di default a 0");
                        }
                    }

                    int inC = 0;
                    if (!nC.equals("")) {
                        try {
                            inC = Integer.valueOf(nC);
                        } catch (NumberFormatException ex) {
                            System.err.println("numero Camere errato messo di default a 0");
                        }
                    }

                    double imet = 0;
                    if (!met.equals("")) {
                        try {
                            imet = Double.valueOf(met);
                        } catch (NumberFormatException ex) {
                            System.err.println("metratura errata messa di default a 0");
                        }
                    }

                    result = gestoreRicerca.aggiornaAFiltroAppartamento(inL, inB, inC, imet);
                    System.out.println("Filtro Appartamento: " + gestoreRicerca.attualeToJSON());

                } else if (tipo.equalsIgnoreCase("Stanza")) {
                    String tS = request.getParameter("tipoStanza");
                    result = gestoreRicerca.aggiornaAFiltroStanza(tS);
                    System.out.println("Filtro Stanza: " + gestoreRicerca.attualeToJSON());
                } else {
                    System.out.println("Filtro Generale: " + gestoreRicerca.attualeToJSON());
                }

                response.setContentType("application/json");
                System.out.println(" Filtro Aggiornato: " + result);
                String json = new Gson().toJson("" + result);
                out.write(json);

            } /*else if (action.equalsIgnoreCase("AjaxGetInfo")) {
                System.out.println("I'm in!!");
                response.setContentType("application/json");
                String json = new Gson().toJson(gestoreRicerca.geocodeCurrentCity());
                System.out.println(json);
                out.write(json);

            }*/
             /*
             Richiesta per coordinate geocodifica della città selezionata in gestoreRicerca
             */ else if (action.equalsIgnoreCase("Ricerca-geoCity")) {
                response.setContentType("application/json");
                String json = new Gson().toJson(gestoreRicerca.geocodeCurrentCity());
                out.write(json);

            }/*
             Richiesta annunci che rispettano filtro di ricerca contenuto in gestoreRicerca
             */ else if (action.equalsIgnoreCase("Ricerca-getAnnunciFiltro")) {
                response.setContentType("application/json");
                JSONArray ris = gestoreRicerca.usaFiltroAttuale();
                System.out.println("n. Risultati: "+ris.length());
                String json = ris.toString();
                //System.out.println(json);
                out.write(json);

            }/*
             Richiesta quartieri città selezionata in gestoreRicerca
             */ else if (action.equalsIgnoreCase("Ricerca-getQuartieri")) {
                response.setContentType("application/json");
                String json = new Gson().toJson(gestoreRicerca.getQuartieriCittà());
                System.out.println(json);
                out.write(json);

            }/*
             Richiesta tipiStanza trattati da haihome
             */ else if (action.equalsIgnoreCase("Ricerca-getTipoStanza")) {
                response.setContentType("application/json");
                String json = new Gson().toJson(gestoreRicerca.getTipoStanza());
                //System.out.println(json);
                out.write(json);

            } /*
             Richiesta utilizzata per salvare filtro di ricerca caricato in gestoreRicerca relativamente
             a un particolare studente
             */else if (action.equalsIgnoreCase("Ricerca-salvaFiltro")) {
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
                            String id_studente = "" + Jstudente.get("id");
                            gestoreRicerca.persistiFiltroAttuale(id_studente);
                            out.write("ok");
                        } catch (JSONException ex) {
                            out.write("errore lettura ID studente da sessione");
                            Logger.getLogger(ServletRicerca.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else {
                        out.write("no studente");
                    }
                }

            } /*
             Richiesta supermercati in un raggio rad(=500) da un particolare annuncio passato come input
             */else if (action.equalsIgnoreCase("Ricerca-addSuperMarket")) {
                String jsonA = (String) request.getParameter("annuncio");
                System.out.println(jsonA);
                try {
                    JSONObject annuncio = new JSONObject(jsonA);
                    double lat = annuncio.getDouble("Lat");
                    double lng = annuncio.getDouble("Lng");

                    response.setContentType("application/json");
                    JSONArray superJ = gestoreRicerca.getSupermarketNearBy(lat, lng, dist);
                    String json = superJ.toString(); //new Gson().toJson();
                    //System.out.println(json);
                    out.write(json);

                } catch (JSONException ex) {
                    String json = new Gson().toJson("ERRORE SUPERMARKET");
                    //System.out.println(json);
                    out.write(json);
                    Logger.getLogger(ServletRicerca.class.getName()).log(Level.SEVERE, null, ex);
                }

            }/*
             Richiesta banche in un raggio rad(=500) da un particolare annuncio passato come input
             */ else if (action.equalsIgnoreCase("Ricerca-addBank")) {
                String jsonA = (String) request.getParameter("annuncio");
                System.out.println(jsonA);
                try {
                    JSONObject annuncio = new JSONObject(jsonA);
                    double lat = annuncio.getDouble("Lat");
                    double lng = annuncio.getDouble("Lng");

                    response.setContentType("application/json");
                    JSONArray superJ = gestoreRicerca.getBankNearBy(lat, lng, dist);
                    String json = superJ.toString(); 
                    //System.out.println(json);
                    out.write(json);

                } catch (JSONException ex) {
                    String json = new Gson().toJson("ERRORE BANK");
                    //System.out.println(json);
                    out.write(json);
                    Logger.getLogger(ServletRicerca.class.getName()).log(Level.SEVERE, null, ex);
                }

            } /*
             Richiesta fermate mezzi pubblici in un raggio rad(=500) da un particolare annuncio passato come input
             */else if (action.equalsIgnoreCase("Ricerca-addBus")) {
               
                String jsonA = (String) request.getParameter("annuncio");
                System.out.println(jsonA);
                try {
                    JSONObject annuncio = new JSONObject(jsonA);
                    double lat = annuncio.getDouble("Lat");
                    double lng = annuncio.getDouble("Lng");

                    response.setContentType("application/json");
                    JSONArray superJ = gestoreRicerca.getBusNearBy(lat, lng, dist);
                    String json = superJ.toString(); //new Gson().toJson();
                    //System.out.println(json);
                    out.write(json);

                } catch (JSONException ex) {
                    String json = new Gson().toJson("ERRORE BUS");
                    //System.out.println(json);
                    out.write(json);
                    Logger.getLogger(ServletRicerca.class.getName()).log(Level.SEVERE, null, ex);
                }

            }/*
             Richiesta di cancellazione di uno specifico filtro di ricerca 
             */ else if (action.equalsIgnoreCase("Ricerca-deleteFiltro")) {
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
                            String id_studente = "" + Jstudente.get("id");
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
            } /*
             Verifica studente loggato
             */else if (action.equalsIgnoreCase("Ricerca-loggatoStudente")) {
                response.setContentType("text/html;charset=UTF-8");
                HttpSession session = request.getSession();
                boolean result = false;
                String id = "";
                if (session != null) {
                    String user_type = (String) session.getAttribute("user-type");
                    if (user_type != null) {
                        if (user_type.equalsIgnoreCase("STUDENTE")) {
                            JSONObject Jstudente = (JSONObject) session.getAttribute("user-data");
                            try {
                                id = "" + Jstudente.get("id");
                                result = true;
                            } catch (JSONException ex) {
                                Logger.getLogger(ServletRicerca.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }

                out.write("" + id);
            } /*
             Verfica locatore loggato
             */else if (action.equalsIgnoreCase("Ricerca-loggatoLocatore")) {
                response.setContentType("text/html;charset=UTF-8");
                HttpSession session = request.getSession();
                boolean result = false;
                String id = "";
                if (session != null) {
                    String user_type = (String) session.getAttribute("user-type");
                    if (user_type != null) {
                        if (user_type.equalsIgnoreCase("LOCATORE")) {
                            JSONObject Jstudente = (JSONObject) session.getAttribute("user-data");
                            try {
                                id = "" + Jstudente.get("id");
                                result = true;
                            } catch (JSONException ex) {
                                Logger.getLogger(ServletRicerca.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }

                out.write("" + id);
            } /*
             Carica particolare immagine definita dai parametri di input
             */else if (action.equalsIgnoreCase("Ricerca-getImage")) {
                String url = request.getParameter("url");
                String type = request.getParameter("type");
                String image = "";
                image = gestoreImmagini.getImage(url, type);
                if (image == null) {
                    out.write("");
                } else {
                    out.write(image);
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
