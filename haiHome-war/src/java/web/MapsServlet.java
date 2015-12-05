/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

//import com.google.gson.Gson;
import ejb.GoogleMapsBeanLocal;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author gianp_000
 */
public class MapsServlet extends HttpServlet {

    @EJB
    private GoogleMapsBeanLocal googleMapsBean;

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
            RequestDispatcher rd = null;
            String action = request.getParameter("action");
            //out.println(action);
            if (action.equals("getInfoAddress")) {
                String route = request.getParameter("route");
                route = route.replace(" ", "+");
                String street_number = request.getParameter("street_number");
                street_number = street_number.replace(" ", "+");
                String locality = request.getParameter("locality");
                locality = locality.replace(" ", "+");
                String administrative_area_level_1 = request.getParameter("administrative_area_level_1");
                administrative_area_level_1 = administrative_area_level_1.replace(" ", "+");
                String postal_code = request.getParameter("postal_code");
                postal_code = postal_code.replace(" ", "+");
                String country = request.getParameter("country");
                country = country.replace(" ", "+");
                String address = route + "," + street_number + "," + locality + "," + administrative_area_level_1 + "," + postal_code + "," + country;
                out.println(address);
                double[] res = googleMapsBean.geocodingAddress(address);
                if (res != null) {
                    out.println("<p>lat:" + res[0] + "</p>");
                    out.println("<p>lng:" + res[1] + "</p>");

                    ArrayList<JSONObject> nearSupermarket = googleMapsBean.getSupermarketNearBy(res[0], res[1], 500);
                    for (JSONObject obj : nearSupermarket) {
                        out.println("<p>" + obj.toString() + "</p>");
                    }

                    request.setAttribute("lat", res[0]);
                    request.setAttribute("lng", res[1]);
                    request.setAttribute("JSONList", nearSupermarket);
                    getServletContext().getRequestDispatcher("/maps.jsp").forward(request, response);
                }else{
                    out.println("<p>Errore geocoding</p>");
                }

            }
        }
    }

        /*private String buildGson(String[] x) {

         Gson gson = new Gson();
         String json = gson.toJson(x);

         if (json == null) {
         System.out.println("servlet buildGson: NULL");
         } else {
         System.out.println("servlet buildGson: NOT NULL  " + json);
         }
         return json;
         }*/
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
        protected void doGet
        (HttpServletRequest request, HttpServletResponse response)
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
        protected void doPost
        (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            processRequest(request, response);
        }

        /**
         * Returns a short description of the servlet.
         *
         * @return a String containing servlet description
         */
        @Override
        public String getServletInfo
        
            () {
        return "Short description";
        }// </editor-fold>

    }
