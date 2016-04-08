/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// variabili globali
        var numStanze = 0;
        
        var PrezzoStanzaHTML = 
                "<div class=\"form-group col-md-12 prezzoStanzaCont\" id=\"prezzo$_$_$\">\n\
                    <div class=\"col-md-6\">\n\
                        <label class=\"control-label\">Tipo: </label> <label class=\"control-label\">&_tipo&</label><br />\n\
                        <label class=\"control-label\">Metratura: </label> <label class=\"control-label\">&_met&</label><br />\n\
                    </div>\n\
                    <div class=\"col-md-6\">\n\
                        <label class=\"control-label\">Prezzo Stanza &_&</label>\n\
                        <input name='PrezzoS' maxlength=\"100\" type=\"text\" required=\"required\" class=\"form-control prezzoStanza\" /><br />\n\
                        <input name='compresoCondominioS' class=\"CompCond\" type=\"checkbox\"  value=\"CC\">Compreso Condominio<br>\n\
                        <input name='compresoRiscaldamentoS' class=\"CompRisc\" type=\"checkbox\"  value=\"CR\" checked>Compreso Riscaldamento<br>\n\
                    </div>\n\
                </div>";


        function getPrezzoStanzaHTMLCode(){
            return PrezzoStanzaHTML;
        }
        function nuovaStanza(){
        numStanze++;
        console.log("stanzaCode");
        var stanzaCode = getStanzaHTMLCode(numStanze);
                console.log(stanzaCode);
                $(stanzaCode).appendTo("#contenitoreStanze");
                //  var myDropzone = new Dropzone("div#contenitoreStanze", { url: "../FotoUploadServlet"});
                $("div#mydropzone" + numStanze).dropzone({ 
                url: "../ServletAnnuncio",
                paramName: "file",
                addRemoveLinks: true,
                hiddenInputContainer: "div#mydropzone" + numStanze,
                
                autoProcessQueue: false,
                uploadMultiple: true,
                parallelUploads: 100,
                maxFiles: 100
                
            });
        }
//"<select class=\"form-control\" id=\"seltipoStanza\"><option class=\"acc\" hidden>Bagno</option><option class=\"acc\" hidden>Cucina</option><option class=\"acc\" hidden>Soggiorno</option><option class=\"acc\" hidden>Altro</option><option class=\"letto\">Singola</option><option class=\"letto\">Doppia</option><option class=\"letto\">Altro</option></select>" //vecchio

//elimina una stanza
function eliminaStanza(nome){
$("div#" + nome).remove();
        numStanze--;
        }


//permette di cambiare specifiche sul prezzo (Prezzo intero Appartamento - Prezzo singole Stanze)
function cambiaSpecifiche(){
    console.log("--------------------------------------");
    console.log("Cambio Specifiche Prezzo");
    $("option#nothing").remove();
    var value = $("#costi option:selected").val();
        console.log(value);
        if (value == 1){
            console.log("Prezzo Appartamento");
            $("div#prezzoStanze").hide();
            $("div#prezzoAppartamento").show();
        } else{
            console.log("Prezzo Stanze");
            $("div#prezzoAppartamento").hide();
            $("div#prezzoStanze").show();
        }
}

//permette di cambiare le specifiche relative alla stanza (Stanza Accessoria - Stanza da Letto)
function cambiaSpecificheTipologiaStanza(id){
    console.log("--------------------------------------");
    console.log("Cambio specifiche Tipologia Stanza");
    console.log("ID Stanza " + id);
    
    //prendo la stanza
    var stanza = $("#" + id);
    
    //leggo il contenuto della select Tipologia Stanza
    var value = stanza.contents().find("#selStanza option:selected").val();
        
    //se è una stanza da letto
    if (value == 1){
        console.log("stanza da letto " + value);
        stanza.contents().find("select#seltipoLetto").show();
        stanza.contents().find("select#seltipoAcc").hide();
    }
    //se è una stanza accessoria
    else{
        console.log("stanza accessoria");
        stanza.contents().find("select#seltipoLetto").hide();
        stanza.contents().find("select#seltipoAcc").show();
    }
    
}

/* da permettere la modifica del tipo stanza in seguito alla selezione (vecchio) DA ELIMINARE*/
function cambiaCameraSpecifiche(){
console.log("random");
        var value = $("#selStanza option:selected").val();
        console.log(value);
        if (value == 1){
        console.log("ciao");
        $("div#prezzoStanze").hide();
        $("div#prezzoAppartamento").show();
        } else{
console.log("ciao");
        $("div#prezzoAppartamento").hide();
        $("div#prezzoStanze").show();
        }
}

//restituisce il codice HTML della stanza
function getStanzaHTMLCode(number){
            var StanzaCode =
        "<div id=\"stanza"+number+"\" class=\"col-md-12 formContainer Stanza\">\n\
            <div class=\"form-group col-md-6\">\n\
                <div class=\"form-group\">\n\
                    <label class=\"control-label\">Stanza</label>\n\
                    <select name='TipologiaStanza' class=\"form-control\" id=\"selStanza\" onchange=\"cambiaSpecificheTipologiaStanza('stanza"+number+"')\">\n\
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
                <div id=\"aggiungia \" class=\"btn buttonElimina\" onclick=\"eliminaStanza('stanza"+number+"')\">-</div><br />\n\
                <label class=\"control-label\">Metratura</label> <input name='MetraturaS' id=\"inpMetratura\"maxlength=\"100\" type=\"text\" class=\"form-control\" placeholder=\"Metratura\" /><br />\n\
            </div>\n\
            <div class=\"form-group col-md-12\">\n\
                <label class=\"control-label\">Foto</label>\n\
                <div>\n\
                    <div  id=\"mydropzone"+number+"\"  class=\"dropzone needsclick dz-clickable\" id=\"demo-upload\">\n\
                        <div class=\"dz-message needsclick\">Drop files here or click to upload.<br>\n\</div>\n\
                    </div>\n\
                </div>\n\
            </div>\n\
        </div>";
    return StanzaCode;
}



$(document).ready(function(){
//bottone NEXT delle stanze (genero le form per i costi) //DA ELIMINARE deprecato
$("button#NxtS").click(function(){
    
    //INIZIO VALIDAZIONE DATI
    var curStep = $(this).closest(".setup-content"),
    curInputs = curStep.find("input[type='text'],input[type='url']"),
    isValid = true;
    for (var i = 0; i < curInputs.length; i++) {
        if (!curInputs[i].validity.valid) {
            isValid = false;
            $(curInputs[i]).closest(".form-group").addClass("has-error");
        }
    }
    //se i dati sono validi vado avanti
    if (isValid){
        $("#prezzoStanze").empty(); //svuoto tutte le form per le stanze (niente duplicati)
        console.log("Inserisco codice delle stanze");
        var i = 1;
        //per ogni stanza che ho inserito
        $(".Stanza").each(function() {
            
            //mi prendo la tipologia della stanza (1 - Letto | 2 - Accessoria)
            var tipologiaStanza = $(this).contents().find("#selStanza").val();
            /*controllo se è una camera da letto e quindi genero la form per il prezzo */
            if (tipologiaStanza == 1){
                //mi prendo un po di informazioni della stanza
                var tipoStanza = $(this).contents().find("#seltipoLetto option:selected").val();
                var metraturaStanza = $(this).contents().find("#inpMetratura").val();
                var idStanza = $(this).attr("id");
                //inserisco le informazioni in delle label
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


});        
});


