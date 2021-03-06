<%@page import="org.json.JSONObject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String user_type = (String) session.getAttribute("user-type"); 
    String img = null; 
    
    if (user_type != null && user_type.equalsIgnoreCase("locatore")) {
        JSONObject user_data = (JSONObject) session.getAttribute("user-data");
        img = user_data.getString("fotoProfilo");
    } else {
        out.print("<script>alert('Devi loggarti come locatore per poter accedere a questa pagina!');"
                + "window.location.replace('index.jsp');</script>");

    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>haiHome? - Profilo Locatore </title>

        
        <link rel="stylesheet" href="include/css/header.css">
        
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

        <!-- INIZIO footer css -->
        <link href="include/css/footer/footer.css" rel="stylesheet">
        <!-- FINE footer css -->


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
        
        <!-- SESSION -->
        <script type="text/javascript" src="include/js/search/jquery.session.js"></script>
        <!-- FINE SESSION -->

        <script type="text/javascript" src="include/js/jquery.confirm.min.js"></script>

        <script type="text/javascript" src="include/js/userProfile/locatore-profile.js"></script>

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
                            <img src="<%= img %>" class="img-responsive" alt=""/>
                        </div>
                        <!-- END SIDEBAR USERPIC -->
                        <!-- SIDEBAR USER TITLE -->
                        <div class="profile-usertitle">
                            <div id='nomeLocatore' class='profile-usertitle-name'></div>
                            <div class="profile-usertitle-job">
                                Stato locatore: 
                                <span id="status-locatore"></span>
                                <!--<a id="info-status" href="#0"><span class="glyphicon glyphicon-info-sign" style="font-size: 0.8em;"></span></a>-->
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
                                        <i class="glyphicon glyphicon-star"></i>
                                        I tuoi annunci 
                                        <span id="num-annunci-visibili"></span>
                                    </a>
                                </li>
                                <li><a href="#archivio" data-toggle="tab">
                                        <i class="glyphicon glyphicon-pushpin"></i>
                                        Archivio annunci
                                        <span id="num-annunci-archiviati"></span>
                                    </a>
                                </li>
                                <li>
                                    <a href="#oscurati" data-toggle="tab">
                                        <i class="glyphicon glyphicon-flag"></i>
                                        Annunci oscurati
                                        <span id="num-annunci-oscurati"></span>
                                    </a>
                                </li>
                                <li>
                                    <a id="button-new-annuncio" href="IA0-InserimentoAnnunci.jsp" class="btn btn-primary">
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
                        <div class="tab-pane fade in active panel panel-primary" id="home">
                            <div class="panel-heading">
                                Info
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="panel panel-default">
                                            <div class='panel-heading  panel-heading-custom'>
                                                Nome
                                            </div>
                                            <div class="panel-body">
                                                <input class="form-control" id="nome"  type="text" 
                                                       value="" disabled="disabled"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="panel panel-default">
                                            <div class='panel-heading'>Cognome</div>
                                            <div class="panel-body">
                                                <input class="form-control" id="cognome"  type="text" 
                                                       value="" disabled="disabled"/>
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
                                                       value="" disabled="disabled"/>
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
                                                       value="" disabled="disabled" placeholder="Inserisci un tuo contatto telefonico..."/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <div id="panel-descrizione" class="panel panel-default editable">
                                            <div class="panel-heading">
                                                Descrizione
                                                <%@include file="include/html/edit-buttons.html" %>
                                            </div>
                                            <div class="panel-body has-feedback">
                                                <textarea class="form-control" id="descrizione" 
                                                          name="description" rows="8" cols="50" 
                                                          maxlength="255" disabled="disabled" style="resize:none"
                                                          placeholder="Scrivi qualcosa su di te"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div id="panel-password" class="panel panel-default editable">
                                            <div class="panel-heading">
                                                Password
                                                <%@include file="include/html/edit-buttons.html" %>
                                            </div>
                                            <div class="panel-body has-feedback">
                                                <input id="password" class="form-control" name="old-pwd" type="password" placeholder="Inserisci la password corrente..." disabled="disabled" style="display:none"/>
                                                <input id="new-password" class="form-control" name="pwd" type="password" placeholder="Inserisci la nuova password..." disabled="disabled" style="display:none"/>
                                                <input id="new-password2" class="form-control" name="pwd-confirm" type="password" placeholder="Conferma la nuova password..." disabled="disabled" style="display:none"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>            
                            </div>
                        </div>
                        <div class="tab-pane fade" id="annunci">
                            <div class="panel panel-primary">
                                <div class="panel-heading">I tuoi annunci</div>
                                <div id="annunci-content" class="panel-body">
                                    <!-- contenuto caricato tramite ajax -->
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="archivio">
                            <div class="panel panel-primary">
                                <div class="panel-heading">Archivio annunci</div>
                                <div id="archivio-content" class="panel-body">
                                    <!-- contenuto caricato tramite ajax -->
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="oscurati">
                            <div class="panel panel-primary">
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
        <%@include file="/footer2.jsp" %>
    </body>
</html>
