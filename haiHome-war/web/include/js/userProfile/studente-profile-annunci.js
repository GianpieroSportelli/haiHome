var split = "\\";
var split2 = "/";
var foto_page = new Array();
var dim_image_car = 500; //dimensione immagine del carosello

var N_ANNUNCI_X_PAGE = 2;
var n_page_1 = 0;
var n_annunci = 0;
var annunci = new Array();
var page_annunci = new Array();
var caroselli = new Array();

var actual_1 = 0;

//Variabile utile per usare lo scroll per caricare gli annunci solo quando
//sei effettivamente sul tab
var tabAnnunci = false;

var annuncioToDelete = '';
var paginaToDisplay = 2;
var annuncioNumberToDelete;

function activateScroll(activation) {
    tabAnnunci = activation;
}

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
    var no_res = "<div class=\"panel-heading\"> I tuoi annunci </div>" +
            "<div class=\"panel-body\"> <p>Nessun annuncio salvato.</p>"
            + "</div> ";
    var page_html = '';
    if (n_page_1 == 0) {
        $("#annunci").empty();
        $("#annunci").append(no_res);
        //$("#list-result").append(result_div + no_res + close_div);
    }
    for (page = 0; page < n_page_1; page++) {
        for (var i = page * N_ANNUNCI_X_PAGE; (i < ((page + 1) * N_ANNUNCI_X_PAGE) && i < annunci.length); i++) {
            var foto_page_actual_1 = new Array();
            var page_html = '';
            var html = getCodeCarousel(annunci[i], i);
            //page_html += "<div class=\col-sm-" + 12 / N_ANNUNCI_X_PAGE + "\">" + html + "</div>";
            foto_page_actual_1 = savePath(foto_page_actual_1, annunci[i]);
            //console.log(foto_page_actual_1);
            // page_html += close_div;
            // 
            //page_annunci[i] = page_html;
            page_annunci[i] = html;
            foto_page[i] = foto_page_actual_1;
            console.log('Immagini annuncio' + i + " " + foto_page[i]);
        }
    }
    /*
     for(var l = 0; l < page_annunci.length; l++){
     console.log(page_annunci[l]);
     }
     for(var l = 0; l < foto_page.length; l++){
     console.log(foto_page[l]);
     }*/
}

function getCodeCarousel(annuncio, k) {
    var html = "<div class=\"annuncio-" + k + "\" id=\"annuncio-" + k + "\">"; //1
    html += "<div class=\"panel panel-default div_snippet\">"; //2
    html += "<div class='panel-heading' OnClick=send_Annuncio(" + k + ") style=\"cursor:pointer\">"; //3
    html += create_carousel(annuncio);
    html += "</div>"; //chiusura testa del pannello
    html += "<div class=\"panel-body snip\">";
    html += create_info_annuncio(annuncio, k);
    html += "</div>"; //3
    html += "</div>"; //2
    html += "</div>"; //1
    return html;
}

