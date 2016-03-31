jQuery(document).ready(function ($) {
    var validate_login_studente = $('#submit-login-stud'),
            validate_reg_studente = $('#submit-reg-stud'),
            validate_login_locatore = $('#submit-login-loc'),
            validate_reg_locatore = $('#submit-reg-loc');

    var form_studente_login = $('#studente-login'),
            form_studente_reg = $('#studente-reg'),
            form_locatore_login = $('#locatore-login'),
            form_locatore_reg = $('#locatore-reg');

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

    validate_reg_studente.on('click', function () {
        console.log('click reg studente');
        /*
         var name = form_studente_reg.find('input[name="user-name"]'),
         surname = form_studente_reg.find('input[name="user-surname"]'),
         email = form_studente_reg.find('input[name="user-email"]'),
         pwd = form_studente_reg.find('input[name="user-pw"]'),
         pwd2 = form_studente_reg.find('input[name="user-pw-repeat"]'); 
         
         var check_name = name.val().trim() !== "", 
         check_surname = surname.val().trim() !== "", 
         check_email = email_regex.test(email.val().trim()),
         check_pwd = pwd.val().trim() !== "" && pwd.val().trim() === pwd2.val().trim(); 
         
         if (check_name && check_surname && check_email && check_pwd) {
         console.log('submit');form_studente_reg.submit(); 
         }
         else {
         if (!check_name) {
         console.log('name');
         
         }
         if (!check_surname) {
         surname.focus();
         console.log('surname');
         }
         if (!check_email) console.log('email');
         if (!check_pwd) console.log('password'); 
         } */
    });

    validate_reg_locatore.on('click', function () {
        if (validate_reg_locatore.attr("disabled") === "disabled") 
            return false; 
    
        console.log('click reg locatore');

        // controlli input client side - inutili se non per la presentazione
        //var name_check = $("#locatore-reg").find(iname).val().trim() !== "";
        //var surname_check = $("#locatore-reg").find(isurname).val().trim() !== "";
        var email_check = email_regex.test($("#locatore-reg").find(iemail).val().trim());
        var phone_check = phone_regex.test($("#locatore-reg").find(iphone).val().trim());
        var pw_check = password_regex.test($("#locatore-reg").find(ipwd).val());
        var pw2_check = $("#locatore-reg").find(ipwd).val() === $("#locatore-reg").find(ipwd2).val();

        if (name_check && surname_check && email_check && phone_check && pw_check && pw2_check) {
            console.log("ok");

            $.post($("#locatore-reg").attr("action"), //servlet
                    $("#locatore-reg").serialize(), //data
                    function (response) {
                        if (response === "OK") {
                            alert("procedo...");
                        } else {
                            $('#submit-reg-loc').attr('data-content', response);
                            $('#submit-reg-loc').popover('show');
                        }
                    });

        } else {
            var message = "NO: ";/*
            if (!name_check)
                message += "name ";
            if (!surname_check)
                message += "surname "; */
            if (!email_check)
                message += "email ";
            if (!phone_check)
                message += "phone ";
            if (!pw_check)
                message += "pw ";
            if (!pw2_check)
                message += "pw2";

            console.log(message);
        }
    });

    validate_login_studente.on('click', function () {
        console.log('click su submit login studente'); //form_studente_login.submit(); 
    });

    validate_login_locatore.on('click', function () {
        if (validate_login_locatore.attr("disabled") === "disabled") {
            return false; 
        }
        console.log('click su submit login locatore');

        if (email_regex.test(form_locatore_login.find(iemail).val().trim())) {
            var $form = $('#locatore-login');
            $.post($form.attr("action"), $form.serialize(), function (response) {
                if (response === "OK") {
                    // qualcosa...
                    alert("eseguo login");
                } else {
                    $('#submit-login-loc').attr('data-content', response);
                    $('#submit-login-loc').popover('show');
                }
            });
        } else {
            $('#submit-login-loc').attr('data-content', 'email non valida');
            $('#submit-login-loc').popover('show');
        }
    });




});