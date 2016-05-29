window.fbAsyncInit = function () {
    FB.init({
        appId: '785498704895774',
        cookie: true, // enable cookies to allow the server to access 
        // the session
        xfbml: true, // parse social plugins on this page
        version: 'v2.5'
    });
};
// Load the SDK asynchronously
(function (d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id))
        return;
    js = d.createElement(s);
    js.id = id;
    js.src = "//connect.facebook.net/en_US/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

function LoginFB(clicked_id) {
    FB.login(function (response) {
        if (response.authResponse) {
            //Controllo permessi
            var declined = [];
            FB.api('/me/permissions', function (permessi) {
                for (i = 0; i < permessi.data.length; i++) {
                    if (permessi.data[i].status == 'declined') {
                        declined.push(permessi.data[i].permission);
                    }
                }
                if (declined.length != 0) {
                    alert("Permessi mancanti: " + declined.toString() + ". Per garantire il funzionamento di haiHome? è necessario fornire le poche informazioni richieste. La preghiamo di ricontrollare i permessi.");
                } else
                    getData(clicked_id);
            });
        } else {
            alert("Login con Facebook non riuscito. Riprova.");
        }
    }, {scope: 'email,public_profile'});
}
function getData(clicked_id) {
    var dati;
    var email;

    FB.api('/me?fields=id,first_name,last_name,email', function (response) {
        //response.name       - User Full name
        //response.link       - User Facebook URL
        //response.username   - User name
        //response.id         - id
        //response.email      - User email
        //
        dati = response.first_name + ',' + response.last_name;
        email = response.email;

        document.getElementById("userData").value = dati;
        document.getElementById("mailUser").value = email;
        document.getElementById("profilo").value = "http://graph.facebook.com/" + response.id + "/picture?type=large";
        if (clicked_id.localeCompare("facebook_studente") === 0) {
            document.getElementById('azione').value = "loginFacebookStudente";
        } else {
            document.getElementById('azione').value = "loginFacebookLocatore";
        }

        //Chiamo la servlet
        document.getElementById("formLogin").submit();
    });
}