function create_info_annuncio(annuncio, k) {
    var html = "";
    var tipoAnnuncio = "Annuncio Stanze";
    if (annuncio.Atomico) {
        tipoAnnuncio = "Annuncio Appartamento";
    }
    var indirizzo = annuncio.Indirizzo;
    var indirizzo_arr = indirizzo.split(",");
    indirizzo = indirizzo_arr[0] + "," + indirizzo_arr[1];
    var htmlOscurato = '';
    if (annuncio.Oscurato) {
        htmlOscurato = "<p style=\"color:red;\"><span class=\"center\">ANNUNCIO OSCURATO</span> </p>";
    }
    if (annuncio.Archiviato) {
        htmlOscurato = "<p style=\"color:red;\"><span class=\"center\">ANNUNCIO ARCHIVIATO</span> </p>";
    }
    if (annuncio.Locatore.bloccato) {
        htmlOscurato = "<p style=\"color:red;\"><span class=\"center\">LOCATORE BLOCCATO</span> </p>";
    }
    html += "<div class=\"center\" OnClick=send_Annuncio(" + k + ") style=\"cursor:pointer\">" +
            htmlOscurato +
            "<h1> <strong>" + tipoAnnuncio + "</strong> </h1>" +
            "<p>" + indirizzo + "</p>" +
            //"<p class=\"text-muted\">" + annuncio.Descrizione + "</p>" +
            "<p><span class=\"text-primary\">Metratura Appartamento: </span>" + annuncio.Metratura + "</p>" +
            "<p><span class=\"text-primary\">Data inizio affitto: </span>" + annuncio.DataInizioAffitto + "</p>" +
            "<p> <span class=\"text-primary\">Quartiere: </span> " + annuncio.Quartiere + "</p>";
    //"<p class=\"text-muted\"> <span class=\"text-primary\">Locatore: </span> " + annuncio.Locatore.nome + "</p>";
    if (annuncio.Arredato) {
        html += "<p> <span class=\"text-primary\">Arredato: </span> Si </p>";
    } else {
        html += "<p> <span class=\"text-primary\">Arredato: </span> No </p>";
    }
    if (annuncio.Atomico) {
        html += "<p><span class=\"text-primary\">Numero locali: </span>" + annuncio.NumeroLocali + "</p>" +
                "<p><span class=\"text-primary\"> Prezzo: </span>" + annuncio.Prezzo + " &euro;</p>";
    }
    html += "</div>"; //chiusura center

    html += "<div class=\"IDF" + annuncio.OID + "\" id=\"footerAnnuncio\"> <p> <a onclick=\"deleteAnnuncioModal(" + annuncio.OID + ")\" class=\" deleteButtonAnnuncio btn btn-danger\"><i class=\"fa fa-trash-o\" title=\"Delete\" aria-hidden=\"true\"></i> <span class=\"sr-only\">Delete</span>Elimina</a> </p> </div>";
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
    console.log(" Load all foto di annuncio: " + page + " - " + fotoPage);
    for (var i = 0; i < fotoPage.length; i++) {
        var foto = fotoPage[i];
        callFoto(foto);
    }
    //activateCaroselli();
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
        var diplayFirst = false;
        var displaySecond = false;
        var inizio_html = '<div class = "search-result row" id =' + page + '_RESULT1>';
        if (page_annunci[page - 1] != null) {
            if (isNaN(page_annunci[page - 1].charAt(0)) === true) {
                diplayFirst = true;
                inizio_html += "<div class=\"idCanc-" + (page - 1) + " col-sm-" + 12 / N_ANNUNCI_X_PAGE + "\">";
                inizio_html += page_annunci[page - 1];
                inizio_html += "</div>";
            } else {
                inizio_html += "<div class=\"idCanc-" + (page - 1) + " col-sm-" + 12 / N_ANNUNCI_X_PAGE + "\">";
                inizio_html += page_annunci[page - 1].substr(1, (page_annunci[page - 1].length - 1));
                inizio_html += "</div>";
            }
            console.log("AGGIUNGO PAGINA: " + inizio_html);
        }
        if (page_annunci[page] != null) {
            if (isNaN(page_annunci[page].charAt(0)) === true) {
                displaySecond = true;
                inizio_html += "<div class=\"idCanc-" + (page) + " col-sm-" + 12 / N_ANNUNCI_X_PAGE + "\">";
                inizio_html += page_annunci[page];
                inizio_html += "</div>";
            } else {
                inizio_html += "<div class=\"idCanc-" + (page) + " col-sm-" + 12 / N_ANNUNCI_X_PAGE + "\">";
                inizio_html += page_annunci[page].substr(1, (page_annunci[page].length - 1));
                inizio_html += "</div>";
            }
            console.log("AGGIUNGO PAGINA: " + inizio_html);
        }
        inizio_html += "</div>";
        $("#list-result").append(inizio_html);
        if (diplayFirst) {
            loadAllfoto(page - 1);
        }
        if (displaySecond) {
            loadAllfoto(page);
        }
        activateCaroselli();
        actual_1 = page;

    } else if (actual_1 !== (+page)) {
        var diplayFirst = false;
        var displaySecond = false;
        var inizio_html = '<div class = "search-result row" id =' + page + '_RESULT1>';
        if (page_annunci[paginaToDisplay] != null) {
            if (isNaN(page_annunci[paginaToDisplay].charAt(0)) === true) {
                inizio_html += "<div class=\"idCanc-" + (paginaToDisplay) + " col-sm-" + 12 / N_ANNUNCI_X_PAGE + "\">";
                inizio_html += page_annunci[paginaToDisplay];
                inizio_html += "</div>";
                diplayFirst = true;
            } else {
                inizio_html += "<div class=\"idCanc-" + (paginaToDisplay) + " col-sm-" + 12 / N_ANNUNCI_X_PAGE + "\">";
                inizio_html += page_annunci[paginaToDisplay].substr(1, (page_annunci[paginaToDisplay].length - 1));
                inizio_html += "</div>";
            }

            console.log("AGGIUNGO PAGINA: " + inizio_html);
        }
        if (page_annunci[paginaToDisplay + 1] != null) {
            if (isNaN(page_annunci[paginaToDisplay + 1].charAt(0)) === true) {
                inizio_html += "<div class=\"idCanc-" + (paginaToDisplay + 1) + " col-sm-" + 12 / N_ANNUNCI_X_PAGE + "\">";
                inizio_html += page_annunci[paginaToDisplay + 1];
                inizio_html += "</div>";
                displaySecond = true;
            } else {
                inizio_html += "<div class=\"idCanc-" + (paginaToDisplay + 1) + " col-sm-" + 12 / N_ANNUNCI_X_PAGE + "\">";
                inizio_html += page_annunci[paginaToDisplay + 1].substr(1, (page_annunci[paginaToDisplay + 1].length - 1));
                inizio_html += "</div>";
            }
            console.log("AGGIUNGO PAGINA: " + inizio_html);
        }
        inizio_html += "</div>";
        $("#" + (actual_1) + "_RESULT1").after(inizio_html);
        if (diplayFirst) {
            loadAllfoto(paginaToDisplay);
        }
        if (displaySecond) {
            loadAllfoto(paginaToDisplay + 1);
        }
        activateCaroselli();
        //   loadAllfoto(page - 1);
        //   loadAllfoto(page);
        actual_1 = +page;
        paginaToDisplay += 2;
    }
}

