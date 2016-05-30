/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var myAnnuncio;

var tipoLetto;
var tipoAcc;

//Informazioni indirizzo
var pannInd;
var pIndopen = false;

//Informazioni Appartamento
var pannInfo;
var pInfopen = false;

//Informazioni Stanze
var pannStanze;
var pStaopen = false;


//Informazioni prezzo
var pannCosti;
var pCosopen = false;

//gestisco due stadi: ready(false) - edit(true)
var editMode = false;

//bottone di modifica Indirizzo - InfoAppartamento - Prezzo
var editButton;

//bottoni stanze
var editStanza;
var deleteStanza;
var confermaStanza;
var annullaStanza;

var newStanza;
var salvaNewStanza;
var annullanewStanza;


$(document).ready(function () {
    pannInd = $("#indirizzoPanel");
    pannInfo = $("#infoPanel");
    pannStanze = $("#stanzePanel");
    pannCosti = $("#costiPanel");

    editButton = $(".editButton");

    editStanza = $(".editStanza");
    deleteStanza = $(".deleteStanza");
    confermaStanza = $(".confermaStanza");
    annullaStanza = $(".annullaStanza");

    newStanza = $(".aggiungiStanza");
    salvaNewStanza = $(".salvaNewStanza");
    annullanewStanza = $(".annullanewStanza");

    //funzioni bottoni

    //bottone modifica
    editButton.on("click", function () {
        var panel = $(this).closest("div.panel");
        var idpanel = panel.attr("id");

        if (!editMode) {
            editMode = true;

            if (idpanel == pannInd.attr("id")) {
                //info indirizzo
                abilitaInsetimentoDati(pannInd);
                showFooterButton(pannInd);

            } else if (idpanel == pannInfo.attr("id")) {
                //info appartamento
                abilitaInsetimentoDati(pannInfo);
                dataInizio.datepicker("option", "showOn", "both");
                showFooterButton(pannInfo);

            } else if (idpanel == pannStanze.attr("id")) {
                //info stanze



            } else if (idpanel == pannCosti.attr("id")) {
                abilitaInsetimentoDati(pannCosti);
                showFooterButton(pannCosti);
            } else {
                
            }
        }else{
            openModalMessage("Modifica non consentita","Impossibile apportare ulteriori modifiche, conferma o annulla le modifiche in corso");
        }




    });



    //bottone conferma
    $(document).on('click', 'a.edit-save', function (event) {
        var panel = $(this).closest("div.panel");
        var idpanel = panel.attr("id");
        
        //da inserire un modal, che permette di chiedere conferma della conferma ;)

        if (idpanel == pannInd.attr("id")) {

            if (checkIndirizzo()) {
                //alert("Indirizzo Corretto");
                sendEditIndirizzoRequest();
                disabilitaInserimentoDati(pannInd);
                hideFooterButton(pannInd);
                openModalMessage("Info Indirizzo","Dati salvati Correttamente");

            } else {
                openModalMessage("Info Indirizzo","Impossibile salvare i dati, formato dati non corretto");
            }



        } else if (idpanel == pannInfo.attr("id")) {

            if (checkInfoApp()) {
                //alert("Dati Corretti");
                sendEditAppartamentoRequest();
                disabilitaInserimentoDati(pannInfo);
                hideFooterButton(pannInfo);
                openModalMessage("Info Annuncio","Dati salvati Correttamente");
            } else {
                 openModalMessage("Info Annuncio","Impossibile salvare i dati, formato dati non corretto");

            }

        } else if (idpanel == pannStanze.attr("id")) {

        } else if (idpanel == pannCosti.attr("id")) {
                
            if(checkCosti()){ // da sviluppare la funzione
                sendEditCosti();
                disabilitaInserimentoDati(pannCosti);
                hideFooterButton(pannCosti);
                openModalMessage("Info Costi","Dati salvati Correttamente");
            }else{
                openModalMessage("Info Costi","Impossibile salvare i dati, formato dati non corretto");

            }
                

        } else {

        }
        editMode = false;
    });

    //bottone annulla 
    $(document).on('click', 'a.edit-cancel', function (event) {
        var panel = $(this).closest("div.panel");
        var idpanel = panel.attr("id");

        //da inserire un modal, che permette di chiedere conferma dell'annulla

        if (idpanel == pannInd.attr("id")) {
            aggiornaIndForm(myAnnuncio);
            disabilitaInserimentoDati(pannInd);
            hideFooterButton(pannInd);

        } else if (idpanel == pannInfo.attr("id")) {
            aggiornaAptForm(myAnnuncio);
            disabilitaInserimentoDati(pannInfo);
            hideFooterButton(pannInfo);

        } else if (idpanel == pannStanze.attr("id")) {

        } else if (idpanel == pannCosti.attr("id")) {
            aggiornaInfoCosti(myAnnuncio);
            disabilitaInserimentoDati(pannCosti);
            hideFooterButton(pannCosti);

        } else {

        }
        editMode = false;
    });


    //BOTTONI STANZE

    //bottone modifica Stanza
    editStanza.on("click", function () {

        var navContent = pannStanze.find(".tab-content");
        var stanzaContent = navContent.find("div.active");

        var tabStanze = pannStanze.find(".nav-tabs");

        //disattivo tutti i tab
        tabStanze.find("a").each(function (index) {
            var href = $(this).attr("href");
            if (href != stanzaContent.attr("id")) {
                $(this).addClass("not-active");
            }

        });
        //abilito inserimento
        abilitaInsetimentoDati(stanzaContent);
        abilitaDropzone(stanzaContent);

        //nascondo bottoni 1
        editStanza.parent("div").hide();

        //mostro bottoni 2 
        confermaStanza.parent("div").show();

    });

    //bottone conferma Stanza
    confermaStanza.on("click", function () {

        var navContent = pannStanze.find(".tab-content");
        var stanzaContent = navContent.find("div.active");

        var supertipo = stanzaContent.find(".tipoStanzaHidd").val();
        if(supertipo=="StanzaAccessoria"){
            supertipo="2";
        }else{
            supertipo="1";
        }

        // controllo dati stanza
        if(checkStanza(stanzaContent,myAnnuncio.Atomico,supertipo)){
            
         modificaStanza(stanzaContent);

        //disabilito l'inserimento
        disabilitaInserimentoDati(stanzaContent);
        disabilitaDropzone(stanzaContent);

        //attivo tutti i tab
        var tabStanze = pannStanze.find(".nav-tabs");

        tabStanze.find("a").each(function (index) {
            var href = $(this).attr("href");
            if (href != stanzaContent.attr("id")) {
                $(this).removeClass("not-active");
            }

        });

        //nascondo bottoni 2
        confermaStanza.parent("div").hide();

        //mostro bottoni 1 
        editStanza.parent("div").show();
        openModalMessage("Modifica Stanza","Dati salvati Correttamente");
        }else{
            openModalMessage("Modifica Stanze","Impossibile modificare i dati, formato dati non corretto");

        }


    });






    //bottone annulla stanza
    annullaStanza.on("click", function () {
        var navContent = pannStanze.find(".tab-content");
        var stanzaContent = navContent.find("div.active");
        var idStanza = stanzaContent.attr("id").slice(6);

        //da inserire un modal di conferma, per confermare l'annullamento delle modifiche
        var stanze = myAnnuncio.Stanze[0];
        var s = stanze[idStanza];

        resetStanza(s, idStanza);

        //disabilito l'inserimento
        disabilitaInserimentoDati(stanzaContent);
        disabilitaDropzone(stanzaContent);

        //attivo tutti i tab
        var tabStanze = pannStanze.find(".nav-tabs");

        tabStanze.find("a").each(function (index) {
            var href = $(this).attr("href");
            if (href != stanzaContent.attr("id")) {
                $(this).removeClass("not-active");
            }

        });
        //nascondo bottoni 2
        confermaStanza.parent("div").hide();
        //mostro bottoni 1 
        editStanza.parent("div").show();
    });




    //bottone cancella stanza
    deleteStanza.on("click", function () {
        var title ="Sicuro di voler eliminare la stanza?";
        var body = "Eliminando la stanza non si può tornare più indietro";
        var footer = "<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">NO</button>";
        footer += "<button type=\"button\" class=\"btn btn-success\" onclick=\"cancellaStanza()\"data-dismiss=\"modal\">SI</button>"
        openModalMessage(title, body, footer);

    });


    //bottone aggiungi stanza
    newStanza.on("click", function () {

        var navContent = pannStanze.find(".tab-content");
        var stanzaContent = navContent.find("div.active");
        var tabStanze = pannStanze.find(".nav-tabs");
        //disattivo tutti i tab
        tabStanze.find("a").each(function (index) {
            var href = $(this).attr("href");
            if (href != stanzaContent.attr("id")) {
                $(this).addClass("not-active");
            }

        });
        setInfoAnnunci(myAnnuncio.Atomico);
        newEditableStanza();

        //nascondo bottoni 1
        editStanza.parent("div").hide();

        //mostro bottoni 3
        salvaNewStanza.parent("div").show();



    });


    //bottone annulla new Stanza
    annullanewStanza.on("click", function () {
        
        

        var navContent = pannStanze.find(".tab-content");
        var stanzaContent = navContent.find("div.active");
        var tabStanze = pannStanze.find(".nav-tabs");
        var tabContent = tabStanze.find("li.active");

        
        stanzaContent.remove();
        tabContent.remove();


        
        navContent.find("div#stanza0").addClass("active").addClass("in");
        tabStanze.find("a[href='#stanza0']").parents("li").addClass("active");

        //attivo tutti i tab
        var tabStanze = pannStanze.find(".nav-tabs");

        tabStanze.find("a").each(function (index) {
            var href = $(this).attr("href");
            if (href != stanzaContent.attr("id")) {
                $(this).removeClass("not-active");
            }

        });



        //mostro bottoni 1
        editStanza.parent("div").show();

        //nascondo bottoni 3
        salvaNewStanza.parent("div").hide();
        



    });


    //bottone salva new Stanza
    salvaNewStanza.on("click", function () {

        var navContent = pannStanze.find(".tab-content");
        var stanzaContent = navContent.find("div.active");
        var tabStanze = pannStanze.find(".nav-tabs");
        var tabContent = tabStanze.find("li.active");

        var supertipo = stanzaContent.find("#selStanza").val();

        if(checkStanza(stanzaContent,myAnnuncio.Atomico,supertipo)){
            salvaNuovaStanza(stanzaContent);


        //disabilito l'inserimento
        disabilitaInserimentoDati(stanzaContent);
        disabilitaDropzone(stanzaContent);

        //attivo tutti i tab
        var tabStanze = pannStanze.find(".nav-tabs");

        tabStanze.find("a").each(function (index) {
            var href = $(this).attr("href");
            if (href != stanzaContent.attr("id")) {
                $(this).removeClass("not-active");
            }

        });


        //mostro bottoni 1
        editStanza.parent("div").show();

        //nascondo bottoni 3
        salvaNewStanza.parent("div").hide();
        openModalMessage("Nuova Stanza","Dati salvati Correttamente");
        } else {
                openModalMessage("Nuova Stanza","Impossibile salvare i dati, formato dati non corretto");
            }



    });





    //CONTROLLO CLICK PANNELLI

    //pannello Indirizzo
    pannInd.find("a.openlink").on("click", function () {
        console.log(pIndopen);
        pIndopen = !pIndopen;
        if (pIndopen) {
            $(this).siblings(".editButton").show();
        } else {
            $(this).siblings(".editButton").hide();
        }


    });

    //pannelo info appartamento
    pannInfo.find("a.openlink").on("click", function () {
        console.log(pInfopen);
        pInfopen = !pInfopen;
        if (pInfopen) {
            $(this).siblings(".editButton").show();
        } else {
            $(this).siblings(".editButton").hide();
        }


    });


    //pannelo info stanze
    pannStanze.find("a.openlink").on("click", function () {
        console.log(pStaopen);
        pStaopen = !pStaopen;
        if (pStaopen) {
            $(this).siblings(".editButton").show();
        } else {
            $(this).siblings(".editButton").hide();
        }


    });

    //pannello info costi
    pannCosti.find("a.openlink").on("click", function () {
        console.log(pCosopen);
        pCosopen = !pCosopen;
        if (pCosopen) {
            $(this).siblings(".editButton").show();
        } else {
            $(this).siblings(".editButton").hide();
        }


    });


    //UTILY PAGINA

    //Ddatapicker
    var dataInizio = pannInfo.find("input#inpDataInizio");
    dataInizio.datepicker({
        showOn: "focus",
        buttonImage: "images/calendario.png",
        buttonImageOnly: true,
        minDate: 0,
        monthNames: ["Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"],
        dayNamesMin: ["Do", "Lu", "Ma", "Me", "Gi", "Ve", "Sa"],
        dateFormat: "dd-mm-yy"
    });



    initalEditFunction();
});


