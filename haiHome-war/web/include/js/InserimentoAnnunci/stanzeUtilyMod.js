/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var tipoLetto;
var tipAcc;

var pannStanze = $("#stanzePanel");

var contenitore = pannStanze.find("div#contenitoreStanze");   
var tab = pannStanze.find("ul#contenitoreTab");

function setTipoCamere(tl,ta){
    tipoLetto=tl;
    tipAcc=ta;
}


function aggiungiStanza(s,i){

    
    var html = stanzaHTMLCode(s,i);



    tab.append(html.tabcode);
    //console.log(html);
    contenitore.append(html.stanzaCode);
    
    var myDropzone = new Dropzone("div#mydropzone" + (i++),
            {
                // Prevents Dropzone from uploading dropped files immediately
                autoProcessQueue: false,
                url: "ServletAnnuncio",
                parallelUploads: 100,
                uploadMultiple: true,
                paramName: "file[]",
                addRemoveLinks: false,
                thumbnailWidth: 100,
                thumbnailHeight: 100,
                previewTemplate : "<div class=\"dz-preview dz-file-preview\">\n  <div class=\"dz-image\"><img data-dz-thumbnail /></div>\n</div>",

                
    });
                


var foto = s.Foto64;
var est = s.Est64;

var cr = $("#contenitoreRadom");

for(var i = 0;i<foto.length;i++){
    
    var mockFile = { name: "", size: 50000 };
    myDropzone.emit("addedfile", mockFile);
    var prefix = "data:image/" + est[i] + ";base64, "; 
    
    myDropzone.emit("thumbnail", mockFile, prefix+foto[i]);
    
    myDropzone.emit("complete", mockFile);


}


    
    }

function stanzaHTMLCode(stanza ,number){
        var bottoneElimina = "<div id=\"aggiungia \" class=\"btn buttonElimina\" onclick=\"eliminaStanza('" + number + "')\">-</div><br />";


    if(number==0){
        //primo tab
        var tabHTML =  "<li class=\"active\"><a data-toggle=\"tab\" href=\"#stanza"+number+ "\">"+(number +1) + ": " + stanza.Tipo+"</a></li>";

        var StanzaCode =
        "<div id=\"stanza" + number + "\" class=\"tab-pane fade in active formContainer Stanza\">\n\
            <input type=\"hidden\" name=\"numStanza\" value=\"Stanza" + number + "\" /> \
                <div class=\"form-group\">\n\
                         <p class='mylabel'>Stanza :  <span class='valore'>"+ stanza.SuperTipo +"</span>TipoStanza : <span class='valore'>"+stanza.Tipo+" </span>\n\
                        </p>\n\
                    <div class=\"form-group\">\n\
                        <label class=\"control-label\">Metratura</label> <input name='MetraturaS' type='number' id=\"inpMetratura\" class=\"form-control\" value=\""+stanza.Metratura+"\"  disabled=\"disabled\" />\n\
                        </div>\n\
                            <label class=\"control-label nomeValore\">Foto</label>\n\
                        <div  id=\"mydropzone" + number + "\"  class=\"dropzone needsclick dz-clickable \">\n\
                            <div class=\"dz-message needsclick\">Drop files here or click to upload.<br>\n\</div>\n\
                        </div>\n\
                    </div>\n\
                </div>\n\
            </div>";
    }else{
        var tabHTML =  "<li><a data-toggle=\"tab\" href=\"#stanza"+number+ "\">"+(number +1) + ": " + stanza.Tipo+"</a></li>";

        var StanzaCode =
        "<div id=\"stanza" + number + "\" class=\"tab-pane fade formContainer Stanza\">\n\
            <input type=\"hidden\" name=\"numStanza\" value=\"Stanza" + number + "\" /> \
                <div class=\"form-group\">\n\
                         <p class='mylabel'>Stanza :  <span class='valore'>"+ stanza.SuperTipo +"</span>TipoStanza : <span class='valore'>"+stanza.Tipo+" </span>\n\
                        </p>\n\
                    <div class=\"form-group\">\n\
                        <label class=\"control-label\">Metratura</label> <input name='MetraturaS' type='number' id=\"inpMetratura\" class=\"form-control\" value=\""+stanza.Metratura+"\"  disabled=\"disabled\" />\n\
                        </div>\n\
                            <label class=\"control-label nomeValore\">Foto</label>\n\
                        <div  id=\"mydropzone" + number + "\"  class=\"dropzone needsclick dz-clickable \">\n\
                            <div class=\"dz-message needsclick\">Drop files here or click to upload.<br>\n\</div>\n\
                        </div>\n\
                    </div>\n\
                </div>\n\
            </div>";
    }
    
    
        
        
    
    
    var result = { stanzaCode: StanzaCode, tabcode: tabHTML};
    return result;
}


