jQuery(document).ready(function ($) {
    var formModal = $('.cd-user-modal'),
            formModalTab = $('.cd-switcher'),
            tabStudente = formModalTab.children('li').eq(0).children('a'),
            tabLocatore = formModalTab.children('li').eq(1).children('a'),
            
            formLoginStudente = formModal.find('#cd-login'),
            formRegistrazioneStudente = formModal.find('#cd-reset-password'),
            /* login e registrazione studente  */
            gotoRegistrazioneStudente = formLoginStudente.find('.cd-form-bottom-message a'),
            backToLoginStudente = formRegistrazioneStudente.find('.cd-form-bottom-message a'),
            
            formLoginLocatore = formModal.find('#cd-signup'),
            formRegistrazioneLocatore = formModal.find('#cd-registrazione-locatore'),     
            /* login e registrazione locatore */
            gotoRegistrazioneLocatore = formLoginLocatore.find('.cd-form-bottom-message2 a'),
            backToLoginLocatore = formRegistrazioneLocatore.find('.cd-form-bottom-message2 a'),
            
            mainNav = $('.main-nav');

    //open modal
    mainNav.on('click', function (event) {
        $(event.target).is(mainNav) && mainNav.children('ul').toggleClass('is-visible');
    });

    //open sign-up form
    mainNav.on('click', '.cd-signup', login_locatore_selected);
    //open login-form form
    mainNav.on('click', '.cd-signin', login_studente_selected);

    //close modal
    formModal.on('click', function (event) {
        if ($(event.target).is(formModal) || $(event.target).is('.cd-close-form')) {
            formModal.removeClass('is-visible');
        }
    });
    //close modal when clicking the esc keyboard button
    $(document).keyup(function (event) {
        if (event.which == '27') {
            formModal.removeClass('is-visible');
        }
    });

    //switch from a tab to another
    formModalTab.on('click', function (event) {
        event.preventDefault();
        ($(event.target).is(tabStudente)) ? login_studente_selected() : login_locatore_selected();
    });

    //hide or show password
    $('.hide-password').on('click', function () {
        var togglePass = $(this),
                passwordField = togglePass.prev('input');

        ('password' == passwordField.attr('type')) ? passwordField.attr('type', 'text') : passwordField.attr('type', 'password');
        ('Hide' == togglePass.text()) ? togglePass.text('Show') : togglePass.text('Hide');
        //focus and move cursor to the end of input field
        passwordField.putCursorAtEnd();
    });

    
    //show form registrazione studente
    gotoRegistrazioneStudente.on('click', function (event) {
        event.preventDefault();
        registrazione_studente_selected();
    });

    //torna al login dal form registrazione studente
    backToLoginStudente.on('click', function (event) {
        event.preventDefault();
        login_studente_selected();
    });
    
    gotoRegistrazioneLocatore.on('click', function (event) {
        event.preventDefault();
        registrazione_locatore_selected(); 
    }); 
    
    backToLoginLocatore.on('click', function (event) {
        event.preventDefault(); 
        login_locatore_selected(); // questa è sbagliata
    });

    /* Tab studente */
    function login_studente_selected() {
        mainNav.children('ul').removeClass('is-visible');
        formModal.addClass('is-visible');
        formLoginStudente.addClass('is-selected');
        formLoginLocatore.removeClass('is-selected');
        
        formRegistrazioneStudente.removeClass('is-selected');
        formRegistrazioneLocatore.removeClass('is-selected');
        
        tabStudente.addClass('selected');
        tabLocatore.removeClass('selected');
    }

    /* Tab Locatore */
    function login_locatore_selected() {
        mainNav.children('ul').removeClass('is-visible');
        formModal.addClass('is-visible');
        formLoginStudente.removeClass('is-selected');
        formLoginLocatore.addClass('is-selected');
        
        formRegistrazioneStudente.removeClass('is-selected');
        formRegistrazioneLocatore.removeClass('is-selected');
        
        tabStudente.removeClass('selected');
        tabLocatore.addClass('selected');
    }

    /* Tab registrazione studente */
    function registrazione_studente_selected() {
        formLoginStudente.removeClass('is-selected');
        formLoginLocatore.removeClass('is-selected');
        formRegistrazioneStudente.addClass('is-selected');
    }
    
    /* Tab registrazione locatore */ 
    function registrazione_locatore_selected() {
        formLoginStudente.removeClass('is-selected'); 
        formLoginLocatore.removeClass('is-selected'); 
        formRegistrazioneLocatore.addClass('is-selected'); 
    } // funzionera'???

    //REMOVE THIS - it's just to show error messages 
    /*
    formLoginStudente.find('input[type="submit"]').on('click', function (event) {
        event.preventDefault();
        formLoginStudente.find('input[type="email"]').toggleClass('has-error').next('span').toggleClass('is-visible');
    });
    formLoginLocatore.find('input[type="submit"]').on('click', function (event) {
        event.preventDefault();
        formLoginLocatore.find('input[type="email"]').toggleClass('has-error').next('span').toggleClass('is-visible');
    }); */


    //IE9 placeholder fallback
    //credits http://www.hagenburger.net/BLOG/HTML5-Input-Placeholder-Fix-With-jQuery.html
    if (!Modernizr.input.placeholder) {
        $('[placeholder]').focus(function () {
            var input = $(this);
            if (input.val() == input.attr('placeholder')) {
                input.val('');
            }
        }).blur(function () {
            var input = $(this);
            if (input.val() == '' || input.val() == input.attr('placeholder')) {
                input.val(input.attr('placeholder'));
            }
        }).blur();
        $('[placeholder]').parents('form').submit(function () {
            $(this).find('[placeholder]').each(function () {
                var input = $(this);
                if (input.val() == input.attr('placeholder')) {
                    input.val('');
                }
            })
        });
    }

});


//credits http://css-tricks.com/snippets/jquery/move-cursor-to-end-of-textarea-or-input/
jQuery.fn.putCursorAtEnd = function () {
    return this.each(function () {
        // If this function exists...
        if (this.setSelectionRange) {
            // ... then use it (Doesn't work in IE)
            // Double the length because Opera is inconsistent about whether a carriage return is one character or two. Sigh.
            var len = $(this).val().length * 2;
            this.focus();
            this.setSelectionRange(len, len);
        } else {
            // ... otherwise replace the contents with itself
            // (Doesn't work in Google Chrome)
            $(this).val($(this).val());
        }
    });
};