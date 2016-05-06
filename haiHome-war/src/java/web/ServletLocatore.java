/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import ejb.GestoreAnnuncioLocal;
import ejb.GestoreLocatoreLocal;
import entity.Annuncio;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;
import verifytoken.Verify;

/**
 *
 * @author gianp_000
 */
public class ServletLocatore extends HttpServlet {

    @EJB
    private GestoreLocatoreLocal gestoreLocatore;

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
        //response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("action");
            HttpSession session = request.getSession();

            System.out.println("--Servlet Locatore");

            if (action.equalsIgnoreCase("signup-locatore")) {
                /* registrazione */
                String name = request.getParameter("user-name").trim(),
                        surname = request.getParameter("user-surname").trim(),
                        email = request.getParameter("user-email").trim(),
                        phone = request.getParameter("user-phone").trim(),
                        pwd = request.getParameter("user-pw"),
                        pwd2 = request.getParameter("user-pw-repeat"),
                        op_result = "OK";

                /* controllo correttezza email, telefono, password */
                if (pwd.equals(pwd2)) {
                    // email non presente
                    if (!gestoreLocatore.checkLocatore(email)) {
                        gestoreLocatore.aggiungiLocatore(email, pwd, name, surname, phone, "https://upload.wikimedia.org/wikipedia/commons/2/23/Pino_Scotto_ceroanKio_2009_6.jpg");
                    } //email presente ma associata a un account social (password null)
                    else if (gestoreLocatore.getLocatore().getPassword() == null) {
                        gestoreLocatore.modificaPassword(pwd);
                    } else {
                        op_result = "email address <" + email + "> is already registered";
                    }
                } else {
                    op_result = "password mismatch";
                }

                response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
                response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
                out.write(op_result);

            } else if (action.equalsIgnoreCase("login-locatore")) {
                String email = request.getParameter("user-email").trim(),
                        pwd = request.getParameter("user-pw"),
                        op_result = "OK"; // ottimismo, gianni!
                // controlli sull'input ??

                if (gestoreLocatore.checkLocatore(email, pwd)) {
                    instantiate_session(session, "");
                } else {
                    op_result = "credenziali non corrette";
                }

                response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
                response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
                out.write(op_result);

            } else if (action.equalsIgnoreCase("loginFacebookLocatore")) {
                String email = request.getParameter("mailUser");
                String foto = request.getParameter("profilo");
                // String nome = dataUser[0];String cognome = dataUser[1];
                String[] dataUser = request.getParameter("userData").split(",");

                if (!gestoreLocatore.checkLocatore(email)) {
                    gestoreLocatore.aggiungiLocatore(email, null, dataUser[0], dataUser[1], "", foto);
                } // si potrebbe aggiornare l'immagine del profilo, possibile che sia cambiata...
                else {
                    gestoreLocatore.modificaAvatarByURL(foto);
                }

                instantiate_session(session, "fb");
                getServletContext().getRequestDispatcher("/locatore-profile.jsp").forward(request, response);

            } else if (action.equalsIgnoreCase("login-googleplus-locatore")) {
                String[] verify = Verify.getUserCredentials(
                        request.getParameter("id_token"),
                        request.getParameter("access_token"));

                if (verify != null) {
                    String name = request.getParameter("name");
                    String surname = request.getParameter("surname");
                    //            String email = verify[1];
                    String email = request.getParameter("email");
                    String url_img = request.getParameter("url-profile-img");// + "?sz=200";

                    String img = request.getParameter("url-profile-img").split("sz=")[0] + "sz=200";

                    if (!gestoreLocatore.checkLocatore(email)) {
                        gestoreLocatore.aggiungiLocatore(email, null, name, surname, "", img);
                    } else {
                        gestoreLocatore.modificaAvatarByURL(img);
                    }

                    instantiate_session(session, "g+");
                    getServletContext().getRequestDispatcher("/locatore-profile.jsp").forward(request, response);

                } else {
                    out.println("errore nell'autenticazione");
                }

            } else if (action.equalsIgnoreCase("locatore-getAnnunci")) {
                //    int requested_page = Integer.parseInt(request.getParameter("page")) - 1;
                //    int NUM_ANNUNCI_X_PAGE = Integer.parseInt(request.getParameter("axp"));
                //   String bottone;

                //     bottone = "";
                //       System.out.println("#PAGINA=" + NUM_ANNUNCI_X_PAGE + "req=" + requested_page);
                //     String html = getPage(gestoreLocatore.getAnnunciVisibili(), NUM_ANNUNCI_X_PAGE, requested_page);
                String html = getPage(gestoreLocatore.getAnnunciVisibili());

                response.setContentType("text/html");
                response.setCharacterEncoding("UTF-8");
                out.write(html);

            } else if (action.equalsIgnoreCase("locatore-getArchivioAnnunci")) {
                //     int requested_page = Integer.parseInt(request.getParameter("page")) - 1;
                //     int NUM_ANNUNCI_X_PAGE = Integer.parseInt(request.getParameter("axp"));

                //         String html = getPage(gestoreLocatore.getAnnunciArchiviati(), NUM_ANNUNCI_X_PAGE, requested_page);
                String html = getPage(gestoreLocatore.getAnnunciArchiviati());

                response.setContentType("text/html");
                response.setCharacterEncoding("UTF-8");
                out.write(html);

            } else if (action.equalsIgnoreCase("locatore-getAnnunciOscurati")) {
                //       int requested_page = Integer.parseInt(request.getParameter("page")) - 1;
                //       int NUM_ANNUNCI_X_PAGE = Integer.parseInt(request.getParameter("axp"));

                //      String html = getPage(gestoreLocatore.getAnnunciOscurati(), NUM_ANNUNCI_X_PAGE, requested_page);
                String html = getPage(gestoreLocatore.getAnnunciOscurati());

                response.setContentType("text/html");
                response.setCharacterEncoding("UTF-8");
                out.write(html);

            } else if (action.equalsIgnoreCase("locatore-edit-info")) {
                String field_name = request.getParameter("field-name");
                String field_value = request.getParameter("field-value");
                String error = "";
                JSONObject jsonresult = new JSONObject();
                boolean res = true;

                if (field_name.equalsIgnoreCase("password")) {
                    if (gestoreLocatore.getLocatore().getPassword().equals(field_value)) {
                        String new_password = request.getParameter("new-pw");
                        /* Regex complessità password?? */

                        if (new_password.length() >= 3) {
                            gestoreLocatore.modificaPassword(new_password);
                        } else {
                            res = false;
                            error = "PASSWORD TOO SHORT";
                        }
                    } else {
                        res = false;
                        error = "OLD PASSWORD INCORRECT";
                    }
                } else if (field_name.equalsIgnoreCase("telefono")) {
                    Pattern pattern = Pattern.compile("^\\+?[0-9]{3}-?[0-9]{6,12}$");
                    Matcher matcher = pattern.matcher(field_value.replace(" ", ""));
                    res = matcher.matches();

                    if (res) {
                        gestoreLocatore.modificaTelefono(field_value);
                        System.out.println("YEAH");
                    } else {
                        error = "REGEX MISMATCH";
                    }
                } else if (field_name.equalsIgnoreCase("descrizione")) {
                    gestoreLocatore.modificaDescrizione(field_value);
                }
                //refresh sessione
                update_session(session);
//                session.setAttribute("user-data", this.gestoreLocatore.toJSON());

                try {
                    jsonresult.accumulate("id", field_name);
                    jsonresult.accumulate("result", res);
                    jsonresult.accumulate("error", error);

                } catch (JSONException ex) {
                    Logger.getLogger(ServletLocatore.class.getName()).log(Level.SEVERE, null, ex);
                }

                response.setContentType("application/json");
                out.write(jsonresult.toString());

            } else if (action.equalsIgnoreCase("locatore-delete-annuncio")) {
                long oid = Long.parseLong(request.getParameter("oid"));
                boolean res = false;

                System.out.println("Richiesta cancellazione annuncio " + oid);

                Annuncio annuncio = gestoreAnnuncio.predniAnnuncio(oid); //dislessia by jack

                if (gestoreLocatore.checkAnnuncio(annuncio)) {
                    gestoreLocatore.removeAnnuncio(annuncio);
                    res = gestoreAnnuncio.eliminaAnnuncio(oid);
                    System.out.println("Annuncio " + oid + "eliminato?" + res);
                } else {
                    System.out.println("Questo non dovrebbe succedere ma non si sa mai");
                }

                update_session(session);

                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                out.write(res ? "ok" : "fail");

            } else if (action.equalsIgnoreCase("locatore-archivia-annuncio")) {
                System.out.println("PRESUNTO OID = " + request.getParameter("oid"));
                long oid = Long.parseLong(request.getParameter("oid"));
                System.out.println("ARCHIVIAZIONEEE annuncio " + oid);
                /*
                System.out.println("annuncio prima dell'operazione...");
                for (Annuncio a : gestoreLocatore.getAnnunci()) {
                    if (a.getId() == oid) {
                        System.out.println(a.toJSON().toString());
                    }
                }
                 */
                boolean res = gestoreAnnuncio.archiviaAnnuncio(oid, true);

                if (res) {
                    Annuncio a = gestoreAnnuncio.getAnnuncioByID(oid);
                    gestoreLocatore.updateAnnuncio(oid, a);
                }
                /*
                System.out.println("annuncio dopo dell'operazione...");
                for (Annuncio a : gestoreLocatore.getAnnunci()) {
                    if (a.getId() == oid) {
                        System.out.println(a.toJSON().toString());
                    }
                }*/

                System.out.println(res
                        ? "Archiviazione annuncio " + oid + " completata con successo"
                        : "Archiviazione annuncio " + oid + " fallita");

                update_session(session);

                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                out.write(res ? "ok" : "errore");

            } else if (action.equalsIgnoreCase("locatore-pubblica-annuncio")) {
                System.out.println("PRESUNTO OID = " + request.getParameter("oid"));
                long oid = Long.parseLong(request.getParameter("oid"));
                System.out.println("PUBBLICAZIONEEEE annuncio " + oid);
                /*
                System.out.println("annuncio prima dell'operazione...");
                for (Annuncio a : gestoreLocatore.getAnnunci()) {
                    if (a.getId() == oid) {
                        System.out.println(a.toJSON().toString());
                    }
                }
                 */
                boolean res = gestoreAnnuncio.archiviaAnnuncio(oid, false);

                if (res) {
                    Annuncio a = gestoreAnnuncio.getAnnuncioByID(oid);
                    gestoreLocatore.updateAnnuncio(oid, a);
                }

                System.out.println(res
                        ? "Pubblicazione annuncio " + oid + " completata con successo"
                        : "Pubblicazione annuncio " + oid + " fallita");
                /*
                System.out.println("annuncio dopo dell'operazione...");
                for (Annuncio a : gestoreLocatore.getAnnunci()) {
                    if (a.getId() == oid) {
                        System.out.println(a.toJSON().toString());
                    }
                }*/

                update_session(session);

                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                out.write(res ? "ok" : "errore");

            } else if (action.equalsIgnoreCase("locatore-get-session")) {
                JSONObject jsonsession = new JSONObject();

                try {
//                    jsonsession.accumulate("user-access", "loc"); 
                    jsonsession.accumulate("user_access", session.getAttribute("user-access"));
                    jsonsession.accumulate("user_type", session.getAttribute("user-type"));
                    jsonsession.accumulate("user_data", session.getAttribute("user-data"));
                    jsonsession.accumulate("num_annunci", session.getAttribute("num-annunci"));
                    jsonsession.accumulate("num_visibili", session.getAttribute("num-visibili"));
                    jsonsession.accumulate("num_archiviati", session.getAttribute("num-archiviati"));
                    jsonsession.accumulate("num_oscurati", session.getAttribute("num-oscurati"));
                } catch (JSONException ex) {
                    Logger.getLogger(ServletLocatore.class.getName()).log(Level.SEVERE, null, ex);
                }

                response.setContentType("application/json");
                System.out.println("LOCATORE GET SESSION: " + jsonsession.toString());
                out.write(jsonsession.toString());
            }
        }
    }

    private void instantiate_session(HttpSession session, String user_access) {
        session.setAttribute("user-access", user_access);
        session.setAttribute("user-type", "locatore");
        session.setAttribute("user-data", this.gestoreLocatore.toJSON());
        session.setAttribute("num-annunci", this.gestoreLocatore.getAnnunci().size());
        session.setAttribute("num-visibili", this.gestoreLocatore.getAnnunciVisibili().size());
        session.setAttribute("num-archiviati", this.gestoreLocatore.getAnnunciArchiviati().size());
        session.setAttribute("num-oscurati", this.gestoreLocatore.getAnnunciOscurati().size());
    }

    private void update_session(HttpSession session) {
        session.setAttribute("user-data", this.gestoreLocatore.toJSON());
        session.setAttribute("num-annunci", this.gestoreLocatore.getAnnunci().size());
        session.setAttribute("num-visibili", this.gestoreLocatore.getAnnunciVisibili().size());
        session.setAttribute("num-archiviati", this.gestoreLocatore.getAnnunciArchiviati().size());
        session.setAttribute("num-oscurati", this.gestoreLocatore.getAnnunciOscurati().size());
    }

    private String getPage(List<Annuncio> l) {
        String html = "";

        if (l.size() > 0) {
            for (Annuncio a : l) {
                html += getDivAnnuncio(a);
            }
        } else {
            html = "No results";
        }

        return html;
    }

    private String getPage(List<Annuncio> l, int psize, int requested_page) {
        int first = requested_page * psize;
        int offset = Math.min(psize, l.size() - first);
        String html = "";

        if (offset > 0) {
            /*
            System.out.println("********************************");
            System.out.println("l.size()=" + l.size() + ",first=" + first);
            System.out.println("offset=" + offset);
            System.out.println("sublist(" + first + ", " + (first + offset) + ")...");
            System.out.println("totale_dati=" + l.size());
            System.out.println("...");*/

            for (Annuncio a : l.subList(first, first + offset)) {
                html += getDivAnnuncio(a);
            }
        }

        return html;
    }

    private String getDivAnnuncio(Annuncio a) {
        String html = "";
        Long oid = a.getId();

        html += "<div id='ann-" + oid + "' class='annuncio'>"; //CONTAINER 
        html += "<div class='panel panel-default'>"; // PANEL
        html += "<div class='panel-heading'>"; // PANEL HEADING
        html += "<span class='nome-annuncio'>Annuncio " + oid + "</span>";
        html += "<div class='dropdown link-annuncio'>"; //DROPZONE - HEADER
        html += "<a class='btn btn-link dropdown-toggle' type='button' data-toggle='dropdown'>";
        html += "<span class='glyphicon glyphicon-menu-down'></span>";
        html += "</a>";
        html += "<ul class='dropdown-menu'>"; //INIZIO DROPZONE - OPZIONI
        html += "<li><a id='edit-ann" + oid + "' class='edit-annuncio' href='#0'>Modifica</a></li>";
        html += "<li>" + getHTMLButtonAnnuncio(oid, a.isArchiviato()) + "</li>"; // pubblica / archivia
        html += "<li><a id='delete-ann" + oid + "' class='delete-annuncio' href='#0'>Elimina</a></li>";
        html += "</ul>";
        html += "</div>"; //FINE DROPZONE
        html += "</div>"; //panel-heading
        html += "<div class='panel-body'>"; // PANEL BODY
        html += "<div>Proprietario: " + a.getLocatore().getEmail() + "</div>";
        html += "<div>Indirizzo: " + a.getIndirizzo() + "</div>";
        html += "<div>Descrizione: " + a.getDescrizione() + "</div>";
        html += "</div>"; //FINE PANEL BODY
        html += "</div>"; //FINE PANEL
        html += "</div>"; //FINE CONTAINER ANNUNCIO

        // var html = "<div id=\"annuncio-" + k + "\" OnClick=send_Annuncio(" + k + ") style=\"cursor:pointer\">"; //1
        /*
        html += "<div class=\"panel panel-default div_snippet\">"; //2
        html += "<div class='panel-heading'>"; //3
        html += create_carousel(annuncio);
        html += "</div>"; //chiusura testa del pannello
        html += "<div class=\"panel-body snip\">";
        html += create_info_annuncio(annuncio);
        html += "</div>"; //3
        html += "</div>"; //2
        html += "</div>"; //1 */
        // div_html += "<span class='dati-annuncio'><p>" + a.toJSON().toString() + "</p></span>";
        return html;
    }

    private String getHTMLButtonAnnuncio(Long oid, boolean is_archiviato) {
        String id = "id='select-ann-" + oid + "'";
        String value = is_archiviato ? "Pubblica" : "Archivia";
        String css_class = " class='" + (is_archiviato ? "pubblica-annuncio" : "archivia-annuncio") + "' ";
        return "<a " + id + css_class + " href='#0'>" + value + "</a>";
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
