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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {            
            String action = request.getParameter("action"); 
            
            
            if (action.equalsIgnoreCase("signup-locatore")) {
                /* registrazione */ 
                String name = request.getParameter("user-name"), 
                       surname = request.getParameter("user-surname"), 
                       email = request.getParameter("user-email"), 
                       phone = request.getParameter("user-phone"), 
                       pwd = request.getParameter("user-pw"), 
                       pwd2 = request.getParameter("user-pw-repeat"), 
                       message; 
               
                /* controllo correttezza email, telefono, password */ 
                if (pwd.equals(pwd2)) {
                    if (!gestoreLocatore.checkLocatore(email)) {
                        gestoreLocatore.aggiungiLocatore(email, name, surname, "foto", pwd, "descrizione");
                        message = "registrazione avvenuta"; 
                    }
                    else {
                        message = "registrazione non avvenuta-email non valida";
                    }
                }
                else {
                    message = "boh-password diverse";
                }
                
                response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
                response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
                response.getWriter().write(message);
            }
            else if (action.equalsIgnoreCase("login-locatore")) {
                String email = request.getParameter("user-email"), message; 
                // controlli sull'input ??
                if (gestoreLocatore.checkLocatore(email)) {
                    if (gestoreLocatore.getLocatore().getPassword().equals(request.getParameter("user-pw"))) {
                        message = "login riuscito"; 
                    }
                    else {
                        message = "password incorretta"; 
                    }
                }
                else {
                    message = "email non riconosciuta"; 
                }
                               
                response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
                response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
                response.getWriter().write(message);
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
