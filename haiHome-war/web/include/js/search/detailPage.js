/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var geocoder;
var map;
var geoAddress;
var supermarket = "images/supermarket2.ico";
var bank = "images/bank2.ico";
var bus = "images/bus2.ico";

var dim_image_car = 500;
var dim_image_prof = 100;

var markers_bus = [];
var markers_super = [];
var markers_bank = [];

var StrAnnuncio = $.session.get('dettagli');
var admin = $.session.get('admin');
console.log("admin? : " + admin);
var annuncio = jQuery.parseJSON(StrAnnuncio);
console.log("Annuncio corrente: " + JSON.stringify(annuncio));

var split = "\\";
var split2 = "/";
var foto_page = new Array();

var service_info = "#service_info";
var bus_info = "bus_info";

var id_studente = "";
var id_locatore = "";

$('.qcar').carousel({
    pause: true,
    interval: 4000
});

$(document).ready(function () {
    if ("" + admin == "true") {
        if (annuncio.Oscurato) {
            $("#visibileButton").show();
        } else {
            $("#oscuraButton").show();
        }
        if (annuncio.Locatore.bloccato == "true") {
            $("#sbloccaButton").show();
        } else {
            $("#bloccaButton").show();
        }
    } else {
        loggatoStudente();
        loggatoLocatore();
    }
});


//-----------------------------------------------------//
//____________________ GOOGLE MAPS____________________ //
function initialize(annuncio, opt) {
    geocoder = new google.maps.Geocoder();
    var latlng = new google.maps.LatLng(annuncio.Lat, annuncio.Lng);
    var mapOptions = {
        zoom: 8,
        center: latlng,
        mapTypeId: google.maps.MapTypeId.TERRAIN
    };
    map = new google.maps.Map(document.getElementById("map"), mapOptions);
    var marker = new google.maps.Marker({
        map: map,
        position: latlng,
        title: annuncio.Indirizzo
    });
    map.setZoom(16);

    $(service_info).html("");
    if (opt[0]) {
        addServices_superMarket(annuncio);
    }
    if (opt[1]) {
        addServices_Bank(annuncio);
    }
    if (opt[2]) {
        addServices_Bus(annuncio);
    }
}

function addServices_superMarket(annuncio) {
    $.ajax({
        url: "ServletController",
        type: 'post',
        dataType: 'json',
        data: {action: "Ricerca-addSuperMarket", annuncio: JSON.stringify(annuncio)},
        success: function (responseJson) {
            $.each(responseJson, function (index, item) {
                var lat = item.location.lat;
                var lng = item.location.lng;
                var label = item.name;
                var location = new google.maps.LatLng(lat, lng);
                var marker = addMarker(location, label, supermarket, index + "-super");
                markers_super.push(marker);
            });
        }
    });
}

function html_tail(html_old, new_tag) {
    return html_old + new_tag;
}

function addServices_Bus(annuncio) {
    $.ajax({
        url: "ServletController",
        type: 'post',
        dataType: 'json',
        data: {action: "Ricerca-addBus", annuncio: JSON.stringify(annuncio)},
        success: function (responseJson) {
            var html = html_tail("", "<div id=\"" + bus_info + "\">" + "\n");
            html = html_tail(html, "<h1 > Fermate vicine</h1>" + "\n"); //class=\"text-muted\"
            var fermate = "fermate_info";
            html = html_tail(html, "<div id=\"" + fermate + "\" style=\"overflow-y:scroll\">" + "\n");
            $.each(responseJson, function (index, item) {
                var lat = item.location.lat;
                var lng = item.location.lng;
                var label = item.name;
                var label_arr = label.split(" ");
                var head = label_arr[0];
                label = "";
                for (var x = 1; x < label_arr.length; x++) {
                    label = html_tail(label, " " + label_arr[x]);
                }
                html = html_tail(html, "<p class=\"text-muted fermata\" id=\"" + index + "-busM\"><span class=\"glyphicon glyphicon-map-marker\"></span> " + label + "</p>" + "\n");
                var location = new google.maps.LatLng(lat, lng);
                var marker = addMarker(location, label, bus, index + "-bus");
                markers_bus.push(marker);
            });
            html = html_tail(html, "</div>" + "\n");
            html = html_tail(html, "</div>" + "\n");
            $(service_info).append(html);
        }
    });
}

