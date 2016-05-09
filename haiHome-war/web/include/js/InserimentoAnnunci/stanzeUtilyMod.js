/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var stanzeMap = new Map();
var dropzoneMaps = new Map();

var tipoLetto;
var tipAcc;

var pannStanze = $("#stanzePanel");

var contenitore = pannStanze.find("div#contenitoreStanze");
var tab = pannStanze.find("ul#contenitoreTab");


function setTipoCamere(tl, ta) {
    tipoLetto = tl;
    tipAcc = ta;
}

//abilita dropzone Stanza
function abilitaDropzone(stanzaContainer) {
    var dropzone = $(stanzaContainer).find("div.dropzone");
    dropzone.removeClass("not-active");
}

//disabilita dropzone Stanza
function disabilitaDropzone(stanzaContainer) {
    var dropzone = $(stanzaContainer).find("div.dropzone");
    dropzone.addClass("not-active");
}

//resetta i dati di una stanza
function resetStanza(s, idStanza) {
    //var html = stanzaHTMLCode(s,idStanza);
    var stanza = contenitore.find(".Stanza#stanza" + idStanza);
    var metTag = stanza.find("#inpMetratura");
    alert(stanza.attr("id"));
    metTag.val(s.Metratura);

    //alert("Metratura "+ s.Metratura +"\nId-Stanza " + idStanza);
    //svuoto dropzone
    var drop = dropzoneMaps.get(parseInt(idStanza));


    drop.removeAllFiles();

    var contenitoreFoto = stanza.find("div#mydropzone" + idStanza);
    contenitoreFoto.empty();

    //ricatico dropzone
    var foto = s.Foto64;
    var est = s.Est64;
    for (var i = 0; i < foto.length; i++) {

        var mockFile = {name: "", size: 50000};
        drop.emit("addedfile", mockFile);
        var prefix = "data:image/" + est[i] + ";base64, ";

        drop.emit("thumbnail", mockFile, prefix + foto[i]);

        drop.emit("complete", mockFile);


    }
    
    //elimino gli hidden Input  
    stanza.find("form").find("input[name='fotoEliminate']").each(function(){
        alert("sono entrato");
        $(this).remove();
    });



}

//aggiunge una Stanza
function aggiungiStanza(s, i) {

    var html = stanzaHTMLCode(s, i);

    tab.append(html.tabcode);
    contenitore.append(html.stanzaCode);

    //creo la dropzone
    var myDropzone = new Dropzone("div#mydropzone" + (i),
            {
                // Prevents Dropzone from uploading dropped files immediately
                autoProcessQueue: false,
                url: "ServletAnnuncio",
                parallelUploads: 100,
                uploadMultiple: true,
                paramName: "file[]",
                addRemoveLinks: true,
                thumbnailWidth: 100,
                thumbnailHeight: 100,
                previewTemplate: "<div class=\"dz-preview dz-file-preview\">\n  <div class=\"dz-image\"><img data-dz-thumbnail /></div>\n</div>"
            });



    //inserisco le foto
    var foto64 = s.Foto64;
    var foto = s.Foto;
    var est = s.Est64;

    for (var j = 0; j < foto64.length; j++) {
        var mockFile = {name: "", size: 50000};
        var prefix = "data:image/" + est[j] + ";base64, ";

        var dropContainer = $("div#mydropzone" + i);
        var fotoHTML = "<div class=\"dz-preview dz-file-preview\">\n  <div class=\"dz-image\"><img id=\"" + foto[j] + "\" src=\"" + prefix + foto64[j] + "\" data-dz-thumbnail class='old'/></div><a class=\"dz-remove removeOldImg\" data-dz-remove>Remove File</a>\n</div>";
        dropContainer.append(fotoHTML);
        /*
         myDropzone.emit("addedfile", mockFile);
         myDropzone.emit("thumbnail", mockFile, prefix + foto[j]);
         myDropzone.emit("complete", mockFile);
         */
    }
    
    var contenitoreStanza = contenitore.find("div#stanza" + i );
    var form = contenitoreStanza.find("form");
            
    //Aggiungo la funzione elimina foto
    dropContainer.find("a.removeOldImg").each(function () {
        $(this).on('click',function(){
            //elimino l'immagine
            var imgContainer = $(this).parent();
            imgContainer.remove();
            
            //aggiungo hidden input per ricordarmelo
            var nomeFoto = imgContainer.find("img").attr("id");

            alert(form.attr("id"));
            var inputHidden = "<input type=\"hidden\" name=\"fotoEliminate\" value=\""+nomeFoto+"\" />";
            form.append(inputHidden);
            
        });
    });
    
    var oidHidden = "<input type=\"hidden\" name=\"oidStanza\" value=\""+s.OID+"\" />";
    form.append(oidHidden);
    
    stanzeMap.set(i, s);
    dropzoneMaps.set(i, myDropzone);
}