//richiesta iniziale
function initalEditFunction() {

    var oidTag = $("input#OIDAnnuncio");

    console.log(oidTag.attr("value"));
    var oid = oidTag.attr("value");

    console.log("INVIO RICHIEDA INIZIALE DI MODIFICA");

    $.post("ServletController",
            {action: "Annunci-editAnnuncio-initialRequest",
                idAnnuncio: oid},
            function (msg) {
                var esito = msg.response;
                if (esito == "OK") {
                    var annuncio = msg.data;
                    console.log(annuncio);
                    //leggo la risposta
                    myAnnuncio = annuncio;
                    tipoLetto = msg.tipoAffitto;
                    tipoAcc = msg.tipoAccessorio;
                    setTipoCamere(tipoLetto, tipoAcc);


                    //riempio i campi
                    aggiornaIndForm(myAnnuncio);
                    aggiornaAptForm(myAnnuncio);
                    aggiornaStanzeForm(myAnnuncio);
                    aggiornaInfoCosti(myAnnuncio);





                } else {
                    console.log("La servlet non ha risposto OK");
                }
            });
}

//Aggiorna Dati Indirizzo
function aggiornaIndForm(ann) {
    var indirizzoTag = pannInd.find("#inpIndirizzo");
    var civicoTag = pannInd.find("#inpCivico");
    var quartTag = pannInd.find("#selQuartiere");



    indirizzoTag.val(ann.Indirizzo.split(", ")[0]);
    civicoTag.val(ann.Indirizzo.split(", ")[1]);

    var quart = '<option id=\"' + ann.Quartiere + '\" value=\"' + ann.Quartiere + '\">' + ann.Quartiere + '</option>';
    quartTag.append(quart);

}

