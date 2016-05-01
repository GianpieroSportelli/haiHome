/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var geocoder;
var map;
var geoAddress;
var supermarket = "images/basket.png";
var bank = "images/bank.png";
var bus = "images/transport.png";
var dim_image_car = 500;
var dim_image_prof = 100;

var markers_bus = [];
var markers_super=[];
var markers_bank=[];

var StrAnnuncio = $.session.get('dettagli');
var annuncio = jQuery.parseJSON(StrAnnuncio);
console.log("Annuncio corrente: "+JSON.stringify(annuncio));

var split = "\\";
var split2 = "/";
var foto_page = new Array();

var service_info = "#service_info";
 var bus_info = "bus_info";

$('.qcar').carousel({
    pause: true,
    interval: 4000
});

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
        title: 'geocode request address'
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
            //console.log(responseJson);
            $.each(responseJson, function (index, item) {
                //console.log(item);
                var lat = item.location.lat;
                var lng = item.location.lng;
                var label = item.name;
                var location = new google.maps.LatLng(lat, lng);
                var marker=addMarker(location, label, supermarket, index + "-super");
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
            //console.log("risposta:" + responseJson);
            //console.log(html);
            html = html_tail(html, "<h1 > Fermate vicine</h1>" + "\n"); //class=\"text-muted\"
            var fermate = "fermate_info";
            html = html_tail(html, "<div id=\"" + fermate + "\" style=\"overflow-y:scroll\">" + "\n");
            //$(fermate).css("overflow-y", "scroll");
            $.each(responseJson, function (index, item) {
                //console.log(item);
                var lat = item.location.lat;
                var lng = item.location.lng;
                var label = item.name;
                var label_arr = label.split(" ");
                var head = label_arr[0];
                label = "";
                for (var x = 1; x < label_arr.length; x++) {
                    label = html_tail(label, " " + label_arr[x]);
                }
                html = html_tail(html, "<p class=\"text-muted fermata\" id=\""+index+"-busM\"><span class=\"glyphicon glyphicon-map-marker\"></span> " + label + "</p>" + "\n");
                var location = new google.maps.LatLng(lat, lng);
                var marker=addMarker(location, label, bus, index + "-bus");
                markers_bus.push(marker);
            });
            html = html_tail(html, "</div>" + "\n");
            html = html_tail(html, "</div>" + "\n");
            //console.log(html);
            $(service_info).append(html);
        }
    });
}
 $(document).ready(function () {
        $(document).on('click', '.fermata', function () {
            var target = $( event.target );
            var id=target.attr("id");
            var id_arr=id.split("-");
            id=id_arr[0];
            //console.log("fermata: "+id);
            google.maps.event.trigger(markers_bus[id], 'click');
            document.getElementById('map').scrollIntoView();
            //$("html, body").animate({ scrollTop: $("#map").offset().top }, "slow");
            
        });
    });

