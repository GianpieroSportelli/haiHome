<%-- 
    Document   : formAutocomplete
    Created on : 26-nov-2015, 14.26.34
    Author     : gianp_000
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Place Autocomplete Address Form</title>
        <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
        <meta charset="utf-8">
        <link rel="stylesheet" href="include/css/bootstrap.min.css">
        <link type="text/css" rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500">
        <style>
            body {
                padding-top: 50px;
                padding-bottom: 20px;
            }
        </style>
        <link rel="stylesheet" href="include/css/bootstrap-theme.min.css">
        <link rel="stylesheet" href="include/css/main.css">
        <script src="js/vendor/modernizr-2.6.2-respond-1.1.0.min.js"></script>
    </head>

    <body>
        <div class="bs-example">
            <h2>Inserisci Dati Ricerca</h2>
            <form class="form-horizontal" method="POST" action="MapsServlet" >
                <div class="form-group">
                    <div class="col-xs-9" id="locationField">
                        <input id="autocomplete" class="form-control" placeholder="Enter your address"
                               onFocus="geolocate()" name="autocomplete" type="text" onkeypress="return event.keyCode != 13">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-xs-3" for="address">indirizzo:</label>
                    <div class="col-xs-9">
                        <input type="hidden" name="action" value="getInfoAddress">
                        <input type="text" class="form-control" id="route" placeholder="route" name="route" onkeypress="return event.keyCode != 13">
                        <input type="text" class="form-control" id="street_number" placeholder="street_number" name="street_number" onkeypress="return event.keyCode != 13">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-xs-3" for="city">city:</label>
                    <div class="col-xs-9">
                        <input type="text" class="form-control" id="locality" placeholder="locality" name="locality" onkeypress="return event.keyCode != 13">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-xs-3" for="state">State:</label>
                    <div class="col-xs-9">
                        <input type="text" class="form-control" id="administrative_area_level_1" placeholder="administrative_area_level_1" name="administrative_area_level_1" onkeypress="return event.keyCode != 13">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-xs-3" for="postal_code">Zip Code:</label>
                    <div class="col-xs-9">
                        <input type="text" class="form-control" id="postal_code" placeholder="postal_code" name="postal_code" onkeypress="return event.keyCode != 13">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-xs-3" for="country">country:</label>
                    <div class="col-xs-9">
                        <input type="text" class="form-control" id="country" placeholder="country" name="country" onkeypress="return event.keyCode != 13">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-xs-offset-3 col-xs-9">
                        <input type="submit" class="btn btn-primary" onclick="IsEmpty();" value="Submit" >
                        <input type="reset" class="btn btn-default" value="Reset">
                    </div>
                </div>      
            </form>
        </div>
        <script>
            // This example displays an address form, using the autocomplete feature
            // of the Google Places API to help users fill in the information.
            function IsEmpty() {

                if (document.form.country.value == "")
                {
                    alert("empty country");
                }
                if (document.form.postal_code.value == "")
                {
                    alert("empty postal_code");
                }

                if (document.form.administrative_area_level_1.value == "")
                {
                    alert("empty postal_code");
                }

                if (document.form.locality.value == "")
                {
                    alert("empty locality");
                }
                if (document.form.street_number.value == "")
                {
                    alert("empty street_number");
                }
                if (document.form.route.value == "")
                {
                    alert("empty route");
                }
                return;
            }
            var placeSearch, autocomplete;
            var componentForm = {
                street_number: 'short_name',
                route: 'long_name',
                locality: 'long_name',
                administrative_area_level_1: 'short_name',
                country: 'long_name',
                postal_code: 'short_name'
            };

            function initAutocomplete() {
                // Create the autocomplete object, restricting the search to geographical
                // location types.
                autocomplete = new google.maps.places.Autocomplete(
                        /** @type {!HTMLInputElement} */(document.getElementById('autocomplete')),
                        {types: ['geocode']});

                // When the user selects an address from the dropdown, populate the address
                // fields in the form.
                autocomplete.addListener('place_changed', fillInAddress);
            }

            // [START region_fillform]
            function fillInAddress() {
                // Get the place details from the autocomplete object.
                var place = autocomplete.getPlace();

                for (var component in componentForm) {
                    document.getElementById(component).value = '';
                    document.getElementById(component).disabled = false;
                }

                // Get each component of the address from the place details
                // and fill the corresponding field on the form.
                for (var i = 0; i < place.address_components.length; i++) {
                    var addressType = place.address_components[i].types[0];
                    if (componentForm[addressType]) {
                        var val = place.address_components[i][componentForm[addressType]];
                        document.getElementById(addressType).value = val;
                    }
                }
            }
            // [END region_fillform]

            // [START region_geolocation]
            // Bias the autocomplete object to the user's geographical location,
            // as supplied by the browser's 'navigator.geolocation' object.
            function geolocate() {
                if (navigator.geolocation) {
                    navigator.geolocation.getCurrentPosition(function (position) {
                        var geolocation = {
                            lat: position.coords.latitude,
                            lng: position.coords.longitude
                        };
                        var circle = new google.maps.Circle({
                            center: geolocation,
                            radius: position.coords.accuracy
                        });
                        autocomplete.setBounds(circle.getBounds());
                    });
                }
            }
            // [END region_geolocation]

        </script>
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC2yod6637sOZqbmDNOZSUh-30b6xTchBE&signed_in=true&libraries=places&callback=initAutocomplete"
        async defer></script>
    </body>
</html>