/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

jQuery(document).ready(function ($) {
   var $form = $('#formAnnuncio');
   var $subButton = $('#submitButtom');
   
   $('#submitButtom').on('click', function () {
       
       
        $('#formAnnuncio').ajaxForm(function () {
                    console.log("Sia lodato");
        });
        
       
          /*    
        console.log("E si parte!!!");
        $.post($form.attr("action"), $form.serialize(), function (response) {
        */
         
         
         
            /*
        if (response === "SIAMO DENTRO") {
            // qualcosa...
            alert("Sono dentro");
        } else {
            alert("MERDA");
        }*/
    //});
   });
    
});












var jsonAnn = [];
var jsonStanze = [];
var annuncioObject;


function prova() {

    //annuncioObject = null;
    jsonAnn  = [];
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

    //prova con oggetto
    
    annuncioObject.Città = citt;
    annuncioObject.Quartiere = quart;
    annuncioObject.Indirizzo = ind + " , " + civ;
    
    //fine prova
    
    
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
    
    //prova con oggetto
    
    annuncioObject.Descrizione = descCasa;
    annuncioObject.Metratura = metCasa;
    
    //fine prova

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
    
    // prova oggetti
    console.log("OGGETTO JAVASCRIPT: ");
    
    console.log(annuncioObject);
    
    sendJSON(annuncioObject);

    //fine proava

    //sendJSON(jsonAnn);

}

function createStanzaJSON(atomico) { //da inserire le foto
    console.log("entro 1");
    jsonStanze = [];
    annuncioObject.Stanze = [];
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
            var tipoStanza = $(this).contents().find("#seltipoLetto").val();
            var Metratura = $(this).contents().find("#inpMetratura").val();
            console.log("Stanza : " + idStanza + "| TipoStanza: " + tipoStanza + "| Metratura " + Metratura);
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
                
                //prova con oggetti
                var objStanza;
                
                objStanza.Tipo = tipoStanza;
                objStanza.Metratura = Metratura;
                objStanza.Prezzo = prezzoS;
                objStanza.compresoCondominio = compCond;
                objStanza.compresoRiscaldamento = compRisc;
                
                
                //fine prova
                
                
                
                
                

            } else {

                //creo item
                itemstanza = {
                    "Tipo": tipoStanza,
                    "Metratura": Metratura
                };
                
                //prova con oggetti
                var objStanza;
                
                objStanza.Tipo = tipoStanza;
                objStanza.Metratura = Metratura;
                
                
                //fine prova

            }
        } else {
            console.log("Camera Accessoria " + idStanza);
            var tipoStanza = $(this).contents().find("#seltipoAcc").val();
            var Metratura = $(this).contents().find("#inpMetratura").val();
            console.log("Stanza " + idStanza + "Metratura: " + Metratura + "TipoStanza: " + tipoStanza);

            //creo item
            itemstanza = {
                "Tipo": tipoStanza,
                "Metratura": Metratura
            };
            
                //prova con oggetti
                var objStanza;
                
                objStanza.Tipo = tipoStanza;
                objStanza.Metratura = Metratura;
                
                
                //fine prova

        }

        jsonStanze.push(itemstanza);
        
        //prova oggetti
        annuncioObject.Stanze.push(objStanza);


    });

}

function sendJSON(jsonData) {
    
    console.log("DATI CHE STO INVIANDO");
    console.log(jsonData);
    var json = JSON.stringify(jsonData);
    
    console.log("DATI DOPO LA TRASFORMAZIONE");
    console.log(json);
    
    
    
    

    $.ajax({
        type: 'POST', 
        url: '../FotoUploadServlet',
        dataType: 'json',
        data: {annuncio: json},
        success: function (data) {
            alert('succes');
        },
        error: function (data) {
            alert('fail');
        }
    });

}