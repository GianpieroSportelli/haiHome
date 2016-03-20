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
        <title>haiHome!! - Search Page</title>
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

        <link href="tutcss.css" rel="stylesheet">
        <!-- footer css -->

        <!-- INIZIO import SOL -SearchPage -->
        <script type="text/javascript" src="include/js/search/sol.js"></script>
        <link rel="stylesheet" href="include/css/search/sol.css">
        <link rel="stylesheet" href="include/css/search/search-result.css">
        <link rel="stylesheet" href="include/css/search/search-page.css">
        <!-- FINE import SOL -->  
        <!-- INIZIO import ajax-fun searchPage-->
        <script type="text/javascript" src="include/js/search/ajax_fun_searchPage.js"></script>
        <!-- FINE import ajax-fun searchPage-->
        <!--INIZIO - Form ajax plugin -->
        <script src="http://malsup.github.com/jquery.form.js"></script> 
        <!--FINE- Form ajax plugin -->

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
        
    </head>
    <body>
        <%@include file="/login2.jsp" %>
        <%@include file="/header3Login.jsp" %> 
        <div class="container">
            <div id="mydiv"></div>
            <div class="row">
                <div class="col-sm-9">

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
                <div class="col-sm-3">
                    <div class="well">
                        <h1 align="center">Filtro di Ricerca</h1>
                        <form class="form-horizontal" id="searchForm" action="ServletController" method="post" >
                            <input type="hidden" name="action" value="search">
                            <div>
                                <label class="sol-label" for="quartieri">Seleziona Quartieri</label>
                                <select class="sol-selection" name="quartieri" id="quartieri" multiple="multiple">

                                </select>


                            </div>
                            <div class="form-group">
                                <label for="tipo" class="control-label">Tipo Annuncio</label>
                                <select class="form-control" name="tipo" id="tipo">
                                    <option value="all">-</option>
                                    <option value="Appartamento">Appartamento</option>
                                    <option value="Stanza">Stanza</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="pricefrom" class="control-label">Prezzo massimo</label>
                                <div class="input-group">
                                    <div class="input-group-addon" id="basic-addon1">Euro</div>
                                    <input type="text" class="form-control" id="pricefrom" name="pricefrom" aria-describedby="basic-addon1">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" value="true" name="compCondomino">
                                        Compreso Condominio
                                    </label>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" value="true" name="compRiscaldamento">
                                        Compreso Riscaldamento
                                    </label>
                                </div>
                            </div>


                            <div class="form-group" id="divTipoStanza" style="display:none">
                                <label for="tipo" class="control-label">Tipo Stanza</label>
                                <select class="form-control" name="tipoStanza" id="tipoStanza">

                                </select>
                            </div>

                            <div class="form-group" id="divNLocali" style="display:none">
                                <label for="numeroLocali" class="control-label">Numero Locali</label>
                                <div class="input-group">
                                    <div class="input-group-addon" id="NLocali" >#</div>
                                    <input type="text" class="form-control" id="numeroLocali" name="numeroLocali" aria-describedby="NLocali">
                                </div>
                            </div>
                            <div class="form-group" id="divMetratura" style="display:none">
                                <label for="metratura" class="control-label">Metratura</label>
                                <div class="input-group">
                                    <div class="input-group-addon" id="divmet" >#</div>
                                    <input type="text" class="form-control" id="metratura" name="metratura" aria-describedby="divmet">
                                </div>
                            </div>
                            <div class="form-group" id="divNCamere" style="display:none">
                                <label for="numeroCamere" class="control-label">Numero Camere</label>
                                <div class="input-group">
                                    <div class="input-group-addon" id="NCamere" >#</div>
                                    <input type="text" class="form-control" id="numeroCamere" name="numeroCamere" aria-describedby="NCamere">
                                </div>
                            </div>
                            <div class="form-group" id="divNBagni" style="display:none">
                                <label for="numeroLocali" class="control-label">Numero Bagni</label>
                                <div class="input-group">
                                    <div class="input-group-addon" id="NBagni" >#</div>
                                    <input type="text" class="form-control" id="numeroBagni" name="numeroBagni" aria-describedby="NBagni">
                                </div>
                            </div>
                            <button id="searchButton"  type="submit" class="btn btn-danger glyphicon glyphicon-search"></button> <!--type="submit"-->
                        </form>

                    </div>
                </div>
            </div>
        </div>


        <%@include file="/footer.jsp" %>
        <script>
            $(window).load(function () {               
                    console.log(annunci_search());              
                    console.log(init_filtro());
            });
        </script>
        <!--<script>
            var geocoder;
            var map;
            var geoAddress;
            var icon = "images/basket.png";
            var N_ANNUNCI_X_PAGE = 2;
            var n_page = 0;
            var n_annunci = 0;
            var annunci = new Array();
            var page_annunci = new Array();
            var actual = 0;
            //Funzion Ajax per caricare e inizializzare la mappa e caricare gli annunci
            function annunci_search() {
                $.post("ServletController",
                        {action: "Ricerca-geoCity"},
                function (responseJson) {                               // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response JSON...
                    var latlng = [0, 0];                                   // Create HTML <ul> element and append it to HTML DOM element with ID "somediv".
                    $.each(responseJson, function (index, item) {       // Iterate over the JSON array.
                        latlng[index] = item;                              // Create HTML <li> element, set its text content with currently iterated item and append it to the <ul>.
                    });
                    
                    console.log(initialize(latlng[0], latlng[1]));
                    
                    //Deferred serve pr delegare l'esecuzione di funzioni javascript
                    var load_A=$.Deferred();
                    load_A.done(load_Annunci);
                    load_A.resolve();
                    
                });
            }

            function initialize(lat, lng) {
                geocoder = new google.maps.Geocoder();
                var latlng = new google.maps.LatLng(lat, lng);
                var mapOptions = {
                    zoom: 14,
                    center: latlng
                };
                map = new google.maps.Map(document.getElementById("map"), mapOptions);
            }

            function load_Annunci() {
                $.post("ServletController",
                        {action: "Ricerca-getAnnunciFiltro"},
                function (responseJson) {
                    annunci = [];
                    n_page = 0;
                    n_annunci = 0;

                    $("#list-result").empty();

                    $.each(responseJson, function (index, annuncio) {
                        n_annunci += 1;
                        annunci[index] = annuncio;
                        console.log(addMarkerToJSON(annuncio, index));
                    });
                    
                    actual=0;
                    n_page = n_annunci / N_ANNUNCI_X_PAGE;
                    
                    console.log(create_pageResult());
                    console.log(add_button());
                    console.log(selectpage(1));
                    
                });
            }
            function create_pageResult() {
                page_annunci = [];
                var result_div = '<div class = "search-result" >';
                var close_div = '</div>';
                var no_res = '<p > Nessun Risultato!! </p>';
                var page_html = '';
                if (n_page == 0) {
                    $("#list-result").append(no_res);
                }
                for (page = 0; page < n_page; page++) {
                    page_html = '';
                    page_html += '<div id =' + (page + 1) + '_RESULT>';
                    for (i = page * N_ANNUNCI_X_PAGE; (i < ((page + 1) * N_ANNUNCI_X_PAGE) && i < annunci.length); i++) {
                        var title = '<h3 > <a href = "#" >' + annunci[i].Indirizzo + '</a></h3 >';
                        var data_i = '<p > Data inizio: ' + annunci[i].DataInizioAffitto + '</p>';
                        var data_p = '<p > Data pubblicazione: ' + annunci[i].DataPubblicazione + '</p>';
                        var prezzo = '<p > Prezzo: ' + annunci[i].Prezzo + '</p>';
                        var quartiere = '<p > Quartiere: ' + annunci[i].Quartiere + '</p>';
                        var n_loc = '<p > Numero Locali: ' + annunci[i].NumeroLocali + '</p>';
                        var loc = '<p > Locatore: ' + annunci[i].Locatore.nome + '</p>';
                        var desc = '<p > Descrizione: ' + annunci[i].Descrizione + '</p>';
                        var met = '<p > Metratura: ' + annunci[i].Metratura + '</p>';
                        var html = result_div + title + data_i + data_p + prezzo + quartiere + n_loc + loc + desc + met + close_div;
                        page_html += html;
                    }
                    page_html += close_div;
                    page_annunci[page] = page_html;
                }
            }

            function add_button() {
                $("#list-result").append("<div class=\"text-center \" id = \"button-div\">");
                $("#button-div").append("<div class=\"btn-group\" id=\"group-button-page\">");
                $("#group-button-page").append("<button class=\"btn btn-white\"onClick=prevpage() type=\"button\"><i class=\"glyphicon glyphicon-chevron-left\"></i></button>");
                for (i = 0; i < n_page; i++) {
                    
                    var html = '<button class=\"btn btn-white\" onClick=selectpage(this.id) id=\"' + (i + 1) + '\"> ' + (i + 1) + '</button>';
                    $("#group-button-page").append(html);
                }
                $("#group-button-page").append("<button class=\"btn btn-white\" onClick=nextpage() type=\"button\"><i class=\"glyphicon glyphicon-chevron-right\"></i> </button>");
                $("#button-div").append("</div>");
                $("#list-result").append("</div>");
            }

            function selectpage(page) {
                if (actual === 0) {
                    $("#button-div").before(page_annunci[page - 1]);
                    $("#" + page).addClass("disabled");
                    actual = page;
                } else if (actual !== (+page)) {
                    $("#" + (actual) + "_RESULT").remove();
                    $("#button-div").before(page_annunci[page - 1]);
                    $("#" + actual).removeClass("disabled");
                    $("#" + page).addClass("disabled");
                    actual = +page;
                }
            }

            function prevpage() {
                
                if (actual > 1) {
                    var page = actual - 1;
                    selectpage(page);
                }
            }
            function nextpage() {
                
                if (actual < n_page) {
                    var page = actual + 1;
                    selectpage(page);
                }
            }
            function addMarker(location, label, contentString) {
                //alert(label);
                // Add the marker at the clicked location, and add the next-available label
                // from the array of alphabetical characters.
                var marker = new google.maps.Marker({
                    position: location,
                    title: label,
                    map: map
                            //icon: icon
                });
                var infowindow = new google.maps.InfoWindow({
                    content: contentString
                });
                marker.addListener('click', function () {
                    infowindow.open(map, marker);
                });
            }
            function addMarkerToJSON(annuncio, index) {
                var lat = annuncio.Lat;
                var lng = annuncio.Lng;
                var address = annuncio.Indirizzo;
                var contentString = ' <div id = \"content\" > ' +
                        '<div id = \"siteNotice\" >' +
                        '</div>' +
                        '<div id = \"firstHeading\" > <span style =\"font-size:18px; font-weight:bold;\"> Uluru </span><br><br><img src=' + icon + ' style=\"max-width:100%;\" / > <br> <br> ' +
                        'Contact info <br> Phone: + 65 123456789 <br> Email: <a href =\"mailto:info@example.com\"> info@example.com </a>' +
                        '</div><div id=”bodyContent”>' +
                        '<p> <b>' + address + ' </b> Lorem upsum</p >' +
                        ' </div>' +
                        '</div>';
                var label = "Annuncio " + (index + 1);
                window.addMarker(new google.maps.LatLng(lat, lng), label, contentString);
            }

            function init_filtro() {
                $.post("ServletController",
                        {action: "Ricerca-getQuartieri"},
                function (responseJson) {
                    var html = '';
                    $.each(responseJson, function (index, item) {
                        html = '<option value=\"' + item + '\">' + item + '</option>';
                        $("#quartieri").append(html);
                    });
                    $('#quartieri').searchableOptionList({
                        maxHeight: '250px'
                    });
                    var filtro_S=$.Deferred();
                    filtro_S.done(init_filtro_tipoStanze);
                    filtro_S.resolve();
                });

            }
            function init_filtro_tipoStanze() {
                $.post("ServletController",
                        {action: "Ricerca-getTipoStanza"},
                function (responseJson) {
                    var html = '';
                    $.each(responseJson, function (index, item) {
                        html = '<option value=\"' + item + '\">' + item + '</option>';
                        $("#tipoStanza").append(html);
                    });
                });
            }
        </script>-->
        <script>
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
            $(document).ready(function () {
                $('#searchForm').ajaxForm(function () {
                    console.log(annunci_search());
                });
            });

        </script>
    </body>
</html>
