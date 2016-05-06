var split = "\\";
var split2 = "/";
var foto_page = new Array();
var dim_image_car = 500; //dimensione immagine del carosello

var geocoder;
var map;
var geoAddress;
var marker_annunci = new Array();

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
            function (responseJson) {
                var latlng = [0, 0];
                $.each(responseJson, function (index, item) {
                    latlng[index] = item;
                });
                //funzione che inizializza la mappa sulla città
                initialize(latlng[0], latlng[1]);
                //Deferred serve pr delegare l'esecuzione di funzioni javascript
                var load_A = $.Deferred();
                load_A.done(load_Annunci);
                load_A.resolve();

            });
}

//Inizializzazione dellla mappa di google sulla città desiderata
// necesità un div chiamato map
function initialize(lat, lng) {
    geocoder = new google.maps.Geocoder();
    var latlng = new google.maps.LatLng(lat, lng);
    var mapOptions = {
        zoom: 14,
        center: latlng
    };
    map = new google.maps.Map(document.getElementById("map"), mapOptions);
}

//funzione per aggiungere un marker sulla mappa salvata nella var. globale map(quindi inizialize(lat,lng) prima)
function addMarker(location, label) {
    var marker = new google.maps.Marker({
        position: location,
        title: label,
        map: map
                //icon: icon
    });
    return marker;
}

//funzione frapper di addMarket che estrai i dati necessari alla creazione del marker da un json annuncio
function addMarkerToJSON(annuncio, index) {
    var lat = annuncio.Lat;
    var lng = annuncio.Lng;
    var address = annuncio.Indirizzo;
    var descrizione = annuncio.Descrizione;
    var label = "Annuncio " + (index + 1);
    var marker = addMarker(new google.maps.LatLng(lat, lng), label);
    var infowindow = new google.maps.InfoWindow({
        content: "<div id=\"" + index + "\"><p class=\"text-muted\">" + address + "</p><p class=\"text-muted\">" + descrizione + "</p>"
    });
    marker.addListener('click', function () {
        infowindow.open(map, marker);
    });
    return marker;
}

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
                    var marker = addMarkerToJSON(annuncio, index);
                    marker_annunci.push(marker);
                    //console.log(load_annuncio_image(annuncio));
                });
                actual = 0;
                n_page = n_annunci / N_ANNUNCI_X_PAGE;
                create_pageResult();
                if (n_page != 0) {
                    selectpage(1);
                }
            });
}

function create_pageResult() {
    //console.log("CREATE");
    //console.log(12 / N_ANNUNCI_X_PAGE);
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
        page_html += '<div class = "search-result row" id =' + (page + 1) + '_RESULT>';
        var foto_page_actual = new Array();
        for (var i = page * N_ANNUNCI_X_PAGE; (i < ((page + 1) * N_ANNUNCI_X_PAGE) && i < annunci.length); i++) {
            var html = getCodeCarousel(annunci[i], i);
            page_html += "<div class=\"col-sm-" + 12 / N_ANNUNCI_X_PAGE + "\">" + html + "</div>";
            foto_page_actual = savePath(foto_page_actual, annunci[i]);
        }
        page_html += close_div;
        page_annunci[page] = page_html;
        foto_page[page] = foto_page_actual;
        foto_page_actual = new Array();
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
    var indirizzo = annuncio.Indirizzo;
    var indirizzo_arr = indirizzo.split(",");
    indirizzo = indirizzo_arr[0] + "," + indirizzo_arr[1];
    html += "<div class=\"center\">" +
            "<h1>" + tipoAnnuncio + "</h1>" +
            "<p>" + indirizzo + "</p>" +
            //"<p class=\"text-muted\">" + annuncio.Descrizione + "</p>" +
            "<p><span class=\"text-primary\">Metratura Appartamento: </span>" + annuncio.Metratura + " m<sup>2</sup></p>" +
            "<p><span class=\"text-primary\">Data inizio affitto: </span>" + annuncio.DataInizioAffitto + "</p>" +
            "<p> <span class=\"text-primary\">Quartiere: </span> " + annuncio.Quartiere + "</p>";
    //"<p class=\"text-muted\"> <span class=\"text-primary\">Locatore: </span> " + annuncio.Locatore.nome + "</p>";
    if(annuncio.Arredato){
        html+="<p> <span class=\"text-primary\">Arredato: </span> Si </p>";
    }else{
        html+="<p> <span class=\"text-primary\">Arredato: </span> No </p>";
    }
    if (annuncio.Atomico) {
        html += "<p><span class=\"text-primary\">Numero locali: </span>" + annuncio.NumeroLocali + "</p>" +
                "<p><span class=\"text-primary\"> Prezzo: </span>" + annuncio.Prezzo + " &euro;</p>";
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
        var id_foto_arr = foto.split(split);
        if (id_foto_arr.length == 1) {
            var id_foto_arr = foto.split(split2);
        }
        var id_foto_ext = id_foto_arr[id_foto_arr.length - 1];
        var id_foto_ext_arr = id_foto_ext.split(".");
        var id_foto = id_foto_ext_arr[0];
        html += "<blockquote>";
        html += "<div id=\"" + stanza.OID + "-" + id_foto + "\" class=\" carousel-item \"></div>";
        html += "<p class=\"text-muted\"> <span class=\"text-primary\">Tipo Stanza: </span> " + stanza.Tipo + "</p>" +
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
        var id_foto_arr = foto.split(split);
        if (id_foto_arr.length == 1) {
            var id_foto_arr = foto.split(split2);
        }
        var id_foto_ext = id_foto_arr[id_foto_arr.length - 1];
        var id_foto_ext_arr = id_foto_ext.split(".");
        var id_foto = id_foto_ext_arr[0];

        html += "<blockquote>";
        html += "<div id=\"" + stanza.OID + "-" + id_foto + "\" class=\" carousel-item \"></div>";
        html += "<p class=\"text-muted\"> <span class=\"text-primary\">Tipo Stanza: </span> " + stanza.Tipo + " ";
        if (!atomico) {
            html += "<span class=\"text-primary\"> Prezzo: </span> " + stanza.Prezzo + "&euro;";
        }
        html += "</p>" +
                "</blockquote>" +
                "</div>";
    });
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
            var f = "<img class=\"img-responsive img-thumbnail\" src=\"data:image/" + type + ";base64, " + base64Image + "\" style=\"width:" + dim_image_car / N_ANNUNCI_X_PAGE + "px;height:" + dim_image_car / N_ANNUNCI_X_PAGE + "px;\">";
            $("#" + OID + "-" + id_foto + "").append(f);
        }
    });
}
function loadAllfoto(page) {

    var fotoPage = foto_page[page];
    for (var i = 0; i < fotoPage.length; i++) {
        var foto = fotoPage[i];
        callFoto(foto);
    }
    activateCaroselli();
}

