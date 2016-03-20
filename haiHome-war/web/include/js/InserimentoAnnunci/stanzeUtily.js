/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var numStanze = 1;
        var PrezzoStanza = "<div class=\"form-group\"><label class=\"control-label\">Prezzo Stanza &_&</label><input maxlength=\"100\" type=\"text\" required=\"required\" class=\"form-control\" placeholder=\"Inserisci Indirizzo\" /><br /><input type=\"checkbox\" name=\"vehicle\" value=\"Bike\">Compreso Condominio<br><input type=\"checkbox\" name=\"vehicle\" value=\"Car\" checked>Compreso Riscaldamento<br></div>";
        function nuovaStanza(){
        numStanze++;
                var stanzaCode = "<div id='stanza" + numStanze + "' class=\"col-md-12 formContainer\"><div class=\"form-group col-md-6\"><div class=\"form-group\"><label class=\"control-label\">Stanza</label><select class=\"form-control\" id=\"sel1\"><option>Stanza da Letto</option><option>Stanza Accessoria</option></select></div><div class=\"form-group\"><label class=\"control-label\">Tipo di Stanza</label><select class=\"form-control\" id=\"sel1\"><option>Camera da Letto</option><option>Camera Accessoria</option></select></div></div><div class=\"form-group col-md-6\"><div id=\"aggiungia \" class=\"btn buttonElimina\" onclick=\"eliminaStanza('stanza" + numStanze + "')\">+</div><br /><label class=\"control-label\">Metratura</label><input maxlength=\"100\" type=\"text\" required=\"required\" class=\"form-control\" placeholder=\"Metratura\" /><br /></div><div class=\"form-group col-md-12\"><label class=\"control-label\">Foto</label><div><form  id=\"mydropzone" + numStanze + "\" action=\"../FotoUploadServlet\" class=\"dropzone needsclick dz-clickable\" id=\"demo-upload\"><div class=\"dz-message needsclick\">Drop files here or click to upload.<br></div></form></div></div></div>";
                $(stanzaCode).appendTo("#contenitoreStanze");
                //  var myDropzone = new Dropzone("div#contenitoreStanze", { url: "../FotoUploadServlet"});
                $("form#mydropzone" + numStanze).dropzone({ url: "../FotoUploadServlet" });
                }

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

if(isValid)
            for (var i=1; i<=numStanze;i++) {
                var codiceStanza =PrezzoStanza.replace('&_&',i);
                $(codiceStanza).appendTo("#prezzoStanze");
    
   
    }
    
});


        });
