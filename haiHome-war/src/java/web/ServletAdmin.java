/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import ejb.GestoreAdminLocal;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author gianp_000
 */
public class ServletAdmin extends HttpServlet {
    
    @EJB
    private GestoreAdminLocal gestoreAdmin;
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
            System.out.println("ACTION: " + action);
            /*gestoreAdmin.addAdmin("admin@haihome.it", "haihome");
            out.write("Aggiunto");*/
            HttpSession session = request.getSession(); 
            
            if (action.equalsIgnoreCase("login-admin")) {
                
                String email = request.getParameter("adminEmail");
                String password = request.getParameter("adminPW");
                
                if (gestoreAdmin.checkAdmin(email, password)) {
                    out.write("OK");
                    
                    session.setAttribute("user-type", "admin");
                    session.setAttribute("email", email);
                } else {
                    out.write("Credenziali ADMIN errate.");
                }
                //getServletContext().getRequestDispatcher("/admin.jsp").forward(request, response);
            }
            else if (action.equalsIgnoreCase("admin-get-session")) {
                //ansia ^ 2
                String user_type = (String) session.getAttribute("user-type"); 
                boolean res = user_type != null && user_type.equalsIgnoreCase("admin");
                out.write(res ? "OK" : "ERR");
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
