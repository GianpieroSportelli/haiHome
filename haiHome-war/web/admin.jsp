<%-- 
    Document   : admin
    Created on : 19-apr-2016, 12.11.12
    Author     : Eugenio Liso
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>HaiHome? Pannello ADMIN</title>
        <link rel="stylesheet" href="include/css/login/normalize.css">

        <link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'>

        <link rel="stylesheet" href="include/css/login/style.css">

        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

        <link rel="stylesheet" href="tutcss.css">

        <!-- Robe di UserProfile-->
        <link rel="stylesheet" href="include/css/Utente/Utente.css">
        <link rel="stylesheet" href="include/css/Utente/bootstrap.vertical-tabs.css">
        <!-- Fine Robe di UserProfile-->

        <!-- Robe di login2.jsp -->
        <link href='http://fonts.googleapis.com/css?family=PT+Sans:400,700' rel='stylesheet' type='text/css'>

        <link rel="stylesheet" href="include/css/login//reset.css"> <!-- CSS reset -->
        <link rel="stylesheet" href="include/css/login/style2.css"> <!-- Gem style -->
        <script src="include/js/login/modernizr.js"></script> <!-- Modernizr -->
        <!-- robe del login bello -->
        <link rel="stylesheet" href="include/css/login/normalize.css">
        <link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'>
        <link rel="stylesheet" href="include/css/login/style.css">
        <!-- Fine robe di login2.jsp -->

        <!-- Import script Facebook -->
        <script type="text/javascript" src="include/js/login/FacebookScript.js"></script>
        <!-- Fine Import script Facebook -->

        <style>
            body {
                padding-top: 50px;
                padding-bottom: 20px;
            }
        </style>        

    </head>
    <body>

        <header role="banner">
            <nav class="main-nav">
                <ul>
                    <%
                        String url_target_page = "index.jsp", title_target_page = "Home";

                        out.println("<li><a class='cd-signup' href='" + url_target_page + "'>"
                                + title_target_page + "</a></li>");
                    %>
                </ul>
            </nav>
        </header>

        <div class="container">
            <div class="row profile">
                <div class="col-md-3">
                    <div class="profile-sidebar">

                        <div class="profile-userpic">

                            <img src=https://theadminzone.com/styles/taz/xenforo/logo.png class="img-responsive" alt="">

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
                                <h4> PANNELLO ADMIN </h4>
                            </div>
                            <div class="profile-usertitle-job">Admin Panel</div>
                        </div>

                        <div class="profile-usermenu"> <!-- required for floating -->
                            <!-- Nav tabs -->
                            <ul class="nav nav-tabs tabs-left">
                                <li class="active"><a href="#1" data-toggle="tab">
                                        <i class="glyphicon glyphicon-list-alt"></i>
                                        Citt&aacute;</a></li>
                                <li><a href="#2" data-toggle="tab">
                                        <i class="glyphicon glyphicon-list-alt"></i>
                                        Altre funzioni TO DO</a></li>
                                <li><a href="#3" data-toggle="tab">
                                        <i class="glyphicon glyphicon-list-alt"></i>
                                        Altre funzioni TO DO</a></li>
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
                <div class="col-xs-9">
                    <!-- Tab panes -->
                    <div class="profile-content tab-content">
                        <div class="tab-pane active" id="1">
                            <form id="formCitta" accept-charset="utf-8" action="ServletController" method="POST" class="simform">
                                <input type="hidden" name="action" value="Aggiungi-Citta" />
                                <div class="sminputs">
                                    <div class="input full">
                                        <label class="string optional" for="citta">Inserisci Citt&Agrave;</label>
                                        <input style="background-color:f2f2f2" class="string optional" maxlength="255" id="citta" name="citta" placeholder="Citt&agrave;" size="50" />
                                    </div>
                                </div>
                                <div class="simform__actions"> 
                                    <a onclick="insertCitta()" class="btn btn-lg btn-success" role="button"
                                       data-toggle="popover" data-trigger="manual" data-content="" > 
                                        Inserisci
                                    </a>
                                </div> 
                            </form>
                        </div>
                        <div class="tab-pane" id="2">Annunci preferiti qui</div>
                        <div class="tab-pane" id="3">Robe Qui</div>
                    </div>
                </div>  
            </div>
        </div> 

        <script>
            function insertCitta() {
                var citta = document.getElementById("citta").value;
                alert(citta);
            }

        </script>
    </body>
</html>
