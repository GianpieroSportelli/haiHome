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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Facebook - Login</title>");
            out.println("</head>");
            out.println("<body>");

            String dati = request.getParameter("userData");
            String email = request.getParameter("mailUser");
            String foto = request.getParameter("profilo");
            //Il cognome è nella seconda posizione
            String[] dataUser = dati.split(",");
            String cognome = dataUser[1];
            //Il nome è nella prima posizione
            String nome = dataUser[0];
            out.println("<p> Dati immessi: Nome e cognome - " + dati + " - Email: " + email + "- Foto: " + foto + "</p>");

            //Check della presenza di uno studente
            boolean esito = gestoreStudente.checkStudente(email);

            //Se true, allora lo studente è presente
            if (esito) {
                out.println("<p> Studente con credenziali: " + email + " " + nome + " " + cognome + " già presente </p>");
            } else {
                //Se non è presente, lo aggiungo
                gestoreStudente.aggiungiStudente(email, nome, cognome, foto, null);
            }

            out.println("<p> Operazione in corso: Get studenti </p>");
            List<String> lista = gestoreStudente.getStudenti();

            for (String s : lista) {
                out.println("<p>" + s + "</p>");
            }

            JSONObject json = this.gestoreStudente.toJSON();

            out.println("<p>" + json.toString() + "</p>");
            
            request.setAttribute("JSONList", json);
            request.setAttribute("Loggato", this.gestoreStudente.getStudente() != null);
            getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
            

            /* ---------------- PROVA CANCELLAZIONE STUDENTE FUNZIONANTE
             
             gestoreStudente.removeStudente();
             out.println("<p> Operazione in corso: Get studenti </p>");

             for (String s : gestoreStudente.getStudenti()) {
             out.println("<p>" + s + "</p>");
             }
             */
            out.println("</body>");
            out.println("</html>");
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
