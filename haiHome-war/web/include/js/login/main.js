jQuery(document).ready(function ($) {
    var myModal = $('#myModal');

    //initialize popovers in the page 
    $(function () {
        $('[data-toggle="popover"]').popover()
    });

    //close popovers when the user clicks 
    // http://jsfiddle.net/guya/fjZja/
    myModal.on('click', function (e) {
        if (typeof $(e.target).data('original-title') == 'undefined' &&
                !$(e.target).parents().is('.popover.in')) {
            $('[data-original-title]').popover('hide');
        }
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

    //close modal when clicking the ESC keyboard button
    $(document).keyup(function (event) {
        if (event.which == '27') {
            myModal.modal('hide');
        }
    });

    //disable submit button until all fields have values
    /* se si riesce a fare una funzione parametrizzata per tutti i form e' meglio, 
     * così fa cagare...ma meglio di niente */
    (function () {
        $('#locatore-login input').keyup(function () {
            var empty = false;
            $('#locatore-login input').each(function () {
                if ($(this).val().trim() == '') 
                    empty = true;
            });

            empty ? $('#submit-login-loc').attr('disabled', 'disabled') :
                    $('#submit-login-loc').removeAttr('disabled');
        });
    })();
    
    (function () {
        $('#locatore-reg input').keyup(function () {
            var empty = false;
            $('#locatore-reg input').each(function () {
                if ($(this).val().trim() == '') 
                    empty = true;
            });

            empty ? $('#submit-reg-loc').attr('disabled', 'disabled') :
                    $('#submit-reg-loc').removeAttr('disabled');
        });
    })();
    


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