<%-- 
    Document   : dettagliAnnuncio
    Created on : 10-apr-2016, 16.21.34
    Author     : SPORT
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


        <!-- INIZIO caricamento bootstrap mediante MaxCDN -->
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">

        <!-- jQuery library -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>

        <!-- Latest compiled JavaScript -->
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <!-- FINE caricamento bootstrap mediante MaxCDN -->

        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
        <!--Tema bootstrap -->
        
        <!-- Inizio import header --------------------->
        <link rel="stylesheet" href="include/css/header.css">
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

        <!-- INIZIO footer css -->
        <link href="include/css/footer/footer.css" rel="stylesheet">
        <!-- FINE footer css -->

        <!-- INIZIO import DetailPage css-->
        <link rel="stylesheet" href="include/css/search/detailPage.css">
        <!-- FINE import DetailPage css --> 

        
        <!-- Robe di login2.jsp FORSE-->
        <link href='http://fonts.googleapis.com/css?family=PT+Sans:400,700' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="include/css/login//reset.css"> <!-- CSS reset -->
        <link rel="stylesheet" href="include/css/login/style2.css"> <!-- Gem style -->
        <script src="include/js/login/modernizr.js"></script> <!-- Modernizr -->
        <!-- robe del login bello -->
        <link rel="stylesheet" href="include/css/login/normalize.css">
        <link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'>
        <link rel="stylesheet" href="include/css/login/style.css">
        <!-- Fine robe di login2.jsp -->

        <!-- SESSION -->
        <script type="text/javascript" src="include/js/search/jquery.session.js"></script>
        <!-- FINE SESSION -->

        <!-- INIZIO import detailPage.js-->
        <!--<script type="text/javascript" src="include/js/search/ajax_fun_searchPage.js"></script>-->
        <script type="text/javascript" src="include/js/search/detailPage.js"></script>
        <!-- FINE import-->
        
        <!-- general Modal -->
        <script type="text/javascript" src="include/js/generalModal.js"></script>
        <!-- FINE General Modal -->

        <script>
            $("head").append("<title>haiHome!! - Annuncio-" + annuncio.Indirizzo + " </title>");
        </script>
        <style>

            #bg { min-width: 100%;
                  height: 600px;
                  border: 3px solid #73AD21;}
            </style>
        </head>
        <body>
            <%@include file="/header.jsp" %>
            <div class="container" id="dettagli-page">
                <%@include file="/include/html/generalModal.html" %>
            <div class="row">
                <div class="col-sm-9"><!--content -->
                    <div class="row">
                        <!--<div class="ibox float-e-margins">-->
                            <div  id="result"> 
                            </div>
                        <!--</div>-->
                    </div>
                </div>

                <div class="col-sm-3"> <!--sidebar-outer -->
                    <div id="info"></div>    
                <div id="service_info" class="center service_info"></div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-9 content">
                    <div class="hr-line-dashed"></div>
                    <div id="map" class="" >   
                    </div>
                    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC2yod6637sOZqbmDNOZSUh-30b6xTchBE&libraries=places"></script>
                    <div class="hr-line-dashed"></div>
                </div>
                <div class="col-sm-3 sidebar-outer" id="service"></div>
            </div>
        </div>
        <script>
            var opt = [true, true, true];
            initialize(annuncio, opt);

            //$("#dettagli-page").append("<img src=\"images/bg.jpg\" id=\"bg\" alt=\"\">");
            $("#result").append(create_Page(annuncio));
            $("#info").append(init_info(annuncio));
            
            loadAllfoto();
            $("#service").append(service());
        </script>

        <%@include file="/footer2.jsp" %>
    </body>

</html>
