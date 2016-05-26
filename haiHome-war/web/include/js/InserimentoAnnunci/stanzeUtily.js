/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


// variabili globali

var numStanze = 0;
var contatoreStanze = 0;

var tipologiaLetto;
var tipologiaStanze;

var PrezzoStanzaHTML2 = "\
        <div class=\"form-group col-md-12 prezzoStanzaCont\" id=\"prezzo$_$_$\">\n\
            <div class=\"col-md-6\">\n\
                <input type=\"hidden\" name=\"idStanza\" value=\"$__$\" />\n\
                <label class=\"control-label\">Tipo: </label> <label class=\"control-label\">&_tipo&</label><br />\n\
                <label class=\"control-label\">Metratura: </label> <label class=\"control-label\">&_met&</label><br />\n\
            </div>\n\
            <div class=\"col-md-6\">\n\
                <label class=\"control-label\">Prezzo Stanza &_&</label>\n\
                <input id=\"prezzoS\" name='PrezzoS' maxlength=\"100\" class=\"form-control prezzoStanza\" /><br />\n\
            </div>\n\
        </div>";

var PrezzoStanzaHTML = "\
        <div class=\"form-group col-md-8 col-md-offset-2\" id=\"prezzo$_$_$\">\n\
            <input type=\"hidden\" name=\"idStanza\" value=\"$__$\" />\n\
            <div class=\"panel panel-info col-md-12\">\n\
                <div class=\"panel-heading row\">\n\
                    <div class='col-md-5'>\n\
                        <label class=\"control-label\">Tipo: </label> <label class=\"control-label\"> &_tipo&</label>\n\
                    </div>\n\
                    <div class='col-md-2'></div>\n\
                    <div class='col-md-5'>\n\
                        <label class=\"control-label\">Metratura: </label><label class=\"control-label\">&_met&</label>\n\
                    </div>\n\
                </div>\n\
                <div class=\"panel-body\">\n\
                    <label class=\"control-label\">Prezzo Stanza &_&</label>\n\
                    <input id=\"prezzoS\" name='PrezzoS' type='number' maxlength=\"100\" class=\"form-control prezzoStanza\" />\n\
                </div>\n\
            </div>\n\
        </div>";

var dropzones = new Array();

//var stanzeForms = new Array();
var dropzoneMaps = new Map();

//aggiunge un elemento all'arrey delle dropzone
function addDropzoneStanza(dz, i) {
    dropzones[i - 1] = dz;
    var key = "Stanza" + i;
    dropzoneMaps.set(key, dz);
}

//restituisce il temlate HTML del prezzo stanza
function getPrezzoStanzaHTMLCode() {
    return PrezzoStanzaHTML;
}

function aggiornaTipoStanze(tl, ta){
    tipologiaLetto = tl;
    tipologiaStanze = ta;
    console.log(tipologiaLetto.length);
    console.log(tipologiaStanze.length);
}


var submitButton2 = document.querySelector("button#buttStanze");

var submitFormButton = document.querySelector("button#buttStanze2");



function nuovaStanza1() {
    numStanze++;
    var stanzaCode = getStanzaHTMLCode(numStanze, tipologiaLetto,tipologiaStanze);
    $(stanzaCode).appendTo("#contenitoreStanze");
    var myDropzone = new Dropzone("div#mydropzone" + numStanze,
            {
                // Prevents Dropzone from uploading dropped files immediately
                autoProcessQueue: false,
                url: "ServletController",
                parallelUploads: 100,
                uploadMultiple: true,
                paramName: "file[]",
                addRemoveLinks: true,
                previewTemplate: "<div class=\"dz-preview dz-file-preview\">\n  <div class=\"dz-image\"><img data-dz-thumbnail /></div>\n</div>"});
    addDropzoneStanza(myDropzone, numStanze);

    contatoreStanze++;

}




//validazione form stanze
function validateFormStanze(butt) {

    var curStep = butt.closest(".setup-content");
    var curStepBtn = curStep.attr("id");
    var nextStepWizard = $('div.setup-panel div a[href="#' + curStepBtn + '"]').parent().next().children("a"),
            isValid = true;
    if (isValid) {
        nextStepWizard.removeAttr('disabled').trigger('click');
    }
    return isValid;
}





