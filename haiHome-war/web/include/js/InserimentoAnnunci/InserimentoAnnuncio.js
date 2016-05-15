/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */







$(document).ready(function () {




    $("#myPreviewModal").on('hide.bs.modal', function () {
        $("#modalPreviewBody").empty();
    });
    
        $("#messageModal").on('hide.bs.modal', function () {
            var messModal = $(this);
            var modalHeader = messModal.find(".modal-header");
            var modalbody = messModal.find(".modal-body");
            var modalFooter = messModal.find(".modal-footer");
            modalHeader.empty();
            modalbody.empty();
            modalFooter.empty();
    });




    var navListItems = $('div.setup-panel div a'),
            allWells = $('.setup-content'),
            form1 = $('form#form-info-appartamento'),
            form2 = $('form#form-info-annuncio'),
            form3 = $('form#form-info-stanze'),
            form4 = $('form#form-info-costi');


    var dataInizio = $('#inpDataInizio:input');



    dataInizio.datepicker({
        showOn: "both",
        buttonImage: "images/calendario.png",
        buttonImageOnly: true,
        minDate: 0,
        monthNames: ["Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"],
        dayNamesMin: ["Do", "Lu", "Ma", "Me", "Gi", "Ve", "Sa"],
        dateFormat: "dd-mm-yy"
    });



    /*    
     $(function() {
     $('input#inpDataInizio').datetimepicker({
     pickTime: false,
     buttonImage: "",
     buttonImageOnly: true,
     minDate: 0,
     changeMonth : true
     
     });
     });*/



    //prendo il codice del locatore e istanzio l'Annuncio
    sendInitialRequest();




    allWells.hide();

    //codice per lo step-wizard
    navListItems.click(function (e) {
        e.preventDefault();
        var $target = $($(this).attr('href')),
                $item = $(this);
        var itemState = $item.attr("disabled");
        console.log("Id bottoncino di su" + $item.attr("id") + " " + itemState);
        if (itemState !== 'disabled') {
            navListItems.removeClass('btn-primary').addClass('btn-default');
            $item.addClass('btn-primary');
            var idSelectedItem = $item.attr("id");
            navListItems.each(function () {
                var $myitem = $(this);
                var idmyitem = $myitem.attr("id");
                if ((idmyitem > idSelectedItem)) {
                    $myitem.attr('disabled', 'disabled');
                }

            });

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

        if (isValid && checkAddress()) {
            console.log("Dati form validi");
            form1.ajaxSubmit({
                dataType: "text",
                success: function (response) {
                    console.log(response);
                    
                }
            });
        } else {
            console.log("Dati form non validi");
            openModalMessage("Errore nell'Inserimento dati","Controlla che tutti i campi siano inseriti correttamente");
            //alert("NOPE!!");
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
            openModalMessage("Errore nell'Inserimento dati","Controlla che tutti i campi siano inseriti correttamente");
        }


    });



    //form info costi
    form4.on('submit', function (e) {

        e.preventDefault();
        
        var buttonForm = form4.find("button#submitButtom");

        console.log(buttonForm.attr("id"));
        var isValid = validateForm(buttonForm);
        console.log("Validazione form costi " + isValid);
        
        var modelloCosti = form4.find("select#costi");
        
        

        if (isValid && modelloCosti.val()!= 0) {
            
            $("button#submitButtom").hide();
            console.log("Dati form validi");
            form4.ajaxSubmit({
                dataType: "json",
                success: function (response) {
                    console.log(response);

                    var myModal = $("#myPreviewModal");
                    var myModalBody = $("#modalPreviewBody");
                    myModalBody.append(create_Page(response));

                    myModal.modal({keyboard: true});
                    myModal.modal('show');



                    loadAllfoto();



                }
            });
        } else {
            openModalMessage("Errore nell'Inserimento dati","Controlla che tutti i campi siano inseriti correttamente");
        }


    });



});


//Invia richiesta iniziale 
function sendInitialRequest() {


    $.ajax({
        type: "POST",
        url: "ServletController",
        data: "action=Annunci-newAnnuncio-initialRequest",
        dataType: "json",
        success: function (msg)
        {
            if (msg.response === "OK") {
                //alert("Successo");
                console.log("RISPOSTA");
                console.log(msg);
                aggiornaTipoStanze(msg.tipoAffitto, msg.tipoAccessorio);
                //openModalMessage("Locatore loggato","Il locatore è loggato"); 
            } else {
                openModalMessage("Errore di Autemtificazione","Si è verificato un errore di autentificazione"); 
            }
        },
        error: function ()
        {
           openModalMessage("Errore di Autemtificazione","Si è verificato un con il Server"); 

        }
    });

    leggi_quartieri();

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
            codiceStanza = codiceStanza.replace('$__$', idStanza);
            codiceStanza = codiceStanza.replace('&_tipo&', tipoStanza);
            codiceStanza = codiceStanza.replace('&_met&', metraturaStanza);

            //inserisco l'HTML nel DOM
            $(codiceStanza).appendTo("#prezzoStanze");
            console.log("Inserita stanza " + i);
            i++;
        }

    });
    var codiceFinale = "<div><input name='compresoCondominioS' class=\"CompCond\" type=\"checkbox\"  value=\"true\">Compreso Condominio<br><input name='compresoRiscaldamentoS' class=\"CompRisc\" type=\"checkbox\"  value=\"true\">Compreso Riscaldamento</div>";
    $(codiceFinale).appendTo("#prezzoStanze");
}

