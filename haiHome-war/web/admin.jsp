<%-- 
    Document   : admin
    Created on : 19-apr-2016, 12.11.12
    Author     : Eugenio Liso
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>HaiHome? Pannello ADMIN</title>
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


        <script src="include/js/admin/admin-javascript.js"></script> <!-- Modernizr -->



        <!-- Robe di UserProfile
        <script src="include/js/userProfile/intro.js"></script>
        <link rel="stylesheet" href="include/css/Utente/introjs.css">-->
        <link rel="stylesheet" href="include/css/Utente/Utente.css">
        <link rel="stylesheet" href="include/css/Utente/bootstrap.vertical-tabs.css">
        <!-- <script type="text/javascript" src="include/js/userProfile/studente-profile.js"></script>
        Fine Robe di UserProfile-->


        <!--<link href="tutcss.css" rel="stylesheet">-->
        <link href="include/css/footer/footer.css" rel="stylesheet">
        <!-- footer css -->
        <link rel="stylesheet" href="include/css/login/style.css">
        <link rel="stylesheet" href="include/css/login/style2.css"> <!-- Gem style 
 
        <link rel="stylesheet" href="include/css/login/normalize.css">
        
        <!--<link rel="stylesheet" href="tutcss.css">-->
        <!-- robe del login bello -->
        <!--<link rel="stylesheet" href="include/css/login/style.css">
        <!-- Fine robe di login2.jsp -->

        <!-- Robe di login2.jsp -->

        <!--<link rel="stylesheet" href="include/css/login//reset.css"> <!-- CSS reset -->
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


        <!-- segnalazioni_script---->
        <script type='text/javascript' src='include/js/admin/segnalazione_script.js'></script>
        <link rel="stylesheet" href="include/css/administrator/segnalazioni.css">
        <!-- FINE --->

        <!-- SESSION -->
        <script type="text/javascript" src="include/js/search/jquery.session.js"></script>
        <!-- FINE SESSION -->


    </head>
    <body>
        <%@include file="/header.jsp" %> 
        <script>
            //console.log("carico");
            $(document).ready(function () {
                loadSegnalazioni();
            });
        </script>

        <div class="container">
            <div class="row profile">
                <div class="col-sm-4">
                    <div class="profile-sidebar">

                        <div class="profile-userpic">

                            <img src=https://theadminzone.com/styles/taz/xenforo/logo.png class="img-responsive" alt="">

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
                                <h4> PANNELLO ADMIN </h4>
                            </div>
                            <div class="profile-usertitle-job">Admin Panel</div>
                        </div>

                        <div class="profile-usermenu"> <!-- required for floating -->
                            <!-- Nav tabs -->
                            <ul class="nav nav-tabs tabs-left">
                                <li class="active"><a href="#1" data-toggle="tab" onclick="no_segn()">
                                        <i class="glyphicon glyphicon-list-alt"></i>
                                        Citt&aacute;</a></li>
                                <li><a href="#2" data-toggle="tab" onclick="getListaCitta()">
                                        <i class="glyphicon glyphicon-list-alt"></i>
                                        Quartieri e CAP</a></li>
                                <li><a href="#3" data-toggle="tab" onclick="segnalazioni()">
                                        <i class="glyphicon glyphicon-list-alt"></i>
                                        Segnalazioni</a></li>
                                <li><a href="#4" data-toggle="tab" onclick="segnalazioni_arch()">
                                        <i class="glyphicon glyphicon-list-alt"></i>
                                        Segnalazioni Archiviate</a></li>
                            </ul>
                        </div>
                    </div>
                </div>

                <div class="col-sm-8">
                    <!-- Tab panes -->
                    <div class="profile-content tab-content">
                        <div class="tab-pane active in fade" id="1">
                            <form id="formCitta" accept-charset="utf-8" action="ServletController" method="POST" class="simform">
                                <div class="sminputs">
                                    <div class="input full">
                                        <label class="string optional" for="citta">Inserisci Citt&Agrave;</label>
                                        <input style="background-color:#f2f2f2" class="string optional" maxlength="255" id="citta" name="citta" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Citt&agrave;'" placeholder="Citt&agrave;" size="50" />
                                    </div>
                                </div>
                                <div class="simform__actions"> 
                                    <a onclick="insertCitta()" class="btn btn-lg btn-success" role="button"
                                       data-toggle="popover" data-trigger="manual" data-content="" > 
                                        Inserisci
                                    </a>
                                </div> 
                            </form>

                            <form id="formCitta2" accept-charset="utf-8" action="ServletController" method="POST" class="simform">
                                <div class="sminputs">
                                    <div class="input full">
                                        <label class="string optional" for="citta2">Cancella Citt&Agrave;</label>
                                        <input style="background-color:#f2f2f2" class="string optional" maxlength="255" id="citta2" name="citta2" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Citt&agrave;'" placeholder="Citt&agrave;" size="50" />
                                    </div>
                                </div>
                                <div class="simform__actions"> 
                                    <a onclick="deleteCitta()" class="btn btn-lg btn-success" role="button"
                                       data-toggle="popover" data-trigger="manual" data-content="" > 
                                        Cancella
                                    </a>
                                </div> 
                            </form>
                        </div>
                        <div class="tab-pane fade" id="2">
                            <div class="col-sm-6 sidebar-outer">
                                <div class="well sidebar">
                                    <div class="form-group" id="cittaDIV">
                                        <label for="cittaDB" class="control-label">Seleziona città</label>
                                        <select class="form-control" name="cittaDB" id="cittaDB">

                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 sidebar-outer" id="quartieriDIV" style="display:none">
                                <div class="well sidebar">
                                    <div class="form-group">
                                        <label for="quartiereInput" class="control-label">Inserisci quartiere</label>
                                        <input type="text" class="form-control" id="quartiereNome" name="quartiereNome">
                                    </div>
                                </div>
                            </div>
                            <div id="capDIV" class="col-sm-12 sidebar-outer" style="display:none">
                                <div class="well sidebar">
                                    <div class="form-group" >
                                        <label for="capQuartiere" class="control-label">Digita i CAP da inserire separati da un trattino <strong> - </strong> </label>
                                        <div class="input-group-addon" id="numCAP" >Lista CAP</div>
                                        <input type="text" onkeypress='return (event.charCode >= 48 && event.charCode <= 57) || event.charCode === 45' class="form-control" id="capQuartiere" name="capQuartiere">
                                    </div>

                                    <div class="simform__actions"> 
                                        <a onclick="inserisciCAP()" class="btn btn-lg btn-success" role="button"
                                           data-toggle="popover" data-trigger="manual" data-content="" > 
                                            Inserisci
                                        </a>
                                    </div> 
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="3">
                            <div class="col-sm-12">

                                <div id="segnalazioni"></div>
                            </div>

                        </div>

                        <div class="tab-pane fade" id="4">
                            <div class="col-sm-12">

                                <div id="segnalazioni-archiviate"></div>
                            </div>

                        </div>
                    </div>
                </div>  
            </div>
        </div> 

        <script>
            $("#cittaDB").change(function () {
                if ($("#cittaDB").val() !== "-") {
                    var citta = $("#cittaDB").val();
                    //getQuartieriCitta(citta);
                    $("#quartieriDIV").show("slow");
                    $("#capDIV").show("slow");
                } else {
                    $("#quartieriDIV").hide();
                    $("#capDIV").hide();
                }
            });
        </script>

        <%@include file="/footer2.jsp" %>
    </body>
</html>