//elimina una stanza
function eliminaStanza(num) {
    $("div#Stanza" + num).remove();
    dropzones[num - 1] = null;
    var key = "Stanza" + num;
    dropzoneMaps.delete(key);
    contatoreStanze--;
}


//permette di cambiare specifiche sul prezzo (Prezzo intero Appartamento - Prezzo singole Stanze)
function cambiaSpecifiche() {
    console.log("--------------------------------------");
    console.log("Cambio Specifiche Prezzo");
    $("button#submitButtom").show();
    $("option#nothing").remove();
    var value = $("#costi option:selected").val();
    var prezzoStanze = $("div#prezzoStanze");
    var prezzoAppartamento = $("div#prezzoAppartamento");

    console.log(value);
    if (value == 1) {
        console.log("Prezzo Appartamento");
        prezzoStanze.hide();
        prezzoAppartamento.show();

        $("input#prezzoA").attr("required", "required");
        $("input#prezzoA").attr("type", "number");

        var numericInput = prezzoStanze.find("input.prezzoStanza");

        for (var i = 0; i < numericInput.length; i++) {
            console.log("Id input " + $(numericInput[i]).attr("id"));
            $(numericInput[i]).removeAttr("required");
            $(numericInput[i]).removeAttr("type");
        }

    } else {
        console.log("Prezzo Stanze");
        prezzoAppartamento.hide();
        prezzoStanze.show();

        $("input#prezzoA").removeAttr("required");
        $("input#prezzoA").removeAttr("type");
        var numericInput = prezzoStanze.find("input.prezzoStanza");

        for (var i = 0; i < numericInput.length; i++) {
            $(numericInput[i]).attr("required", "required");
            $(numericInput[i]).attr("type", "number");
        }
    }
}

//permette di cambiare le specifiche relative alla stanza (Stanza Accessoria - Stanza da Letto)
function cambiaSpecificheTipologiaStanza(id) {
    console.log("--------------------------------------");
    console.log("Cambio specifiche Tipologia Stanza");
    console.log("ID Stanza " + id);
    //prendo la stanza
    var stanza = $("#" + id);
    //leggo il contenuto della select Tipologia Stanza
    var value = stanza.contents().find("#selStanza option:selected").val();
    //se è una stanza da letto
    if (value == 1) {
        console.log("stanza da letto " + value);
        stanza.contents().find("select#seltipoLetto").show();
        stanza.contents().find("select#seltipoAcc").hide();
    }
//se è una stanza accessoria
    else {
        console.log("stanza accessoria");
        stanza.contents().find("select#seltipoLetto").hide();
        stanza.contents().find("select#seltipoAcc").show();
    }

}

/* da permettere la modifica del tipo stanza in seguito alla selezione (vecchio) DA ELIMINARE
function cambiaCameraSpecifiche() {
    console.log("random");
    var value = $("#selStanza option:selected").val();
    console.log(value);
    if (value == 1) {
        console.log("ciao");
        $("div#prezzoStanze").hide();
        $("div#prezzoAppartamento").show();
    } else {
        console.log("ciao");
        $("div#prezzoAppartamento").hide();
        $("div#prezzoStanze").show();
    }
}*/

//            <div id=\"aggiungia \" class=\"btn buttonElimina\" onclick=\"eliminaStanza('" + number + "')\">-</div><br />\n\

//                 <a onclick=\"eliminaStanza('" + number + "')\" >Elimina Stanza<span class=\"glyphicon glyphicon-remove-circle\"></span></a>\n\