function addServices_Bank(annuncio) {
    $.ajax({
        url: "ServletController",
        type: 'post',
        dataType: 'json',
        data: {action: "Ricerca-addBank", annuncio: JSON.stringify(annuncio)},
        success: function (responseJson) {
            //console.log(responseJson);
            $.each(responseJson, function (index, item) {
                //console.log(item);
                var lat = item.location.lat;
                var lng = item.location.lng;
                var label = item.name;
                var location = new google.maps.LatLng(lat, lng);
                var marker=addMarker(location, label, bank, index + "-bank");
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

function create_Page(annuncio) {
    foto_page = new Array();
    var html = "";
    html += info_annuncio(annuncio);
    /*html+="<div class=\"hr-line-dashed\"></div>";
     html+="<div id=\"map\" class=\"\" >";   
     html+="</div>";
     html+="<div class=\"hr-line-dashed\"></div>";*/
    var open_ul = "<ul class=\"nav nav-tabs\">";
    html += open_ul;
    var stanze = annuncio.Stanze[0];

    $.each(stanze, function (index, stanza) {
        //console.log(stanza);
        var classe_tab = "class=\"tab-stanza";
        if (index == 0) {
            classe_tab = "class=\"tab-stanza active";
        }
        if (stanza.SuperTipo == "StanzaInAffitto") {
            //console.log(stanza.archiviato);
            var archiviato = stanza.archiviato;
            var visibile = stanza.visibile;
            //console.log(visibile);
            //console.log(archiviato);
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
    var visibile = stanza.visibile;
    if (!visibile) {
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
            "<p class=\"text-muted\"><span class=\"text-primary\">Metratura Appartamento:</span> " + annuncio.Metratura + "</p>" +
            "<p class=\"text-muted\"><span class=\"text-primary\"> Data inizio: </span> " + annuncio.DataInizioAffitto + "</p>" +
            "<p class=\"text-muted\"> <span class=\"text-primary\">Quartiere: </span> " + annuncio.Quartiere + "</p>";
    if (annuncio.Atomico) {
        html += "<p class=\"text-muted\"> <span class=\"text-primary\">Prezzo: </span> " + annuncio.Prezzo + " &euro;</p>";
    }
    html += "<button id=\"saveButton\" type=\"button\" class=\"btn btn-success\" onClick=\"salvaAnnuncioPreferiti()\" style=\"display:none\">Salva</button>";
    html += "</div>";
    html += " <div class=\"hr-line-dashed\"></div>";
    return html;
}

function init_info(annuncio) {
    var locatore = annuncio.Locatore;
    console.log("Propretario annuncio: "+JSON.stringify(locatore));
    return info_loc(locatore);
}

function info_loc(locatore) {
    var html = "";
    html += "<div id=\"info_locatore\" class=\"center blockLoc\">";
    html += "<h1>Locatore</h1>";
    html += "<div class=\"row center\">";
    html += "<img src='" + locatore.fotoProfilo + "'class='img-responsive img-circle' alt=''style=\"width:" + dim_image_prof + "px;height:" + dim_image_prof + "px;\" \>";
    html += "</div>";
    //html+="<p>"+JSON.stringify(locatore)+"</p>";
    html += "<p class=\"text-muted\"><span class=\"text-primary\">Nome: </span>" + locatore.nome + "</p>";
    html += "<p class=\"text-muted\"><span class=\"text-primary\">Cognome: </span>" + locatore.cognome + "</p>";
    html += "<p class=\"text-muted\"><span class=\"text-primary\">Descrizione: </span>" + locatore.descrizione + "</p>";
    html += "<p class=\"text-muted\"><span class=\"text-primary\">email: </span><span id=\"mail\">" + locatore.email + "</span></p>";
    html += "<button id=\"segnalaBtn\" type=\"button\" class=\"btn btn-warning\" style=\"display:none\" onClick=\"segnalaAnnuncio()\">Seganala</button>";
    html += "</div>";
    return html;
}

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
    //html += "<button id=\"segnalaBtn\" type=\"button\" class=\"btn btn-warning\" onClick=\"getServices()\">Cancella marker</button>";
    html += "</div>";
    return html;
}

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
                    $("#segnalaBtn").show("fast");
                }
            });
}

function salvaAnnuncioPreferiti() {
    alert("Salvo l'annuncio nei Preferiti");
    $.post("ServletController",
            {action: "studente-addAnnuncio",id:annuncio.OID},
            function (item) {
                //var html = '';
                //alert(item);
                console.log("Annuncio Salvato?: " + item);
                if (item == "true") {
                    $("#saveButton").hide();
                }
            });
}

function segnalaAnnuncio() {
    alert("annuncio Segnalato");
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
    //console.log("in load: " + id_foto + " ext: " + type);
    //console.log("in load " + id_foto);
    $.ajax({
        url: "ServletController",
        type: 'get',
        dataType: 'text',
        //contentType: "image/jpg",
        data: {action: "Ricerca-getImage", url: foto, type: type},
        success: function (base64Image) {
            //(foto + ": " + base64Image);
            var f = "<img class=\"img-responsive img-thumbnail\" src=\"data:image/" + type + ";base64, " + base64Image + "\" style=\"width:" + dim_image_car + "px;height:" + dim_image_car + "px;\">";
            $("#" + OID + "-" + id_foto + "").append(f);
        }
    });
}

function loadAllfoto() {
    //console.log(page);
    var fotoPage = foto_page;
    //console.log("foto pagina: " + fotoPage);
    for (var i = 0; i < fotoPage.length; i++) {
        var foto = fotoPage[i];
        callFoto(foto);
    }
    //activateCaroselli();
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

/*$(window).scroll(function(){    
 $("#MapOptionDiv").stop().animate({"marginTop": ($(window).scrollTop()) + "px", "marginLeft":($(window).scrollLeft()) + "px"}, "fast" );
 });*/
$(document).ready(function () {
    $('.checkbox_service').click(function () {
        console.log("IN check");
        /*if (!$(this).is(':checked')) {
         return confirm("Are you sure?");
         }*/
        var bus = $("#bus").is(':checked');
        var banche = $("#banche").is(':checked');
        var superM = $("#super").is(':checked');

        console.log("super: " + superM + " banche: " + banche + " bus: " + bus);
        var opt = [superM, banche, bus];
        
        if(superM){
            setMapOnAll(map,markers_super);
        }else{
           setMapOnAll(null,markers_super); 
        }
        
        if(bus){
            setMapOnAll(map,markers_bus);
            $("#"+bus_info).show();
        }else{
           setMapOnAll(null,markers_bus);
            $("#"+bus_info).hide(); 
        }
        
        if(banche){
            setMapOnAll(map,markers_bank);
        }else{
            setMapOnAll(null,markers_bank);
        }
    });
});

// Sets the map on all markers in the array.
function setMapOnAll(map,markers) {
  for (var i = 0; i < markers.length; i++) {
    markers[i].setMap(map);
  }
}

// Removes the markers from the map, but keeps them in the array.
function clearMarkers(markers) {
  setMapOnAll(null,markers);
}