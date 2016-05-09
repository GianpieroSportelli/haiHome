/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var myAnnuncio;

var tipoLetto;
var tipoAcc;

//Informazioni indirizzo
var pannInd;
var pIndopen = false;

//Informazioni Appartamento
var pannInfo;
var pInfopen = false;

//Informazioni Stanze
var pannStanze;
var pStaopen = false;


//Informazioni prezzo
var pannCosti;
var pCosopen = false;

//gestisco due stadi: ready(false) - edit(true)
var editMode = false;

//bottone di modifica Indirizzo - InfoAppartamento - Prezzo
var editButton;

//bottoni stanze
var editStanza;
var deleteStanza;
var confermaStanza;
var annullaStanza;



$(document).ready(function () {
    pannInd = $("#indirizzoPanel");
    pannInfo = $("#infoPanel");
    pannStanze = $("#stanzePanel");
    pannCosti = $("#costiPanel");

    editButton = $(".editButton");

    editStanza = $(".editStanza");
    deleteStanza = $(".deleteStanza");
    confermaStanza = $(".confermaStanza");
    annullaStanza = $(".annullaStanza");

    //funzioni bottoni

    //bottone modifica
    editButton.on("click", function () {
        var panel = $(this).closest("div.panel");
        var idpanel = panel.attr("id");

        if (!editMode) {
            editMode = true;

            if (idpanel == pannInd.attr("id")) {
                //info indirizzo
                abilitaInsetimentoDati(pannInd);
                showFooterButton(pannInd);

            } else if (idpanel == pannInfo.attr("id")) {
                //info appartamento
                abilitaInsetimentoDati(pannInfo);
                dataInizio.datepicker("option", "showOn", "both");
                showFooterButton(pannInfo);

            } else if (idpanel == pannStanze.attr("id")) {
                //info stanze



            } else if (idpanel == pannCosti.attr("id")) {

            } else {

            }
        }




    });



    //bottone conferma
    $(document).on('click', 'a.edit-save', function (event) {
        var panel = $(this).closest("div.panel");
        var idpanel = panel.attr("id");

        if (idpanel == pannInd.attr("id")) {

            if (checkIndirizzo()) {
                alert("Indirizzo Corretto");
                sendEditIndirizzoRequest();
                disabilitaInserimentoDati(pannInd);
                hideFooterButton(pannInd);
            } else {
                alert("Indirizzo Non Corretto");
            }



        } else if (idpanel == pannInfo.attr("id")) {

            if (checkInfoApp()) {
                alert("Dati Corretti");
                sendEditAppartamentoRequest();
                disabilitaInserimentoDati(pannInfo);
                hideFooterButton(pannInfo);
            } else {
                alert("Dati Non Corretti");
            }

        } else if (idpanel == pannStanze.attr("id")) {

        } else if (idpanel == pannCosti.attr("id")) {

        } else {

        }
        editMode = false;
    });

    //bottone annulla 
    $(document).on('click', 'a.edit-cancel', function (event) {
        var panel = $(this).closest("div.panel");
        var idpanel = panel.attr("id");


        if (idpanel == pannInd.attr("id")) {
            aggiornaIndForm(myAnnuncio);
            disabilitaInserimentoDati(pannInd);
            hideFooterButton(pannInd);

        } else if (idpanel == pannInfo.attr("id")) {
            aggiornaAptForm(myAnnuncio);
            disabilitaInserimentoDati(pannInfo);
            hideFooterButton(pannInfo);

        } else if (idpanel == pannStanze.attr("id")) {

        } else if (idpanel == pannCosti.attr("id")) {

        } else {

        }
        editMode = false;
    });


    //BOTTONI STANZE

    //bottone modifica Stanza
    editStanza.on("click", function () {

        var navContent = pannStanze.find(".tab-content");
        var stanzaContent = navContent.find("div.active");

        var tabStanze = pannStanze.find(".nav-tabs");

        //disattivo tutti i tab
        tabStanze.find("a").each(function (index) {
            var href = $(this).attr("href");
            if (href != stanzaContent.attr("id")) {
                $(this).addClass("not-active");
            }

        });
        //abilito inserimento
        abilitaInsetimentoDati(stanzaContent);
        abilitaDropzone(stanzaContent);

        //nascondo bottoni 1
        editStanza.parent("div").hide();

        //mostro bottoni 2 
        confermaStanza.parent("div").show();

    });

    //bottone conferma Stanza
    confermaStanza.on("click", function () {

        var navContent = pannStanze.find(".tab-content");
        var stanzaContent = navContent.find("div.active");
        modificaStanza(stanzaContent);





        //disabilito l'inserimento
        disabilitaInserimentoDati(stanzaContent);
        disabilitaDropzone(stanzaContent);

        //attivo tutti i tab
        var tabStanze = pannStanze.find(".nav-tabs");

        tabStanze.find("a").each(function (index) {
            var href = $(this).attr("href");
            if (href != stanzaContent.attr("id")) {
                $(this).removeClass("not-active");
            }

        });

        //nascondo bottoni 2
        confermaStanza.parent("div").hide();

        //mostro bottoni 1 
        editStanza.parent("div").show();

    });

    //bottone annulla stanza
    annullaStanza.on("click", function () {


        var navContent = pannStanze.find(".tab-content");
        var stanzaContent = navContent.find("div.active");

        var idStanza = stanzaContent.attr("id").slice(6);

        var stanze = myAnnuncio.Stanze[0];
        var s = stanze[idStanza];


        resetStanza(s, idStanza);





        //disabilito l'inserimento
        disabilitaInserimentoDati(stanzaContent);
        disabilitaDropzone(stanzaContent);



        //attivo tutti i tab
        var tabStanze = pannStanze.find(".nav-tabs");

        tabStanze.find("a").each(function (index) {
            var href = $(this).attr("href");
            if (href != stanzaContent.attr("id")) {
                $(this).removeClass("not-active");
            }

        });

        //nascondo bottoni 2
        confermaStanza.parent("div").hide();

        //mostro bottoni 1 
        editStanza.parent("div").show();

    });



    //CONTROLLO CLICK PANNELLI

    //pannello Indirizzo
    pannInd.find("a.openlink").on("click", function () {
        console.log(pIndopen);
        pIndopen = !pIndopen;
        if (pIndopen) {
            $(this).siblings(".editButton").show();
        } else {
            $(this).siblings(".editButton").hide();
        }


    });

    //pannelo info appartamento
    pannInfo.find("a.openlink").on("click", function () {
        console.log(pInfopen);
        pInfopen = !pInfopen;
        if (pInfopen) {
            $(this).siblings(".editButton").show();
        } else {
            $(this).siblings(".editButton").hide();
        }


    });


    //pannelo info stanze
    pannStanze.find("a.openlink").on("click", function () {
        console.log(pStaopen);
        pStaopen = !pStaopen;
        if (pStaopen) {
            $(this).siblings(".editButton").show();
        } else {
            $(this).siblings(".editButton").hide();
        }


    });



    //UTILY PAGINA

    //Ddatapicker
    var dataInizio = pannInfo.find("input#inpDataInizio");
    dataInizio.datepicker({
        showOn: "focus",
        buttonImage: "images/calendario.png",
        buttonImageOnly: true,
        //minDate: 0,
        monthNames: ["Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"],
        dayNamesMin: ["Do", "Lu", "Ma", "Me", "Gi", "Ve", "Sa"],
        dateFormat: "dd-mm-yy"
    });






    initalEditFunction();
});


