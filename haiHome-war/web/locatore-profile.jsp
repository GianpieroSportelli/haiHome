

<%@page import="org.json.JSONObject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    boolean session_exists = session.getAttribute("user-type") != null;
    JSONObject user_data = null;

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

        <script type="text/javascript" src="include/js/locatore-profile.js"></script>

        <style>
            body {
                padding-top: 50px;
                padding-bottom: 20px;
            }
        </style>
    </head>
    <body>
        <%@include file="/header.jsp" %>

        <div id="user-access"><%= session.getAttribute("user-access")%></div>


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
                                <li><a href="#filtri" data-toggle="tab">
                                        <i class="glyphicon glyphicon-list-alt"></i>
                                        Boh
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
                        <div class="tab-pane fade in active" id="home">
                            <div class="edit-buttons">
                                <a href="#0" id="edit">
                                    <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                                    Modifica informazioni
                                </a>
                                <a href="#0" id="save-edit" style="display: none">
                                    <span class="glyphicon glyphicon-saved" aria-hidden="true"></span>
                                    Salva modifiche 
                                </a>
                                <a href="#0" id="cancel-edit" style="display: none">
                                    <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                                    Annulla modifiche
                                </a>
                            </div>
                            <div id="tbody">
                                <div class="trow">
                                    <div class="field-name">Nome: </div>
                                    <div class="field-value"> <%= user_data.getString("nome")%></div>
                                </div>
                                <div class="trow">
                                    <div class="field-name">Cognome: </div>
                                    <div class="field-value"> <%= user_data.getString("cognome")%></div>
                                </div>
                                <div class="trow">
                                    <div class="field-name">Email: </div>
                                    <div class="field-value"> <%= user_data.getString("email")%></div>
                                </div>
                                <div id="rigapwd" class="trow">
                                    <div class="field-name">Password: </div>
                                    <div class="field-value"> 
                                        <input name="old-pwd" type="password" placeholder="*****************"/>
                                        <div id="modify-pwd-stuff" style="display: none;">
                                            <input name="pwd" type="password" placeholder="Nuova password...">
                                            <input name="pwd-confirm" type="password" placeholder="Conferma password..."/>
                                        </div>
                                    </div>
                                </div>
                                <div class="trow">
                                    <div class="field-name">Telefono: </div>
                                    <div class="field-value"> 
                                        <input name="phone" type="text" value="<%= user_data.getString("telefono")%>" 
                                               disabled="disabled" placeholder="Aggiungi il tuo numero di telefono"
                                               style="border: none;border-color: transparent;background: transparent;"/>
                                    </div>
                                </div>
                                <div class="trow">
                                    <div class="field-name">Descrizione</div>
                                    <div class="field-value">
                                        <textarea name="description" rows="4" cols="50" maxlength="255"
                                                  disabled="disabled" placeholder="Aggiungi una breve descrizione di te"
                                                  style="border: none;border-color: transparent;background: transparent;"><%=user_data.getString("descrizione")%></textarea>
                                    </div>
                                </div>




                            </div>


                            <%--
                            <table class="table table-user-information">
                                <thead> 
                                <div id="edit-buttons" align="right">
                                    <a href="#0" id="edit">
                                        <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                                        Modifica informazioni
                                    </a>
                                    <a href="#0" id="save-edit" style="display: none">
                                        <span class="glyphicon glyphicon-saved" aria-hidden="true"></span>
                                        Salva modifiche 
                                    </a>
                                    <a href="#0" id="cancel-edit" style="display: none">
                                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                                        Annulla modifiche
                                    </a>
                                </div>
                                </thead> -->
                            
                            <tbody>
                                <tr>
                                    <td>Nome: </td>
                                    <td> 
                                        <%= user_data.getString("nome")%>

                                    </td>
                                </tr>
                                <tr>
                                    <td>Cognome:</td>
                                    <td>
                                        <%= user_data.getString("cognome")%>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Email: </td>
                                    <td>
                                        <%= user_data.getString("email")%>
                                    </td>
                                </tr>
                                <!-- soluzione oscena ma momentanea... -->

                                <tr id="rigapwd" style="display:none">
                                    <td>Password</td>
                                    <td><div id="merdaa">********</div>
                                        <div id="modify-pwd-stuff" style="display: none">
                                            <input name="old-pwd" type="password" placeholder="Vecchia password..."/>
                                            <input name="pwd" type="password" placeholder="Nuova password...">
                                            <input name="pwd-confirm" type="password" placeholder="Conferma password..."/>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Numero di telefono: </td>
                                    <td>
                                        <input name="phone" type="text" value="<%= user_data.getString("telefono")%>" 
                                               disabled="disabled" placeholder="Aggiungi il tuo numero di telefono"
                                               style="border: none;border-color: transparent;background: transparent;"/>
                                    </td>
                                </tr>

                                <tr>
                                    <td>Descrizione</td>
                                    <td> 
                                        <textarea name="description" rows="4" cols="50" maxlength="255"
                                                  disabled="disabled" placeholder="Aggiungi una breve descrizione di te"
                                                  style="border: none;border-color: transparent;background: transparent;"><%=user_data.getString("descrizione")%></textarea>

                                    </td>
                                </tr>
                            </tbody>
                            </table> --%>
                        </div>
                        <div class="tab-pane fade" id="annunci">
                            I tuoi annunci
                        </div>
                        <div class="tab-pane fade" id="filtri">
                            Tab random
                        </div>
                    </div> 
                </div>  
            </div>
        </div>
    </body>
</html>
