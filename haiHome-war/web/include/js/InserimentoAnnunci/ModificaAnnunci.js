/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    
    
    initalEditFunction();
    
    
});


function initalEditFunction(){
    
    var oid = "905";
    
    console.log("INVIO RICHIEDA INIZIALE DI MODIFICA");
    
    $.post("ServletAnnuncio",
            {action: "Annunci-editAnnuncio-initialRequest",
            idAnnuncio: oid},
            function (msg) {
                var esito = msg.response;
                if(esito == "OK"){
                    var annuncio = msg.data;
                    console.log(annuncio);
                }else{
                    console.log("La servlet non ha risposto OK");
                }
            });
}