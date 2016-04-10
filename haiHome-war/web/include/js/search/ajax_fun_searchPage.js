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
var caroselli = new Array();
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
                    //console.log(load_annuncio_image(annuncio));
                });

                actual = 0;
                n_page = n_annunci / N_ANNUNCI_X_PAGE;

                create_pageResult();
                //add_button();
                selectpage(1);

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
        var names = new Array();
        var index = 0;
        page_html += '<div class = "search-result row" id =' + (page + 1) + '_RESULT>';
        for (i = page * N_ANNUNCI_X_PAGE; (i < ((page + 1) * N_ANNUNCI_X_PAGE) && i < annunci.length); i++) {
            /*var title = '<h3 > <a href = "#" >' + annunci[i].Indirizzo + '</a></h3 >';
             var data_i = '<p > Data inizio: ' + annunci[i].DataInizioAffitto + '</p>';
             var data_p = '<p > Data pubblicazione: ' + annunci[i].DataPubblicazione + '</p>';
             var prezzo = '<p > Prezzo: ' + annunci[i].Prezzo + '</p>';
             var quartiere = '<p > Quartiere: ' + annunci[i].Quartiere + '</p>';
             var n_loc = '<p > Numero Locali: ' + annunci[i].NumeroLocali + '</p>';
             var loc = '<p > Locatore: ' + annunci[i].Locatore.nome + '</p>';
             var desc = '<p > Descrizione: ' + annunci[i].Descrizione + '</p>';
             var met = '<p > Metratura: ' + annunci[i].Metratura + '</p>';
             var html = result_div + title + data_i + data_p + prezzo + quartiere + n_loc + loc + desc + met + close_div;
             */
            var html = getCodeCarousel(annunci[i], i);
            page_html += "<div class=\"col-sm-" + 12 / N_ANNUNCI_X_PAGE + "\">" + html + "</div>";
            var lat = annunci[i].Lat;
            var lng = annunci[i].Lng;
            var name = i;
            names[index] = name;
            index += 1;
        }
        page_html += close_div;
        page_annunci[page] = page_html;
        caroselli[page] = names;
    }
}

