/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var segn=new Array();
var annunciSegn_html = new Array();
var annunciSegnArch_html=new Array();

function loadSegnalazioni() {
    console.log("load");
    $.post("ServletController",
            {action: "Segnalazione-getAllSegnalazioni"},
            function (responseJson) {
                console.log(responseJson);
                $.each(responseJson, function (index, risp) {
                    var html = createSegnalazione(risp, index);
                    segn.push(risp);
                    if(risp.Archiviato){
                        annunciSegnArch_html.push(html);
                    }else{
                       annunciSegn_html.push(html); 
                    }
                    $("#segnalazioni").append(html);
                });
            });
}

function createSegnalazione(segn, index) {
    var annuncio = segn.Annuncio;
    var studenti = segn.Studenti;
    var nSegn = segn.NSegn;
    var html = "";
    html += "<div id='segn-" + index + "' class='segnalazione' OnClick=send_Segnalazione(" + index + ") style=\"cursor:pointer\">"; //CONTAINER 
    html += "<div class='panel panel-default'>"; // PANEL
    html += "<div class='panel-heading'>"; // PANEL HEADING
    html += "<h2 class=\"text-muted\"> <span class=\"text-primary\">Annuncio " + annuncio.OID + "</span><span class=\"text-danger n_segn\">"+nSegn+"</span></h2>";
    html += "</div>"; //panel-heading
    html += "<div class='panel-body'>"; // PANEL BODY
    if(annuncio.Oscurato){
        html+="<p><span class=\"text-danger\">Annuncio Oscurato</span></p>";
    }
    html += "<p class=\"text-muted\"><span class=\"text-primary\">Locatore: </span>" + annuncio.Locatore.email ;
    if(annuncio.Locatore.bloccato=="true"){
        html+=" <span class=\"text-danger\">Bloccato</span>";
    }
    html+= "</p>";
    html += "<p class=\"text-muted\"><span class=\"text-primary\">Indirizzo: </span>" + annuncio.Indirizzo + "</p>";
    html += "<p class=\"text-muted\"><span class=\"text-primary\">Descrizione: </span>" + annuncio.Descrizione + "</p>";
    html+=  html_studenti(studenti);
    html += "</div>"; //FINE PANEL BODY
    html += "</div>"; //FINE PANEL
    html += "</div>"; //FINE CONTAINER ANNUNCIO
    /*html += "<p>Annuncio " + (index + 1) + " : " + JSON.stringify(segn.Annuncio) + "</p>";
    html += "<p>Studenti: " + JSON.stringify(segn.Studenti) + "</p>";
    html += "<p>N_Segnalazioni: " + JSON.stringify(segn.NSegn) + "</p>";*/
    return html;
}

function html_studenti(studenti){
    var html="";
    html+="<h3 class=\"text-muted\" id=\"labelStudenti\">Studenti segnalatori</h3>";
    
    $.each(studenti,function(index,segn){
        html+="<p>";
       html+="ID studente: "+segn.Studente.id+"/<span class=\"text-primary\">"+segn.Studente.email+" </span> Data: "+segn.Data+" descrizione: "+segn.Descrizione; 
        html+="</p>";
    });
   
    return html;
}

function send_Segnalazione(index){
    var annuncio = segn[index].Annuncio;
    var url = "/haiHome-war/dettagliAnnuncio.jsp";
    var json = JSON.stringify(annuncio);
    $.session.set('dettagli', json);
    $.session.set('admin',true);
    window.location=url;
}