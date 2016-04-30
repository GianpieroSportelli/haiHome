

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

        <script type="text/javascript" src="include/js/locatore/visualizzazione-annunci.js"></script>
        <script type="text/javascript" src="include/js/locatore/profilo-locatore.js"></script>

        
        
        <script src="include/js/login/modal_validation_and_stuff.js"></script> <!-- Gem jQuery -->
        <script src="include/js/login/ajax_req_and_stuff.js"></script>
        
        <script src='include/js/header-updater.js'></script>

    </head>
    <body>
        <%@include file="/header.jsp" %>

        <!-- l'html è bello! imparalo NOW -->
        <div style="display:none"> 
            <div id="user-access"><%= session.getAttribute("user-access")%></div>
            <div id="annunci-length"><%= session.getAttribute("num-annunci")%></div>
            <div id="visibili-length"><%= session.getAttribute("num-visibili")%></div>
            <div id="archiviati-length"><%= session.getAttribute("num-archiviati")%></div>
        </div>

        <div class="container">
            <div class="row profile">
                <div class="col-md-3">
                    <div class="profile-sidebar">
                        <!-- SIDEBAR USERPIC -->
                        <div class="profile-userpic"> 
                            <% out.println(
                                        "<img src='" + user_data.getString("fotoProfilo") + "'"
                                        + "class='img-responsive' alt=''/>"
                                );

                            %>

                        </div>
                        <!-- END SIDEBAR USERPIC -->
                        <!-- SIDEBAR USER TITLE -->
                        <div class="profile-usertitle">
                            Info su di me: 
                            <div class="profile-usertitle-name">
                                <%=user_data.getString("nome") + " " + user_data.getString("cognome")%>

                            </div>
                            <div class="profile-usertitle-job"><%--
                                <span>
                                    <span class="glyphicon glyphicon-envelope"></span>
                                    <%=user_data.getString("email")%>
                                </span> --%>
                            </div>
                        </div>

                        <div class="profile-usermenu"> <!-- required for floating -->
                            <!-- Nav tabs -->
                            <ul class="nav nav-tabs tabs-left">
                                <li class="active"><a href="#home" data-toggle="tab">
                                        <i class="glyphicon glyphicon-user"></i>
                                        Profilo
                                    </a>
                                </li>
                                <li><a href="#annunci" data-toggle="tab">
                                        <i class="glyphicon glyphicon-th-list"></i>
                                        I tuoi annunci
                                    </a>
                                </li>
                                <li><a href="#archivio" data-toggle="tab">
                                        <i class="glyphicon glyphicon-list-alt"></i>
                                        Archivio annunci
                                    </a>
                                </li>
                            </ul>
                        </div> 
                        <!-- END MENU -->
                    </div>

                </div>
                <div class="col-xs-9">
                    <!-- Tab panes -->
                    <div class="profile-content tab-content">
                        <div class="tab-pane fade in active panel panel-default" id="home">
                            <div class="edit-buttons panel-heading">
                                <a href="#0" id="edit" class="edit">
                                    <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                                    Modifica informazioni
                                </a>
                                <a href="#0" id="cancel-edit" class="edit" style="display: none">
                                    <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                                    Annulla modifiche
                                </a>
                                <a href="#0" id="save-edit" class="edit" style="display: none">
                                    <span class="glyphicon glyphicon-saved" aria-hidden="true"></span>
                                    Salva modifiche 
                                </a>

                            </div>
                            <div class="tbody panel-body">
                                <div class="trow">
                                    <div class="field-name">
                                        Nome:
                                    </div>
                                    <div class="field-value">
                                        <%=user_data.getString("nome")%>
                                    </div> 
                                </div>
                                <div class="trow">
                                    <div class="field-name">Cognome: </div>
                                    <div class="field-value"><%=user_data.getString("cognome")%></div>
                                </div>
                                <div class="trow">
                                    <div class="field-name">Email: </div>
                                    <div class="field-value"><%=user_data.getString("email")%></div>
                                </div>
                                <div id="rigapwd" class="trow form-group has-error">
                                    <div class="field-name">Password: </div>
                                    <div class="field-value"> 
                                        <input class="form-control pwd" name="old-pwd" type="password" placeholder="*****************" disabled="disabled"/>
                                        <div id="modify-pwd-stuff" class="form-group has-error">
                                            <input class="form-control pwd" name="pwd" type="password" placeholder="Nuova password...">
                                            <input class="form-control pwd" name="pwd-confirm" type="password" placeholder="Conferma password..."/>
                                        </div>
                                    </div>
                                </div>
                                <div class="trow form-group">
                                    <div class="field-name">
                                        <label for="telefono" class="control-label">Telefono</label>
                                    </div>
                                    <div class="field-value"> 
                                        <input class="form-control" id="telefono" name="phone" type="text" value="<%=user_data.getString("telefono")%>" 
                                               disabled="disabled" placeholder="..."/>
                                    </div> 
                                </div>
                                <div class="trow form-group ">
                                    <div class="field-name">
                                        <label for="descrizione" class="control-label">Descrizione</label>
                                    </div>
                                    <div class="field-value textwrapper">
                                        <textarea class="form-control" id="descrizione" name="description" rows="5" cols="50" maxlength="255" disabled="disabled" 
                                                  placeholder="Scrivi qualcosa su di te"><%=user_data.getString("descrizione")%></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="annunci">
                            <div style="display:block">
                                <a href="Annunci_JSP/InserimentoAnnunci.jsp" class="btn btn-primary">
                                    Inserisci nuovo annuncio
                                </a>
                            </div>
                            <div id="annunci-content">
                                <!-- contenuto caricato tramite ajax -->
                            </div>
                            <div>
                                <ul class="pager">
                                    <li>
                                        <a href='#0' id='prev_page'>
                                            Prev<!--<span class='glyphicon glyphicon-menu-left'></span>-->
                                        </a>
                                    </li>
                                    <li>
                                        <span id="num_page">1</span></li>
                                    <li>
                                        <a href='#0' id='next_page'>
                                            Next<!--<span class='glyphicon glyphicon-menu-right'></span>-->
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="archivio">
                            <div id="archivio-content">
                                <!-- contenuto caricato tramite ajax -->
                            </div>
                            <div>
                                <ul class="pager">
                                    <li>
                                        <a href='#0' id='archivio_prev_page'>
                                            Prev
                                        </a>
                                    </li>
                                    <li>
                                        <span id="archivio_num_page">1</span>
                                    <li>
                                        <a href='#0' id='archivio_next_page'>
                                            Next
                                        </a>
                                    </li>
                                </ul>
                                <!--
                                <a href='#0' id='archivio_prev_page' class="btn">
                                    <span class='glyphicon glyphicon-menu-left'></span>
                                </a>
                                <span id="archivio_num_page">1</span>
                                <a href='#0' id='archivio_next_page' class="btn">
                                    <span class='glyphicon glyphicon-menu-right'></span>
                                </a> -->
                            </div>
                        </div>
                    </div> 
                </div>  
            </div>
        </div>
    </body>
</html>
