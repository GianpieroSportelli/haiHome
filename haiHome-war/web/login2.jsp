<header role="banner">
    <nav class="main-nav">
        <ul>
            <li><a class="cd-signup" href="#0">Accesso</a></li> 
        </ul>
    </nav>
</header>

<div class="cd-user-modal" > <!-- this is the entire modal form, including the background -->
    <div class="cd-user-modal-container"> <!-- this is the container wrapper -->
        <ul class="cd-switcher">
            <li><a href="#0">Accesso Studenti</a></li>
            <li><a href="#0">Accesso Locatori</a></li>
        </ul>

        <!-- Studente: Login -->
        <div id="cd-login-studente"> 
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
                <form id="studente-login" accept-charset="utf-8" action="#" class="simform">
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
                        <span class="simform__actions-sidetext"><a class="special" target="_blank" role="link">Password dimenticata?</a></span>
                    </div> 
                </form>
            </div>

            <p class="cd-form-bottom-message">
                <a href="#0"> Non hai un account? </a>
            </p> 
        </div> 

        <!-- Studente: Registrazione -->
        <div id="cd-registrazione-studente"> <!-- signup form -->
            <div class="logmod__heading">
                <span class="logmod__heading-subtitle">
                    Enter your personal details <strong>to create an account</strong>
                </span>
            </div>
            <div class="logmod__form">
                <form id="studente-reg" accept-charset="utf-8" action="#" class="simform">
                    <div class="sminputs">
                        <div class="input string optional">
                            <label class="string optional" for="user-name">Name*</label>
                            <input class="string optional" maxlength="255" id="user-name" placeholder="Il tuo nome" type="text" size="50" />
                        </div>
                        <div class="input string optional">
                            <label class="string optional" for="user-surname">Surname*</label>
                            <input class="string optional" maxlength="255" id="user-surname" placeholder="Il tuo nome" type="text" size="50" />
                        </div>

                    </div>
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
                        <span class="simform__actions-sidetext">By creating an account you agree to our <a class="special" target="_blank" role="link">Terms & Privacy</a></span>
                    </div> 
                </form>
            </div> 
            <p class="cd-form-bottom-message">
                <a href="#0">Torna al login</a>
            </p>
        </div>


        <!-- Locatore: Login -->
        <div id="cd-login-locatore"> <!-- sign up form -->
            <!---------------------- -->
            <div class="logmod__alter">
                <div class="logmod__alter-container">
                    <a href="#" class="connect facebook">
                        <div class="connect__icon">
                            <i class="fa fa-facebook"></i>
                        </div>
                        <div class="connect__context">
                            <span>Accedi con <strong>Dio</strong></span>
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
                <form id="locatore-login" accept-charset="utf-8" action="#" class="simform">
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
                        <span class="simform__actions-sidetext"><a class="special" target="_blank" role="link">Password dimenticata?</a></span>
                    </div> 
                </form>
            </div>

            <p class="cd-form-bottom-message2">
                <a href="#0"> Non hai un account? </a>
            </p>
        </div> <!-- cd-signup -->


        <!-- Locatore Registrazione -->
        <div id="cd-registrazione-locatore"> <!-- signup form -->
            <div class="logmod__heading">
                <span class="logmod__heading-subtitle">
                    Inserisci le informazioni richieste <strong>per creare un account</strong>
                </span>
            </div>
            <div class="logmod__form">
                <form id="locatore-reg" accept-charset="utf-8" action="#" class="simform">
                    <div class="sminputs">
                        <div class="input string optional">
                            <label class="string optional" for="user-name">Name*</label>
                            <input class="string optional" maxlength="255" id="user-name" placeholder="Il tuo nome" type="text" size="50" />
                        </div>
                        <div class="input string optional">
                            <label class="string optional" for="user-surname">Surname*</label>
                            <input class="string optional" maxlength="255" id="user-surname" placeholder="Il tuo nome" type="text" size="50" />
                        </div>

                    </div>
                    <div class="sminputs">
                        <div class="input string optional">
                            <label class="string optional" for="user-phone">Phone*</label>
                            <input class="string optional" maxlength="255" id="user-phone" placeholder="Phone number" type="text" size="50" />
                        </div>
                        <div class="input string optional">
                            <label class="string optional" for="user-email">Email*</label>
                            <input class="string optional" maxlength="255" id="user-email" placeholder="Email" type="email" size="50" />
                        </div>

                        <!--
                        <div class="input full">
                            <label class="string optional" for="user-name">Email*</label>
                            <input class="string optional" maxlength="255" id="user-email" placeholder="Email" type="email" size="50" />
                        </div> -->
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
                        <span class="simform__actions-sidetext">By creating an account you agree to our <a class="special" target="_blank" role="link" disabled>Terms & Privacy</a></span>
                    </div> 
                </form>
            </div> 


            <p class="cd-form-bottom-message2">
                <a href="#0">Torna al login</a>
            </p>
        </div>

        <a href="#0" class="cd-close-form">Close</a>
    </div> <!-- cd-user-modal-container -->
</div> <!-- cd-user-modal -->
<!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script> -->


<script src="include/js/login/main.js"></script> <!-- Gem jQuery -->
