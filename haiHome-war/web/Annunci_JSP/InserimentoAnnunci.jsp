<%-- 
    Document   : InserimentoAnnunci
    Created on : Mar 11, 2016, 10:19:16 AM
    Author     : giacomocavallo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <!--da modificare con riferimento interno 
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css"> -->
        
        <title>Nuovo Annuncio</title>
        
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
        <script type='text/javascript' src='../include/js/login/googleplus-script.js'></script>
        <script src="https://apis.google.com/js/client:platform.js?onload=startApp" async defer></script>
        <!-- end google+ login stuff -->
        
<!--
        <link href="../tutcss.css" rel="stylesheet">
         footer css -->

        <!-- Robe di login2.jsp -->
        <link href='http://fonts.googleapis.com/css?family=PT+Sans:400,700' rel='stylesheet' type='text/css'>
        <!-- <link rel="stylesheet" href="../include/css/login//reset.css"> CSS reset -->
       <!-- <link rel="stylesheet" href="../include/css/login/style2.css"> gem style -->
        <script src="../include/js/login/modernizr.js"></script> <!-- Modernizr -->
        <!-- robe del login bello -->
        <!--<link rel="stylesheet" href="../include/css/login/normalize.css"> -->
        <link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'> 
        <!-- <link rel="stylesheet" href="../include/css/login/style.css"> -->
        <!-- Fine robe di login2.jsp -->

        <!-- Import script Facebook -->
        <script type="text/javascript" src="../include/js/login/FacebookScript.js"></script>
        <!-- Fine Import script Facebook -->

        
        <!-- fine roba utile al footer e al header -->
        
        
        
        <!-- dropzone -->
        <link rel="stylesheet" href="../include/css/InserimentoAnnunci/dropzone1.css"> 
        <script type="text/javascript" src="../include/js/InserimentoAnnunci/dropzone1.js"></script>
        


        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


    </head>
    <body>
        
        
        
        <!-- modal -->
        <%@include file="anteprimaAnnuncio.jsp" %>
        
        <%--header 
        <%@include file="../header.jsp" %> --%>

        <div class="container">

            <!-- Step superiori -->
            <div class="stepwizard col-md-offset-3">
                <div class="stepwizard-row setup-panel">
                    <div class="stepwizard-step">
                        <a href="#step-1" type="button" class="btn btn-primary btn-circle" id="1">1</a>
                        <p>Info Indirizzo</p>
                    </div>
                    <div class="stepwizard-step">
                        <a href="#step-2" type="button" class="btn btn-default btn-circle" disabled="disabled" id="2">2</a>
                        <p>Info Annuncio </p>
                    </div>
                    <div class="stepwizard-step">
                        <a href="#step-3" type="button" class="btn btn-default btn-circle" disabled="disabled" id="3">3</a>
                        <p>Inserimento Stanze</p>
                    </div>
                    <div class="stepwizard-step">
                        <a href="#step-4" type="button" class="btn btn-default btn-circle" disabled="disabled" id="4">4</a>
                        <p>Specifica Costi</p>
                    </div>
                </div>
            </div> <!-- fine step superiori-->

            <!-- inizio pagine step -->


            
            
                <div class="row setup-content" id="step-1"> 

                    <%@include file="InserimentoInfoAppartamento.jsp" %>
                    
                    
                </div>

                <div class="row setup-content" id="step-2">
                    
                    <%@include file="InserimentoInfoAnnuncio.jsp" %>
                    
                </div>

                <div class="row setup-content" id="step-3">

                    <%@include file="InserimentoStanze.jsp" %>
                                                                        
                </div>
   
                <div class="row setup-content" id="step-4">
                    
                   <%@include file="InserimentoCosti.jsp" %>
                    
                </div>

         <!--   </form>  -->



        </div> <!-- fine container -->

        
        <%-- footer 
        <%@include file="../footer.jsp" %> --%>
        


        <!-- script miei -->
        <link rel="stylesheet" href="../include/css/InserimentoAnnunci/InserimentoAnnuncio.css">
        <script type="text/javascript" src="../include/js/InserimentoAnnunci/stanzeUtily.js"></script>
        <script type="text/javascript" src="../include/js/InserimentoAnnunci/anteprimaAnnuncio.js"></script>
        <script type="text/javascript" src="../include/js/InserimentoAnnunci/InserimentoAnnuncio.js"></script>
        <!-- fine script miei -->


    </body>
</html>