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
 * @author nico
 *
 * http://stackoverflow.com/questions/13707225/kill-session-and-redirect-to-login-page-on-click-of-logout-button
 */
public class LogoutServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {*/
        HttpSession session = request.getSession();
        //        session.setAttribute("is-user-logged", false);
        // If the value passed in is null, this has the same effect as calling removeAttribute().
        session.setAttribute("user-type", null);
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        /*
            HttpSession session = request.getSession(false);
            
            if (session != null) {
                session.setAttribute("is-user-logged", false);
                // cancellare tutti gli attributi??
            }

            response.sendRedirect(request.getContextPath() + "/index.jsp");*/

//        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

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

}
