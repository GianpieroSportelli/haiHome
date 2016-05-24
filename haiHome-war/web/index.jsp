<%-- 
    Document   : home
    Created on : 28-apr-2016, 14.27.28
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
        <title>haiHome!!</title>
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



        <!--<link href="tutcss.css" rel="stylesheet">
        <!-- INIZIO footer css -->
        <link href="include/css/footer/footer.css" rel="stylesheet">
        <!-- FINE footer css -->
        

        <!--INIZIO - Form ajax plugin -->
        <script src="http://malsup.github.com/jquery.form.js"></script> 
        <!--FINE- Form ajax plugin -->

        <!-- google+ login stuff --><!--
        <meta name="google-signin-client_id" content="495487496441-r9l7mppbotcf6i3rt3cl7fag77hl0v62.apps.googleusercontent.com"></meta>
        <script type='text/javascript' src='include/js/login/googleplus-script.js'></script>
        <script src="https://apis.google.com/js/client:platform.js?onload=startApp" async defer></script>-->
        <!-- end google+ login stuff -->

        <!-- Robe di login2.jsp -->
        <link href='http://fonts.googleapis.com/css?family=PT+Sans:400,700' rel='stylesheet' type='text/css'>

<!--        <link rel="stylesheet" href="include/css/login//reset.css"> <!-- CSS reset -->
        <link rel="stylesheet" href="include/css/login/style2.css"> <!-- se viene tolto: sfondo rosa Gem style -->
        <script src="include/js/login/modernizr.js"></script> <!-- Modernizr -->
        <!-- robe del login bello -->

<!--        <link rel="stylesheet" href="include/css/login/normalize.css"> <!-- -->
        <link rel="stylesheet" href="include/css/login/style.css">  <!-- -->
        <link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'>
        <!--
                <link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'>
        
        <!-- Fine robe di login2.jsp -->

        <!-- Import script Facebook -->

        <!-- Fine Import script Facebook -->


        <!-- SESSION -->
        <script type="text/javascript" src="include/js/search/jquery.session.js"></script>
        <!-- FINE SESSION -->

        <!-- Home IMPORT -->
        <link rel="stylesheet" href="include/css/home/home.css">
        <script type="text/javascript" src="include/js/home/home.js"></script>
        <!-- Fine home-->


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

    </head>
    <body>
        <%@include file="/header.jsp" %>
        <div class="container"> 
            <div class="col-sm-12 outer-div non_opaco" id="div_studente">
                <div class="inner-div non_opaco" id="search_div">
                    <h1>Sei in cerca di casa a Torino? <span id="search" class="glyphicon glyphicon-search fake_button"></span></h1>
                </div>
            </div>
            <div class="col-sm-12" id="div_intermezzo">

            </div>
            <div class="col-sm-12 outer-div non_opaco" id="div_locatore">
                <div class="inner-div non_opaco" id="search_div">
                    <h1>Vuoi affitare a Studenti a Torino <span id="in_locatore" class="glyphicon glyphicon-plus fake_button"></span></h1>
                </div>
            </div>

        </div>

        <%@include file="/footer2.jsp" %>
    </body>
</html>
