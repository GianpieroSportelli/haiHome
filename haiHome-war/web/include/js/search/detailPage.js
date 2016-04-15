/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var geocoder;
var map;
var geoAddress;
var icon = "images/basket.png";
var dim_image_car=500;

$('#quote-carousel').carousel({
    pause: true,
    interval: 4000
});

function initialize(annuncio) {
    geocoder = new google.maps.Geocoder();
    var latlng = new google.maps.LatLng(annuncio.Lat, annuncio.Lng);
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
    addServices(annuncio);
}
//window.initialize();
function addServices(annuncio) {
    $.ajax({
        url: "ServletController",
        type: 'post',
        dataType: 'json',
        data: {action: "Ricerca-addServices", annuncio: JSON.stringify(annuncio)},
        success: function (responseJson) {
            //console.log(responseJson);
            $.each(responseJson, function (index, item) {
                //console.log(item);
                var lat = item.location.lat;
                var lng = item.location.lng;
                var label = item.name;
                var location = new google.maps.LatLng(lat, lng);
                addMarker(location, label);
            });
        }
    });
}
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
function create_Page(annuncio) {
    var html = "";
    html += info_annuncio(annuncio);
    var open_ul = "<ul class=\"nav nav-tabs\">";
    html += open_ul;
    var stanze = annuncio.Stanze[0];

    $.each(stanze, function (index, stanza) {
        //console.log(stanza);
        var active = "";
        if (index == 0) {
            active = "class=\"active\"";
        }
        if (stanza.SuperTipo == "StanzaInAffitto") {
            //console.log(stanza.archiviato);
            var archiviato = stanza.archiviato;
            //console.log(archiviato);
            if (!archiviato) {
                html += "<li " + active + "><a data-toggle=\"tab\" href=\"#" + stanza.OID + "\">" + stanza.Tipo + "</a></li>"; //"-" + stanza.OID +
            }
        } else {
            html += "<li " + active + "><a data-toggle=\"tab\" href=\"#" + stanza.OID + "\">" + stanza.Tipo + "</a></li>"; //"-" + stanza.OID +
        }
    });
    var close_ul = "</ul>";
    html += close_ul;
    var open_content = "<div class=\"tab-content\">";
    html += open_content;
    $.each(stanze, function (index, stanza) {
        var corpo = "";
        if (stanza.SuperTipo == "StanzaInAffitto") {
            var archiviato = stanza.achiviato;
            if (!archiviato) {
                corpo = create_corpo_affitto(stanza, index == 0, annuncio.Atomico);
            }
        } else {
            corpo = create_corpo_accessoria(stanza, index == 0);
        }
        html += corpo;
    });
    var close_content = "</div>";
    html += close_content;
    return html;
}

function create_corpo_accessoria(stanza, first) {
    var html = "";
    var active = "";
    if (first) {
        active = "in active";
    } else {
        active = "";
    }
    html += "<div id=\"" + stanza.OID + "\" class=\"tab-pane fade center " + active + "\">" +
            "<h1>" + stanza.Tipo + "</h1>";
    html += create_carousel_stanza(stanza);
    //html += "<p>" + JSON.stringify(stanza) + "</p>";
    //html += "<div class=\"center\">";
    html += "<p class=\"text-muted\"> <span class=\"text-primary\">Metratura: </span> " + stanza.Metratura + "</p>";
    //html += "</div>";//center close
    html += "</div>";
    return html;
}

