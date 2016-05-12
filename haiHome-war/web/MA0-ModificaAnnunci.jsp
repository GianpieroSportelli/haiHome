<%-- 
    Document   : MA0-ModificaAnnunci
    Created on : May 4, 2016, 2:11:19 PM
    Author     : giacomocavallo
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <!--<meta name="google-signin-client_id" content="495487496441-r9l7mppbotcf6i3rt3cl7fag77hl0v62.apps.googleusercontent.com">
        <!-- meta tag googleplus login-->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
        
        
        <!-- jQuery library -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>

        <!-- Latest compiled JavaScript -->
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <!-- FINE caricamento bootstrap mediante MaxCDN -->


        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
        <!--Tema bootstrap -->
        
        <!--INIZIO - Form ajax plugin -->
        <script src="http://malsup.github.com/jquery.form.js"></script> 
        <!--FINE- Form ajax plugin -->
 
        <!-- Datapicker JQuery-->
        <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
        <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">

        <!-- roba utile al footer e al header -->
        
        <!-- google+ login stuff -->
        <meta name="google-signin-client_id" content="495487496441-r9l7mppbotcf6i3rt3cl7fag77hl0v62.apps.googleusercontent.com"></meta>
        <script type='text/javascript' src='include/js/login/googleplus-script.js'></script>
        <script src="https://apis.google.com/js/client:platform.js?onload=startApp" async defer></script>
        <!-- end google+ login stuff -->
        
        <!-- Inizio import header --------------------->
        <script src="include/js/header-updater.js"></script>
        <!-- Script modal login -->
        <script src="include/js/login/modal_validation_and_stuff.js"></script> 
        <script src="include/js/login/ajax_req_and_stuff.js"></script>
        <script src="include/js/login/modernizr.js"></script> <!-- Modernizr -->
        
        <!-- CSS log-in modal -->
        <link rel="stylesheet" href="include/css/login/style2.css"> <!-- se viene tolto: sfondo rosa Gem style -->
        <link rel="stylesheet" href="include/css/login/style.css">  <!-- -->

         <!-- Import script Facebook -->
        <script type="text/javascript" src="include/js/login/FacebookScript.js"></script>
        <!-- Fine Import script Facebook -->
        
         
      

        
        <!-- fine roba utile al footer e al header -->


        
        
        <!-- dropzone -->
        <link rel="stylesheet" href="include/css/InserimentoAnnunci/dropzone1.css"> 
        <script type="text/javascript" src="include/js/InserimentoAnnunci/dropzone1.js"></script>
        

        
    </head>
    <body>
        
                        <%--header --%>
        <%@include file="/header.jsp" %> 
        
        
        <input type="hidden" id="OIDAnnuncio" value="<% 
               String oid = request.getParameter("oid");
               if(oid != null){
                   out.write(oid);
               }else{
                   out.write("1005");
               }
               %>" />  
        



        <div class="container">
            <div class="panel-group">

                <div id="indirizzoPanel" class="panel panel-default">
                    
                    <div class="panel-heading">
                        <div class="panel-title">
                            <a class = "openlink" data-toggle="collapse" href="#collapse1" >Modifica Info Indirizzo</a>
                            <a class="start-edit editButton"  hidden><span class="glyphicon glyphicon-edit"></span>Modifica</a>
                        </div>

                    </div>
                    <div id="collapse1" class="panel-collapse collapse" >
                        <div class="panel-body">
                            <%@include file="MA1-InfoIndirizzo.jsp" %>
                                                    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC2yod6637sOZqbmDNOZSUh-30b6xTchBE&signed_in=true&libraries=places&callback=initAutocomplete" async defer></script>

                        </div>
                        <div class="panel-footer"></div>
                    </div>
                </div>

                <div id="infoPanel" class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a class="openlink" data-toggle="collapse" href="#collapse2">Modifica Info Appartamento</a>
                            <a class="start-edit editButton"  hidden><span class="glyphicon glyphicon-edit"></span>Modifica</a>

                        </h4>
                    </div>
                    <div id="collapse2" class="panel-collapse collapse">
                        <div class="panel-body">
                            <%@include file="MA2-InfoAppartamento.jsp" %>
                        </div>
                        <div class="panel-footer"></div>
                    </div>
                </div>

                <div id="stanzePanel" class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a class="openlink" data-toggle="collapse" href="#collapse3">Modifica Stanze</a>

                        </h4>
                    </div>
                    <div id="collapse3" class="panel-collapse collapse">
                        <div class="panel-body">
                            <%@include file="MA3-InfoStanze.jsp" %>
                        </div>
                        <div class="panel-footer"></div>
                    </div>
                </div>

                <div id="costiPanel" class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" href="#collapse4" class="openlink">Modifica info Costi</a>
                            <a class="start-edit editButton"  hidden><span class="glyphicon glyphicon-edit"></span>Modifica</a>
                        </h4>
                    </div>
                    <div id="collapse4" class="panel-collapse collapse">
                        <div class="panel-body">
                            <%@include file="MA4-InfoCosti.jsp" %>
                        </div>
                        <div class="panel-footer"></div>
                    </div>
                </div>

            </div>
        </div> <!-- fine container -->


        <!-- script miei -->
                <!--mio scriot autocomplite -->
         <script type="text/javascript" src="include/js/InserimentoAnnunci/googleAutocompleteMod.js"></script>
        <link rel="stylesheet" href="include/css/InserimentoAnnunci/ModificaAnnunci.css">
        <script type="text/javascript" src="include/js/InserimentoAnnunci/ModificaAnnunci.js"></script>
        <script type="text/javascript" src="include/js/InserimentoAnnunci/stanzeUtilyMod.js"></script>
        
        <!-- fine script miei -->


    </body>
</html>
