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
var dim_image_car = 250; //dimensione immagine del carosello

//Funzion Ajax per caricare e inizializzare la mappa e caricare gli annunci
function annunci_search() {
    $.post("ServletController",
            {action: "Ricerca-geoCity"},
            function (responseJson) {                               // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response JSON...
                var latlng = [0, 0];                                   // Create HTML <ul> element and append it to HTML DOM element with ID "somediv".
                $.each(responseJson, function (index, item) {       // Iterate over the JSON array.
                    latlng[index] = item;                              // Create HTML <li> element, set its text content with currently iterated item and append it to the <ul>.
                });

                initialize(latlng[0], latlng[1]);

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
                    addMarkerToJSON(annuncio, index);
                    //console.log(load_annuncio_image(annuncio));
                });
                actual = 0;
                n_page = n_annunci / N_ANNUNCI_X_PAGE;
                create_pageResult();
                //selectpage(1);
            });
}

function create_pageResult() {
    page_annunci = [];
    var result_div = '<div class = "search-result" >';
    var close_div = '</div>';
    var no_res = '<p > Nessun Risultato!! </p>';
    var page_html = '';
    if (n_page == 0) {
        $("#list-result").append(result_div + no_res + close_div);
    }
    for (page = 0; page < n_page; page++) {
        page_html = '';
        var names = new Array();
        var index = 0;
        page_html += '<div class = "search-result row" id =' + (page + 1) + '_RESULT>';
        for (var i = page * N_ANNUNCI_X_PAGE; (i < ((page + 1) * N_ANNUNCI_X_PAGE) && i < annunci.length); i++) {
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
        if (page == 0) {
            selectpage(1);
        }
    }
}

function getCodeCarousel(annuncio, k) {
    var html = "<div id=\"annuncio-" + k + "\" OnClick=send_Annuncio(" + k + ") style=\"cursor:pointer\">"; //1
    html += "<div class=\"panel panel-default div_snippet\">"; //2
    html += "<div class='panel-heading'>"; //3
    html += create_carousel(annuncio);
    html += "</div>"; //chiusura testa del pannello
    html += "<div class=\"panel-body snip\">";
    html += create_info_annuncio(annuncio);
    html += "</div>"; //3
    html += "</div>"; //2
    html += "</div>"; //1
    return html;
}

function create_info_annuncio(annuncio) {
    var html = "";
    var tipoAnnuncio = "Annuncio Stanze";
    if (annuncio.Atomico) {
        tipoAnnuncio = "Annuncio Appartamento";
    }
    html += "<div class=\"center\">" +
            "<h1>" + tipoAnnuncio + "</h1>" +
            "<p class=\"text-muted\">" + annuncio.Indirizzo + "</p>" +
            //"<p class=\"text-muted\">" + annuncio.Descrizione + "</p>" +
            "<p class=\"text-muted\"><span class=\"text-primary\">Metratura Appartamento: </span>" + annuncio.Metratura + "</p>" +
            "<p class=\"text-muted\"><span class=\"text-primary\">Data inizio affitto: </span>" + annuncio.DataInizioAffitto + "</p>" +
            "<p class=\"text-muted\"> <span class=\"text-primary\">Quartiere: </span> " + annuncio.Quartiere + "</p>";
    //"<p class=\"text-muted\"> <span class=\"text-primary\">Locatore: </span> " + annuncio.Locatore.nome + "</p>";
    if (annuncio.Atomico) {
        html += "<p class=\"text-muted\"><span class=\"text-primary\">Numero locali: </span>" + annuncio.NumeroLocali + "</p>" +
                "<p class=\"text-muted\"><span class=\"text-primary\"> Prezzo: </span>" + annuncio.Prezzo + " &euro;</p>";
    }
    html += "</div>"; //chiusura center
    return html;
}

function create_carousel(annuncio) {
    var html = "";
    html += "<div class=\"carousel slide qcar\" data-ride=\"carousel\" id=\"quote-carousel-" + annuncio.OID + " \">"; //4       
    var stanze = annuncio.Stanze[0];
    html += "<div class=\"carousel-inner\" align=\"center\">"; //5
    $.each(stanze, function (index, stanza) {
        var superT = stanza.SuperTipo;
        if (superT == "StanzaInAffitto") {
            var archiviato = stanza.achiviato;

            if (!archiviato) {
                html += slide_Affitto(stanza, index, annuncio.Atomico);
            }
        } else {
            html += slide_Accessoria(stanza, index);
        }
    });
    html += "</div>" + //5
            "</div>"; //4
    return html;
}

function slide_Accessoria(stanza, index) {
    var html = "";
    var fotos = stanza.Foto;
    $.each(fotos, function (i, foto) {
        if (index == 0 && i == 0) {
            html += "<div class=\"item active\">"; //5.a
        } else {
            html += "<div class=\"item\">"; //5.b
        }
        html += "<blockquote>" +
                "<div class=\" carousel-item \">" +
                "<img class=\"img-responsive img-thumbnail\" src=\"" + foto + "\" style=\"width:" + dim_image_car + "px;height:" + dim_image_car + "px;\">" +
                "</div>" +
                "<p class=\"text-muted\"> <span class=\"text-primary\">Tipo Stanza: </span> " + stanza.Tipo + "</p>" +
                "</blockquote>" +
                "</div>"; //5
    });
    return html;
}

function slide_Affitto(stanza, index, atomico) {
    var html = "";
    var fotos = stanza.Foto;
    $.each(fotos, function (i, foto) {
        if (index == 0 && i == 0) {
            html += "<div class=\"item active\">";
        } else {
            html += "<div class=\"item\">";
        }
        html += "<blockquote>" +
                "<img class=\"img-responsive img-thumbnail\" src=\"" + foto + "\" style=\"width:" + dim_image_car + "px;height:" + dim_image_car + "px;\">" +
                "<p class=\"text-muted\"> <span class=\"text-primary\">Tipo Stanza: </span> " + stanza.Tipo + " ";
        if (!atomico) {
            html += "<span class=\"text-primary\"> Prezzo: </span> " + stanza.Prezzo + "&euro;";
        }
        html += "</p>" +
                "</blockquote>" +
                "</div>";
    });
    return html;
}

function selectpage(page) {
    if (actual === 0) {
        $("#list-result").append(page_annunci[page - 1]);
        activateCaroselli();
        actual = page;
    } else if (actual !== (+page)) {
        $("#" + (actual) + "_RESULT").after(page_annunci[page - 1]);
        actual = +page;
    }
}

function activateCaroselli() {
    $('.carousel').carousel({
        pause: true,
        interval: 4000
    });
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
    /*var infowindow = new google.maps.InfoWindow({
     content: contentString
     });
     marker.addListener('click', function () {
     infowindow.open(map, marker);
     });*/
}

function addMarkerToJSON(annuncio, index) {
    var lat = annuncio.Lat;
    var lng = annuncio.Lng;
    var address = annuncio.Indirizzo;
    /*var contentString = ' <div id = \"content\" > ' +
     '<div id = \"siteNotice\" >' +
     '</div>' +
     '<div id = \"firstHeading\" > <span style =\"font-size:18px; font-weight:bold;\"> Uluru </span><br><br><img src=' + icon + ' style=\"max-width:100%;\" / > <br> <br> ' +
     'Contact info <br> Phone: + 65 123456789 <br> Email: <a href =\"mailto:info@example.com\"> info@example.com </a>' +
     '</div><div id=”bodyContent”>' +
     '<p> <b>' + address + ' </b> Lorem upsum</p >' +
     ' </div>' +
     '</div>';*/
    var label = "Annuncio " + (index + 1);
    window.addMarker(new google.maps.LatLng(lat, lng), label);
}

function init_filtro() {
    $.post("ServletController",
            {action: "Ricerca-getQuartieri"},
            function (responseJson) {
                var html = '';
                $.each(responseJson, function (index, item) {
                    html = '<option id=\"' + item + '\" value=\"' + item + '\">' + item + '</option>';
                    $("#quartieri").append(html);
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
                    html = '<option id=\"tipoStanza-' + item + '\" value=\"' + item + '\">' + item + '</option>';
                    $("#tipoStanza").append(html);
                });
                var getf = $.Deferred();
                getf.done(getfiltro);
                getf.resolve();
            });
}

function getfiltro() {
    $.post("ServletController",
            {action: "Ricerca-getFiltro"},
            function (filtro) {
                //alert(JSON.stringify(responseJson));
                //var html = '';
                //$.each(responseJson, function (index, filtro) {
                console.log(filtro);
                var Quartieri = filtro.Quartieri;
                console.log(Quartieri);
                //alert(Quartieri);
                $.each(Quartieri, function (index, quart) {
                    //$('#quartieri')._processSelectOption($('#' + quart));
                    $('#' + quart).prop('selected', true);
                });
                $('#quartieri').searchableOptionList({
                    maxHeight: '250px'
                });
                $('#pricefrom').val(filtro.Prezzo);
                if (filtro.CompresoCondominio) {
                    $('#compresoCondominio').prop('selected', true);
                }
                if (filtro.CompresoRiscaldamento) {
                    $('#compresoRiscaldamento').prop('selected', true);
                }
                var tipo = filtro.Tipo;
                if (tipo == "Appartamento") {
                    $('#tipo-' + tipo).prop('selected', true);
                    $('#numeroLocali').val(filtro.NumeroLocali);
                    $('#numeroCamere').val(filtro.NumeroCamereDaLetto);
                    $('#numeroBagni').val(filtro.NumeroBagni);
                    $('#metratura').val(filtro.Metratura);
                    $("#divTipoStanza").hide();
                    $("#divNLocali").show("slow");
                    $("#divNCamere").show("slow");
                    $("#divNBagni").show("slow");
                    $("#divMetratura").show("slow");
                } else if (tipo == "Stanza") {
                    $('#tipo-' + tipo).prop('selected', true);
                    var tipoStanza = filtro.TipoStanza;
                    $('#tipoStanza-' + tipoStanza).prop('selected', true);
                    $("#divTipoStanza").show("slow");
                    $("#divNLocali").hide();
                    $("#divNCamere").hide();
                    $("#divNBagni").hide();
                    $("#divMetratura").hide();

                }
                var id = filtro.Id;
                console.log("id filtro: " + id);
                if (id == undefined) {
                    $("#saveButton").text("Salva");
                } else {
                     $("#saveButton").text("Modifica");
                }
            });
    // });
}

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
    var url2 = "/haiHome-war/dettagliAnnuncio.jsp";
    var json = JSON.stringify(annuncio);
    console.log(k);
    console.log(json);
    $.session.set('dettagli', json);
    window.open(url2);
}

