<%-- 
    Document   : search-page
    Created on : 17-mar-2016, 9.20.41
    Author     : gianp_000
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <!--<meta name="google-signin-client_id" content="495487496441-r9l7mppbotcf6i3rt3cl7fag77hl0v62.apps.googleusercontent.com">
        <!-- meta tag googleplus login-->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>haiHome!! - Ricerca</title>
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
        
        <!-- footer -->
        <link href="tutcss.css" rel="stylesheet">
        <!-- footer css -->

        <!-- INIZIO import SOL -SearchPage -->
        <script type="text/javascript" src="include/js/search/sol.js"></script>
        <link rel="stylesheet" href="include/css/search/sol.css">
        <link rel="stylesheet" href="include/css/search/search-result.css">
        <link rel="stylesheet" href="include/css/search/search-page.css">
        <!-- FINE import SOL -->  

        <!--INIZIO - Form ajax plugin -->
        <script src="http://malsup.github.com/jquery.form.js"></script> 
        <!--FINE- Form ajax plugin -->

        <!-- Robe di login2.jsp ROBE IN DUBBIO DA DECIDERE-->
        <link href='http://fonts.googleapis.com/css?family=PT+Sans:400,700' rel='stylesheet' type='text/css'>

        <link rel="stylesheet" href="include/css/login//reset.css"> <!-- CSS reset -->
        <link rel="stylesheet" href="include/css/login/style2.css"> <!-- Gem style -->
        <script src="include/js/login/modernizr.js"></script> <!-- Modernizr -->
        <!-- robe del login bello -->
        <link rel="stylesheet" href="include/css/login/normalize.css">
        <link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'>
        <link rel="stylesheet" href="include/css/login/style.css">
        <!-- Fine robe di login2.jsp -->

        
        <!-- INFO BOX -->
        <script type="text/javascript" src="include/js/search/jquery.colorbox-min.js"></script>
        <link rel="stylesheet" href="include/css/search/colorbox.css">
        <!-- FINE INFO BOX-->

        <!-- SESSION -->
        <script type="text/javascript" src="include/js/search/jquery.session.js"></script>
        <!-- FINE SESSION -->

        <!-- INIZIO import ajax-fun searchPage-->
        <script type="text/javascript" src="include/js/search/ajax_fun_searchPage.js"></script>
        <!-- FINE import ajax-fun searchPage-->
        
        <!-- general Modal -->
        <script type="text/javascript" src="include/js/generalModal.js"></script>
        <!-- FINE General Modal -->

    </head>
    <body>
        <%@include file="/header.jsp" %>
        <div class="container">
            <%@include file="/include/html/generalModal.html" %>
            <div id="mydiv"></div>
            <div class="row">
                <div class="col-sm-9 content">
                    <div id="map" class="" >   
                    </div>
                    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC2yod6637sOZqbmDNOZSUh-30b6xTchBE&libraries=places"></script>
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

                <div class="col-sm-3 sidebar-outer">
                    <div class="well sidebar" id="searchDiv">
                        <h1 align="center">Filtro di Ricerca</h1>
                        <form class="form-horizontal" id="searchForm" action="ServletController" method="post" >
                            <input type="hidden" name="action" value="search">
                            <div id="quartieri-div"style="display:none;">
                                <label class="sol-label" for="quartieri">
                                    <div class="row">
                                        <p id="selezionaQ">Seleziona quartieri
                                            <a class="infobox btn" href="images/infobox.png" title="Mappa dei quartieri di Torino.">
                                                <span id="info-quartieri" class="glyphicon glyphicon-info-sign" style="inline">

                                                </span>
                                            </a>
                                        </p>
                                    </div>
                                </label>
                                <p></p>
                                <select class="sol-selection" name="quartieri" id="quartieri" multiple="multiple">

                                </select>

                            </div>
                            
                            <div class="form-group">
                                <p class="my-label">Tipo Annuncio</p>
                                <select class="form-control" name="tipo" id="tipo">
                                    <option id="tipo-all" value="all">-</option>
                                    <option id="tipo-Appartamento" value="Appartamento">Appartamento</option>
                                    <option id="tipo-Stanza" value="Stanza">Stanza</option>
                                </select>
                            </div>
                            
                            <div class="form-group" id="divTipoStanza" style="display:none">
                                <label for="tipo" class="control-label">Tipo Stanza</label>
                                <select class="form-control" name="tipoStanza" id="tipoStanza">

                                </select>
                            </div>

                            <div class="form-group" id="divNLocali" style="display:none">
                                <p class="my-label">
                                    Numero Locali
                                    <span id="detail-NLocali-Button" class="glyphicon glyphicon-plus my-btn"></span>                                 
                                </p>
                                <div class="input-group" id="NLocali-div" style="display:none">
                                    <div class="input-group-addon" id="NLocali" >#</div>
                                    <input type="text" class="form-control" id="numeroLocali" name="numeroLocali" aria-describedby="NLocali">
                                </div>
                            </div>

                            <div class="form-group" id="divNCamere" style="display:none">
                                <p class="my-label">
                                    Numero Camere
                                    <span id="detail-numeroCamere-Button" class="glyphicon glyphicon-plus my-btn"></span>
                                </p>
                                <div class="input-group" id="numeroCamere-div" style="display:none">
                                    <div class="input-group-addon" id="NCamere" >#</div>
                                    <input type="text" class="form-control" id="numeroCamere" name="numeroCamere" aria-describedby="NCamere">
                                </div>
                            </div>
                            
                            <div class="form-group" id="divNBagni" style="display:none">
                                <p class="my-label">
                                    Numero Bagni
                                    <span id="detail-NBagni-Button" class="glyphicon glyphicon-plus my-btn"></span>
                                </p>
                                <div class="input-group" id="NBagni-div" style="display:none">
                                    <div class="input-group-addon" id="NBagni">#</div>
                                    <input type="text" class="form-control" id="numeroBagni" name="numeroBagni" aria-describedby="NBagni">
                                </div>

                            </div>
                            
                            <div class="form-group" id="divMetratura" style="display:none">
                                <p class="my-label">
                                    Metratura
                                    <span id="detail-metratura-Button" class="glyphicon glyphicon-plus my-btn"></span>
                                </p>
                                <div class="input-group" id="metratura-div" style="display:none">
                                    <div class="input-group-addon" id="divmet" >#</div>
                                    <input type="text" class="form-control" id="metratura" name="metratura" aria-describedby="divmet">
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <p class="my-label">Prezzo massimo</p>
                                <div class="input-group">
                                    <div class="input-group-addon" id="basic-addon1">Euro</div>
                                    <input type="text" class="form-control number" id="pricefrom" name="pricefrom" aria-describedby="basic-addon1">
                                    <span class="input-group-btn">
                                        <span id="detail-price-Button" class="btn glyphicon glyphicon-plus"></span>
                                    </span>
                                </div>
                            </div>
                            
                            <div class="form-group" id="detail-price" style="display:none">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" name="compCondominio" id="compCondominio">
                                        Compreso Condominio
                                    </label>
                                </div>

                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" name="compRiscaldamento" id="compRiscaldamento">
                                        Compreso Riscaldamento
                                    </label>
                                </div>
                            </div>
                            <div class="form-group" id="divArredato">
                                <div class="checkbox">
                                <label for="arredato" class="control-label">
                                    <input type="checkbox" name="arredato" id="arredato">
                                    Arredato
                                </label>
                                </div>
                            </div>
                            
                            <button id="searchButton"  type="submit" class="btn btn-danger glyphicon glyphicon-search"></button> <!--type="submit"-->
                            <button id="saveButton"  type="button" class="btn btn-danger" onClick="persistiFiltro_init()" style="display:none"></button> <!--type="submit" glyphicon glyphicon-plus-->

                        </form>

                    </div>
                </div>
            </div>
        </div>
        <%@include file="/footer.jsp" %>
        <script>
            $(window).load(function () {
                init_filtro();
                annunci_search();
                loggatoStudente();
            });
            
            $(document).ready(function () {
                $(".infobox").colorbox({rel: 'infobox'});
            });
            $("select").change(function () {
                if ($("#tipo").val() === "Appartamento") {
                    //alert("Appartamento");
                    $("#divTipoStanza").hide();
                    $("#divNLocali").show("slow");
                    $("#divNCamere").show("slow");
                    $("#divNBagni").show("slow");
                    $("#divMetratura").show("slow");
                } else if ($("#tipo").val() === "Stanza") {
                    //alert("Stanza");
                    $("#divTipoStanza").show("slow");
                    $("#divNLocali").hide();
                    $("#divNCamere").hide();
                    $("#divNBagni").hide();
                    $("#divMetratura").hide();
                } else {
                    //alert("Lascia perdere");
                    $("#divTipoStanza").hide();
                    $("#divNLocali").hide();
                    $("#divNCamere").hide();
                    $("#divNBagni").hide();
                    $("#divMetratura").hide();
                }

            });

            $("#searchForm").on("submit", function () {
                //alert("in");

                if ($("#pricefrom").val() === '') {
                    $("#pricefrom").val("0");
                    //alert("prezzo 0");
                }
                if ($("#tipo").val() === 'Appartamento') {
                    if ($("#numeroLocali").val() === '') {
                        $("#numeroLocali").val("0");
                        //alert("prezzo 0");
                    }
                    if ($("#numeroCamere").val() === '') {
                        $("#numeroCamere").val("0");
                        //alert("prezzo 0");
                    }
                    if ($("#numeroBagni").val() === '') {
                        $("#numeroBagni").val("0");
                        //alert("prezzo 0");
                    }
                    if ($("#metratura").val() === '') {
                        $("#metratura").val("0");
                        //alert("prezzo 0");
                    }
                }
            });

            $("#detail-price-Button").click(function () {
                if ($("#detail-price-Button").hasClass("glyphicon-plus")) {
                    $("#detail-price").show("slow");
                    $("#detail-price-Button").removeClass("glyphicon-plus");
                    $("#detail-price-Button").addClass("glyphicon-minus");
                } else if ($("#detail-price-Button").hasClass("glyphicon-minus")) {
                    $("#detail-price").hide();
                    $("#detail-price-Button").removeClass("glyphicon-minus");
                    $("#detail-price-Button").addClass("glyphicon-plus");
                }
            });

            $("#detail-NBagni-Button").click(function () {
                if ($("#detail-NBagni-Button").hasClass("glyphicon-plus")) {
                    $("#NBagni-div").show("slow");
                    $("#detail-NBagni-Button").removeClass("glyphicon-plus");
                    $("#detail-NBagni-Button").addClass("glyphicon-minus");
                } else if ($("#detail-NBagni-Button").hasClass("glyphicon-minus")) {
                    $("#NBagni-div").hide();
                    $("#detail-NBagni-Button").removeClass("glyphicon-minus");
                    $("#detail-NBagni-Button").addClass("glyphicon-plus");
                }
            });
            $("#detail-metratura-Button").click(function () {
                if ($("#detail-metratura-Button").hasClass("glyphicon-plus")) {
                    $("#metratura-div").show("slow");
                    $("#detail-metratura-Button").removeClass("glyphicon-plus");
                    $("#detail-metratura-Button").addClass("glyphicon-minus");
                } else if ($("#detail-metratura-Button").hasClass("glyphicon-minus")) {
                    $("#metratura-div").hide();
                    $("#detail-metratura-Button").removeClass("glyphicon-minus");
                    $("#detail-metratura-Button").addClass("glyphicon-plus");
                }
            });
            $("#detail-numeroCamere-Button").click(function () {
                if ($("#detail-numeroCamere-Button").hasClass("glyphicon-plus")) {
                    $("#numeroCamere-div").show("slow");
                    $("#detail-numeroCamere-Button").removeClass("glyphicon-plus");
                    $("#detail-numeroCamere-Button").addClass("glyphicon-minus");
                } else if ($("#detail-numeroCamere-Button").hasClass("glyphicon-minus")) {
                    $("#numeroCamere-div").hide();
                    $("#detail-numeroCamere-Button").removeClass("glyphicon-minus");
                    $("#detail-numeroCamere-Button").addClass("glyphicon-plus");
                }
            });

            $("#detail-NLocali-Button").click(function () {
                if ($("#detail-NLocali-Button").hasClass("glyphicon-plus")) {
                    $("#NLocali-div").show("slow");
                    $("#detail-NLocali-Button").removeClass("glyphicon-plus");
                    $("#detail-NLocali-Button").addClass("glyphicon-minus");
                } else if ($("#detail-NLocali-Button").hasClass("glyphicon-minus")) {
                    $("#NLocali-div").hide();
                    $("#detail-NLocali-Button").removeClass("glyphicon-minus");
                    $("#detail-NLocali-Button").addClass("glyphicon-plus");
                }
            });
            
        </script>
    </body>
</html>
