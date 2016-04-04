var auth2 = {};


jQuery(document).ready(function ($) {
    $('#googleplus-locatore').on('click', function () {
        $('#form-login-googleplus').find('input[name="action"]').val('login-googleplus-locatore');
    //    $('#form-login-googleplus').submit();
    });

    $('#googleplus-studente').on('click', function () {
        $('#form-login-googleplus').find('input[name="action"]').val('login-googleplus-studente');
    //    $('#form-login-googleplus').submit();
    });
}); 

/* This method sets up the sign-in listener after the client library loads.*/
function startApp() {
    //gapi.load carica dinamicamente librerie js
    gapi.load('auth2', function () {
        gapi.client.load('plus', 'v1').then(function () {
            gapi.signin2.render('signin-googleplus-studente', {
                scope: 'https://www.googleapis.com/auth/plus.login',
                fetch_basic_profile: false,
                theme: 'dark',
                width: 568,
                height: 60
            });
            gapi.signin2.render('signin-googleplus-locatore', {
                scope: 'https://www.googleapis.com/auth/plus.login',
                fetch_basic_profile: false,
                theme: 'dark',
                width: 568,
                height: 60
            });

            gapi.auth2.init({fetch_basic_profile: false,
                scope: 'https://www.googleapis.com/auth/plus.login'}).then(
                    function () {
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
        var authResponse = authResult.currentUser.get().getAuthResponse();
        var $form = $('#form-login-googleplus');

        $form.find('input[name="id_token"]').val(authResponse.id_token);
        $form.find('input[name="access_token"]').val(authResponse.access_token);

        gapi.client.plus.people.get({'userId': 'me'}).then(
                function (profile) {
                    $form.find('input[name="name"]').val(profile.result.name.givenName);
                    $form.find('input[name="surname"]').val(profile.result.name.familyName);
                    /* The URL of the person's profile photo. 
                     * To resize the image and crop it to a square, append the query string 
                     * ?sz=x, where x is the dimension in pixels of each side. */
                    $form.find('input[name="url-profile-img"]').val(profile.result.image.url);
                    // $form.submit(); 

                    //   $('#userParameters').submit();     
                },
                function (err) {
                    ; // da gestire più avanti magari
                    /*var error = err.result;
                     $('#profile').append(error.message); */
                }
        );
    } else if (authResult['error'] || authResult.currentUser.get().getAuthResponse() === null) {
        // There was an error, which means the user is not signed in.
        // As an example, you can handle by writing to the console:
        console.log('There was an error: ' + authResult['error']);
        /*
         $('#authResult').append('Logged out');
         $('#authOps').hide('slow');
         $('#gConnect').show(); */
    }

    //   console.log('authResult', authResult);
}

/*  Calls the OAuth2 endpoint to disconnect the app for the user. */
function disconnect() {
    auth2.disconnect();
}

/* Handler for when the sign-in state changes.
 * @param {boolean} isSignedIn The new signed in state. */
function updateSignIn() {
    if (auth2.isSignedIn.get()) {
        console.log('update sign in state...signed in');
    //    alert("login"); 
        onSignInCallback(gapi.auth2.getAuthInstance());
    }
    else { 
        console.log('update sign in state...signed out'); 
    }
    
}


/*
 function Login() {
 $('#userParameters').submit();
 }
 
 function Logout() {
 ;
 } */