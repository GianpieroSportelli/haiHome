jQuery(document).ready(function ($) {
    var validate_login_studente = $('#submit-login-stud'),
            validate_reg_studente = $('#submit-reg-stud'),
            validate_login_locatore = $('#submit-login-loc'),
            validate_reg_locatore = $('#submit-reg-loc');

    var form_studente_login = $('#studente-login'),
            form_studente_reg = $('#studente-reg'),
            form_locatore_login = $('#locatore-login'),
            form_locatore_reg = $('#locatore-reg');

    var input_name = 'input[name="user-name"]',
        input_surname = 'input[name="user-surname"]',
        input_email = 'input[name="user-email"]',
        input_pwd = 'input[name="user-pw"]',
        input_pwd2 = 'input[name="user-pw-repeat"]',
        input_phone = 'input[name="user-phone"]';

    var email_pattern = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/,
        phone_pattern = /\(?([0-9]{3})\)?([ .-]?)([0-9]{3})\2([0-9]{4})/,
            /*
             * Password expresion that requires one lower case letter, 
             * one upper case letter, one digit, 6-13 length, and no spaces. */
        password_pattern = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).{4,8}$/;

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
         check_email = email_pattern.test(email.val().trim()),
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
        console.log('click reg locatore');

        // controlli input client side - inutili se non per la presentazione
        var name_check = $("#locatore-reg").find(input_name).val().trim() !== "";
        var surname_check = $("#locatore-reg").find(input_surname).val().trim()  !== "";
        var email_check = email_pattern.test($("#locatore-reg").find(input_email).val().trim());
        var phone_check = phone_pattern.test($("#locatore-reg").find(input_phone).val().trim());
        var pw_check = password_pattern.test($("#locatore-reg").find(input_pwd).val()); 
        var pw2_check = $("#locatore-reg").find(input_pwd).val() === $("#locatore-reg").find(input_pwd2).val(); 
        
        if (name_check && surname_check && email_check && phone_check && pw_check && pw2_check) {
            console.log("ok"); 
            
            $.post($("#locatore-reg").attr("action"), //servlet
                   $("#locatore-reg").serialize(), //data
                   function(response) {
                       console.log(response); 
                   });
            
        }
        else {
            var message = "NO: "; 
            if (!name_check) message += "name "; 
            if (!surname_check) message += "surname "; 
            if (!email_check) message += "email "; 
            if (!phone_check) message += "phone "; 
            if (!pw_check) message += "pw "; 
            if (!pw2_check) message += "pw2";
            
            console.log(message);
        }
    });

    validate_login_studente.on('click', function () {
        console.log('click su submit login studente'); //form_studente_login.submit(); 
    });

    validate_login_locatore.on('click', function () {
        console.log('click su submit login locatore');

        var $form = $('#locatore-login');
        $.post($form.attr("action"), $form.serialize(), function (response) {
            alert(response);
        });


        /*
         $.post("ServletController", 
         
         function(responseJson) {
         alert(responseJson); 
         });  */
    });




});