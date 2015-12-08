/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import ejb.GestoreAnnuncioLocal;
import ejb.GestoreLocatoreLocal;
import ejb.GestoreTestLocal;
import entity.Locatore;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author giacomocavallo
 */
@WebServlet(name = "TestInserimentoAnnuncio", urlPatterns = {"/TestInserimentoAnnuncio"})
public class TestInserimentoAnnuncio extends HttpServlet {
    
@EJB
private GestoreAnnuncioLocal gestoreAnnuncio;

@EJB
private GestoreTestLocal gestoreTest;


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
            out.println("<title>Servlet TestInserimentoAnnuncio</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<p>ciao ciao ciao</p>");
            
            //inizio inserimento annuncio
            

            this.gestoreAnnuncio.CreaAnnuncio(10);
            double[] latlng = new double[2];
            latlng[0]=0.12;
            latlng[1]=100.12;
            boolean a,b,c,d,e,f,g;
            

            a=this.gestoreAnnuncio.inserisciInfoIndirizzo("Torino", "Aurora", "Via don Bosco 10", latlng);
            
            
            b=this.gestoreAnnuncio.inserisciInfoAppartamento("descrizione", 200, new Date(), 3,true);
            
            ArrayList<String> foto = new ArrayList<String>();
            c=this.gestoreAnnuncio.inserisciNuovaStanzaInAffitto("tipo", foto, 50);
    
   
    
            d=this.gestoreAnnuncio.inserisciNuovaStanzaAccessoria("Bagno",foto, 30);
             
            e=this.gestoreAnnuncio.inserisciNuovaStanzaAccessoria("Bagno",foto, 30);
             
        
            f=this.gestoreAnnuncio.inserisciInfoCostiAppartamento(500,  false,  false);
    
    
            g=this.gestoreAnnuncio.rendiAnnuncioPersistente();
            
            out.println("<p>ciao ciao ciao</p>");
              System.out.println(a + " " + b);        

            
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
