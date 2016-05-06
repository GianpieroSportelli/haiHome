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

//restituisce il codice HTML della stanza
function newStanzaHTMLCode(number) {
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
    

StanzaCode=StanzaCode+"</div>\n\
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


function aggiungiStanza(s,i){
    var contenitore = $("div#contenitoreStanze");
    
    var html = stanzaHTMLCode(s,i);
    console.log(html);
    contenitore.append(html);
    
}

function stanzaHTMLCode(stanza ,number){
        var StanzaCode =
        "<div id=\"Stanza" + number + "\" class=\"col-md-12 formContainer Stanza\">\n\
            <input type=\"hidden\" name=\"numStanza\" value=\"Stanza" + number + "\" /> \
                <div class=\"form-group col-md-6\">\n\
                    <div class=\"form-group\">\n\
                        <label class=\"control-label\">Stanza</label>\n\
                        <label class=\"control-label\">"+stanza.SuperTipo+"</label>\n\\n\
                        </div>\n\
                    <div class=\"form-group\">\n\
                        <label class=\"control-label\">Tipo di Stanza</label>\n\
                        <label class=\"control-label\">"+stanza.Tipo+"</label></div>\n\
                    </div>\n\
                    <div class=\"form-group col-md-6\">\n\
                        <div id=\"aggiungia \" class=\"btn buttonElimina\" onclick=\"eliminaStanza('" + number + "')\">-</div><br />\n\
                            <label class=\"control-label\">Metratura</label><label class=\"control-label\">"+stanza.Metratura+"</label><br />\n\
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


