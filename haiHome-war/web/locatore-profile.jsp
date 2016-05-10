

<%@page import="org.json.JSONObject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    boolean session_exists = session.getAttribute("user-type") != null;
    JSONObject user_data = null;

    /* da convertire in controllo lato client, questo è brutto come la merda */
    if (session_exists) {
        user_data = (JSONObject) session.getAttribute("user-data");
    } else {
        out.print("<script>alert('You have to log into your account');"
                + "window.location.replace('index.jsp');</script>");

    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>haiHome? - Profilo Locatore </title>

        <link rel="stylesheet" href="include/css/login/normalize.css">

        <link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'>

        <link rel="stylesheet" href="include/css/login/style.css">


        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

        <link rel="stylesheet" href="tutcss.css">

        <!-- Robe di UserProfile-->
        <link rel="stylesheet" href="include/css/Utente/Utente.css"> 
        <link rel="stylesheet" href="include/css/Utente/bootstrap.vertical-tabs.css"> 
        <link rel="stylesheet" href="include/css/profiles/locatore.css">
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
        <!--
                <script type="text/javascript" src="include/js/locatore/visualizzazione-annunci.js"></script> -->
        
        <!-- SESSION -->
        <script type="text/javascript" src="include/js/search/jquery.session.js"></script>
        <!-- FINE SESSION -->
        
        <script type="text/javascript" src="include/js/jquery.confirm.min.js"></script>
                
        <script type="text/javascript" src="include/js/locatore/profilo-locatore.js"></script>

        <script src="include/js/login/modal_validation_and_stuff.js"></script> <!-- Gem jQuery -->
        <script src="include/js/login/ajax_req_and_stuff.js"></script>

        <script src='include/js/header-updater.js'></script>

    </head>
    <body>
        <%@include file="/header.jsp" %>        
        
        <div class="container">
            <div class="row profile">
                <div class="col-md-3">
                    <div class="profile-sidebar">
                        <!-- SIDEBAR USERPIC -->
                        <div class="profile-userpic"> 
                            <img src="<%= user_data.getString("fotoProfilo")%>" class="img-responsive" alt=""/>
                        </div>
                        <!-- END SIDEBAR USERPIC -->
                        <!-- SIDEBAR USER TITLE -->
                        <div class="profile-usertitle">
                            <div id='nomeLocatore' class='profile-usertitle-name'></div>
                            <div class="profile-usertitle-job">
                                Stato locatore: 
                                <span id="status-locatore"> <!-- class="profile-usertitle-name" -->

                                    <%--                                <%=user_data.getString("nome") + " " + user_data.getString("cognome")%> --%>

                                </span>
                                <span class="glyphicon glyphicon-info-sign" style="font-size: 0.8em;"></span>
                                <%--
                            <span>
                                <span class="glyphicon glyphicon-envelope"></span>
                                <%=user_data.getString("email")%>
                            </span> --%>
                            </div>
                        </div>

                        <div class="profile-usermenu"> <!-- required for floating -->
                            <!-- Nav tabs -->
                            <ul class="nav nav-tabs tabs-left">
                                <li class="active">
                                    <a href="#home" data-toggle="tab">
                                        <i class="glyphicon glyphicon-user"></i>
                                        Profilo
                                    </a>
                                </li>
                                <li>
                                    <a href="#annunci" data-toggle="tab">
                                        <i class="glyphicon glyphicon-th-list"></i>
                                        I tuoi annunci 
                                        <span id="num-annunci-visibili"></span>
                                    </a>
                                </li>
                                <li><a href="#archivio" data-toggle="tab">
                                        <i class="glyphicon glyphicon-list-alt"></i>
                                        Archivio annunci
                                        <span id="num-annunci-archiviati"></span>
                                    </a>
                                </li>
                                <li>
                                    <a href="#oscurati" data-toggle="tab">
                                        <i class="glyphicon glyphicon-list-alt"></i>
                                        Annunci oscurati
                                        <span id="num-annunci-oscurati"></span>
                                    </a>
                                </li>
                                <li>
                                    <a href="IA0-InserimentoAnnunci.jsp" class="btn btn-danger">
                                        Inserisci nuovo annuncio
                                    </a>
                                </li>
                            </ul>
                        </div> 
                    </div>
                </div>

                <div class="col-xs-9">
                    <!-- Tab panes -->
                    <div class="profile-content tab-content">
                        <div class="tab-pane fade in active panel panel-default" id="home">
                            <div class="panel-heading">
                                Info
                            </div>
                            <div class="tbody panel-body">
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="panel panel-default">
                                            <div class='panel-heading  panel-heading-custom'>
                                                Nome
                                            </div>
                                            <div class="panel-body">
                                                <input class="form-control" id="nome"  type="text" 
                                                       value="" 
                                                       disabled="disabled"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="panel panel-default">
                                            <div class='panel-heading'>Cognome</div>
                                            <div class="panel-body">
                                                <input class="form-control" id="cognome"  type="text" 
                                                       value="" 
                                                       disabled="disabled"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- EMAIL e TELEFONO --> 
                                <div class="row">
                                    <!-- campo email -->
                                    <div class="col-md-6">
                                        <div class="panel panel-default">
                                            <div class='panel-heading'>Email</div>
                                            <div class="panel-body">
                                                <input class="form-control" id="email"  type="text" 
                                                       value="" 
                                                       disabled="disabled"/>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- TELEFONO -->
                                    <div class="col-md-6">
                                        <div id="panel-telefono" class="panel panel-default editable">
                                            <div class='panel-heading'>
                                                Telefono
                                                <%@include file="include/html/edit-buttons.html" %>
                                            </div>
                                            <div class="panel-body has-feedback">
                                                <input class="form-control" id="telefono" 
                                                       name="phone" type="text" 
                                                       value="" 
                                                       disabled="disabled" placeholder="..."/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row" id="rigapwd"> 
                                    <!-- campo PASSWORD -->
                                    <div class="col-md-12">
                                        <div id="panel-password" class="panel panel-default editable">
                                            <div class='panel-heading  panel-heading-custom'>
                                                Password
                                                <%@include file="include/html/edit-buttons.html" %>
                                            </div>
                                            <div class="panel-body">
                                                <div class="col-md-4">
                                                    <input id="password" class="form-control pwd" name="old-pwd" type="password" placeholder="Vecchia password" disabled="disabled" />
                                                </div>
                                                <div class="col-md-4">
                                                    <input id="new-password" class="form-control pwd" name="pwd" type="password" placeholder="Nuova password..." />
                                                </div>
                                                <div class="col-md-4">
                                                    <input id="new-password2" class="form-control pwd" name="pwd-confirm" type="password" placeholder="Conferma password..." />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12">
                                        <!-- Campo DESCRIZIONE -->
                                        <div id="panel-descrizione" class="panel panel-default editable">
                                            <div class='panel-heading'>
                                                Descrizione
                                                <%@include file="include/html/edit-buttons.html" %>
                                            </div>
                                            <div class="panel-body">
                                                <textarea class="form-control" id="descrizione" 
                                                          name="description" rows="5" cols="50" 
                                                          maxlength="255" disabled="disabled" 
                                                          placeholder="Scrivi qualcosa su di te"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="annunci">
                            <div class="panel panel-default">
                                <div class="panel-heading">Annunci</div>
                                <div id="annunci-content" class="panel-body">
                                    <!-- contenuto caricato tramite ajax -->
                                </div>
                            </div>
                            <!--
                            <div style="display:block">
                                <a href="IA0-InserimentoAnnunci.jsp" class="btn btn-primary">
                                    Inserisci nuovo annuncio
                                </a>
                            </div> --><!--
                            <div id="annunci-content">
                                
                            </div> -->
                            <!--
                            <div>
                                <ul id='pager-visibili' class="pager">
                                    <li>
                                        <a href='#0' id='prev_page'>
                                            Prev
                                        </a>
                                    </li>
                                    <li>
                                        <span id="num_page"></span></li>
                                    <li>
                                        <a href='#0' id='next_page'>
                                            Next
                                        </a>
                                    </li>
                                </ul>
                            </div> -->
                        </div>
                        <div class="tab-pane fade" id="archivio">
                            <div class="panel panel-default">
                                <div class="panel-heading">Archivio annunci</div>
                                <div id="archivio-content" class="panel-body">
                                    <!-- contenuto caricato tramite ajax -->
                                </div>
                            </div>

                            <!--
                            <div>
                                <ul id='pager-archivio' class="pager">
                                    <li>
                                        <a href='#0' id='archivio_prev_page'>
                                            Prev
                                        </a>
                                    </li>
                                    <li>
                                        <span id="archivio_num_page"></span>
                                    <li>
                                        <a href='#0' id='archivio_next_page'>
                                            Next
                                        </a>
                                    </li>
                                </ul>
                            </div> -->
                        </div>
                        <div class="tab-pane fade" id="oscurati">
                            <div class="panel panel-default">
                                <div class="panel-heading">Annunci oscurati</div>
                                <div id="oscurati-content" class="panel-body">
                                    <!-- contenuto caricato tramite ajax -->
                                </div>
                            </div>

                        </div>
                    </div> 
                </div>  
            </div>
        </div>
    </body>
</html>
