<!doctype html>
<html lang="en" class="no-js">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link href='http://fonts.googleapis.com/css?family=PT+Sans:400,700' rel='stylesheet' type='text/css'>

        <link rel="stylesheet" href="include/css/login//reset.css"> <!-- CSS reset -->
        <link rel="stylesheet" href="include/css/login/style2.css"> <!-- Gem style -->
        <script src="include/js/login/modernizr.js"></script> <!-- Modernizr -->
        <!-- robe del login bello -->
        <link rel="stylesheet" href="include/css/login/normalize.css">
        <link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'>
        <link rel="stylesheet" href="include/css/login/style.css">

        <title>Log In &amp; Sign Up Form</title>
    </head>
    <body>
        <header role="banner">
            <nav class="main-nav">
                <ul>
                    <!-- inser more links here -->
                    <li><a class="cd-signin" href="#0">Sign in</a></li>
                    <li><a class="cd-signup" href="#0">Sign up</a></li>
                </ul>
            </nav>
        </header>

        <div class="cd-user-modal"> <!-- this is the entire modal form, including the background -->
            <div class="cd-user-modal-container"> <!-- this is the container wrapper -->
                <ul class="cd-switcher">
                    <li><a href="#0">Accesso Studenti</a></li>
                    <li><a href="#0">Accesso Locatori</a></li>
                </ul>

                <div id="cd-login"> 
                    <div class="logmod__alter">
                        <div class="logmod__alter-container">
                            <a href="#" class="connect facebook">
                                <div class="connect__icon">
                                    <i class="fa fa-facebook"></i>
                                </div>
                                <div class="connect__context">
                                    <span>Accedi con <strong>Facebook</strong></span>
                                </div>
                            </a>
                            <a href="#" class="connect googleplus">
                                <div class="connect__icon">
                                    <i class="fa fa-google-plus"></i>
                                </div>
                                <div class="connect__context">
                                    <span>Accedi con <strong>Google+</strong></span>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div class="logmod__heading">
                        <span class="logmod__heading-subtitle">
                            Oppure inserisci le tue <strong>credenziali</strong>
                        </span>
                    </div> 
                    <div class="logmod__form">
                        <form accept-charset="utf-8" action="#" class="simform">
                            <div class="sminputs">
                                <div class="input full">
                                    <label class="string optional" for="user-name">Email*</label>
                                    <input class="string optional" maxlength="255" id="user-email" placeholder="Email" type="email" size="50" />
                                </div>
                            </div>
                            <div class="sminputs">
                                <div class="input full">
                                    <label class="string optional" for="user-pw">Password *</label>
                                    <input class="string optional" maxlength="255" id="user-pw" placeholder="Password" type="password" size="50" />
                                    <span class="hide-password">Show</span>
                                </div>
                            </div>
                            <div class="simform__actions">
                                <input class="sumbit" name="commit" type="sumbit" value="Log In" />
                            </div> 
                        </form>
                    </div>
                    
                    <!--
                    <form class="cd-form">
                        <p class="fieldset">
                            <label class="image-replace cd-email" for="signin-email">E-mail</label>
                            <input class="full-width has-padding has-border" id="signin-email" type="email" placeholder="E-mail">
                            <span class="cd-error-message">Error message here!</span>
                        </p>

                        <p class="fieldset">
                            <label class="image-replace cd-password" for="signin-password">Password</label>
                            <input class="full-width has-padding has-border" id="signin-password" type="text"  placeholder="Password">
                            <a href="#0" class="hide-password">Hide</a>
                            <span class="cd-error-message">Error message here!</span>
                        </p>

                        <p class="fieldset">
                            <input type="checkbox" id="remember-me" checked>
                            <label for="remember-me">Remember me</label>
                        </p>

                        <p class="fieldset">
                            <input class="full-width" type="submit" value="Login">
                        </p>
                    </form>
                    -->
                    <p class="cd-form-bottom-message">
                        <a href="#0"> Non hai un account? </a>
                    </p> <!-- -->
                    <!-- <a href="#0" class="cd-close-form">Close</a> -->
                </div> <!-- cd-login -->

                <div id="cd-signup"> <!-- sign up form -->
                    
                    <!---------------------- -->
                    <div class="logmod__alter">
                        <div class="logmod__alter-container">
                            <a href="#" class="connect facebook">
                                <div class="connect__icon">
                                    <i class="fa fa-facebook"></i>
                                </div>
                                <div class="connect__context">
                                    <span>Accedi con <strong>Facebook</strong></span>
                                </div>
                            </a>
                            <a href="#" class="connect googleplus">
                                <div class="connect__icon">
                                    <i class="fa fa-google-plus"></i>
                                </div>
                                <div class="connect__context">
                                    <span>Accedi con <strong>Google+</strong></span>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div class="logmod__heading">
                        <span class="logmod__heading-subtitle">
                            Oppure inserisci le tue <strong>credenziali</strong>
                        </span>
                    </div> 
                    <div class="logmod__form">
                        <form accept-charset="utf-8" action="#" class="simform">
                            <div class="sminputs">
                                <div class="input full">
                                    <label class="string optional" for="user-name">Email*</label>
                                    <input class="string optional" maxlength="255" id="user-email" placeholder="Email" type="email" size="50" />
                                </div>
                            </div>
                            <div class="sminputs">
                                <div class="input full">
                                    <label class="string optional" for="user-pw">Password *</label>
                                    <input class="string optional" maxlength="255" id="user-pw" placeholder="Password" type="password" size="50" />
                                    <span class="hide-password">Show</span>
                                </div>
                            </div>
                            <div class="simform__actions">
                                <input class="sumbit" name="commit" type="sumbit" value="Log In" />
                            </div> 
                        </form>
                    </div>
                    
                    <!--
                    <form class="cd-form">
                        <p class="fieldset">
                            <label class="image-replace cd-email" for="signin-email">E-mail</label>
                            <input class="full-width has-padding has-border" id="signin-email" type="email" placeholder="E-mail">
                            <span class="cd-error-message">Error message here!</span>
                        </p>

                        <p class="fieldset">
                            <label class="image-replace cd-password" for="signin-password">Password</label>
                            <input class="full-width has-padding has-border" id="signin-password" type="text"  placeholder="Password">
                            <a href="#0" class="hide-password">Hide</a>
                            <span class="cd-error-message">Error message here!</span>
                        </p>

                        <p class="fieldset">
                            <input type="checkbox" id="remember-me" checked>
                            <label for="remember-me">Remember me</label>
                        </p>

                        <p class="fieldset">
                            <input class="full-width" type="submit" value="Login">
                        </p>
                    </form>
                    -->
                    <p class="cd-form-bottom-message">
                        <a href="#0"> Non hai un account? </a>
                    </p>
                    <!--------------------- -->
                    
                    

                    <!-- <a href="#0" class="cd-close-form">Close</a> -->
                </div> <!-- cd-signup -->

                <div id="cd-reset-password"> <!-- signup form -->

                    <div class="logmod__heading">
                        <span class="logmod__heading-subtitle">Enter your personal details <strong>to create an acount</strong></span>
                    </div>
                    <div class="logmod__form">
                        <form accept-charset="utf-8" action="#" class="simform">
                            <div class="sminputs">
                                <div class="input full">
                                    <label class="string optional" for="user-name">Email*</label>
                                    <input class="string optional" maxlength="255" id="user-email" placeholder="Email" type="email" size="50" />
                                </div>
                            </div>
                            <div class="sminputs">
                                <div class="input string optional">
                                    <label class="string optional" for="user-pw">Password *</label>
                                    <input class="string optional" maxlength="255" id="user-pw" placeholder="Password" type="text" size="50" />
                                </div>
                                <div class="input string optional">
                                    <label class="string optional" for="user-pw-repeat">Repeat password *</label>
                                    <input class="string optional" maxlength="255" id="user-pw-repeat" placeholder="Repeat password" type="text" size="50" />
                                </div>
                            </div>
                            <div class="simform__actions">
                                <input class="sumbit" name="commit" type="sumbit" value="Create Account" />
                                <span class="simform__actions-sidetext">By creating an account you agree to our <a class="special" href="#" target="_blank" role="link">Terms & Privacy</a></span>
                            </div> 
                        </form>
                    </div> 
                    <p class="cd-form-bottom-message"><a href="#0">Torna al login</a></p>
                </div> <!-- cd-reset-password -->
                <a href="#0" class="cd-close-form">Close</a>
            </div> <!-- cd-user-modal-container -->
        </div> <!-- cd-user-modal -->
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
        <script src="include/js/login/main.js"></script> <!-- Gem jQuery -->
    </body>
</html>