$(document).ready(function () {
    $(document).on('click', '.fermata', function (event) {
        var target = $(event.target);
        var id = target.attr("id");
        var id_arr = id.split("-");
        id = id_arr[0];
        google.maps.event.trigger(markers_bus[id], 'click');
        document.getElementById('map').scrollIntoView();
    });
});

function addServices_Bank(annuncio) {
    $.ajax({
        url: "ServletController",
        type: 'post',
        dataType: 'json',
        data: {action: "Ricerca-addBank", annuncio: JSON.stringify(annuncio)},
        success: function (responseJson) {
            $.each(responseJson, function (index, item) {
                var lat = item.location.lat;
                var lng = item.location.lng;
                var label = item.name;
                var location = new google.maps.LatLng(lat, lng);
                var marker = addMarker(location, label, bank, index + "-bank");
                markers_bank.push(marker);
            });
        }
    });
}

function addMarker(location, label, icon, id) {
//alert(label);
// Add the marker at the clicked location, and add the next-available label
// from the array of alphabetical characters.
    var marker = new google.maps.Marker({
        position: location,
        title: label,
        map: map,
        icon: icon
    });
    var infowindow = new google.maps.InfoWindow({
        content: "<p class=\"text-muted\" id=\"" + id + "\">" + label + "</p>"
    });
    marker.addListener('click', function () {
        infowindow.open(map, marker);
    });

    return marker;
}
$(document).ready(function () {
    $('.checkbox_service').click(function () {
        var bus = $("#bus").is(':checked');
        var banche = $("#banche").is(':checked');
        var superM = $("#super").is(':checked');

        if (superM) {
            setMapOnAll(map, markers_super);
        } else {
            setMapOnAll(null, markers_super);
        }

        if (bus) {
            setMapOnAll(map, markers_bus);
            $("#" + bus_info).show();
        } else {
            setMapOnAll(null, markers_bus);
            $("#" + bus_info).hide();
        }

        if (banche) {
            setMapOnAll(map, markers_bank);
        } else {
            setMapOnAll(null, markers_bank);
        }
    });
});

// Sets the map on all markers in the array.
function setMapOnAll(map, markers) {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(map);
    }
}

// Removes the markers from the map, but keeps them in the array.
function clearMarkers(markers) {
    setMapOnAll(null, markers);
}
//-----------------------------------------------------//
//____________________ GOOGLE MAPS____________________ //

//-----------------------------------------------------//
//___________________PAGINA DETTAGLIO__________________//


