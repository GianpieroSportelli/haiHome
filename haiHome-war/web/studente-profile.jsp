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

        <!-- FILE RANDOMICO CHE FA FUNZIONARE IL CAROUSEL. VUOI SAPERE IL PERCHE'?
        AHHAHHAHAAHAHAHAHAHAHAHAHAHAHAHAH NO RIMANE RANDOM IL MOTIVO. 
        COPIATO LE PARTI ESSENZIALI IN UTENTE.CSS
        <link rel="stylesheet" href="include/css/login/reset.css"> <!-- CSS reset -->

        <!-- Robe di UserProfile-->
        <link rel="stylesheet" href="include/css/Utente/Utente.css">
        <link rel="stylesheet" href="include/css/Utente/bootstrap.vertical-tabs.css">
        <script type="text/javascript" src="include/js/userProfile/studente-profile.js"></script>
        <script type="text/javascript" src="include/js/userProfile/studente-profile-annunci.js"></script>
        <!-- Fine Robe di UserProfile-->

        <!-- SESSION -->
        <script type="text/javascript" src="include/js/search/jquery.session.js"></script>
        <!-- FINE SESSION -->



        <!-- footer css -->
        <link href="tutcss.css" rel="stylesheet">

        <link rel="stylesheet" href="include/css/login/style.css">
        <link rel="stylesheet" href="include/css/login/style2.css">  
        <link rel="stylesheet" href="include/css/login/normalize.css">
        <script src="include/js/login/modernizr.js"></script> <!-- Modernizr -->


        <!-- Inizio import header --------------------->
        <script src="include/js/header-updater.js"></script>
        <!-- Script modal login -->
        <script src="include/js/login/modal_validation_and_stuff.js"></script> 
        <script src="include/js/login/ajax_req_and_stuff.js"></script>
        <!-- Import script facebook -->
        <script type="text/javascript" src="include/js/login/FacebookScript.js"></script>
        <!-- Import script google+ -->
        <meta name="google-signin-client_id" content="495487496441-r9l7mppbotcf6i3rt3cl7fag77hl0v62.apps.googleusercontent.com"></meta>
        <script type='text/javascript' src='include/js/login/googleplus-script.js'></script>
        <script src="https://apis.google.com/js/client:platform.js?onload=startApp" async defer></script>
        <!-- end google+ login stuff -->
        <!----------------------- Fine import header -->      

    </head>
    <body>
        <%@include file="/header.jsp" %> 


        <%@include file="include/html/modalConfermaCancellazione.html"%>
        <!-- < %@include file="/header3Login.jsp" %>  -->

        <!-- Non voglio imparare HTML, notyplsbbbrb -->
        <div style="display:none"> 
            <div id="user-access"><%= session.getAttribute("user-access")%></div>
        </div>

        <div class="container">
            <div class="row profile">
                <div class="col-md-3">
                    <div class="profile-sidebar">
                        <!-- SIDEBAR USERPIC -->
                        <div class="profile-userpic">
                            <%                                if (session_exists) {
                                    out.println(
                                            "<img src='" + user_data.getString("foto") + "'"
                                            + "class='img-responsive' alt=''/>");
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
                                        out.println(user_data.getString("nome") + " " + user_data.getString("cognome"));
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
                                <li><a href="#filtriUtente" data-toggle="tab">
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
                <div class="col-xs-9" >
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
                                                <input class="form-control" id="cognome"  type="text" 
                                                       value="<%=user_data.getString("nome")%>" 
                                                       disabled="disabled"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="panel panel-default">
                                            <div class='panel-heading'>Cognome</div>
                                            <div class="panel-body">
                                                <input class="form-control" id="cognome"  type="text" 
                                                       value="<%=user_data.getString("cognome")%>" 
                                                       disabled="disabled"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!--EMAIL--> 
                                <div class="row">
                                    <!-- campo email -->
                                    <div class="col-md-6" id="rigaEmail">
                                        <div class="panel panel-default">
                                            <div class='panel-heading'>Email</div>
                                            <div class="panel-body">
                                                <input class="form-control" id="email"  type="text" 
                                                       value="<%=user_data.getString("email")%>" 
                                                       disabled="disabled"/>
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
                                                    <input id="password" class="form-control pwd" name="old-pwd" type="password" placeholder="Vecchia Password" disabled="disabled" />
                                                </div>
                                                <div class="col-md-4">
                                                    <input id="new-password" class="form-control pwd" name="pwd" type="password" placeholder="Nuova Password" />
                                                </div>
                                                <div class="col-md-4">
                                                    <input id="new-password2" class="form-control pwd" name="pwd-confirm" type="password" placeholder="Conferma Password" />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="annunci">
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="ibox float-e-margins">

                                        <div class="ibox-content" id="list-result">
                                            <div class="hr-line-dashed"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="filtriUtente"></div>
                    </div>
                </div>  
            </div>
        </div> 

        <script>
            /*
             function startTutorial() {
             
             if (filtri.length !== 0) {
             //Ho messo due variabili perche' non aggiorna la sessione in real-time
             if (!activatedTutorial) {
             <%/* if (session.getAttribute("tutorial") == null) {
                    session.setAttribute("tutorial", true);
                  */ %>
             //Analogo del thread.sleep. Ho dovuto metterlo altrimenti si bugga il primo tutorial. 
             //Il problema nasce dal fatto che i tab sono dinamici
             var millisecondsToWait = 400;
             setTimeout(function () {
             introJs().start();
             activatedTutorial = true;
             }, millisecondsToWait);
             <%// }%>
             }
             }
             }*/
        </script>

        <%@include file="/footer.jsp" %>

    </body>
</html>
