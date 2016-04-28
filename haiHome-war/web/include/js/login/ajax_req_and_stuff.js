jQuery(document).ready(function ($) {
    //questa roba può servire per popover più mirati 
    var iname = 'input[name="user-name"]',
            isurname = 'input[name="user-surname"]',
            iemail = 'input[name="user-email"]',
            ipwd = 'input[name="user-pw"]',
            ipwd2 = 'input[name="user-pw-repeat"]',
            iphone = 'input[name="user-phone"]'; //random 


    function submit_registration($form, $submit) {
        $submit.on('click', function () {
            if ($submit.attr("disabled") === "disabled")
                return false;

            console.log('click registrazione...');

            if ($form.find(ipwd).val() === $form.find(ipwd2).val()) {
                $.post($form.attr("action"), // target servlet
                        $form.serialize(), // data
                        function (response) {
                            if (response === "OK")
                                $submit.attr('data-content', 'registrazione avvenuta');
                            else
                                $submit.attr('data-content', response);
                        });
            } else {
                $submit.attr('data-content', "le password non coincidono");
            }

            $submit.popover('show');
        });
    }


    function submit_login($form, $submit, destination) {
        $submit.on('click', function () {
            if ($submit.attr("disabled") === "disabled")
                return false;

            console.log('click login...');

            $.post($form.attr("action"), //servlet
                    $form.serialize(), //data
                    function (response) {
                        if (response === "OK") {
                            window.location.replace(destination);
                        } else {
                            $submit.attr('data-content', response);
                            $submit.popover('show');
                        }
                    });
        });
    }

    submit_login($('#locatore-login'), $('#submit-login-loc'), "locatore-profile.jsp");
    submit_login($('#studente-login'), $('#submit-login-stud'), "index.jsp");
    submit_registration($('#locatore-reg'), $('#submit-reg-loc'));
    submit_registration($('#studente-reg'), $('#submit-reg-stud'));
});