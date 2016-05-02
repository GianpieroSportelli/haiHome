//Memorizzo i filtri che usciranno negli snippet
var filtri = new Array();
var actual;
var n_filtri = 0;

var FIlTRI_X_PAGE = 3;
var n_page = 0;
var page_filtri = new Array();

//variabile utile per capire quale filtro cancellare
var filtroToDelete;
//Variabile per intro.js
var tutorialAttivato = false;
var activatedTutorial = false;




$(document).ready(function () {
    getListaFiltriPreferiti();
    getAnnunciPreferiti();
    checkStudenteType();
});

function checkStudenteType() {
    var login_type = $('#user-access').text();
    console.log(login_type);
    if (login_type === "g+" || login_type === "fb") {
        $('#rigapwd').css('display', 'none');
        $('#rigaEmail').attr("class", "col-md-6 col-md-offset-3");
    }

}


function getAnnunciPreferiti() {
    $.post("ServletController",
            {action: "studente-getAnnunci"},
            function (data) {
                console.log(data);
            });
}


function add_button() {

    if (n_filtri > FIlTRI_X_PAGE) {
        //$("#filtriUtente").append("<div class=\"text-center \" id = \"button-div\">");
        var html = "<div>" +
                "<ul class=\"pager\"> <li> <a onClick='prevpage()' id='prev_page'> <span class='glyphicon glyphicon-menu-left'></span> </a> </li>" +
                "<li> <span id=\"num_page\"> 1 di " + page_filtri.length + "</span> </li>" +
                "<li> <a onClick='nextpage()' id='next_page'> <span class='glyphicon glyphicon-menu-right'> </span> </a> </li>" +
                "</ul> </div>";
        $("#filtriUtente").append(html);
        /*
         $("#button-div").append("<div class=\"btn-group\" id=\"group-button-page\">");
         $("#group-button-page").append("<button class=\"btn btn-white\"onClick=prevpage() type=\"button\"><i class=\"glyphicon glyphicon-chevron-left\"></i></button>");
         for (i = 0; i < n_page; i++) {
         
         var html = '<button class=\"btn btn-white\" onClick=selectpage(this.id) id=\"' + (i + 1) + '\"> ' + (i + 1) + '</button>';
         $("#group-button-page").append(html);
         }
         $("#group-button-page").append("<button class=\"btn btn-white\" onClick=nextpage() type=\"button\"><i class=\"glyphicon glyphicon-chevron-right\"></i> </button>");
         $("#button-div").append("</div>");
         $("#filtriUtente").append("</div>");*/
    }
}

