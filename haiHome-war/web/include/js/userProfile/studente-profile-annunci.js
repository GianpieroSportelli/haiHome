var split = "\\";
var split2 = "/";
var foto_page = new Array();
var dim_image_car = 500; //dimensione immagine del carosello

var geocoder;
var map;
var geoAddress;
var marker_annunci = new Array();

var N_ANNUNCI_X_PAGE = 2;
var n_page_1 = 0;
var n_annunci = 0;
var annunci = new Array();
var page_annunci = new Array();
var caroselli = new Array();

var actual_1 = 0;

function load_Annunci_Studente() {
    $.post("ServletController",
            {action: "studente-getAnnunci"},
            function (responseJson) {
                console.log(responseJson);
                annunci = [];
                n_page_1 = 0;
                n_annunci = 0;

                $("#list-result").empty();


                $.each(responseJson, function (index, annuncio) {
                    n_annunci += 1;
                    annunci[index] = annuncio;
                    //console.log(load_annuncio_image(annuncio));
                });
                actual_1 = 0;
                n_page_1 = n_annunci / N_ANNUNCI_X_PAGE;
                create_pageResult_studente();

                console.log(n_page_1);
                if (n_page_1 != 0) {
                    selectpage_1(1);
                }
            });
}

function create_pageResult_studente() {
    //console.log("CREATE");
    //console.log(12 / N_ANNUNCI_X_PAGE);
    page_annunci = [];
    var result_div = '<div class = "search-result" >';
    var close_div = '</div>';
    var no_res = '<p > Nessun Risultato!! </p>';
    var page_html = '';
    if (n_page_1 == 0) {
        $("#list-result").append(result_div + no_res + close_div);
    }
    for (page = 0; page < n_page_1; page++) {
        page_html = '';
        page_html += '<div class = "search-result row" id =' + (page + 1) + '_RESULT1>';
        var foto_page_actual_1 = new Array();
        for (var i = page * N_ANNUNCI_X_PAGE; (i < ((page + 1) * N_ANNUNCI_X_PAGE) && i < annunci.length); i++) {
            var html = getCodeCarousel(annunci[i], i);
            page_html += "<div class=\"col-sm-" + 12 / N_ANNUNCI_X_PAGE + "\">" + html + "</div>";
            foto_page_actual_1 = savePath(foto_page_actual_1, annunci[i]);
            console.log(foto_page_actual_1);
        }
        page_html += close_div;
        page_annunci[page] = page_html;
        foto_page[page] = foto_page_actual_1;
        foto_page_actual_1 = new Array();
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
            "<h1> <strong>" + tipoAnnuncio + "</strong> </h1>" +
            "<p>" + indirizzo + "</p>" +
            //"<p class=\"text-muted\">" + annuncio.Descrizione + "</p>" +
            "<p><span class=\"text-primary\">Metratura Appartamento: </span>" + annuncio.Metratura + "</p>" +
            "<p><span class=\"text-primary\">Data inizio affitto: </span>" + annuncio.DataInizioAffitto + "</p>" +
            "<p> <span class=\"text-primary\">Quartiere: </span> " + annuncio.Quartiere + "</p>";
    //"<p class=\"text-muted\"> <span class=\"text-primary\">Locatore: </span> " + annuncio.Locatore.nome + "</p>";
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
    console.log(foto_page);
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


function selectpage_1(page) {
    if (actual_1 === 0) {
        $("#list-result").append(page_annunci[page - 1]);
        loadAllfoto(page - 1);
        actual_1 = page;
    } else if (actual_1 !== (+page)) {
        $("#" + (actual_1) + "_RESULT1").after(page_annunci[page - 1]);
        loadAllfoto(page - 1);
        actual_1 = +page;
    }
}

function activateCaroselli() {
    $('.carousel').carousel({
        pause: true,
        interval: 4000
    });
}

function prevpage_1() {
    if (actual_1 > 1) {
        var page = actual_1 - 1;
        selectpage_1(page);
    }
}

function nextpage_1() {
    if (n_page_1 != 0) {
        if (actual_1 < n_page_1) {
            var page = actual_1 + 1;
            selectpage_1(page);
        }
    }
}

/*$(window).scroll(function(){    
 $("#searchDiv").stop().animate({"marginTop": ($(window).scrollTop()) + "px", "marginLeft":($(window).scrollLeft()) + "px"}, "fast" );
 });*/

function send_Annuncio(k) {
    var annuncio = annunci[k];
    var url = "/haiHome-war/dettagliAnnuncio.jsp";
    var json = JSON.stringify(annuncio);
    $.session.set('dettagli', json);
    window.location = url;
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

//Caricamento dei risultati a fine pagina
$(window).scroll(function () {
    if ($(window).scrollTop() == $(document).height() - $(window).height()) {
        nextpage_1();
    }
});