//creo HTML stanza
function stanzaHTMLCode(stanza, number) {

    if (number == 0) {
        //primo tab
        var tabHTML = "<li class=\"active\"><a data-toggle=\"tab\" href=\"#stanza" + number + "\" >" + (number + 1) + ": " + stanza.Tipo + "</a></li>";

        var StanzaCode =
                "<div id=\"stanza" + number + "\" class=\"tab-pane fade in active formContainer Stanza\">\n\
                  <form action=\"ServletAnnuncio\" method=\"post\" id='stanzaForm" + number + "'><input type=\"hidden\" name=\"action\" value=\"Annunci-editAnnuncio-infoEditStanze\" />\n\
                    <input type=\"hidden\" name=\"numStanza\" value=\"Stanza" + number + "\" /> \
                <div class=\"form-group\">\n\
                         <p class='mylabel'>Stanza :  <span class='valore'>" + stanza.SuperTipo + "</span></br>TipoStanza : <span class='valore'>" + stanza.Tipo + " </span>\n\
                        </p>\n\
                    <div class=\"form-group\">\n\
                        <label class=\"control-label\">Metratura</label> <input name='MetraturaS' type='number' id=\"inpMetratura\" class=\"form-control\" value=\"" + stanza.Metratura + "\"  disabled=\"disabled\" />\n\
                        </div>\n\
                            <label class=\"control-label nomeValore\">Foto</label>\n\
                        <div  id=\"mydropzone" + number + "\"  class=\"dropzone needsclick dz-clickable not-active \">\n\
                        </div>\n\
                    </div>\n\
                </div>\n\
            </form>\n\
            </div>";
    } else {
        var tabHTML = "<li><a data-toggle=\"tab\" href=\"#stanza" + number + "\" >" + (number + 1) + ": " + stanza.Tipo + "</a></li>";

        var StanzaCode =
                "<div id=\"stanza" + number + "\" class=\"tab-pane fade formContainer Stanza\">\n\
                   <form action=\"ServletAnnuncio\" method=\"post\" id='stanzaForm" + number + "'><input type=\"hidden\" name=\"action\" value=\"Annunci-editAnnuncio-infoEditStanze\" />\n\
                    <input type=\"hidden\" name=\"numStanza\" value=\"Stanza" + number + "\" /> \
                        <div class=\"form-group\">\n\
                         <p class='mylabel'>Stanza :  <span class='valore'>" + stanza.SuperTipo + "</span></br>TipoStanza : <span class='valore'>" + stanza.Tipo + " </span>\n\
                        </p>\n\
                    <div class=\"form-group\">\n\
                        <label class=\"control-label\">Metratura</label> <input name='MetraturaS' type='number' id=\"inpMetratura\" class=\"form-control\" value=\"" + stanza.Metratura + "\"  disabled=\"disabled\" />\n\
                        </div>\n\
                            <label class=\"control-label nomeValore\">Foto</label>\n\
                        <div  id=\"mydropzone" + number + "\"  class=\"dropzone needsclick dz-clickable not-active \">\n\
                        </div>\n\
                    </div>\n\
                </div>\n\
                </form>\n\
            </div>";
    }



    var result = {stanzaCode: StanzaCode, tabcode: tabHTML};
    return result;
}

//invio richiesta di modifica
function modificaStanza(stanzaContent) {

    var metrS = $(stanzaContent).find("input#inpMetratura").val();
    var idStanza = $(stanzaContent).attr("id").slice(6);
    alert(idStanza);
    var oldStanza = stanzeMap.get(parseInt(idStanza));

    //controllo se le immagini vecchie sono ancora presenti



    //inizializzo richiesta per le nuove stanze
    var myRequest = new XMLHttpRequest();

    //aggiungo la richiesta del check delle vecchie stanze e Metratura
    myRequest.onreadystatechange = function () {
        if (myRequest.readyState === XMLHttpRequest.DONE) {
            var formStanza = stanzaContent.find("form");
            /*
            stanzaContent.find("img.old").each(function () {
                var nomeFoto = $(this).attr("id");
                alert(nomeFoto);
                var hiddInp = "<input type=\"hidden\" name=\"oldFoto\" value=\"" + nomeFoto + "\" class='tempInput' />";
            });
            */
           
            $(formStanza).ajaxSubmit({
                dataType: "json",
                success: function (response) {
                    console.log(response.response);
                    console.log(response.data);
                }
            });



        }
    };

    myRequest.open("post", "ServletAnnuncio", false);

    //inizializzo i dati
    var formData = new FormData();


    for (var key of dropzoneMaps.keys()){

        var files = dropzoneMaps.get(key).getQueuedFiles();
        console.log("Coda file Stanza numero " + key + " " + files.toString());

        for (var j = 0; j < files.length; j++) {
            formData.append(key, files[j]);

        }


    }

    myRequest.setRequestHeader("action", "Annunci-editAnnuncio-FotoUpload");

    myRequest.send(formData);


}

