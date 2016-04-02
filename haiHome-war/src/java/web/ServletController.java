/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Eugenio Liso
 */
public class ServletController extends HttpServlet {

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

        try (PrintWriter out = response.getWriter()) {
            //response.setContentType("text/html;charset=UTF-8");
            /*
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Esempio ServletController</title>");
            out.println("</head>");
            out.println("<body>");*/

            //Prende il parametro "action" dalla form della pagina JSP ed elabora la richiesta
            String action = request.getParameter("action");

            if (action.equalsIgnoreCase("signup-studente") || action.equalsIgnoreCase("login-studente")) {
                request.getRequestDispatcher("/ServletStudente").forward(request, response);
            } else if (action.equalsIgnoreCase("signup-locatore") || action.equalsIgnoreCase("login-locatore")) {
                request.getRequestDispatcher("/ServletLocatore").forward(request, response);
            } else if (action.equalsIgnoreCase("loginFacebookStudente")) {
                request.getRequestDispatcher("/ServletStudente").forward(request, response);

            } else if (action.equalsIgnoreCase("loginFacebookLocatore")) {
                //request.getRequestDispatcher("/ServletLocatore").forward(request, response);
                out.println("Sono in LOCATORE! FUNZIONA!");

            } else if (action.equalsIgnoreCase("setCity")) {
                request.getRequestDispatcher("/ServletRicerca").forward(request, response);

            } else if (action.equalsIgnoreCase("Ricerca-geoCity")) {
                request.getRequestDispatcher("/ServletRicerca").forward(request, response);

            } else if (action.equalsIgnoreCase("Ricerca-getAnnunciFiltro")) {
                request.getRequestDispatcher("/ServletRicerca").forward(request, response);

            } else if (action.equalsIgnoreCase("Ricerca-getQuartieri")) {
                request.getRequestDispatcher("/ServletRicerca").forward(request, response);

            } else if (action.equalsIgnoreCase("Ricerca-getTipoStanza")) {
                request.getRequestDispatcher("/ServletRicerca").forward(request, response);

            } else if (action.equalsIgnoreCase("search")) {
                request.getRequestDispatcher("/ServletRicerca").forward(request, response);
                
            } else if (action.equalsIgnoreCase("logoutFacebook") || action.equalsIgnoreCase("user-logout")) {
                request.getRequestDispatcher("/LogoutServlet").forward(request, response);
                /*
                //verifico se esiste una sessione precedentemente creata
                HttpSession session = request.getSession(false); 
                if (session != null) {
                    if (session)
                    out.println("there is a session...EXTERMINATE");
                    session.invalidate();
                    out.println("done"); 
                    
                    //redirect all'index 
                }
                else { //non dovrebbe essere possibile ma non si sa mai 
                    out.println("no session");
                } */
            }
            else {
                // action random
                response.sendRedirect(request.getContextPath() + "/index.jsp"); // NOPE 
            }
            /*
            out.println(
                    "</body>");
            out.println(
                    "</html>");*/

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
