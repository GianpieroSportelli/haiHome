/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import ejb.GestoreAnnuncioLocal;
import ejb.GestoreLocatoreLocal;
import ejb.GestoreRicercaLocal;
import ejb.GestoreTestLocal;
import ejb.GoogleMapsBeanLocal;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author gianp_000
 */
public class TestServlet extends HttpServlet {

    @EJB
    private GestoreRicercaLocal gestoreRicerca;

    @EJB
    private GestoreTestLocal gestoreTest;

    @EJB
    private GestoreAnnuncioLocal gestoreAnnunci;

    @EJB
    private GestoreLocatoreLocal gestoreLocatore;

    @EJB
    private GoogleMapsBeanLocal gmb;

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
            out.println("<title>Servlet TestServlet</title>");
            out.println("</head>");
            out.println("<body>");
            String nome = "Torino";
            /*String add_test1 = "Via San Giuseppe Benedetto Cottolengo, Torino, TO, Italia";
            String add_test2 = "Via Carlo Alberto, 41, Torino, TO, Italia";
            String add_test4 = "Via Po, 15, Torino, TO, Italia";
            String add_test3 = "Via Gian Domenico Cassini, 15, 10129 Torino, TO, Italia, 41, Torino, TO, Italia";
            //String add_test="Via Po, 15, Torino, TO, Italia";
            add_test3 = add_test3.replace(" ", "+");
            add_test2 = add_test2.replace(" ", "+");
            add_test1 = add_test1.replace(" ", "+");
            add_test4 = add_test4.replace(" ", "+");

            gestoreTest.addCittà(nome);
            ArrayList<String> cities = gestoreTest.getAllCittàNome();
            if (cities.isEmpty()) {
                out.println("<p>Non sono Presenti città nel DB</p>");
            } else {
                for (String cit : cities) {
                    out.println("<p>" + cit + "</p>");
                }
            }
            String[] Quartieri = {"Centro", "Crocetta", "Santa Rita", "Mirafiori Nord",
                "Borgo San Paolo", "Cenisia", "Pozzo Strada", "Cit Turin", "Borgata Lesna",
                "San Donato", "Campidoglio", "Parella",
                "Borgo Vittoria", "Madonna di Campagna", "Lucento", "Vallette",
                "Barriera di Milano", "Regio Parco", "Barca", "Bertolla", "Falchera", "Rebaudengo", "Villaretto",
                "Aurora", "Vanchiglia", "Sassi", "Madonna del Pilone",
                "San Salvario", "Cavoretto", "Borgo Po",
                "Nizza Millefonti", "Lingotto", "Filadelfia",
                "Mirafiori Sud"};
            String circoscrizioni = "Di Bruce The Deus - Opera propria, <a href=\"http://creativecommons.org/licenses/by-sa/3.0/\" title=\"Creative Commons Attribuzione - Condividi allo stesso modo versioni 3.0\">CC BY-SA 3.0</a>, https://it.wikipedia.org/w/index.php?curid=4681908";
            for (String quartiere : Quartieri) {
                gestoreTest.addQuartiere(nome, quartiere);
            }
            ArrayList<String> quartieri = gestoreTest.getListaQuartieriNome(nome);
            for (String quartiere : quartieri) {
                out.println("<p>" + quartiere + "</p>");
            }/*

             /*
             gestoreTest.cancellaCittà(nome);
             gestoreRicerca.selezionaCittà(nome);
             gestoreRicerca.creaFiltroDiRicerca(600, new ArrayList<>(), true, true);
             gestoreRicerca.aggiornaAFiltroAppartamento(1,1,1,0);
             out.println(gestoreRicerca.isFiltroAppartamento());
             request.setAttribute("gestoreRicerca", gestoreRicerca);
             request.getRequestDispatcher("/TestServlet2").forward(request, response);
             */
 /*double[] latlng=gmb.geocodingAddress(add_test);
             System.out.println("lat: "+latlng[0]+" lng: "+latlng[1]);*/


