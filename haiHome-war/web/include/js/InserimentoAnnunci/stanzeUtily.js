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
var PrezzoStanzaHTML = "<div class=\"form-group col-md-12 prezzoStanzaCont\" id=\"prezzo$_$_$\">\n\
            <div class=\"col-md-6\">\n\
                <input type=\"hidden\" name=\"idStanza\" value=\"$__$\" />\n\
                <label class=\"control-label\">Tipo: </label> <label class=\"control-label\">&_tipo&</label><br />\n\
                <label class=\"control-label\">Metratura: </label> <label class=\"control-label\">&_met&</label><br />\n\
            </div>\n\
            <div class=\"col-md-6\">\n\
                <label class=\"control-label\">Prezzo Stanza &_&</label>\n\
                <input name='PrezzoS' maxlength=\"100\" class=\"form-control prezzoStanza\" /><br />\n\
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

//aggiunge un elemento all'arrey delle form
/*function addFormStanza(fs, i){
 stanzeForms[i] = fs;
 }*/

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
                url: "ServletAnnuncio",
                parallelUploads: 100,
                uploadMultiple: true,
                paramName: "file[]",
                addRemoveLinks: true});
    addDropzoneStanza(myDropzone, numStanze);

    contatoreStanze++;



}




// per ora va solo avanti
function validateFormStanze(butt) {
    //alert("Validazione form");
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



        /*
         $(".prezzoStanza").each(function (index) {
         console.log(index + ": " + $(this).text());
         
         });*/

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

/* da permettere la modifica del tipo stanza in seguito alla selezione (vecchio) DA ELIMINARE*/
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
}

//restituisce il codice HTML della stanza
function getStanzaHTMLCode(number, tipoLetto, tipAcc) {
     var StanzaCode2 =
            "<div id=\"Stanza" + number + "\" class=\"col-md-12 formContainer Stanza\">\n\
        <input type=\"hidden\" name=\"numStanza\" value=\"Stanza" + number + "\" /> \
        <div class=\"form-group col-md-6\">\n\
            <div class=\"form-group\">\n\
                <label class=\"control-label\">Stanza</label>\n\
                <select name='TipologiaStanza' class=\"form-control\" id=\"selStanza\" onchange=\"cambiaSpecificheTipologiaStanza('Stanza" + number + "')\">\n\
                    <option value=\"1\">Stanza da Letto</option>\n\
                    <option value=\"2\">Stanza Accessoria</option>\n\
                </select>\n\
            </div>\n\
            <div class=\"form-group\">\n\
                <label class=\"control-label\">Tipo di Stanza</label>\n\
                <select name='TipoL' class=\"form-control\" id=\"seltipoLetto\"  >\n\
                    <option value=\"Singola\">Singola</option>\n\
                    <option value=\"Doppia\">Doppia</option>\n\
                    <option value=\"Altro\">Altro</option>\n\
                </select>\n\
                <select name='TipoA' class=\"form-control\" id=\"seltipoAcc\" style=\"display:none\" >\n\
                    <option>Bagno</option><option>Cucina</option>\n\
                    <option>Soggiorno</option>\n\
                    <option>Altro</option>\n\
                </select>\n\
            </div>\n\
        </div>\n\
        <div class=\"form-group col-md-6\">\n\
            <div id=\"aggiungia \" class=\"btn buttonElimina\" onclick=\"eliminaStanza('" + number + "')\">-</div><br />\n\
            <label class=\"control-label\">Metratura</label> <input name='MetraturaS' type='number' id=\"inpMetratura\"maxlength=\"100\" type=\"text\" class=\"form-control\" placeholder=\"Metratura\" /><br />\n\
        </div>\n\
    <div class=\"form-group col-md-12\">\n\
        <label class=\"control-label\">Foto</label>\n\
    <div>\n\
    <div  id=\"mydropzone" + number + "\"  class=\"dropzone needsclick dz-clickable\">\n\
        <div class=\"dz-message needsclick\">Drop files here or click to upload.<br>\n\</div>\n\
    </div>\n\
    </div>\n\
    </div>\n\
</div>";
    
         var StanzaCode =
            "<div id=\"Stanza" + number + "\" class=\"col-md-12 formContainer Stanza\">\n\
        <input type=\"hidden\" name=\"numStanza\" value=\"Stanza" + number + "\" /> \
        <div class=\"form-group col-md-6\">\n\
            <div class=\"form-group\">\n\
                <label class=\"control-label\">Stanza</label>\n\
                <select name='TipologiaStanza' class=\"form-control\" id=\"selStanza\" onchange=\"cambiaSpecificheTipologiaStanza('Stanza" + number + "')\">\n\
                    <option value=\"1\">Stanza da Letto</option>\n\
                    <option value=\"2\">Stanza Accessoria</option>\n\
                </select>\n\
            </div>\n\
            <div class=\"form-group\">\n\
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
    

 StanzaCode = StanzaCode + "</div>\n\
        </div>\n\
        <div class=\"form-group col-md-6\">\n\
            <div id=\"aggiungia \" class=\"btn buttonElimina\" onclick=\"eliminaStanza('" + number + "')\">-</div><br />\n\
            <label class=\"control-label\">Metratura</label> <input name='MetraturaS' type='number' id=\"inpMetratura\"maxlength=\"100\" type=\"text\" class=\"form-control\" placeholder=\"Metratura\" /><br />\n\
        </div>\n\
    <div class=\"form-group col-md-12\">\n\
        <label class=\"control-label\">Foto</label>\n\
    <div>\n\
    <div  id=\"mydropzone" + number + "\"  class=\"dropzone needsclick dz-clickable\">\n\
        <div class=\"dz-message needsclick\">Drop files here or click to upload.<br>\n\</div>\n\
    </div>\n\
    </div>\n\
    </div>\n\
</div>";
    
    
    return StanzaCode;
}


function sendData() {
    var myRequest = new XMLHttpRequest();
    var url = "ServletAnnuncio";
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
    myRequest.setRequestHeader("action", "action");

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


