/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import ejb.GestoreStudenteLocal;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

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
                    if (gestoreStudente.getStudente().getPassword().equals(pwd)) {
                        // creo una nuova sessione 
                        HttpSession session = request.getSession();
                        
                        //Si salva tutti i dati, senza doverli mandarli nuovamente con una request
                        session.setAttribute("JSONList", this.gestoreStudente.toJSON());
                        session.setAttribute("Loggato", this.gestoreStudente.getStudente() != null);
                        session.setAttribute("IsStudente", true);
                       // getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);

                    } else {
                        op_result = "password incorretta";
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

                String dati = request.getParameter("userData");
                String email = request.getParameter("mailUser");
                String foto = request.getParameter("profilo");
                //Il cognome è nella seconda posizione
                String[] dataUser = dati.split(",");
                String cognome = dataUser[1];
                //Il nome è nella prima posizione
                String nome = dataUser[0];
                /*
                out.println("<p> Dati immessi: Nome e cognome - " + dati + " - Email: " + email + "- Foto: " + foto + "</p>");
                 */
                //Check della presenza di uno studente
                boolean esito = gestoreStudente.checkStudente(email);

                //Se true, allora lo studente è presente
                if (gestoreStudente.checkStudente(email) == false) {
                    gestoreStudente.aggiungiStudente(email, nome, cognome, foto, null);
                }
                /*
                if (esito) {
                    out.println("<p> Studente con credenziali: " + email + " " + nome + " " + cognome + " già presente </p>");
                } else {
                    //Se non è presente, lo aggiungo
                    gestoreStudente.aggiungiStudente(email, nome, cognome, foto, null);
                }*/
 /*
                out.println("<p> Operazione in corso: Get studenti </p>");
                List<String> lista = gestoreStudente.getStudenti();

                for (String s : lista) {
                    out.println("<p>" + s + "</p>");
                }*/

                JSONObject json = this.gestoreStudente.toJSON();

                //       System.out.println("<p>" + json.toString() + "</p>");
                //request.setAttribute("JSONList", json);
                //request.setAttribute("Loggato", this.gestoreStudente.getStudente() != null);
                //getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
                HttpSession session = request.getSession();
                //Si salva tutti i dati, senza doverli mandarli nuovamente con una request
                session.setAttribute("JSONList", json);
                session.setAttribute("Loggato", this.gestoreStudente.getStudente() != null);
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