//crea in maniera comulativa una lista di OID$path_foto
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


function selectpage(page) {
    if (actual === 0) {
        $("#list-result").append(page_annunci[page - 1]);
        loadAllfoto(page - 1);
        actual = page;
    } else if (actual !== (+page)) {
        $("#" + (actual) + "_RESULT").after(page_annunci[page - 1]);
        loadAllfoto(page - 1);
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



function init_filtro() {
    $("#quartieri-div").hide();
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
                console.log(filtro);
                var Quartieri = filtro.Quartieri;
                $.each(Quartieri, function (index, quart) {
                    $('#' + quart).prop('selected', true);
                });
                $('#quartieri').searchableOptionList({
                    maxHeight: '250px'
                });

                $("#quartieri-div").show();
                $('#pricefrom').val(filtro.Prezzo);
                $('#compCondominio').prop('checked', filtro.CompresoCondominio);
                $('#compRiscaldamento').prop('checked', filtro.CompresoRiscaldamento);
                $('#arredato').prop('checked', filtro.Arredato);

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
                if (id == undefined) {
                    $("#saveButton").text("Salva");
                } else {
                    $("#saveButton").text("Modifica");
                }
            });
}

function send_Annuncio(k) {
    var annuncio = annunci[k];
    var url = "/haiHome-war/dettagliAnnuncio.jsp";
    var json = JSON.stringify(annuncio);
    $.session.set('dettagli', json);
    $.session.set('admin',false);
    window.open(url);
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
                if (item == "ok")
                    alert("Fitro Salvato!!");
                var getf = $.Deferred();
                getf.done(getfiltro);
                getf.resolve();
            });
}
//Invio della form tramite PLUGIN
$(document).ready(function () {
    $('#searchForm').ajaxForm(function () {
        $("#searchDiv").stop().animate({"marginTop": "0px"}, "slow");
        clearMarkers(marker_annunci);
        marker_annunci = new Array();
        load_Annunci();
        var getf = $.Deferred();
        getf.done(getfiltro);
        getf.resolve();
    });
});

//Caricamento dei risultati a fine pagina
$(window).scroll(function () {
    if (($(window).scrollTop() + 200) >= $(document).height() - $(window).height()) {
        nextpage();
    }
    /*per far camminare il div del filtro
     * if (($(window).scrollTop()+700) <= $(document).height() - $(window).height() && ($(window).scrollTop()-700) <= $(document).height() - $(window).height()) {
        $("#searchDiv").stop().animate({"marginTop": ($(window).scrollTop()) + "px", "marginLeft": ($(window).scrollLeft()) + "px"}, "slow");
    }*/
});


function loggatoStudente() {
    $.post("ServletController",
            {action: "Ricerca-loggatoStudente"},
            function (item) {
                console.log("Studente loggato?: " + item);
                if (item != "") {
                    $("#saveButton").show("fast");
                }
            });
}


