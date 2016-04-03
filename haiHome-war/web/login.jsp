<header role="banner">
    <nav class="main-nav">
        <ul>
            <li><a class="cd-signup" href="#myModal" data-toggle="modal">Accesso</a></li> 
            <li>
                <form id="form_logout" action="ServletController" method="POST">
                    <input type="hidden" name="action" value="user-logout" >
                    <a class="cd-signup" href="javascript:;" onclick="parentNode.submit();">Log out</a>
                </form>
            </li>
        </ul>

    </nav>
</header>

<div id="myModal" class="modal fade" role="dialog" data-backdrop="static">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <!--
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>-->
                <ul class="nav nav-pills nav-justified">
                    <li class="active"><a data-toggle="tab" href="#login-stud">Accesso Studente</a></li>
                    <li><a data-toggle="tab" href="#login-loc">Accesso Locatore</a></li>
                    <li><a class="close" data-dismiss="modal" aria-hidden="true">&times;</a></li>
                </ul>
            </div> <!-- fine header -->
            <div class="modal-body">
                <div class="tab-content">
                    <!-- tab login studente -->
                    <div id="login-stud" class="tab-pane fade in active">
                        <div class="logmod__alter-container">
                            <a id="facebook_studente" href="#" onclick="LoginFB(this.id)" class="connect facebook">
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
                        <div class="logmod__heading">
                            <span class="logmod__heading-subtitle">
                                Oppure inserisci le tue <strong>credenziali</strong>
                            </span>
                        </div> 
                        <div class="logmod__form">
                            <form id="studente-login" accept-charset="utf-8" action="ServletController" method="POST" class="simform">
                                <input type="hidden" name="action" value="login-studente" />
                                <div class="sminputs">
                                    <div class="input full">
                                        <label class="string optional" for="user-email">Email*</label>
                                        <input class="string optional" maxlength="255" name="user-email" placeholder="Email" type="email" size="50" />
                                    </div>
                                </div>
                                <div class="sminputs">
                                    <div class="input full">
                                        <label class="string optional" for="user-pw">Password *</label>
                                        <input class="string optional" maxlength="255" name="user-pw" placeholder="Password" type="password" size="50" />
                                        <span class="hide-password">Show</span>
                                    </div>
                                </div>
                                <div class="simform__actions"> <!--
                                    <input id="submit-login-stud" class="sumbit" name="commit" type="sumbit" value="Log In" /> -->

                                    <a href="#0" id="submit-login-stud" class="btn btn-lg btn-success" role="button"
                                       data-toggle="popover" data-trigger="manual" data-content="" disabled="disabled"> 
                                        Log In 
                                    </a>

                                    <span class="simform__actions-sidetext">
                                        <!--<a class="special" target="_blank" role="link">Password dimenticata?</a>-->
                                        <a href="#reg-stud" data-toggle="tab"> Non hai un account? </a>
                                    </span>
                                </div> 
                            </form>
                        </div>
                        <!--
                        <p class="cd-form-bottom-message">
                            <a href="#reg-stud" data-toggle="tab"> Non hai un account? </a>
                        </p> -->
                    </div>
                    <!-- tab nascosta: registrazione studente -->
                    <div id="reg-stud" class="tab-pane fade">
                        <div class="logmod__heading">
                            <span class="logmod__heading-subtitle">
                                Inserisci le informazioni richieste <strong>per creare un account</strong>
                            </span>
                        </div>
                        <div class="logmod__form">
                            <form id="studente-reg" accept-charset="utf-8" action="ServletController" method="POST" lcass="simform">
                                <input type="hidden" name="action" value="signup-studente"/>
                                <div class="sminputs">
                                    <div class="input string optional">
                                        <label class="string optional" for="user-name">Nome*</label>
                                        <input class="string optional" maxlength="255" name="user-name" placeholder="Il tuo nome" type="text" size="50" />
                                    </div>
                                    <div class="input string optional">
                                        <label class="string optional" for="user-surname">Cognome*</label>
                                        <input class="string optional" maxlength="255" name="user-surname" placeholder="Il tuo cognome" type="text" size="50" />
                                    </div>

                                </div>
                                <div class="sminputs">
                                    <div class="input full">
                                        <label class="string optional" for="user-email">Email*</label>
                                        <input class="string optional" maxlength="255" name="user-email" placeholder="La tua email" type="email" size="50" />
                                    </div> 
                                </div>
                                <div class="sminputs">
                                    <div class="input string optional">
                                        <label class="string optional" for="user-pw">Password *</label>
                                        <input class="string optional" maxlength="255" name="user-pw" placeholder="Password" type="text" size="50" />
                                    </div>
                                    <div class="input string optional">
                                        <label class="string optional" for="user-pw-repeat">Ripeti password *</label>
                                        <input class="string optional" maxlength="255" name="user-pw-repeat" placeholder="Ripeti password" type="text" size="50" />
                                    </div>
                                </div>
                                <div class="simform__actions">
                                    <!--
                                    <input id="submit-reg-stud" class="sumbit" name="commit" type="sumbit" value="Create Account" /> -->

                                    <a href="#0" id="submit-reg-stud" class="btn btn-lg btn-success" role="button"
                                       data-toggle="popover" data-trigger="manual" data-content="" disabled="disabled"> 
                                        Crea Account
                                    </a>

                                    <span class="simform__actions-sidetext">
                                        <!--By creating an account you agree to our <a class="special" target="_blank" role="link">Terms & Privacy</a>-->
                                        <a data-toggle="tab" href="#login-stud">Torna al login</a>
                                    </span>
                                </div> 
                            </form>
                        </div> 
                        <!--
                        <p class="cd-form-bottom-message">
                            <a data-toggle="tab" href="#login-stud">Torna al login</a>
                        </p> -->
                    </div>
                    <!-- tab login locatore -->
                    <div id="login-loc" class="tab-pane fade">
                        <div class="logmod__alter">
                            <div class="logmod__alter-container">
                                <a id="facebook_locatore" href="#" onclick="LoginFB(this.id)" class="connect facebook">
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
                            <form id="locatore-login" accept-charset="utf-8" action="ServletController" method="POST" class="simform">
                                <input type="hidden" name="action" value="login-locatore" />
                                <div class="sminputs">
                                    <div class="input full">
                                        <label class="string optional" for="user-email">Email*</label>
                                        <input class="string optional" maxlength="255" name="user-email" placeholder="Email" type="email" size="50" />
                                    </div>
                                </div>
                                <div class="sminputs">
                                    <div class="input full">
                                        <label class="string optional" for="user-pw">Password *</label>
                                        <input class="string optional" maxlength="255" name="user-pw" placeholder="Password" type="password" size="50" />
                                        <span class="hide-password">Show</span>
                                    </div>
                                </div>
                                <div class="simform__actions">
                                    <!--
                                    <input id="submit-login-loc" class="sumbit" name="commit" type="sumbit" value="Log In"/> -->

                                    <a href="#0" id="submit-login-loc" class="btn btn-lg btn-success" role="button"
                                       data-toggle="popover" data-trigger="manual" data-content="" disabled="disabled"> 
                                        Log In 
                                    </a>
                                    <!--
                                    <a tabindex="0" class="btn btn-lg btn-danger" role="button" data-toggle="popover" data-trigger="focus" title="Dismissible popover" data-content="And here's some amazing content. It's very engaging. Right?">Log in</a>
                                    -->

                                    <span class="simform__actions-sidetext">
                                        <!--<a class="special" target="_blank" role="link">Password dimenticata?</a>-->
                                        <a href="#reg-loc" data-toggle="tab"> Non hai un account? </a>
                                    </span>
                                </div> 
                            </form>
                        </div>
                        <!--
                                                <p class="cd-form-bottom-message2">
                                                    <a href="#reg-loc" data-toggle="tab"> Non hai un account? </a>
                                                </p> -->


                    </div> 
                    <!-- tab nasconsta: registrazione locatore --> 
                    <div id="reg-loc" class="tab-pane fade">
                        <div class="logmod__heading">
                            <span class="logmod__heading-subtitle">
                                Inserisci le informazioni richieste <strong>per creare un account</strong>
                            </span>
                        </div>
                        <div class="logmod__form">
                            <form id="locatore-reg" accept-charset="utf-8" action="ServletController" method="POST" class="simform">
                                <input type="hidden" name="action" value="signup-locatore" />
                                <div class="sminputs">
                                    <div class="input string optional">
                                        <label class="string optional" for="user-name">Nome*</label>
                                        <input class="string optional" maxlength="255" name="user-name" placeholder="Il tuo nome" type="text" size="50" />
                                    </div>
                                    <div class="input string optional">
                                        <label class="string optional" for="user-surname">Cognome*</label>
                                        <input class="string optional" maxlength="255" name="user-surname" placeholder="Il tuo cognome" type="text" size="50" />
                                    </div>

                                </div>
                                <div class="sminputs">
                                    <div class="input string optional">
                                        <label class="string optional" for="user-phone">Numero di telefono*</label>
                                        <input class="string optional" maxlength="255" name="user-phone" placeholder="Il tuo numero di telefono" type="text" size="50" />
                                    </div>
                                    <div class="input string optional">
                                        <label class="string optional" for="user-email">Email*</label>
                                        <input class="string optional" maxlength="255" name="user-email" placeholder="La tua email" type="email" size="50" />
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
                                        <input class="string optional" maxlength="255" name="user-pw" placeholder="Password" type="password" size="50" />
                                    </div>
                                    <div class="input string optional">
                                        <label class="string optional" for="user-pw-repeat">Ripeti password *</label>
                                        <input class="string optional" maxlength="255" name="user-pw-repeat" placeholder="Ripeti password" type="password" size="50" />
                                    </div>
                                </div>
                                <div class="simform__actions">
                                    <a href="#0" id="submit-reg-loc" class="btn btn-lg btn-success" role="button"
                                       data-toggle="popover" data-trigger="manual" data-content="" disabled="disabled"> 
                                        Crea Account
                                    </a>

                                    <!--
                                    <input id="submit-reg-loc" class="sumbit" name="commit" type="sumbit" value="Create Account" /> -->
                                    <span class="simform__actions-sidetext">
                                        <!--By creating an account you agree to our <a class="special" target="_blank" role="link" disabled>Terms & Privacy</a>-->
                                        <a href="#login-loc" data-toggle="tab">Torna al login</a>
                                    </span>
                                </div> 
                            </form>
                        </div> 
                        <!--
                        <p class="cd-form-bottom-message">
                            <a href="#login-loc" data-toggle="tab">Torna al login</a>
                        </p> -->
                    </div>

                </div> <!-- tab content --> 
            </div>
        </div>

    </div>
</div>
<!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script> -->

<!-- FACEBOOK -->
<form id="formLogin" method="POST" action="ServletController">
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


<script src="include/js/login/modal_validation_and_stuff.js"></script> <!-- Gem jQuery -->
<script src="include/js/login/ajax_req_and_stuff.js"></script>
