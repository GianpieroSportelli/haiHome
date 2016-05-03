/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import ejb.GestoreStudenteLocal;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import verifytoken.Verify;

/**
 *
 * @author gianp_000
 */
public class ServletStudente extends HttpServlet {

    @EJB
    private GestoreStudenteLocal gestoreStudente;

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
            String action = request.getParameter("action");

            /* We have to create or update a session between requests 
             We suppose that a previous session exists.
             request.getSession(false): 
             If create is false and the request has no valid HttpSession, 
             this method returns null.*//*
             HttpSession session = request.getSession(false);
            
             if (session == null) {
             session = request.getSession(true); // 
             session.setAttribute("is-user-logged", false);
             } */
            HttpSession session = request.getSession();
            /*
             if (session.getAttribute("is-user-logged") == null) 
             session.setAttribute("is-user-logged", "false"); */

            if (action.equalsIgnoreCase("signup-studente")) {
                String name = request.getParameter("user-name").trim(),
                        surname = request.getParameter("user-surname").trim(),
                        email = request.getParameter("user-email").trim(),
                        pwd = request.getParameter("user-pw"),
                        pwd2 = request.getParameter("user-pw-repeat"),
                        op_result;

                if (gestoreStudente.checkStudente(email) == false) {
                    /* creo studente con avatar di default */
                    gestoreStudente.aggiungiStudente(email, name, surname,
                            "http://openmag.it/wp-content/uploads/2015/12/gianni_morandi.jpg",
                            pwd);
                    op_result = "OK";

                } else {
                    op_result = "email address <" + email + "> is already registered";
                }

                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(op_result);

            } else if (action.equalsIgnoreCase("login-studente")) {
                String email = request.getParameter("user-email").trim(),
                        pwd = request.getParameter("user-pw"),
                        op_result = "OK"; // ottimismo mode on 

                if (gestoreStudente.checkStudente(email)) {
                    String curr_pwd = gestoreStudente.getStudente().getPassword();

                    if (curr_pwd != null) {
                        if (curr_pwd.equals(pwd)) {
                            // creo una nuova sessione 
                            //   HttpSession session = request.getSession();

                            if (gestoreStudente.getStudente() != null) {
                                //Parametro utile per capire se visualizzare la pass o meno
                                session.setAttribute("user-access", " ");
                                session.setAttribute("user-type", "studente");
                                session.setAttribute("user-data", this.gestoreStudente.toJSON());
                            }
                            /*
                             session.setAttribute("user-type", "studente");
                             session.setAttribute("user-data", this.gestoreStudente.toJSON());
                             session.setAttribute("is-user-logged", (gestoreStudente.getStudente() != null) ? "true" : "false");
                             */
                            //Si salva tutti i dati, senza doverli mandarli nuovamente con una request
                            /*
                             session.setAttribute("JSONList", this.gestoreStudente.toJSON());
                             session.setAttribute("Loggato", this.gestoreStudente.getStudente() != null);
                             session.setAttribute("IsStudente", true);*/
                            // getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
                        } else {
                            op_result = "password incorretta";
                        }
                    } else {
                        op_result = "accedi via social...";
                    }
                } else {
                    op_result = "email non riconosciuta";
                }

                response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
                response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
                response.getWriter().write(op_result);

            } else if (action.equalsIgnoreCase("loginFacebookStudente")) {
                /*
                 out.println("<!DOCTYPE html>");
                 out.println("<html>");
                 out.println("<head>");
                 out.println("<title>Servlet Facebook - Login</title>");
                 out.println("</head>");
                 out.println("<body>");*/

                String email = request.getParameter("mailUser");
                String foto = request.getParameter("profilo");
                //Il cognome è nella seconda posizione
                String[] dataUser = request.getParameter("userData").split(",");
                String cognome = dataUser[1];
                //Il nome è nella prima posizione
                String nome = dataUser[0];
                /*
                 out.println("<p> Dati immessi: Nome e cognome - " + dati + " - Email: " + email + "- Foto: " + foto + "</p>");
                 */
                //Check della presenza di uno studente

                //Se true, allora lo studente è presente
                if (gestoreStudente.checkStudente(email) == false) {
                    gestoreStudente.aggiungiStudente(email, nome, cognome, foto, null);
                }

                //     HttpSession session = request.getSession();
                session.setAttribute("user-type", "studente");
                session.setAttribute("user-data", this.gestoreStudente.toJSON());
                session.setAttribute("is-user-logged", this.gestoreStudente.getStudente() != null);
                //Parametro utile per capire se visualizzare la pass o meno
                session.setAttribute("user-access", "fb");

                //Si salva tutti i dati, senza doverli mandarli nuovamente con una request
                session.setAttribute("JSONList", this.gestoreStudente.toJSON());
                //-          session.setAttribute("Loggato", this.gestoreStudente.getStudente() != null);
                session.setAttribute("IsStudente", true);
                getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);

