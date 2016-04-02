/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;


import java.io.IOException;

import java.io.PrintWriter;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

@MultipartConfig
/**
 *
 * @author giacomocavallo
 */
public class FotoUploadServlet extends HttpServlet {

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
            throws ServletException, IOException, JSONException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            String objarray = request.getParameter("annuncio").toString();
            JSONObject jObj = new JSONObject(request.getParameter("annuncio").toString()); // this parses the json
            Iterator it = jObj.keys(); //gets all the keys

            while (it.hasNext()) {
                String key = (String) it.next(); // get key
                Object o = jObj.get(key); // get value
                System.out.println("Chiave: " + key + " Valore: " + o.toString());
                
            }

            /* TODO output your page here. You may use following sample code. */
 /* CODICE PER PASSARE LE FOTO
        String action = request.getParameter("action");
            System.out.println(action);
                Part filePart = request.getPart("file");
                System.out.println("ciao");
                
                InputStream filecontent = filePart.getInputStream();
                FileOutputStream prova = new FileOutputStream("//Users//giacomocavallo//NetBeansProjects//ProgettoSSCSWeb//haiHome//haiHome-war//web//Immagini//" + filePart.getSubmittedFileName());
                int read = 0;
                final byte[] bytes = new byte[1024];

                while ((read = filecontent.read(bytes)) != -1) {
                    prova.write(bytes, 0, read);
                }

                
                filecontent.close();
                prova.close();
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/Annunci_JSP/InserimentoStanze.jsp");
                rd.forward(request, response);
             */
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
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(FotoUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(FotoUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
