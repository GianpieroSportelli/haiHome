/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var numStanze = 1;
        var PrezzoStanza = "<div class=\"form-group\" id=\"prezzo$_$_$\"><label class=\"control-label\">Prezzo Stanza &_&</label><input maxlength=\"100\" type=\"text\" required=\"required\" class=\"form-control prezzoStanza\" placeholder=\"Inserisci Indirizzo\" /><br /><input class=\"CompCond\" type=\"checkbox\" name=\"vehicle\" value=\"Bike\">Compreso Condominio<br><input class=\"CompRisc\" type=\"checkbox\" name=\"vehicle\" value=\"Car\" checked>Compreso Riscaldamento<br></div>";
        
     
        var PrezzoStanza2 = "<div class=\"form-group col-md-12 prezzoStanzaCont\" id=\"prezzo$_$_$\"><div class=\"col-md-6\"><label class=\"control-label\">Tipo: </label><label class=\"control-label\">&_tipo&</label><br /><label class=\"control-label\">Metratura: </label><label class=\"control-label\">&_met&</label><br /></div><div class=\"col-md-6\"> <label class=\"control-label\">Prezzo Stanza &_&</label><input maxlength=\"100\" type=\"text\" required=\"required\" class=\"form-control prezzoStanza\" placeholder=\"Inserisci Indirizzo\" /><br /><input class=\"CompCond\" type=\"checkbox\" name=\"vehicle\" value=\"Bike\">Compreso Condominio<br><input class=\"CompRisc\" type=\"checkbox\" name=\"vehicle\" value=\"Car\" checked>Compreso Riscaldamento<br></div></div>";
        function nuovaStanza(){
        numStanze++;
                var stanzaCode = "<div id='stanza" + numStanze + "' class=\"col-md-12 formContainer Stanza\"><div class=\"form-group col-md-6\"><div class=\"form-group\"><label class=\"control-label\">Stanza</label><select class=\"form-control\" id=\"selStanza\" onchange=\"cambiaSpecificheTipologiaStanza('stanza" + numStanze + "')\"><option value=\"1\">Stanza da Letto</option><option value=\"2\">Stanza Accessoria</option></select></div><div class=\"form-group\"><label class=\"control-label\">Tipo di Stanza</label> <select class=\"form-control\" id=\"seltipoLetto\"  ><option value=\"Singola\">Singola</option><option value=\"Doppia\">Doppia</option><option value=\"Altro\">Altro</option></select><select class=\"form-control\" id=\"seltipoAcc\" style=\"display:none\" ><option>Bagno</option><option>Cucina</option><option>Soggiorno</option><option>Altro</option></select> </div></div><div class=\"form-group col-md-6\"><div id=\"aggiungia \" class=\"btn buttonElimina\" onclick=\"eliminaStanza('stanza" + numStanze + "')\">-</div><br /><label class=\"control-label\">Metratura</label><input id=\"inpMetratura\"maxlength=\"100\" type=\"text\" required=\"required\" class=\"form-control\" placeholder=\"Metratura\" /><br /></div><div class=\"form-group col-md-12\"><label class=\"control-label\">Foto</label><div><form  id=\"mydropzone" + numStanze + "\" action=\"../FotoUploadServlet\" class=\"dropzone needsclick dz-clickable\" id=\"demo-upload\"><div class=\"dz-message needsclick\">Drop files here or click to upload.<br></div></form></div></div></div>";
                $(stanzaCode).appendTo("#contenitoreStanze");
                //  var myDropzone = new Dropzone("div#contenitoreStanze", { url: "../FotoUploadServlet"});
                $("form#mydropzone" + numStanze).dropzone({ url: "../FotoUploadServlet" });
                }
                //"<select class=\"form-control\" id=\"seltipoStanza\"><option class=\"acc\" hidden>Bagno</option><option class=\"acc\" hidden>Cucina</option><option class=\"acc\" hidden>Soggiorno</option><option class=\"acc\" hidden>Altro</option><option class=\"letto\">Singola</option><option class=\"letto\">Doppia</option><option class=\"letto\">Altro</option></select>" //vecchio
                
                
