<%-- 
    Document   : maps.jsp
    Created on : 10-nov-2015, 16.56.24
    Author     : gianp_000
--%>

<%@page import="org.json.JSONObject"%> 
<%@page import="java.util.ArrayList"%> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>        
        <meta name="google-signin-client_id" content="495487496441-r9l7mppbotcf6i3rt3cl7fag77hl0v62.apps.googleusercontent.com" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        
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
        <link rel="stylesheet" href="tutcss.css">
        
        <title>Maps</title>
        
        <style>
            body {
                padding-top: 50px;
                padding-bottom: 20px;
            }
            
            #map {
                width:100%;
                height:400px;
            } 
        </style>
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC2yod6637sOZqbmDNOZSUh-30b6xTchBE&libraries=places"></script>
        <%
            ArrayList<JSONObject> fromJ = (ArrayList<JSONObject>) request.getAttribute("JSONList");
            String lng = (request.getAttribute("lng")).toString();
            String lat = (request.getAttribute("lat")).toString();

            /*String info = req[2];
             String fAddress= req[3];
             String contentString = "<div id=\"content\">"
             + "<div id=\"siteNotice\">"
             + "</div>"
             + "<h1 id=\"firstHeading\" class=\"firstHeading\">" + fAddress + "</h1>"
             + "<div id=\"bodyContent\">"
             + info
             + "</div>"
             + "</div>";*/
        %>
    </head>
    <body>
        <%@include file="/header.jsp" %>
        <div class="container">
            <div class="row">
                <div class="col-sm-2" ></div>
                <div class="col-sm-8" ><div id="map" class="" ></div></div>
                <div class="col-sm-2"></div>
            </div>
        </div>
        <%@include file="/footer.jsp" %>
        <script>
            var geocoder;
            var map;
            var geoAddress;
            var icon = "images/basket.png";

            function initialize() {
                geocoder = new google.maps.Geocoder();
                var latlng = new google.maps.LatLng(<%= lat%>, <%= lng%>);
                var mapOptions = {
                    zoom: 8,
                    center: latlng
                };
                map = new google.maps.Map(document.getElementById("map"), mapOptions);
                var marker = new google.maps.Marker({
                    map: map,
                    position: latlng,
                    title: 'geocode request address'
                });
                map.setZoom(16);
            }
            window.initialize();

            function addMarker(location, label) {
                //alert(label);
                // Add the marker at the clicked location, and add the next-available label
                // from the array of alphabetical characters.
                var marker = new google.maps.Marker({
                    position: location,
                    title: label,
                    map: map,
                    icon: icon
                });
            }
            <%for (JSONObject jobj : fromJ) {
                    String latMarket = "" + jobj.getJSONObject("location").get("lat");
                    String lngMarket = "" + jobj.getJSONObject("location").get("lng");
                    String label = jobj.getString("name");
                    label = label.replace("'", "&quot;");
            %>
            window.addMarker(new google.maps.LatLng(<%= latMarket%>, <%= lngMarket%>), '<%= label%>');
            <%System.out.println(jobj.getString("name") + "," + jobj.getJSONObject("location")); %>
            <%}%>
            /*function codeAddressInfo(address, contentString) {
             var infowindow = new google.maps.InfoWindow({
             content: contentString
             });
             geocoder.geocode({'address': address}, function (results, status) {
             if (status == google.maps.GeocoderStatus.OK) {
             map.setCenter(results[0].geometry.location);
             var geo=results[0].geometry.location;
             document.getElementById("geo").value=geo;
             window.codeStore(results[0].geometry.location,100);
             var marker = new google.maps.Marker({
             map: map,
             position: results[0].geometry.location,
             title: address
             });
             marker.addListener('click', function () {
             infowindow.open(map, marker);
             });
             map.setZoom(15);
             } else {
             alert("Geocode was not successful for the following reason: " + status);
             }
             document.getElementById("form").submit();
             });
             }
             window.codeAddressInfo('', '');
             function codeStore(geoAddress, radius) {
             var address = geoAddress;
             
             map = new google.maps.Map(document.getElementById('map'), {
             center: address,
             zoom: 15
             });
             
             var request = {
             location: address,
             radius: radius,
             types: ['store']
             };
             
             service = new google.maps.places.PlacesService(map);
             service.nearbySearch(request, callback);
             }
             
             function callback(results, status) {
             if (status == google.maps.places.PlacesServiceStatus.OK) {
             for (var i = 0; i < results.length; i++) {
             var place = results[i];
             createMarker(results[i]);
             }
             }
             }
             function createMarker(place) {
             var placeLoc = place.geometry.location;
             var marker = new google.maps.Marker({
             map: map,
             position: place.geometry.location
             });
             
             google.maps.event.addListener(marker, 'click', function () {
             infowindow.setContent(place.name);
             infowindow.open(map, this);
             });
             }*/
        </script>
    </body>
</html>