function create_Page(annuncio) {
    foto_page = new Array();
    var html = "";
    html += info_annuncio(annuncio);
    var open_ul = "<ul class=\"nav nav-tabs\">";
    html += open_ul;
    var stanze = annuncio.Stanze[0];

    $.each(stanze, function (index, stanza) {
        var classe_tab = "class=\"tab-stanza";
        if (index == 0) {
            classe_tab = "class=\"tab-stanza active";
        }
        if (stanza.SuperTipo == "StanzaInAffitto") {
            var archiviato = stanza.archiviato;
            var visibile = stanza.visibile;
            if (archiviato) {
                classe_tab += " archiviato\"";
            } else if (!visibile) {
                classe_tab += " out_search\"";
            } else {
                classe_tab += "\"";
            }
        } else {
            classe_tab += "\"";
        }
        html += "<li " + classe_tab + "><a data-toggle=\"tab\" href=\"#" + stanza.OID + "\">" + stanza.Tipo + "</a></li>"; //"-" + stanza.OID +
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
    foto_page = savePath(foto_page, annuncio);
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

    html += "<p class=\"text-muted\"> <span class=\"text-primary\">Metratura: </span> " + stanza.Metratura + " m<sup>2</sup></p>";
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
    var visibile = stanza.visibile;
    if (!visibile) {
        html += "<p class=\"text-muted\"> <span class=\"text-warning\">Questa camera non rispetta la tua ricerca ma è disponibile!!</span></p>";
    }

    var archiviato = stanza.archiviato;
    if (archiviato) {
        html += "<p class=\"text-muted\"> <span class=\"text-warning\">Questa camera risulta non disponibile!!</span></p>";
    }
    html += create_carousel_stanza(stanza);
    html += "<p class=\"text-muted\"> <span class=\"text-primary\">Metratura: </span> " + stanza.Metratura + " m<sup>2</sup></p>";
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

        html += "<p class=\"text-muted\"> <span class=\"text-primary\">Costo mensile: </span> " + stanza.Prezzo + " &euro;</p>";
        html += "<p class=\"text-muted\"> " + compreso + "</p>";
    }
    if (!atomico) {
        html += "<button id=\"ArchiviaButton-" + stanza.OID + "\" type=\"button\" class=\"btn btn-warning detailButton\" style=\"display:none\" onClick=\"archiviaStanza(" + stanza.OID + ")\">Archivia</button>";
        html += "<button id=\"PubblicaButton-" + stanza.OID + "\" type=\"button\" class=\"btn btn-success detailButton\" style=\"display:none\" onClick=\"pubblicaStanza(" + stanza.OID + ")\">Pubblica</button>";
    }
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
        var id_foto_arr = foto.split(split);
        if (id_foto_arr.length == 1) {
            var id_foto_arr = foto.split(split2);
        }
        var id_foto_ext = id_foto_arr[id_foto_arr.length - 1];
        var id_foto_ext_arr = id_foto_ext.split(".");
        var id_foto = id_foto_ext_arr[0];
        var type = id_foto_ext_arr[1];
        html += "<blockquote>";
        html += "<div id=\"" + stanza.OID + "-" + id_foto + "\" class=\" carousel-item \"></div>";
        //"<img class=\"img-responsive img-thumbnail\" src=\"" + foto + "\" style=\"width:" + dim_image_car + "px;height:" + dim_image_car + "px;\">" +
        html += "</blockquote>" +
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
    var html = "<div id=\"info_annuncio\">";
    if (annuncio.Atomico) {
        html += "<h1> Annuncio Appartamento</h1>";
    } else {
        html += "<h1> Annuncio Stanze</h1>";
    }
    html += "<p class=\"text-muted\">" + annuncio.Indirizzo + "</p>" +
            "<p class=\"text-muted\">" + annuncio.Descrizione + "</p>" +
            "<p class=\"text-muted\"><span class=\"text-primary\">Metratura Appartamento:</span> " + annuncio.Metratura + " m<sup>2</sup></p>" +
            "<p class=\"text-muted\"><span class=\"text-primary\"> Data inizio affitto: </span> " + annuncio.DataInizioAffitto + "</p>" +
            "<p class=\"text-muted\"><span class=\"text-primary\"> Data pubblicazione: </span> " + annuncio.DataPubblicazione + "</p>" +
            "<p class=\"text-muted\"> <span class=\"text-primary\">Quartiere: </span> " + annuncio.Quartiere + "</p>";
    if (annuncio.Arredato) {
        html += "<p class=\"text-muted\"> <span class=\"text-primary\">Arredato </span></p>";
    } else {
        html += "<p class=\"text-muted\"> <span class=\"text-primary\">Non Arredato </span></p>";
    }
    if (annuncio.Atomico) {
        var cmp = "il prezzo ";
        var cmpC = annuncio.CompresoCondominio;
        var cmpR = annuncio.CompresoRiscaldamento;

        if (cmpC && cmpR) {
            cmp = "comprende le spese di <span class=\"text-primary\">Condominio</span> e di  <span class=\"text-primary\">Riscaldamento</span>.";
        }

        if (!cmpC && cmpR) {
            cmp = "comprende le spese di <span class=\"text-primary\">Riscaldamento</span> ma non quelle di <span class=\"text-primary\">Condomino</span>.";
        }

        if (cmpC && !cmpR) {
            cmp = "comprende le spese di <span class=\"text-primary\">Condomino</span> ma non quelle di <span class=\"text-primary\">Riscaldamento</span>.";
        }

        if (!cmpC && !cmpR) {
            cmp = "non comprende le spese di <span class=\"text-primary\">Condomino</span> e di <span class=\"text-primary\">Riscaldamento</span>.";
        }

        html += "<p class=\"text-muted\"> <span class=\"text-primary\">Costo mensile: </span> " + annuncio.Prezzo + " &euro; " + cmp + "</p>";
    }
    html += "<button id=\"saveButton\" type=\"button\" class=\"btn btn-success detailButton\" onClick=\"salvaAnnuncioPreferiti()\" style=\"display:none\">Salva nei Preferiti</button>";
    html += "<button id=\"deleteButton\" type=\"button\" class=\"btn btn-danger detailButton\" onClick=\"cancellaAnnuncioPreferiti()\" style=\"display:none\">Elimina dai Preferiti</button>";
    html += "<button id=\"oscuraButton\" type=\"button\" class=\"btn btn-danger detailButton\" onClick=\"oscuraAnnuncio()\" style=\"display:none\">Oscura Annuncio</button>";
    html += "<button id=\"visibileButton\" type=\"button\" class=\"btn btn-success detailButton\" onClick=\"visibileAnnuncio()\" style=\"display:none\">Rendi Visibile Annuncio</button>";
    html += "<button id=\"ArchiviaButton\" type=\"button\" class=\"btn btn-warning detailButton\" style=\"display:none\" onClick=\"archiviaAnnuncio()\">Archivia Annuncio</button>";
    html += "<button id=\"PubblicaButton\" type=\"button\" class=\"btn btn-success detailButton\" style=\"display:none\" onClick=\"pubblicaAnnuncio()\">Pubblica Annuncio</button>";
    html += "</div>";
    html += " <div class=\"hr-line-dashed\"></div>";
    return html;
}

function init_info(annuncio) {
    var locatore = annuncio.Locatore;
    console.log("Propretario annuncio: " + JSON.stringify(locatore));
    return info_loc(locatore);
}

function info_loc(locatore) {
    var html = "";
    html += "<div id=\"info_locatore\" class=\"center blockLoc\">";
    html += "<h1>Locatore</h1>";
    html += "<div class=\"row center\">";
    html += "<img src='" + locatore.fotoProfilo + "'class='img-responsive img-circle' alt=''style=\"width:" + dim_image_prof + "px;height:" + dim_image_prof + "px;\" \>";
    html += "</div>";
    html += "<p class=\"text-muted\"><span class=\"text-primary\">Nome: </span>" + locatore.nome + "</p>";
    html += "<p class=\"text-muted\"><span class=\"text-primary\">Cognome: </span>" + locatore.cognome + "</p>";
    html += "<p class=\"text-muted\"><span class=\"text-primary\">Descrizione: </span>" + locatore.descrizione + "</p>";
    html += "<p class=\"text-muted\"><span class=\"text-primary\">Telefono: </span>" + locatore.telefono + "</p>";
    html += "<p class=\"text-muted\"><span class=\"text-primary\">Email: </span><span id=\"mail\">" + locatore.email + "</span></p>";
    html += "<button id=\"segnalaButton\" type=\"button\" class=\"btn btn-warning detailButton\" style=\"display:none\" onClick=\"segnalaAnnuncio()\">Segnala Annuncio</button>";
    html += "<button id=\"bloccaButton\" type=\"button\" class=\"btn btn-danger detailButton\" style=\"display:none\" onClick=\"bloccaLocatore()\">Blocca Locatore</button>";
    html += "<button id=\"sbloccaButton\" type=\"button\" class=\"btn btn-success detailButton\" style=\"display:none\" onClick=\"sbloccaLocatore()\">Sblocca Locatore</button>";
    html += "</div>";
    return html;
}

//parte statica messa qui solo per comodità
function service() {
    var html = "";
    html += "<div class=\"center blockService\" id=\"MapOptionDiv\">";
    html += "<h1>Servizi</h1>";
    html += "<div class=\"checkbox services\">";
    html += "<label><input id=\"super\" class=\"checkbox_service\" type=\"checkbox\" checked>SuperMercati</label>";
    html += "</div>";
    html += "<div class=\"checkbox services\">";
    html += "<label><input id=\"banche\" class=\"checkbox_service\" type=\"checkbox\" checked>Banche</label>";
    html += "</div>";
    html += "<div class=\"checkbox services\">";
    html += "<label><input id=\"bus\" class=\"checkbox_service\" type=\"checkbox\" checked>Fermate bus</label>";
    html += "</div>";
    html += "</div>";
    return html;
}

function callFoto(foto_OID) {
    var foto_arr = foto_OID.split("$");
    var foto = foto_arr[1];
    var OID = foto_arr[0];
    var id_foto_arr = foto.split(split);
    if (id_foto_arr.length == 1) {
        var id_foto_arr = foto.split(split2);
    }
    var id_foto_ext = id_foto_arr[id_foto_arr.length - 1];
    var id_foto_ext_arr = id_foto_ext.split(".");
    var id_foto = id_foto_ext_arr[0];
    var type = id_foto_ext_arr[1];
    $.ajax({
        url: "ServletController",
        type: 'get',
        dataType: 'text',
        data: {action: "Ricerca-getImage", url: foto, type: type},
        success: function (base64Image) {
            var f = "<img class=\"img-responsive img-thumbnail\" src=\"data:image/" + type + ";base64, " + base64Image + "\" style=\"width:" + dim_image_car + "px;height:" + dim_image_car + "px;\">";
            $("#" + OID + "-" + id_foto + "").append(f);
        }
    });
}

function loadAllfoto() {
    var fotoPage = foto_page;
    for (var i = 0; i < fotoPage.length; i++) {
        var foto = fotoPage[i];
        callFoto(foto);
    }
}

function savePath(list, annuncio) {
    var stanze = annuncio.Stanze[0];
    $.each(stanze, function (index, stanza) {
        var fotos = stanza.Foto;
        $.each(fotos, function (i, foto) {
            list.push(stanza.OID + "$" + foto);
        });
    });
    return list;
}



//-----------------------------------------------------//
//____________________ PAGINA DETTAGLIO_______________ //



//-----------------------------------------------------//
//____________________ STUDENTE_______________________ //




function loggatoStudente() {
    $.post("ServletController",
            {action: "Ricerca-loggatoStudente"},
            function (item) {
                if (item != "") {
                    console.log("Studente loggato? id: " + item);
                    id_studente = item;
                    checkAnnuncio("" + annuncio.OID);
                    $("#segnalaButton").show("fast");
                } else {
                    console.log("Studente loggato? id: NO");
                }
            });
}

function checkAnnuncio(id) {
    $.post("ServletController",
            {action: "Ricerca-checkAnnuncio", id: id},
            function (item) {
                console.log("Annuncio Salvato?: " + item);
                if (item == "true") {
                    $("#deleteButton").show("fast");

                } else {
                    $("#saveButton").show("fast");

                }
            });
}

function salvaAnnuncioPreferiti() {
    $.post("ServletController",
            {action: "studente-addAnnuncio", id: annuncio.OID},
            function (item) {
                console.log("Annuncio Salvato?: " + item);
                if (item == "true") {
                    var title = "Ok...";
                    var body = "Annuncio salvato nei preferiti!!";
                    var footer = null;
                    openModalMessage(title, body, footer);
                    $("#saveButton").hide();
                    $("#deleteButton").show();
                } else {
                    var title = "Ops...";
                    var body = "C'è stato un errore nel salvataggio nei preferiti, contatti l'admin per maggiori informazini.";
                    ;
                    var footer = null;
                    openModalMessage(title, body, footer);
                }
            });
}

function cancellaAnnuncioPreferiti() {
    $.post("ServletController",
            {action: "studente-removeAnnuncio", id: annuncio.OID},
            function (item) {
                console.log("Annuncio eliminato?: " + item);
                if (item == "true") {
                    var title = "Ok...";
                    var body = "Annuncio eliminato dai preferiti!!";
                    var footer = null;
                    openModalMessage(title, body, footer);
                    $("#deleteButton").hide();
                    $("#saveButton").show();
                } else {
                    var title = "Ops...";
                    var body = "C'è stato un errore nella eliminazione dai preferiti, contatti l'admin per maggiori informazini.";
                    ;
                    var footer = null;
                    openModalMessage(title, body, footer);
                }
            });
}

//-----------------------------------------------------//
//____________________ STUDENTE_______________________ //


//-----------------------------------------------------//
//____________________ LOCATORE_______________________ //


function loggatoLocatore() {
    $.post("ServletController",
            {action: "Ricerca-loggatoLocatore"},
            function (item) {
                if (item != "") {
                    console.log("Locatore loggato? id: " + item);
                    id_locatore = item;
                    makeVisibleOption();
                } else {
                    console.log("Locatore loggato? id: NO");
                }
            });
}

function makeVisibleOption() {
    if (id_locatore == annuncio.Locatore.id) {
        if (annuncio.Archiviato) {
            $("#PubblicaButton").show("fast");
        } else {
            $("#ArchiviaButton").show("fast");
        }
        var stanze = annuncio.Stanze[0];

        $.each(stanze, function (index, stanza) {
            if (stanza.archiviato) {
                $("#PubblicaButton-" + stanza.OID).show("fast");
            } else {
                $("#ArchiviaButton-" + stanza.OID).show("fast");
            }
        });
    }
}
function archiviaStanza(OID) {
    $.post("ServletController",
            {
                action: "locatore-archivia-stanza",
                oid: annuncio.OID,
                oidStanza: OID
            },
            function (response) {
                if (response == "ok") {
                    var title = "Ok...";
                    var body = "Stanza archiviata!!";
                    var footer = null;
                    openModalMessage(title, body, footer);
                    $("#ArchiviaButton-" + OID).hide();
                    $("#PubblicaButton-" + OID).show("fast");
                } else {
                    var title = "Ops...";
                    var body = "C'è stato un errore nel archiviare la stanza, contatti l'admin per maggiori informazini.";
                    var footer = null;
                    openModalMessage(title, body, footer);
                }
            }
    );
}

function pubblicaStanza(OID) {
    $.post("ServletController",
            {
                action: "locatore-pubblica-stanza",
                oid: annuncio.OID,
                oidStanza: OID
            },
            function (response) {
                if (response == "ok") {
                    var title = "Ok...";
                    var body = "Stanza pubblicata con successo!!";
                    var footer = null;
                    openModalMessage(title, body, footer);
                    $("#ArchiviaButton-" + OID).show("fast");
                    $("#PubblicaButton-" + OID).hide();
                } else {
                    var title = "Ops...";
                    var body = "C'è stato un errore nel pubblicare la stanza, contatti l'admin per maggiori informazini.";
                    var footer = null;
                    openModalMessage(title, body, footer);
                }
            });
}

function archiviaAnnuncio() {
    $.post("ServletController",
            {
                action: "locatore-archivia-annuncio",
                oid: annuncio.OID
            },
            function (response) {
                if (response == "ok") {
                    var title = "Ok...";
                    var body = "Annuncio archiviato!!";
                    var footer = null;
                    openModalMessage(title, body, footer);
                    $("#ArchiviaButton").hide();
                    $("#PubblicaButton").show("fast");
                } else {
                    var title = "Ops...";
                    var body = "C'è stato un errore nel archiviare l'annuncio, contatti l'admin per maggiori informazini.";
                    var footer = null;
                    openModalMessage(title, body, footer);
                }
            }
    );

}

function pubblicaAnnuncio() {
    $.post("ServletController",
            {
                action: "locatore-pubblica-annuncio",
                oid: annuncio.OID
            },
            function (response) {
                if (response == "ok") {
                    var title = "Ok...";
                    var body = "Annuncio pubblicato con successo!!";
                    var footer = null;
                    openModalMessage(title, body, footer);
                    $("#PubblicaButton").hide();
                    $("#ArchiviaButton").show("fast");
                } else {
                    var title = "Ops...";
                    var body = "C'è stato un errore nel pubblicare l'annuncio, contatti l'admin per maggiori informazini.";
                    var footer = null;
                    openModalMessage(title, body, footer);
                }
            });
}

//-----------------------------------------------------//
//____________________ LOCATORE_______________________ //



//--------------------------------------------------------------//
//--------------------ADMIN------------------------------------//


function oscuraAnnuncio() {
    $.post("ServletController",
            {action: "Annunci-oscuraAnnuncio", oidAnnuncio: annuncio.OID, oscuratoValue: true},
            function (item) {
                console.log("Annuncio oscurato?: " + item);
                if (item.response == true) {
                    var title = "Ok...";
                    var body = "Annuncio oscurato!!";
                    var footer = null;
                    openModalMessage(title, body, footer);
                    $("#oscuraButton").hide();
                    $("#visibileButton").show();
                } else {
                    var title = "Ops...";
                    var body = "Errore nel oscuramento del annuncio!!";
                    var footer = null;
                    openModalMessage(title, body, footer);
                }
            });

}

function visibileAnnuncio() {
    $.post("ServletController",
            {action: "Annunci-oscuraAnnuncio", oidAnnuncio: annuncio.OID, oscuratoValue: "false"},
            function (item) {
                console.log(item);
                if (item.response == true) {
                    var title = "Ok...";
                    var body = "Annuncio visibile!!";
                    var footer = null;
                    openModalMessage(title, body, footer);
                    $("#visibileButton").hide();
                    $("#oscuraButton").show();
                } else {
                    var title = "Ops...";
                    var body = "Errore nel rendere visibile l'annuncio!!";
                    var footer = null;
                    openModalMessage(title, body, footer);
                }
            });
}

function bloccaLocatore() {
    $.post("ServletController",
            {action: "locatore-blocca-by-id", oid: annuncio.Locatore.id, bloccatoflag: true},
            function (item) {
                console.log("Locatore bloccato?: " + item);
                if (item == "true") {
                    var title = "Ok...";
                    var body = "Locatore bloccato!!";
                    var footer = null;
                    openModalMessage(title, body, footer);
                    $("#bloccaButton").hide();
                    $("#sbloccaButton").show();
                } else {
                    var title = "Ops...";
                    var body = "Errore nel bloccare il Locatore!!";
                    var footer = null;
                    openModalMessage(title, body, footer);
                }
            });
}

function sbloccaLocatore() {
    $.post("ServletController",
            {action: "locatore-blocca-by-id", oid: annuncio.Locatore.id, bloccatoflag: false},
            function (item) {
                console.log("Locatore sbloccato?: " + item);
                if (item == "true") {
                    var title = "Ok...";
                    var body = "Locatore sbloccato!!";
                    var footer = null;
                    openModalMessage(title, body, footer);
                    $("#sbloccaButton").hide();
                    $("#bloccaButton").show();
                } else {
                    var title = "Ops...";
                    var body = "Errore nello sbloccare il Locatore!!";
                    var footer = null;
                    openModalMessage(title, body, footer);
                }
            });
}

//--------------------------------------------------------------//
//--------------------ADMIN------------------------------------//




//-------------------------------------------------------------//
//---------------------SEGNALAZIONE----------------------------//



function request_sendSegn() {
    var descrizione = $("#des_segn").val();
    if (descrizione != null) {
        $.post("ServletController",
                {action: "Segnalazione-addSegnalazione", id_annuncio: annuncio.OID, id_studente: id_studente, descrizione: descrizione},
                function (item) {
                    console.log("Annuncio segnalato?: " + item);
                    console.log(clearModalMessage());
                    if (item == "true") {
                        var title = "Ok...";
                        var body = "Annuncio segnalato, grazie!!";
                        var footer = null;
                        openModalMessage(title, body, footer);
                    } else {
                        var title = "Ops...";
                        var body = "Errore nel invio della segnalazione, contatti l'admin per maggiori informazioni.";
                        var footer = null;
                        openModalMessage(title, body, footer);
                    }
                });
    }
}
function segnalaAnnuncio() {
    var title = "Ok...";
    var body = "<div class=\"form-group\">" +
            "<label for=\"des_segn\">Inserisci qui la tua segnalazione: </label>" +
            "<input type=\"text\" class=\"form-control ten-margin\" id=\"des_segn\">" +
            "</div>";
    var footer = "<button type=\"button\" class=\"btn btn-default\" onClick=\"request_sendSegn()\">Invia</button>" +
            "<button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">Annulla</button>";
    openModalMessage(title, body, footer);
}



//-------------------------------------------------------------//
//---------------------SEGNALAZIONE----------------------------//


