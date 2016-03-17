<%-- 
    Document   : search
    Created on : 11-dic-2015, 16.58.42
    Author     : gianp_000
--%>
<%@page import="org.json.JSONObject"%> 
<%@page import="java.util.ArrayList"%> 
<%@page import="org.json.JSONArray"%> 

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
        <%
            ArrayList<String> quartieri = (ArrayList<String>) request.getAttribute("quartieri");
            ArrayList<String> tipoStanza = (ArrayList<String>) request.getAttribute("tipoStanza");
            JSONArray annunci = (JSONArray) request.getAttribute("JSONAnnunci");
            double[] latlng = (double[]) request.getAttribute("latlng");
            double lat = latlng[0];
            double lng = latlng[1];
            final int N_ANNUNCI_X_PAGE = 1;

            //int fin=dpage.lastIndexOf(".");
            int n_page = ((Double) Math.ceil(((double) annunci.length() / (double) N_ANNUNCI_X_PAGE))).intValue();

        %>
        <!-- bootstap necessita di container ne esistono di 2 tipi container e container-fluid -->
        <%@include file="/login2.jsp" %>
        <%@include file="/header3Login.jsp" %> 
        <div class="container">

            <div class="row">

                <div class="col-sm-9">
                    <div>
                        <div id="map" class="" >
                        </div>
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="ibox float-e-margins">
                                    <div class="ibox-content" id="list-result"> 
                                        <div class="hr-line-dashed"></div>
                                        <script>
                                            var n_page =<%=n_page%>;
                                            var actual = 1;
                                            $(function () {
                                                var result_div = '<div class = "search-result" >';
                                                var close_div = '</div>';
                                                var no_res = '<p > Nessun Risultato!! </p>';
                                            <%if (annunci.length() == 0) {%>
                                                $("#list-result").append(result_div + no_res + close_div);
                                            <%} else {
                                                for (int i = 0; i < annunci.length(); i++) {
                                                    if (i % N_ANNUNCI_X_PAGE == 0) {%>
                                                $("#list-result").append('<div id ="<%="" + ((i / N_ANNUNCI_X_PAGE) + 1) + "_RESULT"%>" style="display:none">');

                                            <%}%>
                                                var title = '<h3 > <a href = "#" ><%= annunci.getJSONObject(i).getString("Indirizzo")%> </a></h3 >';
                                                var data_i = '<p > Data inizio: <%= annunci.getJSONObject(i).getString("DataInizioAffitto")%> </p>';
                                                var data_p = '<p > Data pubblicazione: <%= annunci.getJSONObject(i).getString("DataPubblicazione")%> </p>';
                                                var prezzo = '<p > Prezzo: <%="" + annunci.getJSONObject(i).getDouble("Prezzo")%> </p>';
                                                var quartiere = '<p > Quartiere: <%= annunci.getJSONObject(i).getString("Quartiere")%> </p>';
                                                var n_loc = '<p > Numero Locali: <%= annunci.getJSONObject(i).getInt("NumeroLocali")%> </p>';
                                                var loc = '<p > Locatore: <%= ((JSONObject) annunci.getJSONObject(i).get("Locatore")).getString("nome")%> </p>';
                                                var desc = '<p > Descrizione: <%= annunci.getJSONObject(i).getString("Descrizione")%> </p>';
                                                var met = '<p > Metratura: <%= annunci.getJSONObject(i).getDouble("Metratura")%> </p>';
                                                var html = result_div + title + data_i + data_p + prezzo + quartiere + n_loc + loc + desc + met + close_div;
                                                $("#<%="" + ((i / N_ANNUNCI_X_PAGE) + 1) + "_RESULT"%>").append(html);

                                            <%if (i % N_ANNUNCI_X_PAGE == 0) {%>
                                                $("#list-result").append(close_div);
                                            <%}
                                                    }
                                                }%>
                                            });

                                            $(function () {
                                                $("#list-result").append("<div class=\"text-center\" id=\"button-div\">");
                                                $("#button-div").append("<div class=\"btn-group\" id=\"group-button-page\">");
                                                $("#group-button-page").append("<button class=\"btn btn-white\"onClick=prevpage() type=\"button\"><i class=\"glyphicon glyphicon-chevron-left\"></i></button>");
                                            <%for (int i = 0; i < n_page; i++) {%>
                                                //var node = document.createElement("div");
                                                var html = "<button class=\"btn btn-white\" onClick=selectpage(this.id) id=\"<%=i + 1%>\"><%= i + 1%> </button>";
                                                $("#group-button-page").append(html);

                                            <%}%>
                                                $("#group-button-page").append("<button class=\"btn btn-white\" onClick=nextpage() type=\"button\"><i class=\"glyphicon glyphicon-chevron-right\"></i> </button>");
                                                $("#button-div").append("</div>");
                                                $("#list-result").append("</div>");

                                            });

                                            function selectpage(page) {
                                                //alert(page);
                                                if (actual !== (+page)) {
                                                    var div_name_hide = "#" + actual + "_RESULT";
                                                    var div_name_show = "#" + page + "_RESULT";

                                                    $(div_name_hide).hide();
                                                    $(div_name_show).show();

                                                    $("#" + actual).removeClass("disabled");
                                                    $("#" + page).addClass("disabled");

                                                    actual = +page;

                                                }
                                            }

                                            function prevpage() {
                                                //alert(actual);
                                                if (actual !== 1) {
                                                    var page = actual - 1;
                                                    selectpage(page);
                                                }
                                            }
                                            function nextpage() {
                                                //alert(actual);
                                                if (actual !== n_page) {
                                                    var page = actual + 1;
                                                    selectpage(page);
                                                }
                                            }
                                            $(function () {
                                                //alert(actual);
                                                var div_name = "#" + actual + "_RESULT";
                                                $("#" + actual).addClass("disabled");
                                                $(div_name).show();

                                            });
                                        </script>


                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC2yod6637sOZqbmDNOZSUh-30b6xTchBE&libraries=places"></script>
                    <script language="javascript">
                                            var geocoder;
                                            var map;
                                            var geoAddress;
                                            var icon = "images/basket.png";
                                            function initialize() {
                                                geocoder = new google.maps.Geocoder();
                                                var latlng = new google.maps.LatLng(<%= lat%>, <%= lng%>);
                                                var mapOptions = {
                                                    zoom: 14,
                                                    center: latlng
                                                };
                                                map = new google.maps.Map(document.getElementById("map"), mapOptions);
                                                /*var marker = new google.maps.Marker({
                                                 map: map,
                                                 position: latlng,
                                                 title: 'geocode request address'
                                                 });*/
                                                //map.setZoom(16);
                                            }
                                            window.initialize();
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
                        <%for (int i = 0; i < annunci.length(); i++) {
                                JSONObject json_annuncio = annunci.getJSONObject(i);
                                //System.out.println(json_annuncio);
                                String latAnnuncio = "" + json_annuncio.get("Lat");
                                String lngAnnuncio = "" + json_annuncio.get("Lng");
                                String addressAnnuncio = "" + json_annuncio.get("Indirizzo");
                        %>
                                            var contentString = ' <div id = \"content\" > ' +
                                                    '<div id = \"siteNotice\" >' +
                                                    '</div>' +
                                                    '<div id = \"firstHeading\" > <span style =\"font-size:18px; font-weight:bold;\"> Uluru </span><br><br><img src='+icon+' style=\"max-width:100%;\" / > <br> <br> ' +
                                                    'Contact info <br> Phone: + 65 123456789 <br> Email: <a href =\"mailto:info@example.com\"> info@example.com </a>' +
                                                    '</div><div id=”bodyContent”>' +
                                                    '<p> <b> <%= addressAnnuncio%> </b> Lorem upsum</p >' +
                                                    ' </div>' +
                                                    '</div>';
                                            window.addMarker(new google.maps.LatLng(<%= latAnnuncio%>, <%= lngAnnuncio%>), '<%= "ANNUNCIO " + i%>', contentString);
                        <%}%>
                    </script>
                </div>

                <div class="col-sm-3">
                    <div class="well">
                        <h1 align="center">Filtro di Ricerca</h1>
                        <form class="form-horizontal" method="POST" action="ServletController" id="searchForm" >
                            <input type="hidden" name="action" value="search">
                            <div>
                                <label class="sol-label" for="quartieri">Seleziona Quartieri</label>
                                <select class="sol-selection" name="quartieri" id="quartieri" multiple="multiple">
                                    <% for (String quart : quartieri) {%>
                                    <option value="<%= quart%>">
                                        <%= quart%>
                                    </option>
                                    <% }%>   
                                </select>

                                <script type="text/javascript">
                                    $(function () {
                                        // initialize sol
                                        $('#quartieri').searchableOptionList({
                                            maxHeight: '250px'
                                        });
                                    });</script>
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
                            </script>
                            <div class="form-group" id="divTipoStanza" style="display:none">
                                <label for="tipo" class="control-label">Tipo Stanza</label>
                                <select class="form-control" name="tipoStanza" id="tipoStanza">
                                    <!--<option value="all">-</option>-->
                                    <% for (String tipo : tipoStanza) {%>
                                    <option value="<%= tipo%>">
                                        <%= tipo%>
                                    </option>
                                    <% }%>
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
                            <button id="searchButton"  class="btn btn-danger glyphicon glyphicon-search"></button> <!--type="submit"-->
                        </form>
                        <script>
                            $("#searchButton").click(function () {
                                var ok = true;
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
                                if ($("#tipo").val() === 'Stanza') {
                                    if ($("#tipoStanza").val() === '') {
                                        alert("Scegliere un tipo stanza");
                                        $("#tipoStanza").css("color", "red").slideUp(2000).slideDown(2000);
                                        ok = false;
                                    }
                                }
                                if (ok) {
                                    $("#searchform").submit();
                                }
                            });

                        </script>
                    </div>
                </div>
            </div>
        </div> 
        <%@include file="/footer.jsp" %>
    </body>
</html>
