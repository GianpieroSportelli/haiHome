/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import ejb.GestoreLocatoreLocal;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
            /*
           if (session.getAttribute("is-user-logged") == null) 
               session.setAttribute("is-user-logged", false);*/

            if (action.equalsIgnoreCase("signup-locatore")) {
                /* registrazione */
                String name = request.getParameter("user-name").trim(),
                        surname = request.getParameter("user-surname").trim(),
                        email = request.getParameter("user-email").trim(),
                        phone = request.getParameter("user-phone").trim(),
                        pwd = request.getParameter("user-pw"),
                        pwd2 = request.getParameter("user-pw-repeat"),
                        op_result;

                /* controllo correttezza email, telefono, password */
                if (pwd.equals(pwd2)) {
                    if (!gestoreLocatore.checkLocatore(email)) {
                        gestoreLocatore.aggiungiLocatore(email, pwd, name, surname, phone, "https://upload.wikimedia.org/wikipedia/commons/2/23/Pino_Scotto_ceroanKio_2009_6.jpg");
                        op_result = "OK";
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
                if (gestoreLocatore.checkLocatore(email)) {
                    if (gestoreLocatore.getLocatore().getPassword().equals(pwd)) {
                        if (gestoreLocatore.getLocatore() != null) {
                            session.setAttribute("user-type", "locatore");
                            session.setAttribute("user-data", this.gestoreLocatore.toJSON());
                        }
                    } else {
                        op_result = "password incorretta";
                    }
                } else {
                    op_result = "email non riconosciuta";
                }

                response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
                response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
                response.getWriter().write(op_result);
            } else if (action.equalsIgnoreCase("loginFacebookLocatore")) {
                String email = request.getParameter("mailUser");
                String foto = request.getParameter("profilo");
                // String nome = dataUser[0];String cognome = dataUser[1];
                String[] dataUser = request.getParameter("userData").split(",");

                /* prevedere eventualità in cui l'email sia presente ma associata
                   a un account con password    */
                // eventuale registrazione se è il primo accesso 
                if (gestoreLocatore.checkLocatore(email) == false) {
                    gestoreLocatore.aggiungiLocatore(email, null, dataUser[0], dataUser[1], null, foto);
                }
                // si potrebbe aggiornare l'immagine del profilo, possibile che sia cambiata...

                // creo sessione 
                session.setAttribute("user-type", "locatore");
                session.setAttribute("user-data", this.gestoreLocatore.toJSON());
                // redirect 
                getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);

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
                    String phone = request.getParameter("phone"); //da recuperare dal profilo...boh

                    if (gestoreLocatore.checkLocatore(email) == false) {
                        gestoreLocatore.aggiungiLocatore(email, null, name, surname, null, url_img);
                    }
                    // creo sessione 

                    session.setAttribute("user-type", "locatore");
                    session.setAttribute("user-data", this.gestoreLocatore.toJSON());
                    // redirect 
                    getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);

                } else {
                    out.println("errore nell'autenticazione");
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
