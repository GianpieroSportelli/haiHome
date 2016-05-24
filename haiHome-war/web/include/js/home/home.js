/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var city = "Torino";


$(document).ready(function () {
    $("#search").click(function () {
        goToSearch();
    });


    $("#in_locatore").on('click', function () {
        $.post("ServletController", {action: "locatore-get-session"}, function (responseJSON) {
            if (jQuery.isEmptyObject(responseJSON)) {
                $('#modalLogin').modal('show');
            } else if ($('#__user_type').text() === "locatore") {
                location.replace("IA0-InserimentoAnnunci.jsp");
            } else {
                console.log("utente non loggato come locatore");
            }
        });

    });
});


//Funzione richiamata alla pressione del cerca nella home page.
function goToSearch() {
    var search_page = "./search-page.jsp";
    $.ajax({
        url: "ServletController",
        type: 'post',
        data: {action: "Ricerca-setCity", city: city},
        success: function (response) {
            if (response == "true") {
                window.location = search_page;
            } else {
                alert("Errore apertura ricerca! contatta l'admin");
            }
        }
    });
}
