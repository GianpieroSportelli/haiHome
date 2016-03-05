<%-- 
    Document   : header2
    Created on : 10-nov-2015, 10.07.01
    Author     : gianp_000
--%>
<%@page import="org.json.JSONObject"%> 
<%@page import="java.util.ArrayList"%> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>haiHome</title>

        <!-- Bootstrap -->
        <link rel="stylesheet" href="include/css/bootstrap.min.css">

        <script type="text/javascript">
            // Wait for the page to load first
            /* window.onload = function () {
             var a = document.getElementById("FacebookButton");
             
             //Set code to run when the link is clicked
             // by assigning a function to "onclick"
             a.onclick = function () {
             // Carica il file .js Facebook
             $.getScript("include/js/FacebookLogin.js", function () {
             });
             };
             };*/
        </script>
    </head>

    <body>
        <nav class="navbar navbar-default" role="navigation">
            <div class="container-fluid">

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav navbar-right">
                        <li class="dropdown">
                            <%
                                boolean loggedMenu = false;
                                Object log = request.getAttribute("Loggato");
                                if (log != null) {
                                    Boolean loggato = (Boolean) request.getAttribute("Loggato");

                                    if (loggato.booleanValue() == true) {
                                        loggedMenu = true;
                                        //JSONObject datiUtente = (JSONObject) request.getAttribute("JSONList");
                                        //System.out.println(datiUtente.toString());
                                    }
                                }

                                if (loggedMenu) {
                                    JSONObject datiUtente = (JSONObject) request.getAttribute("JSONList");
                            %>  
                            <div class="dropdown">
                                <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true">
                                    Ciao, <%= datiUtente.getString("Nome")%>
                                    <span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
                                    <li><a href="#">Profilo</a></li>
                                    <li><a href="#">Preferiti</a></li>
                                    <li class="divider"></li>
                                    <li>
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div>
                                                    <button onClick="checkUser();">Logout</button>
                                                </div>
                                            </div>
                                        </div>
                                </ul>
                            </div>
                            <%    } else { %> 
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><b>Login</b> <span class="caret"></span></a>
                            <ul id="login-dp" class="dropdown-menu">
                                <li>
                                    <div class="row">
                                        <div class="col-md-12">
                                            Login via
                                            <div class="social-buttons">
                                                <button id="FacebookButton" onclick="Login()">Facebook</button>
                                                <a href="#" class="btn btn-tw"><i class="fa fa-twitter"></i>Facebook</a>
                                                <a href="#" class="btn btn-tw"><i class="fa fa-twitter"></i>Google</a>
                                            </div>

                                            <form class="form" role="form" method="post" action="login" accept-charset="UTF-8" id="login-nav">
                                                <div class="form-group">
                                                    <label class="sr-only" for="exampleInputEmail2">Email address</label>
                                                    <input type="email" class="form-control" id="exampleInputEmail2" placeholder="Email address" required>
                                                </div>
                                                <div class="form-group">
                                                    <label class="sr-only" for="exampleInputPassword2">Password</label>
                                                    <input type="password" class="form-control" id="exampleInputPassword2" placeholder="Password" required>
                                                </div>
                                                <div class="form-group">
                                                    <button type="submit" class="btn btn-primary btn-block">Sign in</button>
                                                </div>
                                            </form>

                                            <div class="bottom text-center">
                                                New here ? <a href="#"><b>Join Us</b></a>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                            </ul>    
                            <% }%>

                        </li>
                    </ul>
                </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>

        <!-- FACEBOOK -->
        <form id="form" method="POST" action="ServletController">
            <!-- Per far elaborare la richiesta dalla servlet -->
            <input id="azione" type="hidden" name="action" value="loginFacebook">
            <input type="hidden" id="userData" name="userData">
            <input type="hidden" id="mailUser" name="mailUser">
            <input type="hidden" id="profilo" name="profilo">
        </form>
        <!-- Per passare i parametri -->
        <div id="status"></div>
        <div id="userEmail"></div>
        <div id="userID"></div>

        <img id="profileImage">
        <!-- FACEBOOK -->

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

                //FB.getLoginStatus(function (response) {
                //    statusChangeCallback(response);
                // });

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

                    //Passo i parametri
                    //Prendo nome+cognome
                    document.getElementById("userData").value = dati;                             //Prendo email
                    document.getElementById("mailUser").value = email;
                    document.getElementById("profilo").value = "http://graph.facebook.com/" + response.id + "/picture?type=large";

                    document.getElementById('azione').value = "loginFacebook";
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
                        document.getElementById('azione').value = "logoutFacebook";

                        //Chiamo la servlet
                        document.getElementById("form").submit();

                        document.location.reload();
                    });
                } else {
                    //L'utente non è loggato, avvisalo
                    alert("Non sei loggato! Ti devi connettere per poter effettuare il logout");
                }
            }

        </script>

        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="include/js/bootstrap.min.js"></script>
    </body>
</html>
