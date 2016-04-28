/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import ejb.GestoreLocatoreLocal;
import entity.Annuncio;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
                response.getWriter().write(op_result);

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
                response.getWriter().write(op_result);

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

                    System.out.println("Sono qui: " + img);

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

            } else if (action.equalsIgnoreCase("locatore-edit-profile")) {
                String old_pwd = request.getParameter("old-pwd"),
                        new_pwd = request.getParameter("new-pwd"),
                        phone = request.getParameter("phone"),
                        descrizione = request.getParameter("description");
                boolean res = true;

                if (!old_pwd.equalsIgnoreCase("")) {
                    res = gestoreLocatore.modificaPassword(old_pwd, new_pwd);
                }

                gestoreLocatore.modificaInfoProfilo(phone, descrizione);
                //refresh sessione
                session.setAttribute("user-data", this.gestoreLocatore.toJSON());

                if (gestoreLocatore.getAnnunci() != null) {
                    System.out.println("ZAN ZAN ZAN!!!");
                    System.out.println("Numero annunci: " + gestoreLocatore.getAnnunci().size());
                    for (Annuncio a : gestoreLocatore.getAnnunci()) {
                        System.out.println(a.toJSON().toString());
                    }
                } else {
                    System.out.println("NULLLLLL");
                }

                response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
                response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
                response.getWriter().write(res ? "ok" : "no");

            } else if (action.equalsIgnoreCase("locatore-getAnnunci")) {
                int requested_page = Integer.parseInt(request.getParameter("page")) - 1;
                int NUM_ANNUNCI_X_PAGE = Integer.parseInt(request.getParameter("axp"));
                String bottone;

                bottone = "";
                System.out.println("#PAGINA=" + NUM_ANNUNCI_X_PAGE + "req=" + requested_page);
                String html = getPage(gestoreLocatore.getAnnunciVisibili(), NUM_ANNUNCI_X_PAGE, requested_page);

                response.setContentType("text/html");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(html);

            } else if (action.equalsIgnoreCase("locatore-getArchivioAnnunci")) {
                int requested_page = Integer.parseInt(request.getParameter("page")) - 1;
                int NUM_ANNUNCI_X_PAGE = Integer.parseInt(request.getParameter("axp"));

                String html = getPage(gestoreLocatore.getAnnunciArchiviati(), NUM_ANNUNCI_X_PAGE, requested_page);

                response.setContentType("text/html");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(html);
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
    }

    private String getPage(List<Annuncio> l, int psize, int requested_page) {
        int first = requested_page * psize;
        int offset = Math.min(psize, l.size() - first);
        String html = "";

        if (offset > 0) {
            System.out.println("********************************");
            System.out.println("l.size()=" + l.size() + ",first=" + first);
            System.out.println("offset=" + offset);
            System.out.println("sublist(" + first + ", " + (first + offset) + ")...");
            System.out.println("totale_dati=" + l.size());
            System.out.println("...");

            for (Annuncio a : l.subList(first, first + offset)) {
                html += getDivAnnuncio(a);
            }
        }

        return html;
    }

    private String getDivAnnuncio(Annuncio a) {
        String div_html = "";
        Long oid = a.getId();

        div_html += "<div id='ann-" + oid + "' class='annuncio'>";

        div_html += "<span class='nome-annuncio'><h1>Annuncio " + oid + "</h1></span>";
        div_html += getHTMLButtonAnnuncio(oid, a.isArchiviato());
        div_html += "<a href='#0'>Modifica Annuncio</a>";

        div_html += "<div>Proprietario: " + a.getLocatore().getEmail() + "</div>";
        div_html += "<div>Indirizzo: " + a.getIndirizzo() + "</div>";
        div_html += "<div>Descrizione: " + a.getDescrizione() + "</div>";

        // div_html += "<span class='dati-annuncio'><p>" + a.toJSON().toString() + "</p></span>";
        div_html += "</div>";

        return div_html;
    }

    private String getHTMLButtonAnnuncio(Long oid, boolean archivia) {
        return ("<a href='#0' class='link-annuncio' id='select-ann-" + oid + "'>")
                + (archivia ? "Pubblica" : "Archivia")
                + "</a>";
        /*
        return !archivia
                ? ("<a href='#0' class='link-annuncio' id='select-ann-"+oid+"'>Archivia annuncio</a>")
                : ("<a href='#0' class='link-annuncio' id='select-ann-"+oid+"'>Pubblica annuncio</a>");*/
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
