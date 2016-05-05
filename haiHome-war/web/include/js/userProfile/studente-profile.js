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
    checkStudenteType();
    getListaFiltriPreferiti();
    load_Annunci_Studente();

    var backups = "";

    $('.start-edit').on('click', function () {
        var target_name = $(this).parent().parent().parent().attr("id").replace("panel-", "");
        var $input_target = $("#" + target_name);

        console.log("start-edit on -> " + target_name);

        backups = $input_target.val();
        $input_target.prop("disabled", false);

        $(this).parent().children().each(function () {
            $(this).toggle();
        });
        console.log("current value of " + target_name + "... " + $input_target.val());
    });

    $('.cancel-edit').on('click', function () {
        var $panel = $(this).parent().parent().parent();
        var target_name = $panel.attr("id").replace("panel-", "");
        var $input_target = $("#" + target_name);

        console.log("cancel-edit on -> " + target_name);

        $(this).parent().children().each(function () {
            $(this).toggle();
        });

        $input_target.val(backups);
        $panel.children(".panel-body").removeClass("has-error");
        $input_target.prop("disabled", "disabled");
    });

    $('.save-edit').on('click', function () {
        var $this2 = $(this);
        var $panel = $(this).parent().parent().parent();
        var target_name = $panel.attr("id").replace("panel-", "");
        var $input_target = $("#" + target_name);
        var new_content = $input_target.val();
        console.log("save-edit on -> " + target_name + ", new value: " + new_content);

        if (target_name === "password" &&
                $('#new-password').val() !== $('#new-password2').val()) {
            $panel.children(".panel-body").addClass("has-error");
            console.log("password mismatch - client side")
            return false;
        }

        $.post(
                "ServletController",
                {
                    'action': 'studente-edit-info',
                    'field-value': new_content, /* credo di sì invece */
                    'new-pw': $('#new-password').val()
                },
                function (responseJson) {
                    if (responseJson.result) {
                        $this2.parent().children().each(function () {
                            $(this).toggle();
                        });
                        $input_target.prop("disabled", "disabled");
                        $panel.children(".panel-body").removeClass("has-error");

                        //        $panel.children(".panel-body").addClass("has-success");
                        console.log("done");
                    } else {
                        $panel.children(".panel-body").addClass("has-error");
                        console.log("error code: " + responseJson.error);
                        alert(responseJson.error);
                    }
                    $('#password').val('');
                    $('#new-password').val('');
                    $('#new-password2').val('');
                }
        );


    });
});

function checkStudenteType() {
    var login_type = $('#user-access').text();
    console.log(login_type);
    if (login_type === "g+" || login_type === "fb") {
        $('#rigapwd').css('display', 'none');
        $('#rigaEmail').attr("class", "col-md-6 col-md-offset-3");
    }

}


