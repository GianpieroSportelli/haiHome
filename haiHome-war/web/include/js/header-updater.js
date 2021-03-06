/* 
 * Aggiornamento dell'header e suo funzionamento 
 * in base allo stato della sessione utente 
 */
jQuery(document).ready(function ($) {
    var user_type;
    var user_data;
    var current_url = $('#__current_url').text();

    console.log('current url: ' + current_url);

    /* INIT */
    $(function () {
        user_type = $('#__user_type').text();
        //JQuery.parseJSON("null") = null <3 
        if (user_type !== "admin") {
            user_data = jQuery.parseJSON($('#__user_data').text());
        }

        //  console.log(user_data); 
        console.log("Init header. Stato: " + (user_type === "null" ? "non" : "") + " loggato");

        if (user_type === "null") {
            $('#logged-as').parent().hide();
            $('#go-profile').parent().hide();
            $('#accesso').parent().show();
            $('#logout').parent().hide();
        } else {
            if (user_type !== "admin") {
                $('#logged-as').html("Benvenuto,<b>&nbsp;&nbsp;" + user_data.nome + "</b>");
            }
            $('#logged-as').parent().show();
            $('#go-profile').parent().show();
            $('#accesso').parent().hide();
            $('#logout').parent().show();
        }
    });

    /* Bottoni */
    $('#logout').on('click', function () {
        console.log("LOGOUT");
        $.post(
                "ServletController",
                {
                    'action': 'user-logout'
                },
                function (response) {
                    if (response === "ok")
                        location.replace("index.jsp");
                }
        );
    });

    $('#go-profile').on('click', function () {
        console.log("goto " + user_type + "-profile.jsp");
        location.replace(user_type + "-profile.jsp");
    });

    $('#help').on('click', function () {
        alert("nope");
    });
    
    /* Apertura modal login admin - footer */
    $('#admin-access').on('click', function () {
        $.post("ServletController", {action: "admin-get-session"}, function (response) {
            if (response === "OK") {
                location.assign("admin-profile.jsp"); 
            } else {
                $('#adminModal').modal('show');
            } 
        });
    });
    
    
}); 