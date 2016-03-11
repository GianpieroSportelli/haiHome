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
        <style>
            #map {
                width:100%;
                height:400px;
            }
        </style>
        <!-- INIZIO import SOL -->
        <script type="text/javascript" src="include/js/sol.js"></script>
        <link rel="stylesheet" href="include/css/sol.css">
        <!-- FINE import SOL -->
        <style>
            body {
                padding-top: 50px;
                padding-bottom: 20px;
            }
        </style>

    </head>
    <body>
        <%
            ArrayList<String> quartieri = (ArrayList<String>) request.getAttribute("quartieri");
            ArrayList<String> tipoStanza = (ArrayList<String>) request.getAttribute("tipoStanza");
        %>
        <!-- bootstap necessita di container ne esistono di 2 tipi container e container-fluid -->

        <div class="container">

            <div class="row">
                <div class="col-sm-9">
                    <%
                        JSONArray annunci = (JSONArray) request.getAttribute("JSONAnnunci");
                        double[] latlng = (double[]) request.getAttribute("latlng");
                        double lat = latlng[0];
                        double lng = latlng[1];
                    %>
                    <div id="map" class="" ></div>
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

                        function addMarker(location, label) {
                            //alert(label);
                            // Add the marker at the clicked location, and add the next-available label
                            // from the array of alphabetical characters.
                            var marker = new google.maps.Marker({
                                position: location,
                                title: label,
                                map: map
                                        //icon: icon
                            });
                        }
                        <%for (int i = 0; i < annunci.length(); i++) {
                            JSONObject json_annuncio=annunci.getJSONObject(i);
                            //System.out.println(json_annuncio);
                            double[] latlngAnnuncio = (double[]) json_annuncio.get("LatLng");
                        %>
                        document.addMarker(new google.maps.LatLng(<%= latlngAnnuncio[0]%>, <%= latlngAnnuncio[1]%>), '<%= "ANNUNCIO " + i%>');
                        
                        <%}%>
                    </script>
                </div>
                <div class="col-sm-3">
                    <div class="well">
                        <h3 align="center">Filtro di Ricerca</h3>
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
                                    });
                                </script>
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
                                <label for="pricefrom" class="control-label">Min Price</label>
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
                            <!--<div class="form-group">
                                <label for="priceto" class="control-label">Max Price</label>
                                <div class="input-group">
                                    <div class="input-group-addon" id="basic-addon2">Euro</div>
                                    <input type="text" class="form-control" id="priceto" name="priceto" aria-describedby="basic-addon1">
                                </div>
                            </div>-->
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
                                    <option value="all">-</option>
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
                            <button type="submit" class="btn btn-danger glyphicon glyphicon-search"></button>
                        </form>
                    </div>
                </div>
            </div>
            <%@include file="/footer2.jsp" %>                   
        </div>
    </body>
</html>
