/* 
 * Aggiornamento dell'header e suo funzionamento 
 * in base allo stato della sessione utente 
 */
jQuery(document).ready(function ($) {
    var user_type;
    var user_data; 
    /* INIT */
    $(function () {
        user_type = $('#__user_type').text();
        user_data = jQuery.parseJSON($('#__user_data').text()); 
        
        
        
        console.log("USER DATA: " + user_data.nome); 
        console.log("Init header per utente " + (user_type === "null" ? "non" : "") + " loggato");

        if (user_type === "null") {
            $('#logged-as').parent().hide();
            $('#go-profile').parent().hide();
            $('#accesso').parent().show();
            $('#logout').parent().hide();
        } else {
           // $('#go-profile').attr('href', user_type + "-profile.jsp");
            $('#logged-as').text("Benvenuto, " + user_data.nome); 
            $('#logged-as').parent().show();
            $('#go-profile').parent().show();
            $('#accesso').parent().hide();
            $('#logout').parent().show();
        }
    });

    /* Bottoni */
    $(document).on('click', '#logout', function (event) {
        console.log("LOGOUT");
        $.post(
                "ServletController",
                {
                    'action': 'user-logout'
                },
                function (response) {
                    location.replace("index.jsp");
                }
        );
    });
    
    $(document).on('click', '#go-profile', function (event) {
    //    var json = jQuery.parseJSON($('#__user_data').text());
    //    console.log(json); 
        location.replace(user_type + "-profile.jsp");
    });
}); 