function add_button() {

    if (n_filtri > FIlTRI_X_PAGE) {
        //$("#filtriUtente").append("<div class=\"text-center \" id = \"button-div\">");
        var html = "<div>" +
                "<ul class=\"pager\"> <li class=\"pagerCursore\"> <a onClick='prevpage()' id='prev_page'> <span class='glyphicon glyphicon-menu-left'></span> </a> </li>" +
                "<li> <span id=\"num_page\"> 1 di " + page_filtri.length + "</span> </li>" +
                "<li class=\"pagerCursore\"> <a onClick='nextpage()' id='next_page'> <span class='glyphicon glyphicon-menu-right'> </span> </a> </li>" +
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
                console.log(responseJson);
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
                quartieriHTML = "<hr> <p> <i class=\"glyphicon glyphicon-info-sign\"></i> <span class=\"text-primary\">Quartieri</span>: " + quartieriHTML;

            }
            var htmlCheckBox = '';

            var glyph = "include/css/Utente/check-30.png";
            if (compresoCondominio === true && compresoRiscaldamento === true) {
                htmlCheckBox = "<hr> <p style=\"text-align:center\"> <span class=\"text-primary\">Compreso Condominio</span>: <img src=\"" + glyph + "\"> &nbsp;&nbsp;&nbsp; <span class=\"text-primary\">Compreso Riscaldamento</span>: <img src=\"" + glyph + "\"> </p>"
            } else if (compresoRiscaldamento === true) {
                htmlCheckBox = "<hr> <p style=\"text-align:center\"> <span class=\"text-primary\">Compreso Riscaldamento</span>: <img src=\"" + glyph + "\"> </p>"
            } else if (compresoCondominio === true) {
                htmlCheckBox = "<hr> <p style=\"text-align:center\"> <span class=\"text-primary\">Compreso Condominio</span>: <img src=\"" + glyph + "\"> </p>"
            }
            
            var htmlNumeroCamere = '';

            if (numeroCamere != 0) {
                htmlNumeroCamere = "<hr> <p> <i class=\"glyphicon glyphicon-info-sign\"></i> <span class=\"text-primary\">Numero camere da letto</span>: " + numeroCamere;
            }

            var htmlNumeroLocali = '';

            if (numeroLocali != 0) {
                htmlNumeroLocali = "<hr> <p> <i class=\"glyphicon glyphicon-info-sign\"></i> <span class=\"text-primary\">Numero locali</span>: " + numeroLocali;
            }

            var htmlNumeroBagni = '';

            if (numeroBagni != 0) {
                htmlNumeroBagni = "<hr> <p> <i class=\"glyphicon glyphicon-info-sign\"></i> <span class=\"text-primary\">Numero bagni</span>: " + numeroBagni;
            }

            var htmlmetratura = '';

            if (metratura != 0) {
                htmlmetratura = "<hr> <p> <i class=\"glyphicon glyphicon-info-sign\"></i> <span class=\"text-primary\"> Metratura </span>: " + metratura;
            }

            var htmlprezzo = '';
            if (prezzo != 0) {
                htmlprezzo = "<img src=\"include/css/Utente/euro-icon.png\"> <span class=\"text-primary\"> Prezzo massimo</span>: " + prezzo + " euro";
            }

            var htmltipoAnnuncio = '';
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
                html = "<div><div class=\"panel panel-default\">" + "<div class='panel-heading'>" +
                        htmltipoAnnuncio + "</div>" +
                        "<div class=\"panel-body\" style=\"cursor:pointer\" id=\"filtro-" + idFiltro + "\" OnClick=send_filtro(" + idFiltro + ")>" +
                        "<p> <img src=\"include/css/Utente/Home-30.png\"> <span class=\"text-primary\"> Città </span>: " + citta + "&nbsp;&nbsp;&nbsp;&nbsp;" +
                        htmlprezzo +
                        quartieriHTML +
                        htmlNumeroLocali +
                        htmlNumeroCamere +
                        htmlNumeroBagni +
                        htmlmetratura +
                        htmlCheckBox +
                        "</div>" +
                        "</div>" +
                        //"</div>" +
                        "</div>";


                //HTML per stanza
            } else if (tipoVisualizzazione === 1) {
                html = "<div><div class=\"panel panel-default\">" + "<div class='panel-heading'>" +
                        htmltipoAnnuncio + "</div>" +
                        "<div class=\"panel-body\" style=\"cursor:pointer\" id=\"filtro-" + idFiltro + "\" OnClick=send_filtro(" + idFiltro + ")>" +
                        "<p> <img src=\"include/css/Utente/Home-30.png\"> <span class=\"text-primary\"> Città </span>: " + citta + "&nbsp;&nbsp;&nbsp;&nbsp;" +
                        htmlprezzo +
                        quartieriHTML +
                        "<hr> <p> <i class=\"glyphicon glyphicon-info-sign\"></i> <span class=\"text-primary\">Tipo stanza</span>: " + tipoStanza +
                        htmlCheckBox +
                        "</div>" +
                        "</div>" +
                        //"</div>" +
                        "</div>";
                //HTML Appartamento-Stanza
            } else if (tipoVisualizzazione === 3) {
                html = "<div><div class=\"panel panel-default\">" + "<div class='panel-heading'>" +
                        htmltipoAnnuncio + "</div>" +
                        "<div class=\"panel-body\" style=\"cursor:pointer\" id=\"filtro-" + idFiltro + "\" OnClick=send_filtro(" + idFiltro + ")>" +
                        "<p> <img src=\"include/css/Utente/Home-30.png\"> <span class=\"text-primary\"> Città </span>: " + citta + "&nbsp;&nbsp;&nbsp;&nbsp;" +
                        htmlprezzo +
                        quartieriHTML +
                        htmlCheckBox +
                        "</div>" +
                        "</div>" +
                        //"</div>" +
                        "</div>";

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
                    window.location = url;
                } else
                    alert('ERRORE');
            });
}