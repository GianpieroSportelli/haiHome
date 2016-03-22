/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
        var load_A = $.Deferred();
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

        actual = 0;
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
        var filtro_S = $.Deferred();
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

