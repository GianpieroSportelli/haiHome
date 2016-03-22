/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var jsonAnn = [];

function prova(){
        
        /* controllo se il prezzo è stato inserito per stanze */
        var atomico;
        var value = $( "#selStanza option:selected" ).val(); 
        console.log(value);
        if(value==1){
             console.log("Il Prezzo è riferito all'appartamento");
             atomico = true;
        }else{
            console.log("Il Prezzo è riferito alle singole Stanze");
            atomico = false;
        }


        var citt = $("#selCitta").val();
        var quart = $("#selQuartiere").val();
        var ind = $("#inpIndirizzo").val();
        var civ = $("#inpCivico").val();
        console.log(citt);
        
        itemCitta = {};
        itemCitta ["Città"] = citt;
        
        itemQuart = {};
        itemQuart ["Quartiere"] = quart;
        
        
        itemIndir = {};
        
        itemIndir ["Indirizzo"] = ind + " , " + civ;
        

        jsonAnn.push(itemCitta);
        jsonAnn.push(itemQuart);
        jsonAnn.push(itemIndir);
                
        var descCasa = $("#textDescrizione").val();
        var metCasa = $("#inpMetratura").val();  
        
        itemDesc = {};
        itemDesc ["Descrizione"] = descCasa;
        
        itemMetCasa = {};
        itemMetCasa ["Metratura"] = metCasa;
        
        jsonAnn.push(itemDesc);
        jsonAnn.push(itemMetCasa);
        
        /* da aggiungere data di inizio affitto */
        
        /*Inserimento Stanze*/
        createStanzaJSON();

        
        
        console.log(jsonAnn);
    }
    
    function createStanzaJSON(atomico){ //da inserire le foto
        console.log("entro 1");
        
        $(".Stanza").each(function() {
            console.log("entro 2");
            var idStanza = $(this).attr("id");
            var tipologiaStanza = $(this).contents().find("#selStanza").val();
            if(tipologiaStanza==1){  //da aggiungere val() al DOM
                console.log("Camera da letto");
                var tipoStanza = $(this).contents().find("#seltipoStanza").val();
                var Metratura = $(this).contents().find("#inpMetratura").val();
                console.log(idStanza + " " + tipologiaStanza + " " + tipoStanza + " " + Metratura);
                            /* controllo se il prezzo è stato inserito per stanze */
            if(!atomico){
                console.log("Calcolo il prezzo Stanze");
                // <div class=\"form-group\" id=\"prezzo$_$_$\">
                 var selStanza = $("#prezzo" + idStanza);
                 var prezzoS = selStanza.contents().find(".prezzoStanza");
                 var compCond = selStanza.contents().find(".CompCond");
                 var compRisc = selStanza.contents().find(".CompRisc");
                 console.log("Stanza " + idStanza + "valori: " + prezzoS + " " + compCond + " " +compRisc);

            }
            }else{
                var tipoStanza = $(this).contents().find("#seltipoStanza").val();
                var Metratura = $(this).contents().find("#inpMetratura").val();
                console.log("Stanza " + idStanza + "valori: " + Metratura + " " + tipoStanza);
            }

            
            
        

         
         
        
    });
        
    }