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
import java.security.MessageDigest;
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

  //      gestoreLocatore.reloadLocatore();

        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("action");
            HttpSession session = request.getSession();

            System.out.println("> Servlet Locatore");
            System.out.println("Action '" + action + "'");
            
            if (action.equalsIgnoreCase("signup-locatore")) {
                /* registrazione */
                String name = request.getParameter("user-name").trim(),
                        surname = request.getParameter("user-surname").trim(),
                        email = request.getParameter("user-email").trim(),
                        phone = request.getParameter("user-phone").trim(),
                        pwd = request.getParameter("user-pw"),
                        pwd2 = request.getParameter("user-pw-repeat"),
                        res = "OK";

           //      String DigestUtils.shaHex(String data) 
               
                Pattern email_pattern = Pattern.compile("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$");
                Pattern phone_pattern = Pattern.compile("^\\+?[0-9]{3}-?[0-9]{6,12}$");
                
                if (!email_pattern.matcher(email).matches()) {
                    res = "L'email è in un formato non riconosciuto";
                } 
                else if (!phone_pattern.matcher(phone).matches()) {
                    res = "Numero di telefono non valido";
                }
                else if (pwd.length() < 3) {
                    res = "Password troppo corta";
                }
                else if (!pwd.equals(pwd2)) {
                    res = "Le due password non coincidono"; 
                }
                
                if (res.equalsIgnoreCase("ok")) {
                    // email non presente
                    if (!gestoreLocatore.checkLocatore(email)) {
                        gestoreLocatore.aggiungiLocatore(email, pwd, name, surname, phone, "https://www.keita-gaming.com/assets/profile/default-avatar-c5d8ec086224cb6fc4e395f4ba3018c2.jpg");
                    } //email presente ma associata a un account social (password null)
                    else if (gestoreLocatore.getLocatore().getPassword() == null) {
                        gestoreLocatore.modificaPassword(pwd);
                    } else {
                        res = "L'email " + email + " e' associata ad un altro account"; 
                    }
                }

                response.setContentType("text/html"); 
                response.setCharacterEncoding("UTF-8"); 
                out.write(res);

            } else if (action.equalsIgnoreCase("login-locatore")) {
                String email = request.getParameter("user-email").trim(),
                        pwd = request.getParameter("user-pw"),
                        op_result = "OK"; // ottimismo, gianni!

                if (gestoreLocatore.checkLocatore(email, pwd)) {
                    instantiate_session(session, "");
                } else {
                    op_result = "credenziali non corrette";
                }

                response.setContentType("text/plain");  
                response.setCharacterEncoding("UTF-8"); 
                out.write(op_result);

            } else if (action.equalsIgnoreCase("loginFacebookLocatore")) {
                String email = request.getParameter("mailUser");
                String foto = request.getParameter("profilo");
                String[] dataUser = request.getParameter("userData").split(",");

                if (!gestoreLocatore.checkLocatore(email)) {
                    gestoreLocatore.aggiungiLocatore(email, null, dataUser[0], dataUser[1], "", foto);
                }
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
                    String email = request.getParameter("email");
                    String img = request.getParameter("url-profile-img").split("sz=")[0] + "sz=200";

                    if (!gestoreLocatore.checkLocatore(email)) {
                        gestoreLocatore.aggiungiLocatore(email, null, name, surname, "", img);
                    } else {
                        gestoreLocatore.modificaAvatarByURL(img);
                    }

                    instantiate_session(session, "g+");
                    getServletContext().getRequestDispatcher("/locatore-profile.jsp").forward(request, response);
                } else {
                    out.println("Errore nell'autenticazione");
                }

            } else if (action.equalsIgnoreCase("locatore-getAnnunci")) {
                gestoreLocatore.reloadLocatore();
                
                String html = getPage(gestoreLocatore.getAnnunciVisibili());

                response.setContentType("text/html");
                response.setCharacterEncoding("UTF-8");
                out.write(html);

            } else if (action.equalsIgnoreCase("locatore-getArchivioAnnunci")) {
                gestoreLocatore.reloadLocatore();
                
                String html = getPage(gestoreLocatore.getAnnunciArchiviati());

                response.setContentType("text/html");
                response.setCharacterEncoding("UTF-8");
                out.write(html);

            } else if (action.equalsIgnoreCase("locatore-getAnnunciOscurati")) {
                gestoreLocatore.reloadLocatore();
                
                String html = getPage(gestoreLocatore.getAnnunciOscurati());

                response.setContentType("text/html");
                response.setCharacterEncoding("UTF-8");
                out.write(html);

            } else if (action.equalsIgnoreCase("locatore-edit-info")) {
                gestoreLocatore.reloadLocatore();
                
                String field_name = request.getParameter("field-name");
                String field_value = request.getParameter("field-value");
                String error = "";
                JSONObject jsonresult = new JSONObject();
                boolean res = true;

                if (field_name.equalsIgnoreCase("password")) {
                    if (gestoreLocatore.getLocatore().getPassword().equals(field_value)) {
                        String new_password = request.getParameter("new-pw");

                        if (new_password.length() >= 3) {
                            gestoreLocatore.modificaPassword(new_password);
                        } else {
                            res = false;
                            error = "Password troppo corta";
                        }
                    } else {
                        res = false;
                        error = "Vecchia password errata";
                    }
                } else if (field_name.equalsIgnoreCase("telefono")) {
                    String phone_number = field_value.replace(" ", ""); 
                    Pattern pattern = Pattern.compile("^\\+?[0-9]{3}-?[0-9]{6,12}$");
                    Matcher matcher = pattern.matcher(phone_number);

                    /* L'utente può non indicare un numero di telefono */
                    if (phone_number.equals("") || matcher.matches()) {
                        gestoreLocatore.modificaTelefono(field_value);
                    } else {
                        error = "REGEX MISMATCH - " + phone_number + " non e' un numero di telefono valido";
                        res = false; 
                    }
                } else if (field_name.equalsIgnoreCase("descrizione")) {
                    gestoreLocatore.modificaDescrizione(field_value);
                }
                
                update_session(session); //refresh sessione

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
                gestoreLocatore.reloadLocatore();
                
                long oid = Long.parseLong(request.getParameter("oid"));
                boolean res = false;

                System.out.println("Richiesta cancellazione annuncio " + oid);

                Annuncio annuncio = gestoreAnnuncio.getAnnuncioByID(oid); 
                
                if (gestoreLocatore.checkAnnuncio(annuncio)) {
                    gestoreLocatore.removeAnnuncio(annuncio);
                    res = gestoreAnnuncio.eliminaAnnuncio(oid);
                    System.out.println("Annuncio " + oid + "eliminato?" + res);
                } 

                update_session(session);

                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                out.write(res ? "ok" : "fail");

            } else if (action.equalsIgnoreCase("locatore-archivia-annuncio")) {
                gestoreLocatore.reloadLocatore();
                
                long oid = Long.parseLong(request.getParameter("oid"));
                boolean res = gestoreAnnuncio.archiviaAnnuncio(oid, true);

                if (res) {
                    Annuncio a = gestoreAnnuncio.getAnnuncioByID(oid);
                    gestoreLocatore.updateAnnuncio(oid, a);
                }
                System.out.println("ARCHIVIAZIONE annuncio " + oid + "..." + res);

                update_session(session);

                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                out.write(res ? "ok" : "errore");

            } else if (action.equalsIgnoreCase("locatore-pubblica-annuncio")) {
                gestoreLocatore.reloadLocatore();
                
                long oid = Long.parseLong(request.getParameter("oid"));
                boolean res = gestoreAnnuncio.archiviaAnnuncio(oid, false);
               
                if (res) {
                    Annuncio a = gestoreAnnuncio.getAnnuncioByID(oid);
                    gestoreLocatore.updateAnnuncio(oid, a);
                }

                System.out.println(res ? "ok" : "errore"); 
                update_session(session);

                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                out.write(res ? "ok" : "errore");

            } else if (action.equalsIgnoreCase("locatore-get-session")) {
                JSONObject jsonsession = new JSONObject();
                
                gestoreLocatore.reloadLocatore();
                update_session(session);
                        
                try {
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
                out.write(jsonsession.toString());

            } else if (action.equalsIgnoreCase("locatore-blocca-by-id")) {
                long oid = Long.parseLong(request.getParameter("oid"));
                boolean bloccato = Boolean.parseBoolean(request.getParameter("bloccatoflag"));
                
                gestoreLocatore.reloadLocatore();
                
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                out.write(String.valueOf(gestoreLocatore.bloccaLocatore(oid, bloccato)));
            }
            else if (action.equalsIgnoreCase("locatore-archivia-stanza")) {
                long oid = Long.parseLong(request.getParameter("oid")), 
                       oidStanza = Long.parseLong(request.getParameter("oidStanza")); 
                
                boolean res = gestoreAnnuncio.archiviaStanza(oid, oidStanza, true);
                System.out.println("Risultato archiviazione stanza: " + res);
                
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                out.write(res ? "ok" : "errore"); 
            }
            else if (action.equalsIgnoreCase("locatore-pubblica-stanza")) {
                long oid = Long.parseLong(request.getParameter("oid")), 
                       oidStanza = Long.parseLong(request.getParameter("oidStanza")); 
                
                boolean res = gestoreAnnuncio.archiviaStanza(oid, oidStanza, false);
                System.out.println("Risultato pubblicazione stanza: " + res); 
                
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                out.write(res ? "ok" : "errore"); 
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
        String type = (String) session.getAttribute("user-type");
        
        if (type != null && type.equals("locatore")) {
            session.setAttribute("user-data", this.gestoreLocatore.toJSON());
            session.setAttribute("num-annunci", this.gestoreLocatore.getAnnunci().size());
            session.setAttribute("num-visibili", this.gestoreLocatore.getAnnunciVisibili().size());
            session.setAttribute("num-archiviati", this.gestoreLocatore.getAnnunciArchiviati().size());
            session.setAttribute("num-oscurati", this.gestoreLocatore.getAnnunciOscurati().size());
        }
    }

    private String getPage(List<Annuncio> l) {
        String html = "";

        if (l.size() > 0) {
            for (Annuncio a : l) {
                html += getDivAnnuncio(a, gestoreLocatore.getLocatore().isBloccato());
            }
        } else {
            html = "Non sono presenti annunci al momento!";
        }

        return html;
    }

    private String getPage(List<Annuncio> l, int psize, int requested_page) {
        int first = requested_page * psize;
        int offset = Math.min(psize, l.size() - first);
        String html = "";

        if (offset > 0) {
            for (Annuncio a : l.subList(first, first + offset)) {
                html += getDivAnnuncio(a, gestoreLocatore.getLocatore().isBloccato());
            }
        }

        return html;
    }

    private String getDivAnnuncio(Annuncio a, boolean locatore_bloccato) {
        String html = "";
        Long oid = a.getId();
        String disabled = locatore_bloccato ? " disabled " : "";
        JSONObject json = a.toJSON(); 
        
        String indirizzo = null, metratura = null, 
               dataInizioAffitto = null, quartiere = null, 
               prezzo = null, num_locali = null, arredato = null, 
               tipo_annuncio = (a.isAtomico() ? "Appartamento" : "Stanze"), 
               spese_comprese = ""; 
        boolean is_appartamento = tipo_annuncio.equalsIgnoreCase("appartamento"); 
        
        try {
            indirizzo = json.getString("Indirizzo"); 
            metratura = String.valueOf(json.getDouble("Metratura")) + " m<sup>2</sup>"; 
            dataInizioAffitto = json.getString("DataInizioAffitto"); 
            quartiere = json.getString("Quartiere"); 
            prezzo = String.valueOf(json.getDouble("Prezzo")) + " &euro;";       
            num_locali = String.valueOf(json.getInt("NumeroLocali")); 
            arredato = (a.isArredato() ? "Arredato" : "Non arredato");
            
            /* L'operatore ternario è l'invenzione del secolo */
            spese_comprese = (a.isCompresoCondominio() ? "condominio" : "") +
                    (a.isCompresoCondominio() && a.isCompresoRiscaldamento() ? ", " : "") +
                                (a.isCompresoRiscaldamento() ? "riscaldamento" : ""); 
            if (spese_comprese.equalsIgnoreCase("")) {
                spese_comprese = "nessuna";
            }
        }
        catch (JSONException ex) {
            Logger.getLogger(ServletLocatore.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        html += "<div id='ann-" + oid + "' class='annuncio row'>"; //CONTAINER 
        html += "<div class='panel panel-default'>"; // PANEL
        html += "<div class='panel-heading'>"; // PANEL HEADING
        html += "<span class='nome-annuncio'>"; 
        html += "<a id='open-" + oid + "' class='annuncio-view-details' href='#0'>Annuncio " + tipo_annuncio + " in " + indirizzo + "</a>";
        html += "</span>";
        
        if (!a.isOscurato()) { //annuncio modificabile solo se non oscurato 
            html += "<div class='dropdown link-annuncio'>"; //DROPDOWN MENU - HEADER
            html += "<a class='btn btn-link dropdown-toggle " + disabled + "' type='button' data-toggle='dropdown'>";
            html += "Gestisci annuncio <span class='caret'></span>";
            html += "</a>";
            html += "<ul class='dropdown-menu'>"; //DROPDOWN MENU - OPZIONI
            html += "<li><a id='edit-ann" + oid + "' class='edit-annuncio' href='#0'>Modifica</a></li>";
            html += "<li>" + getHTMLButtonAnnuncio(oid, a.isArchiviato(), locatore_bloccato) + "</li>"; // pubblica / archivia
            html += "<li><a id='delete-ann" + oid + "' class='delete-annuncio' href='#0'>Elimina</a></li>";
            html += "</ul>";
            html += "</div>"; //FINE DROPDOWN MENU
        }
        
        html += "</div>"; //panel-heading
        html += "<div class='panel-body'>"; // PANEL BODY 
        
        html += "<div class='col-md-6'>"; // COLONNA 1 
        html += "<p class='text-muted'><b>Indirizzo:</b> " + indirizzo + "</p>";
        html += "<p class='text-muted'><b>Quartiere:</b> " + quartiere + "</p>";
        html += "<p class='text-muted'><b>In affitto dal:</b> " + dataInizioAffitto + "</p>";
        html += "<p class='text-muted'><b>" + arredato + "</b></p>";
        html += "</div>";
        
        html += "<div class='col-md-6'>"; // COLONNA 2 
        html += "<p class='text-muted'><b>Metratura appartamento:</b> " + metratura + "</p>";
        html += "<p class='text-muted'><b>Numero locali:</b> " + num_locali + "</p>";
        if (is_appartamento) {
            html += "<p class='text-muted'><b>Prezzo:</b> " + prezzo + "</p>"; 
            html += "<p class='text-muted'><b>Spese comprese:</b> " + spese_comprese + "</p>";
        }
        html += "</div>";
        
        html += "</div>"; //FINE PANEL BODY
        html += "</div>"; //FINE PANEL
        html += "</div>"; //FINE CONTAINER ANNUNCIO
        
        return html;
    }

    private String getHTMLButtonAnnuncio(Long oid, boolean is_archiviato, boolean locatore_bloccato) {
        String id = "id='select-ann-" + oid + "'";
        String css_class = " class='" + (is_archiviato ? "pubblica" : "archivia") + "-annuncio' ";
        String value = is_archiviato ? "Pubblica" : "Archivia";
        
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