//restituisce il codice HTML della stanza
function getStanzaHTMLCode2(number, tipoLetto, tipAcc) {
    
    // style=\"background:yellow \"
         var StanzaCode =
   "<div id=\"Stanza" + number + "\" class=\"col-md-8 col-md-offset-2 cont-stanza Stanza \" >\n\
            <input type=\"hidden\" name=\"numStanza\" value=\"Stanza" + number + "\" /> \n\
            <div class=\"row\">\n\
                <div class=\"btn btn-danger col-md-1 pull-right\" onclick=\"eliminaStanza('" + number + "')\">\n\
                    <span class=\"glyphicon glyphicon-minus\"></span>\n\
                </div>\n\
            </div>\n\
            <div class=\"col-md-12 row\">\n\
                <div class=\"col-md-6 formStanzaElement \">\n\
                    <label class=\"control-label\">Stanza</label>\n\
                    <select name='TipologiaStanza' class=\"form-control\" id=\"selStanza\" onchange=\"cambiaSpecificheTipologiaStanza('Stanza" + number + "')\">\n\
                        <option value=\"1\">Stanza da Letto</option>\n\
                        <option value=\"2\">Stanza Accessoria</option>\n\
                    </select>\n\
                </div>\n\
                <div class=\"col-md-6 formStanzaElement\">\n\
                    <label class=\"control-label\">Tipo di Stanza</label>\n\ ";
    
    console.log(tipoLetto);
    StanzaCode = StanzaCode + "<select name='TipoL' class=\"form-control\" id=\"seltipoLetto\"  >\n\ ";
    for(var i =0;i<tipoLetto.length;i++){
        StanzaCode = StanzaCode + "<option value=\"" +tipoLetto[i] + "\">" +tipoLetto[i] + "</option>\n\ ";
    }  
    StanzaCode = StanzaCode + "</select>\n\ ";
    console.log(tipAcc);
        StanzaCode = StanzaCode + "<select name='TipoA' class=\"form-control\" id=\"seltipoAcc\" style=\"display:none\" >\n\ ";
    for(var i =0;i<tipAcc.length;i++){
        StanzaCode = StanzaCode + "<option value=\"" +tipAcc[i] + "\">" +tipAcc[i] + "</option>\n\ ";
    }
        StanzaCode = StanzaCode + "</select>\n\ ";
    


 StanzaCode = StanzaCode + "\
                </div>\n\
            </div>\n\
            <div class=\"col-md-6 formStanzaElement form-group\">\n\
                <label class=\"control-label\">Metratura</label> \n\
                <input name='MetraturaS' type='number' id=\"inpMetratura\"  class=\"form-control\" placeholder=\"Metratura\" />\n\
            </div>\n\
            <div class=\"col-md-12 formStanzaElement\">\n\
                <label class=\"control-label\">Foto</label>\n\
                <div>\n\
                    <div  id=\"mydropzone" + number + "\"  class=\"dropzone needsclick dz-clickable\">\n\
                        <div class=\"dz-message needsclick\">Drop files here or click to upload.<br>\n\
                        </div>\n\
                    </div>\n\
                </div>\n\
            </div>";
    
    
    return StanzaCode;
}