// implement JSON.stringify serialization
JSON.stringify = JSON.stringify || function (obj) {
    var t = typeof (obj);
    if (t != "object" || obj === null) {
        // simple data type
        if (t == "string")
            obj = '"' + obj + '"';
        return String(obj);
    } else {
        // recurse array or object
        var n, v, json = [], arr = (obj && obj.constructor == Array);
        for (n in obj) {
            v = obj[n];
            t = typeof (v);
            if (t == "string")
                v = '"' + v + '"';
            else if (t == "object" && v !== null)
                v = JSON.stringify(v);
            json.push((arr ? "" : '"' + n + '":') + String(v));
        }
        return (arr ? "[" : "{") + String(json) + (arr ? "]" : "}");
    }
};

function persistiFiltro() {
    console.log("salva filtro");
    $.post("ServletController",
            {action: "Ricerca-salvaFiltro"},
            function (item) {
                //var html = '';
                alert(item);
                var getf = $.Deferred();
                getf.done(getfiltro);
                getf.resolve();
            });
}

$(document).ready(function () {
    $('#searchForm').ajaxForm(function () {
        annunci_search();
        var getf = $.Deferred();
        getf.done(getfiltro);
        getf.resolve();
    });
});


function loggatoStudente() {
    //console.log("verifica log Studente");
    $.post("ServletController",
            {action: "Ricerca-loggatoStudente"},
            function (item) {
                //var html = '';
                //alert(item);
                console.log("Studente loggato?: " + item);
                if (item == "true") {
                    $("#saveButton").show("fast");
                }
            });
}