function create_corpo_affitto(stanza, first, atomico) {
    var html = "";
    var active = "";
    if (first) {
        active = "in active";
    } else {
        active = "";
    }
    html += "<div id=\"" + stanza.OID + "\" class=\"tab-pane fade center " + active + "\">" +
            "<h1>" + stanza.Tipo + "</h1>";
    var visibile=stanza.visibile;
    if(!visibile){
    html += "<p class=\"text-muted\"> <span class=\"text-warning\">Questa camera non rispetta la tua ricerca ma è disponibile!!</span></p>";    
    }
    html += create_carousel_stanza(stanza);
    //html += "<p>" + JSON.stringify(stanza) + "</p>" ;
    //html += "<div class=\"center\">";
    html += "<p class=\"text-muted\"> <span class=\"text-primary\">Metratura: </span> " + stanza.Metratura + "</p>";
    if (!atomico) {
        var cmpCon = stanza.compresoCondominio;
        var cmpRisc = stanza.compresoRiscaldamento;
        var compreso = " il prezzo ";
        if (cmpCon && cmpRisc) {
            compreso += "comprende le spese di <span class=\"text-primary\">Condominio</span> e di  <span class=\"text-primary\">Riscaldamento</span>.";
        } else if (!cmpCon && cmpRisc) {
            compreso += "comprende le spese di <span class=\"text-primary\">Riscaldamento</span> ma non quelle di <span class=\"text-primary\">Condomino</span>.";
        } else if (cmpCon && !cmpRisc) {
            compreso += "comprende le spese di <span class=\"text-primary\">Condomino</span> ma non quelle di <span class=\"text-primary\">Riscaldamento</span>.";
        } else {
            compreso += "non comprende le spese di <span class=\"text-primary\">Condomino</span> e di <span class=\"text-primary\">Riscaldamento</span>.";
        }
        
        html += "<p class=\"text-muted\"> <span class=\"text-primary\">Prezzo: </span> " + stanza.Prezzo + " &euro;</p>";
        html += "<p class=\"text-muted\"> " + compreso + "</p>";
    }
    //html += "</div>";//center close
    html += "</div>";
    return html;
}

function create_carousel_stanza(stanza) {
    var html = "";
    html += "<div class=\"carousel slide qcar\" data-ride=\"carousel\" id=\"quote-carousel-" + stanza.OID + " \">";
    html += "<div class=\"carousel-inner\" align=\"center\">"; //5
    html += indicator(stanza);
    html += slide_Stanza(stanza);
    html += "</div>";
    html += "</div>";
    return html;
}

function slide_Stanza(stanza) {
    var html = "";
    var fotos = stanza.Foto;
    $.each(fotos, function (i, foto) {
        if (i == 0) {
            html += "<div class=\"item active\">"; //5.a
        } else {
            html += "<div class=\"item\">"; //5.b
        }
        html += "<blockquote>" +
                "<div class=\" carousel-item \">" +
                "<img class=\"img-responsive img-thumbnail\" src=\"." + foto + "\" style=\"width:"+dim_image_car+"px;height:"+dim_image_car+"px;\">" +
                "</div>" +
                "</blockquote>" +
                "</div>"; //5
    });
    return html;
}
function indicator(stanza) {
    var html = "<ol class=\"carousel-indicators\">";
    var fotos = stanza.Foto;

    $.each(fotos, function (i, foto) {
        var active = "";
        if (i == 0) {
            active = "class=\"active\"";
        } else {
            active = "";
        }
        html += "<li data-target=\"#quote-carousel-" + stanza.OID + "\" data-slide-to=\"" + i + "\" " + active + "></li>";
    });

    html += "</ol>";
    return html;
}
function info_annuncio(annuncio) {
    var html = "<div //class=\"center\">";
    if (annuncio.Atomico) {
        html += "<h1 class=\"text-muted\"> Annuncio Appartamento</h1>";
    } else {
        html += "<h1 class=\"text-muted\"> Annuncio Stanze</h1>";
    }
    html += "<p class=\"text-muted\">" + annuncio.Indirizzo + "</p>" +
            "<p class=\"text-muted\">" + annuncio.Descrizione + "</p>" +
            "<p class=\"text-muted\"><span class=\"text-primary\">Metratura Appartamento:</span> " + annuncio.Metratura + "</p>" +
            "<p class=\"text-muted\"><span class=\"text-primary\"> Data inizio: </span> " + annuncio.DataInizioAffitto + "</p>" +
            "<p class=\"text-muted\"> <span class=\"text-primary\">Quartiere: </span> " + annuncio.Quartiere + "</p>";
    if (annuncio.Atomico) {
        html += "<p class=\"text-muted\"> <span class=\"text-primary\">Prezzo: </span> " + annuncio.Prezzo + " &euro;</p>";
    }
    html += "</div>";
    html += " <div class=\"hr-line-dashed\"></div>";
    return html;
}