//restituisce il codice HTML della stanza PROVAAA
function getStanzaHTMLCode(number, tipoLetto, tipAcc) {
    
    // style=\"background:yellow \"
         var StanzaCode =
   "<div id=\"Stanza" + number + "\" class=\"col-md-8 col-md-offset-2 Stanza \" >\n\
        <input type=\"hidden\" name=\"numStanza\" value=\"Stanza" + number + "\" /> \n\
        <div class='panel panel-primary'>\n\
            <div class='panel-heading' >\n\
                <div class=\"btn btn-danger col-md-offset-9\" onclick=\"eliminaStanza('" + number + "')\">\n\
                    <span class=\"glyphicon glyphicon-remove\"></span>\n\
                </div>\n\
            </div>\n\
            <div class='panel-body' style=\"background:whitesmoke \">\n\
                <div class=\"col-md-12 row\">\n\
                    <div class=\"col-md-6 formStanzaElement \">\n\
                        <label class=\"control-label\">Stanza</label>\n\
                        <div class='select-style'>\n\
                            <select name='TipologiaStanza' class=\"form-control\" id=\"selStanza\" onchange=\"cambiaSpecificheTipologiaStanza('Stanza" + number + "')\">\n\
                                <option value=\"1\">Stanza da Letto</option>\n\
                                <option value=\"2\">Stanza Accessoria</option>\n\
                            </select>\n\
                        </div>\n\
                    </div>\n\
                    <div class=\"col-md-6 formStanzaElement\">\n\
                        <label class=\"control-label\">Tipo di Stanza</label>\n\
                        <div class='select-style'>\n\ ";
    
    console.log(tipoLetto);
    StanzaCode = StanzaCode + "<select name='TipoL' class=\"form-control\" id=\"seltipoLetto\"  >\n\ ";
    for(var i =0;i<tipoLetto.length;i++){
        StanzaCode = StanzaCode + "<option value=\"" +tipoLetto[i] + "\">" +tipoLetto[i] + "</option>\n\ ";
    }  
    StanzaCode = StanzaCode + "</select>\n\ ";
    console.log(tipAcc);
        StanzaCode = StanzaCode + "<select name='TipoA' class=\"form-control\" id=\"seltipoAcc\" style=\"display:none\" >\n\ ";
    for(var i =0;i<tipAcc.length;i++){
        StanzaCode = StanzaCode + "<option value=\"" +tipAcc[i] + "\">" +tipAcc[i] + "</option>\n\ ";
    }
        StanzaCode = StanzaCode + "</select>\n\ ";
    


 StanzaCode = StanzaCode + "</div>\n\
                    </div>\n\
                </div>\n\
                <div class='col-md-12 row'>\n\
                    <div class=\"col-md-6 formStanzaElement form-group\">\n\
                        <label class=\"control-label\">Metratura</label> \n\
                        <input name='MetraturaS' type='number' id=\"inpMetratura\" class=\"form-control\" placeholder=\"Metratura\" />\n\
                    </div>\n\
                </div>\n\
                <div class=\"col-md-12 formStanzaElement\">\n\
                    <label class=\"control-label\">Foto</label>\n\
                <div>\n\
                <div  id=\"mydropzone" + number + "\"  class=\"dropzone needsclick dz-clickable\">\n\
                    <div class=\"dz-message needsclick\">Drop files here or click to upload.<br>\n\
                    </div>\n\
                </div>\n\
            </div>\n\
        </div>\n\
    </div>";
    
    
    return StanzaCode;
}

function sendData() {
    var myRequest = new XMLHttpRequest();
    var url = "ServletController";
    var method = "post";

    myRequest.onreadystatechange = function () {
        if (myRequest.readyState === XMLHttpRequest.DONE) {
            console.log("tutto OK");

            $('form#formStanze').ajaxSubmit({
                dataType: "text",
                success: function (response) {
                    console.log(response);
                }
            });
        }
    };

    myRequest.open(method, url, false);

    var formData = new FormData();


    for (var key of dropzoneMaps.keys()){


        var files = dropzoneMaps.get(key).getQueuedFiles();
        console.log("Coda file numero " + key + " " + files.toString());

        for (var j = 0; j < files.length; j++) {
            formData.append(key, files[j]);

        }


    }
    myRequest.setRequestHeader("action", "Annunci-newAnnuncio-FotoUpload");

    myRequest.send(formData);

}

function numeroDiStanzeValide() {

var validato = false;
    $(".Stanza").each(function (index) {
        var tipo = $(this).find("#selStanza").val();
        console.log("TIPO STANZE: " + tipo);
        if(tipo==1){
            
            validato = true;
        }
    });
    
    return validato;
}


$(document).ready(function () {

    submitButton2.addEventListener("click", function () {
        var butt = $("button#buttStanze");

        var stanzeValide = numeroDiStanzeValide();

        console.log("Stanze valide : " + stanzeValide);
        if (stanzeValide) {
            var datiValidi = validateForm(butt);
            console.log("Dati validi: " + datiValidi);
            if ((datiValidi && stanzeValide)) {

                sendData();
                generateCostiForm();


            }else{
                openModalMessage("Errore nell'inserimento dati","Controlla che tutti i dati siano stati inseriti correttamente");
            }

        } else {
            openModalMessage("Errore nell'inserimento dati","Inserisci almeno una stanza da Letto");
            //alert("INSERISCI ALMENO UNA STANZA DI CUI ALMENO UNA DA LETTO");
            
        }


    });



//prova attuale
    /*)
     submitFormButton.addEventListener("click", function() {
     $('form#formStanze').ajaxSubmit({
     dataType: "text",
     success: function (response) {
     console.log(response);
     }
     });
     });*/




});


