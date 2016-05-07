/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var segn = new Array();
var annunciSegn_html = new Array();
var annunciSegnArch_html = new Array();
var actual = 0;
var actual_arch = 0;
var STEP = 5;

var f_segn = false;
var f_segn_arch = false;

function loadSegnalazioni() {
    //console.log("load");
    segn = new Array();
    annunciSegnArch_html = new Array();
    annunciSegn_html = new Array();
    $("#segnalazioni").empty();
    $("#segnalazioni-archiviate").empty();

    $.post("ServletController",
            {action: "Segnalazione-getAllSegnalazioni"},
            function (responseJson) {
                console.log(responseJson);
                $.each(responseJson, function (index, risp) {
                    var html = createSegnalazione(risp, index);
                    segn.push(risp);
                    if (risp.Archiviato) {
                        annunciSegnArch_html.push(html);
                    } else {
                        annunciSegn_html.push(html);
                    }
                });
                top();
                top_archiviate();
                //console.log(segn);
            });
}

function createSegnalazione(segn, index) {
    var annuncio = segn.Annuncio;
    var studenti = segn.Studenti;
    var nSegn = segn.NSegn;
    var html = "";
    html += "<div id='segn-" + index + "' class='segnalazione'>"; //CONTAINER 
    html += "<div class='panel panel-default'>"; // PANEL
    html += "<div class='panel-heading'>"; // PANEL HEADING
    //html += "<div class='dropdown link-annuncio'>"; //DROPZONE - HEADER
    var arch = "<div class='dropdown link-archivia'><a class='btn btn-link dropdown-toggle' type='button' data-toggle='dropdown'>";
    arch += "<span class='glyphicon glyphicon-menu-down'></span>";
    arch += "</a>";
    arch += "<ul class='dropdown-menu'>"; //INIZIO DROPZONE - OPZIONI
    if (!segn.Archiviato) {
        arch += "<li style=\"cursor:pointer\"><a id='archivia-" + index + "' onclick=\"archivia_segnalazione(" + index + ")\">Archivia</a></li>";
    } else {
        arch += "<li style=\"cursor:pointer\"><a id='disarchivia-" + index + "' onclick=\"disarchivia_segnalazione(" + index + ")\">Disarchivia</a></li>";
    }
    arch += "</ul></div>";
    //    html += "</div>"; //FINE DROPZONE
    html += "<h2 class=\"text-muted\"> <span class=\"text-primary\">Annuncio " + annuncio.OID + "</span><span class=\"text-danger n_segn\">#Segnalazioni: " + nSegn + "</span>" + arch + "</h2>";
    html += "</div>"; //panel-heading
    html += "<div class='panel-body' OnClick=send_Segnalazione(" + index + ") style=\"cursor:pointer\">"; // PANEL BODY
    if (annuncio.Oscurato) {
        html += "<p><span class=\"text-danger\">Annuncio Oscurato</span></p>";
    }
    html += "<p class=\"text-muted\"><span class=\"text-primary\">Locatore: </span>" + annuncio.Locatore.email;
    if (annuncio.Locatore.bloccato == "true") {
        html += " <span class=\"text-danger\">Bloccato</span>";
    }
    html += "</p>";
    html += "<p class=\"text-muted\"><span class=\"text-primary\">Indirizzo: </span>" + annuncio.Indirizzo + "</p>";
    html += "<p class=\"text-muted\"><span class=\"text-primary\">Descrizione: </span>" + annuncio.Descrizione + "</p>";
    html += html_studenti(studenti);
    html += "</div>"; //FINE PANEL BODY
    html += "</div>"; //FINE PANEL
    html += "</div>"; //FINE CONTAINER ANNUNCIO
    /*html += "<p>Annuncio " + (index + 1) + " : " + JSON.stringify(segn.Annuncio) + "</p>";
     html += "<p>Studenti: " + JSON.stringify(segn.Studenti) + "</p>";
     html += "<p>N_Segnalazioni: " + JSON.stringify(segn.NSegn) + "</p>";*/
    return html;
}

