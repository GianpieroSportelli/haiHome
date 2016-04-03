<%-- 
    Document   : locatore-profile
    Created on : 3-apr-2016, 0.36.09
    Author     : nico
--%>

<%@page import="org.json.JSONObject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    boolean session_exists = session.getAttribute("user-type") != null;
    JSONObject user_data = (JSONObject) session.getAttribute("user-data");
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
        <%
            if (session_exists) {
                out.println("Ti sei appena loggato come locatore <br/>");
                out.println("Benvenuto " + user_data.getString("nome") + " " + user_data.getString("cognome"));
                out.println("Ulteriori funzionalit&agrave; saranno disponibili nelle prossime release! <br/>");
            } else {
                out.println("sessione inesistente");
            }
        %>

        <div class="container">
            <div class="row profile">
                <div class="col-md-3">
                    <div class="profile-sidebar">
                        <!-- SIDEBAR USERPIC -->
                        <div class="profile-userpic">
                            <%
                                if (session_exists) {
                                    
                                    out.println(
                                            "<img src='" + user_data.getString("fotoProfilo") + "'"
                                            + "class='img-responsive' alt=''/>"); 
                                } else {
                                    out.println("no login no party");
                                }
                            %>

                        </div>
                        <!-- END SIDEBAR USERPIC -->
                        <!-- SIDEBAR USER TITLE -->
                        <div class="profile-usertitle">
                            <div class="profile-usertitle-name">
                                <%
                                    if (session_exists) {
                                        out.println(user_data.getString("nome") + " " + user_data.getString("cognome"));
                                    }
                                %>
                                <!--  < % = user_data.getString("Nome") + " " + user_data.getString("Cognome")%> -->
                            </div>
                            <div class="profile-usertitle-job">

                            </div>
                        </div>


                        <div class="profile-usermenu"> <!-- required for floating -->
                            <!-- Nav tabs -->
                            <ul class="nav nav-tabs tabs-left">
                                <li class="active"><a href="#home" data-toggle="tab">
                                        <i class="glyphicon glyphicon-user"></i>
                                        Home</a></li>
                                <li><a href="#annunci" data-toggle="tab">
                                        <i class="glyphicon glyphicon-th-list"></i>
                                        Annunci preferiti</a></li>
                                <li><a href="#filtri" data-toggle="tab">
                                        <i class="glyphicon glyphicon-list-alt"></i>
                                        Filtri Preferiti</a></li>
                            </ul>
                        </div>



                        <!-- END MENU -->
                    </div>

                </div>
                <div class="col-xs-9">
                    <!-- Tab panes -->
                    <div class="profile-content tab-content">
                        <div class="tab-pane active" id="home">
                            <table class="table table-user-information">
                                <tbody>
                                    <tr>
                                        <td>Nome: </td>
                                        <td> 
                                            <!-- < % = user_data.getString("nome")%> -->
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Cognome:</td>
                                        <td> 
                                            <!-- < %= user_data.getString("cognome")%> -->
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Email: </td>
                                        <td>
                                            <!-- < %= user_data.getString("email")%> -->
                                        </td>
                                    </tr>

                                    <tr>
                                        <td> </td>
                                        <td> </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="tab-pane" id="annunci">Annunci preferiti qui</div>
                        <div class="tab-pane" id="filtri">Filtri utente qui</div>
                    </div>
                </div>  
            </div>
        </div>


    </body>
</html>