function getCodeCarousel(annuncio, k) {
    var lat = annuncio.Lat;
    var lng = annuncio.Lng;
    var title = annuncio.Indirizzo;
    var data_i = annuncio.DataInizioAffitto;
    var data_p = annuncio.DataPubblicazione;
    var prezzo = annuncio.Prezzo;
    var quartiere = annuncio.Quartiere;
    var n_loc = annuncio.NumeroLocali;
    var loc = annuncio.Locatore.nome;
    var desc = annuncio.Descrizione;
    var met = annuncio.Metratura;
    var html = "<div id=\"annuncio-" + k + "\" OnClick=send_Annuncio(" + k + ")><div class=\"panel panel-default\">" + "<div class='panel-heading'>" +
            "<div class=\"carousel slide qcar\" data-ride=\"carousel\" id=\"quote-carousel-" + k + "\">" +
            "<!-- Bottom Carousel Indicators -->" +
            "<ol class=\"carousel-indicators\">" +
            "<li data-target=\"#quote-carousel-" + k + "\" data-slide-to=\"0\" class=\"active\"></li>" +
            "<li data-target=\"#quote-carousel-" + k + "\" data-slide-to=\"1\"></li>" +
            "<li data-target=\"#quote-carousel-" + k + "\" data-slide-to=\"2\"></li>" +
            "</ol>" +
            "<!-- Carousel Slides / Quotes -->" +
            "<div class=\"carousel-inner\" align=\"center\">" +
            "<!-- Quote 1 -->" +
            "<div class=\"item active\">" +
            "<blockquote>" +
            "<img class=\"img-responsive img-thumbnail\" src=\"http://www.reactiongifs.com/r/overbite.gif\" style=\"width: 250px;height:250px;\">" +
            "<!--<img class=\"img-responsive img-thumbnail\" src=\"https://s3.amazonaws.com/uifaces/faces/twitter/kolage/128.jpg\" style=\"width: 150px;height:150px;\">-->" +
            "</blockquote>" +
            "</div>" +
            "<!-- Quote 2 -->" +
            "<div class=\"item\">" +
            "<blockquote>" +
            "<img class=\"img-responsive img-thumbnail\" src=\"https://s3.amazonaws.com/uifaces/faces/twitter/mijustin/128.jpg\" style=\"width: 250px;height:250px;\">" +
            "</blockquote>" +
            "</div>" +
            "<!-- Quote 3 -->" +
            "<div class=\"item\">" +
            "<blockquote>" +
            "<img class=\"img-responsive img-thumbnail\" src=\"Immagini/appartamento_prova/cucina/cucina.jpg\" style=\"width: 250px;height:250px;\">" +
            "</blockquote>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "</div>" +
            //"</div>"+
            "<div class=\"panel-body snip\">" +
            "<p class=\"text-muted\">" + title + "</p>" +
            //"<p>Data inizio: " + data_i + "</p>" +
            //"<p>Data pubblicazione: " + data_p + "</p>" +
            //"<p> Prezzo: " + prezzo + "</p>" +
            "<p class=\"text-muted\"> <span class=\"text-primary\">Quartiere: </span> " + quartiere + "</p>" +
            //"<p>Numero locali: " + n_loc + "</p>" +
            "<p class=\"text-muted\"> <span class=\"text-primary\">Locatore: </span> " + loc + "</p>" +
            "<p class=\"text-muted\">" + desc + "</p>" +
            //"<p>Metratura: " + met + "</p>" +
            "</div>" +
            "</div>" +
            "</div>";
    return html;
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
        $("#list-result").append(page_annunci[page - 1]);
        activateCaroselli(page - 1);
        //$("#" + page).addClass("disabled");
        actual = page;
    } else if (actual !== (+page)) {
        //$("#" + (actual) + "_RESULT").remove();
        //$("#button-div").before(page_annunci[page - 1]);
        $("#" + (actual) + "_RESULT").after(page_annunci[page - 1]);
        //$("#" + actual).removeClass("disabled");
        //$("#" + page).addClass("disabled");
        actual = +page;
    }
}
function activateCaroselli(page) {
    var names = caroselli[page];
    for (var i = 0; i < names.length; i++) {
        $('#quote-carousel-' + names[i]).carousel({
            pause: true,
            interval: 4000
        });
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
/*function load_annuncio_image(annuncio) {
 //var stanze = annuncio.Stanze;
 $.each(annuncio.Stanze, function (index, stanze) {
 $.each(stanze, function (index2, stanza) {
 var paths = stanza.Foto;
 alert(paths);
 var action = "Ricerca-getImageAnnuncio";
 $.ajax({
 type: "POST",
 url: "ServletRicerca",
 data: 'action=' +action  +'&srcImg=' + encodeURIComponent(paths),
 dataType: "json",
 success: function (responseJson) {
 
 $.each(responseJson, function (index, item) {
 
 });
 }
 });
 });
 });
 }*/

$(window).scroll(function () {
    if ($(window).scrollTop() == $(document).height() - $(window).height()) {
        nextpage();
    }
});
/*$(window).scroll(function(){    
 $("#searchDiv").stop().animate({"marginTop": ($(window).scrollTop()) + "px", "marginLeft":($(window).scrollLeft()) + "px"}, "fast" );
 });*/

function send_Annuncio(k) {
    var annuncio = annunci[k];
    console.log(annuncio);
    var url = "/haiHome-war/ServletController";
    var url2="/haiHome-war/dettagliAnnuncio.jsp";
    var json= JSON.stringify(annuncio);
    console.log(k);
    console.log(json);
    /*$.ajax({
        url: url,
        //dataType: 'json', // I was pretty sure this would do the trick
        data: $.param({ action: 'dettagliAnnuncio', data: json }),
        type: 'POST',
        success: function (data) {
            window.location = data;
        }
    });*/
    $.session.set('dettagli', json);
    window.open(url2);
}

// implement JSON.stringify serialization
JSON.stringify = JSON.stringify || function (obj) {
    var t = typeof (obj);
    if (t != "object" || obj === null) {
        // simple data type
        if (t == "string") obj = '"'+obj+'"';
        return String(obj);
    }
    else {
        // recurse array or object
        var n, v, json = [], arr = (obj && obj.constructor == Array);
        for (n in obj) {
            v = obj[n]; t = typeof(v);
            if (t == "string") v = '"'+v+'"';
            else if (t == "object" && v !== null) v = JSON.stringify(v);
            json.push((arr ? "" : '"' + n + '":') + String(v));
        }
        return (arr ? "[" : "{") + String(json) + (arr ? "]" : "}");
    }
};