//Aggiorna Dati Info Appartamento
function aggiornaAptForm(ann) {
    var descrizioneTag = pannInfo.find("#textDescrizione");
    var metraturaTag = pannInfo.find("#inpMetratura");
    var dataInizio = pannInfo.find("input#inpDataInizio");
    var arredTag = pannInfo.find("#inpArredato");

    if (ann.Arredato) {
        $(arredTag).attr('checked', true);
    } else {
        $(arredTag).attr('checked', false);
    }

    descrizioneTag.val(ann.Descrizione);
    metraturaTag.val(ann.Metratura);

    var d = myAnnuncio.DataInizioAffitto.split("/");
    var data = new Date();
    data.setFullYear(d[2]);
    data.setMonth(d[1] - 1);
    data.setDate(d[0]);
    console.log(data);
    dataInizio.datepicker("setDate", data);


}

//Aggiorna form Stanze
function aggiornaStanzeForm(ann) {
    setInfoAnnunci(ann.Atomico);
    var stanze = ann.Stanze[0];
    for (var i = 0; i < stanze.length; i++) {
        var s = stanze[i];
        aggiungiStanza(s, i);
        console.log(s);


    }
}

//Abilita inserimento dati (generico)
function abilitaInsetimentoDati(pannello) {
    var curInputs = pannello.find("input[type='text'],input[type='url'],input[type='number'],input[type='checkbox'],select,textarea");
    for (var i = 0; i < curInputs.length; i++) {
        console.log("input n " + i + " id: " + $(curInputs[i]).attr("id"));
        $(curInputs[i]).removeAttr("disabled");
    }

}