//richiesta iniziale
function initalEditFunction() {

    var oidTag = $("input#OIDAnnuncio");

    console.log(oidTag.attr("value"));
    var oid = oidTag.attr("value");

    console.log("INVIO RICHIEDA INIZIALE DI MODIFICA");

    $.post("ServletAnnuncio",
            {action: "Annunci-editAnnuncio-initialRequest",
                idAnnuncio: oid},
            function (msg) {
                var esito = msg.response;
                if (esito == "OK") {
                    var annuncio = msg.data;
                    console.log(annuncio);
                    //leggo la risposta
                    myAnnuncio = annuncio;
                    tipoLetto = msg.tipoAffitto;
                    tipoAcc = msg.tipoAccessorio;
                    setTipoCamere(tipoLetto, tipoAcc);

                    //riempio i campi
                    aggiornaIndForm(myAnnuncio);
                    aggiornaAptForm(myAnnuncio);
                    aggiornaStanzeForm(myAnnuncio);





                } else {
                    console.log("La servlet non ha risposto OK");
                }
            });
}

//Aggiorna Dati Indirizzo
function aggiornaIndForm(ann) {
    var indirizzoTag = pannInd.find("#inpIndirizzo");
    var civicoTag = pannInd.find("#inpCivico");
    var quartTag = pannInd.find("#selQuartiere");



    indirizzoTag.val(ann.Indirizzo.split(", ")[0]);
    civicoTag.val(ann.Indirizzo.split(", ")[1]);

    var quart = '<option id=\"' + ann.Quartiere + '\" value=\"' + ann.Quartiere + '\">' + ann.Quartiere + '</option>';
    quartTag.append(quart);

}

