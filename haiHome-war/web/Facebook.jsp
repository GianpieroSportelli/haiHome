<%-- 
    Document   : Facebook
    Created on : 10-nov-2015, 17.37.20
    Author     : Eugenio Liso
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Facebook Login JavaScript Example</title>
        <meta charset="UTF-8">
    </head>
    <body>
        <script>

            // This is called with the results from from FB.getLoginStatus().
            function statusChangeCallback(response) {
                console.log('statusChangeCallback');
                console.log(response);
                // The response object is returned with a status field that lets the
                // app know the current login status of the person.
                // Full docs on the response object can be found in the documentation
                // for FB.getLoginStatus().
                if (response.status === 'connected') {
                    // Logged into your app and Facebook.
                    Login();
                } else if (response.status === 'not_authorized') {
                    // The person is logged into Facebook, but not your app.
                    document.getElementById('status').innerHTML = 'Please log ' +
                            'into this app.';
                } else {
                    // The person is not logged into Facebook, so we're not sure if
                    // they are logged into this app or not.
                    document.getElementById('status').innerHTML = 'Please log ' +
                            'into Facebook.';
                }
            }

            // This function is called when someone finishes with the Login
            // Button.  See the onlogin handler attached to it in the sample
            // code below.
            //function checkLoginState() {
            //    FB.getLoginStatus(function (response) {
            //        statusChangeCallback(response);
            //    });
            // }

            window.fbAsyncInit = function () {
                FB.init({
                    appId: '785498704895774',
                    cookie: true, // enable cookies to allow the server to access 
                    // the session
                    xfbml: true, // parse social plugins on this page
                    version: 'v2.5'
                });

                // Now that we've initialized the JavaScript SDK, we call 
                // FB.getLoginStatus().  This function gets the state of the
                // person visiting this page and can return one of three states to
                // the callback you provide.  They can be:
                //
                // 1. Logged into your app ('connected')
                // 2. Logged into Facebook, but not your app ('not_authorized')
                // 3. Not logged into Facebook and can't tell if they are logged into
                //    your app or not.
                //
                // These three cases are handled in the callback function.

                FB.getLoginStatus(function (response) {
                    statusChangeCallback(response);
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

            function Login() {
                FB.login(function (response) {
                    if (response.authResponse) {

                        //Controllo permessi
                        var declined = [];
                        FB.api('/me/permissions', function (permessi) {

                            for (i = 0; i < permessi.data.length; i++) {
                                if (permessi.data[i].status == 'declined') {
                                    declined.push(permessi.data[i].permission)
                                }
                            }

                            if (declined.length != 0) {
                                alert("Permessi mancanti: " + declined.toString() + ". Per garantire il funzionamento di haiHome? è necessario fornire le poche informazioni richieste. La preghiamo di ricontrollare i permessi.");
                            } else
                                getData();

                        });

                    } else {
                        alert("Login attempt failed!");
                    }
                }, {scope: 'email,public_profile'});
            }

            function getData() {
                var dati;
                var email;

                //prendo l'immagine
                //FB.api("/me/picture?width=180&height=180", function (response) {
                //     var profileImage = response.data.url.split('https://')[1]; //remove https to avoid any cert issues
                //document.getElementById("profileImage").setAttribute("src", "http://" + profileImage);
                //     document.getElementById("profilo").value = "http://" + profileImage;


                //   });


                //IMPORTANTE!! METTI I CAMPI CHE TI SERVONO ANCHE QUI DENTRO!!
                FB.api('/me?fields=id,first_name,last_name,email', function (response) {
                    //response.name       - User Full name
                    //response.link       - User Facebook URL
                    //response.username   - User name
                    //response.id         - id
                    //response.email      - User email
                    //

                    dati = response.first_name + ',' + response.last_name;
                    email = response.email;

                    document.getElementById("status").innerHTML = "Tuoi dati: " + dati;
                    document.getElementById("userEmail").innerHTML = "Tua mail: " + email;

                    //Passo i parametri
                    //Prendo nome+cognome
                    document.getElementById("userData").value = dati;                             //Prendo email
                    document.getElementById("mailUser").value = email;
                    document.getElementById("profilo").value = "http://graph.facebook.com/" + response.id + "/picture?type=large";

                    //Chiamo la servlet
                    document.getElementById("form").submit();
                });
            }

            function checkUser() {
                FB.getLoginStatus(function (response) {
                    logOut(response);
                });
            }

            function logOut(response) {
                if (response.status === 'connected') {
                    //Se l'utente è loggato, sloggalo
                    FB.logout(function () {
                        document.location.reload();
                    });
                } else {
                    //L'utente non è loggato, avvisalo
                    alert("Non sei loggato! Ti devi connettere per poter effettuare il logout");
                }
            }

        </script>

        <form id="form" method="POST" action="ServletController">
            <!-- Per far elaborare la richiesta dalla servlet -->
            <input type="hidden" name="action" value="loginFacebook">
            <input type="hidden" id="userData" name="userData">
            <input type="hidden" id="mailUser" name="mailUser">
            <input type="hidden" id="profilo" name="profilo">
        </form>

        <input type="button" value="Login" onclick="Login();" />
        <input type="button" value="Logout" onclick="checkUser();" />

        <!-- Per passare i parametri -->



        <div id="status"></div>
        <div id="userEmail"></div>
        <div id="userID"></div>

        <img id="profileImage">

    </body>    
</html>