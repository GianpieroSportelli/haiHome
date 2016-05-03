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
var markers = [];

var split = "\\";
var split2="//";
var foto_page = new Array();

$('.qcar').carousel({
    pause: true,
    interval: 4000
});



function initialize(annuncio) {
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
    
}

function create_Page(annuncio) {
    foto_page = new Array();
    var html = "";
    html += info_annuncio(annuncio);

    //apre nav bar
    var open_ul = "<ul class=\"nav nav-tabs\">";
    html += open_ul;
    var stanze = annuncio.Stanze[0];


    $.each(stanze, function (index, stanza) {
        //console.log(stanza);

    
        var active = "";
        stanza.OID = index;
        

    
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
    //chiude navbar
    
    html += close_ul;
    
    //apre nav bar content
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
    //console.log("Path delle foto: " + foto_page.toString());
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
    html += "<div class=\"carousel slide qcar\" data-ride=\"carousel\" id=\"quote-carousel-" + stanza.OID + "\">";
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
        console.log("PER OGNI FOTO ------------------------");
        if (i == 0) {
            html += "<div class=\"item active\">"; //5.a
        } else {
            html += "<div class=\"item\">"; //5.b
        }
        console.log("FOTO PRIMA SPLIT: " + foto);
        var id_foto_arr = foto.split(split);
        console.log("FOTO DOPO SPLIT 1: " + id_foto_arr +" --- " + split);
        
        if(id_foto_arr.length==1){
           var id_foto_arr = foto.split(split2); 
           console.log("FOTO DOPO SPLIT 2: " + id_foto_arr +" --- " + split2);
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
    var html = "<div class=\"center\">";
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
    //html += "<button id=\"saveButton\" type=\"button\" class=\"btn btn-success\" onClick=\"salvaAnnuncioPreferiti()\" style=\"display:none\">Salva</button>";
    html += "</div>";
    html += " <div class=\"hr-line-dashed\"></div>";
    return html;
}

function init_info(annuncio) {
    var locatore = annuncio.Locatore;
    //console.log(locatore);
    return info_loc(locatore);
}
function info_loc(locatore) {
    var html = "";
    html += "<div id=\"info_locatore\" class=\"center blockLoc\">";
    html += "<div class=\"row center\">";
    html += "<img src='" + locatore.fotoProfilo + "'class='img-responsive img-circle' alt=''style=\"width:" + dim_image_prof + "px;height:" + dim_image_prof + "px;\" \>";
    html += "</div>";
    //html+="<p>"+JSON.stringify(locatore)+"</p>";
    html += "<p class=\"text-muted infLoc\"><span class=\"text-primary\">Nome: </span>" + locatore.nome + "</p>";
    html += "<p class=\"text-muted infLoc\"><span class=\"text-primary\">Cognome: </span>" + locatore.cognome + "</p>";
    html += "<p class=\"text-muted infLoc\"><span class=\"text-primary\">Descrizione: </span>" + locatore.descrizione + "</p>";
    html += "<p class=\"text-muted infLoc\"><span class=\"text-primary\">email: </span>" + locatore.email + "</p>";
    html += "<button id=\"segnalaBtn\" type=\"button\" class=\"btn btn-warning\" style=\"display:none\" onClick=\"seganalaAnnuncio()\">Seganala</button>";
    html += "</div>";
    html += "<div class=\"center blockLoc\">";
    html += "<button id=\"segnalaBtn\" type=\"button\" class=\"btn btn-warning\" onClick=\"getServices()\">Cancella marker</button>";
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
                //console.log("Studente loggato?: " + item);
                if (item == "true") {
                    $("#saveButton").show("fast");
                    $("#segnalaBtn").show("fast");
                }
            });
}

function callFoto(foto_OID) {
    var foto_arr = foto_OID.split("$");
    var foto = foto_arr[1];
    var OID = foto_arr[0];
    var id_foto_arr = foto.split(split);
    if(id_foto_arr.length==1){
           var id_foto_arr = foto.split(split2); 
        }
    var id_foto_ext = id_foto_arr[id_foto_arr.length - 1];
    var id_foto_ext_arr = id_foto_ext.split(".");
    var id_foto = id_foto_ext_arr[0];
    var type = id_foto_ext_arr[1];
    console.log("in load: " + id_foto + " ext: " + type);
    //console.log("in load " + id_foto);
    $.ajax({
        url: "ServletAnnuncio",
        type: 'get',
        dataType: 'text',
        //contentType: "image/jpg",
        data: {action: "Annunci-newAnnuncio-getImage", url: foto, type: type},
        success: function (base64Image) {
            //(foto + ": " + base64Image);
            var f = "<img class=\"img-responsive img-thumbnail\" src=\"data:image/" + type + ";base64, " + base64Image + "\" style=\"width:" + dim_image_car + "px;height:" + dim_image_car + "px;\">";
            
            
            var str = "#" + OID + "-" + id_foto;
            //console.log(str);
            var selector = $(str);
            //console.log("id CONTENITORE --- " + selector.attr("id"));
            selector.append(f);
            
            //$("#" + OID + "-" + id_foto + "").append(f);
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
    console.log("-----------------------------");
    console.log("-----------------------------");
    console.log("Entro in SavePath");
    console.log("-----------------------------");
    console.log("-----------------------------");
    
    var stanze = annuncio.Stanze[0];
     console.log(stanze.toString());
    $.each(stanze, function (index, stanza) {
        stanza.OID = index;
         console.log("Entro in stanza oid: " + stanza.OID + " index " + index);
        var fotos = stanza.Foto;
        $.each(fotos, function (i, foto) {
            console.log(stanza.OID + "$" + foto + " info i = " + i + " foto = " + foto );
            list.push(stanza.OID + "$" + foto);
        });
    });
    return list;
}


