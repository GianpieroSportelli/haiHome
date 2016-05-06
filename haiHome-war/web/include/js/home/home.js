/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var city = "Torino";


$(document).ready(function () {
    console.log("OK");
    $("#search").click(function () {
        goToSearch();
    });
    
    
    $("#in_locatore").on('click', function() {
        $('#modalLogin').modal('show');
    }); 
});

function goToSearch() {
    var search_page="./search-page.jsp";
    $.ajax({
        url: "ServletController",
        type: 'post',
        //dataType: 'json',
        data: {action: "Ricerca-setCity", city: city},
        success: function (response) {
            if(response=="true"){
            window.location=search_page;
        }else{
            console.log("Errore apertura ricerca");
        }
    }
    });
}
