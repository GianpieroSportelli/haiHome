<%-- 
    Document   : googleplus
    Created on : 4-mar-2016, 19.31.31
    Author     : Nicola Licheri 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Google+ Login</title>

        <script src="https://apis.google.com/js/client:platform.js?onload=startApp" async defer></script>
    <!-- JavaScript specific to this application that is not related to API calls -->
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js" ></script>
        <meta name="google-signin-client_id" content="495487496441-r9l7mppbotcf6i3rt3cl7fag77hl0v62.apps.googleusercontent.com"></meta>
        
        
        <script>
var auth2 = {}; 

/* jQuery initialization */
$(document).ready(function() {
  $('#disconnect').click(disconnect);
  $('#loaderror').hide();
  if ($('meta')[0].content === 'YOUR_CLIENT_ID') {
    alert('This sample requires your OAuth credentials (client ID) ' +
        'from the Google APIs console:\n' +
        '    https://code.google.com/apis/console/#:access\n\n' +
        'Find and replace YOUR_CLIENT_ID with your client ID.'
    );
  }
});

/* This method sets up the sign-in listener after the client library loads. */
function startApp() {
    //gapi.load carica dinamicamente librerie js
  gapi.load('auth2', function() {
    gapi.client.load('plus','v1').then(function() {
      gapi.signin2.render('signin-button', {
          scope: 'https://www.googleapis.com/auth/plus.login',
          fetch_basic_profile: false });
      gapi.auth2.init({fetch_basic_profile: false,
          scope:'https://www.googleapis.com/auth/plus.login'}).then(
            function (){
              console.log('init');
              auth2 = gapi.auth2.getAuthInstance();
              auth2.isSignedIn.listen(updateSignIn);
              auth2.then(updateSignIn());
            });
    });
  });
}


/**  * Hides the sign in button and starts the post-authorization operations.
     * @param {Object} authResult An Object which contains the access token and
     * other authentication information. */
function onSignInCallback(authResult) {
    if (authResult.isSignedIn.get()) {
        $('#authOps').show('slow');
   //     $('#gConnect').hide();
        
        $('#userParameters').css("display", "inline"); 

        var authResponse = authResult.currentUser.get().getAuthResponse();
        $('#id_token').val(authResponse.id_token);
        $('#id_access_token').val(authResponse.access_token);
        
        getProfileData();        
      } 
      else if (authResult['error'] || authResult.currentUser.get().getAuthResponse() === null) {
        // There was an error, which means the user is not signed in.
        // As an example, you can handle by writing to the console:
        console.log('There was an error: ' + authResult['error']);
        $('#authResult').append('Logged out');
        $('#authOps').hide('slow');
        $('#gConnect').show();
      }

      console.log('authResult', authResult);
}

/* Handler for when the sign-in state changes.
 * @param {boolean} isSignedIn The new signed in state. */
function updateSignIn() {
  console.log('update sign in state');
  if (auth2.isSignedIn.get()) {
    console.log('signed in');
    onSignInCallback(gapi.auth2.getAuthInstance());
  }else{
    console.log('signed out');
    onSignInCallback(gapi.auth2.getAuthInstance());
  }
}


function getProfileData() {    
    gapi.client.plus.people.get({'userId': 'me'}).then(function(res) { 
        var profile = res.result; 
        
        var name = profile.name.givenName; 
        var surname = profile.name.familyName;
        
        $('#id_name').val(name);        
        $('#id_surname').val(surname); 
     //   $('#userParameters').submit();     
      }, function(err) {
        var error = err.result;
        $('#profile').empty();
        $('#profile').append(error.message);
      }
    ); 
}


function Login() { $('#userParameters').submit(); }
function Logout() {  ; }
/*  Calls the OAuth2 endpoint to disconnect the app for the user. */
function disconnect() { auth2.disconnect(); }



        </script>
    </head>
    <body>
        <form id="userParameters" action="ServletLogin" method="post" style="display:none">
            <input type="hidden" name="action" value="loginGooglePlus">
            
            <input id="id_name" type="text" name="name" value="null">
            <input id="id_surname" type="text" name="surname" value="null">
        <!--    <input id="id_email" type="email" name="email"> -->
            <input id="id_token" type="text" name="idToken" value="null">
            <input id="id_access_token" type="text" name="access_token" value="null">
            
            <input type="submit" value="submit">
            
            <input type="button" value="Login" onclick="Login();" />
            <input type="button" value="Logout" onclick="Logout();" />
        </form>
    </body>
</html>
