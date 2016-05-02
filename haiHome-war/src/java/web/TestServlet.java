/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import ejb.GestoreAnnuncioLocal;
import ejb.GestoreCittaLocal;
import ejb.GestoreLocatoreLocal;
import ejb.GestoreRicercaLocal;
import ejb.GestoreTestLocal;
import ejb.GoogleMapsBeanLocal;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
    private GestoreCittaLocal gestoreCitta;

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
            add_test4 = add_test4.replace(" ", "+");*/

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

            ArrayList<ArrayList<String>> CAP = new ArrayList();

            HashMap<String, ArrayList<String>> QuartieriCap = new HashMap();
            String AAA = "";
            ArrayList<String> cap = new ArrayList();

            //POPOLAMENTO QUARTIERI
            // Centro 
            cap = new ArrayList();
            cap.add("10121");
            cap.add("10122");
            cap.add("10123");
            cap.add("10124");
            QuartieriCap.put(Quartieri[0], cap);

            // Crocetta 
            cap = new ArrayList();
            cap.add("10128");
            cap.add("10129");
            cap.add("10130");
            QuartieriCap.put(Quartieri[1], cap);

            // Santa Rita
            cap = new ArrayList();
            cap.add("10134");
            cap.add("10136");
            cap.add("10137");
            QuartieriCap.put(Quartieri[2], cap);

            // Mirafiori Nord 
            cap = new ArrayList();
            cap.add("10137");
            QuartieriCap.put(Quartieri[3], cap);

            // Borgo San Paolo
            cap = new ArrayList();
            cap.add("10141");
            QuartieriCap.put(Quartieri[4], cap);

            // Cenisia 
            cap = new ArrayList();
            cap.add("10138");
            cap.add("10139");
            cap.add("10140");
            QuartieriCap.put(Quartieri[5], cap);

            // Pozzo Strada 
            cap = new ArrayList();
            cap.add("10139");
            cap.add("10140");
            cap.add("10141");
            QuartieriCap.put(Quartieri[6], cap);

            // Cit Turin 
            cap = new ArrayList();
            cap.add("10138");
            QuartieriCap.put(Quartieri[7], cap);

            // Borgata Lesna 
            cap = new ArrayList();
            cap.add("10142");
            QuartieriCap.put(Quartieri[8], cap);

            // San Donato 
            cap = new ArrayList();
            cap.add("10144");
            QuartieriCap.put(Quartieri[9], cap);

            // Campidoglio 
            cap = new ArrayList();
            cap.add("10143");
            QuartieriCap.put(Quartieri[10], cap);

            // Parella 
            cap = new ArrayList();
            cap.add("10145");
            cap.add("10146");
            QuartieriCap.put(Quartieri[11], cap);

            // Borgo Vittoria 
            cap = new ArrayList();
            cap.add("10147");
            cap.add("10148");
            QuartieriCap.put(Quartieri[12], cap);

            // Madonna di Campagna 
            cap = new ArrayList();
            cap.add("10149");
            QuartieriCap.put(Quartieri[13], cap);

            // Lucento 
            cap = new ArrayList();
            cap.add("10150");
            cap.add("10151");
            QuartieriCap.put(Quartieri[14], cap);

            // Vallette 
            cap = new ArrayList();
            cap.add("10150");
            cap.add("10151");
            QuartieriCap.put(Quartieri[15], cap);

            // Barriera di Milano 
            cap = new ArrayList();
            cap.add("10154");
            cap.add("10155");
            QuartieriCap.put(Quartieri[16], cap);

            // Regio Parco 
            cap = new ArrayList();
            cap.add("10154");
            cap.add("10155");
            QuartieriCap.put(Quartieri[17], cap);

            // Barca 
            cap = new ArrayList();
            cap.add("10156");
            QuartieriCap.put(Quartieri[18], cap);

            // Bertolla ù
            cap = new ArrayList();
            cap.add("");
            QuartieriCap.put(Quartieri[19], cap);

            // Falchera 
            cap = new ArrayList();
            cap.add("10156");
            QuartieriCap.put(Quartieri[20], cap);

            // Rebaudengo 
            cap = new ArrayList();
            cap.add("10155");
            QuartieriCap.put(Quartieri[21], cap);

            // Villaretto 
            cap = new ArrayList();
            cap.add("10148");
            QuartieriCap.put(Quartieri[22], cap);

            // Aurora 
            cap = new ArrayList();
            cap.add("10152");
            QuartieriCap.put(Quartieri[23], cap);

            // Vanchiglia 
            cap = new ArrayList();
            cap.add("10124");
            cap.add("10153");
            QuartieriCap.put(Quartieri[24], cap);

            // Sassi 
            cap = new ArrayList();
            cap.add("");
            QuartieriCap.put(Quartieri[25], cap);

            // Madonna del Pilone 
            cap = new ArrayList();
            cap.add("10132");
            QuartieriCap.put(Quartieri[26], cap);

            // San Salvario
            cap = new ArrayList();
            cap.add("10125");
            QuartieriCap.put(Quartieri[27], cap);

            // Cavoretto 
            cap = new ArrayList();
            cap.add("10133");
            QuartieriCap.put(Quartieri[28], cap);

            // Borgo Po 
            cap = new ArrayList();
            cap.add("10131");
            QuartieriCap.put(Quartieri[29], cap);

            // Nizza Millefonti
            cap = new ArrayList();
            cap.add("10126");
            QuartieriCap.put(Quartieri[30], cap);

            // Lingotto
            cap = new ArrayList();
            cap.add("10126");
            cap.add("10127");
            cap.add("10134");
            cap.add("10135");
            QuartieriCap.put(Quartieri[31], cap);

            // Filadelfia
            cap = new ArrayList();
            cap.add("10127");
            QuartieriCap.put(Quartieri[32], cap);

            // Mirafiori Sud
            cap = new ArrayList();
            cap.add("10135");
            cap.add("10136");
            QuartieriCap.put(Quartieri[33], cap);

            String circoscrizioni = "Di Bruce The Deus - Opera propria, <a href=\"http://creativecommons.org/licenses/by-sa/3.0/\" title=\"Creative Commons Attribuzione - Condividi allo stesso modo versioni 3.0\">CC BY-SA 3.0</a>, https://it.wikipedia.org/w/index.php?curid=4681908";
            for (String quartiere : Quartieri) {
                //gestoreTest.addQuartiere(nome, quartiere);
                gestoreCitta.insertQuartiere(nome, quartiere, QuartieriCap.get(quartiere));

            }
            //ArrayList<String> quartieri = gestoreTest.getListaQuartieriNome(nome);

            ArrayList<String> quartieri = gestoreCitta.getListaQuartieri(nome);

            for (String quartiere : quartieri) {
                out.println("<p>" + quartiere + "</p>");
            }

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
 /*
            String email = "sportelligianpiero@gmail.com";
            String add = "Via Garibaldi, 1, Torino, TO, Italia";
            String add1 = "Via San Giuseppe Benedetto Cottolengo, Torino, TO, Italia";
            String add_code = add.replace(" ", "+");
            String add_code1 = add1.replace(" ", "+");
            if (gestoreLocatore.checkLocatore(email)) {
                gestoreAnnunci.CreaAnnuncio(gestoreLocatore.getLocatore());
                gestoreAnnunci.inserisciInfoAnnuncio("Descrizione Annuncio 1", 120, new Date());
                gestoreAnnunci.inserisciInfoStanze(4, false);
                gestoreAnnunci.inserisciInfoCostiAppartamento(800, true, true);
                ArrayList<String> bagno = new ArrayList<String>();
                ArrayList<String> cucina = new ArrayList<String>();
                ArrayList<String> stanza1 = new ArrayList<String>();
                ArrayList<String> stanza2 = new ArrayList<String>();
                String url="C:\\Users\\SPORT\\Pictures\\haiHome!!\\";
                //il path poi se non funziona te lo aggiusti tu
                bagno.add(url+"bagno.jpg");
                bagno.add(url+"bagno1.jpg");
                bagno.add(url+"bagno2.jpg");
                cucina.add(url+"cucina.jpg");
                cucina.add(url+"cucina1.jpg");
                cucina.add(url+"cucina2.jpg");
                stanza1.add(url+"stanza1.jpg");
                stanza1.add(url+"singola1.jpg");
                stanza1.add(url+"singola2.jpg");
                stanza2.add(url+"stanza2.jpg");
                stanza2.add(url+"doppia1.jpg");
                stanza2.add(url+"doppia2.jpg");

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

                gestoreAnnunci.CreaAnnuncio(gestoreLocatore.getLocatore());
                gestoreAnnunci.inserisciInfoAnnuncio("Descrizione Annuncio 2", 120, new Date());
                gestoreAnnunci.inserisciInfoStanze(5, true);
                gestoreAnnunci.inserisciInfoCostiAppartamento(850, true, true);
                ArrayList<String> bagno1 = new ArrayList<String>();
                ArrayList<String> cucina1 = new ArrayList<String>();
                ArrayList<String> stanza1_1 = new ArrayList<String>();
                ArrayList<String> stanza1_2 = new ArrayList<String>();
                ArrayList<String> stanza1_3 = new ArrayList<String>();

                //il path poi se non funziona te lo aggiusti tu
                bagno1.add(url+"bagno.jpg");
                bagno1.add(url+"bagno1.jpg");
                bagno1.add(url+"bagno2.jpg");
                cucina1.add(url+"cucina.jpg");
                cucina1.add(url+"cucina1.jpg");
                cucina1.add(url+"cucina2.jpg");
                stanza1_1.add(url+"singola2.jpg");
                stanza1_1.add(url+"singola1.jpg");
                stanza1_2.add(url+"singola2.jpg");
                stanza1_2.add(url+"singola1.jpg");
                stanza1_3.add(url+"singola1.jpg");
                stanza1_3.add(url+"singola2.jpg");

                double[] latlng1 = gmb.geocodingAddress(add_code1);
                gestoreAnnunci.inserisciInfoIndirizzo("Torino", "Aurora", add1, latlng1);
                gestoreAnnunci.inserisciNuovaStanzaAccessoria("Cucina", cucina, 30);
                gestoreAnnunci.inserisciNuovaStanzaAccessoria("Bagno", bagno, 15);
                gestoreAnnunci.inserisciNuovaStanzaInAffitto("Singola", stanza1_1, 25);
                gestoreAnnunci.inserisciNuovaStanzaInAffitto("Singola", stanza1_2, 25);
                gestoreAnnunci.inserisciNuovaStanzaInAffitto("Singola", stanza1_3, 25);
                if (gestoreAnnunci.rendiAnnuncioPersistente()) {
                    System.out.println(gestoreAnnunci.toJSON());
                } else {
                    System.out.println("errore");
                }
            }*/
 /*String address="Via XX Settembre, 65, Torino, TO, Italia";
            gmb.getQuartiereByAddress(address);*/
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