function html_studenti(studenti) {
    var html = "";
    html += "<h3 class=\"text-muted\" id=\"labelStudenti\">Studenti segnalatori</h3>";

    $.each(studenti, function (index, segn) {
        html += "<p>";
        html += "ID studente: " + segn.Studente.id + "/<span class=\"text-primary\">" + segn.Studente.email + " </span> Data: " + segn.Data + " descrizione: " + segn.Descrizione;
        html += "</p>";
    });

    return html;
}

function send_Segnalazione(index) {
    var annuncio = segn[index].Annuncio;
    var url = "/haiHome-war/dettagliAnnuncio.jsp";
    var json = JSON.stringify(annuncio);
    $.session.set('dettagli', json);
    $.session.set('admin', true);
    window.location = url;
}

$(window).scroll(function () {
    if (($(window).scrollTop() + 200) >= $(document).height() - $(window).height()) {
        if (f_segn) {
            nextpage();
        }
        if (f_segn_arch) {
            nextpage_arch();
        }
    }
});

function nextpage() {
    if (actual < annunciSegn_html.lenght) {
        var alt = actual + STEP;
        for (var i = actual; i < alt && i < annunciSegn_html.length; i++) {
            $("#segnalazioni").append(annunciSegn_html[i]);
            actual = i;
        }
    }
}

function nextpage_arch() {
    if (actual < annunciSegnArch_html.lenght) {
        var alt = actual_arch + STEP;
        for (var i = actual_arch; i < alt && i < annunciSegnArch_html.length; i++) {
            $("#segnalazioni").append(annunciSegnArch_html[i]);
            actual_arch = i;
        }
    }
}

function top() {
    //console.log("TOP");
    if(annunciSegn_html.length==0){
        $("#segnalazioni").append("<p>Non ci sono segnalazioni in sospeso!!!</p>");
    }
    for (var i = 0; i < STEP && i < annunciSegn_html.length; i++) {
        $("#segnalazioni").append(annunciSegn_html[i]);
        actual = i;
    }
}

function top_archiviate() {
    //console.log("TOP");
    if(annunciSegnArch_html.length==0){
        $("#segnalazioni-archiviate").append("<p>Non ci sono segnalazioni Archiviate!!!</p>");
    }
    for (var i = 0; i < STEP && i < annunciSegnArch_html.length; i++) {
        $("#segnalazioni-archiviate").append(annunciSegnArch_html[i]);
        actual_arch = i;
    }
}

function no_segn() {
    //console.log("no_segn");
    f_segn = false;
    f_segn_arch = false;
}

function segnalazioni() {
    //console.log("segn");
    f_segn = true;
    f_segn_arch = false;
}

function segnalazioni_arch() {
    //console.log("segn_arch");
    f_segn = false;
    f_segn_arch = true;
}

function archivia_segnalazione(index) {
    //console.log(segn[index]);
    $.ajax({
        url: "ServletController",
        type: 'post',
        dataType: 'json',
        data: {action: "Segnalazione-archivia", segnalazione: JSON.stringify(segn[index]), status: true},
        success: function (response) {
            //console.log(response);
            if (response == true) {
                alert("Archiviazione segnalazioni andata a buon fine!");
                loadSegnalazioni();
            }else{
                alert("Errore in archiviazione segnalazioni!");
            }
        }
    });
}

function disarchivia_segnalazione(index) {
    
    //console.log(segn[index]);
    $.ajax({
        url: "ServletController",
        type: 'post',
        dataType: 'json',
        data: {action: "Segnalazione-archivia", segnalazione: JSON.stringify(segn[index]), status: false},
        success: function (response) {
            //console.log(response);
            if (response == true) {
                alert("Disarchiviazione segnalazioni andata a buon fine!");
                loadSegnalazioni();
            }else{
                alert("Errore in disarchiviazione segnalazioni!");
            }
        }
    });
}