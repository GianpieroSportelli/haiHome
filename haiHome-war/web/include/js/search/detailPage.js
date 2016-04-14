/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$('#quote-carousel').carousel({
    pause: true,
    interval: 4000
});

function getCodeCarouse(annuncio) {
    var atomico = annuncio.Atomico;
    if (atomico == true) {
        return getCodeCarouser_Atomico(annuncio);
    } else {
        return getCodeCarouser_Stanza(annuncio);
    }

}

function getCodeCarouser_Atomico(annuncio) {
    var title = annuncio.Indirizzo;
    var data_i = annuncio.DataInizioAffitto;
    var data_p = annuncio.DataPubblicazione;
    var prezzo = annuncio.Prezzo;
    var quartiere = annuncio.Quartiere;
    var n_loc = annuncio.NumeroLocali;
    var loc = annuncio.Locatore.nome;
    var desc = annuncio.Descrizione;
    var met = annuncio.Metratura;
    var html = "<div id=\"annuncio\">" +
            "<div class=\"panel panel-default\">" +
            "<div class='panel-heading'>" +
            "<div class=\"center\">" +
            "<p class=\"text-muted\"> Annuncio Appartamento</p>" +
            +"</div>" +
            "<div class=\"carousel slide qcar\" data-ride=\"carousel\" id=\"quote-carousel \">";
    var stanze = annuncio.Stanze[0];
    html += "<div class=\"carousel-inner\" align=\"center\">";
    $.each(stanze, function (index, stanza) {
        var fotos = stanza.Foto;
        $.each(fotos, function (i, foto) {
            if (index == 0 && i == 0) {
                html += "<div class=\"item active\">";
            } else {
                html += "<div class=\"item\">";
            }
            html += "<blockquote>" +
                    "<img class=\"img-responsive img-thumbnail\" src=\"." + foto + "\" style=\"width: 500px;height:500px;\">" +
                    "</blockquote>" +
                    "</div>";
        });
    });
    html += "</div>" +
            "</div>" +
            "</div>";
    html += "<div class=\"panel-body snip\">" +
            "<p class=\"text-muted\">" + title + "</p>" +
            "<p class=\"text-muted\">Data inizio: " + data_i + "</p>" +
            "<p class=\"text-muted\">Data pubblicazione: " + data_p + "</p>" +
            "<p class=\"text-muted\"> Prezzo: " + prezzo + "</p>" +
            "<p class=\"text-muted\"> <span class=\"text-primary\">Quartiere: </span> " + quartiere + "</p>" +
            "<p class=\"text-muted\">Numero locali: " + n_loc + "</p>" +
            "<p class=\"text-muted\"> <span class=\"text-primary\">Locatore: </span> " + loc + "</p>" +
            "<p class=\"text-muted\">" + desc + "</p>" +
            "<p class=\"text-muted\">Metratura: " + met + "</p>" +
            "</div>" + //panel-body
            "</div>" +
            "</div>";
    return html;
}

function getCodeCarouser_Stanza(annuncio) {
    var html =
            "<div id=\"annuncio\">" + //1
            "<div class=\"panel panel-default\">" + //2
            "<div class='panel-heading'>" + //3
            "<div class=\"center\">" +
            "<p class=\"text-muted\"> Annuncio per Stanze</p>" +
            "<p class=\"text-muted\">" + annuncio.Indirizzo + "</p>" +
            "<p class=\"text-muted\">" + annuncio.Descrizione + "</p>" +
            "<p class=\"text-muted\">Metratura Appartamento: " + annuncio.Metratura + "</p>" +
            "<p class=\"text-muted\">Data inizio: " + annuncio.DataInizioAffitto + "</p>" +
            "<p class=\"text-muted\"> <span class=\"text-primary\">Quartiere: </span> " + annuncio.Quartiere + "</p>" +
            "<p class=\"text-muted\"> <span class=\"text-primary\">Locatore: </span> " + annuncio.Locatore.nome + "</p>" +
            "</div>" +
            "<div class=\"carousel slide qcar\" data-ride=\"carousel\" id=\"quote-carousel \">" + //4
            "";
    var stanze = annuncio.Stanze[0];
    html += "<div class=\"carousel-inner\" align=\"center\">"; //5

    $.each(stanze, function (index, stanza) {
        var superT = stanza.SuperTipo;
        if (superT == "StanzaInAffitto") {
            var archiviato = stanza.achiviato;
            //console.log(archiviato);
            if (!archiviato) {
                html += slide_Affitto(stanza, index);
            }
        } else {
            html += slide_Accessoria(stanza, index);
        }
    });
    html += "</div>" + //5
            "</div>" + //4
            "</div>" + //3
            "</div>" + //2
            "</div>"; //1
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
                "<img class=\"img-responsive img-thumbnail\" src=\"." + foto + "\" style=\"width: 500px;height:500px;\">" +
                "</div>" +
                "<div class=\"panel-body snip\">" + //6
                "<p class=\"text-muted\"> <span class=\"text-primary\">Tipo Stanza: </span> " + stanza.Tipo + "</p>" +
                "</div>" + //panel-body*/ //6
                "</blockquote>" +
                "</div>"; //5
    });
    return html;
}

function slide_Affitto(stanza, index) {
    var html = "";
    var fotos = stanza.Foto;
    $.each(fotos, function (i, foto) {
        if (index == 0 && i == 0) {
            html += "<div class=\"item active\">";
        } else {
            html += "<div class=\"item\">";
        }
        html += "<blockquote>" +
                "<img class=\"img-responsive img-thumbnail\" src=\"." + foto + "\" style=\"width:500px;height:500px;\">" +
                "<div class=\"panel-body snip\">" +
                "<p class=\"text-muted\"> <span class=\"text-primary\">Tipo Stanza: </span> " + stanza.Tipo + "</p>" +
                "<p class=\"text-muted\"> <span class=\"text-primary\">Prezzo: </span> " + stanza.Prezzo + "</p>" +
                "</div>" + //panel-body*/
                "</blockquote>" +
                "</div>";
    });
    return html;
}

var geocoder;
var map;
var geoAddress;
var icon = "images/basket.png";

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

        html += "<p class=\"text-muted\"> <span class=\"text-primary\">Prezzo: </span> " + stanza.Prezzo + " €</p>";
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
                "<img class=\"img-responsive img-thumbnail\" src=\"." + foto + "\" style=\"width: 500px;height:500px;\">" +
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
        html += "<p class=\"text-muted\"> <span class=\"text-primary\">Prezzo: </span> " + annuncio.Prezzo + "</p>";
    }
    html += "</div>";
    html += " <div class=\"hr-line-dashed\"></div>";
    return html;
}
/*<ul class="nav nav-tabs">
 <li class="active"><a data-toggle="tab" href="#home">Home</a></li>
 <li><a data-toggle="tab" href="#menu1">Menu 1</a></li>
 <li><a data-toggle="tab" href="#menu2">Menu 2</a></li>
 </ul>
 
 <div class="tab-content">
 <div id="home" class="tab-pane fade in active">
 <h3>HOME</h3>
 <p>Some content.</p>
 </div>
 <div id="menu1" class="tab-pane fade">
 <h3>Menu 1</h3>
 <p>Some content in menu 1.</p>
 </div>
 <div id="menu2" class="tab-pane fade">
 <h3>Menu 2</h3>
 <p>Some content in menu 2.</p>
 </div>
 </div>*/