//Aggiorna Dati Info Appartamento
function aggiornaAptForm(ann) {
    var descrizioneTag = pannInfo.find("#textDescrizione");
    var metraturaTag = pannInfo.find("#inpMetratura");
    var dataInizio = pannInfo.find("input#inpDataInizio");
    var arredTag = pannInfo.find("#inpArredato");

    if (ann.Arredato) {
        $(arredTag).attr('checked', true);
    } else {
        $(arredTag).attr('checked', false);
    }

    descrizioneTag.val(ann.Descrizione);
    metraturaTag.val(ann.Metratura);

    var d = myAnnuncio.DataInizioAffitto.split("/");
    var data = new Date();
    data.setFullYear(d[2]);
    data.setMonth(d[1] - 1);
    data.setDate(d[0]);
    console.log(data);
    dataInizio.datepicker("setDate", data);


}

//Aggiorna form Stanze
function aggiornaStanzeForm(ann) {
    var stanze = ann.Stanze[0];
    for (var i = 0; i < stanze.length; i++) {
        var s = stanze[i];
        aggiungiStanza(s, i);
        console.log(s);


    }
}

//Abilita inserimento dati (generico)
function abilitaInsetimentoDati(pannello) {
    var curInputs = pannello.find("input[type='text'],input[type='url'],input[type='number'],input[type='checkbox'],select,textarea");
    for (var i = 0; i < curInputs.length; i++) {
        console.log("input n " + i + " id: " + $(curInputs[i]).attr("id"));
        $(curInputs[i]).removeAttr("disabled");
    }

}

//Disabilita inserimento dati (generico)
function disabilitaInserimentoDati(pannello) {
    var curInputs = pannello.find("input[type='text'],input[type='url'],input[type='number'],input[type='checkbox'],select,textarea");
    for (var i = 0; i < curInputs.length; i++) {
        console.log("input n " + i + " id: " + $(curInputs[i]).attr("id"));
        $(curInputs[i]).attr("disabled", "disabled");
    }
    var dataInizio = pannInfo.find("input#inpDataInizio");
    dataInizio.datepicker("option", "showOn", "focus");
}

//Visualizza i bottoni nel footer (generico)
function showFooterButton(pannello) {
    var footer = pannello.find("div.panel-footer");

    var confirmButton = "<a class=\"cancel-edit edit-cancel\" ><span class=\"glyphicon glyphicon-remove\" ></span>Annulla</a>";
    var cancelButton = "<a class=\"save-edit edit-save\" ><span class=\"glyphicon glyphicon-saved\" ></span>Salva</a>";
    //var confirmButton = "<button type=\"button\" class=\"btn btn-success edit-conf\">Conferma</button>";
    //var cancelButton = "<button type=\"button\" class=\"btn btn-danger edit-cancel\">Annulla</button>";
    $(footer).append("<div class=\"row\">" + cancelButton + confirmButton + "</div>");

}

//Nasconde i bottoni nel footer (generico)
function hideFooterButton(pannello) {
    var footer = pannello.find("div.panel-footer");
    footer.empty();
}

//RICHIESTE DI MODIFICA

//Richiesta modificia dati info Appartamento
function sendEditAppartamentoRequest() {
    console.log("INVIO RICHIEDA MODIFICA INFO APPARTAMENTO");

    $("form#form-info-appartamento").ajaxSubmit({
        dataType: "json",
        success: function (response) {
            console.log(response.response);
            console.log(response.data);
        }
    });
}


//FUNZIONI DI VERIFICA DATI

//Verifica dati info Appartamento
function checkInfoApp() {

    var descrizioneTag = pannInfo.find("#textDescrizione");
    //var metraturaTag = pannInfo.find("#inpMetratura");
    var dataInizio = pannInfo.find("input#inpDataInizio");

    return descrizioneTag.val() != "" && dataInizio.val() != "";

}





