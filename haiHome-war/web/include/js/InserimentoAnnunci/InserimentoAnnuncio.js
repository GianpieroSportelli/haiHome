/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var jsonAnn = [];
var jsonStanze = [];

function prova() {

    jsonAnn = [];
    /* controllo se il prezzo è stato inserito per stanze */
    var atomico;
    var value = $("#selStanza option:selected").val();
    console.log(value);
    if (value == 1) {
        console.log("Il Prezzo è riferito all'appartamento");
        atomico = true;
    } else {
        console.log("Il Prezzo è riferito alle singole Stanze");
        atomico = false;
    }


    var citt = $("#selCitta").val();
    var quart = $("#selQuartiere").val();
    var ind = $("#inpIndirizzo").val();
    var civ = $("#inpCivico").val();
    console.log(citt);

    itemCitta = {};
    itemCitta ["Città"] = citt;

    itemQuart = {};
    itemQuart ["Quartiere"] = quart;


    itemIndir = {};

    itemIndir ["Indirizzo"] = ind + " , " + civ;


    jsonAnn.push(itemCitta);
    jsonAnn.push(itemQuart);
    jsonAnn.push(itemIndir);

    var descCasa = $("#textDescrizione").val();
    var metCasa = $("#inpMetratura").val();

    itemDesc = {};
    itemDesc ["Descrizione"] = descCasa;

    itemMetCasa = {};
    itemMetCasa ["Metratura"] = metCasa;

    jsonAnn.push(itemDesc);
    jsonAnn.push(itemMetCasa);

    /* da aggiungere data di inizio affitto */

    /*Inserimento Stanze*/
    createStanzaJSON();

    jsonAnn.push(jsonStanze);


    console.log(jsonAnn);

    sendJSON(jsonAnn);



}

function createStanzaJSON(atomico) { //da inserire le foto
    console.log("entro 1");
    jsonStanze = [];
    //ciclo per ogni stanza
    $(".Stanza").each(function () {

        var jsonStanza = [];

        //prendo l'id(del tag)
        var idStanza = $(this).attr("id");
        console.log("Stanza con id: " + idStanza + " ----------");

        //prendo la tipologia (Stanza da letto 1 - Stanza Accessoria 2)
        var tipologiaStanza = $(this).contents().find("#selStanza").val();

        if (tipologiaStanza == 1) {
            console.log("Camera da letto " + idStanza);
            var tipoStanza = $(this).contents().find("#seltipoStanza").val();
            var Metratura = $(this).contents().find("#inpMetratura").val();
            console.log("Stanza :" + idStanza + "TipoStanza: " + tipoStanza + "Metratura " + Metratura);
            /* controllo se il prezzo è stato inserito per stanze */
            if (!atomico) {
                console.log("Calcolo il prezzo Stanze");
                // <div class=\"form-group\" id=\"prezzo$_$_$\">
                var selStanza = $("#prezzo" + idStanza);
                var prezzoS = selStanza.contents().find(".prezzoStanza").val();
                var compCond = selStanza.contents().find(".CompCond").val();
                var compRisc = selStanza.contents().find(".CompRisc").val();
                console.log("Stanza " + idStanza + "Prezzo: " + prezzoS + "CompresoCondominio: " + compCond + "CompresoRiscaldamento " + compRisc);

                //creo item
                itemstanza = {
                    "Tipo": tipoStanza,
                    "Metratura": Metratura,
                    "Prezzo": prezzoS,
                    "compresoCondominio": compCond,
                    "compresoRiscaldamento": compRisc
                };

            } else {

                //creo item
                itemstanza = {
                    "Tipo": tipoStanza,
                    "Metratura": Metratura
                };

            }
        } else {
            console.log("Camera Accessoria " + idStanza);
            var tipoStanza = $(this).contents().find("#seltipoStanza").val();
            var Metratura = $(this).contents().find("#inpMetratura").val();
            console.log("Stanza " + idStanza + "Metratura: " + Metratura + "TipoStanza: " + tipoStanza);

            //creo item
            itemstanza = {
                "Tipo": tipoStanza,
                "Metratura": Metratura
            };

        }

        jsonStanze.push(itemstanza);








    });

}

function sendJSON(jsonData) {
    $.ajax({
        type: 'get', // it's easier to read GET request parameters
        url: '../FotoUploadServlet',
        dataType: 'JSON',
        data: jsonData,
        success: function (data) {
            alert('succes');
        },
        error: function (data) {
            alert('fail');
        }
    });

}