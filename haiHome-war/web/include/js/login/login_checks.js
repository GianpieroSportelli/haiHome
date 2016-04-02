jQuery(document).ready(function ($) {
    var $submit_login_stud = $('#submit-login-stud'),
            $submit_reg_stud = $('#submit-reg-stud'),
            $submit_login_loc = $('#submit-login-loc'),
            $submit_reg_loc = $('#submit-reg-loc');

    var $form_studente_login = $('#studente-login'),
            $form_studente_reg = $('#studente-reg'),
            $form_locatore_login = $('#locatore-login'),
            $form_locatore_reg = $('#locatore-reg');

    var iname = 'input[name="user-name"]',
            isurname = 'input[name="user-surname"]',
            iemail = 'input[name="user-email"]',
            ipwd = 'input[name="user-pw"]',
            ipwd2 = 'input[name="user-pw-repeat"]',
            iphone = 'input[name="user-phone"]'; //random

    var email_regex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/,
            phone_regex = /\(?([0-9]{3})\)?([ .-]?)([0-9]{3})\2([0-9]{4})/,
            /* Password expression that requires one lower case letter, 
             * one upper case letter, one digit, 6-13 length, and no spaces. */
            password_regex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).{4,8}$/;

    $submit_reg_stud.on('click', function () {
        if ($submit_reg_stud.attr("disabled") === "disabled")
            return false;

        console.log('click reg studente');
        
        $.post($form_studente_reg.attr("action"), //servlet
                $form_studente_reg.serialize(), //data
                function (response) {
                    if (response === "OK") {
                        $submit_reg_stud.attr('data-content', "registrazione avvenuta");
                        $submit_reg_stud.popover('show');
                    } else {
                        $submit_reg_stud.attr('data-content', response);
                        $submit_reg_stud.popover('show');
                    }
                });

    });

    $submit_login_stud.on('click', function () {
        if ($submit_login_stud.attr("disabled") === "disabled")
            return false;
        console.log('click su submit login studente'); //form_studente_login.submit(); 

        $.post($form_studente_login.attr("action"), //servlet
                $form_studente_login.serialize(), //data
                function (response) {
                    if (response === "OK") {
                        window.location.replace('UserProfile.jsp');
                    } else {
                        $submit_login_stud.attr('data-content', response);
                        $submit_login_stud.popover('show');
                    }
                });


    });


    $submit_reg_loc.on('click', function () {
        if ($submit_reg_loc.attr("disabled") === "disabled")
            return false;

        console.log('click reg locatore');

        // controlli input client side - inutili se non per la presentazione
        //var name_check = $("#locatore-reg").find(iname).val().trim() !== "";
        //var surname_check = $("#locatore-reg").find(isurname).val().trim() !== "";
        var email_check = email_regex.test($form_locatore_reg.find(iemail).val().trim());
        var phone_check = phone_regex.test($form_locatore_reg.find(iphone).val().trim());
        var pw_check = password_regex.test($form_locatore_reg.find(ipwd).val());
        var pw2_check = $form_locatore_reg.find(ipwd).val() === $form_locatore_reg.find(ipwd2).val();

        if (email_check && phone_check && pw_check && pw2_check) {
            console.log("ok");

            $.post($form_locatore_reg.attr("action"), //servlet
                    $form_locatore_reg.serialize(), //data
                    function (response) {
                        if (response === "OK") {
                            alert("procedo...");
                        } else {
                            $submit_reg_loc.attr('data-content', response);
                            $submit_reg_loc.popover('show');
                        }
                    });

        } else {
            var popover_message = "";

            if (!email_check)
                popover_message = "email non valida"
            else if (!phone_check)
                popover_message += "numero di telefono non valido";
            else if (!pw_check)
                popover_message += "password non valida";
            else if (!pw2_check)
                popover_message += "Password mismatch";

            $submit_reg_loc.attr('data-content', popover_message);
            $submit_reg_loc.popover('show');

            console.log(popover_message);
        }
    });

    $submit_login_loc.on('click', function () {
        if ($submit_login_loc.attr("disabled") === "disabled") {
            return false;
        }
        console.log('click su submit login locatore');

        if (email_regex.test($form_locatore_login.find(iemail).val().trim())) {
            $.post($form_locatore_login.attr("action"), $form_locatore_login.serialize(), function (response) {
                if (response === "OK") {
                    // qualcosa...
                    alert("eseguo login");
                } else {
                    $submit_login_loc.attr('data-content', response);
                    $submit_login_loc.popover('show');
                }
            });
        } else {
            $submit_login_loc.attr('data-content', 'email non valida');
            $submit_login_loc.popover('show');
        }
    });
});