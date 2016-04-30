/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function initAutocomplete() {
    
    //Seleziono l'input su cui inserire l'autocomplite
    var input = document.getElementById('#inpIndirizzoProva');
    
    //inserisco le opzioni dell'autocomplite
    var options = {
        types: ['(address)'],
        componentRestrictions: {country: 'it'}
    };
    
    // Create the autocomplete object, restricting the search to geographical
    // location types. 
    autocomplete = new google.maps.places.Autocomplete(
            /** @type {!HTMLInputElement} */input, options);

    // When the user selects an address from the dropdown, populate the address
    // fields in the form.
    autocomplete.addListener('place_changed', fillInAddress);
}


    function fillInAddress() {
        // Get the place details from the autocomplete object.
        var place = autocomplete.getPlace();
        
        var informazioni = place.formatted_address;
        
        console.log(informazioni);
       
    }