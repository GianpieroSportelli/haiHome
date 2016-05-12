/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var stanzeMap = new Map();
var dropzoneMaps = new Map();

var tipoLetto;
var tipAcc;
var atomico;

var pannStanze = $("#stanzePanel");

var contenitore = pannStanze.find("div#contenitoreStanze");
var tab = pannStanze.find("ul#contenitoreTab");

function setInfoAnnunci(atom){
   
    atomico = atom;
    alert(atomico);
}

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
    stanza.find("form").find("input[name='fotoEliminate']").each(function () {
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
    }

    var contenitoreStanza = contenitore.find("div#stanza" + i);
    var form = contenitoreStanza.find("form");

    //Aggiungo la funzione elimina foto
    dropContainer.find("a.removeOldImg").each(function () {
        $(this).on('click', function () {
            //elimino l'immagine
            var imgContainer = $(this).parent();
            imgContainer.remove();

            //aggiungo hidden input per ricordarmelo
            var nomeFoto = imgContainer.find("img").attr("id");

            alert(form.attr("id"));
            var inputHidden = "<input type=\"hidden\" name=\"fotoEliminate\" value=\"" + nomeFoto + "\" />";
            form.append(inputHidden);

        });
    });

    var oidHidden = "<input type=\"hidden\" name=\"oidStanza\" class=\"oidStanza\" value=\"" + s.OID + "\" />";
    form.append(oidHidden);
    
    if(!atomico && s.SuperTipo=="StanzaInAffitto"){
        $(contenitoreStanza).find("input#inpPrezzoS").parent().show();
        
    }

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
                    <input type=\"hidden\" class=\"tipoStanzaHidd\" value=\"" + stanza.SuperTipo + "\" /> \
                <div class=\"form-group\">\n\
                         <p class='mylabel'>Stanza :  <span class='valore'>" + stanza.SuperTipo + "</span></br>TipoStanza : <span class='valore'>" + stanza.Tipo + " </span>\n\
                        </p>\n\
                    <div class=\"form-group\">\n\
                        <label class=\"control-label\">Metratura</label> <input name='MetraturaS' type='number' id=\"inpMetratura\" class=\"form-control\" value=\"" + stanza.Metratura + "\"  disabled=\"disabled\" />\n\
                        </div>\n\
                         <div class=\"form-group\" hidden>\n\
                            <label class=\"control-label\">Prezzo</label>\n\
                            <input name='PrezzoS'  type='number' id=\"inpPrezzoS\" class=\"form-control\" value=\"" + stanza.Prezzo + "\"  disabled=\"disabled\"/><br />\n\
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
                    <input type=\"hidden\" class=\"tipoStanzaHidd\" value=\"" + stanza.SuperTipo + "\" /> \n\
                    <input type=\"hidden\" name=\"numStanza\" value=\"Stanza" + number + "\" /> \
                        <div class=\"form-group\">\n\
                         <p class='mylabel'>Stanza :  <span class='valore'>" + stanza.SuperTipo + "</span></br>TipoStanza : <span class='valore'>" + stanza.Tipo + " </span>\n\
                        </p>\n\
                    <div class=\"form-group\">\n\
                        <label class=\"control-label\">Metratura</label> <input name='MetraturaS' type='number' id=\"inpMetratura\" class=\"form-control\" value=\"" + stanza.Metratura + "\"  disabled=\"disabled\" />\n\
                        </div>\n\
                       <div class=\"form-group\" hidden>\n\
                            <label class=\"control-label\">Prezzo</label>\n\
                            <input name='PrezzoS' type='number' id=\"inpPrezzoS\" class=\"form-control\" value=\"" + stanza.Prezzo + "\"  disabled=\"disabled\"/><br />\n\
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

    //inizializzo richiesta per le nuove stanze
    var myRequest = new XMLHttpRequest();

    //aggiungo la richiesta del check delle vecchie stanze e Metratura
    myRequest.onreadystatechange = function () {
        if (myRequest.readyState === XMLHttpRequest.DONE) {
            var formStanza = stanzaContent.find("form");

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

    var key= parseInt(stanzaContent.attr("id").substr(6));
    
    alert(key);
    
    var files = dropzoneMaps.get(key).getQueuedFiles();
           for (var j = 0; j < files.length; j++) {
            formData.append(key, files[j]);
            console.log("file da inviare " +files[j]);

        }
    
    myRequest.setRequestHeader("action", "Annunci-editAnnuncio-FotoUpload");

    myRequest.send(formData);


}

//richiesta elimina stanza
function eliminaStanza(stanza) {

    $.ajax({
        type: "POST",
        url: "ServletAnnuncio",
        data: "action=Annunci-editAnnuncio-eliminaStanza&oid=" + stanza.OID,
        dataType: "json",
        success: function (msg)
        {
            if (msg.response === "OK") {
                alert("Successo");
                //annuncio = msg.data;
                console.log("nuovo annuncio");
                var newAnn = msg.data;
                setMyAnnuncio(newAnn);
                ricaricaStanza(newAnn);
    

            } else {
                alert("QUALCOSA NON VA");
            }
        },
        error: function ()
        {
            alert("ERROE NELLA SERVLET");

        }
    });
}

//invio richiesta di aggiunta stanza
function salvaNuovaStanza(stanzaContent) {

    //inizializzo richiesta per le nuove stanze
    var myRequest = new XMLHttpRequest();

    //aggiungo la richiesta del check delle vecchie stanze e Metratura
    myRequest.onreadystatechange = function () {
        if (myRequest.readyState === XMLHttpRequest.DONE) {
            var formStanza = stanzaContent.find("form");

            $(formStanza).ajaxSubmit({
                dataType: "json",
                success: function (msg)
        {
            if (msg.response === "OK") {
                alert("Successo");
                //annuncio = msg.data;
                console.log("nuovo annuncio");
                var newAnn = msg.data;
                setMyAnnuncio(newAnn);
                ricaricaStanza(newAnn);
                

            } else {
                alert("QUALCOSA NON VA");
            }
        },
        error: function ()
        {
            alert("ERROE NELLA SERVLET");

        }
            });



        }
    };

    myRequest.open("post", "ServletAnnuncio", false);

    //inizializzo i dati
    var formData = new FormData();

    var key= parseInt(stanzaContent.attr("id").substr(6));
    
    alert(key);
    
    var files = dropzoneMaps.get(key).getQueuedFiles();
           for (var j = 0; j < files.length; j++) {
            formData.append(key, files[j]);
            console.log("file da inviare " +files[j]);

        }
    
    myRequest.setRequestHeader("action", "Annunci-editAnnuncio-FotoUpload");

    myRequest.send(formData);


}



function ricaricaStanza(newAnn){
    
                var tab = pannStanze.find("ul#contenitoreTab");
                var contenitore = pannStanze.find("div#contenitoreStanze");
                tab.empty();
                contenitore.empty();
 
                var stanze = newAnn.Stanze[0];
                console.log("RICARICO STANZE");
                for (var i = 0; i < stanze.length; i++) {
                    var s = stanze[i];
                    aggiungiStanza(s, i);
                    console.log(s);
                }
}


function newEditableStanza(){
    
     var listaTab = tab.find("a");
    console.log("numero a " + listaTab.length);
    var max = -1;
    for(var i=0;i<listaTab.length;i++){
        var href= parseInt($(listaTab[i]).attr("href").substr(7));
        if(href>max){
            max = href;
        }
        
    }
    
    var index = max+1;
    
    
    var html = stanzanewHTMLCode(index);
    
    tab.find("li.active").removeClass("active");
    
    contenitore.find("div.active").removeClass("active").removeClass("in");
    
    tab.append(html.tabcode);
    contenitore.append(html.stanzaCode);
    
    //creo la dropzone
    var myDropzone = new Dropzone("div#mydropzone" + index,
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
            
    
    dropzoneMaps.set(index, myDropzone);
    
        if(!atomico){
        contenitore.find("div.active").find("input#inpPrezzoS").parent().show();
        contenitore.find("input#inpPrezzoS").val("");
    }
 

    
}

    function stanzanewHTMLCode(number) {
        
        var tabHTML = "<li class='active'><a data-toggle=\"tab\" href=\"#stanza" + number + "\" >" + (number + 1) + ": Nuova Stanza</a></li>";
    
         var StanzaCode =
                "<div id=\"stanza" + number + "\" class=\"tab-pane fade in active formContainer Stanza\">\n\
                  <form action=\"ServletAnnuncio\" method=\"post\" id='stanzaForm" + number + "'><input type=\"hidden\" name=\"action\" value=\"Annunci-editAnnuncio-newStanza\" />\n\
                    <input type=\"hidden\" name=\"numStanza\" value=\"Stanza" + number + "\" /> \
                    <div class=\"form-group\">\n\
                        <div class=\"form-group\">\n\
                            <label class=\"control-label\">Stanza</label>\n\
                            <select name='TipologiaStanza' class=\"form-control\" id=\"selStanza\" onchange=\"cambiaSpecificheTipologiaStanza()\">\n\
                                <option value=\"1\">Stanza da Letto</option>\n\
                                <option value=\"2\">Stanza Accessoria</option>\n\
                            </select>\n\
                        </div>\n\
                        <div class=\"form-group\">\n\
                            <label class=\"control-label\">Tipo di Stanza</label>\n\ ";

    StanzaCode = StanzaCode + "<select name='TipoL' class=\"form-control\" id=\"seltipoLetto\"  >\n\ ";
    for(var i =0;i<tipoLetto.length;i++){
        StanzaCode = StanzaCode + "<option value=\"" +tipoLetto[i] + "\">" +tipoLetto[i] + "</option>\n\ ";
    }  
    StanzaCode = StanzaCode + "</select>\n\ ";

        StanzaCode = StanzaCode + "<select name='TipoA' class=\"form-control\" id=\"seltipoAcc\" style=\"display:none\" >\n\ ";
    for(var i =0;i<tipAcc.length;i++){
        StanzaCode = StanzaCode + "<option value=\"" +tipAcc[i] + "\">" +tipAcc[i] + "</option>\n\ ";
    }
        StanzaCode = StanzaCode + "</select>\n\ ";
    

 StanzaCode = StanzaCode + "</div>\n\
                        </div>\n\
                         <div class=\"form-group\">\n\
                            <label class=\"control-label\">Metratura</label>\n\
                            <input name='MetraturaS' type='number' id=\"inpMetratura\" class=\"form-control\" /><br />\n\
                        </div>\n\
                         <div class=\"form-group\" style=\"display: none;\">\n\
                            <label class=\"control-label\">Prezzo</label>\n\
                            <input name='PrezzoS' type='number' id=\"inpPrezzoS\" class=\"form-control\" value='-1'/><br />\n\
                        </div>\n\
                        <div class=\"form-group col-md-12\">\n\
                            <label class=\"control-label\">Foto</label>\n\
                        <div>\n\
                        <div  id=\"mydropzone" + number + "\"  class=\"dropzone needsclick dz-clickable\">\n\
                            <div class=\"dz-message needsclick\">Drop files here or click to upload.<br>\n\</div>\n\
                        </div>\n\
                    </div>\n\
                </div>\n\
                </form\n\
            </div>";
        
            var result = {stanzaCode: StanzaCode, tabcode: tabHTML};
    
    
    return result;
}

function cambiaSpecificheTipologiaStanza() {
    
    var stanza = pannStanze.find("div.active");

    var value = stanza.contents().find("#selStanza option:selected").val();
    //se è una stanza da letto
    if (value == 1) {
        console.log("stanza da letto " + value);
        stanza.contents().find("select#seltipoLetto").show();
        stanza.contents().find("select#seltipoAcc").hide();
        if(!atomico){
        stanza.find("input#inpPrezzoS").parent().show();
        stanza.find("input#inpPrezzoS").val("");
        
    }
    }
    //se è una stanza accessoria
    else {
        console.log("stanza accessoria");
        stanza.contents().find("select#seltipoLetto").hide();
        stanza.contents().find("select#seltipoAcc").show();
        stanza.find("input#inpPrezzoS").parent().hide();
        stanza.find("input#inpPrezzoS").val("-1");

    }

}


//PREZZO STANZA

function getHTMLprezzoStanza(stanza){
    
    var PrezzoStanzaHTML = "<div class=\"form-group col-md-12 prezzoStanzaCont\" id=\"prezzo$_$_$\">\n\
            <input type=\"hidden\" name=\"oidStanza\" value=\"" + stanza.OID + "\" />\n\
            <div class=\"col-md-6\">\n\
                <label class=\"control-label\">Tipo: </label> <label class=\"control-label\">"+stanza.Tipo+"</label><br />\n\
                <label class=\"control-label\">Metratura: </label> <label class=\"control-label\">"+stanza.Metratura+"</label><br />\n\
            </div>\n\
            <div class=\"col-md-6\">\n\
                <label class=\"control-label\">Prezzo Stanza &_&</label>\n\
                <input name='PrezzoS' class=\"form-control\" /><br />\n\
            </div>\n\
        </div>";
    
    return PrezzoStanzaHTML;
}


function nascondiPrezzoStanze(){
    
    contenitore.find("div.Stanza").each(function(){
        var stanza = $(this);
        alert(stanza);
        var value = stanza.contents().find("input.tipoStanzaHidd").val();
        alert("tipo di stanza " + value);
        //se è una stanza da letto
        if (value == "StanzaInAffitto") {
            
            stanza.find("input#inpPrezzoS").parent().hide();
            alert(stanza + " " + stanza.find("input#inpPrezzoS").attr("id"));
            stanza.find("input#inpPrezzoS").val("");


        }
        
    });


}

function mostraPrezzoStanze(stanze){
    
        contenitore.find("div.Stanza").each(function(){
        var stanza = $(this);
        var value = stanza.contents().find("input.tipoStanzaHidd").val();
        var oidS = stanza.contents().find("input.oidStanza").val();
        alert("stanza OID " + oidS);
        //se è una stanza da letto
        if (value == "StanzaInAffitto") {
            
            for (var i = 0; i < stanze.length; i++) {
                 var s = stanze[i];
                  alert("stanza salvata OID " + s.OID);
                if(s.OID == oidS){
                    stanza.find("input#inpPrezzoS").parent().show();
                    stanza.find("input#inpPrezzoS").val(s.Prezzo);
                }
                
    }



        }
        
    });
}

