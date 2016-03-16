/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function nuovaStanza(){
    
var stanzaCode = "<div class=\"col-md-12\"><div class=\"form-group col-md-6\"><div class=\"form-group\"><label class=\"control-label\">Stanza</label><select class=\"form-control\" id=\"sel1\"><option>Stanza da Letto</option><option>Stanza Accessoria</option></select></div><div class=\"form-group\"><label class=\"control-label\">Tipo di Stanza</label><select class=\"form-control\" id=\"sel1\"><option>Camera da Letto</option><option>Camera Accessoria</option></select></div></div><div class=\"form-group col-md-6\"><label class=\"control-label\">Metratura</label><input maxlength=\"100\" type=\"text\" required=\"required\" class=\"form-control\" placeholder=\"Metratura\" /><br /></div><div class=\"form-group col-md-12\"><label class=\"control-label\">Foto</label><div id=\"dropzoneCont\"></div></div></div>";
     //$(stanzaCode).appendTo("#contenitoreStanze");
  //  var myDropzone = new Dropzone("div#contenitoreStanze", { url: "../FotoUploadServlet"});
    $("div#random").dropzone({ url: "../FotoUploadServlet" });
    


}


$(document).ready(function(){
    $("#aggiungi").click(function(){
        $("#stanza").clone().appendTo("#contenitoreStanze");
    });
});