function getListaFiltriPreferiti() {
    $.post("ServletController",
            {action: "get-lista-preferiti-studente"},
            function (responseJson) {
                page_filtri = new Array();
                filtri = [];
                n_page = 0;
                n_filtri = 0;



                $.each(responseJson, function (index, item) {
                    n_filtri += 1;
                    filtri[index] = item;
                });
                actual = 0;
                n_page = n_filtri / FIlTRI_X_PAGE;


                if (filtri.length === 0) {
                    var page_html = "<div>" +
                            "<div class=\"panel panel-default\">" +
                            "<div class=\"panel-heading\"> <p class=\"text-primary\"> <img src=\"include/css/Utente/Error-30.png\"> Nessun Filtro Salvato </p>"
                            + "</div> " +
                            "</div>" +
                            "</div>";
                    page_filtri[0] = page_html;
                    selectpage(1);
                } else {
                    constructFiltriPage();
                    selectpage(1);
                    add_button();
                }

            }
    );
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

function selectpage(page) {
    console.log(page);
    $("#num_page").html(page + " di " + page_filtri.length);
    if (actual === 0) {
        $("#filtriUtente").append(page_filtri[page - 1]);
        actual = page;
    } else if (actual !== (+page)) {
        $("#" + (actual) + "_RESULT").before(page_filtri[page - 1]);
        $("#" + (actual) + "_RESULT").remove();
        actual = +page;
    }
}

function constructFiltriPage() {
    var page_html = '';

    for (i = 1; i < (n_page + 1); i++) {
        page_html = '';
        page_html += '<div class="filtri row" id =' + i + '_RESULT>';

        for (var k = (i - 1) * FIlTRI_X_PAGE; (k < (i * FIlTRI_X_PAGE) && k < filtri.length); k++) {

            var citta = filtri[k].Città;
            var compresoCondominio = filtri[k].CompresoCondominio;
            var compresoRiscaldamento = filtri[k].CompresoRiscaldamento;
            var idFiltro = filtri[k].Id;
            //var idStudente = filtri[k].Id_Studente;
            var prezzo = filtri[k].Prezzo;
            var quartieri = filtri[k].Quartieri;
            var numeroCamere = filtri[k].NumeroCamereDaLetto;
            var numeroLocali = filtri[k].NumeroLocali;
            var numeroBagni = filtri[k].NumeroBagni;
            var metratura = filtri[k].Metratura;
            var tipoAnnuncio = filtri[k].Tipo;
            var tipoStanza = filtri[k].TipoStanza;

            var quartieriHTML = '';

            for (var indice in quartieri) {
                quartieriHTML += quartieri[indice] + " - ";
            }

            if (quartieriHTML !== '') {
                //Tolgo l'ultimo -
                quartieriHTML = quartieriHTML.substring(0, quartieriHTML.length - 2);
                quartieriHTML = "<p> <i class=\"glyphicon glyphicon-info-sign\"></i> <span class=\"text-primary\">Quartieri</span>: " + quartieriHTML + "<hr>";

            }

            var glyphCondominio = '';
            var glyphRiscaldamento = '';
            if (compresoCondominio === true) {
                glyphCondominio = "include/css/Utente/check-30.png";
            } else {
                glyphCondominio = "include/css/Utente/notChecked-30.png";
            }

            if (compresoRiscaldamento === true) {
                glyphRiscaldamento = "include/css/Utente/check-30.png";
            } else {
                glyphRiscaldamento = "include/css/Utente/notChecked-30.png";
            }

            var htmlNumeroCamere = '';

            if (numeroCamere != 0) {
                htmlNumeroCamere = "<p> <i class=\"glyphicon glyphicon-info-sign\"></i> <span class=\"text-primary\">Numero camere da letto</span>: " + numeroCamere + "<hr>";

            }

            var htmlNumeroLocali = '';

            if (numeroLocali != 0) {
                htmlNumeroLocali = "<p> <i class=\"glyphicon glyphicon-info-sign\"></i> <span class=\"text-primary\">Numero locali</span>: " + numeroLocali + "<hr>";

            }

            var htmlNumeroBagni = '';

            if (numeroBagni != 0) {
                htmlNumeroBagni = "<p> <i class=\"glyphicon glyphicon-info-sign\"></i> <span class=\"text-primary\">Numero bagni</span>: " + numeroBagni + "<hr>";
            }

            var htmlmetratura = '';

            if (metratura != 0) {
                htmlmetratura = "<p> <i class=\"glyphicon glyphicon-info-sign\"></i> <span class=\"text-primary\"> Metratura </span>: " + metratura + "<hr>";
            }

            var htmlprezzo = '';
            if (prezzo != 0) {
                htmlprezzo = "<img src=\"include/css/Utente/euro-icon.png\"> <span class=\"text-primary\"> Prezzo massimo</span>: " + prezzo + " euro";
            }

            var htmltipoAnnuncio = '';

            /**PRIMA C'ERA K AL POSTO DI IDFILTRO!!!!!!!!!!!!!!!!!!!!!!!!*/
            /**
             * 
             * 
             * 
             * 
             */
            var tipoVisualizzazione;
            if (tipoAnnuncio === "Stanza") {
                tipoVisualizzazione = 1;
                htmltipoAnnuncio = "<p class=\"text-primary\" style=\"text-align:center\"> <span class=\"titoloAnnuncio\"> Ricerca per stanze </span> <a onclick=\"deleteFilterModal(" + idFiltro + ")\" class=\"deleteButton btn btn-danger \"><i class=\"fa fa-trash-o\" title=\"Delete\" aria-hidden=\"true\"></i> <span class=\"sr-only\">Delete</span> Elimina </a> </p> ";
            } else if (tipoAnnuncio === "Appartamento") {
                tipoVisualizzazione = 2;
                htmltipoAnnuncio = "<p class=\"text-primary\" style=\"text-align:center\"> <span class=\"titoloAnnuncio\"> Ricerca per appartamenti </span> <a onclick=\"deleteFilterModal(" + idFiltro + ")\" class=\" deleteButton btn btn-danger\"><i class=\"fa fa-trash-o\" title=\"Delete\" aria-hidden=\"true\"></i> <span class=\"sr-only\">Delete</span> Elimina </a> </p>  ";
            } else {
                tipoVisualizzazione = 3;
                htmltipoAnnuncio = "<p class=\"text-primary\" style=\"text-align:center\"> <span class=\"titoloAnnuncio\"> Ricerca per appartamenti-stanze </span> <a onclick=\"deleteFilterModal(" + idFiltro + ")\" class=\" deleteButton btn btn-danger\"><i class=\"fa fa-trash-o\" title=\"Delete\" aria-hidden=\"true\"></i> <span class=\"sr-only\">Delete</span> Elimina </a> </p>  ";
            }

            var html = '';
            //HTML per Appartamenti
            if (tipoVisualizzazione === 2) {
                if (!tutorialAttivato) {
                    html = "<div><div class=\"panel panel-default\">" + "<div class='panel-heading'>" +
                            htmltipoAnnuncio + "</div>" +
                            "<div data-intro=\"Cliccando su un filtro, visualizzerai gli annunci che rispettano i suoi criteri.\" class=\"panel-body\" style=\"cursor:pointer\" id=\"filtro-" + idFiltro + "\" OnClick=send_filtro(" + idFiltro + ")>" +
                            "<p> <img src=\"include/css/Utente/Home-30.png\"> <span class=\"text-primary\"> Città </span>: " + citta + "&nbsp;" +
                            htmlprezzo +
                            "<hr>" +
                            quartieriHTML +
                            htmlNumeroLocali +
                            htmlNumeroCamere +
                            htmlNumeroBagni +
                            htmlmetratura +
                            "<p style=\"text-align:center\"> <span class=\"text-primary\"> Compreso Condominio</span>: <img src=\"" + glyphCondominio + "\"> <span class=\"text-primary\"> Compreso Riscaldamento</span>: <img src=\"" + glyphRiscaldamento + "\"></i>" +
                            "</div>" +
                            "</div>" +
                            //"</div>" +
                            "</div>";
                    tutorialAttivato = true;
                } else {
                    html = "<div><div class=\"panel panel-default\">" + "<div class='panel-heading'>" +
                            htmltipoAnnuncio + "</div>" +
                            "<div class=\"panel-body\" style=\"cursor:pointer\" id=\"filtro-" + idFiltro + "\" OnClick=send_filtro(" + idFiltro + ")>" +
                            "<p> <img src=\"include/css/Utente/Home-30.png\"> <span class=\"text-primary\"> Città </span>: " + citta + "&nbsp;" +
                            htmlprezzo +
                            "<hr>" +
                            quartieriHTML +
                            htmlNumeroLocali +
                            htmlNumeroCamere +
                            htmlNumeroBagni +
                            htmlmetratura +
                            "<p style=\"text-align:center\"> <span class=\"text-primary\"> Compreso Condominio</span>: <img src=\"" + glyphCondominio + "\"> <span class=\"text-primary\"> Compreso Riscaldamento</span>: <img src=\"" + glyphRiscaldamento + "\"></i>" +
                            "</div>" +
                            "</div>" +
                            //"</div>" +
                            "</div>";
                }

                //HTML per stanza
            } else if (tipoVisualizzazione === 1) {
                if (!tutorialAttivato) {
                    html = "<div><div class=\"panel panel-default\">" + "<div class='panel-heading'>" +
                            htmltipoAnnuncio + "</div>" +
                            "<div data-intro=\"Cliccando su un filtro, visualizzerai gli annunci che rispettano i suoi criteri.\" class=\"panel-body\" style=\"cursor:pointer\" id=\"filtro-" + idFiltro + "\" OnClick=send_filtro(" + idFiltro + ")>" +
                            "<p> <img src=\"include/css/Utente/Home-30.png\"> <span class=\"text-primary\"> Città </span>: " + citta + "&nbsp;" +
                            htmlprezzo +
                            "<hr>" +
                            quartieriHTML +
                            "<p> <i class=\"glyphicon glyphicon-info-sign\"></i> <span class=\"text-primary\">Tipo stanza</span>: " + tipoStanza +
                            "<hr>" +
                            "<p style=\"text-align:center\"> <span class=\"text-primary\"> Compreso Condominio</span>: <img src=\"" + glyphCondominio + "\"> <span class=\"text-primary\"> Compreso Riscaldamento</span>: <img src=\"" + glyphRiscaldamento + "\"></i>" +
                            "</div>" +
                            "</div>" +
                            //"</div>" +
                            "</div>";
                    tutorialAttivato = true;
                } else {
                    html = "<div><div class=\"panel panel-default\">" + "<div class='panel-heading'>" +
                            htmltipoAnnuncio + "</div>" +
                            "<div class=\"panel-body\" style=\"cursor:pointer\" id=\"filtro-" + idFiltro + "\" OnClick=send_filtro(" + idFiltro + ")>" +
                            "<p> <img src=\"include/css/Utente/Home-30.png\"> <span class=\"text-primary\"> Città </span>: " + citta + "&nbsp;" +
                            htmlprezzo +
                            "<hr>" +
                            quartieriHTML +
                            "<p> <i class=\"glyphicon glyphicon-info-sign\"></i> <span class=\"text-primary\">Tipo stanza</span>: " + tipoStanza +
                            "<hr>" +
                            "<p style=\"text-align:center\"> <span class=\"text-primary\"> Compreso Condominio</span>: <img src=\"" + glyphCondominio + "\"> <span class=\"text-primary\"> Compreso Riscaldamento</span>: <img src=\"" + glyphRiscaldamento + "\"></i>" +
                            "</div>" +
                            "</div>" +
                            //"</div>" +
                            "</div>";
                }
            } else if (tipoVisualizzazione === 3) {
                if (!tutorialAttivato) {
                    html = "<div><div class=\"panel panel-default\">" + "<div class='panel-heading'>" +
                            htmltipoAnnuncio + "</div>" +
                            "<div data-intro=\"Cliccando su un filtro, visualizzerai gli annunci che rispettano i suoi criteri.\" class=\"panel-body\" style=\"cursor:pointer\" id=\"filtro-" + idFiltro + "\" OnClick=send_filtro(" + idFiltro + ")>" +
                            "<p> <img src=\"include/css/Utente/Home-30.png\"> <span class=\"text-primary\"> Città </span>: " + citta + "&nbsp;" +
                            htmlprezzo +
                            "<hr>" +
                            quartieriHTML +
                            "<p style=\"text-align:center\"> <span class=\"text-primary\"> Compreso Condominio</span>: <img src=\"" + glyphCondominio + "\"> <span class=\"text-primary\"> Compreso Riscaldamento</span>: <img src=\"" + glyphRiscaldamento + "\"></i>" +
                            "</div>" +
                            "</div>" +
                            //"</div>" +
                            "</div>";
                    tutorialAttivato = true;
                } else {
                    html = "<div><div class=\"panel panel-default\">" + "<div class='panel-heading'>" +
                            htmltipoAnnuncio + "</div>" +
                            "<div class=\"panel-body\" style=\"cursor:pointer\" id=\"filtro-" + idFiltro + "\" OnClick=send_filtro(" + idFiltro + ")>" +
                            "<p> <img src=\"include/css/Utente/Home-30.png\"> <span class=\"text-primary\"> Città </span>: " + citta + "&nbsp;" +
                            htmlprezzo +
                            "<hr>" +
                            quartieriHTML +
                            "<p style=\"text-align:center\"> <span class=\"text-primary\"> Compreso Condominio</span>: <img src=\"" + glyphCondominio + "\"> <span class=\"text-primary\"> Compreso Riscaldamento</span>: <img src=\"" + glyphRiscaldamento + "\"></i>" +
                            "</div>" +
                            "</div>" +
                            //"</div>" +
                            "</div>";
                }
            }
            page_html += html;
        }

        page_html += "</div>";
        page_filtri[i - 1] = page_html;
    }

}



//I BOTTONI CON LE X RICHIAMANO QUESTO METODO, MI SALVO L'ID DEL FILTRO DA ELIMINARE E VISUALIZZO 
//IL MODAL PER LA CANCELLAZIONE
function deleteFilterModal(idFiltro) {
    filtroToDelete = idFiltro;

    //IL MODAL VIENE CARICATO NELL'INCLUDE DELLA PAGINA
    $('#modalCancellazione').modal('show');
}

//METODO RICHIAMATO DAL MODAL QUANDO SPINGI SI
function deleteFilter() {
    $.post("ServletController",
            {action: "Ricerca-deleteFiltro", filtroID: filtroToDelete},
            function (data) {
                if (data == "OK") {
                    //Resetto tutto
                    $('#filtriUtente').empty();
                    getListaFiltriPreferiti();
                } else {
                    //Errore
                    alert(data);
                }
            });
}

//Richiama un filtro di ricerca
function send_filtro(idFiltro) {

    $.post("ServletController",
            {action: "Ricerca-setFiltro", ID: idFiltro},
            function (data) {
                if (data == "true") {
                    var url = "/haiHome-war/search-page.jsp";
                    window.open(url);
                } else
                    alert('ERRORE');
            });
    /*
     var annuncio = annunci[k];
     console.log(annuncio);
     var url = "/haiHome-war/ServletController";
     var url2 = "/haiHome-war/dettagliAnnuncio.jsp";
     var json = JSON.stringify(annuncio);
     console.log(k);
     console.log(json);
     $.session.set('dettagli', json);
     window.open(url2);*/
}