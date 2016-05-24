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
        <script type='text/javascript' src='include/js/login/googleplus-script.js'></script>
        <script src="https://apis.google.com/js/client:platform.js?onload=startApp" async defer></script>
        <!-- end google+ login stuff -->


                <!-- Inizio import header --------------------->
        <link rel="stylesheet" href="include/css/header.css">
        
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
        
                <!-- INIZIO footer css -->
        <link href="include/css/footer/footer.css" rel="stylesheet">
        <!-- FINE footer css -->





        <!-- fine roba utile al footer e al header -->




        <!-- dropzone -->
        <link rel="stylesheet" href="include/css/InserimentoAnnunci/dropzone1.css"> 
        <script type="text/javascript" src="include/js/InserimentoAnnunci/dropzone1.js"></script>

        <!--mio scriot autocomplite -->
        <script type="text/javascript" src="include/js/InserimentoAnnunci/googleAutocomplete.js"></script>



        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


    </head>
    <body>
        <%--header --%>
        <%@include file="/header.jsp" %> 


        <!-- modal -->
        <%@include file="IA5-anteprimaAnnuncio.jsp" %>

        <!-- Message Modal -->
        <div id="messageModal" class="modal fade">
            <div class="modal-dialog modal-sm">

                <!-- Modal content-->
                <div class="modal-content">


                    <div class="modal-header">

                    </div>
                    <div class="modal-body">

                    </div>
                    <div class="modal-footer">

                    </div>

                </div>

            </div>
        </div>



        <div class="container" >

            <div class="row">

                <div class="col-md-2 "> </div>

                <div class="col-md-8 myStepContainer">


                    <!-- Step superiori -->
                    <div class="stepwizard">
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


                </div>


            </div>



            <!-- inizio pagine step -->
            <div class="row" >
                <div class="col-md-2 "> 
                </div>


                <div class="setup-content col-md-8 mycontainer" id="step-1"> 

                    <%@include file="IA1-InfoIndirizzo.jsp" %>

                    <!-- google Place API -->
                    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC2yod6637sOZqbmDNOZSUh-30b6xTchBE&signed_in=true&libraries=places&callback=initAutocomplete" async defer></script>


                </div>

            </div>


            <div class="row" >
                <div class="col-md-2 "> </div>
                <div class="setup-content col-md-8 mycontainer" id="step-2">

                    <%@include file="IA2-InfoAppartamento.jsp" %>

                </div>
            </div>


            <div class="row" >
                <div class="col-md-2 "> </div>
                <div class="setup-content col-md-8 mycontainer" id="step-3">

                    <%@include file="IA3-InfoStanze.jsp" %>

                </div>
            </div>

            <div class="row" >
                <div class="col-md-2 "> </div>

                <div class="setup-content col-md-8 mycontainer" id="step-4">

                    <%@include file="IA4-InfoCosti.jsp" %>

                </div>
            </div>

            <!--   </form>  -->



        </div> <!-- fine container -->




        <%@include file="/footer2.jsp" %>


        <!-- script miei -->
        <link rel="stylesheet" href="include/css/InserimentoAnnunci/InserimentoAnnuncio.css">
        <script type="text/javascript" src="include/js/InserimentoAnnunci/stanzeUtily.js"></script>
        <script type="text/javascript" src="include/js/InserimentoAnnunci/anteprimaAnnuncio.js"></script>
        <script type="text/javascript" src="include/js/InserimentoAnnunci/InserimentoAnnuncio.js"></script>
        <!-- fine script miei -->



    </body>
</html>