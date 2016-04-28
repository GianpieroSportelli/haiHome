<%-- 
    Document   : index.jsp
    Created on : 10-nov-2015, 16.56.24
    Author     : Eugenio Liso
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.json.JSONObject"%> 
<%@page import="java.util.ArrayList"%> 
<%@page import="javax.servlet.http.HttpSession"%>

<%
    boolean session_exists = session.getAttribute("user-type") != null;
    JSONObject user_data = null;

    if (session_exists) {
        user_data = (JSONObject) session.getAttribute("user-data");
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <title>haiHome? Profilo Utente</title>
        <!-- IMPORT NECESSARI BOOTSTRAP JS ICONE E ALTRO -->
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
        <link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'>
        <!-- FINEIMPORT NECESSARI BOOTSTRAP E ICONE -->

        <!--<link rel="stylesheet" href="include/css/login/normalize.css"> -->

        <!-- -->



        <!-- Robe di UserProfile-->
        <script src="include/js/userProfile/intro.js"></script>
        <link rel="stylesheet" href="include/css/Utente/introjs.css">
        <link rel="stylesheet" href="include/css/Utente/Utente.css">
        <link rel="stylesheet" href="include/css/Utente/bootstrap.vertical-tabs.css">
        <!-- Fine Robe di UserProfile-->

        <link rel="stylesheet" href="include/css/login/style.css">
        <link rel="stylesheet" href="include/css/login/style2.css"> <!-- Gem style 
 
        <link rel="stylesheet" href="include/css/login/normalize.css">
        
        <!--<link rel="stylesheet" href="tutcss.css">-->
        <!-- robe del login bello -->
        <!--<link rel="stylesheet" href="include/css/login/style.css">
        <!-- Fine robe di login2.jsp -->

        <!-- Robe di login2.jsp -->

        <!--<link rel="stylesheet" href="include/css/login//reset.css"> <!-- CSS reset -->
        <script src="include/js/login/modernizr.js"></script> <!-- Modernizr -->


        <!-- Import script Facebook -->
        <script type="text/javascript" src="include/js/login/FacebookScript.js"></script>
        <!-- Fine Import script Facebook -->       

    </head>
    <body>
        <%@include file="/header.jsp" %> 


        <%@include file="include/html/modalConfermaCancellazione.html"%>
        <!-- < %@include file="/header3Login.jsp" %>  -->

        <div class="container">
            <div class="row profile">
                <div class="col-md-3">
                    <div class="profile-sidebar">
                        <!-- SIDEBAR USERPIC -->
                        <div class="profile-userpic">
                            <%                                if (session_exists) {
                                    out.println(
                                            "<img src='" + user_data.getString("Foto") + "'"
                                            + "class='img-responsive' alt=''/>");
                                } else {
                                    out.println("no login no party");
                                }
                            %>
                            <!--
                            < %
                                sessione = request.getSession();
                                JSONObject datiUtente = (JSONObject) sessione.getAttribute("JSONList");

                            %>
                            <img src="< %= datiUtente.getString("Foto")%>" class="img-responsive" alt=""> -->
                        </div>
                        <!-- END SIDEBAR USERPIC -->
                        <!-- SIDEBAR USER TITLE -->
                        <div class="profile-usertitle">
                            <div class="profile-usertitle-name">
                                <% if (session_exists) {
                                        out.println(user_data.getString("Nome") + " " + user_data.getString("Cognome"));
                                    }
                                %>
                            </div>
                            <div class="profile-usertitle-job">Studente</div>
                        </div>


                        <!-- END SIDEBAR USER TITLE -->
                        <!-- SIDEBAR BUTTONS -->
                        <!-- END SIDEBAR BUTTONS -->
                        <!-- SIDEBAR MENU -->
                        <!--<div class="tabbable">
                            <ul class="nav nav-tabs">
                                <li class="active"><a href="#tab1" data-toggle="tab">Request Audit</a></li>
                                <li class=""><a href="#tab2" data-toggle="tab">Status</a></li>
                                <li class=""><a href="#tab3" data-toggle="tab">Settings</a></li>
                                <li class=""><a href="#tab4" data-toggle="tab">Help</a></li>
                            </ul>
                            <div class="tab-content">
                                <div class="tab-pane active" id="tab1">
                                    <p>Placeholder 1</p>
                                </div>
                                <div class="tab-pane" id="tab2">
                                    <p>Placeholder 2</p>
                                </div>
                                <div class="tab-pane" id="tab3">
                                    <p>Placeholder 3</p>
                                </div>
                                <div class="tab-pane" id="tab4">
                                    <p>Placeholder</p>
                                </div>
                            </div>
                        </div>
                    </div>-->

                        <div class="profile-usermenu"> <!-- required for floating -->
                            <!-- Nav tabs -->
                            <ul class="nav nav-tabs tabs-left">
                                <li class="active"><a href="#home" data-toggle="tab">
                                        <i class="glyphicon glyphicon-user"></i>
                                        Home</a></li>
                                <li><a href="#annunci" data-toggle="tab">
                                        <i class="glyphicon glyphicon-th-list"></i>
                                        Annunci preferiti</a></li>
                                <li><a href="#filtriUtente" data-toggle="tab"  onclick="startTutorial()">
                                        <i class="glyphicon glyphicon-list-alt"></i>
                                        Filtri Preferiti</a></li>
                            </ul>
                        </div>



                        <!--<div class="profile-usermenu">
                            <ul class="nav">
                                <li class="active">
                                    <a href="#">
                                        <i class="glyphicon glyphicon-home"></i>
                                        Overview </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <i class="glyphicon glyphicon-user"></i>
                                        Account Settings </a>
                                </li>
                                <li>
                                    <a href="#" target="_blank">
                                        <i class="glyphicon glyphicon-ok"></i>
                                        Tasks </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <i class="glyphicon glyphicon-flag"></i>
                                        Help </a>
                                </li>
                            </ul>
                        </div>-->
                        <!-- END MENU -->
                    </div>

                </div>
                <div data-intro="Qui puoi visualizzare i tuoi filtri memorizzati." class="col-xs-9">
                    <!-- Tab panes -->
                    <div class="profile-content tab-content">
                        <div class="tab-pane active in fade" id="home">
                            <div class="panel panel-default">
                                <div class="panel-heading"> 
                                    <p class="text-primary" style="text-align:center" >  <img src="include/css/Utente/userImage-30.png"> <span class="text-primary">Informazioni Profilo</span> </p>
                                </div>
                                <div class="panel-body">
                                    <p><span class="text-primary">Nome</span>:
                                        <% if (session_exists) {
                                                out.println(user_data.getString("Nome"));
                                            }
                                        %>
                                    </p>
                                    <hr>
                                    <p> <span class="text-primary">Cognome</span>:
                                        <% if (session_exists) {
                                                out.println(user_data.getString("Cognome"));
                                            }
                                        %>
                                    </p>
                                    <hr>
                                    <p> <span class="text-primary">Email</span>:
                                        <% if (session_exists) {
                                                out.println(user_data.getString("Email"));
                                            }
                                        %>
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="annunci">Annunci preferiti qui</div>
                        <div class="tab-pane fade" id="filtriUtente"></div>
                    </div>
                </div>  
            </div>
        </div> 

        <script>

            //Memorizzo i filtri che usciranno negli snippet
            var filtri = new Array();
            var actual;
            var n_filtri = 0;

            var FIlTRI_X_PAGE = 3;
            var n_page = 0;
            var page_filtri = new Array();

            //variabile utile per capire quale filtro cancellare
            var filtroToDelete;
            //Variabile per intro.js
            var tutorialAttivato = false;
            var activatedTutorial = false;

            function startTutorial() {

                //Ho messo due variabili perche' non aggiorna la sessione in real-time
                if (!activatedTutorial) {
            <% if (session.getAttribute("tutorial") == null) {
                    session.setAttribute("tutorial", true);
            %>
                    //Analogo del thread.sleep. Ho dovuto metterlo altrimenti si bugga il primo tutorial. 
                    //Il problema nasce dal fatto che i tab sono dinamici
                    var millisecondsToWait = 400;
                    setTimeout(function () {
                        introJs().start();
                        activatedTutorial = true;
                    }, millisecondsToWait);
            <% }%>
                }
            }

            $(document).ready(function () {
                getListaFiltriPreferiti();
                getAnnunciPreferiti();
            });


            function getAnnunciPreferiti() {
                $.post("ServletController",
                        {action: "studente-getAnnunci"},
                        function (data) {
                            console.log(data);
                        });
            }


            function add_button() {
                $("#filtriUtente").append("<div class=\"text-center \" id = \"button-div\">");
                $("#button-div").append("<div class=\"btn-group\" id=\"group-button-page\">");
                $("#group-button-page").append("<button class=\"btn btn-white\"onClick=prevpage() type=\"button\"><i class=\"glyphicon glyphicon-chevron-left\"></i></button>");
                for (i = 0; i < n_page; i++) {

                    var html = '<button class=\"btn btn-white\" onClick=selectpage(this.id) id=\"' + (i + 1) + '\"> ' + (i + 1) + '</button>';
                    $("#group-button-page").append(html);
                }
                $("#group-button-page").append("<button class=\"btn btn-white\" onClick=nextpage() type=\"button\"><i class=\"glyphicon glyphicon-chevron-right\"></i> </button>");
                $("#button-div").append("</div>");
                $("#filtriUtente").append("</div>");
            }

            function getListaFiltriPreferiti() {
                $.post("ServletController",
                        {action: "get-lista-preferiti-studente"},
                        function (responseJson) {
                            filtri = [];
                            n_page = 0;
                            n_filtri = 0;



                            $.each(responseJson, function (index, item) {
                                n_filtri += 1;
                                filtri[index] = item;
                            });
                            actual = 0;
                            n_page = n_filtri / FIlTRI_X_PAGE;


                            if (filtri.length === 0) {
                                var page_html = "<div>" +
                                        "<div class=\"panel panel-default\">" +
                                        "<div class=\"panel-heading\"> <p class=\"text-primary\"> <img src=\"include/css/Utente/Error-30.png\"> Nessun Filtro Salvato </p>"
                                        + "</div> " +
                                        "</div>" +
                                        "</div>";
                                page_filtri[0] = page_html;
                                selectpage(1);
                            } else {
                                constructFiltriPage();
                                selectpage(1);
                                add_button();
                            }

                        }
                );
            }

            function prevpage() {

                if (actual > 1) {
                    var page = actual - 1;
                    selectpage(page);
                }
            }
            function nextpage() {

                if (actual < n_page) {
                    var page = actual + 1;
                    selectpage(page);
                }
            }

            function selectpage(page) {
                if (actual === 0) {
                    $("#filtriUtente").append(page_filtri[page - 1]);
                    actual = page;
                } else if (actual !== (+page)) {
                    $("#" + (actual) + "_RESULT").before(page_filtri[page - 1]);
                    $("#" + (actual) + "_RESULT").remove();
                    actual = +page;
                }
            }

            function constructFiltriPage() {
                var page_html = '';

                for (i = 1; i < (n_page + 1); i++) {
                    page_html = '';
                    page_html += '<div class="filtri row" id =' + i + '_RESULT>';

                    for (var k = (i - 1) * FIlTRI_X_PAGE; (k < (i * FIlTRI_X_PAGE) && k < filtri.length); k++) {

                        var citta = filtri[k].Città;
                        var compresoCondominio = filtri[k].CompresoCondominio;
                        var compresoRiscaldamento = filtri[k].CompresoRiscaldamento;
                        var idFiltro = filtri[k].Id;
                        //var idStudente = filtri[k].Id_Studente;
                        var prezzo = filtri[k].Prezzo;
                        var quartieri = filtri[k].Quartieri;
                        var numeroCamere = filtri[k].NumeroCamereDaLetto;
                        var numeroLocali = filtri[k].NumeroLocali;
                        var numeroBagni = filtri[k].NumeroBagni;
                        var metratura = filtri[k].Metratura;
                        var tipoAnnuncio = filtri[k].Tipo;
                        var tipoStanza = filtri[k].TipoStanza;

                        var quartieriHTML = '';

                        for (var indice in quartieri) {
                            quartieriHTML += quartieri[indice] + " - ";
                        }

                        if (quartieriHTML !== '') {
                            //Tolgo l'ultimo -
                            quartieriHTML = quartieriHTML.substring(0, quartieriHTML.length - 2);
                            quartieriHTML = "<p> <i class=\"glyphicon glyphicon-info-sign\"></i> <span class=\"text-primary\">Quartieri</span>: " + quartieriHTML + "<hr>";

                        }

                        var glyphCondominio = '';
                        var glyphRiscaldamento = '';
                        if (compresoCondominio === true) {
                            glyphCondominio = "include/css/Utente/check-30.png";
                        } else {
                            glyphCondominio = "include/css/Utente/notChecked-30.png";
                        }

                        if (compresoRiscaldamento === true) {
                            glyphRiscaldamento = "include/css/Utente/check-30.png";
                        } else {
                            glyphRiscaldamento = "include/css/Utente/notChecked-30.png";
                        }

                        var htmlNumeroCamere = '';

                        if (numeroCamere != 0) {
                            htmlNumeroCamere = "<p> <i class=\"glyphicon glyphicon-info-sign\"></i> <span class=\"text-primary\">Numero camere da letto</span>: " + numeroCamere + "<hr>";

                        }

                        var htmlNumeroLocali = '';

                        if (numeroLocali != 0) {
                            htmlNumeroLocali = "<p> <i class=\"glyphicon glyphicon-info-sign\"></i> <span class=\"text-primary\">Numero locali</span>: " + numeroLocali + "<hr>";

                        }

                        var htmlNumeroBagni = '';

                        if (numeroBagni != 0) {
                            htmlNumeroBagni = "<p> <i class=\"glyphicon glyphicon-info-sign\"></i> <span class=\"text-primary\">Numero bagni</span>: " + numeroBagni + "<hr>";
                        }

                        var htmlmetratura = '';

                        if (metratura != 0) {
                            htmlmetratura = "<p> <i class=\"glyphicon glyphicon-info-sign\"></i> <span class=\"text-primary\"> Metratura </span>: " + metratura + "<hr>";
                        }

                        var htmlprezzo = '';
                        if (prezzo != 0) {
                            htmlprezzo = "<img src=\"include/css/Utente/euro-icon.png\"> <span class=\"text-primary\"> Prezzo massimo</span>: " + prezzo + " euro";
                        }

                        var htmltipoAnnuncio = '';

                        /**PRIMA C'ERA K AL POSTO DI IDFILTRO!!!!!!!!!!!!!!!!!!!!!!!!*/
                        /**
                         * 
                         * 
                         * 
                         * 
                         */
                        var tipoVisualizzazione;
                        if (tipoAnnuncio === "Stanza") {
                            tipoVisualizzazione = 1;
                            htmltipoAnnuncio = "<p class=\"text-primary\" style=\"text-align:center\"> <span class=\"titoloAnnuncio\"> Ricerca per stanze </span> <a onclick=\"deleteFilterModal(" + idFiltro + ")\" class=\"deleteButton btn btn-danger \"><i class=\"fa fa-trash-o\" title=\"Delete\" aria-hidden=\"true\"></i> <span class=\"sr-only\">Delete</span> Elimina </a> </p> ";
                        } else if (tipoAnnuncio === "Appartamento") {
                            tipoVisualizzazione = 2;
                            htmltipoAnnuncio = "<p class=\"text-primary\" style=\"text-align:center\"> <span class=\"titoloAnnuncio\"> Ricerca per appartamenti </span> <a onclick=\"deleteFilterModal(" + idFiltro + ")\" class=\" deleteButton btn btn-danger\"><i class=\"fa fa-trash-o\" title=\"Delete\" aria-hidden=\"true\"></i> <span class=\"sr-only\">Delete</span> Elimina </a> </p>  ";
                        } else {
                            tipoVisualizzazione = 3;
                            htmltipoAnnuncio = "<p class=\"text-primary\" style=\"text-align:center\"> <span class=\"titoloAnnuncio\"> Ricerca per appartamenti-stanze </span> <a onclick=\"deleteFilterModal(" + idFiltro + ")\" class=\" deleteButton btn btn-danger\"><i class=\"fa fa-trash-o\" title=\"Delete\" aria-hidden=\"true\"></i> <span class=\"sr-only\">Delete</span> Elimina </a> </p>  ";
                        }

                        var html = '';
                        //HTML per Appartamenti
                        if (tipoVisualizzazione === 2) {
                            if (!tutorialAttivato) {
                                html = "<div><div class=\"panel panel-default\">" + "<div class='panel-heading'>" +
                                        htmltipoAnnuncio + "</div>" +
                                        "<div data-intro=\"Cliccando su un filtro, visualizzerai gli annunci che rispettano i suoi criteri.\" class=\"panel-body\" style=\"cursor:pointer\" id=\"filtro-" + idFiltro + "\" OnClick=send_filtro(" + idFiltro + ")>" +
                                        "<p> <img src=\"include/css/Utente/Home-30.png\"> <span class=\"text-primary\"> Città </span>: " + citta + "&nbsp;" +
                                        htmlprezzo +
                                        "<hr>" +
                                        quartieriHTML +
                                        htmlNumeroLocali +
                                        htmlNumeroCamere +
                                        htmlNumeroBagni +
                                        htmlmetratura +
                                        "<p style=\"text-align:center\"> <span class=\"text-primary\"> Compreso Condominio</span>: <img src=\"" + glyphCondominio + "\"> <span class=\"text-primary\"> Compreso Riscaldamento</span>: <img src=\"" + glyphRiscaldamento + "\"></i>" +
                                        "</div>" +
                                        "</div>" +
                                        //"</div>" +
                                        "</div>";
                                tutorialAttivato = true;
                            } else {
                                html = "<div><div class=\"panel panel-default\">" + "<div class='panel-heading'>" +
                                        htmltipoAnnuncio + "</div>" +
                                        "<div class=\"panel-body\" style=\"cursor:pointer\" id=\"filtro-" + idFiltro + "\" OnClick=send_filtro(" + idFiltro + ")>" +
                                        "<p> <img src=\"include/css/Utente/Home-30.png\"> <span class=\"text-primary\"> Città </span>: " + citta + "&nbsp;" +
                                        htmlprezzo +
                                        "<hr>" +
                                        quartieriHTML +
                                        htmlNumeroLocali +
                                        htmlNumeroCamere +
                                        htmlNumeroBagni +
                                        htmlmetratura +
                                        "<p style=\"text-align:center\"> <span class=\"text-primary\"> Compreso Condominio</span>: <img src=\"" + glyphCondominio + "\"> <span class=\"text-primary\"> Compreso Riscaldamento</span>: <img src=\"" + glyphRiscaldamento + "\"></i>" +
                                        "</div>" +
                                        "</div>" +
                                        //"</div>" +
                                        "</div>";
                            }

                            //HTML per stanza
                        } else if (tipoVisualizzazione === 1) {
                            if (!tutorialAttivato) {
                                html = "<div><div class=\"panel panel-default\">" + "<div class='panel-heading'>" +
                                        htmltipoAnnuncio + "</div>" +
                                        "<div data-intro=\"Cliccando su un filtro, visualizzerai gli annunci che rispettano i suoi criteri.\" class=\"panel-body\" style=\"cursor:pointer\" id=\"filtro-" + idFiltro + "\" OnClick=send_filtro(" + idFiltro + ")>" +
                                        "<p> <img src=\"include/css/Utente/Home-30.png\"> <span class=\"text-primary\"> Città </span>: " + citta + "&nbsp;" +
                                        htmlprezzo +
                                        "<hr>" +
                                        quartieriHTML +
                                        "<p> <i class=\"glyphicon glyphicon-info-sign\"></i> <span class=\"text-primary\">Tipo stanza</span>: " + tipoStanza +
                                        "<hr>" +
                                        "<p style=\"text-align:center\"> <span class=\"text-primary\"> Compreso Condominio</span>: <img src=\"" + glyphCondominio + "\"> <span class=\"text-primary\"> Compreso Riscaldamento</span>: <img src=\"" + glyphRiscaldamento + "\"></i>" +
                                        "</div>" +
                                        "</div>" +
                                        //"</div>" +
                                        "</div>";
                                tutorialAttivato = true;
                            } else {
                                html = "<div><div class=\"panel panel-default\">" + "<div class='panel-heading'>" +
                                        htmltipoAnnuncio + "</div>" +
                                        "<div class=\"panel-body\" style=\"cursor:pointer\" id=\"filtro-" + idFiltro + "\" OnClick=send_filtro(" + idFiltro + ")>" +
                                        "<p> <img src=\"include/css/Utente/Home-30.png\"> <span class=\"text-primary\"> Città </span>: " + citta + "&nbsp;" +
                                        htmlprezzo +
                                        "<hr>" +
                                        quartieriHTML +
                                        "<p> <i class=\"glyphicon glyphicon-info-sign\"></i> <span class=\"text-primary\">Tipo stanza</span>: " + tipoStanza +
                                        "<hr>" +
                                        "<p style=\"text-align:center\"> <span class=\"text-primary\"> Compreso Condominio</span>: <img src=\"" + glyphCondominio + "\"> <span class=\"text-primary\"> Compreso Riscaldamento</span>: <img src=\"" + glyphRiscaldamento + "\"></i>" +
                                        "</div>" +
                                        "</div>" +
                                        //"</div>" +
                                        "</div>";
                            }
                        } else if (tipoVisualizzazione === 3) {
                            if (!tutorialAttivato) {
                                html = "<div><div class=\"panel panel-default\">" + "<div class='panel-heading'>" +
                                        htmltipoAnnuncio + "</div>" +
                                        "<div data-intro=\"Cliccando su un filtro, visualizzerai gli annunci che rispettano i suoi criteri.\" class=\"panel-body\" style=\"cursor:pointer\" id=\"filtro-" + idFiltro + "\" OnClick=send_filtro(" + idFiltro + ")>" +
                                        "<p> <img src=\"include/css/Utente/Home-30.png\"> <span class=\"text-primary\"> Città </span>: " + citta + "&nbsp;" +
                                        htmlprezzo +
                                        "<hr>" +
                                        quartieriHTML +
                                        "<p style=\"text-align:center\"> <span class=\"text-primary\"> Compreso Condominio</span>: <img src=\"" + glyphCondominio + "\"> <span class=\"text-primary\"> Compreso Riscaldamento</span>: <img src=\"" + glyphRiscaldamento + "\"></i>" +
                                        "</div>" +
                                        "</div>" +
                                        //"</div>" +
                                        "</div>";
                                tutorialAttivato = true;
                            } else {
                                html = "<div><div class=\"panel panel-default\">" + "<div class='panel-heading'>" +
                                        htmltipoAnnuncio + "</div>" +
                                        "<div class=\"panel-body\" style=\"cursor:pointer\" id=\"filtro-" + idFiltro + "\" OnClick=send_filtro(" + idFiltro + ")>" +
                                        "<p> <img src=\"include/css/Utente/Home-30.png\"> <span class=\"text-primary\"> Città </span>: " + citta + "&nbsp;" +
                                        htmlprezzo +
                                        "<hr>" +
                                        quartieriHTML +
                                        "<p style=\"text-align:center\"> <span class=\"text-primary\"> Compreso Condominio</span>: <img src=\"" + glyphCondominio + "\"> <span class=\"text-primary\"> Compreso Riscaldamento</span>: <img src=\"" + glyphRiscaldamento + "\"></i>" +
                                        "</div>" +
                                        "</div>" +
                                        //"</div>" +
                                        "</div>";
                            }
                        }
                        page_html += html;
                    }

                    page_html += "</div>";
                    page_filtri[i - 1] = page_html;
                }

            }



            //I BOTTONI CON LE X RICHIAMANO QUESTO METODO, MI SALVO L'ID DEL FILTRO DA ELIMINARE E VISUALIZZO 
            //IL MODAL PER LA CANCELLAZIONE
            function deleteFilterModal(idFiltro) {
                filtroToDelete = idFiltro;

                //IL MODAL VIENE CARICATO NELL'INCLUDE DELLA PAGINA
                $('#modalCancellazione').modal('show');
            }

            //METODO RICHIAMATO DAL MODAL QUANDO SPINGI SI
            function deleteFilter() {
                $.post("ServletController",
                        {action: "Ricerca-deleteFiltro", filtroID: filtroToDelete},
                        function (data) {
                            if (data == "OK") {
                                //Resetto tutto
                                $('#filtriUtente').empty();
                                getListaFiltriPreferiti();
                            } else {
                                //Errore
                                alert(data);
                            }
                        });
            }

            //Richiama un filtro di ricerca
            function send_filtro(idFiltro) {

                $.post("ServletController",
                        {action: "Ricerca-setFiltro", ID: idFiltro},
                        function (data) {
                            if (data == "true") {
                                var url = "/haiHome-war/search-page.jsp";
                                window.open(url);
                            } else
                                alert('ERRORE');
                        });
                /*
                 var annuncio = annunci[k];
                 console.log(annuncio);
                 var url = "/haiHome-war/ServletController";
                 var url2 = "/haiHome-war/dettagliAnnuncio.jsp";
                 var json = JSON.stringify(annuncio);
                 console.log(k);
                 console.log(json);
                 $.session.set('dettagli', json);
                 window.open(url2);*/
            }
        </script>

        <%@include file="/footer.jsp" %>

    </body>
</html>