function eliminaStanza(nome){
$("div#" + nome).remove();
        numStanze--;
        }
        
function cambiaSpecifiche(){
           console.log("valuerandom");
       var value = $( "#costi option:selected" ).val(); 
        console.log(value);
       if(value==1){
           console.log("ciao");
           $("div#prezzoStanze").hide();
           $("div#prezzoAppartamento").show();
       }else{
           console.log("ciao");
           $("div#prezzoAppartamento").hide();
           $("div#prezzoStanze").show();
       }
} 

function cambiaSpecificheTipologiaStanza(id){
           console.log("valuerandom");
           var stanza = $("#" + id);
       //var value = $( "#selStanza option:selected" ).val(); 
       
       var value = stanza.contents().find("#selStanza option:selected").val();
       
       
        //console.log("ciaomamma " + value + " " + value1);
       if(value==1){
           console.log("stanza da letto");
           stanza.contents().find("select#seltipoLetto").show();
           stanza.contents().find("select#seltipoAcc").hide();
       }else{
           console.log("stanza accessoria");
           stanza.contents().find("select#seltipoLetto").hide();
           stanza.contents().find("select#seltipoAcc").show();
       }
} 

/* da permettere la modifica del tipo stanza in seguito alla selezione */
function cambiaCameraSpecifiche(){
           console.log("random");
       var value = $( "#selStanza option:selected" ).val(); 
        console.log(value);
       if(value==1){
           console.log("ciao");
           $("div#prezzoStanze").hide();
           $("div#prezzoAppartamento").show();
       }else{
           console.log("ciao");
           $("div#prezzoAppartamento").hide();
           $("div#prezzoStanze").show();
       }
}   
        


$(document).ready(function(){
    
    
$("#aggiungi").click(function(){
$("#stanza").clone().appendTo("#contenitoreStanze");
});


$("button#NxtS").click(function(){
    var curStep = $(this).closest(".setup-content"),
    curInputs = curStep.find("input[type='text'],input[type='url']"),
    isValid = true;
    for (var i = 0; i < curInputs.length; i++) {
        if (!curInputs[i].validity.valid) {
        isValid = false;
        $(curInputs[i]).closest(".form-group").addClass("has-error");
    }
}

if(isValid){
    $("#prezzoStanze").empty();
        console.log("Inserisco codice delle stanze");
    /* MIGLIORAMENTO DA TESTARE*/
        var i=1;
        $(".Stanza").each(function() {
            var tipologiaStanza = $(this).contents().find("#selStanza").val();
            /*controllo se è una camera da letto e quindi genero la form per il prezzo */
            if(tipologiaStanza==1){ 
                
                 var tipoStanza = $(this).contents().find("#seltipoLetto option:selected").val();
                 var metraturaStanza = $(this).contents().find("#inpMetratura").val();
                  
                console.log("Camera da letto");
                var idStanza = $(this).attr("id");
                var codiceStanza =PrezzoStanza2.replace('&_&',i);
                codiceStanza =codiceStanza.replace('$_$_$',idStanza);
                
                // nuovo formato di vista con i dati relativi alla stanza

                codiceStanza =codiceStanza.replace('&_tipo&',tipoStanza);
                codiceStanza =codiceStanza.replace('&_met&',metraturaStanza);
                
                $(codiceStanza).appendTo("#prezzoStanze");
                console.log("Inserita stanza " + i);
                i++;
                
            }

        });
    }
    
    
    });
        
    /* FINE MIFLIORAMENTO */
    
    /* vecchio codice
            for (var i=1; i<=numStanze;i++) {
                var codiceStanza =PrezzoStanza.replace('&_&',i);
                $(codiceStanza).appendTo("#prezzoStanze");
    
   
    }*/
    
});