//utile a validare le form, prende in input il pulsante Next premuto e in base
//      ad esso valida la form relativa
function validateForm(buttonForm) {
    console.log("Controllo Dati " + buttonForm.attr("id"));
    var curStep = buttonForm.closest(".setup-content"),
            curStepBtn = curStep.attr("id"),
            nextStepWizard = $('div.setup-panel div a[href="#' + curStepBtn + '"]').parent().next().children("a"),
            curInputs = curStep.find("input[type='text'],input[type='url'],input[type='number'],input[type='date'],textarea"),
            myDropzone = curStep.find("div.dropzone"),
            isValid = true;
    console.log("Contenitore form " + curStep.attr("id"));

    $(".form-group").removeClass("has-error");
    for (var i = 0; i < curInputs.length; i++) {
        console.log("Oggetto da controllare id: " + curInputs.attr("id"));
        if (!curInputs[i].validity.valid || curInputs[i].validity.typeMismatch) {
            console.log("Dati non Validi");
            isValid = false;
            $(curInputs[i]).closest(".form-group").addClass("has-error");
        }
    }

    //aggiunta controllo dropzone
    console.log("Numero di dropzone " + myDropzone.length);
    for (var i = 0; i < myDropzone.length; i++) {
        console.log("Dropzone da controllare id: " + $(myDropzone).attr("id"));
        if (!$(myDropzone[i]).hasClass("dz-started")) {
            console.log("Dati non Validi");
            isValid = false;
            var message = $(myDropzone[i]).find("div.dz-message");

            message.empty();
            message.css({
                "color": '#FF0000'
            });
            message.append("Inserire o trascinare almeno una foto");

           openModalMessage("Errore nell'inserimento dati","Biogna caricare almeno una foto per Stanza"); 

        
        }
    }
    //fine aggiunta controllo dropzone



    if (isValid) {
        nextStepWizard.removeAttr('disabled').trigger('click');
    }
    return isValid;

}

function rendiAnnuncioPersistente() {

    $.ajax({
        type: "POST",
        url: "ServletController",
        data: "action=Annunci-newAnnuncio-persisti",
        dataType: "text",
        success: function (msg)
        {
            if (msg === "OK") {
                //alert("ANNUNCIO PERSISTITO!!!");
                openModalMessage("Annuncio Persistito","L'annuncio è stato salvato correttamente","<a href=\"index.jsp\" class=\"btn btn-default\" >Go Home</a>");
                
                //$(window.location).attr('href', 'index.jsp');
            } else {
                openModalMessage("Errore: Annuncio Non Persistito","Ci sono stati problemi nel salvataggio dell'annuncio");

            }
        },
        error: function ()
        {
                openModalMessage("Errore: Annuncio Non Persistito","Ci sono state problemi con il Server", "<a href=\"index.jsp\" class=\"btn btn-default\">Go Home</a>");

        }
    });

}

function leggi_quartieri() {
    $.post("ServletController",
            {action: "Annunci-newAnnuncio-getQuartieri"},
            function (responseJson) {
                var html = '';
                $("#selQuartiere").empty();
                $.each(responseJson, function (index, item) {
                    html = '<option id=\"' + item + '\" value=\"' + item + '\">' + item + '</option>';

                    $("#selQuartiere").append(html);
                });

            });

}

function aggiornaListaQuartieri(inputCap) {
    $.post("ServletController",
            {action: "Annunci-newAnnuncio-getQuartieri",
                cap: inputCap},
            function (responseJson) {
                var html = '';
                $("#selQuartiere").empty();
                $.each(responseJson, function (index, item) {
                    html = '<option id=\"' + item + '\" value=\"' + item + '\">' + item + '</option>';
                    $("#selQuartiere").append(html);
                });

            });
}

function openModalMessage(title,body,footer) {

    var messModal = $("#messageModal");
    var modalHeader = messModal.find(".modal-header");
    var modalbody = messModal.find(".modal-body");
    var modalFooter = messModal.find(".modal-footer");

    modalHeader.append("<h4 class=\"modal-title\">" + title + "</h4>");
    modalbody.append("<div>" + body + "</div>");
    if(footer== null){
         modalFooter.append("<button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">Close</button>");

    }else{
            modalFooter.append(footer);
    }


    messModal.modal({keyboard: true});
    messModal.modal('show');

}






