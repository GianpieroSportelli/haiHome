jQuery(document).ready(function ($) {
    var $submit_login_stud = $('#submit-login-stud'),
            $submit_reg_stud = $('#submit-reg-stud'),
            $submit_login_loc = $('#submit-login-loc'),
            $submit_reg_loc = $('#submit-reg-loc');

    var $form_studente_login = $('#studente-login'),
            $form_studente_reg = $('#studente-reg'),
            $form_locatore_login = $('#locatore-login'),
            $form_locatore_reg = $('#locatore-reg');


    //questa roba può servire per popover più mirati 
    var iname = 'input[name="user-name"]',
            isurname = 'input[name="user-surname"]',
            iemail = 'input[name="user-email"]',
            ipwd = 'input[name="user-pw"]',
            ipwd2 = 'input[name="user-pw-repeat"]',
            iphone = 'input[name="user-phone"]'; //random 


    $submit_reg_stud.on('click', function () {
        if ($submit_reg_stud.attr("disabled") === "disabled")
            return false;

        console.log('click reg studente');

        if ($form_studente_reg.find(ipwd).val() === $form_studente_reg.find(ipwd2).val()) {
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
        } else {
            $submit_reg_stud.attr('data-content', "le password non coincidono");
            $submit_reg_stud.popover('show');
        }

    });

    $submit_login_stud.on('click', function () {
        if ($submit_login_stud.attr("disabled") === "disabled")
            return false;
        console.log('click su submit login studente'); //form_studente_login.submit(); 

        $.post($form_studente_login.attr("action"), //servlet
                $form_studente_login.serialize(), //data
                function (response) {
                    if (response === "OK") {
                        window.location.replace('index.jsp');
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

        if ($form_locatore_reg.find(ipwd).val() === $form_locatore_reg.find(ipwd2).val()) {
            $.post($form_locatore_reg.attr("action"), //servlet
                    $form_locatore_reg.serialize(), //data
                    function (response) {
                        if (response === "OK") {
                            $submit_reg_loc.attr('data-content', "registrazione avvenuta");
                            $submit_reg_loc.popover('show');
                        } else {
                            $submit_reg_loc.attr('data-content', response);
                            $submit_reg_loc.popover('show');
                        }
                    });
        } else {
            $submit_reg_stud.attr('data-content', "le password non coincidono");
            $submit_reg_stud.popover('show');
        }
    });

    $submit_login_loc.on('click', function () {
        if ($submit_login_loc.attr("disabled") === "disabled")
            return false;

        console.log('click su submit login locatore');

        $.post($form_locatore_login.attr("action"), $form_locatore_login.serialize(), function (response) {
            if (response === "OK") {
                window.location.replace('index.jsp');
            } else {
                $submit_login_loc.attr('data-content', response);
                $submit_login_loc.popover('show');
            }
        });
    });
});