            //String email = "gianpiero.sportelli@libero.it";
            String email="sportelligianpiero@gmail.com";
            //   gestoreLocatore.aggiungiLocatore("gianpiero.sportelli@libero.it", "Gianpiero", "Sportelli", "foto", "pass", "Locatore Random");
            //gestoreLocatore.aggiungiLocatore("gianpiero.sportelli@libero.it", "password", "gianpiero", "sportelli", "3401234567", "https://upload.wikimedia.org/wikipedia/commons/2/23/Pino_Scotto_ceroanKio_2009_6.jpg");
            /*if (gestoreLocatore.checkLocatore(email)) {
            gestoreAnnunci.CreaAnnuncio(gestoreLocatore.getLocatore());

            /*ANNUNCIO 1 Via Cottolengo*/
             /*gestoreAnnunci.inserisciInfoAnnuncio("Descrizione Annuncio 1", 90, new Date(2016, 3, 1), 5, true);
             gestoreAnnunci.inserisciInfoCostiAppartamento(850, true, true);
             double[] latlng=gmb.geocodingAddress(add_test1);
             gestoreAnnunci.inserisciInfoIndirizzo("Torino","Aurora", add_test1, latlng);
             gestoreAnnunci.inserisciNuovaStanzaAccessoria("Cucina",new ArrayList<String>(), 15);
             gestoreAnnunci.inserisciNuovaStanzaAccessoria("Bagno",new ArrayList<String>() , 15);
             gestoreAnnunci.inserisciNuovaStanzaInAffitto("Singola", new ArrayList<String>(), true, true, 20, 272);
             gestoreAnnunci.inserisciNuovaStanzaInAffitto("Singola", new ArrayList<String>(), true, true, 20, 272);
             gestoreAnnunci.inserisciNuovaStanzaInAffitto("Singola", new ArrayList<String>(), true, true, 20, 272);
             if (gestoreAnnunci.rendiAnnuncioPersistente()) {
             System.out.println(gestoreAnnunci.toJSON());
             } else {
             System.out.println("errore");
             }
        }/**/
 /**/
            /* ANNUNCIO 2 Via Carlo Alberto, 41, Torino, TO, Italia*/
            /*if (gestoreLocatore.checkLocatore(email)) {
                gestoreAnnunci.CreaAnnuncio(gestoreLocatore.getLocatore());
                //gestoreAnnunci.inserisciInfoAnnuncio("Descrizione Annuncio 2", 110, new Date(2016, 3, 1), 4, false);
                gestoreAnnunci.inserisciInfoCostiAppartamento(800, true, true);

                /*double[] latlng = gmb.geocodingAddress(add_test2);
                gestoreAnnunci.inserisciInfoIndirizzo("Torino", "Centro", add_test2, latlng);
                gestoreAnnunci.inserisciNuovaStanzaAccessoria("Cucina", new ArrayList<String>(), 30);
                gestoreAnnunci.inserisciNuovaStanzaAccessoria("Bagno", new ArrayList<String>(), 15);
                gestoreAnnunci.inserisciNuovaStanzaInAffitto("Singola", new ArrayList<String>(), true, true, 25, 300);
                gestoreAnnunci.inserisciNuovaStanzaInAffitto("Doppia", new ArrayList<String>(), true, true, 50, 500);
                if (gestoreAnnunci.rendiAnnuncioPersistente()) {
                    System.out.println(gestoreAnnunci.toJSON());
                } else {
                    System.out.println("errore");
                }
            }/**/
            /* ANNUNCIO 3 Via Gian Domenico Cassini, 15, 10129 Torino, TO, Italia, 41, Torino, TO, Italia*/
            /*if (gestoreLocatore.checkLocatore(email)) {
                gestoreAnnunci.CreaAnnuncio(gestoreLocatore.getLocatore());
                //gestoreAnnunci.inserisciInfoAnnuncio("Descrizione Annuncio 3", 110, new Date(2016, 3, 1), 5, false);
                gestoreAnnunci.inserisciInfoCostiAppartamento(900, true, true);

                /*double[] latlng = gmb.geocodingAddress(add_test3);
                System.out.println(""+latlng[0]+" , "+latlng[1]);
                gestoreAnnunci.inserisciInfoIndirizzo("Torino", "Crocetta", add_test3, latlng);
                gestoreAnnunci.inserisciNuovaStanzaAccessoria("Cucina", new ArrayList<String>(), 30);
                gestoreAnnunci.inserisciNuovaStanzaAccessoria("Bagno", new ArrayList<String>(), 15);
                gestoreAnnunci.inserisciNuovaStanzaAccessoria("Bagno", new ArrayList<String>(), 20);
                gestoreAnnunci.inserisciNuovaStanzaInAffitto("Doppia", new ArrayList<String>(), true, true, 25, 450);
                gestoreAnnunci.inserisciNuovaStanzaInAffitto("Doppia", new ArrayList<String>(), true, true, 50, 450);
                if (gestoreAnnunci.rendiAnnuncioPersistente()) {
                    System.out.println(gestoreAnnunci.toJSON());
                } else {
                    System.out.println("errore");
                }
            }/**/
            //ANNUNCIO 4 Via Carlo Alberto, 41, Torino, TO, Italia
            /*if (gestoreLocatore.checkLocatore(email)) {
                gestoreAnnunci.CreaAnnuncio(gestoreLocatore.getLocatore());
                //gestoreAnnunci.inserisciInfoAnnuncio("Descrizione Annuncio 4", 110, new Date(2016, 3, 1), 4, false);
                gestoreAnnunci.inserisciInfoCostiAppartamento(800, true, true);
                ArrayList<String> bagno = new ArrayList<String>();
                ArrayList<String> cucina = new ArrayList<String>();
                ArrayList<String> stanza1 = new ArrayList<String>();
                ArrayList<String> stanza2 = new ArrayList<String>();

                //il path poi se non funziona te lo aggiusti tu
                bagno.add("..//Immagini//appartamento_prova//bagno//bagno.jpg");
                cucina.add("..//Immagini//appartamento_prova//cucina//cucina.jpg");
                stanza1.add("..//Immagini//appartamento_prova//stanza1//stanza1.jpg");
                stanza2.add("..//Immagini//appartamento_prova//stanza2//stanza2.jpg");

                double[] latlng = gmb.geocodingAddress(add_test4);
                gestoreAnnunci.inserisciInfoIndirizzo("Torino", "Centro", add_test4, latlng);
                gestoreAnnunci.inserisciNuovaStanzaAccessoria("Cucina", cucina, 30);
                gestoreAnnunci.inserisciNuovaStanzaAccessoria("Bagno", bagno, 15);
                gestoreAnnunci.inserisciNuovaStanzaInAffitto("Singola", stanza1, true, true, 25, 300);
                gestoreAnnunci.inserisciNuovaStanzaInAffitto("Doppia", stanza2, true, true, 50, 500);
                if (gestoreAnnunci.rendiAnnuncioPersistente()) {
                    System.out.println(gestoreAnnunci.toJSON());
                } else {
                    System.out.println("errore");
                }
            }

            //gmb.getQuartiereByAddress(nome);
            out.println("</body>");
            out.println("</html>");*/
            String add="Via Garibaldi, 1, Torino, TO, Italia";
            String add_code=add.replace(" ", "+");
            if (gestoreLocatore.checkLocatore(email)) {
                gestoreAnnunci.CreaAnnuncio(gestoreLocatore.getLocatore());
                //gestoreAnnunci.inserisciInfoAnnuncio("Descrizione Annuncio 6", 110, new Date(2016, 3, 1), 4, false);
                gestoreAnnunci.inserisciInfoCostiAppartamento(800, true, true);
                ArrayList<String> bagno = new ArrayList<String>();
                ArrayList<String> cucina = new ArrayList<String>();
                ArrayList<String> stanza1 = new ArrayList<String>();
                ArrayList<String> stanza2 = new ArrayList<String>();

                //il path poi se non funziona te lo aggiusti tu
                bagno.add("/Immagini/appartamento_prova/bagno/bagno.jpg");
                cucina.add("/Immagini/appartamento_prova/cucina/cucina.jpg");
                stanza1.add("/Immagini//appartamento_prova/stanza1/stanza1.jpg");
                stanza2.add("/Immagini/appartamento_prova/stanza2/stanza2.jpg");

                double[] latlng = gmb.geocodingAddress(add_code);
                gestoreAnnunci.inserisciInfoIndirizzo("Torino", "Centro", add, latlng);
                gestoreAnnunci.inserisciNuovaStanzaAccessoria("Cucina", cucina, 30);
                gestoreAnnunci.inserisciNuovaStanzaAccessoria("Bagno", bagno, 15);
                gestoreAnnunci.inserisciNuovaStanzaInAffitto("Singola", stanza1, true, true, 25, 300);
                gestoreAnnunci.inserisciNuovaStanzaInAffitto("Doppia", stanza2, true, true, 50, 500);
                if (gestoreAnnunci.rendiAnnuncioPersistente()) {
                    System.out.println(gestoreAnnunci.toJSON());
                } else {
                    System.out.println("errore");
                }
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