//Disabilita inserimento dati (generico)
function disabilitaInserimentoDati(pannello) {
    var curInputs = pannello.find("input[type='text'],input[type='url'],input[type='number'],input[type='checkbox'],select,textarea");
    for (var i = 0; i < curInputs.length; i++) {
        console.log("input n " + i + " id: " + $(curInputs[i]).attr("id"));
        $(curInputs[i]).attr("disabled", "disabled");
    }
    var dataInizio = pannInfo.find("input#inpDataInizio");
    dataInizio.datepicker("option", "showOn", "focus");
}

//Visualizza i bottoni nel footer (generico)
function showFooterButton(pannello) {
    var footer = pannello.find("div.panel-footer");

    var confirmButton = "<a class=\"cancel-edit edit-cancel\" ><span class=\"glyphicon glyphicon-remove\" ></span>Annulla</a>";
    var cancelButton = "<a class=\"save-edit edit-save\" ><span class=\"glyphicon glyphicon-saved\" ></span>Salva</a>";
    //var confirmButton = "<button type=\"button\" class=\"btn btn-success edit-conf\">Conferma</button>";
    //var cancelButton = "<button type=\"button\" class=\"btn btn-danger edit-cancel\">Annulla</button>";
    $(footer).append("<div class=\"row\">" + cancelButton + confirmButton + "</div>");

}