                /* ---------------- PROVA CANCELLAZIONE STUDENTE FUNZIONANTE
             
                 gestoreStudente.removeStudente();
                 out.println("<p> Operazione in corso: Get studenti </p>");

                 for (String s : gestoreStudente.getStudenti()) {
                 out.println("<p>" + s + "</p>");
                 }
                 *//*
                 out.println("</body>");
                 out.println("</html>");*/
            } else if (action.equalsIgnoreCase("login-googleplus-studente")) {
                String[] verify = Verify.getUserCredentials(
                        request.getParameter("id_token"),
                        request.getParameter("access_token"));

                if (verify != null) {
                    String name = request.getParameter("name");
                    String surname = request.getParameter("surname");
                    //                   String email = verify[1];
                    String email = request.getParameter("email");
                    String url_img = request.getParameter("url-profile-img");// + "?sz=200";

                    if (gestoreStudente.checkStudente(email) == false) {
                        gestoreStudente.aggiungiStudente(email, name, surname, url_img, null);
                    }

                    //          HttpSession session = request.getSession();
                    session.setAttribute("user-type", "studente");
                    session.setAttribute("user-data", this.gestoreStudente.toJSON());
                    //Parametro utile per capire se visualizzare la pass o meno
                    session.setAttribute("user-access", "g+");

                    getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);

                } else {
                    out.println("errore nell'autenticazione");
                }
            } else if (action.equalsIgnoreCase("get-lista-preferiti-studente")) {
                response.setContentType("application/json");
                if (gestoreStudente.reloadStudente()) {
                    String json = gestoreStudente.getStudente().getListaFiltriPreferiti().toString();
                    System.out.println("Filtri preferiti " + json);
                    out.write(json);
                } else {
                    out.write("ERRORE");
                }
            } else if (action.equalsIgnoreCase("studente-addAnnuncio")) {
                String id_annuncio = request.getParameter("id");
                if (gestoreStudente.addAnnuncio(id_annuncio)) {
                    out.write("true");
                } else {
                    out.write("false");
                }
            } else if (action.equalsIgnoreCase("studente-getAnnunci")) {
                response.setContentType("application/json");
                if (gestoreStudente.reloadStudente()) {
                    String json = gestoreStudente.getStudente().getAnnunciJSON().toString();
                    System.out.println(json);
                    out.write(json);
                } else {
                    out.write("ERRORE");
                }
            } else if (action.equalsIgnoreCase("studente-edit-info")) {
                String field_value = request.getParameter("field-value");
                String error = "";
                JSONObject jsonresult = new JSONObject();
                boolean res = true;

                if (gestoreStudente.getStudente().getPassword().equals(field_value)) {
                    String new_password = request.getParameter("new-pw");
                    /* Regex complessità password?? */

                    if (new_password.length() >= 3) {
                        gestoreStudente.changePassword(new_password);
                    } else {
                        res = false;
                        error = "PASSWORD TOO SHORT";
                    }
                } else {
                    res = false;
                    error = "OLD PASSWORD INCORRECT";
                }

                //refresh sessione
                session.setAttribute("user-data", this.gestoreStudente.toJSON());

                try {
                    jsonresult.accumulate("result", res);
                    jsonresult.accumulate("error", error);

                } catch (JSONException ex) {
                    Logger.getLogger(ServletLocatore.class.getName()).log(Level.SEVERE, null, ex);
                }

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonresult.toString());
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
