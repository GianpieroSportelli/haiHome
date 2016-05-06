/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var tipoLetto;
var tipAcc;

function setTipoCamere(tl,ta){
    tipoLetto=tl;
    tipAcc=ta;
}


function aggiungiStanza(s,i){
    var contenitore = $("div#contenitoreStanze");
    
    var html = stanzaHTMLCode(s,i);
    //console.log(html);
    contenitore.append(html);
    
}

function stanzaHTMLCode(stanza ,number){
        var StanzaCode =
        "<div id=\"Stanza" + number + "\" class=\"col-md-12 formContainer Stanza\">\n\
            <input type=\"hidden\" name=\"numStanza\" value=\"Stanza" + number + "\" /> \
                <div class=\"form-group col-md-6\">\n\
                    <div class=\"form-group\">\n\
                        <label class=\"control-label nomeValore\">Stanza</label>\n\
                        <label class=\"control-label valore \">"+stanza.SuperTipo+"</label>\n\
                        </div>\n\
                    <div class=\"form-group\">\n\
                        <label class=\"control-label nomeValore\">Tipo di Stanza</label>\n\
                        <label class=\"control-label valore\">"+stanza.Tipo+"</label></div>\n\
                    </div>\n\
                    <div class=\"form-group col-md-6\">\n\
                        <div id=\"aggiungia \" class=\"btn buttonElimina\" onclick=\"eliminaStanza('" + number + "')\">-</div><br />\n\
                            <label class=\"control-label nomeValore\">Metratura</label><label class=\"control-label valore\">"+stanza.Metratura+"</label><br />\n\
                        </div>\n\
                        <div class=\"form-group col-md-12\">\n\
                            <label class=\"control-label nomeValore\">Foto</label>\n\
                        <div>\n\
                        <div  id=\"mydropzone" + number + "\"  class=\"dropzone needsclick dz-clickable\">\n\
                            <div class=\"dz-message needsclick\">Drop files here or click to upload.<br>\n\</div>\n\
                        </div>\n\
                    </div>\n\
                </div>\n\
            </div>";
    
    return StanzaCode;
}


