/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import ejb.GestoreStudente;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author gianp_000
 */
public class ServletStudente extends HttpServlet {
    @EJB
    private GestoreStudente gestoreStudente;

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
            out.println("<title>Servlet ServletStudente</title>");            
            out.println("</head>");
            out.println("<body>");
            
            /*
            String dati = request.getParameter("userData");
            String email = request.getParameter("mailUser");
            String foto = request.getParameter("profilo");
             //Il cognome è nella seconda posizione
            String[] dataUser = dati.split(",");
            String cognome = dataUser[1];
            //Il nome è nella prima posizione
            String nome = dataUser[0];
            out.println("<p> Dati immessi: Nome e cognome - " + dati + " - Email: " + email + "- Foto: " + foto + "</p>");

            //Check della presenza di uno studente MODIFICARE!!
            //Studente st = gestoreStudente.checkStudente(email, nome, cognome);

            //Se non è null significa che ho trovato uno studente con le stesse credenziali.
            //Lo cancello
            if (st != null) {
                out.println("<p> Studente con credenziali: " + email + " " + nome + " " + cognome + " già presente </p>");
                // gestoreStudente.removeStudente(st);
            } else {
                //Se non è presente, lo aggiungo
                gestoreStudente.aggiungiStudente(email, nome, cognome, foto, null);
            }

            out.println("<p> Operazione in corso: Get studenti </p>");
            List<Studente> lista = gestoreStudente.getStudenti();

            for (Studente s : lista) {
                //gestoreStudente.removeStudente(s);
                out.println("<p>Nome =" + s.getNome()
                        + ", Cognome = " + s.getCognome() + ", Email = " + s.getEmail() + ", Foto = " + s.getFotoProfilo() + ", Password = " + s.getPassword() + "</p>");

            }

            /**
             * ***********************CODICE JSON //Qua c'era codice JSON
            ************************************
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