function activateCaroselli() {
    $('.carousel').carousel({
        pause: true,
        interval: 4000
    });
}

/*function prevpage_1() {
 if (actual_1 > 1) {
 var page = actual_1 - 1;
 selectpage_1(page);
 }
 }*/

function nextpage_1() {
    if (n_page_1 != 0) {
        if (actual_1 < n_page_1) {
            var page = actual_1 + 1;
            selectpage_1(page);
        }
    }
}

function send_Annuncio(k) {
    var annuncio = annunci[k];

    if (annuncio.Oscurato) {
        $('#annuncio-' + k).attr('data-content', "L'annuncio è stato oscurato e non può essere visualizzato.");
        $('#annuncio-' + k).popover('show');
    } else if (annuncio.Archiviato) {
        $('#annuncio-' + k).attr('data-content', "L'annuncio è stato archiviato e non può essere visualizzato.");
        $('#annuncio-' + k).popover('show');
    } else if (annuncio.Locatore.bloccato) {
        $('#annuncio-' + k).attr('data-content', "Il locatore è stato bloccato e non può essere visualizzato.");
        $('#annuncio-' + k).popover('show');
    } else {
        var url = "/haiHome-war/dettagliAnnuncio.jsp";
        var json = JSON.stringify(annuncio);
        $.session.set('dettagli', json);
        $.session.set('admin', false);
        window.location = url;
    }

    var millisecondsToWait = 5000;
    setTimeout(function () {
        $('#annuncio-' + k).popover('hide');
    }, millisecondsToWait);
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
    if (tabAnnunci) {
        if (($(window).scrollTop() + 200) >= $(document).height() - $(window).height()) {
            nextpage_1();
        }
    }
});

function deleteAnnuncioModal(idAnnuncio) {
    annuncioToDelete = idAnnuncio;
    annuncioNumberToDelete = $('div.IDF' + annuncioToDelete).parent().parent().parent().parent().attr("class").split(" ")[0];
    annuncioNumberToDelete = annuncioNumberToDelete.toString().split("-")[1];
    console.log("DEVI CANCELLARE -->>> " + annuncioNumberToDelete);
    //IL MODAL VIENE CARICATO NELL'INCLUDE DELLA PAGINA
    $('#modalCancellazioneAnnuncio').modal('show');
}

function deleteAnnuncio() {

    $.post("ServletController",
            {action: "studente-removeAnnuncio", id: annuncioToDelete},
            function (item) {
                //var html = '';
                //alert(item);
                console.log("Annuncio eliminato?: " + item);
                if (item == "true") {
                    refresh_annunci();
                } else {
                    $('#annunci').attr('data-content', "Errore eliminazione annuncio: " + item);
                    $('#annunci').popover('show');
                }
            });
}