//Nasconde i bottoni nel footer (generico)
function hideFooterButton(pannello) {
    var footer = pannello.find("div.panel-footer");
    footer.empty();
}

//RICHIESTE DI MODIFICA

//Richiesta modificia dati info Appartamento
function sendEditAppartamentoRequest() {
    console.log("INVIO RICHIEDA MODIFICA INFO APPARTAMENTO");

    $("form#form-info-appartamento").ajaxSubmit({
        dataType: "json",
        success: function (response) {
            console.log(response.response);
            console.log(response.data);
        }
    });
}


//FUNZIONI DI VERIFICA DATI

//Verifica dati info Appartamento
function checkInfoApp() {

    var descrizioneTag = pannInfo.find("#textDescrizione");
    var metraturaTag = pannInfo.find("#inpMetratura");          //da aggiungere il controllo sui dati
    var dataInizio = pannInfo.find("input#inpDataInizio");
    var met = metraturaTag.val()=="" || parseInt(metraturaTag.val())>=0;
    if(metraturaTag.val()==""){
         metraturaTag.val(0);
    }
    
        return descrizioneTag.val() != "" && dataInizio.val() != "" && met;
    


}

function setMyAnnuncio(ann) {
    myAnnuncio = ann;
}


//FUNZIONI UTILI ALLA MODIFICA DELLE INFO COSTI

var flagAtomico; //  (atomico) true = costiAppartamento  - false = costiStanze

var divPrezzoApp; //div prezzo appartamento

var divPrezzoSta; // div prezzo stanza



function aggiornaInfoCosti(ann) {
    var atomico = ann.Atomico;
    var condominio = false;
    var riscaldamento = false;
    var selModelloCosti = pannCosti.find("select#modelloCosti");


    divPrezzoApp = pannCosti.find("div#prezzoAppartamento");
    divPrezzoSta = pannCosti.find("div#prezzoStanze");

    divPrezzoApp.hide();
    divPrezzoSta.hide();

    if (atomico) {
        //alert("Appartamento Atomico");

        selModelloCosti.find("option[value=1]").attr("selected", "selected");
        selModelloCosti.find("option[value=2]").removeAttr("selected", "selected");


        divPrezzoApp.show();
        divPrezzoApp.find("input#prezzoA").val(ann.Prezzo);

        condominio = ann.CompresoCondominio;
        riscaldamento = ann.CompresoRiscaldamento;
        flagAtomico = true;



    } else {
        selModelloCosti.find("option[value=2]").attr("selected", "selected");
        selModelloCosti.find("option[value=1]").removeAttr("selected", "selected");
        divPrezzoSta.show();
        divPrezzoSta.empty();
        divPrezzoSta.append("Per modificare il prezzo delle stanze, seleziona Modifica Stanze");

        var temp = ann.Stanze[0];
        var trovato = false;
        var j = 0;
        while(!trovato && j<temp.length){
            if(temp[j].SuperTipo == "StanzaInAffitto") {
                condominio = temp[j].compresoCondominio;
                riscaldamento = temp[j].compresoRiscaldamento;
                trovato = true;
               
            }
            j++;
        }
        
        flagAtomico = false;
    }

    if (condominio) {
        pannCosti.find("input#CC").attr("checked", "checked");
    }else{
        pannCosti.find("input#CC").removeAttr("checked", "checked");
    }
    if (riscaldamento) {
        pannCosti.find("input#CR").attr("checked", "checked");

    }else{
        pannCosti.find("input#CR").removeAttr("checked", "checked");
    }



}

function cambiaSpecificheCosti() {

    if (flagAtomico) {
        //prezzo riferito all'appartamenro  --> prezzo riferito alle camere
        divPrezzoApp.hide();
        divPrezzoSta.show();
        divPrezzoSta.empty();
        var atomico = myAnnuncio.Atomico;
        if (atomico) {
            var listaStanze = myAnnuncio.Stanze[0];
            for (var i = 0; i < listaStanze.length; i++) {
                var s = listaStanze[i];
                if (s.SuperTipo == "StanzaInAffitto") {
                    var htmlCode = getHTMLprezzoStanza(s);
                    divPrezzoSta.append(htmlCode);
                }
            }
        }else{
            divPrezzoSta.append("Per modificare il prezzo delle stanze, seleziona Modifica Stanze");

        }

    } else {

        //prezzo riferito alle camere  --> prezzo riferito all'appartamento
        divPrezzoSta.hide();
        divPrezzoApp.show();

    }

    flagAtomico = !flagAtomico;


}

