      // This example displays an address form, using the autocomplete feature
      // of the Google Places API to help users fill in the information.

      // This example requires the Places library. Include the libraries=places
      // parameter when you first load the API. For example:
      // <script src="https://maps.googleapis.com/maps/api/js?key=YOUR_API_KEY&libraries=places">

      var acCivFlag = false;
      var acIndFlag = false;
      var placeSearch, autocomplete;
      var componentForm = {
        street_number: 'short_name',
        route: 'long_name',
        locality: 'long_name',
        administrative_area_level_1: 'short_name',
        country: 'long_name',
        postal_code: 'short_name'
      };
      
    var componentString = {
        street_number: 'short_name',
        locality: 'long_name',
        route: 'long_name',
        postal_code: 'short_name'
    };
    
            var civicoTag = $("#inpCivico");
            var indirizzoTag = $("#inpIndirizzo");
            
            
            civicoTag.focus(function() {
                civicoTag.val("");
                
            });
            
                       civicoTag.focusout(function () {
              //civicoTag.attr("disabled","disabled"); 
               
                    
            });
            
            
           indirizzoTag.focusout(function () {
               /*
                if(!acIndFlag){
                    indirizzoTag.val("");
                }*/
               
                    
            });
            
    
    

      function initAutocomplete() {
        // Create the autocomplete object, restricting the search to geographical
        // location types.
        autocomplete = new google.maps.places.Autocomplete(
            /** @type {!HTMLInputElement} */(document.getElementById('inpIndirizzo')),
            {
                types: ['address'],
                componentRestrictions: {country: 'it'}
            });

        // When the user selects an address from the dropdown, populate the address
        // fields in the form.
        autocomplete.addListener('place_changed', fillInAddress);
      }

   // [START region_fillform]
    function fillInAddress() {
        // Get the place details from the autocomplete object.
        var place = autocomplete.getPlace();
        
        
        //DA ELIMINARE
        
         for (var i = 0; i < place.address_components.length; i++) {
            var addressType = place.address_components[i].types[0];
            if (componentForm[addressType]) {
                var val = place.address_components[i][componentForm[addressType]];
                
                console.log(addressType + " " + val);
                
            }
        }
        
        //FINE DA ELIMINARE
        
        // Get each component of the address from the place details
        // and fill the corresponding field on the form.
        for (var i = 0; i < place.address_components.length; i++) {
            var addressType = place.address_components[i].types[0];
            if (componentString[addressType]) {
                var val = place.address_components[i][componentString[addressType]];
                
                componentString[addressType] = val;
                
            }
        }
        var indirizzo = componentString['route'];
        var civico = componentString['street_number'];
        var CAP = componentString['postal_code'];
        var citta =  componentString['locality'];

        acCivFlag = false;
        
        civicoTag = $("#inpCivico");
        
        
        
        console.log("sono qui " + citta);
        
        if(indirizzo != "long_name" && citta=="Torino"){
        document.getElementById('inpIndirizzo').value = indirizzo;
            acIndFlag = true;
            
        if(civico != "short_name"){
            
            
            civicoTag.val(civico);

                   
               //document.getElementById('inpCivico').value = civico; 
               
            
            acCivFlag = true;
            
        }else{
            //civicoTag.removeAttr("disabled"); 
            acCivFlag = false;
        }

               
            
            
        }else{
            alert("Ci sono problemi");
            acIndFlag = false;
        }

        
        aggiornaListaQuartieri(CAP);
        
    componentString = {
        street_number: 'short_name',
        locality: 'long_name',
        route: 'long_name',
        postal_code: 'short_name'
    };

}

      // Bias the autocomplete object to the user's geographical location,
      // as supplied by the browser's 'navigator.geolocation' object.
      function geolocate() {
        if (navigator.geolocation) {
          navigator.geolocation.getCurrentPosition(function(position) {
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
      
      function civicoFunction(){
          
          var indirizzo = $("#inpIndirizzo");
          var civico = $("#inpCivico");

          
          var indirizzoStr = indirizzo.val() + " " + civico.val();
          
          indirizzo.val(indirizzoStr);
          indirizzo.focus();
          
          
          
          
      }
      
      function checkAddress(){
          console.log("indirizzo corretto : " + acCivFlag);
          return acCivFlag;
      }
          