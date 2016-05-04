/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function loadSegnalazioni() {
    console.log("load");
    $.post("ServletController",
            {action: "Segnalazione-getAllSegnalazioni"},
            function (responseJson) {
                console.log(responseJson);
                $.each(responseJson, function (index, risp) {
                    var html="";
                    html+="<p>Annuncio "+(index+1)+" : " + JSON.stringify(risp.Annuncio) + "</p>";
                    html+="<p>Studenti: " + JSON.stringify(risp.Studenti) + "</p>";
                    html+="<p>N_Segnalazioni: " + JSON.stringify(risp.NSegn) + "</p>";
                    $("#segnalazioni").html(html);
                });
            });
}