function sendEditCosti(){
    var hiddenInfo;
    var doNothing = false;
    if(flagAtomico){
        
        
        if(myAnnuncio.Atomico){
            //è atomico ed era atomico
            var prezzo = divPrezzoApp.find("input#prezzoA").val();
            var condominio = pannCosti.find("input#CC").prop("checked");
            var riscaldamento = pannCosti.find("input#CR").prop("checked");
                        
            if(!((prezzo == myAnnuncio.Prezzo) && !XOR(condominio,myAnnuncio.CompresoCondominio) && !XOR(riscaldamento,myAnnuncio.CompresoRiscaldamento))) {
                hiddenInfo = "<input type=\"hidden\" name=\"info\" class=\"infoCosti\"  value=\"atomico-atomico\">";
            }else{
                //Non ci sono state modifiche 
                    doNothing = true;
            }    
            
        }else{
            //è a stanze ed era atomico 
            hiddenInfo = "<input type=\"hidden\" name=\"info\" class=\"infoCosti\"  value=\"stanze-atomico\">";
        }
        
    }else{
        
        
        if(myAnnuncio.Atomico){
            //era atomico ore è a stanze
            hiddenInfo = "<input type=\"hidden\" name=\"info\" class=\"infoCosti\"  value=\"atomico-stanze\">";
            
        }else{
            //era a stanze ora è a stanze
            //DO NOTHING
                var temp = myAnnuncio.Stanze[0];
                var cc = temp[0].compresoCondominio;
                var cr = temp[0].compresoRiscaldamento;
        
                var condominio = pannCosti.find("input#CC").prop("checked");
                var riscaldamento = pannCosti.find("input#CR").prop("checked");
                if(!XOR(condominio,cc) && !XOR(riscaldamento,cr) ){
                        //Non ci sono state modifiche 
                        doNothing = true;
                        
                }else{
                hiddenInfo = "<input type=\"hidden\" name=\"info\" class=\"infoCosti\" value=\"stanze-stanze\">";
                }
        }
    }
    
    var formInfoCosti = pannCosti.find("#form-edit-costi");
    formInfoCosti.append(hiddenInfo);
    
    formInfoCosti.ajaxSubmit({
        dataType: "json",
        success: function (response) {
            var esito = response.response;
            console.log(response);
            if(esito=="OK"){
            console.log(response.data);
            myAnnuncio = response.data;
            aggiornaInfoCosti(myAnnuncio);
            //alert("risposta" + response.action);
            
            if(response.action=="nascondi-prezzo-stanze"){
                nascondiPrezzoStanze();
            }else if(response.action = "mostra-prezzo-stanze"){
                mostraPrezzoStanze(myAnnuncio.Stanze[0]);
            }

            }
            

        }
    });
    
    formInfoCosti.find("input.infoCosti").remove();

}

function cancellaStanza(){
            //ricavo informazioni sulla stanza
        var navContent = pannStanze.find(".tab-content");
        var stanzaContent = navContent.find("div.active");
        var idStanza = stanzaContent.attr("id").slice(6);

        var stanze = myAnnuncio.Stanze[0];
        var s = stanze[idStanza];

            eliminaStanza(s);

}

//TODO da sviluppare il check dei costi e check stanze
function checkCosti(){
    var selModelloCosti = pannCosti.find("select#modelloCosti");
    var modelloCode = selModelloCosti.val();
    if(modelloCode == "1"){
        //prezzo atomico
        var PrezzoA = pannCosti.find("input#prezzoA");
        return PrezzoA.val()!="" && parseInt(PrezzoA.val())>0;
    }else{
        //prezzo a stanze
        var PrezzoS = pannCosti.find("input.prezzoS");
        var result = true;
        for(var i=0;i<PrezzoS.length;i++){
            result = result && $(PrezzoS[i]).val()!="" && parseInt($(PrezzoS[i]).val())>0;
        }
        return result;
    }
    
}



function XOR(A,B){
    return (A && !B) || (!A && B);
}