function refresh_annunci() {
    //
    n_annunci -= 1;
    actual_1 = 0;
    n_page_1 = n_annunci / N_ANNUNCI_X_PAGE;
    paginaToDisplay = 2;


    for (var i = 0; i < page_annunci.length; i++) {
        console.log("Annuncio: " + i + " - " + page_annunci[i]);
    }
    console.log("CANCELLARE: " + annuncioNumberToDelete);
    /*var newAnnunci = new Array();
     for (var i = 0; i < annuncioNumberToDelete; i++) {
     newAnnunci[i] = page_annunci[i];
     }
     for (var i = annuncioNumberToDelete; i < page_annunci.length - 1; i++) {
     newAnnunci[i] = page_annunci[(i + 1)];
     }*/

    page_annunci.splice(annuncioNumberToDelete, 1);
    foto_page.splice(annuncioNumberToDelete, 1);

    for (var i = 0; i < page_annunci.length; i++) {
        console.log("POST Annuncio: " + i + " - " + page_annunci[i]);
    }
    $("#list-result").empty();
    if (n_page_1 != 0) {
        selectpage_1(1);
    } else {
        var no_res = "<div class=\"panel-heading\"> I tuoi annunci </div>" +
                "<div class=\"panel-body\"> <p>Nessun annuncio salvato.</p>"
                + "</div> ";
        $("#annunci").empty();
        $("#annunci").append(no_res);
        //$("#list-result").append(result_div + no_res + close_div);

    }
}
/*
 function refresh_annunci_1() {
 var annuncioIDPAGE = $('div.IDF' + annuncioToDelete).parent().parent().parent().attr("id").split("-")[1];
 console.log("DA CANCELLARE " + annuncioIDPAGE);
 //console.log("ID STAMPATO: " + $('div.IDF' + annuncioToDelete).parent().parent().parent().parent().empty());
 //DIV RISULTATO ROW
 console.log("CLASSE PADRE ROW " + $('div.IDF' + annuncioToDelete).parent().parent().parent().parent().parent().attr("class"));
 console.log("HTML FRATELLO " + $('div.IDF' + annuncioToDelete).parent().parent().parent().parent().next(".col-sm-6").html());
 
 //console.log("FRATELLO " + $('div.IDF' + annuncioToDelete).parent().parent().parent().parent().next().attr("id"));
 if (annuncioIDPAGE % 2 === 0) {
 //pari, sposto la roba a sinistra e poi scalo di uno
 //Se non è vuoto
 if (!$.trim($('div.IDF' + annuncioToDelete).parent().parent().parent().parent().next(".col-sm-6").html()) === false) {
 //Html non vuoto, c'è un fratello
 var newHtml = $('div.IDF' + annuncioToDelete).parent().parent().parent().parent().next(".col-sm-6").html();
 console.log("HTML: " + newHtml);
 var inizioHTML = "<div class=\"col-sm-" + 12 / N_ANNUNCI_X_PAGE + "\">";
 inizioHTML += newHtml;
 inizioHTML += "</div>";
 
 //Cancello la roba del padre
 $('div.IDF' + annuncioToDelete).parent().parent().parent().parent().parent().empty();
 $('div.IDF' + annuncioToDelete).parent().parent().parent().parent().parent().append(inizioHTML);
 
 if (actual_1 === 1) {
 //Prima pagina
 }
 //Non ho scrollato ancora il prossimo da aggiungere
 if (actual_1 < paginaToDisplay && actual_1 !== 1) {
 
 }
 
 
 } else {
 //O all'ultima pagina oppure sto cancellando l'ultimo annuncio
 //Se non è la prima ROW
 if ($('div.IDF' + annuncioToDelete).parent().parent().parent().parent().parent().attr("ID").split("_")[0] !== '1') {
 console.log('ARRIVATO');
 } else {
 console.log('INIZIO');
 }
 }
 }
 
 }
 */
$(document).ajaxStop(function () {

    for (var i = 0; i < paginaToDisplay; i++) {
        if (!$.trim($('div.idCanc-' + i).html()) === false) {
            page_annunci[i] = "" + i + $('div.idCanc-' + i).html();
            console.log("Nuovo HTML per annuncio: " + i + " : " + page_annunci[i]);
        }
    }
    /*
     //Prima pagina da visualizzare
     if (paginaToDisplay === 2) {
     if (n_page_1 != 0) {
     if (!$.trim($('div.annuncio-0').parent().html()) === false) {
     page_annunci[0] = "0" + $('div.annuncio-0').parent().html();
     console.log("HTML NUOVO DI 0 - " + page_annunci[0]);
     }
     
     if (!$.trim($('div.annuncio-1').parent().html()) === false)
     page_annunci[1] = "1" + $('div.annuncio-1').parent().html();
     console.log("HTML NUOVO DI 1 - " + page_annunci[1]);
     }
     
     
     } else {
     //Se non è vuoto
     if (!$.trim($('div.annuncio-' + (paginaToDisplay - 2)).parent().html()) === false) {
     // console.log("HTML ANNUNCIO: " + (paginaToDisplay - 2) + " - " + $('div.annuncio-' + +(paginaToDisplay - 2)).parent().html());
     page_annunci[paginaToDisplay - 2] = "" + (paginaToDisplay - 2) + $('div.annuncio-' + +(paginaToDisplay - 2)).parent().html();
     console.log(" HTML NUOVO DI " + (paginaToDisplay - 2) + " - " + page_annunci[paginaToDisplay - 2]);
     }
     if (!$.trim($('div.annuncio-' + (paginaToDisplay - 1)).parent().html()) === false) {
     // console.log("HTML ANNUNCIO: " + (paginaToDisplay - 1) + " - " + $('div.annuncio-' + +(paginaToDisplay - 1)).parent().html());
     page_annunci[paginaToDisplay - 1] = "" + (paginaToDisplay - 1) + $('div.annuncio-' + +(paginaToDisplay - 1)).parent().html();
     console.log(" HTML NUOVO DI " + (paginaToDisplay - 1) + " - " + page_annunci[paginaToDisplay - 1]);
     
     }
     }*/
});



