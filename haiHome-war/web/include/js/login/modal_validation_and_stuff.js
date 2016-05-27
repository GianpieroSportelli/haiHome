jQuery(document).ready(function ($) {
    var myModal = $('#modalLogin');

    var email_regex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    var phone_regex = /\(?([0-9]{3})\)?([ .-]?)([0-9]{3})\2([0-9]{4})/;
    /* Password expression that requires one lower case letter, 
     * one upper case letter, one digit, 6-13 length, and no spaces. */
    var password_regex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).{4,8}$/;



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
   
    //disable submit button until all fields have valid values
    function check_filled_form($form, $submit) {
        $form.keyup(function () {
            var empty = false;

            
            $form.each(function () {
                var name = $(this).attr("name");
                var input_value = $(this).val().trim();

                if (((name === "user-name" || name === "user-surname") && input_value == '') ||
                        (name === "user-email" && !email_regex.test(input_value)) ||
                        (name === "user-phone" && !phone_regex.test(input_value)) ||
                        ((name === "user-pw" || name === "user-pw-repeat") && input_value == '')) { 
                    empty = true;
                    return false;
                }
            });

            empty ? $submit.attr('disabled', 'disabled') : $submit.removeAttr('disabled');
        });
    }

    check_filled_form($('#locatore-login input'), $('#submit-login-loc'));
    check_filled_form($('#locatore-reg input'), $('#submit-reg-loc'));
    check_filled_form($('#studente-login input'), $('#submit-login-stud'));
    check_filled_form($('#studente-reg input'), $('#submit-reg-stud'));

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