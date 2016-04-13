/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {


    
    var navListItems = $('div.setup-panel div a'),
            allWells = $('.setup-content'),
            form1 = $('form#form-info-appartamento'),
            form2 = $('form#form-info-annuncio'),
            form3 = $('form#form-info-stanze'),
            form4 = $('form#form-info-costi');
    

    //prendo il codice del locatore e istanzio l'Annuncio
    sendInitialRequest();


    allWells.hide();

    //codice per lo step-wizard
    navListItems.click(function (e) {
        e.preventDefault();
        var $target = $($(this).attr('href')),
                $item = $(this);

        if (!$item.hasClass('disabled')) {
            navListItems.removeClass('btn-primary').addClass('btn-default');
            $item.addClass('btn-primary');
            allWells.hide();
            $target.show();
            $target.find('input:eq(0)').focus();
        }
    });


    jQuery('div.setup-panel div a.btn-primary').trigger('click');

    //codice submit prima form
    form1.on('submit', function (e) {
        //alert("Entro nella submit ");
        e.preventDefault();
        var buttonForm = form1.find("button#butt1");

        var isValid = validateForm(buttonForm);

        if (isValid) {
            console.log("Dati form validi");
            form1.ajaxSubmit({
                dataType: "text",
                success: function (response) {
                    console.log(response);
                }
            });
        } else {
            console.log("Dati form non validi");
        }


    });


    //codice submit seconda form
    form2.on('submit', function (e) {
        //alert("Entro nella submit");
        e.preventDefault();

        var buttonForm = form2.find("button#butt2");

        console.log(buttonForm.attr("id"));
        var isValid = validateForm(buttonForm);

        if (isValid) {
            console.log("Dati form validi");
            form2.ajaxSubmit({
                dataType: "text",
                success: function (response) {
                    console.log(response);
                }
            });
        } else {
            console.log("Dati form non validi");
        }


    });

    
    
        //form info costi
        form4.on('submit', function (e) {

        e.preventDefault();

        var buttonForm = form4.find("button#butt3");

        console.log(buttonForm.attr("id"));
        var isValid = validateForm(buttonForm);

        if (isValid) {
            console.log("Dati form validi");
            form4.ajaxSubmit({
                dataType: "text",
                success: function (response) {
                    generateCostiForm();
                    console.log(response);
                }
            });
        } else {
            console.log("Dati form non validi");
        }


    });
   


});



//Invia richiesta iniziale 
function sendInitialRequest(){
    var IDLocatore ="101";
    
    $.ajax({
      type: "POST",
      url: "../ServletAnnuncio",
      data: "action=Annunci-newAnnuncio-initialRequest&idLocatore=" + IDLocatore,
      dataType: "text",
      success: function(msg)
      {
        alert("Successo");
      },
      error: function()
      {
        alert("Chiamata fallita, si prega di riprovare...");
      }
    });
    
}



//genera l'ultima pagine dello step-wizard in base alle stanze inserite
function generateCostiForm() {

    $("#prezzoStanze").empty(); //svuoto tutte le form per le stanze (niente duplicati)
    console.log("Inserisco codice delle stanze");
    var i = 1;
    //per ogni stanza che ho inserito
    $(".Stanza").each(function () {

        //mi prendo la tipologia della stanza (1 - Letto | 2 - Accessoria)
        var tipologiaStanza = $(this).contents().find("#selStanza").val();
        /*controllo se è una camera da letto e quindi genero la form per il prezzo */
        if (tipologiaStanza == 1) {
            //mi prendo un po di informazioni della stanza
            var tipoStanza = $(this).contents().find("#seltipoLetto option:selected").val();
            var metraturaStanza = $(this).contents().find("#inpMetratura").val();
            var idStanza = $(this).attr("id");
            //inserisco le informazioni in delle label
            var PrezzoStanzaHTML = getPrezzoStanzaHTMLCode();
            var codiceStanza = PrezzoStanzaHTML.replace('&_&', i);
            codiceStanza = codiceStanza.replace('$_$_$', idStanza);
            codiceStanza = codiceStanza.replace('&_tipo&', tipoStanza);
            codiceStanza = codiceStanza.replace('&_met&', metraturaStanza);

            //inserisco l'HTML nel DOM
            $(codiceStanza).appendTo("#prezzoStanze");
            console.log("Inserita stanza " + i);
            i++;
        }

    });
}

//utile a validare le form, prende in input il pulsante Next premuto e in base
//      ad esso valida la form relativa
function validateForm(buttonForm) {
    console.log("Controllo Dati " + buttonForm.attr("id"));
    var curStep = buttonForm.closest(".setup-content"),
            curStepBtn = curStep.attr("id"),
            nextStepWizard = $('div.setup-panel div a[href="#' + curStepBtn + '"]').parent().next().children("a"),
            curInputs = curStep.find("input[type='text'],input[type='url'],textarea"),
            isValid = true;
    console.log("Contenitore form " + curStep.attr("id"));

    $(".form-group").removeClass("has-error");
    for (var i = 0; i < curInputs.length; i++) {
        if (!curInputs[i].validity.valid) {
            console.log("Dati non Validi");
            isValid = false;
            $(curInputs[i]).closest(".form-group").addClass("has-error");
        }
    }

    if (isValid) {
        nextStepWizard.removeAttr('disabled').trigger('click');
    }
    return isValid;

}


